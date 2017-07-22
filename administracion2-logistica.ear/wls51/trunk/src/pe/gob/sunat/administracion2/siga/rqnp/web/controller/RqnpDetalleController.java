package pe.gob.sunat.administracion2.siga.rqnp.web.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import pe.gob.sunat.administracion2.siga.rqnp.model.domain.RequerimientoNoProgBienesBean;
import pe.gob.sunat.administracion2.siga.rqnp.model.domain.RequerimientoNoProgramadoBean;
import pe.gob.sunat.administracion2.siga.rqnp.service.RequerimientoNoProgramadoService;
import pe.gob.sunat.administracion2.siga.rqnp.util.RqnpConstantes;
import pe.gob.sunat.administracion2.siga.rqnp.util.RqnpUtil;
import pe.gob.sunat.framework.core.json.JsonUtil;
import pe.gob.sunat.framework.spring.util.exception.ServiceException;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.MaestroContratistasBean;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.MaestroPersonalBean;
import pe.gob.sunat.recurso2.administracion.siga.registro.service.RegistroPersonalService;
import pe.gob.sunat.recurso2.administracion.siga.registro.util.RegistroUtil;
import pe.gob.sunat.recurso2.administracion.siga.util.Anio;
import pe.gob.sunat.recurso2.administracion.siga.util.AuditoriaUtil;
import pe.gob.sunat.recurso2.administracion.siga.util.FechaUtil;
import pe.gob.sunat.recurso2.administracion.siga.util.FormatoConstantes;
import pe.gob.sunat.recurso2.administracion.siga.util.Mes;
import pe.gob.sunat.recurso2.administracion.siga.web.util.BaseController;
import pe.gob.sunat.tecnologia.menu.bean.UsuarioBean;

/**
 * <p>Title: RqnpController </p>
 * <p>Description: Clase Controller para la Atención de Requerimientos No Programados</p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: SUNAT</p>
 * @author SIGA
 * @version 1.0 
 */

public class RqnpDetalleController extends BaseController{
	
	private static final Log log = LogFactory.getLog(RqnpDetalleController.class);

	private RegistroPersonalService 			registroPersonalService;
	private RequerimientoNoProgramadoService 	requerimientoNoProgramadoService;
	private ReloadableResourceBundleMessageSource 		messageSource;
	private String 								uploadDir;
	
