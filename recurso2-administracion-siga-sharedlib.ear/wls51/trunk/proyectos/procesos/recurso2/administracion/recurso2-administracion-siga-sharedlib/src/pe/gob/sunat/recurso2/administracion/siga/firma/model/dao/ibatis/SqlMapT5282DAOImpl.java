package pe.gob.sunat.recurso2.administracion.siga.firma.model.dao.ibatis;

import java.util.Map;

import pe.gob.sunat.framework.spring.util.dao.SqlMapDAOBase;
import pe.gob.sunat.recurso2.administracion.siga.firma.model.bean.T5282Archbin;
import pe.gob.sunat.recurso2.administracion.siga.firma.model.dao.T5282DAO;

public class SqlMapT5282DAOImpl extends SqlMapDAOBase implements T5282DAO {
	
	@Override
	public void updateNombre(T5282Archbin param){
		log.debug(param);
		getSqlMapClientTemplate().update("t5282archbin.updateNombre", param);
	}

	@Override
	public T5282Archbin findByPk(Long id) {
		// TODO Auto-generated method stub
		log.debug(id);
		return (T5282Archbin)getSqlMapClientTemplate().queryForObject("t5282archbin.findByPk", id);
	}

}