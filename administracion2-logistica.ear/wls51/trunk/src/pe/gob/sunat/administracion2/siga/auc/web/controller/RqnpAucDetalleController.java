package pe.gob.sunat.administracion2.siga.auc.web.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import pe.gob.sunat.administracion2.siga.rqnp.model.domain.RequerimientoNoProgBienesBean;
import pe.gob.sunat.administracion2.siga.rqnp.model.domain.RequerimientoNoProgramadoBean;
import pe.gob.sunat.administracion2.siga.rqnp.service.RequerimientoAucService;
import pe.gob.sunat.administracion2.siga.rqnp.service.RequerimientoNoProgramadoService;
import pe.gob.sunat.framework.core.json.JsonUtil;
import pe.gob.sunat.framework.spring.util.exception.ServiceException;
import pe.gob.sunat.framework.spring.web.view.JsonView;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.MaestroContratistasBean;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.MaestroPersonalBean;
import pe.gob.sunat.recurso2.administracion.siga.registro.service.RegistroPersonalService;
import pe.gob.sunat.recurso2.administracion.siga.web.util.BaseController;
import pe.gob.sunat.tecnologia.menu.bean.UsuarioBean;


/**
 * <p>Title: RqnpAucDetalleController </p>
 * <p>Description: Clase Controller para la Atenc�n de Requerimientos No Programados de AUC</p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: SUNAT</p>
 * @author DPORRASC
 * @version 1.0 
 */

public class RqnpAucDetalleController extends BaseController{
	
	private static final Log log = LogFactory.getLog(RqnpAucDetalleController.class);

