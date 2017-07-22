package pe.gob.sunat.recurso2.administracion.siga.registro.model.dao.ibatis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;



import pe.gob.sunat.framework.spring.util.dao.SqlMapDAOBase;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.MaestroPersonalBean;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.dao.MaestroPersonalDAO;
import pe.gob.sunat.recurso2.administracion.siga.registro.util.RegistroConstantes;

/**
 * Implementacion de la interface MaestroPersonalDAO.
 * Mantenimiento de Masetro de Personal(MAESTRO_PERSONAL)
 * @author emarchena.
 */
public class SqlMapMaestroPersonalDAOImpl extends SqlMapDAOBase implements MaestroPersonalDAO{

	@Override
	public MaestroPersonalBean obtenerPersonaxUsuario(String cod_user)
			throws DataAccessException {
		Map map_vfg = new HashMap();
		map_vfg.put("cod_use", cod_user);
		return (MaestroPersonalBean) getSqlMapClientTemplate().queryForObject(
				"maestropersonal.selectUser", map_vfg);
	}

	
	@Override
	public MaestroPersonalBean obtenerPersonaxCodigo(String cod_empl)
			throws DataAccessException {
		// TODO Auto-generated method stub
		Map map_vfg = new HashMap();
		map_vfg.put("cod_empl", cod_empl);
		return (MaestroPersonalBean) getSqlMapClientTemplate().queryForObject(
				"maestropersonal.selectEmpleado", map_vfg);
	}

	
	public MaestroPersonalBean obtenerPersonaxRegistro(String cod_reg)
	throws DataAccessException{
		Map map_vfg = new HashMap();
		map_vfg.put("cod_reg", cod_reg);
		return (MaestroPersonalBean) getSqlMapClientTemplate().queryForObject(
				"maestropersonal.selectRegistro", map_vfg);
	}
	
	public String  obtenerCorreoEmpleado(Map<String,Object> parm)
			throws DataAccessException{
		return (String) getSqlMapClientTemplate().queryForObject(
				"maestropersonal.selectCorreoEmpleado", parm);
	}
	
	
	public String obtenerRegistroPersonal(String cod_user)
	throws DataAccessException{
		Map map_vfg = new HashMap();
		map_vfg.put("cod_reg", cod_user);
		return (String) getSqlMapClientTemplate().queryForObject("maestropersonal.selectRegPersonal", map_vfg);
	}
	
	
	
	@Override
	public String obtenerJefeInmediato(String cod_empl)
			throws DataAccessException {
		Map map_parm = new HashMap();
		log.debug(cod_empl);
		map_parm.put("cod_empl", cod_empl);	
		return (String) getSqlMapClientTemplate().queryForObject(
				"maestropersonal.rqnp_jefe_inmediato", map_parm);
		
	}

	
	
	@Override
	public String obtenerIntendente(String cod_empl) throws DataAccessException {
		Map map_parm = new HashMap();
		map_parm.put("cod_empl", cod_empl);		
		return (String) getSqlMapClientTemplate().queryForObject(
				"maestropersonal.rqnp_jefe_intendencia", map_parm);
	}
	
	@Override
	public String obtenerAprobador(String cod_empl) throws DataAccessException {
		Map map_parm = new HashMap();
		map_parm.put("cod_empl", cod_empl);		
		return (String) getSqlMapClientTemplate().queryForObject(
				"maestropersonal.rqnp_jefe_aprobador", map_parm);
	}
	
	@Override
	public String obtenerAprobadorAUC(String cod_auc) throws DataAccessException {
		Map map_parm = new HashMap();
		map_parm.put("cod_auc", cod_auc);		
		return (String) getSqlMapClientTemplate().queryForObject(
				"maestropersonal.rqnp_jefe_aprobador_auc", map_parm);
	}
	
	public String esIntendente(String cod_empl) throws DataAccessException {
		Map map_parm = new HashMap();
		map_parm.put("cod_empl", cod_empl);
		return (String) getSqlMapClientTemplate().queryForObject(
				"maestropersonal.rqnp_es_intendencia", map_parm);
	}
	
	public String esAprobador(String cod_empl) throws DataAccessException {
		Map map_parm = new HashMap();
		map_parm.put("cod_empl", cod_empl);
		return (String) getSqlMapClientTemplate().queryForObject(
				"maestropersonal.rqnp_es_aprobador", map_parm);
	}

	@Override
	public String obtenerSuperIntendente(String cod_empl)
			throws DataAccessException {
		Map map_parm = new HashMap();
		map_parm.put("cod_empl", cod_empl);
		log.debug("cod_empl:"+cod_empl);
		
		return (String) getSqlMapClientTemplate().queryForObject(
				"maestropersonal.rqnp_jefe_super_intendencia", map_parm);
	}
	

