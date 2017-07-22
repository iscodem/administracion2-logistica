package pe.gob.sunat.recurso2.administracion.siga.expediente.model.dao.ibatis;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import pe.gob.sunat.framework.spring.util.dao.SqlMapDAOBase;
import pe.gob.sunat.recurso2.administracion.siga.expediente.model.bean.ExpedienteInternoAccionBean;
import pe.gob.sunat.recurso2.administracion.siga.expediente.model.dao.ExpedienteInternoAccionDAO;
/**
 * Implementacion de la interface ExpedienteInternoAccionDAO.
 *  Mantenimiento de  Expediente Interno Acciones(EXPEDIENTES_INTERNOS_ACCIONES)
 * @author emarchena.
 */
public class SqlMapExpedienteInternoAccionDAOImpl extends SqlMapDAOBase implements ExpedienteInternoAccionDAO {

	
	
	
	@Override
	public Collection obtenerlistaAcciones(
			String num_expediente) throws DataAccessException {
		Map map_rqnp = new HashMap();
		map_rqnp.put("num_expediente", num_expediente);
		return (Collection<ExpedienteInternoAccionBean>) getSqlMapClientTemplate().queryForList("expedienteinternoaccion.selectExp", map_rqnp);
	}

}
