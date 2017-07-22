package pe.gob.sunat.recurso2.administracion.siga.firma.model.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;

//T7076DELEGACFIRMA (Nombre de tabla)
public class JefeDependenciaBean implements Serializable{
	private static final long serialVersionUID = 1L;

	private String 	codigoEmpleado;		//M.CODI_EMPL_PER
	private String 	apellidoPaterno;	//M.APE_PAT_PER
	private String 	apellidoMaterno;	//M.APE_MAT_PER
	private String 	nombre;				//M.NOM_EMP_PER
	private String 	nombreCompleto;		//M.NOMB_CORT_PER
	private Date 	fechaIngreso;		//M.FEC_ING_PER
	private String 	sexo;				//M.SEX_EMP_PER
	private String 	codigoDependencia;	//M.CODI_DEPE_TDE
	private String 	dni;				//M.LIBR_ELEC_PER
	private String 	numeroRegistro;		//M.NUMERO_REGISTRO_ALTERNO
	
	//Consulta
	private String 	dependencia;		//D.DESC_DEPE_TDE
	private String 	abrvDependencia;	//D.ABREVIATURA,
	private String 	uuoo;				//D.UNIDAD_ORGANIZACIONAL,
	private String 	flagInfoInf;		//D.FLAG_INFO_INF,
	
	//ESTADO
	private String codigoEstado;
	private String estado;
	
	//CORREO REQUERIDO EN NUEVOS REQUERIMEINTOS
	private String correo;
	
	private List<Dependencia> listaDependenciaJefe;

	public String getCodigoEmpleado() {
		return codigoEmpleado;
	}

	public void setCodigoEmpleado(String codigoEmpleado) {
		this.codigoEmpleado = codigoEmpleado;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getCodigoDependencia() {
		return codigoDependencia;
	}

	public void setCodigoDependencia(String codigoDependencia) {
		this.codigoDependencia = codigoDependencia;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNumeroRegistro() {
		return numeroRegistro;
	}

	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}

	public String getDependencia() {
		return dependencia;
	}

	public void setDependencia(String dependencia) {
		this.dependencia = dependencia;
	}

	public String getAbrvDependencia() {
		return abrvDependencia;
	}

	public void setAbrvDependencia(String abrvDependencia) {
		this.abrvDependencia = abrvDependencia;
	}

	public String getUuoo() {
		return uuoo;
	}

	public void setUuoo(String uuoo) {
		this.uuoo = uuoo;
	}

	public String getCodigoEstado() {
		return codigoEstado;
	}

	public void setCodigoEstado(String codigoEstado) {
		this.codigoEstado = codigoEstado;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public List<Dependencia> getListaDependenciaJefe() {
		return listaDependenciaJefe;
	}

	public void setListaDependenciaJefe(List<Dependencia> listaDependenciaJefe) {
		this.listaDependenciaJefe = listaDependenciaJefe;
	}

	public String getFlagInfoInf() {
		return flagInfoInf;
	}

	public void setFlagInfoInf(String flagInfoInf) {
		this.flagInfoInf = flagInfoInf;
	}

}