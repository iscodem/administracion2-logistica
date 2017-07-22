package pe.gob.sunat.recurso2.administracion.siga.ubigeo.model.bean;

import java.io.Serializable;

public class TDistritosBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String codiDistTdi; // Codigo Distrito
	private String nombDistTdi; // Nombre Distrito
	private String codiDepaDpt; // Codigo Departamento
	private String codiProvTpr; // Codigo Provincia

	public String getCodiDistTdi() {
		return codiDistTdi;
	}

	public void setCodiDistTdi(String codiDistTdi) {
		this.codiDistTdi = codiDistTdi;
	}

	public String getNombDistTdi() {
		return nombDistTdi;
	}

	public void setNombDistTdi(String nombDistTdi) {
		this.nombDistTdi = nombDistTdi;
	}

	public String getCodiDepaDpt() {
		return codiDepaDpt;
	}

	public void setCodiDepaDpt(String codiDepaDpt) {
		this.codiDepaDpt = codiDepaDpt;
	}

	public String getCodiProvTpr() {
		return codiProvTpr;
	}

	public void setCodiProvTpr(String codiProvTpr) {
		this.codiProvTpr = codiProvTpr;
	}

}
