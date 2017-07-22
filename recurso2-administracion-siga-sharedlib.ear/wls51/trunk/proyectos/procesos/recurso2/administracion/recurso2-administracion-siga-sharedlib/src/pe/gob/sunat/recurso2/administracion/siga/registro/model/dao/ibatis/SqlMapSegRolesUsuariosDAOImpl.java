package pe.gob.sunat.recurso2.administracion.siga.registro.model.dao.ibatis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;


import pe.gob.sunat.framework.spring.util.dao.SqlMapDAOBase;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.dao.SegRolesUsuariosDAO;

/**
 * Implementacion de la interface SegRolesUsuariosDAO.
 * Mantenimiento Roles de Usuario SIGA(SEG_ROLES_USUARIOS)
 * @author emarchena.
 */
public class SqlMapSegRolesUsuariosDAOImpl extends SqlMapDAOBase implements SegRolesUsuariosDAO{

	@Override
	public Collection listarRoles(Map<String, Object> params)
			throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("seg_roles_usuarios.selectRol", params);
	}

	//PAS201780000300007
	@Override
	public Collection listarGrupoAUC(Map<String, Object> params) throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("seg_roles_usuarios.selectGrupoAUC", params);
	}

}
