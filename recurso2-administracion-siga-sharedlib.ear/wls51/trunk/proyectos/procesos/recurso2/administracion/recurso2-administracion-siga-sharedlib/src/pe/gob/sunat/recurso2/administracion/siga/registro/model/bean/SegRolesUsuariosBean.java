package pe.gob.sunat.recurso2.administracion.siga.registro.model.bean;

import java.io.Serializable;
import java.util.Date;

public class SegRolesUsuariosBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private String cod_rol ;//CODI_ROLE_ROL
	private String cod_user;// CODI_USUA_USU
	private Date fec_asig ; //FECH_ASIG_RUS
	private String cod_estado;// ESTA_ACTU_RUS 
	
	private String nro_reg;//COD_PERSONAL
	

	public String getNro_reg() {
		return nro_reg;
	}


	public void setNro_reg(String nro_reg) {
		this.nro_reg = nro_reg;
	}


	public SegRolesUsuariosBean(){
		super();
	}


	public String getCod_rol() {
		return cod_rol;
	}


	public void setCod_rol(String cod_rol) {
		this.cod_rol = cod_rol;
	}


	public String getCod_user() {
		return cod_user;
	}


	public void setCod_user(String cod_user) {
		this.cod_user = cod_user;
	}


	public Date getFec_asig() {
		return fec_asig;
	}


	public void setFec_asig(Date fec_asig) {
		this.fec_asig = fec_asig;
	}


	public String getCod_estado() {
		return cod_estado;
	}


	public void setCod_estado(String cod_estado) {
		this.cod_estado = cod_estado;
	}
	
	
	
}
