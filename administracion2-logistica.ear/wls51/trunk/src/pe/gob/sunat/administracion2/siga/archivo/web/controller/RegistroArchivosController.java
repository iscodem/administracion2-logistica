package pe.gob.sunat.administracion2.siga.archivo.web.controller;

import java.io.OutputStream; 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

import pe.gob.sunat.framework.spring.util.exception.ServiceException;
import pe.gob.sunat.recurso2.administracion.siga.archivo.model.bean.RegistroArchivosFisicoBean;
import pe.gob.sunat.recurso2.administracion.siga.archivo.model.bean.TipoDocumentoBean;
import pe.gob.sunat.recurso2.administracion.siga.archivo.service.RegistroArchivosService;
import pe.gob.sunat.recurso2.administracion.siga.web.util.BaseController;

public class RegistroArchivosController extends BaseController {

	protected final Log log = LogFactory.getLog(getClass());

	private RegistroArchivosService registroArchivosService;

	// private String uploadDir;

	/**
	 * @author jsaccatoma
	 * @return Devuelve lista de archivos adjuntos en json
	 * @param numeroRegitroArchivo
	 *            : Numero de Registro de Archivo
	 * @throws Exception
	 */
	public ModelAndView getCargaArchivosAdjuntos(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> respuesta = new HashMap<String, Object>();
		Map<String, Object> parmSearch = new HashMap<String, Object>();
		ModelAndView viewPage = null;

		try {
			log.debug("Iniciando Registrador -- getCargaArchivosAdjuntos");

			String numeroRegitroArchivo = (request.getParameter("numeroRegitroArchivo").isEmpty() ? null : request.getParameter("numeroRegitroArchivo"));
			// String codigoAplicacion=(request.getParameter("hidApliacion").isEmpty()?null:request.getParameter("hidApliacion")) ;
			// String codigoModulo=(request.getParameter("hidModulo").isEmpty()?null:request.getParameter("hidModulo")) ;
			//
			parmSearch.put("num_reg", numeroRegitroArchivo);
			log.debug("yyyyy" + parmSearch);
			List<RegistroArchivosFisicoBean> listArchivosAdj = (List<RegistroArchivosFisicoBean>) registroArchivosService.listarArchivosAdjuntos(parmSearch);
			parmSearch.clear();

			// parmSearch.put("cod_app", codigoAplicacion);
			// parmSearch.put("cod_mod", codigoModulo);
			List<TipoDocumentoBean> listTipoDocumento = (List<TipoDocumentoBean>) registroArchivosService.listarTipoDocumento(parmSearch);

			respuesta.put("listArchivosAdj", listArchivosAdj);
			respuesta.put("listTipoDocumento", listTipoDocumento);
			// respuesta.put("ok","ok");
			viewPage = new ModelAndView(getJsonView(), respuesta);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return viewPage;

	}

	/**
	 * @author jsaccatoma
	 * @return Devuelve lista de archivos adjuntos en json
	 * @param numeroRegitroArchivo
	 *            : Numero de Registro de Archivo
	 * @throws Exception
	 */
	public ModelAndView getCargaTipoDocumentos(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> respuesta = new HashMap<String, Object>();
		Map<String, Object> parmSearch = new HashMap<String, Object>();
		ModelAndView viewPage = null;

		try {
			log.debug("Iniciando Registrador -- getCargaTipoDocumentos");

			String codigoAplicacion = (request.getParameter("hidApliacion").isEmpty() ? null : request.getParameter("hidApliacion"));
			String codigoModulo = (request.getParameter("hidModulo").isEmpty() ? null : request.getParameter("hidModulo"));
			parmSearch.clear();
			parmSearch.put("cod_app", codigoAplicacion);
			parmSearch.put("cod_mod", codigoModulo);
			List<TipoDocumentoBean> listTipoDocumento = (List<TipoDocumentoBean>) registroArchivosService.listarTipoDocumento(parmSearch);

			respuesta.put("listTipoDocumento", listTipoDocumento);

			viewPage = new ModelAndView(getJsonView(), respuesta);

		} catch (Exception e) {
			log.debug(e);
		}
		return viewPage;

	}

	/**
	 * Descargar Archivos Adjuntos de los item
	 * 
	 * @param request
	 * @param response
	 * @return ModelAndView
	 **/
	public ModelAndView descargarArchivo(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()) {
			log.debug("debug Inicio - RegistroArchivosController.descargarArchivo");
		}
		Map<String, Object> params = new HashMap<String, Object>();

		try {
			String sec_reg = StringUtils.trim(request.getParameter("sec_reg"));
			log.debug("sec_reg " + sec_reg);
			params.put("sec_reg", sec_reg);
			RegistroArchivosFisicoBean archivo = registroArchivosService.recuperarArchivo(params);

			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment;filename=" + archivo.getFile_name());

			OutputStream os = response.getOutputStream();

			os.write(archivo.getData());

			os.flush();
			os.close();
			return null;
		} catch (ServiceException e) {
			log.error("Error en RegistroArchivosController.descargarArchivo: " + e.getMessage(), e);
			throw new ServiceException(this, e);
		} catch (Exception e) {
			log.error("Error en RegistroArchivosController.descargarArchivo: " + e.getMessage(), e);
			throw new ServiceException(this, e);
		} finally {
			if (log.isDebugEnabled()) {
				log.debug("Fin - RegistroArchivosController.descargarArchivo");
			}
		}

	}

	public RegistroArchivosService getRegistroArchivosService() {
		return registroArchivosService;
	}

	public void setRegistroArchivosService(RegistroArchivosService registroArchivosService) {
		this.registroArchivosService = registroArchivosService;
	}

	/**
	 * Obtener lista tipo Documento.
	 * 
	 * @param request
	 * @param response
	 * @return ModelAndView
	 **/
	@SuppressWarnings("unchecked")
	public ModelAndView getTipoDocumento(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()) {
			log.debug("Inicio -  RegistroArchivosController.getTipoDocumento");
		}
		Map<String, Object> respuesta = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		List<Map<String, Object>> listTiposDocumentos = new ArrayList<Map<String, Object>>();
		ModelAndView viewPage = null;

		try {
			String aplicacion = StringUtils.trim(request.getParameter("aplicacion"));
			String modulo = StringUtils.trim(request.getParameter("modulo"));

			// LOGISTICA
			// RQNP
			params.put("cod_app", aplicacion);
			params.put("cod_mod", modulo);
			listTiposDocumentos = (List<Map<String, Object>>) registroArchivosService.listarTipoArchivos(params);

			respuesta.put("listTiposDocumentos", listTiposDocumentos);
			// cod
			// name
			viewPage = new ModelAndView(getJsonView(), respuesta);

		} catch (ServiceException e) {
			log.error("Error en  RegistroArchivosController.getTipoDocumento: " + e.getMessage(), e);
			throw new ServiceException(this, e);
		} catch (Exception e) {
			log.error("Error en  RegistroArchivosController.getTipoDocumento: " + e.getMessage(), e);
			throw new ServiceException(this, e);
		} finally {
			if (log.isDebugEnabled()) {
				log.debug("Fin -  RegistroArchivosController.getTipoDocumento");
			}
		}
		return viewPage;
	}
}
