package pe.gob.sunat.administracion2.siga.auc.web.controller;

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
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.DependenciaBean;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.MaestroContratistasBean;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.MaestroPersonalBean;
import pe.gob.sunat.recurso2.administracion.siga.registro.service.RegistroPersonalService;
import pe.gob.sunat.recurso2.administracion.siga.web.util.BaseController;
import pe.gob.sunat.tecnologia.menu.bean.UsuarioBean;

public class RqnpAucMetasController extends BaseController{
	private static final Log log = LogFactory.getLog(RqnpAucMetasController.class);

	private RequerimientoNoProgramadoService 	requerimientoNoProgramadoService;
	private RegistroPersonalService 			registroPersonalService;
	private ReloadableResourceBundleMessageSource 		messageSource;
	//private JsonView 							jsonView;
	//private View 								htmlView;
	private RequerimientoAucService				requerimientoAucService;
	
	
	/**
     * Registra las metas RQNP(afectación presupuestal).
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView registrarRqnpMetas(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("Inicio - RqnpAucMetasController.registrarRqnpMetas");}
		
		Map<String, Object> params = new HashMap<String, Object>();
		boolean okRunning = false;
		Map<String, Object> mapRqnp = new HashMap<String, Object>();
		Map<String, Object> mapCatalogo = new HashMap<String, Object>();
		Map<String, Object> lsCatalogo = new HashMap<String, Object>();
		String cod_req		="";
		String anio_ejec	="";
		String codigo_dep	="";
		String cod_bien		="" ;
		String precio_unid	="";
		String user			="";
		List<Map<String, Object>> listaBienes = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> listaMetas = new ArrayList<Map<String,Object>>();
		try {
			UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
			user= usuarioBean.getLogin();
		
			cod_req 				= 	StringUtils.trim(request.getParameter("txtCodigoRqnp"));
			codigo_dep				= 	StringUtils.trim(request.getParameter("jcodigo_dep"));
			anio_ejec 				= 	StringUtils.trim(request.getParameter("janio_ejec"));
			cod_bien 				= 	StringUtils.trim(request.getParameter("jcodigo_bien"));
			precio_unid  			= 	StringUtils.trim(request.getParameter("jprecio_unid"));
			
			String[] txtBien 		=	request.getParameterValues("txtBien");
			String[] txtCantidad 	=	request.getParameterValues("txtCantidad");
			String[] txtMonto		=	request.getParameterValues("txtMonto");
			String[] txtSecuencia	=	request.getParameterValues("txtSecuencia");
		
			params.put("codigo_rqnp", cod_req);
			params.put("codigo_dep", codigo_dep);
			params.put("anio_ejec", anio_ejec);
			params.put("cod_bien",cod_bien );
			
			params.put("txtBien",txtBien);
			params.put("txtCantidad",txtCantidad);
			params.put("txtMonto",txtMonto);
			params.put("txtSecuencia",txtSecuencia);
			params.put("precio_unid",precio_unid);
			params.put("user", user);
			///guardando metas
			
			requerimientoNoProgramadoService.registrarMetasRqnp(params);
	
			RequerimientoNoProgramadoBean rqnp= requerimientoNoProgramadoService.recuperarRqnpAUC(cod_req);
			params.put("rqnp",rqnp);
			
			BigDecimal monto=requerimientoNoProgramadoService.registrarCabMonto(params);
			
			rqnp.setMontoRqnp(monto);
			//Seteando Cabecera

			 mapRqnp.put("codigoRqnp", rqnp.getCodigoRqnp());
			 mapRqnp.put("codigoReqNoProgramado", rqnp.getCodigoReqNoProgramado());
			 mapRqnp.put("codigoResposanble", rqnp.getNumeroRegistro());
			 mapRqnp.put("resposanble", rqnp.getNombreSolicitante());
			 mapRqnp.put("codigoUuoo", rqnp.getUuoo());
			 mapRqnp.put("codigoDependencia", rqnp.getCodigoDependencia());
			 mapRqnp.put("dependencia", rqnp.getNombreUOSolicitante());
			 mapRqnp.put("fechaRqnp", new SimpleDateFormat("dd/MM/yyyy HH:mm").format(rqnp.getFechaRqnp()));
			 mapRqnp.put("motivoRqnp",rqnp.getMotivoSolicitud());
			 mapRqnp.put("codigoDependencia", rqnp.getCodigoDependencia());
			 mapRqnp.put("anioProceso", rqnp.getAnioProceso());
			// mapRqnp.put("montoRqnp",formateador.format( rqnp.getMontoRqnp()));
			 mapRqnp.put("montoRqnp",rqnp.getMontoRqnp());
			 mapRqnp.put("jcodigo_rqnp", cod_req);
			 mapRqnp.put("jcodigo_bien", cod_bien);
			 mapRqnp.put("jprecio_unid", precio_unid);
			 
			 //Seteando Detalle
			  listaBienes = (List<Map<String, Object>>) rqnp.getListaBienes();
			 if (listaBienes != null){
			
				//setando parametros de busueda de metas
				 params.put("cod_req",rqnp.getCodigoRqnp());
				 params.put("cod_bien",cod_bien );
				 params.put("cod_dep",rqnp.getCodigoDependencia());
				 params.put("anio_ejec",rqnp.getAnioProceso());
				 params.put("precio_unid",precio_unid);
				 
				 listaMetas = (List<Map<String, Object>>) requerimientoNoProgramadoService.listarRqnpMetas(params);
			 }
			 okRunning=true; 
		} 
		catch (ServiceException ex) {
			log.error("Error en RqnpAucMetasController.registrarRqnpMetas: " + ex.getMessage());
			request.setAttribute("excepAttr", ex);
			okRunning=false;
		} 
		catch (Exception ex) {
			log.error("Error en RqnpAucMetasController.registrarRqnpMetas: " + ex.getMessage(), ex);
		} 
		finally {
			if (log.isDebugEnabled()){ log.debug("Fin - RqnpAucMetasController.registrarRqnpMetas");}
		}
		
		if (!okRunning) {
	      return new ModelAndView("pagErrorSistema");
	    }
	    return new ModelAndView("formRegistroRqnpMetas").addObject("mapRqnp", mapRqnp).addObject("mapCatalogo", mapCatalogo)
	      .addObject("lsCatalogo", lsCatalogo).addObject("listaBienes", listaBienes).addObject("listaMetas", listaMetas);
	  }
	
	

	/**
     * Registra las metas modificadas RQNP y devulve un  Json cada vez 
     * que el usuario cambia de fila item(afectaci�n presupuestal).
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView registrarRqnpMetasJsonClick(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()) log.debug("Inicio - RqnpAucMetasController.registrarRqnpMetasJsonClick");
		//viene de click en guardar (BOTON DE ABAJO)
		//valida que cantidad de afectacion presupuestal sea mayor a cero (cantidad!=0)
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		boolean okRunning = false;
		boolean okvalidation	= true;
		String cod_req		="";
		String anio_ejec	="";
		String codigo_dep	="";
		
		String cod_bien		="" ;
		String precio_unid	="";
		String user			="";
	
		List<Map<String, Object>> listaMetas = new ArrayList<Map<String,Object>>();
		try {
		
			UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
			user= usuarioBean.getLogin();
		
			cod_req 				= 	StringUtils.trim(request.getParameter("txtCodigoRqnp"));//codigoRqnp seleccionado(click)
			codigo_dep				= 	StringUtils.trim(request.getParameter("jcodigo_dep"));//codigo_dep seleccionado(click)
			anio_ejec 				= 	StringUtils.trim(request.getParameter("janio_ejec"));//año de seleccionado(click)
			
			cod_bien 				= 	StringUtils.trim(request.getParameter("jcodigo_bien"));//codigo de bien seleccionado(click)
			precio_unid  			= 	StringUtils.trim(request.getParameter("jprecio_unid"));//precio  unitario seleccionado(click)
			
			String[] txtBien 		=	request.getParameterValues("txtBien"); //bienes
			String[] txtCantidad 	=	request.getParameterValues("txtCantidad");//cantidad
			String[] txtxCantidad 	=	request.getParameterValues("txtxCantidad");//xcantidad
			String[] txtMonto 		=	request.getParameterValues("txtMonto");//montos
			String[] txtSecuencia	=	request.getParameterValues("txtSecuencia");//secuencia (SECU_FUNC_SFU)
			
			if (txtxCantidad == null){
				okvalidation=false; 
				data.put("mensaje", "Ingrese la cantidad y presione enter");
			}
			if (okvalidation){
				log.debug("txtBien lentgth  2 :"+ txtBien.length);
				//if (txtBien .length >  1 ){
					log.debug("txtxCantidad lentgth :"+ txtxCantidad .length);
					for(int i=0; i< txtxCantidad.length; i++ ){
						log.debug(txtBien[i]+  "  "+ txtSecuencia[i]+ " - "+  txtxCantidad[i]);
						txtxCantidad[i]=txtxCantidad[i].replace(",", "");	
					}
				//}

				params.put("codigo_rqnp", cod_req);
				params.put("codigo_dep", codigo_dep);
				params.put("anio_ejec", anio_ejec);
				params.put("cod_bien",cod_bien);
				
				params.put("txtBien",txtBien);
				params.put("txtxCantidad",txtxCantidad);
				params.put("txtCantidad",txtCantidad);
				params.put("txtMonto",txtMonto);
				params.put("txtSecuencia",txtSecuencia);
				params.put("precio_unid",precio_unid);
				params.put("user", user);
				///guardando metas
				requerimientoNoProgramadoService.registrarMetasRqnp(params);
				
				RequerimientoNoProgramadoBean rqnp= requerimientoNoProgramadoService.recuperarRqnpAUC(cod_req);
				params.put("rqnp",rqnp);
				
				BigDecimal monto=requerimientoNoProgramadoService.registrarCabMonto(params);
				
				//setando parametros de busqueda de metas
				 	params.put("cod_bien", cod_bien);
					params.put("cod_req", cod_req);
					params.put("precio_unid", precio_unid);
					params.put("cod_dep",codigo_dep);
					params.put("anio_ejec",anio_ejec);
					 
					 //(ORIGINAL)listaMetas = (List<Map<String, Object>>) requerimientoNoProgramadoService.listarRqnpMetas(params);
					//(cambiado anterior)listaMetas = (List<Map<String, Object>>) requerimientoAucService.listarAucMetas(params);
					listaMetas = (List<Map<String, Object>>) requerimientoNoProgramadoService.listarRqnpMetasBien(params);
					 
					data.put("listaMetas", JsonUtil.toString( listaMetas));
				 okRunning=true; 
			}
		}
		
		catch (ServiceException ex) {
			log.error("Error en RqnpAucMetasController.registrarRqnpMetasJsonClick: " + ex.getMessage());
		} 
		catch (Exception ex) {
			log.error("Error en RqnpAucMetasController.registrarRqnpMetasJsonClick: " + ex.getMessage(), ex);
		} 
		finally {
			if (log.isDebugEnabled()) {log.debug("Fin - RqnpAucMetasController.registrarRqnpMetasJsonClick");}
		}
		
		if(!okvalidation){
			data.put("error", "-1");
			return new ModelAndView(jsonView, "data", data);
		}
		if(!okRunning  ){
			data.put("error", "-1");
			
			data.put("mensaje", messageSource.getMessage("error.registrarAtencion.generico" , null, Locale.getDefault()));		
			return new ModelAndView(jsonView, "data", data);
		}
		
		return new ModelAndView(jsonView, "data", JsonUtil.toString(data)) ;
	}

	
	/**
     * Registra las metas modificadas RQNP y devulve un  Json (afectación presupuestal).
     * @param  request
     * @param  response 
     * @return ModelAndView
     **/
	
