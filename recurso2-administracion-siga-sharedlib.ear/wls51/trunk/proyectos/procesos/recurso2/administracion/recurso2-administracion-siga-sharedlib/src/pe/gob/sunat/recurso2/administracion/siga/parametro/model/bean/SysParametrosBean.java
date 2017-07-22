package pe.gob.sunat.recurso2.administracion.siga.parametro.model.bean;

import java.io.Serializable;

public class SysParametrosBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String desc_var; 		//DESC_VAR_PAR
	private Long val_num_par;		//VALOR_NUME_PAR
	private String val_char_par;	//VALOR_CHAR_PAR
	private String peri_anno; 		//PERI_ANNO_PAR
	
	public SysParametrosBean(){
		super();
	}
	public String getDesc_var() {
		return desc_var;
	}
	public Long getVal_num_par() {
		return val_num_par;
	}
	public void setVal_num_par(Long val_num_par) {
		this.val_num_par = val_num_par;
	}
	public void setDesc_var(String desc_var) {
		this.desc_var = desc_var;
	}
	public String getVal_char_par() {
		return val_char_par;
	}
	public void setVal_char_par(String val_char_par) {
		this.val_char_par = val_char_par;
	}
	public String getPeri_anno() {
		return peri_anno;
	}
	public void setPeri_anno(String peri_anno) {
		this.peri_anno = peri_anno;
	}
	
}
