package pe.gob.sunat.recurso2.administracion.siga.parametro.model.dao.ibatis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;


import pe.gob.sunat.framework.spring.util.dao.SqlMapDAOBase;
import pe.gob.sunat.recurso2.administracion.siga.parametro.model.bean.SysParametrosBean;
import pe.gob.sunat.recurso2.administracion.siga.parametro.model.dao.SysEstadosDAO;

/**
 * Implementacion de la interface SysEstadosDAO.
 * Mantenimiento de Registro de Estados(SYS_ESTADOS)
 * @author emarchena.
 */
public class SqlMapSysEstadosDAOImpl extends SqlMapDAOBase implements SysEstadosDAO{

	@Override
	public Collection listarEstados(Map<String, Object> params) throws DataAccessException {
	
		Map map = new HashMap();
		List lista = new ArrayList<SysParametrosBean>();
		map.put("nom_tabla", params.get("nom_tabla"));
		
		return getSqlMapClientTemplate().queryForList("sysestados.selectAll", map);
	}

	
}
