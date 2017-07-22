package pe.gob.sunat.recurso2.administracion.siga.firma.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.rpc.ParameterMode;

import net.sf.sojo.interchange.json.JsonSerializer;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pe.gob.sunat.recurso2.administracion.siga.archivo.util.ReporteArchivoBean;
import pe.gob.sunat.recurso2.administracion.siga.archivo.util.ReporteJasperBean;
import pe.gob.sunat.recurso2.administracion.siga.archivo.util.ReporteJasperUtil;
import pe.gob.sunat.recurso2.administracion.siga.firma.model.bean.MaestroPersonal;
import pe.gob.sunat.recurso2.administracion.siga.firma.model.bean.T5282Archbin;
import pe.gob.sunat.recurso2.administracion.siga.firma.model.bean.T7073FirmaElectron;
import pe.gob.sunat.recurso2.administracion.siga.firma.model.bean.T7074DocumentoFirm;
import pe.gob.sunat.recurso2.administracion.siga.firma.model.bean.T7076DelegacFirma;
import pe.gob.sunat.recurso2.administracion.siga.firma.model.dao.DependenciaDao;
import pe.gob.sunat.recurso2.administracion.siga.firma.model.dao.MaestroPersonalDao;
import pe.gob.sunat.recurso2.administracion.siga.firma.model.dao.T5282DAO;
import pe.gob.sunat.recurso2.administracion.siga.firma.model.dao.T7073FirmaElectronDAO;
import pe.gob.sunat.recurso2.administracion.siga.firma.model.dao.T7074DocumentoFirmDAO;
import pe.gob.sunat.recurso2.administracion.siga.firma.model.dao.T7075TerminoCondDAO;
import pe.gob.sunat.recurso2.administracion.siga.firma.model.dao.T7076DelegacFirmaDAO;
import pe.gob.sunat.recurso2.administracion.siga.firma.util.FirmaConstantes;
import pe.gob.sunat.recurso2.administracion.siga.ldap.service.ValidadorLdapService;

/**
 * @author jcortezro
 *
 */
public class ValidarFirmarServiceImpl implements ValidarFirmarService {

	private static final Log log = LogFactory.getLog(ValidarFirmarServiceImpl.class);
	
	private MaestroPersonalDao maestroPersonalDao;
	private T7073FirmaElectronDAO firmaelectronDao;
	private T7074DocumentoFirmDAO documentofirmDao;
	private T7075TerminoCondDAO terminocondDao;
	private T7076DelegacFirmaDAO delegacfirmaDao;
	private T5282DAO archbinDao;
	private DependenciaDao dependenciaDao;
	
	private ValidadorLdapService validadorLdapService;
	private GrabarDocumentoFirmaService grabarDocumentoFirmaService;
	private ConsultaFirmaService consultaFirmaService;
	
	String urlFirma;
	
