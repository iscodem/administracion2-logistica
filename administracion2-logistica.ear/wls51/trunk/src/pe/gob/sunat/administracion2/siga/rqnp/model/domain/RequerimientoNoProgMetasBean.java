package pe.gob.sunat.administracion2.siga.rqnp.model.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class RequerimientoNoProgMetasBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String codigoRqnp ; 	//CODI_RQNP_SRM
	private String codigoBien ; 	//CODI_BSER_CAT
	private String codigoBien_origen ; 	
	private String anioEjec ;		//ANNO_EJEC_EJE
	private String secuenciaMeta ;		//SECU_FUNC_SFU
	private BigDecimal cantidadTotal  =new BigDecimal(0);	//CANT_TOTA_DSR 
	private BigDecimal montoSoles  =new BigDecimal(0); //MONT_NACI_DSR
	private BigDecimal montoDolar  =new BigDecimal(0); //MONT_DOLA_DSR
	private String ubg ;			//UBG
	private String producto; 		//PRODUCTO
	private String meta ; 			//META
	private String uuoo;			//UUOO
	private BigDecimal precioUnid  =new BigDecimal(0);
	
	private String metaSiaf;		//CADE_SIAF_CAD
	
	public RequerimientoNoProgMetasBean(){
		super();
	}

	public String getMetaSiaf() {
		return metaSiaf;
	}
	public void setMetaSiaf(String metaSiaf) {
		this.metaSiaf = metaSiaf;
	}
	public String getCodigoRqnp() {
		return codigoRqnp;
	}
	public void setCodigoRqnp(String codigoRqnp) {
		this.codigoRqnp = codigoRqnp;
	}
	public String getCodigoBien() {
		return codigoBien;
	}
	public void setCodigoBien(String codigoBien) {
		this.codigoBien = codigoBien;
	}
	public String getAnioEjec() {
		return anioEjec;
	}
	public void setAnioEjec(String anioEjec) {
		this.anioEjec = anioEjec;
	}
	public String getSecuenciaMeta() {
		return secuenciaMeta;
	}
	public void setSecuenciaMeta(String secuenciaMeta) {
		this.secuenciaMeta = secuenciaMeta;
	}
	public BigDecimal getCantidadTotal() {
		return cantidadTotal;
	}
	public void setCantidadTotal(BigDecimal cantidadTotal) {
		this.cantidadTotal = cantidadTotal;
	}
	public BigDecimal getMontoSoles() {
		return montoSoles;
	}
	public void setMontoSoles(BigDecimal montoSoles) {
		this.montoSoles = montoSoles;
	}
	public BigDecimal getMontoDolar() {
		return montoDolar;
	}
	public void setMontoDolar(BigDecimal montoDolar) {
		this.montoDolar = montoDolar;
	}
	public String getUbg() {
		return ubg;
	}
	public void setUbg(String ubg) {
		this.ubg = ubg;
	}
	public String getProducto() {
		return producto;
	}
	public void setProducto(String producto) {
		this.producto = producto;
	}
	public String getMeta() {
		return meta;
	}
	public void setMeta(String meta) {
		this.meta = meta;
	}
	public String getUuoo() {
		return uuoo;
	}
	public void setUuoo(String uuoo) {
		this.uuoo = uuoo;
	}
	public BigDecimal getPrecioUnid() {
		return precioUnid;
	}
	public void setPrecioUnid(BigDecimal precioUnid) {
		this.precioUnid = precioUnid;
	}
	public String getCodigoBien_origen() {
		return codigoBien_origen;
	}
	public void setCodigoBien_origen(String codigoBien_origen) {
		this.codigoBien_origen = codigoBien_origen;
	}
	
}
