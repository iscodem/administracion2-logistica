package pe.gob.sunat.administracion2.siga.expediente.web.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

import pe.gob.sunat.recurso2.administracion.siga.expediente.model.bean.ExpedienteInternoAccionBean;
import pe.gob.sunat.recurso2.administracion.siga.expediente.service.RegistroExpedienteService;
import pe.gob.sunat.recurso2.administracion.siga.registro.service.RegistroGeneralServiceImpl;
import pe.gob.sunat.recurso2.administracion.siga.web.util.BaseController;

public class ConsultaExpedienteController extends BaseController {

	private static final Log log = LogFactory.getLog(RegistroGeneralServiceImpl.class);
	private RegistroExpedienteService registroExpedienteService;

	public RegistroExpedienteService getRegistroExpedienteService() {
		return registroExpedienteService;
	}

	public void setRegistroExpedienteService(RegistroExpedienteService registroExpedienteService) {
		this.registroExpedienteService = registroExpedienteService;
	}

	public ModelAndView seguimientoExpedienteBandeja(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) log.debug("Inicio - ConsultaExpedienteController seguimientoExpedienteBandeja");
		ModelAndView view = new ModelAndView();
		Map<String, Object> respuesta = new HashMap<String, Object>();
		String num_expediente = "";
		num_expediente = request.getParameter("numeroSeguimiento");

		log.debug("num_expediente: " + num_expediente);
		Collection<ExpedienteInternoAccionBean> listSecuencia = registroExpedienteService.listaSecuenciaExpediente(num_expediente);
		respuesta.put("listSecuencia", listSecuencia);

		view = new ModelAndView(getJsonView(), respuesta);
		return view;
	}
}
