package pe.gob.sunat.recurso2.administracion.siga.firma.service;

import java.math.BigDecimal;
import java.util.List;

import pe.gob.sunat.recurso2.administracion.siga.firma.model.bean.MaestroPersonal;
import pe.gob.sunat.recurso2.administracion.siga.firma.model.bean.T5282Archbin;
import pe.gob.sunat.recurso2.administracion.siga.firma.model.bean.T7073FirmaElectron;
import pe.gob.sunat.recurso2.administracion.siga.firma.model.bean.T7074DocumentoFirm;
import pe.gob.sunat.recurso2.administracion.siga.firma.model.bean.T7075TerminoCond;
import pe.gob.sunat.recurso2.administracion.siga.firma.model.bean.T7076DelegacFirma;

public interface ConsultaFirmaService {

	public T7073FirmaElectron recuperarFirmaActiva(String codEmpleado) throws Exception;

	public T5282Archbin recuperarDocumentoPdfFirmado(Long numPDF) throws Exception;
	
	public List<T7074DocumentoFirm> recuperarDocumentosFirmados(T7074DocumentoFirm param) throws Exception;
	
	public List<MaestroPersonal> recuperarFirmantesDocumentoVigente(String codTipdoc, String numDocumento) throws Exception;

	public Long recuperarIdDocumentoFirmadoVigente(String codTipdoc, String numDocumento) throws Exception;
	
	public List<T7076DelegacFirma> recuperarListaEncargaturaDependencia(String codDependencia) throws Exception;

	public T5282Archbin recuperarDocumentoPdfFirmadoVigente(String codTipdoc, String numDocumento) throws Exception;

	public List<T7073FirmaElectron> recuperarFirmas(String codEmpleado) throws Exception;

	public MaestroPersonal recuperarDatosPersona(MaestroPersonal param) throws Exception;

	public List<T7075TerminoCond> recuperarTerminosYCondiciones(String codTipo) throws Exception;
	
	public T7075TerminoCond recuperarTerminosYCondicionesVigente(String codTipo) throws Exception;

	public String recuperarDescripcionParametro(String codPar, String codArg, String codTipo, String codMod, boolean larga) throws Exception;

	public Long recuperarIdDocumentoFirmado(BigDecimal codDocaut) throws Exception;

	public T5282Archbin recuperarDocumentoPdfFirmado(BigDecimal codDocaut) throws Exception;

	public T7074DocumentoFirm recuperarDatosDocumentoFirmadoVigente(String codTipdoc, String numDocumento) throws Exception;

	public T7074DocumentoFirm recuperarDatosDocumentoFirmado(BigDecimal codDocaut) throws Exception;
}
