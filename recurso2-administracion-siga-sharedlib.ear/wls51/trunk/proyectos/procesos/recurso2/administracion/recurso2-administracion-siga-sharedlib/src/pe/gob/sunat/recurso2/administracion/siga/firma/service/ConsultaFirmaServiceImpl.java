package pe.gob.sunat.recurso2.administracion.siga.firma.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pe.gob.sunat.recurso2.administracion.siga.firma.model.bean.Dependencia;
import pe.gob.sunat.recurso2.administracion.siga.firma.model.bean.MaestroPersonal;
import pe.gob.sunat.recurso2.administracion.siga.firma.model.bean.T01Parametro;
import pe.gob.sunat.recurso2.administracion.siga.firma.model.bean.T5282Archbin;
import pe.gob.sunat.recurso2.administracion.siga.firma.model.bean.T7073FirmaElectron;
import pe.gob.sunat.recurso2.administracion.siga.firma.model.bean.T7074DocumentoFirm;
import pe.gob.sunat.recurso2.administracion.siga.firma.model.bean.T7075TerminoCond;
import pe.gob.sunat.recurso2.administracion.siga.firma.model.bean.T7076DelegacFirma;
import pe.gob.sunat.recurso2.administracion.siga.firma.model.dao.DependenciaDao;
import pe.gob.sunat.recurso2.administracion.siga.firma.model.dao.MaestroPersonalDao;
import pe.gob.sunat.recurso2.administracion.siga.firma.model.dao.T01ParametroDao;
import pe.gob.sunat.recurso2.administracion.siga.firma.model.dao.T5282DAO;
import pe.gob.sunat.recurso2.administracion.siga.firma.model.dao.T7073FirmaElectronDAO;
import pe.gob.sunat.recurso2.administracion.siga.firma.model.dao.T7074DocumentoFirmDAO;
import pe.gob.sunat.recurso2.administracion.siga.firma.model.dao.T7075TerminoCondDAO;
import pe.gob.sunat.recurso2.administracion.siga.firma.model.dao.T7076DelegacFirmaDAO;
import pe.gob.sunat.recurso2.administracion.siga.firma.util.FirmaConstantes;

public class ConsultaFirmaServiceImpl implements ConsultaFirmaService {

	private static final Log log = LogFactory.getLog(ConsultaFirmaServiceImpl.class);
	
	private MaestroPersonalDao maestroPersonalDao;
	private T7073FirmaElectronDAO firmaelectronDao;
	private T7074DocumentoFirmDAO documentofirmDao;
	private T7075TerminoCondDAO terminocondDao;
	private T7076DelegacFirmaDAO delegacfirmaDao;
	private T5282DAO archbinDao;
	private DependenciaDao dependenciaDao;
	private T01ParametroDao t01parametroDao;
	
	@Override
	public Long recuperarIdDocumentoFirmadoVigente(String codTipdoc,
			String numDocumento) throws Exception {
		
		log.info("recuperarIdDocumentoFirmado");
		log.info("parametros: " + codTipdoc + " " + numDocumento);
		Long idDoc = -1l;
 				
		T7074DocumentoFirm param1 = new T7074DocumentoFirm();
	  	param1.setCodTipdoc(codTipdoc);
	  	param1.setNumDocumento(numDocumento);
	  	param1.setIndEstdoc(FirmaConstantes.INDICADOR_TRUE);
		List<T7074DocumentoFirm> listaDocumentosFirmados = documentofirmDao.listByParameter(param1);
		log.info("lista de documentos firmados: " + listaDocumentosFirmados);
		
		if (listaDocumentosFirmados.size() > 0) {
			T7074DocumentoFirm documentoFirmado = listaDocumentosFirmados.get(0);
			idDoc = new Long(documentoFirmado.getCodIdarchivo().longValue());			
		}
		
		log.debug("resultado recuperarIdDocumentosFirmado: " + idDoc);
		return idDoc;
	}
	
