package pe.gob.sunat.administracion2.siga.registro.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.DependenciaBean;
import pe.gob.sunat.recurso2.administracion.siga.registro.service.RegistroDependenciasService;
import pe.gob.sunat.recurso2.administracion.siga.registro.util.DependenciaConstantes;
import pe.gob.sunat.recurso2.administracion.siga.registro.util.RegistroUtil;
import pe.gob.sunat.recurso2.administracion.siga.util.FormatoConstantes;
import pe.gob.sunat.recurso2.administracion.siga.web.util.BaseController;
import pe.gob.sunat.tecnologia.menu.bean.UsuarioBean;

public class ConsultaDependenciasController extends BaseController {

	protected final Log log = LogFactory.getLog(getClass());

	private RegistroDependenciasService registroDependenciasService;

	public RegistroDependenciasService getRegistroDependenciasService() {
		return registroDependenciasService;
	}

	public void setRegistroDependenciasService(RegistroDependenciasService registroDependenciasService) {
		this.registroDependenciasService = registroDependenciasService;
	}

	/**
	 * Obtiene la carga uuoo.
	 *
	 * @author Eduardo Marchena
	 * @see ModelAndView
	 * @param request : de la clase HttpServletRequest
	 * @param response : de la clase HttpServletResponse
	 * @param tipoBuscarUUOO : obtenida del request.getParameter
	 * @param parmBuscaUUOO : obtenida del request.getParameter
	 * @retur objeto view de la clase ModelAndView tipo Json con UUOOs
	 * @throws Exception
	 */
	public ModelAndView getCargaUuoo(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> respuesta = new HashMap<String, Object>();
		Map<String, Object> parmSearch = new HashMap<String, Object>();
		ModelAndView viewPage = null;

		try {
			log.debug("Iniciando Dependencias -- getCargaUuoo");
			UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
			request.getSession().setAttribute("usuarioBean", usuarioBean);


			String tipoBuscarUUOO=(request.getParameter("tipoBuscarUUOO").isEmpty()?null:request.getParameter("tipoBuscarUUOO")) ;
			String parmBuscaUUOO=(request.getParameter("parmBuscaUUOO").isEmpty()?null:request.getParameter("parmBuscaUUOO") );

			if(FormatoConstantes.UNO.equals(tipoBuscarUUOO)){
				parmSearch.put("num_uuoo", FormatoConstantes.PORCENTAJE+parmBuscaUUOO+FormatoConstantes.PORCENTAJE);
			}else if(FormatoConstantes.DOS.equals(tipoBuscarUUOO)){
				parmSearch.put("des_dep",FormatoConstantes.PORCENTAJE+ parmBuscaUUOO+FormatoConstantes.PORCENTAJE);
			}
			parmSearch.put("estado_id",FormatoConstantes.UNO);
			List<DependenciaBean> listUUOO= (List<DependenciaBean>)registroDependenciasService.searchDependencias(parmSearch);

			respuesta.put("listUUOO",listUUOO);

			viewPage = new ModelAndView(getJsonView(), respuesta);

		} catch (Exception e) {
			log.error("Error consulta getCargaUuoo", e);
		}
		return viewPage;

	}


