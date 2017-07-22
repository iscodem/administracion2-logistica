package pe.gob.sunat.recurso2.administracion.siga.ubigeo.model.dao;

import java.util.ArrayList;

import org.springframework.dao.DataAccessException;

import pe.gob.sunat.recurso2.administracion.siga.ubigeo.model.bean.TProvinciasBean;

/**
 * Interface TProvinciasDAO.
 * Mantenimiento de Provincias(TPROVINCIAS)
 * @author emarchena.
 */
public interface TProvinciasDAO {
	
	public ArrayList<TProvinciasBean> obtenerProvincias(String codigoDepartamento) throws DataAccessException;
	
}
