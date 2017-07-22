package pe.gob.sunat.recurso2.administracion.siga.parametro.model.dao;


import java.util.Collection;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * Interface SysRegistroArchivosDAO.
 * Mantenimiento de sys Parametro SIGA(SYS_PARAMETROS)
 * @author emarchena.
 */
public interface SysParametrosDAO {
	
	public String permiteExcesoRqnp( )	throws DataAccessException;
	public String activarFlujoAuc( )	throws DataAccessException;
	public Long obtieneUitAnio(Map<String, Object> parm) throws DataAccessException;
	//PAS201680000300007
	public String obtenerValUitAnio(String anioEjec)	throws DataAccessException;
	public String obtenerNumUitAnio(String anioEjec)	throws DataAccessException;
}
