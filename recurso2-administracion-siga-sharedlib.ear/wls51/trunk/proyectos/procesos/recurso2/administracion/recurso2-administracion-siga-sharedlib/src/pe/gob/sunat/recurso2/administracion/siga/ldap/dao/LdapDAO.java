package pe.gob.sunat.recurso2.administracion.siga.ldap.dao;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;

import org.apache.commons.codec.binary.Base64;

import netscape.ldap.LDAPAttribute;
import netscape.ldap.LDAPAttributeSet;
import netscape.ldap.LDAPConnection;
import netscape.ldap.LDAPEntry;
import netscape.ldap.LDAPException;
import netscape.ldap.LDAPSearchConstraints;
import netscape.ldap.LDAPSearchResults;
import netscape.ldap.LDAPv2;
import netscape.ldap.LDAPv3;
import pe.gob.sunat.framework.core.bean.MensajeBean;
import pe.gob.sunat.framework.core.dao.DAOAbstract;
import pe.gob.sunat.framework.core.dao.DAOException;
import pe.gob.sunat.framework.core.pattern.ServiceLocator;
import pe.gob.sunat.framework.util.Propiedades;
import pe.gob.sunat.tecnologia.menu.ldap.LdapHostBean;

/**
 * 
 * Clase : LdapDAO
 * Proyecto : Menu
 * Descripcion :
 * Autor : CGARRATT
 * Fecha : 21/09/2005
 */
public class LdapDAO extends DAOAbstract{

  private static final long serialVersionUID = 2143849331777638318L;
  public final static String NDS_DIRMGR = "cn=Directory Manager";
  Propiedades properties = new Propiedades(this.getClass(), "ldapcliente.properties");

  public String NDS_DIRPWD = null;
  public String ldapBean = "";

  public LdapDAO(String ldapBean){
    this.ldapBean = ldapBean;
  }

  /*
   * public static String NDS_BEANS_BASE = "ou=beans,dc=sunat,dc=gob,dc=pe";
   * public static String NDS_BASE = "ou=People,dc=sunat,dc=gob,dc=pe"; public
   * static String NDS_GROUP_BASE = "ou=Groups,dc=sunat,dc=gob,dc=pe";
   */
  public static String NDS_CTX = "com.sun.jndi.ldap.LdapCtxFactory";

  /**
   * @param puid
   *            String
   * @param ppwd
   *            String
   * @throws NamingException
   * @return InitialDirContext
   */
  protected InitialDirContext getInitialDirContext(String puid, String ppwd)
  throws NamingException {

    Hashtable delegateHT = new Hashtable();
    Hashtable envHT = getEnvironment();

    if(log.isDebugEnabled())log.debug("envHT..." + envHT);
    if (puid == null) {
      puid = (String) envHT.get("NDS_MGR");
    }
    if (ppwd == null) {
      ppwd = (String) envHT.get("NDS_PWD");
    }
    delegateHT.put(Context.INITIAL_CONTEXT_FACTORY, envHT.get("NDS_CTX"));
    delegateHT.put(Context.PROVIDER_URL, envHT.get("NDS_HOST"));

    delegateHT.put(Context.SECURITY_PRINCIPAL, LdapDAO.NDS_DIRMGR);
    delegateHT.put(Context.SECURITY_CREDENTIALS, (String) envHT.get("NDS_DIRPWD"));
    return new InitialDirContext(delegateHT);
  }

