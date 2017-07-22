package pe.gob.sunat.recurso2.administracion.siga.archivo.util;

import java.util.List;
import java.util.Map;

public class ReporteJasperBean {

	private Map parametros;
	private List ListaDetalle;
	private String fileName;
	private String jasperName;
	private String idJasper;
	
	public Map getParametros() {
		return parametros;
	}
	public void setParametros(Map parametros) {
		this.parametros = parametros;
	}
	public List getListaDetalle() {
		return ListaDetalle;
	}
	public void setListaDetalle(List listaDetalle) {
		ListaDetalle = listaDetalle;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getJasperName() {
		return jasperName;
	}
	public void setJasperName(String jasperName) {
		this.jasperName = jasperName;
	}
	public String getIdJasper() {
		return idJasper;
	}
	public void setIdJasper(String idJasper) {
		this.idJasper = idJasper;
	}
	
	
}