	/**
	 * @author dporrasc
     * Registra los item del RQNP.
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView registrarRqnpDetalle(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("Inicio - RqnpDetalleController.registrarRqnpDetalle");}
		
		Map<String, Object> params 		= new HashMap<String, Object>();
		boolean okRunning = false;
		Map<String, Object> mapRqnp 	= new HashMap<String, Object>();
		Map<String, Object> mapCatalogo = new HashMap<String, Object>();
		Map<String, Object> lsCatalogo 	= new HashMap<String, Object>();
		Map<String,Object> respuesta = new HashMap<String, Object>();
		
		String codigoRqnp	="";
		List<Map<String, Object>> listaBienes = new ArrayList<Map<String,Object>>();
		ModelAndView view = null;

		try {
			UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
			this.setAuditoriaBeanHolder(request, response);
			request.getSession().setAttribute("usuarioBean", usuarioBean);
			
			codigoRqnp = StringUtils.trim(request.getParameter("codigoRqnp"));
			String listaRqnpDetalleInString =  StringUtils.trim(request.getParameter("listaRqnpDetalle"));
			
			log.debug("mi jason en java: "+listaRqnpDetalleInString);
			
			ObjectMapper mapper = new ObjectMapper();
			ArrayList<RequerimientoNoProgBienesBean> arrayListRqnpBienes = mapper.readValue(listaRqnpDetalleInString,
					mapper.getTypeFactory().constructCollectionType(ArrayList.class, RequerimientoNoProgBienesBean.class));
			
			//RECUPERANDO CABECERA DE RQNP--------------------------------------------------------
			RequerimientoNoProgramadoBean rqnp= requerimientoNoProgramadoService.recuperarRqnpCab(codigoRqnp);
			
			AuditoriaUtil.set(usuarioBean);
			
			for(int i=0;i<arrayListRqnpBienes.size();i++){
				
				RequerimientoNoProgBienesBean rqnpBienBean = new RequerimientoNoProgBienesBean();
				rqnpBienBean = arrayListRqnpBienes.get(i);
				log.debug("codBien("+i+"):"+rqnpBienBean.getCodigoBien());
				codigoRqnp=requerimientoNoProgramadoService.registrarRqnpDetalle(rqnp, rqnpBienBean);
				log.debug("INSERTADO codBien("+i+"):"+rqnpBienBean.getCodigoBien());
			}
			
			//RECUPERANDO LOS DATOS REGISTRADOS--------------------------------------------------------
			rqnp= requerimientoNoProgramadoService.recuperarRqnp(codigoRqnp);
			
			//Seteando Detalle
			listaBienes = (List<Map<String, Object>>) rqnp.getListaBienes();
			 
			okRunning = true;
			
			respuesta.put("listaBienes",  RqnpUtil.toJSON(listaBienes).toString());
			
			view = new ModelAndView(getJsonView(),respuesta);
			
		}catch (ServiceException ex) {
	      log.error("Error en RqnpDetalleController.registrarRqnpDetalle: " + ex.getMessage());
	      request.setAttribute("excepAttr", ex);
	      okRunning = false;
	    }catch (Exception ex) {
	      log.error("Error en RqnpDetalleController.registrarRqnpDetalle: " + ex.getMessage(), ex);
	    }finally {
	      if (log.isDebugEnabled()) log.debug("Fin - RqnpDetalleController.registrarRqnpDetalle");
	    }

	    if (!okRunning) {
	      return new ModelAndView("pagErrorSistema");
	    }

	    return view;
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
	 * @author dporrasc
     * Elimina los items del RQNP .
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView eliminarBienRqnpJson(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - RqnpDetalleController.eliminarBienRqnpJson");}
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> respuesta = new HashMap<String, Object>();
		ModelAndView view = null;
		try {
			UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
			this.setAuditoriaBeanHolder(request, response);
			request.getSession().setAttribute("usuarioBean", usuarioBean);
			
			String codigoBien = StringUtils.trim(request.getParameter("codigoBien"));
			String codigoRqnp = StringUtils.trim(request.getParameter("codigoRqnp"));
			String anioProceso = FechaUtil.obtenerAnioActual();
			
			params.put("codigo_bien", codigoBien);
			params.put("codigo_rqnp", codigoRqnp);
			params.put("anioEjec", anioProceso);
			
			AuditoriaUtil.set(usuarioBean);

			requerimientoNoProgramadoService.deleteBienRnp(params);
			//RECUPERANDO LOS DATOS REGISTRADOS--------------------------------------------------------
			RequerimientoNoProgramadoBean rqnp= requerimientoNoProgramadoService.recuperarRqnp(codigoRqnp);
			
			//Seteando Detalle
			List<Map<String,Object>> listaBienes = (List<Map<String, Object>>) rqnp.getListaBienes();
			
			respuesta.put("listaBienes",  RqnpUtil.toJSON(listaBienes).toString());
			
			params.put("rqnp",rqnp);
			AuditoriaUtil.set(usuarioBean);
			BigDecimal monto=requerimientoNoProgramadoService.registrarCabMonto(params);
			data.put("grandMontoTotal",monto.toString());
			
			view = new ModelAndView(getJsonView(),respuesta);
		}catch(ServiceException ex){
			log.error("Error en RqnpDetalleController.eliminarBienRqnpJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}catch(Exception ex){
			log.error("Error en RqnpDetalleController.eliminarBienRqnpJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}finally{
			if (log.isDebugEnabled()){ log.debug("Fin - RqnpDetalleController.eliminarBienRqnpJson");}
		}
		
		return view ;
		
	}

	

	/**
     * Recuperar Datos de cabecera del Requerimeinto Programado .
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView recuperarDatosCabeceraRQNPJson(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - RqnpDetalleController.recuperarDatosCabeceraRQNPJson");}
		Map<String, Object>  data = new HashMap<String, Object> ();
		Map<String, Object>  parm = new HashMap<String, Object> ();
		String user="";
		try {
			String cod_rqnp = StringUtils.trim(request.getParameter("txtCodigoRqnp"));
			UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
			user= usuarioBean.getLogin();
			RequerimientoNoProgramadoBean rqnp = requerimientoNoProgramadoService.recuperarRqnp(cod_rqnp);
			
			if (rqnp !=null){
				if(rqnp.getCod_contacto()!=null){
					MaestroPersonalBean maestro = registroPersonalService.obtenerPersonaxCodigo(rqnp.getCod_contacto());
					data.put("cod_contacto", rqnp.getCod_contacto());
					data.put("nom_contacto", maestro.getNombre_completo());
					data.put("reg_contacto", maestro.getNumero_registro());
				}
				
				if (rqnp.getCod_proveedor() !=null){
					parm.put("cod_cont", rqnp.getCod_proveedor());
					parm.put("user", user);
					MaestroContratistasBean contratista=registroPersonalService.recuperarContratista(parm);
					if (contratista!=null){
						data.put("cod_cont", contratista.getCod_cont());
						data.put("num_ruc", contratista.getNum_ruc());
						data.put("raz_social", contratista.getRaz_social());
					}
				}
				data.put("anexo_contacto",rqnp.getAnexo_contacto());
				data.put("cod_finalidad",rqnp.getCod_finalidad());
				data.put("cod_necesidad",rqnp.getCod_necesidad());
				data.put("cod_tip_nececidad",rqnp.getCod_tip_nececidad());
				data.put("nom_tip_necesidad",(rqnp.getNom_tip_necesidad()== null)?" " : rqnp.getNom_tip_necesidad());
				data.put("obs_dir_entrega",(rqnp.getObs_dir_entrega()==null)?" ":rqnp.getObs_dir_entrega());
				data.put("obs_justificacion",(rqnp.getObs_justificacion()==null)?" ":rqnp.getObs_justificacion());
				data.put("obs_lugar_entrega",(rqnp.getObs_lugar_entrega() ==null)?" ": rqnp.getObs_lugar_entrega());
				data.put("obs_plazo_entrega",(rqnp.getObs_plazo_entrega()==null)?" ":rqnp.getObs_plazo_entrega() );
				data.put("motivoSolicitud",rqnp.getMotivoSolicitud());
				
			}else{
				log.debug("NULO RQNP");
				data.put("nom_contacto",messageSource.getMessage("error.registrarRqnp.contacto" , null, Locale.getDefault()));
				data.put("error", "-1");
				data.put("mensaje", messageSource.getMessage("error.registrarRqnp.contacto" , null, Locale.getDefault()));	
			}
			
		} 
		catch(ServiceException ex){
			log.error("Error en RqnpDetalleController.recuperarDatosCabeceraRQNPJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en RqnpDetalleController.recuperarDatosCabeceraRQNPJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()) {log.debug("Fin - RqnpDetalleController.recuperarDatosCabeceraRQNPJson");}
		}
		return new ModelAndView(jsonView, "data", JsonUtil.toString(data)) ;
	}
	
	
	/**
     * Recuperar Datos de Item .
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView recuperarDatosItemJson(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - RqnpDetalleController.recuperarDatosItemJson");}
		Map<String, Object> parm = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		String user="";
		try {
		
			String cod_rqnp = StringUtils.trim(request.getParameter("jcodigo_rqnp"));
			String cod_bien = StringUtils.trim(request.getParameter("jcodigo_bien"));
			
			RequerimientoNoProgBienesBean bien = requerimientoNoProgramadoService.recuperarDatosEntregaBien(cod_rqnp, cod_bien);
			UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
			user= usuarioBean.getLogin();
			
			if (bien !=null){
				if (bien.getCod_proveedor() !=null){
					parm.put("cod_cont", bien.getCod_proveedor());
					parm.put("user", user);
					MaestroContratistasBean contratista=registroPersonalService.recuperarContratista(parm);
					if (contratista !=null){
						data.put("cod_cont", contratista.getCod_cont());
						data.put("num_ruc", contratista.getNum_ruc());
						data.put("raz_social", contratista.getRaz_social());
					}
				}
				data.put("obs_dir_entrega",bien.getObs_dir_entrega());
				data.put("obs_justificacion",bien.getObs_justificacion());
				data.put("obs_lugar_entrega",bien.getObs_lugar_entrega());
				data.put("obs_plazo_entrega",bien.getObs_plazo_entrega());
			}else{
				data.put("nom_contacto",messageSource.getMessage("error.registrarRqnp.contacto" , null, Locale.getDefault()));
				data.put("error", "-1");
				data.put("mensaje", messageSource.getMessage("error.registrarRqnp.contacto" , null, Locale.getDefault()));	
			}
			
		} 
		catch(ServiceException ex){
			log.error("Error en RqnpDetalleController.recuperarDatosItemJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en RqnpDetalleController.recuperarDatosItemJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - RqnpDetalleController.recuperarDatosItemJson");}
		}
		return new ModelAndView(jsonView, "data", JsonUtil.toString(data)) ;
	}

	

	/**
     * Verifica que el registro de contacto existe.
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView validarContactoRqnpJson(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - RqnpDetalleController.validarContactoRqnpJson");}
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
			if (personal !=null){
				data.put("nom_contacto",personal.getNombre_completo());
				data.put("cod_contacto", personal.getCodigoEmpleado());
				 if (!ruc_prov.equals("")){
					 parm.put("num_ruc", ruc_prov);
					 parm.put("user", user);	
					 MaestroContratistasBean contratista=registroPersonalService.recuperarContratistaPadron(parm);
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
			log.error("Error en RqnpDetalleController.validarContactoRqnpJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en RqnpDetalleController.validarContactoRqnpJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - RqnpDetalleController.validarContactoRqnpJson");}
		}
		return new ModelAndView(jsonView, "data", JsonUtil.toString(data)) ;
	}

	

	/**
     * Recuperar los Datos de Entrega del Bien .
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView recuperarBienEntregaJson(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - RqnpDetalleController.recuperarBienEntregaJson");}
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
		} 
		catch(ServiceException ex){
			log.error("Error en RqnpDetalleController.recuperarBienEntregaJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en RqnpDetalleController.recuperarBienEntregaJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - RqnpDetalleController.recuperarBienEntregaJson");}
		}
		return new ModelAndView(jsonView, "data", JsonUtil.toString(data)) ;
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
	public String getUploadDir() {
		return uploadDir;
	}
	public void setUploadDir(String uploadDir) {
		this.uploadDir = uploadDir;
	}
}
