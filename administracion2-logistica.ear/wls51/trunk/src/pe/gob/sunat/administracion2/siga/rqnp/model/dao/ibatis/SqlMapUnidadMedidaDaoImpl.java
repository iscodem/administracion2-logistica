package pe.gob.sunat.administracion2.siga.rqnp.model.dao.ibatis;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import pe.gob.sunat.administracion2.siga.rqnp.model.dao.UnidadMedidaDao;
import pe.gob.sunat.framework.spring.util.dao.SqlMapDAOBase;

public class SqlMapUnidadMedidaDaoImpl extends SqlMapDAOBase implements UnidadMedidaDao{

	@Override
	public Collection listarUnidades() throws DataAccessException {
		Map map_rqnp = new HashMap();
		return  getSqlMapClientTemplate().queryForList("unidadmedida.selectAll", map_rqnp);
	}

}
