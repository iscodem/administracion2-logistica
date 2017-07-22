package pe.gob.sunat.recurso2.administracion.siga.expediente.model.bean;

public class ExpedienteProcesoBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	
	private String CodigoProceso;
	private String codigoExpediente ;
	private String anioProceso;
	private String codigoSede;
	private String codigoResponsable;
	private String CodigoDocumentoOrigen;
	
	
	public ExpedienteProcesoBean(){}


	public String getCodigoProceso() {
		return CodigoProceso;
	}


	public void setCodigoProceso(String codigoProceso) {
		CodigoProceso = codigoProceso;
	}


	public String getCodigoExpediente() {
		return codigoExpediente;
	}


	public void setCodigoExpediente(String codigoExpediente) {
		this.codigoExpediente = codigoExpediente;
	}


	public String getAnioProceso() {
		return anioProceso;
	}


	public void setAnioProceso(String anioProceso) {
		this.anioProceso = anioProceso;
	}


	public String getCodigoSede() {
		return codigoSede;
	}


	public void setCodigoSede(String codigoSede) {
		this.codigoSede = codigoSede;
	}


	public String getCodigoResponsable() {
		return codigoResponsable;
	}


	public void setCodigoResponsable(String codigoResponsable) {
		this.codigoResponsable = codigoResponsable;
	}


	public String getCodigoDocumentoOrigen() {
		return CodigoDocumentoOrigen;
	}


	public void setCodigoDocumentoOrigen(String codigoDocumentoOrigen) {
		CodigoDocumentoOrigen = codigoDocumentoOrigen;
	}
	
	
	
		
		

}
