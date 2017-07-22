package pe.gob.sunat.recurso2.administracion.siga.registro.model.dao.ibatis;

import java.util.Collection;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientTemplate;




import pe.gob.sunat.framework.spring.util.dao.SqlMapDAOBase;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.MaestroContratistasBean;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.dao.MaestroContratistasDAO;

/**
 * Implementacion de la interface MaestroContratistasDAO.
 * Mantenimiento de  Maestro Contratistas(MAESTRO_DE_CONTRATISTAS)
 * @author emarchena.
 */
public class SqlMapMaestroContratistasDAOImpl extends SqlMapDAOBase  implements MaestroContratistasDAO{

	
	@Override
	public Collection<MaestroContratistasBean> listarMaestroContratistas(
			Map parm) throws DataAccessException {
		return  getSqlMapClientTemplate().queryForList("maestro_contratistas.selectAll", parm);
	}

	
	@Override
	public MaestroContratistasBean recuperarMaestroContratistas(Map parm)
			throws DataAccessException {
		return (MaestroContratistasBean) getSqlMapClientTemplate().queryForObject("maestro_contratistas.selectAll", parm);
	}


	@Override
	public void insertar(DataSource datasource, MaestroContratistasBean bean)
			throws DataAccessException {
		new SqlMapClientTemplate(datasource, getSqlMapClientTemplate().getSqlMapClient()).
        insert("maestro_contratistas.insert", bean);
		
	}

	@Override
	public void insertarNew(MaestroContratistasBean bean)
				throws DataAccessException {
		new SqlMapClientTemplate(getSqlMapClientTemplate().getSqlMapClient()).
		insert("maestro_contratistas.insert", bean);
	
	}
}
