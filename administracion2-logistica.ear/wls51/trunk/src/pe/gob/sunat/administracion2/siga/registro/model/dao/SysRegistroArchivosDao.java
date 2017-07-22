package pe.gob.sunat.administracion2.siga.registro.model.dao;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;

import pe.gob.sunat.administracion2.siga.registro.model.domain.RegistroArchivosBean;
import pe.gob.sunat.administracion2.siga.registro.model.domain.TdBeneficiarioBean;

public interface SysRegistroArchivosDao {

	public String   obtenerContador () throws DataAccessException;
	public void insertar(DataSource datasource, RegistroArchivosBean bean)throws DataAccessException;
	
}
