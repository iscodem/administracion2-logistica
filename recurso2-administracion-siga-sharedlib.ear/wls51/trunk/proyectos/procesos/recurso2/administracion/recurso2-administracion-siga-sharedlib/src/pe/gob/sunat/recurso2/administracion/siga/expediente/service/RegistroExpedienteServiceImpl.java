package pe.gob.sunat.recurso2.administracion.siga.expediente.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import pe.gob.sunat.framework.spring.util.exception.ServiceException;
import pe.gob.sunat.recurso2.administracion.siga.expediente.model.bean.ExpedienteAccionBean;
import pe.gob.sunat.recurso2.administracion.siga.expediente.model.bean.ExpedienteInternoAccionBean;
import pe.gob.sunat.recurso2.administracion.siga.expediente.model.bean.ExpedienteProcesoBean;
import pe.gob.sunat.recurso2.administracion.siga.expediente.model.dao.ExpedienteInternoAccionDAO;
import pe.gob.sunat.recurso2.administracion.siga.expediente.model.dao.PaExpedienteDAO;
import pe.gob.sunat.recurso2.administracion.siga.util.FechaUtil;


/**
 * Implementacion de la interface RegistroExpedienteService.
 * Servicio de Registro y consultas de expedientes(Seguimiento de procesos) del Siga
 * @author emarchena.
 */
public class RegistroExpedienteServiceImpl  implements RegistroExpedienteService{
	private static final Log log = LogFactory
			.getLog(RegistroExpedienteServiceImpl.class);
	
	PaExpedienteDAO paExpedienteDAO;
	ExpedienteInternoAccionDAO expedienteInternoAccionDao;
	
	public PaExpedienteDAO getPaExpedienteDAO() {
		return paExpedienteDAO;
	}

	public void setPaExpedienteDAO(PaExpedienteDAO paExpedienteDAO) {
		this.paExpedienteDAO = paExpedienteDAO;
	}

		
	public ExpedienteInternoAccionDAO getExpedienteInternoAccionDao() {
		return expedienteInternoAccionDao;
	}

	public void setExpedienteInternoAccionDao(
			ExpedienteInternoAccionDAO expedienteInternoAccionDao) {
		this.expedienteInternoAccionDao = expedienteInternoAccionDao;
	}

