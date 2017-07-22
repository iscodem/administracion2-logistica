package pe.gob.sunat.recurso2.administracion.siga.parametro.model.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import pe.gob.sunat.recurso2.administracion.siga.parametro.model.bean.T01ParametroBean;

/**
 * Interface T01ParametroDAO.
 * Mantenimiento de Parametro SIGA(T01PARAMETRO)
 * @author emarchena.
 */
public interface T01ParametroDAO {

	public T01ParametroBean recuperarParametro(Map<String, Object> params) throws DataAccessException;
	public Collection recuperarTipoArchivo(Map<String, Object> params )	throws DataAccessException;
	
	public Collection recuperarParametroLista(Map<String, Object> params) throws DataAccessException;
	public boolean determinaAuditor(Map<String, Object> params) throws DataAccessException;
	public ArrayList<T01ParametroBean> obtenerPorcentajeDesplazamiento() throws DataAccessException;
    public ArrayList<T01ParametroBean> obtenerPorcentajeDesplazamiento(String notificador)  throws DataAccessException;
	void registrarComisionado(T01ParametroBean parametro) throws Exception;
	
	void actualizarComisionado(Map<String, Object> params) throws Exception;
	
	T01ParametroBean buscarComisionado(String uuooColaborador, String nroRegistroColaborador, String nroRegistroRegistrador) throws Exception;
	
}
