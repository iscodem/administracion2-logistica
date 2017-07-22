package pe.gob.sunat.recurso2.administracion.siga.registro.model.dao;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;

import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.PersonaBean;



/**
 * Interface PersonaDAO.
 * Mantenimiento de Datos de Personas(PERSONA)
 * @author emarchena.
 */

public interface PersonaDAO {

	public PersonaBean recuperarPersona(Map<String, Object> params)
	throws DataAccessException;
	
	public String recuperarIDPersona()
	throws DataAccessException;
	
	public void updateRuc(DataSource datasource, PersonaBean bean)
	throws DataAccessException;
	
	public void updateRucNew( PersonaBean bean)
			throws DataAccessException;
	
	public void insertar(DataSource datasource, PersonaBean bean)
	throws DataAccessException;
	
	public void insertarNew( PersonaBean bean)
			throws DataAccessException;
	
	public Map<String, Object> spRUCdatos(DataSource datasource,Map<String, Object> params)
	throws DataAccessException;
	
	public Map<String, Object> spRUCdatosNew(Map<String, Object> params)
			throws DataAccessException;
	
	public String validaRUC(Map<String, Object> params)
	throws DataAccessException;
	
	public String recuperaRUCestado(Map<String, Object> params)
	throws DataAccessException;

	void insertarNew2(PersonaBean bean) throws DataAccessException;
}
