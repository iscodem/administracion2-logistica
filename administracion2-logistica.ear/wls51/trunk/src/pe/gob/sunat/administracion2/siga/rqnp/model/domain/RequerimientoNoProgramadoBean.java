package pe.gob.sunat.administracion2.siga.rqnp.model.domain;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

public class RequerimientoNoProgramadoBean implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String 		codigoRqnp; 		//CODI_RQNP_SRM
	private String 		anioProceso ;		//ANO_PROC_SRM
	private String 		mesProeso ;			//MES_PROC_SRM
	private String 		numeroRqnp ; 		//NRO_REQU_SRM
	private String 		codigoSede ;		//CODI_SEDE_SED
	private Date 	fechaRqnp ;			//FECH_REQU_SRM
	private String 		motivoSolicitud ;	//MOTI_SOLI_SRM
	private String 		estadoRqnp;			//FLAG_PASE_SRM
	private BigDecimal 	montoRqnp;		//MTO_VALO_SRM
	private String 		codigoDependencia ; //CODI_DEPE_TDE
	private String 		tipoMoneda ;		//TIPO_MONE_SRM
	private String 		codigoResposanble ;	//CODI_RESP_SRM
	private Date 	fechaEnvioSolicitud ; //FECH_ENVI_SOL
	private String 		especificaAsig;		// ESPE_ASIG_SRM
	private Date 		fechaAsig; 			//FECH_ASIG_SRM
	private String 		numeroExpediente;	//NUME_EXPE_EXP
	private String 		numeroArchivo;		//NUME_REGI_ARC 	
	private String 		codigoReqNoProgramado;//COD_RQTO_NO_PROG
	private String 		motivoRechazo		;//MOTI_RECH_LOG
	private String 		cod_contacto		;//COD_CONTACTO
	private String 		anexo_contacto		;//NUM_ANEXO_CONTACTO
	private String 		cod_necesidad		;//COD_NECESIDAD
	private String 		cod_tip_nececidad		;//COD_TIPO_NECESIDAD
	private String 		nom_tip_necesidad		;//DES_TIPO_NECESIDAD
	private String		cod_finalidad			;//COD_FINALIDAD
	private String 		cod_proveedor			;//COD_PROVEEDOR
	private String 		obs_justificacion		;//OBS_JUSTIFICACION
	private String 		obs_plazo_entrega		;//OBS_PLAZOENTREGA
	private String 		obs_lugar_entrega		;//OBS_LUGARENTREGA
	private String 		obs_dir_entrega			;//OBS_DIRECCENTREGA
	//campos AUC
	private String 		cod_objeto			;//COD_OBJETO
	private String 		des_proceso			;//DES_PROCESO
	private String 		des_meta			;//DES_META
	private String 		cod_uoc1			;//COD_CONFORM1
	private String 		cod_uoc2			;//COD_CONFORM2
	private String 		cod_uoc3			;//COD_CONFORM3
	
	private Date  		fec_rech				;//FECH_RECH_LOG
	private String 		ind_solicitud		;//IND_SOLICITUD
	//Campos a eliminar
	private String 		tipoBien ;		//TIPO_BSER_SRM,
    private String 		tipoPpto	;		//TIPO_PPTO_TPR
	
	//Campos auxiliares
	private String nombreSolicitante;
	private String nombreUOSolicitante;
	private String nombreEstado;
	private String numeroRegistro;
	private String uuoo;
	private String nom_contacto		;
	private String nom_proveedor;
	private String num_ruc_prov;
	
	//campos nuevo PAS20144EB10000138
	private String  anio_atencion; //ANN_ATENCION
	private String mes_atencion ;//MES_ATENCION
	private String tipo_vinculo ;//TIPO_VINCULO
	private String vinculo ;//VINCULO_NECESIDAD
	
	//lista de Bienes
	private Collection listaBienes ;
	
	//AGREGADO: DPORRASC
	private String ind_registropor; //IND_REGISTROPOR
	//AGREGADO: DPORRASC

	//COMITE
	private String ind_comite 			;//IND_COMITE
	private String ind_tec_tit 			;//IND_TEC_TIT
	private String ind_tec_sup 			;//IND_TEC_SUP
	private String cod_au_tit 			;//COD_AU_TIT
	private String cod_au_sup 			;//COD_AU_SUP
	private String cod_tec_tit 			;//COD_TEC_TIT
	private String cod_tec_sup 			;//COD_TEC_SUP

	private String nom_tec_tit 			;//NOM_TEC_TIT
	private String nom_tec_sup 			;//NOM_TEC_SUP
	
	//AGREGADO IND_VINCULO E IND_PRESTAMO
	private String ind_vinculo 			;//IND_VINCULO
	private String ind_prestamo			;//IND_PRESTAMO
	
	public String getInd_vinculo() {
		return ind_vinculo;
	}
	public void setInd_vinculo(String ind_vinculo) {
		this.ind_vinculo = ind_vinculo;
	}
	public String getInd_prestamo() {
		return ind_prestamo;
	}
	public void setInd_prestamo(String ind_prestamo) {
		this.ind_prestamo = ind_prestamo;
	}
	public String getNom_tec_tit() {
		return nom_tec_tit;
	}
	public void setNom_tec_tit(String nom_tec_tit) {
		this.nom_tec_tit = nom_tec_tit;
	}
	public String getNom_tec_sup() {
		return nom_tec_sup;
	}
	public void setNom_tec_sup(String nom_tec_sup) {
		this.nom_tec_sup = nom_tec_sup;
	}
	public String getInd_tec_tit() {
		return ind_tec_tit;
	}
	public void setInd_tec_tit(String ind_tec_tit) {
		this.ind_tec_tit = ind_tec_tit;
	}
	public String getInd_tec_sup() {
		return ind_tec_sup;
	}
	public void setInd_tec_sup(String ind_tec_sup) {
		this.ind_tec_sup = ind_tec_sup;
	}
	public String getInd_comite() {
		return ind_comite;
	}
	public void setInd_comite(String ind_comite) {
		this.ind_comite = ind_comite;
	}
	public String getCod_au_tit() {
		return cod_au_tit;
	}
	public void setCod_au_tit(String cod_au_tit) {
		this.cod_au_tit = cod_au_tit;
	}
	public String getCod_au_sup() {
		return cod_au_sup;
	}
	public void setCod_au_sup(String cod_au_sup) {
		this.cod_au_sup = cod_au_sup;
	}
	public String getCod_tec_tit() {
		return cod_tec_tit;
	}
	public void setCod_tec_tit(String cod_tec_tit) {
		this.cod_tec_tit = cod_tec_tit;
	}
	public String getCod_tec_sup() {
		return cod_tec_sup;
	}
	public void setCod_tec_sup(String cod_tec_sup) {
		this.cod_tec_sup = cod_tec_sup;
	}
	
	public String getInd_registropor() {
		return ind_registropor;
	}
	//AGREGADO: DPORRASC
	public void setInd_registropor(String ind_registropor) {
		this.ind_registropor = ind_registropor;
	}
	public RequerimientoNoProgramadoBean(){
		super();
	}
	public String getUuoo() {
		return uuoo;
	}
	public void setUuoo(String uuoo) {
		this.uuoo = uuoo;
	}
	
	public String getCodigoRqnp() {
		return codigoRqnp;
	}
	public void setCodigoRqnp(String codigoRqnp) {
		this.codigoRqnp = codigoRqnp;
	}
	public String getAnioProceso() {
		return anioProceso;
	}
	public void setAnioProceso(String anioProceso) {
		this.anioProceso = anioProceso;
	}
	public String getMesProeso() {
		return mesProeso;
	}
	public void setMesProeso(String mesProeso) {
		this.mesProeso = mesProeso;
	}
	public String getNumeroRqnp() {
		return numeroRqnp;
	}
	public void setNumeroRqnp(String numeroRqnp) {
		this.numeroRqnp = numeroRqnp;
	}
	public String getCodigoSede() {
		return codigoSede;
	}
	public void setCodigoSede(String codigoSede) {
		this.codigoSede = codigoSede;
	}
	public Date getFechaRqnp() {
		return fechaRqnp;
	}
	public void setFechaRqnp(Date fechaRqnp) {
		this.fechaRqnp = fechaRqnp;
	}
	public String getMotivoSolicitud() {
		return motivoSolicitud;
	}
	public void setMotivoSolicitud(String motivoSolicitud) {
		this.motivoSolicitud = motivoSolicitud;
	}
	public String getEstadoRqnp() {
		return estadoRqnp;
	}
	public void setEstadoRqnp(String estadoRqnp) {
		this.estadoRqnp = estadoRqnp;
	}
	public BigDecimal getMontoRqnp() {
		return montoRqnp;
	}
	public void setMontoRqnp(BigDecimal montoRqnp) {
		this.montoRqnp = montoRqnp;
	}
	public String getCodigoDependencia() {
		return codigoDependencia;
	}
	public void setCodigoDependencia(String codigoDependencia) {
		this.codigoDependencia = codigoDependencia;
	}
	public String getTipoMoneda() {
		return tipoMoneda;
	}
	public void setTipoMoneda(String tipoMoneda) {
		this.tipoMoneda = tipoMoneda;
	}
	public String getCodigoResposanble() {
		return codigoResposanble;
	}
	public void setCodigoResposanble(String codigoResposanble) {
		this.codigoResposanble = codigoResposanble;
	}
	public Date getFechaEnvioSolicitud() {
		return fechaEnvioSolicitud;
	}
	public void setFechaEnvioSolicitud(Date fechaEnvioSolicitud) {
		this.fechaEnvioSolicitud = fechaEnvioSolicitud;
	}
	public String getEspecificaAsig() {
		return especificaAsig;
	}
	public void setEspecificaAsig(String especificaAsig) {
		this.especificaAsig = especificaAsig;
	}
	public Date getFechaAsig() {
		return fechaAsig;
	}
	public void setFechaAsig(Date fechaAsig) {
		this.fechaAsig = fechaAsig;
	}
	public String getNumeroExpediente() {
		return numeroExpediente;
	}
	public void setNumeroExpediente(String numeroExpediente) {
		this.numeroExpediente = numeroExpediente;
	}
	public String getNumeroArchivo() {
		return numeroArchivo;
	}
	public void setNumeroArchivo(String numeroArchivo) {
		this.numeroArchivo = numeroArchivo;
	}
	public String getCodigoReqNoProgramado() {
		return codigoReqNoProgramado;
	}
	public void setCodigoReqNoProgramado(String codigoReqNoProgramado) {
		this.codigoReqNoProgramado = codigoReqNoProgramado;
	}
	public String getNombreSolicitante() {
		return nombreSolicitante;
	}
	public void setNombreSolicitante(String nombreSolicitante) {
		this.nombreSolicitante = nombreSolicitante;
	}
	public String getNombreUOSolicitante() {
		return nombreUOSolicitante;
	}
	public void setNombreUOSolicitante(String nombreUOSolicitante) {
		this.nombreUOSolicitante = nombreUOSolicitante;
	}
	public String getNombreEstado() {
		return nombreEstado;
	}
	public void setNombreEstado(String nombreEstado) {
		this.nombreEstado = nombreEstado;
	}
	public String getNumeroRegistro() {
		return numeroRegistro;
	}
	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}
	public String getTipoBien() {
		return tipoBien;
	}
	public void setTipoBien(String tipoBien) {
		this.tipoBien = tipoBien;
	}
	public String getTipoPpto() {
		return tipoPpto;
	}
	public void setTipoPpto(String tipoPpto) {
		this.tipoPpto = tipoPpto;
	}
	public Collection getListaBienes() {
		return listaBienes;
	}
	public void setListaBienes(Collection listaBienes) {
		this.listaBienes = listaBienes;
	}
	public Date getFec_rech() {
		return fec_rech;
	}
	public void setFec_rech(Date fec_rech) {
		this.fec_rech = fec_rech;
	}
	public String getInd_solicitud() {
		return ind_solicitud;
	}
	public void setInd_solicitud(String ind_solicitud) {
		this.ind_solicitud = ind_solicitud;
	}
	public String getMotivoRechazo() {
		return motivoRechazo;
	}
	public void setMotivoRechazo(String motivoRechazo) {
		this.motivoRechazo = motivoRechazo;
	}
	public String getCod_contacto() {
		return cod_contacto;
	}
	public void setCod_contacto(String cod_contacto) {
		this.cod_contacto = cod_contacto;
	}
	public String getAnexo_contacto() {
		return anexo_contacto;
	}
	public void setAnexo_contacto(String anexo_contacto) {
		this.anexo_contacto = anexo_contacto;
	}
	public String getCod_necesidad() {
		return cod_necesidad;
	}
	public void setCod_necesidad(String cod_necesidad) {
		this.cod_necesidad = cod_necesidad;
	}
	public String getCod_tip_nececidad() {
		return cod_tip_nececidad;
	}
	public void setCod_tip_nececidad(String cod_tip_nececidad) {
		this.cod_tip_nececidad = cod_tip_nececidad;
	}
	public String getNom_tip_necesidad() {
		return nom_tip_necesidad;
	}
	public void setNom_tip_necesidad(String nom_tip_necesidad) {
		this.nom_tip_necesidad = nom_tip_necesidad;
	}
	public String getNom_contacto() {
		return nom_contacto;
	}
	public void setNom_contacto(String nom_contacto) {
		this.nom_contacto = nom_contacto;
	}
	public String getCod_finalidad() {
		return cod_finalidad;
	}
	public void setCod_finalidad(String cod_finalidad) {
		this.cod_finalidad = cod_finalidad;
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
	public String getCod_objeto() {
		return cod_objeto;
	}
	public void setCod_objeto(String cod_objeto) {
		this.cod_objeto = cod_objeto;
	}
	public String getDes_proceso() {
		return des_proceso;
	}
	public void setDes_proceso(String des_proceso) {
		this.des_proceso = des_proceso;
	}
	public String getDes_meta() {
		return des_meta;
	}
	public void setDes_meta(String des_meta) {
		this.des_meta = des_meta;
	}
	public String getCod_uoc1() {
		return cod_uoc1;
	}
	public void setCod_uoc1(String cod_uoc1) {
		this.cod_uoc1 = cod_uoc1;
	}
	public String getCod_uoc2() {
		return cod_uoc2;
	}
	public void setCod_uoc2(String cod_uoc2) {
		this.cod_uoc2 = cod_uoc2;
	}
	public String getCod_uoc3() {
		return cod_uoc3;
	}
	public void setCod_uoc3(String cod_uoc3) {
		this.cod_uoc3 = cod_uoc3;
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
	public String getTipo_vinculo() {
		return tipo_vinculo;
	}
	public void setTipo_vinculo(String tipo_vinculo) {
		this.tipo_vinculo = tipo_vinculo;
	}
	public String getVinculo() {
		return vinculo;
	}
	public void setVinculo(String vinculo) {
		this.vinculo = vinculo;
	}
}
