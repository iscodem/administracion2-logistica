package pe.gob.sunat.administracion2.siga.rqnp.web.view;

import java.text.SimpleDateFormat;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.sojo.interchange.json.JsonSerializer;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.view.AbstractView;

/**
 * Devuelve en el request los datos recibidos por parametro, a una cadena de formato JSON. Utiliza la libreria de SOJO 1.0 para la serializacion.
 * 
 * @author Johnny Valdez Arevalo
 * @since 09/10/2009
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
public class JsonView extends AbstractView {
	protected final Log log = LogFactory.getLog(getClass());
	private static String[] FILTERED_ATTRS = { "~unique-id~", "class" };

	protected void renderMergedOutputModel(Map map, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Object model = null;

		model = map.get("datamodel");

		JsonSerializer serializer = new JsonSerializer();
		serializer.getObjectUtil().addFormatterForType(new SimpleDateFormat("dd/MM/yyyy"), java.sql.Date.class);
		serializer.getObjectUtil().addFormatterForType(new SimpleDateFormat("dd/MM/yyyy"), java.util.Date.class);

		String serialized = null;
		if (model != null) {
			serialized = (String) serializer.serialize(model, FILTERED_ATTRS);
		} else {
			serialized = (String) serializer.serialize(map, FILTERED_ATTRS);
		}

		if (StringUtils.isEmpty(getContentType())) {
			setContentType("text/plain;charset=utf-8");
		}

		response.setContentType(getContentType());
		response.setHeader("Cache-Control", "no-cache");

		response.getWriter().write(serialized);
	}
}