  /**
   * Obtiene todas las variables de ambiente y parametros de inicializacion
   * para el acceso al LDAP.
   * 
   * @return Hashtable
   * @throws NamingException
   */
  protected Hashtable getEnvironment() throws NamingException {
    Hashtable envHT = new Hashtable();
    try {
      if(log.isDebugEnabled())log.debug("environment..");
      if (ldapBean != null){
        LdapHostBean beanldaphost = (LdapHostBean) ServiceLocator.getInstance().lookup(this.ldapBean);
        
        String ro_host = beanldaphost.getAttribute("ldap_iphost");
        String ro_port = beanldaphost.getAttribute("ldap_puerto");
        String ro_host_puerto = "ldap://".concat(ro_host).concat(":").concat(ro_port);

        envHT.put("NDS_HOST", ro_host_puerto);
        envHT.put("NDS_LDAP", ro_host);
        envHT.put("NDS_LDAP_PORT", ro_port);
        envHT.put("NDS_MGR", beanldaphost.getAttribute("ldap_usuario"));
        envHT.put("NDS_PWD", beanldaphost.getAttribute("ldap_clave"));
        envHT.put("NDS_DIRPWD", beanldaphost.getAttribute("ldap_clavedm"));

        envHT.put("NDS_CTX", NDS_CTX);
        envHT.put("NDS_BEANS_BASE", beanldaphost.getAttribute("ldap_beans_base"));
        envHT.put("NDS_BASE", beanldaphost.getAttribute("ldap_base"));
        envHT.put("NDS_GROUP_BASE", beanldaphost.getAttribute("ldap_group_base"));
      } else {

        envHT.put("NDS_CTX", properties.leePropiedad("LDAP_CTX"));
        envHT.put("NDS_LDAP_PORT", properties.leePropiedad("LDAP_PUERTO"));
        envHT.put("NDS_LDAP", properties.leePropiedad("LDAP_IPADDRESS"));
        envHT.put("NDS_HOST", "ldap://".concat( properties.leePropiedad("LDAP_IPADDRESS") ).concat(":").concat( properties.leePropiedad("LDAP_PUERTO") ) );
        envHT.put("NDS_BASE", properties.leePropiedad("LDAP_PEOPLEBASE"));
        envHT.put("NDS_GROUP_BASE", properties.leePropiedad("LDAP_GROUPBASE"));

        envHT.put("NDS_MGR", properties.leePropiedad("LDAP_MANAGER"));
        envHT.put("NDS_PWD", properties.leePropiedad("LDAP_PWD"));
        envHT.put("NDS_DIRPWD", properties.leePropiedad("LDAP_DIRPWD"));
        if(log.isDebugEnabled())log.debug("env.." + envHT);
      }

    } catch (Exception e) {
      log.error("*** Error ***",e);
    }
    return envHT;
  }

  /**
   * Obtiene los datos de la persona en el LDAP basado en su numero de
   * registro.
   * 
   * @ejb.interface-method view-type="remote"
   * @ejb.transaction type="NotSupported"
   * 
   * @param codPers
   *            String
   * @return HashMap
   * @throws NamingException
   */
  public HashMap findByEmployeeNumber(String codPers) throws NamingException {
    return buscarEnLDAP("employeeNumber=" + codPers);
  }

  /**
   * Obtiene los datos de la persona en el LDAP basado en su cuenta de
   * usuario.
   * 
   * @ejb.interface-method view-type="remote"
   * @ejb.transaction type="NotSupported"
   * 
   * @param uid
   *            String
   * @return HashMap
   * @throws NamingException
   */
  public final HashMap findByUID(String uid) throws NamingException {
    return buscarEnLDAP("uid=" + uid);
  }

  /**
   * Obtiene los datos de la persona en el LDAP basado en el criterio de
   * busqueda.
   * 
   * @param uid
   *            String
   * @return HashMap
   * @throws NamingException
   */
  public HashMap buscarEnLDAP(String uid) throws NamingException {
    return buscarEnLDAP(uid, getInitialDirContext(null, null));
  }

