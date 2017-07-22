package pe.gob.sunat.recurso2.administracion.siga.registro.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import pe.gob.sunat.recurso2.administracion.siga.parametro.model.bean.T01ParametroBean;
import pe.gob.sunat.recurso2.administracion.siga.parametro.model.dao.SysEstadosDAO;
import pe.gob.sunat.recurso2.administracion.siga.parametro.model.dao.SysParametrosDAO;
import pe.gob.sunat.recurso2.administracion.siga.parametro.model.dao.T01ParametroDAO;
import pe.gob.sunat.recurso2.administracion.siga.registro.util.ParametroConstantes;
import pe.gob.sunat.recurso2.administracion.siga.ubigeo.model.bean.TDepartamentosBean;
import pe.gob.sunat.recurso2.administracion.siga.ubigeo.model.bean.TDistritosBean;
import pe.gob.sunat.recurso2.administracion.siga.ubigeo.model.bean.TProvinciasBean;
import pe.gob.sunat.recurso2.administracion.siga.ubigeo.model.dao.TDepartamentosDAO;
import pe.gob.sunat.recurso2.administracion.siga.ubigeo.model.dao.TDistritosDAO;
import pe.gob.sunat.recurso2.administracion.siga.ubigeo.model.dao.TProvinciasDAO;


/**
 * Implementacion de la interface RegistroGeneralService.
 * Servicio consulta de consulta de diferentes Parametros y Ubigeo
 * @author emarchena.
 */

public class RegistroGeneralServiceImpl implements RegistroGeneralService {

	T01ParametroDAO t01ParametroDao;
	SysEstadosDAO sysEstadosDao;
	TDepartamentosDAO tDepartamentosDao;
	TProvinciasDAO tProvinciasDao;
	TDistritosDAO tDistritosDao;
	SysParametrosDAO sysParametrosDao;
	private static final Log log = LogFactory
			.getLog(RegistroGeneralServiceImpl.class);

	public T01ParametroDAO getT01ParametroDao() {
		return t01ParametroDao;
	}

	public void setT01ParametroDao(T01ParametroDAO t01ParametroDao) {
		this.t01ParametroDao = t01ParametroDao;
	}

	public SysEstadosDAO getSysEstadosDao() {
		return sysEstadosDao;
	}

	public void setSysEstadosDao(SysEstadosDAO sysEstadosDao) {
		this.sysEstadosDao = sysEstadosDao;
	}

	public TDepartamentosDAO gettDepartamentosDao() {
		return tDepartamentosDao;
	}

	public void settDepartamentosDao(TDepartamentosDAO tDepartamentosDao) {
		this.tDepartamentosDao = tDepartamentosDao;
	}

	public TProvinciasDAO gettProvinciasDao() {
		return tProvinciasDao;
	}

	public void settProvinciasDao(TProvinciasDAO tProvinciasDao) {
		this.tProvinciasDao = tProvinciasDao;
	}

	public TDistritosDAO gettDistritosDao() {
		return tDistritosDao;
	}

	public void settDistritosDao(TDistritosDAO tDistritosDao) {
		this.tDistritosDao = tDistritosDao;
	}
	
	
	
	
	

	public SysParametrosDAO getSysParametrosDao() {
		return sysParametrosDao;
	}

	public void setSysParametrosDao(SysParametrosDAO sysParametrosDao) {
		this.sysParametrosDao = sysParametrosDao;
	}

	@Override
	public T01ParametroBean recuperarParametro(Map<String, Object> params) {
		return t01ParametroDao.recuperarParametro(params);
	}

	@Override
	public Collection<T01ParametroBean> recuperarParametroLista(Map<String, Object> params) {
		return t01ParametroDao.recuperarParametroLista(params);
	}

	@Override
	public Collection recuperarTipoArchivo(Map<String, Object> params) {
		return t01ParametroDao.recuperarTipoArchivo(params);
	}

	@Override
	public Collection listarEstados(Map<String, Object> params) {
		return sysEstadosDao.listarEstados(params);
	}

	public ArrayList<TDepartamentosBean> obtenerDepartamentos() throws Exception{
		return tDepartamentosDao.obtenerDepartamentos();
	}

	public ArrayList<TProvinciasBean> obtenerProvincias(
			String codigoDepartamento) throws Exception {
		return tProvinciasDao.obtenerProvincias(codigoDepartamento);
	}

	public ArrayList<TDistritosBean> obtenerDistritos(
			String codigoDepartamento, String codigoProvincia) throws Exception {
		return tDistritosDao.obtenerDistritos(codigoDepartamento, codigoProvincia);
	}

	@Override
	public boolean determinarAuditor(String codAuditor, String codigoDependencia) 
			throws Exception {
		Map<String, Object> parm = new HashMap<String, Object>();
		parm.put("cod_par",ParametroConstantes.CODIGO_PARAMETRO_DETERMINA_AUDITOR);
		parm.put("cod_mod",ParametroConstantes.CODIGO_MODULO_SIGA);
		parm.put("cod_tipo",ParametroConstantes.DETALLE_PARAMETRO);
		parm.put("cod_arg",codigoDependencia);
		parm.put("cod_emp",codAuditor);
		return t01ParametroDao.determinaAuditor(parm);
	}

	@Override
	public ArrayList<T01ParametroBean> obtenerPorcentajeDesplazamiento()
			throws Exception {
		return t01ParametroDao.obtenerPorcentajeDesplazamiento();
	}
	
	public ArrayList<T01ParametroBean> obtenerPorcentajeDesplazamiento(String notificador)
			throws Exception {
		return t01ParametroDao.obtenerPorcentajeDesplazamiento(notificador);
	}
	public Long obtenerUitAnio(String anio) {
		Map<String, Object> parm = new HashMap<String, Object>();
		parm.put("nom_var",ParametroConstantes.NOMBRE_VARIABLE_PARAMETRO_UIT);
		parm.put("peri_anno",anio);
		return sysParametrosDao.obtieneUitAnio(parm);
		
	}
}
