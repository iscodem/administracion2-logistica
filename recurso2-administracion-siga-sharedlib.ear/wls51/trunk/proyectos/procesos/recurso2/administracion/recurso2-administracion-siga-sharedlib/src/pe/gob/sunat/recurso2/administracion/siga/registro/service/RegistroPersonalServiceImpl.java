package pe.gob.sunat.recurso2.administracion.siga.registro.service;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jndi.JndiTemplate;

import pe.gob.sunat.framework.spring.util.exception.ServiceException;
import pe.gob.sunat.recurso2.administracion.siga.parametro.model.bean.T01ParametroBean;
import pe.gob.sunat.recurso2.administracion.siga.parametro.model.dao.T01ParametroDAO;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.Ddp;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.Dds;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.DependenciaBean;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.EncargosPersonaBean;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.MaestroContratistasBean;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.MaestroPersonalBean;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.PersonaBean;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.T733Pernat;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.dao.AccesoDAO;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.dao.DdpDAO;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.dao.DdsDAO;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.dao.DependenciaDAO;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.dao.EncargosPersonaDAO;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.dao.MaestroContratistasDAO;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.dao.MaestroPersonalDAO;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.dao.PersonaDAO;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.dao.T733PernatDAO;
import pe.gob.sunat.recurso2.administracion.siga.util.FormatoConstantes;


/**
 * Implementacion de la interface RegistroPersonalService.
*<p>Title: Registro Personal</p>
 * <p>Description: Registro del Personal Usuario y Dependencias
 * @author emarchena.
 */
public class RegistroPersonalServiceImpl implements RegistroPersonalService {

	private static final Log log = LogFactory.getLog(RegistroPersonalServiceImpl.class);
	
	MaestroPersonalDAO 			maestroPersonalDao;
	EncargosPersonaDAO 			encargosPersonaDao;
	DependenciaDAO				dependenciaDao;
	PersonaDAO					personaDao;
	AccesoDAO					accesoDao;
	MaestroContratistasDAO		maestroContratistasDao;
	T01ParametroDAO				t01ParametroDao;

	DdpDAO					ddpDao;	
	DdsDAO					ddsDao;
	T733PernatDAO			t733pernatDao;
	
	@Override
	public MaestroPersonalBean obtenerPersonaxUsuario(String cod_user) {
		log.debug("Inicio obtenerPersonaxUsuario");
		return maestroPersonalDao.obtenerPersonaxUsuario(cod_user);
	}

	
	@Override
	public MaestroPersonalBean obtenerPersonaxRegistro(String cod_reg) throws Exception {
		log.debug("Inicio obtenerPersonaxRegistro");
		String correo="";
		Map<String,Object>parm=new HashMap<String, Object>();
		MaestroPersonalBean persona =maestroPersonalDao.obtenerPersonaxRegistro(cod_reg);
			if (persona !=null){
				parm.put("numReg", persona.getNumero_registro());
				correo=maestroPersonalDao.obtenerCorreoEmpleado(parm);//TEMPORAL
				//correo="correo@sunat.gob.pe";//TEMPORAL
				persona.setCorreo(correo);
			}
		return persona;
	}
	
	@Override
	public MaestroPersonalBean obtenerPersonaxCodigo(String cod_per) {
		log.debug("Inicio obtenerPersonaxCodigo");
		String correo="";
		Map<String,Object>parm=new HashMap<String, Object>();
		
		MaestroPersonalBean persona = maestroPersonalDao.obtenerPersonaxCodigo(cod_per);
		if (persona !=null){
			parm.put("numReg", persona.getNumero_registro());
			correo=maestroPersonalDao.obtenerCorreoEmpleado(parm);
			persona.setCorreo(correo);
		}
		
		return persona;
	}
	
	

	@Override
	public String obtenerRegistroPersonal(String cod_user) {
		log.debug("Inicio obtenerRegistroPersonal");
		return maestroPersonalDao.obtenerRegistroPersonal(cod_user);
	}
	

	/**
     * Recuperar Jefe Inmediato de un Empleado
     * @param String - Codigo de Empleado
     * @return String - Codigo de Jefe Inmediato
     */ 
	@Override
	public String obtenerJefeInmediato(String cod_empl) {
		if (log.isDebugEnabled()) log.debug("Inicio - RegistroPersonalServiceImpl.obtenerJefeInmediato");
		try {
			DataSource dataSource = obtenerDataSource("sig", false);
			return maestroPersonalDao.obtenerJefeInmediato( cod_empl);
		} catch (Exception ex) {
			   log.error("Error en RegistroPersonalServiceImpl.obtenerJefeInmediato: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
		}
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RegistroPersonalServiceImpl.obtenerJefeInmediato");
		}
		
	}


	/**
     * Recuperar Intendente de un Empleado
     * @param String - Codigo de Empleado
     * @return String - Codigo de Intendente
     */ 
	@Override
	public String obtenerIntendente(String cod_empl) {
		if (log.isDebugEnabled()) log.debug("Inicio - RegistroPersonalServiceImpl.obtenerIntendente");
		try {
			DataSource dataSource = obtenerDataSource("sig", false);
			return maestroPersonalDao.obtenerIntendente( cod_empl);
		} catch (Exception ex) {
			   log.error("Error en RegistroPersonalServiceImpl.obtenerIntendente: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
		}
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RegistroPersonalServiceImpl.obtenerIntendente");
		}
	}


	/**
     * Recuperar JefeAprobador de un Empleado
     * @param String - Codigo de Empleado
     * @return String - Codigo de Intendente
     */ 
	@Override
	public String obtenerAprobador(String cod_empl) {
		if (log.isDebugEnabled()) log.debug("Inicio - RegistroPersonalServiceImpl.obtenerAprobador");
		try {
			DataSource dataSource = obtenerDataSource("sig", false);
			return maestroPersonalDao.obtenerAprobador( cod_empl);
		} catch (Exception ex) {
			   log.error("Error en RegistroPersonalServiceImpl.obtenerAprobador: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
		}
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RegistroPersonalServiceImpl.obtenerAprobador");
		}
	}
	
	/**
     * Verifica si el Empleado es un intendente
     * @param String - Codigo de Empleado
     * @return String - (S=si , N=no)
     */ 
	@Override
	public String esIntendente(String cod_empl) {
		if (log.isDebugEnabled()) log.debug("Inicio - RegistroPersonalServiceImpl.esIntendente");
		try {
			DataSource dataSource = obtenerDataSource("sig", false);
			return maestroPersonalDao.esIntendente( cod_empl);
		} catch (Exception ex) {
			   log.error("Error en RegistroPersonalServiceImpl.esIntendente: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
		}
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RegistroPersonalServiceImpl.esIntendente");
		}
	}

	/**
     * Verifica si el Empleado es un Jefe Aprobador
     * @param String - Codigo de Empleado
     * @return String - (S=si , N=no)
     */ 
	@Override
	public String esAprobador(String cod_empl) {
		if (log.isDebugEnabled()) log.debug("Inicio - RegistroPersonalServiceImpl.esAprobador");
		try {
			DataSource dataSource = obtenerDataSource("sig", false);
			return maestroPersonalDao.esAprobador( cod_empl);
		} catch (Exception ex) {
			log.error("Error en RegistroPersonalServiceImpl.esAprobador: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);
		}
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RegistroPersonalServiceImpl.esAprobador");
		}
	}
	
	
	/**
     * Verifica si el Empleado es un Jefe de UBG
     * @param String - Codigo de Empleado
     * @return String - (S=si , N=no)
     */ 
	@Override
	public String esJefeUBG(String cod_empl) {
		if (log.isDebugEnabled()) log.debug("Inicio - RegistroPersonalServiceImpl.esJefeUBG");
		try {
			DataSource dataSource = obtenerDataSource("sig", false);
			return maestroPersonalDao.esJefeUBG( cod_empl);
		} catch (Exception ex) {
			   log.error("Error en RegistroPersonalServiceImpl.esJefeUBG: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
		}
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RegistroPersonalServiceImpl.esJefeUBG");
		}
	}
	
	
	/**
     * Recuperar Super Intendente de un Empleado
     * @param String - Codigo de Empleado
     * @return String - Codigo de Super Intendente
     */ 
	@Override
	public String obtenerSuperIntendente(String cod_empl) {
		if (log.isDebugEnabled()) log.debug("Inicio - RegistroPersonalServiceImpl.obtenerSuperIntendente");
		try {
			DataSource dataSource = obtenerDataSource("sig", false);
			return maestroPersonalDao.obtenerSuperIntendente( cod_empl);
		} catch (Exception ex) {
			   log.error("Error en RegistroPersonalServiceImpl.obtenerSuperIntendente: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
		}
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RegistroPersonalServiceImpl.obtenerSuperIntendente");
		}
	}
	
/////////////////////////////////////////////////////////////////////////////////////////////	
	/**
     * Verifica si el Empleado es un Jefe Inmediato
     * @param String - Codigo de Dependencia
     * @param String - Codigo de Empleado
     * @return boolean 
     */ 
	
	@Override
	public boolean esJefe(String cod_empl,String cod_dep) {
		if (log.isDebugEnabled()) log.debug("Inicio - RegistroPersonalServiceImpl.esJefe");
		boolean sw=false;
		try {
			DataSource dataSource = obtenerDataSource("sig", false);
			String rpta = maestroPersonalDao.esJefe(cod_empl,cod_dep);
			if (rpta.equals("N")){
				sw=false;
			}
			if (rpta.equals("S")){
				sw=true;
			}
			return sw;
		} catch (Exception ex) {
			log.error("Error en RegistroPersonalServiceImpl.esJefe: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);
		}
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RegistroPersonalServiceImpl.esJefe");
		}
	}
	
///////////////////////////////////////////////////////////////////////////////////////////
	
