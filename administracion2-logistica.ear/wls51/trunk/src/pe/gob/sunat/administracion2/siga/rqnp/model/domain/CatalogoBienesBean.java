package pe.gob.sunat.administracion2.siga.rqnp.model.domain;

import java.math.BigDecimal;

public class CatalogoBienesBean implements java.io.Serializable{
	
	
	private static final long serialVersionUID = 1L;
	
	private String 		codigo_bien ;		//CODI_BSER_CAT
	private String		desc_bien;			//C.DESC_BSER_CAT
	private String 		tipo_bien;			//TIPO_BSER_CAT
	private String		codigo_unidad;		//UNID_MUSO_CAT
	private BigDecimal	precio_bien; 		//PREC_SPROM_CAT
	private BigDecimal	saldo; 				//SALDO
	private String		asignacion_especi;	//PLAN_CNTA_GAS
	private String		auct1;	//PLAN_CTA_TRAN
	private String		desc_unidad;		//U.DESC_MEDI_UND
	private String		codigo_gasto;		//F.PART_GAST_CAT
	private String 		desc_gasto;			//G.DESC_GAST_GAS
	private String 		auct2;				//MEDI_PRES_CAT
	private String 		auct3;				//PROC_ARTI_CAT
	private String 		auct_name      ;
	private String 		estadoDesconcentrado;
	private String 		ind_recurrente ;//IND_RECURRENTE
	private String     tipo_ruta ;//PLAN_CTA_TRAN
	private String cod_ambito;  		//COD_AMBITO
	private String cod_tipo_prog;		//COD_TIPO_PROG
	public String getCod_tipo_prog() {
		return cod_tipo_prog;
	}
	public void setCod_tipo_prog(String cod_tipo_prog) {
		this.cod_tipo_prog = cod_tipo_prog;
	}
	public String getCod_ambito() {
		return cod_ambito;
	}
	public void setCod_ambito(String cod_ambito) {
		this.cod_ambito = cod_ambito;
	}
	public String getInd_recurrente() {
		return ind_recurrente;
	}
	public void setInd_recurrente(String ind_recurrente) {
		this.ind_recurrente = ind_recurrente;
	}
	public CatalogoBienesBean(){}

	public String getCodigo_bien() {
		return codigo_bien;
	}
	public void setCodigo_bien(String codigo_bien) {
		this.codigo_bien = codigo_bien;
	}
	public String getDesc_bien() {
		return desc_bien;
	}
	public void setDesc_bien(String desc_bien) {
		this.desc_bien = desc_bien;
	}
	public String getTipo_bien() {
		return tipo_bien;
	}
	public void setTipo_bien(String tipo_bien) {
		this.tipo_bien = tipo_bien;
	}
	public String getCodigo_unidad() {
		return codigo_unidad;
	}
	public void setCodigo_unidad(String codigo_unidad) {
		this.codigo_unidad = codigo_unidad;
	}
	public BigDecimal getPrecio_bien() {
		return precio_bien;
	}
	public void setPrecio_bien(BigDecimal precio_bien) {
		this.precio_bien = precio_bien;
	}
	public String getAsignacion_especi() {
		return asignacion_especi;
	}
	public void setAsignacion_especi(String asignacion_especi) {
		this.asignacion_especi = asignacion_especi;
	}
	
	public String getDesc_unidad() {
		return desc_unidad;
	}
	public void setDesc_unidad(String desc_unidad) {
		this.desc_unidad = desc_unidad;
	}
	public String getCodigo_gasto() {
		return codigo_gasto;
	}
	public void setCodigo_gasto(String codigo_gasto) {
		this.codigo_gasto = codigo_gasto;
	}
	public String getDesc_gasto() {
		return desc_gasto;
	}
	public void setDesc_gasto(String desc_gasto) {
		this.desc_gasto = desc_gasto;
	}
	public String getEstadoDesconcentrado() {
		if (this.asignacion_especi == null && this.auct1==null){
			return "N";
		}else{
			return "S";
		}
	}
	public void setEstadoDesconcentrado(String estadoDesconcentrado) {
		
		this.estadoDesconcentrado = estadoDesconcentrado;
	}
	public String getAuct2() {
		return auct2;
	}
	public void setAuct2(String auct2) {
		this.auct2 = auct2;
	}
	public String getAuct3() {
		return auct3;
	}
	public void setAuct3(String auct3) {
		this.auct3 = auct3;
	}
	public String getAuct1() {
		return auct1;
	}
	public void setAuct1(String auct1) {
		this.auct1 = auct1;
	}
	public String getAuct_name() {
		return auct_name;
	}
	public void setAuct_name(String auct_name) {
		this.auct_name = auct_name;
	}
	public BigDecimal getSaldo() {
		return saldo;
	}
	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public String getTipo_ruta() {
		return tipo_ruta;
	}

	public void setTipo_ruta(String tipo_ruta) {
		this.tipo_ruta = tipo_ruta;
	}
}
