package pe.gob.sunat.recurso2.administracion.siga.firma.model.dao.ibatis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import pe.gob.sunat.recurso2.administracion.siga.firma.model.dao.MaestroPersonalDao;
import pe.gob.sunat.recurso2.administracion.siga.firma.model.bean.MaestroPersonal;
import pe.gob.sunat.framework.spring.util.dao.SqlMapDAOBase;


public class SqlMapMaestroPersonalDaoImpl extends SqlMapDAOBase implements MaestroPersonalDao{

	@Override
	public MaestroPersonal obtenerPersonaxUsuario(String cod_user)
			throws DataAccessException {
		Map map_vfg = new HashMap();
		map_vfg.put("cod_use", cod_user);
		return (MaestroPersonal) getSqlMapClientTemplate().queryForObject(
				"maestropersonalfirma.selectUser", map_vfg);
	}

	
	@Override
	public MaestroPersonal obtenerPersonaxCodigo(String cod_empl)
			throws DataAccessException {
		// TODO Auto-generated method stub
		Map map_vfg = new HashMap();
		map_vfg.put("cod_empl", cod_empl);
		return (MaestroPersonal) getSqlMapClientTemplate().queryForObject(
				"maestropersonalfirma.selectEmpleado", map_vfg);
	}

	
	public MaestroPersonal obtenerPersonaxRegistro(String cod_reg)
	throws DataAccessException{
		Map map_vfg = new HashMap();
		map_vfg.put("cod_reg", cod_reg);
		return (MaestroPersonal) getSqlMapClientTemplate().queryForObject(
				"maestropersonalfirma.selectRegistro", map_vfg);
	}
	
	public String obtenerRegistroPersonal(String cod_user)
	throws DataAccessException{
		Map map_vfg = new HashMap();
		map_vfg.put("cod_reg", cod_user);
		return (String) getSqlMapClientTemplate().queryForObject("maestropersonalfirma.selectRegPersonal", map_vfg);
	}
	
	
	
	@Override
	public String obtenerJefeInmediato(String cod_empl)
			throws DataAccessException {
		Map map_parm = new HashMap();
		log.debug(cod_empl);
		map_parm.put("cod_empl", cod_empl);	
		return (String) getSqlMapClientTemplate().queryForObject(
				"maestropersonalfirma.rqnp_jefe_inmediato", map_parm);
		
	}

	
	
	@Override
	public String obtenerIntendente(String cod_empl) throws DataAccessException {
		Map map_parm = new HashMap();
		map_parm.put("cod_empl", cod_empl);		
		return (String) getSqlMapClientTemplate().queryForObject(
				"maestropersonalfirma.rqnp_jefe_intendencia", map_parm);
	}
	
	@Override
	public String obtenerAprobador(String cod_empl) throws DataAccessException {
		Map map_parm = new HashMap();
		map_parm.put("cod_empl", cod_empl);		
		return (String) getSqlMapClientTemplate().queryForObject(
				"maestropersonalfirma.rqnp_jefe_aprobador", map_parm);
	}
	
	@Override
	public String obtenerAprobadorAUC(String cod_auc) throws DataAccessException {
		Map map_parm = new HashMap();
		map_parm.put("cod_auc", cod_auc);		
		return (String) getSqlMapClientTemplate().queryForObject(
				"maestropersonalfirma.rqnp_jefe_aprobador_auc", map_parm);
	}
	
	public String esIntendente(String cod_empl) throws DataAccessException {
		Map map_parm = new HashMap();
		map_parm.put("cod_empl", cod_empl);
		return (String) getSqlMapClientTemplate().queryForObject(
				"maestropersonalfirma.rqnp_es_intendencia", map_parm);
	}
	
	public String esAprobador(String cod_empl) throws DataAccessException {
		Map map_parm = new HashMap();
		map_parm.put("cod_empl", cod_empl);
		return (String) getSqlMapClientTemplate().queryForObject(
				"maestropersonalfirma.rqnp_es_aprobador", map_parm);
	}

	@Override
	public String obtenerSuperIntendente(String cod_empl)
			throws DataAccessException {
		Map map_parm = new HashMap();
		map_parm.put("cod_empl", cod_empl);
		log.debug("cod_empl:"+cod_empl);
		
		return (String) getSqlMapClientTemplate().queryForObject(
				"maestropersonalfirma.rqnp_jefe_super_intendencia", map_parm);
	}
	

