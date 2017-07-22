package pe.gob.sunat.administracion2.siga.registro.model.dao;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;

import pe.gob.sunat.administracion2.siga.registro.model.domain.RegistroArchivosBean;
import pe.gob.sunat.administracion2.siga.registro.model.domain.TdBeneficiarioBean;

public interface TdBeneficiariosDao {
	 public String obtenerSecuenciaBeneficiarios(Map<String, Object> parm)throws DataAccessException;
	public void insertarTdBeneficiarios(TdBeneficiarioBean tdBeneficiarioBean) throws DataAccessException ;
	
}