	@Override
	public Long recuperarIdDocumentoFirmado(BigDecimal codDocaut) 
		throws Exception {
		
		log.info("recuperarIdDocumentoFirmado");
		log.info("parametros: " + codDocaut);
		Long idDoc = -1l;
 				
		T7074DocumentoFirm param1 = new T7074DocumentoFirm();
	  	param1.setCodDocaut(codDocaut);
		List<T7074DocumentoFirm> listaDocumentosFirmados = documentofirmDao.listByParameter(param1);
		log.info("lista de documentos firmados: " + listaDocumentosFirmados);
		
		if (listaDocumentosFirmados.size() > 0) {
			T7074DocumentoFirm documentoFirmado = listaDocumentosFirmados.get(0);
			idDoc = new Long(documentoFirmado.getCodIdarchivo().longValue());			
		}
		
		log.debug("resultado recuperarIdDocumentosFirmado: " + idDoc);
		return idDoc;
	}
	
	
	@Override
	public T7074DocumentoFirm recuperarDatosDocumentoFirmadoVigente(String codTipdoc,
			String numDocumento) throws Exception {
		
		T7074DocumentoFirm documento = null;
		log.info("recuperarDatosDocumentoFirmado");
		log.info("parametros: " + codTipdoc + " " + numDocumento);
 				
		T7074DocumentoFirm param1 = new T7074DocumentoFirm();
	  	param1.setCodTipdoc(codTipdoc);
	  	param1.setNumDocumento(numDocumento);
	  	param1.setIndEstdoc(FirmaConstantes.INDICADOR_TRUE);
		List<T7074DocumentoFirm> listaDocumentosFirmados = documentofirmDao.listByParameter(param1);
		log.info("lista de documentos firmados: " + listaDocumentosFirmados);
		
		if (listaDocumentosFirmados.size() > 0) {
			documento = listaDocumentosFirmados.get(0);
		}
		
		log.debug("resultado recuperarDatosDocumentosFirmado: " + documento);
		return documento;
	}
	
	@Override
	public T7074DocumentoFirm recuperarDatosDocumentoFirmado(BigDecimal codDocaut) throws Exception {
		
		T7074DocumentoFirm documento = null;
		log.info("recuperarDatosDocumentoFirmado");
		log.info("parametros: " + codDocaut);
 				
		T7074DocumentoFirm param1 = new T7074DocumentoFirm();
	  	param1.setCodDocaut(codDocaut);
		List<T7074DocumentoFirm> listaDocumentosFirmados = documentofirmDao.listByParameter(param1);
		log.info("lista de documentos firmados: " + listaDocumentosFirmados);
		
		if (listaDocumentosFirmados.size() > 0) {
			documento = listaDocumentosFirmados.get(0);
		}
		
		log.debug("resultado recuperarDatosDocumentosFirmado: " + documento);
		return documento;
	}
	
	
	@Override
	public T5282Archbin recuperarDocumentoPdfFirmado(Long numPDF) throws Exception {
		
		log.info("recuperando archivo: " + numPDF);
		T5282Archbin record = archbinDao.findByPk(numPDF);
		
		log.info("resultado: " + record);
		return record;
	}
	
	@Override
	public T7073FirmaElectron recuperarFirmaActiva(String codEmpleado)
			throws Exception {

		log.debug("recuperarFirmaActiva");
		T7073FirmaElectron resultado = null;
		
		T7073FirmaElectron param = new T7073FirmaElectron();
		param.setCodFirmante(codEmpleado);
		param.setIndEstfirma(FirmaConstantes.INDICADOR_TRUE); //1 = firma activa
		
		List<T7073FirmaElectron> firmaList = firmaelectronDao.listByParameter(param);
		if (firmaList.size() > 0) {
			resultado = firmaList.get(0);
		}
		
		log.debug("resultado recuperarFirmaActiva: " + resultado);
		return resultado;
	}
	
	@Override
	public List<T7073FirmaElectron> recuperarFirmas(String codEmpleado)
			throws Exception {

		log.info("recuperarFirmas");
		log.debug("codigo de empleado: " + codEmpleado);
		List<T7073FirmaElectron> firmaList;
		
		T7073FirmaElectron param = new T7073FirmaElectron();
		param.setCodFirmante(codEmpleado);
		firmaList = firmaelectronDao.listByParameter(param);
		
		log.debug("resultado recuperarFirmas: " + firmaList.size() + " " + firmaList);
		return firmaList;
	}
	
	@Override
	public MaestroPersonal recuperarDatosPersona(MaestroPersonal param)
			throws Exception {

		log.debug("recuperarDatosPersona");
		MaestroPersonal persona = null;
		
		List<MaestroPersonal> resultado = maestroPersonalDao.obtenerColaboradorConDependencia(param);
		if (resultado.size() > 0) {
			persona = resultado.get(0);
		}
		
		log.debug("resultado recuperarFirmas: " + persona);
		return persona;
	}

