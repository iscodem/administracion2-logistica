package pe.gob.sunat.recurso2.administracion.siga.mensajeria.service;


import pe.gob.sunat.framework.spring.util.exception.ServiceException;
import pe.gob.sunat.recurso2.administracion.siga.mensajeria.model.bean.SysMensajeriaBean;
import pe.gob.sunat.recurso2.administracion.siga.mensajeria.model.bean.SysMensajeriaDestinosBean;

/**
 * Interface RegistroMensajeriaService.
 * Servicio de registro de correos electronicos del Siga para su envio por el CRON
 * @author emarchena.
 */
public interface RegistroMensajeriaService {

	public Long crearMensaje(SysMensajeriaBean mensaje) throws ServiceException;

	public void sendMensaje(Long numeroMensaje) throws ServiceException;

	public void addDestino(SysMensajeriaDestinosBean destino) throws ServiceException;

}
