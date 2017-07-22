package pe.gob.sunat.recurso2.administracion.siga.web.util;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.sojo.interchange.json.JsonSerializer;

import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.util.WebUtils;

import pe.gob.sunat.tecnologia.menu.bean.UsuarioBean;

public class BaseController extends MultiActionController {
	protected String jsonView;
	protected String streamView;
	protected JsonSerializer jsonSerializer;
	protected String paginaError;
	
	public BaseController() {
		// TODO Auto-generated constructor stub
	}
	
	public String getJsonView() {
		return jsonView;
	}

	public void setJsonView(String jsonView) {
		this.jsonView = jsonView;
	}

	public String getStreamView() {
		return streamView;
	}

	public void setStreamView(String streamView) {
		this.streamView = streamView;
	}

	public JsonSerializer getJsonSerializer() {
		return jsonSerializer;
	}

	public void setJsonSerializer(JsonSerializer jsonSerializer) {
		this.jsonSerializer = jsonSerializer;
	}

	public String getPaginaError() {
		return paginaError;
	}

	public void setPaginaError(String paginaError) {
		this.paginaError = paginaError;
	}
	
	/**
	 * Coloca los datos de auditoría al holder que va a ser reutilizado más adelante.
	 * @param HttpServletRequest request, data entrante
	 * @param HttpServletResponse response, data de salida
	 * @return ModelAndView, retorna hacia la página de inicio <code>consultaSolicitudesForm</code>
	 * */
	protected void setAuditoriaBeanHolder(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("Seteando el auditoriaBeanHolder");
		UsuarioBean usuarioBean = (UsuarioBean)WebUtils.getSessionAttribute(request, "usuarioBean");	
		Timestamp fecCrea = new Timestamp(new java.util.Date().getTime());
		Timestamp fecModif = new Timestamp(new java.util.Date().getTime());
		String codUsucrea = usuarioBean.getLogin();//TODO QUEDA COMO EL NUMERO DE LOGIN DEL USUARIO
		String codUsumodif = usuarioBean.getLogin();//TODO QUEDA COMO EL NUMERO DE LOGIN DEL USUARIO 
		String dirUsucrea = request.getRemoteAddr();
		String dirUsumodif = request.getRemoteAddr();
//		AuditoriaBeanHolder.set(fecCrea, fecModif, codUsucrea, codUsumodif, dirUsucrea, dirUsumodif);
	}
}