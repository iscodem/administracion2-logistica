package pe.gob.sunat.administracion2.siga.rqnp.service;

import java.util.Collection;
import java.util.Map;

import pe.gob.sunat.administracion2.siga.registro.model.domain.RegistroArchivosFisicoBean;
import pe.gob.sunat.administracion2.siga.rqnp.model.domain.RequerimientoNoProgBienesBean;
import pe.gob.sunat.administracion2.siga.rqnp.model.domain.RequerimientoNoProgramadoBean;
import pe.gob.sunat.framework.spring.util.exception.ServiceException;

public interface RequerimientoAucService {

	public abstract Collection listarRqnpAuc(Map<String, Object> params);
	public abstract RegistroArchivosFisicoBean recuperarArchivoAnexo(Map<String, Object> params);
	public abstract Map<String, Object> recuperarRqnpCab(Map<String, Object> params);
	public abstract Map<String, Object> recuperarDependenciaXuuoo(String num_uuoo);
	public abstract String registrarCabRqnpAuc(Map<String, Object> params);
	public abstract void registrarCambioBien(RequerimientoNoProgBienesBean bien);
	public abstract String registrarRechazarRqnp(Map<String, Object> params);
	public abstract RequerimientoNoProgramadoBean recuperarRqnp(String cod_req);
	public abstract String registrarContratacionRqnp(Map<String, Object> params) throws ServiceException;
	public abstract String validaUserAUC(Map<String, Object> params) throws ServiceException;
	public abstract Collection listarAnnos() throws ServiceException;
	public abstract Collection listarAnnosAll() throws ServiceException;
	public abstract Collection listarMeses();
	
	  
	//INICIO: DPORRASC
	public abstract Collection listarAucMetas(Map<String, Object> params);
	public abstract Map<String, Object> recuperarDependenciaXcod(String num_uuoo);
	public abstract String validaUserAuAUC(String cod_empl) throws ServiceException;
	public abstract String obtenerCadenaEncargosAUC(String cod_empl);
	public abstract String validaGrupoAUC(Map<String, Object> params) throws ServiceException;	
	public abstract Map<String, Object> obtenerUuoo(Map<String, Object> params) throws ServiceException;
	
	public Collection obtenerBeneficiariasForExcel(Map<String, Object> parmSearch) ;
	
}
