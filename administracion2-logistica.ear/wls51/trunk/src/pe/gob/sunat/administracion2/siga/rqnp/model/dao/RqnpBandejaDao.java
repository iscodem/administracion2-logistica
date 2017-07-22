package pe.gob.sunat.administracion2.siga.rqnp.model.dao;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;

public interface RqnpBandejaDao {
	
	public Collection listarBienesJefeInmediato(Map<String, Object> params )	throws DataAccessException;
	
	public Collection listarBienesIntedente(Map<String, Object> params )	throws DataAccessException;
	
	public Collection listarBienesJefeUBG(Map<String, Object> params )	throws DataAccessException;
	public Collection listarBienesJefeOEC(Map<String, Object> params )	throws DataAccessException;
	
	public String ubgSolicitanteJefeUct(String cod_rqnp, String cod_bien)throws DataAccessException;
	
	public Map<String, Object> evaluaOEC(DataSource datasource,Map<String, Object> params)throws DataAccessException;
	
	public BigDecimal saldoCD(Map<String, Object> params)throws DataAccessException;
	
	public String oecEsCentral(String cod_oec)throws DataAccessException;
	
	//(DPORRASC)/////////////////////////////////////////////////
	public String obtenerIndicadorOec(String cod_oec)throws DataAccessException;
	public String obtenerDpg(String codParametro, String descParametro)throws DataAccessException;
	
	
}
