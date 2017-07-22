package pe.gob.sunat.recurso2.administracion.siga.registro.model.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.DependenciaBean;


/**
 * Interface DependenciaDAO.
 * Mantenimiento dependencias SUNAT(TDEPENDENCIAS)
 * @author emarchena.
 */

public interface DependenciaDAO {
	public Collection< DependenciaBean> searchDependencias(Map parmSearch ) throws DataAccessException ;
	public Collection< DependenciaBean> searchDependenciasMovilidad(Map parmSearch ) throws DataAccessException ;	
	public DependenciaBean obtenerDependencia(String cod_dep) throws DataAccessException;
	public Collection<DependenciaBean> obtenerJefeDependencia(String cod_dep, String cod_per) throws DataAccessException;
	public DependenciaBean obtenerDependenciaXuuoo(String num_uuoo)
	throws DataAccessException;

	//INICIO: DPORRASC
	public String obtenerTipoAUC(String cod_dep) throws DataAccessException;
	public DependenciaBean obtenerDependenciaXcod(String cod_dep) throws DataAccessException;
	public String obtenerUuooNoSupervision(String cod_dep, String long_uuoo) throws DataAccessException;
	
	/*Viaticos*/
	public Collection< DependenciaBean> searchDependenciasViatico(Map parmSearch ) throws DataAccessException;
	public Collection<DependenciaBean> buscarIntendenciaByUnidadOrganizacional (String num_uuoo) throws DataAccessException;
	public List<DependenciaBean> obtenerAllViaticoIndividual (Map<String, Object> parmSearch) throws DataAccessException;
	
	//PAS201780000300007
	public abstract DependenciaBean obtenerUuoo(Map<String, Object> param) throws DataAccessException;
	public abstract DependenciaBean obtenerDependenciaOECdeRqnp(String codDependencia) throws DataAccessException;
	public abstract DependenciaBean obtenerDependenciaAUCdeRqnp(String codDependencia) throws DataAccessException;
	public abstract DependenciaBean obtenerDependenciaRqnp(String codDependencia) throws DataAccessException;
	public abstract boolean esPerfilDependenciaAprobador(String codDependencia ) throws DataAccessException;
	public abstract boolean esPerfilDependenciaAUC(String codDependencia ) throws DataAccessException;
	public Collection<DependenciaBean> obtenerJefaturasAucJson(Map<String, Object> param) throws DataAccessException;
	public String obtenerNumJefaturasEmpleado(String codEmpleado) throws DataAccessException;
}
