package pe.gob.sunat.administracion2.siga.registro.model.domain;

import java.util.Date;



public class RegistroArchivosBean implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	private String 		num_reg 	;	//NUME_REGI_ARC
	private Date 	fec_reg 	;	//FECH_REGI_ARC
	private String 		aplicacion  ;	//APLICACION
	private String 		modulo		;	//MODULO
	private String 		registro 	;	//REGISTRO	
	private String 		descripcion	;   //DESCRIPCION_REGISTRO
	
	
	
	public RegistroArchivosBean(){
		super();		
	}

	public String getNum_reg() {
		return num_reg;
	}

	public void setNum_reg(String num_reg) {
		this.num_reg = num_reg;
	}

	public Date getFec_reg() {
		return fec_reg;
	}

	public void setFec_reg(Date fec_reg) {
		this.fec_reg = fec_reg;
	}

	public String getAplicacion() {
		return aplicacion;
	}

	public void setAplicacion(String aplicacion) {
		this.aplicacion = aplicacion;
	}

	public String getModulo() {
		return modulo;
	}

	public void setModulo(String modulo) {
		this.modulo = modulo;
	}

	public String getRegistro() {
		return registro;
	}

	public void setRegistro(String registro) {
		this.registro = registro;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	
	
}
