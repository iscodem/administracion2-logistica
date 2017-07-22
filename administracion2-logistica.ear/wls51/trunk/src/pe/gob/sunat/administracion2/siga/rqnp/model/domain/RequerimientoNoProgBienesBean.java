package pe.gob.sunat.administracion2.siga.rqnp.model.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class RequerimientoNoProgBienesBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String codigoRqnp ;  		//CODI_RQNP_SRM 
	private String codigoBien ;  		//CODI_BSER_CAT
	private String item_origen;			//COD_ITEMORIGEN
	private String codigoUnidad ;  		//UNID_MEDI_DSR
	private BigDecimal cantBien  		=new BigDecimal(0);	//CANT_TOTA_DSR
	private BigDecimal precioUnid  		=new BigDecimal(0);	//PUNI_NACI_DSR
	private BigDecimal precioUnidDol  	=new BigDecimal(0);	//PUNI_DOLA_DSR
	private BigDecimal saldo  			=new BigDecimal(0);	//SALDO
	private String flagServ ;  			//FLAG_PASE_SRM
	private String presBien; 			// PRES_BIEN_SRM
	private String codPaa ; 			//CODI_PROC_PAA
	private String codRegAdq; 			//CODI_REGI_ADQ
	private String codClasifGasto; 		//CLASIFICADOR
	private String indAtentencion ;		//IND_ATENCION
	private String ind_estado ;			//IND_ESTADO
	private String obs_rechazo;			//OBS_RECHAZO
	private BigDecimal precio_origen;//MTO_PRECIOORIGEN
	private BigDecimal precio_item;
	private String cod_jefe ;			//COD_JEFE
	private Date  fec_rechazo ;			//FEC_RECHAZO
	private String desBien ;  			//DESC_BSER_CAT
	private String tipo_bien;			//TIPO_BSER_CAT
	private String desUnid ;  			//DESC_MEDI_UND
	private String codClasificador ;  	//PART_GAST_CAT
	private String desClasificador ;  	//DESC_GAST_GAS
	private String asignacion_especi;	//PLAN_CNTA_GAS
	private String tipo_ruta;	//PLAN_CTA_TRAN
	private String estadoDesconcentrado;
	private String numeroArchivo ;//NUM_ARCHIVO
	private String ind_especializado ;//IND_ESPECIALIZADO
	private String auct1 			;//COD_AUCT1
	private String auct2 			;//COD_AUCT2
	private String auct3 			;//COD_AUCT3
	private String ind_auct1;		//IND_AUTORIZAAUCT1
	private String ind_auct2;		//IND_AUTORIZAAUCT2
	private String ind_auct3;		//IND_AUTORIZAAUCT3
	private String num_exp	;		//NUME_EXPE_EXP
	private String cod_plaza;		//COD_PLAZA
	private String ind_estado_alterno;	//IND_ESTADO_ALTERNO
	private String ind_aprueba_exceso;	//IND_APRUEBA_EXCESO
	private String uuoo_exceso;			//COD_UUOO_EXCESO
	private String oec_compra;			//COD_UORESPONSABLE
	private String 		cod_proveedor			;//COD_PROVEEDOR
	private String 		obs_justificacion		;//OBS_JUSTIFICACION
	private String 		obs_plazo_entrega		;//OBS_PLAZOENTREGA
	private String 		obs_lugar_entrega		;//OBS_LUGARENTREGA
	private String 		obs_dir_entrega			;//OBS_DIRECCENTREGA
	private String 	    nom_estado ;//DESC_ESTADO
	private String 		des_tecnica ;//DES_TECNICA 
	private String 		ind_recurrente;//IND_RECURRENTE
	private String nom_proveedor;
	private String num_ruc_prov;
	private String auct_name      ;
	private BigDecimal precioTotal =new BigDecimal(0) ;	
	private String  auc_modi;//COD_AUCTMODIFICO
	private String  user_modi;//COD_USERMODIFICO
	private Date  fec_item_modi ;			//FEC_ITEMMODIFICADO
	private String ind_autorizaauct1 ;//IND_AUTORIZAAUCT1
	private Date   fec_autorizaauct1; //FEC_AUTORIZAAUCT1
	private String cod_usuarioauct1;// COD_USUARIOAUCT1
	private String desBien_origen ;
	private String desUnid_origen;
	private String nro_adjuntos ;
	private String cod_ambito; //COD_AMBITO
	private String cod_tipo_prog; //COD_TIPO_PROG
	private Date fechEnviOec; //FECH_ENVI_OEC
	private BigDecimal exceso; 
	
