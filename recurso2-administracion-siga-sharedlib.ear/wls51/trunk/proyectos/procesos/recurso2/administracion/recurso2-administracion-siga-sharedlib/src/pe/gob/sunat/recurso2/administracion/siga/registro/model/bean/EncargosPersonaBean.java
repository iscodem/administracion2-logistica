package pe.gob.sunat.recurso2.administracion.siga.registro.model.bean;

import java.util.Date;

public class EncargosPersonaBean implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	
	
	private String 	cod_per	;//CODI_EMPL_PER
	private String 	cod_enc	;//CODI_ENCA_ENC
	private Date 	fec_ini	;//FECH_INIC_ENC
	private Date 	fec_fin	;//FECH_FINA_ENC
	private String 	motivo	;//MOTIVO
	private String 	estado	;//FLAG_ENCA_ENC
	private String 	cod_dep	;//COD_DEPENDENCIA
	
	
	public EncargosPersonaBean(){
		super();
	}

	
	

	public String getCod_per() {
		return cod_per;
	}


	public void setCod_per(String cod_per) {
		this.cod_per = cod_per;
	}


	public String getCod_enc() {
		return cod_enc;
	}


	public void setCod_enc(String cod_enc) {
		this.cod_enc = cod_enc;
	}


	public Date getFec_ini() {
		return fec_ini;
	}


	public void setFec_ini(Date fec_ini) {
		this.fec_ini = fec_ini;
	}


	public Date getFec_fin() {
		return fec_fin;
	}


	public void setFec_fin(Date fec_fin) {
		this.fec_fin = fec_fin;
	}


	public String getMotivo() {
		return motivo;
	}


	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}


	public String getCod_dep() {
		return cod_dep;
	}


	public void setCod_dep(String cod_dep) {
		this.cod_dep = cod_dep;
	}

	
	
	
}
