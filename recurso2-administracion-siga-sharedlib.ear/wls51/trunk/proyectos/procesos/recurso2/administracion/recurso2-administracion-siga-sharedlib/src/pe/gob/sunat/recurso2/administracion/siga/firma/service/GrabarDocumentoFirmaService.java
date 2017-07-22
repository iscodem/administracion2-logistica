package pe.gob.sunat.recurso2.administracion.siga.firma.service;

import java.util.Date;

public interface GrabarDocumentoFirmaService {

	int grabarDocumentoFirmado(String tipoOperacion, Long codDocaut,
			String codEmpleado, String codTipdoc, String numDocumento,
			String codDependencia, String codDependenciaNormativa, String codToken,
			String nomModulo, String nomProceso, String nomArchivo, Long numid, Date fechaValidacionLdap, 
			String codUsuregis, String dirIpusuregis, String codUsumodif,
			String dirIpusumodif) throws Exception;
	
	Long obtenerSecuencialDocumento() throws Exception;
	
	int anularDocumento(Long codDocaut) throws Exception;
	
	//Metodo creado para la firma SIGA
	int grabarDocumentoFirmadoSiga(String tipoOperacion, Long codDocaut,
			String codEmpleado, String codTipdoc, String numDocumento,
			String codDependencia, String codDependenciaNormativa, String codToken,
			String nomModulo, String nomProceso, String nomArchivo, Long numid, Date fechaValidacionLdap, 
			String codUsuregis, String dirIpusuregis, String codUsumodif,
			String dirIpusumodif,byte[] byData) throws Exception;
}