//Getter and Setter
	
	public BigDecimal getExceso() {
		return exceso;
	}
	public void setExceso(BigDecimal exceso) {
		this.exceso = exceso;
	}
	public Date getFechEnviOec() {
		return fechEnviOec;
	}
	public void setFechEnviOec(Date fechEnviOec) {
		this.fechEnviOec = fechEnviOec;
	}
	public String getCod_ambito() {
		return cod_ambito;
	}
	public void setCod_ambito(String cod_ambito) {
		this.cod_ambito = cod_ambito;
	}
	public String getCod_tipo_prog() {
		return cod_tipo_prog;
	}
	public void setCod_tipo_prog(String cod_tipo_prog) {
		this.cod_tipo_prog = cod_tipo_prog;
	}
	public RequerimientoNoProgBienesBean(){
		super();
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
	public String getCodigoUnidad() {
		return codigoUnidad;
	}
	public void setCodigoUnidad(String codigoUnidad) {
		this.codigoUnidad = codigoUnidad;
	}
	public BigDecimal getCantBien() {
		return cantBien;
	}
	public void setCantBien(BigDecimal cantBien) {
		this.cantBien = cantBien;
	}
	public BigDecimal getPrecioUnid() {
		return precioUnid;
	}
	public void setPrecioUnid(BigDecimal precioUnid) {
		this.precioUnid = precioUnid;
	}
	public BigDecimal getPrecioUnidDol() {
		return precioUnidDol;
	}
	public void setPrecioUnidDol(BigDecimal precioUnidDol) {
		this.precioUnidDol = precioUnidDol;
	}
	public String getFlagServ() {
		return flagServ;
	}
	public void setFlagServ(String flagServ) {
		this.flagServ = flagServ;
	}
	public String getPresBien() {
		return presBien;
	}
	public void setPresBien(String presBien) {
		this.presBien = presBien;
	}
	public String getCodPaa() {
		return codPaa;
	}
	public void setCodPaa(String codPaa) {
		this.codPaa = codPaa;
	}
	public String getCodRegAdq() {
		return codRegAdq;
	}
	public void setCodRegAdq(String codRegAdq) {
		this.codRegAdq = codRegAdq;
	}
	public String getCodClasifGasto() {
		return codClasifGasto;
	}
	public void setCodClasifGasto(String codClasifGasto) {
		this.codClasifGasto = codClasifGasto;
	}
	public String getIndAtentencion() {
		return indAtentencion;
	}
	public void setIndAtentencion(String indAtentencion) {
		this.indAtentencion = indAtentencion;
	}
	public String getDesBien() {
		return desBien;
	}
	public void setDesBien(String desBien) {
		this.desBien = desBien;
	}
	public String getDesUnid() {
		return desUnid;
	}
	public void setDesUnid(String desUnid) {
		this.desUnid = desUnid;
	}
	public String getCodClasificador() {
		return codClasificador;
	}
	public void setCodClasificador(String codClasificador) {
		this.codClasificador = codClasificador;
	}
	public String getDesClasificador() {
		return desClasificador;
	}
	public void setDesClasificador(String desClasificador) {
		this.desClasificador = desClasificador;
	}
	public String getAsignacion_especi() {
		return asignacion_especi;
	}
	public void setAsignacion_especi(String asignacion_especi) {
		this.asignacion_especi = asignacion_especi;
	}
	public String getEstadoDesconcentrado() {
		if (this.asignacion_especi == null && this.tipo_ruta==null){
			return "N";
		}else{
			return "S";
		}
	}
	public void setEstadoDesconcentrado(String estadoDesconcentrado) {
		this.estadoDesconcentrado = estadoDesconcentrado;
	}
	public BigDecimal getPrecioTotal() {
		return precioTotal;
	}
	public void setPrecioTotal(BigDecimal precioTotal) {
		this.precioTotal = precioTotal;
	}
	public String getInd_especializado() {
		return ind_especializado;
	}
	public void setInd_especializado(String ind_especializado) {
		this.ind_especializado = ind_especializado;
	}
	public String getAuct1() {
		return auct1;
	}
	public void setAuct1(String auct1) {
		this.auct1 = auct1;
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
	public String getInd_estado() {
		return ind_estado;
	}
	public void setInd_estado(String ind_estado) {
		this.ind_estado = ind_estado;
	}
	public String getAuct_name() {
		return auct_name;
	}
	public void setAuct_name(String auct_name) {
		this.auct_name = auct_name;
	}
	public String getItem_origen() {
		return item_origen;
	}
	public void setItem_origen(String item_origen) {
		this.item_origen = item_origen;
	}
	public BigDecimal getPrecio_origen() {
		return precio_origen;
	}
	public void setPrecio_origen(BigDecimal precio_origen) {
		this.precio_origen = precio_origen;
	}
	public BigDecimal getPrecio_item() {
		return precio_item;
	}
	public void setPrecio_item(BigDecimal precio_item) {
		this.precio_item = precio_item;
	}
	public String getObs_rechazo() {
		return obs_rechazo;
	}
	public void setObs_rechazo(String obs_rechazo) {
		this.obs_rechazo = obs_rechazo;
	}
	public String getCod_jefe() {
		return cod_jefe;
	}
	public void setCod_jefe(String cod_jefe) {
		this.cod_jefe = cod_jefe;
	}
	public String getInd_auct1() {
		return ind_auct1;
	}
	public void setInd_auct1(String ind_auct1) {
		this.ind_auct1 = ind_auct1;
	}
	public String getInd_auct2() {
		return ind_auct2;
	}
	public void setInd_auct2(String ind_auct2) {
		this.ind_auct2 = ind_auct2;
	}
	public String getInd_auct3() {
		return ind_auct3;
	}
	public void setInd_auct3(String ind_auct3) {
		this.ind_auct3 = ind_auct3;
	}
	public String getTipo_bien() {
		return tipo_bien;
	}
	public void setTipo_bien(String tipo_bien) {
		this.tipo_bien = tipo_bien;
	}
	public BigDecimal getSaldo() {
		return saldo;
	}
	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}
	public Date getFec_rechazo() {
		return fec_rechazo;
	}
	public void setFec_rechazo(Date fec_rechazo) {
		this.fec_rechazo = fec_rechazo;
	}
	public String getNumeroArchivo() {
		return numeroArchivo;
	}
	public void setNumeroArchivo(String numeroArchivo) {
		this.numeroArchivo = numeroArchivo;
	}
	public String getNum_exp() {
		return num_exp;
	}
	public void setNum_exp(String num_exp) {
		this.num_exp = num_exp;
	}
	public String getCod_plaza() {
		return cod_plaza;
	}
	public void setCod_plaza(String cod_plaza) {
		this.cod_plaza = cod_plaza;
	}
	public String getInd_estado_alterno() {
		return ind_estado_alterno;
	}
	public void setInd_estado_alterno(String ind_estado_alterno) {
		this.ind_estado_alterno = ind_estado_alterno;
	}
	public String getInd_aprueba_exceso() {
		return ind_aprueba_exceso;
	}
	public void setInd_aprueba_exceso(String ind_aprueba_exceso) {
		this.ind_aprueba_exceso = ind_aprueba_exceso;
	}
	public String getUuoo_exceso() {
		return uuoo_exceso;
	}
	public void setUuoo_exceso(String uuoo_exceso) {
		this.uuoo_exceso = uuoo_exceso;
	}
	public String getOec_compra() {
		return oec_compra;
	}
	public void setOec_compra(String oec_compra) {
		this.oec_compra = oec_compra;
	}
	public String getCod_proveedor() {
		return cod_proveedor;
	}
	public void setCod_proveedor(String cod_proveedor) {
		this.cod_proveedor = cod_proveedor;
	}
	public String getObs_justificacion() {
		return obs_justificacion;
	}
	public void setObs_justificacion(String obs_justificacion) {
		this.obs_justificacion = obs_justificacion;
	}
	public String getObs_plazo_entrega() {
		return obs_plazo_entrega;
	}
	public void setObs_plazo_entrega(String obs_plazo_entrega) {
		this.obs_plazo_entrega = obs_plazo_entrega;
	}
	public String getObs_lugar_entrega() {
		return obs_lugar_entrega;
	}
	public void setObs_lugar_entrega(String obs_lugar_entrega) {
		this.obs_lugar_entrega = obs_lugar_entrega;
	}
	public String getObs_dir_entrega() {
		return obs_dir_entrega;
	}
	public void setObs_dir_entrega(String obs_dir_entrega) {
		this.obs_dir_entrega = obs_dir_entrega;
	}
	public String getNom_proveedor() {
		return nom_proveedor;
	}
	public void setNom_proveedor(String nom_proveedor) {
		this.nom_proveedor = nom_proveedor;
	}
	public String getNum_ruc_prov() {
		return num_ruc_prov;
	}
	public void setNum_ruc_prov(String num_ruc_prov) {
		this.num_ruc_prov = num_ruc_prov;
	}
	public String getNom_estado() {
		return nom_estado;
	}
	public void setNom_estado(String nom_estado) {
		this.nom_estado = nom_estado;
	}
	public String getDes_tecnica() {
		return des_tecnica;
	}
	public void setDes_tecnica(String des_tecnica) {
		this.des_tecnica = des_tecnica;
	}
	public String getAuc_modi() {
		return auc_modi;
	}
	public void setAuc_modi(String auc_modi) {
		this.auc_modi = auc_modi;
	}
	public String getUser_modi() {
		return user_modi;
	}
	public void setUser_modi(String user_modi) {
		this.user_modi = user_modi;
	}
	public String getDesBien_origen() {
		return desBien_origen;
	}
	public void setDesBien_origen(String desBien_origen) {
		this.desBien_origen = desBien_origen;
	}
	public String getDesUnid_origen() {
		return desUnid_origen;
	}
	public void setDesUnid_origen(String desUnid_origen) {
		this.desUnid_origen = desUnid_origen;
	}
	public Date getFec_item_modi() {
		return fec_item_modi;
	}
	public void setFec_item_modi(Date fec_item_modi) {
		this.fec_item_modi = fec_item_modi;
	}
	public String getNro_adjuntos() {
		return nro_adjuntos;
	}
	public void setNro_adjuntos(String nro_adjuntos) {
		this.nro_adjuntos = nro_adjuntos;
	}
	public String getInd_autorizaauct1() {
		return ind_autorizaauct1;
	}
	public void setInd_autorizaauct1(String ind_autorizaauct1) {
		this.ind_autorizaauct1 = ind_autorizaauct1;
	}
	public Date getFec_autorizaauct1() {
		return fec_autorizaauct1;
	}
	public void setFec_autorizaauct1(Date fec_autorizaauct1) {
		this.fec_autorizaauct1 = fec_autorizaauct1;
	}
	public String getCod_usuarioauct1() {
		return cod_usuarioauct1;
	}
	public void setCod_usuarioauct1(String cod_usuarioauct1) {
		this.cod_usuarioauct1 = cod_usuarioauct1;
	}
	public String getInd_recurrente() {
		return ind_recurrente;
	}
	public void setInd_recurrente(String ind_recurrente) {
		this.ind_recurrente = ind_recurrente;
	}
	public String getTipo_ruta() {
		return tipo_ruta;
	}
	public void setTipo_ruta(String tipo_ruta) {
		this.tipo_ruta = tipo_ruta;
	}
}
