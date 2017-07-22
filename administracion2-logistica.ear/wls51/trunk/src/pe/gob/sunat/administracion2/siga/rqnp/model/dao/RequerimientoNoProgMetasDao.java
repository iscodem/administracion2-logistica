package pe.gob.sunat.administracion2.siga.rqnp.model.dao;

import java.util.Collection;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;




import pe.gob.sunat.administracion2.siga.rqnp.model.domain.RequerimientoNoProgMetasBean;

public interface RequerimientoNoProgMetasDao {
	public Collection listarRqnpMetas(Map<String, Object> params )
	throws DataAccessException;
	
	public abstract Collection listarMetasUuoo(Map<String, Object> params)
	throws DataAccessException;
	
	public Collection listarRqnpMetasBien(Map<String, Object> params )
	throws DataAccessException;
	
	public void insertar(RequerimientoNoProgMetasBean bean) throws DataAccessException;
	public void update(RequerimientoNoProgMetasBean bean) throws DataAccessException;
	
	public void updateModificaBien(DataSource datasource, RequerimientoNoProgMetasBean bean)
	throws DataAccessException;

	public void delete(RequerimientoNoProgMetasBean bean) throws DataAccessException;

	//INICIO: DPORRASC
	public Collection listarRqnpMetasProyectos(Map<String, Object> params )
	throws DataAccessException;
	
	public Map<String, Object> spReplicarMetas(DataSource datasource,Map<String, Object> params)
	throws DataAccessException;
	
	public abstract Collection validaMetaVacia(Map<String, Object> params)
	throws DataAccessException;
	
	public Map<String, Object> obtenerSecuFuncUuoo(Map<String, Object> params)
	throws DataAccessException;
	
	public String obtenerCantBien(Map<String, Object> params) throws DataAccessException;
	public String obtenerMontoRqnp(Map<String, Object> params) throws DataAccessException;
}
