package pe.gob.sunat.administracion2.siga.rqnp.web.controller;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import pe.gob.sunat.administracion2.siga.rqnp.model.domain.RequerimientoNoProgramadoBean;
import pe.gob.sunat.administracion2.siga.rqnp.service.RequerimientoNoProgramadoService;
import pe.gob.sunat.administracion2.siga.rqnp.util.RqnpConstantes;
import pe.gob.sunat.administracion2.siga.rqnp.util.RqnpUtil;
import pe.gob.sunat.framework.core.json.JsonUtil;
import pe.gob.sunat.framework.spring.util.exception.ServiceException;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.DependenciaBean;
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

public class RqnpController extends BaseController{
	
	private static final Log log = LogFactory.getLog(RqnpController.class);

	private RegistroPersonalService 			registroPersonalService;
	private RequerimientoNoProgramadoService 	requerimientoNoProgramadoService;
	private ReloadableResourceBundleMessageSource 		messageSource;
	private String 								uploadDir;
	
	/**
     * Carga la Pagina de Login
     * @param  request
     * @param  response 
     * @return ModelAndView 
     */
	public ModelAndView mostrarlogin(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()) {
			log.debug("debug Inicio - RqnpController.mostrarLogin");
		}
		try {
			UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
			log.debug("Usuario" + usuarioBean.getLogin());
			log.debug("Registro" + usuarioBean.getNroRegistro());
			return new ModelAndView("general/login");
			
		} catch (ServiceException ex) {
			log.error("Error en RqnpController.mostrarLogin: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);
			
		} finally {
			if (log.isDebugEnabled()) {
				log.debug("Fin - RqnpController.mostrarLogin");
			}
		}
	}
	
	
	/**
     * Valida el Ingreso de Usuario y Carga la página de menú
     * @param  request
     * @param  response 
     * @return ModelAndView 
     */ 
public ModelAndView iniciarSesion(HttpServletRequest request, HttpServletResponse response) {
		
		if (log.isDebugEnabled()) {
			log.debug("debug Inicio - RqnpController.iniciarSesion");
		}
		UsuarioBean usuarioBean = new UsuarioBean();
		boolean is_ok = true;
		String tipo_error = "";

		try {
			String login = StringUtils.trimToEmpty(request.getParameter("username"));
			String clave = StringUtils.trimToEmpty(request.getParameter("password"));
			String errorMessageLogin = RqnpConstantes.CADENA_VACIA;
			
			if ("".equals(login)) {
				is_ok = false;
				tipo_error = "01";
			} /*else if (!login.toUpperCase().equals(clave.toUpperCase())) {
				is_ok = false;
				tipo_error = "02";
			}*/ else {
				login = login.trim();

				usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
				//MaestroPersonalBean maestroPersonal = registroPersonalService.obtenerPersonaxRegistro(login);
				MaestroPersonalBean  maestroPersonal= registroPersonalService.obtenerPersonaxUsuario(login);
				
				if (maestroPersonal != null) {
					usuarioBean.setLogin(login);
					usuarioBean.setApeMaterno(maestroPersonal.getApellido_materno());
					usuarioBean.setApePaterno(maestroPersonal.getApellido_paterno());
					usuarioBean.setNombres(maestroPersonal.getNombre());
					usuarioBean.setNombreCompleto(maestroPersonal.getNombre_completo());
					usuarioBean.setCodDepend(maestroPersonal.getCodigoDependencia());
					usuarioBean.setCodUO(maestroPersonal.getUuoo());
					usuarioBean.setNroRegistro(maestroPersonal.getNumero_registro());
					log.debug("registro: " + maestroPersonal.getNumero_registro());
					WebUtils.setSessionAttribute(request, "usuarioBean", usuarioBean);
					WebUtils.setSessionAttribute(request, "maestroPersonalBean", maestroPersonal);
					
				} else {
					is_ok = false;
					tipo_error = "03";
				}
			}
			
			if (is_ok) {
				return new ModelAndView("general/menu").addObject("usuarioBean",usuarioBean);
			} else {
				Exception ex = new Exception("");
				if ("01".equals(tipo_error)) {
					ex = new Exception("Ingrese Usuario.");
					errorMessageLogin = "Ingrese Usuario.";
				} else if ("02".equals(tipo_error)) {
					ex = new Exception("Usuario o Contraseña incorrecta.");
					errorMessageLogin = "Usuario o Contraseña incorrecta.";
				} else if ("03".equals(tipo_error)) {
					ex = new Exception("No existe los datos de personal.");
					errorMessageLogin = "No existe los datos de personal.";
				}
				request.setAttribute("errorMessageLogin", errorMessageLogin);
				return new ModelAndView("general/login");
			}
			
		} catch (ServiceException ex) {
			log.error("Error en RqnpController.iniciarSesion: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);
			
		} catch (Exception ex) {
			log.error("Error en RqnpController.iniciarSesion: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);
			
		} finally {
			if (log.isDebugEnabled()) {
				log.debug("Fin - RqnpController.iniciarSesion");
			}
		}
	}
	
	public ModelAndView menu(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("general/menu");
	}
	/**
	 * @author dporrasc
     * Carga la Bandeja de Registro de Requerimientos no Programados
     * @param  request
     * @param  response 
     * @return ModelAndView 
     */
	//Menu Registro RQNP/////////////////////////
	public ModelAndView iniciarbandeja(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()) {log.debug("debug Inicio - RqnpController.iniciarbandeja");}
		
		String validaExistenciaMeta =FormatoConstantes.CADENA_VACIA;
		String	lsAnio=FormatoConstantes.CADENA_VACIA;
		String	lsRegistro=FormatoConstantes.CADENA_VACIA;
		Map<String, Object> respuesta = new HashMap<String, Object>();
		ModelAndView view = null;
		
		try {
			UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
			lsRegistro= usuarioBean.getNroRegistro().trim();
			lsAnio= FechaUtil.obtenerAnioActual();
			
			if (lsRegistro !=null){
				MaestroPersonalBean  colaborador= registroPersonalService.obtenerPersonaxRegistro(lsRegistro);
				if(colaborador != null){
					WebUtils.setSessionAttribute(request,"maestroPersonalBean",colaborador );
					
					@SuppressWarnings("unchecked")
					List<Map<String, Object>> lsRqnp = (List<Map<String, Object>>) requerimientoNoProgramadoService
														.listarRqnpUsuario(lsAnio, "01", null, colaborador.getCodigoEmpleado(),null,RqnpConstantes.IND_REGISTRO_AU);
					respuesta.put("colaborador", colaborador);
					respuesta.put("lsRqnp", RqnpUtil.toJSON(lsRqnp));
					
					//validar existencia de meta
					validaExistenciaMeta = requerimientoNoProgramadoService.validaExisteMetaJson(colaborador.getCodigoDependencia(), lsAnio);
					log.debug("La UUOO posee meta?: "+validaExistenciaMeta);
					
					if(validaExistenciaMeta.equals(RqnpConstantes.UNO)){
				    	respuesta.put("poseeMeta", RqnpConstantes.SI);
					}else{
						DependenciaBean dependenciaDpg = new DependenciaBean();
				    	dependenciaDpg = requerimientoNoProgramadoService.obtenerDpg(RqnpConstantes.COD_PARAMETRO_UPP, RqnpConstantes.DESC_PARAMETRO_UPP);
				    	String uuooDpg = dependenciaDpg.getUuoo()+" - "+dependenciaDpg.getNom_corto();
				    	respuesta.put("poseeMeta", RqnpConstantes.NO);
				    	respuesta.put("mensajeNoPoseeMeta", "Su unidad organizacional no posee metas asignadas para el año en curso, favor coordinar con la unidad " +
				    				uuooDpg + " para proceder con el registro de su requerimiento.");
					}
					
					view = new ModelAndView(RqnpConstantes.RQNP_BANDEJA_INICIO_PAGE, respuesta);
					
				}else{
					Exception ex = new Exception("(Error) No existe los datos del Usuario para el registro: " + lsRegistro);
					request.setAttribute("excepAttr", ex);
					view = new ModelAndView("pagErrorSistema");
				}
			}else{
				Exception ex = new Exception("(Error) No se recuperó el número de registro: " + lsRegistro);
				request.setAttribute("excepAttr", ex);
				view = new ModelAndView("pagErrorSistema");
			}
		}catch(ServiceException ex){
			log.error("Error en RqnpController.iniciarbandeja: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en RqnpController.iniciarbandeja: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - RqnpController.iniciarbandeja");}
		}
		return view;
	}
	
	
	/**
	 * @author dporrasc
     * Nuevo Requerimiento No Programado
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	@SuppressWarnings("unchecked")
	public ModelAndView nuevoRqnp(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()) log.debug("Inicio -  RqnpController.nuevoRqnp");
		String lsAnio=FormatoConstantes.CADENA_VACIA;
		String lsMes=FormatoConstantes.CADENA_VACIA;
		List<Map<String, Object>> lsNecesidad= new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> lsTipoNecesidad= new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> lsFinalidad= new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> lsTipoVinculo= new ArrayList<Map<String, Object>>();
		
		Map<String, Object> respuesta = new HashMap<String, Object>();
		
		try {
			lsAnio=FechaUtil.obtenerAnioActual();
			lsMes=FechaUtil.obtenerMesActual();

			MaestroPersonalBean  colaborador=  (MaestroPersonalBean) WebUtils.getSessionAttribute(request, "maestroPersonalBean");
			Map<String, Object> mapRqnp = new HashMap<String, Object>();
			mapRqnp.put("numRegResponsable", colaborador.getNumero_registro());
			mapRqnp.put("codEmpleadoResponsable",colaborador.getCodigoEmpleado());
			mapRqnp.put("nomResponsable", colaborador.getNombre_completo());
			mapRqnp.put("codDepResponsable", colaborador.getCodigoDependencia());
			mapRqnp.put("nomUuooResponsable", colaborador.getDependencia());
			mapRqnp.put("uuooResponsable", colaborador.getUuoo());
			mapRqnp.put("fechaRqnp", FechaUtil.formatDateToDateDDMMYYYYHHMM(new Date()));
			mapRqnp.put("anioAtencion",lsAnio);
			mapRqnp.put("mesAtencion",lsMes);
			mapRqnp.put("tipoVinculo", "04");
			mapRqnp.put("vinculo", "01");
			mapRqnp.put("indVinculo", "N");
			mapRqnp.put("indPrestamo", "N");
			ArrayList<Mes> arrMeses = RqnpUtil.obtenerMesesAnio();
			ArrayList<Anio> arrAnios =RqnpUtil.obtenerAniosRqnp(Integer.parseInt(lsAnio));
			
			lsNecesidad = (List<Map<String, Object>>) requerimientoNoProgramadoService.listaNecesidad();
			lsTipoNecesidad = (List<Map<String, Object>>) requerimientoNoProgramadoService.listaTipoNecesidad();
			lsFinalidad= (List<Map<String, Object>>) requerimientoNoProgramadoService.listaFinalidad();
			
			lsTipoVinculo= (List<Map<String, Object>>) requerimientoNoProgramadoService.listarTipoVinculo();
			
			respuesta.put("mapRqnp", mapRqnp);
			respuesta.put("lsNecesidad", lsNecesidad);
			respuesta.put("lsTipoNecesidad", lsTipoNecesidad);
			respuesta.put("lsFinalidad", lsFinalidad);
			respuesta.put("arrMeses", arrMeses);
			respuesta.put("arrAnios", arrAnios);
			
			respuesta.put("lsTipoVinculo", lsTipoVinculo);
			 
			return new ModelAndView(RqnpConstantes.RQNP_REGISTRO_RQNP_CAB_PAGE,respuesta);
			
		} catch(ServiceException ex){
			log.error("Error en  RqnpController.nuevoRqnp: " + ex.getMessage());
			throw new ServiceException(this, ex);
		} catch(Exception ex){
			log.error("Error en  RqnpController.nuevoRqnp: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		} finally{
			if (log.isDebugEnabled()){ log.debug("Fin -  RqnpController.nuevoRqnp");}
		}
	}
	
	/**
	 * @author dporrasc
     * Modificar Requerimiento No Programado
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	@SuppressWarnings("unchecked")
	public ModelAndView modificarRqnp(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("Inicio -  RqnpController.modificarRqnp");}
		Map<String, Object> respuesta 	= 	new HashMap<String, Object>();
		Map<String, Object> mapRqnp 	= 	new HashMap<String, Object>();
		Map<String, Object> parm 	= 	new HashMap<String, Object>();
		
		String codigoRqnp	=	FormatoConstantes.CADENA_VACIA;
		String codigoRqnpMod	=	FormatoConstantes.CADENA_VACIA;
		String user=FormatoConstantes.CADENA_VACIA;
		String lsAnio=FormatoConstantes.CADENA_VACIA;
		List<Map<String, Object>> lsNecesidad= new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> lsTipoNecesidad= new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> lsFinalidad= new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> lsTipoVinculo= new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> lsVinculo= new ArrayList<Map<String, Object>>();
		RequerimientoNoProgramadoBean rqnp = new RequerimientoNoProgramadoBean();
		ModelAndView view = null;
		
		try {
			UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
			user= usuarioBean.getLogin();
			
			lsAnio=FechaUtil.obtenerAnioActual();
			
			ArrayList<Mes> arrMeses = RqnpUtil.obtenerMesesAnio();
			ArrayList<Anio> arrAnios =RqnpUtil.obtenerAniosRqnp(Integer.parseInt(lsAnio));
			
			codigoRqnp = RegistroUtil.validarEmptyToNull(request.getParameter("hidCodigoRqnp"));
			
			codigoRqnpMod = RegistroUtil.validarEmptyToNull(request.getParameter("hidCodigoRqnpMod"));
			
			if(codigoRqnpMod !=null){
				if (log.isDebugEnabled()){ log.debug("codigoRqnpMod: "+codigoRqnpMod);}
				rqnp= requerimientoNoProgramadoService.recuperarRqnp(codigoRqnpMod);
			}else{
				if (log.isDebugEnabled()){ log.debug("codigoRqnp: "+codigoRqnp);}
				rqnp= requerimientoNoProgramadoService.recuperarRqnp(codigoRqnp);
			}
			
			//RequerimientoNoProgramadoBean rqnp= requerimientoNoProgramadoService.recuperarRqnp(codigoRqnp);
			
			//Obtiene datos de cabecera par enviar a la vista
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
			
			log.debug("indVinculo: " + rqnp.getInd_vinculo());
			log.debug("indPrestamo: " + rqnp.getInd_prestamo());
			log.debug("tipoVinculo: " + rqnp.getTipo_vinculo());
			log.debug("vinculo: " + rqnp.getVinculo());
			 
			//Seteando Detalle---------------------------------------------------------------------------
			List<Map<String, Object>> listaBienes = (List<Map<String, Object>>) rqnp.getListaBienes();

			lsNecesidad = (List<Map<String, Object>>) requerimientoNoProgramadoService.listaNecesidad();
			lsTipoNecesidad = (List<Map<String, Object>>) requerimientoNoProgramadoService.listaTipoNecesidad();
			lsFinalidad= (List<Map<String, Object>>) requerimientoNoProgramadoService.listaFinalidad();
			lsTipoVinculo= (List<Map<String, Object>>) requerimientoNoProgramadoService.listarTipoVinculo();

			String tipoVinculoSel = rqnp.getTipo_vinculo();
			log.debug("tipoVinculoSel:>>>>>>>"+tipoVinculoSel);
			
			if (tipoVinculoSel != null ){
				if (!tipoVinculoSel.equals("")){
					lsVinculo = (List<Map<String, Object>>) requerimientoNoProgramadoService.listarVinculo(tipoVinculoSel);
				}
			}
			
			respuesta.put("mapRqnp", mapRqnp);
			respuesta.put("lsNecesidad", lsNecesidad);
			respuesta.put("lsTipoNecesidad", lsTipoNecesidad);
			respuesta.put("lsFinalidad", lsFinalidad);
			respuesta.put("arrMeses", arrMeses);
			respuesta.put("arrAnios", arrAnios);
			respuesta.put("lsTipoVinculo", lsTipoVinculo);
			respuesta.put("lsVinculo", lsVinculo);
			respuesta.put("listaBienes", RqnpUtil.toJSON(listaBienes).toString());
			
			view= new ModelAndView(RqnpConstantes.RQNP_REGISTRO_RQNP_DETALLE_PAGE, respuesta);
			
		} catch(ServiceException ex){
			log.error("Error en  RqnpController.modificarRqnp: " + ex.getMessage());
			throw new ServiceException(this, ex);
		} catch(Exception ex){
			log.error("Error en  RqnpController.modificarRqnp: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		} finally{
			if (log.isDebugEnabled()){ log.debug("Fin -  RqnpController.modificarRqnp");}
		}
		
		return view;
	}
	

	
	
	/**
	 * @author dporrasc
     * Registra la Cabecera del RQNP, genera el numero de secuencia.
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	@SuppressWarnings("unchecked")
	public ModelAndView registrarCabRqnp(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()) {log.debug("Inicio - RqnpController.registrarCabRqnp");}
		Map<String, Object> params 		= new HashMap<String, Object>();
		Map<String, Object> parm 		= new HashMap<String, Object>();
		Map<String, Object> respuesta 	= new HashMap<String, Object>();
		boolean okRunning = false;
		Map<String, Object>  mapRqnp 	= new HashMap<String, Object> ();
		String lsAnio=FormatoConstantes.CADENA_VACIA;
		String tipoAccion=FormatoConstantes.CADENA_VACIA;
		
		List<Map<String, Object>> lsNecesidad= new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> lsTipoNecesidad= new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> lsFinalidad= new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> lsTipoVinculo= new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> lsVinculo= new ArrayList<Map<String, Object>>();
		RequerimientoNoProgramadoBean rqnp = new RequerimientoNoProgramadoBean();
		ModelAndView view = null;
		String cod_req	=FormatoConstantes.CADENA_VACIA;
		 
		try {
			UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
		
			this.setAuditoriaBeanHolder(request, response);
			log.debug("request.getSession()" +request.getSession());
			request.getSession().setAttribute("usuarioBean", usuarioBean);	
						
			lsAnio=FechaUtil.obtenerAnioActual();
			
			ArrayList<Mes> arrMeses = RqnpUtil.obtenerMesesAnio();
			ArrayList<Anio> arrAnios =RqnpUtil.obtenerAniosRqnp(Integer.parseInt(lsAnio));
			
			MaestroPersonalBean  maestroPersonal=  (MaestroPersonalBean) WebUtils.getSessionAttribute(request, "maestroPersonalBean");
			
			tipoAccion = RegistroUtil.validarEmptyToNull(request.getParameter("hidRqnpCabTipoAccion"));
			cod_req = RegistroUtil.validarEmptyToNull(request.getParameter("hidCodigoRqnp"));
			
			String cod_contacto = RegistroUtil.validarEmptyToNull(request.getParameter("hidCodEmpleadoContacto"));
			String anexoContacto = RegistroUtil.validarEmptyToNull(request.getParameter("hidAnexoContacto"));
			String num_ruc = RegistroUtil.validarEmptyToNull(request.getParameter("hidNumRucProveedor"));
			String codProveedor=FormatoConstantes.CADENA_VACIA;
			if (num_ruc!=null){
				if(!num_ruc.equals(FormatoConstantes.CADENA_VACIA)){
					codProveedor = StringUtils.trim(request.getParameter("hidCodProveedor"));
				}
			}
			String obsJustificacion = RegistroUtil.validarEmptyToNull(request.getParameter("hidObsJustificaProveedor"));
			String obsPlazoEntrega = RegistroUtil.validarEmptyToNull(request.getParameter("hidObsPlazoEntrega"));
			String obsLugarEntrega = RegistroUtil.validarEmptyToNull(request.getParameter("hidObsLugarEntrega"));
			String obsDirEntrega = RegistroUtil.validarEmptyToNull(request.getParameter("hidObsDirEntrega"));
			String codFinalidad = RegistroUtil.validarEmptyToNull(request.getParameter("hidCodFinalidad"));
			String codNecesidad =RegistroUtil.validarEmptyToNull(request.getParameter("hidCodNecesidad"));
			String anioAtencion = RegistroUtil.validarEmptyToNull(request.getParameter("hidAnioAtencion"));
			String mesAtencion = RegistroUtil.validarEmptyToNull(request.getParameter("hidMesAtencion"));
			
			String indVinculo = RegistroUtil.validarEmptyToNull(request.getParameter("hidOptVinculo"));
			String tipoVinculo = RegistroUtil.validarEmptyToNull(request.getParameter("hidTipoVinculo"));
			String vinculo = RegistroUtil.validarEmptyToNull(request.getParameter("hidVinculo"));
			
			String indPrestamo = RegistroUtil.validarEmptyToNull(request.getParameter("hidOptPrestamo"));
			
			String montoRqnp = RegistroUtil.validarEmptyToNull(request.getParameter("hidMontoRqnp"));
			String motivoSustento = RegistroUtil.validarEmptyToNull(request.getParameter("hidMotivoSustento"));
			
			
			//tipo_vinculo (04:NINGUNO) - vinculo (01:NINGUNO)
			indVinculo = (indVinculo==null?"N":indVinculo);
			indPrestamo = (indPrestamo==null?"N":indPrestamo);
			
			if(indVinculo.equals("N")){ tipoVinculo="04";	vinculo="01";}
			
			params.put("codigoRqnp", cod_req);
			params.put("motivoSolicitud", motivoSustento);
			params.put("cod_contacto", cod_contacto);
			params.put("anexo_contacto", anexoContacto);
			params.put("cod_necesidad", codNecesidad);
			
			//AGREGADO PARA IDENTIFICAR EL REGISTRO DE LAS AUC
			params.put("ind_registropor", "AU");
			params.put("cod_finalidad", codFinalidad);
			params.put("cod_proveedor", codProveedor);
			params.put("obs_justificacion", obsJustificacion);
			params.put("obs_plazo_entrega", obsPlazoEntrega);
			params.put("obs_lugar_entrega", obsLugarEntrega);
			params.put("obs_dir_entrega", obsDirEntrega);
			params.put("anio_atencion", anioAtencion);
			params.put("mes_atencion", mesAtencion);
			params.put("tipo_vinculo", tipoVinculo);
			params.put("vinculo", vinculo);
			params.put("ind_vinculo", indVinculo);
			params.put("ind_prestamo", indPrestamo);
			params.put("montoRqnp", montoRqnp);
			params.put("uuoo", maestroPersonal.getUuoo());
			params.put("codigoDependencia", maestroPersonal.getCodigoDependencia());
			params.put("codigoEmpleado", maestroPersonal.getCodigoEmpleado());
			//params.put("user", user);
			AuditoriaUtil.set(usuarioBean);
			
			if(tipoAccion.equals("R")){
				cod_req=requerimientoNoProgramadoService.registrarCabRqnp(params);
			}if (tipoAccion.equals("M")){
				rqnp = requerimientoNoProgramadoService.recuperarRqnp(cod_req);
				requerimientoNoProgramadoService.updateCabRqnp(params);
			}
			
			okRunning = true;
			
			rqnp = requerimientoNoProgramadoService.recuperarRqnp(cod_req);
			
			mapRqnp = RqnpUtil.getRqnpCabDatos(rqnp);
			
			if(rqnp.getCod_contacto()!=null){
				MaestroPersonalBean personalContacto =registroPersonalService.obtenerPersonaxCodigo( rqnp.getCod_contacto());
				Map<String, Object> mapRqnpContacto = RqnpUtil.getRqnpCabDatosContacto(personalContacto);
				mapRqnp.putAll(mapRqnpContacto);
			}
			
			if (rqnp.getCod_proveedor() !=null) {
				if (!rqnp.getCod_proveedor().equals(FormatoConstantes.CADENA_VACIA)){
					parm.put("cod_cont", rqnp.getCod_proveedor());
				
					MaestroContratistasBean contratista=registroPersonalService.recuperarContratista(parm);
					Map<String, Object> mapRqnpProveedor = RqnpUtil.getRqnpCabDatosProveedor(contratista);
					mapRqnp.putAll(mapRqnpProveedor);
				}
			}
			
			lsNecesidad = (List<Map<String, Object>>) requerimientoNoProgramadoService.listaNecesidad();
			lsTipoNecesidad = (List<Map<String, Object>>) requerimientoNoProgramadoService.listaTipoNecesidad();
			lsFinalidad= (List<Map<String, Object>>) requerimientoNoProgramadoService.listaFinalidad();
			
			lsTipoVinculo= (List<Map<String, Object>>) requerimientoNoProgramadoService.listarTipoVinculo();
			
			String tipoVinculoSel = rqnp.getTipo_vinculo();
			log.debug("tipoVinculoSel:>>>>>>>"+tipoVinculoSel);
			
			if (tipoVinculoSel != null ){
				if (!tipoVinculoSel.equals("")){
					lsVinculo = (List<Map<String, Object>>) requerimientoNoProgramadoService.listarVinculo(tipoVinculoSel);
				}
			}
			
			List<Map<String, Object>> listaBienes = (List<Map<String, Object>>) rqnp.getListaBienes();
			
			respuesta.put("mapRqnp", mapRqnp);
			respuesta.put("lsNecesidad", lsNecesidad);
			respuesta.put("lsTipoNecesidad", lsTipoNecesidad);
			respuesta.put("lsFinalidad", lsFinalidad);
			respuesta.put("arrMeses", arrMeses);
			respuesta.put("arrAnios", arrAnios);
			
			respuesta.put("lsTipoVinculo", lsTipoVinculo);
			respuesta.put("lsVinculo", lsVinculo);
			
			respuesta.put("listaBienes", RqnpUtil.toJSON(listaBienes).toString());
		} 
		catch (ServiceException ex) {
			log.error("Error en RqnpController.registrarCabRqnp: " + ex.getMessage());
			request.setAttribute("excepAttr", ex);
			okRunning=false;
		}
		catch (Exception ex) {
			log.error("Error en RqnpController.registrarCabRqnp: " + ex.getMessage(), ex);
		} 
		finally {
			if (log.isDebugEnabled()){ log.debug("Fin - RqnpController.registrarCabRqnp");}
		}
		
		if (!okRunning) {
			view = new ModelAndView("pagErrorSistema");
		}else{
			view=new ModelAndView(RqnpConstantes.RQNP_REGISTRO_RQNP_DETALLE_PAGE, respuesta);
		}
		return view;
	}

	
	/**
     * Envia el Informe a la INA
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	@SuppressWarnings("unchecked")
	public ModelAndView enviarInformeRqnp(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("Inicio -  RqnpController.enviarInformeRqnp");}
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
			mapRqnp.put("fechaRqnp",FechaUtil.formatDateToDateDDMMYYYYHHMM(rqnp.getFechaRqnp()));
			mapRqnp.put("motivoRqnp",rqnp.getMotivoSolicitud());
			mapRqnp.put("montoRqnp",  rqnp.getMontoRqnp().setScale(2, RoundingMode.HALF_UP) );
			//Seteando Detalle
			List<Map<String, Object>> listaBienes = (List<Map<String, Object>>) rqnp.getListaBienes();
			 
			return new ModelAndView("formRqnpDetalleInforme").addObject("mapRqnp", mapRqnp).
			addObject("lsCatalogo",lsCatalogo).
			addObject("listaBienes",listaBienes).addObject("visor", visor);
		}
		catch(ServiceException ex){
			log.error("Error en  RqnpController.enviarInformeRqnp: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en  RqnpController.enviarInformeRqnp: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()) {log.debug("Fin -  RqnpController.enviarInformeRqnp");}
		}
	}
	
	
	
	/**
	 * @author dporrasc
     * Anula un Requerimiento no Programado
     * meta afectada .
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView anularRqnp(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("Inicio - RqnpController.anularRqnp");}
		Map<String, Object> params = new HashMap<String, Object>();
		boolean okRunning = false;
		String user=FormatoConstantes.CADENA_VACIA;
		String	lsAnio=FormatoConstantes.CADENA_VACIA;
		Map<String, Object> respuesta = new HashMap<String, Object>();
		ModelAndView view = null;
		
		try {
			lsAnio=FechaUtil.obtenerAnioActual();
			UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
			user= usuarioBean.getLogin();
			MaestroPersonalBean  colaborador=  (MaestroPersonalBean) WebUtils.getSessionAttribute(request, "maestroPersonalBean");
			String codigoRqnp = StringUtils.trim(request.getParameter("txtCodigoRqnp"));
			String motivoAnulacion = StringUtils.trim(request.getParameter("txtMotivoAnulacion"));
			
			params.put("codigo_rqnp", codigoRqnp);
			params.put("user", user);
			params.put("motivo_anulacion", motivoAnulacion);
			
			requerimientoNoProgramadoService.registrarAnularRqnp(params);
			okRunning = true;
			if(okRunning){
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> lsRqnp = (List<Map<String, Object>>) requerimientoNoProgramadoService
													.listarRqnpUsuario(lsAnio, "01", null, colaborador.getCodigoEmpleado(),null,RqnpConstantes.IND_REGISTRO_AU);
				
				respuesta.put("lsRqnp", RqnpUtil.toJSON(lsRqnp).toString());
				respuesta.put("registroNro", colaborador.getNumero_registro());
				view = new ModelAndView(getJsonView(),respuesta);
			}
		} 
		catch (ServiceException ex) {
			log.error("Error en RqnpController.anularRqnp: " + ex.getMessage());
			request.setAttribute("excepAttr", ex);
			okRunning=false;
		}catch (Exception ex) {
			log.error("Error en RqnpController.anularRqnp: " + ex.getMessage(), ex);
			okRunning=false;
		}finally {
			if (log.isDebugEnabled()){ log.debug("Fin - RqnpController.anularRqnp");}
		}
		
		if(!okRunning){
			return new ModelAndView("pagErrorSistema");
		}
		return view;
	}
	
	
	/**
     * Busca los Persona de MaestroPersonal .
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	@SuppressWarnings("unchecked")
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
	
	
	/**
     * Recuperar Contacto de Requerimiento .
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	
	/*
	public ModelAndView recuperarContactoRqnpJson(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - RqnpController.recuperarContactoRqnpJson");}
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
			log.error("Error en RqnpController.recuperarContactoRqnpJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en RqnpController.recuperarContactoRqnpJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - RqnpController.recuperarContactoRqnpJson");}
		}
		return new ModelAndView(jsonView, "data", JsonUtil.toString(data)) ;
	}
	
	*/

	
	/**
	 * @author dporrasc
     * Recuperar Contratista del Requerimiento .
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView recuperarContratistaRqnpJson(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - RqnpController.recuperarContratistaRqnpJson");}
		Map<String, Object> parm = new HashMap<String, Object>();
		Map<String, Object> respuesta = new HashMap<String, Object>();
		String user="";
		ModelAndView viewPage = null;
		try {
			
			UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
			user= usuarioBean.getLogin();
			String numRuc = RegistroUtil.validarEmptyToNull(request.getParameter("hidNumRucProveedor"));
			parm.put("num_ruc", numRuc);
			parm.put("user", user);
			
			if(numRuc!=null){
				MaestroContratistasBean contratista=registroPersonalService.recuperarContratistaPadron(parm);
				//MaestroContratistasBean contratista=registroPersonalService.recuperarContratistaPadronNew(parm);
				
				if (contratista !=null){
					respuesta.put("contratista", contratista);
					respuesta.put("contratistaOK",RqnpConstantes.UNO);
				}else{
					respuesta.put("error", "-1");
					respuesta.put("cod_cont", "-1");
					respuesta.put("contratistaOK",RqnpConstantes.CERO);
					respuesta.put("mensaje", messageSource.getMessage("error.registrarRqnp.contratista" , null, Locale.getDefault()));	
				}
			}else{
				respuesta.put("error", "-1");
				respuesta.put("cod_cont", "-1");
				respuesta.put("contratistaOK",RqnpConstantes.CERO);
				respuesta.put("mensaje", messageSource.getMessage("error.registrarRqnp.contratista" , null, Locale.getDefault()));	
			}
			
			viewPage = new ModelAndView(getJsonView(), respuesta);
		} 
		catch(ServiceException ex){
			log.error("Error en RqnpController.recuperarContratistaRqnpJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en RqnpController.recuperarContratistaRqnpJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - RqnpController.recuperarContratistaRqnpJson");}
		}
		return viewPage;
	}


	/**
     * Verifica que el registro de contacto existe.
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	/*
	public ModelAndView validarContactoRqnpJson(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - RqnpController.validarContactoRqnpJson");}
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
			log.error("Error en RqnpController.validarContactoRqnpJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}catch(Exception ex){
			log.error("Error en RqnpController.validarContactoRqnpJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}finally{
			if (log.isDebugEnabled()){ log.debug("Fin - RqnpController.validarContactoRqnpJson");}
		}
		
		return new ModelAndView(jsonView, "data", JsonUtil.toString(data)) ;
	}
*/
	