	@Override
	public String esJefeUBG(String cod_empl) throws DataAccessException {
		Map map_parm = new HashMap();
		map_parm.put("cod_empl", cod_empl);
		log.debug("cod_empl:"+cod_empl);
		
		return (String) getSqlMapClientTemplate().queryForObject(
				"maestropersonal.rqnp_jefe_UBG", map_parm);
	}


	@Override
	public Collection<MaestroPersonalBean> listarMaestroPersonal(Map parm)
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
		
		return  getSqlMapClientTemplate().queryForList("maestropersonal.selectConsulta", map_rqnp);
	}

	
	@Override
	public Collection<MaestroPersonalBean> buscarMaestroPersonal(Map parm)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return  getSqlMapClientTemplate().queryForList("maestropersonal.selectConsulta", parm);
	}
	

	@Override
	public Collection<MaestroPersonalBean> buscarMaestroPersonalInactivo(Map parm)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return  getSqlMapClientTemplate().queryForList("maestropersonal.selectConsultaInactivo", parm);
	}
	
	
	

	@Override
	public Collection<MaestroPersonalBean> buscarMaestroPersonalMovilidad(Map parm)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return  getSqlMapClientTemplate().queryForList("maestropersonal.selectConsultaMovilidad", parm);
	}

	@Override
	public Collection<MaestroPersonalBean> buscarMaestroPersonalViaticos(Map parm)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return  getSqlMapClientTemplate().queryForList("maestropersonal.selectConsultaViaticos", parm);
	}
	
	@Override
	public String obtenerJefeInmediatoEncargado(String cod_empl,String  cod_encargado)
			throws DataAccessException {
		Map map_parm = new HashMap();
		log.debug(cod_empl);
		map_parm.put("cod_empl", cod_empl);
		map_parm.put("cod_encargado", cod_encargado);	
		return (String) getSqlMapClientTemplate().queryForObject(
				"maestropersonal.rqnp_jefe_inmediato_encargado", map_parm);
	}
	
	@Override
	public String obtenerAprobadorEncargado(String cod_empl, String cod_encargado) throws DataAccessException {
		Map map_parm = new HashMap();
		map_parm.put("cod_empl", cod_empl);	
		map_parm.put("cod_encargado", cod_encargado);
		return (String) getSqlMapClientTemplate().queryForObject(
				"maestropersonal.rqnp_jefe_aprobador_encargado", map_parm);
	}
	
	@Override
	public String obtenerAprobadorEncargadoAUC(String cod_auc, String cod_encargado) throws DataAccessException {
		Map map_parm = new HashMap();
		map_parm.put("cod_auc", cod_auc);	
		map_parm.put("cod_encargado", cod_encargado);
		return (String) getSqlMapClientTemplate().queryForObject(
				"maestropersonal.rqnp_jefe_aprobador_encargado_auc", map_parm);
	}
	
	public String esAprobadorEncargado(String cod_empl) throws DataAccessException {
		Map map_parm = new HashMap();
		map_parm.put("cod_empl", cod_empl);
		return (String) getSqlMapClientTemplate().queryForObject(
				"maestropersonal.rqnp_es_aprobador_encargado", map_parm);
	}
	
	
	////////////////////////////////////////////////////////////////////////
	@Override
	public String esJefe(String cod_empl,String cod_dep) throws DataAccessException {
		Map map_parm = new HashMap();
		map_parm.put("cod_empl", cod_empl);
		map_parm.put("cod_dep", cod_dep);

		return  (String) getSqlMapClientTemplate().queryForObject(
				"maestropersonal.rqnp_es_jefe", map_parm);
	}
	
	@Override
	public String obtenerJefeAuc(String cod_dep)
			throws DataAccessException {
		Map map_parm = new HashMap();
		log.debug(cod_dep);
		map_parm.put("cod_dep", cod_dep);	
		return (String) getSqlMapClientTemplate().queryForObject(
				"maestropersonal.rqnp_jefe_auc", map_parm);
		
	}
	
	@Override
	public String esJefeInmediatoEncargado(String cod_empl, String cod_enca) throws DataAccessException {
		Map map_parm = new HashMap();
		map_parm.put("cod_empl", cod_empl);
		map_parm.put("cod_encargado", cod_enca);
		return  (String) getSqlMapClientTemplate().queryForObject(
				"maestropersonal.rqnp_jefe_inmediato_encargado", map_parm);
	}
	
	
	@Override
	public String verificaEncargo(String cod_empl, String cod_enca)
			throws DataAccessException {
		Map map_parm = new HashMap();
		map_parm.put("cod_empl", cod_empl);	
		map_parm.put("cod_encargado", cod_enca);
		String rpta;
		String result=(String) getSqlMapClientTemplate().queryForObject(
				"maestropersonal.rqnp_verifica_encargo", map_parm);
		if(result!=null){
			rpta= RegistroConstantes.LETRA_S;//"S";
		}else{
			rpta= RegistroConstantes.LETRA_N;//"N";
		}
		return rpta;
		
	}
	
	@SuppressWarnings("unchecked")
	public MaestroPersonalBean obtenerCategoriaGasto(String codigoEmpleado) throws DataAccessException {
		MaestroPersonalBean maestroPersonalBean = null;
		Map<String, Object> parmSearch = new HashMap<String, Object>();
		parmSearch.put("codigoEmpleado", codigoEmpleado);
		ArrayList<MaestroPersonalBean> maestroPersonalList = (ArrayList<MaestroPersonalBean>) getSqlMapClientTemplate().queryForList("maestropersonal.obtenerCategoriaGasto", parmSearch);
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
		return (String) getSqlMapClientTemplate().queryForObject("maestropersonal.rqnp_es_jefe_inmediato", map_parm);
	}


	@Override
	public String determinaAutorizadorGastoMovilidad(String codigoEmpleado) throws DataAccessException {
		Map map_parm = new HashMap();
		log.debug(codigoEmpleado);
		map_parm.put("codigoEmpleado", codigoEmpleado);	
		return (String) getSqlMapClientTemplate().queryForObject("maestropersonal.rqnp_es_autorizador_gasto", map_parm);
	}
	
	////////////////////////////////////////////////////////////////////////
	@Override
	@SuppressWarnings("unchecked")
	public List<MaestroPersonalBean> buscarMaestroPersonalComisionadoInactivo(Map<String, Object> parm) throws DataAccessException {
 
		return  getSqlMapClientTemplate().queryForList("maestropersonal.selectConsultaComisionadoInactivo", parm);
	}


	@Override
	public String buscarNombreAutorizador(String codigoEmpleado)
			throws Exception {		
		Map map_parm = new HashMap();
		log.debug(codigoEmpleado);
		map_parm.put("codigoEmpleado", codigoEmpleado);	
		return (String) getSqlMapClientTemplate().queryForObject("maestropersonal.nombreAutorizador", map_parm);
	}


	@Override
	public String determinaAutorizadorGastoViatico(String codigoEmpleado)
			throws DataAccessException {
		Map map_parm = new HashMap();
		log.debug(codigoEmpleado);
		map_parm.put("codigoEmpleado", codigoEmpleado);	
		return (String) getSqlMapClientTemplate().queryForObject("maestropersonal.rqnp_es_autorizador_gasto_viatico", map_parm);
	}

	//PAS201780000300007
	@Override
	public boolean esPerfilColaboradorJefe(String codDependencia,String codEmpleado) throws DataAccessException {
		Map mapParmetros = new HashMap();
		boolean rpta=false;
		mapParmetros.put("codEmpleado", codEmpleado);
		mapParmetros.put("codDependencia", codDependencia);
		MaestroPersonalBean colaborador=(MaestroPersonalBean) getSqlMapClientTemplate().queryForObject(
				"maestropersonal.esPerfilColaboradorJefe", mapParmetros);
		if(colaborador!=null){
			rpta=true;
		}
		return rpta;
	}
	
	@Override
	public boolean esPerfilColaboradorEncargado(String codDependencia,String codEmpleado) throws DataAccessException {
		Map mapParmetros = new HashMap();
		boolean rpta=false;
		mapParmetros.put("codEmpleado", codEmpleado);
		mapParmetros.put("codDependencia", codDependencia);
		MaestroPersonalBean colaborador= (MaestroPersonalBean) getSqlMapClientTemplate().queryForObject(
				"maestropersonal.esPerfilColaboradorEncargado", mapParmetros);
		if(colaborador!=null){
			rpta=true;
		}
		return rpta;
	}
	
	@Override
	public boolean esEncargadoOtraUuoo(String codEmpleado) throws DataAccessException {
		Map mapParmetros = new HashMap();
		boolean rpta = false;
		mapParmetros.put("codEmpleado", codEmpleado);
		MaestroPersonalBean colaborador = (MaestroPersonalBean)getSqlMapClientTemplate().queryForObject(
		"maestropersonal.esEncargadoOtraUuoo", mapParmetros);
		if (colaborador != null) {
			rpta = true;
		}
		return rpta;
	}
	
	@Override
	public boolean esEncargadoAuc(String codEmpleado) throws DataAccessException {
		Map mapParmetros = new HashMap();
		boolean rpta = false;
		mapParmetros.put("codEmpleado", codEmpleado);
		MaestroPersonalBean colaborador = (MaestroPersonalBean)getSqlMapClientTemplate().queryForObject(
		"maestropersonal.esEncargadoAuc", mapParmetros);
		if (colaborador != null) {
			rpta = true;
		}
		return rpta;
	}
	
	@Override
	public String obtenerCadenaUuoosQueEsJefe(String codEmpleadoJefe) throws DataAccessException {
		Map map_parm = new HashMap();
		map_parm.put("codEmpleadoJefe", codEmpleadoJefe);
		return  (String) getSqlMapClientTemplate().queryForObject(
				"maestropersonal.obtenerCadenaUuoosQueEsJefe", map_parm);
	}
}
