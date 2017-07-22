package pe.gob.sunat.recurso2.administracion.siga.firma.model.dao.ibatis;

import java.util.List;

import org.springframework.dao.DataAccessException;

import pe.gob.sunat.recurso2.administracion.siga.firma.model.dao.DependenciaDao;
import pe.gob.sunat.recurso2.administracion.siga.firma.model.bean.Dependencia;
import pe.gob.sunat.framework.spring.util.dao.SqlMapDAOBase;

public class SqlMapDependenciaDaoImpl  extends SqlMapDAOBase  implements DependenciaDao{

	@Override
	public List<Dependencia> obtenerDatosDependencia(Dependencia param)
			throws DataAccessException {
		return (List<Dependencia>)getSqlMapClientTemplate().queryForList("tdependenciasfirma.obtenerDatosDependencia", param);
	}
}
