package pe.gob.sunat.recurso2.administracion.siga.registro.model.dao.ibatis;


import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientTemplate;



import pe.gob.sunat.framework.spring.util.dao.SqlMapDAOBase;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.PersonaBean;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.dao.PersonaDAO;

/**
 * Implementacion de la interface PersonaDAO
 * Mantenimiento de Datos de Personas(PERSONA)
 * @author emarchena.
 */
public class SqlMapPersonaDAOImpl extends SqlMapDAOBase implements PersonaDAO  {

	@Override
	public PersonaBean recuperarPersona(Map<String, Object> params)
			throws DataAccessException {
		return (PersonaBean) getSqlMapClientTemplate().queryForObject(
				"persona_map.selectPersona", params);
	}

	@Override
	public String recuperarIDPersona() throws DataAccessException {
		Map params = new HashMap();
		return (String) getSqlMapClientTemplate().queryForObject(
				"persona_map.selectPersonaID", params);
	}

	@Override
	public void updateRuc(DataSource datasource, PersonaBean bean)
			throws DataAccessException {
		new SqlMapClientTemplate(datasource, getSqlMapClientTemplate().getSqlMapClient()).
		update("persona_map.update_ruc", bean);
		
	}

	@Override
	public void updateRucNew( PersonaBean bean)
			throws DataAccessException {
		new SqlMapClientTemplate( getSqlMapClientTemplate().getSqlMapClient()).
		update("persona_map.update_ruc", bean);
	}	

	@Override
	public void insertar(DataSource datasource, PersonaBean bean)
			throws DataAccessException {
		new SqlMapClientTemplate(datasource, getSqlMapClientTemplate().getSqlMapClient()).
        insert("persona_map.insert", bean);
		
	}

	@Override
	public void insertarNew( PersonaBean bean)
			throws DataAccessException {
		new SqlMapClientTemplate( getSqlMapClientTemplate().getSqlMapClient()).
        insert("persona_map.insert", bean);
	}
	
	@Override
	public void insertarNew2( PersonaBean bean)
			throws DataAccessException {
		new SqlMapClientTemplate( getSqlMapClientTemplate().getSqlMapClient()).
        insert("persona_map.insert2", bean);
	}
	
	@Override
	public Map<String, Object> spRUCdatos(DataSource datasource,
			Map<String, Object> params) throws DataAccessException {
		new SqlMapClientTemplate(datasource, getSqlMapClientTemplate().getSqlMapClient()).queryForObject("persona_map.sp_ruc_datos", params);
		return params;
	}

	@Override
	public Map<String, Object> spRUCdatosNew(
			Map<String, Object> params) throws DataAccessException {
		new SqlMapClientTemplate( getSqlMapClientTemplate().getSqlMapClient()).queryForObject("persona_map.sp_ruc_datos", params);
		return params;
	}
	
	@Override
	public String validaRUC(Map<String, Object> params)
			throws DataAccessException {
		return  (String) getSqlMapClientTemplate().queryForObject("persona_map.valida_ruc", params);
	}

	@Override
	public String recuperaRUCestado(Map<String, Object> params)
		throws DataAccessException {
			return  (String) getSqlMapClientTemplate().queryForObject("persona_map.get_ruc_estado", params);
	}

	

}
