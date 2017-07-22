package pe.gob.sunat.administracion2.siga.rqnp.web.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Formatter;
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
import pe.gob.sunat.framework.core.json.JsonUtil;
import pe.gob.sunat.framework.spring.util.exception.ServiceException;
import pe.gob.sunat.framework.spring.web.view.JsonView;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.MaestroContratistasBean;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.MaestroPersonalBean;
import pe.gob.sunat.recurso2.administracion.siga.registro.service.RegistroPersonalService;
import pe.gob.sunat.recurso2.administracion.siga.web.util.BaseController;
import pe.gob.sunat.tecnologia.menu.bean.UsuarioBean;

public class RqnpConsultaController extends BaseController{

	private static final Log log = LogFactory.getLog(RqnpController.class);

	private RegistroPersonalService 			registroPersonalService;
	private RequerimientoNoProgramadoService 	requerimientoNoProgramadoService;
	private ReloadableResourceBundleMessageSource 		messageSource;
	//private JsonView 							jsonView;
	//private View 								htmlView;
	//private MensajeBean 						beanMsg;
	
		
	/**
     * Carga la pagina inicial de la lista de Acciones
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView iniciaListaAcciones(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("Inicio -  RqnpController.iniciaListaAcciones");}
		//mapRqnp
		 Map<String, Object> mapRqnp 		= new HashMap<String, Object>();
		 Map<String, Object> mapCatalogo 	= new HashMap<String, Object>();
		 Map<String, Object> lsCatalogo 	= new HashMap<String, Object>();

		 String cod_req	="";
		 String visor	="";
		 String path_url="";
		 List<Map<String, Object>> lsAcciones = new ArrayList<Map<String,Object>>();
		try {
			 cod_req 	= StringUtils.trim(request.getParameter("txtCodigoRqnp"));
			 visor		= StringUtils.trim(request.getParameter("txtvisor"));
			 path_url	= StringUtils.trim(request.getParameter("path_url"));
			 log.debug("URL: "+ path_url);
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
				 if (listaBienes.size()>0){
					 String num_expediente="";
					 Map<String, Object> bien = listaBienes.get(0);
					 num_expediente = (String)bien.get("num_exp");
					 lsAcciones = (List<Map<String, Object>>) requerimientoNoProgramadoService.listarAccionesBien(num_expediente);
				 }
			 }
			return new ModelAndView("formListaAccionBien").addObject("mapRqnp", mapRqnp).addObject("mapCatalogo", mapCatalogo).
			addObject("lsCatalogo",lsCatalogo).addObject("listaBienes",listaBienes).addObject("listaAcciones",lsAcciones)
			.addObject("visor", visor).addObject("path_url", path_url);
		} 
		catch(ServiceException ex){
			log.error("Error en  RqnpController.iniciaListaAcciones: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en  RqnpController.iniciaListaAcciones: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin -  RqnpController.iniciaListaAcciones");}
		}
	}
	
	

	/**
     * Carga la Página inicial para el registro de informe de justificacion.
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView informeRqnp(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("Inicio -  RqnpController.informeRqnp");}
		Map<String, Object> mapRqnp 	= 	new HashMap<String, Object>();
		Map<String, Object> mapCatalogo = 	new HashMap<String, Object>();
		Map<String, Object> lsCatalogo 	= 	new HashMap<String, Object>();
		String cod_req	=	"";
		String visor	=	"";
		try {
			
			cod_req = StringUtils.trim(request.getParameter("txtCodigoRqnp"));
			visor	= StringUtils.trim(request.getParameter("txtvisor"));
			RequerimientoNoProgramadoBean rqnp= requerimientoNoProgramadoService.recuperarRqnp(cod_req);
			//Seteando Cabecera
			mapRqnp.put("codigoRqnp", rqnp.getCodigoRqnp());
			mapRqnp.put("codigoReqNoProgramado", rqnp.getCodigoReqNoProgramado());
			mapRqnp.put("codigoResposanble", rqnp.getNumeroRegistro());
			mapRqnp.put("resposanble", rqnp.getNombreSolicitante());
			mapRqnp.put("codigoUuoo", rqnp.getUuoo());
			mapRqnp.put("codigoDependencia", rqnp.getCodigoDependencia());
			mapRqnp.put("anioProceso", rqnp.getAnioProceso());
			
			mapRqnp.put("dependencia", rqnp.getNombreUOSolicitante());
			String newDate = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(rqnp.getFechaRqnp());
			log.debug("FECHA ------>>>>>>" +newDate);
			mapRqnp.put("fechaRqnp",newDate);
			mapRqnp.put("motivoRqnp",rqnp.getMotivoSolicitud());
			mapRqnp.put("montoRqnp",  rqnp.getMontoRqnp().setScale(2, RoundingMode.HALF_UP) );
			//Seteando Detalle
			List<Map<String, Object>> listaBienes = (List<Map<String, Object>>) rqnp.getListaBienes();
			
			return new ModelAndView("formRqnpDetalleInforme").addObject("mapRqnp", mapRqnp).
			addObject("mapCatalogo", mapCatalogo).addObject("lsCatalogo",lsCatalogo).
			addObject("listaBienes",listaBienes).addObject("visor", visor);
			
		} 
		catch(ServiceException ex){
			log.error("Error en  RqnpController.informeRqnp: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en  RqnpController.informeRqnp: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()) {log.debug("Fin -  RqnpController.informeRqnp");}
		}
	}


	/**
     * Carga la Bandeja de Consulta de los Requerimientos registrados
     * por el Usuario.
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/ 
	@SuppressWarnings("unchecked")
	public ModelAndView iniciarconsulta(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - RqnpConsultaController.iniciarConsulta");}
		String ls_anio="";
		String ls_mes="";
		Calendar fecha =  Calendar.getInstance();
		String codEmpleado="";
		String tituloVista="";
		String codDependencia="";
		String nroRegEmpleado="";
		String ls_next="";		
		List<Map<String, Object>> lsRqnp= new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> lsNecesidad= new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> lsTipoNecesidad= new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> lsFinalidad= new ArrayList<Map<String, Object>>();
		
		try {
			UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");

			nroRegEmpleado=usuarioBean.getNroRegistro();
			ls_anio= String.valueOf( fecha.get(Calendar.YEAR)) ;
			ls_mes= String.valueOf( fecha.get(Calendar.MONTH) +1) ;
			MaestroPersonalBean  maestroPersonal= registroPersonalService.obtenerPersonaxRegistro(nroRegEmpleado.trim());
			WebUtils.setSessionAttribute(request,"maestroPersonalBean",maestroPersonal );
	
			Formatter fmt = new Formatter();
			fmt.format("%02d",Integer.valueOf(ls_mes));
			ls_mes = fmt.toString();
			Map<String, Object> params = new HashMap<String, Object>();
			codEmpleado = maestroPersonal.getCodigoEmpleado();
			codDependencia= registroPersonalService.obtenerUuooNoSupervision(maestroPersonal.getCodigoDependencia(),"4");
			
			//Obtenemos perfiles de Colaborador y Dependencia
			boolean esPerfilColaboradorJefe=registroPersonalService.esPerfilColaboradorJefe(codDependencia,codEmpleado);
			boolean esPerfilColaboradorEncargado=registroPersonalService.esPerfilColaboradorEncargado(codDependencia,codEmpleado);
			boolean esPerfilDependenciaAprobador=requerimientoNoProgramadoService.esPerfilDependenciaAprobador(codDependencia);
			//esPerfilDependenciaAUC=requerimientoNoProgramadoService.esPerfilDependenciaAUC(codDependencia);
			
			log.debug("esPerfilColaboradorJefe: "+esPerfilColaboradorJefe);
			log.debug("esPerfilColaboradorEncargado: "+esPerfilColaboradorEncargado);
			log.debug("esPerfilDependenciaAprobador: "+esPerfilDependenciaAprobador);
			//log.debug("esPerfilDependenciaAUC: "+esPerfilDependenciaAUC);
			
			params=new HashMap<String, Object>();
			params.put("cod_per", codEmpleado);
			params.put("anio_act", ls_anio);
			
			params.put("nom_tabla", "REQUERIMIENTO_NO_PROG");
			ls_next="formConsultaRqnp";
			
			if ((esPerfilColaboradorJefe) || (esPerfilColaboradorEncargado)) {
				if (log.isDebugEnabled()) log.debug("JEFE INMEDIATO ");
				tituloVista =" (JEFE INMEDIATO) ";
				lsRqnp = (List<Map<String, Object>>)this.requerimientoNoProgramadoService.listarRqnpJefe(ls_anio, "-1", null, ls_mes, codEmpleado);
			}else{
				if (log.isDebugEnabled()) log.debug("USUARIO ");
				tituloVista =" (USUARIO) ";
				lsRqnp = (List<Map<String, Object>>) requerimientoNoProgramadoService.listarRqnpUsuario(ls_anio, "-1", null, maestroPersonal.getCodigoEmpleado(), ls_mes,null);
			}
			
			List<Map<String, Object>> lsEstados=(List<Map<String, Object>>) requerimientoNoProgramadoService.listarSysEstados(params);
			List<Map<String, Object>> lsMeses=(List<Map<String, Object>>) requerimientoNoProgramadoService.listarMeses();

			lsNecesidad = (List<Map<String, Object>>) requerimientoNoProgramadoService.listaNecesidad();
			lsTipoNecesidad = (List<Map<String, Object>>) requerimientoNoProgramadoService.listaTipoNecesidad();
			lsFinalidad= (List<Map<String, Object>>) requerimientoNoProgramadoService.listaFinalidad();
	 
			return new ModelAndView(ls_next, "lsrqnp", lsRqnp).addObject("lsEstados", lsEstados).addObject("tituloVista", tituloVista).
			addObject("lsMeses",lsMeses).addObject("anio", ls_anio).addObject("mess",ls_mes).addObject("lsNecesidad", lsNecesidad).
			addObject("lsTipoNecesidad", lsTipoNecesidad).addObject("lsFinalidad",lsFinalidad);
			
		}catch(ServiceException ex){
			log.error("Error en RqnpConsultaController.iniciarConsulta: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}catch(Exception ex){
			log.error("Error en RqnpConsultaController.iniciarConsulta: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}finally{
			if (log.isDebugEnabled()){ log.debug("Fin - RqnpConsultaController.iniciarConsulta");}
		}
	}
	
	/**
     * Buscar los Requerimientos No Programados segun el estado en que se encuentren
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/ 
	public ModelAndView iniciarConsultaBusqueda(HttpServletRequest request, HttpServletResponse response)  {
	    if (log.isDebugEnabled()) log.debug("debug Inicio - RqnpConsultaController.iniciarConsultaBusqueda"); 
	    String ls_anio = "";
	    String cod_estado = "";
	    String ls_registro = "";
	    String ls_mes = "";
	    String codEmpleado = "";
	    String codDependencia = "";

	    List<Map<String, Object>> lsRqnp= new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> lsRqnpAdd= new ArrayList<Map<String,Object>>();
	    Map<String, Object> data 	= 	new HashMap<String, Object>();
	    
	    try { 
		  ls_anio = StringUtils.trim(request.getParameter("txtAnio"));
	      ls_mes = StringUtils.trim(request.getParameter("txtMes"));
	      cod_estado = StringUtils.trim(request.getParameter("txtEstado"));

	      UsuarioBean usuarioBean = (UsuarioBean)WebUtils.getSessionAttribute(request, "usuarioBean");
	      ls_registro = usuarioBean.getNroRegistro();
	      MaestroPersonalBean maestroPersonal = this.registroPersonalService.obtenerPersonaxRegistro(ls_registro);
	      Map<String, Object> params = new HashMap<String, Object>();
	      codEmpleado = maestroPersonal.getCodigoEmpleado();
	      codDependencia = this.registroPersonalService.obtenerUuooNoSupervision(maestroPersonal.getCodigoDependencia(), "4");

	      boolean esPerfilColaboradorJefe = this.registroPersonalService.esPerfilColaboradorJefe(codDependencia, codEmpleado);
	      boolean esPerfilColaboradorEncargado = this.registroPersonalService.esPerfilColaboradorEncargado(codDependencia, codEmpleado);
	      //boolean esPerfilDependenciaAprobador = this.requerimientoNoProgramadoService.esPerfilDependenciaAprobador(codDependencia);

	      log.debug("esPerfilColaboradorJefe: " + esPerfilColaboradorJefe);
	      log.debug("esPerfilColaboradorEncargado: " + esPerfilColaboradorEncargado);
	      //log.debug("esPerfilDependenciaAprobador: " + esPerfilDependenciaAprobador);

	      params = new HashMap<String, Object>();
	      params.put("cod_per", codEmpleado);
	      params.put("anio_act", ls_anio);
	      params.put("nom_tabla", "REQUERIMIENTO_NO_PROG");

	      if ((esPerfilColaboradorJefe) || (esPerfilColaboradorEncargado)) {
	        if (log.isDebugEnabled()) log.debug("JEFE INMEDIATO ");
	      //lsRqnp =  (List<Map<String, Object>>) requerimientoNoProgramadoService.listarRqnpJefe(ls_anio, cod_estado, maestroPersonal.getCodigoSede(),  ls_mes, cod_solicita);
	        lsRqnp = (List)this.requerimientoNoProgramadoService.listarRqnpJefe(ls_anio, cod_estado, null, ls_mes, codEmpleado);
	      } else {
	        if (log.isDebugEnabled()) log.debug("USUARIO ");
	      //lsRqnp = (List<Map<String, Object>>) requerimientoNoProgramadoService.listarRqnpUsuario(ls_anio, cod_estado, maestroPersonal.getCodigoSede(), maestroPersonal.getCodigoEmpleado(), ls_mes,null);
	        lsRqnp = (List)this.requerimientoNoProgramadoService.listarRqnpUsuario(ls_anio, cod_estado, null, maestroPersonal.getCodigoEmpleado(), ls_mes, null);
	      }

	      data.put("lsRqnp", JsonUtil.toString(lsRqnp));
	    } catch (ServiceException ex){
	      log.error("Error en RqnpConsultaController.iniciarConsultaBusqueda: " + ex.getMessage());
	      data.put("error", "Se ha producido un error inesperado. Comuniquese con el administrador.");
	      throw new ServiceException(this, ex);
	    }catch (Exception ex) {
	      log.error("Error en RqnpConsultaController.iniciarConsultaBusqueda: " + ex.getMessage(), ex);
	      data.put("error", "Se ha producido un error inesperado. Comuniquese con el administrador.");
	      throw new ServiceException(this, ex);
	    }
	    finally {
	      if (log.isDebugEnabled()) log.debug("Fin - RqnpConsultaController.iniciarConsultaBusqueda");
	    }
	    return new ModelAndView(this.jsonView, "data", JsonUtil.toString(data));
	  }
	
	
	/**
     * Carga la pagina inicial de afectación presupuestal(metas)
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView modificarRqnpMetas(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("Inicio -  RqnpConsultaController.modificarRqnpMetas");}
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
			log.error("Error en  RqnpConsultaController.modificarRqnpMetas: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en  RqnpConsultaController.modificarRqnpMetas: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()) {log.debug("Fin -  RqnpConsultaController.modificarRqnpMetas");}
		}
	}
	
	

	/**
     * Recuperar Datos de cabecera del Requerimeinto Programado .
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView recuperarDatosCabeceraRQNPJson(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - RqnpConsultaController.recuperarDatosCabeceraRQNPJson");}
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
			log.error("Error en RqnpConsultaController.recuperarDatosCabeceraRQNPJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en RqnpConsultaController.recuperarDatosCabeceraRQNPJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()) {log.debug("Fin - RqnpConsultaController.recuperarDatosCabeceraRQNPJson");}
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
}