	public ModelAndView registrarRqnpMetasJson(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()) {log.debug("Inicio - RqnpAucMetasController.registrarRqnpMetasJson");}
		
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		List<Map<String, Object>> listaMetas= new ArrayList<Map<String,Object>>();
		boolean okRunning = false;
		boolean okvalidation=true;
		String cod_req		="";
		String anio_ejec	="";
		String codigo_dep	="";
		String cod_bien		=""; 
		String precio_unid	="";
		String cod_bien_old	="" ;
		String precio_unid_old="";
		String user			="";

		try {
			UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
			user= usuarioBean.getLogin();
		
			cod_req 				= 	StringUtils.trim(request.getParameter("txtCodigoRqnp"));
			codigo_dep				= 	StringUtils.trim(request.getParameter("jcodigo_dep"));
			anio_ejec 				= 	StringUtils.trim(request.getParameter("janio_ejec"));
			cod_bien 				= 	StringUtils.trim(request.getParameter("jcodigo_bien"));
			precio_unid  			= 	StringUtils.trim(request.getParameter("jprecio_unid"));
			
			cod_bien_old			= 	StringUtils.trim(request.getParameter("jcodigo_bien_old"));
			precio_unid_old			= 	StringUtils.trim(request.getParameter("jprecio_unid_old"));
			String[] txtxCantidad 	=	request.getParameterValues("txtxCantidad");
			String[] txtBien 		=	request.getParameterValues("txtBien");
			String[] txtCantidad 	=	request.getParameterValues("txtCantidad");
			String[] txtMonto 		=	request.getParameterValues("txtMonto");
			String[] txtSecuencia	=	request.getParameterValues("txtSecuencia");
			log.debug("lentgth txtBien :" + txtBien.length + " | lentgth txtCantidad " + txtCantidad.length + " | lentgth txtSecuencia " + txtSecuencia.length);
			
			if (txtxCantidad == null){
				okvalidation=false; 
				data.put("mensaje", "Ingrese la cantidad y presione enter");
			}
			if (okvalidation){	
				log.debug("txtxCantidad lentgth :"+ txtxCantidad .length);
					
				for(int i=0; i< txtxCantidad.length; i++ ){
					log.debug(txtBien[i]+  "  "+ txtSecuencia[i]+ " - "+  txtxCantidad[i]);
					txtxCantidad[i]=txtxCantidad[i].replace(",", "");
				}
					
				params.put("codigo_rqnp", cod_req);
				params.put("codigo_dep", codigo_dep);
				params.put("anio_ejec", anio_ejec);
				params.put("cod_bien",cod_bien_old );
				params.put("txtBien",txtBien);
				params.put("txtCantidad",txtCantidad);
				params.put("txtxCantidad",txtxCantidad);
				params.put("txtMonto",txtMonto);
				params.put("txtSecuencia",txtSecuencia);
				params.put("precio_unid",precio_unid_old);
				params.put("user", user);
				///GUARDANDO METAS
				requerimientoNoProgramadoService.registrarMetasRqnp(params);
					
				RequerimientoNoProgramadoBean rqnp= requerimientoNoProgramadoService.recuperarRqnpAUC(cod_req);
				params.put("rqnp",rqnp);
					
				BigDecimal monto=requerimientoNoProgramadoService.registrarCabMonto(params);
				
				params.put("cod_bien", cod_bien);
				params.put("cod_req", cod_req);
				params.put("precio_unid", precio_unid);
				params.put("cod_dep",codigo_dep);
				params.put("anio_ejec",anio_ejec);
					
				//listaMetas = (List<Map<String, Object>>) requerimientoNoProgramadoService.listarProyProdAccion(params);
				//listaMetas = (List<Map<String, Object>>) requerimientoNoProgramadoService.listarRqnpMetas(params);
				listaMetas = (List<Map<String, Object>>) requerimientoNoProgramadoService.listarRqnpMetasBien(params);
				
				data.put("listaMetas", JsonUtil.toString( listaMetas));
				okRunning=true; 
			}
		} 
		
