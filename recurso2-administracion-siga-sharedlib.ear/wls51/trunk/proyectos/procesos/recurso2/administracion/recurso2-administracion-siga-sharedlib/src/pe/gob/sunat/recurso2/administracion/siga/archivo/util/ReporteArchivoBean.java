package pe.gob.sunat.recurso2.administracion.siga.archivo.util;

public class ReporteArchivoBean {

	private String fieldName;
	private String fieldExtension;
	private String fieldNamePath;
	private Long size;
	private byte[] data;
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldExtension() {
		return fieldExtension;
	}
	public void setFieldExtension(String fieldExtension) {
		this.fieldExtension = fieldExtension;
	}
	public String getFieldNamePath() {
		return fieldNamePath;
	}
	public void setFieldNamePath(String fieldNamePath) {
		this.fieldNamePath = fieldNamePath;
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
