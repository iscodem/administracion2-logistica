package pe.gob.sunat.administracion2.siga.rqnp.util;

import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.MaestroPersonalBean;
import pe.gob.sunat.tecnologia.menu.bean.UsuarioBean;

public class MaestroPersonalUtil {
	public static MaestroPersonalBean setMaestroPersonal(UsuarioBean usuarioBean){
		MaestroPersonalBean maestroPersonal = new MaestroPersonalBean();

		maestroPersonal.setApellido_materno(usuarioBean.getApeMaterno());
		maestroPersonal.setApellido_paterno(usuarioBean.getApePaterno());
		maestroPersonal.setNombre(usuarioBean.getNombres());
		maestroPersonal.setNombre_completo(usuarioBean.getNombreCompleto());
		maestroPersonal.setNumero_registro(usuarioBean.getNroRegistro());
		maestroPersonal.setCodigoDependencia(usuarioBean.getCodDepend());
		maestroPersonal.setUuoo(usuarioBean.getCodUO());
		
		return maestroPersonal;
	}

}