	/**
	 * Obtiene la carga uuoo movilidad.
	 *
	 * @author Eduardo Marchena
	 * @see ModelAndView
	 * @param request : de la clase HttpServletRequest
	 * @param response : de la clase HttpServletResponse
	 * @param tipoBuscarUUOO : obtenida del request.getParameter
	 * @param parmBuscaUUOO : obtenida del request.getParameter
	 * @param parmRegistrador : obtenida del request.getParameter
	 * @param parmTodos : obtenida del request.getParameter
	 * @retur objeto view de la clase ModelAndView tipo Json con UUOOs
	 * @throws Exception
	 */
	public ModelAndView getCargaUuooMovilidad(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> respuesta = new HashMap<String, Object>();
		Map<String, Object> parmSearch = new HashMap<String, Object>();
		ModelAndView viewPage = null;

		try {
			log.debug("Iniciando Dependencias -- getCargaUuooMovilidad");
			UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
			request.getSession().setAttribute("usuarioBean", usuarioBean);


			String tipoBuscarUUOO=(request.getParameter("tipoBuscarUUOO").isEmpty()?null:request.getParameter("tipoBuscarUUOO")) ;
			String parmBuscaUUOO=(request.getParameter("parmBuscaUUOO").isEmpty()?null:request.getParameter("parmBuscaUUOO") );
			String parmRegistrador=(request.getParameter("parmRegistrador").isEmpty()?null:request.getParameter("parmRegistrador") );
			String parmTodos=(request.getParameter("parmTodos").isEmpty()?null:request.getParameter("parmTodos") );

			if(DependenciaConstantes.BUSCAR_FILTRO.equals(parmTodos)){
				if(FormatoConstantes.UNO.equals(tipoBuscarUUOO)){
					parmSearch.put("num_uuoo", FormatoConstantes.PORCENTAJE+parmBuscaUUOO+FormatoConstantes.PORCENTAJE);
				}else if(FormatoConstantes.DOS.equals(tipoBuscarUUOO)){
					parmSearch.put("des_dep",FormatoConstantes.PORCENTAJE+ parmBuscaUUOO+FormatoConstantes.PORCENTAJE);
				}
			}
			parmSearch.put("codRegistrador", parmRegistrador);


			parmSearch.put("estado_id",FormatoConstantes.UNO);
			List<DependenciaBean> listUUOO= (List<DependenciaBean>)registroDependenciasService.searchDependenciasMovilidad(parmSearch);

			respuesta.put("listUUOO",listUUOO);

			viewPage = new ModelAndView(getJsonView(), respuesta);

		} catch (Exception e) {
			log.error("Error consulta getCargaUuooMovilidad", e);
		}
		return viewPage;

	}


	/**
	 * Obtiene la carga uuoo.
	 *
	 * @author Eduardo Marchena
	 * @see ModelAndView
	 * @param request : de la clase HttpServletRequest
	 * @param response : de la clase HttpServletResponse
	 * @param parmBuscaUUOO : obtenida del request.getParameter
	 * @param parmTodos : obtenida del request.getParameter
	 * @retur objeto view de la clase ModelAndView tipo Json con UUOO
	 * @throws Exception
	 */

	public ModelAndView getUuoo(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> respuesta = new HashMap<String, Object>();
		//Map<String, Object> parmSearch = new HashMap<String, Object>();
		ModelAndView viewPage = null;

		try {
			log.debug("Iniciando Dependencias -- getUuoo");
			UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
			request.getSession().setAttribute("usuarioBean", usuarioBean);

			String parmBuscaUUOO=(request.getParameter("parmBuscaUUOO").isEmpty()?null:request.getParameter("parmBuscaUUOO") );

			DependenciaBean dependencia= registroDependenciasService.getDependencias(parmBuscaUUOO);
			if (dependencia ==null){
				dependencia = new DependenciaBean();
				dependencia.setUuoo("-1");
				dependencia.setNom_largo("El uuoo no existe");
			}
			respuesta.put("dependencia",dependencia);

			viewPage = new ModelAndView(getJsonView(), respuesta);

		} catch (Exception e) {
			log.error("Error consulta getUuoo", e);
		}
		return viewPage;

	}

	/**
	 * Buscar unidad organizacional.
	 *
	 * @author jponce
	 * @param request the request
	 * @param response the response
	 * @return Obtiene los UUOO's de acuerdo a los parametros de busqueda en JSon.
	 */

	/**
	 * Buscar unidad organizacional.
	 *
	 * @author Eduardo Marchena
	 * @see ModelAndView
	 * @param request : de la clase HttpServletRequest
	 * @param response : de la clase HttpServletResponse
	 * @param codigoTipoBusquedaUUOO : obtenida del request.getParameter
	 * @param descripcionBuscarUUOO : obtenida del request.getParameter
	 * @param codigoRegistrador : obtenida del request.getParameter
	 * @param flagParametroTodos : obtenida del request.getParameter
	 * @param codigoTipoPagina : obtenida del request.getParameter
	 * @retur objeto view de la clase ModelAndView tipo Json con los UUOO's de acuerdo a los parametros de busqueda
	 * @throws Exception
	 */