	@Override
	public List<T7075TerminoCond> recuperarTerminosYCondiciones(
			String codTipo) throws Exception {
	
		log.debug("recuperarTerminosYCondiciones");
		List<T7075TerminoCond> terminoCondList;
		
		T7075TerminoCond param = new T7075TerminoCond();
		param.setCodTipo(FirmaConstantes.TERMINOS_AFILIACION);
		
		terminoCondList = terminocondDao.listByParameter(param);
		log.debug("resultado recuperarTerminosYCondiciones: " + terminoCondList);
		return terminoCondList;
	}

	@Override
	public T7075TerminoCond recuperarTerminosYCondicionesVigente(
			String codTipo) throws Exception {

		log.debug("recuperarTerminosYCondiciones");
		List<T7075TerminoCond> terminoCondList;
		T7075TerminoCond terminoCond = null;
		
		T7075TerminoCond param = new T7075TerminoCond();
		param.setCodTipo(FirmaConstantes.TERMINOS_AFILIACION);
		param.setIndEsttermino(FirmaConstantes.INDICADOR_TRUE);
		
		terminoCondList = terminocondDao.listByParameter(param);
		log.debug("resultado recuperarTerminosYCondiciones: " + terminoCondList);
		if (terminoCondList.size() > 0) {
			terminoCond = terminoCondList.get(0);
		}
		return terminoCond;
	}
	
	@Override
	public List<T7076DelegacFirma> recuperarListaEncargaturaDependencia(
			String codDependencia) throws Exception {

		log.debug("recuperarListaEncargaturaDependencia");
		List<T7076DelegacFirma> delegacFirmaList;
		
		T7076DelegacFirma param = new T7076DelegacFirma();
		param.setCodUuoo(codDependencia);
		
		delegacFirmaList = delegacfirmaDao.listByParameter(param);
		log.debug("resultado recuperarListaEncargaturaDependencia: " + delegacFirmaList);
		return delegacFirmaList;
	}
	
	@Override
	public List<T7074DocumentoFirm> recuperarDocumentosFirmados(
			T7074DocumentoFirm param) throws Exception {

		log.debug("recuperarDocumentosFirmados");
		List<T7074DocumentoFirm> listaDocumentosFirmados = documentofirmDao.listByParameter(param);
		
		log.debug("resultado recuperarDocumentosFirmados: " + listaDocumentosFirmados);
		return listaDocumentosFirmados;
	}
	
	@Override
	public List<MaestroPersonal> recuperarFirmantesDocumentoVigente(String codTipdoc,
			String numDocumento) throws Exception {
		
		List<MaestroPersonal> firmantes = new ArrayList<MaestroPersonal>();
		MaestroPersonal firmante;
		
		//obtener documentos
	  	T7074DocumentoFirm param1;
	  	Dependencia dep, param2;
	  	
	  	param1 = new T7074DocumentoFirm();
	  	param1.setCodTipdoc(codTipdoc);
	  	param1.setNumDocumento(numDocumento);
	  	param1.setIndEstdoc(FirmaConstantes.INDICADOR_TRUE);
		List<T7074DocumentoFirm> listaDocumentosFirmados = documentofirmDao.listByParameterConDatosFirmantes(param1);
		
		if (listaDocumentosFirmados.size() > 0) {
			T7074DocumentoFirm documentoActivo = listaDocumentosFirmados.get(0);
			
			firmante = new MaestroPersonal();
			firmante.setCodigoEmpleado(documentoActivo.getCodUsuautor());
			firmante.setNumero_registro(documentoActivo.getNumeroRegistroAlterno());
			firmante.setNombre_completo(documentoActivo.getNombCortPer());
			firmante.setCodigoDependencia(documentoActivo.getCodUuoo());
			firmante.setDni(documentoActivo.getLibrElecPer());
			firmante.setCargo(documentoActivo.getCodCargtca());
			firmante.setDescCargo(documentoActivo.getDescCargTca());
			firmante.setDescCortaCargo(documentoActivo.getDescripcionCortaCargo());
			firmantes.add(firmante);
			
		  	param1 = new T7074DocumentoFirm();
		  	param1.setCodTipdoc(codTipdoc);
		  	param1.setNumDocumento(numDocumento);
		  	param1.setIndEstdoc(FirmaConstantes.INDICADOR_FALSE);
			List<T7074DocumentoFirm> listaDocumentosFirmadosAnteriores = documentofirmDao.listByParameterConDatosFirmantes(param1);
			
			BigDecimal codDocseqAnterior = documentoActivo.getCodDocseq();
			log.info("primer codDocseqAnterior: " + codDocseqAnterior);
			while (codDocseqAnterior.longValue() > 0) {
				for (T7074DocumentoFirm documentoAnterior : listaDocumentosFirmadosAnteriores) {
					
					if (documentoAnterior.getCodDocaut().longValue() == codDocseqAnterior.longValue()) {
						firmante = new MaestroPersonal();
						firmante.setCodigoEmpleado(documentoAnterior.getCodUsuautor());
						firmante.setNumero_registro(documentoAnterior.getNumeroRegistroAlterno());
						firmante.setNombre_completo(documentoAnterior.getNombCortPer());
						firmante.setCodigoDependencia(documentoAnterior.getCodUuoo());
						firmante.setDni(documentoAnterior.getLibrElecPer());
						firmante.setCargo(documentoAnterior.getCodCargtca());
						firmante.setDescCargo(documentoAnterior.getDescCargTca());
						firmante.setDescCortaCargo(documentoAnterior.getDescripcionCortaCargo());
						firmantes.add(firmante);
						codDocseqAnterior = documentoAnterior.getCodDocseq();
						break;
					}
				}
				log.info("codDocseqAnterior: " + codDocseqAnterior);
			}
			log.info("cantidad firmantes: " + firmantes.size());
			
			for (MaestroPersonal fmte : firmantes) {
				param2 = new Dependencia();
				param2.setCod_dep(documentoActivo.getCodUuoo());
				try {
					dep = dependenciaDao.obtenerDatosDependencia(param2).get(0);
					
					fmte.setDependencia(dep.getNom_largo());
					fmte.setUuoo(dep.getUuoo());
					fmte.setAbrvDependencia(dep.getNom_corto());					
				}
				catch (Exception e) {
					fmte.setDependencia("");
					fmte.setUuoo("");
					fmte.setAbrvDependencia("");
				}
			}
		}
		
		return firmantes;
	}
	
