
package pe.gob.sunat.recurso2.administracion.siga.ubigeo.model.bean;

import java.io.Serializable;

public class TDepartamentosBean implements Serializable {

	private static final long serialVersionUID = 7302805519874598656L;
	private String codiDepaDpt; //Codigo Departamento
	private String nombDptoDpt; //Nombre Departamento

	public String getCodiDepaDpt() {
		return codiDepaDpt;
	}

	public void setCodiDepaDpt(String codiDepaDpt) {
		this.codiDepaDpt = codiDepaDpt;
	}

	public String getNombDptoDpt() {
		return nombDptoDpt;
	}

	public void setNombDptoDpt(String nombDptoDpt) {
		this.nombDptoDpt = nombDptoDpt;
	}

}
