package pe.gob.sunat.recurso2.administracion.siga.parametro.model.dao.ibatis;

import java.util.Collection;
import java.util.Map;

import org.springframework.dao.DataAccessException;


import pe.gob.sunat.framework.spring.util.dao.SqlMapDAOBase;
import pe.gob.sunat.recurso2.administracion.siga.parametro.model.bean.ObjetivosBean;
import pe.gob.sunat.recurso2.administracion.siga.parametro.model.dao.ObjetivosDAO;


/**
 * Implementacion de la interface ObjetivosDAO.
 * Mantenimiento de objetivos (OBJETIVOS)
 * @author emarchena.
 */
public class SqlMapObjetivosDAOImpl extends SqlMapDAOBase implements ObjetivosDAO {

	@Override
	public Collection<ObjetivosBean> listarObjetivos(Map parm)
			throws DataAccessException {
		return  getSqlMapClientTemplate().queryForList("objetivos.selectAll", parm);
	}

}