 	/**
     * Verifica si el Empleado es un Jefe Inmediato
     * @param String - Codigo de Dependencia
     * @param String - Codigo de Empleado
     * @return boolean 
     */ 
	@Override
	public boolean isJefeInmediato(String cod_dep ,String cod_empl) {
		if (log.isDebugEnabled()) log.debug("Inicio - RegistroPersonalServiceImpl.isJefeInmediato");
		boolean sw=false;
		List<EncargosPersonaBean> listaEncargos = new ArrayList<EncargosPersonaBean>();
		List<DependenciaBean> listaDependencias = new ArrayList<DependenciaBean>();
		
		listaEncargos=(List<EncargosPersonaBean>) encargosPersonaDao.obtenerEncargosPersona(cod_empl);
		if (listaEncargos==null ){
			if (log.isDebugEnabled())log.debug("Sin Encargos");
			listaEncargos=new ArrayList<EncargosPersonaBean>();
		}
		
		listaDependencias= (List<DependenciaBean>) dependenciaDao.obtenerJefeDependencia(cod_dep, cod_empl);
		if (listaDependencias==null){
			if (log.isDebugEnabled())log.debug("Sin Dependencias");
			listaDependencias= new ArrayList<DependenciaBean>();
		}
		if (listaEncargos.size()>0 || listaDependencias.size()>0){
			sw=true;
		}
		if (log.isDebugEnabled()) log.debug("Fin - RegistroPersonalServiceImpl.isJefeInmediato");
		return sw;
	}
	
	
	/**
     * Listar los Registro de Personal
     * @param Map<String, Object> parmt 
     * @return Collection - Lista de Personas
     */  
	@Override
	public Collection listarMaestroPersonal(Map parmt) {
			if (log.isDebugEnabled()) log.debug("Inicio - RegistroPersonalServiceImpl.listarMaestroPersonal");
		Map<String, Object> map;
		char ini=' ' ;
		char ini2= "'".charAt(0) ;
		
		int i =0;
	
		List<Map<String, Object>> lsMaestro = new ArrayList<Map<String,Object>>();
		try {
		
			List<MaestroPersonalBean> lista = (List<MaestroPersonalBean>)maestroPersonalDao.listarMaestroPersonal(parmt);
			
			for(MaestroPersonalBean data: lista){
				map = new HashMap<String, Object>();
		
				map.put("codigoEmpleado", data.getCodigoEmpleado());
				map.put("nombre_completo", data.getNombre_completo().replace( '"',ini).replace('/', ini).replace('°', ini).replace(ini2, ini).trim());
				map.put("numero_registro", data.getNumero_registro());
				map.put("apellido_paterno", data.getApellido_paterno().replace( '"',ini).replace('/', ini).replace('°', ini).replace(ini2, ini).trim());
				map.put("apellido_materno", data.getApellido_materno().replace( '"',ini).replace('/', ini).replace('°', ini).replace(ini2, ini).trim());
				map.put("nombre", data.getNombre().replace( '"',ini).replace('/', ini).replace('°', ini).replace(ini2, ini).trim());
				map.put("codigoDependencia", data.getCodigoDependencia());
				//map.put("dependencia", data.getDependencia().replace( '"',ini).replace('/', ini).replace('°', ini).replace(ini2, ini).trim());
				map.put("codigoSede", data.getCodigoSede());
				//map.put("sede", data.getSede().replace( '"',ini).replace('/', ini).replace('°', ini).replace(ini2, ini).trim());
				map.put("cod_plaza", data.getCod_plaza());
				map.put("uuoo", data.getUuoo());
				map.put("dni", data.getDni());
				
				lsMaestro.add(map);
			}
			return lsMaestro;
		} 
		catch (Exception ex) {
			   log.error("Error en RegistroPersonalServiceImpl.listarMaestroPersonal: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
		}
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RegistroPersonalServiceImpl.listarMaestroPersonal");
		}
		
	}
	
	
	/**
     * Listar los Registro de Personal
     * @param Map<String, Object> parmt 
     * @return Collection - Lista de Personas
     */  
	@Override
	public Collection<MaestroPersonalBean> buscarColaboradores(Map parmSearch) throws Exception {
			if (log.isDebugEnabled()) log.debug("Inicio - RegistroPersonalServiceImpl.buscarColaboradores");
		Map<String, Object> map;
		char ini=' ' ;
		char ini2= "'".charAt(0) ;
		
		int i =0;
	
		List<MaestroPersonalBean> lsMaestro = new ArrayList<MaestroPersonalBean>();
		try {
		
			List<MaestroPersonalBean> lista = (List<MaestroPersonalBean>)maestroPersonalDao.buscarMaestroPersonalInactivo(parmSearch);
			
			for(MaestroPersonalBean data: lista){
				MaestroPersonalBean persona = data;
		
			
				persona.setNombre_completo( data.getNombre_completo().replace( '"',ini).replace('/', ini).replace('°', ini).replace(ini2, ini).trim());
				
				persona.setApellido_paterno( data.getApellido_paterno().replace( '"',ini).replace('/', ini).replace('°', ini).replace(ini2, ini).trim());
				persona.setApellido_materno( data.getApellido_materno().replace( '"',ini).replace('/', ini).replace('°', ini).replace(ini2, ini).trim());
				persona.setNombre( data.getNombre().replace( '"',ini).replace('/', ini).replace('°', ini).replace(ini2, ini).trim());
			
				
				
				
				lsMaestro.add(persona);
			}
			return lsMaestro;
		} 
		catch (Exception ex) {
			   log.error("Error en RegistroPersonalServiceImpl.buscarColaboradores: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
		}
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RegistroPersonalServiceImpl.buscarColaboradores");
		}
		
	}
	
	
	/**
     * Listar los Registro  de Personal de movilidad
     * @param Map<String, Object> parmt 
     * @return Collection - Lista de Personas
     */  
	@Override
	public Collection<MaestroPersonalBean> buscarColaboradoresMovilidad(Map parmSearch) throws Exception {
			if (log.isDebugEnabled()) log.debug("Inicio - RegistroPersonalServiceImpl.buscarColaboradoresMovilidad");
		Map<String, Object> map;
		char ini=' ' ;
		char ini2= "'".charAt(0) ;
		
		int i =0;
	
		List<MaestroPersonalBean> lsMaestro = new ArrayList<MaestroPersonalBean>();
		try {
		
			List<MaestroPersonalBean> lista = (List<MaestroPersonalBean>)maestroPersonalDao.buscarMaestroPersonalMovilidad(parmSearch);
			
			for(MaestroPersonalBean data: lista){
				MaestroPersonalBean persona = data;
		
			
				persona.setNombre_completo( data.getNombre_completo().replace( '"',ini).replace('/', ini).replace('°', ini).replace(ini2, ini).trim());
				
				persona.setApellido_paterno( data.getApellido_paterno().replace( '"',ini).replace('/', ini).replace('°', ini).replace(ini2, ini).trim());
				persona.setApellido_materno( data.getApellido_materno().replace( '"',ini).replace('/', ini).replace('°', ini).replace(ini2, ini).trim());
				persona.setNombre( data.getNombre().replace( '"',ini).replace('/', ini).replace('°', ini).replace(ini2, ini).trim());
			
				
				
				
				lsMaestro.add(persona);
			}
			return lsMaestro;
		} 
		catch (Exception ex) {
			   log.error("Error en RegistroPersonalServiceImpl.buscarColaboradores: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
		}
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RegistroPersonalServiceImpl.buscarColaboradores");
		}
		
	}
	
	
	/**
     * Listar los Registro  de Personal de movilidad
     * @param Map<String, Object> parmt 
     * @return Collection - Lista de Personas
     */  
	@Override
	public Collection<MaestroPersonalBean> buscarColaboradoresViaticos(Map parmSearch) throws Exception {
			if (log.isDebugEnabled()) log.debug("Inicio - RegistroPersonalServiceImpl.buscarColaboradoresViaticos");
		Map<String, Object> map;
		char ini=' ' ;
		char ini2= "'".charAt(0) ;
		
		int i =0;
	
		List<MaestroPersonalBean> lsMaestro = new ArrayList<MaestroPersonalBean>();
		try {
		
			List<MaestroPersonalBean> lista = (List<MaestroPersonalBean>)maestroPersonalDao.buscarMaestroPersonalViaticos(parmSearch);
			
			for(MaestroPersonalBean data: lista){
				MaestroPersonalBean persona = data;
		
			
				persona.setNombre_completo( data.getNombre_completo().replace( '"',ini).replace('/', ini).replace('°', ini).replace(ini2, ini).trim());
				
				persona.setApellido_paterno( data.getApellido_paterno().replace( '"',ini).replace('/', ini).replace('°', ini).replace(ini2, ini).trim());
				persona.setApellido_materno( data.getApellido_materno().replace( '"',ini).replace('/', ini).replace('°', ini).replace(ini2, ini).trim());
				persona.setNombre( data.getNombre().replace( '"',ini).replace('/', ini).replace('°', ini).replace(ini2, ini).trim());
			
				
				
				
				lsMaestro.add(persona);
			}
			return lsMaestro;
		} 
		catch (Exception ex) {
			   log.error("Error en RegistroPersonalServiceImpl.buscarColaboradoresViaticos: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
		}
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RegistroPersonalServiceImpl.buscarColaboradoresViaticos");
		}
		
	}
	
	
	
