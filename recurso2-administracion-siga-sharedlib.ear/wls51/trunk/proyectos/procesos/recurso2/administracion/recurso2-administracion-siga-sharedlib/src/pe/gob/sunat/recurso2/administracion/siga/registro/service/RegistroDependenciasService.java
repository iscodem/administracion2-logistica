package pe.gob.sunat.recurso2.administracion.siga.registro.service;

import java.util.Collection;
import java.util.Map;

import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.DependenciaBean;


/**
 * Interface RegistroDependenciasService.
 * Servicio consulta de Dependencias
 * @author emarchena.
 */
public interface RegistroDependenciasService {
	 public Collection<DependenciaBean> searchDependencias(Map<String, Object> parmSearch);
	 public Collection<DependenciaBean> searchDependenciasMovilidad(Map<String, Object> parmSearch);
	 public DependenciaBean getDependencias(String uuuoo);
	 /*Viaticos*/
	 public Collection<DependenciaBean> searchDependenciasViatico(Map<String, Object> parmSearch);	
	 public DependenciaBean obtenerDependenciaXcod(String codigoDependencia);
	 public Collection<DependenciaBean> buscarIntendenciaByUnidadOrganizacional(String num_uuoo);
	 public Collection<DependenciaBean> obtenerAllViaticoIndividual(Map<String, Object> parmSearch);
	 
	 
	 public DependenciaBean getDependencia(String codDependencia);
	 
	 /**
		 * Metodo que permite obtener la UUOO Aprobadora.
		 * @author emarchena.
		 * @param  codigoDependencia :codigo de dependencia Solicitante(DDJJ o Reembolso).
		 * @param  codigoColaborador :codigo de colaborador Solicitante.
		 * @param  codigoProceso :codigo de proceso(M:moilidad , V: viaticos).
		 * @return String codigo uuoo Aprobadora.
		 * @see    TlugarMoviLocalBean
		 * @throws Exception
		 */
		public String obtenerUUOOAprobador(String codigoDependencia, String codigoColaborador,String codigoProceso ) throws Exception;
		

		/**
		 * Metodo que permite obtener la UUOO Autorizadora.
		 * @author emarchena.
		 * @param  codigoDependencia :codigo de dependencia Solicitante(DDJJ o Reembolso).
		 * @param  codigoColaborador :codigo de colaborador Solicitante.
		 * @param  codigoProceso :codigo de proceso(M:moilidad , V: viaticos).
		 * @return String codigo uuoo Aprobadora.
		 * @see    TlugarMoviLocalBean
		 * @throws Exception
		 */
		public String obtenerUUOOAutorizador(String codigoDependencia, String codigoColaborador,String codigoProceso) throws Exception;
		
		
		/**
		 * Metodo que permite revaluar la UUOO Aprobadora de un delegado.
		 * @author emarchena.
		 * @param  codigoAprobador :codigo de Aprobador.
		 * @return String codigo de resultado (00:exito)
		 * @see    TlugarMoviLocalBean
		 * @throws Exception
		 */
		public String revaluaUUOOAprueba( String codigoAprobador ) throws Exception;
		
		/**
		 * Metodo que permite revaluar la UUOO Autorizador de un delegado.
		 * @author emarchena.
		 * @param  codigoAutorizador :codigo de Aprobador.
		 * @return String codigo de resultado (00:exito)
		 * @see    TlugarMoviLocalBean
		 * @throws Exception
		 */
		public String revaluaUUOOAutoriza( String codigoAutorizador) throws Exception;
}
