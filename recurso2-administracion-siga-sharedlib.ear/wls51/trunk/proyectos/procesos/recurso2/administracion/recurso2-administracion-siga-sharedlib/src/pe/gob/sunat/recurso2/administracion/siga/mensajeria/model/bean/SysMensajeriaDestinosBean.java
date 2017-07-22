package pe.gob.sunat.recurso2.administracion.siga.mensajeria.model.bean;

import java.io.Serializable;

public class SysMensajeriaDestinosBean  implements Serializable{
	private static final long serialVersionUID = 1L;

	
	
	private Long nroMensaje;
	private String  destinoNombre;
	private String  destinoEmail;
	private String	tipoDestino;
	
	
	public Long getNroMensaje() {
		return nroMensaje;
	}
	public void setNroMensaje(Long nroMensaje) {
		this.nroMensaje = nroMensaje;
	}
	public String getDestinoNombre() {
		return destinoNombre;
	}
	public void setDestinoNombre(String destinoNombre) {
		this.destinoNombre = destinoNombre;
	}
	public String getDestinoEmail() {
		return destinoEmail;
	}
	public void setDestinoEmail(String destinoEmail) {
		this.destinoEmail = destinoEmail;
	}
	public String getTipoDestino() {
		return tipoDestino;
	}
	public void setTipoDestino(String tipoDestino) {
		this.tipoDestino = tipoDestino;
	}
	
	
		
}
