package pe.gob.sunat.recurso2.administracion.siga.parametro.model.dao;


import java.util.Collection;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * Interface SysEstadosDAO.
 * Mantenimiento de Registro de Estados(SYS_ESTADOS)
 * @author emarchena.
 */
public interface SysEstadosDAO {
	
	public Collection listarEstados(Map<String, Object> params )	throws DataAccessException;
	
}