  /**
   * 
   * @param uid
   *            String
   * @param sunatCtx
   *            DirContext
   * @return HashMap
   * @throws NamingException
   */
  protected HashMap buscarEnLDAP(String uid, DirContext sunatCtx)
  throws NamingException {
    String nombre = null;
    String correo = null;
    String numreg = null;
    String userid = null;
    String key = null;
    HashMap hMap = new HashMap(3);
    LDAPConnection ldap = new LDAPConnection();
    try {
      Hashtable ht = this.getEnvironment();
      mensajeLDAP(true, ht.get("NDS_LDAP"), ht.get("NDS_LDAP_PORT"));
      ldap.connect(3, (String) ht.get("NDS_LDAP"), Integer
          .parseInt((String) ht.get("NDS_LDAP_PORT")),
          LdapDAO.NDS_DIRMGR, (String) ht.get("NDS_DIRPWD"));

      LDAPSearchConstraints mySearchConstraints = ldap
      .getSearchConstraints();
      mySearchConstraints.setMaxResults(1);
      String attrs[] = { "uid", "cn", "mail", "employeeNumber", "userPKCS12" };
      LDAPSearchResults myResults = null;

      myResults = ldap.search((String) ht.get("NDS_BASE"),
          LDAPv3.SCOPE_SUB, "(&(objectclass=person)(" + uid + "))",
          attrs, false, mySearchConstraints);

      if (myResults.hasMoreElements()) {
        LDAPEntry myEntry = myResults.next();
        LDAPAttributeSet entryAttrs = myEntry.getAttributeSet();
        Enumeration attrsInSet = entryAttrs.getAttributes();
        while (attrsInSet.hasMoreElements()) {
          LDAPAttribute nextAttr = (LDAPAttribute) attrsInSet
          .nextElement();
          String attrName = nextAttr.getName();
          if (attrName.trim().equalsIgnoreCase("uid")) {
            Enumeration valsInAttr = nextAttr.getStringValues();
            if (valsInAttr.hasMoreElements()) {
              userid = (String) valsInAttr.nextElement();
            }
          }
          if (attrName.trim().equalsIgnoreCase("cn")) {
            Enumeration valsInAttr = nextAttr.getStringValues();
            if (valsInAttr.hasMoreElements()) {
              nombre = (String) valsInAttr.nextElement();
            }
          }
          if (attrName.trim().equalsIgnoreCase("mail")) {
            Enumeration valsInAttr = nextAttr.getStringValues();
            if (valsInAttr.hasMoreElements()) {
              correo = (String) valsInAttr.nextElement();
            }
          }
          if (attrName.trim().equalsIgnoreCase("employeenumber")) {
            Enumeration valsInAttr = nextAttr.getStringValues();
            if (valsInAttr.hasMoreElements()) {
              numreg = (String) valsInAttr.nextElement();
            }
          }
          if (attrName.trim().equalsIgnoreCase("userPKCS12")) {
            Enumeration valsInAttr = nextAttr.getStringValues();
            if (valsInAttr.hasMoreElements()) {
              key = (String) valsInAttr.nextElement();
            }
          }
        }
        if (userid != null) {
          hMap.put("uid", userid);
          hMap.put("nombre", nombre.trim());
          hMap.put("correo", correo != null ? correo : "");
          hMap.put("codreg", numreg);
          hMap.put("key", key);
        } else {
          hMap = null;
        }
      } else {
        hMap = null;
      }
    } catch (NamingException e) {
      throw new DAOException(this,
          "EAL_LA_NA: NO SE PUDO CARGAR EL AMBIENTE:"
          + e.getExplanation());
    } catch (LDAPException e) {
      throw new DAOException(this,
          "EAL_LA_LE: NO SE PUDO CREAR AL USUARIO POR LIMITACIONES EN EL SISTEMA:"
          + e.getLDAPResultCode()
          + "->"
          + LDAPException.errorCodeToString(e
              .getLDAPResultCode()));
    } catch (NumberFormatException e) {
      throw new DAOException(this,
          "EAL_LA_NFE: HA OCURRIDO UN PROBLEMA EN LOS PARAMETROS DEL SERVIDOR:"
          + e.getMessage());
    } finally {
      try {
        if (ldap.isConnected()) {
          ldap.disconnect();
        }
      } catch (LDAPException ex) {
        throw new DAOException(this,
            "EAL_LA_LAC: NO SE PUDO CERRAR EL ENLACE:"
            + ex.getLDAPResultCode()
            + "->"
            + LDAPException.errorCodeToString(ex
                .getLDAPResultCode()));
      }
    }
    return hMap;
  }