	private RegistroPersonalService 			registroPersonalService;
	private RequerimientoNoProgramadoService 	requerimientoNoProgramadoService;
	private ReloadableResourceBundleMessageSource 		messageSource;
	//private JsonView 							jsonView;
	private String 								uploadDir;
	//private View 								htmlView;
	//private MensajeBean 						beanMsg;
	
	
	private RequerimientoAucService	requerimientoAucService;
	/**
     * Registra la Cabecera del RQNP, genera el numero de secuencia.
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	//REGISTRAR EL DETALLE DE RQNP AUCT1-AUCT2-AUCT3 (UPDATE BASE DE DATOS)
	public ModelAndView registrarCabEntregaRqnpJson(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("Inicio - RqnpAucDetalleController.registrarCabEntregaRqnpJson");}
		Map<String, Object> data 		= new HashMap<String, Object>();
		Map<String, Object> params 		= new HashMap<String, Object>();
		
		boolean okRunning = true;
		 String user	="";
		 String cod_proveedor="";
		 String cod_cont="";

		try {
			UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
			user = usuarioBean.getLogin();
			MaestroPersonalBean personal=new MaestroPersonalBean();
			String cod_reg 		= StringUtils.trim(request.getParameter("txtReg_contacto"));
			String cod_prov 	= StringUtils.trim(request.getParameter("txtCod_proveedor"));
			String codigoRqnp 	=  StringUtils.trim(request.getParameter("txtCodigoRqnp"));
			String motivoSol 	= StringUtils.trim(request.getParameter("txtMotivo"));
			String cod_contacto = StringUtils.trim(request.getParameter("txtCod_contacto"));
			String anexo_contacto = StringUtils.trim(request.getParameter("txtAnexo_contacto"));
			String cod_necesidad = StringUtils.trim(request.getParameter("txtCod_necesidad"));
			String cod_tip_nececidad = "01";
			String nom_tip_necesidad = StringUtils.trim(request.getParameter("txtNom_tip_necesidad"));
			String cod_finalidad = StringUtils.trim(request.getParameter("txtCod_finalidad"));
			String num_ruc = StringUtils.trim(request.getParameter("txtNum_ruc_prov"));
			String obs_justificacion = StringUtils.trim(request.getParameter("txtObs_justificacion"));
			String obs_plazo_entrega = StringUtils.trim(request.getParameter("txtObs_plazo_entrega"));
			String obs_lugar_entrega = StringUtils.trim(request.getParameter("txtObs_lugar_entrega"));
			String obs_dir_entrega = StringUtils.trim(request.getParameter("txtObs_dir_entrega"));
			//AGREGADO (DPORRASC)
			String auct1 		= 	StringUtils.trim(request.getParameter("txtCod_UUOO1"));
			String auct2 		= 	StringUtils.trim(request.getParameter("txtCod_UUOO2"));
			String auct3 		= 	StringUtils.trim(request.getParameter("txtCod_UUOO3"));
			
			params.put("cod_reg", cod_reg);
			personal=registroPersonalService.obtenerPersonaxRegistro(cod_reg);
			if (personal !=null){
				data.put("nom_contacto",personal.getNombre_completo());
				data.put("cod_contacto", personal.getCodigoEmpleado());
				 if (!num_ruc.equals("")){
					 params.put("user", user);	
					 params.put("num_ruc", num_ruc);
					 MaestroContratistasBean contratista=registroPersonalService.recuperarContratistaPadron(params);
					 if (contratista !=null){
						 cod_cont=contratista.getCod_cont();
						 data.put("cod_cont", contratista.getCod_cont());
						 data.put("num_ruc", contratista.getNum_ruc());
						 data.put("raz_social", contratista.getRaz_social());
						 data.put("msj_err", " ");
						 if (!num_ruc.equals("")){
							cod_proveedor = StringUtils.trim(request.getParameter("txtCod_proveedor"));
						}
					 }else{
						 data.put("cod_cont", "-1");
						 data.put("raz_social", "-1");
						 data.put("msj_err", " ");
						 okRunning = true;
					 }
				 }
			}else{
				data.put("nom_contacto","-1");
				data.put("msj_err", "");
				okRunning = true;
			}
			if(okRunning){
				params.put("cod_cont", cod_cont);
				params.put("codigoRqnp", codigoRqnp);
				params.put("motivoSolicitud", motivoSol);
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
				
				//AGREGADO (DPORRASC)
				params.put("auct1",auct1);
				params.put("auct2",auct2);
				params.put("auct3",auct3);
				
				params.put("user", user);
				
				requerimientoNoProgramadoService.registrarCabEntregaRqnpAUC(params);
			}
		} 
		catch (ServiceException ex) {
			log.error("Error en RqnpAucDetalleController.registrarCabEntregaRqnpJson: " + ex.getMessage());
		} 
		catch (Exception ex) {
			log.error("Error en RqnpAucDetalleController.registrarCabEntregaRqnpJson: " + ex.getMessage(), ex);
		} 
		finally {
			if (log.isDebugEnabled()){ log.debug("Fin - RqnpAucDetalleController.registrarCabEntregaRqnpJson");}
		}
		data.put("mensaje", messageSource.getMessage("formRegistro.mensaje.registroAtender" , null, Locale.getDefault()));	
		if(!okRunning){
			data.put("error", "-1");
			data.put("mensaje", messageSource.getMessage("error.registrarAtencion.generico" , null, Locale.getDefault()));		
			return new ModelAndView(jsonView, "data", data);
		}
		return new ModelAndView(jsonView, "data", JsonUtil.toString(data));
	}
	
	
	
	/**
     * Registra los item del RQNP.
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView registrarRqnpDetalle(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("Inicio - RqnpAucDetalleController.registrarRqnpDetalle");}
		
		Map<String, Object> params 		= new HashMap<String, Object>();
		boolean okRunning = false;
		Map<String, Object> mapRqnp 	= new HashMap<String, Object>();
		Map<String, Object> mapCatalogo = new HashMap<String, Object>();
		Map<String, Object> lsCatalogo 	= new HashMap<String, Object>();
		Map<String, Object> parm = new HashMap<String, Object>();
		
		String cod_req	="";
		String ls_pag 	="";
		String user 	="";
		String visor	="";
		String cod_proveedor="";
		List<Map<String, Object>> listaBienes = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> listaMetas = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> lsNecesidad= new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> lsTipoNecesidad= new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> lsFinalidad= new ArrayList<Map<String, Object>>();
		
		Calendar fecha =  Calendar.getInstance();
		visor	= StringUtils.trim(request.getParameter("txtvisor"));//(viene de formRegistroRqnpDetalleAUC) visor=0
		try {
			UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
			MaestroPersonalBean  maestroPersonal=  (MaestroPersonalBean) WebUtils.getSessionAttribute(request, "maestroPersonalBean");
			
			String motivoSol = StringUtils.trim(request.getParameter("jtxtMotivo"));
			String cod_contacto = StringUtils.trim(request.getParameter("txtCod_contacto"));
			String anexo_contacto = StringUtils.trim(request.getParameter("txtAnexo_contacto")); //(MODIFICADO DPORRASC)jtxtAnexo_contacto
			String cod_necesidad = StringUtils.trim(request.getParameter("jtxtCod_necesidad"));
			String cod_tip_nececidad = "01";
			String nom_tip_necesidad = StringUtils.trim(request.getParameter("jtxtNom_tip_necesidad"));
			String num_ruc = StringUtils.trim(request.getParameter("txtNum_ruc_prov"));
			String cod_finalidad = StringUtils.trim(request.getParameter("txtCod_finalidad"));
			if (!num_ruc.equals("")){
				cod_proveedor = StringUtils.trim(request.getParameter("txtCod_proveedor"));
			}
			String obs_justificacion = StringUtils.trim(request.getParameter("txtObs_justificacion"));
			String obs_plazo_entrega = StringUtils.trim(request.getParameter("txtObs_plazo_entrega"));
			String obs_lugar_entrega = StringUtils.trim(request.getParameter("txtObs_lugar_entrega"));
			String obs_dir_entrega = StringUtils.trim(request.getParameter("txtObs_dir_entrega"));
			
			cod_req = StringUtils.trim(request.getParameter("txtCodigoRqnp"));
			ls_pag =  StringUtils.trim(request.getParameter("jParametro"));
			user = usuarioBean.getLogin();
			
			//RECUPERANDO RQNP--------------------------------------------------------
			RequerimientoNoProgramadoBean rqnp= requerimientoNoProgramadoService.recuperarRqnpCabAUC(cod_req);
			
			String[] cod_rqnp =request.getParameterValues("txtRqnp");
			String[] cod_bien =request.getParameterValues("txtCodigo");
			String[] cod_unid =request.getParameterValues("txtcodUnidad");
			String[] cantidad =request.getParameterValues("txtCantidad");
			String[] pre_unit =request.getParameterValues("txtPrecioUnit");
			String[] cod_clas =request.getParameterValues("txtCodClasi");
			String[] auct1 =request.getParameterValues("txtAuct1");
			String[] auct2 =request.getParameterValues("txtAuct2");
			String[] auct3 =request.getParameterValues("txtAuct3");
			String anio_ejec = StringUtils.trim(request.getParameter("janio_ejec"));
			
			params.put("codigo_rqnp", cod_req);
			params.put("motivoSolicitud",motivoSol);
			
			params.put("cod_contacto",		cod_contacto);
			params.put("anexo_contacto",	anexo_contacto);
			params.put("cod_necesidad",		cod_necesidad);
			params.put("cod_tip_nececidad",	cod_tip_nececidad);
			params.put("nom_tip_necesidad",	nom_tip_necesidad);
			
			params.put("cod_finalidad", cod_finalidad);
			params.put("cod_proveedor", cod_proveedor);
			params.put("obs_justificacion", obs_justificacion);
			params.put("obs_plazo_entrega", obs_plazo_entrega);
			params.put("obs_lugar_entrega", obs_lugar_entrega);
			params.put("obs_dir_entrega", obs_dir_entrega);
					
			params.put("cod_rqnp",cod_rqnp);
			params.put("cod_bien",cod_bien);
			params.put("cod_unid",cod_unid);
			params.put("cantidad",cantidad);
			params.put("pre_unit",pre_unit);
			params.put("cod_clas",cod_clas);
			params.put("anio_ejec", anio_ejec);
			params.put("auct1", auct1);
			params.put("auct2", auct2);
			params.put("auct3", auct3);
			params.put("user", user);
			params.put("anio_pro", String.valueOf( fecha.get(Calendar.YEAR)));
			params.put("cod_sede", rqnp.getCodigoSede());
			params.put("cod_plaza", maestroPersonal.getCod_plaza());
			params.put("cod_responsable", rqnp.getCodigoResposanble());
		
			
			//REGISTRANDO EL RQNP---------------------------------------------------------------------
			cod_req=requerimientoNoProgramadoService.registrarDetalleRqnp(params);
			rqnp=null;
			//RECUPERANDO LOS DATOS REGISTRADOS--------------------------------------------------------
			rqnp= requerimientoNoProgramadoService.recuperarRqnpAUC(cod_req);
					
			 //Seteando Detalle
			
			 listaBienes = (List<Map<String, Object>>) rqnp.getListaBienes();
			 
			 if (listaBienes !=null){
				 if (listaBienes.size() >0 ){
					 Map<String, Object> bien =  listaBienes.get(0);
					 
					mapRqnp.put("jcodigo_bien", bien.get("codigoBien"));
					mapRqnp.put("jprecio_unid", bien.get("precioUnid"));
					 
					params.put("cod_bien", bien.get("codigoBien") );
					params.put("cod_req",  bien.get("codigoRqnp"));
					params.put("precio_unid", ( (BigDecimal)bien.get("precioUnid")).toString() );
					params.put("cod_dep",rqnp.getCodigoDependencia());
					params.put("anio_ejec",rqnp.getAnioProceso());
						 
					// COMENTADO PARA NO MOSTRAR LISTA DE METAS POR UNIDAD
					//listaMetas = (List<Map<String, Object>>) requerimientoNoProgramadoService.listarRqnpMetas(params);
				 }
			 }
			 
			okRunning = true;
			
			 mapRqnp.put("codigoRqnp", rqnp.getCodigoRqnp());
			 mapRqnp.put("codigoReqNoProgramado", rqnp.getCodigoReqNoProgramado());
			 mapRqnp.put("codigoResposanble", rqnp.getNumeroRegistro());
			 mapRqnp.put("resposanble", rqnp.getNombreSolicitante());
			 mapRqnp.put("dependencia", rqnp.getNombreUOSolicitante());
			 mapRqnp.put("fechaRqnp",new SimpleDateFormat("dd/MM/yyyy HH:mm").format(rqnp.getFechaRqnp()));
			 mapRqnp.put("motivoRqnp",rqnp.getMotivoSolicitud());
			 MaestroPersonalBean personal =registroPersonalService.obtenerPersonaxCodigo(rqnp.getCod_contacto().trim());
			 mapRqnp.put("cod_contacto",rqnp.getCod_contacto());
			 mapRqnp.put("nom_contacto",personal.getNombre_completo());
			 mapRqnp.put("reg_contacto",personal.getNumero_registro());
			 mapRqnp.put("anexo_contacto",rqnp.getAnexo_contacto());
			 mapRqnp.put("cod_necesidad",rqnp.getCod_necesidad());
			 mapRqnp.put("cod_tip_nececidad",rqnp.getCod_tip_nececidad());
			 mapRqnp.put("nom_tip_necesidad",rqnp.getNom_tip_necesidad());
			 mapRqnp.put("codigoUuoo", rqnp.getUuoo());
			 mapRqnp.put("codigoDependencia", rqnp.getCodigoDependencia());
			 mapRqnp.put("anioProceso", rqnp.getAnioProceso());
			 mapRqnp.put("montoRqnp",  rqnp.getMontoRqnp().setScale(2, RoundingMode.HALF_UP).toString());
			 mapRqnp.put("cod_finalidad", rqnp.getCod_finalidad());
			
			 if (rqnp.getCod_proveedor() !=null) {
				parm.put("cod_cont", rqnp.getCod_proveedor());
				parm.put("user", user);
				MaestroContratistasBean contratista=registroPersonalService.recuperarContratista(parm);
				if (contratista!=null){
					mapRqnp.put("cod_cont", contratista.getCod_cont());
					mapRqnp.put("num_ruc", contratista.getNum_ruc());
					mapRqnp.put("raz_social", contratista.getRaz_social());
				}
			 }
			 
			 mapRqnp.put("obs_justificacion", rqnp.getObs_justificacion());
			 mapRqnp.put("obs_plazo_entrega", rqnp.getObs_plazo_entrega());
			 mapRqnp.put("obs_lugar_entrega", rqnp.getObs_lugar_entrega());
			 mapRqnp.put("obs_dir_entrega", rqnp.getObs_dir_entrega());
			 mapRqnp.put("cod_objeto", rqnp.getCod_objeto());
			 log.debug("cod_objeto" + rqnp.getCod_objeto());
			 lsFinalidad= (List<Map<String, Object>>) requerimientoNoProgramadoService.listaFinalidad();
			 lsNecesidad = (List<Map<String, Object>>) requerimientoNoProgramadoService.listaNecesidad();
			 lsTipoNecesidad = (List<Map<String, Object>>) requerimientoNoProgramadoService.listaTipoNecesidad();
			 
		} 
		catch (ServiceException ex) {
			log.error("Error en RqnpAucDetalleController.registrarRqnpDetalle: " + ex.getMessage());
			request.setAttribute("excepAttr", ex);
			okRunning=false;
		} 
		catch (Exception ex) {
			log.error("Error en RqnpAucDetalleController.registrarRqnpDetalle: " + ex.getMessage(), ex);
		} 
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RqnpAucDetalleController.registrarRqnpDetalle");
		}

		if (!okRunning) {
			return new ModelAndView("pagErrorSistema");
		}
		
		return new ModelAndView(ls_pag).addObject("mapRqnp", mapRqnp).addObject("mapCatalogo", mapCatalogo)
			.addObject("lsCatalogo", lsCatalogo).addObject("listaBienes", listaBienes)
			.addObject("visor", visor).addObject("lsNecesidad", lsNecesidad)
			.addObject("lsTipoNecesidad", lsTipoNecesidad).addObject("lsFinalidad", lsFinalidad);
	}
	
	
	
	/**
     * Registra los datos de la entrega de cada item del requerimiento y devulve un  Json (afectaci�n presupuestal).
     * @param  request
     * @return ModelAndView 
     **/
	public ModelAndView registrarDetalleEntregaBienJson(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()) {log.debug("Inicio - RqnpAucDetalleController.registrarDetalleEntregaBienJson");}
		
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
		
