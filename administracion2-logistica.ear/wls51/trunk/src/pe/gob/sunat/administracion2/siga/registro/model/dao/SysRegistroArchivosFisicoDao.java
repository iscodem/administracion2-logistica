package pe.gob.sunat.administracion2.siga.registro.model.dao;

import java.util.Collection;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;

import pe.gob.sunat.administracion2.siga.registro.model.domain.RegistroArchivosFisicoBean;


public interface SysRegistroArchivosFisicoDao {

	public Long   obtenerContador (Map<String, Object> params) throws DataAccessException;
	
	public Collection listarArchivos(Map<String, Object> params )	throws DataAccessException;
	public RegistroArchivosFisicoBean recuperarArchivo(Map<String, Object> params)
	throws DataAccessException ;
	public void insertar(DataSource datasource, RegistroArchivosFisicoBean bean)
	throws DataAccessException;
	
	public void eliminaArchivo(DataSource datasource, RegistroArchivosFisicoBean bean)
	throws DataAccessException;
}
