package pe.gob.sunat.recurso2.administracion.siga.archivo.model.dao;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;

import pe.gob.sunat.recurso2.administracion.siga.archivo.model.bean.RegistroArchivosBean;


/**
 * Interface SysRegistroArchivosDAO.
 * Mantenimiento de Registro de Archivos(SYS_REGISTRO_ARCHIVOS)
 * @author emarchena.
 */

public interface SysRegistroArchivosDAO {

	public String obtenerContador() throws DataAccessException;
	public void insertar(DataSource datasource, RegistroArchivosBean bean) throws DataAccessException;
	public void insertar(RegistroArchivosBean bean) throws DataAccessException;
	public void insertarWithDocFirmado(RegistroArchivosBean bean) throws DataAccessException;
	public Long correoAdjuntoMovilidad(String fuenteMovilidad, String numeroRegistro) throws DataAccessException;
	public Long obtenerNumeroArchivosRegistrados(String aplicacion, String modulo, String fuente, String numeroRegistroArchivo) throws DataAccessException;
}