	@Override
	public String crearExpediente(ExpedienteProcesoBean proceso)
			throws ServiceException {
		log.debug("Inicio - RegistroExpedienteServiceImpl.crearExpedienteMovilidad");
		String codigoExpediente="";
		try{
			
		Map<String, Object> parm = new HashMap<String, Object>();
		parm.put("anio_pro", proceso.getAnioProceso());
		parm.put("cod_sede", proceso.getCodigoSede());
		parm.put("cod_responsable", proceso.getCodigoResponsable());
		parm.put("cod_proceso", proceso.getCodigoProceso());
		parm.put("cod_doc", proceso.getCodigoDocumentoOrigen());
		
		
			codigoExpediente=paExpedienteDAO.crearExpediente(parm);
		} catch (Exception ex) {
			   log.error("Error en RegistroExpedienteServiceImpl.crearExpedienteMovilidad: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
		}
		finally {
			 log.debug("Fin - RegistroExpedienteServiceImpl.crearExpedienteMovilidad");
		}
		return codigoExpediente;
	}

	@Override
	public void crearAccion(ExpedienteAccionBean accion) throws ServiceException {
		log.debug("Inicio - RegistroExpedienteServiceImpl.crearAccion");

		Map<String, Object> parm = new HashMap<String, Object>();
		
		try{

			parm.put("cod_sede", accion.getCodigoSede());
			parm.put("cod_responsable", accion.getCodigoResponsable());
			parm.put("cod_proceso", accion.getCodigoProceso());
			parm.put("cod_accion", accion.getCodigoAccion());
			parm.put("cod_exp", accion.getCodigoExpedienteOrigen());
			parm.put("exp_estado", accion.getExpedienteEstado());
			parm.put("exp_obs", accion.getExpedienteObservacion());
			
			paExpedienteDAO.crearAccion(parm);
		} catch (Exception ex) {
			   log.error("Error en RegistroExpedienteServiceImpl.crearAccion: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
		}
		finally {
			 log.debug("Fin - RegistroExpedienteServiceImpl.crearAccion");
		}
	}

	@Override
	public Collection<ExpedienteInternoAccionBean> listaSecuenciaExpediente(
			String num_expediente) throws Exception {
		
		if (log.isDebugEnabled()) log.debug("Inicio - MovilidadLocalExpedienteServiceImpl listaSecuenciaExpediente");
		 Collection<ExpedienteInternoAccionBean> listSecExpediente =  new ArrayList<ExpedienteInternoAccionBean>();
		 	
		 if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.listarAccionesBien");
		Map<String, Object> map;

		char ini=' ' ;
		char ini2= "'".charAt(0) ;
		char salto =(char)10;
		char retorno =(char)13;
		char espacio =(char)32;
		CharSequence comSimple="'";
		CharSequence comSimpleSalva="&#39";
		
		CharSequence comaSimple=",";
		CharSequence comaSimpleSalva=";";
	
		
		int i = 1;
			
		try {

			List<ExpedienteInternoAccionBean> lista =  (ArrayList<ExpedienteInternoAccionBean>) expedienteInternoAccionDao.obtenerlistaAcciones(num_expediente) ;
			
			for(ExpedienteInternoAccionBean data: lista){				
				ExpedienteInternoAccionBean secuenciaExpedienteBean =  new ExpedienteInternoAccionBean();
				secuenciaExpedienteBean.setCod_accion(data.getCod_accion());
				secuenciaExpedienteBean.setDes_accion(data.getDes_accion()==null ? "": data.getDes_accion().replace( '"',ini).replace('/', ini).replace('°', ini).replace(ini2, ini).trim().replace(comaSimple, comaSimpleSalva).replace(comSimple, comSimpleSalva). replace(salto, espacio).replace(retorno, espacio).trim());
				secuenciaExpedienteBean.setCod_emp(data.getCod_emp());
				secuenciaExpedienteBean.setEstado(data.getEstado());
				secuenciaExpedienteBean.setNom_estado(data.getNom_estado());
				secuenciaExpedienteBean.setNom_emp(data.getNom_emp() );
				secuenciaExpedienteBean.setNum_exp(data.getNum_exp());
				secuenciaExpedienteBean.setFechaInicioFormat(FechaUtil.formatDateToDateDDMMYYYYHHMM(data.getFecha_ini()));
				secuenciaExpedienteBean.setFechaFinFormat(FechaUtil.formatDateToDateDDMMYYYYHHMM(data.getFecha_ter()));
				secuenciaExpedienteBean.setObservacion(data.getObservacion()==null ? "": data.getObservacion().replace(comaSimple, comaSimpleSalva).replace(comSimple, comSimpleSalva). replace(salto, espacio).replace(retorno, espacio).trim());
				secuenciaExpedienteBean.setSec_accion(data.getSec_accion());
				secuenciaExpedienteBean.setNumItem(i);			
				i++;

				listSecExpediente.add(secuenciaExpedienteBean);
			}			

			return listSecExpediente ;
			
		} 
		catch (Exception ex) {
			   log.error("Error en RequerimientoNoProgramadoServiceImpl.listarAccionesBien: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
		}
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.listarAccionesBien");
		}
		
	}
	

	
	//CUANDO INICIA
			/*parm.put("cod_accion", "001");
			parm.put("cod_exp", cod_exp);
			parm.put("exp_estado", "001");
			parm.put("exp_obs", "");*/
			
			///CUANDO RECHAZA JEFI
			/*parm.put("anio_pro", (String) params.get ("anio_pro"));
			parm.put("cod_sede", (String) params.get ("cod_sede"));
			parm.put("cod_responsable", cod_aprueba);
			parm.put("cod_proceso", "249");
			parm.put("cod_accion", "003");
			parm.put("cod_exp", bien.getNum_exp());
			parm.put("exp_estado", "003");
			parm.put("exp_obs", "REQUERIMIENTO RECHAZADO JEFE UUOO SUPERIOR");*/
			
			///
			/*
			parm.put("anio_pro", rqnp.getAnioProceso());
			parm.put("cod_sede", rqnp.getCodigoSede());
			parm.put("cod_responsable", rqnp.getCodigoResposanble());
			parm.put("cod_proceso", "249");
			parm.put("cod_accion", "002");
			parm.put("exp_obs", "REQUERIMIENTO ENVIADO AL J. INMEDIATO DE "+jefe.getUuoo()+ " - " + jefe.getDependencia() + "  PARA SU APROBACIÓN");
			parm.put("cod_exp", obj.get("num_exp"));
			parm.put("exp_estado", "001");*/
}
