package pe.gob.sunat.administracion2.siga.registro.model.dao.ibatis;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import pe.gob.sunat.administracion2.siga.registro.model.dao.SysRegistroArchivosDao;
import pe.gob.sunat.administracion2.siga.registro.model.domain.RegistroArchivosBean;
import pe.gob.sunat.framework.spring.util.dao.SqlMapDAOBase;

public class SqlMapSysRegistroArchivosDaoImpl extends SqlMapDAOBase implements SysRegistroArchivosDao{

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

}