	@Override
	public Map<String, Object> validarLdapGenerarGrabarDocumentoFirmado(int iddoc, String nomArchivo, 
			Map<String, Object> cabecera, List<Map<String, Object>> detalles,
			String user, String pass,
			String tipoOperacion, String codEmpleado, String codTipdoc, String numDocumento, 
			String codDependencia, String codDependenciaNormativa, String nomModulo, String nomProceso, 
			String codUsuregis, String dirIpusuregis, String codUsumodif, String dirIpusumodif) 
			throws Exception {
		
		Map<String, Object> resultado = new HashMap<String, Object>();
		
		try {
			/*if (!validadorLdapService.validarLdapLevantado()) {
				resultado.put("error", "1");
				resultado.put("codigoError", "ED03");
				String mensajeError = consultaFirmaService.recuperarDescripcionParametro(FirmaConstantes.PARAMETRO_MENSAJES, "CUS03E03", "D", FirmaConstantes.PARAMETRO_MODULO_SIGA, true);
				resultado.put("mensajeError", mensajeError);//resultado.put("mensajeError", "No es posible establecer comunicaci&oacute;n con el servidor ldap, por favor intentarlo mas tarde.");
			}
			else {
				if (!validadorLdapService.validarUsuario(user, pass)) {
					resultado.put("error", "1");
					resultado.put("codigoError", "CUS01E02");//resultado.put("codigoError", "CUS03E03");
					String mensajeError = consultaFirmaService.recuperarDescripcionParametro(FirmaConstantes.PARAMETRO_MENSAJES, "CUS03E03", "D", FirmaConstantes.PARAMETRO_MODULO_SIGA, true);
					resultado.put("mensajeError", mensajeError);//resultado.put("mensajeError", "El usuario y/o password no es correcto. Se requiere los datos de acceso a INTRANET para continuar el proceso. Por favor, ingresar nuevamente.");
				}
				else {
					resultado = generarGrabarDocumentoFirmado(iddoc, nomArchivo, cabecera, detalles, tipoOperacion, codEmpleado, codTipdoc, numDocumento, codDependencia, codDependenciaNormativa, nomModulo, nomProceso, codUsuregis, dirIpusuregis, codUsumodif, dirIpusumodif);
				}
			}*/
			
			T7073FirmaElectron param1 = new T7073FirmaElectron();
			param1.setCodFirmante(codEmpleado);
			param1.setIndEstfirma(FirmaConstantes.INDICADOR_TRUE); //1 = firma activa
			
			List<T7073FirmaElectron> firmaList = firmaelectronDao.listByParameter(param1);
			if (firmaList.size() == 0) {
				throw new Exception("El usuario no tiene firma electronica activa disponible");
			}
			T7073FirmaElectron firma = firmaList.get(0);
			
			String codFirma = firma.getCodFirma();
			Map<String, Object> resultadoToken = validadorLdapService.validarLdapDevolverToken(user, pass, codFirma);
			if ("0".equals(resultadoToken.get("error"))) {
				String codToken = (String)resultadoToken.get("codToken");
				resultado = generarGrabarDocumentoFirmado(iddoc, nomArchivo, cabecera, detalles, tipoOperacion, codEmpleado, codTipdoc, numDocumento, codDependencia, codDependenciaNormativa, codToken, nomModulo, nomProceso, codUsuregis, dirIpusuregis, codUsumodif, dirIpusumodif);
			}
			else {
				resultado.putAll(resultadoToken);
			}
			
			return resultado;
		}
		catch (Exception e) {
			log.error("error", e);
			throw e;
		}
	}
	
