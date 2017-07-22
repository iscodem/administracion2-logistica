package pe.gob.sunat.recurso2.administracion.siga.parametro.model.dao.ibatis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import pe.gob.sunat.recurso2.administracion.siga.registro.util.ParametroConstantes;
import pe.gob.sunat.framework.spring.util.dao.SqlMapDAOBase;
import pe.gob.sunat.recurso2.administracion.siga.parametro.model.bean.SysParametrosBean;
import pe.gob.sunat.recurso2.administracion.siga.parametro.model.dao.SysParametrosDAO;

/**
 * Implementacion de la interface SysParametrosDAO.
 * Mantenimiento de sys Parametro SIGA(SYS_PARAMETROS)
 * @author emarchena.
 */
public class SqlMapSysParametrosDAOImpl extends SqlMapDAOBase implements SysParametrosDAO {

	@Override
	public String permiteExcesoRqnp()
			throws DataAccessException {
		String rtn= ParametroConstantes.LETRA_N;//"N";
				
		Map map = new HashMap();
		map.put("nom_var", "vgs_excedercd");
		
		SysParametrosBean parametro= new SysParametrosBean();
		List lista = new ArrayList<SysParametrosBean>();
		
		lista =getSqlMapClientTemplate().queryForList("sysparametros.rqnp_permite_exceso", map);;
		if (lista != null){
			parametro=(SysParametrosBean)lista.get(0);
			rtn = parametro.getVal_char_par();
		}
		return rtn;
	}
	
	@Override
	public String activarFlujoAuc()
			throws DataAccessException {
		String rtn= ParametroConstantes.CIFRA_0;//"0";
		
		Map map = new HashMap();
		map.put("nom_var", "ind_ate_sol_cat");
		
		SysParametrosBean parametro= new SysParametrosBean();
		List lista = new ArrayList<SysParametrosBean>();
		
		lista =getSqlMapClientTemplate().queryForList("sysparametros.activar_flujo_auc", map);;
		if (lista != null){
			parametro=(SysParametrosBean)lista.get(0);
			rtn = parametro.getVal_char_par();
		}
		return rtn;
	}
	
	//PAS201780000300007
	@Override
	public Long obtieneUitAnio(Map<String, Object> parm) throws DataAccessException {
		Long rtn = (long) 0;
		
		SysParametrosBean parametro= new SysParametrosBean();
		List lista = new ArrayList<SysParametrosBean>();
		
		lista =getSqlMapClientTemplate().queryForList("sysparametros.rqnp_permite_exceso", parm);;
		if (lista != null){
			parametro=(SysParametrosBean)lista.get(0);
			rtn = parametro.getVal_num_par();
		}
		return rtn;
	}
	
	//PAS201780000300007
	@Override
	public String obtenerValUitAnio(String anioEjec) throws DataAccessException {
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("nom_var", "vgn_uit");
		map.put("peri_anno", anioEjec);
		
		return (String) getSqlMapClientTemplate().queryForObject(
				"sysparametros.obtenerValUitAnio", map);
	}
	
	@Override
	public String obtenerNumUitAnio(String anioEjec) throws DataAccessException {
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("nom_var", "vgn_uit_cmp_dir");
		map.put("peri_anno", anioEjec);
		
		return (String) getSqlMapClientTemplate().queryForObject(
				"sysparametros.obtenerNumUitAnio", map);
	}
	
}
