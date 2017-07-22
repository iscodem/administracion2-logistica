package pe.gob.sunat.administracion2.siga.registro.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

import pe.gob.sunat.administracion2.siga.rqnp.util.RqnpConstantes;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.MaestroPersonalBean;
import pe.gob.sunat.recurso2.administracion.siga.registro.service.RegistroPersonalService;
import pe.gob.sunat.recurso2.administracion.siga.registro.util.ColaboradorConstantes;
import pe.gob.sunat.recurso2.administracion.siga.registro.util.RegistroUtil;
import pe.gob.sunat.recurso2.administracion.siga.util.FormatoConstantes;
import pe.gob.sunat.recurso2.administracion.siga.web.util.BaseController;

public class ConsultaColaboradorController extends BaseController {

	protected final Log log = LogFactory.getLog(getClass());
	private RegistroPersonalService registroPersonalService;

	public RegistroPersonalService getRegistroPersonalService() {
		return registroPersonalService;
	}

	public void setRegistroPersonalService(RegistroPersonalService registroPersonalService) {
		this.registroPersonalService = registroPersonalService;
	}

	/**
	 * Obtiene la carga colaboradores.
	 *
	 * @author Eduardo Marchena
	 * @see ModelAndView
	 * @param request : de la clase HttpServletRequest
	 * @param response : de la clase HttpServletResponse
	 * @param tipoBuscaColaborador : obtenida del request.getParameter
	 * @param parmBuscaColaborador : obtenida del request.getParameter
	 * @return objeto view de la clase ModelAndView tipo Json con colaboradores
	 * @throws Exception the exception
	 */
	public ModelAndView getCargaColaboradores(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> respuesta = new HashMap<String, Object>();
		Map<String, Object> parmSearch = new HashMap<String, Object>();
		ModelAndView viewPage = null;

		try {
			log.debug("Iniciando Registrador -- getCargaColaboradores");
			String tipoBuscaColaborador=(request.getParameter("tipoBuscaColaborador").isEmpty()?null:request.getParameter("tipoBuscaColaborador")) ;
			String parmBuscaColaborador=(request.getParameter("parmBuscaColaborador").isEmpty()?null:request.getParameter("parmBuscaColaborador") );

			if(FormatoConstantes.UNO.equals(tipoBuscaColaborador)){
				parmSearch.put("cod_reg", FormatoConstantes.PORCENTAJE+parmBuscaColaborador+FormatoConstantes.PORCENTAJE);
			}else if(FormatoConstantes.DOS.equals(tipoBuscaColaborador)){
				parmSearch.put("nom_per",FormatoConstantes.PORCENTAJE+ parmBuscaColaborador+FormatoConstantes.PORCENTAJE);
			}else if(FormatoConstantes.TRES.equals(tipoBuscaColaborador)){
				parmSearch.put("uuoo",FormatoConstantes.PORCENTAJE+ parmBuscaColaborador+FormatoConstantes.PORCENTAJE);
			}

			List<MaestroPersonalBean> listColaboradores= (List<MaestroPersonalBean>)registroPersonalService.buscarColaboradores(parmSearch);
			respuesta.put("listColaboradores",listColaboradores);
			viewPage = new ModelAndView(getJsonView(), respuesta);

		} catch (Exception e) {
			log.error("Error consulta getCargaColaboradores", e);
		}

		return viewPage;
	}

	/**
	 * Obtiene la carga colaboradores movilidad.
	 *
	 * @author Eduardo Marchena
	 * @see ModelAndView
	 * @param request : de la clase HttpServletRequest
	 * @param response : de la clase HttpServletResponse
	 * @param tipoBuscaColaborador : obtenida del request.getParameter
	 * @param parmBuscaColaborador : obtenida del request.getParameter
	 * @param codigoRegistrador : obtenida del request.getParameter
	 * @param numeroRegistroRegistrador : obtenida del request.getParameter
	 * @param parmTodos : obtenida del request.getParameter
	 * @return objeto view de la clase ModelAndView tipo Json con colaboradores de movilidad en json
	 * @throws Exception the exception
	 */
	public ModelAndView getCargaColaboradoresMovilidad(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> respuesta = new HashMap<String, Object>();
		Map<String, Object> parmSearch = new HashMap<String, Object>();
		ModelAndView viewPage = null;

		try {
			log.debug("Iniciando Registrador -- getCargaColaboradoresMovilidad");


			String tipoBuscaColaborador=(request.getParameter("tipoBuscaColaborador").isEmpty()?null:request.getParameter("tipoBuscaColaborador")) ;
			String parmBuscaColaborador=(request.getParameter("parmBuscaColaborador").isEmpty()?null:request.getParameter("parmBuscaColaborador") );
			String codigoRegistrador=(request.getParameter("codigoRegistrador").isEmpty()?null:request.getParameter("codigoRegistrador") );
			String numeroRegistroRegistrador=(request.getParameter("numeroRegistroRegistrador").isEmpty()?null:request.getParameter("numeroRegistroRegistrador") );
			String parmTodos=(request.getParameter("parmTodos").isEmpty()?null:request.getParameter("parmTodos") );

			log.debug("codigoRegistrador: " + codigoRegistrador);
			log.debug("numeroRegistroRegistrador: " + numeroRegistroRegistrador);

			if(ColaboradorConstantes.BUSCAR_FILTRO.equals(parmTodos)){
				if(FormatoConstantes.UNO.equals(tipoBuscaColaborador)){
					parmSearch.put("cod_reg", FormatoConstantes.PORCENTAJE+parmBuscaColaborador+FormatoConstantes.PORCENTAJE);
				}else if(FormatoConstantes.DOS.equals(tipoBuscaColaborador)){
					parmSearch.put("nom_per",FormatoConstantes.PORCENTAJE+ parmBuscaColaborador+FormatoConstantes.PORCENTAJE);
				}else if(FormatoConstantes.TRES.equals(tipoBuscaColaborador)){
					parmSearch.put("uuoo",FormatoConstantes.PORCENTAJE+ parmBuscaColaborador+FormatoConstantes.PORCENTAJE);
				}
			}
			parmSearch.put("codRegistrador", codigoRegistrador);
			parmSearch.put("num_reg_registrador", numeroRegistroRegistrador);
			List<MaestroPersonalBean> listColaboradores= (List<MaestroPersonalBean>)registroPersonalService.buscarColaboradoresMovilidad(parmSearch);
			respuesta.put("listColaboradores",listColaboradores);

			viewPage = new ModelAndView(getJsonView(), respuesta);

		} catch (Exception e) {
			log.error("Error consulta getCargaColaboradoresMovilidad", e);
		}

		return viewPage;
	}

