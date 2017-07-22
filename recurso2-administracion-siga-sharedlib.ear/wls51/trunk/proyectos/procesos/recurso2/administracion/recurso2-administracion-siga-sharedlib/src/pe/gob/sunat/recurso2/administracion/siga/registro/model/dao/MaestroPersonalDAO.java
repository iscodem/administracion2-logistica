package pe.gob.sunat.recurso2.administracion.siga.registro.model.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.MaestroPersonalBean;



/**
 * Interface MaestroPersonalDAO.
 * Mantenimiento de Masetro de Personal(MAESTRO_PERSONAL)
 * @author emarchena.
 */
public interface MaestroPersonalDAO {
	public MaestroPersonalBean obtenerPersonaxUsuario(String cod_user)
	throws DataAccessException;
	
	public MaestroPersonalBean obtenerPersonaxCodigo(String cod_empl)
	throws DataAccessException;
	
	public MaestroPersonalBean obtenerPersonaxRegistro(String cod_reg)
	throws DataAccessException;
	
	public String obtenerCorreoEmpleado(Map<String,Object> parm)
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

	public Collection<MaestroPersonalBean> listarMaestroPersonal(Map parm)
	throws DataAccessException;

	public Collection<MaestroPersonalBean> buscarMaestroPersonal(Map parm)
			throws DataAccessException ;
	
	
	public Collection<MaestroPersonalBean> buscarMaestroPersonalInactivo(Map parm)
			throws DataAccessException ;
	
	
	
	
	public Collection<MaestroPersonalBean> buscarMaestroPersonalMovilidad(Map parm)
			throws DataAccessException ;
	public Collection<MaestroPersonalBean> buscarMaestroPersonalViaticos(Map parm)
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
	
	public MaestroPersonalBean obtenerCategoriaGasto(String codigoEmpleado) throws DataAccessException;
	
	public String determinaJefeInmediatoEncargadoMovilidad(String codigoEmpleado) throws DataAccessException;
	
	public String determinaAutorizadorGastoMovilidad(String codigoEmpleado) throws DataAccessException;
	
	public String determinaAutorizadorGastoViatico(String codigoEmpleado) throws DataAccessException;
	
	public List<MaestroPersonalBean> buscarMaestroPersonalComisionadoInactivo(Map<String, Object> parm) throws DataAccessException;

	public String buscarNombreAutorizador(String codigoEmpleado) throws Exception;
	
	//PAS201780000300007
	public boolean esPerfilColaboradorJefe(String codDependencia,String codEmpleado ) throws DataAccessException;
	public boolean esPerfilColaboradorEncargado(String codDependencia,String codEmpleado ) throws DataAccessException;
	public abstract boolean esEncargadoOtraUuoo(String codEmpleado) throws DataAccessException;
	public abstract boolean esEncargadoAuc(String codEmpleado) throws DataAccessException;
	public String obtenerCadenaUuoosQueEsJefe(String codEmpleadoJefe) throws DataAccessException;
}
