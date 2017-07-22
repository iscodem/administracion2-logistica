package pe.gob.sunat.recurso2.administracion.siga.ldap.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pe.gob.sunat.recurso2.administracion.siga.firma.service.ConsultaFirmaService;
import pe.gob.sunat.recurso2.administracion.siga.firma.util.FirmaConstantes;
import pe.gob.sunat.recurso2.administracion.siga.ldap.dao.LdapDAO;

public class ValidadorLdapServiceImpl implements ValidadorLdapService {
	
	private ConsultaFirmaService consultaFirmaService;

	private static final Log log = LogFactory.getLog(ValidadorLdapServiceImpl.class);
	
	public Boolean validarUsuario(String user, String password) throws Exception {
		
		Boolean resultado = false;
		
    	try{
    		LdapDAO ldap = new LdapDAO("ldapintranet");//LdapDAO ldap = new LdapDAO(propiedades.leePropiedad("menu.internet.service.ldap.jndi"));
    		//log.debug("password: [" + password.trim() + "] " + !"".equals(password.trim()));
    		if ("".equals(password.trim())) {
    			resultado = false;
    		}
    		else {
    			ldap.firmar(user, password);
        		resultado = true;
    		}
    	}
    	catch(Exception e){
    		log.error("erorr ldap autenticacion ", e);
    		resultado = false;
    	}
    	log.debug("resultado validarUsuario: " + resultado);

		return resultado;
	}

	public Boolean validarLdapLevantado() throws Exception {

		Boolean resultado = false;

    	try{
    		LdapDAO ldap = new LdapDAO("ldapintranet");
       		ldap.probarConexionLdap();
       		resultado = true;
    	}
    	catch(Exception e){
    		log.error("erorr ldap validacion ", e);
    		resultado = false;
    	}
    	log.debug("resultado validarLdapLevantado: " + resultado);

		return resultado;
	}

	public Map<String, Object> validarLdapDevolverToken(String user, String password, String codFirma) throws Exception {

		Map<String, Object> resultado = new HashMap<String, Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmss");

	  	if (validarLdapLevantado()) {
	  		if (validarUsuario(user, password)) {
	  			Date fechaValidacionLdap = new Date();
	  			String codToken = sdf.format(fechaValidacionLdap);
	  		  	codToken = codToken + codFirma;

				resultado.put("error", "0");
				resultado.put("codToken", codToken);
	  		}
	  		else {
				resultado.put("error", "1");
				resultado.put("codigoError", "CUS01E02");
				String mensajeError = consultaFirmaService.recuperarDescripcionParametro(FirmaConstantes.PARAMETRO_MENSAJES, "CUS01E02", "D", FirmaConstantes.PARAMETRO_MODULO_SIGA, true);				
				resultado.put("mensajeError", mensajeError);//resultado.put("mensajeError", "No es posible establecer comunicaci&oacute;n con el servidor ldap, por favor intentarlo mas tarde.");
	  		}
	  	}
  		else {
			resultado.put("codigoError", "ED03");
			String mensajeError = consultaFirmaService.recuperarDescripcionParametro(FirmaConstantes.PARAMETRO_MENSAJES, "ED03", "D", FirmaConstantes.PARAMETRO_MODULO_SIGA, true);
			resultado.put("error", "1");
			resultado.put("mensajeError", mensajeError);//resultado.put("mensajeError", "El usuario y/o password no es correcto. Se requiere los datos de acceso a INTRANET para continuar el proceso. Por favor, ingresar nuevamente.");
  		}
		
		return resultado;
	}

	public void setConsultaFirmaService(ConsultaFirmaService consultaFirmaService) {
		this.consultaFirmaService = consultaFirmaService;
	}

	public ConsultaFirmaService getConsultaFirmaService() {
		return consultaFirmaService;
	}
}
