package pe.gob.sunat.recurso2.administracion.siga.firma.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pe.gob.sunat.recurso2.administracion.siga.firma.model.bean.MaestroPersonal;
import pe.gob.sunat.recurso2.administracion.siga.firma.model.bean.T7073FirmaElectron;
import pe.gob.sunat.recurso2.administracion.siga.firma.model.bean.T7074DocumentoFirm;
import pe.gob.sunat.recurso2.administracion.siga.firma.model.dao.MaestroPersonalDao;
import pe.gob.sunat.recurso2.administracion.siga.firma.model.dao.T7073FirmaElectronDAO;
import pe.gob.sunat.recurso2.administracion.siga.firma.model.dao.T7074DocumentoFirmDAO;
import pe.gob.sunat.recurso2.administracion.siga.firma.util.FirmaConstantes;

public class GrabarDocumentoFirmaServiceImpl implements GrabarDocumentoFirmaService {

	private static final Log log = LogFactory.getLog(GrabarDocumentoFirmaServiceImpl.class);

	private MaestroPersonalDao maestroPersonalDao;
	private T7073FirmaElectronDAO firmaelectronDao;
	private T7074DocumentoFirmDAO documentofirmDao;

	@Override
	public int grabarDocumentoFirmado(String tipoOperacion, Long codDocaut, String codEmpleado, String codTipdoc,
			String numDocumento, String codDependencia,
			String codDependenciaNormativa, String codToken, String nomModulo,
			String nomProceso, String nomArchivo, Long numid, Date fechaValidacionLdap,
			String codUsuregis, String dirIpusuregis, String codUsumodif,
			String dirIpusumodif) throws Exception {

		log.debug("grabarDocumentoFirmado");
		int resultado = 0;

		//recuperacion de la firma activa.

		T7073FirmaElectron param1 = new T7073FirmaElectron();
		param1.setCodFirmante(codEmpleado);
		param1.setIndEstfirma(FirmaConstantes.INDICADOR_TRUE); //1 = firma activa

		List<T7073FirmaElectron> firmaList = firmaelectronDao.listByParameter(param1);
		if (firmaList.size() == 0) {
			throw new Exception("El usuario no tiene firma electronica activa disponible");
		}
		T7073FirmaElectron firma = firmaList.get(0);
		String codFirma = firma.getCodFirma();

		//recuperar documento vigente.

	  	T7074DocumentoFirm param2 = new T7074DocumentoFirm();
	  	param2.setCodTipdoc(codTipdoc);
	  	param2.setNumDocumento(numDocumento);
	  	param2.setIndEstdoc(FirmaConstantes.INDICADOR_TRUE);
		List<T7074DocumentoFirm> listaDocumentosFirmados = documentofirmDao.listByParameter(param2);

		MaestroPersonal param3 = new MaestroPersonal();
		param3.setCodigoEmpleado(codEmpleado);
		List<MaestroPersonal> mpList = maestroPersonalDao.obtenerColaboradorConDependencia(param3);
		MaestroPersonal mp = new MaestroPersonal();
		if (mpList.size() > 0) {
			mp = mpList.get(0);
		}

		short secuencia = 1;
		short version = 1;
		BigDecimal codDocseq = new BigDecimal(0);

		if (listaDocumentosFirmados.size() > 0) {
			T7074DocumentoFirm documentoAnterior = listaDocumentosFirmados.get(0);
			
			/*Map<String, Object> paramEncripta = new HashMap<String, Object>();
			paramEncripta.put("codToken", documentoAnterior.getCodToken());
			paramEncripta.put("clave", FirmaConstantes.LLAVE_FIRMA);
			String desencripta = documentofirmDao.desencriptaCadena(paramEncripta);
			log.info("desencriptando: " + documentoAnterior.getCodToken() + " "  + desencripta); */

			try {
				secuencia = documentoAnterior.getNumSecuencia().shortValue();
				version = documentoAnterior.getNumVersion().shortValue();

				if (FirmaConstantes.OPERACION_SECUENCIA.equals(tipoOperacion)) {
					secuencia++;
					codDocseq = documentoAnterior.getCodDocaut();
				}
				else if (FirmaConstantes.OPERACION_VERSION.equals(tipoOperacion)) {
					version++;
					codDocseq = new BigDecimal(0);
				}
				else if (FirmaConstantes.OPERACION_NUEVO.equals(tipoOperacion)) {
					secuencia = 1;
					version = 1;
					codDocseq = new BigDecimal(0);
				}
				else if (FirmaConstantes.OPERACION_LEVANTA_OBSERVACION.equals(tipoOperacion)) {
					secuencia = 1;
					version++;
					codDocseq = new BigDecimal(0);
				}
				else {
					throw new Exception("Operacion invalida");
				}
			}
			catch (Exception e) {
				secuencia = 1;
				version = 1;
				codDocseq = new BigDecimal(0);
			}
		}

		//anulacion de todas las versiones anteriores del documento
	  	param2 = new T7074DocumentoFirm();
	  	param2.setCodTipdoc(codTipdoc);
	  	param2.setNumDocumento(numDocumento);
	  	param2.setIndEstdoc(FirmaConstantes.INDICADOR_FALSE);
	  	param2.setFecModif(new Timestamp(new Date().getTime()));
	  	param2.setCodUsumodif(codUsumodif);
	  	param2.setDirIpusumodif(dirIpusumodif);
	  	documentofirmDao.updateEstadoDocumento(param2);

		// grabacion de la informacion del documento
		T7074DocumentoFirm documento = new T7074DocumentoFirm();

		documento.setCodDocaut(new BigDecimal(codDocaut));
		documento.setCodFirma(codFirma);
		documento.setCodTipdoc(codTipdoc);
		documento.setNumDocumento(numDocumento);
		documento.setNumSecuencia(secuencia);
		documento.setCodDocseq(codDocseq);
		documento.setNumVersion(version);

		documento.setCodUuoo(codDependencia);
		documento.setCodUnnorma(codDependenciaNormativa);
		
		Map<String, Object> paramEncripta = new HashMap<String, Object>();
		paramEncripta.put("codToken", codToken);
		paramEncripta.put("clave", FirmaConstantes.LLAVE_FIRMA);
		documento.setCodToken(documentofirmDao.encriptaCadena(paramEncripta));
		
		//documento.setCodToken(codToken);
		documento.setNomModulo(nomModulo);
		documento.setNomProceso(nomProceso);
		documento.setFecFirma(new Timestamp(new Date().getTime()));
		documento.setCodUsuautor(codEmpleado);
		documento.setCodCargtca(mp.getCargo());
		
		// inicio cambio de registro alterno
		documento.setCodNumregalterno(mp.getNumero_registro());
		documento.setCodCodinivenvl(mp.getCodiNiveNvl());
		// fin cambio de registro alterno
		
		documento.setNomArchivo(nomArchivo);
		documento.setCodIdarchivo(new BigDecimal(numid));
		documento.setIndEstdoc(FirmaConstantes.INDICADOR_TRUE);
		documento.setFecRegis(new Timestamp(new Date().getTime()));
		documento.setCodUsuregis(codUsuregis);
		documento.setDirIpusuregis(dirIpusuregis);
		documento.setFecModif(new Timestamp(new Date().getTime()));
		documento.setCodUsumodif(codUsumodif);
		documento.setDirIpusumodif(dirIpusumodif);

		documentofirmDao.insertSelective(documento);

		log.debug("grabarDocumentoFirmado: " + resultado);
		return resultado;
	}

