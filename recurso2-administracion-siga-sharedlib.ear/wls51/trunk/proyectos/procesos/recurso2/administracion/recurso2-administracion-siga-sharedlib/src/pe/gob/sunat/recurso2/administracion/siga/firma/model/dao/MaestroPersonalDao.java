package pe.gob.sunat.recurso2.administracion.siga.firma.model.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import pe.gob.sunat.recurso2.administracion.siga.firma.model.bean.MaestroPersonal;


public interface MaestroPersonalDao {
	public MaestroPersonal obtenerPersonaxUsuario(String cod_user)
	throws DataAccessException;
	
	public MaestroPersonal obtenerPersonaxCodigo(String cod_empl)
	throws DataAccessException;
	
	public MaestroPersonal obtenerPersonaxRegistro(String cod_reg)
	throws DataAccessException;
	
	public String obtenerRegistroPersonal(String cod_user)
	throws DataAccessException;
	
	public String obtenerJefeInmediato(String cod_empl)
	throws DataAccessException;
	
	public String obtenerIntendente(String cod_empl)
	throws DataAccessException;
	
	public String obtenerAprobador(String cod_empl)
	throws DataAccessException;
	
	public String obtenerAprobadorAUC(String cod_auc)
	throws DataAccessException;
	
	public String esIntendente(String cod_empl)
	throws DataAccessException;
	
	public String esAprobador(String cod_empl)
	throws DataAccessException;
	
	public String obtenerSuperIntendente(String cod_empl)
	throws DataAccessException;
	
	
	public String esJefeUBG(String cod_empl)
	throws DataAccessException;

	public Collection<MaestroPersonal> listarMaestroPersonal(Map parm)
	throws DataAccessException;

	public Collection<MaestroPersonal> buscarMaestroPersonal(Map parm)
			throws DataAccessException ;
	
	
	public Collection<MaestroPersonal> buscarMaestroPersonalInactivo(Map parm)
			throws DataAccessException ;
	
	
	
	
	public Collection<MaestroPersonal> buscarMaestroPersonalMovilidad(Map parm)
			throws DataAccessException ;
	public String obtenerJefeInmediatoEncargado(String cod_empl, String cod_encargado)
	throws DataAccessException;
	
	public String obtenerAprobadorEncargado(String cod_empl, String cod_encargado)
	throws DataAccessException;
	
	public String obtenerAprobadorEncargadoAUC(String cod_auc, String cod_encargado)
	throws DataAccessException;
	
	public String esAprobadorEncargado(String cod_empl)
	throws DataAccessException;
	
////////////////////////////////////////////////////////////////////////	
	public String esJefe(String cod_empl, String cod_dep )
	throws DataAccessException;
	
	public String obtenerJefeAuc(String cod_dep)
	throws DataAccessException;
	
	public String esJefeInmediatoEncargado(String cod_empl, String cod_enca)
	throws DataAccessException;
	
	public String verificaEncargo(String cod_empl, String cod_enca)
	throws DataAccessException;
	
	public MaestroPersonal obtenerCategoriaGasto(String codigoEmpleado) throws DataAccessException;
	
	public String determinaJefeInmediatoEncargadoMovilidad(String codigoEmpleado) throws DataAccessException;
	
	public String determinaAutorizadorGastoMovilidad(String codigoEmpleado) throws DataAccessException;
	
	public List<MaestroPersonal> buscarMaestroPersonalComisionadoInactivo(Map<String, Object> parm) throws DataAccessException;

	public String buscarNombreAutorizador(String codigoEmpleado) throws Exception;

	List<MaestroPersonal> obtenerJefeDependencia(MaestroPersonal param)
			throws DataAccessException;

	List<MaestroPersonal> obtenerColaboradorConDependencia(MaestroPersonal param)
		throws DataAccessException;
}