	/**
	 * Recupera el seguimiento de acciones del bien de un 
	 * Requerimiento No Programado.
	 * @param  request
	 * @param  response 
	 * @return ModelAndView 
	 **/
	@SuppressWarnings("unchecked")
	public ModelAndView recuperarListaAccionesJson(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - RqnpController.recuperarListaAccionesJson");}
		Map<String, Object> data = new HashMap<String, Object>();
		List<Map<String, Object>> listaAcciones = new ArrayList<Map<String,Object>>();
		try {			
			String num_expediente 	= StringUtils.trim(request.getParameter("txtnum_expediente"));
			listaAcciones = (List<Map<String, Object>>) requerimientoNoProgramadoService.listarAccionesBien(num_expediente);
			//data.put("listaAcciones", JsonUtil.toString( listaAcciones));
			data.put("listaAcciones", listaAcciones);
		} 
		catch(ServiceException ex){
			log.error("Error en RqnpController.recuperarListaAccionesJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en RqnpController.recuperarListaAccionesJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - RqnpController.recuperarListaAccionesJson");}
		}
		//return new ModelAndView(jsonView, "data", JsonUtil.toString(data)) ;
		return new ModelAndView(jsonView, data) ;
	}
	
	/**
     * Carga la Bandeja de del Jefe Inmediado o Intendente con los Items
     * asignados para su aprobación
     * @param  request
     * @param  response 
     * @return ModelAndView 
     */ 
	
	@SuppressWarnings("unchecked")
	public ModelAndView iniciarbandejaji(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - RqnpController.iniciarbandejaji");}
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
				if (esUserAprobador.equals("S")) {
					tituloVista = " (APROBADOR) ";
					params.put("codEmpleadoEncargado", codEmpleado);
					params.put("cod_est", "0304");
					lsRqnp = (List)this.requerimientoNoProgramadoService.recuperarBienesIntedente(params);
					lsRqnp.addAll((List)this.requerimientoNoProgramadoService.recuperarBienesJefeInmediato(params));
					sw = true;
				}else if ((esUserJefe.booleanValue()) || (esEncargadoOtraUuoo.booleanValue())) {
					tituloVista = " (JEFE INMEDIATO) ";
					params.put("codEmpleadoEncargado", codEmpleado);
					params.put("cod_est", "03");
					lsRqnp = (List)this.requerimientoNoProgramadoService.recuperarBienesJefeInmediato(params);
					sw = true;
				}

				if (sw) {
					return new ModelAndView("formBandejaJefeInmediato", "lsrqnp", lsRqnp).addObject("tituloVista", tituloVista);
				}
				
				Exception ex = new Exception("Usted No posee el perfil para realizar esta operación.");
				request.setAttribute("excepAttr", ex);
				return new ModelAndView("pagErrorSistema");
			}

			Exception ex = new Exception("No existe los datos del Usuario.");
			request.setAttribute("excepAttr", ex);
			return new ModelAndView("pagErrorSistema");
		}
			catch (ServiceException ex) {
			log.error("Error en RqnpController.iniciarbandejaji: " + ex.getMessage());
			throw new ServiceException(this, ex);
		} catch (Exception ex) {
			log.error("Error en RqnpController.iniciarbandejaji: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);
		} finally {
			if (log.isDebugEnabled()) log.debug("Fin - RqnpController.iniciarbandejaji");
		}
	}
	
	
	/**
     * Recuperar Lista de Meses en Json.
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView listarTipoViculoJson(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - RqnpController.listarTipoViculoJson");}
		Map<String, Object> data = new HashMap<String, Object>();
		List<Map<String, Object>> lsTipoVinculo= new ArrayList<Map<String, Object>>();
		try {
		
			lsTipoVinculo= (List<Map<String, Object>>) requerimientoNoProgramadoService.listarTipoVinculo();
			 
			data.put("identifier", "cod");
			data.put("label", "name");
			data.put("items", lsTipoVinculo);
			 
		} 
		catch(ServiceException ex){
			log.error("Error en RqnpController.listarTipoViculoJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en RqnpController.listarTipoViculoJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - RqnpController.listarTipoViculoJson");}
		}
		return new ModelAndView(jsonView,  data) ;
	}
	
	
	
	/**
     * Recuperar Lista de Vinculo en Json.
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView listarVinculoJson(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - RqnpController.listarVinculoJson");}
		Map<String, Object> data = new HashMap<String, Object>();
		List<Map<String, Object>> lsVinculo= new ArrayList<Map<String, Object>>();
		
		try {
			String tipoVinculoSel =StringUtils.trim( request.getParameter("tipoVinculoSel"));
			log.debug("tipoVinculoSel:>>>>>>>"+tipoVinculoSel);
			if (tipoVinculoSel != null ){
				if (!tipoVinculoSel.equals("")){
					lsVinculo = (List<Map<String, Object>>) requerimientoNoProgramadoService.listarVinculo(tipoVinculoSel);
				}
			}
			
			 Map<String,Object> respuesta = new HashMap<String,Object>();
			 data.put("identifier", "cod");
			 data.put("label", "name");
			 data.put("items", lsVinculo);
		} 
		catch(ServiceException ex){
			log.error("Error en RqnpController.listarVinculoJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en RqnpController.listarVinculoJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - RqnpController.listarVinculoJson");}
		}
		return new ModelAndView(jsonView,  data) ;
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
}
