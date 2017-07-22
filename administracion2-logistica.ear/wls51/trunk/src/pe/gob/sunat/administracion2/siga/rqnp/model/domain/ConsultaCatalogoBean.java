package pe.gob.sunat.administracion2.siga.rqnp.model.domain;

import java.math.BigDecimal;

public class ConsultaCatalogoBean implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String tipo;					//TIPO
	private String codigo_item;				//CODIGO_ITEM
	private String descripcion_item;		//DESCRIPCION_ITEM
	private String unidad_medida;			//UNIDAD_MEDIDA
	private String precio_unitario;			//PRECIO_UNITARIO
	private String clasificador_gasto;		//CLASIFICADOR_GASTO
	private String descripcion_clasificador;//DESCRIPCION_CLASIFICADOR
	private String auc;						//AUC
	private String ambito;					//AMBITO
	private String ruta_programado;			//RUTA_PROGRAMADO
	private String ruta_no_programado;		//RUTA_NO_PROGRAMADO
	private String rubro_general;			//RUBRO_GENERAL
	private String rubro_especifico;		//RUBRO_ESPECIFICO
	private String estado;					//ESTADO
	private String alerta;					//ALERTA
	public String getAlerta() {
		return alerta;
	}
	public void setAlerta(String alerta) {
		this.alerta = alerta;
	}
	private String grupo_catalogo;			//GRUPO_CATALOGO
	private String grupo_descripcion;		//GRUPO_DESCRIPCION
	private String clase_catalogo;			//CLASE_CATALOGO
	private String clase_descripcion;		//CLASE_DESCRIPCION
	private String familia_catalogo;		//FAMILIA_CATALOGO
	private String familia_descripcion;		//FAMILIA_DESCRIPCION
	
	public ConsultaCatalogoBean() {
		super();
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getCodigo_item() {
		return codigo_item;
	}
	public void setCodigo_item(String codigo_item) {
		this.codigo_item = codigo_item;
	}
	public String getDescripcion_item() {
		return descripcion_item;
	}
	public void setDescripcion_item(String descripcion_item) {
		this.descripcion_item = descripcion_item;
	}
	public String getUnidad_medida() {
		return unidad_medida;
	}
	public void setUnidad_medida(String unidad_medida) {
		this.unidad_medida = unidad_medida;
	}
	public String getPrecio_unitario() {
		return precio_unitario;
	}
	public void setPrecio_unitario(String precio_unitario) {
		this.precio_unitario = precio_unitario;
	}
	public String getClasificador_gasto() {
		return clasificador_gasto;
	}
	public void setClasificador_gasto(String clasificador_gasto) {
		this.clasificador_gasto = clasificador_gasto;
	}
	public String getDescripcion_clasificador() {
		return descripcion_clasificador;
	}
	public void setDescripcion_clasificador(String descripcion_clasificador) {
		this.descripcion_clasificador = descripcion_clasificador;
	}
	public String getAuc() {
		return auc;
	}
	public void setAuc(String auc) {
		this.auc = auc;
	}
	public String getAmbito() {
		return ambito;
	}
	public void setAmbito(String ambito) {
		this.ambito = ambito;
	}
	public String getRuta_programado() {
		return ruta_programado;
	}
	public void setRuta_programado(String ruta_programado) {
		this.ruta_programado = ruta_programado;
	}
	public String getRuta_no_programado() {
		return ruta_no_programado;
	}
	public void setRuta_no_programado(String ruta_no_programado) {
		this.ruta_no_programado = ruta_no_programado;
	}
	public String getRubro_general() {
		return rubro_general;
	}
	public void setRubro_general(String rubro_general) {
		this.rubro_general = rubro_general;
	}
	public String getRubro_especifico() {
		return rubro_especifico;
	}
	public void setRubro_especifico(String rubro_especifico) {
		this.rubro_especifico = rubro_especifico;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getGrupo_catalogo() {
		return grupo_catalogo;
	}
	public void setGrupo_catalogo(String grupo_catalogo) {
		this.grupo_catalogo = grupo_catalogo;
	}
	public String getGrupo_descripcion() {
		return grupo_descripcion;
	}
	public void setGrupo_descripcion(String grupo_descripcion) {
		this.grupo_descripcion = grupo_descripcion;
	}
	public String getClase_catalogo() {
		return clase_catalogo;
	}
	public void setClase_catalogo(String clase_catalogo) {
		this.clase_catalogo = clase_catalogo;
	}
	public String getClase_descripcion() {
		return clase_descripcion;
	}
	public void setClase_descripcion(String clase_descripcion) {
		this.clase_descripcion = clase_descripcion;
	}
	public String getFamilia_catalogo() {
		return familia_catalogo;
	}
	public void setFamilia_catalogo(String familia_catalogo) {
		this.familia_catalogo = familia_catalogo;
	}
	public String getFamilia_descripcion() {
		return familia_descripcion;
	}
	public void setFamilia_descripcion(String familia_descripcion) {
		this.familia_descripcion = familia_descripcion;
	}
}
