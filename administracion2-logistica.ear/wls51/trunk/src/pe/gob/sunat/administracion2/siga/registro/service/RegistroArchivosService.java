package pe.gob.sunat.administracion2.siga.registro.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import pe.gob.sunat.administracion2.siga.registro.model.domain.RegistroArchivosFisicoBean;
import pe.gob.sunat.administracion2.siga.registro.model.domain.TdBeneficiarioBean;



public interface RegistroArchivosService {
	
	 public String registrarArchivo(Map<String, Object> params);
	 public String registrarArchivoSolicitud(Map<String, Object> params);
	 public RegistroArchivosFisicoBean recuperarArchivo(Map<String, Object> params);
	 public Collection listarArchivos(Map<String, Object> params);
	 public Collection listarTipoArchivos(Map<String, Object> params);
	 public String registrarArchivoFisico(Map<String, Object> params);
	 public String registrarBajaArchivo(Map<String, Object> params);
	 public void eliminarArchivoFisico(Map<String, Object> params);
	 
	 public String registrarTdBeneficiarios(List<TdBeneficiarioBean> params);
}

