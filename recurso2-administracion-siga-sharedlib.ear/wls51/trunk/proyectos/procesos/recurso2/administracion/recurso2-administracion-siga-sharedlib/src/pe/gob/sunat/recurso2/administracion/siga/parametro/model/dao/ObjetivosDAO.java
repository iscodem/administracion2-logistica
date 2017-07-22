package pe.gob.sunat.recurso2.administracion.siga.parametro.model.dao;

import java.util.Collection;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import pe.gob.sunat.recurso2.administracion.siga.parametro.model.bean.ObjetivosBean;



/**
 * Interface SysRegistroArchivosDAO.
 * Mantenimiento de objetivos (OBJETIVOS)
 * @author emarchena.
 */
public interface ObjetivosDAO {

		public Collection<ObjetivosBean> listarObjetivos(Map parm)
		throws DataAccessException;
	
}
