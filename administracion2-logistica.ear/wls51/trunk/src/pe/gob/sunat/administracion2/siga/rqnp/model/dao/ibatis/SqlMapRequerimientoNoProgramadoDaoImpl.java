package pe.gob.sunat.administracion2.siga.rqnp.model.dao.ibatis;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import pe.gob.sunat.administracion2.siga.rqnp.model.dao.RequerimientoNoProgramadoDao;
import pe.gob.sunat.administracion2.siga.rqnp.model.domain.RequerimientoNoProgramadoBean;

@SuppressWarnings("unchecked")
public class SqlMapRequerimientoNoProgramadoDaoImpl  extends SqlMapClientDaoSupport implements RequerimientoNoProgramadoDao {

	@Override
	public Collection listarRqnpUsuario(String ano_pro, String est_req,
			String cod_sed, String cod_res, String mes_pro, String ind_registropor) throws DataAccessException {
	
		Map map_rqnp = new HashMap();
		map_rqnp.put("ano_pro", ano_pro);
		if (!est_req.endsWith("-1")){
			map_rqnp.put("est_req", est_req);
		}
		if (mes_pro !=null ){
			if (!mes_pro.endsWith("-1")){
				map_rqnp.put("mes_pro", mes_pro);
			}
		}
		
		map_rqnp.put("cod_sed", cod_sed);
		map_rqnp.put("cod_res", cod_res);
		
		map_rqnp.put("ind_registropor", ind_registropor);
		
		return  getSqlMapClientTemplate().queryForList("requerimientonoprogramado.selectRqnp", map_rqnp);
	}
	
	
	@Override
	public Collection listarRqnpJefe(String ano_pro, String est_req,
			String cod_sed,  String mes_pro, String cod_per) throws DataAccessException {
	
		Map map_rqnp = new HashMap();
		
		map_rqnp.put("ano_pro", ano_pro);
		if (!est_req.endsWith("-1")){
			map_rqnp.put("est_req", est_req);
		}
		if (mes_pro !=null ){
			if (!mes_pro.endsWith("-1")){
				map_rqnp.put("mes_pro", mes_pro);
			}
		}
		map_rqnp.put("cod_sed", cod_sed);
		map_rqnp.put("cod_per", cod_per);
		return  getSqlMapClientTemplate().queryForList("requerimientonoprogramado.selectRqnpJefeInmediato", map_rqnp);
	}
	
	@Override
	public Collection listarRqnpIntendente(String ano_pro, String est_req,
			String cod_sed,  String mes_pro, String cod_per) throws DataAccessException {
	
		Map map_rqnp = new HashMap();
		map_rqnp.put("ano_pro", ano_pro);
		if (!est_req.endsWith("-1")){
			map_rqnp.put("est_req", est_req);
		}
		if (mes_pro !=null ){
			if (!mes_pro.endsWith("-1")){
				map_rqnp.put("mes_pro", mes_pro);
			}
		}
		map_rqnp.put("cod_sed", cod_sed);
		
		map_rqnp.put("cod_per", cod_per);
		return  getSqlMapClientTemplate().queryForList("requerimientonoprogramado.selectRqnpIntendente", map_rqnp);
	}
	

	
	@Override
	public String secuencialRequerimientoRqnpUuoo(String anio_pro, String cod_uuoo)
			throws DataAccessException {
		Map map_rqnp = new HashMap();
		map_rqnp.put("anio_pro", anio_pro);
		map_rqnp.put("cod_uuoo", cod_uuoo);
		return (String)  getSqlMapClientTemplate().queryForObject("requerimientonoprogramado.secuencialRqnpUuooByAnio", map_rqnp);
	}

	@Override
	public String getTipoObjetoRqnp(String cod_req)
			throws DataAccessException {
		Map map_rqnp = new HashMap();
		map_rqnp.put("cod_req", cod_req);

		return (String)  getSqlMapClientTemplate().queryForObject("requerimientonoprogramado.tipoObjetoRqnp", map_rqnp);
	}
	
	@Override
	public String secuencialRequerimientoRqnp(String anio_pro)
			throws DataAccessException {
		Map map_rqnp = new HashMap();
		map_rqnp.put("anio_pro", anio_pro);
		
		return (String)  getSqlMapClientTemplate().queryForObject("requerimientonoprogramado.secuencialRqnpByAnio", map_rqnp);
	}

	@Override
	public void insertar(RequerimientoNoProgramadoBean bean) throws DataAccessException {
		new SqlMapClientTemplate(getSqlMapClientTemplate().getSqlMapClient()).
		insert("requerimientonoprogramado.insertar", bean);
		
	}

	@Override
	public RequerimientoNoProgramadoBean recuperarRqnp(String cod_req) throws DataAccessException {
		Map map_rqnp = new HashMap();
		
		map_rqnp.put("cod_req", cod_req);
		return (RequerimientoNoProgramadoBean) getSqlMapClientTemplate().queryForObject("requerimientonoprogramado.selectRqnp", map_rqnp);
	}

	
	
	@Override
	public void update(RequerimientoNoProgramadoBean bean)
			throws DataAccessException {
		new SqlMapClientTemplate(getSqlMapClientTemplate().getSqlMapClient()).
		update("requerimientonoprogramado.updateCab", bean);
		
	}

