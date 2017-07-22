package pe.gob.sunat.recurso2.administracion.siga.mensajeria.service;


import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import pe.gob.sunat.framework.spring.util.exception.ServiceException;
import pe.gob.sunat.recurso2.administracion.siga.mensajeria.model.bean.SysMensajeriaBean;
import pe.gob.sunat.recurso2.administracion.siga.mensajeria.model.bean.SysMensajeriaDestinosBean;
import pe.gob.sunat.recurso2.administracion.siga.mensajeria.model.dao.SysMensajeriaDAO;

/**
 * Implementacion de la interface RegistroMensajeriaService.
 * Servicio de registro de correos electronicos del Siga para su envio por el CRON
 * @author emarchena.
 */
public class RegistroMensajeriaServiceImpl implements RegistroMensajeriaService {

	private SysMensajeriaDAO sysMensajeriaDAO;

	public SysMensajeriaDAO getSysMensajeriaDAO() {
		return sysMensajeriaDAO;
	}

	public void setSysMensajeriaDAO(SysMensajeriaDAO sysMensajeriaDAO) {
		this.sysMensajeriaDAO = sysMensajeriaDAO;
	}

	private static final Log log = LogFactory.getLog(RegistroMensajeriaServiceImpl.class);

	@Override
	public Long crearMensaje(SysMensajeriaBean mensaje) throws ServiceException {
		Long ll_numero_mensaje = 0L;
		Map<String, Object> lista = new HashMap<String, Object>();
		if (log.isDebugEnabled()) log.debug("Inicio - RegistroMensajeriaServiceImpl.crearMensaje");
		try {
			lista = sysMensajeriaDAO.creaMensaje(mensaje);
			if (lista != null) {
				if (lista.size() > 0) {
					ll_numero_mensaje = (Long) lista.get("nroMensaje");
				}
			}

		} catch (Exception ex) {
			log.error("Error en RegistroMensajeriaServiceImpl.crearMensaje: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);
		} finally {
			if (log.isDebugEnabled()) log.debug("Fin - RegistroMensajeriaServiceImpl.crearMensaje");
		}
		return ll_numero_mensaje;
	}

	@Override
	public void sendMensaje(Long numeroMensaje) throws ServiceException {
		if (log.isDebugEnabled()) log.debug("Inicio - RegistroMensajeriaServiceImpl.sendMensaje");
		try {
			sysMensajeriaDAO.sendMensaje(numeroMensaje);

		} catch (Exception ex) {
			log.error("Error en RegistroMensajeriaServiceImpl.senMensaje: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);
		} finally {
			if (log.isDebugEnabled()) log.debug("Fin - RegistroMensajeriaServiceImpl.sendMensaje");
		}

	}

	@Override
	public void addDestino(SysMensajeriaDestinosBean destino) throws ServiceException {
		if (log.isDebugEnabled()) log.debug("Inicio - RegistroMensajeriaServiceImpl.addDestino");
		try {
			sysMensajeriaDAO.addDestino(destino);

		} catch (Exception ex) {
			log.error("Error en RegistroMensajeriaServiceImpl.addDetino: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);
		} finally {
			if (log.isDebugEnabled()) log.debug("Fin - RegistroMensajeriaServiceImpl.addDestino");
		}

	}

}