		catch (ServiceException ex) {
			log.error("Error en RqnpAucMetasController.registrarRqnpMetasJson: " + ex.getMessage());
		} 
		catch (Exception ex) {
			log.error("Error en RqnpAucMetasController.registrarRqnpMetasJson: " + ex.getMessage(), ex);
		} 
		finally {
			if (log.isDebugEnabled()){ log.debug("Fin - RqnpDetalleController.registrarRqnpMetasJson");}
		}
		if(!okvalidation){
			data.put("error", "-1");
			return new ModelAndView(jsonView, "data", data);
		}else{
			if(!okRunning  ){
				data.put("error", "-1");
				data.put("mensaje", messageSource.getMessage("error.registrarAtencion.generico" , null, Locale.getDefault()));		
				return new ModelAndView(jsonView, "data", data);
			}
		}
		return new ModelAndView(jsonView, "data", JsonUtil.toString(data)) ;
	}
	
	
	/**
     * Recupera las metas afectadas y no afectadas de un  item del RQNP .
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView recuperarMetasJson(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - RqnpAucMetasController.recuperarMetasJson");}
		Map<String, Object>  params = new HashMap<String, Object> ();
		Map<String, Object>  data = new HashMap<String, Object> ();
		List<Map<String, Object>> listaMetas = new ArrayList<Map<String,Object>>();
		try {
			String codigo_bien = StringUtils.trim(request.getParameter("jcodigo_bien")); //CON DOJO
			//SIGUIENTE Y RECUPERAR
			String codigo_rqnp 	= StringUtils.trim(request.getParameter("txtCodigoRqnp")); //hidden
			String codigo_dep 	= StringUtils.trim(request.getParameter("jcodigo_dep")); //hidden
			String anio_ejec 	= StringUtils.trim(request.getParameter("janio_ejec")); //hidden
			String precio_unid 	= StringUtils.trim(request.getParameter("jprecio_unid")); //CON DOJO
			
			params.put("cod_bien", codigo_bien);
			params.put("cod_req", codigo_rqnp);
			params.put("precio_unid", precio_unid);
			params.put("cod_dep",codigo_dep);
			params.put("anio_ejec",anio_ejec);
			 
			listaMetas = (List<Map<String, Object>>) requerimientoNoProgramadoService.listarRqnpMetas(params);
			data.put("listaMetas", JsonUtil.toString(listaMetas));
		} 
		catch(ServiceException ex){
			log.error("Error en RqnpAucMetasController.recuperarMetasJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en RqnpAucMetasController.recuperarMetasJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()) {log.debug("Fin - RqnpAucMetasController.recuperarMetas");}
		}
		return new ModelAndView(jsonView, "data", JsonUtil.toString(data)) ;
	}
	

	/**
     * Valida que los item del Rqnp tengan almenos una 
     * meta afectada .
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView validaMetasBienJson(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - RqnpAucMetasController.validaMetasBienJson");}

		Map<String, Object> data = new HashMap<String, Object>();
		String valida="";
		try {
			String codigo_rqnp = StringUtils.trim(request.getParameter("txtCodigoRqnp"));
			valida=requerimientoNoProgramadoService.validaMetasBienes(codigo_rqnp);
			 data.put("validaMeta",valida);
		} 
		catch(ServiceException ex){
			log.error("Error en RqnpAucMetasController.validaMetasBienJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en RqnpAucMetasController.validaMetasBienJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - RqnpAucMetasController.validaMetasBienJson");}
		}
		return new ModelAndView(jsonView, "data", JsonUtil.toString(data)) ;
	}
	/**
     * Recupera solo las metas afectadas de un  item del RQNP  .
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView recuperarMetasVistaJson(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - RqnpAucMetasController.recuperarMetasVistaJson");}
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		List<Map<String, Object>> listaMetas = new ArrayList<Map<String,Object>>();
		try {
			String codigo_bien = StringUtils.trim(request.getParameter("jcodigo_bien"));
			String codigo_rqnp 				= 	StringUtils.trim(request.getParameter("txtCodigoRqnp"));
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
			log.error("Error en RqnpAucMetasController.recuperarMetasVistaJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en RqnpAucMetasController.recuperarMetasVistaJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()) {log.debug("Fin - RqnpAucMetasController.recuperarMetasVistaJson");}
		}
		return new ModelAndView(jsonView, "data", JsonUtil.toString(data)) ;
	}

	/**
     * Envia un Requerimiento no Programado
     * al Jefe inmediado.
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView enviarRqnp(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()) {log.debug("Inicio - RqnpController.enviarRqnp");}
		List<Map<String, Object>> lsRqnp= new ArrayList<Map<String,Object>>();
		Map<String, Object> params = new HashMap<String, Object>();
		boolean okRunning = false;
		boolean isJefe=false;
		String user="";
		String ls_mes=null;
		String ls_anio="";
		Calendar fecha =  Calendar.getInstance();
		ls_anio= String.valueOf( fecha.get(Calendar.YEAR)) ;
		
		try {
			MaestroPersonalBean  maestroPersonal=  (MaestroPersonalBean) WebUtils.getSessionAttribute(request, "maestroPersonalBean");
			UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
			user= usuarioBean.getLogin();
			if (maestroPersonal!=null){
			String codigo_rqnp = StringUtils.trim(request.getParameter("txtCodigoRqnp"));
			isJefe=registroPersonalService.isJefeInmediato(maestroPersonal.getCodigoDependencia(), maestroPersonal.getCodigoEmpleado());
			params.put("codigo_rqnp", codigo_rqnp);
			params.put("user", user);
			params.put("isJefe", isJefe);
			//REGISTRA EL ENVIO DE RQNP
			requerimientoNoProgramadoService.registrarEnviarRqnp(params);
			okRunning = true;
			requerimientoNoProgramadoService.EnviarMailRqnp(params);
			lsRqnp = (List<Map<String, Object>>) requerimientoNoProgramadoService.listarRqnpUsuario(ls_anio, "01", maestroPersonal.getCodigoSede(), maestroPersonal.getCodigoEmpleado(),ls_mes,"AUC");
			}else{
				Exception ex = new Exception("Error al Obtener Datos del Usuario");
				request.setAttribute("excepAttr", ex);
				return new ModelAndView("pagErrorSistema");
			}
		} 
		catch (ServiceException ex) {
			log.error("Error en RqnpController.enviarRqnp: " + ex.getMessage());
		} 
		catch (Exception ex) {
			log.error("Error en RqnpController.enviarRqnp: " + ex.getMessage(), ex);
		} 
		finally {
			if (log.isDebugEnabled()){ log.debug("Fin - RqnpController.enviarRqnp");}
		}
		if(!okRunning){
			Exception ex = new Exception("error.registrarAtencion.generico");
			request.setAttribute("excepAttr", ex);
			return new ModelAndView("pagErrorSistema");
		}
		return new ModelAndView("formBandejaRqnp", "lsrqnp", lsRqnp);
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
	public RegistroPersonalService getRegistroPersonalService() {
		return registroPersonalService;
	}
	public void setRegistroPersonalService(
			RegistroPersonalService registroPersonalService) {
		this.registroPersonalService = registroPersonalService;
	}
	
	
	/**
     * Registra los item del RQNP.
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	
	public ModelAndView registrarRqnpDetalle(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("Inicio - RqnpAucMetasController.registrarRqnpDetalle");}
		
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
		visor	= StringUtils.trim(request.getParameter("txtvisor"));
		try {
			UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
			MaestroPersonalBean  maestroPersonal=  (MaestroPersonalBean) WebUtils.getSessionAttribute(request, "maestroPersonalBean");
			
			String motivoSol = StringUtils.trim(request.getParameter("jtxtMotivo"));
			String cod_contacto = StringUtils.trim(request.getParameter("txtCod_contacto"));
			String anexo_contacto = StringUtils.trim(request.getParameter("txtAnexo_contacto")); // (MODIFICADO DPORRASC)jtxtAnexo_contacto
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
			String anio_ejec 			= StringUtils.trim(request.getParameter("janio_ejec"));
			
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
			mapRqnp.put("cod_objeto", rqnp.getCod_objeto());
			
			//REGISTRANDO EL RQNP---------------------------------------------------------------------
			cod_req=requerimientoNoProgramadoService.registrarDetalleRqnp(params);
			
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
						 
					listaMetas = (List<Map<String, Object>>) requerimientoNoProgramadoService.listarRqnpMetas(params);
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
			 
			 lsFinalidad= (List<Map<String, Object>>) requerimientoNoProgramadoService.listaFinalidad();
			 lsNecesidad = (List<Map<String, Object>>) requerimientoNoProgramadoService.listaNecesidad();
			 lsTipoNecesidad = (List<Map<String, Object>>) requerimientoNoProgramadoService.listaTipoNecesidad();
			 
		} 
		catch (ServiceException ex) {
			log.error("Error en RqnpAucMetasController.registrarRqnpDetalle: " + ex.getMessage());
			request.setAttribute("excepAttr", ex);
			okRunning=false;
		} 
		catch (Exception ex) {
			log.error("Error en RqnpAucMetasController.registrarRqnpDetalle: " + ex.getMessage(), ex);
		} 
		finally {
			if (log.isDebugEnabled()){ log.debug("Fin - RqnpAucMetasController.registrarRqnpDetalle");}
		}
		
		if(!okRunning){
			return new ModelAndView("pagErrorSistema");
		}
		
		return new ModelAndView(ls_pag).addObject("mapRqnp", mapRqnp).addObject("mapCatalogo", mapCatalogo).
			addObject("lsCatalogo",lsCatalogo).addObject("listaBienes",listaBienes).
			addObject("listaMetas",listaMetas).addObject("visor", visor).addObject("lsNecesidad", lsNecesidad).
			addObject("lsTipoNecesidad", lsTipoNecesidad).addObject("lsFinalidad", lsFinalidad);	
	}
	
//INICIO DPORRASC
	/**
	 * @author dporrasc
     * Carga la pagina inicial de afectaci�n presupuestal(metas)
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView modificarRqnpMetasAUC(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("Inicio -  RqnpAucMetasController.modificarRqnpMetas");}
		//mapRqnp
		
		 Map<String, Object> mapRqnp 		= new HashMap<String, Object>();
		 Map<String, Object> mapCatalogo 	= new HashMap<String, Object>();
		 Map<String, Object> lsCatalogo 	= new HashMap<String, Object>();
		 Map<String, Object> params 		= new HashMap<String, Object>();
		 Map<String, Object> mapDatos_auc = new HashMap<String, Object>();
		 String cod_req	="";
		 String visor	="";
		
		 List<Map<String, Object>> listaMetas = new ArrayList<Map<String,Object>>();
		try {
			cod_req 	= StringUtils.trim(request.getParameter("txtCodigoRqnp"));
			visor		= StringUtils.trim(request.getParameter("txtvisor"));
			mapDatos_auc = (HashMap<String, Object>) WebUtils.getSessionAttribute(request, "mapDatos_auc");
			log.debug("tipo_auc" + mapDatos_auc.get("tipo_auc") );
				
			RequerimientoNoProgramadoBean rqnp= requerimientoNoProgramadoService.recuperarRqnpAUC(cod_req);

			//Seteando Cabecera
			 mapRqnp.put("codigoRqnp", rqnp.getCodigoRqnp());
			 mapRqnp.put("codigoReqNoProgramado", rqnp.getCodigoReqNoProgramado());
			 mapRqnp.put("codigoResposanble", rqnp.getNumeroRegistro());
			 mapRqnp.put("resposanble", rqnp.getNombreSolicitante());
			 mapRqnp.put("codigoUuoo", rqnp.getUuoo());
			 mapRqnp.put("dependencia", rqnp.getNombreUOSolicitante());
			 mapRqnp.put("fechaRqnp",new SimpleDateFormat("dd/MM/yyyy HH:mm").format(rqnp.getFechaRqnp()));
			 mapRqnp.put("motivoRqnp",rqnp.getMotivoSolicitud());
			 mapRqnp.put("codigoDependencia", rqnp.getCodigoDependencia());
			 mapRqnp.put("anioProceso", rqnp.getAnioProceso());
			 mapRqnp.put("montoRqnp",  rqnp.getMontoRqnp().setScale(2, RoundingMode.HALF_UP).toString());
			 mapRqnp.put("cod_objeto", rqnp.getCod_objeto());
			 //Seteando Detalle
			 List<Map<String, Object>> listaBienes = (List<Map<String, Object>>) rqnp.getListaBienes();
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
					listaMetas = (List<Map<String, Object>>) requerimientoNoProgramadoService.listarRqnpMetas(params);
				 }
			 }
			 return new ModelAndView("formRegistroRqnpMetasAUC").addObject("mapRqnp", mapRqnp).addObject("mapCatalogo", mapCatalogo).
			addObject("lsCatalogo",lsCatalogo).addObject("listaBienes",listaBienes).addObject("listaMetas",listaMetas)
			.addObject("visor", visor).addObject("mapDatos_auc",mapDatos_auc);
		} 
		catch(ServiceException ex){
			log.error("Error en  RqnpAucMetasController.modificarRqnpMetas: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en  RqnpAucMetasController.modificarRqnpMetas: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()) {log.debug("Fin -  RqnpAucMetasController.modificarRqnpMetas");}
		}
	}
	
	
	
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
			//DEVUELVE: {nom_dep=NOMBRE, cod_dep=4844,  cod_plaza=??,}
			if (data ==null){
				data = new HashMap<String, Object>();
				data.put("nom_dep",messageSource.getMessage("error.registrarRqnp.uuoo" , null, Locale.getDefault()));
				data.put("error", "-1");
				data.put("cod_dep", "-1");
				data.put("mensaje", messageSource.getMessage("error.registrarRqnp.uuoo" , null, Locale.getDefault()));
				//AGREGADO
				data.put("cod_plaza", "-1");
				data.put("mensaje", messageSource.getMessage("error.registrarRqnp.uuoo" , null, Locale.getDefault()));
				data.put("uuoo", "-1");
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
	
	//BUSCAR METAS PROYECTOS JSON
	/**
	 * @author dporrasc
	 * Busca Metas Proyectos que lo selecciona una AUC
	 */
	public ModelAndView buscarMetasProyectosJson(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - RqnpAucMetasController.buscarMetasProyectosJson");}
		Map<String, Object> mapMetasProyectos = new HashMap<String, Object> ();
		Map<String, Object> data = new HashMap<String, Object> ();
		Map<String, Object> params = new HashMap<String, Object>();
		
		Map<String, Object> mapRqnp 	= new HashMap<String, Object>();
		List<Map<String, Object>> listaBienes = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> listaMetasProyectos = new ArrayList<Map<String,Object>>();

		String anio_ejec="";
		String cod_bien="";
		String precio_bien="";
		String cod_req="";
		
		try {
			cod_bien=  StringUtils.trim(request.getParameter("valcodigoBien"));
			precio_bien=  StringUtils.trim(request.getParameter("valprecioUnid"));
			cod_req = StringUtils.trim(request.getParameter("txtCodigoRqnp"));

			//String parm = StringUtils.trim(request.getParameter("jParametro"));
			//String tipo = StringUtils.trim(request.getParameter("jtipoConsulta"));
			
			Calendar fecha =  Calendar.getInstance();
			RequerimientoNoProgramadoBean rqnp= requerimientoNoProgramadoService.recuperarRqnpCabAUC(cod_req);
			
			//REGISTRANDO EL RQNP---------------------------------------------------------------------
			cod_req=requerimientoNoProgramadoService.registrarDetalleRqnp(params);
			
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
						 
					listaMetasProyectos = (List<Map<String, Object>>) requerimientoNoProgramadoService.listarRqnpMetas(params);
				 }
			 }
			
			data.put("lsMetasProyectos", JsonUtil.toString(listaMetasProyectos));
			data.put("mapMetasProyectos", JsonUtil.toString(mapMetasProyectos));
			
		} 
		catch(ServiceException ex){
			log.error("Error en RqnpAucMetasController.buscarMetasProyectosJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en RqnpAucMetasController.buscarMetasProyectosJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - RqnpAucMetasController.buscarMetasProyectosJson");}
		}
		return new ModelAndView(jsonView, "data", JsonUtil.toString(data)) ;
	}
	
	/**
     * Recupera las metas afectadas y no afectadas de un  item del RQNP PERO DE UN AUC.
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	//FUE AGREGADO PARA MOSTRAR DETALLES DE UN NUEVO XML (auc_metas.xml)
	//ANTES: recuperarMetasJsonAUC
	public ModelAndView recuperarProyProdMetasJsonAUC(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - RqnpAucMetasController.recuperarProyProdMetasJsonAUC");}
		Map<String, Object>  params = new HashMap<String, Object> ();
		Map<String, Object>  data = new HashMap<String, Object> ();
		List<Map<String, Object>> listaMetasProyectos = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> listaMetasProductos = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> listaMetasAcciones = new ArrayList<Map<String,Object>>();
		
		List<Map<String, Object>> listaParametros = new ArrayList<Map<String,Object>>();
		try {
			String codigo_bien = StringUtils.trim(request.getParameter("jcodigo_bien_old"));
			String codigo_rqnp 	= StringUtils.trim(request.getParameter("txtCodigoRqnp"));
			//String codigo_dep = StringUtils.trim(request.getParameter("jcodigo_dep"));
			String codigo_dep 	= StringUtils.trim(request.getParameter("txtNum_UUOO"));
			String anio_ejec 	= StringUtils.trim(request.getParameter("janio_ejec"));
			String precio_unid 	= StringUtils.trim(request.getParameter("jprecio_unid_old"));
			
			params.put("cod_bien", codigo_bien);
			params.put("cod_req", codigo_rqnp);
			params.put("precio_unid", precio_unid);
			params.put("cod_dep",codigo_dep);
			params.put("anio_ejec",anio_ejec);
			 
			//listaMetas = (List<Map<String, Object>>) requerimientoNoProgramadoService.listarMetasProyectos(params);
			listaMetasProyectos = (List<Map<String, Object>>) requerimientoNoProgramadoService.listarProyProdAccion(params);
			
			//agrega datos a params: Codigo de proyecto
			for (Map<String, Object> lsMapMetasProyectos : listaMetasProyectos) {
			    for (Map.Entry<String, Object> entry : lsMapMetasProyectos.entrySet()) {
			       params.put(entry.getKey(),entry.getValue());
			    }
			}

			data.put("listaMetasProyectos", JsonUtil.toString(listaMetasProyectos));

			
		} 
		catch(ServiceException ex){
			log.error("Error en RqnpAucMetasController.recuperarProyProdMetasJsonAUC: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en RqnpAucMetasController.recuperarProyProdMetasJsonAUC: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()) {log.debug("Fin - RqnpAucMetasController.recuperarProyProdMetasJsonAUC");}
		}
		return new ModelAndView(jsonView, "data", JsonUtil.toString(data)) ;
	}
	
public RequerimientoAucService getRequerimientoAucService() {
	return requerimientoAucService;
}

public void setRequerimientoAucService(
		RequerimientoAucService requerimientoAucService) {
	this.requerimientoAucService = requerimientoAucService;
}


//INICIO: DPORRASC

/**
 * Recupera las metas afectadas y no afectadas de un  item del RQNP .
 * @param  request
 * @param  response 
 * @return ModelAndView 
 **/
