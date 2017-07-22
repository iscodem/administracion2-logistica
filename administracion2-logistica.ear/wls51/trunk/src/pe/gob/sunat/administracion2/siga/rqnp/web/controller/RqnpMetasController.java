package pe.gob.sunat.administracion2.siga.rqnp.web.controller;

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

import pe.gob.sunat.administracion2.siga.rqnp.model.domain.RequerimientoNoProgramadoBean;
import pe.gob.sunat.administracion2.siga.rqnp.service.RequerimientoNoProgramadoService;
import pe.gob.sunat.administracion2.siga.rqnp.util.RqnpConstantes;
import pe.gob.sunat.administracion2.siga.rqnp.util.RqnpUtil;
import pe.gob.sunat.framework.core.json.JsonUtil;
import pe.gob.sunat.framework.spring.util.exception.ServiceException;
import pe.gob.sunat.framework.spring.web.view.JsonView;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.DependenciaBean;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.MaestroContratistasBean;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.MaestroPersonalBean;
import pe.gob.sunat.recurso2.administracion.siga.registro.service.RegistroPersonalService;
import pe.gob.sunat.recurso2.administracion.siga.util.FechaUtil;
import pe.gob.sunat.recurso2.administracion.siga.util.FormatoConstantes;
import pe.gob.sunat.recurso2.administracion.siga.web.util.BaseController;
import pe.gob.sunat.tecnologia.menu.bean.UsuarioBean;

public class RqnpMetasController extends BaseController{
	private static final Log log = LogFactory.getLog(RqnpMetasController.class);

	private RequerimientoNoProgramadoService 	requerimientoNoProgramadoService;
	private RegistroPersonalService 			registroPersonalService;
	private ReloadableResourceBundleMessageSource 		messageSource;
	