	@Override
	public T5282Archbin recuperarDocumentoPdfFirmadoVigente(String codTipdoc,
			String numDocumento) throws Exception {
		
		T7074DocumentoFirm documento = recuperarDatosDocumentoFirmadoVigente(codTipdoc, numDocumento);
		Long iddoc = documento.getCodIdarchivo().longValue();
		String nombreArchivo = documento.getNomArchivo();
		
		log.info("recuperando archivo: " + iddoc);
		T5282Archbin record = recuperarDocumentoPdfFirmado(iddoc);
		record.setDesNombreAlternativo(nombreArchivo);
		
		log.info("resultado: " + record);
		return record;
	}
	
	@Override
	public T5282Archbin recuperarDocumentoPdfFirmado(BigDecimal codDocaut) 
		throws Exception {
		
		T7074DocumentoFirm documento = recuperarDatosDocumentoFirmado(codDocaut);
		Long iddoc = documento.getCodIdarchivo().longValue();
		String nombreArchivo = documento.getNomArchivo();
		
		log.info("recuperando archivo: " + iddoc);
		T5282Archbin record = recuperarDocumentoPdfFirmado(iddoc);
		record.setDesNombreAlternativo(nombreArchivo);
		
		log.info("resultado: " + record);
		return record;
	}
	
	@Override
	public String recuperarDescripcionParametro(String codPar, String codArg, String codTipo, String codMod, boolean larga) throws Exception {
		
		String descripcion = "";
		
		T01Parametro param = new T01Parametro();
		param.setCod_parametro(codPar);
		param.setCod_argumento(codArg);
		param.setCod_tipo(codTipo);
		param.setCod_modulo(codMod);
		List<T01Parametro> paramList = t01parametroDao.recuperarParametro(param);
		if (paramList.size() > 0) {
			T01Parametro p = paramList.get(0);
			if (larga) {
				descripcion = p.getNom_largo();
			}
			else {
				descripcion = p.getNom_corto();
			}
		}
		
		return descripcion;
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

	public T5282DAO getArchbinDao() {
		return archbinDao;
	}

	public void setArchbinDao(T5282DAO archbinDao) {
		this.archbinDao = archbinDao;
	}

	public void setDependenciaDao(DependenciaDao dependenciaDao) {
		this.dependenciaDao = dependenciaDao;
	}

	public DependenciaDao getDependenciaDao() {
		return dependenciaDao;
	}

	public void setT01parametroDao(T01ParametroDao t01parametroDao) {
		this.t01parametroDao = t01parametroDao;
	}

	public T01ParametroDao getT01parametroDao() {
		return t01parametroDao;
	}

}