//UTILIZADO PARA DEVOLVER METAS SEGUN ITEM SELECCIONADO
public ModelAndView recuperarMetasJsonAUC(HttpServletRequest request, HttpServletResponse response) {
	if (log.isDebugEnabled()){ log.debug("debug Inicio - RqnpAucMetasController.recuperarMetasJsonAUC");}
	Map<String, Object>  params = new HashMap<String, Object> ();
	Map<String, Object>  data = new HashMap<String, Object> ();
	List<Map<String, Object>> listaMetas = new ArrayList<Map<String,Object>>();
	try {
		String codigo_bien = StringUtils.trim(request.getParameter("jcodigo_bien")); //CON DOJO
		//SIGUIENTE Y RECUPERAR
		String codigo_rqnp 	= StringUtils.trim(request.getParameter("txtCodigoRqnp")); //hidden
		String codigo_dep 	= StringUtils.trim(request.getParameter("jcodigo_dep")); //hidden
		String anio_ejec 	= StringUtils.trim(request.getParameter("janio_ejec")); //hidden
		String precio_unid 	= StringUtils.trim(request.getParameter("jprecio_unid")); //CON DOJO
		
		params.put("cod_bien", codigo_bien);
		params.put("cod_req", codigo_rqnp);
		params.put("precio_unid", precio_unid);
		params.put("cod_dep",codigo_dep);
		params.put("anio_ejec",anio_ejec);
		
		//listarAucMetas
		//listaMetas = (List<Map<String, Object>>) requerimientoAucService.listarAucMetas(params);
		listaMetas = (List<Map<String, Object>>) requerimientoNoProgramadoService.listarRqnpMetasBien(params);
		data.put("listaMetas", JsonUtil.toString(listaMetas));
	} 
	catch(ServiceException ex){
		log.error("Error en RqnpAucMetasController.recuperarMetasJsonAUC: " + ex.getMessage());
		throw new ServiceException(this, ex);
	}
	catch(Exception ex){
		log.error("Error en RqnpAucMetasController.recuperarMetasJsonAUC: " + ex.getMessage(), ex);
		throw new ServiceException(this, ex);		   
	}
	finally{
		if (log.isDebugEnabled()) {log.debug("Fin - RqnpAucMetasController.recuperarMetasJsonAUC");}
	}
	return new ModelAndView(jsonView, "data", JsonUtil.toString(data)) ;
}


