package pe.gob.sunat.recurso2.administracion.siga.registro.model.dao.ibatis;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientTemplate;


import pe.gob.sunat.framework.spring.util.dao.SqlMapDAOBase;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.dao.AccesoDAO;

import javax.sql.DataSource;

public class SqlMapAccesoDAOImpl extends SqlMapDAOBase implements AccesoDAO{

	
	public void setearVariableEntorno(Map<String, Object> parametros) {
		getSqlMapClientTemplate().queryForObject("acceso.setearVariableEntorno", parametros);
	}

	public void limpiarVariableEntorno(Map<String, Object> parametros) {
		getSqlMapClientTemplate().queryForObject("acceso.limpiarVariableEntorno", parametros);
	}

	
	@Override
	public void setUsuarioAcceso(DataSource dataSource,String variable, String valor)
			throws DataAccessException {
			log.debug("Inicia - setUsuarioAcceso");
			//variable = USER
			Map map_rqnp = new HashMap();
			map_rqnp.put("variable", variable);
			map_rqnp.put("valor",valor);
			
			getSqlMapClientTemplate().setDataSource(dataSource);
			
			String retorno = (String)getSqlMapClientTemplate().queryForObject("acceso.acceso_f_setuserenv", map_rqnp);
			
			log.debug("retorno:"+retorno); 
			
	}

	@Override
	public void setUsuarioAccesoNull(DataSource dataSource, String variable)
			throws DataAccessException {
		log.debug("Inicia - setUsuarioAccesoNull");
		//variable = USER
		Map map_rqnp = new HashMap();
		map_rqnp.put("variable", variable);
		
		
		getSqlMapClientTemplate().setDataSource(dataSource);
		
		String retorno = (String)getSqlMapClientTemplate().queryForObject("acceso.acceso_f_setuserenv_null", map_rqnp);
		
		log.debug("retorno:"+retorno); 
		
	}

}
