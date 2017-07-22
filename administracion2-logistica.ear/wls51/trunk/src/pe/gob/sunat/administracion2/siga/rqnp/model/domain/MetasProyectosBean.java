package pe.gob.sunat.administracion2.siga.rqnp.model.domain;


public class MetasProyectosBean implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	private String nomActividad;//	NOM_ACTIVIDAD
	private String nomProducto;//	NOM_PRODUCTO
	private String nomMeta;//	NOM_META
	private String anioEjec;//	ANIO_EJEC_EJE
	
	private String secuFunc;//	SECU_FUNC_SFU
	private String codiProy;//	CODI_PROY_PRY
	private String codiProd;//	CODI_PROD_PRD
	private String codiMeta;//	CODI_META_MET
	private String codiDep;//	CODI_DEPE_TDE
	private String metaSiaf;//	CADE_SIAF_CAD
	
	public MetasProyectosBean() {
		super();
	}

	public String getMetaSiaf() {
		return metaSiaf;
	}

	public void setMetaSiaf(String metaSiaf) {
		this.metaSiaf = metaSiaf;
	}

	public String getNomActividad() {
		return nomActividad;
	}

	public void setNomActividad(String nomActividad) {
		this.nomActividad = nomActividad;
	}

	public String getNomProducto() {
		return nomProducto;
	}

	public void setNomProducto(String nomProducto) {
		this.nomProducto = nomProducto;
	}

	public String getNomMeta() {
		return nomMeta;
	}

	public void setNomMeta(String nomMeta) {
		this.nomMeta = nomMeta;
	}

	public String getAnioEjec() {
		return anioEjec;
	}

	public void setAnioEjec(String anioEjec) {
		this.anioEjec = anioEjec;
	}

	public String getSecuFunc() {
		return secuFunc;
	}

	public void setSecuFunc(String secuFunc) {
		this.secuFunc = secuFunc;
	}

	public String getCodiProy() {
		return codiProy;
	}

	public void setCodiProy(String codiProy) {
		this.codiProy = codiProy;
	}

	public String getCodiProd() {
		return codiProd;
	}

	public void setCodiProd(String codiProd) {
		this.codiProd = codiProd;
	}

	public String getCodiMeta() {
		return codiMeta;
	}

	public void setCodiMeta(String codiMeta) {
		this.codiMeta = codiMeta;
	}

	public String getCodiDep() {
		return codiDep;
	}

	public void setCodiDep(String codiDep) {
		this.codiDep = codiDep;
	}
}
