package pe.gob.sunat.administracion2.siga.rqnp.model.dao.ibatis;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import pe.gob.sunat.administracion2.siga.rqnp.model.dao.MetasProyectosDao;
import pe.gob.sunat.framework.spring.util.dao.SqlMapDAOBase;

public class SqlMapMetasProyectosDaoImpl extends  SqlMapDAOBase implements MetasProyectosDao{

	@Override
	public Collection listarMetasProyectos(Map<String, Object> params)
			throws DataAccessException {

		//String tipo;
//		String parmt;
//		String anio_ejec;
//		String cod_plaza;
//		String cod_dep;
//		
//		//tipo = (String) params.get("tipo");
//		parmt = (String) params.get("parm"); //SIN VALOR
//		anio_ejec = (String) params.get("anio_ejec");
//		cod_plaza = (String) params.get("cod_plaza"); //SIN VALOR
//		cod_dep = (String) params.get("cod_dep");
//		Map map_rqnp = new HashMap();
//		map_rqnp.put("anio_ejec", anio_ejec);
//		map_rqnp.put("cod_plaza", cod_plaza);
//		map_rqnp.put("cod_dep", cod_dep);
		
//		if(tipo.equals("C")) {
//			//map_rqnp.put("cod_bien", "'%"+parm+"'");
//			map_rqnp.put("cod_bien", "%"+parmt+"%");
//			
//		}else{
//			//map_rqnp.put("des_bien","'%"+ parm+"'");
//			map_rqnp.put("des_bien",'%'+ parmt+"%");
//		}
		
		return  getSqlMapClientTemplate().queryForList("metasproyectos.selectMetasProyectos", params);
	}

	@Override
	public Collection listarMetasProductos(Map<String, Object> params)
			throws DataAccessException {
//		//String tipo;
//		String parmt;
//		String anio_ejec;
//		String cod_plaza;
//		String cod_dep;
//		
//		//tipo = (String) params.get("tipo");
//		parmt = (String) params.get("parm");
//		anio_ejec = (String) params.get("anio_ejec");
//		cod_plaza = (String) params.get("cod_plaza");
//		cod_dep = (String) params.get("cod_dep");
//		Map map_rqnp = new HashMap();
//		map_rqnp.put("anio_ejec", anio_ejec);
//		map_rqnp.put("cod_plaza", cod_plaza);
//		map_rqnp.put("cod_dep", cod_dep);
		
		return  getSqlMapClientTemplate().queryForList("metasproyectos.selectMetasProductos", params);
	}

	@Override
	public Collection listarMetasAcciones(Map<String, Object> params)
			throws DataAccessException {
		//String tipo;
//		String parmt;
//		String anio_ejec;
//		String cod_plaza;
//		String cod_dep;
//		
//		//tipo = (String) params.get("tipo");
//		parmt = (String) params.get("parm");
//		anio_ejec = (String) params.get("anio_ejec");
//		cod_plaza = (String) params.get("cod_plaza");
//		cod_dep = (String) params.get("cod_dep");
//		Map map_rqnp = new HashMap();
//		map_rqnp.put("anio_ejec", anio_ejec);
//		map_rqnp.put("cod_plaza", cod_plaza);
//		map_rqnp.put("cod_dep", cod_dep);
		
		return  getSqlMapClientTemplate().queryForList("metasproyectos.selectMetasAcciones", params);
	}

	@Override
	public Collection listarProyProdAccion(Map<String, Object> params)
			throws DataAccessException {
		return  getSqlMapClientTemplate().queryForList("metasproyectos.selectMetasProyectos", params);
	}
}
