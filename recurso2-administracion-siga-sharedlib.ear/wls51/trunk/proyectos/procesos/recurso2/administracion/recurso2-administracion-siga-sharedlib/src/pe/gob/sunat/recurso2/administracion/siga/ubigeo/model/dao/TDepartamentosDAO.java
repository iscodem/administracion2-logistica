package pe.gob.sunat.recurso2.administracion.siga.ubigeo.model.dao;


import java.util.ArrayList;

import org.springframework.dao.DataAccessException;

import pe.gob.sunat.recurso2.administracion.siga.ubigeo.model.bean.TDepartamentosBean;


/**
 * Interface TDepartamentosDAO.
 * Mantenimiento de Departamentos(TDEPARTAMENTOS)
 * @author emarchena.
 */
public interface TDepartamentosDAO {
	
	public ArrayList<TDepartamentosBean> obtenerDepartamentos() throws DataAccessException;

}
