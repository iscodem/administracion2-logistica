package pe.gob.sunat.recurso2.administracion.siga.archivo.model.dao.ibatis;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import pe.gob.sunat.framework.spring.util.dao.SqlMapDAOBase;
import pe.gob.sunat.recurso2.administracion.siga.archivo.model.bean.RegistroArchivosBean;
import pe.gob.sunat.recurso2.administracion.siga.archivo.model.dao.SysRegistroArchivosDAO;

/**
 * Implementacion de la interface SysRegistroArchivosDAO.
 * Mantenimiento de Registro de Archivos(SYS_REGISTRO_ARCHIVOS)
 * @author emarchena.
 */
public class SqlMapSysRegistroArchivosDAOImpl extends SqlMapDAOBase implements SysRegistroArchivosDAO{

	@Override
	public String obtenerContador() throws DataAccessException {
		return (String) getSqlMapClientTemplate(). queryForObject(
				"registro_archivos.selectCount");
	}

	@Override
	public void insertar(DataSource datasource, RegistroArchivosBean bean)
			throws DataAccessException {
		new SqlMapClientTemplate(datasource, getSqlMapClientTemplate().getSqlMapClient()).
        insert("registro_archivos.insert", bean);
		
	}
	
	@Override
	public void insertar(RegistroArchivosBean bean) 
			throws DataAccessException {
		new SqlMapClientTemplate( getSqlMapClientTemplate().getSqlMapClient()).
        insert("registro_archivos.insert", bean);
		
	}
	
	public Long correoAdjuntoMovilidad(String fuenteMovilidad, String numeroRegistro) throws DataAccessException {
		Map<String, Object> parmSearch = new HashMap<String, Object>();
		parmSearch.put("fuenteMovilidad", fuenteMovilidad);
		parmSearch.put("numeroRegistro", numeroRegistro);
		return (Long) getSqlMapClientTemplate().queryForObject("registro_archivos.selectCountCorreoMovilidad", parmSearch);
	}
	
	@Override
	public void insertarWithDocFirmado(RegistroArchivosBean bean)
			throws DataAccessException {
		new SqlMapClientTemplate( getSqlMapClientTemplate().getSqlMapClient()).
        insert("registro_archivos.insertWithDocFirmado", bean);
		
	}
	
	@SuppressWarnings("deprecation")
	public Long obtenerNumeroArchivosRegistrados(String aplicacion, String modulo, String fuente, String numeroRegistroArchivo) throws DataAccessException {
		
		Map<String, Object> paramSearch = new HashMap<String, Object>();
		paramSearch.put("aplicacion", aplicacion);
		paramSearch.put("modulo", modulo);
		paramSearch.put("fuente", fuente);
		paramSearch.put("numeroRegistroArchivo", numeroRegistroArchivo);
		return (Long) getSqlMapClientTemplate().queryForObject("registro_archivos.obtenerNumeroArchivosRegistrados", paramSearch);
	}

}
