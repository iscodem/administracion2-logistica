package pe.gob.sunat.administracion2.siga.rqnp.model.dao;

import java.util.Collection;

import org.springframework.dao.DataAccessException;

public interface UnidadMedidaDao {

	public Collection listarUnidades( )	throws DataAccessException;
}
