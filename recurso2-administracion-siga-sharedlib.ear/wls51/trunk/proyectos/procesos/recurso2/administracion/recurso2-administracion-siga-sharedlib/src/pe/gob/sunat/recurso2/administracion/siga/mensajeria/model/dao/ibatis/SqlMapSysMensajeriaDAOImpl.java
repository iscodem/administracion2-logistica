package pe.gob.sunat.recurso2.administracion.siga.mensajeria.model.dao.ibatis;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientTemplate;


import pe.gob.sunat.framework.spring.util.dao.SqlMapDAOBase;
import pe.gob.sunat.recurso2.administracion.siga.mensajeria.model.bean.SysMensajeriaBean;
import pe.gob.sunat.recurso2.administracion.siga.mensajeria.model.bean.SysMensajeriaDestinosBean;
import pe.gob.sunat.recurso2.administracion.siga.mensajeria.model.dao.SysMensajeriaDAO;

/**
 * Implementacion de la interface SysMensajeriaDAO.
 * registro de correos(sys_mensajeria)
 * @author emarchena.
 */
public class SqlMapSysMensajeriaDAOImpl extends SqlMapDAOBase implements SysMensajeriaDAO{

	

	@Override
	public Map<String, Object> creaMensaje(SysMensajeriaBean mensajeria)
			throws DataAccessException {
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("codigoMensaje", mensajeria.getCodigoMensaje());
		params.put("remitenteNombre", mensajeria.getRemitenteNombre());
		params.put("remitenteFirma", mensajeria.getRemitenteFirma());
		params.put("remitenteEmail", mensajeria.getRemitenteEmail());
		params.put("asunto", mensajeria.getAsunto());
		params.put("mensaje", mensajeria.getMensaje());
	
		
		new SqlMapClientTemplate( getSqlMapClientTemplate().getSqlMapClient()).queryForObject("sysMensajeria.sys_mail_crear_mensaje", params);
		return params;
	}

	@Override
	public void sendMensaje(Long numeroMensaje) throws DataAccessException {
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("nroMensaje", numeroMensaje);
		
	
		
		new SqlMapClientTemplate( getSqlMapClientTemplate().getSqlMapClient()).queryForObject("sysMensajeria.sys_mail_send_mensaje", params);
	}

	@Override
	public void addDestino(SysMensajeriaDestinosBean destino)
			throws DataAccessException {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("nroMensaje", destino.getNroMensaje());
		params.put("destinoNombre", destino.getDestinoNombre());
		params.put("destinoEmail", destino.getDestinoEmail());
		params.put("tipoDestino", destino.getTipoDestino());
		
		new SqlMapClientTemplate( getSqlMapClientTemplate().getSqlMapClient()).queryForObject("sysMensajeria.sys_mail_add_destino", params);
	}
	

}