	@Override
	public String esJefeUBG(String cod_empl) throws DataAccessException {
		Map map_parm = new HashMap();
		map_parm.put("cod_empl", cod_empl);
		log.debug("cod_empl:"+cod_empl);
		
		return (String) getSqlMapClientTemplate().queryForObject(
				"maestropersonalfirma.rqnp_jefe_UBG", map_parm);
	}


	@Override
	public Collection<MaestroPersonal> listarMaestroPersonal(Map parm)
			throws DataAccessException {
		// TODO Auto-generated method stub

		String tipo;
		String parmt;
		
		
		String cod_dep;
		
		tipo = (String) parm.get("per_tipo");
		parmt = (String) parm.get("per_parm");
		
		cod_dep = (String) parm.get("cod_dep");
		Map map_rqnp = new HashMap();
		
		map_rqnp.put("cod_dep", cod_dep);
		
		if(tipo.equals("C")) {
			map_rqnp.put("cod_reg", "%"+parmt+"%");
			
		}if(tipo.equals("U")) {
			map_rqnp.put("uuoo",'%'+ parmt+"%");
		}else{
			map_rqnp.put("nom_per",'%'+ parmt+"%");
		}
		
		return  getSqlMapClientTemplate().queryForList("maestropersonalfirma.selectConsulta", map_rqnp);
	}

	
	@Override
	public Collection<MaestroPersonal> buscarMaestroPersonal(Map parm)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return  getSqlMapClientTemplate().queryForList("maestropersonalfirma.selectConsulta", parm);
	}
	

	@Override
	public Collection<MaestroPersonal> buscarMaestroPersonalInactivo(Map parm)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return  getSqlMapClientTemplate().queryForList("maestropersonalfirma.selectConsultaInactivo", parm);
	}
	
	
	

	@Override
	public Collection<MaestroPersonal> buscarMaestroPersonalMovilidad(Map parm)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return  getSqlMapClientTemplate().queryForList("maestropersonalfirma.selectConsultaMovilidad", parm);
	}

	@Override
	public String obtenerJefeInmediatoEncargado(String cod_empl,String  cod_encargado)
			throws DataAccessException {
		Map map_parm = new HashMap();
		log.debug(cod_empl);
		map_parm.put("cod_empl", cod_empl);
		map_parm.put("cod_encargado", cod_encargado);	
		return (String) getSqlMapClientTemplate().queryForObject(
				"maestropersonalfirma.rqnp_jefe_inmediato_encargado", map_parm);
	}
	
	@Override
	public String obtenerAprobadorEncargado(String cod_empl, String cod_encargado) throws DataAccessException {
		Map map_parm = new HashMap();
		map_parm.put("cod_empl", cod_empl);	
		map_parm.put("cod_encargado", cod_encargado);
		return (String) getSqlMapClientTemplate().queryForObject(
				"maestropersonalfirma.rqnp_jefe_aprobador_encargado", map_parm);
	}
	
	@Override
	public String obtenerAprobadorEncargadoAUC(String cod_auc, String cod_encargado) throws DataAccessException {
		Map map_parm = new HashMap();
		map_parm.put("cod_auc", cod_auc);	
		map_parm.put("cod_encargado", cod_encargado);
		return (String) getSqlMapClientTemplate().queryForObject(
				"maestropersonalfirma.rqnp_jefe_aprobador_encargado_auc", map_parm);
	}
	
	public String esAprobadorEncargado(String cod_empl) throws DataAccessException {
		Map map_parm = new HashMap();
		map_parm.put("cod_empl", cod_empl);
		return (String) getSqlMapClientTemplate().queryForObject(
				"maestropersonalfirma.rqnp_es_aprobador_encargado", map_parm);
	}
	
	
	////////////////////////////////////////////////////////////////////////
	@Override
	public String esJefe(String cod_empl,String cod_dep) throws DataAccessException {
		Map map_parm = new HashMap();
		map_parm.put("cod_empl", cod_empl);
		map_parm.put("cod_dep", cod_dep);

		return  (String) getSqlMapClientTemplate().queryForObject(
				"maestropersonalfirma.rqnp_es_jefe", map_parm);
	}
	
	@Override
	public String obtenerJefeAuc(String cod_dep)
			throws DataAccessException {
		Map map_parm = new HashMap();
		log.debug(cod_dep);
		map_parm.put("cod_dep", cod_dep);	
		return (String) getSqlMapClientTemplate().queryForObject(
				"maestropersonalfirma.rqnp_jefe_auc", map_parm);
		
	}
	
	@Override
	public String esJefeInmediatoEncargado(String cod_empl, String cod_enca) throws DataAccessException {
		Map map_parm = new HashMap();
		map_parm.put("cod_empl", cod_empl);
		map_parm.put("cod_encargado", cod_enca);
		return  (String) getSqlMapClientTemplate().queryForObject(
				"maestropersonalfirma.rqnp_jefe_inmediato_encargado", map_parm);
	}
	
	
	@Override
	public String verificaEncargo(String cod_empl, String cod_enca)
			throws DataAccessException {
		Map map_parm = new HashMap();
		map_parm.put("cod_empl", cod_empl);	
		map_parm.put("cod_encargado", cod_enca);
		String rpta;
		String result=(String) getSqlMapClientTemplate().queryForObject(
				"maestropersonalfirma.rqnp_verifica_encargo", map_parm);
		if(result!=null){
			rpta="S";
		}else{
			rpta="N";
		}
		return rpta;
		
	}
	
	@SuppressWarnings("unchecked")
	public MaestroPersonal obtenerCategoriaGasto(String codigoEmpleado) throws DataAccessException {
		MaestroPersonal maestroPersonalBean = null;
		Map<String, Object> parmSearch = new HashMap<String, Object>();
		parmSearch.put("codigoEmpleado", codigoEmpleado);
		ArrayList<MaestroPersonal> maestroPersonalList = (ArrayList<MaestroPersonal>) getSqlMapClientTemplate().queryForList("maestropersonalfirma.obtenerCategoriaGasto", parmSearch);
		if (maestroPersonalList != null && !maestroPersonalList.isEmpty()) {
			maestroPersonalBean = maestroPersonalList.get(0);
		}
		return maestroPersonalBean;
	}


	@Override
	public String determinaJefeInmediatoEncargadoMovilidad(String codigoEmpleado)
			throws DataAccessException {
		Map map_parm = new HashMap();
		log.debug(codigoEmpleado);
		map_parm.put("codigoEmpleado", codigoEmpleado);	
		return (String) getSqlMapClientTemplate().queryForObject("maestropersonalfirma.rqnp_es_jefe_inmediato", map_parm);
	}


	@Override
	public String determinaAutorizadorGastoMovilidad(String codigoEmpleado) throws DataAccessException {
		Map map_parm = new HashMap();
		log.debug(codigoEmpleado);
		map_parm.put("codigoEmpleado", codigoEmpleado);	
		return (String) getSqlMapClientTemplate().queryForObject("maestropersonalfirma.rqnp_es_autorizador_gasto", map_parm);
	}
	
	////////////////////////////////////////////////////////////////////////
	@Override
	@SuppressWarnings("unchecked")
	public List<MaestroPersonal> buscarMaestroPersonalComisionadoInactivo(Map<String, Object> parm) throws DataAccessException {
 
		return  getSqlMapClientTemplate().queryForList("maestropersonalfirma.selectConsultaComisionadoInactivo", parm);
	}


	@Override
	public String buscarNombreAutorizador(String codigoEmpleado)
			throws Exception {		
		Map map_parm = new HashMap();
		log.debug(codigoEmpleado);
		map_parm.put("codigoEmpleado", codigoEmpleado);	
		return (String) getSqlMapClientTemplate().queryForObject("maestropersonalfirma.nombreAutorizador", map_parm);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<MaestroPersonal> obtenerJefeDependencia(MaestroPersonal param) throws DataAccessException {
 
		return  getSqlMapClientTemplate().queryForList("maestropersonalfirma.obtenerJefeDependencia", param);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<MaestroPersonal> obtenerColaboradorConDependencia(MaestroPersonal param) throws DataAccessException {
 
		return  getSqlMapClientTemplate().queryForList("maestropersonalfirma.obtenerColaboradorConDependencia", param);
	}
}