	// JMCR PADRON RUC
	/**
     * Recuperar Contratista .Padron
     * @param Map<String, Object> params 
     * @return Collection - lista de items<
     */
	@Override
	public PersonaBean recuperarNuevaPersonaNew(Map parm) throws Exception {
		if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.recuperarContratistaPadronNew");
		PersonaBean persona= null;
		
		try {
			
		    String rpta_mensaje=FormatoConstantes.CADENA_VACIA;
			String num_ruc = (String) parm.get("num_ruc");
			String userWeb = (String) parm.get("user");
			String num_doc=null;
			String tip_doc=null;
			String tip_doc_siga=null;
			String razon_social	=FormatoConstantes.CADENA_VACIA;
			String cod_ubigeo	=FormatoConstantes.CADENA_VACIA;
			String tipo_via		=FormatoConstantes.CADENA_VACIA;
			String nom_via		=FormatoConstantes.CADENA_VACIA;
			String num_via		=FormatoConstantes.CADENA_VACIA;
			String num_interior	=FormatoConstantes.CADENA_VACIA;
			String tipo_zona	=FormatoConstantes.CADENA_VACIA;
			String nom_zona		=FormatoConstantes.CADENA_VACIA;
			String referencia	=FormatoConstantes.CADENA_VACIA;
			String ls_direccion =FormatoConstantes.CADENA_VACIA;
			String ls_cod_perona=FormatoConstantes.CADENA_VACIA;
			String ls_ruc_estado=FormatoConstantes.CADENA_VACIA;
			//verificar si existe en TABLA PERSONA
			Map<String,Object>  parm01 = new HashMap<String,Object>();
			
			persona=personaDao.recuperarPersona(parm);
			if ( persona==null){
				log.info("Objetos dao a ddp, dds y pernat: " + ddpDao + " " + ddsDao + " " + t733pernatDao);
				//importar de padron
				Ddp ddpParam = new Ddp();
				ddpParam.setDdpNumruc(num_ruc);
				List<Ddp> ddpList = ddpDao.selectByExample(ddpParam);
				
				Dds ddsParam = new Dds();
				ddsParam.setDdsNumruc(num_ruc);
				List<Dds> ddsList = ddsDao.selectByExample(ddsParam);
				
				Ddp ddp = new Ddp();
				Dds dds = new Dds();
				if (ddpList.size() > 0 && ddsList.size() > 0) {
					ddp = ddpList.get(0);
					dds = ddsList.get(0);
				}
				else {
					log.error("No fue posible validar el ruc contra el padron RUC: " + num_ruc);
					throw new Exception("No fue posible validar el ruc contra el padron RUC.");
				}
				
				//evealuando Persona natual--------------------------------------------------------
				if (log.isDebugEnabled())log.debug("PERSONA NATUAL:"+num_ruc.trim().substring(0, 2));
				//if (num_ruc.trim().substring(0, 2).equals("10") ){
				if ("01".equals(ddp.getDdpIdenti())) {//PERSONA NATURAL
					
					num_doc = dds.getDdsNrodoc();//num_dni = num_ruc.substring(2,10);
					tip_doc = dds.getDdsDocide();
					tip_doc_siga = " ";
					
					parm01.clear();
					parm01.put("cod_par", "3076");
					parm01.put("cod_arg", tip_doc);
					T01ParametroBean t01Bean=t01ParametroDao.recuperarParametro(parm01);
					if (t01Bean != null && t01Bean.getNom_largo() != null) {
						tip_doc_siga = t01Bean.getNom_largo().trim(); 
					}
					
					if (log.isDebugEnabled())log.debug("DNI:"+num_ruc.trim().substring(2, 10));
					//buscando persona natural-------------------------------------------------------
					parm.clear();
					 parm.put("num_doc", num_doc);
					 parm.put("tipod_doc", tip_doc_siga);
					//verificar si existe en TABLA PERSONA
					persona=personaDao.recuperarPersona(parm);
					if (persona !=null){
						if(persona.getRuc_persona() !=null){
							if (persona.getRuc_persona()!=""){
								parm.clear();
								if (log.isDebugEnabled())log.debug("RUC Persona:" +persona.getRuc_persona() );
								parm.put("num_ruc", persona.getRuc_persona());
								if (personaDao.validaRUC(parm).equals("NO EXISTE" )){
									rpta_mensaje="La persona "+persona.getNombre_razon()+", se encuentra registrada en el sistema con un RUC no correcto ("+persona.getRuc_persona()+"). Verificar su registro en el sistema.";
								}else if ( ! num_ruc.equals( persona.getRuc_persona())){
									rpta_mensaje="La persona "+persona.getNombre_razon()+", se encuentra registrada en el sistema con un RUC diferente ("+persona.getRuc_persona()+"). Verificar su registro en el sistema.";
								}
							}else{
								if (log.isDebugEnabled())log.debug("Persona Natural sin RUC :"+ persona.getNombre_razon());
								

								parm01.put("num_ruc", num_ruc);
								String estado=personaDao.recuperaRUCestado(parm01);
								if (estado!=null){
									if (estado.equals("00")){
											persona.setRuc_persona(num_ruc);
											this.registrarActualizaRucNew(persona);
									}else{
										parm01.clear();
										parm01.put("cod_par", "4014%");
										parm01.put("cod_arg", estado+"%");
										T01ParametroBean estado_ruc=t01ParametroDao.recuperarParametro(parm01);
										rpta_mensaje="El RUC se encuentra en el estado ("+estado_ruc.getNom_largo()+"), no puede ser registrado";
									}
								}
							}
						}else{
							parm01.put("num_ruc", num_ruc);
							String estado=personaDao.recuperaRUCestado(parm01);
						
							if (estado!=null){
								if (estado.equals("00")){
									if (log.isDebugEnabled())log.debug("Persona Natural sin RUC(NULL) :"+ persona.getNombre_razon() + "-"+num_ruc );
									persona.setRuc_persona(num_ruc);
									this.registrarActualizaRucNew(persona);
								}else{
									parm01.clear();
									parm01.put("cod_par", "4014%");
									parm01.put("cod_arg", estado+"%");
									T01ParametroBean estado_ruc=t01ParametroDao.recuperarParametro(parm01);
									rpta_mensaje="El RUC se encuentra en el estado ("+estado_ruc.getNom_largo()+"), no puede ser registrado";
								}
							}
						}
					}else{
						log.info("contratista no Existe ni como DNI");
						if (log.isDebugEnabled())log.debug("Persona Natual- es Contratista");
						//evaluando contristas-----------------------------------------------------
						parm.clear();
						parm.put("num_ruc", num_ruc);
						//parm.put("user", user);
						parm=this.spRUCdatosNew( parm);
						razon_social= (String) parm.get("razon_social");
						cod_ubigeo	= (String) parm.get("cod_ubigeo");
						tipo_via	= (String) parm.get("tipo_via");
						nom_via		= (String) parm.get("nom_via");
						num_via		= (String) parm.get("num_via");
						num_interior= (String) parm.get("num_interior");
						tipo_zona	= (String) parm.get("tipo_zona");
						nom_zona	= (String) parm.get("nom_zona");
						referencia	= (String) parm.get("referencia");
						ls_ruc_estado	= (String) parm.get("ruc_estado");
						if (razon_social==null || razon_social.equals("")){
						}else{
							// Verificando si el RUC esta Activo--------------------------
							if (ls_ruc_estado!=null){
								if (ls_ruc_estado.equals("00")){ //Ruc Activo
									if (nom_via!=null  ){
										if(!nom_via.equals("-")){
											ls_direccion += " "+nom_via;
										}
									}
									if (num_via!=null ){
										if(!num_via.equals("-")){
											ls_direccion += " "+num_via;
										}
									}
									if (nom_zona!=null ){
										if(!nom_zona.equals("-")){
											ls_direccion += " "+nom_zona;
										}
									}
									if (referencia!=null ){
										if(!referencia.equals("-")){
											ls_direccion += " "+referencia;
										}
									}
									
									if (cod_ubigeo!=null ){
										if(cod_ubigeo.equals("-") || cod_ubigeo.length() <6 ){
											cod_ubigeo = "";
										}
									}else{
										cod_ubigeo = "";
									}
									persona = new PersonaBean();
									persona.setRuc_persona(num_ruc);
									persona.setNombre_razon(razon_social);
									persona.setCod_ubigeo(cod_ubigeo);

									persona.setDireccion_per(ls_direccion);
									persona.setReferencia(referencia);
									
									persona.setTipo_persona("NATURAL");//"JURIDICA" o "NATURAL"
									persona.setTipo_documento(tip_doc_siga);//04: RUC
									persona.setNumero_documento(num_doc);
									
									persona.setUser_crea(userWeb);
									persona.setFech_crea(new Date());
									persona.setUser_modi (userWeb);
									persona.setFech_modi(new Date());

									persona.setNombres(FormatoConstantes.CADENA_VACIA);
									persona.setApellido_pat(FormatoConstantes.CADENA_VACIA);
									persona.setApellido_mat(FormatoConstantes.CADENA_VACIA);
									persona.setFecha_nacimiento(null);
									
									if ("01".equals(tip_doc_siga)) {
										T733Pernat t733Param = new T733Pernat();
										t733Param.setTipDocidePnat("01");
										t733Param.setNumDocidePnat(num_doc);
										List<T733Pernat> t733List = t733pernatDao.selectByExample(t733Param);
										
										T733Pernat t733Pernat = new T733Pernat();
										if (t733List.size() > 0) {
											t733Pernat = t733List.get(0);

											persona.setNombres(t733Pernat.getDesNombrePnat());
											persona.setApellido_pat(t733Pernat.getDesApepatPnat());
											persona.setApellido_mat(t733Pernat.getDesApematPnat());
											persona.setFecha_nacimiento(t733Pernat.getFecNacPnat());
										}
									}
									
									ls_cod_perona=this.registrarNuevaSoloPersonaNew(persona);
								}else{//Ruc Inactivo
									parm01.clear();
									parm01.put("cod_par", "4014%");
									parm01.put("cod_arg", ls_ruc_estado.trim()+"%");
									T01ParametroBean estado_ruc=t01ParametroDao.recuperarParametro(parm01);
									rpta_mensaje="El RUC se encuentra en el estado ("+estado_ruc.getNom_largo()+"), no puede ser registrado";
								}
							}
						}
					}
				}else {
					if (log.isDebugEnabled())log.debug("NOOOO es Persona Natual- es Juridica");
					//evaluando contristas-----------------------------------------------------
					parm.clear();
					parm.put("num_ruc", num_ruc);
					//parm.put("user", user);

					parm=this.spRUCdatosNew( parm);
					razon_social= (String) parm.get("razon_social");
					cod_ubigeo	= (String) parm.get("cod_ubigeo");
					tipo_via	= (String) parm.get("tipo_via");
					nom_via		= (String) parm.get("nom_via");
					num_via		= (String) parm.get("num_via");
					num_interior= (String) parm.get("num_interior");
					tipo_zona	= (String) parm.get("tipo_zona");
					nom_zona	= (String) parm.get("nom_zona");
					referencia	= (String) parm.get("referencia");
					ls_ruc_estado	= (String) parm.get("ruc_estado");
					if (razon_social==null || razon_social.equals(FormatoConstantes.CADENA_VACIA)){
					}else{
						// Verificando si el RUC esta Activo--------------------------
						if (ls_ruc_estado!=null){
							if (ls_ruc_estado.equals("00")){ //Ruc Activo
								if (nom_via!=null  ){
									if(!nom_via.equals("-")){
										ls_direccion += " "+nom_via;
									}
								}
								if (num_via!=null ){
									if(!num_via.equals("-")){
										ls_direccion += " "+num_via;
									}
								}
								if (nom_zona!=null ){
									if(!nom_zona.equals("-")){
										ls_direccion += " "+nom_zona;
									}
								}
								if (referencia!=null ){
									if(!referencia.equals("-")){
										ls_direccion += " "+referencia;
									}
								}
								
								if (cod_ubigeo!=null ){
									if(cod_ubigeo.equals("-") || cod_ubigeo.length() <6 ){
										cod_ubigeo = "";
									}
								}else{
									cod_ubigeo = "";
								}
								persona = new PersonaBean();
								persona.setRuc_persona(num_ruc);
								persona.setNombre_razon(razon_social);
								persona.setCod_ubigeo(cod_ubigeo);
								persona.setDireccion_per(ls_direccion);
								persona.setReferencia(referencia);
								
								persona.setTipo_persona("JURIDICA");//"JURIDICA" o "NATURAL"
								persona.setTipo_documento("04");//04: RUC
								persona.setNumero_documento(num_ruc);
								
								persona.setUser_crea(userWeb);
								persona.setFech_crea(new Date());
								persona.setUser_modi (userWeb);
								persona.setFech_modi(new Date());
								
								persona.setNombres(FormatoConstantes.CADENA_VACIA);
								persona.setApellido_pat(FormatoConstantes.CADENA_VACIA);
								persona.setApellido_mat(FormatoConstantes.CADENA_VACIA);
								persona.setFecha_nacimiento(null);
								
								// FALTA AUDITORIA
								// EN CASO DE JALAR ESTADO CONSIDERAR SUBSTRING HASTA 18 caracteres
								
								ls_cod_perona=this.registrarNuevaSoloPersonaNew(persona);
							}else{//Ruc Inactivo
								parm01.clear();
								parm01.put("cod_par", "4014%");
								parm01.put("cod_arg", ls_ruc_estado.trim()+"%");
								T01ParametroBean estado_ruc=t01ParametroDao.recuperarParametro(parm01);
								rpta_mensaje="El RUC se encuentra en el estado ("+estado_ruc.getNom_largo()+"), no puede ser registrado";
							}
						}
					}
				}
			}
			
			// Verificar 
			return persona;
		}catch (Exception ex) {
			   log.error("Error en RequerimientoNoProgramadoServiceImpl.recuperarContratistaPadronNew: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
			} 
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.recuperarContratistaPadronNew");
		}
	}
	
	
	
