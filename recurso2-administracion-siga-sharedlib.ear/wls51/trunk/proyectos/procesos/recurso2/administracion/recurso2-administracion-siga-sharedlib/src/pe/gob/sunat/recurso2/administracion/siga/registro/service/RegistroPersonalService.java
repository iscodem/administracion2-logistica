package pe.gob.sunat.recurso2.administracion.siga.registro.service;

import java.util.Collection;
import java.util.Map;

import pe.gob.sunat.framework.spring.util.exception.ServiceException;
import pe.gob.sunat.recurso2.administracion.siga.parametro.model.bean.T01ParametroBean;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.MaestroContratistasBean;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.MaestroPersonalBean;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.PersonaBean;
/**
 * <p>Title: Registro Personal</p>
 * <p>Description: Registro del Personal Usuario y Dependencias
 * @author Eduardo Marchena
 */
public interface RegistroPersonalService {

	 public MaestroPersonalBean obtenerPersonaxUsuario(String cod_user);
	 public MaestroPersonalBean obtenerPersonaxRegistro(String cod_reg) throws Exception;
	 public MaestroPersonalBean obtenerPersonaxCodigo(String cod_per);
	 
	 public String obtenerRegistroPersonal(String cod_user);
	 public String obtenerJefeInmediato(String cod_empl);
	 
	 public String obtenerIntendente(String cod_empl);
	 public String obtenerAprobador(String cod_empl);
	 
	 public String esIntendente(String cod_empl);
	 public String esAprobador(String cod_empl);
	 public String obtenerSuperIntendente(String cod_empl);
	 public boolean isJefeInmediato(String cod_dep,String cod_empl);
	 
	 public String esJefeUBG(String cod_empl);
	 public Collection listarMaestroPersonal(Map parmt) ;
	 public Collection<MaestroPersonalBean> buscarColaboradores(Map parmSearch) throws Exception;
	 public Collection<MaestroPersonalBean> buscarColaboradoresMovilidad(Map parmSearch) throws Exception;
	 public Collection<MaestroPersonalBean> buscarColaboradoresViaticos(Map parmSearch) throws Exception;
	 public MaestroContratistasBean recuperarContratista(Map parm) throws ServiceException ;
	 public MaestroContratistasBean recuperarContratistaPadron(Map parm) throws ServiceException ;
	 public MaestroContratistasBean recuperarContratistaPadronNew(Map parm) throws ServiceException ;
	 
	 public PersonaBean recuperarPersona(Map<String, Object> params) throws ServiceException;
	 public String validaRUC(Map<String, Object> params)throws ServiceException;
	 public Map<String, Object>  spRUCdatos( Map<String, Object> params)throws ServiceException;
	 public Map<String, Object>  spRUCdatosNew( Map<String, Object> params)throws ServiceException;
	 
	 
	 public String registrarNuevaPersona(PersonaBean persona, String user)throws ServiceException;
	 public String registrarNuevaPersonaNew(PersonaBean persona)throws ServiceException;
	 
	 public void registrarNuevoContratista(MaestroContratistasBean contratista, String user)throws ServiceException;
	 public void registrarNuevoContratistaNew(MaestroContratistasBean contratista)throws ServiceException;
	 
	 public void registrarActualizaRuc(PersonaBean persona, String user)throws ServiceException;
	 public void registrarActualizaRucNew(PersonaBean persona)throws ServiceException;
	 
	 
	 public String obtenerJefeInmediatoEncargado(String cod_empl, String cod_encargado);
	 public String obtenerAprobadorEncargado(String cod_empl, String cod_encargado);
	 public String esAprobadorEncargado(String cod_empl);
	 
	 public String buscarNombreAutorizador(String codigoEmpleado) throws Exception;
	 
	//AGREGADO PARA CATALOGO
	 public T01ParametroBean obtenerParametro(Map<String, Object> params ) ;
	 public Collection obtenerParametroLista(Map<String, Object> params ) ;
	 public boolean esJefe(String cod_empl,String cod_dep ) ;
	 
	 public boolean determinaJefeInmediatoEncargadoMovilidad(String codigoEmpleado) throws Exception;
	 public boolean determinaAutorizadorGastoMovilidad(String codigoEmpleado) throws Exception;
	 public boolean determinaAutorizadorGastoViatico(String codigoEmpleado) throws Exception;
	 
	 // JMCR PADRON RUC
	 public PersonaBean recuperarNuevaPersonaNew(Map parm) throws Exception;
	 public String registrarNuevaSoloPersonaNew(PersonaBean persona) throws Exception;
	 
	//PAS201780000300007
	public abstract boolean esPerfilColaboradorJefe(String codDependencia,String codEmpleado ) ;
	public abstract boolean esPerfilColaboradorEncargado(String codDependencia,String codEmpleado ) ;
	public abstract boolean esEncargadoOtraUuoo(String codEmpleado);
	public abstract boolean esEncargadoAuc(String codEmpleado);
	public abstract String obtenerCadenaUuoosQueEsJefe(String codEmpleadoJefe);
	public abstract String obtenerNumJefaturasEmpleado(String codEmpleado);
	public abstract Collection obtenerJefaturasAucJson(Map<String, Object> params ) ;
	public abstract String obtenerUuooNoSupervision(String cod_dep, String long_uuoo);
}
