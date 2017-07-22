package pe.gob.sunat.recurso2.administracion.siga.parametro.model.dao;

import java.util.Collection;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * Interface PrgAnnoDAO.
 * Mantenimiento de años(PRG_ANNO)
 * @author emarchena.
 */
public interface PrgAnnoDAO {
	public Collection listarAnnos(Map<String, Object> params )	throws DataAccessException;

}
