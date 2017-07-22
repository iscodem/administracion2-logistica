package pe.gob.sunat.recurso2.administracion.siga.firma.model.bean;

import java.util.Date;

public class T5282Archbin {
	
	private Long numId;
	private Date fecCreacion;
	private byte[] arcDatos;
	private Integer cntTamanho;
	private String desNombre;
	private Integer numIdasoc;
	private String indSisorigen;
	private String desNombreAlternativo;
	
	public Long getNumId() {
		return numId;
	}
	public void setNumId(Long numPDF) {
		this.numId = numPDF;
	}
	public Date getFecCreacion() {
		return fecCreacion;
	}
	public void setFecCreacion(Date fecCreacion) {
		this.fecCreacion = fecCreacion;
	}
	public byte[] getArcDatos() {
		return arcDatos;
	}
	public void setArcDatos(byte[] arcDatos) {
		this.arcDatos = arcDatos;
	}
	public Integer getCntTamanho() {
		return cntTamanho;
	}
	public void setCntTamanho(Integer cntTamanho) {
		this.cntTamanho = cntTamanho;
	}
	public String getDesNombre() {
		return desNombre;
	}
	public void setDesNombre(String desNombre) {
		this.desNombre = desNombre;
	}
	public Integer getNumIdasoc() {
		return numIdasoc;
	}
	public void setNumIdasoc(Integer numIdasoc) {
		this.numIdasoc = numIdasoc;
	}
	public String getIndSisorigen() {
		return indSisorigen;
	}
	public void setIndSisorigen(String indSisorigen) {
		this.indSisorigen = indSisorigen;
	}
	public void setDesNombreAlternativo(String desNombreAlternativo) {
		this.desNombreAlternativo = desNombreAlternativo;
	}
	public String getDesNombreAlternativo() {
		return desNombreAlternativo;
	}
	
}