	@Override
	public String registrarNuevaPersona(PersonaBean persona, String user)
			throws ServiceException {
		DataSource dataSource;
		MaestroContratistasBean contratista = new MaestroContratistasBean();
		String ls_dep=FormatoConstantes.CADENA_VACIA;
		String ls_prov=FormatoConstantes.CADENA_VACIA;
		String ls_dis=FormatoConstantes.CADENA_VACIA;
		String ls_pais=FormatoConstantes.CADENA_VACIA;
		String ls_id_persona=FormatoConstantes.CADENA_VACIA;
		if(persona.getCod_ubigeo()!=null){
			String ls_ubigeo = persona.getCod_ubigeo();
			if (ls_ubigeo.length() ==6){
				ls_dep	= 	ls_ubigeo.substring(0,2);
				ls_prov	= 	ls_ubigeo.substring(2,4);
				ls_dis	=	ls_ubigeo.substring(4,6);
				ls_pais	=	FormatoConstantes.CADENA_VACIA;
			}
		}
		try {
			ls_id_persona=personaDao.recuperarIDPersona();
			log.debug("ID_PERSONA NEW:"+ls_id_persona);
			persona.setCodigo_persona(ls_id_persona);
			persona.setCod_depa(ls_dep);
			persona.setCod_prov(ls_prov);
			persona.setCod_dis(ls_dis);
			persona.setCod_pais(ls_pais);
			
			contratista.setCod_cont(ls_id_persona);
			dataSource = obtenerDataSource("sig", false);
			log.info("insertar PERSONA");
			accesoDao.setUsuarioAcceso(dataSource,"USER", user);
			
			personaDao.insertar(dataSource, persona);
			log.info("insertar contratista");
			maestroContratistasDao.insertar(dataSource, contratista);
			accesoDao.setUsuarioAccesoNull(dataSource,"USER");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error("Error en registrarNuevaPersona " + e.getMessage());
			throw new ServiceException(this, e);
		}
		return ls_id_persona;
	}
	
	// JMCR PADRON RUC	
	@Override
	public String registrarNuevaSoloPersonaNew(PersonaBean persona)
			throws Exception {
	//	DataSource dataSource;
		String ls_dep		=	FormatoConstantes.CADENA_VACIA;
		String ls_prov		=	FormatoConstantes.CADENA_VACIA;
		String ls_dis		=	FormatoConstantes.CADENA_VACIA;
		String ls_pais		=	FormatoConstantes.CADENA_VACIA;
		String ls_id_persona=	FormatoConstantes.CADENA_VACIA;
		
		if(persona.getCod_ubigeo()!=null){
			String ls_ubigeo = persona.getCod_ubigeo();
			if (ls_ubigeo.length() ==6){
				ls_dep	= 	ls_ubigeo.substring(0,2);
				ls_prov	= 	ls_ubigeo.substring(2,4);
				ls_dis	=	ls_ubigeo.substring(4,6);
				ls_pais	=	FormatoConstantes.CADENA_VACIA;;
			}
		}
		try {
			ls_id_persona=personaDao.recuperarIDPersona();
			log.debug("ID_PERSONA NEW:"+ls_id_persona);
			persona.setCodigo_persona(ls_id_persona);
			persona.setCod_depa(ls_dep);
			persona.setCod_prov(ls_prov);
			persona.setCod_dis(ls_dis);
			persona.setCod_pais(ls_pais);
			
			log.info("insertar PERSONA");
			personaDao.insertarNew2( persona);
			log.info("insertar contratista");
			
		} catch (Exception e) {

			log.error("Error en registrarNuevaPersonaNew " + e.getMessage());
			throw new ServiceException(this, e);
		}
		return ls_id_persona;
	}	

	@Override
	public String registrarNuevaPersonaNew(PersonaBean persona)
			throws ServiceException {
	//	DataSource dataSource;
		MaestroContratistasBean contratista = new MaestroContratistasBean();
		String ls_dep		=	FormatoConstantes.CADENA_VACIA;
		String ls_prov		=	FormatoConstantes.CADENA_VACIA;
		String ls_dis		=	FormatoConstantes.CADENA_VACIA;
		String ls_pais		=	FormatoConstantes.CADENA_VACIA;
		String ls_id_persona=	FormatoConstantes.CADENA_VACIA;
		
		if(persona.getCod_ubigeo()!=null){
			String ls_ubigeo = persona.getCod_ubigeo();
			if (ls_ubigeo.length() ==6){
				ls_dep	= 	ls_ubigeo.substring(0,2);
				ls_prov	= 	ls_ubigeo.substring(2,4);
				ls_dis	=	ls_ubigeo.substring(4,6);
				ls_pais	=	FormatoConstantes.CADENA_VACIA;;
			}
		}
		try {
			ls_id_persona=personaDao.recuperarIDPersona();
			log.debug("ID_PERSONA NEW:"+ls_id_persona);
			persona.setCodigo_persona(ls_id_persona);
			persona.setCod_depa(ls_dep);
			persona.setCod_prov(ls_prov);
			persona.setCod_dis(ls_dis);
			persona.setCod_pais(ls_pais);
			
			contratista.setCod_cont(ls_id_persona);
			//dataSource = obtenerDataSource("sig", false);
			log.info("insertar PERSONA");
			personaDao.insertarNew( persona);
			log.info("insertar contratista");
			maestroContratistasDao.insertarNew( contratista);
			
		} catch (Exception e) {

			log.error("Error en registrarNuevaPersonaNew " + e.getMessage());
			throw new ServiceException(this, e);
		}
		return ls_id_persona;
	}
	/**
     * Verifica si el Empleado es un Jefe Aprobador Encargado
     * @param String - Codigo de Empleado
     * @return String - (S=si , N=no)
     */ 
	@Override
	public String esAprobadorEncargado(String cod_empl) {
		if (log.isDebugEnabled()) log.debug("Inicio - RegistroPersonalServiceImpl.esAprobadorEncargado");
		try {
			DataSource dataSource = obtenerDataSource("sig", false);
			return maestroPersonalDao.esAprobadorEncargado( cod_empl);
		} catch (Exception ex) {
			   log.error("Error en RegistroPersonalServiceImpl.esAprobadorEncargado: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
		}
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RegistroPersonalServiceImpl.esAprobadorEncargado");
		}
	}


	@Override
	public void registrarActualizaRuc(PersonaBean persona, String user)
			throws ServiceException {
		DataSource dataSource;
		try {
			dataSource = obtenerDataSource("sig", false);
			
			accesoDao.setUsuarioAcceso(dataSource,"USER", user);
			personaDao.updateRuc(dataSource, persona);
			accesoDao.setUsuarioAccesoNull(dataSource,"USER");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error("Error en registrarActualizaRuc " + e.getMessage());
			throw new ServiceException(this, e);
		}
	
	}
		
	@Override
	public void registrarActualizaRucNew(PersonaBean persona)
			throws ServiceException {
		try {
			personaDao.updateRucNew( persona);
		} catch (Exception e) {
			//e.printStackTrace();
			log.error("Error en registrarActualizaRucNew " + e.getMessage());
			throw new ServiceException(this, e);
		}
	}
	@Override
	public PersonaBean recuperarPersona(Map<String, Object> params)
			throws ServiceException {

		return personaDao.recuperarPersona(params);
	}


	@Override
	public String validaRUC(Map<String, Object> params)
			throws ServiceException{
		
		return personaDao.validaRUC(params);
	}


	@Override
	public Map<String, Object>  spRUCdatos( Map<String, Object> params)
			throws ServiceException {

		String user = (String)params.get("user");
		DataSource dataSource;
		
		try {
			dataSource = obtenerDataSource("sig", false);
			log.info("spRUCdatos");
			accesoDao.setUsuarioAcceso(dataSource,"USER", user);
			personaDao.spRUCdatos(dataSource, params);
			accesoDao.setUsuarioAccesoNull(dataSource,"USER");
			
		} catch (Exception e) {

			//e.printStackTrace();
			log.error("Error en spRUCdatos " + e.getMessage());
			throw new ServiceException(this, e);
			
		}
		return  params;
		
	}
	