	@Override
	public Map<String, Object> generarGrabarDocumentoFirmado (int iddoc, String nomArchivo,
			Map<String, Object> cabecera, List<Map<String, Object>> detalles,
			String tipoOperacion, String codEmpleado, String codTipdoc,
			String numDocumento, String codDependencia,
			String codDependenciaNormativa, String codToken, String nomModulo,
			String nomProceso, String codUsuregis, String dirIpusuregis, String codUsumodif, String dirIpusumodif) throws Exception {
		
		Map<String, Object> resultado = new HashMap<String, Object>();
		
		try {
			
		  	T7074DocumentoFirm param2 = new T7074DocumentoFirm();
		  	param2.setCodTipdoc(codTipdoc);
		  	param2.setNumDocumento(numDocumento);
		  	param2.setIndEstdoc(FirmaConstantes.INDICADOR_TRUE);
			List<T7074DocumentoFirm> listaDocumentosFirmados = documentofirmDao.listByParameter(param2);
			log.info("firmado: " + listaDocumentosFirmados.size() + " " + FirmaConstantes.OPERACION_NUEVO);
			if (listaDocumentosFirmados.size() > 0 && FirmaConstantes.OPERACION_NUEVO.equals(tipoOperacion)) {
				resultado.put("error", "1");
				resultado.put("codigoError", "CUS03E01");
				String mensajeError = consultaFirmaService.recuperarDescripcionParametro(FirmaConstantes.PARAMETRO_MENSAJES, "CUS03E01", "D", FirmaConstantes.PARAMETRO_MODULO_SIGA, true);
				resultado.put("mensajeError", mensajeError);//resultado.put("mensajeError", "No fue posible generar el documento firmado al ya existir anteriormente.");
			}
			else {
				//DPORRASC - FIRMA SIGA
				//Long numid = new Long(generarDocumentoPdfFirmadoSiga(iddoc, nomArchivo, cabecera, detalles));//Long numid = new Long(666);
				Map<String, Object> resultadoGenerarDocumentoPdfFirmadoSiga = generarDocumentoPdfFirmadoSiga(iddoc, nomArchivo, cabecera, detalles);
				Long numid = new Long((String)resultadoGenerarDocumentoPdfFirmadoSiga.get("numid"));
				byte[] byData = (byte[]) resultadoGenerarDocumentoPdfFirmadoSiga.get("byData");
				
				if (numid <= 0) {
					resultado.put("error", "1");
					resultado.put("codigoError", "CUS03E02");
					String mensajeError = consultaFirmaService.recuperarDescripcionParametro(FirmaConstantes.PARAMETRO_MENSAJES, "CUS03E02", "D", FirmaConstantes.PARAMETRO_MODULO_SIGA, true);
					resultado.put("mensajeError", mensajeError);//resultado.put("mensajeError", "No fue posible generar el documento firmado.");
				}
				else {
					Date fechaValidacionLdap = new Date();
					
					Long codDocaut = grabarDocumentoFirmaService.obtenerSecuencialDocumento();
					////DPORRASC - FIRMA SIGA
					//grabarDocumentoFirmaService.grabarDocumentoFirmado(tipoOperacion, codDocaut, codEmpleado, codTipdoc, numDocumento, codDependencia, codDependenciaNormativa, codToken, nomModulo, nomProceso, nomArchivo, numid, fechaValidacionLdap, codUsuregis, dirIpusuregis, codUsumodif, dirIpusumodif);
					grabarDocumentoFirmaService.grabarDocumentoFirmadoSiga(tipoOperacion, codDocaut, codEmpleado, codTipdoc, numDocumento, codDependencia, codDependenciaNormativa, codToken, nomModulo, nomProceso, nomArchivo, numid, fechaValidacionLdap, codUsuregis, dirIpusuregis, codUsumodif, dirIpusumodif,byData);
					resultado.put("error", "0");
					resultado.put("numid", numid);
					resultado.put("codDocaut", codDocaut);
				}	
			}			
			 			
			return resultado;
		}
		catch (Exception e) {
			log.error("error", e);
			throw e;
		}
	}
	
	/**
	 * Metodo creado para generar firma en SIGA 
	 */
	@Override
	public Map<String, Object> generarDocumentoPdfFirmadoSiga(int iddoc, Map<String, Object> cabecera, List<Map<String, Object>> detalles) throws Exception {
    	
		log.info("Inicio de generarPDF - SIGA");
    	Long numPDF = 0l;
    	Map<String,Object> resultado = new HashMap<String, Object>();
    	
		byte[] byData=null;
		try {
	    	
			ReporteJasperBean params = new ReporteJasperBean();
			params.setParametros(cabecera);
			params.setListaDetalle(detalles);
			params.setFileName("nombreArchivo");
			params.setJasperName("PLANTILLA"+iddoc+".jasper");
			params.setIdJasper(Integer.toString(iddoc));
			ReporteArchivoBean reporte = ReporteJasperUtil.GenerarArchivoPDF(params);
			
			byData=reporte.getData();
			
			//Obtener secuencia 
			Long numid=documentofirmDao.obtenerSecuencialFirmaSiga();
			log.debug("Secuencia de Firma SIGA:"+numid);
			
			resultado.put("numid", Long.toString(numid));
			resultado.put("byData", byData);
			
		}catch(Exception e){
			log.error("error al firmar", e);
			resultado.put("numid", "0");
		}

		return resultado;
	}
	
	
	/**
	 * Metodo creado para generar firma en SIGA 
	 */
	@Override
	public Map<String,Object> generarDocumentoPdfFirmadoSiga(int iddoc, String nombreDocumento, Map<String, Object> cabecera, List<Map<String, Object>> detalles) throws Exception {
    	
   		log.info("Inicio de generarPDF con nuevo nombre");
   		Map<String, Object> numPDF = generarDocumentoPdfFirmadoSiga(iddoc, cabecera, detalles);	
    	
    	/*String nuevoNombreDocumento = nombreDocumento + ".pdf";
    	T5282Archbin paramUpdate = new T5282Archbin();
    	paramUpdate.setNumId(numPDF);
    	paramUpdate.setDesNombre(nuevoNombreDocumento);
    	archbinDao.updateNombre(paramUpdate);*/
    	
   		log.info("numPDF : " + numPDF);    	
   		log.info("Fin de generarPDF con nuevo nombre");
    	
    	return numPDF;
	}
	
