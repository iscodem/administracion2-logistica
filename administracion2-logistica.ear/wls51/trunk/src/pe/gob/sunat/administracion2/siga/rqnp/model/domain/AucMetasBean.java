package pe.gob.sunat.administracion2.siga.rqnp.model.domain;
import java.math.BigDecimal;
import pe.gob.sunat.framework.spring.util.date.FechaBean;

public class AucMetasBean implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String uuooBeneficiaria; //UUOO_BENEFICIARIA
	private String actProyecto;		//ACTIVIDAD_PROYECTO
	private String producto;		//PRODUCTO
	private String accion;			//ACCION
	private BigDecimal cantidad;		//CANTIDAD
	private BigDecimal precioUnitario;	//PRECIO_UNITARIO
	private BigDecimal importe;			//IMPORTE
	
	public AucMetasBean() {
		super();
	}
	public String getUuooBeneficiaria() {
		return uuooBeneficiaria;
	}
	public void setUuooBeneficiaria(String uuooBeneficiaria) {
		this.uuooBeneficiaria = uuooBeneficiaria;
	}
	public String getActProyecto() {
		return actProyecto;
	}
	public void setActProyecto(String actProyecto) {
		this.actProyecto = actProyecto;
	}
	public String getProducto() {
		return producto;
	}
	public void setProducto(String producto) {
		this.producto = producto;
	}
	public String getAccion() {
		return accion;
	}
	public void setAccion(String accion) {
		this.accion = accion;
	}
	public BigDecimal getCantidad() {
		return cantidad;
	}
	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}
	public BigDecimal getPrecioUnitario() {
		return precioUnitario;
	}
	public void setPrecioUnitario(BigDecimal precioUnitario) {
		this.precioUnitario = precioUnitario;
	}
	public BigDecimal getImporte() {
		return importe;
	}
	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}
}