	@Override
	public Map<String, Object>  spRUCdatosNew( Map<String, Object> params)
			throws ServiceException {
		
		try {
			log.info("spRUCdatosNew");
			personaDao.spRUCdatosNew( params);
		} catch (Exception e) {

			//e.printStackTrace();
			log.error("Error en spRUCdatosNew " + e.getMessage());
			throw new ServiceException(this, e);
		}
		return  params;
		
	}
	/**
     * Recuperar Contratista .
     * @param Map<String, Object> params 
     * @return Collection - lista de items
     */
	@Override
	public MaestroContratistasBean recuperarContratista(Map parm) {
		MaestroContratistasBean contratista = null;
		contratista=maestroContratistasDao.recuperarMaestroContratistas(parm);
		return contratista;
	}
	
	
	/**
     * Recuperar Contratista .Padron
     * @param Map<String, Object> params 
     * @return Collection - lista de items
     */
	@Override
	public MaestroContratistasBean recuperarContratistaPadron(Map parm) {
		if (log.isDebugEnabled()) log.debug("Inicio - RegistroPersonalServiceImpl.recuperarContratista");
		PersonaBean persona= null;
		MaestroContratistasBean contratista = null;
		
		try {
			
		    String rpta_mensaje="";
			String num_ruc = (String) parm.get("num_ruc");
			String user = (String) parm.get("user");
			String num_dni=null;
			String razon_social	="";
			String cod_ubigeo	="";
			String tipo_via		="";
			String nom_via		="";
			String num_via		="";
			String num_interior	="";
			String tipo_zona	="";
			String nom_zona		="";
			String referencia	="";
			String ls_direccion ="";
			String ls_cod_perona="";
			String ls_ruc_estado="";
			//verificar si existe en TABLA PERSONA
			Map<String,Object>  parm01 = new HashMap<String,Object>();
			
				persona=personaDao.recuperarPersona(parm);
				if ( persona==null){
					//importar de padron
					//evealuando Persona natual--------------------------------------------------------
					if (log.isDebugEnabled())log.debug("PERSONA NATUAL:"+num_ruc.trim().substring(0, 2));
					if (num_ruc.trim().substring(0, 2).equals("10") ){
						num_dni = num_ruc.substring(2,10);
						if (log.isDebugEnabled())log.debug("DNI:"+num_ruc.trim().substring(2, 10));
						//buscando persona natual-------------------------------
						parm.clear();
						 parm.put("num_doc", num_dni);
						 parm.put("tipod_doc", "01");
						//verificar si existe en TABLA PERSONA
						persona=personaDao.recuperarPersona(parm);
						if (persona !=null){
							if(persona.getRuc_persona() !=null){
								if (persona.getRuc_persona()!=""){
									parm.clear();
									if (log.isDebugEnabled())log.debug("RUC Persona:" +persona.getRuc_persona() );
									parm.put("num_ruc", persona.getRuc_persona());
									if (personaDao.validaRUC(parm).equals("NO EXISTE" )){
										rpta_mensaje="La persona "+persona.getNombre_razon()+", se encuentra registrada en el sistema con un RUC no correcto ("+persona.getRuc_persona()+"). Verificar su registro en el sistema.";
									}else if ( ! num_ruc.equals( persona.getRuc_persona())){
										rpta_mensaje="La persona "+persona.getNombre_razon()+", se encuentra registrada en el sistema con un RUC diferente ("+persona.getRuc_persona()+"). Verificar su registro en el sistema.";
									}
									contratista = new MaestroContratistasBean();
									contratista.setCod_cont("-1");
									contratista.setNum_ruc("-1");
									contratista.setRaz_social(rpta_mensaje);
								}else{
									if (log.isDebugEnabled())log.debug("Persona Natural sin RUC :"+ persona.getNombre_razon());
									

									parm01.put("num_ruc", num_ruc);
									String estado=personaDao.recuperaRUCestado(parm01);
									if (estado!=null){
									if (estado.equals("00")){
											persona.setRuc_persona(num_ruc);
											this.registrarActualizaRuc(persona, user);
											contratista = new MaestroContratistasBean();
											contratista.setCod_cont(persona.getCodigo_persona());
											contratista.setNum_ruc(persona.getRuc_persona());
											contratista.setRaz_social(persona.getNombre_razon());	
									}else{
										parm01.clear();
										parm01.put("cod_par", "4014%");
										parm01.put("cod_arg", estado+"%");
										T01ParametroBean estado_ruc=t01ParametroDao.recuperarParametro(parm01);
										rpta_mensaje="El RUC se encuentra en el estado ("+estado_ruc.getNom_largo()+"), no puede ser registrado";
										contratista = new MaestroContratistasBean();
										contratista.setCod_cont("-1");
										contratista.setNum_ruc("-1");
										contratista.setRaz_social(rpta_mensaje);
									}
									}else{
										contratista = new MaestroContratistasBean();
										contratista.setCod_cont("-1");
										contratista.setNum_ruc("-1");
										contratista.setRaz_social("El  RUC de Contratista no existe en Padron SUNAT");
									}
								}
							}else{
								parm01.put("num_ruc", num_ruc);
								String estado=personaDao.recuperaRUCestado(parm01);
							
								if (estado!=null){
									if (estado.equals("00")){
									if (log.isDebugEnabled())log.debug("Persona Natural sin RUC(NULL) :"+ persona.getNombre_razon() + "-"+num_ruc );
									persona.setRuc_persona(num_ruc);
									this.registrarActualizaRuc(persona, user);
									contratista = new MaestroContratistasBean();
									contratista.setCod_cont(persona.getCodigo_persona());
									contratista.setNum_ruc(persona.getRuc_persona());
									contratista.setRaz_social(persona.getNombre_razon());	
									}else{
										parm01.clear();
										parm01.put("cod_par", "4014%");
										parm01.put("cod_arg", estado+"%");
										T01ParametroBean estado_ruc=t01ParametroDao.recuperarParametro(parm01);
										rpta_mensaje="El RUC se encuentra en el estado ("+estado_ruc.getNom_largo()+"), no puede ser registrado";
										contratista = new MaestroContratistasBean();
										contratista.setCod_cont("-1");
										contratista.setNum_ruc("-1");
										contratista.setRaz_social(rpta_mensaje);
									}
									}else{
										contratista = new MaestroContratistasBean();
										contratista.setCod_cont("-1");
										contratista.setNum_ruc("-1");
										contratista.setRaz_social("El  RUC de Contratista no existe en Padron SUNAT");
									}
							}
						}else{
							log.info("contratista no Existe ni como DNI");
							if (log.isDebugEnabled())log.debug("Persona Natual- es Contratista");
							//evaluando contristas-----------------------------------------------------
							parm.clear();
							parm.put("num_ruc", num_ruc);
							parm.put("user", user);
							parm=this.spRUCdatos( parm);
							razon_social= (String) parm.get("razon_social");
							cod_ubigeo	= (String) parm.get("cod_ubigeo");
							tipo_via	= (String) parm.get("tipo_via");
							nom_via		= (String) parm.get("nom_via");
							num_via		= (String) parm.get("num_via");
							num_interior= (String) parm.get("num_interior");
							tipo_zona	= (String) parm.get("tipo_zona");
							nom_zona	= (String) parm.get("nom_zona");
							referencia	= (String) parm.get("referencia");
							ls_ruc_estado	= (String) parm.get("ruc_estado");
							if (razon_social==null || razon_social.equals("")){
								contratista = new MaestroContratistasBean();
								contratista.setCod_cont("-1");
								contratista.setNum_ruc("-1");
								contratista.setRaz_social("El RUC de Contratista no existe");
							}else{
								// Verificando si el RUC esta Activo--------------------------
								if (ls_ruc_estado!=null){
								if (ls_ruc_estado.equals("00")){ //Ruc Activo
									if (nom_via!=null  ){
										if(!nom_via.equals("-")){
											ls_direccion += " "+nom_via;
										}
									}
									if (num_via!=null ){
										if(!num_via.equals("-")){
											ls_direccion += " "+num_via;
										}
									}
									if (nom_zona!=null ){
										if(!nom_zona.equals("-")){
											ls_direccion += " "+nom_zona;
										}
									}
									if (referencia!=null ){
										if(!referencia.equals("-")){
											ls_direccion += " "+referencia;
										}
									}
									
									if (cod_ubigeo!=null ){
										if(cod_ubigeo.equals("-") || cod_ubigeo.length() <6 ){
											cod_ubigeo = "";
										}
									}else{
										cod_ubigeo = "";
									}
									persona = new PersonaBean();
									persona.setRuc_persona(num_ruc);
									persona.setCod_ubigeo(cod_ubigeo);
									persona.setNombre_razon(razon_social);
									persona.setDireccion_per(ls_direccion);
									ls_cod_perona=this.registrarNuevaPersona(persona, user);
									contratista = new MaestroContratistasBean();
									contratista.setCod_cont(ls_cod_perona);
									contratista.setNum_ruc(persona.getRuc_persona());
									contratista.setRaz_social(persona.getNombre_razon());	
								}else{//Ruc Inactivo
									parm01.clear();
									parm01.put("cod_par", "4014%");
									parm01.put("cod_arg", ls_ruc_estado.trim()+"%");
									T01ParametroBean estado_ruc=t01ParametroDao.recuperarParametro(parm01);
									rpta_mensaje="El RUC se encuentra en el estado ("+estado_ruc.getNom_largo()+"), no puede ser registrado";
									contratista = new MaestroContratistasBean();
									contratista.setCod_cont("-1");
									contratista.setNum_ruc("-1");
									contratista.setRaz_social(rpta_mensaje);
								}
								}else{
									contratista = new MaestroContratistasBean();
									contratista.setCod_cont("-1");
									contratista.setNum_ruc("-1");
									contratista.setRaz_social("El  RUC de Contratista o su Estado Actual no existe en Padron SUNAT");
								}
							}
						}
					}else {
						if (log.isDebugEnabled())log.debug("NOOOO es Persona Natual- es Contratista");
						//evaluando contristas-----------------------------------------------------
						parm.clear();
						parm.put("num_ruc", num_ruc);
						parm.put("user", user);
						parm=this.spRUCdatos( parm);
						razon_social= (String) parm.get("razon_social");
						cod_ubigeo	= (String) parm.get("cod_ubigeo");
						tipo_via	= (String) parm.get("tipo_via");
						nom_via		= (String) parm.get("nom_via");
						num_via		= (String) parm.get("num_via");
						num_interior= (String) parm.get("num_interior");
						tipo_zona	= (String) parm.get("tipo_zona");
						nom_zona	= (String) parm.get("nom_zona");
						referencia	= (String) parm.get("referencia");
						ls_ruc_estado	= (String) parm.get("ruc_estado");
						if (razon_social==null || razon_social.equals("")){
							contratista = new MaestroContratistasBean();
							contratista.setCod_cont("-1");
							contratista.setNum_ruc("-1");
							contratista.setRaz_social("El RUC de Contratista no existe");
						}else{
							// Verificando si el RUC esta Activo--------------------------
							if (ls_ruc_estado!=null){
							if (ls_ruc_estado.equals("00")){ //Ruc Activo
								if (nom_via!=null  ){
									if(!nom_via.equals("-")){
										ls_direccion += " "+nom_via;
									}
								}
								if (num_via!=null ){
									if(!num_via.equals("-")){
										ls_direccion += " "+num_via;
									}
								}
								if (nom_zona!=null ){
									if(!nom_zona.equals("-")){
										ls_direccion += " "+nom_zona;
									}
								}
								if (referencia!=null ){
									if(!referencia.equals("-")){
										ls_direccion += " "+referencia;
									}
								}
								
								if (cod_ubigeo!=null ){
									if(cod_ubigeo.equals("-") || cod_ubigeo.length() <6 ){
										cod_ubigeo = "";
									}
								}else{
									cod_ubigeo = "";
								}
								persona = new PersonaBean();
								persona.setRuc_persona(num_ruc);
								persona.setCod_ubigeo(cod_ubigeo);
								persona.setNombre_razon(razon_social);
								persona.setDireccion_per(ls_direccion);
								ls_cod_perona=this.registrarNuevaPersona(persona, user);
								contratista = new MaestroContratistasBean();
								contratista.setCod_cont(ls_cod_perona);
								contratista.setNum_ruc(persona.getRuc_persona());
								contratista.setRaz_social(persona.getNombre_razon());	
							}else{//Ruc Inactivo
								parm01.clear();
								parm01.put("cod_par", "4014%");
								parm01.put("cod_arg", ls_ruc_estado.trim()+"%");
								T01ParametroBean estado_ruc=t01ParametroDao.recuperarParametro(parm01);
								rpta_mensaje="El RUC se encuentra en el estado ("+estado_ruc.getNom_largo()+"), no puede ser registrado";
								contratista = new MaestroContratistasBean();
								contratista.setCod_cont("-1");
								contratista.setNum_ruc("-1");
								contratista.setRaz_social(rpta_mensaje);
							}
							}else{
								contratista = new MaestroContratistasBean();
								contratista.setCod_cont("-1");
								contratista.setNum_ruc("-1");
								contratista.setRaz_social("El  RUC de Contratista o su Estado Actual no existe en Padron SUNAT");
							}
						}
					}
				}else{
					//Si existe en Persona verifica su ruc esta Activo
						if (log.isDebugEnabled())log.debug("Persona No nulo");
					
							parm01.put("num_ruc", num_ruc);
							String estado=personaDao.recuperaRUCestado(parm01);
							if (estado!=null){
								if (estado.equals("00")){
									//if (!num_ruc.trim().substring(0, 2).equals("10")){
										
										//VRIFICANDO SI ESTA EN MAESTRO CONTRATISTA
										parm.clear();
										parm.put("num_ruc", num_ruc);
										
										contratista = maestroContratistasDao.recuperarMaestroContratistas(parm);
										if (contratista == null){
											contratista = new MaestroContratistasBean();
											contratista.setCod_cont(persona.getCodigo_persona());
											if (log.isDebugEnabled())log.debug("registrarNuevoContratista");
											this.registrarNuevoContratista(contratista, user);
										}else{
											if (log.isDebugEnabled())log.debug("Contratista Existe");
										}
										contratista = new MaestroContratistasBean();
										contratista.setCod_cont(persona.getCodigo_persona());
										contratista.setNum_ruc(persona.getRuc_persona());
										contratista.setRaz_social(persona.getNombre_razon());	
									//}
								}else{
									parm01.clear();
									parm01.put("cod_par", "4014%");
									parm01.put("cod_arg", estado+"%");
									T01ParametroBean estado_ruc=t01ParametroDao.recuperarParametro(parm01);
									rpta_mensaje="El RUC se encuentra en el estado ("+estado_ruc.getNom_largo()+"), no puede ser registrado";
									contratista = new MaestroContratistasBean();
									contratista.setCod_cont("-1");
									contratista.setNum_ruc("-1");
									contratista.setRaz_social(rpta_mensaje);
								}
							}else{
									contratista = new MaestroContratistasBean();
									contratista.setCod_cont("-1");
									contratista.setNum_ruc("-1");
									contratista.setRaz_social("El  RUC de Contratista no existe en el Padron SUNAT");
							}
				}
			
			// Verificar 
			//contratista=maestroContratistasDao.recuperarMaestroContratistas(parm);
			return contratista;
		}catch (Exception ex) {
			   log.error("Error en RegistroPersonalServiceImpl.recuperarContratista: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
			} 
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RegistroPersonalServiceImpl.recuperarContratista");
		}
	}

	
	
