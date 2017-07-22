package pe.gob.sunat.administracion2.siga.catalogo.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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

import pe.gob.sunat.administracion2.siga.rqnp.model.domain.SolicitudBienBean;
import pe.gob.sunat.administracion2.siga.rqnp.service.RequerimientoAucService;
import pe.gob.sunat.administracion2.siga.rqnp.service.RequerimientoNoProgramadoService;
import pe.gob.sunat.administracion2.siga.rqnp.service.SolicitudBienService;
import pe.gob.sunat.framework.core.json.JsonUtil;
import pe.gob.sunat.framework.spring.util.exception.ServiceException;
import pe.gob.sunat.framework.spring.web.view.JsonView;
import pe.gob.sunat.framework.util.date.FechaBean;
import pe.gob.sunat.recurso2.administracion.siga.parametro.model.bean.T01ParametroBean;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.MaestroPersonalBean;
import pe.gob.sunat.recurso2.administracion.siga.registro.service.RegistroPersonalService;
import pe.gob.sunat.recurso2.administracion.siga.web.util.BaseController;
import pe.gob.sunat.tecnologia.menu.bean.UsuarioBean;
public class SolicitudIncorporarBienController extends BaseController{

private static final Log log = LogFactory.getLog(SolicitudIncorporarBienController.class);
	private SolicitudBienService				solicitudBienService;
	private RegistroPersonalService 			registroPersonalService;
	private RequerimientoNoProgramadoService	requerimientoNoProgramadoService;
	private RequerimientoAucService 			requerimientoAucService;
	private ReloadableResourceBundleMessageSource 		messageSource;
	//private JsonView 							jsonView;
	//private View 								htmlView;

