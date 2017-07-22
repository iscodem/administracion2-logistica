package pe.gob.sunat.administracion2.siga.rqnp.model.dao.ibatis;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import pe.gob.sunat.administracion2.siga.rqnp.model.dao.RequerimientoNoProgBienesDao;
import pe.gob.sunat.administracion2.siga.rqnp.model.domain.RequerimientoNoProgBienesBean;


import pe.gob.sunat.framework.spring.util.dao.SqlMapDAOBase;

public class SqlMapRequerimientoNoProgBienesDaoImpl extends SqlMapDAOBase implements RequerimientoNoProgBienesDao {

	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	@Override
	public Collection listarRqnpBienes(String cod_req)
			throws DataAccessException {
		Map map_rqnp = new HashMap();
		map_rqnp.put("cod_req", cod_req);
		return  getSqlMapClientTemplate().queryForList("requerimientonoprogbienes.selectRqnpBienes", map_rqnp);
	}
	@Override
	public Collection listarRqnpBienes(Map parm)
			throws DataAccessException {

		return  getSqlMapClientTemplate().queryForList("requerimientonoprogbienes.selectRqnpBienes", parm);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public RequerimientoNoProgBienesBean recuperarRqnpBienes(String cod_req ,String cod_bien )
			throws DataAccessException {
		Map map_rqnp = new HashMap();
		map_rqnp.put("cod_req", cod_req);
		map_rqnp.put("cod_bien", cod_bien);
		return  (RequerimientoNoProgBienesBean)getSqlMapClientTemplate().queryForObject("requerimientonoprogbienes.selectRqnpBienes", map_rqnp);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public RequerimientoNoProgBienesBean recuperarRqnpBienesModifica(String cod_req ,String cod_bien )
			throws DataAccessException {
		Map map_rqnp = new HashMap();
		map_rqnp.put("cod_req", cod_req);
		map_rqnp.put("cod_bien", cod_bien);
		return  (RequerimientoNoProgBienesBean)getSqlMapClientTemplate().queryForObject("requerimientonoprogbienes.selectRqnpBienesModifica", map_rqnp);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void insertar(RequerimientoNoProgBienesBean bean) throws DataAccessException {
		new SqlMapClientTemplate(getSqlMapClientTemplate().getSqlMapClient()).
        insert("requerimientonoprogbienes.insertar", bean);
		
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	@Override
	public void update(RequerimientoNoProgBienesBean bean)
			throws DataAccessException {
		new SqlMapClientTemplate(getSqlMapClientTemplate().getSqlMapClient()).
		update("requerimientonoprogbienes.updateBienes", bean);
	}
	
	@Override
	public void updateEntrega(DataSource datasource, RequerimientoNoProgBienesBean bean)
			throws DataAccessException {
		new SqlMapClientTemplate(datasource, getSqlMapClientTemplate().getSqlMapClient()).
		update("requerimientonoprogbienes.updateBienesEntrega", bean);	
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void delete(RequerimientoNoProgBienesBean bean) throws DataAccessException {
		new SqlMapClientTemplate(getSqlMapClientTemplate().getSqlMapClient()).
		delete("requerimientonoprogbienes.deleteBien", bean);
		
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void updateCantidades(DataSource datasource,
			RequerimientoNoProgBienesBean bean) throws DataAccessException {
		new SqlMapClientTemplate(datasource, getSqlMapClientTemplate().getSqlMapClient()).
		update("requerimientonoprogbienes.updateCantidades", bean);
	}

	@Override
	public void updateEstado(DataSource datasource,
			RequerimientoNoProgBienesBean bean) throws DataAccessException {
		new SqlMapClientTemplate(datasource, getSqlMapClientTemplate().getSqlMapClient()).
		update("requerimientonoprogbienes.updateEstado", bean);
	}
	
	@Override
	public void updateEstadoAUC(DataSource datasource,
			RequerimientoNoProgBienesBean bean) throws DataAccessException {
		new SqlMapClientTemplate(datasource, getSqlMapClientTemplate().getSqlMapClient()).
		update("requerimientonoprogbienes.updateEstadoAUC", bean);
	}
	
	@Override
	public void updateEstadoOnly(DataSource datasource,
			RequerimientoNoProgBienesBean bean) throws DataAccessException {
		new SqlMapClientTemplate(datasource, getSqlMapClientTemplate().getSqlMapClient()).
		update("requerimientonoprogbienes.updateEstadoOnly", bean);
	}
	
	@Override
	public void updateRechazo(DataSource datasource,
			RequerimientoNoProgBienesBean bean) throws DataAccessException {
		new SqlMapClientTemplate(datasource, getSqlMapClientTemplate().getSqlMapClient()).
		update("requerimientonoprogbienes.updateRechazo", bean);
	}
	@Override
	public void updateRechazoAuc(DataSource datasource,
			RequerimientoNoProgBienesBean bean) throws DataAccessException {
		new SqlMapClientTemplate(datasource, getSqlMapClientTemplate().getSqlMapClient()).
		update("requerimientonoprogbienes.updateRechazoAuc", bean);
	}
	
	
	@Override
	public void updateFile(DataSource datasource,
			RequerimientoNoProgBienesBean bean) throws DataAccessException {
		new SqlMapClientTemplate(datasource, getSqlMapClientTemplate().getSqlMapClient()).
		update("requerimientonoprogbienes.updateFile", bean);
		
	}

	@Override
	public String crearExpediente(Map parm) throws DataAccessException {
		new SqlMapClientTemplate(getSqlMapClientTemplate().getSqlMapClient()).queryForObject("requerimientonoprogbienes.sp_crear_exp", parm);
		return (String) parm.get("cod_exp");
	}

	@Override
	public void crearAccion(Map parm) throws DataAccessException {
		new SqlMapClientTemplate(getSqlMapClientTemplate().getSqlMapClient()).queryForObject("requerimientonoprogbienes.sp_crear_acc", parm);
		
	}

	@Override
	public void updateModificaBien(DataSource datasource, RequerimientoNoProgBienesBean bean)
			throws DataAccessException {
		new SqlMapClientTemplate(datasource, getSqlMapClientTemplate().getSqlMapClient()).
		update("requerimientonoprogbienes.updateModificaBienes", bean);
	}
	
}
