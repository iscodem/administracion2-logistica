package pe.gob.sunat.recurso2.administracion.siga.ubigeo.model.bean;

import java.io.Serializable;

public class TProvinciasBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String codiProvTpr; //Codigo Provincia
	private String nombProvTpr; //Nobre Provincia
	private String codiDepaDpt; //Codigo Departamento

	public String getCodiProvTpr() {
		return codiProvTpr;
	}

	public void setCodiProvTpr(String codiProvTpr) {
		this.codiProvTpr = codiProvTpr;
	}

	public String getNombProvTpr() {
		return nombProvTpr;
	}

	public void setNombProvTpr(String nombProvTpr) {
		this.nombProvTpr = nombProvTpr;
	}

	public String getCodiDepaDpt() {
		return codiDepaDpt;
	}

	public void setCodiDepaDpt(String codiDepaDpt) {
		this.codiDepaDpt = codiDepaDpt;
	}

}
