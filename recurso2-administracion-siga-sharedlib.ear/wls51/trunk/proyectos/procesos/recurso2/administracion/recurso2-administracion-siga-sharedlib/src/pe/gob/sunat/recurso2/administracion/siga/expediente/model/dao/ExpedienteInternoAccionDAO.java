package pe.gob.sunat.recurso2.administracion.siga.expediente.model.dao;

import java.util.Collection;

import org.springframework.dao.DataAccessException;



/**
 * Interface ExpedienteInternoAccionDAO.
 * Mantenimiento de  Expediente Interno Acciones(EXPEDIENTES_INTERNOS_ACCIONES)
 * @author emarchena.
 */
public interface ExpedienteInternoAccionDAO {

	public Collection obtenerlistaAcciones(String num_expediente)
	throws DataAccessException;
}
