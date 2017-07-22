package pe.gob.sunat.recurso2.administracion.siga.parametro.model.dao.ibatis;

import java.util.Collection;
import java.util.Map;

import org.springframework.dao.DataAccessException;


import pe.gob.sunat.framework.spring.util.dao.SqlMapDAOBase;
import pe.gob.sunat.recurso2.administracion.siga.parametro.model.dao.PrgAnnoDAO;

/**
 * Implementacion de la interface PrgAnnoDAO.
 * Mantenimiento de años(PRG_ANNO)
 * @author emarchena.
 */
public class SqlMapPrgAnnoDAOImpl extends SqlMapDAOBase implements PrgAnnoDAO {

	@Override
	public Collection listarAnnos(Map<String, Object> params)
			throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("prg_anno.selectAll", params);
		
	}
	
	

}
