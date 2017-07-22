package pe.gob.sunat.recurso2.administracion.siga.ubigeo.model.dao.ibatis;


import java.util.ArrayList;

import org.springframework.dao.DataAccessException;


import pe.gob.sunat.framework.spring.util.dao.SqlMapDAOBase;
import pe.gob.sunat.recurso2.administracion.siga.ubigeo.model.bean.TDepartamentosBean;
import pe.gob.sunat.recurso2.administracion.siga.ubigeo.model.dao.TDepartamentosDAO;


/**
 * Implementacion de la interface TDepartamentosDAO.
 * Mantenimiento de Departamentos(TDEPARTAMENTOS)
 * @author emarchena.
 */
public class SqlMapTDepartamentosDAOImpl extends SqlMapDAOBase implements TDepartamentosDAO {

	public ArrayList<TDepartamentosBean> obtenerDepartamentos() throws DataAccessException {
		
		ArrayList<TDepartamentosBean> departamentoList = (ArrayList<TDepartamentosBean>) getSqlMapClientTemplate().queryForList("tdepartamentos.obtenerDepartamentos");
		return departamentoList;
	}
}
