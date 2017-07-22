package pe.gob.sunat.administracion2.siga.rqnp.model.dao.ibatis;

import java.util.Collection;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import pe.gob.sunat.administracion2.siga.rqnp.model.dao.AucMetasDao;
import pe.gob.sunat.framework.spring.util.dao.SqlMapDAOBase;

public class SqlMapAucMetasDaoImpl extends  SqlMapDAOBase implements AucMetasDao{

	@Override
	public Collection listarMetasAuc(Map<String, Object> params)
			throws DataAccessException {
		return  getSqlMapClientTemplate().queryForList("aucmetas.selectMetasAuc", params);
	}
}
