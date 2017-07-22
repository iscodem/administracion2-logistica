package pe.gob.sunat.recurso2.administracion.siga.parametro.model.bean;

import java.io.Serializable;

public class T01ParametroBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	private String cod_parametro; //COD_PARAMETRO
	private String cod_modulo; //COD_MODULO
	private String cod_tipo; //COD_TIPO
	private String cod_argumento; //COD_ARGUMENTO
	private String nom_corto; //DESC_CORTA
	private String nom_largo; //DESC_LARGA
	private String desc_abrv; //DESC_ABREVIATURA
	private String cod_estado; // COD_ESTADO
	
	public T01ParametroBean(){
		super();
	}
	
	public String getCod_parametro() {
		return cod_parametro;
	}
	public void setCod_parametro(String cod_parametro) {
		this.cod_parametro = cod_parametro;
	}
	public String getCod_modulo() {
		return cod_modulo;
	}
	public void setCod_modulo(String cod_modulo) {
		this.cod_modulo = cod_modulo;
	}
	public String getCod_tipo() {
		return cod_tipo;
	}
	public void setCod_tipo(String cod_tipo) {
		this.cod_tipo = cod_tipo;
	}
	public String getCod_argumento() {
		return cod_argumento;
	}
	public void setCod_argumento(String cod_argumento) {
		this.cod_argumento = cod_argumento;
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

	public String getDesc_abrv() {
		return desc_abrv;
	}

	public void setDesc_abrv(String desc_abrv) {
		this.desc_abrv = desc_abrv;
	}

	public String getCod_estado() {
		return cod_estado;
	}

	public void setCod_estado(String cod_estado) {
		this.cod_estado = cod_estado;
	}
	
}
