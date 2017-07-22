package pe.gob.sunat.recurso2.administracion.siga.registro.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import pe.gob.sunat.recurso2.administracion.siga.parametro.model.bean.T01ParametroBean;
import pe.gob.sunat.recurso2.administracion.siga.ubigeo.model.bean.TDepartamentosBean;
import pe.gob.sunat.recurso2.administracion.siga.ubigeo.model.bean.TDistritosBean;
import pe.gob.sunat.recurso2.administracion.siga.ubigeo.model.bean.TProvinciasBean;

/**
 * Interface RegistroGeneralService.
 * Servicio consulta de consulta de diferentes Parametros y Ubigeo
 * @author emarchena.
 */
public interface RegistroGeneralService {
	
	///METODOS DE PARAMETROS 01
	public T01ParametroBean recuperarParametro(Map<String, Object> params);
	public Collection<T01ParametroBean> recuperarParametroLista(Map<String, Object> params);
	
	public Collection recuperarTipoArchivo(Map<String, Object> params);
	
	//METODOS DE ESTADOS
	public Collection listarEstados(Map<String, Object> params);
	
	//Metodo para obtener Ubigeo
	public ArrayList<TDepartamentosBean> obtenerDepartamentos() throws Exception;
	public ArrayList<TProvinciasBean> obtenerProvincias(String codigoDepartamento) throws Exception;
	public ArrayList<TDistritosBean> obtenerDistritos(String codigoDepartamento, String codigoProvincia) throws Exception;
	public boolean determinarAuditor(String codAuditor, String codigoDependencia) throws Exception;
	public ArrayList<T01ParametroBean> obtenerPorcentajeDesplazamiento() throws Exception;
	 public ArrayList<T01ParametroBean> obtenerPorcentajeDesplazamiento(String notificador) throws Exception;
	public Long obtenerUitAnio(String anio) ;
	
	
}