  /**
   * Busca a traves de cadenas de busqueda segun la sintaxis del ldap, por
   * ejemplo:
   * 
   * (uid=JVALDEZ) (cn=VALDEZ*) (|(cn=SOTO*)(cn=VALDEZ*))
   * (employeenumber=1550)
   * 
   * Cada objeto Map de la lista contiene: uid, nombre, correo, codreg.
   * 
   * @ejb.interface-method view-type="remote"
   * @ejb.transaction type="NotSupported"
   * 
   * @param patron
   *            String
   * @return List
   * @throws NamingException
   */
  public List buscarPorPatron(String patron) throws NamingException {
    LDAPConnection ldap = new LDAPConnection();
    List a = new ArrayList();
    try {
      Hashtable ht = this.getEnvironment();
      mensajeLDAP(true, ht.get("NDS_LDAP"), ht.get("NDS_LDAP_PORT"));
      ldap.setOption(LDAPv2.SIZELIMIT, new Integer(0));
      ldap.connect(3, (String) ht.get("NDS_LDAP"), Integer
          .parseInt((String) ht.get("NDS_LDAP_PORT")),
          LdapDAO.NDS_DIRMGR, (String) ht.get("NDS_DIRPWD"));

      LDAPSearchConstraints mySearchConstraints = ldap
      .getSearchConstraints();
      mySearchConstraints.setMaxResults(1000);
      String attrs[] = { "uid", "cn", "mail", "employeeNumber" };
      LDAPSearchResults myResults = null;
      myResults = ldap.search((String) ht.get("NDS_BASE"),
          LDAPv3.SCOPE_SUB, "(&(objectclass=person)" + patron + ")",
          attrs, false, mySearchConstraints);

      while (myResults.hasMoreElements()) {
        String nombre = null;
        String correo = null;
        String numreg = null;
        String userid = null;
        HashMap hMap = new HashMap(3);

        LDAPEntry myEntry = myResults.next();
        LDAPAttributeSet entryAttrs = myEntry.getAttributeSet();
        Enumeration attrsInSet = entryAttrs.getAttributes();
        while (attrsInSet.hasMoreElements()) {
          LDAPAttribute nextAttr = (LDAPAttribute) attrsInSet
          .nextElement();
          String attrName = nextAttr.getName();
          if (attrName.trim().equalsIgnoreCase("uid")) {
            Enumeration valsInAttr = nextAttr.getStringValues();
            if (valsInAttr.hasMoreElements()) {
              userid = (String) valsInAttr.nextElement();
            }
          }
          if (attrName.trim().equalsIgnoreCase("cn")) {
            Enumeration valsInAttr = nextAttr.getStringValues();
            if (valsInAttr.hasMoreElements()) {
              nombre = (String) valsInAttr.nextElement();
            }
          }
          if (attrName.trim().equalsIgnoreCase("mail")) {
            Enumeration valsInAttr = nextAttr.getStringValues();
            if (valsInAttr.hasMoreElements()) {
              correo = (String) valsInAttr.nextElement();
            }
          }
          if (attrName.trim().equalsIgnoreCase("employeenumber")) {
            Enumeration valsInAttr = nextAttr.getStringValues();
            if (valsInAttr.hasMoreElements()) {
              numreg = (String) valsInAttr.nextElement();
            }
          }
        }
        if (userid != null) {
          hMap.put("uid", userid);
          hMap.put("nombre", nombre.trim());
          hMap.put("correo", correo != null ? correo : "");
          hMap.put("codreg", numreg);
          a.add(hMap);
        }
      }
    } catch (NamingException e) {
      throw new DAOException(this,
          "EAL_LA_NA: NO SE PUDO CARGAR EL AMBIENTE:"
          + e.getExplanation());
    } catch (LDAPException e) {
      throw new DAOException(this,
          "EAL_LA_LE: NO SE PUDO CREAR AL USUARIO POR LIMITACIONES EN EL SISTEMA:"
          + e.getLDAPResultCode()
          + "->"
          + LDAPException.errorCodeToString(e
              .getLDAPResultCode()));
    } catch (NumberFormatException e) {
      throw new DAOException(this,
          "EAL_LA_NFE: HA OCURRIDO UN PROBLEMA EN LOS PARAMETROS DEL SERVIDOR:"
          + e.getMessage());
    } finally {
      try {
        if (ldap.isConnected()) {
          ldap.disconnect();
        }
      } catch (LDAPException ex) {
        throw new DAOException(this,
            "EAL_LA_LAC: NO SE PUDO CERRAR EL ENLACE:"
            + ex.getLDAPResultCode()
            + "->"
            + LDAPException.errorCodeToString(ex
                .getLDAPResultCode()));
      }
    }
    return a;
  }

