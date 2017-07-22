package pe.gob.sunat.recurso2.administracion.siga.registro.model.dao;

import java.util.Collection;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;

import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.MaestroContratistasBean;




/**
 * Interface MaestroContratistasDAO.
 * Mantenimiento de  Maestro Contratistas(MAESTRO_DE_CONTRATISTAS)
 * @author emarchena.
 */

public interface MaestroContratistasDAO {
	public Collection<MaestroContratistasBean> listarMaestroContratistas(Map parm)
	throws DataAccessException;
	public MaestroContratistasBean recuperarMaestroContratistas(Map parm)
	throws DataAccessException;
	
	public void insertar(DataSource datasource, MaestroContratistasBean bean)
	throws DataAccessException;
	
	public void insertarNew( MaestroContratistasBean bean)
			throws DataAccessException;
}
