package pe.gob.sunat.administracion2.siga.rqnp.model.dao.ibatis;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import pe.gob.sunat.administracion2.siga.rqnp.model.dao.SolicitudBienDao;
import pe.gob.sunat.administracion2.siga.rqnp.model.domain.RequerimientoNoProgramadoBean;
import pe.gob.sunat.administracion2.siga.rqnp.model.domain.SolicitudBienBean;
import pe.gob.sunat.framework.spring.util.dao.SqlMapDAOBase;

public class SqlMapSolicitudBienDaoImpl extends SqlMapDAOBase implements SolicitudBienDao {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Collection listarSolicitudes(Map<String, Object> params)
			throws DataAccessException {
		Map map_rqnp = new HashMap();
		
		map_rqnp.put("cod_per", (String) params.get("cod_per"));
		map_rqnp.put("cod_estado",(String) params.get("cod_estado"));
		map_rqnp.put("cod_dep",(String) params.get("cod_dep"));
		return  getSqlMapClientTemplate().queryForList("solicitudbien.selectAll", map_rqnp);
	}
	

	@SuppressWarnings({"rawtypes", "unchecked" })
	@Override
	public SolicitudBienBean recuperarSolicitud(String cod_sol)
			throws DataAccessException {
		Map map_rqnp = new HashMap();
		
		map_rqnp.put("cod_sol", cod_sol);
		
		return  (SolicitudBienBean)getSqlMapClientTemplate().queryForObject("solicitudbien.selectSolicitud", map_rqnp);
	}

	@Override
	public Long obtenerContador() throws DataAccessException {
		// TODO Auto-generated method stub
		Long ll_count= (Long)   getSqlMapClientTemplate().queryForObject("solicitudbien.selectCount") + 1;
		return ll_count;
	}

	@Override
	public void insertar(DataSource datasource, SolicitudBienBean bean)
			throws DataAccessException {
		new SqlMapClientTemplate(datasource, getSqlMapClientTemplate().getSqlMapClient()).
		insert("solicitudbien.insertar", bean);
	}

	@Override
	public void update(DataSource datasource, SolicitudBienBean bean)
			throws DataAccessException {
		new SqlMapClientTemplate(datasource, getSqlMapClientTemplate().getSqlMapClient()).
		update("solicitudbien.update", bean);
	}

	@Override
	public void updateEstado(DataSource datasource, SolicitudBienBean bean)
			throws DataAccessException {
		new SqlMapClientTemplate(datasource, getSqlMapClientTemplate().getSqlMapClient()).
		update("solicitudbien.updateEstado", bean);
	}
	
	@Override
	public void updateAprueba(DataSource datasource, SolicitudBienBean bean)
			throws DataAccessException {
		new SqlMapClientTemplate(datasource, getSqlMapClientTemplate().getSqlMapClient()).
		update("solicitudbien.updateAprueba", bean);
	}
	
	@Override
	public void updateRechaza(DataSource datasource, SolicitudBienBean bean)
			throws DataAccessException {
		new SqlMapClientTemplate(datasource, getSqlMapClientTemplate().getSqlMapClient()).
		update("solicitudbien.updateRechaza", bean);
	}
	@Override
	public void updateFile(DataSource datasource, SolicitudBienBean bean)
			throws DataAccessException {
		new SqlMapClientTemplate(datasource, getSqlMapClientTemplate().getSqlMapClient()).
		update("solicitudbien.updateFile", bean);
		
	}
	
	@Override
	public void envioMailDerivar(String cod_msg, String cod_sol) throws DataAccessException {
		Map map_parm = new HashMap();
		map_parm.put("tipo_msg", cod_msg);
		map_parm.put("cod_sol", cod_sol);
		getSqlMapClientTemplate().queryForObject(
				"solicitudbien.rqnp_mail_derivar", map_parm);
	}

//(DPORRASC)/////////////////////////////////////////////	
	@Override
	public SolicitudBienBean recuperarSolicitudBienBean(String cod_sol) throws DataAccessException {
		Map map_rqnp = new HashMap();
		
		map_rqnp.put("cod_sol", cod_sol);
		return (SolicitudBienBean) getSqlMapClientTemplate().queryForObject("solicitudbien.selectSolicitud", map_rqnp);
	}
	
	@Override
	public Collection listarConsultaCatalogo(Map<String, Object> params)
			throws DataAccessException {
		Map map_rqnp = new HashMap();
	
		String tipo_consulta = (String) params.get("tipo_consulta");
		String desc_bien = (String) params.get("desc_bien");
		String cod_dep = (String) params.get("cod_dep");
		String tipo_bien=(String) params.get("tipo_bien");
		
		if(tipo_bien==null){
			tipo_bien="";
		}
		if(tipo_consulta==null){
			tipo_consulta="";
		}
		
		if(cod_dep==null){
			cod_dep="";
		}
		
		if(tipo_consulta.equals("C")) {
			map_rqnp.put("tipo_bien", tipo_bien);
			map_rqnp.put("cod_bien", "%"+desc_bien+"%");
			
		}else if(tipo_consulta.equals("D")) {
			if(desc_bien.equals("")){
				map_rqnp.put("tipo_bien", tipo_bien);
				map_rqnp.put("desc_bien","%IMPRESO%");
			}else{
				map_rqnp.put("tipo_bien", tipo_bien);
				map_rqnp.put("desc_bien","%"+ desc_bien+"%");
			}
			
		}else if(tipo_consulta.equals("A")) {
			if(!cod_dep.equals("")){
				map_rqnp.put("tipo_bien", tipo_bien);
				map_rqnp.put("cod_auc",cod_dep);
			}else{
				map_rqnp.put("tipo_bien", "Z");
			}
		}
		
		log.debug("tipo_consulta: "+tipo_consulta);
		log.debug("tipo_bien: "+tipo_bien);
		log.debug("desc_bien: "+desc_bien);
		log.debug("cod_dep: "+cod_dep);
		
		return  getSqlMapClientTemplate().queryForList("consultaCatalogo.selectConsultaCatalogo", map_rqnp);
	}
}
