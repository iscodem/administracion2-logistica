package pe.gob.sunat.recurso2.administracion.siga.registro.model.bean;

import java.io.Serializable;

public class TcargosBean implements Serializable { 
	
	private static final long serialVersionUID = 1;

	//Cargos
	private String codigoCargo;			//CODI_CARG_TCA
	private String cargo;				//DESC_CARG_TCA 
	//Personal
	private String codigoEmpleado;		//CODI_EMPL_PER
	private String numeroRegistro;		//NUMERO_REGISTRO_ALTERNO
	private String nombreCompleto;		//NOMB_CORT_PER
	//Niveles 
	private String codigoNivel;			//CODI_NIVE_NVL
	private String nivel;				//DESC_NIVE_NVL
	
	public String getCodigoCargo() {
		return codigoCargo;
	}
	public void setCodigoCargo(String codigoCargo) {
		this.codigoCargo = codigoCargo;
	}
	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	public String getCodigoEmpleado() {
		return codigoEmpleado;
	}
	public void setCodigoEmpleado(String codigoEmpleado) {
		this.codigoEmpleado = codigoEmpleado;
	}
	public String getNumeroRegistro() {
		return numeroRegistro;
	}
	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}
	public String getNombreCompleto() {
		return nombreCompleto;
	}
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
	public String getCodigoNivel() {
		return codigoNivel;
	}
	public void setCodigoNivel(String codigoNivel) {
		this.codigoNivel = codigoNivel;
	}
	public String getNivel() {
		return nivel;
	}
	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	
	
}
