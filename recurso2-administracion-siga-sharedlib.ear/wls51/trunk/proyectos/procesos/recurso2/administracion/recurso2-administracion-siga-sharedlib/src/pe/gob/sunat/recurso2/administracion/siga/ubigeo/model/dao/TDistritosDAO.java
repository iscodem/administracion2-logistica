package pe.gob.sunat.recurso2.administracion.siga.ubigeo.model.dao;

import java.util.ArrayList;

import org.springframework.dao.DataAccessException;

import pe.gob.sunat.recurso2.administracion.siga.ubigeo.model.bean.TDistritosBean;


/**
 * Interface TDistritosDAO.
 * Mantenimiento de Distritos(TDISTRITOS)
 * @author emarchena.
 */
public interface TDistritosDAO {
	
	public ArrayList<TDistritosBean> obtenerDistritos(String codigoDepartamento, String codigoProvincia) throws DataAccessException;
	
}