/**
 * @author dporrasc
 * Registra las metas RQNP(afectaci�n presupuestal).
 * @param  request
 * @param  response 
 * @return ModelAndView 
 **/
public ModelAndView registrarRqnpMetasPopup(HttpServletRequest request, HttpServletResponse response) {
	if (log.isDebugEnabled()){ log.debug("Inicio - RqnpDetalleController.registrarRqnpMetas");}
	
	Map<String, Object>  data = new HashMap<String, Object> (); //AGREGADO (DPORRASC)
	Map<String, Object> params = new HashMap<String, Object>();
	boolean okRunning = false;
	Map<String, Object> mapRqnp = new HashMap<String, Object>();
	Map<String, Object> mapCatalogo = new HashMap<String, Object>();
	Map<String, Object> lsCatalogo = new HashMap<String, Object>();
	String cod_req		="";
	String anio_ejec	="";
	String codigo_dep	="";
	String cod_bien		="" ;
	String precio_unid	="";
	String user			="";
	List<Map<String, Object>> listaBienes = new ArrayList<Map<String,Object>>();
	List<Map<String, Object>> listaMetas = new ArrayList<Map<String,Object>>();
	try {
	
		UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
		user= usuarioBean.getLogin();

		cod_req 				= 	StringUtils.trim(request.getParameter("txtCodigoRqnp"));
		//codigo_dep			= 	StringUtils.trim(request.getParameter("jcodigo_dep"));
		codigo_dep				= 	StringUtils.trim(request.getParameter("txtNum_UUOO"));
		anio_ejec 				= 	StringUtils.trim(request.getParameter("janio_ejec"));
		cod_bien 				= 	StringUtils.trim(request.getParameter("jcodigo_bien"));
		precio_unid  			= 	StringUtils.trim(request.getParameter("jprecio_unid"));
		
		String[] txtBien 		=	request.getParameterValues("a_txtBien");
		String[] txtCantidad 	=	request.getParameterValues("a_txtCantidad");
		String[] txtMonto		=	request.getParameterValues("a_txtMonto");
		String[] txtSecuencia	=	request.getParameterValues("a_txtSecuencia");
		
		
		if (txtCantidad ==null){
			log.debug("cantidad null: "   );
		}else{
			log.debug("cantidad length:" + txtCantidad.length);
		}
		params.put("codigo_rqnp", cod_req);
		params.put("codigo_dep", codigo_dep);
		params.put("codigo_bien",cod_bien );
		params.put("anio_ejec", anio_ejec);
		params.put("txtBien",txtBien);
		params.put("txtCantidad",txtCantidad);
		params.put("txtxCantidad",txtCantidad);
		params.put("txtMonto",txtMonto);
		params.put("txtSecuencia",txtSecuencia);
		params.put("precio_unid",precio_unid);
		params.put("user", user);
		params.put("flagExcel", "N");
		///guardando metas
		
		requerimientoNoProgramadoService.registrarMetasRqnpAUC(params);

		RequerimientoNoProgramadoBean rqnp= requerimientoNoProgramadoService.recuperarRqnpAUC(cod_req);
		params.put("rqnp",rqnp);
		
		BigDecimal monto=requerimientoNoProgramadoService.registrarCabMonto(params);
		
		rqnp.setMontoRqnp(monto);
		//Seteando Cabecera

		 mapRqnp.put("codigoRqnp", rqnp.getCodigoRqnp());
		 mapRqnp.put("codigoReqNoProgramado", rqnp.getCodigoReqNoProgramado());
		 mapRqnp.put("codigoResposanble", rqnp.getNumeroRegistro());
		 mapRqnp.put("resposanble", rqnp.getNombreSolicitante());
		 mapRqnp.put("codigoUuoo", rqnp.getUuoo());
		 mapRqnp.put("codigoDependencia", rqnp.getCodigoDependencia());
		 mapRqnp.put("dependencia", rqnp.getNombreUOSolicitante());
		 mapRqnp.put("fechaRqnp",new SimpleDateFormat("dd/MM/yyyy HH:mm").format(rqnp.getFechaRqnp()));
		 mapRqnp.put("motivoRqnp",rqnp.getMotivoSolicitud());
		 mapRqnp.put("codigoDependencia", rqnp.getCodigoDependencia());
		 mapRqnp.put("anioProceso", rqnp.getAnioProceso());
		// mapRqnp.put("montoRqnp",formateador.format( rqnp.getMontoRqnp()));
		 mapRqnp.put("montoRqnp",rqnp.getMontoRqnp());
		 mapRqnp.put("jcodigo_rqnp", cod_req);
		 mapRqnp.put("jcodigo_bien", cod_bien);
		 mapRqnp.put("jprecio_unid", precio_unid);
		
		//setando parametros de busueda de metas
		 params.put("cod_req",rqnp.getCodigoRqnp());
		 params.put("cod_bien",cod_bien );
		 params.put("cod_dep",codigo_dep);
		 params.put("anio_ejec",rqnp.getAnioProceso());
		 params.put("precio_unid",precio_unid);
		 
		 //MODIFICADO(DPORRASC)
		 listaMetas = (List<Map<String, Object>>) requerimientoNoProgramadoService.listarRqnpMetasBien(params);
		 data.put("listaMetas", JsonUtil.toString(listaMetas));
		 okRunning=true; 
	} 
	catch (ServiceException ex) {
		log.error("Error en RqnpDetalleController.registrarRqnpMetas: " + ex.getMessage());
		request.setAttribute("excepAttr", ex);
		okRunning=false;
	} 
	catch (Exception ex) {
		log.error("Error en RqnpDetalleController.registrarRqnpMetas: " + ex.getMessage(), ex);
	} 
	finally {
		if (log.isDebugEnabled()){ log.debug("Fin - RqnpDetalleController.registrarRqnpMetas");}
	}
	
	if(!okRunning){
		return new ModelAndView("pagErrorSistema");
	}else{
		return new ModelAndView(jsonView, "data", JsonUtil.toString(data)) ;
	}
}

/**
 * Registra las metas RQNP(afectaci�n presupuestal).
 * @param  request
 * @param  response 
 * @return ModelAndView 
 **/
public ModelAndView replicarRqnpMetasPopup(HttpServletRequest request, HttpServletResponse response) {
	if (log.isDebugEnabled()){ log.debug("Inicio - RqnpDetalleController.registrarRqnpMetas");}
	
	Map<String, Object>  data_sp = new HashMap<String, Object> (); //AGREGADO (DPORRASC)
	Map<String, Object>  data = new HashMap<String, Object> (); //AGREGADO (DPORRASC)
	Map<String, Object> params = new HashMap<String, Object>();
	boolean okRunning = false;
	Map<String, Object> mapRqnp = new HashMap<String, Object>();
	Map<String, Object> mapCatalogo = new HashMap<String, Object>();
	Map<String, Object> lsCatalogo = new HashMap<String, Object>();
	String cod_req		="";
	String anio_ejec	="";
	String codigo_dep	="";
	String cod_bien		="" ;
	String precio_unid	="";
	String user			="";
	List<Map<String, Object>> listaBienes = new ArrayList<Map<String,Object>>();
	List<Map<String, Object>> listaMetas = new ArrayList<Map<String,Object>>();
	try {
	
		UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
		user= usuarioBean.getLogin();

		cod_req 				= 	StringUtils.trim(request.getParameter("txtCodigoRqnp"));
		//codigo_dep			= 	StringUtils.trim(request.getParameter("jcodigo_dep"));
		codigo_dep				= 	StringUtils.trim(request.getParameter("txtNum_UUOO"));
		anio_ejec 				= 	StringUtils.trim(request.getParameter("janio_ejec"));
		cod_bien 				= 	StringUtils.trim(request.getParameter("jcodigo_bien"));
		precio_unid  			= 	StringUtils.trim(request.getParameter("jprecio_unid"));

		params.put("cod_rqnp", cod_req);
		params.put("codigo_dep", codigo_dep);
		params.put("cod_bien",cod_bien );
		params.put("anio_ejec", anio_ejec);  
		params.put("precio_unid", precio_unid);
		params.put("user", user);
		///guardando metas

		RequerimientoNoProgramadoBean rqnp= requerimientoNoProgramadoService.recuperarRqnpAUC(cod_req);
		params.put("rqnp",rqnp);

		 mapRqnp.put("codigoRqnp", rqnp.getCodigoRqnp());
		 mapRqnp.put("codigoReqNoProgramado", rqnp.getCodigoReqNoProgramado());
		 mapRqnp.put("codigoResposanble", rqnp.getNumeroRegistro());
		 mapRqnp.put("resposanble", rqnp.getNombreSolicitante());
		 mapRqnp.put("codigoUuoo", rqnp.getUuoo());
		 mapRqnp.put("codigoDependencia", rqnp.getCodigoDependencia());
		 mapRqnp.put("dependencia", rqnp.getNombreUOSolicitante());
		 mapRqnp.put("fechaRqnp",new SimpleDateFormat("dd/MM/yyyy HH:mm").format(rqnp.getFechaRqnp()));
		 mapRqnp.put("motivoRqnp",rqnp.getMotivoSolicitud());
		 mapRqnp.put("codigoDependencia", rqnp.getCodigoDependencia());
		 mapRqnp.put("anioProceso", rqnp.getAnioProceso());
		// mapRqnp.put("montoRqnp",formateador.format( rqnp.getMontoRqnp()));
		 mapRqnp.put("montoRqnp",rqnp.getMontoRqnp());
		 mapRqnp.put("jcodigo_rqnp", cod_req);
		 mapRqnp.put("jcodigo_bien", cod_bien);
		 mapRqnp.put("jprecio_unid", precio_unid);
		 
		 data_sp= requerimientoNoProgramadoService.spReplicarMetas(params);
		 
		//MODIFICADO(DPORRASC)
		 listaMetas = (List<Map<String, Object>>) requerimientoNoProgramadoService.listarRqnpMetasBien(params);
		 data.put("listaMetas", JsonUtil.toString(listaMetas));
		 
		 okRunning=true;
	} 
	catch (ServiceException ex) {
		log.error("Error en RqnpDetalleController.registrarRqnpMetas: " + ex.getMessage());
		
		request.setAttribute("showErrorMessage", "1");
		request.setAttribute("hideAccionMessage", "1");
		request.setAttribute("excepAttr", ex);
		okRunning=false;
	} 
	catch (Exception ex) {
		log.error("Error en RqnpDetalleController.registrarRqnpMetas: " + ex.getMessage(), ex);
	} 
	finally {
		if (log.isDebugEnabled()){ log.debug("Fin - RqnpDetalleController.registrarRqnpMetas");}
	}
	
	if(!okRunning){
		return new ModelAndView("pagErrorSistema");
	}
		
	return new ModelAndView(jsonView, "data", JsonUtil.toString(data)) ;
	}

