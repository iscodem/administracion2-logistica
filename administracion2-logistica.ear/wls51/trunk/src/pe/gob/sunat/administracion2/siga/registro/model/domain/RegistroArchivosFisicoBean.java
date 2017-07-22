package pe.gob.sunat.administracion2.siga.registro.model.domain;

import java.util.Date;



public class RegistroArchivosFisicoBean implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	
	private String 	num_reg;		//NUME_REGI_ARC
	private Long 	sec_reg;		//SECU_REGI_ARC
	private String 	file_name;		//FILENAME
	private String 	file_ext;		//FILEEXTENSION
	private String 	nom_object;		//NOMBRE_OBJETO
	private String 	descrip_reg;	//DESCRIPCION
	private String 	fuente_reg;		//FUENTE
	private Date	fec_carga;	//FECH_CARG_ARC
	private String 	flag_imagen;	//FLAG_IMAGEN
	private String 	flag_security;	//FLAG_RESTRINGIDO
	private Long 	size;			//TAMANO
	private byte[] data;			//DATAOBJECT

		
	public RegistroArchivosFisicoBean(){
		super();
	}


	public String getNum_reg() {
		return num_reg;
	}


	public void setNum_reg(String num_reg) {
		this.num_reg = num_reg;
	}


	public Long getSec_reg() {
		return sec_reg;
	}


	public void setSec_reg(Long sec_reg) {
		this.sec_reg = sec_reg;
	}


	public String getFile_name() {
		return file_name;
	}


	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}


	public String getFile_ext() {
		return file_ext;
	}


	public void setFile_ext(String file_ext) {
		this.file_ext = file_ext;
	}


	public String getNom_object() {
		return nom_object;
	}


	public void setNom_object(String nom_object) {
		this.nom_object = nom_object;
	}


	public String getDescrip_reg() {
		return descrip_reg;
	}


	public void setDescrip_reg(String descrip_reg) {
		this.descrip_reg = descrip_reg;
	}


	public String getFuente_reg() {
		return fuente_reg;
	}


	public void setFuente_reg(String fuente_reg) {
		this.fuente_reg = fuente_reg;
	}


	public Date getFec_carga() {
		return fec_carga;
	}


	public void setFec_carga(Date fec_carga) {
		this.fec_carga = fec_carga;
	}


	public String getFlag_imagen() {
		return flag_imagen;
	}


	public void setFlag_imagen(String flag_imagen) {
		this.flag_imagen = flag_imagen;
	}


	public String getFlag_security() {
		return flag_security;
	}


	public void setFlag_security(String flag_security) {
		this.flag_security = flag_security;
	}


	public Long getSize() {
		return size;
	}


	public void setSize(Long size) {
		this.size = size;
	}


	public byte[] getData() {
		return data;
	}


	public void setData(byte[] data) {
		this.data = data;
	}
	
	
	
	
	
}
