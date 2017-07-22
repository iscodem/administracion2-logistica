package pe.gob.sunat.recurso2.administracion.siga.registro.model.dao;

import java.util.Collection;
import java.util.Map;
import org.springframework.dao.DataAccessException;

import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.TcargosBean;


/**
 * Interface TcargosDAO.
 * Consulta de Niveles Cargos del Personal(NIVELES)
 * @author emarchena.
 */
 
 
public interface TcargosDAO {
	public TcargosBean obtieneCargo(Map<String, Object> parm) throws DataAccessException;
}
