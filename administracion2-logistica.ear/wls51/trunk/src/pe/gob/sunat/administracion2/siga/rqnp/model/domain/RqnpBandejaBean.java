package pe.gob.sunat.administracion2.siga.rqnp.model.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



public class RqnpBandejaBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String 		cod_rqnp_ext ;//COD_RQTO_NO_PROG
	private Date 	fec_rqnp ;//FECH_REQU_SRM
	private String 		nom_sol ;//SOLICITANTE
	private String 		uuoo_sol ;//UUOO_SOLICITANTE
	
	private String 		motivo_sol ;//MOTI_SOLI_SRM
	private String 		cod_resp ;//CODI_RESP_SRM
	private String 		cod_dep ;//CODI_DEPE_TDE
	private String 		cod_bien ;//CODI_BSER_CAT
	private String 		des_bien ;//DES_BBSS_NUEVO
	private String 		cod_unid ;//UNID_MEDI_DSR
	private String 		unid_bien ;//UM_NUEVO
	private BigDecimal 		cantidad ;//CANT_TOTA_DSR
	private BigDecimal 		prec_bien ;//PUNI_NACI_DSR

	private BigDecimal 		prec_total ;//TOTAL
	private String 		cod_bien_orig ;//COD_ITEMORIGEN
	private String 		des_bien_orig ;//DESC_BBSS_ORIGEN
	private String 		unid_bien_orig ;//UM_ORIGEN
	private String 		auct_modifico ;//AUCT_MODIFICO
	private String 		user_modifco ;//USERMODIFICO
	private String 		fec_modifico ;//FEC_ITEMMODIFICADO
	private String 		cod_auct_mod ;//COD_AUCTMODIFICO
	private String 		cod_user_mod ;//COD_USERMODIFICO
	private String 		cod_rqnp ;//CODI_RQNP_SRM
	private String 		cod_clas ;//CLASIFICADOR
	private String 		cod_estado ;//IND_ESTADO
	private String 		des_estado ;//DESC_REGI_EST
	private String 		obs_rechazo ;//OBS_RECHAZO
	private String 		ind_especializado ;//IND_ESPECIALIZADO
	private BigDecimal	saldo ;
	private String 		name_auct;
	
	private String anio_atencion;//ANN_ATENCION
	private String mes_atencion;//MES_ATENCION
	
	private String numeroArchivo ;//NUM_ARCHIVO
	
	
	public RqnpBandejaBean(){
		super();
	}

	public String getCod_rqnp_ext() {
		return cod_rqnp_ext;
	}

	public void setCod_rqnp_ext(String cod_rqnp_ext) {
		this.cod_rqnp_ext = cod_rqnp_ext;
	}

	public Date getFec_rqnp() {
		return fec_rqnp;
	}

	public void setFec_rqnp(Date fec_rqnp) {
		this.fec_rqnp = fec_rqnp;
	}

	public String getNom_sol() {
		return nom_sol;
	}

	public void setNom_sol(String nom_sol) {
		this.nom_sol = nom_sol;
	}

	public String getUuoo_sol() {
		return uuoo_sol;
	}

	public void setUuoo_sol(String uuoo_sol) {
		this.uuoo_sol = uuoo_sol;
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

	public String getCod_bien() {
		return cod_bien;
	}

	public void setCod_bien(String cod_bien) {
		this.cod_bien = cod_bien;
	}

	public String getDes_bien() {
		return des_bien;
	}

	public void setDes_bien(String des_bien) {
		this.des_bien = des_bien;
	}

	public String getCod_unid() {
		return cod_unid;
	}

	public void setCod_unid(String cod_unid) {
		this.cod_unid = cod_unid;
	}

	public String getUnid_bien() {
		return unid_bien;
	}

	public void setUnid_bien(String unid_bien) {
		this.unid_bien = unid_bien;
	}

	public BigDecimal getCantidad() {
		return cantidad;
	}

	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

	public BigDecimal getPrec_bien() {
		return prec_bien;
	}

	public void setPrec_bien(BigDecimal prec_bien) {
		this.prec_bien = prec_bien;
	}

	public BigDecimal getPrec_total() {
		return prec_total;
	}

	public void setPrec_total(BigDecimal prec_total) {
		this.prec_total = prec_total;
	}

	public String getCod_bien_orig() {
		return cod_bien_orig;
	}

	public void setCod_bien_orig(String cod_bien_orig) {
		this.cod_bien_orig = cod_bien_orig;
	}

	public String getDes_bien_orig() {
		return des_bien_orig;
	}

	public void setDes_bien_orig(String des_bien_orig) {
		this.des_bien_orig = des_bien_orig;
	}

	public String getUnid_bien_orig() {
		return unid_bien_orig;
	}

	public void setUnid_bien_orig(String unid_bien_orig) {
		this.unid_bien_orig = unid_bien_orig;
	}

	public String getAuct_modifico() {
		return auct_modifico;
	}

	public void setAuct_modifico(String auct_modifico) {
		this.auct_modifico = auct_modifico;
	}

	public String getUser_modifco() {
		return user_modifco;
	}

	public void setUser_modifco(String user_modifco) {
		this.user_modifco = user_modifco;
	}

	public String getFec_modifico() {
		return fec_modifico;
	}

	public void setFec_modifico(String fec_modifico) {
		this.fec_modifico = fec_modifico;
	}

	public String getCod_auct_mod() {
		return cod_auct_mod;
	}

	public void setCod_auct_mod(String cod_auct_mod) {
		this.cod_auct_mod = cod_auct_mod;
	}

	public String getCod_user_mod() {
		return cod_user_mod;
	}

	public void setCod_user_mod(String cod_user_mod) {
		this.cod_user_mod = cod_user_mod;
	}

	public String getCod_rqnp() {
		return cod_rqnp;
	}

	public void setCod_rqnp(String cod_rqnp) {
		this.cod_rqnp = cod_rqnp;
	}

	public String getCod_clas() {
		return cod_clas;
	}

	public void setCod_clas(String cod_clas) {
		this.cod_clas = cod_clas;
	}

	public String getCod_estado() {
		return cod_estado;
	}

	public void setCod_estado(String cod_estado) {
		this.cod_estado = cod_estado;
	}

	public String getDes_estado() {
		return des_estado;
	}

	public void setDes_estado(String des_estado) {
		this.des_estado = des_estado;
	}

	public String getObs_rechazo() {
		return obs_rechazo;
	}

	public void setObs_rechazo(String obs_rechazo) {
		this.obs_rechazo = obs_rechazo;
	}

	public String getInd_especializado() {
		return ind_especializado;
	}

	public void setInd_especializado(String ind_especializado) {
		this.ind_especializado = ind_especializado;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public String getName_auct() {
		return name_auct;
	}

	public void setName_auct(String name_auct) {
		this.name_auct = name_auct;
	}

	public String getNumeroArchivo() {
		return numeroArchivo;
	}

	public void setNumeroArchivo(String numeroArchivo) {
		this.numeroArchivo = numeroArchivo;
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