  /**
   * Realiza la autenticacion en el LDAP configurado en el ambiente.
   * 
   * @ejb.interface-method view-type="remote"
   * @ejb.transaction type="NotSupported"
   * 
   * @param uid
   *            String
   * @param pwd
   *            String
   * @return Map
   * @throws DAOException
   */
  public Map firmar(String uid, String pwd)
  throws DAOException {

    Map mDN = new HashMap();
    MensajeBean beanM = new MensajeBean();
    beanM.setError(true);
    LDAPConnection ldap = new LDAPConnection();
    try {
      Hashtable ht = this.getEnvironment();
      mensajeLDAP(true, ht.get("NDS_LDAP"), ht.get("NDS_LDAP_PORT"));
      // Se conecta como anonimo..
      ldap.connect((String) ht.get("NDS_LDAP"), Integer.parseInt((String) ht.get("NDS_LDAP_PORT")),"cn=Directory Manager", (String) ht.get("NDS_DIRPWD"));
      mDN = obtenerDN(uid, ldap, ht);
      log.info("mDN: " + mDN);

      if (!mDN.isEmpty()) {
        ldap.authenticate((String) mDN.get("dn"), pwd);
        
    	beanM.setError(false);							
      } else {
        beanM.setMensajeerror(uid.concat(": El usuario indicado no existe"));
        throw new DAOException(this,beanM);
      }
    } catch (NamingException e) {
      throw new DAOException(this,"EAL_ODNA_NE: NO SE PUDO CARGAR EL AMBIENTE:"+ e.getExplanation());
    } catch (LDAPException e) {
      switch (e.getLDAPResultCode()) {
      case LDAPException.NO_SUCH_OBJECT:
        beanM.setMensajeerror(uid.concat(": El usuario indicado no existe"));
        break;
      case LDAPException.INVALID_CREDENTIALS:
        beanM.setMensajeerror(uid.concat(": Password invalido"));
        break;
      default:
        beanM.setMensajeerror(uid.concat(": No se ha podido realizar la autenticacion, error:"+ e.getLDAPResultCode()));
      break;
      }
      throw new DAOException(this,beanM);
    } catch (NumberFormatException e) {	
      throw new DAOException(this,"EAL_ODNA_NFE: HA OCURRIDO UN PROBLEMA EN LOS PARAMETROS DEL SERVIDOR:"+ e.getMessage());
    } finally {
      try {
        if (ldap.isConnected()) {
          ldap.disconnect();
        }
      } catch (LDAPException ex) {
        throw new DAOException(this,
            "EAL_ODNA_LED: NO SE PUDO CERRAR EL ENLACE:"
            + ex.getLDAPResultCode()
            + "->"
            + LDAPException.errorCodeToString(ex
                .getLDAPResultCode()) + "<br>"
                + ex.getMessage());
      }
    }		
    return mDN;
  }

  
  public boolean probarConexionLdap() throws DAOException {

	  LDAPConnection ldap = new LDAPConnection();
	  boolean resultado = false;
    
	  try {
		  Hashtable ht = this.getEnvironment();
		  mensajeLDAP(true, ht.get("NDS_LDAP"), ht.get("NDS_LDAP_PORT"));
		  // Se conecta como anonimo..
		  ldap.connect((String) ht.get("NDS_LDAP"), Integer.parseInt((String) ht.get("NDS_LDAP_PORT")),"cn=Directory Manager", (String) ht.get("NDS_DIRPWD"));
		  resultado = true;

	  } catch (Exception e) {	
		  log.error("Error de conexion ldap: ", e);
		  resultado = false;
	  } finally {
		  try {
			  if (ldap.isConnected()) {
				  ldap.disconnect();
			  }
		  } catch (LDAPException ex) {
			  resultado = false;
		  }
	  }		
	  return resultado;
  }
  
  
  /**
   * Envia mensajes a la consola, sobre el acceso al LDAP
   * 
   * @param lectura
   *            boolean
   * @param ip
   *            Object
   * @param puerto
   *            Object
   */
  protected void mensajeLDAP(boolean lectura, Object ip, Object puerto) {
    StringBuffer sb = new StringBuffer("Accediendo al LDAP (").append(lectura ? "LECTURA) : ldap://" : "TRX) : ldap://").append(ip).append(":").append(puerto);
    log.info(sb.toString());
  }