/**
 * Valida que los item del Rqnp tengan almenos una 
 * meta afectada .
 * @param  request
 * @param  response 
 * @return ModelAndView 
 **/
public ModelAndView validaMetasBienJsonAUC(HttpServletRequest request, HttpServletResponse response) {
	if (log.isDebugEnabled()){ log.debug("debug Inicio - RqnpAucMetasController.validaMetasBienJson");}

	Map<String, Object> data = new HashMap<String, Object>();
	String valida="";
	try {
		String codigo_rqnp = StringUtils.trim(request.getParameter("txtCodigoRqnp"));
		
		valida=requerimientoNoProgramadoService.validaMetasBienesAUC(codigo_rqnp);
		 data.put("validaMeta",valida);
	} 
	catch(ServiceException ex){
		log.error("Error en RqnpAucMetasController.validaMetasBienJson: " + ex.getMessage());
		throw new ServiceException(this, ex);
	}
	catch(Exception ex){
		log.error("Error en RqnpAucMetasController.validaMetasBienJson: " + ex.getMessage(), ex);
		throw new ServiceException(this, ex);		   
	}
	finally{
		if (log.isDebugEnabled()){ log.debug("Fin - RqnpAucMetasController.validaMetasBienJson");}
	}
	return new ModelAndView(jsonView, "data", JsonUtil.toString(data)) ;
}

//AGREGADO PARA FORMULARIO DE APROBADOR
//INICIO: DPORRASC
/**
 * Recupera los el detalle de los items del RQNP
 * @param  request
 * @param  response 
 * @return ModelAndView 
 **/
public ModelAndView recuperarRqnpDetalleAUC(HttpServletRequest request, HttpServletResponse response) {
	if (log.isDebugEnabled()){ log.debug("Inicio - RqnpAucMetasController.recuperarRqnpDetalleAUC");}
	
	Map<String, Object> params 		= new HashMap<String, Object>();
	boolean okRunning = false;
	Map<String, Object> mapRqnp 	= new HashMap<String, Object>();
	Map<String, Object> parm = new HashMap<String, Object>();
	
	String cod_req	="";
	String ls_pag 	="";
	String user 	="";
	String visor	="";
	String cod_objeto	="";
	
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
	
		cod_req = StringUtils.trim(request.getParameter("txtCodigoRqnp"));
		ls_pag =  StringUtils.trim(request.getParameter("jParametroAuc")); 
		cod_objeto = StringUtils.trim(request.getParameter("txtCod_objeto"));
		user = usuarioBean.getLogin();
		
		 params.put("user", usuarioBean.getLogin());
		 params.put ("codigo_rqnp",cod_req);
		params.put("cod_objeto", cod_objeto);
		
		//REGISTRANDO OBJETO(BIEN-SERVICIO)
		if (cod_objeto !=null) {
			requerimientoNoProgramadoService.registrarCabObjeto(params);
		}
		//RECUPERANDO RQNP--------------------------------------------------------
		RequerimientoNoProgramadoBean rqnp= requerimientoNoProgramadoService.recuperarRqnpCabAUC(cod_req);
		
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
		 
		 lsFinalidad= (List<Map<String, Object>>) requerimientoNoProgramadoService.listaFinalidad();
		 lsNecesidad = (List<Map<String, Object>>) requerimientoNoProgramadoService.listaNecesidad();
		 lsTipoNecesidad = (List<Map<String, Object>>) requerimientoNoProgramadoService.listaTipoNecesidad();
		 
	} 
	catch (ServiceException ex) {
		log.error("Error en RqnpAucMetasController.recuperarRqnpDetalleAUC: " + ex.getMessage());
		request.setAttribute("excepAttr", ex);
		okRunning=false;
	} 
	catch (Exception ex) {
		log.error("Error en RqnpAucMetasController.recuperarRqnpDetalleAUC: " + ex.getMessage(), ex);
	} 
	finally {
		if (log.isDebugEnabled()){ log.debug("Fin - RqnpAucMetasController.recuperarRqnpDetalleAUC");}
	}
	
	if(!okRunning){
		return new ModelAndView("pagErrorSistema");
	}else{
		return new ModelAndView(ls_pag).addObject("mapRqnp", mapRqnp).addObject("listaBienes",listaBienes).
		addObject("visor", visor).addObject("lsNecesidad", lsNecesidad).
		addObject("lsTipoNecesidad", lsTipoNecesidad).addObject("lsFinalidad", lsFinalidad);	
	}
}


/**
 * Elimina los items del RQNP .
 * @param  request
 * @param  response 
 * @return ModelAndView 
 **/
