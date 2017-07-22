package pe.gob.sunat.administracion2.siga.registro.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

import pe.gob.sunat.recurso2.administracion.siga.parametro.model.bean.T01ParametroBean;
import pe.gob.sunat.recurso2.administracion.siga.registro.service.RegistroGeneralService;
import pe.gob.sunat.recurso2.administracion.siga.ubigeo.model.bean.TDepartamentosBean;
import pe.gob.sunat.recurso2.administracion.siga.ubigeo.model.bean.TDistritosBean;
import pe.gob.sunat.recurso2.administracion.siga.ubigeo.model.bean.TProvinciasBean;
import pe.gob.sunat.recurso2.administracion.siga.web.util.BaseController;

public class ConsultaParametrosController extends BaseController {

	protected final Log log = LogFactory.getLog(getClass());
	
	private RegistroGeneralService registroGeneralService;

	public RegistroGeneralService getRegistroGeneralService() {
		return registroGeneralService;
	}

	public void setRegistroGeneralService(RegistroGeneralService registroGeneralService) {
		this.registroGeneralService = registroGeneralService;
	}

	/**
	 * @author emarchena
	 * @return Devuelve colaboradores en json
	 * @throws Exception
	 */
	public ModelAndView getCargaT01Parametros(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> respuesta = new HashMap<String, Object>();
		Map<String, Object> parmSearch = new HashMap<String, Object>();
		ModelAndView viewPage = null;

		try {
			log.debug("Iniciando ConsultaParametrosController -- getCargaT01Parametros");
			parmSearch.put("cod_par", "NZ");
			parmSearch.put("cod_tipo", "D");

			List<T01ParametroBean> listEstados = (List<T01ParametroBean>) registroGeneralService.recuperarParametroLista(parmSearch);

			respuesta.put("listEstados", listEstados);

			viewPage = new ModelAndView(getJsonView(), respuesta);

		} catch (Exception e) {
			log.error("Error consulta getCargaT01Parametros", e);
		}
		return viewPage;
	}

	/**
	 * @author jponce
	 * @return Obtiene la lista de Departamentos para el Ubigeo en JSon
	 * @throws Exception
	 */
	public ModelAndView obtenerDepartamentos(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String, Object> respuesta = new HashMap<String, Object>();
		ModelAndView viewPage = null;

		try {
			log.debug(ConsultaParametrosController.class.getSimpleName() + ".obtenerDepartamentos");
			ArrayList<TDepartamentosBean> departamentoList = registroGeneralService.obtenerDepartamentos();
			if (departamentoList != null && !departamentoList.isEmpty()) {
				TDepartamentosBean tDepartamentosBean = new TDepartamentosBean();
				tDepartamentosBean.setCodiDepaDpt("00");
				tDepartamentosBean.setNombDptoDpt("-- Seleccione --");
				departamentoList.add(0, tDepartamentosBean);
				log.debug(ConsultaParametrosController.class.getSimpleName() + ".size: " + departamentoList.size());
			}
			respuesta.put("departamentoList", departamentoList);
			viewPage = new ModelAndView(getJsonView(), respuesta);

		} catch (Exception e) {
			log.error("Error consulta obtenerDepartamentos", e);
		}
		return viewPage;
	}

	/**
	 * @author jponce
	 * @return Obtiene la lista de Provincias para el Ubigeo en JSon
	 * @throws Exception
	 */
	public ModelAndView obtenerProvincias(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String, Object> respuesta = new HashMap<String, Object>();
		ModelAndView viewPage = null;

		try {
			log.debug(ConsultaParametrosController.class.getSimpleName() + ".obtenerProvincias");
			String codigoDepartamento = request.getParameter("codigoDepartamento");
			if ("".equals(codigoDepartamento)) {
				codigoDepartamento = null;
			}
			ArrayList<TProvinciasBean> provinciaList = registroGeneralService.obtenerProvincias(codigoDepartamento);
			if (provinciaList != null && !provinciaList.isEmpty()) {
				TProvinciasBean tProvinciasBean = new TProvinciasBean();
				tProvinciasBean.setCodiProvTpr("00");
				tProvinciasBean.setNombProvTpr("-- Seleccione --");
				tProvinciasBean.setCodiDepaDpt("00");
				provinciaList.add(0, tProvinciasBean);
				log.debug(ConsultaParametrosController.class.getSimpleName() + ".size: " + provinciaList.size());
			}
			respuesta.put("provinciaList", provinciaList);
			viewPage = new ModelAndView(getJsonView(), respuesta);

		} catch (Exception e) {
			log.error("Error consulta obtenerProvincias", e);
		}
		return viewPage;
	}

	/**
	 * @author jponce
	 * @return Obtiene la lista de Distritos para el Ubigeo en JSon
	 * @throws Exception
	 */
	public ModelAndView obtenerDistritos(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String, Object> respuesta = new HashMap<String, Object>();
		ModelAndView viewPage = null;

		try {
			log.debug(ConsultaParametrosController.class.getSimpleName() + ".obtenerDistritos");
			String codigoDepartamento = request.getParameter("codigoDepartamento");
			String codigoProvincia = request.getParameter("codigoProvincia");
			if ("".equals(codigoDepartamento)) {
				codigoDepartamento = null;
			}
			if ("".equals(codigoProvincia)) {
				codigoProvincia = null;
			}
			ArrayList<TDistritosBean> distritoList = registroGeneralService.obtenerDistritos(codigoDepartamento, codigoProvincia);
			if (distritoList != null && !distritoList.isEmpty()) {
				TDistritosBean tDistritosBean = new TDistritosBean();
				tDistritosBean.setCodiDistTdi("00");
				tDistritosBean.setNombDistTdi("-- Seleccione --");
				tDistritosBean.setCodiDepaDpt("00");
				tDistritosBean.setCodiProvTpr("00");
				distritoList.add(0, tDistritosBean);
				log.debug(ConsultaParametrosController.class.getSimpleName() + ".size: " + distritoList.size());
			}
			respuesta.put("distritoList", distritoList);
			viewPage = new ModelAndView(getJsonView(), respuesta);

		} catch (Exception e) {
			log.error("Error consulta obtenerDistritos", e);
		}
		return viewPage;
	}

}
