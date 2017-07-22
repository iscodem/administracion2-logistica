package pe.gob.sunat.administracion2.siga.auc.web.controller;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.NDC;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import pe.gob.sunat.administracion2.siga.registro.model.domain.RegistroArchivosFisicoBean;
import pe.gob.sunat.administracion2.siga.rqnp.model.domain.RequerimientoNoProgBienesBean;
import pe.gob.sunat.administracion2.siga.rqnp.model.domain.RequerimientoNoProgramadoBean;
import pe.gob.sunat.administracion2.siga.rqnp.service.RequerimientoAucService;
import pe.gob.sunat.administracion2.siga.rqnp.service.RequerimientoNoProgramadoService;
import pe.gob.sunat.framework.core.json.JsonUtil;
import pe.gob.sunat.framework.spring.util.exception.ServiceException;
import pe.gob.sunat.framework.util.date.FechaBean;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.DependenciaBean;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.MaestroContratistasBean;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.MaestroPersonalBean;
import pe.gob.sunat.recurso2.administracion.siga.registro.service.RegistroPersonalService;
import pe.gob.sunat.recurso2.administracion.siga.web.util.BaseController;
import pe.gob.sunat.tecnologia.menu.bean.UsuarioBean;


/**
 * <p>Title: RqnpController </p>
 * <p>Description: Clase Controller para Consultar los Requerimientos Atender por la AUC</p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: SUNAT</p>
 * @author EMARCHENA
 * @version 1.0 
 */


public class AucBandejaController extends BaseController {
	
	private static final Log log = LogFactory.getLog(AucBandejaController.class);
	
	private ReloadableResourceBundleMessageSource 		messageSource;
	//private JsonView 							jsonView;
	//private View 								htmlView;
	private RequerimientoAucService 			requerimientoAucService;
	private RegistroPersonalService 			registroPersonalService;
	private RequerimientoNoProgramadoService 	requerimientoNoProgramadoService;
	
	/**
     * Carga la Bandeja de Registro de Requerimientos no Programados
     * Atender por la AUC
     * @param  request
     * @param  response 
     * @return ModelAndView 
     */
	//Menu Regisgtro RQNP///////////////////////////////////////////////////////////////////////////
	public ModelAndView iniciarbandejaauc(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()) {log.debug("debug Inicio - AucBandejaController.iniciarbandejaauc");}
		String 		ls_anio="";
		Calendar 	fecha =  Calendar.getInstance();
		String 		ls_registro="";
		String 		is_user_auc="N";
		String 		is_grupo_auc="N";
		String 		depe_no_superv="";
		String 		is_user_au_auc="";
		String 		codEmpleado="";
		Map<String, Object>params = new HashMap<String, Object>();
		try {
			UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
			ls_registro= usuarioBean.getNroRegistro();
			
			MaestroPersonalBean  maestroPersonal= registroPersonalService.obtenerPersonaxRegistro(ls_registro.trim());
			codEmpleado= maestroPersonal.getCodigoEmpleado();
			
			depe_no_superv=registroPersonalService.obtenerUuooNoSupervision(maestroPersonal.getCodigoDependencia(), "4");
			log.debug("depe_no_superv: "+depe_no_superv);
			
			if(depe_no_superv==null){
				Exception ex  = new Exception("Existe un problema con su dependencia ó la de su Jefatura, Por favor verifique.");
				request.setAttribute("excepAttr", ex);
				return new ModelAndView("pagErrorSistema");
			}
			//params.put("cod_user", usuarioBean.getLogin());
			params.put("nro_reg", ls_registro.trim());
			params.put("cod_rol", "2001");
			params.put("cod_dep", depe_no_superv);
			
			is_user_auc=requerimientoAucService.validaUserAUC(params);
			is_grupo_auc=requerimientoAucService.validaGrupoAUC(params);
			is_user_au_auc=requerimientoAucService.validaUserAuAUC(codEmpleado);
			Boolean esEncargadoAuc = Boolean.valueOf(registroPersonalService.esEncargadoAuc(codEmpleado));
			Boolean esEncargadoOtraUuoo = Boolean.valueOf(registroPersonalService.esEncargadoOtraUuoo(codEmpleado));
			
			if (log.isDebugEnabled()) log.debug("Valor de is_user_au_auc: "+is_user_au_auc);
			
			log.debug("es jefe: "+registroPersonalService.esJefe(codEmpleado,depe_no_superv));
			log.debug("es is_user_au_auc: "+is_user_au_auc);
			log.debug("es is_grupo_auc: "+is_grupo_auc);
			log.debug("es esEncargadoAuc: "+esEncargadoAuc);
			log.debug("esEncargadoOtraUuoo: " + esEncargadoOtraUuoo);
			
			//if (is_user_auc.equals("S")|| is_user_au_auc.equals("S")|| (registroPersonalService.esJefe(codEmpleado,depe_no_superv)&&is_grupo_auc.equals("S"))){
			if (is_user_auc.equals("S") && esEncargadoAuc.booleanValue()
					|| is_user_au_auc.equals("S")
					|| (registroPersonalService.esJefe(codEmpleado,depe_no_superv))
					|| (esEncargadoOtraUuoo) && esEncargadoAuc.booleanValue()
					||esEncargadoAuc.booleanValue()){
				NDC.push(usuarioBean.getLogin()+"-"+usuarioBean.getTicket());
				ls_registro= usuarioBean.getNroRegistro();
				ls_anio= String.valueOf( fecha.get(Calendar.YEAR)) ;
				
				if(maestroPersonal != null){
					params.put("cod_per", maestroPersonal.getCodigoEmpleado());
					params.put("anio_act", ls_anio);
					params.put("cod_estado", "02");
					params.put("ind_registropor", "AUC");
					String as_cadena=requerimientoAucService.obtenerCadenaEncargosAUC(maestroPersonal.getCodigoEmpleado());
					if(as_cadena==null){
						params.put("as_cadena", depe_no_superv);
					}else{
						params.put("as_cadena",as_cadena);
					}
					
					WebUtils.setSessionAttribute(request,"maestroPersonalBean",maestroPersonal );
					@SuppressWarnings("unchecked")
					List<Map<String, Object>> lsRqnp = (List<Map<String, Object>>) requerimientoAucService.listarRqnpAuc(params);
					
					return new ModelAndView("formBandejaAUC", "lsrqnp", lsRqnp).addObject("anio",ls_anio);
				}else{
					Exception ex  = new Exception("No existe los datos del Usuario ");
					request.setAttribute("excepAttr", ex);
					return new ModelAndView("pagErrorSistema");
				}
			}else{
				Exception ex  = new Exception("Ud. no tiene permiso para realizar esta operación. ");
				request.setAttribute("excepAttr", ex);
				return new ModelAndView("pagErrorSistema");
			}
		} 
		catch(ServiceException ex){
			log.error("Error en AucBandejaController.iniciarbandejaauc: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en AucBandejaController.iniciarbandejaauc: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			NDC.pop();
			NDC.remove();
			if (log.isDebugEnabled()){ log.debug("Fin - AucBandejaController.iniciarbandejaauc");}
		}
	}

	
	/**
     * Busca la Bandeja de Registro de Requerimientos no Programados
     * Atender por la AUC
     * @param  request
     * @param  response 
     * @return ModelAndView 
     */
	//Menu Regisgtro RQNP///////////////////////////////////////////////////////////////////////////
	public ModelAndView buscarbandejaaucJson(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()) {log.debug("debug Inicio - AucBandejaController.buscarbandejaaucJson");}
		
