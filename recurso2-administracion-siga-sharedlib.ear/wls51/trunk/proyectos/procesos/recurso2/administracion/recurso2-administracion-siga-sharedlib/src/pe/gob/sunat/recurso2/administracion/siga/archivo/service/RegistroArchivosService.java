package pe.gob.sunat.recurso2.administracion.siga.archivo.service;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import pe.gob.sunat.framework.spring.util.exception.ServiceException;
import pe.gob.sunat.recurso2.administracion.siga.archivo.model.bean.RegistroArchivosFisicoBean;
import pe.gob.sunat.recurso2.administracion.siga.archivo.model.bean.TipoDocumentoBean;


/**
 * Interface RegistroArchivosService.
 * Servicio de Registro y consultas de Archivos del Siga
 * @author emarchena.
 */
public interface RegistroArchivosService {
	
	 
	 public String registrarArchivo(Map<String, Object> params);
	 public String registrarArchivoGeneral(Map<String, Object> params);
	 public String registrarArchivoSolicitud(Map<String, Object> params);
	 public RegistroArchivosFisicoBean recuperarArchivo(Map<String, Object> params);
	
	 public Collection listarArchivos(Map<String, Object> params);
	 public Collection<RegistroArchivosFisicoBean> listarArchivosAdjuntos(Map<String, Object> params) ;
	 public Collection listarTipoArchivos(Map<String, Object> params);
	 public Collection<TipoDocumentoBean> listarTipoDocumento(Map<String, Object> params);
	 public void eliminarArchivoFisico(Map<String, Object> params);
	 public void eliminarArchivoFisicoGeneral(Map<String, Object> params);
	 
	 public boolean validarCorreoAdjuntoMovilidad(String fuenteMovilidad, String numeroRegistro) throws ServiceException;
	 public boolean validarArchivoRegistrado(String aplicacion, String modulo, String fuente, String numeroRegistroArchivo) throws ServiceException;
	 
	 public String registrarArchivoFirmado(RegistroArchivosFisicoBean refArchivoFirmado) throws ServiceException;
	 public String registrarArchivoFirmado(RegistroArchivosFisicoBean refArchivoFirmado, String numeroRegistroArchivo) throws ServiceException;
	 public List<RegistroArchivosFisicoBean> listarArchivosFirmados(String numArchivo, String nomArchivo);
}

