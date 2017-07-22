package pe.gob.sunat.recurso2.administracion.siga.parametro.model.bean;

import java.io.Serializable;

public class SysEstadosBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String nom_tabla ; //NOMB_TABL_EST
	private String desc_reg ; //DESC_REGI_EST
	private String val_estado ; //VALO_ESTA_EST
	private String desc_estado ; //DESC_ESTA_EST
	
	
	public SysEstadosBean(){
		super();
	}
	
	
	public String getNom_tabla() {
		return nom_tabla;
	}
	public void setNom_tabla(String nom_tabla) {
		this.nom_tabla = nom_tabla;
	}
	public String getDesc_reg() {
		return desc_reg;
	}
	public void setDesc_reg(String desc_reg) {
		this.desc_reg = desc_reg;
	}
	public String getVal_estado() {
		return val_estado;
	}
	public void setVal_estado(String val_estado) {
		this.val_estado = val_estado;
	}
	public String getDesc_estado() {
		return desc_estado;
	}
	public void setDesc_estado(String desc_estado) {
		this.desc_estado = desc_estado;
	}
	
	
	


}
