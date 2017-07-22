package pe.gob.sunat.recurso2.administracion.siga.expediente.model.dao;

import java.util.Collection;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * Interface SysRegistroArchivosDAO.
 * Mantenimiento de Registro de Expediente(PA_EXPEDIENTES)
 * @author emarchena.
 */

public interface PaExpedienteDAO {
	
	public String crearExpediente( Map<String,Object> parm)throws DataAccessException;
	
	public void crearAccion( Map<String,Object> parm)throws DataAccessException;

}
