package pe.gob.sunat.recurso2.administracion.siga.firma.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import pe.gob.sunat.recurso2.administracion.siga.firma.model.bean.MaestroPersonal;
import pe.gob.sunat.recurso2.administracion.siga.firma.model.bean.T5282Archbin;
import pe.gob.sunat.recurso2.administracion.siga.firma.model.bean.T7073FirmaElectron;
import pe.gob.sunat.recurso2.administracion.siga.firma.model.bean.T7074DocumentoFirm;
import pe.gob.sunat.recurso2.administracion.siga.firma.model.bean.T7076DelegacFirma;

public interface ValidarFirmarService {
	
	public Map<String, Object> validarFirmaActiva(String codEmpleado) throws Exception;
		
	public Map<String, Object> validarSiEsJefeDependencia(String codEmpleado, String codDependencia) throws Exception;
		
	public Map<String, Object> validarEncargaturaPorDependencia(String codEmpleadoResponsable, String codDependencia, Date today) throws Exception;
	
	public Map<String, Object> validarEncagadoPorDependencia(String codEmpleadoDelegado, String codDependencia, Date today) throws Exception;
	
	public Map<String, Object> validarAutorizadoFirma(String codEmpleado, String codDependencia, boolean validacionCompleta) throws Exception;
	
	public Long generarDocumentoPdfFirmado(int iddoc, Map<String, Object> cabecera, List<Map<String, Object>> detalles) throws Exception;
	
	public Long generarDocumentoPdfFirmado(int iddoc, String nombreDocumento, Map<String, Object> cabecera, List<Map<String, Object>> detalles) throws Exception;

	public Map<String, Object> generarGrabarDocumentoFirmado(int iddoc, String nomArchivo,
			Map<String, Object> cabecera, List<Map<String, Object>> detalles,
			String tipoOperacion, String codEmpleado, String codTipdoc,
			String numDocumento, String codDependencia,
			String codDependenciaNormativa, String codToken, String nomModulo,
			String nomProceso, String codUsuregis, String dirIpusuregis, String codUsumodif, String dirIpusumodif) throws Exception;

	
	public Map<String, Object> validarLdapGenerarGrabarDocumentoFirmado(int iddoc, String nomArchivo,
			Map<String, Object> cabecera, List<Map<String, Object>> detalles,
			String user, String pass,
			String tipoOperacion, String codEmpleado, String codTipdoc,
			String numDocumento, String codDependencia,
			String codDependenciaNormativa, String nomModulo,
			String nomProceso, String codUsuregis, String dirIpusuregis,
			String codUsumodif, String dirIpusumodif) throws Exception;	
	
	//Pase Firma SIGA
	public Map<String, Object> generarDocumentoPdfFirmadoSiga(int iddoc, Map<String, Object> cabecera, List<Map<String, Object>> detalles) throws Exception;
	public Map<String, Object> generarDocumentoPdfFirmadoSiga(int iddoc, String nombreDocumento, Map<String, Object> cabecera, List<Map<String, Object>> detalles) throws Exception;
}
