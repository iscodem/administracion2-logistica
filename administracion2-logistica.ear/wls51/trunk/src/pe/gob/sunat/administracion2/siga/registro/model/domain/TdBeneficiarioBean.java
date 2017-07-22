package pe.gob.sunat.administracion2.siga.registro.model.domain;

import java.math.BigDecimal;

public class TdBeneficiarioBean implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	private BigDecimal idBeneficiarios;		//CORR_BIEN_ADQ
	private String codiRegiAdq;				//CODI_REGI_ADQ
	private BigDecimal numeItemCon;			//NUME_ITEM_CON
	private String unidadOrganizacional;	//CODI_BSER_CAT
	private String descDepeTde;				//DESC_TECN_RES
	private double cantidad;				//CANT_INIC_RES
	private String secuFuncSfu;				//SECU_FUNC_SFU
	private String metaSiaf;				//META_SIAF
	
	public String getMetaSiaf() {
		return metaSiaf;
	}
	public void setMetaSiaf(String metaSiaf) {
		this.metaSiaf = metaSiaf;
	}
	public String getSecuFuncSfu() {
		return secuFuncSfu;
	}
	public void setSecuFuncSfu(String secuFuncSfu) {
		this.secuFuncSfu = secuFuncSfu;
	}
	public BigDecimal getIdBeneficiarios() {
		return idBeneficiarios;
	}
	public void setIdBeneficiarios(BigDecimal idBeneficiarios) {
		this.idBeneficiarios = idBeneficiarios;
	}
	public String getUnidadOrganizacional() {
		return unidadOrganizacional;
	}
	public void setUnidadOrganizacional(String unidadOrganizacional) {
		this.unidadOrganizacional = unidadOrganizacional;
	}
	public String getDescDepeTde() {
		return descDepeTde;
	}
	public void setDescDepeTde(String descDepeTde) {
		this.descDepeTde = descDepeTde;
	}
	public double getCantidad() {
		return cantidad;
	}
	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}
	public BigDecimal getNumeItemCon() {
		return numeItemCon;
	}
	public void setNumeItemCon(BigDecimal numeItemCon) {
		this.numeItemCon = numeItemCon;
	}
	public String getCodiRegiAdq() {
		return codiRegiAdq;
	}
	public void setCodiRegiAdq(String codiRegiAdq) {
		this.codiRegiAdq = codiRegiAdq;
	}
}
