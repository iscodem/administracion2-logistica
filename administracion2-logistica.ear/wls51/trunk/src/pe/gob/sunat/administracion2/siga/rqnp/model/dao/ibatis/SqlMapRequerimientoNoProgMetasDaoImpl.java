package pe.gob.sunat.administracion2.siga.rqnp.model.dao.ibatis;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import pe.gob.sunat.administracion2.siga.rqnp.model.dao.RequerimientoNoProgMetasDao;
import pe.gob.sunat.administracion2.siga.rqnp.model.domain.RequerimientoNoProgMetasBean;
import pe.gob.sunat.framework.spring.util.dao.SqlMapDAOBase;

public class SqlMapRequerimientoNoProgMetasDaoImpl extends SqlMapDAOBase implements  RequerimientoNoProgMetasDao {

	@Override
	public Collection listarRqnpMetas(Map<String, Object> params)
			throws DataAccessException {
		
		return   getSqlMapClientTemplate().queryForList("requerimientonoprogmetas.selectRqnpMetas", params);
	}

	@Override
	public Collection listarMetasUuoo(Map<String, Object> params) throws DataAccessException  {
			    return getSqlMapClientTemplate().queryForList("requerimientonoprogmetas.selectMetasUuoo", params);
			  }
	
	@Override
	public void insertar(RequerimientoNoProgMetasBean bean) throws DataAccessException {
		new SqlMapClientTemplate(getSqlMapClientTemplate().getSqlMapClient()).
        insert("requerimientonoprogmetas.insertar", bean); 
		
	}

	@Override
	public void update(RequerimientoNoProgMetasBean bean) throws DataAccessException {
		new SqlMapClientTemplate(getSqlMapClientTemplate().getSqlMapClient()).
		update("requerimientonoprogmetas.updateMetas", bean);
		
	}

	@Override
	public void updateModificaBien(DataSource datasource, RequerimientoNoProgMetasBean bean)
			throws DataAccessException {
		new SqlMapClientTemplate(datasource, getSqlMapClientTemplate().getSqlMapClient()).
		update("requerimientonoprogmetas.updateMetasModificaBien", bean);
	}
	
	@Override
	public void delete(RequerimientoNoProgMetasBean bean) throws DataAccessException {
		new SqlMapClientTemplate(getSqlMapClientTemplate().getSqlMapClient()).
		delete("requerimientonoprogmetas.deleteMetas", bean);
		
	}

	@Override
	public Collection listarRqnpMetasBien(Map<String, Object> params)
			throws DataAccessException {
		return   getSqlMapClientTemplate().queryForList("requerimientonoprogmetas.selectRqnpMetas_bien", params);
	}

//INICIO: DPORRASC
	@Override
	public Collection listarRqnpMetasProyectos(Map<String, Object> params)
			throws DataAccessException {
		return   getSqlMapClientTemplate().queryForList("requerimientonoprogmetas.selectMetasProyectos", params);
	}

	@Override
	public Map<String, Object> spReplicarMetas(DataSource datasource,
			Map<String, Object> params) throws DataAccessException {
		new SqlMapClientTemplate(datasource, getSqlMapClientTemplate().getSqlMapClient()).queryForObject("requerimientonoprogmetas.sp_replicar_metas", params);
		return params;
	}

	@Override
	public Collection validaMetaVacia(Map<String, Object> params)
			throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("requerimientonoprogmetas.validaMetaVacia", params);
	 }
	
	//Obtiene el SecunFunc de una uuoo
	@Override
	public Map<String, Object> obtenerSecuFuncUuoo(Map<String, Object> params) throws DataAccessException {
		Map<String,Object> map = new HashMap<String, Object>();
		map = (Map<String, Object>) getSqlMapClientTemplate().queryForObject("requerimientonoprogmetas.obtenerSecuFuncUuoo", params);
		return map;
	}
	
	
	//Obtiene cantidad del bien despues de eliminar beneficiaria
	@Override
	public String obtenerCantBien(Map<String, Object> params) throws DataAccessException {
		return (String)  getSqlMapClientTemplate().queryForObject("requerimientonoprogmetas.obtenerCantBien", params);
	}
	
	//Obtiene el monto total del rqnp despues de eliminar la beneficiaria
	@Override
	public String obtenerMontoRqnp(Map<String, Object> params) throws DataAccessException {
		return (String)  getSqlMapClientTemplate().queryForObject("requerimientonoprogmetas.obtenerMontoRqnp", params);
	}
	
}
