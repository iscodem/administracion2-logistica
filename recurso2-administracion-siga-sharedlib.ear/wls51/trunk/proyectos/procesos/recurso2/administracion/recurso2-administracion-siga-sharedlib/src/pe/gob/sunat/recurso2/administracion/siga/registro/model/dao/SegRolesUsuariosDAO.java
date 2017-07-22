package pe.gob.sunat.recurso2.administracion.siga.registro.model.dao;

import java.util.Collection;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * Interface SegRolesUsuariosDAO.
 * Mantenimiento Roles de Usuario SIGA(SEG_ROLES_USUARIOS)
 * @author emarchena.
 */
public interface SegRolesUsuariosDAO {
	public Collection listarRoles(Map<String, Object> params )	throws DataAccessException;
	//PAS201780000300007
	public Collection listarGrupoAUC(Map<String, Object> params )	throws DataAccessException;
}
