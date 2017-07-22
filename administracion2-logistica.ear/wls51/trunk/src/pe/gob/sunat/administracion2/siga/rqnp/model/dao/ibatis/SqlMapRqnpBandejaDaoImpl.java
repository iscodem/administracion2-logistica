package pe.gob.sunat.administracion2.siga.rqnp.model.dao.ibatis;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import pe.gob.sunat.administracion2.siga.rqnp.model.dao.RqnpBandejaDao;
import pe.gob.sunat.framework.spring.util.dao.SqlMapDAOBase;

public class SqlMapRqnpBandejaDaoImpl extends  SqlMapDAOBase implements RqnpBandejaDao{

	
	@Override
	public Collection listarBienesJefeInmediato(Map<String, Object> params)
			throws DataAccessException {
		return  getSqlMapClientTemplate().queryForList("rqnpbandeja.selectJefeInmediato", params);
	}

	
	@Override
	public Collection listarBienesIntedente(Map<String, Object> params)
			throws DataAccessException {
		return  getSqlMapClientTemplate().queryForList("rqnpbandeja.selectItendente", params);
	}

	
	@Override
	public Collection listarBienesJefeUBG(Map<String, Object> params)
			throws DataAccessException {
		return  getSqlMapClientTemplate().queryForList("rqnpbandeja.selectUBG", params);
	}

	@Override
	public Collection listarBienesJefeOEC(Map<String, Object> params)
			throws DataAccessException {
		return  getSqlMapClientTemplate().queryForList("rqnpbandeja.selectOEC", params);
	}

	
	
	@Override
	public String ubgSolicitanteJefeUct(String cod_rqnp, String cod_bien)
			throws DataAccessException {
		Map params = new HashMap();
		params.put("cod_rqnp", cod_rqnp);
		params.put("cod_bien", cod_bien);
		return  (String) getSqlMapClientTemplate().queryForObject("rqnpbandeja.rqnp_ubg_dep", params);
	}


	@Override
	public Map<String, Object> evaluaOEC(DataSource datasource, Map<String, Object> params)
			throws DataAccessException {
		new SqlMapClientTemplate(datasource, getSqlMapClientTemplate().getSqlMapClient()).queryForObject("rqnpbandeja.sp_oec", params);
		return params;
	}


	public BigDecimal saldoCD(Map<String, Object> params)throws DataAccessException{
		return  (BigDecimal) getSqlMapClientTemplate().queryForObject("rqnpbandeja.rqnp_saldo_cd", params);
	}
	
	
	@Override
	public String oecEsCentral(String cod_oec) throws DataAccessException {
		Map params = new HashMap();
		params.put("cod_oec", cod_oec);
		return  (String) getSqlMapClientTemplate().queryForObject("rqnpbandeja.rqnp_oec_central", params);
	}
	
	///////////////////////////////////////////////////////////////////////////////
	@Override
	public String obtenerIndicadorOec(String cod_oec) throws DataAccessException {
		Map params = new HashMap();
		params.put("cod_oec", cod_oec);
		return  (String) getSqlMapClientTemplate().queryForObject("rqnpbandeja.rqnp_obtener_indicador_oec", params);
	}
	
	@Override
	public String obtenerDpg(String codParametro, String descParametro) throws DataAccessException {
		Map params = new HashMap();
		params.put("codParametro", codParametro);
		params.put("descParametro", descParametro);
		return  (String) getSqlMapClientTemplate().queryForObject("rqnpbandeja.rqnp_obtener_dpg", params);
	}
	///////////////////////////////////////////////////////////////////////////////

}