	public ModelAndView buscarUnidadOrganizacional(HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> respuesta = new HashMap<String, Object>();
		Map<String, Object> parmSearch = new HashMap<String, Object>();
		ModelAndView viewPage = null;

		try {
			log.debug(ConsultaDependenciasController.class.getSimpleName() + ".buscarUnidadOrganizacional");
			String codigoTipoBusquedaUUOO = request.getParameter("codigoTipoBusquedaUUOO");
			String descripcionBuscarUUOO = request.getParameter("descripcionBuscarUUOO");
			String codigoRegistrador = request.getParameter("codigoRegistrador");
			String flagParametroTodos = request.getParameter("flagParametroTodos");
			String flagAutoriza = request.getParameter("flagAutoriza");
			String codigoTipoUUOOViatico = request.getParameter("codigoTipoUUOOViatico");
			
			codigoTipoBusquedaUUOO = RegistroUtil.validarEmptyToNull(codigoTipoBusquedaUUOO);
			descripcionBuscarUUOO = RegistroUtil.validarEmptyToNull(descripcionBuscarUUOO);
			codigoRegistrador = RegistroUtil.validarEmptyToNull(codigoRegistrador);
			flagParametroTodos = RegistroUtil.validarEmptyToNull(flagParametroTodos);
			codigoTipoUUOOViatico = RegistroUtil.validarEmptyToNull(codigoTipoUUOOViatico);

			if (DependenciaConstantes.BUSCAR_FILTRO.equals(flagParametroTodos)) {
				if (DependenciaConstantes.TIPO_BUSQUEDA_UUOO.equals(codigoTipoBusquedaUUOO)) {
					parmSearch.put("num_uuoo", DependenciaConstantes.PORCENTAJE + descripcionBuscarUUOO + DependenciaConstantes.PORCENTAJE);
				}
				else if (DependenciaConstantes.TIPO_BUSQUEDA_DESCRIPCION_UUOO.equals(codigoTipoBusquedaUUOO)) {
					parmSearch.put("des_dep", DependenciaConstantes.PORCENTAJE + descripcionBuscarUUOO + DependenciaConstantes.PORCENTAJE);
				}
			}
			parmSearch.put("codRegistrador", codigoRegistrador);
			parmSearch.put("estado_id", "1");
			parmSearch.put("indRegistro", "V");
			
			if("1".equals(flagAutoriza)){
				parmSearch.put("uuoo_autoriza_viaticos", "1");	
			}
			
			
			List<DependenciaBean> dependenciaList = null;
			if (codigoTipoUUOOViatico != null && DependenciaConstantes.TIPO_PAGINA_GENERAL.equals(codigoTipoUUOOViatico)) {
				log.debug("Registrador general");
				dependenciaList = (List<DependenciaBean>) registroDependenciasService.obtenerAllViaticoIndividual(parmSearch);
			}
			else if (codigoTipoUUOOViatico != null && DependenciaConstantes.TIPO_PAGINA_ESPECIFICO.equals(codigoTipoUUOOViatico)) {
				log.debug("Registrador regitrador");
				dependenciaList = (List<DependenciaBean>) registroDependenciasService.searchDependenciasViatico(parmSearch);
			}
			else if (codigoTipoUUOOViatico != null && DependenciaConstantes.TIPO_PAGINA_ESPECIFICO_UNIVERSAL.equals(codigoTipoUUOOViatico)) {
				log.debug("Registrador universal");
				dependenciaList = (List<DependenciaBean>) registroDependenciasService.obtenerAllViaticoIndividual(parmSearch);
			}
			

			respuesta.put("dependenciaList", dependenciaList);
			viewPage = new ModelAndView(getJsonView(), respuesta);

		} catch (Exception e) {
			log.error("Error consulta buscarUnidadOrganizacional", e);
		}

		return viewPage;
	}
	
	/**
	 * Buscar intentencia
	 *
	 * @author Juan Farro
	 * @see ModelAndView
	 * @param request : de la clase HttpServletRequest
	 * @param response : de la clase HttpServletResponse
	 * @param num_uuoo : obtenida del request.getParameter
	 * @retur objeto view de la clase ModelAndView tipo Json con la intendencia de acuerdo a los parametros de busqueda
	 * @throws Exception
	 */
	public ModelAndView buscarIntendencia(HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> respuesta = new HashMap<String, Object>();
		Map<String, Object> parmSearch = new HashMap<String, Object>();
		ModelAndView viewPage = null;

		try {
			log.debug(ConsultaDependenciasController.class.getSimpleName() + ".buscarIntendencia");
			
			String num_uuoo = StringUtils.trimToEmpty( request.getParameter("num_uuoo") );
 
			parmSearch.put("num_uuoo", num_uuoo);
			
			List<DependenciaBean> dependenciaList = (List<DependenciaBean>) registroDependenciasService.buscarIntendenciaByUnidadOrganizacional(num_uuoo);

			respuesta.put("dependenciaList", dependenciaList);
			
			viewPage = new ModelAndView(getJsonView(), respuesta);

		} catch (Exception e) {
			log.error("Error consulta buscarIntendencia", e);
		}

		return viewPage;
	}	
}
