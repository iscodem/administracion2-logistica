package pe.gob.sunat.recurso2.administracion.siga.mensajeria.model.dao;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import pe.gob.sunat.recurso2.administracion.siga.mensajeria.model.bean.SysMensajeriaBean;
import pe.gob.sunat.recurso2.administracion.siga.mensajeria.model.bean.SysMensajeriaDestinosBean;


/**
 * Interface SysMensajeriaDAO.
 * registro de correos(sys_mensajeria)
 * @author emarchena.
 */
public interface SysMensajeriaDAO {
	
	public Map<String, Object>  creaMensaje(SysMensajeriaBean mensajeria)	throws DataAccessException;
	public void sendMensaje(Long numeroMensaje)	throws DataAccessException;
	public void addDestino(SysMensajeriaDestinosBean destino)	throws DataAccessException;
	
	

}
