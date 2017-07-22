package pe.gob.sunat.recurso2.administracion.siga.ubigeo.model.dao.ibatis;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;


import pe.gob.sunat.framework.spring.util.dao.SqlMapDAOBase;
import pe.gob.sunat.recurso2.administracion.siga.ubigeo.model.bean.TDistritosBean;
import pe.gob.sunat.recurso2.administracion.siga.ubigeo.model.dao.TDistritosDAO;

/**
 * Implementacion de la interface TDistritosDAO.
 * Mantenimiento de Distritos(TDISTRITOS)
 * @author emarchena.
 */
public class SqlMapTDistritosDAOImpl extends SqlMapDAOBase implements TDistritosDAO {
	
	public ArrayList<TDistritosBean> obtenerDistritos(String codigoDepartamento, String codigoProvincia) throws DataAccessException {
		
		Map<String, Object> parmSearch = new HashMap<String, Object>();
		parmSearch.put("codiDepaDpt", codigoDepartamento);
		parmSearch.put("codiProvTpr", codigoProvincia);
		ArrayList<TDistritosBean> distritoList = (ArrayList<TDistritosBean>) getSqlMapClientTemplate().queryForList("tdistritos.obtenerDistritosByCodDepartamentoCodProvincia", parmSearch);
		return distritoList;
	}

}
