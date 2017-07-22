package pe.gob.sunat.recurso2.administracion.siga.registro.service;

import java.util.Collection;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.DependenciaBean;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.dao.DependenciaDAO;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.dao.PaFirmaElectronicaDAO;
import pe.gob.sunat.recurso2.administracion.siga.util.FormatoConstantes;

/**
 * Implementacion de la interface RegistroDependenciasService.
 * Servicio consulta de Dependencias
 * @author emarchena.
 */
public class RegistroDependenciasServiceImpl implements RegistroDependenciasService {

	private static final Log log = LogFactory.getLog(RegistroDependenciasServiceImpl.class);
	DependenciaDAO				dependenciaDao;
	PaFirmaElectronicaDAO		paFirmaElectronicaDAO;
	
	
	
	public PaFirmaElectronicaDAO getPaFirmaElectronicaDAO() {
		return paFirmaElectronicaDAO;
	}


	public void setPaFirmaElectronicaDAO(PaFirmaElectronicaDAO paFirmaElectronicaDAO) {
		this.paFirmaElectronicaDAO = paFirmaElectronicaDAO;
	}


	@Override
	public Collection<DependenciaBean> searchDependencias(
			Map<String, Object> parmSearch) {
		log.debug("Inicio searchDependencias");
		return dependenciaDao.searchDependencias(parmSearch);
	}
	
	
	@Override
	public Collection<DependenciaBean> searchDependenciasMovilidad(
			Map<String, Object> parmSearch) {
		log.debug("Inicio searchDependenciasMovilidad");
		return dependenciaDao.searchDependenciasMovilidad(parmSearch);
	}
	
	@Override
	public DependenciaBean getDependencias(String uuuoo){
		log.debug("Inicio getDependencias");
		return dependenciaDao.obtenerDependenciaXuuoo(uuuoo);
	}
	public DependenciaDAO getDependenciaDao() {
		return dependenciaDao;
	}
	@Override
	public DependenciaBean getDependencia(String codDependencia){
		log.debug("Inicio getDependencias");
		return dependenciaDao.obtenerDependencia(codDependencia);
	}

	public void setDependenciaDao(DependenciaDAO dependenciaDao) {
		this.dependenciaDao = dependenciaDao;
	}

	public Collection<DependenciaBean> searchDependenciasViatico(Map<String, Object> parmSearch) {
		/*Viaticos*/
		return dependenciaDao.searchDependenciasViatico(parmSearch);
	}	
	
	public DependenciaBean obtenerDependenciaXcod(String codigoDependencia){
		return dependenciaDao.obtenerDependenciaXcod(codigoDependencia);
	}
	@Override
	public Collection<DependenciaBean> buscarIntendenciaByUnidadOrganizacional(String num_uuoo) {
		/*Viaticos*/
		return dependenciaDao.buscarIntendenciaByUnidadOrganizacional(num_uuoo);
	}	 
	
	@Override
	public Collection<DependenciaBean> obtenerAllViaticoIndividual(	Map<String, Object> parmSearch) {
		log.debug("Inicio obtenerAllViaticoIndividual");
		return dependenciaDao.obtenerAllViaticoIndividual(parmSearch);
	}
		@Override
	public String obtenerUUOOAprobador(String codigoDependencia,
			String codigoColaborador, String codigoProceso) throws Exception {
		log.debug("Inicia obtenerUUOOAprobador ");
		Map<String, Object > parm = new HashMap<String, Object>();
		String codUUOOAprueba=FormatoConstantes.CADENA_VACIA ;
		String codMensaje=FormatoConstantes.CADENA_VACIA ;
		parm.put("codDepeSoli", codigoDependencia );
		parm.put("codEmplSoli", codigoColaborador);
		parm.put("codProceso", codigoProceso);
		log.debug("codigoDependencia"+ codigoDependencia);
		log.debug("codigoColaborador"+ codigoColaborador);
		log.debug("codigoProceso"+ codigoProceso);
		
		parm=paFirmaElectronicaDAO.obtenerUUOOApruba(parm);
		if (parm != null){
			
			codMensaje=(String)parm.get("codResultado");
			if(FormatoConstantes.CODIGO_00.equals( codMensaje)){
				codUUOOAprueba=(String)parm.get("codUUOO");
			}else{
				codUUOOAprueba=FormatoConstantes.MENOS_UNO;
			}
		}
		log.debug("codUUOOAprueba"+ codUUOOAprueba);
		log.debug("codMensaje"+ codMensaje);
		log.debug("Fin obtenerUUOOAprobador ");
		return codUUOOAprueba;
	}

