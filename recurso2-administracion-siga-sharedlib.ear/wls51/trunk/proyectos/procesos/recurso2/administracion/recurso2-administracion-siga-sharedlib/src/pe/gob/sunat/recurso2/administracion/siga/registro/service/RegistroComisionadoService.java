package pe.gob.sunat.recurso2.administracion.siga.registro.service;

import java.util.List;
import java.util.Map;

import pe.gob.sunat.recurso2.administracion.siga.parametro.model.bean.T01ParametroBean;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.MaestroPersonalBean;


/**
 * Interface RegistroComisionadoService.
 * Servicio de registro de Comisionados Inactivos en la T01Parametros (Movilidad)
 * @author emarchena.
 */
public interface RegistroComisionadoService {

	List<MaestroPersonalBean> buscarColaboradores(Map<String, Object> parmSearch) throws Exception;

	void registrarComisionado(T01ParametroBean parametro) throws Exception;
	
	void actualizarComisionado(T01ParametroBean parametro, String cod_argumento_nuevo) throws Exception;
	
	T01ParametroBean buscarComisionado(String uuooColaborador, String nroRegistroColaborador, String nroRegistroRegistrador) throws Exception;
}
