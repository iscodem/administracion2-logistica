package pe.gob.sunat.recurso2.administracion.siga.registro.model.dao.ibatis;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;


import pe.gob.sunat.framework.spring.util.dao.SqlMapDAOBase;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.EncargosPersonaBean;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.dao.EncargosPersonaDAO;

/**
 * Implementacion de la interface EncargosPersonaDAO.
 * Mantenimiento de encargaturas(ENCARGOS_PERSONA)
 * @author emarchena.
 */
public class SqlMapEncargosPersonaDAOImpl extends SqlMapDAOBase implements EncargosPersonaDAO{

	@Override
	public Collection<EncargosPersonaBean> obtenerEncargosPersona(String cod_per)
			throws DataAccessException {
		Map map_rqnp = new HashMap();
		
		map_rqnp.put("cod_per", cod_per);
		return  getSqlMapClientTemplate().queryForList( "encargospersona.selectAll", map_rqnp);
	}

}
