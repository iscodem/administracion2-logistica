package pe.gob.sunat.administracion2.siga.rqnp.model.dao.ibatis;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import pe.gob.sunat.administracion2.siga.rqnp.model.dao.CatalogoBienesDao;
import pe.gob.sunat.administracion2.siga.rqnp.model.domain.CatalogoBienesBean;
import pe.gob.sunat.framework.spring.util.dao.SqlMapDAOBase;

/**
 * <p>Title: SqlMapCatalogoBienesDaoImpl </p>
 * <p>Description: Clase para CONSULTAR  la tabla  CATALOGO_BIEN_SERVICIO</p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: SUNAT</p>
 * @author EMARCHENA
 * @version 1.0 
 */
public class SqlMapCatalogoBienesDaoImpl  extends SqlMapDAOBase implements CatalogoBienesDao {

	
	 /**
     * Lista los Items del Catalogo de Bienes y Servicios
     * @param String tipo - Tipo de busqueda
     * @param String parm - Parametro de Busqueda
     * @throws DataAccessException
     * @return Collection
     */  
	@Override
	public Collection listarCatalogo(Map parm)
			throws DataAccessException {

		String tipo;
		String parmt;
		String anio_ejec;
		String cod_plaza;
		String cod_dep;
		
		tipo = (String) parm.get("tipo");
		parmt = (String) parm.get("parm");
		anio_ejec = (String) parm.get("anio_ejec");
		cod_plaza = (String) parm.get("cod_plaza");
		cod_dep = (String) parm.get("cod_dep");
		Map map_rqnp = new HashMap();
		map_rqnp.put("anio_ejec", anio_ejec);
		map_rqnp.put("cod_plaza", cod_plaza);
		map_rqnp.put("cod_dep", cod_dep);
		
		if(tipo.equals("C")) {
			//map_rqnp.put("cod_bien", "'%"+parm+"'");
			map_rqnp.put("cod_bien", "%"+parmt+"%");
			
		}else{
			//map_rqnp.put("des_bien","'%"+ parm+"'");
			map_rqnp.put("des_bien",'%'+ parmt+"%");
		}
		
		return  getSqlMapClientTemplate().queryForList("catalogobienes.selectCatalogo", map_rqnp);
	}

	
	
	 /**
     * Lista los Items del Catalogo de Bienes y Servicios Familia
     * @param String tipo - Tipo de busqueda
     * @param String parm - Parametro de Busqueda
     * @throws DataAccessException
     * @return Collection
     */  
	@Override
	public Collection listarCatalogoFamilia(Map parm)
			throws DataAccessException {

		String tipo;
		String parmt;
		String cod_uuoo;
		String cod_familia;
		
		tipo = (String) parm.get("tipo");
		parmt = (String) parm.get("parm");
		cod_uuoo = (String) parm.get("cod_uuoo");
		cod_familia = (String) parm.get("cod_familia");

		Map map_rqnp = new HashMap();
		map_rqnp.put("cod_uuoo", cod_uuoo);
		map_rqnp.put("cod_familia", cod_familia);
		
		if(tipo.equals("C")) {
			map_rqnp.put("cod_bien", "%"+parmt+"%");
		}else{
			map_rqnp.put("des_bien",'%'+ parmt+"%");
		}
		
		return  getSqlMapClientTemplate().queryForList("catalogobienes.selectCatalogoFamilia", map_rqnp);
	}

	
	@Override
	public CatalogoBienesBean recuperarCatalogoBien(Map<String, Object> params)
			throws DataAccessException {
		CatalogoBienesBean bien = new CatalogoBienesBean();
		List<CatalogoBienesBean> lista = getSqlMapClientTemplate().queryForList("catalogobienes.selectCatalogoBien", params);
		if (lista !=null ){
			if (lista.size() >0 ){
				bien = lista.get(0);
			}else{
				bien=null;
			}
		}
		return bien;
	}

}
