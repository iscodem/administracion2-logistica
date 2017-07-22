package pe.gob.sunat.administracion2.siga.rqnp.model.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

public class AucAnexoBean {
	private static final long serialVersionUID = 1L;
	
	String num_rqnp 		;//COD_RQTO_NO_PROG 
	String cod_rqnp 		;//CODI_RQNP_SRM
	String fec_rqnp 		;//FECH_REQU_SRM
	String nom_contacto 	;//NOMBRE_CONTACTO
	String uuoo_des 		;//UUOO_DESCRIPCION
	String anexo_contacto 	;//NUM_ANEXO_CONTACTO
	String des_proceso	 	;//DES_PROCESO
	String des_auc 			;//DES_AUC
	String fec_auc 			;//FECHA_AUCT
	String des_ubg 			;//UBG
	String cod_objeto		;//COD_OBJETO
	String des_meta			;//DES_META
	String des_finalidad	;//DES_FINALIDAD
	String cod_finalidad	;//COD_FINALIDAD
	String des_motivo  		;//MOTI_SOLI_SRM
	String dur_necesidad	;//DURACION_NECESIDAD
	String tip_necesidad  	;//TIPO_NECESIDAD
	String des_tip_necesidad  		;//DES_TIPO_NECESIDAD,
	
	String des_tecnica 		;//DES_TECNICA
	String des_c1  			;//DES_C1
	String des_c2  			;//DES_C2
	String des_c3  			;//DES_C3
	String cod_resp			;//CODI_RESP_SRM
	String cod_dep  		;//CODI_DEPE_TDE
	String cod_contacto		;//COD_CONTACTO
	
	
	
	//DETALLE
	String cod_bien  		;//CODI_BSER_CAT
	String des_bien  		;//DESC_BSER_CAT
	BigDecimal monto_bien  		;//IMPORTE
	String ubg_det			;//UBG_DET
	BigDecimal monto_ubg		;//IMP_UBG
	BigDecimal cantidad_bien	;//CANTIDAD_DETALLE
	String unidad_bien		;//UNIDAD_MEDIDA
	String tipo_vinculo ;//TIPO_VINCULO
	String vinculo;//VINCULO_NECESIDAD
	String anio_atencion;
	String mes_atencion;
	
	
	//COMITE
	String ind_comite 			;//IND_COMITE
	String ind_tec_tit 			;//IND_TEC_TIT
	String ind_tec_sup 			;//IND_TEC_SUP
	String cod_au_tit 			;//COD_AU_TIT
	String cod_au_sup 			;//COD_AU_SUP
	String cod_tec_tit 			;//COD_TEC_TIT
	String cod_tec_sup 			;//COD_TEC_SUP

	String nom_tec_tit 			;//NOM_TEC_TIT
	String nom_tec_sup 			;//NOM_TEC_SUP
	
	String ind_vinculo 			;//IND_VINCULO
	String ind_prestamo 		;//IND_PRESTAMO
	
	
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

	public AucAnexoBean(){
		super();
	}

	public String getInd_comite() {
		return ind_comite;
	}


