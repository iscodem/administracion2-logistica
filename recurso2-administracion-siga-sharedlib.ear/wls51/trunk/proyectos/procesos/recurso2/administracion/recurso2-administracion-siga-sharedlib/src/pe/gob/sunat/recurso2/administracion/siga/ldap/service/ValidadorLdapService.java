package pe.gob.sunat.recurso2.administracion.siga.ldap.service;

import java.util.Map;

public interface ValidadorLdapService {
	
	public Boolean validarUsuario(String user, String password) throws Exception;
	
	public Boolean validarLdapLevantado() throws Exception;

	public Map<String, Object> validarLdapDevolverToken(String user, String pass, String codFirma) throws Exception ;
}
