package pe.gob.sunat.recurso2.administracion.siga.expediente.model.dao.ibatis;

import java.util.Collection;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientTemplate;



import pe.gob.sunat.framework.spring.util.dao.SqlMapDAOBase;
import pe.gob.sunat.recurso2.administracion.siga.expediente.model.dao.PaExpedienteDAO;
/**
 * Implementacion de la interface PaExpedienteDAO.
 * Mantenimiento de Registro de Expediente(PA_EXPEDIENTES)
 * @author emarchena.
 */
public class SqlMapPaExpedienteDAOImpl  extends SqlMapDAOBase implements PaExpedienteDAO{

	@Override
	public String crearExpediente(Map<String, Object> parm)
			throws DataAccessException {
		new SqlMapClientTemplate( getSqlMapClientTemplate().getSqlMapClient()).queryForObject("paExpedientes.sp_crear_exp", parm);
		return (String) parm.get("cod_exp");
	}

	@Override
	public void crearAccion(Map<String, Object> parm)
			throws DataAccessException {
		new SqlMapClientTemplate( getSqlMapClientTemplate().getSqlMapClient()).queryForObject("paExpedientes.sp_crear_acc", parm);
		
	}


}
