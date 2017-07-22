package pe.gob.sunat.recurso2.administracion.siga.registro.model.bean;

import java.io.Serializable;
import java.util.Date;

public class PersonaBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private String codigo_persona; 	//PERSONA_ID
	private String ruc_persona;		//RUC
	private String tipo_documento;	//DOCUMENTO_TIPO
	private String numero_documento;//DOCUMENTO_NUMERO
	private String nombre_razon;	//NOMBRE_RAZON_SOCIAL
	private String apellido_pat;	//APELLIDO_PATERNO
	private String apellido_mat;	//APELLIDO_MATERNO
	private String nombres;			//NOMBRES
	private String tipo_persona;	//PERSONA_TIPO
	private String direccion_per;	//DIRECCION
	private String cod_depa;  //DEPARTAMENTO_ID		
	private String cod_prov;//PROVINCIA_ID				
	private String cod_dis;//DISTRITO_ID	
	private String cod_pais;//PAIS_ID				
	private String cod_ubigeo;
	
	private String referencia;//REFERENCIA
	private Date fecha_nacimiento;//FECHA_NACIMIENTO
	private String user_crea;//USER_CREA
	private Date fech_crea;//FECH_CREA
	private String user_modi;//USER_MODI
	private Date fech_modi;//FECH_MODI

	
	
	public PersonaBean(){
		super();
	}



	public String getCodigo_persona() {
		return codigo_persona;
	}



	public void setCodigo_persona(String codigo_persona) {
		this.codigo_persona = codigo_persona;
	}



	public String getRuc_persona() {
		return ruc_persona;
	}



	public void setRuc_persona(String ruc_persona) {
		this.ruc_persona = ruc_persona;
	}



	public String getTipo_documento() {
		return tipo_documento;
	}



	public void setTipo_documento(String tipo_documento) {
		this.tipo_documento = tipo_documento;
	}



	public String getNumero_documento() {
		return numero_documento;
	}



	public void setNumero_documento(String numero_documento) {
		this.numero_documento = numero_documento;
	}



	public String getNombre_razon() {
		return nombre_razon;
	}



	public void setNombre_razon(String nombre_razon) {
		this.nombre_razon = nombre_razon;
	}



	public String getApellido_pat() {
		return apellido_pat;
	}



	public void setApellido_pat(String apellido_pat) {
		this.apellido_pat = apellido_pat;
	}



	public String getApellido_mat() {
		return apellido_mat;
	}



	public void setApellido_mat(String apellido_mat) {
		this.apellido_mat = apellido_mat;
	}



	public String getNombres() {
		return nombres;
	}



	public void setNombres(String nombres) {
		this.nombres = nombres;
	}



	public String getTipo_persona() {
		return tipo_persona;
	}



	public void setTipo_persona(String tipo_persona) {
		this.tipo_persona = tipo_persona;
	}



	public String getDireccion_per() {
		return direccion_per;
	}



	public void setDireccion_per(String direccion_per) {
		this.direccion_per = direccion_per;
	}



	public String getCod_depa() {
		return cod_depa;
	}



	public void setCod_depa(String cod_depa) {
		this.cod_depa = cod_depa;
	}



	public String getCod_prov() {
		return cod_prov;
	}



	public void setCod_prov(String cod_prov) {
		this.cod_prov = cod_prov;
	}



	public String getCod_dis() {
		return cod_dis;
	}



	public void setCod_dis(String cod_dis) {
		this.cod_dis = cod_dis;
	}



	public String getCod_pais() {
		return cod_pais;
	}



	public void setCod_pais(String cod_pais) {
		this.cod_pais = cod_pais;
	}



	public String getCod_ubigeo() {
		return cod_ubigeo;
	}



	public void setCod_ubigeo(String cod_ubigeo) {
		this.cod_ubigeo = cod_ubigeo;
	}



	public String getReferencia() {
		return referencia;
	}



	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}



	public Date getFecha_nacimiento() {
		return fecha_nacimiento;
	}



	public void setFecha_nacimiento(Date fecha_nacimiento) {
		this.fecha_nacimiento = fecha_nacimiento;
	}



	public String getUser_crea() {
		return user_crea;
	}



	public void setUser_crea(String user_crea) {
		this.user_crea = user_crea;
	}



	public Date getFech_crea() {
		return fech_crea;
	}



	public void setFech_crea(Date fech_crea) {
		this.fech_crea = fech_crea;
	}



	public String getUser_modi() {
		return user_modi;
	}



	public void setUser_modi(String user_modi) {
		this.user_modi = user_modi;
	}



	public Date getFech_modi() {
		return fech_modi;
	}



	public void setFech_modi(Date fech_modi) {
		this.fech_modi = fech_modi;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	
	
	
	
	
}