	@Override
	public Long generarDocumentoPdfFirmado(int iddoc, Map<String, Object> cabecera, List<Map<String, Object>> detalles) throws Exception {
    	
   		log.info("Inicio de generarPDF");
    	Long numPDF = 0l;
   		
   		Service service = new Service();
   		JsonSerializer serializer = new JsonSerializer();
   		
   		String json = "";
    	Map<String, Object> datos = new HashMap<String, Object>();
    	datos.put("cabecera",cabecera);
    	datos.put("detalle",detalles);
    	
    	json = serializer.serialize(datos).toString();
    	log.info("Cadena json = " + json);
    	
    	log.info("call ws generaPDF");
    	log.info("seteando parametros GENPDF in/out");
    	
    	//A trv?de un servicio generamos el PDF
    	Call callGenPDF = (Call) service.createCall();
   		callGenPDF.setTargetEndpointAddress(urlFirma);   //PRODUCCION
		callGenPDF.setOperationName("genera0");
    	callGenPDF.addParameter("iddoc", XMLType.XSD_INT, ParameterMode.IN);
    	callGenPDF.addParameter("datos", XMLType.XSD_STRING, ParameterMode.IN);
    	callGenPDF.addParameter("tipo", XMLType.XSD_STRING, ParameterMode.IN);
    	callGenPDF.addParameter("modelo", XMLType.XSD_INT, ParameterMode.IN);
    	callGenPDF.addParameter("firma", XMLType.XSD_INT, ParameterMode.IN);
    	callGenPDF.setReturnClass(int.class);
    	
    	try {
        	numPDF = new Long(callGenPDF.invoke(new Object[] {new Long(iddoc),json,"pdf",new Integer(1000),new Integer(1)}).toString()).longValue();    		
    	}
    	catch (Exception e) {
    		log.error("error al firmar", e);
    		numPDF = 0l;
    	}
    	
   		log.info("numPDF : " + numPDF);
   		log.info("Fin de generarPDF");
    	
    	return numPDF;
	}
	
	@Override
	public Long generarDocumentoPdfFirmado(int iddoc, String nombreDocumento, Map<String, Object> cabecera, List<Map<String, Object>> detalles) throws Exception {
    	
   		log.info("Inicio de generarPDF con nuevo nombre");
   		Long numPDF = generarDocumentoPdfFirmado(iddoc, cabecera, detalles);	
    	
    	/*String nuevoNombreDocumento = nombreDocumento + ".pdf";
    	T5282Archbin paramUpdate = new T5282Archbin();
    	paramUpdate.setNumId(numPDF);
    	paramUpdate.setDesNombre(nuevoNombreDocumento);
    	archbinDao.updateNombre(paramUpdate);*/
    	
   		log.info("numPDF : " + numPDF);    	
   		log.info("Fin de generarPDF con nuevo nombre");
    	
    	return numPDF;
	}

