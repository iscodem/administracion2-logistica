package pe.gob.sunat.recurso2.administracion.siga.expediente.service;

import java.util.Collection;


import pe.gob.sunat.framework.spring.util.exception.ServiceException;
import pe.gob.sunat.recurso2.administracion.siga.expediente.model.bean.ExpedienteAccionBean;
import pe.gob.sunat.recurso2.administracion.siga.expediente.model.bean.ExpedienteInternoAccionBean;
import pe.gob.sunat.recurso2.administracion.siga.expediente.model.bean.ExpedienteProcesoBean;

/**
 * Interface RegistroExpedienteService.
 * Servicio de Registro y consultas de expedientes(Seguimiento de procesos) del Siga
 * @author emarchena.
 */
public interface RegistroExpedienteService {
	
	
	public String crearExpediente( ExpedienteProcesoBean proceso)throws ServiceException;
	public void crearAccion(ExpedienteAccionBean accion)throws ServiceException;
	public Collection<ExpedienteInternoAccionBean> listaSecuenciaExpediente(String num_expediente) throws Exception; 
}
