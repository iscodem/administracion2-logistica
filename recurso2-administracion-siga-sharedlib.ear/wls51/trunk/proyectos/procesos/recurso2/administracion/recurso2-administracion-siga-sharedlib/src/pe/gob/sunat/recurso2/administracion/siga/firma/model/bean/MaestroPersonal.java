package pe.gob.sunat.recurso2.administracion.siga.firma.model.bean;

import java.util.Date;

public class MaestroPersonal implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String 	codigoEmpleado;		//M.CODI_EMPL_PER
	private String 	apellido_paterno;	//M.APE_PAT_PER
	private String 	apellido_materno;	//M.APE_MAT_PER
	private String 	nombre;				//M.NOM_EMP_PER
	private String 	nombre_completo;	//M.NOMB_CORT_PER
	private Date 	fechaIngreso;		//M.FEC_ING_PER
	private String 	estadoCivil;		//M.EST_CIV_PER
	private String 	sexo;				//M.SEX_EMP_PER
	private Date 	fechaNac;			//M.FEC_NAC_PER
	private String 	codigoDependencia;	//M.CODI_DEPE_TDE
	private String 	codigoAfp;			//M.CODI_AFP_PER
	private String 	dni;				//M.LIBR_ELEC_PER
	private String 	codigoSede; 		//M.SEDE_ACTU_PER
	private String 	numero_registro;	//M.NUMERO_REGISTRO_ALTERNO
	
	//Consulta
	private String 	dependencia;		//D.DESC_DEPE_TDE
	private String 	abrvDependencia;	//D.ABREVIATURA,
	private String 	uuoo;				//D.UNIDAD_ORGANIZACIONAL,
	
	private String cod_plaza ;			//COD_PLAZA
	
	//ESTADO
	private String codigoEstado;
	private String estado;
	
	//CORREO REQUERIDO EN NUEVOS REQUERIMEINTOS
	private String correo;
	
	//CATEGORIA DE GASTO
	private String codigoCategoriaGasto;
	private String descripcionCategoriaGasto;

	private String 	codigoEmpleadoJefe;		//M.CODI_EMPL_PER
	private String 	numero_registro_jefe;	//M.NUMERO_REGISTRO_ALTERNO
	private String 	nombre_completo_jefe;	//M.NOMB_CORT_PER	
	
	private String cargo;
	private String descCortaCargo;
	private String descCargo;
	
	private String codiNiveNvl;
	
	public String getCod_plaza() {
		return cod_plaza;
	}
	
	
	public void setCod_plaza(String cod_plaza) {
		this.cod_plaza = cod_plaza;
	}
	
	
	public String getUuoo() {
		return uuoo;
	}
	public void setUuoo(String uuoo) {
		this.uuoo = uuoo;
	}

	private String 	sede;				//S.DESC_SEDE_SED
	
	
	
	public MaestroPersonal(){
		super();
	}


	public String getCodigoEmpleado() {
		return codigoEmpleado;
	}

	public void setCodigoEmpleado(String codigoEmpleado) {
		this.codigoEmpleado = codigoEmpleado;
	}


	public String getApellido_paterno() {
		return apellido_paterno;
	}



	public void setApellido_paterno(String apellido_paterno) {
		this.apellido_paterno = apellido_paterno;
	}


	public String getApellido_materno() {
		return apellido_materno;
	}

	public void setApellido_materno(String apellido_materno) {
		this.apellido_materno = apellido_materno;
	}

	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre_completo() {
		return nombre_completo;
	}

	public void setNombre_completo(String nombre_completo) {
		this.nombre_completo = nombre_completo;
	}

	public String getNumero_registro() {
		return numero_registro;
	}

	public void setNumero_registro(String numero_registro) {
		this.numero_registro = numero_registro;
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



	public Date getFechaNac() {
		return fechaNac;
	}


	public void setFechaNac(Date fechaNac) {
		this.fechaNac = fechaNac;
	}


	public String getCodigoDependencia() {
		return codigoDependencia;
	}


	public void setCodigoDependencia(String codigoDependencia) {
		this.codigoDependencia = codigoDependencia;
	}


	public String getCodigoAfp() {
		return codigoAfp;
	}


	public void setCodigoAfp(String codigoAfp) {
		this.codigoAfp = codigoAfp;
	}



	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}


	public String getCodigoSede() {
		return codigoSede;
	}


	public void setCodigoSede(String codigoSede) {
		this.codigoSede = codigoSede;
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

	public String getSede() {
		return sede;
	}


	public void setSede(String sede) {
		this.sede = sede;
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
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

	public String getCodigoCategoriaGasto() {
		return codigoCategoriaGasto;
	}

	public void setCodigoCategoriaGasto(String codigoCategoriaGasto) {
		this.codigoCategoriaGasto = codigoCategoriaGasto;
	}

	public String getDescripcionCategoriaGasto() {
		return descripcionCategoriaGasto;
	}

	public void setDescripcionCategoriaGasto(String descripcionCategoriaGasto) {
		this.descripcionCategoriaGasto = descripcionCategoriaGasto;
	}


	public String getCodigoEmpleadoJefe() {
		return codigoEmpleadoJefe;
	}


	public void setCodigoEmpleadoJefe(String codigoEmpleadoJefe) {
		this.codigoEmpleadoJefe = codigoEmpleadoJefe;
	}


	public String getNumero_registro_jefe() {
		return numero_registro_jefe;
	}


	public void setNumero_registro_jefe(String numeroRegistroJefe) {
		numero_registro_jefe = numeroRegistroJefe;
	}


	public String getNombre_completo_jefe() {
		return nombre_completo_jefe;
	}


	public void setNombre_completo_jefe(String nombreCompletoJefe) {
		nombre_completo_jefe = nombreCompletoJefe;
	}


	public void setCargo(String cargo) {
		this.cargo = cargo;
	}


	public String getCargo() {
		return cargo;
	}


	public void setDescCortaCargo(String descCortaCargo) {
		this.descCortaCargo = descCortaCargo;
	}


	public String getDescCortaCargo() {
		return descCortaCargo;
	}


	public void setDescCargo(String descCargo) {
		this.descCargo = descCargo;
	}


	public String getDescCargo() {
		return descCargo;
	}


	public void setCodiNiveNvl(String codiNiveNvl) {
		this.codiNiveNvl = codiNiveNvl;
	}


	public String getCodiNiveNvl() {
		return codiNiveNvl;
	}
	
}