	@Override
	public Map<String, Object> validarAutorizadoFirma(String codEmpleado,
			String codDependencia, boolean validacionCompleta) throws Exception {

		log.debug("validarAutorizadoFirma");
		Map<String, Object> resultado = new HashMap<String, Object>();
		Map<String, Object> resultadoParcial;
		
		List<Map<String, Object>> erroresList = new ArrayList<Map<String, Object>>();
		Map<String, Object> error;
		
		Date today = new Date();
		
		resultadoParcial = validarFirmaActiva(codEmpleado);
		if ("1".equals(resultadoParcial.get("error"))) {
			error = new HashMap<String, Object>();
			error.put("codigoError", resultadoParcial.get("codigoError"));
			error.put("mensajeError", resultadoParcial.get("mensajeError"));
			erroresList.add(error);
		}
		else {
			resultado.put("firmaActiva", resultadoParcial.get("firmaActiva"));
		}
		
		if (validacionCompleta) {

			resultadoParcial = validarSiEsJefeDependencia(codEmpleado, codDependencia);
			if ("1".equals(resultadoParcial.get("esJefe"))) {
				resultadoParcial = validarEncargaturaPorDependencia(codEmpleado, codDependencia, today);
				if ("1".equals(resultadoParcial.get("error"))) {
					error = new HashMap<String, Object>();
					error.put("codigoError", resultadoParcial.get("codigoError"));
					error.put("mensajeError", resultadoParcial.get("mensajeError"));
					erroresList.add(error);
				}
			}
			else {
				resultadoParcial = validarEncagadoPorDependencia(codEmpleado, codDependencia, today);
				if ("1".equals(resultadoParcial.get("error"))) {
					error = new HashMap<String, Object>();
					error.put("codigoError", resultadoParcial.get("codigoError"));
					error.put("mensajeError", resultadoParcial.get("mensajeError"));
					erroresList.add(error);
				}
			}
		}
			
		if (erroresList.size() == 0) {
			resultado.put("error", "0");
		}
		else {
			resultado.put("error", "1");
		}
		resultado.put("erroresList", erroresList);

		log.debug("resultado validarAutorizadoFirma: " + resultado);
		return resultado;
	}

	@Override
	public Map<String, Object> validarEncagadoPorDependencia(
			String codEmpleadoDelegado, String codDependencia, Date today)
			throws Exception {

		log.debug("validarEncargadoPorDependencia");
		Map<String, Object> resultado = new HashMap<String, Object>();
		
		Calendar cal = Calendar.getInstance();
		
		T7076DelegacFirma param = new T7076DelegacFirma();
		param.setCodUsudeleg(codEmpleadoDelegado);
		param.setCodUuoo(codDependencia);
		//param.setIndSuspenci(FirmaConstantes.INDICADOR_FALSE);
		param.setIndEstdelega(FirmaConstantes.INDICADOR_TRUE);

		cal.setTime(today);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		param.setFecInideleg(cal.getTime());

		cal.setTime(today);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		param.setFecFindeleg(cal.getTime());
		
		List<T7076DelegacFirma> delegacFirmaList = delegacfirmaDao.listByParameter(param);		
		if (delegacFirmaList.size() > 0) {
			resultado.put("error", "0");
			resultado.put("delegacFirma", delegacFirmaList.get(0));
		}
		else {
			resultado.put("error", "1");
			resultado.put("codigoError", "CUS02E02");
			String mensajeError = consultaFirmaService.recuperarDescripcionParametro(FirmaConstantes.PARAMETRO_MENSAJES, "CUS02E02", "D", FirmaConstantes.PARAMETRO_MODULO_SIGA, true);
			resultado.put("mensajeError", mensajeError);//resultado.put("mensajeError", "El usuario no se encuentra encargado por la persona indicada, en el &aacute;rea y rango de fecha indicada.");
		}
		
		log.debug("resultado validarEncargadoPorDependencia: " + resultado);		
		return resultado;
	}

