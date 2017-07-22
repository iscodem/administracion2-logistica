package pe.gob.sunat.recurso2.administracion.siga.registro.model.dao.ibatis;

import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import pe.gob.sunat.framework.spring.util.dao.SqlMapDAOBase;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.dao.PaFirmaElectronicaDAO;

/**
 * Implementacion de la interface PaFirmaElectronicaDAO
 * consultas al paquete PAFIRMAELECTRONICA
 * @author emarchena.
 */
public class SqlMapPaFirmaElectronicaDAOImpl extends SqlMapDAOBase implements PaFirmaElectronicaDAO {

	@Override
	public Map<String, Object> obtenerUUOOApruba(Map parm) throws DataAccessException {
		 	getSqlMapClientTemplate().queryForObject("pafirmaelectronica.spUUOOAprueba", parm);
		 	
		 	return parm;
	}

	@Override
	public Map<String, Object> obtenerUUOOAutoriza(Map parm) throws DataAccessException {
		getSqlMapClientTemplate().queryForObject("pafirmaelectronica.spUUOOAutoriza", parm);
	 	
	 	return parm;
	}
	
	@Override
	public Map<String, Object> revaluaAprueba(Map parm) throws DataAccessException {
		 	getSqlMapClientTemplate().queryForObject("pafirmaelectronica.spRevaluaAprueba", parm);
		 	
		 	return parm;
	}
	
	@Override
	public Map<String, Object> revaluaAutoriza(Map parm) throws DataAccessException {
		getSqlMapClientTemplate().queryForObject("pafirmaelectronica.spRevaluaAutoriza", parm);
	 	
	 	return parm;
	}

}
