package pe.gob.sunat.recurso2.administracion.siga.firma.model.bean;

public class Dependencia implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	
	private String cod_dep;		//CODI_DEPE_TDE
	private String cod_sede;	//CODI_SEDE_SED
	private String nom_largo;	//DESC_DEPE_TDE
	private String nom_corto;	//ABREVIATURA
	private String uuoo;		//UNIDAD_ORGANIZACIONAL
	private String cod_per;		//CODI_EMPL_PER
	//private String uuoo_super;	//COD_UUOO_SUPERIOR
	private String 	flagInfoInf;	//D.FLAG_INFO_INF,
	
	public Dependencia(){
		super();
	}

	public String getCod_dep() {
		return cod_dep;
	}

	public void setCod_dep(String cod_dep) {
		this.cod_dep = cod_dep;
	}

	public String getCod_sede() {
		return cod_sede;
	}

	public void setCod_sede(String cod_sede) {
		this.cod_sede = cod_sede;
	}

	public String getNom_largo() {
		return nom_largo;
	}

	public void setNom_largo(String nom_largo) {
		this.nom_largo = nom_largo;
	}

	public String getNom_corto() {
		return nom_corto;
	}

	public void setNom_corto(String nom_corto) {
		this.nom_corto = nom_corto;
	}

	public String getUuoo() {
		return uuoo;
	}

	public void setUuoo(String uuoo) {
		this.uuoo = uuoo;
	}

	public String getCod_per() {
		return cod_per;
	}

	public void setCod_per(String cod_per) {
		this.cod_per = cod_per;
	}

	public String getFlagInfoInf() {
		return flagInfoInf;
	}

	public void setFlagInfoInf(String flagInfoInf) {
		this.flagInfoInf = flagInfoInf;
	}
	
}
