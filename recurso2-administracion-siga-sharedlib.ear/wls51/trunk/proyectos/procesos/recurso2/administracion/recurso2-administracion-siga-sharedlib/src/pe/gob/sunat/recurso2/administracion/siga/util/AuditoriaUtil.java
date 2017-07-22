package pe.gob.sunat.recurso2.administracion.siga.util;

import pe.gob.sunat.tecnologia.menu.bean.UsuarioBean;

public class AuditoriaUtil{
	
  private static ThreadLocal<UsuarioBean> tLocal = new ThreadLocal<UsuarioBean>();

  public static void set(UsuarioBean envioBean) {
	  tLocal.set(envioBean);
  }
  
  public static UsuarioBean get() {
	  return (UsuarioBean)tLocal.get();
  }
  
}