	/**
     * Recuperar Contratista .Padron
     * @param Map<String, Object> params 
     * @return Collection - lista de items
     */
	@Override
	public MaestroContratistasBean recuperarContratistaPadronNew(Map parm) {
		if (log.isDebugEnabled()) log.debug("Inicio - RegistroPersonalServiceImpl.recuperarContratistaPadronNew");
		PersonaBean persona= null;
		MaestroContratistasBean contratista = null;
		
		try {
			
		    String rpta_mensaje=FormatoConstantes.CADENA_VACIA;
			String num_ruc = (String) parm.get("num_ruc");
			//String user = (String) parm.get("user");
			String num_dni=null;
			String razon_social	=FormatoConstantes.CADENA_VACIA;
			String cod_ubigeo	=FormatoConstantes.CADENA_VACIA;
			String tipo_via		=FormatoConstantes.CADENA_VACIA;
			String nom_via		=FormatoConstantes.CADENA_VACIA;
			String num_via		=FormatoConstantes.CADENA_VACIA;
			String num_interior	=FormatoConstantes.CADENA_VACIA;
			String tipo_zona	=FormatoConstantes.CADENA_VACIA;
			String nom_zona		=FormatoConstantes.CADENA_VACIA;
			String referencia	=FormatoConstantes.CADENA_VACIA;
			String ls_direccion =FormatoConstantes.CADENA_VACIA;
			String ls_cod_perona=FormatoConstantes.CADENA_VACIA;
			String ls_ruc_estado=FormatoConstantes.CADENA_VACIA;
			//verificar si existe en TABLA PERSONA
			Map<String,Object>  parm01 = new HashMap<String,Object>();
			
				persona=personaDao.recuperarPersona(parm);
				if ( persona==null){
					//importar de padron
					//evealuando Persona natual--------------------------------------------------------
					if (log.isDebugEnabled())log.debug("PERSONA NATUAL:"+num_ruc.trim().substring(0, 2));
					if (num_ruc.trim().substring(0, 2).equals("10") ){
						num_dni = num_ruc.substring(2,10);
						if (log.isDebugEnabled())log.debug("DNI:"+num_ruc.trim().substring(2, 10));
						//buscando persona natural-------------------------------------------------------
						parm.clear();
						 parm.put("num_doc", num_dni);
						 parm.put("tipod_doc", "01");
						//verificar si existe en TABLA PERSONA
						persona=personaDao.recuperarPersona(parm);
						if (persona !=null){
							if(persona.getRuc_persona() !=null){
								if (persona.getRuc_persona()!=""){
									parm.clear();
									if (log.isDebugEnabled())log.debug("RUC Persona:" +persona.getRuc_persona() );
									parm.put("num_ruc", persona.getRuc_persona());
									if (personaDao.validaRUC(parm).equals("NO EXISTE" )){
										rpta_mensaje="La persona "+persona.getNombre_razon()+", se encuentra registrada en el sistema con un RUC no correcto ("+persona.getRuc_persona()+"). Verificar su registro en el sistema.";
									}else if ( ! num_ruc.equals( persona.getRuc_persona())){
										rpta_mensaje="La persona "+persona.getNombre_razon()+", se encuentra registrada en el sistema con un RUC diferente ("+persona.getRuc_persona()+"). Verificar su registro en el sistema.";
									}
									contratista = new MaestroContratistasBean();
									contratista.setCod_cont("-1");
									contratista.setNum_ruc("-1");
									contratista.setRaz_social(rpta_mensaje);
								}else{
									if (log.isDebugEnabled())log.debug("Persona Natural sin RUC :"+ persona.getNombre_razon());
									

									parm01.put("num_ruc", num_ruc);
									String estado=personaDao.recuperaRUCestado(parm01);
									if (estado!=null){
									if (estado.equals("00")){
											persona.setRuc_persona(num_ruc);
											this.registrarActualizaRucNew(persona);
											contratista = new MaestroContratistasBean();
											contratista.setCod_cont(persona.getCodigo_persona());
											contratista.setNum_ruc(persona.getRuc_persona());
											contratista.setRaz_social(persona.getNombre_razon());	
									}else{
										parm01.clear();
										parm01.put("cod_par", "4014%");
										parm01.put("cod_arg", estado+"%");
										T01ParametroBean estado_ruc=t01ParametroDao.recuperarParametro(parm01);
										rpta_mensaje="El RUC se encuentra en el estado ("+estado_ruc.getNom_largo()+"), no puede ser registrado";
										contratista = new MaestroContratistasBean();
										contratista.setCod_cont("-1");
										contratista.setNum_ruc("-1");
										contratista.setRaz_social(rpta_mensaje);
									}
									}else{
										contratista = new MaestroContratistasBean();
										contratista.setCod_cont("-1");
										contratista.setNum_ruc("-1");
										contratista.setRaz_social("El  RUC de Contratista no existe en Padron SUNAT");
									}
								}
							}else{
								parm01.put("num_ruc", num_ruc);
								String estado=personaDao.recuperaRUCestado(parm01);
							
								if (estado!=null){
									if (estado.equals("00")){
									if (log.isDebugEnabled())log.debug("Persona Natural sin RUC(NULL) :"+ persona.getNombre_razon() + "-"+num_ruc );
									persona.setRuc_persona(num_ruc);
									this.registrarActualizaRucNew(persona);
									contratista = new MaestroContratistasBean();
									contratista.setCod_cont(persona.getCodigo_persona());
									contratista.setNum_ruc(persona.getRuc_persona());
									contratista.setRaz_social(persona.getNombre_razon());	
									}else{
										parm01.clear();
										parm01.put("cod_par", "4014%");
										parm01.put("cod_arg", estado+"%");
										T01ParametroBean estado_ruc=t01ParametroDao.recuperarParametro(parm01);
										rpta_mensaje="El RUC se encuentra en el estado ("+estado_ruc.getNom_largo()+"), no puede ser registrado";
										contratista = new MaestroContratistasBean();
										contratista.setCod_cont("-1");
										contratista.setNum_ruc("-1");
										contratista.setRaz_social(rpta_mensaje);
									}
									}else{
										contratista = new MaestroContratistasBean();
										contratista.setCod_cont("-1");
										contratista.setNum_ruc("-1");
										contratista.setRaz_social("El  RUC de Contratista no existe en Padron SUNAT");
									}
							}
						}else{
							log.info("contratista no Existe ni como DNI");
							if (log.isDebugEnabled())log.debug("Persona Natual- es Contratista");
							//evaluando contristas-----------------------------------------------------
							parm.clear();
							parm.put("num_ruc", num_ruc);
							//parm.put("user", user);
							parm=this.spRUCdatosNew( parm);
							razon_social= (String) parm.get("razon_social");
							cod_ubigeo	= (String) parm.get("cod_ubigeo");
							tipo_via	= (String) parm.get("tipo_via");
							nom_via		= (String) parm.get("nom_via");
							num_via		= (String) parm.get("num_via");
							num_interior= (String) parm.get("num_interior");
							tipo_zona	= (String) parm.get("tipo_zona");
							nom_zona	= (String) parm.get("nom_zona");
							referencia	= (String) parm.get("referencia");
							ls_ruc_estado	= (String) parm.get("ruc_estado");
							if (razon_social==null || razon_social.equals("")){
								contratista = new MaestroContratistasBean();
								contratista.setCod_cont("-1");
								contratista.setNum_ruc("-1");
								contratista.setRaz_social("El RUC de Contratista no existe");
								log.debug("El RUC de Contratista no existe");
							}else{
								// Verificando si el RUC esta Activo--------------------------
								if (ls_ruc_estado!=null){
								if (ls_ruc_estado.equals("00")){ //Ruc Activo
									if (nom_via!=null  ){
										if(!nom_via.equals("-")){
											ls_direccion += " "+nom_via;
										}
									}
									if (num_via!=null ){
										if(!num_via.equals("-")){
											ls_direccion += " "+num_via;
										}
									}
									if (nom_zona!=null ){
										if(!nom_zona.equals("-")){
											ls_direccion += " "+nom_zona;
										}
									}
									if (referencia!=null ){
										if(!referencia.equals("-")){
											ls_direccion += " "+referencia;
										}
									}
									
									if (cod_ubigeo!=null ){
										if(cod_ubigeo.equals("-") || cod_ubigeo.length() <6 ){
											cod_ubigeo = "";
										}
									}else{
										cod_ubigeo = "";
									}
									persona = new PersonaBean();
									persona.setRuc_persona(num_ruc);
									persona.setCod_ubigeo(cod_ubigeo);
									persona.setNombre_razon(razon_social);
									persona.setDireccion_per(ls_direccion);
									
									ls_cod_perona=this.registrarNuevaPersonaNew(persona);
									contratista = new MaestroContratistasBean();
									contratista.setCod_cont(ls_cod_perona);
									contratista.setNum_ruc(persona.getRuc_persona());
									contratista.setRaz_social(persona.getNombre_razon());	
								}else{//Ruc Inactivo
									parm01.clear();
									parm01.put("cod_par", "4014%");
									parm01.put("cod_arg", ls_ruc_estado.trim()+"%");
									T01ParametroBean estado_ruc=t01ParametroDao.recuperarParametro(parm01);
									rpta_mensaje="El RUC se encuentra en el estado ("+estado_ruc.getNom_largo()+"), no puede ser registrado";
									contratista = new MaestroContratistasBean();
									contratista.setCod_cont("-1");
									contratista.setNum_ruc("-1");
									contratista.setRaz_social(rpta_mensaje);
								}
								}else{
									contratista = new MaestroContratistasBean();
									contratista.setCod_cont("-1");
									contratista.setNum_ruc("-1");
									contratista.setRaz_social("El  RUC de Contratista o su Estado Actual no existe en Padron SUNAT");
								}
							}
						}
					}else {
						if (log.isDebugEnabled())log.debug("NOOOO es Persona Natual- es Contratista");
						//evaluando contristas-----------------------------------------------------
						parm.clear();
						parm.put("num_ruc", num_ruc);
						//parm.put("user", user);

						parm=this.spRUCdatosNew( parm);
						razon_social= (String) parm.get("razon_social");
						cod_ubigeo	= (String) parm.get("cod_ubigeo");
						tipo_via	= (String) parm.get("tipo_via");
						nom_via		= (String) parm.get("nom_via");
						num_via		= (String) parm.get("num_via");
						num_interior= (String) parm.get("num_interior");
						tipo_zona	= (String) parm.get("tipo_zona");
						nom_zona	= (String) parm.get("nom_zona");
						referencia	= (String) parm.get("referencia");
						ls_ruc_estado	= (String) parm.get("ruc_estado");
						if (razon_social==null || razon_social.equals(FormatoConstantes.CADENA_VACIA)){
							contratista = new MaestroContratistasBean();
							contratista.setCod_cont("-1");
							contratista.setNum_ruc("-1");
							log.debug("El RUC de Contratista no existe");
							contratista.setRaz_social("El RUC de Contratista no existe");
						}else{
							// Verificando si el RUC esta Activo--------------------------
							if (ls_ruc_estado!=null){
							if (ls_ruc_estado.equals("00")){ //Ruc Activo
								if (nom_via!=null  ){
									if(!nom_via.equals("-")){
										ls_direccion += " "+nom_via;
									}
								}
								if (num_via!=null ){
									if(!num_via.equals("-")){
										ls_direccion += " "+num_via;
									}
								}
								if (nom_zona!=null ){
									if(!nom_zona.equals("-")){
										ls_direccion += " "+nom_zona;
									}
								}
								if (referencia!=null ){
									if(!referencia.equals("-")){
										ls_direccion += " "+referencia;
									}
								}
								
								if (cod_ubigeo!=null ){
									if(cod_ubigeo.equals("-") || cod_ubigeo.length() <6 ){
										cod_ubigeo = "";
									}
								}else{
									cod_ubigeo = "";
								}
								persona = new PersonaBean();
								persona.setRuc_persona(num_ruc);
								persona.setCod_ubigeo(cod_ubigeo);
								persona.setNombre_razon(razon_social);
								persona.setDireccion_per(ls_direccion);
								ls_cod_perona=this.registrarNuevaPersonaNew(persona);
								contratista = new MaestroContratistasBean();
								contratista.setCod_cont(ls_cod_perona);
								contratista.setNum_ruc(persona.getRuc_persona());
								contratista.setRaz_social(persona.getNombre_razon());	
							}else{//Ruc Inactivo
								parm01.clear();
								parm01.put("cod_par", "4014%");
								parm01.put("cod_arg", ls_ruc_estado.trim()+"%");
								T01ParametroBean estado_ruc=t01ParametroDao.recuperarParametro(parm01);
								rpta_mensaje="El RUC se encuentra en el estado ("+estado_ruc.getNom_largo()+"), no puede ser registrado";
								contratista = new MaestroContratistasBean();
								contratista.setCod_cont("-1");
								contratista.setNum_ruc("-1");
								contratista.setRaz_social(rpta_mensaje);
							}
							}else{
								contratista = new MaestroContratistasBean();
								contratista.setCod_cont("-1");
								contratista.setNum_ruc("-1");
								contratista.setRaz_social("El  RUC de Contratista o su Estado Actual no existe en Padron SUNAT");
							}
						}
					}
				}else{
					//Si existe en Persona verifica su ruc esta Activo
						if (log.isDebugEnabled())log.debug("Persona No nulo");
					
							parm01.put("num_ruc", num_ruc);
							String estado=personaDao.recuperaRUCestado(parm01);
							if (estado!=null){
								if (estado.equals("00")){
									//if (!num_ruc.trim().substring(0, 2).equals("10")){
										
										//VRIFICANDO SI ESTA EN MAESTRO CONTRATISTA
										parm.clear();
										parm.put("num_ruc", num_ruc);
										
										contratista = maestroContratistasDao.recuperarMaestroContratistas(parm);
										if (contratista == null){
											contratista = new MaestroContratistasBean();
											contratista.setCod_cont(persona.getCodigo_persona());
											if (log.isDebugEnabled())log.debug("registrarNuevoContratista");
											this.registrarNuevoContratistaNew(contratista);
										}else{
											if (log.isDebugEnabled())log.debug("Contratista Existe");
										}
										contratista = new MaestroContratistasBean();
										contratista.setCod_cont(persona.getCodigo_persona());
										contratista.setNum_ruc(persona.getRuc_persona());
										contratista.setRaz_social(persona.getNombre_razon());	
									//}
								}else{
									parm01.clear();
									parm01.put("cod_par", "4014%");
									parm01.put("cod_arg", estado+"%");
									T01ParametroBean estado_ruc=t01ParametroDao.recuperarParametro(parm01);
									rpta_mensaje="El RUC se encuentra en el estado ("+estado_ruc.getNom_largo()+"), no puede ser registrado";
									contratista = new MaestroContratistasBean();
									contratista.setCod_cont("-1");
									contratista.setNum_ruc("-1");
									contratista.setRaz_social(rpta_mensaje);
								}
							}else{
									contratista = new MaestroContratistasBean();
									contratista.setCod_cont("-1");
									contratista.setNum_ruc("-1");
									contratista.setRaz_social("El  RUC de Contratista no existe en el Padron SUNAT");
							}
				}
			
			// Verificar 
			//contratista=maestroContratistasDao.recuperarMaestroContratistas(parm);
			return contratista;
		}catch (Exception ex) {
			   log.error("Error en RegistroPersonalServiceImpl.recuperarContratistaPadronNew: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
			} 
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RegistroPersonalServiceImpl.recuperarContratistaPadronNew");
		}
	}
	
