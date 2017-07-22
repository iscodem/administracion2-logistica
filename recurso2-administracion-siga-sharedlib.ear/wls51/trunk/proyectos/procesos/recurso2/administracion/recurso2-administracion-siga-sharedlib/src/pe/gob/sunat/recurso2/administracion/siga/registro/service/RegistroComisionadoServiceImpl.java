package pe.gob.sunat.recurso2.administracion.siga.registro.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pe.gob.sunat.framework.spring.util.exception.ServiceException;
import pe.gob.sunat.recurso2.administracion.siga.parametro.model.bean.T01ParametroBean;
import pe.gob.sunat.recurso2.administracion.siga.parametro.model.dao.T01ParametroDAO;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.MaestroPersonalBean;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.dao.MaestroPersonalDAO;
import pe.gob.sunat.recurso2.administracion.siga.registro.util.ComisionadoConstantes;
import pe.gob.sunat.recurso2.administracion.siga.registro.util.ComisionadoUtil;

/**
 * Implementacion de la interface RegistroComisionadoService.
 * Servicio de registro de Comisionados Inactivos en la T01Parametros (Movilidad)
 * @author emarchena.
 */
public class RegistroComisionadoServiceImpl implements RegistroComisionadoService {

	private static final Log log = LogFactory.getLog(RegistroComisionadoServiceImpl.class);

	private T01ParametroDAO t01ParametroDao;
	private MaestroPersonalDAO  maestroPersonalDao;

	@Override
	public List<MaestroPersonalBean> buscarColaboradores(Map<String, Object> parmSearch) throws Exception {

		if (log.isDebugEnabled()) log.debug("Inicio - RegistroComisionadoServiceImpl.buscarColaboradores");

		try {

			List<MaestroPersonalBean> lista = (List<MaestroPersonalBean>) maestroPersonalDao.buscarMaestroPersonalComisionadoInactivo(parmSearch);

			if (CollectionUtils.isNotEmpty(lista)) {

				for (MaestroPersonalBean persona : lista) {

					persona.setNombre_completo(ComisionadoUtil.limpiarCaracteres(persona.getNombre_completo()));
					persona.setApellido_paterno(ComisionadoUtil.limpiarCaracteres(persona.getApellido_paterno()));
					persona.setApellido_materno(ComisionadoUtil.limpiarCaracteres(persona.getApellido_materno()));
					persona.setNombre(ComisionadoUtil.limpiarCaracteres(persona.getNombre()));
					persona.setDependencia(ComisionadoUtil.limpiarCaracteres(persona.getDependencia()));
				}

			}

			return lista;

		} catch (Exception ex) {

			log.error("Error en RegistroComisionadoServiceImpl.buscarColaboradores: " + ex.getMessage(), ex);

			throw new ServiceException(this, ex);

		} finally {

			if (log.isDebugEnabled()) log.debug("Fin - RegistroComisionadoServiceImpl.buscarColaboradores");

		}

	}

	@Override
	public void registrarComisionado(T01ParametroBean parametro) throws Exception {
		if (log.isDebugEnabled()) log.debug("Inicio - RegistroComisionadoServiceImpl.registrarComisionado");
		// asegurar parametros del comisionado alta/baja 
		try{
		parametro.setCod_parametro(ComisionadoConstantes.COD_PARAMETRO_FILTRO_3079);
		parametro.setCod_modulo(ComisionadoConstantes.COD_MODULO_FILTRO_SIGA);
		parametro.setCod_tipo(ComisionadoConstantes.COD_TIPO_FILTRO_DETALLE);		
		
		t01ParametroDao.registrarComisionado(parametro);
		}catch(Exception ex){
			log.error("Error en RegistroComisionadoServiceImpl.registrarComisionado: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);
		}finally{
			if (log.isDebugEnabled()) log.debug("Fin - RegistroComisionadoServiceImpl.registrarComisionado");
		}
	}

	@Override
	public void actualizarComisionado(T01ParametroBean parametro, String cod_argumento_nuevo) throws Exception {
		if (log.isDebugEnabled()) log.debug("Inicio - RegistroComisionadoServiceImpl.actualizarComisionado");
		Map<String, Object> params = new HashMap<String, Object>();

		try{
		// atributos set: nom_largo, nom_corto, cod_estado, desc_abrv, cod_argumento
		params.put("nom_largo", StringUtils.trimToNull(parametro.getNom_largo()));
		params.put("nom_corto", StringUtils.trimToNull(parametro.getNom_corto()));
		params.put("cod_estado", StringUtils.trimToNull(parametro.getCod_estado()));
		params.put("desc_abrv", StringUtils.trimToNull(parametro.getDesc_abrv()));
		
		// nuevo codigo de argumento ya que el que viene en parametro se usa para buscar
		params.put("cod_argumento_set", StringUtils.trimToNull(cod_argumento_nuevo)); 

		// atributos where: cod_parametro, cod_modulo, cod_tipo, cod_argumento
		params.put("cod_parametro", ComisionadoConstantes.COD_PARAMETRO_FILTRO_3079);
		params.put("cod_modulo", ComisionadoConstantes.COD_MODULO_FILTRO_SIGA);
		params.put("cod_tipo", ComisionadoConstantes.COD_TIPO_FILTRO_DETALLE);
		params.put("cod_argumento", StringUtils.trimToNull(parametro.getCod_argumento()));

		t01ParametroDao.actualizarComisionado(params);
		}catch(Exception ex){
			log.error("Error en RegistroComisionadoServiceImpl.actualizarComisionado: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);
		}finally{
			if (log.isDebugEnabled()) log.debug("Fin - RegistroComisionadoServiceImpl.actualizarComisionado");
		}
	}

	@Override
	public T01ParametroBean buscarComisionado(String uuooColaborador, String nroRegistroColaborador, String nroRegistroRegistrador) throws Exception {
		T01ParametroBean t01ParametroBean = new T01ParametroBean(); 
		try{
			t01ParametroBean = t01ParametroDao.buscarComisionado(uuooColaborador, nroRegistroColaborador, nroRegistroRegistrador);
		}catch(Exception ex){
			log.error("Error en RegistroComisionadoServiceImpl.buscarComisionado: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);
		}finally{
			if (log.isDebugEnabled()) log.debug("Fin - RegistroComisionadoServiceImpl.buscarComisionado");
		}
			return t01ParametroBean;
	}

	public MaestroPersonalDAO getMaestroPersonalDao() {
		return maestroPersonalDao;
	}

	public void setMaestroPersonalDao(MaestroPersonalDAO maestroPersonalDao) {
		this.maestroPersonalDao = maestroPersonalDao;
	}

	public T01ParametroDAO getT01ParametroDao() {
		return t01ParametroDao;
	}

	public void setT01ParametroDao(T01ParametroDAO t01ParametroDao) {
		this.t01ParametroDao = t01ParametroDao;
	}

}
