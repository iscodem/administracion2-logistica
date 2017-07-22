package pe.gob.sunat.administracion2.siga.rqnp.model.domain;

public class UnidadMedidaBean implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String cod_unidad;
	private String nom_corto;
	private String nom_largo;
	private String equi_unid;
	
	
	
	public UnidadMedidaBean(){}



	public String getCod_unidad() {
		return cod_unidad;
	}



	public void setCod_unidad(String cod_unidad) {
		this.cod_unidad = cod_unidad;
	}



	public String getNom_corto() {
		return nom_corto;
	}



	public void setNom_corto(String nom_corto) {
		this.nom_corto = nom_corto;
	}



	public String getNom_largo() {
		return nom_largo;
	}



	public void setNom_largo(String nom_largo) {
		this.nom_largo = nom_largo;
	}



	public String getEqui_unid() {
		return equi_unid;
	}



	public void setEqui_unid(String equi_unid) {
		this.equi_unid = equi_unid;
	}
	
	
	
	

}
