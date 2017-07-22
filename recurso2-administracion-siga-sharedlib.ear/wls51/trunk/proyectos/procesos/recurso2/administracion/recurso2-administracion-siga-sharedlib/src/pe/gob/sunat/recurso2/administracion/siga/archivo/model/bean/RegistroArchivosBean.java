/*
 * 
 */
package pe.gob.sunat.recurso2.administracion.siga.archivo.model.bean;

import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * Utilizado para los parametros de salida y/o entrada de las consultas o transacciones en la tabla registro archivo.		
 * @author: Eduardo Marchena.
 * 
 */



public class RegistroArchivosBean implements java.io.Serializable{
	
	/** La constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** El num_reg. */
	private String 		num_reg 	;	//NUME_REGI_ARC
	
	/** El fec_reg. */
	private Date 		fec_reg 	;	//FECH_REGI_ARC
	
	/** El aplicacion. */
	private String 		aplicacion  ;	//APLICACION
	
	/** El modulo. */
	private String 		modulo		;	//MODULO
	
	/** El registro. */
	private String 		registro 	;	//REGISTRO	
	
	/** El descripcion. */
	private String 		descripcion	;   //DESCRIPCION_REGISTRO
	
	
	
	/**
	 * Instancias a nuevo registro archivos bean.
	 */
	public RegistroArchivosBean(){
		super();		
	}

	/**
	 * Gets the num_reg.
	 *
	 * @return the num_reg
	 */
	public String getNum_reg() {
		return num_reg;
	}

	/**
	 * Sets the num_reg.
	 *
	 * @param num_reg the new num_reg
	 */
	public void setNum_reg(String num_reg) {
		this.num_reg = num_reg;
	}

	/**
	 * Gets the fec_reg.
	 *
	 * @return the fec_reg
	 */
	public Date getFec_reg() {
		return fec_reg;
	}

	/**
	 * Sets the fec_reg.
	 *
	 * @param fec_reg the new fec_reg
	 */
	public void setFec_reg(Date fec_reg) {
		this.fec_reg = fec_reg;
	}

	/**
	 * Gets the aplicacion.
	 *
	 * @return the aplicacion
	 */
	public String getAplicacion() {
		return aplicacion;
	}

	/**
	 * Sets the aplicacion.
	 *
	 * @param aplicacion the new aplicacion
	 */
	public void setAplicacion(String aplicacion) {
		this.aplicacion = aplicacion;
	}

	/**
	 * Gets the modulo.
	 *
	 * @return the modulo
	 */
	public String getModulo() {
		return modulo;
	}

	/**
	 * Sets the modulo.
	 *
	 * @param modulo the new modulo
	 */
	public void setModulo(String modulo) {
		this.modulo = modulo;
	}

	/**
	 * Gets the registro.
	 *
	 * @return the registro
	 */
	public String getRegistro() {
		return registro;
	}

	/**
	 * Sets the registro.
	 *
	 * @param registro the new registro
	 */
	public void setRegistro(String registro) {
		this.registro = registro;
	}

	/**
	 * Gets the descripcion.
	 *
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Sets the descripcion.
	 *
	 * @param descripcion the new descripcion
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	
	
}
