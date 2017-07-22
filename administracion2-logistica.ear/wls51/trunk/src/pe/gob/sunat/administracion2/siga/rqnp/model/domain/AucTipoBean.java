package pe.gob.sunat.administracion2.siga.rqnp.model.domain;

public class AucTipoBean implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String cod_dep; 		//CODI_DEPE_TDE
	private String tipo_auc;		//TIPO_AUC
	
	public AucTipoBean() {
		super();
	}
	public String getCod_dep() {
		return cod_dep;
	}
	public void setCod_dep(String cod_dep) {
		this.cod_dep = cod_dep;
	}
	public String getTipo_auc() {
		return tipo_auc;
	}
	public void setTipo_auc(String tipo_auc) {
		this.tipo_auc = tipo_auc;
	}
	
	
}
