package pe.gob.sunat.recurso2.administracion.siga.archivo.model.dao.ibatis;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import pe.gob.sunat.framework.spring.util.dao.SqlMapDAOBase;
import pe.gob.sunat.recurso2.administracion.siga.archivo.model.bean.RegistroArchivosFisicoBean;
import pe.gob.sunat.recurso2.administracion.siga.archivo.model.dao.SysRegistroArchivosFisicoDAO;

/**
 * Implementacion de la interface SysRegistroArchivosFisicoDAO.
 * Mantenimiento de Registro de Archivos Fisicos(SYS_REGISTRO_ARCHIVOS_FISICO)
 * @author emarchena.
 */
public class SqlMapSysRegistroArchivosFisicoDAOImpl extends SqlMapDAOBase implements SysRegistroArchivosFisicoDAO{

	@Override
	public Long obtenerContador(Map<String, Object> params) throws DataAccessException {
		return (Long) getSqlMapClientTemplate(). queryForObject(
		"registro_archivos_fisico.selectCount", params);
	}
	
	@Override
	public Collection listarArchivos(Map<String, Object> params)
			throws DataAccessException {
			return  getSqlMapClientTemplate().queryForList("registro_archivos_fisico.selectArchivos", params);
	}

	
	@Override
	public RegistroArchivosFisicoBean recuperarArchivo(Map<String, Object> params)
			throws DataAccessException {
			return  (RegistroArchivosFisicoBean)getSqlMapClientTemplate().queryForObject("registro_archivos_fisico.selectArchivoDescarga", params);
	}
	
	@Override
	public void insertar(DataSource datasource, RegistroArchivosFisicoBean bean)
			throws DataAccessException {
			new SqlMapClientTemplate(datasource, getSqlMapClientTemplate().getSqlMapClient()).
			insert("registro_archivos_fisico.insert", bean);
	}

	@Override
	public void insertar( RegistroArchivosFisicoBean bean)
			throws DataAccessException {
			new SqlMapClientTemplate( getSqlMapClientTemplate().getSqlMapClient()).
			insert("registro_archivos_fisico.insert", bean);
	}
	
	@Override
	public void eliminaArchivo(DataSource datasource, RegistroArchivosFisicoBean bean)
			throws DataAccessException {
			new SqlMapClientTemplate(datasource, getSqlMapClientTemplate().getSqlMapClient()).
			update("registro_archivos_fisico.eliminaArchivo", bean);
	}
	
	@Override
	public void eliminaArchivo(RegistroArchivosFisicoBean bean)
			throws DataAccessException {
			new SqlMapClientTemplate(getSqlMapClientTemplate().getSqlMapClient()).
			update("registro_archivos_fisico.eliminaArchivo", bean);
	}

	@Override
	public void insertarWithDocFirmado(RegistroArchivosFisicoBean bean) throws DataAccessException {
			new SqlMapClientTemplate( getSqlMapClientTemplate().getSqlMapClient()).
			insert("registro_archivos_fisico.insertWithDocFirmado", bean);
	}

	@Override
	public List<RegistroArchivosFisicoBean> listarArchivosFirmados(String numeroRegistroArchivo, String nombreObjeto)
			throws DataAccessException {
		Map<String,Object> parametros = new HashMap<String,Object>();
		parametros.put("numeroRegistroArchivo", numeroRegistroArchivo);
		parametros.put("nombreObjeto", nombreObjeto);
		return  getSqlMapClientTemplate().queryForList("registro_archivos_fisico.selectArchivosFirma", parametros);
	}

}
