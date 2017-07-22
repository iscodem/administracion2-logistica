package pe.gob.sunat.recurso2.administracion.siga.registro.model.dao.ibatis;


import java.util.Map;

import org.springframework.dao.DataAccessException;


import pe.gob.sunat.framework.spring.util.dao.SqlMapDAOBase;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.TcargosBean;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.dao.TcargosDAO;

/**
 * Implementacion de la interface TcargosDAO.
 * Consulta de Niveles Cargos del Personal(NIVELES)
 * @author emarchena.
 */
public class SqlMapTcargosDAOImpl  extends SqlMapDAOBase implements TcargosDAO{

	@Override
	public TcargosBean obtieneCargo(Map<String, Object> parm) throws DataAccessException {
		return (TcargosBean)getSqlMapClientTemplate().queryForObject("tcargos.select_cargo", parm); 
	}
	
}