	@Override
	public void registrarNuevoContratista(MaestroContratistasBean contratista,
			String user)
			throws ServiceException {

		DataSource dataSource;
		try {
			dataSource = obtenerDataSource("sig", false);
			log.info("Actualizando RUC");
			accesoDao.setUsuarioAcceso(dataSource,"USER", user);
			
			maestroContratistasDao.insertar(dataSource, contratista);
			accesoDao.setUsuarioAccesoNull(dataSource,"USER");

		} catch (Exception e) {
			
			//e.printStackTrace();			
			log.error("Error en registrarNuevoContratista " + e.getMessage());
			throw new ServiceException(this, e);
		}
	}
	
	
	@Override
	public void registrarNuevoContratistaNew(MaestroContratistasBean contratista)
			throws ServiceException {
		try {
			log.info("Actualizando registrarNuevoContratistaNew");
			maestroContratistasDao.insertarNew( contratista);
		} catch (Exception e) {
			//e.printStackTrace();			
			log.error("Error en registrarNuevoContratistaNew " + e.getMessage());
			throw new ServiceException(this, e);
		}
	}
	
	/**
     * Recuperar Jefe Inmediato Encargado de un Empleado
     * @param String - Codigo de Empleado
     * @return String - Codigo de Jefe Inmediato Encargado
     */ 
	@Override
	public String obtenerJefeInmediatoEncargado(String cod_empl, String cod_encargado) {
		if (log.isDebugEnabled()) log.debug("Inicio - RegistroPersonalServiceImpl.obtenerJefeInmediatoEncargado");
		try {
			DataSource dataSource = obtenerDataSource("sig", false);
			return maestroPersonalDao.obtenerJefeInmediatoEncargado( cod_empl,cod_encargado);
		} catch (Exception ex) {
			   log.error("Error en RegistroPersonalServiceImpl.obtenerJefeInmediatoEncargado: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
		}
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RegistroPersonalServiceImpl.obtenerJefeInmediatoEncargado");
		}
		
	}
	
	/**
     * Recuperar JefeAprobador Encargado de un Empleado
     * @param String - Codigo de Empleado
     * @return String - Codigo de Aprobado uuoo Encargado
     */ 
	@Override
	public String obtenerAprobadorEncargado(String cod_empl,String cod_encargado) {
		if (log.isDebugEnabled()) log.debug("Inicio - RegistroPersonalServiceImpl.obtenerAprobadorEncargado");
		try {
			DataSource dataSource = obtenerDataSource("sig", false);
			return maestroPersonalDao.obtenerAprobadorEncargado( cod_empl,cod_encargado);
		} catch (Exception ex) {
			   log.error("Error en RegistroPersonalServiceImpl.obtenerAprobadorEncargado: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
		}
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RegistroPersonalServiceImpl.obtenerAprobadorEncargado");
		}
	}
	
	
	
	/**
     * Recuperar Parametros de T01
     * @param Map<String, Object>  - params
     * @return T01ParametroBean - t01parametro
     */ 
	@Override
	public T01ParametroBean obtenerParametro(Map<String, Object> params ) {
		if (log.isDebugEnabled()) log.debug("Inicio - RegistroPersonalServiceImpl.obtenerParametro");
		try {
			
			return t01ParametroDao.recuperarParametro(params);
		} catch (Exception ex) {
			   log.error("Error en RegistroPersonalServiceImpl.obtenerParametro: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
		}
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RegistroPersonalServiceImpl.obtenerParametro");
		}
	}
	
	
	/**
     * Recuperar Parametros de T01
     * @param Map<String, Object>  - params
     * @return T01ParametroBean - t01parametro
     */ 
	@Override
	public Collection obtenerParametroLista(Map<String, Object> params ) {
		if (log.isDebugEnabled()) log.debug("Inicio - RegistroPersonalServiceImpl.obtenerParametroLista");
		List<Map<String, Object>> lsParametros= new ArrayList<Map<String,Object>>();
		 List<T01ParametroBean>lista = new ArrayList<T01ParametroBean>();
		 Map<String, Object> map;
		 
		try {
			 lista =( List<T01ParametroBean> ) t01ParametroDao.recuperarParametroLista(params);
			 for(T01ParametroBean data: lista){
					map = new HashMap<String, Object>();
					map.put("cod", data.getCod_argumento().trim());
					map.put("name", data.getNom_largo().trim());
					lsParametros.add(map);
			 }
			return lsParametros;
		} catch (Exception ex) {
			   log.error("Error en RegistroPersonalServiceImpl.obtenerParametroLista: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
		}
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RegistroPersonalServiceImpl.obtenerParametroLista");
		}
	}
	
	
	@Override
	public boolean determinaJefeInmediatoEncargadoMovilidad(String codigoEmpleado) throws Exception {
		String esJefe =  maestroPersonalDao.determinaJefeInmediatoEncargadoMovilidad(codigoEmpleado);		
		if(esJefe.equals("S")) return true;
		else return false;			
	}
	
	
	@Override
	public boolean determinaAutorizadorGastoMovilidad(String codigoEmpleado) throws Exception {
		String esJefe =  maestroPersonalDao.determinaAutorizadorGastoMovilidad(codigoEmpleado);		
		if(esJefe.equals("S")) return true;
		else return false;			
	}
	
	
	@Override
	public String buscarNombreAutorizador(String codigoEmpleado) throws Exception {

		return maestroPersonalDao.buscarNombreAutorizador(codigoEmpleado);
	}

