package pe.gob.sunat.administracion2.siga.rqnp.model.dao;

import java.util.Collection;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;


import pe.gob.sunat.administracion2.siga.rqnp.model.domain.RequerimientoNoProgramadoBean;
import pe.gob.sunat.administracion2.siga.rqnp.model.domain.SolicitudBienBean;

public interface SolicitudBienDao {

	public Collection listarSolicitudes(Map<String, Object> params )	throws DataAccessException;

	public SolicitudBienBean recuperarSolicitud(String cod_sol)throws DataAccessException;
	
	public Long   obtenerContador () throws DataAccessException;
	
	public void insertar(DataSource datasource, SolicitudBienBean bean)
	throws DataAccessException;
	
	public void update(DataSource datasource, SolicitudBienBean bean)
	throws DataAccessException;
	
	public void updateEstado(DataSource datasource, SolicitudBienBean bean)
	throws DataAccessException;
	public void updateAprueba(DataSource datasource, SolicitudBienBean bean)
	throws DataAccessException;
	
	public void updateRechaza(DataSource datasource, SolicitudBienBean bean)
	throws DataAccessException;
	
	public void updateFile(DataSource datasource, SolicitudBienBean bean)
	throws DataAccessException ;
	
	//DPORRASC-CATALOGO/////////////////////////////
//	public void updateEnvio(DataSource datasource, SolicitudBienBean bean)
//	throws DataAccessException;
	public void envioMailDerivar( String cod_rqnp, String cod_bien)
	throws DataAccessException;
	
	public SolicitudBienBean recuperarSolicitudBienBean(String cod_req )
	throws DataAccessException;
	
	public Collection listarConsultaCatalogo(Map<String, Object> params )	
	throws DataAccessException;
	
	//DPORRASC-CATALOGO/////////////////////////////
}
