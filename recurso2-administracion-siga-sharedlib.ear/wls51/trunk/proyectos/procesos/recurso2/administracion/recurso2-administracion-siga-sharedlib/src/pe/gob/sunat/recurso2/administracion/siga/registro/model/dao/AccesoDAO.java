package pe.gob.sunat.recurso2.administracion.siga.registro.model.dao;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
/**
 * Interface SysRegistroArchivosDAO.
 * auditoria de registro de tablas, para cambio de usuario web a bd(SEG_ACCESO)
 * @author emarchena.
 */
public interface AccesoDAO {
	
	void setearVariableEntorno(Map<String,Object> parametros);
	void limpiarVariableEntorno(Map<String,Object> parametros);
	
	public void setUsuarioAcceso(DataSource dataSource,String variable, String valor)	throws DataAccessException ;
	public void setUsuarioAccesoNull(DataSource dataSource,String variable)	throws DataAccessException ;
}
