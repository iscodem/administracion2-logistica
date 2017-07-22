package pe.gob.sunat.recurso2.administracion.siga.expediente.model.bean;

public class ExpedienteAccionBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private String codigoProceso;
	private String codigoExpedienteOrigen;
	private String codigoAccion;
	private String codigoSede;
	private String codigoResponsable;
	private String expedienteEstado;
	private String expedienteObservacion;

	
	public ExpedienteAccionBean(){}


	public String getCodigoProceso() {
		return codigoProceso;
	}


	public void setCodigoProceso(String codigoProceso) {
		this.codigoProceso = codigoProceso;
	}


	


	public String getCodigoExpedienteOrigen() {
		return codigoExpedienteOrigen;
	}


	public void setCodigoExpedienteOrigen(String codigoExpedienteOrigen) {
		this.codigoExpedienteOrigen = codigoExpedienteOrigen;
	}


	public String getCodigoAccion() {
		return codigoAccion;
	}


	public void setCodigoAccion(String codigoAccion) {
		this.codigoAccion = codigoAccion;
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


	public String getExpedienteEstado() {
		return expedienteEstado;
	}


	public void setExpedienteEstado(String expedienteEstado) {
		this.expedienteEstado = expedienteEstado;
	}


	public String getExpedienteObservacion() {
		return expedienteObservacion;
	}


	public void setExpedienteObservacion(String expedienteObservacion) {
		this.expedienteObservacion = expedienteObservacion;
	}
	
	
	
	
		
}
