package pe.gob.sunat.administracion2.siga.rqnp.model.domain;

import java.math.BigDecimal;
import java.util.Date;

public class AucBandejaBean {

	private static final long serialVersionUID = 1L;
	
	private String num_rqnp  ;//COD_RQTO_NO_PROG
	private Date 	fec_rqnp;//FECH_REQU_SRM
	private String solicitante;//SOLICITANTE
	private String uuoo_solicitante;//UUOO_SOLICITANTE
	private String motivo_sol ;//MOTI_SOLI_SRM
	private String cod_resp;//CODI_RESP_SRM
	private String cod_dep;//CODI_DEPE_TDE
	private BigDecimal monto_total;//TOTAL
	private String cod_rqnp ;//CODI_RQNP_SRM
	private Integer num_auc;//NUM_AUC
	private Integer num_item;//NUM_ITEM
	private String estado ;//IND_ESTADO 
	private String anio_atencion ;//ANN_ATENCION
	private String mes_atencion;//MES_ATENCION
	
	
	
	
	public AucBandejaBean(){
		super();
	}
	
	
	public String getNum_rqnp() {
		return num_rqnp;
	}
	public void setNum_rqnp(String num_rqnp) {
		this.num_rqnp = num_rqnp;
	}
	public Date getFec_rqnp() {
		return fec_rqnp;
	}
	public void setFec_rqnp(Date fec_rqnp) {
		this.fec_rqnp = fec_rqnp;
	}
	public String getSolicitante() {
		return solicitante;
	}
	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}
	public String getUuoo_solicitante() {
		return uuoo_solicitante;
	}
	public void setUuoo_solicitante(String uuoo_solicitante) {
		this.uuoo_solicitante = uuoo_solicitante;
	}
	public String getMotivo_sol() {
		return motivo_sol;
	}
	public void setMotivo_sol(String motivo_sol) {
		this.motivo_sol = motivo_sol;
	}
	public String getCod_resp() {
		return cod_resp;
	}
	public void setCod_resp(String cod_resp) {
		this.cod_resp = cod_resp;
	}
	public String getCod_dep() {
		return cod_dep;
	}
	public void setCod_dep(String cod_dep) {
		this.cod_dep = cod_dep;
	}
	public BigDecimal getMonto_total() {
		return monto_total;
	}
	public void setMonto_total(BigDecimal monto_total) {
		this.monto_total = monto_total;
	}
	public String getCod_rqnp() {
		return cod_rqnp;
	}
	public void setCod_rqnp(String cod_rqnp) {
		this.cod_rqnp = cod_rqnp;
	}
	public Integer getNum_auc() {
		return num_auc;
	}
	public void setNum_auc(Integer num_auc) {
		this.num_auc = num_auc;
	}


	public Integer getNum_item() {
		return num_item;
	}


	public void setNum_item(Integer num_item) {
		this.num_item = num_item;
	}


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}


	public String getAnio_atencion() {
		return anio_atencion;
	}


	public void setAnio_atencion(String anio_atencion) {
		this.anio_atencion = anio_atencion;
	}


	public String getMes_atencion() {
		return mes_atencion;
	}


	public void setMes_atencion(String mes_atencion) {
		this.mes_atencion = mes_atencion;
	}
	
	
	
	
	
	
	
	
	
	
	
}
