package pe.gob.sunat.recurso2.administracion.siga.registro.model.dao.ibatis;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import pe.gob.sunat.framework.spring.util.dao.SqlMapDAOBase;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.DependenciaBean;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.dao.DependenciaDAO;

/**
 * Implementacion de la interface DependenciaDAO.
 * Mantenimiento dependencias SUNAT(TDEPENDENCIAS)
 * @author emarchena.
 */
public class SqlMapDependenciaDAOImpl  extends SqlMapDAOBase  implements DependenciaDAO{

	
	
	@Override
	public Collection<DependenciaBean> searchDependencias(Map parmSearch )
			throws DataAccessException {
		return (Collection<DependenciaBean>)getSqlMapClientTemplate().queryForList("tdependencias.selectAll", parmSearch);
	}
	
	
	@Override
	public Collection<DependenciaBean> searchDependenciasMovilidad(Map parmSearch )
			throws DataAccessException {
		return (Collection<DependenciaBean>)getSqlMapClientTemplate().queryForList("tdependencias.selectAllMovilidad", parmSearch);
	}
	
	@Override
	public DependenciaBean obtenerDependencia(String cod_dep)
			throws DataAccessException {
		//variable = USER
		Map map_rqnp = new HashMap();
		map_rqnp.put("cod_dep", cod_dep);
		map_rqnp.put("estado_id", "1"); //DPORRASC
		return (DependenciaBean)getSqlMapClientTemplate().queryForObject("tdependencias.selectAll", map_rqnp);
	}

	@Override
	public Collection<DependenciaBean> obtenerJefeDependencia(String cod_dep,
			String cod_per) throws DataAccessException {
		Map map_rqnp = new HashMap();
		map_rqnp.put("cod_dep", cod_dep);
		map_rqnp.put("cod_per", cod_per);
		map_rqnp.put("estado_id", "1");//DPORRASC
		return  getSqlMapClientTemplate().queryForList( "tdependencias.selectAll", map_rqnp);
	}

	@Override
	public DependenciaBean obtenerDependenciaXuuoo(String num_uuoo)
			throws DataAccessException {
		Map map_rqnp = new HashMap();
		map_rqnp.put("num_uuoo", num_uuoo);
	map_rqnp.put("estado_id", "1");//DPORRASC
		return (DependenciaBean)getSqlMapClientTemplate().queryForObject("tdependencias.selectAll", map_rqnp);
	}

	@Override
	public String obtenerTipoAUC(String cod_dep) throws DataAccessException {
		Map map_parm = new HashMap();
		map_parm.put("cod_dep", cod_dep);
		return (String) getSqlMapClientTemplate().queryForObject("tdependencias.tipoAuc", map_parm);
	}

	@Override
	public DependenciaBean obtenerDependenciaXcod(String cod_dep)
			throws DataAccessException {
		Map map_rqnp = new HashMap();
		map_rqnp.put("cod_dep", cod_dep);
		map_rqnp.put("estado_id", "1");//DPORRASC
		return (DependenciaBean)getSqlMapClientTemplate().queryForObject("tdependencias.selectAll", map_rqnp);
	}
	
	/////////////////////////////////////////////////////////
	@Override
	public String obtenerUuooNoSupervision(String cod_dep, String long_uuoo)
			throws DataAccessException {
		Map map_parm = new HashMap();
		map_parm.put("cod_dep", cod_dep);
		map_parm.put("long_uuoo", long_uuoo);
		return (String) getSqlMapClientTemplate().queryForObject(
				"tdependencias.rqnp_uuoo_no_supervision", map_parm);
	}
	/////////////////////////////////////////////////////////
	
	public Collection<DependenciaBean> searchDependenciasViatico(Map parmSearch ) throws DataAccessException {
		/*Viaticos*/
		return (Collection<DependenciaBean>)getSqlMapClientTemplate().queryForList("tdependencias.selectAllViatico", parmSearch);
	}
	