	/**
     * Carga la Página inicial para solicitar incoporacion
     * de nuevo bien o servicio.
     * @param  request
     * @param  response
     * @return ModelAndView 
     **/
	public ModelAndView iniciarSolicitud(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()) log.debug("debug Inicio - SolicitudIncorporarBienController.iniciarSolicitud");
		Map<String, Object> params = new HashMap<String, Object>();
		String cod_req="";
		String visor ="";
		String ls_registro ="";
		String path_url ="";
		FechaBean fecha = new FechaBean();
		try {
			UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
			ls_registro= usuarioBean.getNroRegistro();
			log.debug("usuario:" + ls_registro);
			
			MaestroPersonalBean  maestroPersonal= registroPersonalService.obtenerPersonaxRegistro(ls_registro.trim());
			if(maestroPersonal != null){
				WebUtils.setSessionAttribute(request,"maestroPersonalBean",maestroPersonal );
			}
			
			log.debug("Numero Registro:" + maestroPersonal.getNumero_registro());
			log.debug("codi_empl_per:" + maestroPersonal.getCodigoEmpleado());
			//MaestroPersonalBean maestroPersonal = (MaestroPersonalBean) WebUtils.getSessionAttribute(request, "maestroPersonalBean");
			
			cod_req = StringUtils.trim(request.getParameter("txtCodigoRqnp"));
			visor	= StringUtils.trim(request.getParameter("txtvisor"));
			path_url	= StringUtils.trim(request.getParameter("path_url"));
			if (log.isDebugEnabled()){log.debug(maestroPersonal.getCodigoEmpleado());}
			if (log.isDebugEnabled()){log.debug("path_url " + path_url);}
			params.put("cod_estado", "%");
			params.put("cod_per", maestroPersonal.getCodigoEmpleado());
			List<Map<String, Object>> lsSol = (List<Map<String, Object>>) solicitudBienService.listarSolicitudXPersona(params);
			List<Map<String, Object>> lsUnidad = (List<Map<String, Object>>) solicitudBienService.listarUnidadMedidad();
			
			return new ModelAndView("formRegistroSolicitud", "lsSol", lsSol).addObject("lsUnidad", lsUnidad)
			.addObject("txtCodigoRqnp", cod_req).addObject("visor", visor)
			.addObject("txt_nombreCompleto",maestroPersonal.getNombre_completo())
			.addObject("txt_fecha",fecha.getFormatDate("dd/MM/yyyy"))
			.addObject("txt_uuoo", maestroPersonal.getUuoo()+" " + maestroPersonal.getDependencia() ) 
			.addObject("path_url", path_url);
		} 
		catch(ServiceException ex){
			log.error("Error en SolicitudIncorporarBienController.iniciarSolicitud: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en SolicitudIncorporarBienController.iniciarSolicitud: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - SolicitudIncorporarBienController.iniciarSolicitud");}
		}
	}
	
	
	/**
     * Carga la Página inicial para solicitar incoporacion
     * de nuevo bien o servicio.
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView iniciarSolicitudAUC(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()) log.debug("debug Inicio - SolicitudIncorporarBienController.iniciarSolicitudAUC");
		Map<String, Object> params = new HashMap<String, Object>();
		String cod_req="";
		String codEmpleado="";
		String cod_dep="";
		String visor ="";
		String path_url ="";
		String ls_registro ="";
		String path_url_back ="";
		String is_grupo_auc="N";
		String depe_no_superv="";
		FechaBean fecha = new FechaBean();
		try {
			
			UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
			ls_registro= usuarioBean.getNroRegistro();
		
			MaestroPersonalBean  maestroPersonal= registroPersonalService.obtenerPersonaxRegistro(ls_registro.trim());
			if(maestroPersonal != null){
				WebUtils.setSessionAttribute(request,"maestroPersonalBean",maestroPersonal );
				codEmpleado = maestroPersonal.getCodigoEmpleado();
				cod_dep= maestroPersonal.getCodigoDependencia();
			}
			
			cod_req = StringUtils.trim(request.getParameter("txtCodigoRqnp"));
			visor	= StringUtils.trim(request.getParameter("txtvisor"));
			path_url	= StringUtils.trim(request.getParameter("path_url"));
			path_url_back	= StringUtils.trim(request.getParameter("path_url_back"));//DPORRASC - CATALOGO
			
			/////////////////////////////////
			depe_no_superv=registroPersonalService.obtenerUuooNoSupervision(maestroPersonal.getCodigoDependencia(),"4");
			//String jefeInmediato =registroPersonalService.obtenerJefeInmediato(codEmpleado); //DPORRASC 2017
			String cadenaUuoosJefe=registroPersonalService.obtenerCadenaUuoosQueEsJefe(codEmpleado);
			log.debug("depe_no_superv: "+depe_no_superv);
			//log.debug("jefeInmediato: "+jefeInmediato); //DPORRASC 2017
			log.debug("cadenaUuoosJefe: "+cadenaUuoosJefe); //DPORRASC 2017
			
			if(depe_no_superv==null){
				Exception ex  = new Exception("Existe un problema con su dependencia ó la de su Jefatura, Por favor verifique.");
				request.setAttribute("excepAttr", ex);
				return new ModelAndView("pagErrorSistema");
			}
			
			params.put("nro_reg", ls_registro.trim());
			params.put("cod_rol", "2001");
			params.put("cod_estado", "1");
			params.put("cod_dep", depe_no_superv+"-"+cadenaUuoosJefe);
			
			log.debug("cod_dep: "+depe_no_superv+"-"+cadenaUuoosJefe); //DPORRASC 2017
			
			String is_user_auc=requerimientoAucService.validaUserAUC(params);
			is_grupo_auc=requerimientoAucService.validaGrupoAUC(params);
			Boolean esEncargadoAuc = Boolean.valueOf(registroPersonalService.esEncargadoAuc(codEmpleado));
			Boolean esEncargadoOtraUuoo = Boolean.valueOf(registroPersonalService.esEncargadoOtraUuoo(codEmpleado));
			
			log.debug("es esEncargadoAuc: "+esEncargadoAuc);
			log.debug("esEncargadoOtraUuoo: " + esEncargadoOtraUuoo);
			
			List<Map<String, Object>> lsSol = (List<Map<String, Object>>) solicitudBienService.listarSolicitudXPersona(params);
			
			if (log.isDebugEnabled()){log.debug(maestroPersonal.getCodigoEmpleado());}
			if (log.isDebugEnabled()){log.debug("path_url " + path_url);}
			if (log.isDebugEnabled()){log.debug("path_url_back " + path_url_back);} //DPORRASC - CATALOGO

			if(is_user_auc.equals("S") && esEncargadoAuc.booleanValue()
					||(registroPersonalService.esJefe(codEmpleado,depe_no_superv)&&is_grupo_auc.equals("S"))
					||((esEncargadoOtraUuoo)&&!lsSol.isEmpty())
					||esEncargadoAuc.booleanValue()){
				List<Map<String, Object>> lsUnidad = (List<Map<String, Object>>) solicitudBienService.listarUnidadMedidad();
				return new ModelAndView("formRegistroSolicitudAUC", "lsSol", lsSol).addObject("lsUnidad", lsUnidad)
				.addObject("txtCodigoRqnp", cod_req).addObject("visor", visor)
				.addObject("txt_nombreCompleto",maestroPersonal.getNombre_completo())
				.addObject("txt_fecha",fecha.getFormatDate("dd/MM/yyyy"))
				.addObject("txt_uuoo", maestroPersonal.getDependencia() ) 
				.addObject("path_url", path_url).addObject("cod_estado", "1")
				.addObject("path_url_back", path_url_back);
			}else{
				Exception ex = new Exception("Ud. no tiene permiso para realizar esta operación.");
				request.setAttribute("excepAttr", ex);
				return new ModelAndView("pagErrorSistema");
			}	
			
		}catch(ServiceException ex){
			log.error("Error en SolicitudIncorporarBienController.iniciarSolicitudAUC: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en SolicitudIncorporarBienController.iniciarSolicitudAUC: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - SolicitudIncorporarBienController.iniciarSolicitudAUC");}
		}
	}	

	
	/**
     * Registra la solicitud incorpación
     * de bien o servicio al catálogo. 
     * meta afectada .
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView registrarSolictudJson(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - SolicitudIncorporarBienController.RegistrarSolictudJson");}
		Map<String, Object> mapCatalogo = new HashMap<String, Object>();
		Map<String, Object> data 		= new HashMap<String, Object>();
		Map<String, Object> parm = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		String reg_contacto="";
		String cod_sol	="";
		String user		="";
		String ls_activar_flujo_auc="";
		
		//boolean okRunning = false;
		String ls_cod_categoria ="";
		try {
			UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
			user = usuarioBean.getLogin();

			log.debug("usuario:" + user);
			
			MaestroPersonalBean maestroPersonal = (MaestroPersonalBean) WebUtils.getSessionAttribute(request, "maestroPersonalBean");
			//MaestroPersonalBean  maestroPersonal= registroPersonalService.obtenerPersonaxUsuario(user);
			
			log.debug("Numero Registro:" + maestroPersonal.getNumero_registro());
			log.debug("codi_empl_per:" + maestroPersonal.getCodigoEmpleado());
			
			mapCatalogo.put("j_crud",  StringUtils.trim(request.getParameter("jcrud")));
			mapCatalogo.put("txtCodSol",  StringUtils.trim(request.getParameter("txtCodSol")));
			mapCatalogo.put("txtTipBien",  StringUtils.trim(request.getParameter("txtTipBien")));
			mapCatalogo.put("txtTipoSol",  StringUtils.trim(request.getParameter("txtTipoSol")));
			mapCatalogo.put("txtNomBien",  StringUtils.trim(request.getParameter("txtNomBien")));
			mapCatalogo.put("txtPresentacion",  StringUtils.trim(request.getParameter("txtPresentacion")));
			mapCatalogo.put("txtCaract_tecnica",  StringUtils.trim(request.getParameter("txtCaract_tecnica")));
			mapCatalogo.put("txtCaract_fisica",  StringUtils.trim(request.getParameter("txtCaract_fisica")));
			mapCatalogo.put("txtUnidad_sol",  StringUtils.trim(request.getParameter("txtUnidad_sol")));
			mapCatalogo.put("txtPrecio_sol",  StringUtils.trim(request.getParameter("txtPrecio_sol").replace(",", "") ));
			mapCatalogo.put("txtTiempo_expira",  StringUtils.trim(request.getParameter("txtTiempo_expira")));
			mapCatalogo.put("txtOtras_caract",  StringUtils.trim(request.getParameter("txtOtras_caract")));
			mapCatalogo.put("txtSuger_proveedor",  StringUtils.trim(request.getParameter("txtSuger_proveedor")));
			mapCatalogo.put("txtEnvase",  StringUtils.trim(request.getParameter("txtEnvase")));
			mapCatalogo.put("txtSubCategoria",  StringUtils.trim(request.getParameter("txtSubCategoria")));
			mapCatalogo.put("txtSolcitante", maestroPersonal.getCodigoEmpleado());
			mapCatalogo.put("txtSede", maestroPersonal.getCodigoSede());
			mapCatalogo.put("user", user);
			ls_cod_categoria = StringUtils.trim(request.getParameter("txtSubCategoria"));
			
			///////////////////////////////////////////////////////////////////////
			reg_contacto= StringUtils.trim(request.getParameter("txtReg_contacto"));
			log.debug("contacto: "+reg_contacto);
			
			if(reg_contacto!=null){
				MaestroPersonalBean personal =registroPersonalService.obtenerPersonaxRegistro(reg_contacto.trim());
				mapCatalogo.put("txtCod_contacto", personal.getCodigoEmpleado());
				mapCatalogo.put("txtNom_contacto", personal.getNombre_completo());
			}else{
				reg_contacto="";
			}
			mapCatalogo.put("txtNum_anexo_contacto", StringUtils.trim(request.getParameter("txtAnexo_contacto")));
			mapCatalogo.put("txtObs_motivo", StringUtils.trim(request.getParameter("txtObs_motivo")));
			///////////////////////////////////////////////////////////////////////
			
			parm.put("cod_par", "5010");
			parm.put("cod_tipo","D");
			parm.put("cod_arg2","%" + ls_cod_categoria );
			T01ParametroBean bean= solicitudBienService.obtenerParametro(parm);
			
			if ( bean == null){
				log.debug( "au cod_auc" +registroPersonalService.obtenerUuooNoSupervision(maestroPersonal.getCodigoDependencia(),"4") );
				mapCatalogo.put("cod_auc",registroPersonalService.obtenerUuooNoSupervision(maestroPersonal.getCodigoDependencia(),"4"));
				
			}else{
				log.debug("cod_auc" +  registroPersonalService.obtenerUuooNoSupervision(bean.getCod_argumento().trim().substring(0, 4),"4"));
				mapCatalogo.put("cod_auc", registroPersonalService.obtenerUuooNoSupervision(bean.getCod_argumento().trim().substring(0, 4),"4"));
			}
			
			cod_sol= solicitudBienService.registrarSolictudBien(mapCatalogo);
			ls_activar_flujo_auc=solicitudBienService.activarFlujoAuc("ind_ate_sol_cat");
			
			//AGREGADO PARA MENSAJERIA DE CATALOGO
			////////////////////////////////////////////////////////////////////////////////////////////
			if (ls_activar_flujo_auc.equals("1")){
				if(mapCatalogo.get("j_crud").equals("enviar")){
					mapCatalogo.put("tipo_msg","ENVIAR_AUC");
					solicitudBienService.EnviarMailRqnp(mapCatalogo);
				}
			}
			///////////////////////////////////////////////////////////////////////////////////////////
			
			params.put("cod_estado", "%");
			params.put("cod_per", maestroPersonal.getCodigoEmpleado());
			
			Map<String, Object> map_sol = (Map<String, Object>) solicitudBienService.recuperarSolictudBien(cod_sol);
			List<Map<String, Object>> lsSol = (List<Map<String, Object>>) solicitudBienService.listarSolicitudXPersona(params);
			
			data.put("map_sol", JsonUtil.toString( map_sol));
			data.put("lsSol", JsonUtil.toString(lsSol));
			
		} 
		catch(ServiceException ex){
			log.error("Error en SolicitudIncorporarBienController.RegistrarSolictudJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en SolicitudIncorporarBienController.RegistrarSolictudJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - SolicitudIncorporarBienController.RegistrarSolictudJson");}
		}
		return new ModelAndView(jsonView, "data", JsonUtil.toString(data)) ;
	}	
	
	
	/**
     * Recuperar Lista de Categoria en Json.
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView listarCategoriaJson(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - SolicitudIncorporarBienController.listarCategoriaJson");}
		Map<String, Object> parm = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		List<Map<String, Object>> lsTipoVinculo= new ArrayList<Map<String, Object>>();
		try {
			parm.put("cod_par", "4015");
			parm.put("cod_tipo","D");
			lsTipoVinculo= (List<Map<String, Object>>) solicitudBienService.obtenerParametroLista(parm);
			 
			data.put("identifier", "cod");
			data.put("label", "name");
			data.put("items", lsTipoVinculo);
	
		} 
		catch(ServiceException ex){
			log.error("Error en SolicitudIncorporarBienController.listarCategoriaJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en SolicitudIncorporarBienController.listarCategoriaJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - SolicitudIncorporarBienController.listarCategoriaJson");}
		}
		return new ModelAndView(jsonView, data) ;
	}
	
	
	/**
     * Recuperar Lista de Sub Categoria en Json.
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView listarSubCategoriaJson(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - SolicitudIncorporarBienController.listarSubCategoriaJson");}
		Map<String, Object> parm = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		List<Map<String, Object>> lsTipoVinculo= new ArrayList<Map<String, Object>>();
		String user="";
		String num_uuoo="" ;
		try {
			String ls_cod_categoria =StringUtils.trim( request.getParameter("jcodCategoria"));
			log.debug("ls_cod_categoria:" +ls_cod_categoria ); 
			if(! ls_cod_categoria.equals("")  ){
				parm.put("cod_par", "4016");
				parm.put("cod_tipo","D");
				parm.put("cod_arg2",ls_cod_categoria +"%");
				lsTipoVinculo= (List<Map<String, Object>>) solicitudBienService.obtenerParametroLista(parm);
				 
				data.put("identifier", "cod");
				data.put("label", "name");
				data.put("items", lsTipoVinculo);
			}	
		} 
		catch(ServiceException ex){
			log.error("Error en SolicitudIncorporarBienController.listarSubCategoriaJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en SolicitudIncorporarBienController.listarSubCategoriaJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - SolicitudIncorporarBienController.listarSubCategoriaJson");}
		}
		return new ModelAndView(jsonView,  data) ;
	}
	
	
	/**
     * Recuperar Lista de Estados de Solicitud en Json.
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView listarEstadosJson(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - SolicitudIncorporarBienController.listarEstadosJson");}
		Map<String, Object> parm = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		List<Map<String, Object>> lsEstados= new ArrayList<Map<String, Object>>();
	
		try {
			parm.put("nom_tabla", "SOLICITUD_BIEN_CATALOGO");
			
			lsEstados= (List<Map<String, Object>>) solicitudBienService.obtenerEstadosLista(parm);
			 
			data.put("identifier", "cod");
			data.put("label", "name");
			data.put("items", lsEstados);
		} 
		catch(ServiceException ex){
			log.error("Error en SolicitudIncorporarBienController.listarEstadosJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en SolicitudIncorporarBienController.listarEstadosJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - SolicitudIncorporarBienController.listarEstadosJson");}
		}
		return new ModelAndView(jsonView,  data) ;
	}
	
	
	/**
     * Recupera los datos de una solicitud incorpacion
     * de bien o servicio al catal�go. 
     * meta afectada .
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView recuperarSolictudJson(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - SolicitudIncorporarBienController.RecuperarSolictudJson");}
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			String cod_sol = StringUtils.trim(request.getParameter("jcod_sol"));
			Map<String, Object> map_sol = (Map<String, Object>) solicitudBienService.recuperarSolictudBien(cod_sol);
			data.put("map_sol", JsonUtil.toString( map_sol));
		} 
		catch(ServiceException ex){
			log.error("Error en SolicitudIncorporarBienController.RecuperarSolictudJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en SolicitudIncorporarBienController.RecuperarSolictudJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - SolicitudIncorporarBienController.RecuperarSolictudJson");}
		}
		return new ModelAndView(jsonView, "data", JsonUtil.toString(data)) ;
	}


	
	/**
     * Recupera los datos de una solicitud incorpacion
     * de bien o servicio al catálogo. 
     * meta afectada .
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView recuperarDependenciaJson(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - SolicitudIncorporarBienController.recuperarDependenciaJson");}
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> parm = new HashMap<String, Object>();
		String cod_dep="";
		try {
			String ls_cod_categoria = StringUtils.trim(request.getParameter("txtSubCategoria"));
			MaestroPersonalBean maestroPersonal = (MaestroPersonalBean) WebUtils.getSessionAttribute(request, "maestroPersonalBean");
			
			log.debug("Codigo empleado:" + maestroPersonal.getCodigoEmpleado());
			log.debug("Numero registro: " + maestroPersonal.getNumero_registro());
			
			Map<String, Object> map_dep=new HashMap<String, Object>();
			
			if(ls_cod_categoria !=null){
				if(!ls_cod_categoria.equals("")){
				parm.put("cod_par", "5010");
				parm.put("cod_tipo","D");
				parm.put("cod_arg2","%" + ls_cod_categoria );
				T01ParametroBean bean= solicitudBienService.obtenerParametro(parm);
				
				if ( bean == null){
					log.debug( "au cod_auc" +maestroPersonal.getCodigoDependencia() );
					cod_dep =registroPersonalService.obtenerUuooNoSupervision(maestroPersonal.getCodigoDependencia(),"4");
					//cod_dep =maestroPersonal.getCodigoDependencia();
					
				}else{
					log.debug("cod_auc" +  bean.getCod_argumento().trim().substring(0, 4));
					cod_dep = bean.getCod_argumento().trim().substring(0, 4);
				}
				
				//String cod_dep_auc=registroPersonalService.obtenerUuooNoSupervision(cod_dep, "4");
				 map_dep = (Map<String, Object>) solicitudBienService.recuperarDependencia(cod_dep);
				 data.put("auc",(String ) map_dep.get("auc"));
			}
		}
		} 
		catch(ServiceException ex){
			log.error("Error en SolicitudIncorporarBienController.recuperarDependenciaJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en SolicitudIncorporarBienController.recuperarDependenciaJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - SolicitudIncorporarBienController.recuperarDependenciaJson");}
		}
		return new ModelAndView(jsonView, "data", JsonUtil.toString(data)) ;
	}
	
	
	/**
     * Registra la Aprobación solicitud incorpación
     * de bien o servicio al catálogo. 
     * meta afectada .
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView registrarApruebaSolictud(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - SolicitudIncorporarBienController.registrarApruebaSolictudJson");}
		Map<String, Object> mapCatalogo = new HashMap<String, Object>();
		String cod_sol	="";
		String user		="";
		Map<String, Object> params = new HashMap<String, Object>();
		String cod_req="";
		String visor ="";
		String path_url ="";
		FechaBean fecha = new FechaBean();
		String ls_activar_flujo_auc="";
		
		try {
			
			UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
			user = usuarioBean.getLogin();
			
			log.debug("Usuario: " +user );
			
			MaestroPersonalBean  maestroPersonal=  (MaestroPersonalBean) WebUtils.getSessionAttribute(request, "maestroPersonalBean");
			
			cod_sol= StringUtils.trim(request.getParameter("txtCodSol"));
			log.debug("txtCodSol " +cod_sol );
			SolicitudBienBean bean = new SolicitudBienBean();
			user= usuarioBean.getLogin();
		
			bean.setFecha_aprueba(new Date());
			bean.setEstado("4");
			bean.setCod_res_aprueba(maestroPersonal.getCodigoEmpleado());
			bean.setCod_solicitud(cod_sol);
			mapCatalogo.put("user", user);
			mapCatalogo.put("txtCodSol", cod_sol);
			
			solicitudBienService.updateAprueba(bean, user);
			
			ls_activar_flujo_auc=solicitudBienService.activarFlujoAuc("ind_ate_sol_cat");
			
			//DPORRASC-CATALOGO/////////////////////////////////
			if(ls_activar_flujo_auc.equals("1")){
				mapCatalogo.put("tipo_msg","APRUEBA_AUC");
				solicitudBienService.EnviarMailRqnp(mapCatalogo);
			}
			//DPORRRASC-CATALOGO////////////////////////////////
			
			params.put("cod_estado", "1");
			
			String depe_no_superv=registroPersonalService.obtenerUuooNoSupervision(maestroPersonal.getCodigoDependencia(),"4");
			String cadenaUuoosJefe=registroPersonalService.obtenerCadenaUuoosQueEsJefe(maestroPersonal.getCodigoEmpleado());
			
			params.put("cod_dep", depe_no_superv+"-"+cadenaUuoosJefe);
			
			List<Map<String, Object>> lsSol = (List<Map<String, Object>>) solicitudBienService.listarSolicitudXPersona(params);
			List<Map<String, Object>> lsUnidad = (List<Map<String, Object>>) solicitudBienService.listarUnidadMedidad();
			
			return new ModelAndView("formRegistroSolicitudAUC", "lsSol", lsSol).addObject("lsUnidad", lsUnidad)
				.addObject("txtCodigoRqnp", cod_req).addObject("visor", visor)
				.addObject("txt_nombreCompleto",maestroPersonal.getNombre_completo())
				.addObject("txt_fecha",fecha.getFormatDate("dd/MM/yyyy"))
				.addObject("txt_uuoo", maestroPersonal.getDependencia() ) 
				.addObject("path_url", path_url);
		} 
		catch(ServiceException ex){
			log.error("Error en SolicitudIncorporarBienController.registrarApruebaSolictudJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en SolicitudIncorporarBienController.registrarApruebaSolictudJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - SolicitudIncorporarBienController.registrarApruebaSolictudJson");}
		}
	}
	

	/**
     * Registra la Rechazar solicitud incorpación
     * de bien o servicio al catálogo. 
     * meta afectada .
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView registrarRechazarSolictud(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - SolicitudIncorporarBienController.registrarRechazarSolictud");}
		Map<String, Object> mapCatalogo = new HashMap<String, Object>();
		String cod_sol	="";
		String user		="";
		Map<String, Object> params = new HashMap<String, Object>();
		String cod_req="";
		String visor ="";
		String path_url ="";
		String cod_dep ="";
		String ls_comentario="";
		String ls_activar_flujo_auc="";
		FechaBean fecha = new FechaBean();

		try {
			UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
			MaestroPersonalBean maestroPersonal = (MaestroPersonalBean) WebUtils.getSessionAttribute(request, "maestroPersonalBean");
			cod_sol= StringUtils.trim(request.getParameter("txtCodSol"));
			log.debug("txtCodSol " +cod_sol );
			SolicitudBienBean bean = new SolicitudBienBean();
			user= usuarioBean.getLogin();
			ls_comentario = StringUtils.trim(request.getParameter("txtRechazo"));

			//bean.setFecha_rpta( new pe.gob.sunat.framework.spring.util.date.FechaBean());
			bean.setFecha_rpta( new Date());
			bean.setEstado("5");
			bean.setCod_emp_rpta(maestroPersonal.getCodigoEmpleado() );
			bean.setComentario_rpta(ls_comentario);
			bean.setCod_solicitud(cod_sol);
			mapCatalogo.put("user", user);
			mapCatalogo.put("txtCodSol", cod_sol);
			
			solicitudBienService.updateRechaza(bean, user);
			 
			ls_activar_flujo_auc=solicitudBienService.activarFlujoAuc("ind_ate_sol_cat");
			
			///////////////////////////////////////
			if (ls_activar_flujo_auc.equals("1")){
				mapCatalogo.put("tipo_msg","RECHAZA_AUC");
				solicitudBienService.EnviarMailRqnp(mapCatalogo);
			}
			///////////////////////////////////////
			 
			params.put("cod_estado", "1");
			params.put("cod_dep", cod_dep);
			
			String depe_no_superv=registroPersonalService.obtenerUuooNoSupervision(maestroPersonal.getCodigoDependencia(),"4");
			String cadenaUuoosJefe=registroPersonalService.obtenerCadenaUuoosQueEsJefe(maestroPersonal.getCodigoEmpleado());
			
			params.put("cod_dep", depe_no_superv+"-"+cadenaUuoosJefe);
			
				List<Map<String, Object>> lsSol = (List<Map<String, Object>>) solicitudBienService.listarSolicitudXPersona(params);
				List<Map<String, Object>> lsUnidad = (List<Map<String, Object>>) solicitudBienService.listarUnidadMedidad();
				return new ModelAndView("formRegistroSolicitudAUC", "lsSol", lsSol).addObject("lsUnidad", lsUnidad)
				.addObject("txtCodigoRqnp", cod_req).addObject("visor", visor)
				.addObject("txt_nombreCompleto",maestroPersonal.getNombre_completo())
				.addObject("txt_fecha",fecha.getFormatDate("dd/MM/yyyy"))
				.addObject("txt_uuoo", maestroPersonal.getDependencia() ) 
				.addObject("path_url", path_url);
			
		} 
		catch(ServiceException ex){
			log.error("Error en SolicitudIncorporarBienController.registrarRechazarSolictud: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en SolicitudIncorporarBienController.registrarRechazarSolictud: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - SolicitudIncorporarBienController.registrarRechazarSolictud");}
		}
	}
	
	
	/**
     * Recupera las metas afectadas y no afectadas de un  item del RQNP .
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView recuperarListaSolicitudJson(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - SolicitudIncorporarBienController.recuperarListaSolicitudJson");}
		Map<String, Object>  params = new HashMap<String, Object> ();
		Map<String, Object>  data = new HashMap<String, Object> ();
		try {
			String cod_dep = "";
			String cod_estado="";
			 
			MaestroPersonalBean maestroPersonal = (MaestroPersonalBean) WebUtils.getSessionAttribute(request, "maestroPersonalBean");
			
			cod_dep= maestroPersonal.getCodigoDependencia();
			cod_estado = StringUtils.trim(request.getParameter("txtEstado"));
			log.debug("cod_estado " + cod_estado);
			
			String cadenaUuoosJefe=registroPersonalService.obtenerCadenaUuoosQueEsJefe(maestroPersonal.getCodigoEmpleado());
			log.debug("cadenaUuoosJefe: "+cadenaUuoosJefe); //DPORRASC 2017
			
			params.put("cod_estado", cod_estado);
			params.put("cod_dep", cod_dep+"-"+cadenaUuoosJefe);
			List<Map<String, Object>> lsSol = (List<Map<String, Object>>) solicitudBienService.listarSolicitudXPersona(params);
				
			data.put("lsSol", JsonUtil.toString( lsSol));
		} 
		catch(ServiceException ex){
			log.error("Error en SolicitudIncorporarBienController.recuperarListaSolicitudJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en SolicitudIncorporarBienController.recuperarListaSolicitudJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()) {log.debug("Fin - SolicitudIncorporarBienController.recuperarListaSolicitudJson");}
		}
		return new ModelAndView(jsonView, "data", JsonUtil.toString(data)) ;
	}
	public SolicitudBienService getSolicitudBienService() {
		return solicitudBienService;
	}
	public void setSolicitudBienService(SolicitudBienService solicitudBienService) {
		this.solicitudBienService = solicitudBienService;
	}
	public RequerimientoAucService getRequerimientoAucService() {
		return requerimientoAucService;
	}
	public void setRequerimientoAucService(
			RequerimientoAucService requerimientoAucService) {
		this.requerimientoAucService = requerimientoAucService;
	}
	public ReloadableResourceBundleMessageSource getMessageSource() {
		return messageSource;
	}
	public void setMessageSource(ReloadableResourceBundleMessageSource messageSource) {
		this.messageSource = messageSource;
	}
	public RegistroPersonalService getRegistroPersonalService() {
		return registroPersonalService;
	}
	public void setRegistroPersonalService(
			RegistroPersonalService registroPersonalService) {
		this.registroPersonalService = registroPersonalService;
	}
	
//DPORRASC-CATALOGO///////////////////////
	//RECUPERA EL PERSONAL 
	/**
     * Recuperar Contacto de Requerimiento .
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView recuperarContactoRqnpJson(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - SolicitudIncorporarBienController.recuperarContactoRqnpJson");}
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			MaestroPersonalBean personal=new MaestroPersonalBean();
			String cod_reg = StringUtils.trim(request.getParameter("txtReg_contacto"));
			log.debug("Numero Registro:" + cod_reg);
			params.put("cod_reg", cod_reg);
			personal=registroPersonalService.obtenerPersonaxRegistro(cod_reg.toUpperCase());
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
			log.error("Error en SolicitudIncorporarBienController.recuperarContactoRqnpJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en SolicitudIncorporarBienController.recuperarContactoRqnpJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - SolicitudIncorporarBienController.recuperarContactoRqnpJson");}
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
		if (log.isDebugEnabled()){ log.debug("debug Inicio - RqnpController.buscarPersonaJson");}
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
			log.error("Error en RqnpController.buscarPersonaJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en RqnpController.buscarPersonaJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - RqnpController.buscarPersonaJson");}
		}
		return new ModelAndView(jsonView, "data", JsonUtil.toString(data)) ;
	}
	
	//RECUPERAR LISTA DE BIENES PARA REFRESCAR GRILLA
	
	/**
     * Recuperar Lista de Categoria en Json.
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView listarMotivoJson(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - SolicitudIncorporarBienController.listarMotivoJson");}
		Map<String, Object> parm = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		List<Map<String, Object>> lsTipoVinculo= new ArrayList<Map<String, Object>>();
		String user="";
		String num_uuoo="" ;
		try {
			parm.put("cod_par", "4041");
			parm.put("cod_tipo","D");
			lsTipoVinculo= (List<Map<String, Object>>) solicitudBienService.obtenerParametroLista(parm);
			 
			data.put("identifier", "cod");
			data.put("label", "name");
			data.put("items", lsTipoVinculo);
	
		} 
		catch(ServiceException ex){
			log.error("Error en SolicitudIncorporarBienController.listarMotivoJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en SolicitudIncorporarBienController.listarMotivoJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - SolicitudIncorporarBienController.listarMotivoJson");}
		}
		return new ModelAndView(jsonView, data) ;
	}

	/**
     * Carga la P�gina inicial para solicitar incoporacion
     * de nuevo bien o servicio.
     * @param  request
     * @param  response
     * @return ModelAndView 
     **/
	public ModelAndView iniciarConsultaCatalogo(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()) log.debug("debug Inicio - SolicitudIncorporarBienController.iniciarConsultaCatalogo");
		Map<String, Object> params = new HashMap<String, Object>();
		String cod_req="";
		String visor ="";
		String ls_registro="";
		String path_url ="";
		FechaBean fecha = new FechaBean();
		try {
			UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
			ls_registro= usuarioBean.getNroRegistro();
			log.debug("Numero Registro:" + ls_registro);
			MaestroPersonalBean  maestroPersonal= registroPersonalService.obtenerPersonaxRegistro(ls_registro.trim());
			if(maestroPersonal != null){
				WebUtils.setSessionAttribute(request,"maestroPersonalBean",maestroPersonal );
			}
			//MaestroPersonalBean maestroPersonal = (MaestroPersonalBean) WebUtils.getSessionAttribute(request, "maestroPersonalBean");
			
			cod_req = StringUtils.trim(request.getParameter("txtCodigoRqnp"));
			visor	= StringUtils.trim(request.getParameter("txtvisor"));
			path_url	= StringUtils.trim(request.getParameter("path_url"));
			
			//String parm = StringUtils.trim(request.getParameter("jParametro"));
			//String tipo = StringUtils.trim(request.getParameter("jtipoConsulta"));
			
			params.put("tipo_bien", "");
			params.put("desc_bien", "%");
			params.put("tipo_consulta", "");

			List<Map<String, Object>> lsSol = (List<Map<String, Object>>) solicitudBienService.listarConsultaCatalogo(params);
			
			return new ModelAndView("formConsultaDeCatalogo", "lsSol", lsSol)
			//.addObject("lsUnidad", lsUnidad)
			.addObject("txtCodigoRqnp", cod_req).addObject("visor", visor)
			.addObject("txt_nombreCompleto",maestroPersonal.getNombre_completo())
			.addObject("txt_fecha",fecha.getFormatDate("dd/MM/yyyy"))
			.addObject("txt_uuoo", maestroPersonal.getUuoo()+" " + maestroPersonal.getDependencia() ) 
			.addObject("path_url", path_url);
		} 
		catch(ServiceException ex){
			log.error("Error en SolicitudIncorporarBienController.iniciarConsultaCatalogo: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en SolicitudIncorporarBienController.iniciarConsultaCatalogo: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - SolicitudIncorporarBienController.iniciarConsultaCatalogo");}
		}
	}
	
	
	/**
     * Busca los bienes y servicios del Catalogo .
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView buscarConsultaCatalogoJson(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - SolicitudIncorporarBienController.buscarConsultaCatalogoJson");}
		Map<String, Object>  mapCatalogo = new HashMap<String, Object> ();
		Map<String, Object>  data = new HashMap<String, Object> ();
		String  data_uuoo= "";
		try {
			String parm = StringUtils.trim(request.getParameter("jParametro"));
			String tipo_bien = StringUtils.trim(request.getParameter("txtTipo_bien"));
			String tipo_consulta = StringUtils.trim(request.getParameter("txtTipo_consulta"));
			
			parm= parm.toUpperCase().replace(" ", "%");
			
			if(tipo_consulta.equals("A")){
				if(!parm.equals("NINGUNA") && !parm.equals("NINGUNO")){
					data_uuoo=solicitudBienService.recuperarDependenciaPorUUOO(parm);
					if(!data_uuoo.equals("")){
						mapCatalogo.put("cod_dep", data_uuoo);
					}else{
						mapCatalogo.put("cod_dep", "");
					}
				}else{
					mapCatalogo.put("cod_dep", "0000");
				}
			}
			
			mapCatalogo.put("tipo_bien", tipo_bien);
			mapCatalogo.put("tipo_consulta", tipo_consulta);
			mapCatalogo.put("desc_bien", parm);
			
			List<Map<String, Object>> lsCatalogo = (List<Map<String, Object>>) solicitudBienService.listarConsultaCatalogo(mapCatalogo);

			//data.put("lsCatalogo", JsonUtil.toString( lsCatalogo));
			//data.put("mapCatalogo", JsonUtil.toString(mapCatalogo));
			data.put("lsCatalogo", lsCatalogo);
			data.put("mapCatalogo", mapCatalogo);
			
		}
		catch(ServiceException ex){
			log.error("Error en SolicitudIncorporarBienController.buscarConsultaCatalogoJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en SolicitudIncorporarBienController.buscarConsultaCatalogoJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - SolicitudIncorporarBienController.buscarConsultaCatalogoJson");}
		}
		//return new ModelAndView(jsonView, "data", JsonUtil.toString(data));
		return new ModelAndView(jsonView,data);
	}

	public RequerimientoNoProgramadoService getRequerimientoNoProgramadoService() {
		return requerimientoNoProgramadoService;
	}


	public void setRequerimientoNoProgramadoService(
			RequerimientoNoProgramadoService requerimientoNoProgramadoService) {
		this.requerimientoNoProgramadoService = requerimientoNoProgramadoService;
	}
//DPORRASC_CATALOGO/////////////////////////////////	
	
}
