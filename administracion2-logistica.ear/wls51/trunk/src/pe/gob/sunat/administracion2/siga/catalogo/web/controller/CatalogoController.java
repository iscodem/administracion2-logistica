package pe.gob.sunat.administracion2.siga.catalogo.web.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import pe.gob.sunat.administracion2.siga.rqnp.service.RequerimientoAucService;
import pe.gob.sunat.administracion2.siga.rqnp.service.RequerimientoNoProgramadoService;
import pe.gob.sunat.administracion2.siga.rqnp.service.SolicitudBienService;
import pe.gob.sunat.framework.core.json.JsonUtil;
import pe.gob.sunat.framework.spring.util.exception.ServiceException;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.MaestroPersonalBean;
import pe.gob.sunat.recurso2.administracion.siga.registro.service.RegistroPersonalService;
import pe.gob.sunat.recurso2.administracion.siga.util.FechaUtil;
import pe.gob.sunat.recurso2.administracion.siga.util.FormatoConstantes;
import pe.gob.sunat.recurso2.administracion.siga.web.util.BaseController;
public class CatalogoController extends BaseController{

private static final Log log = LogFactory.getLog(CatalogoController.class);
	private SolicitudBienService				solicitudBienService;
	private RegistroPersonalService 			registroPersonalService;
	private RequerimientoNoProgramadoService	requerimientoNoProgramadoService;
	private RequerimientoAucService 			requerimientoAucService;
	private ReloadableResourceBundleMessageSource 		messageSource;

	/**
	 * @author dporrasc
     * Busca los bienes y servicios del Catalogo para agregar al RQNP.
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	@SuppressWarnings("unchecked")
	public ModelAndView buscarCatalogoJson(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - CatalogoController.buscarCatalogoJson");}
		Map<String, Object>  mapCatalogo = new HashMap<String, Object> ();
		Map<String, Object>  respuesta = new HashMap<String, Object> ();
		String lsAnio=FormatoConstantes.CADENA_VACIA;
		ModelAndView viewPage = null;
		try {
			String parm = StringUtils.trim(request.getParameter("parmBuscaCatalogo"));
			String tipo = StringUtils.trim(request.getParameter("tipoBuscaCatalogo"));
			
			lsAnio=FechaUtil.obtenerAnioActual();
			
			parm= parm.toUpperCase().replace(" ", "%");
			MaestroPersonalBean  maestroPersonal=  (MaestroPersonalBean) WebUtils.getSessionAttribute(request, "maestroPersonalBean");
			
			String codPlaza = maestroPersonal.getCod_plaza();
			
			mapCatalogo.put("parm", parm);
			mapCatalogo.put("tipo", tipo);
			mapCatalogo.put("anio_ejec", lsAnio);
			mapCatalogo.put("cod_plaza", codPlaza);
			mapCatalogo.put("cod_dep", registroPersonalService.obtenerUuooNoSupervision(maestroPersonal.getCodigoDependencia(),"4"));
			
			List<Map<String, Object>> lsCatalogo = (List<Map<String, Object>>) requerimientoNoProgramadoService.listarCatalogo(mapCatalogo);

			respuesta.put("listCatalogo", lsCatalogo);
			respuesta.put("mapCatalogo", JsonUtil.toString(mapCatalogo));
			viewPage = new ModelAndView(getJsonView(), respuesta);
		} 
		catch(ServiceException ex){
			log.error("Error en CatalogoController.buscarCatalogoJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en CatalogoController.buscarCatalogoJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - CatalogoController.buscarCatalogoJson");}
		}
		return viewPage;
	}
	
	
	/**
	 * @author dporrasc
     * Obtener el saldo de los bienes y servicios del Catalogo para agregar al RQNP.
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	@SuppressWarnings("unchecked")
	public ModelAndView getSaldoItemJson(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("debug Inicio - CatalogoController.getSaldoItemJson");}
		Map<String, Object>  params = new HashMap<String, Object> ();
		Map<String, Object>  respuesta = new HashMap<String, Object> ();
		String lsAnio=FormatoConstantes.CADENA_VACIA;
		BigDecimal saldoItem = new BigDecimal(0);
		ModelAndView viewPage = null;
		try {
			String codigoBien = StringUtils.trim(request.getParameter("codigoBien"));
			
			lsAnio=FechaUtil.obtenerAnioActual();
			
			MaestroPersonalBean  maestroPersonal=  (MaestroPersonalBean) WebUtils.getSessionAttribute(request, "maestroPersonalBean");
			
			String codPlaza = maestroPersonal.getCod_plaza();
			//mapCatalogo.put("parm", parm);
			params.put("anio_pro", lsAnio);
			params.put("cod_plaza", codPlaza);
			params.put("cod_bien", codigoBien);
			//mapCatalogo.put("cod_dep", registroPersonalService.obtenerUuooNoSupervision(maestroPersonal.getCodigoDependencia(),"4"));
			
			saldoItem = (BigDecimal) requerimientoNoProgramadoService.getSaldoItem(params);

			respuesta.put("saldoItem", saldoItem);
			respuesta.put("codigoBien", codigoBien);
			viewPage = new ModelAndView(getJsonView(), respuesta);
		} 
		catch(ServiceException ex){
			log.error("Error en CatalogoController.getSaldoItemJson: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en CatalogoController.getSaldoItemJson: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - CatalogoController.getSaldoItemJson");}
		}
		return viewPage;
	}
	
	
	//Getter and Setter
	public SolicitudBienService getSolicitudBienService() {
		return solicitudBienService;
	}
	public void setSolicitudBienService(SolicitudBienService solicitudBienService) {
		this.solicitudBienService = solicitudBienService;
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
}