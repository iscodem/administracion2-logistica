package pe.gob.sunat.administracion2.siga.rqnp.model.dao;

import java.util.Collection;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface AucBandejaDao {

	public Collection listarRqnpAuc(Map<String, Object> params )	throws DataAccessException;
	public Collection listarRqnpAucAll(Map<String, Object> params )	throws DataAccessException;
	public Collection listarRqnpAnexo(Map<String, Object> params )	throws DataAccessException;
	public Collection listarRqnpAnexoMetas(Map<String, Object> params )	throws DataAccessException;
	///////////////////////
	public String obtenerCadenaEncargosAUC(String cod_empl) throws DataAccessException;
	public Collection obtenerBeneficiariasForExcel(Map<String,Object> parm) throws DataAccessException ;
}
