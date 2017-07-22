package pe.gob.sunat.administracion2.siga.rqnp.model.dao;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;

public interface MetasProyectosDao {
	public Collection listarMetasProyectos(Map<String, Object> params )	throws DataAccessException;
	public Collection listarMetasProductos(Map<String, Object> params )	throws DataAccessException;
	public Collection listarMetasAcciones(Map<String, Object> params )	throws DataAccessException;
	public Collection listarProyProdAccion(Map<String, Object> params )	throws DataAccessException;
}