	@Override
	public Map<String, Object> validarEncargaturaPorDependencia(
			String codEmpleadoResponsable, String codDependencia, Date today)
			throws Exception {

		// codUsurespo es el que delega, codUsudeleg es el que recibe la delegatura, codUuoo es el codigo de dependencia segun siga (el de 4 digitos),
		// indSuspenci 1 si la delegatura se suspendio (se dejo sin efectos) y * si la delegatura no ha sido suspendida, grabar por defecto el valor de * cuando se genera la delegatura
		// fec_inideleg, fec_findeleg se graban con la hora 00:00:00
	  	// evaluar la necesidad de crear E04 para cuando el que intenta firmar no es jefe (evaluar si es que el negocio filtra dicha condicion)  
		
		log.debug("validarEncargaturaPorDependencia");
		Map<String, Object> resultado = new HashMap<String, Object>();
		
		Calendar cal = Calendar.getInstance();
		
		T7076DelegacFirma param = new T7076DelegacFirma();
		param.setCodUsurespo(codEmpleadoResponsable);
		param.setCodUuoo(codDependencia);
		//param.setIndSuspenci(FirmaConstantes.INDICADOR_FALSE);
		param.setIndEstdelega(FirmaConstantes.INDICADOR_TRUE);
		
		cal.setTime(today);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		param.setFecInideleg(cal.getTime());

		cal.setTime(today);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		param.setFecFindeleg(cal.getTime());
		
		List<T7076DelegacFirma> delegacFirmaList = delegacfirmaDao.listByParameter(param);
		if (delegacFirmaList.size() == 0) {
			resultado.put("error", "0");
		}
		else {
			resultado.put("error", "1");
			resultado.put("codigoError", "CUS02E03");
			String mensajeError = consultaFirmaService.recuperarDescripcionParametro(FirmaConstantes.PARAMETRO_MENSAJES, "CUS02E03", "D", FirmaConstantes.PARAMETRO_MODULO_SIGA, true);
			resultado.put("mensajeError", mensajeError);//resultado.put("mensajeError", "La fecha actual se encuentra en el rango de fecha de una delegaci&oacute;n registrado por el usuario.");
			resultado.put("delegacFirma", delegacFirmaList.get(0));
		}
		
		log.debug("resultado validarEncargaturaPorDependenci: " + resultado);		
		return resultado;
	}

	@Override
	public Map<String, Object> validarFirmaActiva(String codEmpleado)
			throws Exception {

		log.debug("validarFirmaActiva");
		Map<String, Object> resultado = new HashMap<String, Object>();
		
		MaestroPersonal mpParam = new MaestroPersonal();
		mpParam.setCodigoEmpleado(codEmpleado);
		List<MaestroPersonal> mpList = maestroPersonalDao.obtenerColaboradorConDependencia(mpParam);
		if (mpList.size() > 0 && mpList.get(0) != null && "1".equals(mpList.get(0).getCodigoEstado())) {
			T7073FirmaElectron param = new T7073FirmaElectron();
			param.setCodFirmante(codEmpleado);
			param.setIndEstfirma(FirmaConstantes.INDICADOR_TRUE); //1 = firma activa
			
			List<T7073FirmaElectron> firmaList = firmaelectronDao.listByParameter(param);
			if (firmaList.size() > 0) {
				resultado.put("error", "0");
				resultado.put("firmaActiva", firmaList.get(0));
			}
			else {
				resultado.put("error", "1");
				resultado.put("codigoError", "CUS02E01");
				String mensajeError = consultaFirmaService.recuperarDescripcionParametro(FirmaConstantes.PARAMETRO_MENSAJES, "CUS02E01", "D", FirmaConstantes.PARAMETRO_MODULO_SIGA, true);
				resultado.put("mensajeError", mensajeError);//resultado.put("mensajeError", "El usuario no cuenta con una Firma Electr&oacute;nica activa");
			}
		}
		else {
			resultado.put("error", "1");
			resultado.put("codigoError", "CUS02E04");
			String mensajeError = consultaFirmaService.recuperarDescripcionParametro(FirmaConstantes.PARAMETRO_MENSAJES, "CUS02E04", "D", FirmaConstantes.PARAMETRO_MODULO_SIGA, true);
			resultado.put("mensajeError", mensajeError);//resultado.put("mensajeError", "El usuario se encuentra inactivo");
		}
		
		log.debug("resultado validarFirmaActiva: " + resultado);		
		return resultado;
	}