	/**
	 * @author dporrasc
     * Carga la pagina inicial de afectación presupuestal(metas)
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView iniciarRqnpMetas(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("Inicio -  RqnpMetasController.iniciarRqnpMetas");}
		 Map<String, Object> mapRqnp 		= new HashMap<String, Object>();
		 Map<String, Object> params 		= new HashMap<String, Object>();
		 Map<String, Object> parm 			= new HashMap<String, Object>();
		 Map<String, Object> respuesta 		= new HashMap<String, Object>();
		 ModelAndView view = null;
		 String cod_req	=FormatoConstantes.CADENA_VACIA;
		
		 List<Map<String, Object>> listaMetas = new ArrayList<Map<String,Object>>();
		try {
			 cod_req 	= StringUtils.trim(request.getParameter("hidCodigoRqnpMetas")); 
			 
			RequerimientoNoProgramadoBean rqnp= requerimientoNoProgramadoService.recuperarRqnp(cod_req);

			//Seteando Cabecera
			mapRqnp = RqnpUtil.getRqnpCabDatos(rqnp);
			
			if(rqnp.getCod_contacto()!=null){
				MaestroPersonalBean personalContacto =registroPersonalService.obtenerPersonaxCodigo( rqnp.getCod_contacto());
				Map<String, Object> mapRqnpContacto = RqnpUtil.getRqnpCabDatosContacto(personalContacto);
				mapRqnp.putAll(mapRqnpContacto);
			}
			
			if (rqnp.getCod_proveedor() !=null) {
				if (!rqnp.getCod_proveedor().equals("")){
					parm.put("cod_cont", rqnp.getCod_proveedor());
				
					MaestroContratistasBean contratista=registroPersonalService.recuperarContratista(parm);
					Map<String, Object> mapRqnpProveedor = RqnpUtil.getRqnpCabDatosProveedor(contratista);
					mapRqnp.putAll(mapRqnpProveedor);
				}
			}
			
			//Seteando Metas para el primer item
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
			
			respuesta.put("mapRqnp", mapRqnp);
			respuesta.put("listaBienes", RqnpUtil.toJSON(listaBienes).toString());
			respuesta.put("listaMetas", RqnpUtil.toJSON(listaMetas).toString());
			 
			view=new ModelAndView(RqnpConstantes.RQNP_REGISTRO_RQNP_METAS_PAGE, respuesta);
		} 
		catch(ServiceException ex){
			log.error("Error en  RqnpMetasController.iniciarRqnpMetas: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en  RqnpMetasController.iniciarRqnpMetas: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()) {log.debug("Fin -  RqnpMetasController.iniciarRqnpMetas");}
		}
		return view;
	}
	
	
	/**
	 * @author dporrasc
     * Carga la pagina inicial de afectación presupuestal(metas)
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView modificarRqnpMetas(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("Inicio -  RqnpMetasController.modificarRqnpMetas");}
		//mapRqnp
		 Map<String, Object> mapRqnp 		= new HashMap<String, Object>();
		 Map<String, Object> mapCatalogo 	= new HashMap<String, Object>();
		 Map<String, Object> lsCatalogo 	= new HashMap<String, Object>();
		 Map<String, Object> params 		= new HashMap<String, Object>();
		 String cod_req	="";
		 String visor	="";
		
		 List<Map<String, Object>> listaMetas = new ArrayList<Map<String,Object>>();
		try {
			 cod_req 	= StringUtils.trim(request.getParameter("txtCodigoRqnp"));
			 visor		= StringUtils.trim(request.getParameter("txtvisor"));
			 
			RequerimientoNoProgramadoBean rqnp= requerimientoNoProgramadoService.recuperarRqnp(cod_req);

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
			return new ModelAndView("formRegistroRqnpMetas").addObject("mapRqnp", mapRqnp).addObject("mapCatalogo", mapCatalogo).
			addObject("lsCatalogo",lsCatalogo).addObject("listaBienes",listaBienes).addObject("listaMetas",listaMetas)
			.addObject("visor", visor);
		} 
		catch(ServiceException ex){
			log.error("Error en  RqnpMetasController.modificarRqnpMetas: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en  RqnpMetasController.modificarRqnpMetas: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()) {log.debug("Fin -  RqnpMetasController.modificarRqnpMetas");}
		}
	}
	
	/**
     * Registra las metas RQNP(afectación presupuestal).
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView registrarRqnpMetas(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("Inicio - RqnpDetalleController.registrarRqnpMetas");}
		
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
			String[]  txtMonto		=	request.getParameterValues("txtMonto");
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
	
			RequerimientoNoProgramadoBean rqnp= requerimientoNoProgramadoService.recuperarRqnp(cod_req);
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
		}
		
		return new ModelAndView("formRegistroRqnpMetas").addObject("mapRqnp", mapRqnp).addObject("mapCatalogo", mapCatalogo).
			addObject("lsCatalogo",lsCatalogo).addObject("listaBienes",listaBienes).addObject("listaMetas",listaMetas) ;
	}
	
	public Map<String, Object> validaCantidadMeta(String[] txtBien, String[] txtxCantidad){
		Map<String, Object> respuesta = new HashMap<String, Object>();
		
		if (txtxCantidad == null){
			respuesta.put("okvalidation", false);
			respuesta.put("mensaje", "Ingrese la cantidad y presione enter");
		}else {
			for(int x=0;x<txtxCantidad.length;x++) {
				String indTipoItem= txtBien[x].substring(0,1);
				
				if(indTipoItem.equals("S") && txtxCantidad[x].equals("1")){
					respuesta.put("okvalidation", false);
					respuesta.put("mensaje", "Debe ingresar el monto aproximado en (S/.) que costar\u00E1 el servicio.\n\n Est\u00E1 seguro de la cantidad \u00F3 monto contratado del servicio?");
				}
			}
		}
		return respuesta;
	}

	/**
     * Registra las metas modificadas RQNP y devuelve un  Json cada vez 
     * que el usuario cambia de fila item(afectación presupuestal).
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView registrarRqnpMetasJsonClick(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()) log.debug("Inicio - RqnpMetasController.registrarRqnpMetasJson");
		
		Map<String, Object> respuesta = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		boolean okRunning 	= false;
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
		
			cod_req 				= 	StringUtils.trim(request.getParameter("txtCodigoRqnp"));
			codigo_dep				= 	StringUtils.trim(request.getParameter("jcodigo_dep"));
			anio_ejec 				= 	StringUtils.trim(request.getParameter("janio_ejec"));
			
			cod_bien 				= 	StringUtils.trim(request.getParameter("jcodigo_bien"));
			precio_unid  			= 	StringUtils.trim(request.getParameter("jprecio_unid"));
			
			String[] txtBien 		=	request.getParameterValues("txtBien");
			String[] txtCantidad 	=	request.getParameterValues("txtCantidad");
			String[] txtxCantidad 	=	request.getParameterValues("txtxCantidad");
			String[] txtMonto 		=	request.getParameterValues("txtMonto");
			String[] txtSecuencia	=	request.getParameterValues("txtSecuencia");
			
			respuesta=this.validaCantidadMeta(txtBien, txtxCantidad);
			
			if(respuesta.size()>0){
				okvalidation=(Boolean) respuesta.get("okvalidation");
				data.put("mensaje",(String) respuesta.get("mensaje"));
			}
			
			if (okvalidation){
				log.debug("txtBien lentgth  2 :"+ txtBien .length);

				log.debug("txtxCantidad lentgth :"+ txtxCantidad .length);
				for(int i=0; i< txtxCantidad.length; i++ ){
					log.debug(txtBien[i]+  "  "+ txtSecuencia[i]+ " - "+  txtxCantidad[i]);
					txtxCantidad[i]=txtxCantidad[i].replace(",", "");
				}
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
				
				RequerimientoNoProgramadoBean rqnp= requerimientoNoProgramadoService.recuperarRqnp(cod_req);
				params.put("rqnp",rqnp);
				
				BigDecimal monto=requerimientoNoProgramadoService.registrarCabMonto(params);
				
				//setando parametros de busueda de metas
				params.put("cod_bien", cod_bien);
				params.put("cod_req", cod_req);
				params.put("precio_unid", precio_unid);
				params.put("cod_dep",codigo_dep);
				params.put("anio_ejec",anio_ejec);
				 
				listaMetas = (List<Map<String, Object>>) requerimientoNoProgramadoService.listarRqnpMetas(params);
				 
				data.put("listaMetas", JsonUtil.toString( listaMetas));
				okRunning=true; 
			}
		
		}catch (ServiceException ex) {
			log.error("Error en RqnpDetalleController.registrarRqnpMetasJson: " + ex.getMessage());
		}catch (Exception ex) {
			log.error("Error en RqnpDetalleController.registrarRqnpMetasJson: " + ex.getMessage(), ex);
		}finally {
			if (log.isDebugEnabled()) {log.debug("Fin - RqnpDetalleController.registrarRqnpMetasJson");}
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
		if (log.isDebugEnabled()) {log.debug("Inicio - RqnpMetasController.registrarRqnpMetasJson");}
		
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
			String[] txtxCantidad 		=	request.getParameterValues("txtxCantidad");
			String[] txtBien 		=	request.getParameterValues("txtBien");
			String[] txtCantidad 	=	request.getParameterValues("txtCantidad");
			String[] txtMonto 		=	request.getParameterValues("txtMonto");
			String[] txtSecuencia	=	request.getParameterValues("txtSecuencia");
			log.debug("lentgth txtBien :"+ txtBien .length);
			//log.debug("lentgth txtCantidad "+ txtCantidad .length);
			//log.debug("lentgth txtSecuencia "+ txtSecuencia .length);
			
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
				///guardando metas
				requerimientoNoProgramadoService.registrarMetasRqnp(params);
				RequerimientoNoProgramadoBean rqnp= requerimientoNoProgramadoService.recuperarRqnp(cod_req);
				params.put("rqnp",rqnp);
				
				BigDecimal monto=requerimientoNoProgramadoService.registrarCabMonto(params);
				
			 	params.put("cod_bien", cod_bien);
				params.put("cod_req", cod_req);
				params.put("precio_unid", precio_unid);
				params.put("cod_dep",codigo_dep);
				params.put("anio_ejec",anio_ejec);
					 
				listaMetas = (List<Map<String, Object>>) requerimientoNoProgramadoService.listarRqnpMetas(params);
		
				data.put("listaMetas", JsonUtil.toString( listaMetas));
				okRunning=true; 
			}

		}catch (ServiceException ex) {
			log.error("Error en RqnpMetasController.registrarRqnpMetasJson: " + ex.getMessage());
		}catch (Exception ex) {
			log.error("Error en RqnpMetasController.registrarRqnpMetasJson: " + ex.getMessage(), ex);
		}finally {
			if (log.isDebugEnabled()){ log.debug("Fin - RqnpMetasController.registrarRqnpMetasJson");}
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
     * Recupera las metas afectadas y no afectadas de un  item del RQNP .
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView recuperarMetasJson(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - RqnpMetasController.recuperarMetas");}
		Map<String, Object>  params = new HashMap<String, Object> ();
		Map<String, Object>  data = new HashMap<String, Object> ();
		List<Map<String, Object>> listaMetas = new ArrayList<Map<String,Object>>();
		try {
			String codigo_bien = StringUtils.trim(request.getParameter("jcodigo_bien"));
			String codigo_rqnp 	= 	StringUtils.trim(request.getParameter("txtCodigoRqnp"));
			String codigo_dep 	= StringUtils.trim(request.getParameter("jcodigo_dep"));
			String anio_ejec 	= StringUtils.trim(request.getParameter("janio_ejec"));
			String precio_unid 	= StringUtils.trim(request.getParameter("jprecio_unid"));
			
			params.put("cod_bien", codigo_bien);
			params.put("cod_req", codigo_rqnp);
			params.put("precio_unid", precio_unid);
			params.put("cod_dep",codigo_dep);
			params.put("anio_ejec",anio_ejec);
			 
			 listaMetas = (List<Map<String, Object>>) requerimientoNoProgramadoService.listarRqnpMetas(params);
			 data.put("listaMetas", JsonUtil.toString( listaMetas));
		} 
		catch(ServiceException ex){
			log.error("Error en RqnpMetasController.recuperarMetas: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en RqnpMetasController.recuperarMetas: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()) {log.debug("Fin - RqnpMetasController.recuperarMetas");}
		}
		return new ModelAndView(jsonView, "data", JsonUtil.toString(data)) ;
	}
	

	/**
     * Valida que los item del Rqnp tengan al menos una meta afectada .
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView validaMetasBienJson(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - RqnpMetasController.validaMetasBienJson");}

		Map<String, Object> data = new HashMap<String, Object>();
		String valida="";
		try {
			String codigo_rqnp = StringUtils.trim(request.getParameter("txtCodigoRqnp"));
			valida=requerimientoNoProgramadoService.validaMetasBienes(codigo_rqnp);
			 data.put("validaMeta",valida);
		} 
		catch(ServiceException ex){
			log.error("Error en RqnpMetasController.validaMetasBienJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en RqnpMetasController.validaMetasBienJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - RqnpMetasController.validaMetasBienJson");}
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
		if (log.isDebugEnabled()){ log.debug("debug Inicio - RqnpMetasController.recuperarMetasVistaJson");}
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
			log.error("Error en RqnpMetasController.recuperarMetasVistaJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en RqnpMetasController.recuperarMetasVistaJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()) {log.debug("Fin - RqnpMetasController.recuperarMetasVistaJson");}
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
		Map<String, Object> data = new HashMap<String, Object>();
		boolean isJefe=false;
		String user="";
		
		try {
			MaestroPersonalBean  maestroPersonal=  (MaestroPersonalBean) WebUtils.getSessionAttribute(request, "maestroPersonalBean");
			UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
			user= usuarioBean.getLogin();
			if (maestroPersonal!=null){
				String codigo_rqnp = StringUtils.trim(request.getParameter("txtCodigoRqnp"));
				isJefe=registroPersonalService.esJefe(maestroPersonal.getCodigoEmpleado(),maestroPersonal.getCodigoDependencia());
				params.put("codigo_rqnp", codigo_rqnp);
				params.put("user", user);
				params.put("isJefe", isJefe);
				
				Map validar = validarEnviarJI(params);
		        String flag = (String)validar.get("flagValidacion");
		        String mensaje = (String)validar.get("mensaje");
		        if (flag == "0") {
		          this.requerimientoNoProgramadoService.registrarEnviarRqnp(params);
		          this.requerimientoNoProgramadoService.EnviarMailRqnp(params);
		          data.put("flagRegistroDeclaracion", "0");
		          data.put("mensaje", mensaje);
			}else{
				data.put("flagRegistroDeclaracion", "1");
				data.put("mensaje", mensaje);
	        }

	        return new ModelAndView(this.jsonView, "data", data);
	      }
	      Exception ex = new Exception("Error al Obtener Datos del Usuario");
	      request.setAttribute("excepAttr", ex);
	      return new ModelAndView("pagErrorSistema");
	
		} catch (ServiceException ex) {
			log.error("Error en RqnpMetasController.enviarRqnp: " + ex.getMessage());
			return new ModelAndView("pagErrorSistema");
	    } catch (Exception ex) {
	    	ModelAndView localModelAndView;
	    	log.error("Error en RqnpMetasController.enviarRqnp: " + ex.getMessage(), ex);
	    	return new ModelAndView("pagErrorSistema");
	    } finally {
	    	if (log.isDebugEnabled()) log.debug("Fin - RqnpMetasController.enviarRqnp");
	    }
	}
	
	
	/**
	 * 
	 * @return
	 */
	public ModelAndView validaExisteMetaJson(HttpServletRequest request, HttpServletResponse response)
	  {
	    if (log.isDebugEnabled()) log.debug("debug Inicio - RqnpMetasController.validaExisteMetaJson");

	    Map data = new HashMap();
	    String validaExisteMeta = "";
	    String anioMeta = "";
	    try {
	    	String codDependencia = StringUtils.trim(request.getParameter("txtcodDependencia"));
	    	anioMeta = FechaUtil.obtenerAnioActual();
	    	
	    	DependenciaBean dependenciaDpg = new DependenciaBean();
	    	dependenciaDpg = requerimientoNoProgramadoService.obtenerDpg(RqnpConstantes.COD_PARAMETRO_UPP, RqnpConstantes.DESC_PARAMETRO_UPP);
	    	String uuooDpg = dependenciaDpg.getUuoo()+" - "+dependenciaDpg.getNom_corto();
	    	
	    	validaExisteMeta = this.requerimientoNoProgramadoService.validaExisteMetaJson(codDependencia, anioMeta);
	    	
	    	data.put("validaExisteMeta", validaExisteMeta);
	    	data.put("uuooDpg", uuooDpg );
	    	
	    	log.debug("codDependencia: " + codDependencia);
	    	log.debug("validaExisteMeta: " + validaExisteMeta);
	    	log.debug("uuooDpg: " + uuooDpg);
	    	
	    } catch (ServiceException ex) {
	      log.error("Error en RqnpMetasController.validaExisteMetaJson: " + ex.getMessage());
	      throw new ServiceException(this, ex);
	    } catch (Exception ex) {
	      log.error("Error en RqnpMetasController.validaExisteMetaJson: " + ex.getMessage(), ex);
	      throw new ServiceException(this, ex);
	    } finally {
	      if (log.isDebugEnabled()) log.debug("Fin - RqnpMetasController.validaExisteMetaJson");
	    }
	    return new ModelAndView(this.jsonView, "data", JsonUtil.toString(data));
	  }
	
	
		
