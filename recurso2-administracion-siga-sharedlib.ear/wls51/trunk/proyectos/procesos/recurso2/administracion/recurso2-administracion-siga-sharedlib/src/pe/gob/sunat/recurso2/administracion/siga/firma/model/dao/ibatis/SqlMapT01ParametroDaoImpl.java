package pe.gob.sunat.recurso2.administracion.siga.firma.model.dao.ibatis;

import java.util.List;

import org.springframework.dao.DataAccessException;

import pe.gob.sunat.framework.spring.util.dao.SqlMapDAOBase;
import pe.gob.sunat.recurso2.administracion.siga.firma.model.bean.T01Parametro;
import pe.gob.sunat.recurso2.administracion.siga.firma.model.dao.T01ParametroDao;

public class SqlMapT01ParametroDaoImpl extends SqlMapDAOBase implements T01ParametroDao{

	@Override
	public List<T01Parametro> recuperarParametro(T01Parametro param)
			throws DataAccessException {
		List<T01Parametro> lista= (List<T01Parametro> ) getSqlMapClientTemplate().queryForList("t01parametrofirma.select_arg", param);			
		return lista;
	}	
}
