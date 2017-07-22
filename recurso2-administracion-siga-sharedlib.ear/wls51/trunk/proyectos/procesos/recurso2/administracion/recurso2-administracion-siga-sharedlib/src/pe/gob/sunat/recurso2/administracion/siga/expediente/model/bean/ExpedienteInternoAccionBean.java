package pe.gob.sunat.recurso2.administracion.siga.expediente.model.bean;

import java.util.Date;



public class ExpedienteInternoAccionBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private String 		num_exp;		//NUME_EXPE_EXP
	private String 		sec_accion;		//SECU_ACCI_ACC
	private String 		cod_accion;		//CODI_ACCI_ACC
	private String 		des_accion;		//DESC_ACCI_ACC
	private String 		cod_emp	;		//CODI_EMPL_PER
	private String 		nom_emp ; 		//NOMB_CORT_PER
	private Date 	fecha_ini ;		//FECH_INIC_ACC
	private Date 	fecha_ter ;		//FECH_TERM_ACC
	private String 		observacion ;	//OBSE_TERM_ACC
	private String 		estado ;		//ESTA_FINA_ACC
	private String      nom_estado;		//DESC_ESTA_EST
	private Integer numItem;
	private String 	fechaInicioFormat ;	
	private String 	fechaFinFormat ;	
	
	public String getNom_estado() {
		return nom_estado;
	}


	public void setNom_estado(String nom_estado) {
		this.nom_estado = nom_estado;
	}


	public ExpedienteInternoAccionBean(){}


	public String getNum_exp() {
		return num_exp;
	}


	public void setNum_exp(String num_exp) {
		this.num_exp = num_exp;
	}


	public String getSec_accion() {
		return sec_accion;
	}


	public void setSec_accion(String sec_accion) {
		this.sec_accion = sec_accion;
	}


	public String getCod_accion() {
		return cod_accion;
	}


	public void setCod_accion(String cod_accion) {
		this.cod_accion = cod_accion;
	}


	public String getDes_accion() {
		return des_accion;
	}


	public void setDes_accion(String des_accion) {
		this.des_accion = des_accion;
	}


	public String getCod_emp() {
		return cod_emp;
	}


	public void setCod_emp(String cod_emp) {
		this.cod_emp = cod_emp;
	}


	public String getNom_emp() {
		return nom_emp;
	}


	public void setNom_emp(String nom_emp) {
		this.nom_emp = nom_emp;
	}


	public Date getFecha_ini() {
		return fecha_ini;
	}


	public void setFecha_ini(Date fecha_ini) {
		this.fecha_ini = fecha_ini;
	}


	public Date getFecha_ter() {
		return fecha_ter;
	}


	public void setFecha_ter(Date fecha_ter) {
		this.fecha_ter = fecha_ter;
	}


	public String getObservacion() {
		return observacion;
	}


	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}


	public Integer getNumItem() {
		return numItem;
	}


	public void setNumItem(Integer numItem) {
		this.numItem = numItem;
	}


	public String getFechaInicioFormat() {
		return fechaInicioFormat;
	}


	public void setFechaInicioFormat(String fechaInicioFormat) {
		this.fechaInicioFormat = fechaInicioFormat;
	}


	public String getFechaFinFormat() {
		return fechaFinFormat;
	}


	public void setFechaFinFormat(String fechaFinFormat) {
		this.fechaFinFormat = fechaFinFormat;
	}
	
	
	
	
}