public ModelAndView eliminarMetaRqnpJson(HttpServletRequest request, HttpServletResponse response) {
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
		String secu_func = StringUtils.trim(request.getParameter("jsecuFunc"));//DPORRASC
		
		params.put("codigo_bien", codigo_bien);
		params.put("codigo_rqnp", codigo_rqnp);
		params.put("anioEjec", anioEjec);
		params.put("secu_func", secu_func);//DPORRASC
		params.put("user", user);
		requerimientoNoProgramadoService.deleteMetaRnp(params);
		
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

//ULTIMO METODO DE BUSCAR METAS - FUNCION CREADA
/**
 * Recupera las metas afectadas y no afectadas de un  item del RQNP PERO DE UN AUC.
 * @param  request
 * @param  response 
 * @return ModelAndView 
 **/
//FUE AGREGADO PARA MOSTRAR DETALLES DE UN NUEVO XML (auc_metas.xml)
//ANTES: recuperarMetasJsonAUC 
public ModelAndView recuperarProyProdAccionJsonAUC(HttpServletRequest request, HttpServletResponse response) {
	if (log.isDebugEnabled()){ log.debug("debug Inicio - RqnpAucMetasController.recuperarProyectosJsonAUC");}
	Map<String, Object>  params = new HashMap<String, Object> ();
	Map<String, Object>  data = new HashMap<String, Object> ();
	List<Map<String, Object>> listaMetasProyectos = new ArrayList<Map<String,Object>>();
		
	List<Map<String, Object>> listaParametros = new ArrayList<Map<String,Object>>();
	try {
		String codigo_bien = StringUtils.trim(request.getParameter("jcodigo_bien_old"));
		String codigo_rqnp 	= StringUtils.trim(request.getParameter("txtCodigoRqnp"));
		//String codigo_dep = StringUtils.trim(request.getParameter("jcodigo_dep"));
		String codigo_dep 	= StringUtils.trim(request.getParameter("txtNum_UUOO"));
		String anio_ejec 	= StringUtils.trim(request.getParameter("janio_ejec"));
		String precio_unid 	= StringUtils.trim(request.getParameter("jprecio_unid_old"));
		
		params.put("cod_bien", codigo_bien);
		params.put("cod_req", codigo_rqnp);
		params.put("precio_unid", precio_unid);
		params.put("cod_dep",codigo_dep);
		params.put("anio_ejec",anio_ejec);
		 
		//listaMetas = (List<Map<String, Object>>) requerimientoNoProgramadoService.listarMetasProyectos(params);
		listaMetasProyectos = (List<Map<String, Object>>) requerimientoNoProgramadoService.listarProyProdAccion(params);
		
		listaParametros.add(params);
		
		data.put("listaProyProdAccion", JsonUtil.toString(listaMetasProyectos));
		
	} 
	catch(ServiceException ex){
		log.error("Error en RqnpAucMetasController.recuperarProyProdMetasJsonAUC: " + ex.getMessage());
		throw new ServiceException(this, ex);
	}
	catch(Exception ex){
		log.error("Error en RqnpAucMetasController.recuperarProyProdMetasJsonAUC: " + ex.getMessage(), ex);
		throw new ServiceException(this, ex);		   
	}
	finally{
		if (log.isDebugEnabled()) {log.debug("Fin - RqnpAucMetasController.recuperarProyProdMetasJsonAUC");}
	}
	//DEVUELVE data.listaMetasProyectos
	return new ModelAndView(jsonView, "data", JsonUtil.toString(data)) ;
}


//(DPORRASC)
//AGREGADO PARA DETALLE DE ENTREGA
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
		data.put("obs_plazo_entrega",( bien.getObs_plazo_entrega()==null)?"" : bien.getObs_plazo_entrega());
		data.put("obs_lugar_entrega",( bien.getObs_lugar_entrega()==null)?"":bien.getObs_lugar_entrega());
		data.put("obs_dir_entrega", (bien.getObs_dir_entrega()==null) ?"":bien.getObs_dir_entrega());
		
		data.put("cod_bien", (bien.getCodigoBien() ==null) ?"":bien.getCodigoBien());
		data.put("des_bien", (bien.getDesBien()==null) ?"":bien.getDesBien());
		data.put("des_unidad", (bien.getDesUnid()==null) ?"":bien.getDesUnid());
		data.put("des_tecnica", (bien.getDes_tecnica()==null) ?"":bien.getDes_tecnica());

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


//AGREGADO DE AucBandejaController
/**
 * Registra la Cabecera del RQNP por parte de la AUC
 * @param  request
 * @param  response 
 * @return ModelAndView 
 **/
public ModelAndView recuperarDetalleAUC(HttpServletRequest request, HttpServletResponse response) {
	if (log.isDebugEnabled()) {log.debug("Inicio - RqnpAucMetasController.recuperarDetalleAUC");}
	Map<String, Object> params 		= new HashMap<String, Object>();
	boolean okRunning 	= false;
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
	} 
	catch (ServiceException ex) {
		log.error("Error en RqnpAucMetasController.registrarCabRqnp: " + ex.getMessage());
		request.setAttribute("excepAttr", ex);
		okRunning=false;
	} 
	catch (Exception ex) {
		log.error("Error en RqnpAucMetasController.recuperarDetalleAUC: " + ex.getMessage(), ex);
	} 
	finally {
		if (log.isDebugEnabled()){ log.debug("Fin - RqnpAucMetasController.recuperarDetalleAUC");}
	}
	
	if(!okRunning){
		return new ModelAndView("pagErrorSistema");
	}
	
	return new ModelAndView("formAucRqnpDetalle").addObject("mapRqnp", mapRqnp).addObject("visor", visor).
		addObject("lsNecesidad", lsNecesidad).addObject("listaBienes",listaBienes).
		addObject("lsTipoNecesidad", lsTipoNecesidad).addObject("lsFinalidad", lsFinalidad);
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
 * Permite aprobar el RQNP por el Jefe Inmediato o Itendente.
 * @param  request
 * @param  response 
 * @return ModelAndView 
 **/
public ModelAndView contratacionRqnp(HttpServletRequest request, HttpServletResponse response) {
	if (log.isDebugEnabled()) {log.debug("Inicio - RqnpAucMetasController.contratacionRqnp");}
	List<Map<String, Object>> lsRqnp= new ArrayList<Map<String,Object>>();
	List<Map<String, Object>> lsNecesidad= new ArrayList<Map<String, Object>>();
	List<Map<String, Object>> lsTipoNecesidad= new ArrayList<Map<String, Object>>();
	List<Map<String, Object>> lsFinalidad	= new ArrayList<Map<String, Object>>();
	List<Map<String, Object>> listaBienes = new ArrayList<Map<String, Object>>();
	Map<String, Object>  mapRqnp 	= new HashMap<String, Object> ();
	Map<String, Object> params = new HashMap<String, Object>();
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
				intendente=requerimientoAucService.registrarContratacionRqnp(params);
				cod_req="";
				cod_bie="";
			}
			//fin for
			//-------------------------------------------------------------------------- 
			okRunning = true;
			params.put("cod_per", maestroPersonal.getCodigoEmpleado());
			params.put("anio_act", ls_anio);
			
			///---------------------------------------------------------------------------------------
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
			if (!cod_bie_env.equals("") ){
				
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
				
				 
				lsFinalidad= (List<Map<String, Object>>) requerimientoNoProgramadoService.listaFinalidad();
				lsNecesidad = (List<Map<String, Object>>) requerimientoNoProgramadoService.listaNecesidad();
				lsTipoNecesidad = (List<Map<String, Object>>) requerimientoNoProgramadoService.listaTipoNecesidad();	
				
			}else{
				return new ModelAndView("formAucFormatoAUC").addObject("mapRqnp", mapRqnp).addObject("visor", visor);
			}
		}else{
			Exception ex = new Exception("Error al Obtener Datos del Usuario");
			request.setAttribute("excepAttr", ex);
			return new ModelAndView("pagErrorSistema");
		}
	} 
	catch (ServiceException ex) {
		log.error("Error en RqnpAucMetasController.contratacionRqnp: " + ex.getMessage());
		exError = new Exception("error.aprobarji.generico");
		okRunning=false;
	} 
	catch (Exception ex) {
		log.error("Error en RqnpAucMetasController.contratacionRqnp: " + ex.getMessage(), ex);
	} 
	finally {
		if (log.isDebugEnabled()){ log.debug("Fin - RqnpAucMetasController.contratacionRqnp");}
	}
	if(!okRunning){
		exError = new Exception("error.registrarAtencion.generico");
		request.setAttribute("excepAttr", exError);
		return new ModelAndView("pagErrorSistema");
	}
	return new ModelAndView("formAucRqnpAtenderAUC").addObject("mapRqnp", mapRqnp).addObject("visor", visor).
	addObject("lsNecesidad", lsNecesidad).addObject("listaBienes",listaBienes).
	addObject("lsTipoNecesidad", lsTipoNecesidad).addObject("lsFinalidad", lsFinalidad).addObject("anio",ls_anio);
}



/**
 * @author dporrasc
 * @return
 * Metodo que exporta a excel las UUOOs beneficiarias
 */

public ModelAndView exportarExcelBeneficiarias(HttpServletRequest request, HttpServletResponse response) {
Map<String, Object> parmSearch = new HashMap<String, Object>();
	try {
		ModelAndView resultado = null;
		UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
		Map<String, Object> mapDatos_auc = (HashMap<String, Object>) WebUtils.getSessionAttribute(request, "mapDatos_auc");
		log.debug("cod_dep: " + mapDatos_auc.get("cod_dep") );
		/*
		MaestroPersonalBean colaborador;
		
		if(usuarioBean.getNroRegistro().equals("A")){
			colaborador =registroPersonalService.obtenerPersonaxRegistro("7065");
		}else{
			colaborador =registroPersonalService.obtenerPersonaxRegistro(usuarioBean.getNroRegistro());
		}
		*/
		MaestroPersonalBean colaborador =registroPersonalService.obtenerPersonaxRegistro(usuarioBean.getNroRegistro());
		//MaestroPersonalBean colaborador =registroPersonalService.obtenerPersonaxRegistro("7065"); //DLOCK
		//MaestroPersonalBean colaborador =registroPersonalService.obtenerPersonaxRegistro("0444"); //CGAMARRAM
		//MaestroPersonalBean colaborador =registroPersonalService.obtenerPersonaxRegistro("1397"); //VCABRERA
		
		String codRqnp = StringUtils.trim(request.getParameter("txtCodigoRqnp"));
		String codAmbito = StringUtils.trim(request.getParameter("codAmbito"));

		//parmSearch.put("uuoo", colaborador.getUuoo());
		parmSearch.put("codDependencia", mapDatos_auc.get("cod_dep"));
		parmSearch.put("cod_ambito", codAmbito.substring(0, 2));
		
		DependenciaBean dependenciaAUC = new DependenciaBean();
		dependenciaAUC = this.requerimientoNoProgramadoService.obtenerUuoo(parmSearch);
		parmSearch.put("cod_plaza", dependenciaAUC.getCod_plaza());
		
		List<Map<String, Object>> listaBeneficiarias	= (List<Map<String, Object>>) requerimientoAucService.obtenerBeneficiariasForExcel(parmSearch);
		
		// 2. Proceso las solicitudes para su exportacion a excel:
		HSSFWorkbook libroXLS = null;
		HSSFSheet hojaXLS = null;
		HSSFRow filaXLS = null;
		HSSFCell celdaXLS = null;

		HSSFCellStyle estiloTit = null;
		HSSFFont fuenteTit = null;

		HSSFCellStyle estiloDat = null;
		HSSFFont fuenteDat = null;

		HSSFCellStyle estiloDatIzq = null;
		HSSFFont fuenteDatIzq = null;

		libroXLS = new HSSFWorkbook();

		fuenteTit = libroXLS.createFont();
		fuenteTit.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // En negrita
		fuenteTit.setFontHeight((short) 160);
		fuenteTit.setFontName("Arial");

		fuenteDat = libroXLS.createFont();
		fuenteDat.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL); // Sin negrita
		fuenteDat.setFontHeight((short) 160);
		fuenteDat.setFontName("Arial");

		fuenteDatIzq = libroXLS.createFont();
		fuenteDatIzq.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL); // Sin negrita
		fuenteDatIzq.setFontHeight((short) 160);
		fuenteDatIzq.setFontName("Arial");
		
		estiloTit = libroXLS.createCellStyle();
		estiloTit.setFont(fuenteTit);
		estiloTit.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		estiloTit.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		estiloTit.setBorderRight(HSSFCellStyle.BORDER_THIN);
		estiloTit.setBorderTop(HSSFCellStyle.BORDER_THIN);
		estiloTit.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		estiloTit.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		estiloDat = libroXLS.createCellStyle();
		estiloDat.setFont(fuenteDat);
		estiloDat.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		estiloDat.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		estiloDat.setBorderRight(HSSFCellStyle.BORDER_THIN);
		estiloDat.setBorderTop(HSSFCellStyle.BORDER_THIN);
		estiloDat.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		
		estiloDatIzq = libroXLS.createCellStyle();
		estiloDatIzq.setFont(fuenteDat);
		estiloDatIzq.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		estiloDatIzq.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		estiloDatIzq.setBorderRight(HSSFCellStyle.BORDER_THIN);
		estiloDatIzq.setBorderTop(HSSFCellStyle.BORDER_THIN);
		estiloDatIzq.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		
		String nombreArchivo = "Beneficiarias_" + colaborador.getUuoo() + ".xls";
		response.setHeader("Content-Disposition", "attachment; filename=\"" + nombreArchivo + "\"");
		response.setContentType("application/vnd.ms-excel");

		// Ahora procesamos el reporte en
		hojaXLS = libroXLS.createSheet("Beneficiarias");

		filaXLS = hojaXLS.createRow((short) 0); //Nro de fila desde inicia la cebecera
		
		String[] columnas = { "UUOO", "DESCRIPCION UUOO","CANTIDAD"};
		int z = 0;
		for (String columna : columnas) {
			celdaXLS = filaXLS.createCell(z++);
			celdaXLS.setCellStyle(estiloTit);
			celdaXLS.setCellValue(columna);
			//hojaXLS.setColumnWidth(z, (columna.length() * 512));
			hojaXLS.setColumnWidth(1, (columna.length() * 2048));
		}

		int i = 1;// fila desde la cual se itera. 

		for (Map<String, Object> objBeneficiaria : listaBeneficiarias) {
			filaXLS = hojaXLS.createRow(i++); //Titulo
			z = 0;
			
			celdaXLS = filaXLS.createCell(z++);
			celdaXLS.setCellStyle(estiloDat);
			celdaXLS.setCellValue((String)objBeneficiaria.get("uuoo"));
			//hojaXLS.autoSizeColumn(z);
			
			celdaXLS = filaXLS.createCell(z++);
			celdaXLS.setCellStyle(estiloDat);
			celdaXLS.setCellValue((String)objBeneficiaria.get("descUuoo"));
			//hojaXLS.autoSizeColumn(z);
			
			celdaXLS = filaXLS.createCell(z++);
			celdaXLS.setCellStyle(estiloDat);
			celdaXLS.setCellValue((String)objBeneficiaria.get("cantidad"));
			//hojaXLS.autoSizeColumn(z);
			
			/*celdaXLS = filaXLS.createCell(++z);
			celdaXLS.setCellStyle(estiloDat);
			celdaXLS.setCellValue((String)firma.get("codIdarchivo").toString());*/
			
		}
		
		libroXLS.write(response.getOutputStream());
		response.getOutputStream().close();
		response.getOutputStream().flush();
		
		return resultado;
	}
	catch (Exception e) {
		log.error(e.getMessage(), e);
		throw new ServiceException(this, e);
	}
}	


