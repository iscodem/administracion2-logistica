package pe.gob.sunat.recurso2.administracion.siga.registro.model.dao;

import java.util.Collection;

import org.springframework.dao.DataAccessException;

import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.EncargosPersonaBean;


/**
 * Interface EncargosPersonaDAO.
 * Mantenimiento de encargaturas(ENCARGOS_PERSONA)
 * @author emarchena.
 */

public interface EncargosPersonaDAO {
	
	public Collection<EncargosPersonaBean> obtenerEncargosPersona(String cod_per)
	throws DataAccessException;

}
