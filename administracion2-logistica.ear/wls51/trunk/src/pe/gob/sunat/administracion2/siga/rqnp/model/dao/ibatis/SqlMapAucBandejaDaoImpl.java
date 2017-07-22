package pe.gob.sunat.administracion2.siga.rqnp.model.dao.ibatis;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import pe.gob.sunat.administracion2.siga.rqnp.model.dao.AucBandejaDao;
import pe.gob.sunat.framework.spring.util.dao.SqlMapDAOBase;

public class SqlMapAucBandejaDaoImpl extends  SqlMapDAOBase implements AucBandejaDao {

	@Override
	public Collection listarRqnpAuc(Map<String, Object> params)
			throws DataAccessException {
		return  getSqlMapClientTemplate().queryForList("auc_bandeja.selectBandejaAUC", params);
	}
	
	@Override
	public Collection listarRqnpAucAll(Map<String, Object> params)
			throws DataAccessException {
		return  getSqlMapClientTemplate().queryForList("auc_bandeja.selectBandejaAUCAll", params);
	}

	@Override
	public Collection listarRqnpAnexo(Map<String, Object> params)
			throws DataAccessException {
		return  getSqlMapClientTemplate().queryForList("auc_bandeja.selectAUCAnexo", params);
	}
	@Override
	public Collection listarRqnpAnexoMetas(Map<String, Object> params)
			throws DataAccessException {
		return  getSqlMapClientTemplate().queryForList("auc_bandeja.selectAUCAnexoMetas", params);
	}
	
	/////////////////////////////////////////////////////
	@Override
	public String obtenerCadenaEncargosAUC(String cod_empl)
			throws DataAccessException {
		Map map_parm = new HashMap();
		map_parm.put("cod_empl", cod_empl);
		return (String) getSqlMapClientTemplate().queryForObject(
				"auc_bandeja.rqnp_obtener_cadena_encargos_auc", map_parm);
	}
	/////////////////////////////////////////////////////
	
  	@Override
  	public Collection obtenerBeneficiariasForExcel(Map parm) throws DataAccessException {
  		return getSqlMapClientTemplate().queryForList("auc_bandeja.obtenerBeneficiariasForExcel", parm);
  	}
}