  /**
   * 
   * @ejb.interface-method view-type="remote"
   * @ejb.transaction type="NotSupported"
   * 
   * @param puid
   * @return Map
   */
  public Map cargarDatosLDAP(String puid)
  throws DAOException{

    Map mDN = new HashMap();
    LDAPConnection ldap = new LDAPConnection();
    try {

      Hashtable ht = this.getEnvironment();			
      mensajeLDAP(true, ht.get("NDS_LDAP"), ht.get("NDS_LDAP_PORT"));

      // Se conecta como anonimo..
      ldap.connect((String) ht.get("NDS_LDAP"), Integer.parseInt((String) ht.get("NDS_LDAP_PORT")),"cn=Directory Manager", (String) ht.get("NDS_DIRPWD"));

      // Prueba autenticarse con los parametros enviados.
      mDN = obtenerDN(puid, ldap, ht);

      if (mDN.isEmpty()){
        throw new DAOException(this,"El usuario indicado no existe");
      }

    } catch (LDAPException e) {
      throw new DAOException(this,
          "EAL_ODNB_LE: NO SE PUDO OBTENER EL USUARIO POR UN PROBLEMA EN EL SISTEMA:"
          + e.getLDAPResultCode()
          + "->"
          + LDAPException.errorCodeToString(e
              .getLDAPResultCode()));
    } catch (NumberFormatException e) {
      throw new DAOException(this,
          "EAL_ODNB_NFE: HA OCURRIDO UN PROBLEMA EN LOS PARAMETROS DEL SERVIDOR:"
          + e.getMessage());
    } catch (Exception e) {
      throw new DAOException(this,e.getMessage());
    } finally {
      try {
        if (ldap.isConnected()) {
          ldap.disconnect();
        }
      } catch (LDAPException ex) {
        throw new DAOException(this,
            "EAL_LA_LAC: NO SE PUDO CERRAR EL ENLACE:"
            + ex.getLDAPResultCode()
            + "->"
            + LDAPException.errorCodeToString(ex
                .getLDAPResultCode()));
      }
    }
    return mDN;		
  }

  /**
   * Retorna el DN basado en el UID del usuario, debe haber previamente una
   * conexion
   * 
   * @param puid
   *            String
   * @param ldap
   *            LDAPConnection
   * @return String
   */
  protected HashMap obtenerDN(String puid, LDAPConnection ldap, Hashtable ht)
  throws DAOException {
    HashMap m = new HashMap();
    try {

      LDAPSearchConstraints mySearchConstraints = ldap.getSearchConstraints();
      mySearchConstraints.setMaxResults(1);
      LDAPSearchResults myResults = null;

      String attrs[] = {"uid", "cn", "sn", "givenname", "mail", "roomNumber", "dn", "employeeNumber", "password"};

      myResults = ldap.search(
          (String) ht.get("NDS_BASE"),
          LDAPv2.SCOPE_SUB, 
          "(&(objectclass=person)(uid="+ puid.trim() + "))", 
          attrs, 
          false,
          mySearchConstraints);

      if (myResults.hasMoreElements()) {

        m.put("uid", puid);
        LDAPEntry myEntry = myResults.next();
        m.put("dn", myEntry.getDN());				
        LDAPAttributeSet entryAttrs = myEntry.getAttributeSet();
        Enumeration attrsInSet = entryAttrs.getAttributes();
        while (attrsInSet.hasMoreElements()) {
          LDAPAttribute nextAttr = (LDAPAttribute) attrsInSet.nextElement();					
          Enumeration valsInAttr = nextAttr.getStringValues();
          if (valsInAttr.hasMoreElements()) {
            m.put(nextAttr.getName(), valsInAttr.nextElement());					
          }
        }
      }

    } catch (LDAPException e) {
      log.error("Error en LDAP",e);
      throw new DAOException(this,
          "EAL_ODNB_LE: NO SE PUDO OBTENER EL USUARIO POR UN PROBLEMA EN EL SISTEMA:"
          + e.getLDAPResultCode()
          + "->"
          + LDAPException.errorCodeToString(e.getLDAPResultCode()));
    } catch (NumberFormatException e) {
      log.error("Error en LDAP",e);
      throw new DAOException(this,
          "EAL_ODNB_NFE: HA OCURRIDO UN PROBLEMA EN LOS PARAMETROS DEL SERVIDOR:"
          + e.getMessage());
    }
    return m;
  }
}