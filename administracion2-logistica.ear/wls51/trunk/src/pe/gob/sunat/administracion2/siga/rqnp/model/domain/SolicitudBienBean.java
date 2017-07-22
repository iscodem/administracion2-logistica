package pe.gob.sunat.administracion2.siga.rqnp.model.domain;

import java.math.BigDecimal;
import java.util.Date;


public class SolicitudBienBean implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String 		cod_solicitud 	;//CODI_BIEN_CAT;
	private String 		cod_bien_rpta	;//CODI_BSER_CAT;
	private String 		cod_empl_sol	;//CODI_EMPL_PER
	private String 		nombre_bien		;//NOMB_BIEN_CAT
	private String 		otras_caract	;//DESC_BIEN_CAT
	private Date 	fecha_sol	= new Date()	;//FECH_BIEN_CAT
	private String 		unidad_sol		;//UNID_MEDI_CAT
	private BigDecimal 	precio_sol		;//PREC_UNIT_CAT
	private String 		comentario_rpta	;//COME_BIEN_RES
	private String 		estado			;//ESTA_BIEN_CAT
	private Date 	fecha_rpta 		;//FECH_RESP_RES
	private String 		cod_emp_rpta	;//CODI_EMPL_RES
	private String 		cod_sede_sol	;//CODI_SEDE_SED
	private String 		flag			;//FLAG_REVI_CAT
	private String 		tipo_bien		;//TIPO_BSER_CAT
	private String 		tipo_sol		;//CONS_BIEN_CAT
	private String 		presentacion	;//PRES_BIEN_CAT
	private String 		caract_tecnica	;//COMP_BIEN_CAT
	private String 		caract_fisica	;//FISI_BIEN_CAT
	private String 		suger_proveedor	;//REFE_BIEN_CAT
	private String 		tiempo_expira	;//TIEM_EXPI_CAT
	private String 		envase			;//DESC_ENVA_CAT
	
	private String 		nom_empl_rpta	;
	
	private String  	nombre_sol		;//SOLICITANTE
	private String 		nombre_uuo		;//NOMBRE_UUO
	private String 		cod_unidad_rpta	;//UNID_MUSO_CAT
	private BigDecimal	precio_rpta		;//PREC_SPROM_CAT
	private String 		nom_bien_rpta	;//DESC_BSER_CAT;
	private String		unidad_rpta		;//DESC_MEDI_UND
	private String 		nom_estado		;//DESC_REGI_EST
	
	//AGREGADO CATALOGO
	private String cod_categoria 		;//COD_CATEGORIA
	private Date fecha_envio			;//FEC_ENVIO
	private String numeroArchivo 		;//COD_ADJUNTOS
	private String cod_auc 				;//COD_AUC
	private Date fecha_aprueba			;//FEC_APRUEBA_AUC
	
	private String cod_res_aprueba		;//COD_RESPONSABLE_AUC
	
	
	
	private String obs_motivo			;//OBS_MOTIVO 
	private String cod_contacto			;//COD_CONTACTO 
	private String num_anexo_contacto	;//NUM_ANEXO_CONTACTO 
	

	
	public SolicitudBienBean(){}




	public String getObs_motivo() {
		return obs_motivo;
	}




	public void setObs_motivo(String obs_motivo) {
		this.obs_motivo = obs_motivo;
	}




	public String getCod_contacto() {
		return cod_contacto;
	}




	public void setCod_contacto(String cod_contacto) {
		this.cod_contacto = cod_contacto;
	}




	public String getNum_anexo_contacto() {
		return num_anexo_contacto;
	}




	public void setNum_anexo_contacto(String num_anexo_contacto) {
		this.num_anexo_contacto = num_anexo_contacto;
	}




	public String getCod_solicitud() {
		return cod_solicitud;
	}




	public void setCod_solicitud(String cod_solicitud) {
		this.cod_solicitud = cod_solicitud;
	}




	public String getCod_bien_rpta() {
		return cod_bien_rpta;
	}




	public void setCod_bien_rpta(String cod_bien_rpta) {
		this.cod_bien_rpta = cod_bien_rpta;
	}




	public String getCod_empl_sol() {
		return cod_empl_sol;
	}




	public void setCod_empl_sol(String cod_empl_sol) {
		this.cod_empl_sol = cod_empl_sol;
	}




	public String getNombre_bien() {
		return nombre_bien;
	}




	public void setNombre_bien(String nombre_bien) {
		this.nombre_bien = nombre_bien;
	}




	public String getOtras_caract() {
		return otras_caract;
	}




	public void setOtras_caract(String otras_caract) {
		this.otras_caract = otras_caract;
	}




	




	public String getUnidad_sol() {
		return unidad_sol;
	}




	public void setUnidad_sol(String unidad_sol) {
		this.unidad_sol = unidad_sol;
	}




	public BigDecimal getPrecio_sol() {
		return precio_sol;
	}




	public void setPrecio_sol(BigDecimal precio_sol) {
		this.precio_sol = precio_sol;
	}




	public String getComentario_rpta() {
		return comentario_rpta;
	}




	public void setComentario_rpta(String comentario_rpta) {
		this.comentario_rpta = comentario_rpta;
	}




	public String getEstado() {
		return estado;
	}




	public void setEstado(String estado) {
		this.estado = estado;
	}




	




	public Date getFecha_sol() {
		return fecha_sol;
	}




	public void setFecha_sol(Date fecha_sol) {
		this.fecha_sol = fecha_sol;
	}




	public Date getFecha_rpta() {
		return fecha_rpta;
	}




	public void setFecha_rpta(Date fecha_rpta) {
		this.fecha_rpta = fecha_rpta;
	}




	public String getCod_emp_rpta() {
		return cod_emp_rpta;
	}




	public void setCod_emp_rpta(String cod_emp_rpta) {
		this.cod_emp_rpta = cod_emp_rpta;
	}




	public String getCod_sede_sol() {
		return cod_sede_sol;
	}




	public void setCod_sede_sol(String cod_sede_sol) {
		this.cod_sede_sol = cod_sede_sol;
	}




	public String getFlag() {
		return flag;
	}




	public void setFlag(String flag) {
		this.flag = flag;
	}




	public String getTipo_bien() {
		return tipo_bien;
	}




	public void setTipo_bien(String tipo_bien) {
		this.tipo_bien = tipo_bien;
	}




	public String getTipo_sol() {
		return tipo_sol;
	}




	public void setTipo_sol(String tipo_sol) {
		this.tipo_sol = tipo_sol;
	}




	public String getPresentacion() {
		return presentacion;
	}




	public void setPresentacion(String presentacion) {
		this.presentacion = presentacion;
	}




	public String getCaract_tecnica() {
		return caract_tecnica;
	}




	public void setCaract_tecnica(String caract_tecnica) {
		this.caract_tecnica = caract_tecnica;
	}




	public String getCaract_fisica() {
		return caract_fisica;
	}




	public void setCaract_fisica(String caract_fisica) {
		this.caract_fisica = caract_fisica;
	}




	public String getSuger_proveedor() {
		return suger_proveedor;
	}




	public void setSuger_proveedor(String suger_proveedor) {
		this.suger_proveedor = suger_proveedor;
	}




	public String getTiempo_expira() {
		return tiempo_expira;
	}




	public void setTiempo_expira(String tiempo_expira) {
		this.tiempo_expira = tiempo_expira;
	}




	public String getEnvase() {
		return envase;
	}




	public void setEnvase(String envase) {
		this.envase = envase;
	}




	public String getNombre_sol() {
		return nombre_sol;
	}




	public void setNombre_sol(String nombre_sol) {
		this.nombre_sol = nombre_sol;
	}




	public String getNombre_uuo() {
		return nombre_uuo;
	}




	public void setNombre_uuo(String nombre_uuo) {
		this.nombre_uuo = nombre_uuo;
	}




	public String getCod_unidad_rpta() {
		return cod_unidad_rpta;
	}




	public void setCod_unidad_rpta(String cod_unidad_rpta) {
		this.cod_unidad_rpta = cod_unidad_rpta;
	}




	public BigDecimal getPrecio_rpta() {
		return precio_rpta;
	}




	public void setPrecio_rpta(BigDecimal precio_rpta) {
		this.precio_rpta = precio_rpta;
	}




	public String getNom_bien_rpta() {
		return nom_bien_rpta;
	}




	public void setNom_bien_rpta(String nom_bien_rpta) {
		this.nom_bien_rpta = nom_bien_rpta;
	}




	public String getUnidad_rpta() {
		return unidad_rpta;
	}




	public void setUnidad_rpta(String unidad_rpta) {
		this.unidad_rpta = unidad_rpta;
	}




	public String getNom_estado() {
		return nom_estado;
	}




	public void setNom_estado(String nom_estado) {
		this.nom_estado = nom_estado;
	}
	
	public String getCod_categoria() {
		return cod_categoria;
	}




	public void setCod_categoria(String cod_categoria) {
		this.cod_categoria = cod_categoria;
	}




	public Date getFecha_envio() {
		return fecha_envio;
	}




	public void setFecha_envio(Date fecha_envio) {
		this.fecha_envio = fecha_envio;
	}




	public String getNumeroArchivo() {
		return numeroArchivo;
	}




	public void setNumeroArchivo(String numeroArchivo) {
		this.numeroArchivo = numeroArchivo;
	}




	public String getCod_auc() {
		return cod_auc;
	}




	public void setCod_auc(String cod_auc) {
		this.cod_auc = cod_auc;
	}




	public Date getFecha_aprueba() {
		return fecha_aprueba;
	}




	public void setFecha_aprueba(Date fecha_aprueba) {
		this.fecha_aprueba = fecha_aprueba;
	}




	public String getCod_res_aprueba() {
		return cod_res_aprueba;
	}




	public void setCod_res_aprueba(String cod_res_aprueba) {
		this.cod_res_aprueba = cod_res_aprueba;
	}




	public String getNom_empl_rpta() {
		return nom_empl_rpta;
	}




	public void setNom_empl_rpta(String nom_empl_rpta) {
		this.nom_empl_rpta = nom_empl_rpta;
	}
	
	
}
