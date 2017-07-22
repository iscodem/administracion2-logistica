package pe.gob.sunat.administracion2.siga.rqnp.model.dao;

import java.util.Collection;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;

import pe.gob.sunat.administracion2.siga.rqnp.model.domain.RequerimientoNoProgBienesBean;

public interface RequerimientoNoProgBienesDao {
	
	public Collection listarRqnpBienes(String cod_req )
	throws DataAccessException;
	
	public RequerimientoNoProgBienesBean recuperarRqnpBienes(String cod_req ,String cod_bien )
	throws DataAccessException;
	
	public RequerimientoNoProgBienesBean recuperarRqnpBienesModifica(String cod_req ,String cod_bien )
	throws DataAccessException;
	
	public void insertar(RequerimientoNoProgBienesBean bean) throws DataAccessException;
	public void update(RequerimientoNoProgBienesBean bean) throws DataAccessException;
	
	public void updateEntrega(DataSource datasource, RequerimientoNoProgBienesBean bean)
	throws DataAccessException;

	public void updateCantidades(DataSource datasource, RequerimientoNoProgBienesBean bean)
	throws DataAccessException;
	
	public void updateEstado(DataSource datasource, RequerimientoNoProgBienesBean bean)
	throws DataAccessException;
	
	public void updateEstadoAUC(DataSource datasource, RequerimientoNoProgBienesBean bean)
	throws DataAccessException;
	
	public void updateEstadoOnly(DataSource datasource, RequerimientoNoProgBienesBean bean)
	throws DataAccessException;
	
	public void updateRechazo(DataSource datasource, RequerimientoNoProgBienesBean bean)
	throws DataAccessException;
	
	public void updateRechazoAuc(DataSource datasource, RequerimientoNoProgBienesBean bean)
	throws DataAccessException;
	
	public void updateFile(DataSource datasource, RequerimientoNoProgBienesBean bean)
	throws DataAccessException;
	
	public void delete(RequerimientoNoProgBienesBean bean) throws DataAccessException;
	
	public String crearExpediente(Map parm)throws DataAccessException;
	public void crearAccion(Map parm)throws DataAccessException;
	
	public void updateModificaBien(DataSource datasource, RequerimientoNoProgBienesBean bean)
	throws DataAccessException;
	
	public Collection listarRqnpBienes(Map parm)
	throws DataAccessException;
	
}
