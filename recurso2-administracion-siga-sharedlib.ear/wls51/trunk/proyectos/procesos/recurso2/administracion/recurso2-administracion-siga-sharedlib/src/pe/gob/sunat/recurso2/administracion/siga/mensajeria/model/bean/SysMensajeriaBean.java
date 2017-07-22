package pe.gob.sunat.recurso2.administracion.siga.mensajeria.model.bean;


import java.io.Serializable;

public class SysMensajeriaBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long nroMensaje;
	private String codigoMensaje;
	private String remitenteNombre;
	private String remitenteFirma;
	private String remitenteEmail;
	private String asunto;
	private String mensaje;
	
	
	
	
	
	
	
	public String getCodigoMensaje() {
		return codigoMensaje;
	}
	public void setCodigoMensaje(String codigoMensaje) {
		this.codigoMensaje = codigoMensaje;
	}
	public String getRemitenteNombre() {
		return remitenteNombre;
	}
	public void setRemitenteNombre(String remitenteNombre) {
		this.remitenteNombre = remitenteNombre;
	}
	public String getRemitenteFirma() {
		return remitenteFirma;
	}
	public void setRemitenteFirma(String remitenteFirma) {
		this.remitenteFirma = remitenteFirma;
	}
	public String getRemitenteEmail() {
		return remitenteEmail;
	}
	public void setRemitenteEmail(String remitenteEmail) {
		this.remitenteEmail = remitenteEmail;
	}
	public String getAsunto() {
		return asunto;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public Long getNroMensaje() {
		return nroMensaje;
	}
	public void setNroMensaje(Long nroMensaje) {
		this.nroMensaje = nroMensaje;
	}
	
	
	
	
	
	
	

}
