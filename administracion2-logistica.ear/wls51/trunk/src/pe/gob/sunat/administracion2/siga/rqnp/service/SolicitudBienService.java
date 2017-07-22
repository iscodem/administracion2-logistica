package pe.gob.sunat.administracion2.siga.rqnp.service;

import java.util.Collection;
import java.util.Map;

import pe.gob.sunat.administracion2.siga.rqnp.model.domain.SolicitudBienBean;
import pe.gob.sunat.recurso2.administracion.siga.parametro.model.bean.T01ParametroBean;

public interface SolicitudBienService {
	public abstract Collection listarSolicitudXPersona(Map<String, Object> params);
	public abstract Collection listarUnidadMedidad();
	public abstract Map recuperarSolictudBien(String cod_sol);
	public abstract Map recuperarDependencia(String cod_dep) ;
	public abstract String registrarSolictudBien(Map<String, Object> params) ;
	public abstract T01ParametroBean obtenerParametro(Map<String, Object> params ) ;
	public abstract Collection obtenerParametroLista(Map<String, Object> params ) ;
	public abstract Collection obtenerEstadosLista(Map<String, Object> params ) ;
	public abstract void updateFile(String cod_sol, String num_reg, String user) ;
	public abstract void updateAprueba(SolicitudBienBean bean, String user) ;
	public abstract void updateRechaza(SolicitudBienBean bean, String user) ;
	public abstract String EnviarMailRqnp(Map<String, Object> params) ;
	public abstract SolicitudBienBean recuperarSolicitudBienBean(String cod_req);
	public abstract Collection listarConsultaCatalogo(Map<String, Object> params);
	public abstract String recuperarDependenciaPorUUOO(String num_uuoo) ;
	public abstract String activarFlujoAuc(String parametro) ;
	 
}
