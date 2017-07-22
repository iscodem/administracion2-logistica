package pe.gob.sunat.recurso2.administracion.siga.registro.model.bean;

public class DependenciaBean implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	
	private String cod_dep;		//CODI_DEPE_TDE
	private String cod_sede;	//CODI_SEDE_SED
	private String nom_largo;	//DESC_DEPE_TDE
	private String nom_corto;	//ABREVIATURA
	private String uuoo;		//UNIDAD_ORGANIZACIONAL
	private String intendencia; //INTENDENCIA
	//private String uuoo_super;	//COD_UUOO_SUPERIOR
	
	private String cod_plaza;	//COD_PLAZA
	private String codEmpleado;	//Código de empleado responsables de la intendencia.
	
	//PAS201780000300007
	private String codOecDefecto; 	//COD_OEC_DEFECTO
	private String codUuooAprueba;	//COD_UUOO_APRUEBA
	private String flagInfoInf;		//FLAG_INFO_INF
	
	public String getCod_plaza() {
		return cod_plaza;
	}
	public void setCod_plaza(String cod_plaza) {
		this.cod_plaza = cod_plaza;
	}
	public DependenciaBean(){
		super();
	}
	public String getCod_dep() {
		return cod_dep;
	}
	public void setCod_dep(String cod_dep) {
		this.cod_dep = cod_dep;
	}
	public String getCod_sede() {
		return cod_sede;
	}
	public void setCod_sede(String cod_sede) {
		this.cod_sede = cod_sede;
	}
	public String getNom_largo() {
		return nom_largo;
	}
	public void setNom_largo(String nom_largo) {
		this.nom_largo = nom_largo;
	}
	public String getNom_corto() {
		return nom_corto;
	}
	public void setNom_corto(String nom_corto) {
		this.nom_corto = nom_corto;
	}
	public String getUuoo() {
		return uuoo;
	}
	public void setUuoo(String uuoo) {
		this.uuoo = uuoo;
	}
	public void setIntendencia(String intendencia) {
		this.intendencia = intendencia;
	}
	public String getIntendencia() {
		return intendencia;
	}
	public String getCodEmpleado() {
		return codEmpleado;
	}
	public void setCodEmpleado(String codEmpleado) {
		this.codEmpleado = codEmpleado;
	}
	public String getCodOecDefecto() {
		return codOecDefecto;
	}
	public void setCodOecDefecto(String codOecDefecto) {
		this.codOecDefecto = codOecDefecto;
	}
	public String getCodUuooAprueba() {
		return codUuooAprueba;
	}
	public void setCodUuooAprueba(String codUuooAprueba) {
		this.codUuooAprueba = codUuooAprueba;
	}
	public String getFlagInfoInf() {
		return flagInfoInf;
	}
	public void setFlagInfoInf(String flagInfoInf) {
		this.flagInfoInf = flagInfoInf;
	}

}
