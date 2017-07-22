package pe.gob.sunat.administracion2.siga.registro.model.dao.ibatis;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import pe.gob.sunat.administracion2.siga.registro.model.dao.SysRegistroArchivosFisicoDao;
import pe.gob.sunat.administracion2.siga.registro.model.domain.RegistroArchivosFisicoBean;
import pe.gob.sunat.framework.spring.util.dao.SqlMapDAOBase;

public class SqlMapSysRegistroArchivosFisicoDaoImpl extends SqlMapDAOBase implements SysRegistroArchivosFisicoDao{

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
	public void eliminaArchivo(DataSource datasource, RegistroArchivosFisicoBean bean)
			throws DataAccessException {
			new SqlMapClientTemplate(datasource, getSqlMapClientTemplate().getSqlMapClient()).
			update("registro_archivos_fisico.eliminaArchivo", bean);
	}

}