	public void setInd_comite(String ind_comite) {
		this.ind_comite = ind_comite;
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



	public String getNum_rqnp() {
		return num_rqnp;
	}



	public void setNum_rqnp(String num_rqnp) {
		this.num_rqnp = num_rqnp;
	}



	public String getCod_rqnp() {
		return cod_rqnp;
	}







	public void setCod_rqnp(String cod_rqnp) {
		this.cod_rqnp = cod_rqnp;
	}







	







	public String getNom_contacto() {
		return nom_contacto;
	}







	public void setNom_contacto(String nom_contacto) {
		this.nom_contacto = nom_contacto;
	}







	public String getUuoo_des() {
		return uuoo_des;
	}







	public void setUuoo_des(String uuoo_des) {
		this.uuoo_des = uuoo_des;
	}







	public String getAnexo_contacto() {
		return anexo_contacto;
	}







	public void setAnexo_contacto(String anexo_contacto) {
		this.anexo_contacto = anexo_contacto;
	}







	public String getDes_proceso() {
		return des_proceso;
	}







	public void setDes_proceso(String des_proceso) {
		this.des_proceso = des_proceso;
	}







	public String getDes_auc() {
		return des_auc;
	}







	public void setDes_auc(String des_auc) {
		this.des_auc = des_auc;
	}




























	public String getFec_rqnp() {
		return fec_rqnp;
	}















	public void setFec_rqnp(String fec_rqnp) {
		this.fec_rqnp = fec_rqnp;
	}















	public String getFec_auc() {
		return fec_auc;
	}















	public void setFec_auc(String fec_auc) {
		this.fec_auc = fec_auc;
	}















	public String getDes_ubg() {
		return des_ubg;
	}







	public void setDes_ubg(String des_ubg) {
		this.des_ubg = des_ubg;
	}







	public String getCod_objeto() {
		return cod_objeto;
	}







	public void setCod_objeto(String cod_objeto) {
		this.cod_objeto = cod_objeto;
	}







	public String getDes_meta() {
		return des_meta;
	}







	public void setDes_meta(String des_meta) {
		this.des_meta = des_meta;
	}







	public String getDes_finalidad() {
		return des_finalidad;
	}







	public void setDes_finalidad(String des_finalidad) {
		this.des_finalidad = des_finalidad;
	}







	public String getCod_finalidad() {
		return cod_finalidad;
	}







	public void setCod_finalidad(String cod_finalidad) {
		this.cod_finalidad = cod_finalidad;
	}







	public String getDes_motivo() {
		return des_motivo;
	}







	public void setDes_motivo(String des_motivo) {
		this.des_motivo = des_motivo;
	}







	public String getDur_necesidad() {
		return dur_necesidad;
	}







	public void setDur_necesidad(String dur_necesidad) {
		this.dur_necesidad = dur_necesidad;
	}







	public String getTip_necesidad() {
		return tip_necesidad;
	}







	public void setTip_necesidad(String tip_necesidad) {
		this.tip_necesidad = tip_necesidad;
	}







	public String getDes_tip_necesidad() {
		return des_tip_necesidad;
	}







	public void setDes_tip_necesidad(String des_tip_necesidad) {
		this.des_tip_necesidad = des_tip_necesidad;
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







	public BigDecimal getMonto_bien() {
		return monto_bien;
	}







	public void setMonto_bien(BigDecimal monto_bien) {
		this.monto_bien = monto_bien;
	}







	public String getDes_tecnica() {
		return des_tecnica;
	}







	public void setDes_tecnica(String des_tecnica) {
		this.des_tecnica = des_tecnica;
	}







	public String getDes_c1() {
		return des_c1;
	}







	public void setDes_c1(String des_c1) {
		this.des_c1 = des_c1;
	}







	public String getDes_c2() {
		return des_c2;
	}







	public void setDes_c2(String des_c2) {
		this.des_c2 = des_c2;
	}







	public String getDes_c3() {
		return des_c3;
	}







	public void setDes_c3(String des_c3) {
		this.des_c3 = des_c3;
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







	public String getCod_contacto() {
		return cod_contacto;
	}







	public void setCod_contacto(String cod_contacto) {
		this.cod_contacto = cod_contacto;
	}







	public String getUbg_det() {
		return ubg_det;
	}







	public void setUbg_det(String ubg_det) {
		this.ubg_det = ubg_det;
	}







	public BigDecimal getMonto_ubg() {
		return monto_ubg;
	}







	public void setMonto_ubg(BigDecimal monto_ubg) {
		this.monto_ubg = monto_ubg;
	}







	public BigDecimal getCantidad_bien() {
		return cantidad_bien;
	}







	public void setCantidad_bien(BigDecimal cantidad_bien) {
		this.cantidad_bien = cantidad_bien;
	}







	public String getUnidad_bien() {
		return unidad_bien;
	}







	public void setUnidad_bien(String unidad_bien) {
		this.unidad_bien = unidad_bien;
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