	/**
	 * Buscar colaboradores.
	 *
	 * @author Jorge Ponce
	 * @see ModelAndView
	 * @param request : de la clase HttpServletRequest
	 * @param response : de la clase HttpServletResponse
	 * @param tipoBuscaColaborador : obtenida del request.getParameter
	 * @param parmBuscaColaborador : obtenida del request.getParameter
	 * @param codigoRegistrador : obtenida del request.getParameter
	 * @param numeroRegistroRegistrador : obtenida del request.getParameter
	 * @param parmTodos : obtenida del request.getParameter
	 * @param codigoTipoPagina : obtenida del request.getParameter
	 * @return  objeto view de la clase ModelAndView tipo Json con los colaboradores de acuerdo a los parametros de busqueda.
	 */
	public ModelAndView buscarColaboradores(HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> respuesta = new HashMap<String, Object>();
		Map<String, Object> parmSearch = new HashMap<String, Object>();
		ModelAndView viewPage = null;

		try {
			log.debug(ConsultaColaboradorController.class.getSimpleName() + ".buscarColaboradores");

			String tipoBuscaColaborador = request.getParameter("tipoBuscaColaborador");
			String parmBuscaColaborador = request.getParameter("parmBuscaColaborador");

			tipoBuscaColaborador = RegistroUtil.validarEmptyToNull(tipoBuscaColaborador);
			parmBuscaColaborador = RegistroUtil.validarEmptyToNull(parmBuscaColaborador);
			
			log.debug("<<<<< tipoBuscaColaborador >>>>>" + tipoBuscaColaborador + " parmBuscaColaborador: " + parmBuscaColaborador);
			
			if (ColaboradorConstantes.TIPO_BUSQUEDA_NUM_REGISTRO.equals(tipoBuscaColaborador)) {
				parmSearch.put("cod_reg", ColaboradorConstantes.PORCENTAJE + parmBuscaColaborador + ColaboradorConstantes.PORCENTAJE);
			}
			else if (ColaboradorConstantes.TIPO_BUSQUEDA_NOMBRE.equals(tipoBuscaColaborador)) {
				parmSearch.put("nom_per", ColaboradorConstantes.PORCENTAJE + parmBuscaColaborador.replace(" ","%") + ColaboradorConstantes.PORCENTAJE);
			}
			else if (ColaboradorConstantes.TIPO_BUSQUEDA_UUOO.equals(tipoBuscaColaborador)) {
				parmSearch.put("uuoo", ColaboradorConstantes.PORCENTAJE + parmBuscaColaborador + ColaboradorConstantes.PORCENTAJE);
			}
			
			List<MaestroPersonalBean> listColaboradores = null;
			
			listColaboradores= (List<MaestroPersonalBean>)registroPersonalService.buscarColaboradores(parmSearch);

			if (listColaboradores != null) {
				log.debug(ConsultaColaboradorController.class.getSimpleName() + ".listColaboradores.size: " + listColaboradores.size());
			}

			respuesta.put("listColaboradores", listColaboradores);
			viewPage = new ModelAndView(getJsonView(), respuesta);

		} catch (Exception e) {
			log.error("Error consulta buscarColaboradores", e);
		}

		return viewPage;
	}

	/**
	 * Obtiene el colaborador.
	 * @author Eduardo Marchena
	 * @see ModelAndView
	 * @param request : de la clase HttpServletRequest
	 * @param response : de la clase HttpServletResponse
	 * @param parmBuscaColaborador : obtenida del request.getParameter
	 * @return objeto view de la clase ModelAndView tipo Json con colaborador
	 * @throws Exception the exception
	 */
	public ModelAndView getColaborador(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> respuesta = new HashMap<String, Object>();

		ModelAndView viewPage = null;

		try {
			log.debug("Iniciando Registrador -- getColaborador");
			String numRegistroColaborador=RegistroUtil.validarEmptyToNull(request.getParameter("numRegistroColaborador"));
			
			if(numRegistroColaborador!=null){
				MaestroPersonalBean colaborador= registroPersonalService.obtenerPersonaxRegistro(numRegistroColaborador);
				if (colaborador == null){
					respuesta.put("colaboradorOK",RqnpConstantes.CERO);
				}else{
					respuesta.put("colaborador",colaborador);
					respuesta.put("colaboradorOK",RqnpConstantes.UNO);
				}
			}
			
			viewPage = new ModelAndView(getJsonView(), respuesta);

		} catch (Exception e) {
			log.error("Error consulta getColaborador", e);
		}

		return viewPage;
	}
}