	@Override
	public String obtenerUUOOAutorizador(String codigoDependencia,
			String codigoColaborador, String codigoProceso) throws Exception {
		log.debug("Inicia obtenerUUOOAutorizador ");
		Map<String, Object > parm = new HashMap<String, Object>();
		String codUUOOAprueba=FormatoConstantes.CADENA_VACIA ;
		String codMensaje=FormatoConstantes.CADENA_VACIA ;
		parm.put("codDepeSoli", codigoDependencia );
		parm.put("codEmplSoli", codigoColaborador);
		parm.put("codProceso", codigoProceso);
		log.debug("codigoDependencia"+ codigoDependencia);
		log.debug("codigoColaborador"+ codigoColaborador);
		log.debug("codigoProceso"+ codigoProceso);
		
		parm=paFirmaElectronicaDAO.obtenerUUOOAutoriza(parm);
		if (parm != null){
			
			codMensaje=(String)parm.get("codResultado");
			if(FormatoConstantes.CODIGO_00.equals( codMensaje)){
				codUUOOAprueba=(String)parm.get("codUUOO");
			}else{
				codUUOOAprueba=FormatoConstantes.MENOS_UNO;
			}
		}
		log.debug("codUUOOAprueba"+ codUUOOAprueba);
		log.debug("codMensaje"+ codMensaje);
		log.debug("Fin obtenerUUOOAutorizador ");
		return codUUOOAprueba;
	}

	@Override
	public String revaluaUUOOAprueba(String codigoAprobador) throws Exception {
		log.debug("Inicia revaluaUUOOAprueba ");
		Map<String, Object > parm = new HashMap<String, Object>();
		String codMensaje=FormatoConstantes.CADENA_VACIA ;
		String codResultado=FormatoConstantes.CADENA_VACIA ;
		parm.put("codEmplSoli", codigoAprobador);
		log.debug("codigoAprobador"+ codigoAprobador);
		
		parm=paFirmaElectronicaDAO.revaluaAprueba(parm);
		if (parm != null){
			codResultado=(String)parm.get("codResultado");
			if(!FormatoConstantes.CODIGO_00.equals( codResultado)){
				codMensaje=(String)parm.get("codMensaje");
			}
		}
		log.debug("codResultado"+ codResultado);
		log.debug("codMensaje"+ codMensaje);
		log.debug("Fin revaluaUUOOAprueba ");
		return codResultado;
	}

	@Override
	public String revaluaUUOOAutoriza(String codigoAutorizador)
			throws Exception {
		log.debug("Inicia revaluaUUOOAutoriza ");
		Map<String, Object > parm = new HashMap<String, Object>();
		String codMensaje=FormatoConstantes.CADENA_VACIA ;
		String codResultado=FormatoConstantes.CADENA_VACIA ;
		parm.put("codEmplSoli", codigoAutorizador);
		log.debug("codigoAutorizador"+ codigoAutorizador);
		
		parm=paFirmaElectronicaDAO.revaluaAutoriza(parm);
		if (parm != null){
			codResultado=(String)parm.get("codResultado");
			if(!FormatoConstantes.CODIGO_00.equals( codResultado)){
				codMensaje=(String)parm.get("codMensaje");
			}
		}
		log.debug("codResultado"+ codResultado);
		log.debug("codMensaje"+ codMensaje);
		log.debug("Fin revaluaUUOOAutoriza ");
		return codResultado;
	}
	
}
