package pe.gob.sunat.recurso2.administracion.siga.archivo.model.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;

import pe.gob.sunat.recurso2.administracion.siga.archivo.model.bean.RegistroArchivosFisicoBean;



/**
 * Interface SysRegistroArchivosFisicoDAO.
 * Mantenimiento de Registro de Archivos Fisicos(SYS_REGISTRO_ARCHIVOS_FISICO)
 * @author emarchena.
 */
public interface SysRegistroArchivosFisicoDAO {

	public Long   obtenerContador (Map<String, Object> params) throws DataAccessException;
	
	@SuppressWarnings("rawtypes")
	public Collection listarArchivos(Map<String, Object> params )	throws DataAccessException;
	public RegistroArchivosFisicoBean recuperarArchivo(Map<String, Object> params)
	throws DataAccessException ;
	public void insertar(DataSource datasource, RegistroArchivosFisicoBean bean)throws DataAccessException;
	public void insertar( RegistroArchivosFisicoBean bean)
	throws DataAccessException;
	
	public void eliminaArchivo(DataSource datasource, RegistroArchivosFisicoBean bean)
	throws DataAccessException;
	
	public void eliminaArchivo( RegistroArchivosFisicoBean bean)
			throws DataAccessException;
	
	public void insertarWithDocFirmado( RegistroArchivosFisicoBean bean) throws DataAccessException;
	
	@SuppressWarnings("rawtypes")
	public List<RegistroArchivosFisicoBean> listarArchivosFirmados(String numeroRegistroArchivo, String nombreObjeto)throws DataAccessException;
}
