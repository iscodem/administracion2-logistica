package pe.gob.sunat.administracion2.siga.registro.model.dao.ibatis;

import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import pe.gob.sunat.administracion2.siga.registro.model.dao.TdBeneficiariosDao;
import pe.gob.sunat.administracion2.siga.registro.model.domain.TdBeneficiarioBean;
import pe.gob.sunat.framework.spring.util.dao.SqlMapDAOBase;
@SuppressWarnings("deprecation")
public class SqlMapTdBeneficiariosDaoImpl extends SqlMapDAOBase implements TdBeneficiariosDao{

	
	@Override
	public void insertarTdBeneficiarios(TdBeneficiarioBean tdBeneficiarioBean) throws DataAccessException {
		getSqlMapClientTemplate().insert("tdbeneficiarios.insertarTdBeneficiarios",tdBeneficiarioBean);
	}
	
	/**
	 * Obtiene la secuencia de la tabla TD_BENEFICIARIOS
	 */
	@Override
	public String obtenerSecuenciaBeneficiarios (Map<String, Object> parm) throws DataAccessException {
		return (String) getSqlMapClientTemplate().queryForObject("tdbeneficiarios.selectSecuencia", parm);
	}
}