	@Override
	public Collection<DependenciaBean> buscarIntendenciaByUnidadOrganizacional (String num_uuoo) throws DataAccessException{
		Map<String, String> parmSearch = new HashMap<String, String>();
		parmSearch.put("num_uuoo", num_uuoo);
		return (Collection<DependenciaBean>)getSqlMapClientTemplate().queryForList("tdependencias.buscarIntendenciaByUnidadOrganizacional", parmSearch);
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public 	List<DependenciaBean> obtenerAllViaticoIndividual (Map<String, Object> parmSearch) throws DataAccessException{
		return (List<DependenciaBean>)getSqlMapClientTemplate().queryForList("tdependencias.selectAllViaticoIndividual", parmSearch);
	}
	
	//PAS201780000300007
	@SuppressWarnings("deprecation")
	@Override
	public DependenciaBean obtenerUuoo(Map<String, Object> params) throws DataAccessException {
		return (DependenciaBean)getSqlMapClientTemplate().queryForObject("tdependencias.obtenerUuoo", params);
	}
	
	//PAS201780000300007
	@Override
	public DependenciaBean obtenerDependenciaRqnp(String codRqnp) throws DataAccessException {
		Map map_rqnp = new HashMap();
		map_rqnp.put("codRqnp", codRqnp);
		return (DependenciaBean)getSqlMapClientTemplate().queryForObject("tdependencias.obtenerDependenciaRqnp", map_rqnp);
	}
	
	//PAS201780000300007
	@Override
	 public DependenciaBean obtenerDependenciaOECdeRqnp(String codRqnp) throws DataAccessException {
		Map map_rqnp = new HashMap();
		map_rqnp.put("codRqnp", codRqnp);
		return (DependenciaBean)getSqlMapClientTemplate().queryForObject("tdependencias.obtenerDependenciaOECdeRqnp", map_rqnp);
	}
	
	//PAS201780000300007
	@Override
	public DependenciaBean obtenerDependenciaAUCdeRqnp(String codRqnp) throws DataAccessException {
		Map map_rqnp = new HashMap();
		map_rqnp.put("codRqnp", codRqnp);
		return (DependenciaBean)getSqlMapClientTemplate().queryForObject("tdependencias.obtenerDependenciaAUCdeRqnp", map_rqnp);
	}
	
	//PAS201780000300007
	@Override
	public boolean esPerfilDependenciaAprobador(String codDependencia) throws DataAccessException {
		Map mapParmetros = new HashMap();
		boolean rpta=false;
		mapParmetros.put("codDependencia", codDependencia);
		DependenciaBean dependencia=(DependenciaBean) getSqlMapClientTemplate().queryForObject(
				"tdependencias.esPerfilDependenciaAprobador", mapParmetros);
		if(dependencia!=null){
			rpta=true;
		}
		return rpta;
	}
	
	//PAS201780000300007
	@Override
	public boolean esPerfilDependenciaAUC(String codDependencia) throws DataAccessException {
		Map mapParmetros = new HashMap();
		boolean rpta=false;
		mapParmetros.put("codDependencia", codDependencia);
		DependenciaBean dependencia= (DependenciaBean) getSqlMapClientTemplate().queryForObject(
				"tdependencias.esPerfilDependenciaAUC", mapParmetros);
		if(dependencia!=null){
			rpta=true;
		}
		return rpta;
	}
	
	//PAS201780000300007
	@Override
	public Collection<DependenciaBean> obtenerJefaturasAucJson(Map<String, Object> params) throws DataAccessException {
		return  getSqlMapClientTemplate().queryForList( "tdependencias.obtenerJefaturasAucJson", params);
	}
	
	//PAS201780000300007
	@Override
	public String obtenerNumJefaturasEmpleado(String codEmpleado) throws DataAccessException {
		Map map_parm = new HashMap();
		map_parm.put("codEmpleado", codEmpleado);
		return (String) getSqlMapClientTemplate().queryForObject( "tdependencias.obtenerNumJefaturasEmpleado", map_parm);
	}
}