	@Override
	public Map<String, Object> validarSiEsJefeDependencia(String codEmpleado,
			String codDependencia) throws Exception {
		
		log.debug("validarSiEsJefeDependencia");
		Map<String, Object> resultado = new HashMap<String, Object>();
		List<MaestroPersonal> mpbList;
		
		MaestroPersonal param = new MaestroPersonal();
		param.setCodigoEmpleado(codEmpleado);
		param.setCodigoDependencia(codDependencia);
		mpbList = maestroPersonalDao.obtenerJefeDependencia(param);
		log.debug("mpbList: " + mpbList);
		
		if (mpbList.size() > 0 && mpbList.get(0).getCodigoEmpleado().equals(mpbList.get(0).getCodigoEmpleadoJefe())) {
			resultado.put("error", "0");
			resultado.put("esJefe", "1");
		}
		else {
			resultado.put("error", "1");
			resultado.put("codigoError", "CUS02EXX"); //validacion redundante
			resultado.put("mensajeError", "El usuario no es responsable de la uuoo por la cual se quiere firmar un documento.");			
			resultado.put("esJefe", "0");

		}

		log.debug("resultado validarSiEsJefeDependencia: " + resultado);
		return resultado;
	}
	

	
	public void setArchbinDao(T5282DAO archbinDao) {
		this.archbinDao = archbinDao;
	}

	public T5282DAO getArchbinDao() {
		return archbinDao;
	}

	public T7073FirmaElectronDAO getFirmaelectronDao() {
		return firmaelectronDao;
	}

	public void setFirmaelectronDao(T7073FirmaElectronDAO firmaelectronDao) {
		this.firmaelectronDao = firmaelectronDao;
	}

	public T7074DocumentoFirmDAO getDocumentofirmDao() {
		return documentofirmDao;
	}

	public void setDocumentofirmDao(T7074DocumentoFirmDAO documentofirmDao) {
		this.documentofirmDao = documentofirmDao;
	}

	public T7075TerminoCondDAO getTerminocondDao() {
		return terminocondDao;
	}

	public void setTerminocondDao(T7075TerminoCondDAO terminocondDao) {
		this.terminocondDao = terminocondDao;
	}

	public T7076DelegacFirmaDAO getDelegacfirmaDao() {
		return delegacfirmaDao;
	}

	public void setDelegacfirmaDao(T7076DelegacFirmaDAO delegacfirmaDao) {
		this.delegacfirmaDao = delegacfirmaDao;
	}

	public void setMaestroPersonalDao(MaestroPersonalDao maestroPersonalDao) {
		this.maestroPersonalDao = maestroPersonalDao;
	}

	public MaestroPersonalDao getMaestroPersonalDao() {
		return maestroPersonalDao;
	}

	public void setDependenciaDao(DependenciaDao dependenciaDao) {
		this.dependenciaDao = dependenciaDao;
	}

	public DependenciaDao getDependenciaDao() {
		return dependenciaDao;
	}
	
	public GrabarDocumentoFirmaService getGrabarDocumentoFirmaService() {
		return grabarDocumentoFirmaService;
	}

	public void setGrabarDocumentoFirmaService(
			GrabarDocumentoFirmaService grabarDocumentoFirmaService) {
		this.grabarDocumentoFirmaService = grabarDocumentoFirmaService;
	}

	public String getUrlFirma() {
		return urlFirma;
	}

	public void setUrlFirma(String urlFirma) {
		this.urlFirma = urlFirma;
	}

	public void setValidadorLdapService(ValidadorLdapService validadorLdapService) {
		this.validadorLdapService = validadorLdapService;
	}

	public ValidadorLdapService getValidadorLdapService() {
		return validadorLdapService;
	}

	public void setConsultaFirmaService(ConsultaFirmaService consultaFirmaService) {
		this.consultaFirmaService = consultaFirmaService;
	}

	public ConsultaFirmaService getConsultaFirmaService() {
		return consultaFirmaService;
	}
}
