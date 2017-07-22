package pe.gob.sunat.recurso2.administracion.siga.archivo.model.bean;

import java.io.Serializable;

/**
 * Utilizado para los parametros de salida y/o entrada de las consultas del tipo de archivo.		
 * @author: Eduardo Marchena.
 * 
 */

public class TipoDocumentoBean implements Serializable {
	
	private static final long serialVersionUID = -3136892286002584301L;
	
	private String codigoTipoDocumento;
	private String descripcion;
	
	
	public String getCodigoTipoDocumento() {
		return codigoTipoDocumento;
	}
	public void setCodigoTipoDocumento(String codigoTipoDocumento) {
		this.codigoTipoDocumento = codigoTipoDocumento;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
	
	
}
