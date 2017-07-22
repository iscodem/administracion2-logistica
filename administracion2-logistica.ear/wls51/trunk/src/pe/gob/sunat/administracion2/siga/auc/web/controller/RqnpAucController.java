package pe.gob.sunat.administracion2.siga.auc.web.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;



import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import javax.jws.soap.SOAPBinding.Use;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.NDC;
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
import pe.gob.sunat.framework.util.date.FechaBean;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.DependenciaBean;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.MaestroContratistasBean;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.MaestroPersonalBean;
import pe.gob.sunat.recurso2.administracion.siga.registro.service.RegistroPersonalService;
import pe.gob.sunat.recurso2.administracion.siga.web.util.BaseController;
import pe.gob.sunat.tecnologia.menu.bean.UsuarioBean;


/**
 * <p>Title: RqnpAucController </p>
 * <p>Description: Clase Controller para la Atención de Requerimientos No Programados</p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: SUNAT</p>
 * @author DPORRASC
 * @version 1.0 
 */


public class RqnpAucController extends BaseController {
	
	private static final Log log = LogFactory.getLog(RqnpAucController.class);
	
	private ReloadableResourceBundleMessageSource 		messageSource;
	//private JsonView 							jsonView;
	//private View 								htmlView;
	private RequerimientoAucService 			requerimientoAucService;
	private RegistroPersonalService 			registroPersonalService;
	private RequerimientoNoProgramadoService 	requerimientoNoProgramadoService;

	private String uploadDir;
	
	/**
     * Carga la Bandeja de Registro de Requerimientos no Programados de AUC
     * @param  request
     * @param  response 
     * @return ModelAndView 
     */
	//Menu Registro RQNP//
	public ModelAndView auciniciarbandeja(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()) {log.debug("debug Inicio - RqnpAucController.auciniciarbandeja");}
		
		String 		lsAnio="";
		Calendar 	fecha =  Calendar.getInstance();
		String 		lsRegistro="";
		String 		is_user_auc="N";
		String 		is_grupo_auc="N";
		String 		depe_no_superv="";
		Map mapUsuario = new HashMap();
		String codEmpleado="";
		String codDependencia="";
		
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
			lsRegistro= usuarioBean.getNroRegistro();
			log.debug("lsRegistro: "+lsRegistro);
			
			MaestroPersonalBean maestroPersonal= registroPersonalService.obtenerPersonaxRegistro(lsRegistro);
				
