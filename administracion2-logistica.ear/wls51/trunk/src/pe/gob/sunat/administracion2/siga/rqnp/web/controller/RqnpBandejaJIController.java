package pe.gob.sunat.administracion2.siga.rqnp.web.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.util.WebUtils;

import pe.gob.sunat.administracion2.siga.rqnp.model.domain.RequerimientoNoProgramadoBean;
import pe.gob.sunat.administracion2.siga.rqnp.service.RequerimientoNoProgramadoService;
import pe.gob.sunat.framework.core.json.JsonUtil;
import pe.gob.sunat.framework.spring.util.exception.ServiceException;
import pe.gob.sunat.framework.spring.web.view.JsonView;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.DependenciaBean;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.MaestroPersonalBean;
import pe.gob.sunat.recurso2.administracion.siga.registro.service.RegistroPersonalService;
import pe.gob.sunat.recurso2.administracion.siga.web.util.BaseController;
import pe.gob.sunat.tecnologia.menu.bean.UsuarioBean;

/**
 * <p>Title: RqnpBandejaJIController </p>
 * <p>Description: Clase Controller para la Atención de Requerimientos No Programados</p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: SUNAT</p>
 * @author EMARCHENA
 * @version 1.0 
 */

public class RqnpBandejaJIController extends BaseController{
	
	private static final Log log = LogFactory.getLog(RqnpBandejaJIController.class);
	
	private RegistroPersonalService 			registroPersonalService;
	private RequerimientoNoProgramadoService 	requerimientoNoProgramadoService;
	private ReloadableResourceBundleMessageSource 		messageSource;
	//private JsonView 							jsonView;
	//private View 								htmlView;
		
	
	/**
     * Carga la Bandeja de del Jefe Inmediado o Intendente con los Items
     * asignados para su aprobación
     * @param  request
     * @param  response 
     * @return ModelAndView 
     */ 
	