	 /**
	 * Obtiene el datasource a la BD Siga Consulta o Transaccional.
	 * @param ds
	 * @return
	 * @throws Exception
	 */
	private DataSource obtenerDataSource(String ds, boolean esDeLectura) throws Exception {
		String dataSource = "jdbc/d" + (esDeLectura ? "c" : "g") +  ds;
		if (log.isDebugEnabled()) log.debug("dataSource" + dataSource);
		return (DataSource) (new JndiTemplate()).lookup(dataSource);
	}

	//PAS201780000300007
	/**
     * Verifica si el Empleado es un Jefe Inmediato (NO USA FUNCION ORACLE)
     * @param String - Código de Dependencia
     * @param String - Código de Empleado
     * @return boolean 
     */ 
	
	@Override
	public boolean esPerfilColaboradorJefe(String codDependencia,String codEmpleado) {
		log.debug("Inicio - RegistroPersonalServiceImpl.esPerfilColaboradorJefe");
		boolean sw=false;
		try {
			DataSource dataSource = obtenerDataSource("sig", false);
			return maestroPersonalDao.esPerfilColaboradorJefe(codDependencia,codEmpleado);
		} catch (Exception ex) {
			log.error("Error en RegistroPersonalServiceImpl.esPerfilColaboradorJefe: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);
		}
		finally {
			log.debug("Fin - RegistroPersonalServiceImpl.esPerfilColaboradorJefe");
		}
	}
	
	
	/**
     * Verifica si el Empleado es un Jefe Encargado (NO USA FUNCION ORACLE)
     * @param String - Código de Dependencia
     * @param String - Código de Empleado
     * @return boolean 
     */ 
	
	@Override
	public boolean esPerfilColaboradorEncargado(String codDependencia,String codEmpleado) {
		log.debug("Inicio - RegistroPersonalServiceImpl.esPerfilColaboradorEncargado");
		boolean sw=false;
		try {
			DataSource dataSource = obtenerDataSource("sig", false);
			return maestroPersonalDao.esPerfilColaboradorEncargado(codDependencia,codEmpleado);
		} catch (Exception ex) {
			log.error("Error en RegistroPersonalServiceImpl.esPerfilColaboradorEncargado: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);
		}
		finally {
			log.debug("Fin - RegistroPersonalServiceImpl.esPerfilColaboradorEncargado");
		}
	}
	
	/**
     * Verifica si el Empleado es un Jefe Encargado de cualquier uuoo diferente a la suya (NO USA FUNCION ORACLE)
     * @param String - Código de Dependencia
     * @param String - Código de Empleado
     * @return boolean 
     */ 
	public boolean esEncargadoOtraUuoo(String codEmpleado) {
        log.debug((Object)"Inicio - RegistroPersonalServiceImpl.esEncargadoOtraUuoo");
        boolean sw = false;
        try {
            DataSource dataSource = this.obtenerDataSource("sig", false);
            boolean bl = this.maestroPersonalDao.esEncargadoOtraUuoo(codEmpleado);
            return bl;
        }
        catch (Exception ex) {
            log.error((Object)("Error en RegistroPersonalServiceImpl.esEncargadoOtraUuoo: " + ex.getMessage()), (Throwable)ex);
            throw new ServiceException((Object)this, ex);
        }
        finally {
            log.debug((Object)"Fin - RegistroPersonalServiceImpl.esEncargadoOtraUuoo");
        }
    }
	
	/**
     * Verifica si el Empleado es un Jefe Encargado de cualquier AUC diferente a la suya (NO USA FUNCION ORACLE)
     * @param String - Código de Dependencia
     * @param String - Código de Empleado
     * @return boolean 
     */ 
	public boolean esEncargadoAuc(String codEmpleado) {
        log.debug((Object)"Inicio - RegistroPersonalServiceImpl.esEncargadoOtraUuoo");
        boolean sw = false;
        try {
            DataSource dataSource = this.obtenerDataSource("sig", false);
            boolean bl = this.maestroPersonalDao.esEncargadoAuc(codEmpleado);
            return bl;
        }
        catch (Exception ex) {
            log.error((Object)("Error en RegistroPersonalServiceImpl.esEncargadoOtraUuoo: " + ex.getMessage()), (Throwable)ex);
            throw new ServiceException((Object)this, ex);
        }
        finally {
            log.debug((Object)"Fin - RegistroPersonalServiceImpl.esEncargadoOtraUuoo");
        }
    }
	
	/**
     * Recuperar dependencias de la cual una persona es Jefe Inmediato de un Empleado
     * @param String - Codigo de Empleado
     * @return String - Cadena de dependencias que es jefe
     */ 
	@Override
	public String obtenerCadenaUuoosQueEsJefe(String codEmpleadoJefe) {
		if (log.isDebugEnabled()) log.debug("Inicio - RegistroPersonalServiceImpl.obtenerCadenaUuoosQueEsJefe");
		try {
			DataSource dataSource = obtenerDataSource("sig", false);
			return maestroPersonalDao.obtenerCadenaUuoosQueEsJefe(codEmpleadoJefe);
		} catch (Exception ex) {
			   log.error("Error en RegistroPersonalServiceImpl.obtenerCadenaUuoosQueEsJefe: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
		}
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RegistroPersonalServiceImpl.obtenerCadenaUuoosQueEsJefe");
		}
	}

	/**
     * Recuperar el número de jefaturas de la cual es jefe el empleado
     * @param String - Codigo de Empleado
     * @return String - Cadena de dependencias que es jefe
     */ 
	@Override
	public String obtenerNumJefaturasEmpleado(String codEmpleado) {
		if (log.isDebugEnabled()) log.debug("Inicio - RegistroPersonalServiceImpl.obtenerNumJefaturasEmpleado");
		try {
			return dependenciaDao.obtenerNumJefaturasEmpleado(codEmpleado);
		} catch (Exception ex) {
			   log.error("Error en RegistroPersonalServiceImpl.obtenerNumJefaturasEmpleado: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
		}
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RegistroPersonalServiceImpl.obtenerNumJefaturasEmpleado");
		}
	}
	
	/**
     * Recuperar las jefaturas de auc de un empleado
     * @param Map<String, Object>  - params
     * @return tdependencias - tdependencias
     */ 
	@Override
	public Collection obtenerJefaturasAucJson(Map<String, Object> params ) {
		if (log.isDebugEnabled()) log.debug("Inicio - RegistroPersonalServiceImpl.obtenerJefaturasAucJson");
		List<Map<String, Object>> lsParametros= new ArrayList<Map<String,Object>>();
		 List<DependenciaBean>lista = new ArrayList<DependenciaBean>();
		 Map<String, Object> map;
		 
		try {
			 lista =( List<DependenciaBean> ) dependenciaDao.obtenerJefaturasAucJson(params);
			 for(DependenciaBean data: lista){
					map = new HashMap<String, Object>();
					map.put("cod", data.getCod_dep());
					map.put("name", data.getUuoo()+" - "+data.getNom_corto());
					lsParametros.add(map);
			 }
			return lsParametros;
		} catch (Exception ex) {
			   log.error("Error en RegistroPersonalServiceImpl.obtenerJefaturasAucJson: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
		}
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RegistroPersonalServiceImpl.obtenerJefaturasAucJson");
		}
	}

	@Override
	public String obtenerUuooNoSupervision(String cod_dep, String long_uuoo) {
		if (log.isDebugEnabled()) log.debug("Inicio - RegistroPersonalServiceImpl.obtenerUuooNoSupervision");
		try {

			return dependenciaDao.obtenerUuooNoSupervision(cod_dep, long_uuoo);

		}catch (Exception ex) {
			   log.error("Error en RegistroPersonalServiceImpl.obtenerUuooNoSupervision: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
			} 
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RegistroPersonalServiceImpl.obtenerUuooNoSupervision");
		}
	}
	//================================================SETTERRRR///////////////////////////////////////////////
	
	
	public MaestroPersonalDAO getMaestroPersonalDao() {
		return maestroPersonalDao;
	}


	public void setMaestroPersonalDao(MaestroPersonalDAO maestroPersonalDao) {
		this.maestroPersonalDao = maestroPersonalDao;
	}





	public EncargosPersonaDAO getEncargosPersonaDao() {
		return encargosPersonaDao;
	}





	public void setEncargosPersonaDao(EncargosPersonaDAO encargosPersonaDao) {
		this.encargosPersonaDao = encargosPersonaDao;
	}





	public DependenciaDAO getDependenciaDao() {
		return dependenciaDao;
	}





	public void setDependenciaDao(DependenciaDAO dependenciaDao) {
		this.dependenciaDao = dependenciaDao;
	}


	public PersonaDAO getPersonaDao() {
		return personaDao;
	}


	public void setPersonaDao(PersonaDAO personaDao) {
		this.personaDao = personaDao;
	}


	public AccesoDAO getAccesoDao() {
		return accesoDao;
	}


	public void setAccesoDao(AccesoDAO accesoDao) {
		this.accesoDao = accesoDao;
	}


	public MaestroContratistasDAO getMaestroContratistasDao() {
		return maestroContratistasDao;
	}


	public void setMaestroContratistasDao(
			MaestroContratistasDAO maestroContratistasDao) {
		this.maestroContratistasDao = maestroContratistasDao;
	}


	public T01ParametroDAO getT01ParametroDao() {
		return t01ParametroDao;
	}


	public void setT01ParametroDao(T01ParametroDAO t01ParametroDao) {
		this.t01ParametroDao = t01ParametroDao;
	}


	@Override
	public boolean determinaAutorizadorGastoViatico(String codigoEmpleado)
			throws Exception {
		String esJefe =  maestroPersonalDao.determinaAutorizadorGastoViatico(codigoEmpleado);		
		if(esJefe.equals("1")) return true;
		else return false;			
	}


	public DdpDAO getDdpDao() {
		return ddpDao;
	}


	public void setDdpDao(DdpDAO ddpDao) {
		this.ddpDao = ddpDao;
	}


	public DdsDAO getDdsDao() {
		return ddsDao;
	}


	public void setDdsDao(DdsDAO ddsDao) {
		this.ddsDao = ddsDao;
	}


	public T733PernatDAO getT733pernatDao() {
		return t733pernatDao;
	}


	public void setT733pernatDao(T733PernatDAO t733pernatDao) {
		this.t733pernatDao = t733pernatDao;
	}
	
}