		Calendar 	fecha =  Calendar.getInstance();
		String 		ls_registro="";
		Map<String, Object>params = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		String cod_estado ="";
		String anio_act ="";
		String depe_no_superv="";
		try {
			MaestroPersonalBean  maestroPersonal=  (MaestroPersonalBean) WebUtils.getSessionAttribute(request, "maestroPersonalBean");
			log.debug(maestroPersonal.getCodigoEmpleado() );
			
			anio_act = StringUtils.trim(request.getParameter("anio_act"));
			cod_estado = StringUtils.trim(request.getParameter("cod_estado"));
			
			depe_no_superv=registroPersonalService.obtenerUuooNoSupervision(maestroPersonal.getCodigoDependencia(),"4");
			params.put("cod_dep", depe_no_superv);
			
			if(maestroPersonal != null){
				params.put("cod_per", maestroPersonal.getCodigoEmpleado());
				params.put("anio_act", anio_act);
				params.put("cod_estado", cod_estado);
				
				///////////////////////////////////////////////////////////////////////
				String as_cadena=requerimientoAucService.obtenerCadenaEncargosAUC(maestroPersonal.getCodigoEmpleado());
				if(as_cadena==null){
					params.put("as_cadena", depe_no_superv);
				}else{
					params.put("as_cadena",as_cadena);
				}
				params.put("cod_dep", depe_no_superv);
				///////////////////////////////////////////////////////////////////////
				
				WebUtils.setSessionAttribute(request,"maestroPersonalBean",maestroPersonal );
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> lsRqnptem = new  ArrayList<Map<String,Object>>();
				List<Map<String, Object>> lsRqnp = (List<Map<String, Object>>) requerimientoAucService.listarRqnpAuc(params);
				if (cod_estado.equals("%")){
					params.put("cod_estado", "02");
					lsRqnptem = (List<Map<String, Object>>) requerimientoAucService.listarRqnpAuc(params);
					lsRqnp.addAll(lsRqnptem);
				}
				
				data.put("identifier", "num_rqnp");
				data.put("items",  lsRqnp);
				
			}else{
				Exception ex  = new Exception("No existe los datos del Usuario ");
				request.setAttribute("excepAttr", ex);
				return new ModelAndView("pagErrorSistema");
			}
		} 
		catch(ServiceException ex){
			log.error("Error en AucBandejaController.buscarbandejaaucJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en AucBandejaController.buscarbandejaaucJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - AucBandejaController.buscarbandejaaucJson");}
		}
		return new ModelAndView(jsonView,  data) ;
	}
	
	/**
     * Recuperar Requerimiento No Programado Cabecera
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView aucRqnpCab(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()) log.debug("Inicio -  AucBandejaController.aucRqnpCab");
		String cod_req="";
		String cod_dep_user="";
		FechaBean fecha = new FechaBean();

		List<Map<String, Object>> lsTipoNecesidad= new ArrayList<Map<String, Object>>();
		
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			MaestroPersonalBean  maestroPersonal=  (MaestroPersonalBean) WebUtils.getSessionAttribute(request, "maestroPersonalBean");
			cod_req = StringUtils.trim(request.getParameter("txtCodigoRqnp"));
			cod_dep_user= maestroPersonal.getCodigoDependencia();
			
			if (log.isDebugEnabled()) log.debug(maestroPersonal.getNombre_completo());
			if (log.isDebugEnabled()) log.debug(maestroPersonal.getCodigoDependencia());
			Map<String, Object> mapRqnp = new HashMap<String, Object>();
			 
			if (log.isDebugEnabled()){ log.debug("cod_req:"+cod_req);}
			params.put("cod_req", cod_req);
			params.put("cod_dep_user", cod_dep_user);
			 
			mapRqnp= requerimientoAucService.recuperarRqnpCab(params);
			
			lsTipoNecesidad = (List<Map<String, Object>>) requerimientoNoProgramadoService.listaTipoNecesidad();
			
			return new ModelAndView("formAucRqnpCab").addObject("mapRqnp", mapRqnp).addObject("lsTipoNecesidad", lsTipoNecesidad) ;
		} 
		catch(ServiceException ex){
			log.error("Error en  AucBandejaController.aucRqnpCab: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en  AucBandejaController.aucRqnpCab: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin -  AucBandejaController.aucRqnpCab");}
		}
	}
	
	
	
	/**
     * Recuperar Contacto de Requerimiento .
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView recuperarContactoRqnpJson(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - AucBandejaController.recuperarContactoRqnpJson");}
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			MaestroPersonalBean personal=new MaestroPersonalBean();
			String cod_reg = StringUtils.trim(request.getParameter("txtReg_contacto"));
			log.debug("Numero Registro:" + cod_reg);
			params.put("cod_reg", cod_reg);
			personal=registroPersonalService.obtenerPersonaxRegistro(cod_reg);
			if (personal !=null){
				data.put("cod_contacto", personal.getCodigoEmpleado());
				data.put("nom_contacto",personal.getNombre_completo());
			}else{
				data.put("nom_contacto",messageSource.getMessage("error.registrarRqnp.contacto" , null, Locale.getDefault()));
				data.put("error", "-1");
				data.put("cod_contacto","-1");
				data.put("mensaje", messageSource.getMessage("error.registrarRqnp.contacto" , null, Locale.getDefault()));	
			}
		} 
		catch(ServiceException ex){
			log.error("Error en AucBandejaController.recuperarContactoRqnpJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en AucBandejaController.recuperarContactoRqnpJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - AucBandejaController.recuperarContactoRqnpJson");}
		}
		return new ModelAndView(jsonView, "data", JsonUtil.toString(data)) ;
	}
	
	
	//AGREGADO PARA EL PASE - MIEMBROS DEL COMITE
	/**
     * Recuperar Miembro cel comite de requerimiento No Programado.
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView recuperarComiteRqnpJson(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - RqnpAucController.recuperarComiteRqnpJson");}
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			MaestroPersonalBean personal=new MaestroPersonalBean();
			String cod_reg = StringUtils.trim(request.getParameter("txtNum_user_comite"));
			log.debug("Numero Registro:" + cod_reg);
			params.put("cod_reg", cod_reg);
			personal=registroPersonalService.obtenerPersonaxRegistro(cod_reg);
			if (personal !=null){
				data.put("cod_contacto", personal.getCodigoEmpleado());
				data.put("nom_contacto",personal.getNombre_completo());
			}else{
				data.put("nom_contacto",messageSource.getMessage("error.registrarRqnp.contacto" , null, Locale.getDefault()));
				data.put("error", "-1");
				data.put("cod_contacto","-1");
				data.put("mensaje", messageSource.getMessage("error.registrarRqnp.contacto" , null, Locale.getDefault()));	
			}
		} 
		catch(ServiceException ex){
			log.error("Error en RqnpAucController.recuperarContactoRqnpJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en RqnpAucController.recuperarContactoRqnpJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - RqnpAucController.recuperarComiteRqnpJson");}
		}
		return new ModelAndView(jsonView, "data", JsonUtil.toString(data)) ;
	}
	
	
	
	/**
     * Busca los Persona de MaestroPersonal .
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView buscarPersonaJson(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - AucBandejaController.buscarPersonaJson");}
		Map<String, Object> mapParm = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
	
		try {
			String parm = StringUtils.trim(request.getParameter("jPer_parametro"));
			String tipo = StringUtils.trim(request.getParameter("jPer_tipoConsulta"));
			parm= parm.toUpperCase().replace(" ", "%");
			mapParm.put("per_parm", parm);
			mapParm.put("per_tipo", tipo);
			List<Map<String, Object>> lsPersonal= (List<Map<String, Object>>) registroPersonalService.listarMaestroPersonal(mapParm);
			data.put("lsPersonal", JsonUtil.toString( lsPersonal));
			data.put("mapPersonal", JsonUtil.toString(mapParm));
		} 
		catch(ServiceException ex){
			log.error("Error en AucBandejaController.buscarPersonaJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en AucBandejaController.buscarPersonaJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - AucBandejaController.buscarPersonaJson");}
		}
		return new ModelAndView(jsonView, "data", JsonUtil.toString(data)) ;
	}
	
	
	/**
     * Busca los Persona de MaestroPersonal .
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView buscarPersonaJson2(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - AucBandejaController.buscarPersonaJson");}
		Map<String, Object> mapParm = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		List<Map<String, Object>> lsPersonal = new ArrayList<Map<String,Object>>();
	
		try {
			String parm = StringUtils.trim(request.getParameter("jPer_parametro"));
			String tipo = StringUtils.trim(request.getParameter("jPer_tipoConsulta"));
			parm= parm.toUpperCase().replace(" ", "%");
			mapParm.put("per_parm", parm);
			mapParm.put("per_tipo", tipo);
			lsPersonal= (List<Map<String, Object>>) registroPersonalService.listarMaestroPersonal(mapParm);
			
			data.put("identifier", "codigoEmpleado");
			data.put("items",  lsPersonal);
		} 
		catch(ServiceException ex){
			log.error("Error en AucBandejaController.buscarPersonaJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en AucBandejaController.buscarPersonaJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - AucBandejaController.buscarPersonaJson");}
		}
		return new ModelAndView(jsonView,  data) ;
	}
	
	
	/**
     * Recuperar Contratista del Requerimiento .
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView recuperarContratistaRqnpJson(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - AucBandejaController.recuperarContratistaRqnpJson");}
		Map<String, Object> parm = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		String user="";
		try {
			MaestroContratistasBean controtatista= new MaestroContratistasBean();
			UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
			user= usuarioBean.getLogin();
			String num_ruc = StringUtils.trim(request.getParameter("txtNum_ruc_prov"));
			parm.put("num_ruc", num_ruc);
			parm.put("user", user);
			controtatista=registroPersonalService.recuperarContratistaPadron(parm);
			if (controtatista !=null){
				data.put("cod_cont", controtatista.getCod_cont());
				data.put("num_ruc",controtatista.getNum_ruc());
				data.put("raz_social",controtatista.getRaz_social());
			}else{
				data.put("raz_social",messageSource.getMessage("error.registrarRqnp.contratista" , null, Locale.getDefault()));
				data.put("error", "-1");
				data.put("cod_cont", "-1");
				data.put("mensaje", messageSource.getMessage("error.registrarRqnp.contratista" , null, Locale.getDefault()));	
			}
		} 
		catch(ServiceException ex){
			log.error("Error en AucBandejaController.recuperarContratistaRqnpJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en AucBandejaController.recuperarContratistaRqnpJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - AucBandejaController.recuperarContratistaRqnpJson");}
		}
		return new ModelAndView(jsonView, "data", JsonUtil.toString(data)) ;
	}
	
	
	/**
     * Recuperar Codigo de UUOO Conformidad .
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView recuperarCodUUOOJson(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - AucBandejaController.recuperarCodUUOOJson");}
		Map<String, Object> parm = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		String user="";
		String num_uuoo="" ;
		try {
			MaestroContratistasBean controtatista= new MaestroContratistasBean();
			UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
			user= usuarioBean.getLogin();
			num_uuoo= StringUtils.trim(request.getParameter("txtNum_UUOO"));
			parm.put("num_uuoo", num_uuoo);
			parm.put("flagInfoInf", "6");
			//data=requerimientoAucService.recuperarDependenciaXuuoo(num_uuoo) ;
			data=requerimientoAucService.obtenerUuoo(parm);
			
			if (data !=null){
				if(data.get("flagInfoInf").equals("6")){
					data.put("mensaje", "Mensaje: Una Supervisión no puede ser asignado como Unidad de Conformidad.");	
					data.put("error", "-1");
					data.put("cod_dep", "-1");
					data.put("nom_dep",messageSource.getMessage("error.registrarRqnp.uuoo" , null, Locale.getDefault()));
				}
				
			}else{
				data = new HashMap<String, Object>();
				data.put("nom_dep",messageSource.getMessage("error.registrarRqnp.uuoo" , null, Locale.getDefault()));
				data.put("error", "-1");
				data.put("cod_dep", "-1");
				data.put("mensaje", messageSource.getMessage("error.registrarRqnp.uuoo" , null, Locale.getDefault()));	
			}
		} 
		catch(ServiceException ex){
			log.error("Error en AucBandejaController.recuperarCodUUOOJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en AucBandejaController.recuperarCodUUOOJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - AucBandejaController.recuperarCodUUOOJson");}
		}
		return new ModelAndView(jsonView, "data", JsonUtil.toString(data)) ;
	}
	
	
	/**
     * Verifica que el registro de contacto existe.
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView validarGuardarCabJson(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - AucBandejaController.validarGuardarCabJson");}
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> parm = new HashMap<String, Object>();
		String user="";
		try {
			UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
			user= usuarioBean.getLogin();
			MaestroPersonalBean personal=new MaestroPersonalBean();
			String cod_reg = StringUtils.trim(request.getParameter("txtReg_contacto"));
			String ruc_prov = StringUtils.trim(request.getParameter("txtNum_ruc_prov"));
			
			params.put("cod_reg", cod_reg);
			personal=registroPersonalService.obtenerPersonaxRegistro(cod_reg);
			log.debug("paso personalll");
			if (personal !=null){
				data.put("nom_contacto",personal.getNombre_completo());
				data.put("cod_contacto", personal.getCodigoEmpleado());
				if (!ruc_prov.equals("")){
					parm.put("num_ruc", ruc_prov);
					parm.put("user", user);	
					MaestroContratistasBean contratista=registroPersonalService.recuperarContratistaPadron(parm);
					log.debug("paso cpntratista");
					if (contratista !=null){
						data.put("cod_cont", contratista.getCod_cont());
						data.put("num_ruc", contratista.getNum_ruc());
						data.put("raz_social", contratista.getRaz_social());
						data.put("msj_err", " ");
					}else{
						data.put("raz_social", "-1");
						data.put("msj_err", " ");
						data.put("error", "-1");
						data.put("cod_cont", "-1");
					}
				 }
			}else{
				data.put("nom_contacto","-1");
				data.put("msj_err", "");
			}
			
		} 
		catch(ServiceException ex){
			log.error("Error en AucBandejaController.validarGuardarCabJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en AucBandejaController.validarGuardarCabJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - AucBandejaController.validarGuardarCabJson");}
		}
		return new ModelAndView(jsonView, "data", JsonUtil.toString(data)) ;
	}
	
	

	
	/**
     * Registra la Cabecera del RQNP por parte de la AUC
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView registrarCabRqnp(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()) {log.debug("Inicio - AucBandejaController.registrarCabRqnp");}
		Map<String, Object> params 		= new HashMap<String, Object>();
		boolean okRunning = false;
		Map<String, Object>  mapRqnp 	= new HashMap<String, Object> ();
		Map<String, Object> parm = new HashMap<String, Object>();
		List<Map<String, Object>> listaBienes = new ArrayList<Map<String, Object>>();
		String cod_req	="";
		String user	="";
		String visor	="";
		 
		try {
			UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
			user = usuarioBean.getLogin();
					
			MaestroPersonalBean  maestroPersonal=  (MaestroPersonalBean) WebUtils.getSessionAttribute(request, "maestroPersonalBean");
			MaestroPersonalBean  maestroPersonalTem=null;
				
			//String motivoSol = StringUtils.trim(request.getParameter("txtMotivo"));
			String cod_contacto = StringUtils.trim(request.getParameter("txtCod_contacto"));
			String anexo_contacto = StringUtils.trim(request.getParameter("txtAnexo_contacto"));
			String cod_necesidad = StringUtils.trim(request.getParameter("txtCod_necesidad"));
			String cod_tip_nececidad = "01";
			String nom_tip_necesidad = StringUtils.trim(request.getParameter("txtNom_tip_necesidad"));
			
			String cod_finalidad = StringUtils.trim(request.getParameter("txtCod_finalidad"));
			String num_ruc = StringUtils.trim(request.getParameter("txtNum_ruc_prov"));
			String cod_proveedor="";
			if (!num_ruc.equals("")){
				cod_proveedor = StringUtils.trim(request.getParameter("txtCod_proveedor"));
			}
			
			String obs_justificacion = StringUtils.trim(request.getParameter("txtObs_justificacion"));
			String obs_plazo_entrega = StringUtils.trim(request.getParameter("txtObs_plazo_entrega"));
			String obs_lugar_entrega = StringUtils.trim(request.getParameter("txtObs_lugar_entrega"));
			String obs_dir_entrega = StringUtils.trim(request.getParameter("txtObs_dir_entrega"));
			String des_proceso 	= StringUtils.trim(request.getParameter("txtDesProceso"));
			String des_meta	= StringUtils.trim(request.getParameter("txtMetaInst"));
			String cod_uuoo1 	= StringUtils.trim(request.getParameter("txtCod_uuoo1"));
			String cod_uuoo2 	= StringUtils.trim(request.getParameter("txtCod_uuoo2"));
			String cod_uuoo3	= StringUtils.trim(request.getParameter("txtCod_uuoo3"));
			String cod_objeto	= StringUtils.trim(request.getParameter("txtCod_objeto"));
			String anio_atencion = StringUtils.trim(request.getParameter("txtAnio_atencion"));
			String mes_atencion = StringUtils.trim(request.getParameter("txtMes_atencion"));
			
			String tipo_vinculo = StringUtils.trim(request.getParameter("txtTipo_vinculo"));
			String vinculo 		= StringUtils.trim(request.getParameter("txtVinculo"));
			
			String ind_vinculo = StringUtils.trim(request.getParameter("jtipoRegistroVinculo"));
			String ind_prestamo = StringUtils.trim(request.getParameter("jtipoRegistroPrestamo"));
			
			String motivoSolicitud 		= StringUtils.trim(request.getParameter("txtMotivo"));
			
			//DPORRASC - AGREGADO PARA EL PASE
			String ind_comite= StringUtils.trim(request.getParameter("jtipoRegistroComite"));
			String cod_au_tit= StringUtils.trim(request.getParameter("txtCod_au_tit"));
			String cod_au_sup = StringUtils.trim(request.getParameter("txtCod_au_sup"));
			
			String ind_tec_tit = StringUtils.trim(request.getParameter("jtipoRegistroComiteTecTit"));
			String ind_tec_sup = StringUtils.trim(request.getParameter("jtipoRegistroComiteTecSup"));
			
			String cod_tec_tit = StringUtils.trim(request.getParameter("txtCod_tec_tit"));
			String cod_tec_sup = StringUtils.trim(request.getParameter("txtCod_tec_sup"));
			
			String nom_tec_tit = StringUtils.trim(request.getParameter("txtNom_tec_tit"));
			String nom_tec_sup = StringUtils.trim(request.getParameter("txtNom_tec_sup"));
			//DPORRASC - AGREGADO PARA EL PASE
			
			cod_req = StringUtils.trim(request.getParameter("txtCodigoRqnp"));
				
			visor	= StringUtils.trim(request.getParameter("txtvisor"));
			params.put("codigoRqnp", cod_req);
			params.put("cod_contacto", cod_contacto);
			params.put("anexo_contacto", anexo_contacto);
			params.put("cod_necesidad", cod_necesidad);
			params.put("cod_tip_nececidad", cod_tip_nececidad);
			params.put("nom_tip_necesidad", nom_tip_necesidad);
			
			params.put("cod_finalidad", cod_finalidad);
			params.put("cod_proveedor", cod_proveedor);
			params.put("obs_justificacion", obs_justificacion);
			params.put("obs_plazo_entrega", obs_plazo_entrega);
			params.put("obs_lugar_entrega", obs_lugar_entrega);
			params.put("obs_dir_entrega", obs_dir_entrega);
			
			params.put("des_proceso", des_proceso);
			params.put("des_meta", des_meta);
			params.put("cod_uoc1", cod_uuoo1);
			params.put("cod_uoc2", cod_uuoo2);
			params.put("cod_uoc3", cod_uuoo3);
			params.put("cod_objeto",cod_objeto);
			params.put("anio_atencion",anio_atencion);
			params.put("mes_atencion",mes_atencion);
			params.put("motivoSolicitud",motivoSolicitud);
			params.put("tipo_vinculo", tipo_vinculo);
			params.put("vinculo", vinculo);
			
			if(ind_vinculo.equals("N") || ind_vinculo.equals("") || ind_vinculo==null){
				tipo_vinculo = "04"; //04:NINGUNO
				vinculo = "01";//01:NINGUNO
				ind_vinculo="N";
			}
			
			if(ind_prestamo.equals("") || ind_prestamo==null){
				ind_prestamo="N";
			}
			
			///////////////////////////////////
			params.put("ind_vinculo", ind_vinculo);
			params.put("ind_prestamo", ind_prestamo);
			///////////////////////////////////
			
			//COMITE DEL REQUERIMIENTO
			if(ind_comite.equals("")||ind_comite.equals("N")||ind_comite==null){
				params.put("ind_comite", "N");
			}else{
				params.put("ind_comite", ind_comite);
			}
			
			if(ind_tec_tit.equals("")||ind_tec_tit.equals("N")||ind_tec_tit==null){
				params.put("ind_tec_tit", "N");
			}else{
				params.put("ind_tec_tit", ind_tec_tit);
			}
			
			if(ind_tec_sup.equals("")||ind_tec_sup.equals("N")||ind_tec_sup==null){
				params.put("ind_tec_sup", "N");
			}else{
				params.put("ind_tec_sup", ind_tec_sup);
			}
			
			
			params.put("cod_au_tit", cod_au_tit);
			params.put("cod_au_sup", cod_au_sup);
			params.put("cod_tec_tit", cod_tec_tit);
			params.put("cod_tec_sup", cod_tec_sup);
			params.put("nom_tec_tit", nom_tec_tit);
			params.put("nom_tec_sup", nom_tec_sup);
			//FIN AGREGADO: DPORRASC
			
			params.put("user", user);
			
			//implimentar un nuevo update
			cod_req=requerimientoAucService.registrarCabRqnpAuc(params);
			
			RequerimientoNoProgramadoBean rqnp= requerimientoAucService.recuperarRqnp(cod_req);
			listaBienes = (List<Map<String, Object>>) rqnp.getListaBienes();
			
			okRunning = true;
			
			mapRqnp.put("codigoRqnp", rqnp.getCodigoRqnp());
			mapRqnp.put("codigoReqNoProgramado", rqnp.getCodigoReqNoProgramado());
			mapRqnp.put("codigoResposanble", rqnp.getNumeroRegistro());
			mapRqnp.put("resposanble", rqnp.getNombreSolicitante());
			mapRqnp.put("codigoDependencia", rqnp.getCodigoDependencia());
			mapRqnp.put("codigoUuoo", rqnp.getUuoo());
			mapRqnp.put("dependencia", rqnp.getNombreUOSolicitante());
			mapRqnp.put("fechaRqnp",new SimpleDateFormat("dd/MM/yyyy HH:mm").format(rqnp.getFechaRqnp()));
			mapRqnp.put("motivoRqnp",rqnp.getMotivoSolicitud());
			mapRqnp.put("montoRqnp",rqnp.getMontoRqnp());		
			MaestroPersonalBean personal =registroPersonalService.obtenerPersonaxCodigo( rqnp.getCod_contacto().trim());
			mapRqnp.put("cod_contacto", rqnp.getCod_contacto());
			mapRqnp.put("anexo_contacto", rqnp.getAnexo_contacto());
			mapRqnp.put("cod_necesidad", rqnp.getCod_necesidad());
			mapRqnp.put("cod_tip_nececidad", rqnp.getCod_tip_nececidad());
			mapRqnp.put("nom_tip_necesidad", rqnp.getNom_tip_necesidad());
			mapRqnp.put("nom_contacto", personal.getNombre_completo());
			mapRqnp.put("reg_contacto", personal.getNumero_registro());
			mapRqnp.put("cod_finalidad", rqnp.getCod_finalidad());
			
			if ((rqnp.getCod_proveedor() != null) && 
				(!rqnp.getCod_proveedor().equals(""))) {
				parm.put("cod_cont", rqnp.getCod_proveedor());
				parm.put("user", user);
	
				MaestroContratistasBean contratista = this.registroPersonalService.recuperarContratista(parm);
				if (contratista != null) {
					mapRqnp.put("cod_cont", contratista.getCod_cont());
					mapRqnp.put("num_ruc", contratista.getNum_ruc());
					mapRqnp.put("raz_social", contratista.getRaz_social());
				}
			}		
			
			mapRqnp.put("obs_justificacion", rqnp.getObs_justificacion());
			mapRqnp.put("obs_plazo_entrega", rqnp.getObs_plazo_entrega());
			mapRqnp.put("obs_lugar_entrega", rqnp.getObs_lugar_entrega());
			mapRqnp.put("obs_dir_entrega", rqnp.getObs_dir_entrega());
			mapRqnp.put("ind_vinculo", rqnp.getInd_vinculo());
			mapRqnp.put("ind_prestamo", rqnp.getInd_prestamo());
			
		}
	    catch (ServiceException ex)
	    {
	      log.error("Error en AucBandejaController.registrarCabRqnp: " + ex.getMessage());
	      request.setAttribute("excepAttr", ex);
	      okRunning = false;
	    }
	    catch (Exception ex) {
	      log.error("Error en AucBandejaController.registrarCabRqnp: " + ex.getMessage(), ex);
	    }
	    finally {
	      if (log.isDebugEnabled()) log.debug("Fin - AucBandejaController.registrarCabRqnp");
	    }

	    if (!okRunning) {
	      return new ModelAndView("pagErrorSistema");
	    }
	    return new ModelAndView("formAucRqnpDetalle").addObject("mapRqnp", mapRqnp).addObject("visor", visor)
	      .addObject("listaBienes", listaBienes);
	  }
	
	
	/**
     * Registra la Cabecera del RQNP por parte de la AUC
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView registrarModificaBienRqnp(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()) {log.debug("Inicio - AucBandejaController.registrarModificaBienRqnp");}
	
		boolean okRunning = false;
		Map<String, Object>  mapRqnp 	= new HashMap<String, Object> ();
		Map<String, Object> parm = new HashMap<String, Object>();
	
		List<Map<String, Object>> listaBienes = new ArrayList<Map<String, Object>>();
		RequerimientoNoProgBienesBean bien = new RequerimientoNoProgBienesBean();
		String cod_req	="";
		String user	="";
		String visor	="";
		 
		try {
			UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
			user = usuarioBean.getLogin();
			
			MaestroPersonalBean  maestroPersonal=  (MaestroPersonalBean) WebUtils.getSessionAttribute(request, "maestroPersonalBean");
			
			String precioUnid = StringUtils.trim(request.getParameter("txtPrecioM"));
			String codigoUnidad = StringUtils.trim(request.getParameter("txtCod_Unidad"));
			String precio_item = StringUtils.trim(request.getParameter("txtPrecioM"));
			String codigoBien = StringUtils.trim(request.getParameter("txtCod_bienM"));
			String codigoRqnp = StringUtils.trim(request.getParameter("txtCodigoRqnp"));
			String item_origen = StringUtils.trim(request.getParameter("txtCod_bien"));
		
			visor	= StringUtils.trim(request.getParameter("txtvisor"));
			bien.setCodigoRqnp(codigoRqnp);
			bien.setCodigoBien(codigoBien);
			bien.setCodigoUnidad(codigoUnidad);
			
			bien.setPrecio_item(new BigDecimal( precio_item));
			bien.setPrecioUnid( new BigDecimal( precioUnid));
			
			bien.setUser_modi( maestroPersonal.getCodigoEmpleado());
			bien.setCod_jefe(user);
			bien.setAuc_modi(maestroPersonal.getCodigoDependencia());
			bien.setItem_origen(item_origen);
			bien.setFec_item_modi( new Date());
			
			//implimentar un nuevo update
			requerimientoAucService.registrarCambioBien(bien);
			
			RequerimientoNoProgramadoBean rqnp= requerimientoAucService.recuperarRqnp(codigoRqnp);
			listaBienes = (List<Map<String, Object>>) rqnp.getListaBienes();
			
			okRunning = true;
			
			mapRqnp.put("codigoRqnp", rqnp.getCodigoRqnp());
			mapRqnp.put("codigoReqNoProgramado", rqnp.getCodigoReqNoProgramado());
			mapRqnp.put("codigoResposanble", rqnp.getNumeroRegistro());
			mapRqnp.put("resposanble", rqnp.getNombreSolicitante());
			mapRqnp.put("codigoDependencia", rqnp.getCodigoDependencia());
			mapRqnp.put("codigoUuoo", rqnp.getUuoo());
			mapRqnp.put("dependencia", rqnp.getNombreUOSolicitante());
			mapRqnp.put("fechaRqnp",new SimpleDateFormat("dd/MM/yyyy HH:mm").format(rqnp.getFechaRqnp()));
			mapRqnp.put("motivoRqnp",rqnp.getMotivoSolicitud());
			mapRqnp.put("montoRqnp",rqnp.getMontoRqnp());
			
			MaestroPersonalBean personal =registroPersonalService.obtenerPersonaxCodigo( rqnp.getCod_contacto().trim());
			mapRqnp.put("cod_contacto", rqnp.getCod_contacto());
			mapRqnp.put("anexo_contacto", rqnp.getAnexo_contacto());
			mapRqnp.put("cod_necesidad", rqnp.getCod_necesidad());
			mapRqnp.put("cod_tip_nececidad", rqnp.getCod_tip_nececidad());
			mapRqnp.put("nom_tip_necesidad", rqnp.getNom_tip_necesidad());
			mapRqnp.put("nom_contacto", personal.getNombre_completo());
			mapRqnp.put("reg_contacto", personal.getNumero_registro());
			
			mapRqnp.put("cod_finalidad", rqnp.getCod_finalidad());
			
			if ((rqnp.getCod_proveedor() != null) && 
				(!rqnp.getCod_proveedor().equals(""))) {
					parm.put("cod_cont", rqnp.getCod_proveedor());
					parm.put("user", user);

					MaestroContratistasBean contratista = this.registroPersonalService.recuperarContratista(parm);
					if (contratista != null) {
						mapRqnp.put("cod_cont", contratista.getCod_cont());
						mapRqnp.put("num_ruc", contratista.getNum_ruc());
						mapRqnp.put("raz_social", contratista.getRaz_social());
					}
			}	
			
			mapRqnp.put("obs_justificacion", rqnp.getObs_justificacion());
			mapRqnp.put("obs_plazo_entrega", rqnp.getObs_plazo_entrega());
			mapRqnp.put("obs_lugar_entrega", rqnp.getObs_lugar_entrega());
			mapRqnp.put("obs_dir_entrega", rqnp.getObs_dir_entrega());
		} 
		catch (ServiceException ex) {
			log.error("Error en AucBandejaController.registrarModificaBienRqnp: " + ex.getMessage());
			request.setAttribute("excepAttr", ex);
			okRunning=false;
		} 
		catch (Exception ex) {
			log.error("Error en AucBandejaController.registrarModificaBienRqnp: " + ex.getMessage(), ex);
		} 
		finally {
			if (log.isDebugEnabled()){ log.debug("Fin - AucBandejaController.registrarModificaBienRqnp");}
		}
		
		if(!okRunning){
			return new ModelAndView("pagErrorSistema");
		}else{
			return new ModelAndView("formAucRqnpDetalle").addObject("mapRqnp", mapRqnp).addObject("visor", visor).
			addObject("listaBienes",listaBienes);
		}
	}
	
	/**
     * Recuperar Lista de Finalidad en Json.
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView listarNecesidadJson(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - AucBandejaController.listarNecesidadJson");}
		Map<String, Object> parm = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		List<Map<String, Object>> lsNecesidad= new ArrayList<Map<String, Object>>();
		String user="";
		String num_uuoo="" ;
		try {
			
			lsNecesidad = (List<Map<String, Object>>) requerimientoNoProgramadoService.listaNecesidad();
			
			Map<String,Object> respuesta = new HashMap<String,Object>();
			data.put("identifier", "cod");
			data.put("label", "name");
			data.put("items", lsNecesidad);
		} 
		catch(ServiceException ex){
			log.error("Error en AucBandejaController.listarNecesidadJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en AucBandejaController.listarNecesidadJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - AucBandejaController.listarNecesidadJson");}
		}
		return new ModelAndView(jsonView,  data) ;
	}
	
	
	/**
     * Recuperar Lista de Finalidad en Json.
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView listarFinalidadJson(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - AucBandejaController.listarFinalidadJson");}
		Map<String, Object> parm = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		List<Map<String, Object>> lsFinalidad= new ArrayList<Map<String, Object>>();
		String user="";
		String num_uuoo="" ;
		try {
			
			lsFinalidad= (List<Map<String, Object>>) requerimientoNoProgramadoService.listaFinalidad();
			
			data.put("identifier", "cod");
			data.put("label", "name");
			data.put("items", lsFinalidad);
			 
		} 
		catch(ServiceException ex){
			log.error("Error en AucBandejaController.listarFinalidadJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en AucBandejaController.listarFinalidadJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - AucBandejaController.listarFinalidadJson");}
		}
		return new ModelAndView(jsonView,  data) ;
	}
	
	
	/**
     * Recuperar Lista de A�os en Json.
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView listarAnnosJson(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - AucBandejaController.listarAnnosJson");}
		Map<String, Object> parm = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		List<Map<String, Object>> lsannos= new ArrayList<Map<String, Object>>();
		String user="";
		String num_uuoo="" ;
		try {
		
			lsannos= (List<Map<String, Object>>) requerimientoAucService.listarAnnos();
			 
			 data.put("identifier", "cod");
			 data.put("label", "name");
			 data.put("items", lsannos);
		} 
		catch(ServiceException ex){
			log.error("Error en AucBandejaController.listarAnnosJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en AucBandejaController.listarAnnosJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - AucBandejaController.listarAnnosJson");}
		}
		return new ModelAndView(jsonView,  data) ;
	}
	
	
	/**
     * Recuperar Lista de Meses en Json.
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView listarMesesJson(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - AucBandejaController.listarMesesJson");}
		Map<String, Object> parm = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		List<Map<String, Object>> lsmeses= new ArrayList<Map<String, Object>>();
		String user="";
		String num_uuoo="" ;
		try {
			lsmeses= (List<Map<String, Object>>) requerimientoAucService.listarMeses();
			 
			data.put("identifier", "cod");
			data.put("label", "name");
			data.put("items", lsmeses);
		} 
		catch(ServiceException ex){
			log.error("Error en AucBandejaController.listarMesesJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en AucBandejaController.listarMesesJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - AucBandejaController.listarMesesJson");}
		}
		return new ModelAndView(jsonView,  data) ;
	}
	
	
	/**
     * Recuperar Lista de Vinculo en Json.
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView listarVinculoEditarJson(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - AucBandejaController.listarVinculoEditarJson");}
		Map<String, Object> parm = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		List<Map<String, Object>> lsVinculo= new ArrayList<Map<String, Object>>();
		
		try {
			String ls_tipo_vinculo =StringUtils.trim( request.getParameter("jtipo_vinculo"));
			log.debug("ls_tipo_vinculo:>>>>>>>"+ls_tipo_vinculo);
			if (ls_tipo_vinculo != null ){
				if (!ls_tipo_vinculo.equals("")){
					lsVinculo = (List<Map<String, Object>>) requerimientoNoProgramadoService.listarVinculo(ls_tipo_vinculo);
				}
			}
			 Map<String,Object> respuesta = new HashMap<String,Object>();
			 data.put("identifier", "cod");
			 data.put("label", "name");
			 data.put("items", lsVinculo);
		} 
		catch(ServiceException ex){
			log.error("Error en AucBandejaController.listarVinculoEditarJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en AucBandejaController.listarVinculoEditarJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - AucBandejaController.listarVinculoEditarJson");}
		}
		return new ModelAndView(jsonView,  data) ;
	}
	
	/**
     * Recuperar los Datos de Entrega del Bien .
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView recuperarBienEntregaJson(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - AucBandejaController.recuperarBienEntregaJson");}
		Map<String, Object> parm = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		String codigoRqnp="";
		String codigoBien="";
	
		String user="";
		codigoRqnp 				= 	StringUtils.trim(request.getParameter("txtCodigoRqnp"));
		codigoBien				= 	StringUtils.trim(request.getParameter("txtCodigoBien"));
		
		try {
			UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
			user= usuarioBean.getLogin();
			RequerimientoNoProgBienesBean bien= requerimientoNoProgramadoService.recuperarDatosEntregaBien(codigoRqnp, codigoBien);
			 if (bien.getCod_proveedor() !=null) {
				 parm.put("cod_cont", bien.getCod_proveedor());
				 parm.put("user", user);	
				 MaestroContratistasBean contratista=registroPersonalService.recuperarContratista(parm);
				if (contratista!=null)	 {
				 data.put("cod_cont",( contratista.getCod_cont()==null)?"":contratista.getCod_cont());
				 data.put("num_ruc", (contratista.getNum_ruc()==null)?"":contratista.getNum_ruc());
				 data.put("raz_social", (contratista.getRaz_social()==null)?"":contratista.getRaz_social());
				}
			 }
			
			data.put("obs_justificacion",( bien.getObs_justificacion()==null)?"":bien.getObs_justificacion());
			data.put("obs_plazo_entrega",( bien.getObs_plazo_entrega()==null)?"" : bien.getObs_plazo_entrega() );
			data.put("obs_lugar_entrega",( bien.getObs_lugar_entrega()==null)?"":bien.getObs_lugar_entrega());
			data.put("obs_dir_entrega", (bien.getObs_dir_entrega()==null) ?"":bien.getObs_dir_entrega());
			
			data.put("cod_bien", (bien.getCodigoBien() ==null) ?"":bien.getCodigoBien());
			data.put("des_bien", (bien.getDesBien()==null) ?"":bien.getDesBien());
			data.put("des_unidad", (bien.getDesUnid()==null) ?"":bien.getDesUnid());
			data.put("des_tecnica", (bien.getDes_tecnica()==null) ?"":bien.getDes_tecnica());
		} 
		catch(ServiceException ex){
			log.error("Error en AucBandejaController.recuperarBienEntregaJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en AucBandejaController.recuperarBienEntregaJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - AucBandejaController.recuperarBienEntregaJson");}
		}
		return new ModelAndView(jsonView, "data", JsonUtil.toString(data)) ;
	}

	
	
	/**
     * Recuperar los Datos de Entrega del Bien .
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView recuperarBienModificar(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - AucBandejaController.recuperarBienModificar");}
		Map<String, Object> parm = new HashMap<String, Object>();
		Map<String, Object> mapRqnp = new HashMap<String, Object>();
		Map<String, Object> lsCatalogo 	= 	new HashMap<String, Object>();
		
		String codigoRqnp="";
		String codigoBien="";
	
		String user="";
		codigoRqnp 				= 	StringUtils.trim(request.getParameter("txtCodigoRqnp"));
		codigoBien				= 	StringUtils.trim(request.getParameter("txtCodigoBien"));
		
		try {
			UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
			user= usuarioBean.getLogin();
			RequerimientoNoProgramadoBean rqnp = requerimientoNoProgramadoService.recuperarRqnpCabAUC(codigoRqnp);
			parm.put("cod_req", codigoRqnp);
			//parm.put("cod_dep_user", cod_dep_user);
			 
			mapRqnp= requerimientoAucService.recuperarRqnpCab(parm);
			RequerimientoNoProgBienesBean bien= requerimientoNoProgramadoService.recuperarDatosBienModifica(codigoRqnp, codigoBien);
			
			//mapRqnp.put("resposanble", (rqnp.get ==null) ?"":rqnp.getCodigoRqnp());
			
			mapRqnp.put("cod_bien", (bien.getCodigoBien() ==null) ?"":bien.getCodigoBien());
			mapRqnp.put("des_bien", (bien.getDesBien()==null) ?"":bien.getDesBien());
			mapRqnp.put("des_unidad", (bien.getDesUnid()==null) ?"":bien.getDesUnid());
			mapRqnp.put("des_tecnica", (bien.getDes_tecnica()==null) ?"":bien.getDes_tecnica());
			mapRqnp.put("unid_precio", (bien.getPrecioUnid()==null) ?"":bien.getPrecioUnid());
			 
			mapRqnp.put("item_origen", (bien.getCodigoBien() ==null) ?"":bien.getItem_origen());
			mapRqnp.put("desBien_origen", (bien.getDesBien()==null) ?"":bien.getDesBien_origen());
			mapRqnp.put("desUnid_origen", (bien.getDesUnid()==null) ?"":bien.getDesUnid_origen());
			mapRqnp.put("unid_precio_origen", (bien.getPrecioUnid()==null) ?"":bien.getPrecio_origen());
	
		} 
		catch(ServiceException ex){
			log.error("Error en AucBandejaController.recuperarBienModificar: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en AucBandejaController.recuperarBienModificar: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - AucBandejaController.recuperarBienModificar");}
		}
		//return new ModelAndView(jsonView, "data", JsonUtil.toString(data)) ;
		return new ModelAndView("formAucRqnpBien").addObject("mapRqnp", mapRqnp).addObject("lsCatalogo",lsCatalogo);
	}
	
	
	/**
     * Registra los datos de la entrega de cada item del requerimiento y devulve un  Json (afectaci�n presupuestal).
     * @param  request
     * @return ModelAndView 
     **/
	public ModelAndView registrarDetalleEntregaBienJson(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()) {log.debug("Inicio - RqnpDetalleController.registrarDetalleEntregaBienJson");}
		
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		boolean okRunning = false;
		
		String user			="";
		String codigoRqnp="";
		String codigoBien="";
		String cod_proveedor="";
		String num_ruc ="";
		String obs_justificacion="";
		String obs_plazo_entrega="";
		String obs_lugar_entrega="";
		String obs_dir_entrega="";
		String des_tecnica="";
	
		try {
			
			UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
			user= usuarioBean.getLogin();
			codigoRqnp 				= 	StringUtils.trim(request.getParameter("txtCodigoRqnp"));
			codigoBien				= 	StringUtils.trim(request.getParameter("txtCodigoBien"));
			num_ruc 				= 	StringUtils.trim(request.getParameter("txtNum_ruc_prov"));
			obs_justificacion 		= 	StringUtils.trim(request.getParameter("txtObs_justificacion"));
			obs_plazo_entrega 		= 	StringUtils.trim(request.getParameter("txtObs_plazo_entrega"));
			obs_lugar_entrega 		= 	StringUtils.trim(request.getParameter("txtObs_lugar_entrega"));
			obs_dir_entrega 		= 	StringUtils.trim(request.getParameter("txtObs_dir_entrega"));
			des_tecnica 		= 	StringUtils.trim(request.getParameter("txtDesTec"));
			
			if (!num_ruc.equals("")){
				cod_proveedor 			= 	StringUtils.trim(request.getParameter("txtCod_proveedor3"));
			}
			params.put("codigoRqnp", codigoRqnp);
			params.put("codigoBien", codigoBien);
			params.put("cod_proveedor", cod_proveedor);
			params.put("obs_justificacion",obs_justificacion );
			
			params.put("obs_plazo_entrega",obs_plazo_entrega);
			params.put("obs_lugar_entrega",obs_lugar_entrega);
			params.put("obs_dir_entrega",obs_dir_entrega);
			params.put("des_tecnica",des_tecnica);
			
			params.put("user", user);
			///guardando metas
			requerimientoNoProgramadoService.registrarDetalleEntregaBien(params);
			data.put("mensaje", messageSource.getMessage("formRegistro.mensaje.registroAtender" , null, Locale.getDefault()));
			okRunning=true; 
		} 
		
		catch (ServiceException ex) {
			log.error("Error en RqnpDetalleController.registrarDetalleEntregaBienJson: " + ex.getMessage());
		} 
		catch (Exception ex) {
			log.error("Error en RqnpDetalleController.registrarDetalleEntregaBienJson: " + ex.getMessage(), ex);
		} 
		finally {
			if (log.isDebugEnabled()){ log.debug("Fin - RqnpDetalleController.registrarDetalleEntregaBienJson");}
		}
		
		if(!okRunning){
			data.put("error", "-1");
			data.put("mensaje", messageSource.getMessage("error.registrarAtencion.generico" , null, Locale.getDefault()));		
			return new ModelAndView(jsonView, "data", data);
		}
		return new ModelAndView(jsonView, "data", JsonUtil.toString(data)) ;
	}
	
	/**
     * Registra la Cabecera del RQNP por parte de la AUC
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView recuperarDetalleAUC(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()) {log.debug("Inicio - AucBandejaController.recuperarDetalleAUC");}
		Map<String, Object> params 		= new HashMap<String, Object>();
		boolean okRunning = false;
		Map<String, Object>  mapRqnp 	= new HashMap<String, Object> ();
		Map<String, Object> parm = new HashMap<String, Object>();
		List<Map<String, Object>> lsNecesidad= new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> lsTipoNecesidad= new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> lsFinalidad	= new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listaBienes = new ArrayList<Map<String, Object>>();
		String cod_req	="";
		String user	="";
		String visor	="";
		 
		try {
			cod_req = StringUtils.trim(request.getParameter("txtCodigoRqnp"));
				
			visor	= StringUtils.trim(request.getParameter("txtvisor"));
			params.put("codigoRqnp", cod_req);
		
			RequerimientoNoProgramadoBean rqnp= requerimientoAucService.recuperarRqnp(cod_req);
			listaBienes = (List<Map<String, Object>>) rqnp.getListaBienes();
			
			okRunning = true;
			
			mapRqnp.put("codigoRqnp", rqnp.getCodigoRqnp());
			mapRqnp.put("codigoReqNoProgramado", rqnp.getCodigoReqNoProgramado());
			mapRqnp.put("codigoResposanble", rqnp.getNumeroRegistro());
			mapRqnp.put("resposanble", rqnp.getNombreSolicitante());
			mapRqnp.put("codigoDependencia", rqnp.getCodigoDependencia());
			mapRqnp.put("codigoUuoo", rqnp.getUuoo());
			mapRqnp.put("dependencia", rqnp.getNombreUOSolicitante());
			mapRqnp.put("fechaRqnp",new SimpleDateFormat("dd/MM/yyyy HH:mm").format(rqnp.getFechaRqnp()));
			mapRqnp.put("motivoRqnp",rqnp.getMotivoSolicitud());
			mapRqnp.put("montoRqnp",rqnp.getMontoRqnp());
			MaestroPersonalBean personal =registroPersonalService.obtenerPersonaxCodigo( rqnp.getCod_contacto().trim());
			mapRqnp.put("cod_contacto", rqnp.getCod_contacto());
			mapRqnp.put("anexo_contacto", rqnp.getAnexo_contacto());
			mapRqnp.put("cod_necesidad", rqnp.getCod_necesidad());
			mapRqnp.put("cod_tip_nececidad", rqnp.getCod_tip_nececidad());
			mapRqnp.put("nom_tip_necesidad", rqnp.getNom_tip_necesidad());
			mapRqnp.put("nom_contacto", personal.getNombre_completo());
			mapRqnp.put("reg_contacto", personal.getNumero_registro());
			
			mapRqnp.put("cod_finalidad", rqnp.getCod_finalidad());
			
			if (rqnp.getCod_proveedor() !=null) {
				if (!rqnp.getCod_proveedor().equals("")){
					parm.put("cod_cont", rqnp.getCod_proveedor());
					parm.put("user", user);	
					
					MaestroContratistasBean contratista=registroPersonalService.recuperarContratista(parm);
					if (contratista!=null){
						mapRqnp.put("cod_cont", contratista.getCod_cont());
						mapRqnp.put("num_ruc", contratista.getNum_ruc());
						mapRqnp.put("raz_social", contratista.getRaz_social());
					}
				}
			}		 
			mapRqnp.put("obs_justificacion", rqnp.getObs_justificacion());
			mapRqnp.put("obs_plazo_entrega", rqnp.getObs_plazo_entrega());
			mapRqnp.put("obs_lugar_entrega", rqnp.getObs_lugar_entrega());
			mapRqnp.put("obs_dir_entrega", rqnp.getObs_dir_entrega());
			 
			lsFinalidad= (List<Map<String, Object>>) requerimientoNoProgramadoService.listaFinalidad();
			 
			lsNecesidad = (List<Map<String, Object>>) requerimientoNoProgramadoService.listaNecesidad();
			lsTipoNecesidad = (List<Map<String, Object>>) requerimientoNoProgramadoService.listaTipoNecesidad();	
			
		}catch (ServiceException ex) {
			log.error("Error en AucBandejaController.registrarCabRqnp: " + ex.getMessage());
			request.setAttribute("excepAttr", ex);
			okRunning=false;
		}catch (Exception ex) {
			log.error("Error en AucBandejaController.recuperarDetalleAUC: " + ex.getMessage(), ex);
		}finally {
			if (log.isDebugEnabled()){ log.debug("Fin - AucBandejaController.recuperarDetalleAUC");}
		}
		
		if(!okRunning){
			return new ModelAndView("pagErrorSistema");
		}
		
		return new ModelAndView("formAucRqnpDetalle").addObject("mapRqnp", mapRqnp).addObject("visor", visor).
		addObject("lsNecesidad", lsNecesidad).addObject("listaBienes",listaBienes).
		addObject("lsTipoNecesidad", lsTipoNecesidad).addObject("lsFinalidad", lsFinalidad);
		
	}
	

	/**
     * Busca los bienes y servicios del Catalogo .
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView buscarCatalogoJson(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - AucBandejaController.buscarCatalogoJson");}
		Map<String, Object>  mapCatalogo = new HashMap<String, Object> ();
		Map<String, Object>  data = new HashMap<String, Object> ();
		String anio_ejec="";
		try {
			String parm = StringUtils.trim(request.getParameter("jParametro"));
			String tipo = StringUtils.trim(request.getParameter("jtipoConsulta"));
			String cod_bien = StringUtils.trim(request.getParameter("txtCod_bien"));
			String cod_familia = cod_bien.substring(0, 9)+"%";
			log.debug(cod_bien) ;
			log.debug(cod_familia) ;
			Calendar fecha =  Calendar.getInstance();
			anio_ejec= String.valueOf( fecha.get(Calendar.YEAR)) ;
			
			parm= parm.toUpperCase().replace(" ", "%");
			MaestroPersonalBean  maestroPersonal=  (MaestroPersonalBean) WebUtils.getSessionAttribute(request, "maestroPersonalBean");
			
			String cod_uuoo = maestroPersonal.getCodigoDependencia();
			
			mapCatalogo.put("parm", parm);
			mapCatalogo.put("tipo", tipo);
			mapCatalogo.put("cod_uuoo", cod_uuoo);
			mapCatalogo.put("cod_familia", cod_familia);
			
			List<Map<String, Object>> lsCatalogo = (List<Map<String, Object>>) requerimientoNoProgramadoService.listarCatalogoFamilia(mapCatalogo);
			data.put("identifier", "codigo_bien");
	
			data.put("items", lsCatalogo);
		} 
		catch(ServiceException ex){
			log.error("Error en AucBandejaController.buscarCatalogoJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en AucBandejaController.buscarCatalogoJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin -AucBandejaController.buscarCatalogoJson");}
		}
		return new ModelAndView(jsonView, data) ;
	}
	
	
	
	/**
     * Permite Rechazar el RQNP por el Jefe Inmediato o Itendente.
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView rechazarRqnp(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("Inicio - AucBandejaController.rechazarRqnp");}
	
		List<Map<String, Object>> lsNecesidad= new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> lsTipoNecesidad= new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> lsFinalidad	= new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listaBienes = new ArrayList<Map<String, Object>>();
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object>  mapRqnp 	= new HashMap<String, Object> ();
		Map<String, Object> params = new HashMap<String, Object>();
		List<String> lista = new ArrayList<String>();
		String visor	="";
	
		boolean okRunning = false;
		Exception exError = null;
		String valor	="";
		String cod_req	="";
		String cod_bie	="";
		String ind_int	="";
		String cod_bie_env	="";
		String ls_anio		="";
		String ind_especializado="";
		String user			="";
		String is_bandeja	="AUCT";
	
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
				visor	= StringUtils.trim(request.getParameter("txtvisor"));
				
				params.put("cod_accion", "004");
				params.put("exp_obs", "REQUERIMIENTO RECHAZADO POR AUC");
			
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
					
					requerimientoAucService.registrarRechazarRqnp(params);
					data.put("flagRegistroDeclaracion", "0");
          			data.put("mensaje", "No hay problemas");
          			okRunning = true;
				}	
				if (okRunning) {
					cod_req="";
					cod_bie="";
					String cod_req_ant="";
					//----enviar correos------------------------------------------------------
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
										System.out.println(valor + " - " + cod_req_ant+ " - " + cod_bie_env +" "+is_bandeja );
										requerimientoNoProgramadoService.envioMailRechazo( cod_req_ant, cod_bie_env,is_bandeja);
									}
									cod_bie_env="";
								} 
							}else if (i==2){
								cod_bie= tokens.nextToken();
							}else{
								ind_especializado= tokens.nextToken();
							}
						}
						if (cod_bie_env.equals("") ){
							cod_bie_env =cod_bie;
						}else {
							cod_bie_env =cod_bie_env+','+cod_bie;
						}
						cod_req_ant=cod_req;
					}
					if (!cod_bie_env.equals("") ){
						requerimientoNoProgramadoService.envioMailRechazo( cod_req, cod_bie_env,is_bandeja);
					}
					//------------------------------------------
					okRunning = true;
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
			log.error("Error en AucBandejaController.rechazarRqnp: " + ex.getMessage());
		} 
		catch (Exception ex) {
			log.error("Error en AucBandejaController.rechazarRqnp: " + ex.getMessage(), ex);
		} 
		finally {
			if (log.isDebugEnabled()){ log.debug("Fin - AucBandejaController.rechazarRqnp");}
			return new ModelAndView(this.jsonView, "data", data);
		}
	}
	
	
	/**
     * Permite aprobar el RQNP por el Jefe Inmediato o Itendente.
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView contratacionRqnp(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()) {log.debug("Inicio - AucBandejaController.atenderRqnp");}
		List<Map<String, Object>> lsRqnp= new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> lsNecesidad= new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> lsTipoNecesidad= new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> lsFinalidad	= new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listaBienes = new ArrayList<Map<String, Object>>();
		Map<String, Object>  mapRqnp 	= new HashMap<String, Object> ();
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		List<String> lista = new ArrayList<String>();
		
		boolean okRunning = false;
		Exception exError=null ;
		
		String 	valor		="";
		String 	cod_req		="";
		String 	cod_bie		="";
		String 	cod_bie_env	="";
		String 	ls_anio		="";
		String 	ind_especializado="";
		String 	intendente	="N";

		String  visor 		="";
		
		String 	user="";
		int 	i=0;
		Calendar fecha =  Calendar.getInstance();
		ls_anio= String.valueOf( fecha.get(Calendar.YEAR)) ;
		
		try {
			MaestroPersonalBean  maestroPersonal=  (MaestroPersonalBean) WebUtils.getSessionAttribute(request, "maestroPersonalBean");
			UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
			user= usuarioBean.getLogin();
			if (maestroPersonal!=null){
				String[] cod_bien = request.getParameterValues("txtCodigobien");
				visor	= StringUtils.trim(request.getParameter("txtvisor"));
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
					Map<String, String> validar  = this.validarAprobarAUC(params);
					String flag = (String)validar.get("flagValidacion");
					String mensaje = (String)validar.get("mensaje");
					if(flag=="0"){
						intendente=requerimientoAucService.registrarContratacionRqnp(params);
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
					//intendente=requerimientoAucService.registrarContratacionRqnp(params);
					cod_req="";
					cod_bie="";
				}
				//fin for
				//-------------------------------------------------------------------------- 
				//okRunning = true;
				params.put("cod_per", maestroPersonal.getCodigoEmpleado());
				params.put("anio_act", ls_anio);
				///---------------------------------------------------------------------------------------
				if (okRunning) {
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
					if (!cod_bie_env.equals("")){
						requerimientoNoProgramadoService.envioMailDerivar( cod_req_ant, cod_bie_env);
					}
					
					params.clear();
					///obtener detalle de Requerimiento
					RequerimientoNoProgramadoBean rqnp= requerimientoAucService.recuperarRqnp(cod_req);
					listaBienes = (List<Map<String, Object>>) rqnp.getListaBienes();
					mapRqnp.put("codigoRqnp", rqnp.getCodigoRqnp());
					mapRqnp.put("codigoReqNoProgramado", rqnp.getCodigoReqNoProgramado());
					mapRqnp.put("codigoResposanble", rqnp.getNumeroRegistro());
					mapRqnp.put("resposanble", rqnp.getNombreSolicitante());
					mapRqnp.put("codigoDependencia", rqnp.getCodigoDependencia());
					mapRqnp.put("codigoUuoo", rqnp.getUuoo());
					mapRqnp.put("dependencia", rqnp.getNombreUOSolicitante());
					mapRqnp.put("fechaRqnp",new SimpleDateFormat("dd/MM/yyyy HH:mm").format(rqnp.getFechaRqnp()));
					mapRqnp.put("motivoRqnp",rqnp.getMotivoSolicitud());
					mapRqnp.put("montoRqnp",rqnp.getMontoRqnp());
					mapRqnp.put("cod_contacto", rqnp.getCod_contacto());
					mapRqnp.put("anexo_contacto", rqnp.getAnexo_contacto());
					mapRqnp.put("cod_necesidad", rqnp.getCod_necesidad());
					mapRqnp.put("cod_tip_nececidad", rqnp.getCod_tip_nececidad());
					mapRqnp.put("nom_tip_necesidad", rqnp.getNom_tip_necesidad());
					MaestroPersonalBean personal =registroPersonalService.obtenerPersonaxCodigo( rqnp.getCod_contacto().trim());
					
					mapRqnp.put("nom_contacto", personal.getNombre_completo());
					mapRqnp.put("reg_contacto", personal.getNumero_registro());
					mapRqnp.put("obs_justificacion", rqnp.getObs_justificacion());
					mapRqnp.put("obs_plazo_entrega", rqnp.getObs_plazo_entrega());
					mapRqnp.put("obs_lugar_entrega", rqnp.getObs_lugar_entrega());
					mapRqnp.put("obs_dir_entrega", rqnp.getObs_dir_entrega());
					mapRqnp.put("cod_finalidad", rqnp.getCod_finalidad());
					
					if (listaBienes.size() !=0 ){ 
						 if (rqnp.getCod_proveedor() !=null) {
							 if (!rqnp.getCod_proveedor().equals("")){
								 params.put("cod_cont", rqnp.getCod_proveedor());
								 params.put("user", user);	
							
								 MaestroContratistasBean contratista=registroPersonalService.recuperarContratista(params);
								 if (contratista!=null){
									 mapRqnp.put("cod_cont", contratista.getCod_cont());
									 mapRqnp.put("num_ruc", contratista.getNum_ruc());
									 mapRqnp.put("raz_social", contratista.getRaz_social());
								 }
							 }
						 }		 
						
						lsFinalidad = (List)this.requerimientoNoProgramadoService.listaFinalidad();
						lsNecesidad = (List)this.requerimientoNoProgramadoService.listaNecesidad();
						lsTipoNecesidad = (List)this.requerimientoNoProgramadoService.listaTipoNecesidad();
					}

				}// Fin (okRunning)

				return new ModelAndView(this.jsonView, "data", data);
			} //Fin MaestroPersonal

			Exception ex = new Exception("Error al Obtener Datos del Usuario");
			request.setAttribute("excepAttr", ex);
			return new ModelAndView("pagErrorSistema");
			
		}catch (ServiceException ex) {
			log.error("Error en AucBandejaController.contratacionRqnp: " + ex.getMessage());
			exError = new Exception("error.contratacionRqnp.generico");
			return new ModelAndView("pagErrorSistema");
		}catch (Exception ex){
			log.error("Error en AucBandejaController.contratacionRqnp: " + ex.getMessage(), ex);
			return new ModelAndView("pagErrorSistema");
		} finally {
			if (log.isDebugEnabled()) log.debug("Fin - AucBandejaController.contratacionRqnp");
		}
	}
	
	
	/**
     * Permite mostrar el formato FAR despues de atendido el requerimiento por la AUC
     * Requerimiento No Programado.
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	  public ModelAndView atenderMostrarFormato(HttpServletRequest request, HttpServletResponse response)
	  {
	    if (log.isDebugEnabled()) log.debug("Inicio - AucBandejaController.atenderMostrarFormato");
		List<Map<String, Object>> lsNecesidad= new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> lsTipoNecesidad= new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> lsFinalidad	= new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listaBienes = new ArrayList<Map<String, Object>>();
		Map<String, Object>  mapRqnp 	= new HashMap<String, Object> ();
		Map<String, Object> params = new HashMap<String, Object>();
	    
	    Exception exError = null;

	    String cod_req = "";
	    String ls_anio = "";
	    String visor = "";

	    String user = "";
	    int i = 0;
	    Calendar fecha = Calendar.getInstance();
	    ls_anio = String.valueOf(fecha.get(1));
	    try
	    {
	      MaestroPersonalBean maestroPersonal = (MaestroPersonalBean)WebUtils.getSessionAttribute(request, "maestroPersonalBean");
	      UsuarioBean usuarioBean = (UsuarioBean)WebUtils.getSessionAttribute(request, "usuarioBean");
	      user = usuarioBean.getLogin();

	      cod_req = StringUtils.trim(request.getParameter("txtCodigoRqnp"));

	      RequerimientoNoProgramadoBean rqnp = this.requerimientoAucService.recuperarRqnp(cod_req);
	      listaBienes = (List)rqnp.getListaBienes();
	      mapRqnp.put("codigoRqnp", rqnp.getCodigoRqnp());
	      mapRqnp.put("codigoReqNoProgramado", rqnp.getCodigoReqNoProgramado());
	      mapRqnp.put("codigoResposanble", rqnp.getNumeroRegistro());
	      mapRqnp.put("resposanble", rqnp.getNombreSolicitante());
	      mapRqnp.put("codigoDependencia", rqnp.getCodigoDependencia());
	      mapRqnp.put("codigoUuoo", rqnp.getUuoo());
	      mapRqnp.put("dependencia", rqnp.getNombreUOSolicitante());
	      mapRqnp.put("fechaRqnp", new SimpleDateFormat("dd/MM/yyyy HH:mm").format(rqnp.getFechaRqnp()));
	      mapRqnp.put("motivoRqnp", rqnp.getMotivoSolicitud());
	      mapRqnp.put("montoRqnp", rqnp.getMontoRqnp());
	      mapRqnp.put("cod_contacto", rqnp.getCod_contacto());
	      mapRqnp.put("anexo_contacto", rqnp.getAnexo_contacto());
	      mapRqnp.put("cod_necesidad", rqnp.getCod_necesidad());
	      mapRqnp.put("cod_tip_nececidad", rqnp.getCod_tip_nececidad());
	      mapRqnp.put("nom_tip_necesidad", rqnp.getNom_tip_necesidad());
	      MaestroPersonalBean personal = this.registroPersonalService.obtenerPersonaxCodigo(rqnp.getCod_contacto().trim());
	      mapRqnp.put("nom_contacto", personal.getNombre_completo());
	      mapRqnp.put("reg_contacto", personal.getNumero_registro());
	      mapRqnp.put("obs_justificacion", rqnp.getObs_justificacion());
	      mapRqnp.put("obs_plazo_entrega", rqnp.getObs_plazo_entrega());
	      mapRqnp.put("obs_lugar_entrega", rqnp.getObs_lugar_entrega());
	      mapRqnp.put("obs_dir_entrega", rqnp.getObs_dir_entrega());
	      mapRqnp.put("cod_finalidad", rqnp.getCod_finalidad());

	      if (listaBienes.size() != 0) {
	        if ((rqnp.getCod_proveedor() != null) && 
	          (!rqnp.getCod_proveedor().equals(""))) {
	          params.put("cod_cont", rqnp.getCod_proveedor());
	          params.put("user", user);
	          MaestroContratistasBean contratista = this.registroPersonalService.recuperarContratista(params);
	          if (contratista != null) {
	            mapRqnp.put("cod_cont", contratista.getCod_cont());
	            mapRqnp.put("num_ruc", contratista.getNum_ruc());
	            mapRqnp.put("raz_social", contratista.getRaz_social());
	          }

	        }

	        lsFinalidad = (List)this.requerimientoNoProgramadoService.listaFinalidad();
	        lsNecesidad = (List)this.requerimientoNoProgramadoService.listaNecesidad();
	        lsTipoNecesidad = (List)this.requerimientoNoProgramadoService.listaTipoNecesidad();

	        return new ModelAndView("formAucRqnpDetalle").addObject("mapRqnp", mapRqnp).addObject("visor", visor)
	          .addObject("lsNecesidad", lsNecesidad).addObject("listaBienes", listaBienes)
	          .addObject("lsTipoNecesidad", lsTipoNecesidad).addObject("lsFinalidad", lsFinalidad).addObject("anio", ls_anio);
	      }
	      return new ModelAndView("formAucFormato").addObject("mapRqnp", mapRqnp).addObject("visor", visor);
	    }
	    catch (ServiceException ex)
	    {
	      log.error("Error en AucBandejaController.atenderMostrarFormato: " + ex.getMessage());
	      exError = new Exception("error.contratacionRqnp.generico");
	      return new ModelAndView("pagErrorSistema");
	    }
	    catch (Exception ex)
	    {
	      log.error("Error en AucBandejaController.atenderMostrarFormato: " + ex.getMessage(), ex);
	      return new ModelAndView("pagErrorSistema");
	    } finally {
	      if (log.isDebugEnabled()) log.debug("Fin - AucBandejaController.atenderMostrarFormato");
	    }
	  }
	  
	  
	/**
     * Descargar Archivos Adjuntos de los item del Requerimiento 
     * Requerimiento No Programado.
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView descargarArchivoAnexo(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()) {log.debug("debug Inicio - RqnpArchivoController.descargarArchivoAnexo");}
		Map<String, Object> params = new HashMap<String, Object>();
	
		try {			
			String cod_req 	= StringUtils.trim(request.getParameter("txtCodigoRqnp"));
			
			params.put("cod_req", cod_req);
			RegistroArchivosFisicoBean archivo = requerimientoAucService.recuperarArchivoAnexo(params);
			
			log.debug("Nombre Archivo:" + archivo.getFile_name());
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition","attachment;filename="+archivo.getFile_name());	        
			
			OutputStream os = response.getOutputStream();
		
			os.write(archivo.getData());

			os.flush();
			os.close();				
			return null;
		} 
		catch(ServiceException ex){
			log.error("Error en RqnpArchivoController.descargarArchivoAnexo: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en RqnpArchivoController.descargarArchivoAnexo: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - RqnpArchivoController.descargarArchivoAnexo");}
		}
	}
	public ReloadableResourceBundleMessageSource getMessageSource() {
		return messageSource;
	}
	public void setMessageSource(ReloadableResourceBundleMessageSource messageSource) {
		this.messageSource = messageSource;
	}
	public RequerimientoAucService getRequerimientoAucService() {
		return requerimientoAucService;
	}
	public void setRequerimientoAucService(
			RequerimientoAucService requerimientoAucService) {
		this.requerimientoAucService = requerimientoAucService;
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
	
///////////////////////////////////////////////////////////////////
	/**
     * Recupera los RQNP por parte de la AUC
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView recuperarRqnp(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()) {log.debug("Inicio - AucBandejaController.recuperarRqnp");}
		Map<String, Object> params 		= new HashMap<String, Object>();
		boolean okRunning = false;
		Map<String, Object>  mapRqnp 	= new HashMap<String, Object> ();
		Map<String, Object> parm = new HashMap<String, Object>();
		List<Map<String, Object>> listaBienes = new ArrayList<Map<String, Object>>();
		String cod_req	="";
		String user	="";
		String visor	="";
		 
		try {
			UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
			user = usuarioBean.getLogin();
					
			MaestroPersonalBean  maestroPersonal=  (MaestroPersonalBean) WebUtils.getSessionAttribute(request, "maestroPersonalBean");
			MaestroPersonalBean  maestroPersonalTem=null;
						
			cod_req = StringUtils.trim(request.getParameter("txtCodigoRqnp"));
				
			params.put("user", user);
			
			RequerimientoNoProgramadoBean rqnp= requerimientoAucService.recuperarRqnp(cod_req);
			listaBienes = (List<Map<String, Object>>) rqnp.getListaBienes();
			
			okRunning = true;
			
			mapRqnp.put("codigoRqnp", rqnp.getCodigoRqnp());
			mapRqnp.put("codigoReqNoProgramado", rqnp.getCodigoReqNoProgramado());
			mapRqnp.put("codigoResposanble", rqnp.getNumeroRegistro());
			mapRqnp.put("resposanble", rqnp.getNombreSolicitante());
			mapRqnp.put("codigoDependencia", rqnp.getCodigoDependencia());
			mapRqnp.put("codigoUuoo", rqnp.getUuoo());
			mapRqnp.put("dependencia", rqnp.getNombreUOSolicitante());
			mapRqnp.put("fechaRqnp", new SimpleDateFormat("dd/MM/yyyy HH:mm").format(rqnp.getFechaRqnp()));
			mapRqnp.put("motivoRqnp",rqnp.getMotivoSolicitud());
			mapRqnp.put("montoRqnp",rqnp.getMontoRqnp());			
			MaestroPersonalBean personal =registroPersonalService.obtenerPersonaxCodigo( rqnp.getCod_contacto().trim());
			mapRqnp.put("cod_contacto", rqnp.getCod_contacto());
			mapRqnp.put("anexo_contacto", rqnp.getAnexo_contacto());
			mapRqnp.put("cod_necesidad", rqnp.getCod_necesidad());
			mapRqnp.put("cod_tip_nececidad", rqnp.getCod_tip_nececidad());
			mapRqnp.put("nom_tip_necesidad", rqnp.getNom_tip_necesidad());
			mapRqnp.put("nom_contacto", personal.getNombre_completo());
			mapRqnp.put("reg_contacto", personal.getNumero_registro());
			
			mapRqnp.put("cod_finalidad", rqnp.getCod_finalidad());
			
			if (rqnp.getCod_proveedor() !=null) {
				if (!rqnp.getCod_proveedor().equals("")){
					parm.put("cod_cont", rqnp.getCod_proveedor());
					parm.put("user", user);	
					
					MaestroContratistasBean contratista=registroPersonalService.recuperarContratista(parm);
					if (contratista!=null){
						mapRqnp.put("cod_cont", contratista.getCod_cont());
						mapRqnp.put("num_ruc", contratista.getNum_ruc());
						mapRqnp.put("raz_social", contratista.getRaz_social());
					}
				}
			}		 
			mapRqnp.put("obs_justificacion", rqnp.getObs_justificacion());
			mapRqnp.put("obs_plazo_entrega", rqnp.getObs_plazo_entrega());
			mapRqnp.put("obs_lugar_entrega", rqnp.getObs_lugar_entrega());
			mapRqnp.put("obs_dir_entrega", rqnp.getObs_dir_entrega());
			 
		} 
		catch (ServiceException ex)
	    {
	      log.error("Error en AucBandejaController.recuperarRqnp: " + ex.getMessage());
	      request.setAttribute("excepAttr", ex);
	      okRunning = false;
	    }
	    catch (Exception ex) {
	      log.error("Error en AucBandejaController.recuperarRqnp: " + ex.getMessage(), ex);
	    }
	    finally {
	      if (log.isDebugEnabled()) log.debug("Fin - AucBandejaController.recuperarRqnp");
	    }

	    if (!okRunning) {
	      return new ModelAndView("pagErrorSistema");
	    }
	    return new ModelAndView("formAucRqnpValidar").addObject("mapRqnp", mapRqnp).addObject("visor", visor)
	      .addObject("listaBienes", listaBienes);
	  }
	
///////////////////////////////////////////////////////////////////
	/**
     * Permite Rechazar el RQNP por el Jefe Inmediato o Itendente.
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView rechazarValidarRqnp(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("Inicio - AucBandejaController.rechazarRqnp");}
	
		List<Map<String, Object>> lsNecesidad= new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> lsTipoNecesidad= new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> lsFinalidad	= new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listaBienes = new ArrayList<Map<String, Object>>();
		Map<String, Object>  mapRqnp 	= new HashMap<String, Object> ();
		Map<String, Object> params = new HashMap<String, Object>();
		List<String> lista = new ArrayList<String>();
		String visor	="";
	
		boolean okRunning = false;
		String valor	="";
		String cod_req	="";
		String cod_bie	="";
	
		String ind_int	="";
		String cod_bie_env	="";
		String ls_anio		="";
		String ind_especializado="";
		String user			="";
		String is_bandeja	="AUCT";
	
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
				visor	= StringUtils.trim(request.getParameter("txtvisor"));
				
				params.put("cod_accion", "004");
				params.put("exp_obs", "REQUERIMIENTO RECHAZADO POR AUC");
			
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
					
					requerimientoAucService.registrarRechazarRqnp(params);
				}	
				
				cod_req="";
				cod_bie="";
				String cod_req_ant="";
				//----enviar correos------------------------------------------------------
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
									System.out.println(valor + " - " + cod_req_ant+ " - " + cod_bie_env +" "+is_bandeja );
									requerimientoNoProgramadoService.envioMailRechazo( cod_req_ant, cod_bie_env,is_bandeja);
								}
								cod_bie_env="";
							} 
						}else if (i==2){
							cod_bie= tokens.nextToken();
						}else{
							ind_especializado= tokens.nextToken();
						}
					}
					if (cod_bie_env.equals("") ){
						cod_bie_env =cod_bie;
					}else {
						cod_bie_env =cod_bie_env+','+cod_bie;
					}
					cod_req_ant=cod_req;
				}
				if (!cod_bie_env.equals("") ){
					requerimientoNoProgramadoService.envioMailRechazo( cod_req, cod_bie_env,is_bandeja);
				}
				//------------------------------------------
				okRunning = true;
	
				params.clear();
				///obtener detalle de Requerimiento
				RequerimientoNoProgramadoBean rqnp= requerimientoAucService.recuperarRqnp(cod_req);
				listaBienes = (List<Map<String, Object>>) rqnp.getListaBienes();
				mapRqnp.put("codigoRqnp", rqnp.getCodigoRqnp());
				mapRqnp.put("codigoReqNoProgramado", rqnp.getCodigoReqNoProgramado());
				mapRqnp.put("codigoResposanble", rqnp.getNumeroRegistro());
				mapRqnp.put("resposanble", rqnp.getNombreSolicitante());
				mapRqnp.put("codigoDependencia", rqnp.getCodigoDependencia());
				mapRqnp.put("codigoUuoo", rqnp.getUuoo());
				mapRqnp.put("dependencia", rqnp.getNombreUOSolicitante());
				mapRqnp.put("fechaRqnp", new SimpleDateFormat("dd/MM/yyyy HH:mm").format(rqnp.getFechaRqnp()));
				mapRqnp.put("motivoRqnp",rqnp.getMotivoSolicitud());
				mapRqnp.put("montoRqnp",rqnp.getMontoRqnp());
				MaestroPersonalBean personal =registroPersonalService.obtenerPersonaxCodigo( rqnp.getCod_contacto().trim());
				mapRqnp.put("cod_contacto", rqnp.getCod_contacto());
				mapRqnp.put("anexo_contacto", rqnp.getAnexo_contacto());
				mapRqnp.put("cod_necesidad", rqnp.getCod_necesidad());
				mapRqnp.put("cod_tip_nececidad", rqnp.getCod_tip_nececidad());
				mapRqnp.put("nom_tip_necesidad", rqnp.getNom_tip_necesidad());
				mapRqnp.put("nom_contacto", personal.getNombre_completo());
				mapRqnp.put("reg_contacto", personal.getNumero_registro());
				
				mapRqnp.put("cod_finalidad", rqnp.getCod_finalidad());
				mapRqnp.put("obs_justificacion", rqnp.getObs_justificacion());
				mapRqnp.put("obs_plazo_entrega", rqnp.getObs_plazo_entrega());
				mapRqnp.put("obs_lugar_entrega", rqnp.getObs_lugar_entrega());
				mapRqnp.put("obs_dir_entrega", rqnp.getObs_dir_entrega());
				
				if (listaBienes.size() !=0 ){ 
					if (rqnp.getCod_proveedor() !=null) {
						if (!rqnp.getCod_proveedor().equals("")){
							params.put("cod_cont", rqnp.getCod_proveedor());
							params.put("user", user);	
						
							MaestroContratistasBean contratista=registroPersonalService.recuperarContratista(params);
							if (contratista!=null){
								mapRqnp.put("cod_cont", contratista.getCod_cont());
								mapRqnp.put("num_ruc", contratista.getNum_ruc());
								mapRqnp.put("raz_social", contratista.getRaz_social());
							}
						}
					}		 
					lsFinalidad= (List<Map<String, Object>>) requerimientoNoProgramadoService.listaFinalidad();
					lsNecesidad = (List<Map<String, Object>>) requerimientoNoProgramadoService.listaNecesidad();
					lsTipoNecesidad = (List<Map<String, Object>>) requerimientoNoProgramadoService.listaTipoNecesidad();	
					
				}else{
					params.put("cod_per", maestroPersonal.getCodigoEmpleado());
					params.put("anio_act", ls_anio);
					params.put("cod_estado", "02");
					WebUtils.setSessionAttribute(request,"maestroPersonalBean",maestroPersonal );
					@SuppressWarnings("unchecked")
					List<Map<String, Object>> lsRqnp = (List<Map<String, Object>>) requerimientoAucService.listarRqnpAuc(params);
					
					return new ModelAndView("formBandejaAUC", "lsrqnp", lsRqnp).addObject("anio",ls_anio);
				}
			}else{
				Exception ex = new Exception("Error al Obtener Datos del Usuario");
				request.setAttribute("excepAttr", ex);
				return new ModelAndView("pagErrorSistema");
			}
		} 
		catch (ServiceException ex) {
			log.error("Error en AucBandejaController.rechazarRqnp: " + ex.getMessage());
		} 
		catch (Exception ex) {
			log.error("Error en AucBandejaController.rechazarRqnp: " + ex.getMessage(), ex);
		} 
		finally {
			if (log.isDebugEnabled()){ log.debug("Fin - AucBandejaController.rechazarRqnp");}
		}
		if(!okRunning){
			Exception ex = new Exception("error.registrarAtencion.generico");
			request.setAttribute("excepAttr", ex);
			return new ModelAndView("pagErrorSistema");
		}
		return new ModelAndView("formAucRqnpValidar").addObject("mapRqnp", mapRqnp).addObject("visor", visor).
		addObject("lsNecesidad", lsNecesidad).addObject("listaBienes",listaBienes).
		addObject("lsTipoNecesidad", lsTipoNecesidad).addObject("lsFinalidad", lsFinalidad).addObject("anio",ls_anio);
	}
	

	  /**
	   * Funcion de validación para la aprobación del requerimiento por parte de la AUC 
	   * @param params
	   * @return
	   */
	  
	  private Map<String, String> validarAprobarAUC(Map<String, Object> params)
	  {
	    if (log.isDebugEnabled()) log.debug("Inicio - AucBandejaController.validarAprobarAUC");
	    String mensaje = "";
	    String flagValidacion = "0";
	    boolean validaExisteMeta = false;
	    Map respuesta = new HashMap();
	    String codRqnp = (String)params.get("cod_rqnp");
	    try {
	    	RequerimientoNoProgramadoBean rqnp = new RequerimientoNoProgramadoBean();
		      DependenciaBean dependenciaOEC = new DependenciaBean();
		      DependenciaBean dependenciaAUC = new DependenciaBean();
		      DependenciaBean dependenciaRqnp = new DependenciaBean();
		      DependenciaBean dependenciaJefatura = new DependenciaBean();

		      String codParametro="4023";
		      String descParametro="UPP";
		      
		      DependenciaBean dependenciaDpg = requerimientoNoProgramadoService.obtenerDpg(codParametro, descParametro);
		      dependenciaDpg = requerimientoNoProgramadoService.obtenerDpg(codParametro, descParametro);
		      String uuooDpg = dependenciaDpg.getUuoo()+" - "+dependenciaDpg.getNom_corto();
		      
		      rqnp = this.requerimientoNoProgramadoService.recuperarRqnpCab(codRqnp);

		      String codDepeJefatura = this.registroPersonalService.obtenerUuooNoSupervision(rqnp.getCodigoDependencia(), "4");
		      params.put("codDepeRqnp", rqnp.getCodigoDependencia());
		      params.put("codRqnp", codRqnp);

		      if (codDepeJefatura != null) {
		        params.put("codDependencia", codDepeJefatura);
		        dependenciaJefatura = this.requerimientoNoProgramadoService.obtenerUuoo(params);
		      } else {
		        dependenciaJefatura = null;
		      }

		      dependenciaRqnp = this.requerimientoNoProgramadoService.obtenerUuooRQNP(params);
		      //dependenciaOEC = this.requerimientoNoProgramadoService.validarOEC(params);
		      dependenciaAUC = this.requerimientoNoProgramadoService.obtenerUuooAUC(params);
		      validaExisteMeta = this.requerimientoNoProgramadoService.validaMetaVacia(params);

		      BigDecimal indMonto = rqnp.getMontoRqnp();

		      if (dependenciaRqnp == null) {
		        mensaje = "La dependencia que generó el Requerimiento se encuentra Desactivada.\nPara más detalles favor de comunicarse con Recursos Humanos.";
		        flagValidacion = "1";
		      } else {
		    	  log.debug("dependenciaRqnp: "+dependenciaRqnp.getCod_dep() + " - UUOO: "+dependenciaRqnp.getUuoo());
		    	  String codOecDefecto = dependenciaRqnp.getCodOecDefecto();
		        
		        if (codOecDefecto == null) {
		          mensaje = "El Área Encargada de la Contratación que atenderá este Requerimiento no se encuentra configurada. \nFavor de comunicarse con la Unidad "+uuooDpg+" para mas detalles.";
		          flagValidacion = "1";
		        }else{
		        	params.put("codOecDefecto",codOecDefecto);
		        	dependenciaOEC = this.requerimientoNoProgramadoService.obtenerUuooOEC(params);
		        }
		      }
		      
		      /*
		      if (dependenciaJefatura == null) {
		        mensaje = "La dependencia de la Jefatura que atenderá este Requerimiento se encuentra Desactivada.";
		        flagValidacion = "1";
		      }else if (dependenciaJefatura.getCodEmpleado() == null) {
		    	  log.debug("dependenciaJefatura: "+dependenciaJefatura.getCod_dep() + " - UUOO: "+dependenciaJefatura.getUuoo());
		    	  mensaje = "La Jefatura Inmediata que atenderá este Requerimiento no se encuentra asignada.";
		        flagValidacion = "1";
		      }else if(dependenciaJefatura.getCodEmpleado() != null){
		    	  MaestroPersonalBean jefe = registroPersonalService.obtenerPersonaxCodigo(dependenciaJefatura.getCodEmpleado());
		    	  if(jefe.getEstaTrabPer()!=null){
		    		  if(!jefe.getEstaTrabPer().equals("1")){
			    		  	mensaje = "El Jefe que atenderá este Requerimiento se encuentra en estado inactivo, verifique.";
			    		  	flagValidacion = "1";
			    	  } 
		    	  }else{
		    		  mensaje = "El Jefe que atenderá este Requerimiento se encuentra en estado inactivo, verifique.";
		    		  flagValidacion = "1";
		    	  }
		      }
			*/	
				
		      if (flagValidacion.equals("0")) {
		        if (dependenciaOEC == null) {
		          mensaje = "El Área Encargada de la Contratación que atenderá este Requerimiento se encuentra Desactivada.\nFavor de comunicarse con la Unidad "+uuooDpg+" para mas detalles.";
		          flagValidacion = "1";
		        } else {
		        	log.debug("dependenciaOEC: "+dependenciaOEC.getCod_dep() + " - UUOO: "+dependenciaOEC.getUuoo());
		        	String codEmpleadoJefeOec = dependenciaOEC.getCodEmpleado();
		          if (codEmpleadoJefeOec == null) {
		            mensaje = "El Jefe del Área Encargada de la Contratación que atenderá este Requerimiento NO se encuentra asignado.\nFavor de comunicarse con Información de Personal en Recursos Humanos.";
		            flagValidacion = "1";
		          }
		        }
		      }

		      if (flagValidacion.equals("0")) {
		        if (dependenciaAUC == null) {
		          mensaje = "El Área Técnica (AT) que atenderá este Requerimiento se encuentra Desactivada ó no se asignó al requerimiento.\nFavor de comunicarse con la Unidad "+uuooDpg+" para mas detalles.";
		          flagValidacion = "1";
		        } else {
		        	log.debug("dependenciaAUC: "+dependenciaAUC.getCod_dep() + " - UUOO: "+dependenciaAUC.getUuoo());
		        	String codAucDeRqnp = dependenciaAUC.getCod_dep();
		        	String codEmpleadoJefeAuc = dependenciaAUC.getCodEmpleado();
		        	
		          if (codAucDeRqnp == null) {
		            mensaje = "El Área Técnica (AT) que atenderá este Requerimiento se encuentra Desactivada ó no se asignó al requerimiento.\nFavor de comunicarse con la Unidad "+uuooDpg+" para mas detalles.";
		            flagValidacion = "1";
		          } else if (codEmpleadoJefeAuc == null) {
		            mensaje = "El Jefe del Área Técnica (AT) que atenderá este Requerimiento no se encuentra asignado.\nFavor de comunicarse con Información de Personal en Recursos Humanos.";
		            flagValidacion = "1";
		          }
		          
		          DependenciaBean dependenciaAprobadora = new DependenciaBean();
		          String codUuooAprobadoraAuc = dependenciaAUC.getCodUuooAprueba();
		          if (codUuooAprobadoraAuc == null) {
			          mensaje = "La UUOO Aprobadora que atenderá este Requerimiento no se encuentra configurada.\nFavor de comunicarse con la Unidad "+uuooDpg+" para mas detalles.";
			          flagValidacion = "1";
			        }else{
			        	params.put("codUuooAprueba",codUuooAprobadoraAuc);
			        	dependenciaAprobadora = this.requerimientoNoProgramadoService.obtenerUuooAprobadoraAuc(params);
			        	
			        	if(dependenciaAprobadora!=null){
			        		log.debug("dependenciaAprobadora: "+dependenciaAprobadora.getCod_dep() + " - UUOO: "+dependenciaAprobadora.getUuoo());
			        		if(dependenciaAprobadora.getCodEmpleado()==null){
				        		mensaje = "El Jefe de la UUOO Aprobadora que atenderá este Requerimiento no se encuentra asignado.\nFavor de comunicarse con Información de Personal en Recursos Humanos.";
						        flagValidacion = "1";
				        	}
			        	}else{
			        		mensaje = "La UUOO Aprobadora que atenderá este Requerimiento se encuentra desactivada.\nFavor de comunicarse con Información de Personal en Recursos Humanos.";
					        flagValidacion = "1";
			        	}
			        	
			        }
		        }
		      }

		      if (validaExisteMeta) {
		        mensaje = "Existen items que no ha indicado la cantidad de bienes a adquirir ó el monto en S/. aproximado que costará el servicio.";
		        flagValidacion = "1";
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
	      log.error("Error en AucBandejaController.validarAprobarAUC: " + ex.getMessage());
	      throw new ServiceException(this, ex);
	    }
	    catch (Exception ex) {
	      log.error("Error en AucBandejaController.validarAprobarAUC: " + ex.getMessage(), ex);
	      throw new ServiceException(this, ex);
	    }
	    finally {
	      if (log.isDebugEnabled()) log.debug("Fin - AucBandejaController.validarAprobarAUC");
	    }
	    return respuesta;
	  }
///////////////////////////////////////////////////////////////////	

}
