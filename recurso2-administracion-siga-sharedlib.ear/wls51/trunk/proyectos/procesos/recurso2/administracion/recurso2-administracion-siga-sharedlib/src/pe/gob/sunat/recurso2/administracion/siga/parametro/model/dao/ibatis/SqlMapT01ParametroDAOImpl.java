package pe.gob.sunat.recurso2.administracion.siga.parametro.model.dao.ibatis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;



import pe.gob.sunat.framework.spring.util.dao.SqlMapDAOBase;
import pe.gob.sunat.recurso2.administracion.siga.parametro.model.bean.T01ParametroBean;
import pe.gob.sunat.recurso2.administracion.siga.parametro.model.dao.T01ParametroDAO;
import pe.gob.sunat.recurso2.administracion.siga.registro.util.ComisionadoConstantes;
import pe.gob.sunat.recurso2.administracion.siga.registro.util.ParametroConstantes;
import pe.gob.sunat.recurso2.administracion.siga.util.FormatoConstantes;

/**
 * Implementacion de la interface T01ParametroDAO.
 * Mantenimiento de Parametro SIGA(T01PARAMETRO)
 * @author emarchena.
 */
public class SqlMapT01ParametroDAOImpl extends SqlMapDAOBase implements T01ParametroDAO{

	@Override
	public T01ParametroBean recuperarParametro(Map<String, Object> params)
			throws DataAccessException {
		T01ParametroBean bean= new T01ParametroBean();
		List<T01ParametroBean> lista= (List<T01ParametroBean> ) getSqlMapClientTemplate().queryForList("t01parameto.select_arg", params);
		
		if (lista!=null){
			if(lista.size()>0){
				bean= (T01ParametroBean)lista.get(0);
				return bean;
			}else{
				return null;
			}
		}else{
			return null;
		}
			
		 
	}

	@Override
	public Collection<T01ParametroBean> recuperarParametroLista(Map<String, Object> params)
			throws DataAccessException {
		return (List<T01ParametroBean> )getSqlMapClientTemplate().queryForList("t01parameto.select_arg", params);
		
	}

	
	@Override
	public Collection<T01ParametroBean> recuperarTipoArchivo(Map<String, Object> params)
			throws DataAccessException {
		List<T01ParametroBean> lista = null;
		T01ParametroBean bean= new T01ParametroBean();
		return lista= (List<T01ParametroBean> ) getSqlMapClientTemplate().queryForList("t01parameto.select_tipo_archivo", params);
		
	}
	
	@Override
	public boolean determinaAuditor(Map<String, Object> params) throws DataAccessException {
		boolean rtn = false;
		Long resultado ;
		
		resultado  = (Long) getSqlMapClientTemplate().queryForObject("t01parameto.select_auditor", params);
		
		if (resultado>0) {
			rtn= true;
		}
		return rtn;
	}
	
	public ArrayList<T01ParametroBean> obtenerPorcentajeDesplazamiento(String notificador) throws DataAccessException {
		
		Map<String, Object> parmSearch = new HashMap<String, Object>();
		
		if(FormatoConstantes.UNO.equals(notificador))
		{	parmSearch.put("cod_par", ParametroConstantes.CODIGO_PARAMETRO_PORCENTAJE_DESPLAZ_NOTIFICADOR);}
		else{
			parmSearch.put("cod_par", ParametroConstantes.CODIGO_PARAMETRO_PORCENTAJE_DESPLAZ);
		}
					
		parmSearch.put("cod_mod", ParametroConstantes.CODIGO_MODULO_SIGA);
		parmSearch.put("cod_tipo", ParametroConstantes.DETALLE_PARAMETRO);
		ArrayList<T01ParametroBean> t01ParametroList = (ArrayList<T01ParametroBean>)getSqlMapClientTemplate().queryForList("t01parameto.select_arg", parmSearch);
		return t01ParametroList;
	}

	@Override
	public void registrarComisionado(T01ParametroBean parametro) throws Exception {
		
		getSqlMapClientTemplate().insert("t01parameto.registrarComisionado",parametro);
		
	}

	@Override
	public void actualizarComisionado(Map<String, Object> params) throws Exception {
		
		getSqlMapClientTemplate().update("t01parameto.actualizarComisionado", params);
	}

	@Override
	@SuppressWarnings("unchecked")
	public T01ParametroBean buscarComisionado(String uuooColaborador, String nroRegistroColaborador, String nroRegistroRegistrador) throws Exception {

		Map<String, Object> params = new HashMap<String, Object>();

		// asegurar parametros del comisionado alta/baja 
		params.put("cod_parametro", ComisionadoConstantes.COD_PARAMETRO_FILTRO_3079);
		params.put("cod_modulo", ComisionadoConstantes.COD_MODULO_FILTRO_SIGA);
		params.put("cod_tipo", ComisionadoConstantes.COD_TIPO_FILTRO_DETALLE);		
		
		
		params.put("uuooColaborador", uuooColaborador); // TRIM(SUBSTR(P.cod_argumento,1,6))
		params.put("nroRegistroColaborador", nroRegistroColaborador); // TRIM(SUBSTR(P.cod_argumento,7,4)) 
		params.put("nroRegistroRegistrador", nroRegistroRegistrador); // TRIM(SUBSTR(P.cod_argumento,11,4))
		
		List<T01ParametroBean> result = getSqlMapClientTemplate().queryForList("t01parameto.buscarComisionado",params);
		
		if ( CollectionUtils.isNotEmpty( result ) ) {
			return result.get(0);
		}
		
		return null;
	}

	@Override
	public ArrayList<T01ParametroBean> obtenerPorcentajeDesplazamiento() throws DataAccessException {
		
		Map<String, Object> parmSearch = new HashMap<String, Object>();
		parmSearch.put("cod_par", ParametroConstantes.CODIGO_PARAMETRO_PORCENTAJE_DESPLAZ);
		parmSearch.put("cod_mod", ParametroConstantes.CODIGO_MODULO_SIGA);
		parmSearch.put("cod_tipo", ParametroConstantes.DETALLE_PARAMETRO);
		ArrayList<T01ParametroBean> t01ParametroList = (ArrayList<T01ParametroBean>)getSqlMapClientTemplate().queryForList("t01parameto.select_arg", parmSearch);
		return t01ParametroList;
	}
	
	
}