			if(maestroPersonal != null){
				WebUtils.setSessionAttribute(request,"maestroPersonalBean",maestroPersonal );	
				codEmpleado= maestroPersonal.getCodigoEmpleado();
				codDependencia=maestroPersonal.getCodigoDependencia();  //aqui la dependencia que viene del front
				
				mapUsuario.put("codDependencia", codDependencia);
				mapUsuario.put("codEmpleado", codEmpleado);
				
				depe_no_superv=registroPersonalService.obtenerUuooNoSupervision(maestroPersonal.getCodigoDependencia(), "4");
				log.debug("depe_no_superv: "+depe_no_superv);
				
				if(depe_no_superv==null){
					Exception ex  = new Exception("Existe un problema con su dependencia ó la de su Jefatura, Por favor verifique.");
					request.setAttribute("excepAttr", ex);
					return new ModelAndView("pagErrorSistema");
				}
				
				//params.put("cod_user", usuarioBean.getLogin());
				params.put("nro_reg", lsRegistro.trim());
				params.put("cod_rol", "2001");//ROL AUC
				params.put("cod_dep", depe_no_superv);
				//AGREGADO
				is_user_auc= requerimientoAucService.validaUserAUC(params);
				is_grupo_auc=requerimientoAucService.validaGrupoAUC(params);
				Boolean esEncargadoAuc = Boolean.valueOf(registroPersonalService.esEncargadoAuc(codEmpleado));
				
				log.debug("es jefe: "+registroPersonalService.esJefe(codEmpleado,depe_no_superv));
				log.debug("es is_grupo_auc: "+is_grupo_auc);
				log.debug("es esEncargadoAuc: "+esEncargadoAuc);
				
//				List<Map<String, Object>> lsdatos_auc = new ArrayList<Map<String,Object>>();
			
				//Un Jefe de AUC unicamente puede delegar funciones a otra persona que pertenezca a una AUC
				if ((is_user_auc.equals("S")&&is_grupo_auc.equals("S"))
						|| (registroPersonalService.esJefe(codEmpleado,depe_no_superv)&&is_grupo_auc.equals("S"))
						||esEncargadoAuc.booleanValue()){
					NDC.push(usuarioBean.getLogin()+"-"+usuarioBean.getTicket());
					lsRegistro= usuarioBean.getNroRegistro();
					lsAnio= String.valueOf( fecha.get(Calendar.YEAR));
					
//					Map<String, Object> mapDatos_auc=requerimientoAucService.recuperarDependenciaXuuoo(maestroPersonal.getUuoo());
//					
//					mapDatos_auc.put("tipo_auc", requerimientoNoProgramadoService.obtenerTipoAUC(maestroPersonal.getCodigoDependencia()));
//					log.debug("TIPO AUC:>>>>> " + mapDatos_auc.get("tipo_auc"));
//					lsdatos_auc.add(mapDatos_auc);
//					
//					WebUtils.setSessionAttribute(request,"mapDatos_auc",mapDatos_auc);
					
					@SuppressWarnings("unchecked")
					List<Map<String, Object>> lsRqnp = (List<Map<String, Object>>) requerimientoNoProgramadoService.
					listarRqnpUsuario(lsAnio, "01", maestroPersonal.getCodigoSede(), maestroPersonal.getCodigoEmpleado(),null,"AUC");
					return new ModelAndView("formBandejaRqnpAUC", "lsrqnp", lsRqnp).addObject("mapUsuario", mapUsuario);
					
				}else{
					Exception ex  = new Exception("Ud. no tiene permiso para realizar esta operación");
					request.setAttribute("excepAttr", ex);
					return new ModelAndView("pagErrorSistema");
				}
			}else{
				Exception ex  = new Exception("No existe los datos del Usuario ");
				request.setAttribute("excepAttr", ex);
				return new ModelAndView("pagErrorSistema");
			}
		} 
		catch(ServiceException ex){
			log.error("Error en RqnpAucController.auciniciarbandeja: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en RqnpAucController.auciniciarbandeja: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			NDC.pop();
			NDC.remove();
			if (log.isDebugEnabled()){ log.debug("Fin - RqnpAucController.auciniciarbandeja");}
		}
	}
	
	
	/**
     * Nuevo Requerimiento No Programado
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView nuevoRqnp(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()) log.debug("Inicio -  RqnpAucController.nuevoRqnp");
		String lsAnio="";
		String lsMes="";
		int ll_mes =0;
		//log.debug(" user registro" + usuarioBean.getLogin());
		FechaBean fecha = new FechaBean();
		Calendar fechaCal 		=  Calendar.getInstance();
		List<Map<String, Object>> lsNecesidad= new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> lsTipoNecesidad= new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> lsFinalidad= new ArrayList<Map<String, Object>>();
		UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
		log.debug(" login user" + usuarioBean.getLogin());
		
		Map<String,Object> params = new HashMap<String, Object>();
		Map<String,Object> mapDatos_auc = new HashMap<String, Object>();
		
		String codDependencia = StringUtils.trim(request.getParameter("txtcodDependencia"));
		String flagVariasJefaturasAuc = StringUtils.trim(request.getParameter("txtflagVariasJefaturasAuc"));
		
		try {
			lsAnio= String.valueOf( fechaCal.get(Calendar.YEAR)) ;
			ll_mes=fechaCal.get(Calendar.MONTH);
			ll_mes++;
			
			lsMes =String.valueOf( ll_mes ) ;
			
			String ls_mes_fin = "00".substring(lsMes.length()) + lsMes;
			
			MaestroPersonalBean  maestroPersonal=  (MaestroPersonalBean) WebUtils.getSessionAttribute(request, "maestroPersonalBean");
			if (log.isDebugEnabled()) log.debug(maestroPersonal.getNombre_completo());
			 Map<String, Object> mapRqnp = new HashMap<String, Object>();
			 mapRqnp.put("codigoResposanble", maestroPersonal.getNumero_registro());
			 mapRqnp.put("resposanble", maestroPersonal.getNombre_completo());
			 
			 
			 DependenciaBean dependenciaAUC = new DependenciaBean();
			 String codDependenciaAUC=registroPersonalService.obtenerUuooNoSupervision(maestroPersonal.getCodigoDependencia(),"4");
			 params.put("codDependencia", codDependenciaAUC);
			 dependenciaAUC = this.requerimientoNoProgramadoService.obtenerUuoo(params);
			 
			 
			 if(flagVariasJefaturasAuc.equals("S")){
				DependenciaBean dependenciaRqnp = new DependenciaBean();
				params.put("codDependencia", codDependencia);
				dependenciaRqnp = requerimientoNoProgramadoService.obtenerUuoo(params);
				
				mapRqnp.put("codigoDependencia", dependenciaRqnp.getCod_dep());
				mapRqnp.put("dependencia", dependenciaRqnp.getNom_largo());
				mapRqnp.put("codigoUuoo", dependenciaRqnp.getUuoo());
				
				mapDatos_auc.put("tipo_auc", requerimientoNoProgramadoService.obtenerTipoAUC(dependenciaRqnp.getCod_dep()));
				mapDatos_auc.put("cod_dep",codDependencia);
				mapDatos_auc.put("cod_plaza",dependenciaRqnp.getCod_plaza());
				mapDatos_auc.put("uuoo", dependenciaRqnp.getUuoo());
				mapDatos_auc.put("nomUuoo", dependenciaRqnp.getNom_largo());
			 }else{
				 mapRqnp.put("codigoDependencia", maestroPersonal.getCodigoDependencia());
				 mapRqnp.put("dependencia", maestroPersonal.getDependencia());
				 mapRqnp.put("codigoUuoo", maestroPersonal.getUuoo());
				 mapDatos_auc.put("tipo_auc", requerimientoNoProgramadoService.obtenerTipoAUC(codDependenciaAUC));
				 mapDatos_auc.put("cod_dep", codDependenciaAUC);
				 mapDatos_auc.put("cod_plaza", dependenciaAUC.getCod_plaza());
				 mapDatos_auc.put("uuoo", dependenciaAUC.getUuoo());
				 mapDatos_auc.put("nomUuoo", dependenciaAUC.getNom_largo());
			 }
			 mapRqnp.put("cod_uoc1", maestroPersonal.getCodigoDependencia());
			 mapRqnp.put("fechaRqnp",fecha.getFormatDate("dd/MM/yyyy HH:mm"));
			 log.debug("Anio " +lsAnio + "Mes " + ls_mes_fin);
			 mapRqnp.put("anio_atencion",lsAnio);
			 mapRqnp.put("mes_atencion",ls_mes_fin);
			 /////////////////////////////////////////////////////////
			 mapRqnp.put("ind_vinculo","N");
			 mapRqnp.put("ind_prestamo","N");
			 mapRqnp.put("tipo_vinculo","04");
			 mapRqnp.put("vinculo","01");
			 
			 mapRqnp.put("flagVariasJefaturasAuc", flagVariasJefaturasAuc);
			 mapRqnp.put("codDependencia", codDependencia);
			 /////////////////////////////////////////////////////////
			 lsNecesidad = (List<Map<String, Object>>) requerimientoNoProgramadoService.listaNecesidad();
			 lsTipoNecesidad = (List<Map<String, Object>>) requerimientoNoProgramadoService.listaTipoNecesidad();
			 lsFinalidad= (List<Map<String, Object>>) requerimientoNoProgramadoService.listaFinalidad();
			 WebUtils.setSessionAttribute(request,"mapDatos_auc",mapDatos_auc);
			 
			return new ModelAndView("formRegistroRqnpCabAUC").addObject("mapRqnp", mapRqnp).addObject("lsNecesidad", lsNecesidad).
			addObject("lsTipoNecesidad", lsTipoNecesidad).addObject("lsFinalidad",lsFinalidad) ;
		}
		catch(ServiceException ex){
			log.error("Error en  RqnpAucController.nuevoRqnp: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en  RqnpAucController.nuevoRqnp: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin -  RqnpAucController.nuevoRqnp");}
		}
	}
	
	/**
     * Modificar Requerimiento No Programado
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView modificarRqnp(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("Inicio -  RqnpAucController.modificarRqnp");}
		Map<String, Object> data_uoc1 	= 	new HashMap<String, Object>();
		Map<String, Object> data_uoc2 	= 	new HashMap<String, Object>();
		Map<String, Object> data_uoc3 	= 	new HashMap<String, Object>();
		
		Map<String, Object> mapRqnp 	= 	new HashMap<String, Object>();
		Map<String, Object> mapCatalogo = 	new HashMap<String, Object>();
		Map<String, Object> lsCatalogo 	= 	new HashMap<String, Object>();
		Map<String, Object> mapDatos_auc = new HashMap<String, Object>();
		Map<String, Object> params 	= 	new HashMap<String, Object>();
		String cod_req	=	"";
		String visor	=	"";
		String user="";
		
		String uuoo1="";
		String uuoo2="";
		String uuoo3="";
		String nom_uuoo1= "";
		String nom_uuoo2= "";
		String nom_uuoo3= "";
		
		List<Map<String, Object>> lsNecesidad= new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> lsTipoNecesidad= new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> lsFinalidad= new ArrayList<Map<String, Object>>();
		Map<String, Object> parm = new HashMap<String, Object>();
		
		MaestroPersonalBean personal=new MaestroPersonalBean();
		try {
			UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
			user= usuarioBean.getLogin();
			cod_req = StringUtils.trim(request.getParameter("txtCodigoRqnp"));
			visor	= StringUtils.trim(request.getParameter("txtvisor")); //viene de bandejaAUC (txtvisor=0)

			if (log.isDebugEnabled()){ log.debug("cod_req:"+cod_req);}
			RequerimientoNoProgramadoBean rqnp= requerimientoNoProgramadoService.recuperarRqnpAUC(cod_req);
		
			//Seteando Cabecera
			 mapRqnp.put("codigoRqnp", rqnp.getCodigoRqnp());
			 mapRqnp.put("codigoReqNoProgramado", rqnp.getCodigoReqNoProgramado());
			 mapRqnp.put("codigoResposanble", rqnp.getNumeroRegistro());
			 mapRqnp.put("resposanble", rqnp.getNombreSolicitante());
			 mapRqnp.put("codigoUuoo", rqnp.getUuoo());
			 mapRqnp.put("codigoDependencia", rqnp.getCodigoDependencia());
			 mapRqnp.put("anioProceso", rqnp.getAnioProceso());
			 
			 mapRqnp.put("dependencia", rqnp.getNombreUOSolicitante());
			 mapRqnp.put("fechaRqnp",new SimpleDateFormat("dd/MM/yyyy HH:mm").format(rqnp.getFechaRqnp()));
			 mapRqnp.put("motivoRqnp",rqnp.getMotivoSolicitud());
			 log.debug("MotivoSolicitud: "+rqnp.getMotivoSolicitud());
			 mapRqnp.put("montoRqnp",  rqnp.getMontoRqnp().setScale(2, RoundingMode.HALF_UP) );
			 mapRqnp.put("motivoRechazo", rqnp.getMotivoRechazo());
			
			personal=registroPersonalService.obtenerPersonaxCodigo( rqnp.getCod_contacto().trim());
				
			 mapRqnp.put("cod_contacto", rqnp.getCod_contacto());
			 mapRqnp.put("anexo_contacto", rqnp.getAnexo_contacto());
			 mapRqnp.put("cod_necesidad", rqnp.getCod_necesidad());
			 //mapRqnp.put("cod_tip_nececidad", rqnp.getCod_tip_nececidad());
			 //mapRqnp.put("nom_tip_necesidad", rqnp.getNom_tip_necesidad());
			 mapRqnp.put("nom_contacto", personal.getNombre_completo());
			 mapRqnp.put("reg_contacto", personal.getNumero_registro());
			 mapRqnp.put("cod_finalidad", rqnp.getCod_finalidad());
			 
			//////////////////////////////////////////////////////////////
			mapRqnp.put("tipo_vinculo", rqnp.getTipo_vinculo());
			mapRqnp.put("vinculo", rqnp.getVinculo());
			mapRqnp.put("ind_vinculo", rqnp.getInd_vinculo());
			mapRqnp.put("ind_prestamo", rqnp.getInd_prestamo());
			//////////////////////////////////////////////////////////////
					
			log.debug("tipo_vinculo: " + rqnp.getTipo_vinculo());
			log.debug("vinculo: " + rqnp.getVinculo());
			log.debug("ind_vinculo: " + rqnp.getInd_vinculo());
			log.debug("ind_prestamo: " + rqnp.getInd_prestamo());
			 
			 //(DPORRASC)AGREGADO PARA OBTENER LAS UUOO DE CONFORMIDAD
			 
			 mapRqnp.put("cod_uoc1", rqnp.getCod_uoc1());
			 mapRqnp.put("cod_uoc2", rqnp.getCod_uoc2());
			 mapRqnp.put("cod_uoc3", rqnp.getCod_uoc3());
					 
			 if(data_uoc1!=null){
				 uuoo1= (String) data_uoc1.get("uuoo");
				 nom_uuoo1= (String) data_uoc1.get("nom_dep"); 
			 }
			 
			 if(data_uoc2!=null){
				 uuoo2= (String) data_uoc2.get("uuoo");
				 nom_uuoo2= (String) data_uoc2.get("nom_dep");
			 }
			 
			 if(data_uoc3!=null){
				 uuoo3= (String) data_uoc3.get("uuoo");
				 nom_uuoo3= (String) data_uoc3.get("nom_dep");
			 }
			 
			 if(rqnp.getCod_proveedor()!=null) {
				 parm.clear();
				 parm.put("cod_cont", rqnp.getCod_proveedor().trim());
				 parm.put("user",user);
				log.debug("Prueba: " +rqnp.getCod_proveedor().trim());
				
				 MaestroContratistasBean contratista=registroPersonalService.recuperarContratista(parm);
					 if (contratista !=null){
						mapRqnp.put("cod_cont", contratista.getCod_cont());
						mapRqnp.put("num_ruc", contratista.getNum_ruc());
						mapRqnp.put("raz_social", contratista.getRaz_social()); 
						
						log.debug("CONTRATISTA  " + contratista.getRaz_social());
					 }
			 }else{
				 log.debug("Esg etCod_proveedor Nulo");
			 }
			 mapRqnp.put("obs_justificacion", rqnp.getObs_justificacion());
			 mapRqnp.put("obs_plazo_entrega", rqnp.getObs_plazo_entrega());
			 mapRqnp.put("obs_lugar_entrega", rqnp.getObs_lugar_entrega());
			 mapRqnp.put("obs_dir_entrega", rqnp.getObs_dir_entrega());
			 
			 //AGREGAR DETALLE UOC1 - UOC2 - UOC3
			 mapRqnp.put("uuoo1", uuoo1);
			 mapRqnp.put("nom_uuoo1", nom_uuoo1);
			 
			 mapRqnp.put("uuoo2", uuoo2);
			 mapRqnp.put("nom_uuoo2", nom_uuoo2);
			 
			 mapRqnp.put("uuoo3", uuoo3);
			 mapRqnp.put("nom_uuoo3", nom_uuoo3);
				
			 //Seteando Detalle------------------------------------------------------------
			 List<Map<String, Object>> listaBienes = (List<Map<String, Object>>) rqnp.getListaBienes();

			 lsNecesidad = (List<Map<String, Object>>) requerimientoNoProgramadoService.listaNecesidad();
			 lsTipoNecesidad = (List<Map<String, Object>>) requerimientoNoProgramadoService.listaTipoNecesidad();
			 lsFinalidad= (List<Map<String, Object>>) requerimientoNoProgramadoService.listaFinalidad();
			 
			 DependenciaBean dependenciaAUC = new DependenciaBean();
			 String codDependenciaAUC=registroPersonalService.obtenerUuooNoSupervision(rqnp.getCodigoDependencia(),"4");
			 params.put("codDependencia", codDependenciaAUC);
			 dependenciaAUC = this.requerimientoNoProgramadoService.obtenerUuoo(params);
			 
			 mapDatos_auc.put("tipo_auc", requerimientoNoProgramadoService.obtenerTipoAUC(codDependenciaAUC));
			 mapDatos_auc.put("cod_dep", codDependenciaAUC);
			 mapDatos_auc.put("cod_plaza", dependenciaAUC.getCod_plaza());
			 mapDatos_auc.put("uuoo", dependenciaAUC.getUuoo());
			 mapDatos_auc.put("nomUuoo", dependenciaAUC.getNom_largo());
			 
			 WebUtils.setSessionAttribute(request,"mapDatos_auc",mapDatos_auc);
			 
			return new ModelAndView("formRegistroRqnpDetalleAUC").addObject("mapRqnp", mapRqnp).
			addObject("mapCatalogo", mapCatalogo).addObject("lsCatalogo",lsCatalogo).
			addObject("listaBienes",listaBienes).addObject("visor", visor).addObject("lsNecesidad", lsNecesidad).
			addObject("lsTipoNecesidad", lsTipoNecesidad).addObject("lsFinalidad", lsFinalidad).addObject("mapDatos_auc",mapDatos_auc);
		} 
		catch(ServiceException ex){
			log.error("Error en  RqnpAucController.modificarRqnp: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en  RqnpAucController.modificarRqnp: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin -  RqnpAucController.modificarRqnp");}
		}
	}

	
	
	/**
     * Modificar Requerimiento No Programado
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView modificarRqnpAUC(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("Inicio -  RqnpAucController.modificarRqnpAUC");}
		Map<String, Object> data_uoc1 	= 	new HashMap<String, Object>();
		Map<String, Object> data_uoc2 	= 	new HashMap<String, Object>();
		Map<String, Object> data_uoc3 	= 	new HashMap<String, Object>();
		
		Map<String, Object> mapRqnp 	= 	new HashMap<String, Object>();
		Map<String, Object> mapCatalogo = 	new HashMap<String, Object>();
		Map<String, Object> lsCatalogo 	= 	new HashMap<String, Object>();
		String cod_req	=	"";
		String visor	=	"";
		String user="";
		
		String uuoo1="";
		String uuoo2="";
		String uuoo3="";
		String nom_uuoo1= "";
		String nom_uuoo2= "";
		String nom_uuoo3= "";
		
		List<Map<String, Object>> lsNecesidad= new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> lsTipoNecesidad= new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> lsFinalidad= new ArrayList<Map<String, Object>>();
		Map<String, Object> parm = new HashMap<String, Object>();
		MaestroPersonalBean personal=new MaestroPersonalBean();
		MaestroPersonalBean miembroComiteAuTit=new MaestroPersonalBean();
		MaestroPersonalBean miembroComiteAuSup=new MaestroPersonalBean();
		MaestroPersonalBean miembroComiteTecTit=new MaestroPersonalBean();
		MaestroPersonalBean miembroComiteTecSup=new MaestroPersonalBean();
		try {
			UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
			user= usuarioBean.getLogin();
			cod_req = StringUtils.trim(request.getParameter("txtCodigoRqnp"));
			visor	= StringUtils.trim(request.getParameter("txtvisor")); //viene de bandejaAUC (txtvisor=0)

			if (log.isDebugEnabled()){ log.debug("cod_req:"+cod_req);}
			RequerimientoNoProgramadoBean rqnp= requerimientoNoProgramadoService.recuperarRqnpAUC(cod_req);
		
			//Seteando Cabecera
			 mapRqnp.put("codigoRqnp", rqnp.getCodigoRqnp());
			 mapRqnp.put("codigoReqNoProgramado", rqnp.getCodigoReqNoProgramado());
			 mapRqnp.put("codigoResposanble", rqnp.getNumeroRegistro());
			 mapRqnp.put("resposanble", rqnp.getNombreSolicitante());
			 mapRqnp.put("codigoUuoo", rqnp.getUuoo());
			 mapRqnp.put("codigoDependencia", rqnp.getCodigoDependencia());
			 mapRqnp.put("anioProceso", rqnp.getAnioProceso());
			 
			 mapRqnp.put("dependencia", rqnp.getNombreUOSolicitante());
			 mapRqnp.put("fechaRqnp",new SimpleDateFormat("dd/MM/yyyy HH:mm").format(rqnp.getFechaRqnp()));
			 mapRqnp.put("motivoRqnp",rqnp.getMotivoSolicitud());
			 log.debug("MotivoSolicitud: "+rqnp.getMotivoSolicitud());
			 mapRqnp.put("montoRqnp",  rqnp.getMontoRqnp().setScale(2, RoundingMode.HALF_UP) );
			 mapRqnp.put("motivoRechazo", rqnp.getMotivoRechazo());
			
			personal=registroPersonalService.obtenerPersonaxCodigo( rqnp.getCod_contacto().trim());
				
			 mapRqnp.put("cod_contacto", rqnp.getCod_contacto());
			 mapRqnp.put("anexo_contacto", rqnp.getAnexo_contacto());
			 mapRqnp.put("cod_necesidad", rqnp.getCod_necesidad());
			 //mapRqnp.put("cod_tip_nececidad", rqnp.getCod_tip_nececidad());
			 //mapRqnp.put("nom_tip_necesidad", rqnp.getNom_tip_necesidad());
			 mapRqnp.put("nom_contacto", personal.getNombre_completo());
			 mapRqnp.put("reg_contacto", personal.getNumero_registro());
			 mapRqnp.put("cod_finalidad", rqnp.getCod_finalidad());
			 
			 mapRqnp.put("anio_atencion", rqnp.getAnio_atencion());
			 mapRqnp.put("mes_atencion", rqnp.getMes_atencion());
			 mapRqnp.put("tipo_vinculo", rqnp.getTipo_vinculo());
			 mapRqnp.put("vinculo", rqnp.getVinculo());
			
			mapRqnp.put("ind_vinculo", rqnp.getInd_vinculo());
			mapRqnp.put("ind_prestamo", rqnp.getInd_prestamo());
			
			 //(DPORRASC)AGREGADO PARA SEGUIMIENTO
			 if (log.isDebugEnabled()){ 
				 log.debug("tipo_vinculo: "+rqnp.getTipo_vinculo());
				 log.debug("vinculo: "+rqnp.getVinculo());}
			 
			 //rqnp.getCod_proveedor()
			 
			 //(DPORRASC)AGREGADO PARA OBTENER LAS UUOO DE CONFORMIDAD
			 
			 mapRqnp.put("cod_uoc1", rqnp.getCod_uoc1());
			 mapRqnp.put("cod_uoc2", rqnp.getCod_uoc2());
			 mapRqnp.put("cod_uoc3", rqnp.getCod_uoc3());
			 
			 //RECUPERANDO COMITE
			 //INDICADOR COMITE
			 
			 mapRqnp.put("ind_comite", rqnp.getInd_comite()==null?"":rqnp.getInd_comite());
			 mapRqnp.put("ind_tec_tit", rqnp.getInd_tec_tit()==null?"":rqnp.getInd_tec_tit());
			 mapRqnp.put("ind_tec_sup", rqnp.getInd_tec_sup()==null?"":rqnp.getInd_tec_sup());
			 mapRqnp.put("cod_au_tit", rqnp.getCod_au_tit()==null?"":rqnp.getCod_au_tit());
			 mapRqnp.put("cod_au_sup", rqnp.getCod_au_sup()==null?"":rqnp.getCod_au_sup());
			 mapRqnp.put("cod_tec_tit", rqnp.getCod_tec_tit()==null?"":rqnp.getCod_tec_tit());
			 mapRqnp.put("cod_tec_sup", rqnp.getCod_tec_sup()==null?"":rqnp.getCod_tec_sup());
			 mapRqnp.put("nom_tec_tit", rqnp.getNom_tec_tit()==null?"":rqnp.getNom_tec_tit());
			 mapRqnp.put("nom_tec_sup", rqnp.getNom_tec_sup()==null?"":rqnp.getNom_tec_sup());
			 
			 
			 //RECUPERANDO MIEMBROS DEL COMITE
			 miembroComiteAuTit=registroPersonalService.obtenerPersonaxCodigo(rqnp.getCod_au_tit()==null?"":rqnp.getCod_au_tit());
			 miembroComiteAuSup=registroPersonalService.obtenerPersonaxCodigo(rqnp.getCod_au_sup()==null?"":rqnp.getCod_au_sup());
			 
			 //////////////////
			 
			 if(miembroComiteAuTit!=null){
				 mapRqnp.put("nombre_au_tit", miembroComiteAuTit.getNombre_completo()==null?"":miembroComiteAuTit.getNombre_completo());
				 mapRqnp.put("reg_au_tit", miembroComiteAuTit.getNumero_registro()==null?"":miembroComiteAuTit.getNumero_registro());
				}else{
					mapRqnp.put("nombre_au_tit", "");
					mapRqnp.put("reg_au_tit", "");
				}
				
				if(miembroComiteAuSup!=null){
					mapRqnp.put("nombre_au_sup", miembroComiteAuSup.getNombre_completo()==null?"":miembroComiteAuSup.getNombre_completo());
					mapRqnp.put("reg_au_sup", miembroComiteAuSup.getNumero_registro()==null?"":miembroComiteAuSup.getNumero_registro());
				}else{
					mapRqnp.put("nombre_au_sup", "");
					mapRqnp.put("reg_au_sup", "");
				}
				
				 
				if(rqnp.getCod_tec_tit()!=null ){
					 miembroComiteTecTit=registroPersonalService.obtenerPersonaxCodigo(rqnp.getCod_tec_tit().trim());
					 mapRqnp.put("nombre_tec_tit", miembroComiteTecTit.getNombre_completo());
					 mapRqnp.put("reg_tec_tit", miembroComiteTecTit.getNumero_registro());
				}else if (rqnp.getNom_tec_tit()!=null){
					mapRqnp.put("nombre_tec_tit", rqnp.getNom_tec_tit());
					mapRqnp.put("reg_tec_tit", "");
				}else{
					mapRqnp.put("nombre_tec_tit", "");
					mapRqnp.put("reg_tec_tit", ""); 
					 }
				
				 
				if(rqnp.getCod_tec_sup()!=null){
					 miembroComiteTecSup=registroPersonalService.obtenerPersonaxCodigo(rqnp.getCod_tec_sup().trim());
					 mapRqnp.put("nombre_tec_sup", miembroComiteTecSup.getNombre_completo());
					 mapRqnp.put("reg_tec_sup", miembroComiteTecSup.getNumero_registro());
				 }else if (rqnp.getNom_tec_sup()!=null){
					 mapRqnp.put("nombre_tec_sup", rqnp.getNom_tec_sup());
					 mapRqnp.put("reg_tec_sup", "");
				 }else{
					 mapRqnp.put("nombre_tec_sup", "");
					 mapRqnp.put("reg_tec_sup", ""); 
					 }
			 ///////////////////
				
			 //AGREGADO DPORRASC (RECUPERAR DEPENDENCIAS DE UOC)
			 data_uoc1=requerimientoAucService.recuperarDependenciaXcod(rqnp.getCod_uoc1());
			 data_uoc2=requerimientoAucService.recuperarDependenciaXcod(rqnp.getCod_uoc2());
			 data_uoc3=requerimientoAucService.recuperarDependenciaXcod(rqnp.getCod_uoc3());
			 
			 if(data_uoc1!=null){
				 uuoo1= (String) data_uoc1.get("uuoo");
				 nom_uuoo1= (String) data_uoc1.get("nom_dep"); 
			 }
			 
			 if(data_uoc2!=null){
				 uuoo2= (String) data_uoc2.get("uuoo");
				 nom_uuoo2= (String) data_uoc2.get("nom_dep");
			 }
			 
			 if(data_uoc3!=null){
				 uuoo3= (String) data_uoc3.get("uuoo");
				 nom_uuoo3= (String) data_uoc3.get("nom_dep");
			 }
			 
			 if(rqnp.getCod_proveedor()!=null) {
				 parm.clear();
				 parm.put("cod_cont", rqnp.getCod_proveedor().trim());
				 parm.put("user",user);
				log.debug("Prueba: " +rqnp.getCod_proveedor().trim());
				
				 MaestroContratistasBean contratista=registroPersonalService.recuperarContratista(parm);
					 if (contratista !=null){
						mapRqnp.put("cod_cont", contratista.getCod_cont());
						mapRqnp.put("num_ruc", contratista.getNum_ruc());
						mapRqnp.put("raz_social", contratista.getRaz_social()); 
						
						log.debug("CONTRATISTA  " + contratista.getRaz_social());
					 }
			 }else{
				 log.debug("Esg etCod_proveedor Nulo");
			 }
			 mapRqnp.put("obs_justificacion", rqnp.getObs_justificacion());
			 
			 mapRqnp.put("obs_plazo_entrega", rqnp.getObs_plazo_entrega());
			 log.debug("obs_plazo_entrega" +   rqnp.getObs_plazo_entrega());
			 
			 mapRqnp.put("obs_lugar_entrega", rqnp.getObs_lugar_entrega());
			 mapRqnp.put("obs_dir_entrega", rqnp.getObs_dir_entrega());
			 
			 //AGREGAR DETALLE UOC1 - UOC2 - UOC3
			 mapRqnp.put("num_uuoo1", uuoo1);
			 mapRqnp.put("nom_uuoo1", nom_uuoo1);
			 
			 mapRqnp.put("num_uuoo2", uuoo2);
			 mapRqnp.put("nom_uuoo2", nom_uuoo2);
			 
			 mapRqnp.put("num_uuoo3", uuoo3);
			 mapRqnp.put("nom_uuoo3", nom_uuoo3);
			 
			 
			 //Seteando Detalle------------------------------------------------------------
			 List<Map<String, Object>> listaBienes = (List<Map<String, Object>>) rqnp.getListaBienes();

			 lsNecesidad = (List<Map<String, Object>>) requerimientoNoProgramadoService.listaNecesidad();
			 lsTipoNecesidad = (List<Map<String, Object>>) requerimientoNoProgramadoService.listaTipoNecesidad();
			 lsFinalidad= (List<Map<String, Object>>) requerimientoNoProgramadoService.listaFinalidad();
			 
			return new ModelAndView("formRegistroRqnpCabAUCModi").addObject("mapRqnp", mapRqnp).
			addObject("mapCatalogo", mapCatalogo).addObject("lsCatalogo",lsCatalogo).
			addObject("listaBienes",listaBienes).addObject("visor", visor).addObject("lsNecesidad", lsNecesidad).
			addObject("lsTipoNecesidad", lsTipoNecesidad).addObject("lsFinalidad", lsFinalidad);
		} 
		catch(ServiceException ex){
			log.error("Error en  RqnpAucController.modificarRqnpAUC: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en  RqnpAucController.modificarRqnpAUC: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin -  RqnpAucController.modificarRqnpAUC");}
		}
	}

	
	/**
     * Envia el Informe a la INA
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView enviarInformeRqnp(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("Inicio -  RqnpAucController.enviarInformeRqnp");}
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> mapRqnp = new HashMap<String, Object>();
	
		Map<String, Object> lsCatalogo = new HashMap<String, Object>();
		String cod_req="";
		String cod_bien="";
		String visor="";
		String user="";
		try {
			
			cod_req = StringUtils.trim(request.getParameter("txtCodigoRqnp"));
			cod_bien = StringUtils.trim(request.getParameter("txtCodigoBien"));
			visor	= StringUtils.trim(request.getParameter("txtvisor"));
			
			UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
			user = usuarioBean.getLogin();
			
			params.put("cod_rqnp", cod_req);
			params.put("cod_bien", cod_bien);
			params.put("user", user);
			params.put("ind_estado", "05");
			requerimientoNoProgramadoService.registrarActualizaEstadoItem(params);
			RequerimientoNoProgramadoBean rqnp= requerimientoNoProgramadoService.recuperarRqnpAUC(cod_req);
			//Seteando Cabecera
			 mapRqnp.put("codigoRqnp", rqnp.getCodigoRqnp());
			 mapRqnp.put("codigoReqNoProgramado", rqnp.getCodigoReqNoProgramado());
			 mapRqnp.put("codigoResposanble", rqnp.getNumeroRegistro());
			 mapRqnp.put("resposanble", rqnp.getNombreSolicitante());
			 mapRqnp.put("codigoUuoo", rqnp.getUuoo());
			 mapRqnp.put("codigoDependencia", rqnp.getCodigoDependencia());
			 mapRqnp.put("anioProceso", rqnp.getAnioProceso());
			 mapRqnp.put("dependencia", rqnp.getNombreUOSolicitante());
			 mapRqnp.put("fechaRqnp",new SimpleDateFormat("dd/MM/yyyy HH:mm").format(rqnp.getFechaRqnp()));
			 mapRqnp.put("motivoRqnp",rqnp.getMotivoSolicitud());
			 mapRqnp.put("montoRqnp",  rqnp.getMontoRqnp().setScale(2, RoundingMode.HALF_UP) );
			 //Seteando Detalle
			 List<Map<String, Object>> listaBienes = (List<Map<String, Object>>) rqnp.getListaBienes();
			 
			return new ModelAndView("formRqnpDetalleInforme").addObject("mapRqnp", mapRqnp).
			addObject("lsCatalogo",lsCatalogo).
			addObject("listaBienes",listaBienes).addObject("visor", visor);
			
		} 
		catch(ServiceException ex){
			log.error("Error en  RqnpAucController.enviarInformeRqnp: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en  RqnpAucController.enviarInformeRqnp: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()) {log.debug("Fin -  RqnpAucController.enviarInformeRqnp");}
		}
	}
	
	
	/**
     * Registra la Cabecera del RQNP, genera el numero de secuencia.
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView registrarCabRqnp(HttpServletRequest request, HttpServletResponse response) {
		 if (log.isDebugEnabled()) {log.debug("Inicio - RqnpAucController.registrarCabRqnp");}
		
		 Map<String, Object> params = new HashMap<String, Object>();
		 
		 Map<String, Object> data_uoc1 	= 	new HashMap<String, Object>();
		 Map<String, Object> data_uoc2 	= 	new HashMap<String, Object>();
		 Map<String, Object> data_uoc3 	= 	new HashMap<String, Object>();
		 Map<String, Object> mapDatos_auc = new HashMap<String, Object>();
		 
		 boolean okRunning = false;
		 Map<String, Object>  mapRqnp 	= new HashMap<String, Object> ();
		 Map<String, Object> parm = new HashMap<String, Object>();
		 List<Map<String, Object>> lsNecesidad= new ArrayList<Map<String, Object>>();
		 List<Map<String, Object>> lsTipoNecesidad= new ArrayList<Map<String, Object>>();
		 List<Map<String, Object>> lsFinalidad= new ArrayList<Map<String, Object>>();
		 
		 String cod_req	="";
		 String user	="";
		 String visor	="";
		 String tipo_auc="";
		 
		 String uuoo1="";
		 String uuoo2="";
	 	 String uuoo3="";
		 String nom_uuoo1= "";
		 String nom_uuoo2= "";
		 String nom_uuoo3= "";

		try {
			
			UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
			user = usuarioBean.getLogin();
			mapDatos_auc = (HashMap<String, Object>) WebUtils.getSessionAttribute(request, "mapDatos_auc");
			log.debug("tipo_auc: " + mapDatos_auc.get("tipo_auc") );
			
			MaestroPersonalBean  maestroPersonal=  (MaestroPersonalBean) WebUtils.getSessionAttribute(request, "maestroPersonalBean");
			
			log.debug(" codi registro: " + usuarioBean.getNroRegistro());
			log.debug(" user registro: " + usuarioBean.getLogin());
			
			//MaestroPersonalBean maestroPersonal = registroPersonalService.obtenerPersonaxRegistro(usuarioBean.getNroRegistro()); 
			//MaestroPersonalBean  maestroPersonalTem=null;
			if (maestroPersonal ==null){
				log.debug("MAESTRO PERSONAL NULLLOOOO");
			}
			
			String motivoSol = StringUtils.trim(request.getParameter("txtMotivo"));
			String cod_contacto = StringUtils.trim(request.getParameter("txtCod_contacto"));
			String anexo_contacto = StringUtils.trim(request.getParameter("txtAnexo_contacto"));
			String cod_necesidad = StringUtils.trim(request.getParameter("txtCod_necesidad"));
			//String cod_tip_nececidad = "01";
			//String nom_tip_necesidad = StringUtils.trim(request.getParameter("txtNom_tip_necesidad"));
			
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
			String anio_atencion = StringUtils.trim(request.getParameter("txtAnio_atencion"));
			String mes_atencion = StringUtils.trim(request.getParameter("txtMes_atencion"));
			String tipo_vinculo = StringUtils.trim(request.getParameter("txtTipo_vinculo"));
			String vinculo = StringUtils.trim(request.getParameter("txtVinculo"));
			
			String ind_vinculo = StringUtils.trim(request.getParameter("jtipoRegistroVinculo"));
			String ind_prestamo = StringUtils.trim(request.getParameter("jtipoRegistroPrestamo"));
			//AGREGADO: DPORRASC
			String cod_uoc1 = StringUtils.trim(request.getParameter("txtCod_UUOO1"));
			String cod_uoc2 = StringUtils.trim(request.getParameter("txtCod_UUOO2"));
			String cod_uoc3 = StringUtils.trim(request.getParameter("txtCod_UUOO3"));
			
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
			
			//maestroPersonalTem = registroPersonalService.obtenerPersonaxRegistro(cod_contacto.toUpperCase());
		
			visor	= StringUtils.trim(request.getParameter("txtvisor"));
			String codDependencia = StringUtils.trim(request.getParameter("txtcodDependencia"));
			String flagVariasJefaturasAuc	= StringUtils.trim(request.getParameter("txtflagVariasJefaturasAuc"));
			
			params.put("motivoSolicitud", motivoSol);
			params.put("cod_contacto", cod_contacto);
			params.put("anexo_contacto", anexo_contacto);
			params.put("cod_necesidad", cod_necesidad);
			//params.put("cod_tip_nececidad", cod_tip_nececidad);
			//params.put("nom_tip_necesidad", nom_tip_necesidad);
			
			//AGREGADO PARA IDENTIFICAR EL REGISTRO DE LAS AUC
			params.put("ind_registropor", "AUC");
			
			params.put("cod_finalidad", cod_finalidad);
			params.put("cod_proveedor", cod_proveedor);
			params.put("obs_justificacion", obs_justificacion);
			params.put("obs_plazo_entrega", obs_plazo_entrega);
			params.put("obs_lugar_entrega", obs_lugar_entrega);
			params.put("obs_dir_entrega", obs_dir_entrega);
			params.put("anio_atencion", anio_atencion);
			params.put("mes_atencion", mes_atencion);
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
			
			//INICIO AGREGADO: DPORRASC
			if(cod_uoc1.equals("")){
				params.put("cod_uoc1", maestroPersonal.getCodigoDependencia());
			}else{
				params.put("cod_uoc1", cod_uoc1);
			}
			
			//params.put("cod_uoc1", cod_uoc1);
			params.put("cod_uoc2", cod_uoc2);
			params.put("cod_uoc3", cod_uoc3);
			
			
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
			
			
			if(flagVariasJefaturasAuc.equals("S")){
				DependenciaBean dependenciaRqnp = new DependenciaBean();
				params.put("codDependencia", codDependencia);
				dependenciaRqnp = requerimientoNoProgramadoService.obtenerUuoo(params);
				
				params.put("uuoo", dependenciaRqnp.getUuoo());
				params.put("codigoDependencia", dependenciaRqnp.getCod_dep());
			 }else{
				params.put("uuoo", maestroPersonal.getUuoo());
				params.put("codigoDependencia", maestroPersonal.getCodigoDependencia());
				//mapRqnp.put("dependencia", maestroPersonal.getDependencia());
				//mapRqnp.put("codigoUuoo", maestroPersonal.getUuoo());
			 }
			
			
			params.put("codigoEmpleado", maestroPersonal.getCodigoEmpleado());
			params.put("codigoSede", maestroPersonal.getCodigoSede());
			params.put("user", user);
			
			//(ORIGINAL)cod_req=requerimientoNoProgramadoService.registrarCabRqnp(params);
			cod_req=requerimientoNoProgramadoService.registrarCabRqnpAUC(params);

			RequerimientoNoProgramadoBean rqnp= requerimientoNoProgramadoService.recuperarRqnpAUC(cod_req);
			
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
			
			mapRqnp.put("anio_atencion", rqnp.getAnio_atencion());
			mapRqnp.put("mes_atencion", rqnp.getMes_atencion());
			mapRqnp.put("tipo_vinculo", rqnp.getTipo_vinculo());
			mapRqnp.put("vinculo", rqnp.getVinculo());
			
			MaestroPersonalBean personal =registroPersonalService.obtenerPersonaxCodigo( rqnp.getCod_contacto().trim());
			
			//AGREGADO PARA OBTENER EL TIPO DE AUC
			tipo_auc=(String)requerimientoNoProgramadoService.obtenerTipoAUC(usuarioBean.getCodDepend());
			
			mapRqnp.put("cod_contacto", rqnp.getCod_contacto());
			mapRqnp.put("anexo_contacto", rqnp.getAnexo_contacto());
			mapRqnp.put("cod_necesidad", rqnp.getCod_necesidad());
			mapRqnp.put("cod_tip_nececidad", rqnp.getCod_tip_nececidad());
			mapRqnp.put("nom_tip_necesidad", rqnp.getNom_tip_necesidad());
			mapRqnp.put("nom_contacto", personal.getNombre_completo());
			mapRqnp.put("reg_contacto", personal.getNumero_registro());
			
			mapRqnp.put("cod_finalidad", rqnp.getCod_finalidad());
			
			//AGREGADO DPORRASC (RECUPERAR DEPENDENCIAS DE UOC)
			 data_uoc1=requerimientoAucService.recuperarDependenciaXcod(rqnp.getCod_uoc1());
			 data_uoc2=requerimientoAucService.recuperarDependenciaXcod(rqnp.getCod_uoc2());
			 data_uoc3=requerimientoAucService.recuperarDependenciaXcod(rqnp.getCod_uoc3());
			 
			if(data_uoc1!=null){
				 uuoo1= (String) data_uoc1.get("uuoo");
				 nom_uuoo1= (String) data_uoc1.get("nom_dep"); 
			 }
			 
			 if(data_uoc2!=null){
				 uuoo2= (String) data_uoc2.get("uuoo");
				 nom_uuoo2= (String) data_uoc2.get("nom_dep");
			 }
			 
			 if(data_uoc3!=null){
				 uuoo3= (String) data_uoc3.get("uuoo");
				 nom_uuoo3= (String) data_uoc3.get("nom_dep");
			 }
			
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
			 
			////////////////////////////////////////////////////////////
			mapRqnp.put("ind_vinculo", rqnp.getInd_vinculo());
			mapRqnp.put("ind_prestamo", rqnp.getInd_prestamo());
			//////////////////////////////////////////////////////////////
			 
			 mapRqnp.put("cod_uoc1", rqnp.getCod_uoc1());
			 mapRqnp.put("cod_uoc2", rqnp.getCod_uoc2());
			 mapRqnp.put("cod_uoc3", rqnp.getCod_uoc3());
			 mapRqnp.put("tipo_auc", tipo_auc); //TIPO DE AUC(TECNICA O DESCONCENTRADA)
			 
			//AGREGAR DETALLE UOC1 - UOC2 - UOC3
			 mapRqnp.put("uuoo1", uuoo1);
			 mapRqnp.put("nom_uuoo1", nom_uuoo1);
			 
			 mapRqnp.put("uuoo2", uuoo2);
			 mapRqnp.put("nom_uuoo2", nom_uuoo2);
			 
			 mapRqnp.put("uuoo3", uuoo3);
			 mapRqnp.put("nom_uuoo3", nom_uuoo3); 
			 
			lsFinalidad= (List<Map<String, Object>>) requerimientoNoProgramadoService.listaFinalidad();
			lsNecesidad = (List<Map<String, Object>>) requerimientoNoProgramadoService.listaNecesidad();
			lsTipoNecesidad = (List<Map<String, Object>>) requerimientoNoProgramadoService.listaTipoNecesidad();	
		} 
		catch (ServiceException ex) {
			log.error("Error en RqnpAucController.registrarCabRqnp: " + ex.getMessage());
			request.setAttribute("showErrorMessage", "1");
			request.setAttribute("hideAccionMessage", "1");
			request.setAttribute("excepAttr", ex);
			okRunning=false;
		} 
		catch (Exception ex) {
			log.error("Error en RqnpAucController.registrarCabRqnp: " + ex.getMessage(), ex);
		} 
		finally {
			if (log.isDebugEnabled()){ log.debug("Fin - RqnpAucController.registrarCabRqnp");}
		}
		
		if(!okRunning){
			return new ModelAndView("pagErrorSistema");
		}
		
		//PANTALLA QUE MUESTRA DESPUES DE GRABAR LA CABECERA DE REQUERIMIENTO NO PROGRAMADO
		return new ModelAndView("formRegistroRqnpDetalleAUC").addObject("mapRqnp", mapRqnp).addObject("visor", visor).
		addObject("lsNecesidad", lsNecesidad).
		addObject("lsTipoNecesidad", lsTipoNecesidad).addObject("lsFinalidad", lsFinalidad).addObject("mapDatos_auc",mapDatos_auc);
	}
	
	
	
	/**
     * Registra la Cabecera del RQNP, genera el numero de secuencia.
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView registrarCabRqnpModi(HttpServletRequest request, HttpServletResponse response) {
		 if (log.isDebugEnabled()) {log.debug("Inicio - RqnpAucController.registrarCabRqnpModi");}
		
		 
		 Map<String, Object> params = new HashMap<String, Object>();
		 
		 Map<String, Object> data_uoc1 	= 	new HashMap<String, Object>();
		 Map<String, Object> data_uoc2 	= 	new HashMap<String, Object>();
		 Map<String, Object> data_uoc3 	= 	new HashMap<String, Object>();
		 
		 boolean okRunning = false;
		 Map<String, Object>  mapRqnp 	= new HashMap<String, Object> ();
		 Map<String, Object> parm = new HashMap<String, Object>();
		 List<Map<String, Object>> lsNecesidad= new ArrayList<Map<String, Object>>();
		 List<Map<String, Object>> lsBienes= new ArrayList<Map<String, Object>>();
		 List<Map<String, Object>> lsTipoNecesidad= new ArrayList<Map<String, Object>>();
		 List<Map<String, Object>> lsFinalidad= new ArrayList<Map<String, Object>>();
		 Map<String, Object> mapDatos_auc = new HashMap<String, Object>();
		 
	
		 String user	="";
		 String visor	="";
		 String tipo_auc="";
		 
		 String uuoo1="";
		 String uuoo2="";
	 	 String uuoo3="";
		 String nom_uuoo1= "";
		 String nom_uuoo2= "";
		 String nom_uuoo3= "";

		try {
			
			UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
			mapDatos_auc = (HashMap<String, Object>) WebUtils.getSessionAttribute(request, "mapDatos_auc");
			log.debug("tipo_auc" + mapDatos_auc.get("tipo_auc") );
			
			user = usuarioBean.getLogin();
					
			//MaestroPersonalBean  maestroPersonal=  (MaestroPersonalBean) WebUtils.getSessionAttribute(request, "maestroPersonalBean");
			//usuarioBean.setNroRegistro("2303");
			//usuarioBean.setLogin("MMOSTACE");
			
			log.debug(" codi registro" + usuarioBean.getNroRegistro());
			log.debug(" user registro" + usuarioBean.getLogin());
			
			MaestroPersonalBean maestroPersonal = registroPersonalService.obtenerPersonaxRegistro(usuarioBean.getNroRegistro());
			
			//MaestroPersonalBean  maestroPersonalTem=null;
			if (maestroPersonal ==null){
				log.debug("MAESTRO PERSONAL NULLLOOOO");
			}
			
			String cod_req = StringUtils.trim(request.getParameter("txtCodigoRqnp"));
			
			
			String cod_contacto = StringUtils.trim(request.getParameter("txtCod_contacto"));
			String anexo_contacto = StringUtils.trim(request.getParameter("txtAnexo_contacto"));
			String num_ruc = StringUtils.trim(request.getParameter("txtNum_ruc_prov"));
			String cod_proveedor="";
			if (!num_ruc.equals("")){
				cod_proveedor = StringUtils.trim(request.getParameter("txtCod_proveedor"));
			}
			String obs_justificacion = StringUtils.trim(request.getParameter("txtObs_justificacion"));
			String obs_plazo_entrega = StringUtils.trim(request.getParameter("txtObs_plazo_entrega"));
			String obs_lugar_entrega = StringUtils.trim(request.getParameter("txtObs_lugar_entrega"));
			String obs_dir_entrega = StringUtils.trim(request.getParameter("txtObs_dir_entrega"));
			String cod_necesidad = StringUtils.trim(request.getParameter("txtCod_necesidad"));
			//String cod_tip_nececidad = "01";
			//String nom_tip_necesidad = StringUtils.trim(request.getParameter("txtNom_tip_necesidad"));
		
			String cod_finalidad = StringUtils.trim(request.getParameter("txtCod_finalidad"));
			String anio_atencion = StringUtils.trim(request.getParameter("txtAnio_atencion"));
			String mes_atencion = StringUtils.trim(request.getParameter("txtMes_atencion"));
		
			String tipo_vinculo = StringUtils.trim(request.getParameter("txtTipo_vinculo"));
			String vinculo = StringUtils.trim(request.getParameter("txtVinculo"));
			String motivoSol = StringUtils.trim(request.getParameter("txtMotivo"));
			
			String ind_vinculo = StringUtils.trim(request.getParameter("jtipoRegistroVinculo"));
			String ind_prestamo = StringUtils.trim(request.getParameter("jtipoRegistroPrestamo"));
			//AGREGADO: DPORRASC
			String cod_uoc1 = StringUtils.trim(request.getParameter("txtCod_UUOO1"));
			String cod_uoc2 = StringUtils.trim(request.getParameter("txtCod_UUOO2"));
			String cod_uoc3 = StringUtils.trim(request.getParameter("txtCod_UUOO3"));
			
			
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
			
			//maestroPersonalTem = registroPersonalService.obtenerPersonaxRegistro(cod_contacto.toUpperCase());
		
			visor	= StringUtils.trim(request.getParameter("txtvisor"));
			
			params.put("cod_req", cod_req);
			params.put("motivoSolicitud", motivoSol);
			params.put("cod_contacto", cod_contacto);
			params.put("anexo_contacto", anexo_contacto);
			params.put("cod_necesidad", cod_necesidad);
			//params.put("cod_tip_nececidad", cod_tip_nececidad);
			//params.put("nom_tip_necesidad", nom_tip_necesidad);
			
			//AGREGADO PARA IDENTIFICAR EL REGISTRO DE LAS AUC
			params.put("ind_registropor", "AUC");
			
			params.put("cod_finalidad", cod_finalidad);
			params.put("cod_proveedor", cod_proveedor);
			params.put("obs_justificacion", obs_justificacion);
			params.put("obs_plazo_entrega", obs_plazo_entrega);
			params.put("obs_lugar_entrega", obs_lugar_entrega);
			params.put("obs_dir_entrega", obs_dir_entrega);
			params.put("anio_atencion", anio_atencion);
			params.put("mes_atencion", mes_atencion);
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
			
			//INICIO AGREGADO: DPORRASC
			if(cod_uoc1.equals("")){
				params.put("cod_uoc1", maestroPersonal.getCodigoDependencia());
			}else{
				params.put("cod_uoc1", cod_uoc1);
			}
			
			//params.put("cod_uoc1", cod_uoc1);
			params.put("cod_uoc2", cod_uoc2);
			params.put("cod_uoc3", cod_uoc3);
			
			//FIN AGREGADO: DPORRASC
			
			
			//COMITE DEL REQUERIMIENTO

			params.put("ind_comite", ind_comite);
			params.put("ind_tec_tit", ind_tec_tit);
			params.put("ind_tec_sup", ind_tec_sup);
			params.put("cod_au_tit", cod_au_tit);
			params.put("cod_au_sup", cod_au_sup);
			params.put("cod_tec_tit", cod_tec_tit);
			params.put("cod_tec_sup", cod_tec_sup);
			params.put("nom_tec_tit", nom_tec_tit);
			params.put("nom_tec_sup", nom_tec_sup);
			
			params.put("uuoo", maestroPersonal.getUuoo());
			params.put("codigoDependencia", maestroPersonal.getCodigoDependencia());
			params.put("codigoEmpleado", maestroPersonal.getCodigoEmpleado());
			params.put("codigoSede", maestroPersonal.getCodigoSede());
			params.put("user", user);
			
			//(ORIGINAL)cod_req=requerimientoNoProgramadoService.registrarCabRqnp(params);
			cod_req=requerimientoNoProgramadoService.registrarCabRqnpAUCModi(params);
			log.debug( "--------------->>>>>>" + cod_req);
			RequerimientoNoProgramadoBean rqnp= requerimientoNoProgramadoService.recuperarRqnpAUC(cod_req);
			
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
			
			mapRqnp.put("anio_atencion", rqnp.getAnio_atencion());
			mapRqnp.put("mes_atencion", rqnp.getMes_atencion());
			mapRqnp.put("tipo_vinculo", rqnp.getTipo_vinculo());
			mapRqnp.put("vinculo", rqnp.getVinculo());
			 
			MaestroPersonalBean personal =registroPersonalService.obtenerPersonaxCodigo( rqnp.getCod_contacto().trim());
			
			//AGREGADO PARA OBTENER EL TIPO DE AUC
			tipo_auc=(String)requerimientoNoProgramadoService.obtenerTipoAUC(usuarioBean.getCodDepend());
			
			mapRqnp.put("cod_contacto", rqnp.getCod_contacto());
			mapRqnp.put("anexo_contacto", rqnp.getAnexo_contacto());
			mapRqnp.put("cod_necesidad", rqnp.getCod_necesidad());
			mapRqnp.put("cod_tip_nececidad", rqnp.getCod_tip_nececidad());
			mapRqnp.put("nom_tip_necesidad", rqnp.getNom_tip_necesidad());
			mapRqnp.put("nom_contacto", personal.getNombre_completo());
			mapRqnp.put("reg_contacto", personal.getNumero_registro());
			
			mapRqnp.put("cod_finalidad", rqnp.getCod_finalidad());
			
			////////////////////////////////////////////////////////////
			mapRqnp.put("ind_vinculo", rqnp.getInd_vinculo());
			mapRqnp.put("ind_prestamo", rqnp.getInd_prestamo());
			//////////////////////////////////////////////////////////////
			
			//AGREGADO DPORRASC (RECUPERAR DEPENDENCIAS DE UOC)
			 data_uoc1=requerimientoAucService.recuperarDependenciaXcod(rqnp.getCod_uoc1());
			 data_uoc2=requerimientoAucService.recuperarDependenciaXcod(rqnp.getCod_uoc2());
			 data_uoc3=requerimientoAucService.recuperarDependenciaXcod(rqnp.getCod_uoc3());
			 
			if(data_uoc1!=null){
				 uuoo1= (String) data_uoc1.get("uuoo");
				 nom_uuoo1= (String) data_uoc1.get("nom_dep"); 
			 }
			 
			 if(data_uoc2!=null){
				 uuoo2= (String) data_uoc2.get("uuoo");
				 nom_uuoo2= (String) data_uoc2.get("nom_dep");
			 }
			 
			 if(data_uoc3!=null){
				 uuoo3= (String) data_uoc3.get("uuoo");
				 nom_uuoo3= (String) data_uoc3.get("nom_dep");
			 }
			
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
			 
			 mapRqnp.put("cod_uoc1", rqnp.getCod_uoc1());
			 mapRqnp.put("cod_uoc2", rqnp.getCod_uoc2());
			 mapRqnp.put("cod_uoc3", rqnp.getCod_uoc3());
			 mapRqnp.put("tipo_auc", tipo_auc); //TIPO DE AUC(TECNICA O DESCONCENTRADA)
			 
			//AGREGAR DETALLE UOC1 - UOC2 - UOC3
			 mapRqnp.put("uuoo1", uuoo1);
			 mapRqnp.put("nom_uuoo1", nom_uuoo1);
			 
			 mapRqnp.put("uuoo2", uuoo2);
			 mapRqnp.put("nom_uuoo2", nom_uuoo2);
			 
			 mapRqnp.put("uuoo3", uuoo3);
			 mapRqnp.put("nom_uuoo3", nom_uuoo3); 
			 
			 lsBienes=(List<Map<String, Object>>) rqnp.getListaBienes();
			lsFinalidad= (List<Map<String, Object>>) requerimientoNoProgramadoService.listaFinalidad();
			lsNecesidad = (List<Map<String, Object>>) requerimientoNoProgramadoService.listaNecesidad();
			lsTipoNecesidad = (List<Map<String, Object>>) requerimientoNoProgramadoService.listaTipoNecesidad();	
		} 
		catch (ServiceException ex) {
			log.error("Error en RqnpAucController.registrarCabRqnpModi: " + ex.getMessage());
			request.setAttribute("excepAttr", ex);
			okRunning=false;
		} 
		catch (Exception ex) {
			log.error("Error en RqnpAucController.registrarCabRqnpModi: " + ex.getMessage(), ex);
		} 
		finally {
			if (log.isDebugEnabled()){ log.debug("Fin - RqnpAucController.registrarCabRqnpModi");}
		}
		
		if(!okRunning){
			return new ModelAndView("pagErrorSistema");
		}
		//PANTALLA QUE MUESTRA DESPUES DE GRABAR LA CABECERA DE REQUERIMIENTO NO PROGRAMADO
		return new ModelAndView("formRegistroRqnpDetalleAUC").addObject("mapRqnp", mapRqnp)
			.addObject("listaBienes",lsBienes).addObject("visor", visor).
			addObject("lsNecesidad", lsNecesidad).
			addObject("lsTipoNecesidad", lsTipoNecesidad).addObject("lsFinalidad", lsFinalidad).addObject("mapDatos_auc",mapDatos_auc);
		}
	
	
	

	
	/**
     * Anula un Requerimiento no Programado
     * meta afectada .
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView anularRqnp(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("Inicio - RqnpAucController.anularRqnp");}
		List<Map<String, Object>> lsRqnp= new ArrayList<Map<String,Object>>();
		Map<String, Object> params = new HashMap<String, Object>();
		boolean okRunning = false;
		String user="";
		String ls_mes=null;
		String ls_anio="";
		Calendar fecha =  Calendar.getInstance();
		ls_anio= String.valueOf( fecha.get(Calendar.YEAR)) ;
		try {
			UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
			user= usuarioBean.getLogin();
			MaestroPersonalBean  maestroPersonal=  (MaestroPersonalBean) WebUtils.getSessionAttribute(request, "maestroPersonalBean");
			String codigo_rqnp = StringUtils.trim(request.getParameter("txtCodigoRqnp"));
			String motivo_anulacion = StringUtils.trim(request.getParameter("txtAnulacion"));
			
			params.put("codigo_rqnp", codigo_rqnp);
			params.put("user", user);
			params.put("motivo_anulacion", motivo_anulacion);
			
			requerimientoNoProgramadoService.registrarAnularRqnp(params);
			okRunning = true;
			lsRqnp = (List<Map<String, Object>>) requerimientoNoProgramadoService.listarRqnpUsuario(ls_anio, "01", maestroPersonal.getCodigoSede(), maestroPersonal.getCodigoEmpleado(),ls_mes,"AUC");
		}catch (ServiceException ex) {
			log.error("Error en RqnpAucController.anularRqnp: " + ex.getMessage());
			request.setAttribute("excepAttr", ex);
			okRunning=false;
		}catch (Exception ex) {
			log.error("Error en RqnpAucController.anularRqnp: " + ex.getMessage(), ex);
		}finally {
			if (log.isDebugEnabled()){ log.debug("Fin - RqnpAucController.anularRqnp");}
		}
		
		if(!okRunning){
			return new ModelAndView("pagErrorSistema");
		}
		return new ModelAndView("formBandejaRqnpAUC", "lsrqnp", lsRqnp);
	}
	
	
	
	/**
     * Busca los Persona de MaestroPersonal .
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView buscarPersonaJson(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - RqnpAucController.buscarPersonaJson");}
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
			log.error("Error en RqnpAucController.buscarPersonaJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en RqnpAucController.buscarPersonaJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - RqnpAucController.buscarPersonaJson");}
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
		if (log.isDebugEnabled()){ log.debug("debug Inicio - RqnpAucController.recuperarContactoRqnpJson");}
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			MaestroPersonalBean personal=new MaestroPersonalBean();
			String cod_reg = StringUtils.trim(request.getParameter("txtReg_contacto").toUpperCase());
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
			if (log.isDebugEnabled()){ log.debug("Fin - RqnpAucController.recuperarContactoRqnpJson");}
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
     * Recuperar Contratista del Requerimiento .
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView recuperarContratistaRqnpJson(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - RqnpAucController.recuperarContratistaRqnpJson");}
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
			log.error("Error en RqnpAucController.recuperarContratistaRqnpJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en RqnpAucController.recuperarContratistaRqnpJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - RqnpAucController.recuperarContratistaRqnpJson");}
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
		if (log.isDebugEnabled()){ log.debug("debug Inicio - RqnpAucController.validarContactoRqnpJson");}
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		 Map<String, Object> parm = new HashMap<String, Object>();
		String user="";
		try {
			UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
			user= usuarioBean.getLogin();
			MaestroPersonalBean personal=new MaestroPersonalBean();
			String cod_reg = StringUtils.trim(request.getParameter("txtReg_contacto").toUpperCase());
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
			log.error("Error en RqnpAucController.validarContactoRqnpJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en RqnpAucController.validarContactoRqnpJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - RqnpAucController.validarContactoRqnpJson");}
		}
		return new ModelAndView(jsonView, "data", JsonUtil.toString(data)) ;
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
			
			MaestroContratistasBean controtatista= new MaestroContratistasBean();
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
	
	
	/**
     * Recupera el seguimiento de acciones del bien de un 
     * Requerimiento No Programado.
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView recuperarListaAccionesJson(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - RqnpAucController.recuperarListaAccionesJson");}
		Map<String, Object> data = new HashMap<String, Object>();
		List<Map<String, Object>> listaAcciones = new ArrayList<Map<String,Object>>();
		try {			
			String num_expediente 	= StringUtils.trim(request.getParameter("txtnum_expediente"));
			listaAcciones = (List<Map<String, Object>>) requerimientoNoProgramadoService.listarAccionesBien(num_expediente);
			data.put("listaAcciones", JsonUtil.toString( listaAcciones));
		} 
		catch(ServiceException ex){
			log.error("Error en RqnpAucController.recuperarListaAccionesJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en RqnpAucController.recuperarListaAccionesJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - RqnpAucController.recuperarListaAccionesJson");}
		}
		return new ModelAndView(jsonView, "data", JsonUtil.toString(data)) ;
	}
	

	
	/**
	 * Recupera las jefaturas de una AUC 
	 * Requerimiento No Programado.
	 * @param  request
	 * @param  response 
	 * @return ModelAndView 
	 **/
	public ModelAndView obtenerJefaturasAucJson(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - RqnpAucController.obtenerJefaturasAucJson");}
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		List<Map<String, Object>> listaJefaturasAuc = new ArrayList<Map<String,Object>>();
		try {			
			String codEmpleado 	= StringUtils.trim(request.getParameter("txtcodEmpleado"));
			param.put("codEmpleado", codEmpleado);
			
			listaJefaturasAuc = (List<Map<String, Object>>) registroPersonalService.obtenerJefaturasAucJson(param);
			//data.put("listaJefaturasAuc", JsonUtil.toString( listaJefaturasAuc));
			data.put("listaJefaturasAuc", listaJefaturasAuc);
			data.put("numJefaturas", registroPersonalService.obtenerNumJefaturasEmpleado(codEmpleado));
			//data.put("numJefaturas", listaJefaturasAuc.size());
		} 
		catch(ServiceException ex){
			log.error("Error en RqnpAucController.obtenerJefaturasAucJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en RqnpAucController.obtenerJefaturasAucJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - RqnpAucController.obtenerJefaturasAucJson");}
		}
		//return new ModelAndView(jsonView, "data", JsonUtil.toString(data)) ;
		return new ModelAndView(jsonView, data) ;
	}
	
	public RegistroPersonalService getRegistroPersonalService() {
		return registroPersonalService;
	}
	public void setRegistroPersonalService(
			RegistroPersonalService registroPersonalService) {
		this.registroPersonalService = registroPersonalService;
	}
	public ReloadableResourceBundleMessageSource getMessageSource() {
		return messageSource;
	}
	public void setMessageSource(ReloadableResourceBundleMessageSource messageSource) {
		this.messageSource = messageSource;
	}
	public RequerimientoNoProgramadoService getRequerimientoNoProgramadoService() {
		return requerimientoNoProgramadoService;
	}
	public void setRequerimientoNoProgramadoService(
			RequerimientoNoProgramadoService requerimientoNoProgramadoService) {
		this.requerimientoNoProgramadoService = requerimientoNoProgramadoService;
	}
	public String getUploadDir() {
		return uploadDir;
	}
	public void setUploadDir(String uploadDir) {
		this.uploadDir = uploadDir;
	}
	public RequerimientoAucService getRequerimientoAucService() {
		return requerimientoAucService;
	}
	public void setRequerimientoAucService (RequerimientoAucService requerimientoAucService) {
		this.requerimientoAucService = requerimientoAucService;
	}
}
