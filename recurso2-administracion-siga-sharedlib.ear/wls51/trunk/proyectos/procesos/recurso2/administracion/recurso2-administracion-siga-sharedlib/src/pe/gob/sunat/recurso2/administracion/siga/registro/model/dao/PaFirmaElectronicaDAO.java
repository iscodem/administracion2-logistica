package pe.gob.sunat.recurso2.administracion.siga.registro.model.dao;


import java.util.Map;

import org.springframework.dao.DataAccessException;



public interface PaFirmaElectronicaDAO {
	
	public Map<String, Object> obtenerUUOOApruba(Map parm)
			throws DataAccessException;
	
	public Map<String, Object> obtenerUUOOAutoriza(Map parm)
			throws DataAccessException;
	
	public Map<String, Object> revaluaAprueba(Map parm)
			throws DataAccessException;
	
	public Map<String, Object> revaluaAutoriza(Map parm)
			throws DataAccessException;
}
