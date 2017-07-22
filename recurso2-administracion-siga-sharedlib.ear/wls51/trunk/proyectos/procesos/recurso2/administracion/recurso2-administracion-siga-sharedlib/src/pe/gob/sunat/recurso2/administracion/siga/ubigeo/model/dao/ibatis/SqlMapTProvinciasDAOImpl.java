package pe.gob.sunat.recurso2.administracion.siga.ubigeo.model.dao.ibatis;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;


import pe.gob.sunat.framework.spring.util.dao.SqlMapDAOBase;
import pe.gob.sunat.recurso2.administracion.siga.ubigeo.model.bean.TProvinciasBean;
import pe.gob.sunat.recurso2.administracion.siga.ubigeo.model.dao.TProvinciasDAO;

/**
 * Implementacion de la interface TProvinciasDAO.
 * Mantenimiento de Provincias(TPROVINCIAS)
 * @author emarchena.
 */
public class SqlMapTProvinciasDAOImpl extends SqlMapDAOBase implements TProvinciasDAO {
	
	public ArrayList<TProvinciasBean> obtenerProvincias(String codigoDepartamento) throws DataAccessException {
		
		Map<String, Object> parmSearch = new HashMap<String, Object>();
		parmSearch.put("codiDepaDpt", codigoDepartamento);
		ArrayList<TProvinciasBean> provinciaList = (ArrayList<TProvinciasBean>) getSqlMapClientTemplate().queryForList("tprovincias.obtenerProvinciasByCodigoDepartamento", parmSearch);
		return provinciaList;
	}
	
}