	@Override
	public void updateAUC(DataSource datasource, RequerimientoNoProgramadoBean bean)
			throws DataAccessException {
		new SqlMapClientTemplate(datasource, getSqlMapClientTemplate().getSqlMapClient()).
		update("requerimientonoprogramado.updateCabAuc", bean);

	}
	
	
	@Override
	public void updateCodObjeto(DataSource datasource, RequerimientoNoProgramadoBean bean)
			throws DataAccessException {
		new SqlMapClientTemplate(datasource, getSqlMapClientTemplate().getSqlMapClient()).
		update("requerimientonoprogramado.updateCab_Objeto", bean);

	}
	
	
	@Override
	public void updateMonto(RequerimientoNoProgramadoBean bean) throws DataAccessException {
		new SqlMapClientTemplate(getSqlMapClientTemplate().getSqlMapClient()).
		update("requerimientonoprogramado.updateMonto", bean);
	}

	
	@Override
	public void updateEstado(DataSource datasource,
			RequerimientoNoProgramadoBean bean) throws DataAccessException {
		new SqlMapClientTemplate(datasource, getSqlMapClientTemplate().getSqlMapClient()).
		update("requerimientonoprogramado.updateEstado", bean);
	}
	
	public void updateAnula(DataSource datasource, RequerimientoNoProgramadoBean bean)throws DataAccessException {
		new SqlMapClientTemplate(datasource, getSqlMapClientTemplate().getSqlMapClient()).
		update("requerimientonoprogramado.updateAnula", bean);
	}

	@Override
	public void updateEnvio(DataSource datasource,
			RequerimientoNoProgramadoBean bean) throws DataAccessException {
		new SqlMapClientTemplate(datasource, getSqlMapClientTemplate().getSqlMapClient()).
		update("requerimientonoprogramado.updateEnvio", bean);
		
	}

	@Override
	public void updateFile(DataSource datasource,
			RequerimientoNoProgramadoBean bean) throws DataAccessException {
		new SqlMapClientTemplate(datasource, getSqlMapClientTemplate().getSqlMapClient()).
		update("requerimientonoprogramado.updateFile", bean);
		
	}
	
	@Override
	public void envioMailUct(String cod_rqnp, String cod_bien) throws DataAccessException {
		Map map_parm = new HashMap();
		map_parm.put("cod_rqnp", cod_rqnp);
		map_parm.put("cod_bien", cod_bien);
		getSqlMapClientTemplate().queryForObject(
				"requerimientonoprogramado.rqnp_mail_uct", map_parm);
	}
	
	
	@Override
	public void envioMailRechazo(String cod_rqnp, String cod_bien, String cod_bandeja) throws DataAccessException {
		Map map_parm = new HashMap();
		map_parm.put("cod_rqnp", cod_rqnp);
		map_parm.put("cod_bien", cod_bien);
		map_parm.put("cod_bandeja", cod_bandeja);
		getSqlMapClientTemplate().queryForObject(
				"requerimientonoprogramado.rqnp_mail_rechazo", map_parm);
	}
	
	
	@Override
	public void envioMailDerivar(String cod_rqnp, String cod_bien) throws DataAccessException {
		Map map_parm = new HashMap();
		map_parm.put("cod_rqnp", cod_rqnp);
		map_parm.put("cod_bien", cod_bien);
		getSqlMapClientTemplate().queryForObject(
				"requerimientonoprogramado.rqnp_mail_derivar", map_parm);
	}
//INICIO: DPORRASC
	//SE UTILIZA PARA INSERTAR LAS METAS SELECCIONADAS
	@Override
	public void insertarAUC(DataSource datasource,
			RequerimientoNoProgramadoBean bean) throws DataAccessException {
		new SqlMapClientTemplate(datasource, getSqlMapClientTemplate().getSqlMapClient()).
        insert("requerimientonoprogramado.insertarAUC", bean);
	}

	//INICIO: DPORRASC PARA DETERMINAR EL TIPO DE AUC
	@Override
	public String obtenerTipoAUC(String cod_dep) throws DataAccessException {
		Map map_parm = new HashMap();
		map_parm.put("cod_dep", cod_dep);
		return (String)  getSqlMapClientTemplate().queryForObject("requerimientonoprogramado.tipoAuc", map_parm);
	}
	
	//DPORRASC - RUTA 04 - INICIO 
	@Override
	public String validaUserAuAUC(String cod_empl)
			throws DataAccessException {
		Map map_parm = new HashMap();
		map_parm.put("cod_empl", cod_empl);
		return (String) getSqlMapClientTemplate().queryForObject(
				"requerimientonoprogramado.rqnp_valida_user_au_auc", map_parm);
	}
	
	//DPORRAS - RUTA 04 - FIN
	/**
	 * Metodo que permite obtener la AUC que atendera un requerimiento a partir del codigo de requerimiento
	 * @param codRqnp: Codigo del requerimiento no programado
	 */
	@Override
	public String obtenerAucOfRqnp(String codRqnp) throws DataAccessException {
		Map map_parm = new HashMap();
		map_parm.put("codRqnp", codRqnp);
		return (String) getSqlMapClientTemplate().queryForObject(
				"requerimientonoprogramado.obtenerAucOfRqnp", map_parm);
	}
	
}