/**
 * @author dporrasc
 * Registra las metas desde el archivo excel.
 * @param  request
 * @param  response 
 * @return ModelAndView 
 **/
public ModelAndView registrarRqnpMetasExcel(HttpServletRequest request, HttpServletResponse response) {
	if (log.isDebugEnabled()){ log.debug("Inicio - RqnpDetalleController.registrarRqnpMetas");}
	
	Map<String, Object>  data = new HashMap<String, Object> (); //AGREGADO (DPORRASC)
	Map<String, Object> params = new HashMap<String, Object>();
	boolean okRunning = false;
	Map<String, Object> mapRqnp = new HashMap<String, Object>();
	String cod_req		="";
	String anio_ejec	="";
	String codigo_dep	="";
	String cod_bien		="" ;
	String precio_unid	="";
	String user			="";
	List<Map<String, Object>> listaMetas = new ArrayList<Map<String,Object>>();
	try {
	
		UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
		user= usuarioBean.getLogin();

		cod_req 				= 	StringUtils.trim(request.getParameter("txtCodigoRqnp"));
		//codigo_dep			= 	StringUtils.trim(request.getParameter("jcodigo_dep"));
		codigo_dep				= 	StringUtils.trim(request.getParameter("txtNum_UUOO"));
		anio_ejec 				= 	StringUtils.trim(request.getParameter("janio_ejec"));
		cod_bien 				= 	StringUtils.trim(request.getParameter("jcodigo_bien"));
		precio_unid  			= 	StringUtils.trim(request.getParameter("jprecio_unid"));
		
		String[] txtBien 		=	validaArray(request.getParameterValues("a_txtBien"));
		String[] txtCantidad 	=	validaArray(request.getParameterValues("a_txtCantidad"));
		String[] txtMonto		=	validaArray(request.getParameterValues("a_txtMonto"));
		String[] txtSecuencia	=	validaArray(request.getParameterValues("a_txtSecuencia"));
		
		if (txtCantidad ==null){
			log.debug("cantidad null: "   );
		}else{
			log.debug("cantidad length:" + txtCantidad.length);
		}
		params.put("codigo_rqnp", cod_req);
		params.put("codigo_dep", codigo_dep);
		params.put("codigo_bien",cod_bien );
		params.put("anio_ejec", anio_ejec);
		params.put("txtBien",txtBien);
		params.put("txtCantidad",txtCantidad);
		params.put("txtxCantidad",txtCantidad);
		params.put("txtMonto",txtMonto);
		params.put("txtSecuencia",txtSecuencia);
		params.put("precio_unid",precio_unid);
		params.put("user", user);
		params.put("flagExcel", "S");
		///guardando metas
		
		requerimientoNoProgramadoService.registrarMetasRqnpAUC(params);

		RequerimientoNoProgramadoBean rqnp= requerimientoNoProgramadoService.recuperarRqnpAUC(cod_req);
		params.put("rqnp",rqnp);
		
		BigDecimal monto=requerimientoNoProgramadoService.registrarCabMonto(params);
		
		rqnp.setMontoRqnp(monto);
		//Seteando Cabecera

		 mapRqnp.put("codigoRqnp", rqnp.getCodigoRqnp());
		 mapRqnp.put("codigoReqNoProgramado", rqnp.getCodigoReqNoProgramado());
		 mapRqnp.put("codigoResposanble", rqnp.getNumeroRegistro());
		 mapRqnp.put("resposanble", rqnp.getNombreSolicitante());
		 mapRqnp.put("codigoUuoo", rqnp.getUuoo());
		 mapRqnp.put("codigoDependencia", rqnp.getCodigoDependencia());
		 mapRqnp.put("dependencia", rqnp.getNombreUOSolicitante());
		 mapRqnp.put("fechaRqnp",new SimpleDateFormat("dd/MM/yyyy HH:mm").format(rqnp.getFechaRqnp()));
		 mapRqnp.put("motivoRqnp",rqnp.getMotivoSolicitud());
		 mapRqnp.put("codigoDependencia", rqnp.getCodigoDependencia());
		 mapRqnp.put("anioProceso", rqnp.getAnioProceso());
		// mapRqnp.put("montoRqnp",formateador.format( rqnp.getMontoRqnp()));
		 mapRqnp.put("montoRqnp",rqnp.getMontoRqnp());
		 mapRqnp.put("jcodigo_rqnp", cod_req);
		 mapRqnp.put("jcodigo_bien", cod_bien);
		 mapRqnp.put("jprecio_unid", precio_unid);
		
		//setando parametros de busueda de metas
		 params.put("cod_req",rqnp.getCodigoRqnp());
		 params.put("cod_bien",cod_bien );
		 params.put("cod_dep",codigo_dep);
		 params.put("anio_ejec",rqnp.getAnioProceso());
		 params.put("precio_unid",precio_unid);
		 
		 //MODIFICADO(DPORRASC)
		 listaMetas = (List<Map<String, Object>>) requerimientoNoProgramadoService.listarRqnpMetasBien(params);
		 data.put("listaMetas", JsonUtil.toString(listaMetas));
		 okRunning=true; 
	} 
	catch (ServiceException ex) {
		log.error("Error en RqnpDetalleController.registrarRqnpMetas: " + ex.getMessage());
		request.setAttribute("excepAttr", ex);
		okRunning=false;
	} 
	catch (Exception ex) {
		log.error("Error en RqnpDetalleController.registrarRqnpMetas: " + ex.getMessage(), ex);
	} 
	finally {
		if (log.isDebugEnabled()){ log.debug("Fin - RqnpDetalleController.registrarRqnpMetas");}
	}
	
	if(!okRunning){
		return new ModelAndView("pagErrorSistema");
	}else{
		return new ModelAndView(jsonView, "data", JsonUtil.toString(data)).addObject("mapRqnp",mapRqnp) ;
	}
}


public String [] validaArray(String [] arr){
	String sortIds[] = null;
	String array[] = arr[0].split(",");
		sortIds = new String[array.length];
       for(int i=0;i<array.length;i++){
           try{
        	   sortIds[i] = array[i]; 
           }catch(Exception e){
           }
       }
       return sortIds;
}

}