		//AGREGADO (DPORRASC)
		String auct1="";
		String auct2="";
		String auct3="";
	
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
			
			//AGREGADO (DPORRASC)
			auct1 		= 	StringUtils.trim(request.getParameter("txtNum_UUOO1"));
			auct2 		= 	StringUtils.trim(request.getParameter("txtNum_UUOO2"));
			auct3 		= 	StringUtils.trim(request.getParameter("txtNum_UUOO3"));
			
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
			
			//AGREGADO (DPORRASC)
			params.put("auct1",auct1);
			params.put("auct2",auct2);
			params.put("auct3",auct3);
			
			params.put("user", user);
			///guardando metas
			requerimientoNoProgramadoService.registrarDetalleEntregaBien(params);
			data.put("mensaje", messageSource.getMessage("formRegistro.mensaje.registroAtender" , null, Locale.getDefault()));
			okRunning=true; 
		} 
		
		catch (ServiceException ex) {
			log.error("Error en RqnpAucDetalleController.registrarDetalleEntregaBienJson: " + ex.getMessage());
		} 
		catch (Exception ex) {
			log.error("Error en RqnpAucDetalleController.registrarDetalleEntregaBienJson: " + ex.getMessage(), ex);
		} 
		finally {
			if (log.isDebugEnabled()){ log.debug("Fin - RqnpAucDetalleController.registrarDetalleEntregaBienJson");}
		}
		
		if(!okRunning){
			data.put("error", "-1");
			data.put("mensaje", messageSource.getMessage("error.registrarAtencion.generico" , null, Locale.getDefault()));		
			return new ModelAndView(jsonView, "data", data);
		}
		return new ModelAndView(jsonView, "data", JsonUtil.toString(data)) ;
	}
	
	
	/**
     * Busca los bienes y servicios del Catalogo .
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView buscarCatalogoJson(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - RqnpAucDetalleController.buscarCatalogoJson");}
		Map<String, Object>  mapCatalogo = new HashMap<String, Object> ();
		Map<String, Object>  data = new HashMap<String, Object> ();
		String anio_ejec="";
		try {
			String parm = StringUtils.trim(request.getParameter("jParametro"));
			String tipo = StringUtils.trim(request.getParameter("jtipoConsulta"));
			
			Calendar fecha =  Calendar.getInstance();
			anio_ejec= String.valueOf( fecha.get(Calendar.YEAR)) ;
			
			parm= parm.toUpperCase().replace(" ", "%");
			MaestroPersonalBean  maestroPersonal=  (MaestroPersonalBean) WebUtils.getSessionAttribute(request, "maestroPersonalBean");
			
			String cod_plaza = maestroPersonal.getCodigoSede();
			
			mapCatalogo.put("parm", parm);
			mapCatalogo.put("tipo", tipo);
			mapCatalogo.put("anio_ejec", anio_ejec);
			mapCatalogo.put("cod_plaza", cod_plaza);
			//mapCatalogo.put("cod_sede", cod_sede);
			mapCatalogo.put("cod_dep", maestroPersonal.getCodigoDependencia());
			
			List<Map<String, Object>> lsCatalogo = (List<Map<String, Object>>) requerimientoNoProgramadoService.listarCatalogo(mapCatalogo);

			data.put("lsCatalogo", JsonUtil.toString( lsCatalogo));
			data.put("mapCatalogo", JsonUtil.toString(mapCatalogo));
			
		} 
		catch(ServiceException ex){
			log.error("Error en RqnpAucDetalleController.buscarCatalogoJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en RqnpAucDetalleController.buscarCatalogoJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - RqnpAucDetalleController.buscarCatalogoJson");}
		}
		return new ModelAndView(jsonView, "data", JsonUtil.toString(data)) ;
	}
	
	
	
	
	
	
	/**
     * Elimina los items del RQNP .
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView eliminarBienRqnpJson(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - RqnpAucDetalleController.eliminarBienRqnpJson");}
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		String user="";
		try {
			UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
			user = usuarioBean.getLogin();
			
			String codigo_bien = StringUtils.trim(request.getParameter("jcodigo_bien"));
			String codigo_rqnp = StringUtils.trim(request.getParameter("jcodigo_rqnp"));
			String anioEjec = StringUtils.trim(request.getParameter("janio_ejec"));
			
			params.put("codigo_bien", codigo_bien);
			params.put("codigo_rqnp", codigo_rqnp);
			params.put("anioEjec", anioEjec);
			params.put("user", user);
			requerimientoNoProgramadoService.deleteBienRnp(params);
			
			RequerimientoNoProgramadoBean rqnp= requerimientoNoProgramadoService.recuperarRqnpAUC(codigo_rqnp);
			params.put("rqnp",rqnp);
			BigDecimal monto=requerimientoNoProgramadoService.registrarCabMonto(params);
			data.put("grandMontoTotal",monto.toString());
		} 
		catch(ServiceException ex){
			log.error("Error en RqnpAucDetalleController.eliminarBienRqnpJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en RqnpAucDetalleController.eliminarBienRqnpJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - RqnpAucDetalleController.eliminarBienRqnpJson");}
		}
		return new ModelAndView(jsonView, "data", JsonUtil.toString(data)) ;
	}

	


	/**
     * Recuperar Datos de cabecera del Requerimeinto Programado .
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView recuperarDatosCabeceraRQNPJson(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - RqnpAucDetalleController.recuperarDatosCabeceraRQNPJson");}
		Map<String, Object>  data = new HashMap<String, Object> ();
		Map<String, Object>  parm = new HashMap<String, Object> ();
		String user="";
		try {
			String cod_rqnp = StringUtils.trim(request.getParameter("txtCodigoRqnp"));
			UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
			user= usuarioBean.getLogin();
			RequerimientoNoProgramadoBean rqnp = requerimientoNoProgramadoService.recuperarRqnpAUC(cod_rqnp);
			
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
			log.error("Error en RqnpAucDetalleController.recuperarDatosCabeceraRQNPJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en RqnpAucDetalleController.recuperarDatosCabeceraRQNPJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()) {log.debug("Fin - RqnpAucDetalleController.recuperarDatosCabeceraRQNPJson");}
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
		if (log.isDebugEnabled()){ log.debug("debug Inicio - RqnpAucDetalleController.buscarPersonaJson");}
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
			log.error("Error en RqnpAucDetalleController.buscarPersonaJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en RqnpAucDetalleController.buscarPersonaJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - RqnpAucDetalleController.buscarPersonaJson");}
		}
		return new ModelAndView(jsonView, "data", JsonUtil.toString(data)) ;
	}
	
	
	

	
	
	
	

	/**
     * Recuperar Contacto de Requerimiento .
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView recuperarContactoRqnpJson(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - RqnpAucDetalleController.recuperarContactoRqnpJson");}
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
			log.error("Error en RqnpAucDetalleController.recuperarContactoRqnpJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en RqnpAucDetalleController.recuperarContactoRqnpJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - RqnpAucDetalleController.recuperarContactoRqnpJson");}
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
		if (log.isDebugEnabled()){ log.debug("debug Inicio - RqnpAucDetalleController.recuperarDatosItemJson");}
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
			log.error("Error en RqnpAucDetalleController.recuperarDatosItemJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en RqnpAucDetalleController.recuperarDatosItemJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - RqnpAucDetalleController.recuperarDatosItemJson");}
		}
		return new ModelAndView(jsonView, "data", JsonUtil.toString(data)) ;
	}

	
	

	/**
     * Recuperar Contratista del Requerimiento .
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView recuperarContratistaRqnpJson(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - RqnpAucDetalleController.recuperarContratistaRqnpJson");}
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
			log.error("Error en RqnpAucDetalleController.recuperarContratistaRqnpJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en RqnpAucDetalleController.recuperarContratistaRqnpJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - RqnpAucDetalleController.recuperarContratistaRqnpJson");}
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
		if (log.isDebugEnabled()){ log.debug("debug Inicio - RqnpAucDetalleController.validarContactoRqnpJson");}
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
			log.error("Error en RqnpAucDetalleController.validarContactoRqnpJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en RqnpAucDetalleController.validarContactoRqnpJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - RqnpAucDetalleController.validarContactoRqnpJson");}
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
		if (log.isDebugEnabled()){ log.debug("debug Inicio - RqnpAucDetalleController.recuperarBienEntregaJson");}
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
			log.error("Error en RqnpAucDetalleController.recuperarBienEntregaJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en RqnpAucDetalleController.recuperarBienEntregaJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - RqnpAucDetalleController.recuperarBienEntregaJson");}
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
//INICIO: DPORRASC
	/**
     * Registra los item del RQNP.
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView registrarRqnpDetalleAUC(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("Inicio - RqnpAucDetalleController.registrarRqnpDetalleAUC");}
		
		Map<String, Object> params 		= new HashMap<String, Object>();
		boolean okRunning = false;
		Map<String, Object> mapRqnp 	= new HashMap<String, Object>();
		Map<String, Object> mapCatalogo = new HashMap<String, Object>();
		Map<String, Object> lsCatalogo 	= new HashMap<String, Object>();
		Map<String, Object> mapDatos_auc = new HashMap<String, Object>();
		Map<String, Object> parm = new HashMap<String, Object>();
		
		String cod_req	="";
		String ls_pag 	="";
		String user 	="";
		String visor	="";
		String cod_proveedor="";
		List<Map<String, Object>> listaBienes = new ArrayList<Map<String,Object>>();
		
		//(DPORRASC)List<Map<String, Object>> listaMetas = new ArrayList<Map<String,Object>>();

		List<Map<String, Object>> lsNecesidad= new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> lsTipoNecesidad= new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> lsFinalidad= new ArrayList<Map<String, Object>>();
		
		Calendar fecha =  Calendar.getInstance();
		visor	= StringUtils.trim(request.getParameter("txtvisor"));//(viene de formRegistroRqnpDetalleAUC) visor=0
		try {
			UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
			MaestroPersonalBean  maestroPersonal=  (MaestroPersonalBean) WebUtils.getSessionAttribute(request, "maestroPersonalBean");
			mapDatos_auc = (HashMap<String, Object>) WebUtils.getSessionAttribute(request, "mapDatos_auc");
			log.debug("tipo_auc" + mapDatos_auc.get("tipo_auc") );
			String motivoSol = StringUtils.trim(request.getParameter("jtxtMotivo"));
			String cod_contacto = StringUtils.trim(request.getParameter("txtCod_contacto"));
			String anexo_contacto = StringUtils.trim(request.getParameter("txtAnexo_contacto")); //(MODIFICADO DPORRASC)jtxtAnexo_contacto
			String cod_necesidad = StringUtils.trim(request.getParameter("jtxtCod_necesidad"));
			String cod_tip_nececidad = "01";
			String nom_tip_necesidad = StringUtils.trim(request.getParameter("jtxtNom_tip_necesidad"));
			String num_ruc = StringUtils.trim(request.getParameter("txtNum_ruc_prov"));
			String cod_finalidad = StringUtils.trim(request.getParameter("txtCod_finalidad"));
			if (!num_ruc.equals("")){
				cod_proveedor = StringUtils.trim(request.getParameter("txtCod_proveedor"));
			}
			String obs_justificacion = StringUtils.trim(request.getParameter("txtObs_justificacion"));
			String obs_plazo_entrega = StringUtils.trim(request.getParameter("txtObs_plazo_entrega"));
			String obs_lugar_entrega = StringUtils.trim(request.getParameter("txtObs_lugar_entrega"));
			String obs_dir_entrega = StringUtils.trim(request.getParameter("txtObs_dir_entrega"));
			
			cod_req = StringUtils.trim(request.getParameter("txtCodigoRqnp"));
			ls_pag =  StringUtils.trim(request.getParameter("jParametro")); //formRegistroRqnpMetasAUC.jsp
			user = usuarioBean.getLogin();
			
			//RECUPERANDO RQNP--------------------------------------------------------
			RequerimientoNoProgramadoBean rqnp= requerimientoNoProgramadoService.recuperarRqnpCabAUC(cod_req);
			
			String[] cod_rqnp =request.getParameterValues("txtRqnp");
			String[] cod_bien =request.getParameterValues("txtCodigo");
			String[] cod_unid =request.getParameterValues("txtcodUnidad");
			String[] cantidad =request.getParameterValues("txtCantidad");
			String[] pre_unit =request.getParameterValues("txtPrecioUnit");
			String[] cod_clas =request.getParameterValues("txtCodClasi");
			String[] auct1 =request.getParameterValues("txtAuct1");
			//String[] auct2 =request.getParameterValues("txtAuct2");
			//String[] auct3 =request.getParameterValues("txtAuct3");
			
			String[] tipo_ruta =request.getParameterValues("txtCod_tipo_prog");
			
			String anio_ejec = StringUtils.trim(request.getParameter("janio_ejec"));
			
			params.put("codigo_rqnp", cod_req);
			params.put("motivoSolicitud",motivoSol);
			
			params.put("cod_contacto",		cod_contacto);
			params.put("anexo_contacto",	anexo_contacto);
			params.put("cod_necesidad",		cod_necesidad);
			//params.put("cod_tip_nececidad",	cod_tip_nececidad);
			//params.put("nom_tip_necesidad",	nom_tip_necesidad);
			
			params.put("cod_finalidad", cod_finalidad);
			params.put("cod_proveedor", cod_proveedor);
			params.put("obs_justificacion", obs_justificacion);
			params.put("obs_plazo_entrega", obs_plazo_entrega);
			params.put("obs_lugar_entrega", obs_lugar_entrega);
			params.put("obs_dir_entrega", obs_dir_entrega);
					
			params.put("cod_rqnp",cod_rqnp);
			params.put("cod_bien",cod_bien);
			params.put("cod_unid",cod_unid);
			params.put("cantidad",cantidad);
			params.put("pre_unit",pre_unit);
			params.put("cod_clas",cod_clas);
			params.put("anio_ejec", anio_ejec);
			params.put("auct1", auct1);
			//params.put("auct2", auct2);
			//params.put("auct3", auct3);
			params.put("tipo_ruta", tipo_ruta);
			params.put("user", user);
			params.put("anio_pro", String.valueOf( fecha.get(Calendar.YEAR)));
			params.put("cod_sede", rqnp.getCodigoSede());
			params.put("cod_plaza", maestroPersonal.getCod_plaza());
			params.put("cod_responsable", rqnp.getCodigoResposanble());
			
			//REGISTRANDO EL RQNP---------------------------------------------------------------------
			//MODIFICADO DPORRASC
			//EN EL FLUJO ANTERIOR, ACTUALIZA LAS METAS - AHORA YA NO
			cod_req=requerimientoNoProgramadoService.registrarDetalleRqnpAUC(params);
			//cod_req=requerimientoNoProgramadoService.registrarDetalleRqnp(params);
			
			//RECUPERANDO LOS DATOS REGISTRADOS--------------------------------------------------------
			rqnp= requerimientoNoProgramadoService.recuperarRqnpAUC(cod_req);
					
			 //Seteando Detalle
			 listaBienes = (List<Map<String, Object>>) rqnp.getListaBienes(); //obtiene lista de bienes
			 
			 if (listaBienes !=null){
				 if (listaBienes.size() >0 ){
					Map<String, Object> bien =  listaBienes.get(0);
					 
					mapRqnp.put("jcodigo_bien", bien.get("codigoBien"));
					mapRqnp.put("jprecio_unid", bien.get("precioUnid"));
					 
					params.put("cod_bien", bien.get("codigoBien") );
					params.put("cod_req",  bien.get("codigoRqnp"));
					params.put("precio_unid", ((BigDecimal)bien.get("precioUnid")).toString() );
					params.put("cod_dep",rqnp.getCodigoDependencia());
					params.put("anio_ejec",rqnp.getAnioProceso());
						 
					//COMENTADO PARA NO MOSTRAR LISTA DE METAS POR UNIDAD
					//listaMetas = (List<Map<String, Object>>) requerimientoNoProgramadoService.listarRqnpMetas(params);
				 }
			 }
			 
			okRunning = true;
			
			 mapRqnp.put("codigoRqnp", rqnp.getCodigoRqnp());
			 mapRqnp.put("codigoReqNoProgramado", rqnp.getCodigoReqNoProgramado());
			 mapRqnp.put("codigoResposanble", rqnp.getNumeroRegistro());
			 mapRqnp.put("resposanble", rqnp.getNombreSolicitante());
			 mapRqnp.put("dependencia", rqnp.getNombreUOSolicitante());
			 mapRqnp.put("fechaRqnp",new SimpleDateFormat("dd/MM/yyyy HH:mm").format(rqnp.getFechaRqnp()));
			 mapRqnp.put("motivoRqnp",rqnp.getMotivoSolicitud());
			 MaestroPersonalBean personal =registroPersonalService.obtenerPersonaxCodigo(rqnp.getCod_contacto().trim());
			 mapRqnp.put("cod_contacto",rqnp.getCod_contacto());
			 mapRqnp.put("nom_contacto",personal.getNombre_completo());
			 mapRqnp.put("reg_contacto",personal.getNumero_registro());
			 mapRqnp.put("anexo_contacto",rqnp.getAnexo_contacto());
			 mapRqnp.put("cod_necesidad",rqnp.getCod_necesidad());
			 mapRqnp.put("cod_tip_nececidad",rqnp.getCod_tip_nececidad());
			 mapRqnp.put("nom_tip_necesidad",rqnp.getNom_tip_necesidad());
			 mapRqnp.put("codigoUuoo", rqnp.getUuoo());
			 mapRqnp.put("codigoDependencia", rqnp.getCodigoDependencia());
			 mapRqnp.put("anioProceso", rqnp.getAnioProceso());
			 mapRqnp.put("montoRqnp",  rqnp.getMontoRqnp().setScale(2, RoundingMode.HALF_UP).toString());
			 mapRqnp.put("cod_finalidad", rqnp.getCod_finalidad());
			
			 if (rqnp.getCod_proveedor() !=null) {
				parm.put("cod_cont", rqnp.getCod_proveedor());
				parm.put("user", user);
				MaestroContratistasBean contratista=registroPersonalService.recuperarContratista(parm);
				if (contratista!=null){
					mapRqnp.put("cod_cont", contratista.getCod_cont());
					mapRqnp.put("num_ruc", contratista.getNum_ruc());
					mapRqnp.put("raz_social", contratista.getRaz_social());
				}
			 }
			 
			 mapRqnp.put("obs_justificacion", rqnp.getObs_justificacion());
			 mapRqnp.put("obs_plazo_entrega", rqnp.getObs_plazo_entrega());
			 mapRqnp.put("obs_lugar_entrega", rqnp.getObs_lugar_entrega());
			 mapRqnp.put("obs_dir_entrega", rqnp.getObs_dir_entrega());
			 mapRqnp.put("cod_objeto", rqnp.getCod_objeto());
			 lsFinalidad= (List<Map<String, Object>>) requerimientoNoProgramadoService.listaFinalidad();
			 lsNecesidad = (List<Map<String, Object>>) requerimientoNoProgramadoService.listaNecesidad();
			 lsTipoNecesidad = (List<Map<String, Object>>) requerimientoNoProgramadoService.listaTipoNecesidad();
			 
		} 
		catch (ServiceException ex) {
			log.error("Error en RqnpAucDetalleController.registrarRqnpDetalleAUC: " + ex.getMessage());
			request.setAttribute("showErrorMessage", "1");
			request.setAttribute("hideAccionMessage", "1");
			request.setAttribute("excepAttr", ex);
			okRunning=false;
		} 
		catch (Exception ex) {
			log.error("Error en RqnpAucDetalleController.registrarRqnpDetalleAUC: " + ex.getMessage(), ex);
		} 
		finally {
			if (log.isDebugEnabled()){ log.debug("Fin - RqnpAucDetalleController.registrarRqnpDetalleAUC");}
		}
		
		if(!okRunning){
			return new ModelAndView("pagErrorSistema");
		}
		
		return new ModelAndView(ls_pag).addObject("mapRqnp", mapRqnp).addObject("mapCatalogo", mapCatalogo).
		addObject("lsCatalogo",lsCatalogo).addObject("listaBienes",listaBienes).
		addObject("visor", visor).addObject("lsNecesidad", lsNecesidad).
		addObject("lsTipoNecesidad", lsTipoNecesidad).addObject("lsFinalidad", lsFinalidad).addObject("mapDatos_auc",mapDatos_auc);	
	}
	
	/**
	 * AGREGADO - NUEVO: DPORRASC
	 */
	
	public ModelAndView recuperarCodUUOOJson(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - RqnpAucMetasController.recuperarCodUUOOJson");}
		Map<String, Object> parm = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		String user="";
		String num_uuoo="" ;
		
		try {
			
			//MaestroContratistasBean controtatista= new MaestroContratistasBean();
			UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
			user= usuarioBean.getLogin();
			
			num_uuoo= StringUtils.trim(request.getParameter("txtNum_UUOO"));
			
			data=requerimientoAucService.recuperarDependenciaXuuoo(num_uuoo) ;
			//DEVUELVE: {nom_dep=NOMBRE, cod_dep=4844}
			if (data ==null){
				data = new HashMap<String, Object>();
				data.put("nom_dep",messageSource.getMessage("error.registrarRqnp.uuoo" , null, Locale.getDefault()));
				data.put("error", "-1");
				data.put("cod_dep", "-1");
				data.put("mensaje", messageSource.getMessage("error.registrarRqnp.uuoo" , null, Locale.getDefault()));	
			}
		} 
		catch(ServiceException ex){
			log.error("Error en RqnpAucMetasController.recuperarCodUUOOJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en RqnpAucMetasController.recuperarCodUUOOJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - RqnpAucMetasController.recuperarCodUUOOJson");}
		}
		//DEVUELVE: data.cod_dep y data.nom_dep
		return new ModelAndView(jsonView, "data", JsonUtil.toString(data));
	}
	
	
	public void setRequerimientoAucService (RequerimientoAucService requerimientoAucService) {
		this.requerimientoAucService = requerimientoAucService;
	}
}