	/**
	 * @author dporrasc
	 * @param params
	 * @return
	 */
	  private Map<String, String> validarEnviarJI(Map<String, Object> params)
	  {
	    String mensaje = "";
	    String flagValidacion = "0";
	    boolean validaExisteMeta = false;
	    Map respuesta = new HashMap();
	    String codRqnp = (String)params.get("codigo_rqnp");
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
	        mensaje = "La dependencia que generó el Requerimiento se encuentra Desactivada.\n Para más detalles favor de comunicarse con Recursos Humanos.";
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
	      
	      if (dependenciaJefatura == null) {
	        mensaje = "La dependencia de la Jefatura que atenderá este Requerimiento se encuentra Desactivada. \nPara más detalles favor de comunicarse con Recursos Humanos.";
	        flagValidacion = "1";
	      }else if (dependenciaJefatura.getCodEmpleado() == null) {
	    	  log.debug("dependenciaJefatura: "+dependenciaJefatura.getCod_dep() + " - UUOO: "+dependenciaJefatura.getUuoo());
	    	  mensaje = "La Jefatura Inmediata que atenderá este Requerimiento no se encuentra asignada. \n\n Favor de comunicarse con Información de Personal.";
	        flagValidacion = "1";
	      }else if(dependenciaJefatura.getCodEmpleado() != null){
	    	  MaestroPersonalBean jefe = registroPersonalService.obtenerPersonaxCodigo(dependenciaJefatura.getCodEmpleado());
	    	  if(jefe.getCodigoEstado()!=null){
	    		  if(!jefe.getCodigoEstado().equals("1")){
		    		  	mensaje = "El Jefe que atenderá este Requerimiento se encuentra en estado inactivo. \nFavor de comunicarse con Información de Personal en Recursos Humanos.";
		    		  	flagValidacion = "1";
		    	  } 
	    	  }else{
	    		  mensaje = "El Jefe que atenderá este Requerimiento se encuentra en estado inactivo. \nFavor de comunicarse con Información de Personal en Recursos Humanos.";
	    		  flagValidacion = "1";
	    	  }
	      }

	      if (flagValidacion.equals("0")) {
	        if (dependenciaOEC == null) {
	          mensaje = "El Área Encargada de la Contratación que atenderá este Requerimiento se encuentra Desactivada. \nFavor de comunicarse con la Unidad "+uuooDpg+" para mas detalles.";
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
	          mensaje = "El Área Técnica (AT) que atenderá este Requerimiento se encuentra Desactivada ó no se asignó al requerimiento. \nFavor de comunicarse con la Unidad "+uuooDpg+" para mas detalles.";
	          flagValidacion = "1";
	        } else {
	        	log.debug("dependenciaAUC: "+dependenciaAUC.getCod_dep() + " - UUOO: "+dependenciaAUC.getUuoo());
	        	String codAucDeRqnp = dependenciaAUC.getCod_dep();
	        	String codEmpleadoJefeAuc = dependenciaAUC.getCodEmpleado();
	        	
	          if (codAucDeRqnp == null) {
	            mensaje = "El Area Técnica (AT) que atenderá este Requerimiento se encuentra Desactivada ó no se asignó al requerimiento.Favor de comunicarse con la Unidad "+uuooDpg+" para mas detalles.";
	            flagValidacion = "1";
	          } else if (codEmpleadoJefeAuc == null) {
	            mensaje = "El Jefe del Área Técnica (AT) que atenderá este Requerimiento no se encuentra asignado. \nFavor de comunicarse con Información de Personal en Recursos Humanos.";
	            flagValidacion = "1";
	          }
	          
	          /*
	          DependenciaBean dependenciaAprobadora = new DependenciaBean();
	          String codUuooAprobadoraAuc = dependenciaAUC.getCodUuooAprueba();
	          if (codUuooAprobadoraAuc == null) {
		          mensaje = "La UUOO Aprobadora que atenderá este Requerimiento no se encuentra configurada.";
		          flagValidacion = "1";
		        }else{
		        	params.put("codUuooAprueba",codUuooAprobadoraAuc);
		        	dependenciaAprobadora = this.requerimientoNoProgramadoService.obtenerUuooAprobadoraAuc(params);
		        	log.debug("dependenciaAprobadora: "+dependenciaAprobadora.getCod_dep() + " - UUOO: "+dependenciaAprobadora.getUuoo());
		        }
		        */
	        }
	      }

	      if (validaExisteMeta) {
	        mensaje = "No ha indicado la cantidad de bienes a adquirir ó el monto en S/. aproximado que costará el servicio.";
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
	      respuesta.put("uuooDpg", uuooDpg );
	    }
	    catch (ServiceException ex) {
	      log.error("Error en RqnpBandejaJIController.validarEnviarJI: " + ex.getMessage());
	      throw new ServiceException(this, ex);
	    }
	    catch (Exception ex) {
	      log.error("Error en RqnpBandejaJIController.validarEnviarJI: " + ex.getMessage(), ex);
	      throw new ServiceException(this, ex);
	    }
	    finally {
	      if (log.isDebugEnabled()) log.debug("Fin - RqnpBandejaJIController.validarEnviarJI");
	    }
	    return respuesta;
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
}