	public ModelAndView iniciarbandejaji(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - RqnpBandejaJIController.iniciarbandejaji");}
		String anioAct="";
		Calendar fecha =  Calendar.getInstance();
		String codEmpleado="";
		String nroRegEmpleado="";
		String codDependencia="";
		String tituloVista="";
		boolean sw=false;
		Map<String,Object> params=null;
		try {
			UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
			
			nroRegEmpleado= usuarioBean.getNroRegistro();
			List<Map<String, Object>> lsRqnp = new ArrayList<Map<String,Object>>();
			anioAct= String.valueOf( fecha.get(Calendar.YEAR)) ;
			MaestroPersonalBean  maestroPersonal= registroPersonalService.obtenerPersonaxRegistro(nroRegEmpleado);
			
			if(maestroPersonal != null){
				WebUtils.setSessionAttribute(request,"maestroPersonalBean",maestroPersonal );
						
				codEmpleado = maestroPersonal.getCodigoEmpleado();
				codDependencia= registroPersonalService.obtenerUuooNoSupervision(maestroPersonal.getCodigoDependencia(),"4");
				
				/*
				//Obtenemos perfiles de Colaborador y Dependencia
				boolean esPerfilColaboradorJefe=registroPersonalService.esPerfilColaboradorJefe(codDependencia,codEmpleado);
				boolean esPerfilColaboradorEncargado=registroPersonalService.esPerfilColaboradorEncargado(codDependencia,codEmpleado);
				boolean esPerfilDependenciaAprobador=requerimientoNoProgramadoService.esPerfilDependenciaAprobador(codDependencia);
				boolean esPerfilDependenciaAUC=requerimientoNoProgramadoService.esPerfilDependenciaAUC(codDependencia);
				boolean esEncargadoOtraUuoo = Boolean.valueOf(registroPersonalService.esEncargadoOtraUuoo(codEmpleado));
				
				log.debug("esPerfilColaboradorJefe: "+esPerfilColaboradorJefe);
				log.debug("esPerfilColaboradorEncargado: "+esPerfilColaboradorEncargado);
				log.debug("esPerfilDependenciaAprobador: "+esPerfilDependenciaAprobador);
				log.debug("esEncargadoOtraUuoo: " + esEncargadoOtraUuoo);
				*/
				Boolean esEncargadoOtraUuoo = Boolean.valueOf(registroPersonalService.esEncargadoOtraUuoo(codEmpleado));
				
				Boolean esUserJefe = Boolean.valueOf(this.registroPersonalService.esJefe(codEmpleado, codDependencia));
		        String esUserAprobador = this.registroPersonalService.esAprobador(codEmpleado);

		        log.debug("esUserJefe: " + esUserJefe);
		        log.debug("esUserAprobador: " + esUserAprobador);
		        log.debug("esEncargadoOtraUuoo: " + esEncargadoOtraUuoo);
		        
				params=new HashMap<String, Object>();
				if ((esUserJefe.booleanValue()) || (esEncargadoOtraUuoo.booleanValue())) {
					tituloVista = " (JEFE INMEDIATO) ";
					params.put("codEmpleadoEncargado", codEmpleado);
					params.put("cod_est", "03");
					lsRqnp = (List)this.requerimientoNoProgramadoService.recuperarBienesJefeInmediato(params);
					sw = true;
				}else{
					Exception ex = new Exception("Usted No posee el perfil para realizar esta operación.");
					request.setAttribute("excepAttr", ex);
					return new ModelAndView("pagErrorSistema");
				}

				if (sw) {
					return new ModelAndView("formBandejaJefeInmediato", "lsrqnp", lsRqnp).addObject("tituloVista", tituloVista);
				}
			}

			Exception ex = new Exception("No existe los datos del Usuario.");
			request.setAttribute("excepAttr", ex);
			return new ModelAndView("pagErrorSistema");
		}
			catch (ServiceException ex) {
			log.error("Error en RqnpBandejaJIController.iniciarbandejaji: " + ex.getMessage());
			throw new ServiceException(this, ex);
		} catch (Exception ex) {
			log.error("Error en RqnpBandejaJIController.iniciarbandejaji: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);
		} finally {
			if (log.isDebugEnabled()) log.debug("Fin - RqnpBandejaJIController.iniciarbandejaji");
		}
	}
	
	
	/**
     * Carga la Bandeja de del Intendente / Gerente / Aprobador
     * @param  request
     * @param  response 
     * @return ModelAndView 
     */ 
	
	public ModelAndView iniciarbandejaaprobador(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - RqnpBandejaJIController.iniciarbandejaaprobador");}
		String anioAct="";
		Calendar fecha =  Calendar.getInstance();
		String codEmpleado="";
		String nroRegEmpleado="";
		String codDependencia="";
		String tituloVista="";
		boolean sw=false;
		Map<String,Object> params=null;
		try {
			UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
			
			nroRegEmpleado= usuarioBean.getNroRegistro();
			List<Map<String, Object>> lsRqnp = new ArrayList<Map<String,Object>>();
			anioAct= String.valueOf( fecha.get(Calendar.YEAR)) ;
			MaestroPersonalBean  maestroPersonal= registroPersonalService.obtenerPersonaxRegistro(nroRegEmpleado);
			
			if(maestroPersonal != null){
				WebUtils.setSessionAttribute(request,"maestroPersonalBean",maestroPersonal );
						
				codEmpleado = maestroPersonal.getCodigoEmpleado();
				codDependencia= registroPersonalService.obtenerUuooNoSupervision(maestroPersonal.getCodigoDependencia(),"4");
				
				Boolean esEncargadoOtraUuoo = Boolean.valueOf(registroPersonalService.esEncargadoOtraUuoo(codEmpleado));
				
				Boolean esUserJefe = Boolean.valueOf(this.registroPersonalService.esJefe(codEmpleado, codDependencia));
		        String esUserAprobador = this.registroPersonalService.esAprobador(codEmpleado);

		        log.debug("esUserJefe: " + esUserJefe);
		        log.debug("esUserAprobador: " + esUserAprobador);
		        log.debug("esEncargadoOtraUuoo: " + esEncargadoOtraUuoo);
		        
				params=new HashMap<String, Object>();
				if (esUserAprobador.equals("S")) {
					tituloVista = " (APROBADOR) ";
					params.put("codEmpleadoEncargado", codEmpleado);
					params.put("cod_est", "0304");
					lsRqnp = (List)this.requerimientoNoProgramadoService.recuperarBienesIntedente(params);
					lsRqnp.addAll((List)this.requerimientoNoProgramadoService.recuperarBienesJefeInmediato(params));
					sw = true;
				}else {
					Exception ex = new Exception("Usted No posee el perfil para realizar esta operación.");
					request.setAttribute("excepAttr", ex);
					return new ModelAndView("pagErrorSistema");
				}
				
				if (sw) {
					return new ModelAndView("formBandejaAprobador", "lsrqnp", lsRqnp).addObject("tituloVista", tituloVista);
				}
			}

			Exception ex = new Exception("No existe los datos del Usuario.");
			request.setAttribute("excepAttr", ex);
			return new ModelAndView("pagErrorSistema");
		}
			catch (ServiceException ex) {
			log.error("Error en RqnpBandejaJIController.iniciarbandejaaprobador: " + ex.getMessage());
			throw new ServiceException(this, ex);
		} catch (Exception ex) {
			log.error("Error en RqnpBandejaJIController.iniciarbandejaaprobador: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);
		} finally {
			if (log.isDebugEnabled()) log.debug("Fin - RqnpBandejaJIController.iniciarbandejaaprobador");
		}
	}
	
	
	/**
     * Permite aprobar el RQNP por el Jefe Inmediato o Itendente.
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView aprobarRqnpJI(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()) {log.debug("Inicio -RqnpBandejaJIController.aprobarRqnpJI");}
		List<Map<String, Object>> lsRqnp= new ArrayList<Map<String,Object>>();
		List<String> lista = new ArrayList<String>();
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> data =  new HashMap<String, Object>();
		boolean okRunning = false;
		Exception exError=null ;
		
		String 	valor		="";
		String 	cod_req		="";
		String 	cod_bie		="";
		String 	cod_bie_env	="";
		String 	ls_anio		="";
		String 	ind_especializado="";
		String 	intendente	="N";
		String 	tituloVista	="";
		String ls_encargado ="";
		String 	user="";
		String codEmpleado = "";
    	String codDependencia = "";
		int 	i=0;
		Calendar fecha =  Calendar.getInstance();
		ls_anio= String.valueOf( fecha.get(Calendar.YEAR)) ;
		
		try {
			MaestroPersonalBean  maestroPersonal=  (MaestroPersonalBean) WebUtils.getSessionAttribute(request, "maestroPersonalBean");
			UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
			user= usuarioBean.getLogin();
			if (maestroPersonal!=null){
				String[] cod_bien = request.getParameterValues("txtCodigobien");
				for (int j=0; j<cod_bien.length; j++){
					if (!cod_bien[j].equals("")){
						lista.add(cod_bien[j]);
					}
				}
				//fin for
				Collections.sort(lista);
				///-------------------------------------------------------------------
				for (String obj:lista ){
					valor = obj;
					StringTokenizer tokens = new StringTokenizer(valor,",");
					i=0;
					while(tokens.hasMoreTokens()){
						i++;
						if (i==1){
							cod_req=tokens.nextToken();
						}else if (i==2){
							cod_bie= tokens.nextToken();
						}else{
							ind_especializado= tokens.nextToken();
						}
					}
					
					params.put("cod_rqnp", cod_req);
					params.put("cod_aprueba", maestroPersonal.getCodigoEmpleado());
					params.put("cod_bien", cod_bie);
					params.put("ind_especializado", ind_especializado);
					params.put("user", user);
					params.put("anio_pro", ls_anio);
					params.put("cod_sede", maestroPersonal.getCodigoSede());
					
					////////////////////////////////////////////////////////
					//INICIO DE VALIDACION
					Map<String, String> validar  = this.validarAprobarJI(params);
					String flag = (String)validar.get("flagValidacion");
          			String mensaje = (String)validar.get("mensaje");
					if(flag=="0"){
						intendente=requerimientoNoProgramadoService.registrarAprobarRqnp(params);
						data.put("flagRegistroDeclaracion", "0");
						data.put("mensaje", mensaje);
						okRunning = true;
					}else{
						data.put("flagRegistroDeclaracion", "1");
						data.put("mensaje", mensaje);
						return new ModelAndView(this.jsonView, "data", data);
					}
					//FIN DE VALIDACION
					////////////////////////////////////////////////////////
					
					cod_req="";
					cod_bie="";
				}
				//fin for
				//-------------------------------------------------------------------------- 
				
				if(okRunning){
					cod_req="";
					cod_bie="";
					String cod_req_ant="";
					for (String obj:lista ){
						valor = obj;
						StringTokenizer tokens = new StringTokenizer(valor,",");
						i=0;
						while(tokens.hasMoreTokens()){
							i++;
							if (i==1){
								cod_req=tokens.nextToken();
								if(!(cod_req_ant.equals(cod_req)) && !(cod_req_ant.equals(""))){
									if (!cod_bie_env.equals("") ){
										System.out.println(valor + " - " + cod_req_ant+ " - " + cod_bie_env);
										requerimientoNoProgramadoService.envioMailUct( cod_req_ant, cod_bie_env);
										requerimientoNoProgramadoService.envioMailDerivar( cod_req_ant, cod_bie_env);
									}
									cod_bie_env="";
								} 
							}else if (i==2){
								cod_bie= tokens.nextToken();
							}else{
								ind_especializado= tokens.nextToken();
							}
							
						}
						if (cod_bie_env.equals("") && ind_especializado.equals("S")){
								cod_bie_env =cod_bie;
						}else if( ind_especializado.equals("S")){
							cod_bie_env =cod_bie_env+','+cod_bie;
						}
						cod_req_ant=cod_req;
					}
					//fin for
					if (!cod_bie_env.equals("") ){
						requerimientoNoProgramadoService.envioMailUct( cod_req, cod_bie_env);
						requerimientoNoProgramadoService.envioMailDerivar( cod_req_ant, cod_bie_env);
					}
				
				}else{//fin (okRunning)
					exError = new Exception(data.get("mensaje").toString());
		          request.setAttribute("excepAttr", exError);
		          return new ModelAndView("pagErrorSistema");
		        }
		      } else {
		        Exception ex = new Exception("Error al Obtener Datos del Usuario");
		        request.setAttribute("excepAttr", ex);
		        return new ModelAndView("pagErrorSistema");
		      }
		    }
		    catch (ServiceException ex) {
		      log.error("Error en RqnpBandejaJIController.aprobarRqnpJI: " + ex.getMessage());
		      request.setAttribute("excepAttr", exError);
		      return new ModelAndView("pagErrorSistema");
		    }
		    catch (Exception ex)
		    {
		      ModelAndView localModelAndView;
		      log.error("Error en RqnpBandejaJIController.aprobarRqnpJI: " + ex.getMessage(), ex);
		      request.setAttribute("excepAttr", exError);
		      return new ModelAndView("pagErrorSistema");
		    }
		    finally {
		      if (log.isDebugEnabled()) log.debug("Fin - RqnpBandejaJIController.aprobarRqnpJI");  } if (log.isDebugEnabled()) log.debug("Fin - RqnpBandejaJIController.aprobarRqnpJI");

		    return new ModelAndView(this.jsonView, "data", data);
		  }
	


	/**
     * Permite Rechazar el RQNP por el Jefe Inmediato o Itendente.
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView rechazarRqnp(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("Inicio - RqnpBandejaJIController.rechazarRqnp");}
		List<Map<String, Object>> lsRqnp= new ArrayList<Map<String,Object>>();
		List<String> lista = new ArrayList<String>();
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		boolean okRunning = false;
		Exception exError = null;
		String valor	="";
		String cod_req	="";
		String cod_bie	="";
		String ls_intendente="";
		String ind_int	="";
		String titulo_jf="";
		String cod_bie_env	="";
		String ls_anio		="";
		String ind_especializado="";
		String user			="";
		String is_bandeja	="";
		String ls_encargado ="";
		int i=0;
	
		Calendar fecha =  Calendar.getInstance();
		ls_anio= String.valueOf( fecha.get(Calendar.YEAR)) ;
		try {
			MaestroPersonalBean  maestroPersonal=  (MaestroPersonalBean) WebUtils.getSessionAttribute(request, "maestroPersonalBean");
			UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
			user= usuarioBean.getLogin();
			
			if (maestroPersonal!=null){
				String[] cod_bien = request.getParameterValues("txtCodigobien");
				String obs_rechazo 	= StringUtils.trim(request.getParameter("txtRechazo"));
				ls_intendente		=registroPersonalService.obtenerAprobador( maestroPersonal.getCodigoEmpleado()) ;
				
				ls_encargado		=	registroPersonalService.esAprobadorEncargado(maestroPersonal.getCodigoEmpleado()).trim() ;
				if (ls_encargado==null){
					ls_encargado="";
				}
				
				if(ls_intendente==null){
					ls_intendente="";
				}
				
				if (ls_intendente.equals(maestroPersonal.getCodigoEmpleado() )){
					params.put("cod_accion", "005");
					params.put("exp_obs", "REQUERIMIENTO RECHAZADO JEFE UUOO SUPERIOR");
					is_bandeja="JEFE UUOO - SUPERIOR";
					log.debug("JEFE UUOO - SUPERIOR");
				}else if(registroPersonalService.esJefe(maestroPersonal.getCodigoEmpleado(),maestroPersonal.getCodigoDependencia())){
					is_bandeja = "JEFE";
					params.put("cod_accion", "003");
					params.put("exp_obs", "REQUERIMIENTO RECHAZADO POR EL JEFE INMEDIATO");
					log.debug("JEFE INMEDIATO");
				}else if(ls_encargado.equals("S") ){
					is_bandeja = "JEFE";
					params.put("cod_accion", "003");
					params.put("exp_obs", "REQUERIMIENTO RECHAZADO POR EL ENCARGADO APROBADOR");
					log.debug("JEFE ENCARGADO");
				}
				for (int j=0; j<cod_bien.length; j++){
					if (!cod_bien[j].equals("")){
						lista.add(cod_bien[j]);
						log.debug(cod_bien[j]);
					}
				}
				
				Collections.sort(lista);
				///-------------------------------------------------------------------
				
				for (String obj:lista ){
					valor = obj;
					StringTokenizer tokens = new StringTokenizer(valor,",");
					i=0;
					
					while(tokens.hasMoreTokens()){
						i++;
						if (i==1){
							cod_req=tokens.nextToken();
						}else if (i==2){
							cod_bie= tokens.nextToken();
						}else{
							ind_int=tokens.nextToken();
						}
					}
					params.put("anio_pro", ls_anio);
					params.put("cod_sede", maestroPersonal.getCodigoSede());
					
					params.put("cod_rqnp", cod_req);
					params.put("cod_aprueba", maestroPersonal.getCodigoEmpleado());
					params.put("cod_Bien", cod_bie);
					params.put("obs_rechazo",obs_rechazo );
					params.put("user", user);
					
					log.debug("(ANTES DE <registrarRechazarRqnp(params)> params: "+params.toString());
					
					requerimientoNoProgramadoService.registrarRechazarRqnp(params);
					data.put("flagRegistroDeclaracion", "0");
          			data.put("mensaje", "No hay problemas");
          			okRunning = true;
				}	
				
				if (okRunning) {
					cod_req="";
					cod_bie="";
					String cod_req_ant="";
					//----enviar correos------------------------------------------------------
					for (String obj : lista) {
			            valor = obj;
			            StringTokenizer tokens = new StringTokenizer(valor, ",");
			            i = 0;
			            while (tokens.hasMoreTokens()) {
			              i++;
			              if (i == 1) {
			                cod_req = tokens.nextToken();
			                if ((!cod_req_ant.equals(cod_req)) && (!cod_req_ant.equals(""))) {
			                  if (!cod_bie_env.equals("")) {
			                    System.out.println(valor + " - " + cod_req_ant + " - " + cod_bie_env + " " + is_bandeja);
			                    this.requerimientoNoProgramadoService.envioMailRechazo(cod_req_ant, cod_bie_env, is_bandeja);
			                  }
			                  cod_bie_env = "";
			                }
			              } else if (i == 2) {
			                cod_bie = tokens.nextToken();
			              }
			              else {
			                ind_especializado = tokens.nextToken();
			              }
			            }
			            if (cod_bie_env.equals(""))
			              cod_bie_env = cod_bie;
			            else {
			              cod_bie_env = cod_bie_env + ',' + cod_bie;
			            }
			            cod_req_ant = cod_req;
			          }
	
		          if (!cod_bie_env.equals(""))
		            this.requerimientoNoProgramadoService.envioMailRechazo(cod_req, cod_bie_env, is_bandeja);
		        }else{
		          exError = new Exception(data.get("mensaje").toString());
		          request.setAttribute("excepAttr", exError);
		          return new ModelAndView("pagErrorSistema");
		        }
		      } else {
		        Exception ex = new Exception("Error al Obtener Datos del Usuario");
		        request.setAttribute("excepAttr", ex);
		        return new ModelAndView("pagErrorSistema");
		      }
		    }
		    catch (ServiceException ex) {
		      log.error("Error en RqnpBandejaJIController.rechazarRqnp: " + ex.getMessage());
		      request.setAttribute("excepAttr", exError);
		      return new ModelAndView("pagErrorSistema");
		    }
		    catch (Exception ex)
		    {
		      log.error("Error en RqnpBandejaJIController.rechazarRqnp: " + ex.getMessage(), ex);
		      request.setAttribute("excepAttr", exError);
		      return new ModelAndView("pagErrorSistema");
		    }
		    finally {
		      if (log.isDebugEnabled()) log.debug("Fin - RqnpBandejaJIController.rechazarRqnp");  } if (log.isDebugEnabled()) log.debug("Fin - RqnpBandejaJIController.rechazarRqnp");
	
		    return new ModelAndView(this.jsonView, "data", data);
		  }
	
	
	/**
     * Recupera solo las metas afectadas de un  item del RQNP  .
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView recuperarMetasVistaJson(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - RqnpBandejaJIController.recuperarMetasVistaJson");}
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		List<Map<String, Object>> listaMetas = new ArrayList<Map<String,Object>>();
		try {
			String codigo_bien = StringUtils.trim(request.getParameter("jcodigo_bien"));
			String codigo_rqnp 	= 	StringUtils.trim(request.getParameter("txtCodigoRqnp"));
			String codigo_dep = StringUtils.trim(request.getParameter("jcodigo_dep"));
			String anio_ejec = StringUtils.trim(request.getParameter("janio_ejec"));
			String precio_unid = StringUtils.trim(request.getParameter("jprecio_unid"));
			
			params.put("cod_bien", codigo_bien);
			params.put("cod_req", codigo_rqnp);
			params.put("precio_unid", precio_unid);
			params.put("cod_dep",codigo_dep);
			params.put("anio_ejec",anio_ejec);
			 
			listaMetas = (List<Map<String, Object>>) requerimientoNoProgramadoService.listarRqnpMetasVista(params);
			data.put("listaMetas", JsonUtil.toString( listaMetas));
		} 
		catch(ServiceException ex){
			log.error("Error en RqnpBandejaJIController.recuperarMetasVistaJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en RqnpBandejaJIController.recuperarMetasVistaJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()) {log.debug("Fin - RqnpBandejaJIController.recuperarMetasVistaJson");}
		}
		return new ModelAndView(jsonView, "data", JsonUtil.toString(data)) ;
	}

	//Validar datos de aprobacion del Jefe Inmediato
	/**
	 * Metodo que valida los parametros antes de que el JI apruebe el requerimiento
	 * @author dporrasc
	 * @param Map<String, Object>
	 * @return
	 */
	private Map<String, String> validarAprobarJI(Map<String, Object> params){
		String mensaje = "";
		String flagValidacion = "0";
		Map<String, String> respuesta = new HashMap<String, String>();
		String codRqnp = (String) params.get("cod_rqnp");
		try{
			RequerimientoNoProgramadoBean rqnp = new RequerimientoNoProgramadoBean();
			DependenciaBean dependenciaOEC = new DependenciaBean();
	      	DependenciaBean dependenciaAUC = new DependenciaBean();
	      	DependenciaBean dependenciaRqnp = new DependenciaBean();
		    DependenciaBean dependenciaAprobadora = new DependenciaBean();
		      
		    String codParametro="4023";
		    String descParametro="UPP";
		      
		    DependenciaBean dependenciaDpg = requerimientoNoProgramadoService.obtenerDpg(codParametro, descParametro);
		    dependenciaDpg = requerimientoNoProgramadoService.obtenerDpg(codParametro, descParametro);
		    String uuooDpg = dependenciaDpg.getUuoo()+" - "+dependenciaDpg.getNom_corto();
		      
			rqnp=requerimientoNoProgramadoService.recuperarRqnpCab(codRqnp);
			
			String codDepeRqnp=rqnp.getCodigoDependencia();
			params.put("codDepeRqnp", codDepeRqnp);
			params.put("codRqnp", codRqnp);
			
			dependenciaRqnp = this.requerimientoNoProgramadoService.obtenerUuooRQNP(params);
	      	//dependenciaOEC = this.requerimientoNoProgramadoService.obtenerUuooOEC(params);
	      	dependenciaAUC = this.requerimientoNoProgramadoService.obtenerUuooAUC(params);

	      	BigDecimal indMonto = rqnp.getMontoRqnp();

	      	if (dependenciaRqnp == null) {
	        	mensaje = "La dependencia que generó el Requerimiento Nro. " + rqnp.getCodigoReqNoProgramado() + " se encuentra Desactivada. \nPara más detalles favor de comunicarse con Recursos Humanos.";
	        	flagValidacion = "1";
	      	} else {
	      		log.debug("dependenciaRqnp: "+dependenciaRqnp.getCod_dep() + " - UUOO: "+dependenciaRqnp.getUuoo());
	      		String codOecDefecto = dependenciaRqnp.getCodOecDefecto();
	        if (codOecDefecto == null) {
	          mensaje = "El Área Encargada de la Contratación que atenderá el Requerimiento Nro. " + rqnp.getCodigoReqNoProgramado() + " no se encuentra configurada.\nFavor de comunicarse con la Unidad "+uuooDpg+" para mas detalles.";
	          flagValidacion = "1";
	        } else{
	        	params.put("codOecDefecto",codOecDefecto);
	        	dependenciaOEC = this.requerimientoNoProgramadoService.obtenerUuooOEC(params);
	        }
	        
	      }

	      if (flagValidacion.equals("0")) {
		      if (dependenciaOEC == null) {
		        mensaje = "El Área Encargada de la Contratación que atenderá el Requerimiento Nro. " + rqnp.getCodigoReqNoProgramado() + " se encuentra Desactivada.\nFavor de comunicarse con la Unidad "+uuooDpg+" para mas detalles.";
		        flagValidacion = "1";
		      } else {
		    	  log.debug("dependenciaOEC: "+dependenciaOEC.getCod_dep() + " - UUOO: "+dependenciaOEC.getUuoo());
		    	  String codEmpleadoJefeOec = dependenciaOEC.getCodEmpleado();
		        if (codEmpleadoJefeOec == null) {
		          mensaje = "El Jefe del Área Encargada de la Contratación que atenderá el Requerimiento Nro. " + rqnp.getCodigoReqNoProgramado() + " no se encuentra asignado.\nFavor de comunicarse con Información de Personal en Recursos Humanos.";
		          flagValidacion = "1";
		        }
		      }
		  }
	      
	      
	      if (flagValidacion.equals("0")) {
	        if (dependenciaAUC == null) {
	          mensaje = "El Área Técnica (AT) que atenderá el Requerimiento Nro. " + rqnp.getCodigoReqNoProgramado() + " se encuentra Desactivada ó no se asigno al requerimiento. \nFavor de comunicarse con la Unidad "+uuooDpg+" para mas detalles.";
	          flagValidacion = "1";
	        } else {
	        	log.debug("dependenciaAUC: "+dependenciaAUC.getCod_dep() + " - UUOO: "+dependenciaAUC.getUuoo());
	        	String codAucDeRqnp = dependenciaAUC.getCod_dep();
	        	String codEmpleadoJefeAuc = dependenciaAUC.getCodEmpleado();
	        	String codUuooAprobadoraAuc = dependenciaAUC.getCodUuooAprueba();
	          if (codAucDeRqnp == null) {
	            mensaje = "El Área Técnica (AT) que atenderá el Requerimiento Nro. " + rqnp.getCodigoReqNoProgramado() + " no se encuentra configurada.\nFavor de comunicarse con la Unidad "+uuooDpg+" para mas detalles.";
	            flagValidacion = "1";
	          } else if (codEmpleadoJefeAuc == null) {
	            mensaje = "El Jefe del Área Técnica (AT) que atenderá el Requerimiento Nro. " + rqnp.getCodigoReqNoProgramado() + " no se encuentra asignado. \nFavor de comunicarse con Información de Personal en Recursos Humanos.";
	            flagValidacion = "1";
	          }
	          
	          if (codUuooAprobadoraAuc == null) {
		          mensaje = "La UUOO Aprobadora que atenderá este Requerimiento Nro. " + rqnp.getCodigoReqNoProgramado() + "  no se encuentra configurada.\nFavor de comunicarse con la Unidad "+uuooDpg+" para mas detalles.";
		          flagValidacion = "1";
		        }else{
		        	params.put("codUuooAprueba",codUuooAprobadoraAuc);
		        	dependenciaAprobadora = this.requerimientoNoProgramadoService.obtenerUuooAprobadoraAuc(params);
		        	log.debug("dependenciaAprobadora: "+dependenciaAprobadora.getCod_dep() + " - UUOO: "+dependenciaAprobadora.getUuoo());
		        }
	        }
	      }

	      if (flagValidacion.equals("0")) {
		     if (indMonto == null) {
		         mensaje = "El monto total del Requerimiento Nro. " + rqnp.getCodigoReqNoProgramado() + " no puede ser cero ó vacio.";
		         flagValidacion = "1";
		     }else if(indMonto.compareTo(BigDecimal.ZERO)<=0){
		       	mensaje = "El monto total del Requerimiento Nro. " + rqnp.getCodigoReqNoProgramado() + " no puede ser cero ó vacio.";
		       	flagValidacion = "1";
		     } else {
		        mensaje = "no hay problema";
		        flagValidacion = "0";
		     }
		  }

	      respuesta.put("mensaje", mensaje);
	      respuesta.put("flagValidacion", flagValidacion);
	    }
	    catch (ServiceException ex) {
	      log.error("Error en RqnpBandejaJIController.validarAprobarJI: " + ex.getMessage());
	      throw new ServiceException(this, ex);
	    }
	    catch (Exception ex) {
	      log.error("Error en RqnpBandejaJIController.validarAprobarJI: " + ex.getMessage(), ex);
	      throw new ServiceException(this, ex);
	    }
	    finally {
	      if (log.isDebugEnabled()) log.debug("Fin - RqnpBandejaJIController.validarAprobarJI");
	    }
	    return respuesta;
	  }

	
	
	public RegistroPersonalService getRegistroPersonalService() {
		return registroPersonalService;
	}
	public void setRegistroPersonalService(
			RegistroPersonalService registroPersonalService) {
		this.registroPersonalService = registroPersonalService;
	}
	public RequerimientoNoProgramadoService getRequerimientoNoProgramadoService() {
		return requerimientoNoProgramadoService;
	}
	public void setRequerimientoNoProgramadoService(
			RequerimientoNoProgramadoService requerimientoNoProgramadoService) {
		this.requerimientoNoProgramadoService = requerimientoNoProgramadoService;
	}
	public ReloadableResourceBundleMessageSource getMessageSource() {
		return messageSource;
	}
	public void setMessageSource(ReloadableResourceBundleMessageSource messageSource) {
		this.messageSource = messageSource;
	}
}