	@Override
	public Long obtenerSecuencialDocumento() throws Exception {

		log.debug("obtenerSecuencialDocumento");
		Long resultado = documentofirmDao.obtenerSecuencial();

		log.debug("obtenerSecuencialDocumento: " + resultado);
		return resultado;
	}

	public MaestroPersonalDao getMaestroPersonalDao() {
		return maestroPersonalDao;
	}

	public void setMaestroPersonalDao(MaestroPersonalDao maestroPersonalDao) {
		this.maestroPersonalDao = maestroPersonalDao;
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

	@Override
	public int anularDocumento(Long codDocaut) throws Exception {
		//anulacion de todas las versiones anteriores del documento
		T7074DocumentoFirm paramUpdate = new T7074DocumentoFirm();
		paramUpdate.setCodDocaut(new BigDecimal(codDocaut));
		paramUpdate.setIndEstdoc(FirmaConstantes.INDICADOR_FALSE);
		paramUpdate.setFecModif(new Timestamp(new Date().getTime()));
	  	int firmasActualizadas = documentofirmDao.updateByPrimaryKeySelective(paramUpdate);
		return firmasActualizadas;
	}
	
	/**
	 * Metodo creado para la firma SIGA
	 */
	@Override
	public int grabarDocumentoFirmadoSiga(String tipoOperacion, Long codDocaut, String codEmpleado, String codTipdoc,
			String numDocumento, String codDependencia,
			String codDependenciaNormativa, String codToken, String nomModulo,
			String nomProceso, String nomArchivo, Long numid, Date fechaValidacionLdap,
			String codUsuregis, String dirIpusuregis, String codUsumodif,
			String dirIpusumodif, byte[] byData ) throws Exception {

		log.debug("grabarDocumentoFirmado - Firma SIGA");
		int resultado = 0;

		//recuperacion de la firma activa.

		T7073FirmaElectron param1 = new T7073FirmaElectron();
		param1.setCodFirmante(codEmpleado);
		param1.setIndEstfirma(FirmaConstantes.INDICADOR_TRUE); //1 = firma activa

		List<T7073FirmaElectron> firmaList = firmaelectronDao.listByParameter(param1);
		if (firmaList.size() == 0) {
			throw new Exception("El usuario no tiene firma electronica activa disponible");
		}
		T7073FirmaElectron firma = firmaList.get(0);
		String codFirma = firma.getCodFirma();

		//recuperar documento vigente.

	  	T7074DocumentoFirm param2 = new T7074DocumentoFirm();
	  	param2.setCodTipdoc(codTipdoc);
	  	param2.setNumDocumento(numDocumento);
	  	param2.setIndEstdoc(FirmaConstantes.INDICADOR_TRUE);
		List<T7074DocumentoFirm> listaDocumentosFirmados = documentofirmDao.listByParameter(param2);

		MaestroPersonal param3 = new MaestroPersonal();
		param3.setCodigoEmpleado(codEmpleado);
		List<MaestroPersonal> mpList = maestroPersonalDao.obtenerColaboradorConDependencia(param3);
		MaestroPersonal mp = new MaestroPersonal();
		if (mpList.size() > 0) {
			mp = mpList.get(0);
		}

		short secuencia = 1;
		short version = 1;
		BigDecimal codDocseq = new BigDecimal(0);

		if (listaDocumentosFirmados.size() > 0) {
			T7074DocumentoFirm documentoAnterior = listaDocumentosFirmados.get(0);
			
			/*Map<String, Object> paramEncripta = new HashMap<String, Object>();
			paramEncripta.put("codToken", documentoAnterior.getCodToken());
			paramEncripta.put("clave", FirmaConstantes.LLAVE_FIRMA);
			String desencripta = documentofirmDao.desencriptaCadena(paramEncripta);
			log.info("desencriptando: " + documentoAnterior.getCodToken() + " "  + desencripta); */

			try {
				secuencia = documentoAnterior.getNumSecuencia().shortValue();
				version = documentoAnterior.getNumVersion().shortValue();

				if (FirmaConstantes.OPERACION_SECUENCIA.equals(tipoOperacion)) {
					secuencia++;
					codDocseq = documentoAnterior.getCodDocaut();
				}
				else if (FirmaConstantes.OPERACION_VERSION.equals(tipoOperacion)) {
					version++;
					codDocseq = new BigDecimal(0);
				}
				else if (FirmaConstantes.OPERACION_NUEVO.equals(tipoOperacion)) {
					secuencia = 1;
					version = 1;
					codDocseq = new BigDecimal(0);
				}
				else if (FirmaConstantes.OPERACION_LEVANTA_OBSERVACION.equals(tipoOperacion)) {
					secuencia = 1;
					version++;
					codDocseq = new BigDecimal(0);
				}
				else {
					throw new Exception("Operacion invalida");
				}
			}
			catch (Exception e) {
				secuencia = 1;
				version = 1;
				codDocseq = new BigDecimal(0);
			}
		}

		//anulacion de todas las versiones anteriores del documento
	  	param2 = new T7074DocumentoFirm();
	  	param2.setCodTipdoc(codTipdoc);
	  	param2.setNumDocumento(numDocumento);
	  	param2.setIndEstdoc(FirmaConstantes.INDICADOR_FALSE);
	  	param2.setFecModif(new Timestamp(new Date().getTime()));
	  	param2.setCodUsumodif(codUsumodif);
	  	param2.setDirIpusumodif(dirIpusumodif);
	  	documentofirmDao.updateEstadoDocumento(param2);

		// grabacion de la informacion del documento
		T7074DocumentoFirm documento = new T7074DocumentoFirm();

		documento.setCodDocaut(new BigDecimal(codDocaut));
		documento.setCodFirma(codFirma);
		documento.setCodTipdoc(codTipdoc);
		documento.setNumDocumento(numDocumento);
		documento.setNumSecuencia(secuencia);
		documento.setCodDocseq(codDocseq);
		documento.setNumVersion(version);

		documento.setCodUuoo(codDependencia);
		documento.setCodUnnorma(codDependenciaNormativa);
		
		Map<String, Object> paramEncripta = new HashMap<String, Object>();
		paramEncripta.put("codToken", codToken);
		paramEncripta.put("clave", FirmaConstantes.LLAVE_FIRMA);
		documento.setCodToken(documentofirmDao.encriptaCadena(paramEncripta));
		
		//documento.setCodToken(codToken);
		documento.setNomModulo(nomModulo);
		documento.setNomProceso(nomProceso);
		documento.setFecFirma(new Timestamp(new Date().getTime()));
		documento.setCodUsuautor(codEmpleado);
		documento.setCodCargtca(mp.getCargo());
		
		// inicio cambio de registro alterno
		documento.setCodNumregalterno(mp.getNumero_registro());
		documento.setCodCodinivenvl(mp.getCodiNiveNvl());
		// fin cambio de registro alterno
		
		documento.setNomArchivo(nomArchivo);
		documento.setCodIdarchivo(new BigDecimal(numid));
		documento.setIndEstdoc(FirmaConstantes.INDICADOR_TRUE);
		documento.setFecRegis(new Timestamp(new Date().getTime()));
		documento.setCodUsuregis(codUsuregis);
		documento.setDirIpusuregis(dirIpusuregis);
		documento.setFecModif(new Timestamp(new Date().getTime()));
		documento.setCodUsumodif(codUsumodif);
		documento.setDirIpusumodif(dirIpusumodif);
		documento.setArcFirmado(byData);

		documentofirmDao.insertSelective(documento);

		log.debug("grabarDocumentoFirmado - Firma SIGA: " + resultado);
		return resultado;
	}
}
