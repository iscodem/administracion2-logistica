package pe.gob.sunat.administracion2.siga.rqnp.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jndi.JndiTemplate;

import pe.gob.sunat.administracion2.siga.rqnp.model.dao.CatalogoBienesDao;
import pe.gob.sunat.administracion2.siga.rqnp.model.dao.MetasProyectosDao;
import pe.gob.sunat.administracion2.siga.rqnp.model.dao.RequerimientoNoProgBienesDao;
import pe.gob.sunat.administracion2.siga.rqnp.model.dao.RequerimientoNoProgMetasDao;
import pe.gob.sunat.administracion2.siga.rqnp.model.dao.RequerimientoNoProgramadoDao;
import pe.gob.sunat.administracion2.siga.rqnp.model.dao.RqnpBandejaDao;
import pe.gob.sunat.administracion2.siga.rqnp.model.domain.CatalogoBienesBean;
import pe.gob.sunat.administracion2.siga.rqnp.model.domain.MetasProyectosBean;
import pe.gob.sunat.administracion2.siga.rqnp.model.domain.RequerimientoNoProgBienesBean;
import pe.gob.sunat.administracion2.siga.rqnp.model.domain.RequerimientoNoProgMetasBean;
import pe.gob.sunat.administracion2.siga.rqnp.model.domain.RequerimientoNoProgramadoBean;
import pe.gob.sunat.administracion2.siga.rqnp.model.domain.RqnpBandejaBean;
import pe.gob.sunat.administracion2.siga.rqnp.util.RqnpUtil;
import pe.gob.sunat.framework.spring.util.exception.ServiceException;
import pe.gob.sunat.recurso2.administracion.siga.expediente.model.bean.ExpedienteInternoAccionBean;
import pe.gob.sunat.recurso2.administracion.siga.expediente.model.dao.ExpedienteInternoAccionDAO;
import pe.gob.sunat.recurso2.administracion.siga.parametro.model.bean.ObjetivosBean;
import pe.gob.sunat.recurso2.administracion.siga.parametro.model.bean.SysEstadosBean;
import pe.gob.sunat.recurso2.administracion.siga.parametro.model.bean.T01ParametroBean;
import pe.gob.sunat.recurso2.administracion.siga.parametro.model.dao.ObjetivosDAO;
import pe.gob.sunat.recurso2.administracion.siga.parametro.model.dao.SysEstadosDAO;
import pe.gob.sunat.recurso2.administracion.siga.parametro.model.dao.SysParametrosDAO;
import pe.gob.sunat.recurso2.administracion.siga.parametro.model.dao.T01ParametroDAO;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.DependenciaBean;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.MaestroPersonalBean;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.dao.AccesoDAO;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.dao.DependenciaDAO;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.dao.MaestroContratistasDAO;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.dao.MaestroPersonalDAO;
import pe.gob.sunat.recurso2.administracion.siga.registro.service.RegistroPersonalService;
import pe.gob.sunat.recurso2.administracion.siga.registro.util.RegistroUtil;
import pe.gob.sunat.recurso2.administracion.siga.util.FechaUtil;

/**
 * <p>Title: RequerimientoNoProgramadoServiceImpl </p>
 * <p>Description: Clase de Servicios para los Requerimientos No Programados</p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: SUNAT</p>
 * @author EMARCHENA
 * @version 1.0 
 */
public class RequerimientoNoProgramadoServiceImpl  implements RequerimientoNoProgramadoService  {
	
	private static final Log log = LogFactory.getLog(RequerimientoNoProgramadoServiceImpl.class);
	
	RequerimientoNoProgramadoDao 	requerimientoNoProgramadoDao;
	CatalogoBienesDao				catalogoBienesDao;
	RequerimientoNoProgBienesDao	requerimientoNoProgBienesDao;
	RequerimientoNoProgMetasDao		requerimientoNoProgMetasDao;
	AccesoDAO						accesoDao;
	MaestroPersonalDAO				maestroPersonalDao;
	DependenciaDAO					dependenciaDao;
	RqnpBandejaDao					rqnpBandejaDao;
	SysEstadosDAO					sysEstadosDao;
	ExpedienteInternoAccionDAO		expedienteInternoAccionDao;
	SysParametrosDAO				sysParametrosDao;
	ObjetivosDAO					objetivosDao;
	MaestroContratistasDAO			maestroContratistasDao;
	RegistroPersonalService         registroPersonalService;
	MetasProyectosDao				metasProyectosDao;
	T01ParametroDAO					t01ParametroDao;
	/**
     * Lista los Requerimientos No Programados de un Responsable
     * @param String ano_pro - Año de Periodo
     * @param String est_req - Estado del Requerimiento
     * @param String cod_sed - Código de Sede
     * @param String cod_res - Código de Responsable del Requerimiento
     * @param String mes_pro - Mes de Periodo
     * @return Collection - Lista de Requerimientos
     */
	@Override
	public Collection listarRqnpUsuario(String ano_pro, String est_req,
			String cod_sed, String cod_res, String mes_pro,String ind_registropor) {
		if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.listarRqnpUsuario");
		List<Map<String, Object>> lsRqnp = new ArrayList<Map<String,Object>>();
		
		char salto =(char)10;
		char retorno =(char)13;
		char espacio =(char)32;
		
		CharSequence comi ="&quot";
		CharSequence comis ="\"";
		CharSequence comSimple="'";
		CharSequence comSimpleSalva="&#39";
		
		CharSequence comaSimple=",";
		CharSequence comaSimpleSalva=";";
		
		Map<String, Object> requerimientoNoProgramado;
		try {
			List<RequerimientoNoProgramadoBean> lista = (List<RequerimientoNoProgramadoBean>) requerimientoNoProgramadoDao.listarRqnpUsuario(ano_pro, est_req, null, cod_res, mes_pro,ind_registropor);
			
			for(RequerimientoNoProgramadoBean data: lista){
				requerimientoNoProgramado = new HashMap<String, Object>();

				requerimientoNoProgramado.put("codigoRqnp", data.getCodigoRqnp());
				requerimientoNoProgramado.put("anioProceso", data.getAnioProceso());				
				requerimientoNoProgramado.put("mesProeso", data.getMesProeso());
				requerimientoNoProgramado.put("codigoSede", data.getCodigoSede());
				requerimientoNoProgramado.put("fechaRqnp",new SimpleDateFormat("dd/MM/yyyy HH:mm").format(data.getFechaRqnp()) );
				requerimientoNoProgramado.put("motivoSolicitud", data.getMotivoSolicitud().replace(comis, comi).replace(comaSimple, comaSimpleSalva).replace(comSimple, comSimpleSalva).replace(salto, espacio).replace(retorno, espacio) );
				requerimientoNoProgramado.put("estadoRqnp", data.getEstadoRqnp());
				//requerimientoNoProgramado.put("montoRqnp", data.getMontoRqnp().toString());
				requerimientoNoProgramado.put("codigoDependencia", data.getCodigoDependencia());
				requerimientoNoProgramado.put("tipoMoneda", data.getTipoMoneda());
				requerimientoNoProgramado.put("codigoResposanble", data.getCodigoResposanble());
			//	requerimientoNoProgramado.put("fechaEnvioSolicitud", fechaEnvioSolicitud.getFormatDate("dd/MM/yyyy"));
				requerimientoNoProgramado.put("especificaAsig", data.getEspecificaAsig());
				//requerimientoNoProgramado.put("fechaAsig", fechaRqnp.getFormatDate("dd/MM/yyyy"));
				requerimientoNoProgramado.put("numeroExpediente", data.getNumeroExpediente());
				requerimientoNoProgramado.put("numeroArchivo", data.getNumeroArchivo());
				requerimientoNoProgramado.put("codigoReqNoProgramado", data.getCodigoReqNoProgramado());
				requerimientoNoProgramado.put("nombreSolicitante", data.getNombreSolicitante().replace(comis, comi).replace(comaSimple, comaSimpleSalva).replace(comSimple, comSimpleSalva).replace(salto, espacio).replace(retorno, espacio));
				requerimientoNoProgramado.put("nombreEstado", data.getNombreEstado());
				requerimientoNoProgramado.put("ind_solicitud", data.getInd_solicitud());
				requerimientoNoProgramado.put("anio_atencion", data.getAnio_atencion());
				requerimientoNoProgramado.put("mes_atencion", data.getMes_atencion());
				requerimientoNoProgramado.put("tipo_vinculo", data.getTipo_vinculo());
				requerimientoNoProgramado.put("vinculo", data.getVinculo());
				lsRqnp.add(requerimientoNoProgramado);
			}
			return lsRqnp;
		} 
		catch (Exception ex) {
			   log.error("Error en RequerimientoNoProgramadoServiceImpl.listarRqnpUsuario: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
		}
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.listarRqnpUsuario");
		}
		
	}

	/**
     * Lista los Requerimientos No Programados que aprueba  un Jefe Inmediato
     * @param String ano_pro - Año de Periodo
     * @param String est_req - Estado del Requerimiento
     * @param String cod_sed - Código de Sede
     * @param String cod_res - Código de Responsable del Requerimiento
     * @param String mes_pro - Mes de Periodo
     * @param String mes_pro - Codigo jefe
     * @return Collection - Lista de Requerimientos
     */  
	@Override
	public Collection listarRqnpJefe(String ano_pro, String est_req,
			String cod_sed,  String mes_pro, String cod_per) {
		if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.listarRqnpJefe");
		List<Map<String, Object>> lsRqnp = new ArrayList<Map<String,Object>>();
	
		Map<String, Object> requerimientoNoProgramado;
		try {
			List<RequerimientoNoProgramadoBean> lista = (List<RequerimientoNoProgramadoBean>) requerimientoNoProgramadoDao.listarRqnpJefe(ano_pro, est_req, null,  mes_pro, cod_per);
			char ini=' ' ;
			char ini2= "'".charAt(0) ;
			char salto =(char)10;
			char retorno =(char)13;
			char espacio =(char)32;
			CharSequence comi ="&quot";
			CharSequence comis ="\"";
			CharSequence comSimple="'";
			CharSequence comSimpleSalva="&#39";
			CharSequence comaSimple=",";
			CharSequence comaSimpleSalva=";";
			
			for(RequerimientoNoProgramadoBean data: lista){
				requerimientoNoProgramado = new HashMap<String, Object>();

				requerimientoNoProgramado.put("codigoRqnp", data.getCodigoRqnp());
				requerimientoNoProgramado.put("anioProceso", data.getAnioProceso());				
				requerimientoNoProgramado.put("mesProeso", data.getMesProeso());
				requerimientoNoProgramado.put("codigoSede", data.getCodigoSede());
				requerimientoNoProgramado.put("fechaRqnp",new SimpleDateFormat("dd/MM/yyyy HH:mm").format(data.getFechaRqnp()));
				requerimientoNoProgramado.put("motivoSolicitud", data.getMotivoSolicitud().replace(comis, comi).replace(comaSimple, comaSimpleSalva).replace(comSimple, comSimpleSalva).replace(salto, espacio).replace(retorno, espacio));
				requerimientoNoProgramado.put("estadoRqnp", data.getEstadoRqnp());
				//requerimientoNoProgramado.put("montoRqnp", data.getMontoRqnp().toString());
				requerimientoNoProgramado.put("codigoDependencia", data.getCodigoDependencia());
				requerimientoNoProgramado.put("tipoMoneda", data.getTipoMoneda());
				requerimientoNoProgramado.put("codigoResposanble", data.getCodigoResposanble());
			//	requerimientoNoProgramado.put("fechaEnvioSolicitud", fechaEnvioSolicitud.getFormatDate("dd/MM/yyyy"));
				requerimientoNoProgramado.put("especificaAsig", data.getEspecificaAsig());
				//requerimientoNoProgramado.put("fechaAsig", fechaRqnp.getFormatDate("dd/MM/yyyy"));
				requerimientoNoProgramado.put("numeroExpediente", data.getNumeroExpediente());
				requerimientoNoProgramado.put("numeroArchivo", data.getNumeroArchivo());
				requerimientoNoProgramado.put("codigoReqNoProgramado", data.getCodigoReqNoProgramado());
				requerimientoNoProgramado.put("nombreSolicitante", data.getNombreSolicitante().replace(comis, comi).replace(comaSimple, comaSimpleSalva).replace(comSimple, comSimpleSalva).replace(salto, espacio).replace(retorno, espacio));
				requerimientoNoProgramado.put("nombreEstado", data.getNombreEstado());
				requerimientoNoProgramado.put("ind_solicitud", data.getInd_solicitud());
				requerimientoNoProgramado.put("anio_atencion", data.getAnio_atencion());
				requerimientoNoProgramado.put("mes_atencion", data.getMes_atencion());
				requerimientoNoProgramado.put("tipo_vinculo", data.getTipo_vinculo());
				requerimientoNoProgramado.put("vinculo", data.getVinculo());
				
				lsRqnp.add(requerimientoNoProgramado);
			}
			return lsRqnp;
		} 
		catch (Exception ex) {
			   log.error("Error en RequerimientoNoProgramadoServiceImpl.listarRqnpJefe: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
		}
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.listarRqnpJefe");
		}
		
	}
	
	
	/**
     * Lista los Requerimientos No Programados que aprueba  un Intendente
     * @param String ano_pro - Año de Periodo
     * @param String est_req - Estado del Requerimiento
     * @param String cod_sed - Código de Sede
     * @param String cod_res - Código de Responsable del Requerimiento
     * @param String mes_pro - Mes de Periodo
     * @param String mes_pro - Codigo jefe
     * @return Collection - Lista de Requerimientos
     */  
	@Override
	public Collection listarRqnpIntendente(String ano_pro, String est_req,
			String cod_sed,  String mes_pro, String cod_per) {
		if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.listarRqnpIntendente");
		List<Map<String, Object>> lsRqnp = new ArrayList<Map<String,Object>>();
		char ini=' ' ;
		char ini2= "'".charAt(0) ;
		char salto =(char)10;
		char retorno =(char)13;
		char espacio =(char)32;
		CharSequence comi ="&quot";
		CharSequence comis ="\"";
		CharSequence comSimple="'";
		CharSequence comSimpleSalva="&#39";
		
		CharSequence comaSimple=",";
		CharSequence comaSimpleSalva=";";
		
		Map<String, Object> requerimientoNoProgramado;
		try {
			List<RequerimientoNoProgramadoBean> lista = (List<RequerimientoNoProgramadoBean>) requerimientoNoProgramadoDao.listarRqnpIntendente(ano_pro, est_req, null,  mes_pro, cod_per);
			
			for(RequerimientoNoProgramadoBean data: lista){
				requerimientoNoProgramado = new HashMap<String, Object>();

				requerimientoNoProgramado.put("codigoRqnp", data.getCodigoRqnp());
				requerimientoNoProgramado.put("anioProceso", data.getAnioProceso());				
				requerimientoNoProgramado.put("mesProeso", data.getMesProeso());
				requerimientoNoProgramado.put("codigoSede", data.getCodigoSede());
				requerimientoNoProgramado.put("fechaRqnp",new SimpleDateFormat("dd/MM/yyyy HH:mm").format(data.getFechaRqnp()) );
				requerimientoNoProgramado.put("motivoSolicitud", data.getMotivoSolicitud().replace(comis, comi).replace(comaSimple, comaSimpleSalva).replace(comSimple, comSimpleSalva).replace(salto, espacio).replace(retorno, espacio));
				requerimientoNoProgramado.put("estadoRqnp", data.getEstadoRqnp());
				//requerimientoNoProgramado.put("montoRqnp", data.getMontoRqnp().toString());
				requerimientoNoProgramado.put("codigoDependencia", data.getCodigoDependencia());
				requerimientoNoProgramado.put("tipoMoneda", data.getTipoMoneda());
				requerimientoNoProgramado.put("codigoResposanble", data.getCodigoResposanble());
			//	requerimientoNoProgramado.put("fechaEnvioSolicitud", fechaEnvioSolicitud.getFormatDate("dd/MM/yyyy"));
				requerimientoNoProgramado.put("especificaAsig", data.getEspecificaAsig());
				//requerimientoNoProgramado.put("fechaAsig", fechaRqnp.getFormatDate("dd/MM/yyyy"));
				requerimientoNoProgramado.put("numeroExpediente", data.getNumeroExpediente());
				requerimientoNoProgramado.put("numeroArchivo", data.getNumeroArchivo());
				requerimientoNoProgramado.put("codigoReqNoProgramado", data.getCodigoReqNoProgramado());
				requerimientoNoProgramado.put("nombreSolicitante", data.getNombreSolicitante().replace(comis, comi).replace(comaSimple, comaSimpleSalva).replace(comSimple, comSimpleSalva).replace(salto, espacio).replace(retorno, espacio));
				requerimientoNoProgramado.put("nombreEstado", data.getNombreEstado());
				requerimientoNoProgramado.put("ind_solicitud", data.getInd_solicitud());
				requerimientoNoProgramado.put("anio_atencion", data.getAnio_atencion());
				requerimientoNoProgramado.put("mes_atencion", data.getMes_atencion());
				requerimientoNoProgramado.put("vinculo", data.getVinculo());
				requerimientoNoProgramado.put("tipo_vinculo", data.getTipo_vinculo());
				
				lsRqnp.add(requerimientoNoProgramado);
			}
			return lsRqnp;
		} 
		catch (Exception ex) {
			   log.error("Error en RequerimientoNoProgramadoServiceImpl.listarRqnpIntendente: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
		}
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.listarRqnpIntendente");
		}
		
	}

	
	/**
     * Reqistra  la Cabecera del Requerimientos No Programados 
     * @param Map<String, Object> params 
     * @return String - Código de Requerimiento no Programado
     */  
	@Override
	public String registrarCabRqnp(Map<String, Object> params) {
		if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.registrarCabRqnp");
		RequerimientoNoProgramadoBean rqnp= new RequerimientoNoProgramadoBean();
		String 	codigo_rq="";
		String 	ls_anio="";
		String 	ls_mes="";
		String 	ls_sec_rqnp="";
		String 	codigo_rqnp="";
		String 	cod_uuoo="";
		String 	user="";
		try {
			cod_uuoo =(String) params.get("uuoo");
			Calendar fecha =  Calendar.getInstance();
			ls_anio= String.valueOf( fecha.get(Calendar.YEAR)) ;
			
			Formatter fmt = new Formatter();
		
			ls_mes = fmt.format("%02d", fecha.get(Calendar.MONTH )+1) .toString();
			
			if (log.isDebugEnabled()) log.debug("MESSSS: "+ls_mes );
			
			ls_sec_rqnp = requerimientoNoProgramadoDao.secuencialRequerimientoRqnp(ls_anio);
			codigo_rq	= ls_anio+ls_sec_rqnp;
			ls_sec_rqnp=ls_sec_rqnp.substring(3,8);
			
			codigo_rqnp = requerimientoNoProgramadoDao.secuencialRequerimientoRqnpUuoo(ls_anio, cod_uuoo);
			codigo_rqnp= ls_anio+cod_uuoo+codigo_rqnp;
			
			rqnp.setCodigoRqnp(codigo_rq);
			rqnp.setAnioProceso(ls_anio);
			rqnp.setMesProeso(ls_mes);
			rqnp.setNumeroRqnp(ls_sec_rqnp);
			//rqnp.setCodigoSede((String) params.get("codigoSede"));
			rqnp.setFechaRqnp(new  Date());
			rqnp.setMotivoSolicitud((String) params.get("motivoSolicitud"));
			rqnp.setCod_contacto((String) params.get("cod_contacto"));
			rqnp.setAnexo_contacto((String) params.get("anexo_contacto"));
			rqnp.setCod_necesidad((String) params.get("cod_necesidad"));
			rqnp.setCod_tip_nececidad((String) params.get("cod_tip_nececidad"));
			rqnp.setNom_tip_necesidad((String) params.get("nom_tip_necesidad"));
			rqnp.setCod_finalidad((String) params.get("cod_finalidad"));
			rqnp.setCod_proveedor((String) params.get("cod_proveedor"));
			rqnp.setObs_dir_entrega((String) params.get("obs_dir_entrega"));
			rqnp.setObs_justificacion((String) params.get("obs_justificacion"));
			rqnp.setObs_lugar_entrega((String) params.get("obs_lugar_entrega"));
			rqnp.setObs_plazo_entrega((String) params.get("obs_plazo_entrega"));
			rqnp.setAnio_atencion((String) params.get("anio_atencion"));
			rqnp.setMes_atencion((String) params.get("mes_atencion"));
			rqnp.setTipo_vinculo((String) params.get("tipo_vinculo"));
			rqnp.setVinculo((String) params.get("vinculo"));
			rqnp.setInd_vinculo((String) params.get("ind_vinculo"));
			rqnp.setInd_prestamo((String) params.get("ind_prestamo"));
			rqnp.setEstadoRqnp("01");
			rqnp.setMontoRqnp(new BigDecimal(0));
			rqnp.setCodigoDependencia((String) params.get("codigoDependencia"));
			rqnp.setTipoMoneda("S");
			rqnp.setCodigoResposanble((String) params.get("codigoEmpleado"));
			rqnp.setCodigoReqNoProgramado(codigo_rqnp);
			rqnp.setTipoBien("B");
			rqnp.setTipoPpto("B");
			//AGREGADO
			rqnp.setInd_registropor("AU");
			
			////DataSource dataSource = obtenerDataSource("sig", false);
			////accesoDao.setUsuarioAcceso(dataSource,"USER", user);
			requerimientoNoProgramadoDao.insertar(rqnp);
			////accesoDao.setUsuarioAccesoNull(dataSource,"USER");
			
		}catch (Exception ex) {
			   log.error("Error en RequerimientoNoProgramadoServiceImpl.registrarCabRqnp: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
			} 
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.registrarCabRqnp");
		}
		
		return codigo_rq;
	}
	
	
	/**
     * Reqistra  la Cabecera del Requerimientos No Programados 
     * @param Map<String, Object> params 
     * @return String - Código de Requerimiento no Programado
     */  
	@Override
	public String updateCabRqnp(Map<String, Object> params) {
		if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.registrarCabEntregaRqnp");
		RequerimientoNoProgramadoBean rqnp= new RequerimientoNoProgramadoBean();
		String 	codigoRqnp="";
	
		try {
			
			rqnp.setCodigoRqnp((String) params.get("codigoRqnp"));	
			rqnp.setMotivoSolicitud((String) params.get("motivoSolicitud"));
			rqnp.setCod_contacto((String) params.get("cod_contacto"));
			rqnp.setAnexo_contacto((String) params.get("anexo_contacto"));
			rqnp.setCod_necesidad((String) params.get("cod_necesidad"));
			rqnp.setCod_tip_nececidad((String) params.get("cod_tip_nececidad"));
			rqnp.setNom_tip_necesidad((String) params.get("nom_tip_necesidad"));
			rqnp.setCod_finalidad((String) params.get("cod_finalidad"));
			rqnp.setCod_proveedor((String) params.get("cod_proveedor"));
			rqnp.setObs_justificacion((String) params.get("obs_justificacion"));
			rqnp.setObs_dir_entrega((String) params.get("obs_dir_entrega"));
			rqnp.setObs_lugar_entrega((String) params.get("obs_lugar_entrega"));
			rqnp.setObs_plazo_entrega((String) params.get("obs_plazo_entrega"));
			rqnp.setAnio_atencion((String) params.get("anio_atencion"));
			rqnp.setMes_atencion((String) params.get("mes_atencion"));
			rqnp.setTipo_vinculo((String) params.get("tipo_vinculo") );
			rqnp.setVinculo((String) params.get("vinculo"));
			rqnp.setInd_vinculo((String) params.get("ind_vinculo"));
			rqnp.setInd_prestamo((String) params.get("ind_prestamo"));
			
			requerimientoNoProgramadoDao.update(rqnp);
			
		}catch (Exception ex) {
			   log.error("Error en RequerimientoNoProgramadoServiceImpl.registrarCabEntregaRqnp: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
			} 
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.registrarCabEntregaRqnp");
		}
		
		return codigoRqnp;
	}
	
	/**
     * Recupera la Cabecera de un  Requerimientos No Programado 
     * @param String cod_req - Código de Requerimeinto no Programado 
     * @return RequerimientoNoProgramadoBean - Requerimiento no Programado
     */  
	@Override
	public RequerimientoNoProgramadoBean recuperarRqnpCab(String cod_req) {
		if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.recuperarRqnpCab");
		try{
			RequerimientoNoProgramadoBean rqnp= requerimientoNoProgramadoDao.recuperarRqnp(cod_req);
		return rqnp;
		
		}catch (Exception ex) {
			log.error("Error en RequerimientoNoProgramadoServiceImpl.recuperarRqnpCab: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);
			} 
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.recuperarRqnpCab");
		}
	}
	
	@Override
	public RequerimientoNoProgramadoBean recuperarRqnpCabAUC(String cod_req) {
		if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.recuperarRqnpCabAUC");
		try{
			RequerimientoNoProgramadoBean rqnp= requerimientoNoProgramadoDao.recuperarRqnp(cod_req);
		return rqnp;
		
	}catch (Exception ex) {
		   log.error("Error en RequerimientoNoProgramadoServiceImpl.recuperarRqnpCabAUC: " + ex.getMessage(), ex);
		   throw new ServiceException(this, ex);
		} 
	finally {
		if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.recuperarRqnpCabAUC");
	}
	
	}
	
	/**
     * Recupera la Cabecera de un  Requerimientos No Programado 
     * @param String cod_req - Código de Requerimeinto no Programado 
     * @return RequerimientoNoProgramadoBean - Requerimiento no Programado
     */  
	@Override
	public String registrarDetalleEntregaBien(Map<String, Object> params) {
		if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.registrarDetalleEntrega");
		String user;
		RequerimientoNoProgBienesBean bien= new RequerimientoNoProgBienesBean();
		String codigoRqnp="";
		String codigoBien="";
		String cod_proveedor="";
		String obs_justificacion="";
		String obs_plazo_entrega="";
		String obs_lugar_entrega="";
		String obs_dir_entrega="";
		String des_tecnica ="";
		try{
			codigoRqnp =(String) params.get("codigoRqnp");
			codigoBien =(String) params.get("codigoBien");
			cod_proveedor =(String) params.get("cod_proveedor");
			obs_justificacion =(String) params.get("obs_justificacion");
			obs_plazo_entrega =(String) params.get("obs_plazo_entrega");
			obs_lugar_entrega =(String) params.get("obs_lugar_entrega");
			obs_dir_entrega =(String) params.get("obs_dir_entrega");
			des_tecnica =(String) params.get("des_tecnica");
			
			bien.setCodigoRqnp(codigoRqnp);
			bien.setCodigoBien(codigoBien);
			bien.setCod_proveedor(cod_proveedor);
			bien.setObs_justificacion(obs_justificacion);
			bien.setObs_plazo_entrega(obs_plazo_entrega);
			bien.setObs_lugar_entrega(obs_lugar_entrega);
			bien.setObs_dir_entrega(obs_dir_entrega);
			bien.setDes_tecnica(des_tecnica);
			user =(String) params.get("user");
			DataSource dataSource = obtenerDataSource("sig", false);
			accesoDao.setUsuarioAcceso(dataSource,"USER", user);			
			requerimientoNoProgBienesDao.updateEntrega(dataSource, bien);
			accesoDao.setUsuarioAccesoNull(dataSource,"USER");
		return null;
	}catch (Exception ex) {
		   log.error("Error en RequerimientoNoProgramadoServiceImpl.registrarDetalleEntrega: " + ex.getMessage(), ex);
		   throw new ServiceException(this, ex);
		} 
	finally {
		if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.registrarDetalleEntrega");
	}
	
	}
	
	/**
     * Recupera un  Requerimientos No Programado 
     * @param String cod_req - Código de Requerimeinto no Programado 
     * @return RequerimientoNoProgramadoBean - Requerimiento no Programado
     */  
	@Override
	public RequerimientoNoProgramadoBean recuperarRqnp(String cod_req) {
		if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.RecuperarRqnp");
		
		List<Map<String, Object>> lsbienes = new ArrayList<Map<String,Object>>();
		Map<String, Object> requerimientoNoProgBienesBean ;
		//AGREGADO (DPORRASC)
		String tipo_auc;
		char ini=' ' ;
		char ini2= "'".charAt(0) ;
		char salto =(char)10;
		char retorno =(char)13;
		char espacio =(char)32;
		CharSequence comi ="&quot";
		CharSequence comis ="\"";
		try{
		
		RequerimientoNoProgramadoBean rqnp= requerimientoNoProgramadoDao.recuperarRqnp(cod_req);
		Collection<RequerimientoNoProgBienesBean> listaBienes = (List<RequerimientoNoProgBienesBean>) requerimientoNoProgBienesDao.listarRqnpBienes(cod_req);
		if (listaBienes!=null){
			for( RequerimientoNoProgBienesBean  obj:   listaBienes ){
				BigDecimal total = new BigDecimal(0);
				BigDecimal exceso = new BigDecimal(0);
				requerimientoNoProgBienesBean = new HashMap<String, Object>();
				
				requerimientoNoProgBienesBean.put("codigoRqnp", obj.getCodigoRqnp());
				requerimientoNoProgBienesBean.put("codigoBien", obj.getCodigoBien());
				requerimientoNoProgBienesBean.put("codigoUnidad", obj.getCodigoUnidad());
				requerimientoNoProgBienesBean.put("cantBien", obj.getCantBien());
				requerimientoNoProgBienesBean.put("precioUnid", obj.getPrecioUnid().setScale(2, RoundingMode.HALF_UP)  );
				
				requerimientoNoProgBienesBean.put("saldo", obj.getSaldo().setScale(2, RoundingMode.HALF_UP)  );
				total = obj.getCantBien().multiply( obj.getPrecioUnid()).setScale(2, RoundingMode.HALF_UP) ;
				requerimientoNoProgBienesBean.put("precioTotal",   total);
				exceso = obj.getSaldo().setScale(2, RoundingMode.HALF_UP).subtract(total);
				
				if (exceso.doubleValue()<0 ){
					exceso =exceso.multiply(new BigDecimal(-1));
				}else if (exceso.doubleValue()>0){
					exceso= new BigDecimal(0);
				}
				requerimientoNoProgBienesBean.put("exceso", exceso.setScale(2, RoundingMode.HALF_UP) );
				
				requerimientoNoProgBienesBean.put("desBien", obj.getDesBien().replace( comis,comi).replace(salto, espacio).replace(retorno, espacio));
				requerimientoNoProgBienesBean.put("tipo_bien", obj.getTipo_bien());
				requerimientoNoProgBienesBean.put("desUnid", (obj.getDesUnid() ==null) ? "" : obj.getDesUnid().trim());
				requerimientoNoProgBienesBean.put("codClasificador", obj.getCodClasificador());
				requerimientoNoProgBienesBean.put("desClasificador", obj.getDesClasificador());
				requerimientoNoProgBienesBean.put("estadoDesconcentrado", obj.getEstadoDesconcentrado());
				requerimientoNoProgBienesBean.put("numeroArchivo", obj.getNumeroArchivo());
				requerimientoNoProgBienesBean.put("ind_especializado", obj.getInd_especializado());
				requerimientoNoProgBienesBean.put("ind_estado", obj.getInd_estado());
				requerimientoNoProgBienesBean.put("item_origen", obj.getItem_origen());
				requerimientoNoProgBienesBean.put("nom_estado", obj.getNom_estado());
				requerimientoNoProgBienesBean.put("num_exp", obj.getNum_exp());
				requerimientoNoProgBienesBean.put("nro_adjuntos", obj.getNro_adjuntos());
				requerimientoNoProgBienesBean.put("auct1", (obj.getAuct1() ==null) ? "" : obj.getAuct1().trim());
				requerimientoNoProgBienesBean.put("auct2", (obj.getAuct2() ==null) ? "" : obj.getAuct2());
				requerimientoNoProgBienesBean.put("auct3", (obj.getAuct3() ==null) ? "" : obj.getAuct3());
				requerimientoNoProgBienesBean.put("tipo_ruta", obj.getTipo_ruta()  );
				requerimientoNoProgBienesBean.put("cod_ambito",obj.getCod_ambito());
				requerimientoNoProgBienesBean.put("cod_tipo_prog",obj.getCod_tipo_prog());	
				if(obj.getAuct1() !=null){
					DependenciaBean dependencia=dependenciaDao.obtenerDependencia(obj.getAuct1());
					if (dependencia != null){
						requerimientoNoProgBienesBean.put("auct_name", dependencia.getUuoo()+" - "+ dependencia.getNom_corto());
					}else{
						requerimientoNoProgBienesBean.put("auct_name", " ");
					}
					
				}else{
					requerimientoNoProgBienesBean.put("auct_name", " ");
				}
				log.debug("Adicionando " + obj.getCodigoBien());
				lsbienes.add(requerimientoNoProgBienesBean);
			}
			rqnp.setListaBienes(lsbienes);
		}
		
		return rqnp;
		
	}catch (Exception ex) {
		   log.error("Error en RequerimientoNoProgramadoServiceImpl.RecuperarRqnp: " + ex.getMessage(), ex);
		   throw new ServiceException(this, ex);
		} 
	finally {
		if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.RecuperarRqnp");
	}
	
	}
	
	
	/**
     * Recupera un  Requerimientos No Programado 
     * @param String cod_req - Código de Requerimeinto no Programado 
     * @return RequerimientoNoProgramadoBean - Requerimiento no Programado
     */  
	@Override
	public RequerimientoNoProgramadoBean recuperarRqnpAUC(String cod_req) {
		if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.recuperarRqnpAUC");
		
		List<Map<String, Object>> lsbienes = new ArrayList<Map<String,Object>>();
		Map<String, Object> requerimientoNoProgBienesBean ;
		//AGREGADO (DPORRASC)
		String tipo_auc;
		char ini=' ' ;
		char ini2= "'".charAt(0) ;
		char salto =(char)10;
		char retorno =(char)13;
		char espacio =(char)32;
		CharSequence comi ="&quot";
		CharSequence comis ="\"";
		try{
		
		RequerimientoNoProgramadoBean rqnp= requerimientoNoProgramadoDao.recuperarRqnp(cod_req);
		String cod_objeto =requerimientoNoProgramadoDao.getTipoObjetoRqnp(cod_req);
		
		Collection<RequerimientoNoProgBienesBean> listaBienes = (List<RequerimientoNoProgBienesBean>) requerimientoNoProgBienesDao.listarRqnpBienes(cod_req);
		if (listaBienes!=null){
			for( RequerimientoNoProgBienesBean  obj:   listaBienes ){
				BigDecimal total = new BigDecimal(0);
				BigDecimal exceso = new BigDecimal(0);
				requerimientoNoProgBienesBean = new HashMap<String, Object>();
				
				requerimientoNoProgBienesBean.put("codigoRqnp", obj.getCodigoRqnp());
				requerimientoNoProgBienesBean.put("codigoBien", obj.getCodigoBien());
				requerimientoNoProgBienesBean.put("codigoUnidad", obj.getCodigoUnidad());
				requerimientoNoProgBienesBean.put("cantBien", obj.getCantBien());
				requerimientoNoProgBienesBean.put("precioUnid", obj.getPrecioUnid().setScale(2, RoundingMode.HALF_UP)  );
				
				requerimientoNoProgBienesBean.put("saldo", obj.getSaldo().setScale(2, RoundingMode.HALF_UP)  );
				total = obj.getCantBien().multiply( obj.getPrecioUnid()).setScale(2, RoundingMode.HALF_UP) ;
				requerimientoNoProgBienesBean.put("precioTotal",   total);
				exceso = obj.getSaldo().setScale(2, RoundingMode.HALF_UP).subtract(total);
				
				if (exceso.doubleValue()<0 ){
					exceso =exceso.multiply(new BigDecimal(-1));
				}else if (exceso.doubleValue()>0){
					exceso= new BigDecimal(0);
				}
				requerimientoNoProgBienesBean.put("exceso", exceso.setScale(2, RoundingMode.HALF_UP) );
				
				requerimientoNoProgBienesBean.put("desBien", obj.getDesBien().replace( comis,comi).replace(salto, espacio).replace(retorno, espacio));
				requerimientoNoProgBienesBean.put("tipo_bien", obj.getTipo_bien());
				requerimientoNoProgBienesBean.put("desUnid", obj.getDesUnid());
				requerimientoNoProgBienesBean.put("codClasificador", obj.getCodClasificador());
				requerimientoNoProgBienesBean.put("desClasificador", obj.getDesClasificador());
				requerimientoNoProgBienesBean.put("estadoDesconcentrado", obj.getEstadoDesconcentrado());
				requerimientoNoProgBienesBean.put("numeroArchivo", obj.getNumeroArchivo());
				requerimientoNoProgBienesBean.put("ind_especializado", obj.getInd_especializado());
				requerimientoNoProgBienesBean.put("ind_estado", obj.getInd_estado());
				requerimientoNoProgBienesBean.put("item_origen", obj.getItem_origen());
				requerimientoNoProgBienesBean.put("nom_estado", obj.getNom_estado());
				requerimientoNoProgBienesBean.put("num_exp", obj.getNum_exp());
				requerimientoNoProgBienesBean.put("nro_adjuntos", obj.getNro_adjuntos());
				requerimientoNoProgBienesBean.put("auct1", (obj.getAuct1() ==null) ? "" : obj.getAuct1().trim());
				requerimientoNoProgBienesBean.put("auct2", (obj.getAuct2() ==null) ? "" : obj.getAuct2());
				requerimientoNoProgBienesBean.put("auct3", (obj.getAuct3() ==null) ? "" : obj.getAuct3());
				requerimientoNoProgBienesBean.put("tipo_ruta", obj.getTipo_ruta()  );
				requerimientoNoProgBienesBean.put("cod_ambito",obj.getCod_ambito());
				requerimientoNoProgBienesBean.put("cod_tipo_prog",obj.getCod_tipo_prog());	
				requerimientoNoProgBienesBean.put("cod_plaza","solo plaza");	
				
				if(obj.getCod_ambito().equals("01")){
					requerimientoNoProgBienesBean.put("des_ambito","01:Nacional");
				}
				if(obj.getCod_ambito().equals("02")){
					requerimientoNoProgBienesBean.put("des_ambito","02:Lima/Callao");
				}
				if(obj.getCod_ambito().equals("03")){
					requerimientoNoProgBienesBean.put("des_ambito","03:Desconcentrada");
				}
				if(obj.getCod_ambito().equals("04")){
					requerimientoNoProgBienesBean.put("des_ambito","04:Lima-Callao/Desconcentrada");
				}
				
				if(obj.getAuct1() !=null){
				DependenciaBean dependencia=dependenciaDao.obtenerDependencia(obj.getAuct1());
					if (dependencia != null){
						requerimientoNoProgBienesBean.put("auct_name", dependencia.getUuoo()+" - "+ dependencia.getNom_corto());
					}else{
						requerimientoNoProgBienesBean.put("auct_name", " ");
					}
					
				}else{
					requerimientoNoProgBienesBean.put("auct_name", " ");
				}
				log.debug("Adicionando " + obj.getCodigoBien());
				lsbienes.add(requerimientoNoProgBienesBean);
			}
			rqnp.setListaBienes(lsbienes);
		}
		if(cod_objeto != null){
			log.debug("obejto " + cod_objeto);
			if(cod_objeto.equals("B")){
			 cod_objeto="1";
			}else if (cod_objeto.equals("S")){
			 cod_objeto="2";
			}else if(cod_objeto.equals("O")){
			 cod_objeto="3";
			}
			rqnp.setCod_objeto(cod_objeto);
		}
			 
		return rqnp;
		
	}catch (Exception ex) {
		   log.error("Error en RequerimientoNoProgramadoServiceImpl.recuperarRqnpAUC: " + ex.getMessage(), ex);
		   throw new ServiceException(this, ex);
		} 
	finally {
		if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.recuperarRqnpAUC");
	}
	
	}
	/**
     * Listar los ítems del catálogo de Bienes y Servicios
     * @param Map<String, Object> parmt 
     * @return Collection - Lista de Requerimientos
     */  
	@Override
	public Collection listarCatalogo(Map<String, Object> parmt) {
		if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.listarCatalogo");
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
		int i =0;
	
		List<Map<String, Object>> lsCatalogo = new ArrayList<Map<String,Object>>();
		String cod_dep =(String) parmt.get("cod_dep");
		
		DependenciaBean dependencia=null;
		String anioActual = FechaUtil.obtenerAnioActual();
		BigDecimal limiteCompraDirecta = new BigDecimal(0);
		BigDecimal gastoActual = new BigDecimal(0);
		
		try {
		
			List<CatalogoBienesBean> lista = (List<CatalogoBienesBean>) catalogoBienesDao.listarCatalogo(parmt);
			limiteCompraDirecta = this.calculaLimiteCompraDirecta(anioActual);
			
			for(CatalogoBienesBean data: lista){
				map = new HashMap<String, Object>();
				// if (log.isDebugEnabled()) log.debug( formatter.format(data.getPrecio_bien().doubleValue()));
				BigDecimal exceso= new BigDecimal(0);
				map.put("codigo_bien", data.getCodigo_bien());
				map.put("desc_bien", data.getDesc_bien(). replace(comaSimple, comaSimpleSalva).replace(comSimple, comSimpleSalva). replace(salto, espacio).replace(retorno, espacio).trim());	
				map.put("tipo_bien", data.getTipo_bien());
				map.put("codigo_unidad", data.getCodigo_unidad());
				map.put("precio_bien", data.getPrecio_bien().setScale(2, RoundingMode.HALF_UP).toString());
				map.put("saldo", data.getSaldo().setScale(2, RoundingMode.HALF_UP).toString());
				map.put("desc_unidad", data.getDesc_unidad(). replace(comaSimple, comaSimpleSalva).replace(comSimple, comSimpleSalva). replace(salto, espacio).replace(retorno, espacio).trim());
				map.put("codigo_gasto", data.getCodigo_gasto());
				map.put("desc_gasto", data.getDesc_gasto(). replace(comaSimple, comaSimpleSalva).replace(comSimple, comSimpleSalva). replace(salto, espacio).replace(retorno, espacio).trim());
				map.put("estadoDesconcentrado", data.getEstadoDesconcentrado().replace(salto, espacio).replace(retorno, espacio).trim());
				map.put("auct1", (data.getAuct1() ==null) ? "" : data.getAuct1().trim());
				map.put("tipo_ruta", data.getTipo_ruta());
				
				//AGREGADO
				map.put("cod_ambito",data.getCod_ambito());
				map.put("cod_tipo_prog",data.getCod_tipo_prog());
				
				if(limiteCompraDirecta!= null){
					if( gastoActual.compareTo(limiteCompraDirecta)==1){
						exceso = data.getSaldo().setScale(2, RoundingMode.HALF_UP).subtract(limiteCompraDirecta);
					}else{
						exceso=new BigDecimal(0);
					}
					map.put("exceso", exceso.setScale(2, RoundingMode.HALF_UP));
				}else{
					map.put("exceso", exceso.setScale(2, RoundingMode.HALF_UP));
				}
				
				log.debug("auct1: "+data.getAuct1());
				log.debug("getCod_tipo_ruta!!!! "+data.getTipo_ruta());
				log.debug("getCod_tipo_prog!!!! "+data.getCod_tipo_prog()  +" - "+ cod_dep);
				if(data.getAuct1() !=null){
					if (data.getTipo_ruta().equals("04")){
						dependencia=dependenciaDao.obtenerDependencia(cod_dep);
					}else{
						dependencia=dependenciaDao.obtenerDependencia(data.getAuct1());
					}
					
					if (dependencia != null){
						//if (log.isDebugEnabled())log.debug(dependencia.getUuoo() )+" - "+ dependencia.getNom_corto());
						map.put("auct_name", dependencia.getUuoo() +" - "+ dependencia.getNom_corto());
						lsCatalogo.add(map);
					}else{
						if (log.isDebugEnabled())log.debug(">>>>>>>>>>>>AUCT DEP Nulo00000000000:"+data.getAuct1() );
						map.put("auct_name", "");
					}
				}else{
					dependencia=dependenciaDao.obtenerDependencia(cod_dep);
					if (dependencia != null){
						//if (log.isDebugEnabled())log.debug(dependencia.getUuoo() )+" - "+ dependencia.getNom_corto());
						map.put("auct_name", dependencia.getUuoo() +" - "+ dependencia.getNom_corto());
						lsCatalogo.add(map);
					}else{
						if (log.isDebugEnabled())log.debug(">>>>>>>>>>>>AUCT DEP Nulo00000000000:"+data.getAuct1() );
						map.put("auct_name", "");
					}
				}
				i++;
			}
			return lsCatalogo;
		} 
		catch (Exception ex) {
			   log.error("Error en RequerimientoNoProgramadoServiceImpl.listarCatalogo: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
		}
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.listarCatalogo");
		}
	}


	/**
     * Listar los ítems del catálogo de Bienes y Servicios de una Familia
     * @param Map<String, Object> parmt 
     * @return Collection - Lista de Requerimientos
     */  
	@Override
	public Collection listarCatalogoFamilia(Map<String, Object> parmt) {
			if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.listarCatalogoFamilia");
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
		
		int i =0;
	
		List<Map<String, Object>> lsCatalogo = new ArrayList<Map<String,Object>>();
		try {
		
			List<CatalogoBienesBean> lista = (List<CatalogoBienesBean>) catalogoBienesDao.listarCatalogoFamilia(parmt);
			for(CatalogoBienesBean data: lista){
				map = new HashMap<String, Object>();
				// if (log.isDebugEnabled()) log.debug( formatter.format(data.getPrecio_bien().doubleValue()));
				
				map.put("codigo_bien", data.getCodigo_bien());
				map.put("desc_bien", data.getDesc_bien(). replace(comaSimple, comaSimpleSalva).replace(comSimple, comSimpleSalva). replace(salto, espacio).replace(retorno, espacio).trim());	
				map.put("tipo_bien", data.getTipo_bien());
				map.put("codigo_unidad", data.getCodigo_unidad());
				map.put("precio_bien", data.getPrecio_bien().setScale(2, RoundingMode.HALF_UP).toString());
				map.put("desc_unidad", data.getDesc_unidad(). replace(comaSimple, comaSimpleSalva).replace(comSimple, comSimpleSalva). replace(salto, espacio).replace(retorno, espacio).trim());
				map.put("auct_name", data.getAuct_name(). replace(comaSimple, comaSimpleSalva).replace(comSimple, comSimpleSalva). replace(salto, espacio).replace(retorno, espacio).trim());
				
				lsCatalogo.add(map);
				i++;
			}
			return lsCatalogo;
		} 
		catch (Exception ex) {
			   log.error("Error en RequerimientoNoProgramadoServiceImpl.listarCatalogoFamilia: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
		}
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.listarCatalogoFamilia");
		}
	}


	/**
	 * @author dporrasc
     * Reqista los item del Requerimiento No Programado.
     * @param Map<String, Object> parmt 
     * @return String - Código del Requerimiento No Programado
     */
	@SuppressWarnings("unchecked")
	@Override
	public String registrarRqnpDetalle(RequerimientoNoProgramadoBean rqnpCabBean, RequerimientoNoProgBienesBean rqnpBienBean) throws  ServiceException{
		if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.registrarCabActualizaRqnp");
		RequerimientoNoProgramadoBean rqnp= new RequerimientoNoProgramadoBean();
		Map params = new HashMap();
		Map parm = new HashMap();
		
		String codDoc	= "";
		String codigoRqnp	= rqnpCabBean.getCodigoRqnp();
		String cod_exp		="";
		List<Map<String, Object>> listaMetas= new ArrayList<Map<String,Object>>();
		BigDecimal montoTotal = new BigDecimal(0);
		BigDecimal suma = new BigDecimal(0);
	
		try {
			if (log.isDebugEnabled()) log.debug("codigo_rqnp" + codigoRqnp);
			
			//codigo_bien
			//codigoRqnp
			rqnpBienBean.setItem_origen(rqnpBienBean.getCodigoBien());
			//plaza
			//cod unidad
			//cod clas gasto
			//cantidad bien -- rqnpBienBean.setCantBien(new BigDecimal(0));
			//precio unidad
			rqnpBienBean.setPrecio_item(rqnpBienBean.getPrecioUnid());
			rqnpBienBean.setPrecio_origen(rqnpBienBean.getPrecioUnid());
			rqnpBienBean.setPrecioUnidDol(rqnpBienBean.getPrecioUnid());
			
			if(rqnpBienBean.getAuct1().equals("04")){ //Para items de ruta 04
				rqnpBienBean.setAuct1(dependenciaDao.obtenerUuooNoSupervision(rqnpCabBean.getCodigoDependencia(),"4"));
			}
			
			rqnpBienBean.setInd_estado("01");
			
			if(rqnpBienBean.getAuct1().equals("")){
				rqnpBienBean.setInd_especializado("N");
			}else{
				rqnpBienBean.setInd_especializado("S");
			}
			montoTotal = (rqnpBienBean.getCantBien()).multiply(rqnpBienBean.getPrecioUnid());
			suma= suma.add(montoTotal. setScale(2, RoundingMode.HALF_UP));
			
			ArrayList<RequerimientoNoProgBienesBean> listaBienes = (ArrayList<RequerimientoNoProgBienesBean>) requerimientoNoProgBienesDao.listarRqnpBienes(codigoRqnp);
			
			if(listaBienes.size()==0){ //cuando es la primera inserción
				//requerimientoNoProgBienesDao.insertar(rqnpBienBean);
				log.debug("INSERTAR CUNADO ES LA PRIMERA VEZ");
				codDoc = rqnpBienBean.getCodigoRqnp() + rqnpBienBean.getCodigoBien();
				parm.put("anio_pro", rqnpCabBean.getAnioProceso());
				//parm.put("cod_sede", cod_sede);
				parm.put("cod_responsable", rqnpCabBean.getCodigoResposanble());
				parm.put("cod_proceso", "249");
				parm.put("cod_doc", codDoc);
				if (log.isDebugEnabled())log.debug("crear EXPEDIENTE>>>>>>>>>>>>>><<");
				cod_exp=requerimientoNoProgBienesDao.crearExpediente(parm).trim();
				rqnpBienBean.setNum_exp(cod_exp );
				
				requerimientoNoProgBienesDao.insertar(rqnpBienBean);
				
				parm.put("cod_accion", "001");
				parm.put("cod_exp", cod_exp);
				parm.put("exp_estado", "001");
				parm.put("exp_obs", "");
				if (log.isDebugEnabled())log.debug("crear ACCION>>>>>>>>>>>>>><<");
				requerimientoNoProgBienesDao.crearAccion(parm);
			}else{
				
				Boolean exiteItemEnLista = RqnpUtil.existeItemEnListaBienes(rqnpBienBean.getCodigoBien(),listaBienes);
				
				if(exiteItemEnLista){
					requerimientoNoProgBienesDao.update(rqnpBienBean);
				}else{
					//requerimientoNoProgBienesDao.insertar(bien);
					log.debug("INSERTAR ITEM A LA BD CANDO LA DB POSEE DATOS");
					log.debug("(INSERTAR EL ITEM) codigoBien: "+rqnpBienBean.getCodigoBien());
					codDoc = rqnpBienBean.getCodigoRqnp() + rqnpBienBean.getCodigoBien();
					parm.put("anio_pro", rqnpCabBean.getAnioProceso());
					//parm.put("cod_sede", cod_sede);
					parm.put("cod_responsable", rqnpCabBean.getCodigoResposanble());
					parm.put("cod_proceso", "249");
					parm.put("cod_doc", codDoc);
					if (log.isDebugEnabled())log.debug("crear EXPEDIENTE>>>>>>>>>>>>>><<");
					cod_exp=requerimientoNoProgBienesDao.crearExpediente(parm).trim();
					rqnpBienBean.setNum_exp(cod_exp );
					
					requerimientoNoProgBienesDao.insertar(rqnpBienBean);
					
					parm.put("cod_accion", "001");
					parm.put("cod_exp", cod_exp);
					parm.put("exp_estado", "001");
					parm.put("exp_obs", "");
					if (log.isDebugEnabled())log.debug("crear ACCION>>>>>>>>>>>>>><<");
					requerimientoNoProgBienesDao.crearAccion(parm);
				}
				
			}
			
			//rqnp.setCodigoRqnp(codigo_rqnp);
			rqnpCabBean.setMontoRqnp(suma);
			requerimientoNoProgramadoDao.updateMonto(rqnpCabBean);
		
		}catch (Exception ex) {
			   log.error("Error en RequerimientoNoProgramadoServiceImpl.registrarCabActualizaRqnp: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
			} 
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.registrarCabActualizaRqnp");
		}
		
		return rqnpCabBean.getCodigoRqnp();
		
	}


	
	/**
	 * @author dporrasc
     * Reqista los item del Requerimiento No Programado.
     * @param Map<String, Object> parmt 
     * @return String - Código del Requerimiento No Programado
     */
	@Override
	public String registrarDetalleRqnp(Map<String, Object> params) throws  ServiceException{
		if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.registrarCabActualizaRqnp");
		RequerimientoNoProgramadoBean rqnp= new RequerimientoNoProgramadoBean();
		Map parm = new HashMap();
		String cod_doc			="";
		String codigo_rqnp		="";
		String cod_exp			="";
		List<Map<String, Object>> listaMetas= new ArrayList<Map<String,Object>>();
		BigDecimal montoTotal = new BigDecimal(0);
		BigDecimal suma = new BigDecimal(0);
		
		String anio_pro			=(String) params.get("anio_pro");
		//String cod_sede			=(String) params.get("cod_sede");
		String cod_responsable	=(String) params.get("cod_responsable");
		String cod_plaza		=(String) params.get("cod_plaza");
		String cod_dep		=(String) params.get("cod_dep");
		
		String cod_rqnp[] =(String[]) params.get ("cod_rqnp");
		String cod_bien[] =(String[]) params.get("cod_bien");
		String cod_unid[] =(String[]) params.get("cod_unid");
		String cantidad[] =(String[]) params.get("cantidad");
		String pre_unit[] =(String[]) params.get("pre_unit");
		String cod_clas[] =(String[]) params.get("cod_clas");
		String auct1[] =(String[]) params.get("auct1");
		String tipo_ruta[] =(String[]) params.get("tipo_ruta");
		String user =(String) params.get("user");
		
		String anio_ejec =(String) params.get ("anio_ejec");
	
		try {
			codigo_rqnp =(String) params.get("codigo_rqnp");
		
			if (log.isDebugEnabled()) log.debug("codigo_rqnp" + codigo_rqnp);
			
			rqnp.setCodigoRqnp(codigo_rqnp);
			
			DataSource dataSource = obtenerDataSource("sig", false);
			
			if (cod_rqnp !=null){
				for (int i =0 ; i < cod_rqnp.length; i++){
					RequerimientoNoProgBienesBean bienes = new RequerimientoNoProgBienesBean();
					bienes.setCodigoBien(cod_bien[i]);
					bienes.setItem_origen(cod_bien[i]);
					bienes.setCodigoRqnp(codigo_rqnp);
					bienes.setCod_plaza(cod_plaza);
					bienes.setCodigoUnidad(cod_unid[i]);
					bienes.setCodClasifGasto(cod_clas[i]);
					
					bienes.setCantBien( new BigDecimal(cantidad[i]));
					bienes.setPrecioUnid(new BigDecimal(pre_unit[i] ));
					bienes.setPrecio_item(new BigDecimal(pre_unit[i]));
					bienes.setPrecio_origen(new BigDecimal(pre_unit[i]));
					bienes.setPrecioUnidDol(new BigDecimal(0));
					
					log.debug("TIPO_RUTA ------- >>>" + tipo_ruta[i]);
					log.debug("cod_dep"+cod_dep);
					if (tipo_ruta[i].equals("04")){//ITEM SIN AUC - SON ATENDIDO POR EL SOLICITANTE
						bienes.setAuct1(dependenciaDao.obtenerUuooNoSupervision(auct1[i],"4"));
					}else{
						bienes.setAuct1(auct1[i]);	
					}
					
					bienes.setInd_estado("01");//anteriormente estuvo "00"
					
					if (auct1[i]==null){
						bienes.setInd_especializado("N");
					}else{
						bienes.setInd_especializado("S");
					}
					
					montoTotal = (new BigDecimal(cantidad[i])).multiply(new BigDecimal(pre_unit[i] ));
					suma= suma.add(montoTotal. setScale(2, RoundingMode.HALF_UP));
					if (cod_rqnp[i].equals(codigo_rqnp)){
						requerimientoNoProgBienesDao.update(bienes);
						//Actualizando Metas
						params.put("cod_bien", cod_bien[i]);
						params.put("cod_req", codigo_rqnp);
						params.put("precio_unid", pre_unit[i]);
						 
						//RECUPERANDO METASSS
						 listaMetas = (List<Map<String, Object>>) this.listarRqnpMetas(params);
						 for (Map<String, Object> obj:listaMetas ){
							RequerimientoNoProgMetasBean metas = new RequerimientoNoProgMetasBean();
							BigDecimal mtoCantidad = new BigDecimal((String)obj.get("cantidadTotal"));
							BigDecimal monto = new BigDecimal(0);
							monto = mtoCantidad.multiply(new BigDecimal(pre_unit[i] ));
							metas.setCodigoRqnp(codigo_rqnp);
							metas.setCodigoBien(cod_bien[i]);
							metas.setAnioEjec(anio_ejec);
							metas.setSecuenciaMeta((String)obj.get("secuenciaMeta"));
							metas.setMontoSoles(monto.setScale(2, RoundingMode.HALF_UP));
							metas.setCantidadTotal(mtoCantidad);
							//ACTUALIZANDO EL MONTO TOTAL SI SE CAMBIO DE PRECIO UNITARIO
							requerimientoNoProgMetasDao.update(metas);
						 }
					}else{
						cod_doc = codigo_rqnp+ bienes.getCodigoBien();
						parm.put("anio_pro", anio_pro);
						//parm.put("cod_sede", cod_sede);
						parm.put("cod_responsable", cod_responsable);
						parm.put("cod_proceso", "249");
						parm.put("cod_doc", cod_doc);
						accesoDao.setUsuarioAcceso(dataSource,"USER", user);
						if (log.isDebugEnabled())log.debug("crear EXPEDIENTE>>>>>>>>>>>>>><<");
						cod_exp=requerimientoNoProgBienesDao.crearExpediente(parm).trim();
						bienes.setNum_exp(cod_exp );
						
						requerimientoNoProgBienesDao.insertar(bienes);
						
						parm.put("cod_accion", "001");
						parm.put("cod_exp", cod_exp);
						parm.put("exp_estado", "001");
						parm.put("exp_obs", "");
						if (log.isDebugEnabled())log.debug("crear ACCION>>>>>>>>>>>>>><<");
						requerimientoNoProgBienesDao.crearAccion(parm);
						
						accesoDao.setUsuarioAccesoNull(dataSource,"USER");
					}
				}
				rqnp.setCodigoRqnp(codigo_rqnp);
				rqnp.setMontoRqnp(suma);
				accesoDao.setUsuarioAcceso(dataSource,"USER", user);
				requerimientoNoProgramadoDao .updateMonto(rqnp);
				accesoDao.setUsuarioAccesoNull(dataSource,"USER");
			}
		
		}catch (Exception ex) {
			   log.error("Error en RequerimientoNoProgramadoServiceImpl.registrarCabActualizaRqnp: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
			} 
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.registrarCabActualizaRqnp");
		}
		
		return codigo_rqnp;
		
	}

	
	/**
	 * @author dporrasc
     * Elimina   ítems del Requerimiento No Programado.
     * @param Map<String, Object> parmt 
     * @return void
     */
	@Override
	public void deleteBienRnp(Map<String, Object> params) {
		
		if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.deleteBienRnp");
		RequerimientoNoProgBienesBean bien= new RequerimientoNoProgBienesBean();
		RequerimientoNoProgMetasBean meta = new RequerimientoNoProgMetasBean();
		try {
			
			meta.setAnioEjec((String) params.get("anioEjec"));
			meta.setCodigoBien((String) params.get("codigo_bien"));
			meta.setCodigoRqnp((String) params.get("codigo_rqnp"));
			
			requerimientoNoProgMetasDao.delete(meta);
			
			bien.setCodigoBien((String) params.get("codigo_bien"));
			bien.setCodigoRqnp((String) params.get("codigo_rqnp"));
			
			requerimientoNoProgBienesDao.delete(bien);
			
		}catch (Exception ex) {
			   log.error("Error en RequerimientoNoProgramadoServiceImpl.deleteBienRnp: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
			} 
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.deleteBienRnp");
		}
	}
	
	
	
	/**
     * Lista las Metas(Acciones)  del Requerimiento No Programado y/o los del Bien o Servicio.
     * @param Map<String, Object> parmt 
     * @return Collection -Lista de Metas 
     */
	@Override
	public Collection listarRqnpMetas(Map<String, Object> params) {
		if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.listarRqnpMetas");
		Map<String, Object> map;
		List<Map<String, Object>> lsMetas = new ArrayList<Map<String,Object>>();
		try {
			String precio_unid =(String)params.get("precio_unid");
			
			List<RequerimientoNoProgMetasBean> lista = (List<RequerimientoNoProgMetasBean>) requerimientoNoProgMetasDao.listarRqnpMetas(params);
			
			BigDecimal precio_unitario = new BigDecimal(precio_unid);
			char ini=' ' ;
			char ini2= "'".charAt(0) ;
			char salto =(char)10;
			char retorno =(char)13;
			char espacio =(char)32;
			CharSequence comSimple="'";
			CharSequence comSimpleSalva="&#39";
			CharSequence comaSimple=",";
			CharSequence comaSimpleSalva=";";
			CharSequence comi ="&quot";
			CharSequence comis ="\"";
			
			for(RequerimientoNoProgMetasBean data: lista){
				map = new HashMap<String, Object>();
				map.put("codigoRqnp", data.getCodigoRqnp());
				map.put("codigoBien", data.getCodigoBien());
				map.put("anioEjec", data.getAnioEjec());
				map.put("secuenciaMeta", data.getSecuenciaMeta());
				map.put("cantidadTotal", data.getCantidadTotal().setScale(0, RoundingMode.HALF_UP).toString() );
				map.put("montoSoles", data.getMontoSoles().setScale(2, RoundingMode.HALF_UP).toString());
				map.put("montoDolar", data.getMontoDolar().setScale(2, RoundingMode.HALF_UP).toString());
				map.put("precioUnid",precio_unitario.setScale(2, RoundingMode.HALF_UP).toString());
				map.put("ubg", data.getUbg().replace(comis, comi).replace(comaSimple, comaSimpleSalva).replace(comSimple, comSimpleSalva). replace(salto, espacio).replace(retorno, espacio).trim());
				map.put("producto", data.getProducto().replace(comis, comi).replace(comaSimple, comaSimpleSalva).replace(comSimple, comSimpleSalva). replace(salto, espacio).replace(retorno, espacio).trim());
				map.put("meta",data.getMeta().replace(comis, comi).replace(comaSimple, comaSimpleSalva).replace(comSimple, comSimpleSalva). replace(salto, espacio).replace(retorno, espacio).trim());
				map.put("uuoo",data.getUuoo().replace(comis, comi).replace(comaSimple, comaSimpleSalva).replace(comSimple, comSimpleSalva). replace(salto, espacio).replace(retorno, espacio).trim());
				map.put("metaSiaf",data.getMetaSiaf().replace(comis, comi).replace(comaSimple, comaSimpleSalva).replace(comSimple, comSimpleSalva). replace(salto, espacio).replace(retorno, espacio).trim());
				
				lsMetas.add(map);
			}
			return lsMetas;
		} 
		catch (Exception ex) {
			   log.error("Error en RequerimientoNoProgramadoServiceImpl.listarRqnpMetas: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
		}
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.listarRqnpMetas");
		}
	}
	
	
	/**
     * Lista las Metas(Acciones)  del Requerimiento No Programado (Filtro solo registradas).
     * @param Map<String, Object> parmt 
     * @return Collection -Lista de Metas 
     */
	@Override
	public Collection listarRqnpMetasVista(Map<String, Object> params) {
		if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.listarRqnpMetasVista");
		Long lj = new Long(0);
		char ini=' ' ;
		char ini2= "'".charAt(0) ;
		char salto =(char)10;
		char retorno =(char)13;
		char espacio =(char)32;
		CharSequence comSimple="'";
		CharSequence comSimpleSalva="&#39";
		CharSequence comaSimple=",";
		CharSequence comaSimpleSalva=";";
		List<Map<String, Object>> lsMetas = new ArrayList<Map<String,Object>>();
		try {
			String precio_unid =(String)params.get("precio_unid");
			List<RequerimientoNoProgMetasBean> lista = (List<RequerimientoNoProgMetasBean>) requerimientoNoProgMetasDao.listarRqnpMetasBien(params);
			
			Map<String, Object> map;
			BigDecimal precio_unitario = new BigDecimal(precio_unid);
			for(RequerimientoNoProgMetasBean data: lista){
				if (data.getCantidadTotal().doubleValue()>0 ){
					map = new HashMap<String, Object>();
					map.put("codigoRqnp", data.getCodigoRqnp());
					map.put("codigoBien", data.getCodigoBien());
					map.put("anioEjec", data.getAnioEjec());
					map.put("secuenciaMeta", data.getSecuenciaMeta());
					map.put("cantidadTotal", data.getCantidadTotal().setScale(0, RoundingMode.HALF_UP).toString() );
					map.put("montoSoles", data.getMontoSoles().setScale(2, RoundingMode.HALF_UP).toString());
					map.put("montoDolar", data.getMontoDolar().setScale(2, RoundingMode.HALF_UP).toString());
					map.put("precioUnid",precio_unitario.setScale(2, RoundingMode.HALF_UP).toString());
					map.put("ubg", data.getUbg().replace(comaSimple, comaSimpleSalva).replace(comSimple, comSimpleSalva). replace(salto, espacio).replace(retorno, espacio).trim());
					map.put("producto", data.getProducto().replace(comaSimple, comaSimpleSalva).replace(comSimple, comSimpleSalva). replace(salto, espacio).replace(retorno, espacio).trim());
					map.put("meta",data.getMeta().replace(comaSimple, comaSimpleSalva).replace(comSimple, comSimpleSalva). replace(salto, espacio).replace(retorno, espacio).trim());
					map.put("uuoo",data.getUuoo().replace(comaSimple, comaSimpleSalva).replace(comSimple, comSimpleSalva). replace(salto, espacio).replace(retorno, espacio).trim());
					map.put("metaSiaf",data.getMetaSiaf().replace(comaSimple, comaSimpleSalva).replace(comSimple, comSimpleSalva). replace(salto, espacio).replace(retorno, espacio).trim());
					
					lsMetas.add(map);
				}
			}
			return lsMetas;
		} 
		catch (Exception ex) {
			   log.error("Error en RequerimientoNoProgramadoServiceImpl.listarRqnpMetasVista: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
		}
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.listarRqnpMetasVista");
		}
	}
	
	
	/**
     * Lista las Metas(Acciones)  del Requerimiento No Programado (Filtro solo registradas).
     * @param Map<String, Object> parmt 
     * @return Collection -Lista de Metas 
     */
	@Override
	public RequerimientoNoProgBienesBean recuperarDatosEntregaBien(String cod_req ,String cod_bien) {
		
		if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.recuperarDatosEntregaBien");
		
		try {
			return requerimientoNoProgBienesDao.recuperarRqnpBienes(cod_req, cod_bien);
		} 
		catch (Exception ex) {
			   log.error("Error en RequerimientoNoProgramadoServiceImpl.recuperarDatosEntregaBien: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
		}
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.recuperarDatosEntregaBien");
		}
	}
	
	
	/**
     * Lista las Metas(Acciones)  del Requerimiento No Programado (Filtro solo registradas).
     * @param Map<String, Object> parmt 
     * @return Collection -Lista de Metas 
     */
	@Override
	public RequerimientoNoProgBienesBean recuperarDatosBienModifica(String cod_req ,String cod_bien) {
		
		if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.recuperarDatosBienModifica");
		
		try {
			return requerimientoNoProgBienesDao.recuperarRqnpBienesModifica(cod_req, cod_bien);
		} 
		catch (Exception ex) {
			   log.error("Error en RequerimientoNoProgramadoServiceImpl.recuperarDatosBienModifica: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
		}
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.recuperarDatosBienModifica");
		}
	}
	
	/**
     * Lista las Metas(Acciones)  del Requerimiento No Programado (Filtro solo registradas).
     * @param Map<String, Object> parmt 
     * @return Collection -Lista de Metas 
     */
	@Override
	public String registrarMetasRqnp(Map<String, Object> params) {
		if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.registrarMetasRqnp");
	
		String cod_req =(String) params.get ("codigo_rqnp");
		String anio_ejec =(String) params.get ("anio_ejec");
		String cod_bien =(String) params.get ("cod_bien");
		String user = (String) params.get("user");
		log.debug("cod_bien "+cod_bien);
		String txtBien[] =(String[]) params.get("txtBien");
		log.debug(" bien size :" + txtBien.length );
		String txtCantidad[] =(String[]) params.get("txtCantidad");
		String txtxCantidad[] =(String[]) params.get("txtxCantidad");
		String txtMonto[] =(String[]) params.get("txtMonto");
		String txtSecuencia[] =(String[]) params.get("txtSecuencia");
		Boolean flagUpdate = true;
		
		try {
			DataSource dataSource = obtenerDataSource("sig", false);
			BigDecimal cantidad = new BigDecimal(0);
			
			if (txtBien !=null){
				////para datos igual a 1 meta
				if (txtBien.length==1){
					log.debug("una meta");
					for (int i =0 ; i < txtxCantidad.length; i++){
						RequerimientoNoProgMetasBean metas = new RequerimientoNoProgMetasBean();
						metas.setCodigoRqnp(cod_req);
						metas.setCodigoBien(cod_bien);
						metas.setAnioEjec(anio_ejec);
						metas.setSecuenciaMeta(txtSecuencia[i]);
						metas.setMontoSoles(new BigDecimal(txtMonto[i]).setScale(2, RoundingMode.HALF_UP));
						//log.debug( "Cantidadx " + txtxCantidad[i]) ;
						metas.setCantidadTotal(new BigDecimal(txtxCantidad[i]));
						cantidad=cantidad.add(new BigDecimal(txtxCantidad[i]));
						log.debug("cod_bien "+cod_bien + " txt_bien" + txtBien[i]);
						if (!cod_bien.substring(0, 1).equals("S") || !txtxCantidad[i].equals("1")) {
                        	flagUpdate = false;
							if (txtBien[i].equals(cod_bien)){
								requerimientoNoProgMetasDao.update(metas);
							}else{
								if((! txtMonto[i].equals("0")) && (! txtMonto[i].equals("0.00")) ){
									requerimientoNoProgMetasDao.insertar(metas);
								}
							}
						}
					}
				}else{
					log.debug("MAYORRRRRRRRRRRRRRRRRR");
					for (int i =0 ; i < txtxCantidad.length; i++){
						RequerimientoNoProgMetasBean metas = new RequerimientoNoProgMetasBean();
						metas.setCodigoRqnp(cod_req);
						metas.setCodigoBien(cod_bien);
						metas.setAnioEjec(anio_ejec);
						metas.setSecuenciaMeta(txtSecuencia[i]);
						metas.setMontoSoles(new BigDecimal(txtMonto[i]).setScale(2, RoundingMode.HALF_UP));
						log.debug(txtBien[i]+ " " + txtSecuencia[i] +  " " + txtxCantidad[i] +"---------------------" ) ;
						metas.setCantidadTotal(new BigDecimal(txtxCantidad[i]));
						cantidad=cantidad.add(new BigDecimal(txtxCantidad[i]));
						log.debug((Object)("cod_bien " + cod_bien + " txt_bien" + txtBien[i]));
						if (!cod_bien.substring(0, 1).equals("S") || !txtxCantidad[i].equals("1")) {
							flagUpdate = false;
							if (txtBien[i].equals(cod_bien)){
								requerimientoNoProgMetasDao.update(metas);
							}else{
								if((! txtBien[i].equals("0")) && (! txtBien[i].equals("0.00")) ){
									requerimientoNoProgMetasDao.insertar(metas);
								}
							}
						}
					}
				}
				////par mas de un meta
				
				RequerimientoNoProgBienesBean bien = new RequerimientoNoProgBienesBean();
				bien.setCodigoRqnp(cod_req);
				bien.setCodigoBien(cod_bien);
				bien.setCantBien(cantidad);
				
				if (!flagUpdate.booleanValue()) {
					accesoDao.setUsuarioAcceso(dataSource,"USER", user);
					requerimientoNoProgBienesDao.updateCantidades(dataSource, bien);
					accesoDao.setUsuarioAccesoNull(dataSource,"USER");
				}
			}
		
		}catch (Exception ex) {
			   log.error("Error en RequerimientoNoProgramadoServiceImpl.registrarMetasRqnp: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
			} 
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.registrarMetasRqnp");
		}
		
		return cod_req;
	}
	
	
	/**
     * Lista las Metas(Acciones)  del Requerimiento No Programado (Filtro solo registradas).
     * @param Map<String, Object> parmt 
     * @return Collection -Lista de Metas 
     */
	@Override
	public String registrarMetasRqnpAUC(Map<String, Object> params) {
		if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.registrarMetasRqnpAUC");
	
		String cod_req =(String) params.get ("codigo_rqnp");
		String anio_ejec =(String) params.get ("anio_ejec");
		String cod_bien =(String) params.get ("codigo_bien");
		String user = (String) params.get("user");
		log.debug("cod_bien "+cod_bien);
		String txtBien[] =(String[]) params.get("txtBien");
		log.debug(" bien size :" + txtBien.length );
		String txtCantidad[] =(String[]) params.get("txtCantidad");
		String txtxCantidad[] =(String[]) params.get("txtxCantidad");
		String txtMonto[] =(String[]) params.get("txtMonto");
		String txtSecuencia[] =(String[]) params.get("txtSecuencia");
		String flagExcel =(String) params.get ("flagExcel");
		
		try {
			DataSource dataSource = obtenerDataSource("sig", false);
			BigDecimal cantidad = new BigDecimal(0);
			
			if (txtBien !=null){
			
				////para datos igual a 1 meta
				if (txtBien.length==1){
					log.debug("una meta");
					log.debug("txtxCantidad.length: "+txtxCantidad.length);
					for (int i =0 ; i < txtxCantidad.length; i++){
						RequerimientoNoProgMetasBean metas = new RequerimientoNoProgMetasBean();
						metas.setCodigoRqnp(cod_req);
						metas.setCodigoBien(cod_bien);
						metas.setAnioEjec(anio_ejec);
						
						log.debug("txtCantidad("+i+"): "+txtCantidad[i]);
						log.debug("txtxCantidad("+i+"): "+txtxCantidad[i]);
						log.debug("txtMonto("+i+"): "+txtMonto[i]);
						log.debug("Secuencia("+i+"): "+txtSecuencia[i]);
						
						metas.setSecuenciaMeta(txtSecuencia[i]);
						metas.setMontoSoles(new BigDecimal(txtMonto[i]).setScale(2, RoundingMode.HALF_UP));
						//log.debug( "Cantidadx " + txtxCantidad[i]) ;
						metas.setCantidadTotal(new BigDecimal(txtxCantidad[i]));
						cantidad=cantidad.add(new BigDecimal(txtxCantidad[i]));
						log.debug("cod_bien "+cod_bien + " txt_bien" + txtBien[i]);
						if (txtBien[i].equals(cod_bien)){
							requerimientoNoProgMetasDao.update(metas);
						}else{
							
							requerimientoNoProgMetasDao.insertar(metas);
						}
					}
				}else{
					log.debug("MAYORRRRRRRRRRRRRRRRRR");
					for (int i =0 ; i < txtxCantidad.length; i++){
						RequerimientoNoProgMetasBean metas = new RequerimientoNoProgMetasBean();
						metas.setCodigoRqnp(cod_req);
						metas.setCodigoBien(cod_bien);
						metas.setAnioEjec(anio_ejec);
						metas.setSecuenciaMeta(txtSecuencia[i]);
						metas.setMontoSoles(new BigDecimal(txtMonto[i]).setScale(2, RoundingMode.HALF_UP));
						log.debug(txtBien[i]+ " " + txtSecuencia[i] +  " " + txtxCantidad[i] +"---------------------" ) ;
						metas.setCantidadTotal(new BigDecimal(txtxCantidad[i]));
						cantidad=cantidad.add(new BigDecimal(txtxCantidad[i]));
						
						if (txtBien[i].equals(cod_bien)){
							requerimientoNoProgMetasDao.update( metas);
						}else{
							requerimientoNoProgMetasDao.insertar(metas);
						}
					}
				}
				////par mas de un meta
				
				RequerimientoNoProgBienesBean bien = new RequerimientoNoProgBienesBean();
				String cantBien=requerimientoNoProgMetasDao.obtenerCantBien(params);
				log.debug("cantBien: "+cantBien);
				
				bien.setCodigoRqnp(cod_req);
				bien.setCodigoBien(cod_bien);
				
				if(flagExcel.equals("S")){
					bien.setCantBien(cantidad);
				}else{
					bien.setCantBien(new BigDecimal(cantBien));
				}
				
				accesoDao.setUsuarioAcceso(dataSource,"USER", user);
				requerimientoNoProgBienesDao.updateCantidades(dataSource, bien);
				accesoDao.setUsuarioAccesoNull(dataSource,"USER");
			}
		
		}catch (Exception ex) {
			   log.error("Error en RequerimientoNoProgramadoServiceImpl.registrarMetasRqnpAUC: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
			} 
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.registrarMetasRqnpAUC ");
		}
		
		return cod_req;
	}
	/**
	 * @author dporrasc
     * Registra el Monto Total del Requerimiento No Programado .
     * @param Map<String, Object> params 
     * @return BigDecimal - Monto Total 
     */
	@Override
	public BigDecimal registrarCabMonto(Map<String, Object> params) {
		if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.registrarCabMonto");
		RequerimientoNoProgramadoBean rqnp= new RequerimientoNoProgramadoBean();
		 List<Map<String, Object>> listaBienes = new ArrayList<Map<String,Object>>();
		 BigDecimal suma = new BigDecimal(0);
		String cod_req =(String) params.get ("codigo_rqnp");
		rqnp=(RequerimientoNoProgramadoBean) params.get ("rqnp");
		listaBienes = (List<Map<String, Object>>) rqnp.getListaBienes();
		
		try {
				
			BigDecimal cantidad = new BigDecimal(0);
			BigDecimal precio = new BigDecimal(0);
			BigDecimal monto = new BigDecimal(0);
			
			for(Map<String, Object> obj: listaBienes){
				cantidad =(BigDecimal)obj.get("cantBien");
				precio =(BigDecimal)obj.get("precioUnid");
				monto=precio.multiply(cantidad );
				suma= suma.add(monto.setScale(2, RoundingMode.HALF_UP));
			}
			rqnp.setMontoRqnp(suma);
			rqnp.setCodigoRqnp(cod_req);
			
			requerimientoNoProgramadoDao .updateMonto(rqnp);
		
		}catch (Exception ex) {
			   log.error("Error en RequerimientoNoProgramadoServiceImpl.registrarCabMonto: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
			} 
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.registrarCabMonto");
		}
		
	return suma;
		
	}
	
	/**
     * Registra el objeto(Bien-Servicio) Total del Requerimiento No Programado .
     * @param Map<String, Object> params 
     * @return BigDecimal - Monto Total 
     */
	@Override
	public void registrarCabObjeto(Map<String, Object> params) {
		if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.registrarCabObjeto");
		RequerimientoNoProgramadoBean rqnp= new RequerimientoNoProgramadoBean();
		
		String user = (String) params.get("user");
		String cod_req =(String) params.get ("codigo_rqnp");
		String cod_objeto =(String) params.get ("cod_objeto");
		
		try {
				
			DataSource dataSource = obtenerDataSource("sig", false);
			
			rqnp.setCod_objeto(cod_objeto);
			rqnp.setCodigoRqnp(cod_req);
			
			accesoDao.setUsuarioAcceso(dataSource,"USER", user);
			requerimientoNoProgramadoDao .updateCodObjeto(dataSource, rqnp);
			accesoDao.setUsuarioAccesoNull(dataSource,"USER");
		
		}catch (Exception ex) {
			   log.error("Error en RequerimientoNoProgramadoServiceImpl.registrarCabObjeto: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
			} 
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.registrarCabObjeto");
		}
		
	}
	
	/**
     * Registra el Monto Total del Requerimiento No Programado .
     * @param Map<String, Object> params 
     * @return BigDecimal - Monto Total 
     */
	@Override
	public String registrarAnularRqnp(Map<String, Object> params) {
		if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.anularRqnp");
		RequerimientoNoProgramadoBean rqnp= new RequerimientoNoProgramadoBean();
		List<Map<String, Object>> listaBienes = new ArrayList<Map<String,Object>>();
		Map<String , Object> parm = new HashMap<String, Object>();
		String cod_req =(String) params.get ("codigo_rqnp");
		String user =(String) params.get ("user");
		String motivo_anulacion =(String) params.get ("motivo_anulacion");
		
		try {
			rqnp= this.recuperarRqnp(cod_req);
			listaBienes = (List<Map<String, Object>>) rqnp.getListaBienes();
			DataSource dataSource = obtenerDataSource("sig", false);
			rqnp.setEstadoRqnp("02");//ANULADO
			rqnp.setCodigoRqnp(cod_req);
			rqnp.setFec_rech(new Date());
			rqnp.setMotivoRechazo(motivo_anulacion);
			
			accesoDao.setUsuarioAcceso(dataSource,"USER", user);
			requerimientoNoProgramadoDao.updateAnula(dataSource, rqnp);
			accesoDao.setUsuarioAccesoNull(dataSource,"USER");
			for (Map<String,Object> obj: listaBienes){
				RequerimientoNoProgBienesBean bien = new RequerimientoNoProgBienesBean();
				bien.setCodigoRqnp(cod_req);
				bien.setCodigoBien((String) obj.get("codigoBien"));
				bien.setInd_estado("10");//ANULADO
					
				accesoDao.setUsuarioAcceso(dataSource,"USER", user);
				requerimientoNoProgBienesDao.updateEstado(dataSource, bien);
				
				parm.put("anio_pro", rqnp.getAnioProceso());
				//parm.put("cod_sede", rqnp.getCodigoSede());
				parm.put("cod_responsable", rqnp.getCodigoResposanble());
				parm.put("cod_proceso", "249");
				parm.put("cod_accion", "002");
				parm.put("cod_exp", obj.get("num_exp"));
				parm.put("exp_estado", "002");
				parm.put("exp_obs", "");
				if (log.isDebugEnabled())log.debug("crear ACCION>>>>>>>>>>>>>><<");
				requerimientoNoProgBienesDao.crearAccion(parm);
				
				accesoDao.setUsuarioAccesoNull(dataSource,"USER");
			}
		}catch (Exception ex) {
			   log.error("Error en RequerimientoNoProgramadoServiceImpl.anularRqnp: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
			} 
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.anularRqnp");
		}
		return cod_req;
	}
	
	
	/**
     * Registra el envio a las bandeja de Jefe Inmediato del Requerimiento No Programado .
     * @param Map<String, Object> params 
     * @return BigDecimal - Código de RQNP
     */
	@Override
	public String registrarEnviarRqnp(Map<String, Object> params) throws  ServiceException{
		if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.enviarRqnp");
		RequerimientoNoProgramadoBean rqnp= new RequerimientoNoProgramadoBean();
		String cod_solicita ;
		List<Map<String, Object>> listaBienes = new ArrayList<Map<String,Object>>();
		
		Map<String, Object> tipoRuta = new HashMap<String, Object>();
		
		Map<String, Object> parm = new HashMap<String, Object>();
		String cod_req =(String) params.get ("codigo_rqnp");
		//=(String) params.get ("cod_solicita");
		String ls_intendente="";
		String ls_es_intendente="";
		String ls_jefe="";
		String ls_jefe_auc="";
		String cod_ruta="";
		String ind_especializado="";
		String  cod_auc=""; 
		StringBuilder ls_bien=new StringBuilder("");
		String user =(String) params.get ("user");
		boolean isJefe=(Boolean )params.get("isJefe");
		boolean is_Auc=false;
		DependenciaBean dependencia = null;
		 //
		try {
			rqnp= this.recuperarRqnp(cod_req);
			cod_solicita= rqnp.getCodigoResposanble();
			listaBienes = (List<Map<String, Object>>) rqnp.getListaBienes();
			DataSource dataSource = obtenerDataSource("sig", false);
			
			rqnp.setEstadoRqnp("03");//APROBACION
			rqnp.setCodigoRqnp(cod_req);
			rqnp.setFechaEnvioSolicitud(new Date());
			
			accesoDao.setUsuarioAcceso(dataSource,"USER", user);
			requerimientoNoProgramadoDao.updateEnvio(dataSource, rqnp);
			accesoDao.setUsuarioAccesoNull(dataSource,"USER");
			
			for (Map<String,Object> obj: listaBienes){
				RequerimientoNoProgBienesBean bien = new RequerimientoNoProgBienesBean();
				bien=requerimientoNoProgBienesDao.recuperarRqnpBienes(cod_req, (String)obj.get("codigoBien"));
				ind_especializado = (String) obj.get("ind_especializado"); 
				cod_ruta =bien.getTipo_ruta();
				cod_auc= bien.getAuct1();
				log.debug("cod_ruta " + cod_ruta);
				log.debug("cod_auc " + cod_auc);
				
				ls_intendente		=maestroPersonalDao.obtenerAprobadorAUC(cod_auc);
				ls_jefe				=maestroPersonalDao.obtenerJefeInmediato(cod_solicita);
				ls_jefe_auc			=maestroPersonalDao.obtenerJefeAuc(cod_auc);
				ls_es_intendente	=maestroPersonalDao.esAprobador(cod_solicita).trim();
				
				//ls_intendente=((ls_intendente==null)?"":ls_intendente);
				//ls_jefe=((ls_jefe==null)?"":ls_jefe);
				//ls_jefe_auc=((ls_jefe_auc==null)?"":ls_jefe_auc);
				//ls_es_intendente=((ls_jefe==null)?"":ls_es_intendente);
				
				if (log.isDebugEnabled()){log.debug("cod_solicita: "+cod_solicita);}
				if (log.isDebugEnabled()){log.debug("ls_intendente: "+ls_intendente);}
				if (log.isDebugEnabled()){log.debug("ls_jefe: "+ls_jefe);}
				if (log.isDebugEnabled()){log.debug("ls_jefe_auc: "+ls_jefe_auc);}
				if (log.isDebugEnabled()){log.debug("ind_especializado: "+ind_especializado);}
				
				String ls_es_aprobador_encargado=maestroPersonalDao.verificaEncargo(ls_intendente,cod_solicita);
				String ls_es_jefe_encargado=maestroPersonalDao.verificaEncargo(ls_jefe,cod_solicita);
					
				if(ls_es_aprobador_encargado.equals("S")){
					if(ls_intendente.equals(ls_jefe_auc)){
						ls_jefe_auc=cod_solicita;
					}
					ls_intendente=cod_solicita;
					ls_es_intendente ="S";
				}
				
				if(ls_es_jefe_encargado.equals("S")){
					if(ls_jefe.equals(ls_jefe_auc)){
						ls_jefe_auc=cod_solicita;
					}
					ls_jefe=cod_solicita;
				}
				
				if(ls_jefe.equals(ls_jefe_auc)){
					if(bien.getTipo_ruta().equals("04")){
						MaestroPersonalBean jefe= maestroPersonalDao.obtenerPersonaxCodigo(ls_jefe);
						bien.setInd_estado("02");//ENVIADO JEFE INMEDIATO COMO AUC
						parm.put("cod_accion", "002");
						parm.put("exp_obs", "REQUERIMIENTO ENVIADO AL JEFE "+jefe.getUuoo()+ " - " + jefe.getDependencia() + "  PARA SU FORMULACIÓN");
					}else{
						bien.setCod_jefe(cod_solicita);
						bien.setInd_estado("02");//ENVIADO AUCT
						parm.put("cod_accion", "002");
						dependencia =dependenciaDao.obtenerDependencia(bien.getAuct1());
						parm.put("exp_obs", "REQUERIMIENTO ENVIADO A: " +dependencia.getUuoo() + " - "+ dependencia.getNom_corto());
						is_Auc=true;
					}
				}else {
					if(ls_jefe.equals(ls_intendente)){
						MaestroPersonalBean jefe= maestroPersonalDao.obtenerPersonaxCodigo(ls_jefe);
						bien.setCod_jefe(ls_jefe);
						bien.setInd_estado("04");//ENVIADO INTENDENCIA
						parm.put("cod_accion", "002");
						parm.put("exp_obs", "REQUERIMIENTO ENVIADO AL JEFE UUOO SUPERIOR DE "+ jefe.getUuoo()+ " - " + jefe.getDependencia() +"  PARA SU APROBACIÓN");
					}else if (cod_solicita.equals(ls_jefe)){
						bien.setCod_jefe(cod_solicita);
						bien.setInd_estado("02");//ENVIADO AUCT
						parm.put("cod_accion", "002");
						dependencia =dependenciaDao.obtenerDependencia(bien.getAuct1());
						parm.put("exp_obs", "REQUERIMIENTO ENVIADO A: " +dependencia.getUuoo() + " - "+ dependencia.getNom_corto());
						is_Auc=true;
					}else if(bien.getTipo_ruta().equals("04")){
						MaestroPersonalBean jefe= maestroPersonalDao.obtenerPersonaxCodigo(ls_jefe);
						bien.setInd_estado("02");//ENVIADO JEFE INMEDIATO COMO AUC
						parm.put("cod_accion", "002");
						parm.put("exp_obs", "REQUERIMIENTO ENVIADO AL JEFE "+jefe.getUuoo()+ " - " + jefe.getDependencia() + "  PARA SU FORMULACIÓN");
					}else{
						MaestroPersonalBean jefe= maestroPersonalDao.obtenerPersonaxCodigo(ls_jefe);
						bien.setInd_estado("03");//ENVIADO JEFE INMEDIATO
						parm.put("cod_accion", "002");
						parm.put("exp_obs", "REQUERIMIENTO ENVIADO AL J. INMEDIATO DE "+jefe.getUuoo()+ " - " + jefe.getDependencia() + "  PARA SU APROBACIÓN");
					}
				}
		
				if (ls_bien.toString().equals("")){
					ls_bien.append((String) obj.get("codigoBien"));
				}else{
					ls_bien.append(","+(String) obj.get("codigoBien"));
				}
				
				accesoDao.setUsuarioAcceso(dataSource,"USER", user);
				requerimientoNoProgBienesDao.updateEstado(dataSource, bien);
				
				parm.put("anio_pro", rqnp.getAnioProceso());
				//parm.put("cod_sede", rqnp.getCodigoSede());
				parm.put("cod_responsable", rqnp.getCodigoResposanble());
				parm.put("cod_proceso", "249");
				
				parm.put("cod_exp", obj.get("num_exp"));
				parm.put("exp_estado", "001");
				requerimientoNoProgBienesDao.crearAccion(parm);
				
				accesoDao.setUsuarioAccesoNull(dataSource,"USER");
			}
			if (is_Auc){
				requerimientoNoProgramadoDao.envioMailUct(cod_req, ls_bien.toString());
			}else{
				requerimientoNoProgramadoDao.envioMailDerivar(cod_req,ls_bien.toString());
			}
		}
		
		catch(NullPointerException ex){
			log.error("Error Objeto nulo en RequerimientoNoProgramadoServiceImpl.enviarRqnp: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);
		}
		catch (Exception ex) {
			log.error("Error en RequerimientoNoProgramadoServiceImpl.enviarRqnp: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);
			} 
		
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.enviarRqnp");
		}
		return cod_req;
	}
	
	//DPORRASC - ENVIAR PARA RUTA 04
	/**
     * Registra el envio a las bandeja de Jefe Inmediato del Requerimiento No Programado .
     * @param Map<String, Object> params 
     * @return BigDecimal - Código de RQNP
     */
	@Override
	public String registrarEnviarRqnpAuAuc(Map<String, Object> params) {
		if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.enviarRqnp");
		RequerimientoNoProgramadoBean rqnp= new RequerimientoNoProgramadoBean();
		String cod_solicita ;
		List<Map<String, Object>> listaBienes = new ArrayList<Map<String,Object>>();
		Map<String, Object> parm = new HashMap<String, Object>();
		String cod_req =(String) params.get ("codigo_rqnp");
		//=(String) params.get ("cod_solicita");
		String ls_intendente="";
		//String ls_super_intendente;
		String ls_jefe="";
		String cod_ruta="";
		String ind_especializado="";
		String  cod_auc=""; 
		StringBuilder ls_bien=new StringBuilder("");
		String user =(String) params.get ("user");
		boolean isJefe=(Boolean )params.get("isJefe");
		boolean is_Auc=false;
		DependenciaBean dependencia = null;
		 //
		try {
			rqnp= this.recuperarRqnp(cod_req);
			cod_solicita= rqnp.getCodigoResposanble();
			listaBienes = (List<Map<String, Object>>) rqnp.getListaBienes();
			DataSource dataSource = obtenerDataSource("sig", false);
			rqnp.setEstadoRqnp("02");//APROBACION Y ATENCION PARA RUTA 04
			rqnp.setCodigoRqnp(cod_req);
			rqnp.setFechaEnvioSolicitud(new Date());
			
			accesoDao.setUsuarioAcceso(dataSource,"USER", user);
			requerimientoNoProgramadoDao.updateEnvio(dataSource, rqnp);
			accesoDao.setUsuarioAccesoNull(dataSource,"USER");
			
			for (Map<String,Object> obj: listaBienes){
				RequerimientoNoProgBienesBean bien = new RequerimientoNoProgBienesBean();
				bien=requerimientoNoProgBienesDao.recuperarRqnpBienes(cod_req, (String)obj.get("codigoBien"));
				ind_especializado = (String) obj.get("ind_especializado"); 
				cod_ruta =bien.getTipo_ruta();
				cod_auc= bien.getAuct1();
				log.debug("cod_ruta " + cod_ruta);
				log.debug("cod_auc " + cod_auc);
				
				//	ls_super_intendente = maestroPersonalDao.obtenerSuperIntendente(cod_solicita);
				//	ls_intendente		=maestroPersonalDao.obtenerAprobador(cod_solicita) ;
				ls_intendente		=maestroPersonalDao.obtenerAprobadorAUC(cod_auc) ;
				ls_jefe				=maestroPersonalDao.obtenerJefeInmediato(cod_solicita);
					
				if(ls_jefe ==null) { ls_jefe="";}
				if(ls_intendente ==null){ ls_intendente="";}
				if (log.isDebugEnabled()){log.debug("cod_solicita:"+cod_solicita);}
				if (log.isDebugEnabled()){log.debug("ls_intendente:"+ls_intendente);}
				if (log.isDebugEnabled()){log.debug("ls_jefe:"+ls_jefe);}
				if (log.isDebugEnabled()){log.debug("ind_especializado:"+ind_especializado);}
					
				if(	cod_solicita.equals(ls_intendente)	){
					if ( ind_especializado.equals("S")){
						bien.setCod_jefe(cod_solicita);
						bien.setInd_estado("02");//ENVIADO AUCT
						parm.put("cod_accion", "003");
						dependencia =dependenciaDao.obtenerDependencia(bien.getAuct1());
						//parm.put("exp_obs", "REQUERIMIENTO ENVIADO A LA AUC: " +dependencia.getUuoo() + " - "+ dependencia.getNom_corto());
						parm.put("exp_obs", "REQUERIMIENTO ENVIADO A: " +dependencia.getUuoo() + " - "+ dependencia.getNom_corto());
						is_Auc=true;
					}else{
						bien.setCod_jefe(cod_solicita);
						bien.setInd_estado("05");//ENVIADO INA;
						parm.put("cod_accion", "005");
						parm.put("exp_obs", "REQUERIMIENTO ENVIADO A LA DPG-INA");
					}
					//}else if (isJefe==false &&(	ls_jefe.equals(ls_intendente) || ls_jefe.equals(ls_super_intendente)) ){
				}else if (isJefe==false &&(	ls_jefe.equals(ls_intendente) ) ){
							
							MaestroPersonalBean jefe= maestroPersonalDao.obtenerPersonaxCodigo(ls_jefe);
						
							bien.setCod_jefe(ls_jefe);
							bien.setInd_estado("04");//ENVIADO INTENDENCIA
							parm.put("cod_accion", "002");
							parm.put("exp_obs", "REQUERIMIENTO ENVIADO AL JEFE UUOO SUPERIOR DE "+ jefe.getUuoo()+ " - " + jefe.getDependencia() +"  PARA SU APROBACIÓN");
							
				}else if( isJefe  ){//|| ls_jefe.equals(ls_super_intendente)
					//SE CAMIO YA QUE EL JEFE DEL SOLICITANTE GENERALMENTE NO SERA JEFE DE UUOO DE AUC
					//}else if( isJefe && ( ls_jefe.equals(ls_intendente) )){//|| ls_jefe.equals(ls_super_intendente)
						if ( ind_especializado.equals("S")){
							if (log.isDebugEnabled())log.debug("Jefe lo envia Auct");
							bien.setCod_jefe(cod_solicita);
							bien.setInd_estado("02");//ENVIADO AUCT
							parm.put("cod_accion", "003");
							dependencia =dependenciaDao.obtenerDependencia(bien.getAuct1());
							//parm.put("exp_obs", "REQUERIMIENTO ENVIADO A LA AUC: " + dependencia.getUuoo() + " - "+ dependencia.getNom_corto());
							parm.put("exp_obs", "REQUERIMIENTO ENVIADO A: " + dependencia.getUuoo() + " - "+ dependencia.getNom_corto());
							is_Auc=true;
						}
				}else{
					MaestroPersonalBean jefe= maestroPersonalDao.obtenerPersonaxCodigo(ls_jefe);
					bien.setInd_estado("03");//ENVIADO JEFE INMEDIATO
					parm.put("cod_accion", "002");
					parm.put("exp_obs", "REQUERIMIENTO ENVIADO AL J. INMEDIATO DE "+jefe.getUuoo()+ " - " + jefe.getDependencia() + "  PARA SU APROBACIÓN");
				}
				if (ls_bien.toString().equals("")){
					ls_bien.append((String) obj.get("codigoBien"));
				}else{
					ls_bien.append(","+(String) obj.get("codigoBien"));
				}
					
				accesoDao.setUsuarioAcceso(dataSource,"USER", user);
				requerimientoNoProgBienesDao.updateEstado(dataSource, bien);
					
				parm.put("anio_pro", rqnp.getAnioProceso());
				//parm.put("cod_sede", rqnp.getCodigoSede());
				parm.put("cod_responsable", rqnp.getCodigoResposanble());
				parm.put("cod_proceso", "249");
				
				parm.put("cod_exp", obj.get("num_exp"));
				parm.put("exp_estado", "001");
				requerimientoNoProgBienesDao.crearAccion(parm);
					
				accesoDao.setUsuarioAccesoNull(dataSource,"USER");
			}
			if (is_Auc){
				requerimientoNoProgramadoDao.envioMailUct(cod_req, ls_bien.toString());
			}else{
				requerimientoNoProgramadoDao.envioMailDerivar(cod_req,ls_bien.toString());
			}
		}catch (Exception ex) {
			   log.error("Error en RequerimientoNoProgramadoServiceImpl.enviarRqnp: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
			} 
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.enviarRqnp");
		}
		return cod_req;
	}
	//DPORRASC - FIN ENVIAR PARA RUTA 04
	/**
     * Recuperar Ítems para la aprobación del Jefe Inmediato .
     * @param Map<String, Object> params 
     * @return Collection - lista de items
     */
	@Override
	public Collection recuperarBienesJefeInmediato(Map<String, Object> params) {
		if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.recuperarBienesJefeInmediato");
		List<Map<String, Object>> lsbien = new ArrayList<Map<String,Object>>();
		List<RqnpBandejaBean> lista = new ArrayList<RqnpBandejaBean>();
		Map<String, Object> map;
		char ini=' ';
		char ini2= "'".charAt(0) ;
		char salto =(char)10;
		char retorno =(char)13;
		char espacio =(char)32;
		
		try {
			
			 lista= (List<RqnpBandejaBean>)rqnpBandejaDao.listarBienesJefeInmediato(params);
			 if (lista !=null){
         			
				for(RqnpBandejaBean obj:lista){
					map= new HashMap<String, Object>();
					BigDecimal exceso = new BigDecimal(0);
					BigDecimal total = new BigDecimal(0);
					map.put("id", obj.getCod_rqnp()+ obj.getCod_bien());
					//map.put("auct_modifico", obj.getAuct_modifico());
					map.put("cantidad", obj.getCantidad().setScale(0, RoundingMode.HALF_UP));
					//map.put("cod_auct_mod", obj.getCod_auct_mod());
					map.put("cod_bien", obj.getCod_bien());
					//map.put("cod_bien_orig", obj.getCod_bien_orig());
					//map.put("cod_clas", obj.getCod_clas());
					//map.put("cod_dep", obj.getCod_dep());
					//map.put("cod_estado", obj.getCod_estado());
					//map.put("cod_resp", obj.getCod_resp());
					map.put("Cod_rqnp", obj.getCod_rqnp());
					map.put("cod_rqnp_ext", obj.getCod_rqnp_ext());
					//map.put("cod_unid", obj.getCod_unid());
					//map.put("cod_user_mod", obj.getCod_user_mod());
					///////map.put("des_bien", obj.getDes_bien().replace( '"',ini).replace('/', ini).replace('°', ini).replace(ini2, ini).replace(salto, espacio).replace(retorno, espacio) );
					map.put("des_bien", obj.getDes_bien().replace( '"',ini).replace('/', ini).replace('°', ini).replace(ini2, ini).replace(salto, espacio).replace(retorno, espacio) );
					//map.put("des_bien_orig", obj.getDes_bien_orig());
					map.put("des_estado", obj.getDes_estado());
					//map.put("fec_modifico", obj.getFec_modifico());
					map.put("fec_rqnp",new SimpleDateFormat("dd/MM/yyyy HH:mm").format(obj.getFec_rqnp()) );
					map.put("motivo_sol", obj.getMotivo_sol().replace( '"',ini).replace('/', ini).replace('°', ini).replace(ini2, ini).replace(salto, espacio).replace(retorno, espacio) );
					map.put("nom_sol", obj.getNom_sol());
					//map.put("obs_rechazo", obj.getObs_rechazo());
					map.put("prec_bien", obj.getPrec_bien().setScale(2, RoundingMode.HALF_UP).toString());
					map.put("prec_total", obj.getPrec_total().setScale(2, RoundingMode.HALF_UP).toString());
					map.put("unid_bien", obj.getUnid_bien());
					//map.put("unid_bien_orig", obj.getUnid_bien_orig());
					//map.put("user_modifco", obj.getUser_modifco());
					map.put("uuoo_sol", obj.getUuoo_sol());
					map.put("ind_especializado", obj.getInd_especializado());
					map.put("saldo", obj.getSaldo().setScale(2, RoundingMode.HALF_UP).toString() );
					//map.put("saldo","");
					map.put("name_auct", obj.getName_auct());
					map.put("numeroArchivo", obj.getNumeroArchivo());
					map.put("anio_atencion", obj.getAnio_atencion());
					map.put("mes_atencion", obj.getMes_atencion());
					
					total = obj.getPrec_total().setScale(2, RoundingMode.HALF_UP) ;
					exceso = obj.getSaldo().setScale(2, RoundingMode.HALF_UP).subtract(total);
					
					if (exceso.doubleValue()<0 ){
						exceso =exceso.multiply(new BigDecimal(-1));
					}else if (exceso.doubleValue()>0){
						exceso= new BigDecimal(0);
					}
					map.put("exceso", exceso.setScale(2, RoundingMode.HALF_UP).toString() );
					
					lsbien.add(map);
				}
			 }
			 
			 return lsbien;
		
		}catch (Exception ex) {
			   log.error("Error en RequerimientoNoProgramadoServiceImpl.recuperarBienesJefeInmediato: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
			} 
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.recuperarBienesJefeInmediato");
		}
	}
	
	
	/**
     * Recuperar Ítems para la aprobación del Jefe UBG .
     * @param Map<String, Object> params 
     * @return Collection - lista de items
     */
	@Override
	public Collection recuperarBienesJefeUBG(Map<String, Object> params) {
		if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.recuperarBienesJefeUBG");
		List<Map<String, Object>> lsbien = new ArrayList<Map<String,Object>>();
		List<RqnpBandejaBean> lista = new ArrayList<RqnpBandejaBean>();
		Map<String, Object> map;
		char ini=' ';
		char ini2= "'".charAt(0) ;
		try {
			///------------>>>>>>>>>>>>>>>
			 lista= (List<RqnpBandejaBean>)rqnpBandejaDao.listarBienesJefeUBG(params);
			 if (lista !=null){
				 
				for(RqnpBandejaBean obj:lista){
					map= new HashMap<String, Object>();
					BigDecimal exceso = new BigDecimal(0);
					BigDecimal total = new BigDecimal(0);
					map.put("id", obj.getCod_rqnp()+ obj.getCod_bien());
					map.put("auct_modifico", obj.getAuct_modifico());
					map.put("cantidad", obj.getCantidad().setScale(0, RoundingMode.HALF_UP));
					map.put("cod_auct_mod", obj.getCod_auct_mod());
					map.put("cod_bien", obj.getCod_bien());
					map.put("cod_bien_orig", obj.getCod_bien_orig());
					map.put("cod_clas", obj.getCod_clas());
					map.put("cod_dep", obj.getCod_dep());
					map.put("cod_estado", obj.getCod_estado());
					map.put("cod_resp", obj.getCod_resp());
					map.put("Cod_rqnp", obj.getCod_rqnp());
					map.put("cod_rqnp_ext", obj.getCod_rqnp_ext());
					map.put("cod_unid", obj.getCod_unid());
					map.put("cod_user_mod", obj.getCod_user_mod());
					map.put("des_bien", obj.getDes_bien().replace( '"',ini).replace('/', ini).replace('°', ini).replace(ini2, ini) );
					map.put("des_bien_orig", obj.getDes_bien_orig());
					map.put("des_estado", obj.getDes_estado());
					map.put("fec_modifico", obj.getFec_modifico());
					map.put("fec_rqnp",new SimpleDateFormat("dd/MM/yyyy HH:mm").format(obj.getFec_rqnp()) );
					map.put("motivo_sol", obj.getMotivo_sol().replace( '"',ini).replace('/', ini).replace('°', ini).replace(ini2, ini));
					map.put("nom_sol", obj.getNom_sol());
					map.put("obs_rechazo", obj.getObs_rechazo());
					map.put("prec_bien", obj.getPrec_bien().setScale(2, RoundingMode.HALF_UP).toString());
					map.put("prec_total", obj.getPrec_total().setScale(2, RoundingMode.HALF_UP).toString());
					map.put("unid_bien", obj.getUnid_bien());
					map.put("unid_bien_orig", obj.getUnid_bien_orig());
					map.put("user_modifco", obj.getUser_modifco());
					map.put("uuoo_sol", obj.getUuoo_sol());
					map.put("ind_especializado", obj.getInd_especializado());
					map.put("saldo", obj.getSaldo().setScale(2, RoundingMode.HALF_UP).toString() );
					map.put("name_auct", obj.getName_auct());
					
					total = obj.getPrec_total().setScale(2, RoundingMode.HALF_UP) ;
					exceso = obj.getSaldo().setScale(2, RoundingMode.HALF_UP).subtract(total);
					
					if (exceso.doubleValue()<0 ){
						exceso =exceso.multiply(new BigDecimal(-1));
					}else if (exceso.doubleValue()>0){
						exceso= new BigDecimal(0);
					}
					map.put("exceso", exceso.setScale(2, RoundingMode.HALF_UP).toString() );
					
					lsbien.add(map);
				}
			 }
			 
			 return lsbien;
		
		}catch (Exception ex) {
			   log.error("Error en RequerimientoNoProgramadoServiceImpl.recuperarBienesJefeUBG: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
			} 
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.recuperarBienesJefeUBG");
		}
	}
	
	/**
     * Recuperar Ítems para la aprobación del Jefe OEC .
     * @param Map<String, Object> params 
     * @return Collection - lista de items
     */
	@Override
	public Collection recuperarBienesJefeOEC(Map<String, Object> params) {
		if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.recuperarBienesJefeOEC");
		List<Map<String, Object>> lsbien = new ArrayList<Map<String,Object>>();
		List<RqnpBandejaBean> lista = new ArrayList<RqnpBandejaBean>();
		Map<String, Object> map;
		char ini=' ';
		char ini2= "'".charAt(0) ;
		char salto =(char)10;
		char retorno =(char)13;
		char espacio =(char)32;
		try {
			///------------>>>>>>>>>>>>>>>
			 lista= (List<RqnpBandejaBean>)rqnpBandejaDao.listarBienesJefeOEC(params);
			 if (lista !=null){
				 
				for(RqnpBandejaBean obj:lista){
					map= new HashMap<String, Object>();
					BigDecimal exceso = new BigDecimal(0);
					BigDecimal total = new BigDecimal(0);
					map.put("id", obj.getCod_rqnp()+ obj.getCod_bien());
					map.put("auct_modifico", obj.getAuct_modifico());
					map.put("cantidad", obj.getCantidad().setScale(0, RoundingMode.HALF_UP));
					map.put("cod_auct_mod", obj.getCod_auct_mod());
					map.put("cod_bien", obj.getCod_bien());
					map.put("cod_bien_orig", obj.getCod_bien_orig());
					map.put("cod_clas", obj.getCod_clas());
					map.put("cod_dep", obj.getCod_dep());
					map.put("cod_estado", obj.getCod_estado());
					map.put("cod_resp", obj.getCod_resp());
					map.put("Cod_rqnp", obj.getCod_rqnp());
					map.put("cod_rqnp_ext", obj.getCod_rqnp_ext());
					map.put("cod_unid", obj.getCod_unid());
					map.put("cod_user_mod", obj.getCod_user_mod());
					map.put("des_bien", obj.getDes_bien().replace( '"',ini).replace('/', ini).replace('°', ini).replace(ini2, ini).replace(salto, espacio).replace(retorno, espacio) );
					map.put("des_bien_orig", obj.getDes_bien_orig());
					map.put("des_estado", obj.getDes_estado());
					map.put("fec_modifico", obj.getFec_modifico());
					map.put("fec_rqnp",new SimpleDateFormat("dd/MM/yyyy HH:mm").format(obj.getFec_rqnp()) );
					map.put("motivo_sol", obj.getMotivo_sol().replace( '"',ini).replace('/', ini).replace('°', ini).replace(ini2, ini).replace(salto, espacio).replace(retorno, espacio));
					map.put("nom_sol", obj.getNom_sol());
					map.put("obs_rechazo", obj.getObs_rechazo());
					map.put("prec_bien", obj.getPrec_bien().setScale(2, RoundingMode.HALF_UP).toString());
					map.put("prec_total", obj.getPrec_total().setScale(2, RoundingMode.HALF_UP).toString());
					map.put("unid_bien", obj.getUnid_bien());
					map.put("unid_bien_orig", obj.getUnid_bien_orig());
					map.put("user_modifco", obj.getUser_modifco());
					map.put("uuoo_sol", obj.getUuoo_sol());
					map.put("ind_especializado", obj.getInd_especializado());
					map.put("saldo", obj.getSaldo().setScale(2, RoundingMode.HALF_UP).toString() );
					map.put("name_auct", obj.getName_auct());
					
					total = obj.getPrec_total().setScale(2, RoundingMode.HALF_UP) ;
					exceso = obj.getSaldo().setScale(2, RoundingMode.HALF_UP).subtract(total);
					
					if (exceso.doubleValue()<0 ){
						exceso =exceso.multiply(new BigDecimal(-1));
					}else if (exceso.doubleValue()>0){
						exceso= new BigDecimal(0);
					}
					map.put("exceso", exceso.setScale(2, RoundingMode.HALF_UP).toString() );
					
					lsbien.add(map);
				}
			 }
			 
			 return lsbien;
		
		}catch (Exception ex) {
			   log.error("Error en RequerimientoNoProgramadoServiceImpl.recuperarBienesJefeOEC: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
			} 
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.recuperarBienesJefeOEC");
		}
		
		
	}
	
	
	/**
     * Recuperar Ítems para la aprobación del Intendente .
     * @param Map<String, Object> params 
     * @return Collection - lista de items
     */
	@Override
	public Collection recuperarBienesIntedente(Map<String, Object> params) {
		if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.recuperarBienesIntedente");
		List<Map<String, Object>> lsbien = new ArrayList<Map<String,Object>>();
		List<RqnpBandejaBean> lista = new ArrayList<RqnpBandejaBean>();
		Map<String, Object> map;
		char ini=' ';
		char ini2= "'".charAt(0) ;
		char salto =(char)10;
		char retorno =(char)13;
		char espacio =(char)32;
		
		try {
			 lista= (List<RqnpBandejaBean>)rqnpBandejaDao.listarBienesIntedente(params);
			 if (lista !=null){
				 
				for(RqnpBandejaBean obj:lista){
					map= new HashMap<String, Object>();
					BigDecimal exceso = new BigDecimal(0);
					BigDecimal total = new BigDecimal(0);
					
					map.put("id", obj.getCod_rqnp()+ obj.getCod_bien());
					map.put("auct_modifico", obj.getAuct_modifico());
					map.put("cantidad", obj.getCantidad().setScale(0, RoundingMode.HALF_UP));
					map.put("cod_auct_mod", obj.getCod_auct_mod());
					map.put("cod_bien", obj.getCod_bien());
					map.put("cod_bien_orig", obj.getCod_bien_orig());
					map.put("cod_clas", obj.getCod_clas());
					map.put("cod_dep", obj.getCod_dep());
					map.put("cod_estado", obj.getCod_estado());
					map.put("cod_resp", obj.getCod_resp());
					map.put("Cod_rqnp", obj.getCod_rqnp());
					map.put("cod_rqnp_ext", obj.getCod_rqnp_ext());
					map.put("cod_unid", obj.getCod_unid());
					map.put("cod_user_mod", obj.getCod_user_mod());
					map.put("des_bien", obj.getDes_bien().replace( '"',ini).replace('/', ini).replace('°', ini).replace(ini2, ini).replace(salto, espacio).replace(retorno, espacio));
					map.put("des_bien_orig", obj.getDes_bien_orig());
					map.put("des_estado", obj.getDes_estado());
					map.put("fec_modifico", obj.getFec_modifico());
					map.put("fec_rqnp",new SimpleDateFormat("dd/MM/yyyy HH:mm").format(obj.getFec_rqnp()) );
					map.put("motivo_sol", obj.getMotivo_sol().replace( '"',ini).replace('/', ini).replace('°', ini).replace(ini2, ini).replace(salto, espacio).replace(retorno, espacio));
					map.put("nom_sol", obj.getNom_sol());
					map.put("obs_rechazo", obj.getObs_rechazo());
					map.put("prec_bien", obj.getPrec_bien().setScale(2, RoundingMode.HALF_UP).toString());
					map.put("prec_total", obj.getPrec_total().setScale(2, RoundingMode.HALF_UP).toString());
					map.put("unid_bien", obj.getUnid_bien());
					map.put("unid_bien_orig", obj.getUnid_bien_orig());
					map.put("user_modifco", obj.getUser_modifco());
					map.put("uuoo_sol", obj.getUuoo_sol());
					map.put("ind_especializado", obj.getInd_especializado());
					map.put("saldo", obj.getSaldo().setScale(2, RoundingMode.HALF_UP).toString() );
					map.put("name_auct", obj.getName_auct());
					map.put("numeroArchivo", obj.getNumeroArchivo());
					map.put("anio_atencion", obj.getAnio_atencion());
					map.put("mes_atencion", obj.getMes_atencion());
					
					total = obj.getPrec_total().setScale(2, RoundingMode.HALF_UP) ;
					exceso = obj.getSaldo().setScale(2, RoundingMode.HALF_UP).subtract(total);
					
					if (exceso.doubleValue()<0 ){
						exceso =exceso.multiply(new BigDecimal(-1));
					}else if (exceso.doubleValue()>0){
						exceso= new BigDecimal(0);
					}
					map.put("exceso", exceso.setScale(2, RoundingMode.HALF_UP).toString() );
					
					lsbien.add(map);
				}
			 }
			 
			 return lsbien;
		
		}catch (Exception ex) {
			   log.error("Error en RequerimientoNoProgramadoServiceImpl.recuperarBienesIntedente: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
			} 
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.recuperarBienesIntedente");
		}
		
		
	}
	
	
	/**
     * Registra la Aprobación de la atención de un ítem del RQNP
     * por parte del Jefe Inmediato o Intendente.
     * @param Map<String, Object> params 
     * @return String - Intendente(S-N)
     */
	@Override
	public String registrarAprobarRqnp(Map<String, Object> params)throws  ServiceException{
		if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.registrarAprobarRqnp");
		//String intendente;
		String ls_intendente;
		String ls_jefe_auc;
		String ls_es_intendente;
		String ls_super_intendente;
		String ls_jefe;
		String ls_ubg_dep;
		String ls_anio 		= "";
		String cod_dep		= "";
		String oec_ini		= "";
		String cod_plaza	= "";
		String oec_sig		= "";
		String ind_fin		= "";
		String ind_saldo	= "";
		String ls_solicitante="";
		String ls_jefe_uuoo ="";
		String ls_encargado ="";
		String ls_estado_bien ="";
		boolean ind_auct;
		Map<String, Object> parm = new HashMap<String, Object>();
		//CatalogoBienesBean bienCatalogo= new CatalogoBienesBean(); 
		String cod_req 		=(String) params.get ("cod_rqnp");
		String cod_aprueba 		=(String) params.get ("cod_aprueba"); //JEFE LOGUEADO
		String cod_bien			=(String) params.get ("cod_bien");
		String ind_especializado	=(String) params.get ("ind_especializado");
		String 	user 		=(String) params.get ("user");
		ls_anio 			=(String) params.get ("anio_pro");
		
		//Recuperando Bien///////////////////////////////////////
		try {
			if (log.isDebugEnabled()) log.debug("cod_aprueba:"+cod_aprueba);
			
			DataSource dataSource = obtenerDataSource("sig", false);
			RequerimientoNoProgBienesBean bien = new RequerimientoNoProgBienesBean();
			RequerimientoNoProgramadoBean rqnp = new RequerimientoNoProgramadoBean();
			
			rqnp = requerimientoNoProgramadoDao.recuperarRqnp(cod_req); 
			bien=requerimientoNoProgBienesDao.recuperarRqnpBienes(cod_req, cod_bien);
			if (bien.getOec_compra()!=null){ //CAMPO:COD_UORESPONSABLE
				oec_ini=bien.getOec_compra();
			}

			ls_super_intendente = maestroPersonalDao.obtenerSuperIntendente(cod_aprueba);
			ls_es_intendente	=maestroPersonalDao.esAprobador(cod_aprueba).trim() ;
			//ls_jefe				=maestroPersonalDao.obtenerJefeInmediato(cod_aprueba);
			ls_jefe				=maestroPersonalDao.obtenerJefeInmediato(rqnp.getCodigoResposanble());
			ls_intendente		=maestroPersonalDao.obtenerAprobadorAUC(bien.getAuct1()) ;
			ls_jefe_auc			=maestroPersonalDao.obtenerJefeAuc(bien.getAuct1());
			
			//Verificando si el aprobador es encargado
			//ls_encargado = maestroPersonalDao.esAprobadorEncargado(cod_aprueba);

			///////////////////////////////////////////////////////////////////
			if (log.isDebugEnabled()) log.debug("ls_es_intendente: "+ls_es_intendente);
			if (log.isDebugEnabled()) log.debug("ls_super_intendente: "+ls_super_intendente);
			if (log.isDebugEnabled()) log.debug("ls_encargado: "+ls_encargado);
			if (log.isDebugEnabled()) log.debug("ls_intendente: "+ls_intendente);
			if (log.isDebugEnabled()) log.debug("ls_jefe_auc: "+ls_jefe_auc);
			if (log.isDebugEnabled()) log.debug("ls_jefe: "+ls_jefe);
			
			String ls_es_aprobador_encargado=maestroPersonalDao.verificaEncargo(ls_intendente,cod_aprueba);
			String ls_es_jefe_encargado=maestroPersonalDao.verificaEncargo(ls_jefe,cod_aprueba);
			
			//String ls_es_aprobador_encargado=maestroPersonalDao.esAprobadorEncargado(cod_aprueba);
			//String ls_es_jefe_encargado=maestroPersonalDao.esJefe(cod_aprueba,rqnp.getCodigoDependencia());
			
			if(ls_es_aprobador_encargado.equals("S")){
				if(ls_intendente.equals(ls_jefe_auc)){
					ls_jefe_auc=cod_aprueba;
				}
				
				ls_intendente=cod_aprueba;
				ls_es_intendente ="S";
			}
			
			if(ls_es_jefe_encargado.equals("S")){
				if(ls_jefe.equals(ls_jefe_auc)){
					ls_jefe_auc=cod_aprueba;
				}
				ls_jefe=cod_aprueba;
			}
			
			ls_solicitante=rqnp.getCodigoResposanble();
			cod_dep = rqnp.getCodigoDependencia();
			
			if(bien.getInd_estado().equals("04") && bien.getInd_auct1()!=null){
			//if((cod_aprueba.equals(ls_super_intendente) || ls_es_intendente.equals("S"))&&(ls_intendente.equals(cod_aprueba))){
				//APROBAR POR INTENDENTE O SUPER INTENDENTE---------------------------
				//PENDIENTE PARA EVALUAR EL SALDO Y RUTAS DE ESTE
				if(ls_intendente.equals(ls_jefe)){
					if(!ls_intendente.equals(ls_jefe_auc)){
						bien.setCod_jefe(cod_aprueba);
						bien.setInd_estado("02");//ENVIADO AUCT						
						parm.put("cod_accion", "003");
						DependenciaBean dependencia=dependenciaDao.obtenerDependencia(bien.getAuct1());
						//parm.put("exp_obs", "REQUERIMIENTO ENVIADO A LA AUC: " +dependencia.getUuoo()+" - "+  dependencia.getNom_corto());
						parm.put("exp_obs", "REQUERIMIENTO ENVIADO A: " +dependencia.getUuoo()+" - "+  dependencia.getNom_corto());
					}else{
						
						parm=this.identificaBandeja(ls_anio, cod_bien, cod_dep, oec_ini, bien,0);
						
						if (parm !=null){
							bien.setCod_jefe(cod_aprueba);
							bien.setInd_estado((String)parm.get ("ind_estado"));
							bien.setInd_estado_alterno((String)parm.get ("ind_estado_alterno"));
							bien.setCod_plaza((String) parm.get("cod_plaza"));
							bien.setOec_compra((String) parm.get("oec_sig"));
						
							parm.put("cod_accion", "005");
						}	
					}
				}else{
					parm=this.identificaBandeja(ls_anio, cod_bien, cod_dep, oec_ini, bien,0);
					if (parm !=null){
						if(bien.getCod_jefe().isEmpty()){
							bien.setCod_jefe(cod_aprueba);
						}
						
						bien.setInd_estado((String)parm.get ("ind_estado"));
						bien.setInd_estado_alterno((String)parm.get("ind_estado_alterno"));
						bien.setCod_plaza((String) parm.get("cod_plaza"));
						bien.setOec_compra((String) parm.get("oec_sig"));
						
						parm.put("cod_accion", "005");
					}	
					
				}

			}else{
			//APROBAR POR JEFE INMEDIATO---------------------------------------------------------------
				bien.setCod_jefe(cod_aprueba);
				if (log.isDebugEnabled())log.debug("jefe inmediato, especializado, enviado a AUCT");
				bien.setInd_estado("02");//ENVIADO AUCT
				parm.put("cod_accion", "003");
				DependenciaBean dependencia=dependenciaDao.obtenerDependencia(bien.getAuct1());
				//parm.put("exp_obs", "REQUERIMIENTO ENVIADO A LA AUC: "+dependencia.getUuoo()+" - "+ dependencia.getNom_corto());
				parm.put("exp_obs", "REQUERIMIENTO ENVIADO A: "+dependencia.getUuoo()+" - "+ dependencia.getNom_corto());
			}

			if (parm !=null){
				accesoDao.setUsuarioAcceso(dataSource,"USER", user);
				String fechEnviOec = (String)parm.get("fech_envi_oec");
                
				if (fechEnviOec != null && fechEnviOec.equals("fech_envi_oec")) {
                	bien.setFechEnviOec(new Date());
                }
				
				requerimientoNoProgBienesDao.updateEstado(dataSource, bien);	
				parm.put("anio_pro", (String) params.get ("anio_pro"));
				//parm.put("cod_sede", (String) params.get ("cod_sede"));
				parm.put("cod_responsable", (String) params.get ("cod_aprueba"));
				parm.put("cod_proceso", "249");		
				parm.put("cod_exp", bien.getNum_exp());
				parm.put("exp_estado", "001");			
				requerimientoNoProgBienesDao.crearAccion(parm);
				accesoDao.setUsuarioAccesoNull(dataSource,"USER");
			}					
		}catch (ServiceException exsrv) {	
			log.error("Error ServiceException en RequerimientoNoProgramadoServiceImpl.registrarAprobarRqnp: " + exsrv.getMessage(), exsrv);
			throw new ServiceException(this, exsrv);
		}catch (Exception ex) {
			   log.error("Error en RequerimientoNoProgramadoServiceImpl.registrarAprobarRqnp: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
			} 
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.registrarAprobarRqnp");
		}
		return ls_es_intendente;
	}
	
	
	
	/**
     * Registra la Aprobación de la atención de un ítem por la UBG
     *  por parte del Jefe UBG
     * @param Map<String, Object> params 
     * @return String - 
     */
	@Override
	public String registrarAprobarUBG(Map<String, Object> params) {
		if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.registrarAprobarUBG");
		
		String cod_aprueba ;
		String cod_bien;
	
		String ls_ubg_dep="";
		String ls_anio 		= "";
		String cod_dep		= "";
		String oec_ini		= "";

		Map<String, Object> parm = new HashMap<String, Object>();
		
		String cod_req 		=(String) params.get ("cod_rqnp");
		cod_aprueba 		=(String) params.get ("cod_aprueba");
		cod_bien			=(String) params.get ("cod_bien");
	
		String 	user 		=(String) params.get ("user");
		ls_anio 			=(String) params.get ("anio_pro");
		
		//Recuperando Bien///////////////////////////////////////
		//
		try {
			if (log.isDebugEnabled())log.debug("cod_aprueba:"+cod_aprueba);
			
			DataSource dataSource = obtenerDataSource("sig", false);
	
			RequerimientoNoProgBienesBean bien = new RequerimientoNoProgBienesBean();
			RequerimientoNoProgramadoBean rqnp = new RequerimientoNoProgramadoBean();
			
			rqnp = requerimientoNoProgramadoDao.recuperarRqnp(cod_req); 
			bien=requerimientoNoProgBienesDao.recuperarRqnpBienes(cod_req, cod_bien);
			if (bien.getOec_compra()!=null){
				oec_ini=bien.getOec_compra();
			}
			
			cod_dep = rqnp.getCodigoDependencia();	
		
			parm=this.identificaBandeja(ls_anio, cod_bien, cod_dep, oec_ini,bien,0);
			bien.setCod_jefe(cod_aprueba);
			bien.setInd_estado((String)parm.get ("ind_estado"));
			bien.setInd_estado_alterno((String)parm.get ("ind_estado_alterno"));
			bien.setCod_plaza((String) parm.get("cod_plaza"));
			bien.setOec_compra((String) parm.get("oec_sig"));
			
			parm.put("cod_accion", "005");	
			
			accesoDao.setUsuarioAcceso(dataSource,"USER", user);
			requerimientoNoProgBienesDao.updateEstado(dataSource, bien);
			
			parm.put("anio_pro", (String) params.get ("anio_pro"));
			//parm.put("cod_sede", (String) params.get ("cod_sede"));
			parm.put("cod_responsable", (String) params.get ("cod_aprueba"));
			parm.put("cod_proceso", "249");
			
			parm.put("cod_exp", bien.getNum_exp());
			parm.put("exp_estado", "001");
			requerimientoNoProgBienesDao.crearAccion(parm);
			accesoDao.setUsuarioAccesoNull(dataSource,"USER");
		}catch (ServiceException ex) {	
			throw new ServiceException((Object)this, (Exception)ex);
		}catch (Exception ex) {
			   log.error("Error en RequerimientoNoProgramadoServiceImpl.registrarAprobarUBG: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
			} 
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.registrarAprobarUBG");
		}
		return ls_ubg_dep;
		
	}
	
	private Map<String, Object> identificaBandeja(String ls_anio,String cod_bien, String cod_dep, String oec_ini , RequerimientoNoProgBienesBean bien , int pasada )
	throws ServiceException{
		Map<String , Object> params = new  HashMap<String, Object>();
		if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.identificaBandeja");
		BigDecimal saldo = new BigDecimal(0);
		BigDecimal monto = bien.getCantBien().multiply(bien.getPrecioUnid() );
		if (log.isDebugEnabled())log.debug("monto : "+ monto.doubleValue());
		String cod_plaza="";
		String oec_sig="";
		String ind_fin="";
		String ind_saldo="";
		String oec_central="";
		String ls_exceso="";
		/////////////////////////////
		String indicador_oec="";
		/////////////////////////////
		
		DependenciaBean dependencia=null;
		//Identificando OEC------------------------------------------------------
		params.put("anio_pro", ls_anio);
		params.put("cod_bien", cod_bien);
		params.put("uuo_dep", cod_dep);
		params.put("oec_ini", oec_ini);
		DataSource dataSource;
		
		try {
			dataSource = obtenerDataSource("sig", false);
			params=rqnpBandejaDao.evaluaOEC(dataSource, params);
			
			cod_plaza	=(String) params.get("cod_plaza");
			oec_sig		=(String) params.get("oec_sig");
			ind_fin		=(String) params.get("ind_fin");
			ind_saldo	=(String) params.get("ind_saldo");
			
			if (log.isDebugEnabled())log.debug("EVALUA OEC >>>>>>> :" + cod_bien + "  cod_plaza:"+cod_plaza + "   oec_sig:" +oec_sig  + " ind_saldo:" + ind_saldo +" ind_fin:"+ind_fin+"------------------");
			saldo=rqnpBandejaDao.saldoCD(params);
			if (log.isDebugEnabled())log.debug("Saldo OEC:" +saldo.doubleValue()+" monto rqnp:"+monto.doubleValue());
			/*ind_saldo="N";
			ind_fin="N";*/
			if (ind_saldo.equals("S") && saldo.doubleValue() >= monto.doubleValue() ){
				//oec central------------------------------------------------
				//(dporrasc)oec_central=rqnpBandejaDao.oecEsCentral(oec_sig);
				indicador_oec=rqnpBandejaDao.obtenerIndicadorOec(oec_sig);
				
				//if (oec_central.equals("S")){
				if (indicador_oec.equals("0") || indicador_oec.equals("") || indicador_oec==null){
					params.put("ind_estado", "05");
					params.put("ind_estado_alterno", "01");
					params.put("exp_obs", "Requerimiento ha sido derivado a la DPG  para su atención." );
					if (log.isDebugEnabled())log.debug("CON SALDO CENTRAL");
					if (log.isDebugEnabled())log.debug("05.01 Requerimiento ha sido derivado a la DPG para su atención.");
				}else{
					params.put("ind_estado", "07");
					params.put("ind_estado_alterno", "01");
					dependencia =dependenciaDao.obtenerDependencia(oec_sig);
					if (dependencia !=null){
						params.put("fech_envi_oec", "fech_envi_oec");
						params.put("exp_obs", "Requerimiento ha sido derivado a la OEC "+ dependencia.getUuoo() + " - "+ dependencia.getNom_corto()+ " para su atención.");
					}
					
					if (log.isDebugEnabled())log.debug("CON SALDO NO CENTRAL");
					if (log.isDebugEnabled())log.debug("07.01 Requerimiento ha sido derivado a la OEC para su atención.");
				}
			}else if (ind_fin.equals("S")){
				//Se permite exceso------------------------------------------
				ls_exceso=sysParametrosDao.permiteExcesoRqnp();
				if(ls_exceso.equals("S")){
					//oec central--------------------------------------------
					indicador_oec=rqnpBandejaDao.obtenerIndicadorOec(oec_sig);
					//oec_central=rqnpBandejaDao.oecEsCentral(oec_sig);
					//if (oec_central.equals("S")){
					if (indicador_oec.equals("0")){
						params.put("ind_estado", "05");
						params.put("ind_estado_alterno", "02");
						params.put("exp_obs", "Requerimiento ha sido derivado a la DPG para determinar la necesidad de su compra directa.");
						if (log.isDebugEnabled())log.debug("SIN SALDO ... REG FINAL ...EXCESO  ...CENTRAL");
						if (log.isDebugEnabled())log.debug("05.02 Requerimiento ha sido derivado a la DPG para determinar la necesidad de su compra directa.");
					}else{
						params.put("ind_estado", "07");
						params.put("ind_estado_alterno", "02");
						dependencia =dependenciaDao.obtenerDependencia(oec_sig);
						if (dependencia !=null){
							params.put("fech_envi_oec", "fech_envi_oec");
							params.put("exp_obs", "Requerimiento ha sido derivado a la OEC "+ dependencia.getUuoo() + " - "+ dependencia.getNom_corto()+ " para determinar la necesidad de su compra directa.");
						}
						
						if (log.isDebugEnabled())log.debug("SIN SALDO ... REG FINAL ..EXCESO  .NOO CENTRAL");
						if (log.isDebugEnabled())log.debug("07.02 Requerimiento ha sido derivado a la OEC para determinar la necesidad de su compra directa.");
					}
				}else{
					params.put("ind_estado", "05");
					params.put("ind_estado_alterno", "01");
					params.put("exp_obs", "Requerimiento ha sido derivado a la DPG para su atención.");
					if (log.isDebugEnabled())log.debug("SIN SALDO ...REG FINAL ... NO EXCESO ");
					if (log.isDebugEnabled())log.debug("05.01 Requerimiento ha sido derivado a la DPG para su atención.");
				}
			}else{
				if (3 >pasada  && 0<=pasada){
					if (log.isDebugEnabled())log.debug("anuevas pasadas");
					pasada++;
					log.debug("PASADA>>>>:"+pasada);
					params=this.identificaBandeja(ls_anio, cod_bien, cod_dep, oec_sig, bien, pasada);
				}else{
					if (log.isDebugEnabled())log.debug("no debio pasar por aqui");
					params=null;
					throw new ServiceException();	
				}

			}
		} catch (Exception e) {	
			e.printStackTrace();
		}finally{
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.identificaBandeja");
		}
		
		
		return params;
	}
	
	
	/**
     * Actualiza el Estado de un item de un RQNP
     * @param Map<String, Object> params 
     * @return String - Intendente(S-N)
     */
	@Override
	public String registrarActualizaEstadoItem(Map<String, Object> params) {
		if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.registrarActualizaEstadoItem");
	
		String 	cod_req 		=(String) params.get ("cod_rqnp");
		String 	cod_bien		=(String) params.get ("cod_bien");
		String 	user 			=(String) params.get ("user");
		String 	ind_estado 		=(String) params.get ("ind_estado");
		
		 //
		try {
			DataSource dataSource = obtenerDataSource("sig", false);
			RequerimientoNoProgBienesBean bien = new RequerimientoNoProgBienesBean();
			bien.setCodigoRqnp(cod_req);
			bien.setCodigoBien(cod_bien);
			bien.setInd_estado(ind_estado);
			accesoDao.setUsuarioAcceso(dataSource,"USER", user);
			requerimientoNoProgBienesDao.updateEstadoOnly(dataSource, bien);
			accesoDao.setUsuarioAccesoNull(dataSource,"USER");
			
		}catch (Exception ex) {
			   log.error("Error en RequerimientoNoProgramadoServiceImpl.registrarActualizaEstadoItem: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
			} 
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.registrarActualizaEstadoItem");
		}
		
		return cod_req;
	}
	
	/**
     * Registra el Rechazo de la atención de un item del RQNP
     *  por parte del Jefe Inmediato o Intendente.
     * @param Map<String, Object> params 
     * @return String - Intendente(S-N)
     */
	@Override
	public String registrarRechazarRqnp(Map<String, Object> params) {
		if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.registrarRechazarRqnp");
		Map<String,Object> parm = new HashMap<String, Object>();
		String cod_aprueba ;
		String cod_Bien;
		String obs_rechazo;
		
		String cod_req 	=(String) params.get ("cod_rqnp");
		cod_aprueba 	=(String) params.get ("cod_aprueba");
		cod_Bien		=(String) params.get ("cod_Bien");
		obs_rechazo		=(String) params.get ("obs_rechazo");
		String user 	=(String) params.get ("user");
		
		try {
			DataSource dataSource = obtenerDataSource("sig", false);
			
			RequerimientoNoProgBienesBean bien = new RequerimientoNoProgBienesBean();
			bien=requerimientoNoProgBienesDao.recuperarRqnpBienes(cod_req, cod_Bien);
			//bien.setCodigoRqnp(cod_req);
			//bien.setCodigoBien(cod_Bien);
			if (bien.getInd_estado().equals("03") ){
				params.put("cod_accion", "003");
				params.put("exp_obs", "REQUERIMIENTO RECHAZADO POR EL JEFE INMEDIATO");
				parm.put("exp_estado", "003");
			}else if(bien.getInd_estado().equals("04") ){
				params.put("cod_accion", "005");
				params.put("exp_obs", "REQUERIMIENTO RECHAZADO JEFE UUOO SUPERIOR");
				parm.put("exp_estado", "002");
			}
			bien.setObs_rechazo(obs_rechazo);
			bien.setFec_rechazo(new Date());
			bien.setCod_jefe(cod_aprueba);
			
			bien.setInd_estado("08");//RECHAZADO
			
			accesoDao.setUsuarioAcceso(dataSource,"USER", user);
			requerimientoNoProgBienesDao.updateRechazo(dataSource, bien);
			
			parm.put("anio_pro", (String) params.get ("anio_pro"));
			//parm.put("cod_sede", (String) params.get ("cod_sede"));
			parm.put("cod_responsable", cod_aprueba);
			parm.put("cod_proceso", "249");
			parm.put("cod_accion", (String) params.get ("cod_accion"));
			parm.put("cod_exp", bien.getNum_exp());
			//parm.put("exp_estado", "003");
			parm.put("exp_obs", (String) params.get ("exp_obs"));
			
			log.debug("ANTES DE crearAccion(dataSource,parm): "+parm.toString());
			requerimientoNoProgBienesDao.crearAccion(parm);
			accesoDao.setUsuarioAccesoNull(dataSource,"USER");
		}catch (Exception ex) {
			   log.error("Error en RequerimientoNoProgramadoServiceImpl.registrarRechazarRqnp: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
			} 
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.registrarRechazarRqnp");
		}
		
		return cod_req;
	}
	
	
	
	
	/**
     * Envio de correo a las UCT, para la atención de los item.
     * @param String cod_rqnp - Código de Rqnp
     * @param String cod_bien - Codigo de ítem
     * @return void
     */
	@Override
	public void envioMailUct(String cod_rqnp, String cod_bien) {
		if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.envioMailUct");
		try{	
			requerimientoNoProgramadoDao.envioMailUct(cod_rqnp,cod_bien);
		}catch (Exception ex) {
		   log.error("Error en RequerimientoNoProgramadoServiceImpl.envioMailUct: " + ex.getMessage(), ex);
		   throw new ServiceException(this, ex);
		} 
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.envioMailUct");
		}
	
	}
	
	
	/**
     * Envio de correo a las UCT, para la atención de los item.
     * @param String cod_rqnp - Código de Rqnp
     * @param String cod_bien - Codigo de ítem
     * @return void
     */
	@Override
	public void envioMailDerivar(String cod_rqnp, String cod_bien) {
		if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.envioMailDerivar");
		try{	
			requerimientoNoProgramadoDao.envioMailDerivar(cod_rqnp,cod_bien);
		}catch (Exception ex) {
		   log.error("Error en RequerimientoNoProgramadoServiceImpl.envioMailUct: " + ex.getMessage(), ex);
		   throw new ServiceException(this, ex);
		} 
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.envioMailDerivar");
		}
	}
	
	
	/**
     * Registra el Número de Registro de Archivos Adjuntos.
     * @param String cod_rqnp - Código de Rqnp
     * @param String cod_bien - Codigo de ítem
     * @return void
     */
	@Override
	public void updateFile(String cod_rqnp,String cod_bien, String num_reg, String user) {
		if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.updateFile");
		try{	
			DataSource dataSource = obtenerDataSource("sig", false);
			RequerimientoNoProgBienesBean bean = new RequerimientoNoProgBienesBean();
			bean.setCodigoRqnp(cod_rqnp);
			bean.setNumeroArchivo(num_reg);
			bean.setCodigoBien(cod_bien);
			accesoDao.setUsuarioAcceso(dataSource,"USER", user);
			requerimientoNoProgBienesDao.updateFile(dataSource, bean);
			accesoDao.setUsuarioAccesoNull(dataSource,"USER");
			
		}catch (Exception ex) {
		   log.error("Error en RequerimientoNoProgramadoServiceImpl.updateFile: " + ex.getMessage(), ex);
		   throw new ServiceException(this, ex);
		} 
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.updateFile");
		}
	}
	
	/**
     * Envio de correo al responsable del Rqnp informando el rechazo de su Rqnp
     *  por parte del Jefe Inmediato o Intendente.
     * @param String cod_rqnp - Código de Rqnp
     * @param String cod_bien - Codigo de ítem
     * @param String cod_bandeja - Codigo de Bandeja
     * @return void
     */
	@Override
	public void envioMailRechazo(String cod_rqnp, String cod_bien, String cod_bandeja) {
		if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.envioMailRechazo");
		try{	
			requerimientoNoProgramadoDao.envioMailRechazo(cod_rqnp, cod_bien, cod_bandeja) ;
		}catch (Exception ex) {
		   log.error("Error en RequerimientoNoProgramadoServiceImpl.envioMailRechazo: " + ex.getMessage(), ex);
		   throw new ServiceException(this, ex);
		} 
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.envioMailRechazo");
		}
	
	}
	
	
	/**
     * Valida si el Rqnp ya fue enviado  a la UCT
     * @param String cod_rqnp - Código de Rqnp
     * @param String cod_bien - Codigo de ítem
     * @return boolean
     */
	@Override
	public boolean enviadoUCT(String cod_req, String cod_bien) {
		if (log.isDebugEnabled()) log.debug("inicio - RequerimientoNoProgramadoServiceImpl.enviadoUCT");
		boolean sw=false;
		try{	
			RequerimientoNoProgBienesBean bien=requerimientoNoProgBienesDao.recuperarRqnpBienes(cod_req, cod_bien);
			if (bien.getInd_auct1() !=null || bien.getInd_auct2() !=null || bien.getInd_auct3()!=null){
				sw=true;
			}
		}catch (Exception ex) {
		   log.error("Error en RequerimientoNoProgramadoServiceImpl.enviadoUCT: " + ex.getMessage(), ex);
		   throw new ServiceException(this, ex);
		} 
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.enviadoUCT");
		}
		return sw;
	}
	
	/**
     * Valida si las tienen metas tienen cantidades
     * @param String cod_rqnp - Código de Rqnp
     * @return String - 0=ok, 1= no Tiene monto, 3= no itene metas ,
     */
	public String validaMetasBienes(String cod_req){
		if (log.isDebugEnabled()) log.debug("inicio - RequerimientoNoProgramadoServiceImpl.validaMetasBienes");
		String sw="0";
	
		try{	
			Collection<RequerimientoNoProgBienesBean> listaBienes = (List<RequerimientoNoProgBienesBean>) requerimientoNoProgBienesDao.listarRqnpBienes(cod_req);
			
			if (listaBienes!=null){
				log.debug("nulos");
				if (listaBienes.size()!=0){
					for (RequerimientoNoProgBienesBean bien:listaBienes){
						
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("cod_bien",  bien.getCodigoBien());
						params.put("cod_req",cod_req);
			
						List<RequerimientoNoProgMetasBean> lista = (List<RequerimientoNoProgMetasBean>) requerimientoNoProgMetasDao.listarRqnpMetas(params);
						if (lista!= null){
							if (lista.size()>0 ){
								for ( RequerimientoNoProgMetasBean obj : lista){
									log.debug("bien " + obj.getCodigoBien() + " - " + obj.getMontoSoles());
									if (obj.getMontoSoles().doubleValue()>0){
										sw="2";
									}
								}
								if (!sw.equals("2")){
									sw="1";
								}else{
									sw="0";
								}
							}else{
								sw="1";
							}
						}else{
							sw="1";
						}
						if(sw.equals("1")){
							break;
						}
					}
				}else{
					sw="3";
				}
			}else{
				sw="3";
			}
		}catch (Exception ex) {
		   log.error("Error en RequerimientoNoProgramadoServiceImpl.validaMetasBienes: " + ex.getMessage(), ex);
		   throw new ServiceException(this, ex);
		} 
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.validaMetasBienes");
		}
		return sw;
	}
	
	
	
	/**
     * Registra el envio a las bandeja de Jefe Inmediato del Requerimiento No Programado .
     * @param Map<String, Object> params 
     * @return BigDecimal - Código de RQNP
     */
	@Override
	public String EnviarMailRqnp(Map<String, Object> params) {
		if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.EnviarMailRqnp");
		RequerimientoNoProgramadoBean rqnp= new RequerimientoNoProgramadoBean();
		
		List<Map<String, Object>> listaBienes = new ArrayList<Map<String,Object>>();
		
		String cod_req =(String) params.get ("codigo_rqnp");
	
		StringBuilder ls_bien=new StringBuilder("");
		 //
		try {
			rqnp= this.recuperarRqnp(cod_req);
			
			listaBienes = (List<Map<String, Object>>) rqnp.getListaBienes();				
			for (Map<String,Object> obj: listaBienes){
				RequerimientoNoProgBienesBean bien = new RequerimientoNoProgBienesBean();
			//	bien=requerimientoNoProgBienesDao.recuperarRqnpBienes(cod_req, (String)obj.get("codigoBien"));
			
				String ind_especializado = (String) obj.get("ind_especializado"); 

				if (ls_bien.toString().equals("")){
					ls_bien.append((String) obj.get("codigoBien"));
				}else{
					ls_bien.append(","+(String) obj.get("codigoBien"));
				}
					
			}
			requerimientoNoProgramadoDao.envioMailDerivar(cod_req,ls_bien.toString());
			
		}catch (Exception ex) {
			   log.error("Error en RequerimientoNoProgramadoServiceImpl.EnviarMailRqnp: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
			} 
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.EnviarMailRqnp");
		}
		return cod_req;
	}
	
	
	/**
     * Lista las Metas(Acciones)  del Requerimiento No Programado y/o los del Bien o Servicio.
     * @param Map<String, Object> parmt 
     * @return Collection -Lista de Metas 
     */
	
	@Override
	public Collection listarSysEstados(Map<String, Object> params) {
		if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.listarSysEstados");
		
		List<Map<String, Object>> lstEstado = new ArrayList<Map<String,Object>>();
		HashMap<String, Object> map_estado;
		try {
			
			List<SysEstadosBean> lista = (List<SysEstadosBean>) sysEstadosDao.listarEstados(params);
			
			Map<String, Object> map;
			
			for(SysEstadosBean data: lista){
				map = new HashMap<String, Object>();
				map.put("val_estado", data.getVal_estado());
				map.put("desc_estado", data.getDesc_estado());
				lstEstado.add(map);
			}
			
			map_estado = new HashMap<String, Object>();
			map_estado.put("val_estado", "-1");
			map_estado.put("desc_estado", "Todos");
			
			lstEstado.add(map_estado);
			return lstEstado;
		} 
		catch (Exception ex) {
			   log.error("Error en RequerimientoNoProgramadoServiceImpl.listarSysEstados: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
		}
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.listarSysEstados");
		}
	}
	
	@Override
	public Collection listarAccionesBien(
			String num_expediente) {
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
	
		
		int i =0;
	
		List<Map<String, Object>> lsAcciones = new ArrayList<Map<String,Object>>();
		try {
		
			List<ExpedienteInternoAccionBean> lista = (List<ExpedienteInternoAccionBean>)  expedienteInternoAccionDao.obtenerlistaAcciones(num_expediente) ;
			for(ExpedienteInternoAccionBean data: lista){
				map = new HashMap<String, Object>();
				
				map.put("cod_accion", data.getCod_accion() );
				//map.put("des_accion",data.getDes_accion()==null ? "": data.getDes_accion().replace( '"',ini).replace('/', ini).replace('°', ini).replace(ini2, ini).trim().replace(comaSimple, comaSimpleSalva).replace(comSimple, comSimpleSalva). replace(salto, espacio).replace(retorno, espacio).trim());	
				map.put("des_accion",data.getDes_accion()==null ? "": data.getDes_accion().replace( '"',ini).replace('°', ini).replace(ini2, ini).trim().replace(comaSimple, comaSimpleSalva).replace(comSimple, comSimpleSalva). replace(salto, espacio).replace(retorno, espacio).trim());
				map.put("cod_emp", data.getCod_emp() );
				map.put("estado", data.getEstado());
				map.put("nom_estado", data.getNom_estado() );
				map.put("nom_emp", data.getNom_emp() );
				String iniDate ="";
				String terDate ="";
				
				if (data.getFecha_ini()!=null ){
					iniDate=new SimpleDateFormat("dd/MM/yyyy HH:mm").format(data.getFecha_ini());
				}
				//log.debug("FECHA INI ------>>>>>>" +iniDate);
				if (data.getFecha_ter()!=null ){
					terDate=new SimpleDateFormat("dd/MM/yyyy HH:mm").format(data.getFecha_ter());
				}
				//log.debug("FECHA TER ------>>>>>>" +terDate);
				map.put("fecha_ini", iniDate );
				map.put("fecha_ter",terDate );
				map.put("num_exp", data.getNum_exp());
				map.put("observacion",data.getObservacion()==null ? "": data.getObservacion().replace(comaSimple, comaSimpleSalva).replace(comSimple, comSimpleSalva). replace(salto, espacio).replace(retorno, espacio).trim());
				map.put("sec_accion", data.getSec_accion());
				
				i++;
				lsAcciones.add(map);
			}
			return lsAcciones;
		} 
		catch (Exception ex) {
			   log.error("Error en RequerimientoNoProgramadoServiceImpl.listarAccionesBien: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
		}
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.listarAccionesBien");
		}
		
	}

	
	
	/**
	 * Obtiene el datasource a la BD Siga Consulta o Transaccional.
	 * @param ds
	 * @return
	 * @throws Exception
	 */
	private DataSource obtenerDataSource(String ds, boolean esDeLectura) throws Exception {
		String dataSource = "jdbc/d" + (esDeLectura ? "c" : "g") +  ds;
		if (log.isDebugEnabled()) log.debug("dataSource" + dataSource);
		return (DataSource) (new JndiTemplate()).lookup(dataSource);
	}
	
	public Collection listaNecesidad(){
		List<Map<String, Object>> lstNecesidad = new ArrayList<Map<String,Object>>();
		HashMap<String, Object> map_necesidad = new HashMap<String, Object>();
		
		map_necesidad = new HashMap<String, Object>();
		map_necesidad.put("cod", "01");
		map_necesidad.put("name", "Permanente");
		lstNecesidad.add(map_necesidad);
		
		map_necesidad = new HashMap<String, Object>();
		map_necesidad.put("cod", "02");
		map_necesidad.put("name", "Reiterada o estacional ");
		lstNecesidad.add(map_necesidad);
				
		map_necesidad = new HashMap<String, Object>();
		map_necesidad.put("cod", "03");
		map_necesidad.put("name", "Agotamiento Inmediato");
		lstNecesidad.add(map_necesidad);

		return lstNecesidad;
	}
	
	public Collection listaFinalidad(){
		if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.listaFinalidad");
		Map<String, Object> map;
		char ini=' ' ;
		char ini2= "'".charAt(0) ;
	
		int i =0;
		List<Map<String, Object>> lsfinalidad = new ArrayList<Map<String,Object>>();
		try {
		Map parm = new HashMap();
		
		List<ObjetivosBean> lista=(List<ObjetivosBean>)  objetivosDao.listarObjetivos(parm);
		
		for (ObjetivosBean obj :lista){
			 map = new HashMap<String, Object>();
			 map.put("cod", obj.getCod_obj());
			 map.put("name", obj.getDes_obj());
			 
			 lsfinalidad.add(map);
		}
		return lsfinalidad;
		} 
		catch (Exception ex) {
			   log.error("Error en RequerimientoNoProgramadoServiceImpl.listaFinalidad: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
		}
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.listaFinalidad");
		}
	}
	
	
	public Collection listaFinalidad2(){
		if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.listaFinalidad");
		Map<String, Object> map;
		char ini=' ' ;
		char ini2= "'".charAt(0) ;
	
		int i =0;
		List<Map<String, Object>> lsfinalidad = new ArrayList<Map<String,Object>>();
		try {
		Map parm = new HashMap();
		
		List<ObjetivosBean> lista=(List<ObjetivosBean>)  objetivosDao.listarObjetivos(parm);
		
		for (ObjetivosBean obj :lista){
			map = new HashMap<String, Object>();
			map.put("codigo", obj.getCod_obj());
			map.put("descripcion", obj.getDes_obj());
			
			lsfinalidad.add(map);
		}
		return lsfinalidad;
		} 
		catch (Exception ex) {
			log.error("Error en RequerimientoNoProgramadoServiceImpl.listaFinalidad: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);
		}
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.listaFinalidad");
		}
	}
	
	public Collection listaTipoNecesidad(){
		List<Map<String, Object>> lstTipoNecesidad = new ArrayList<Map<String,Object>>();
		HashMap<String, Object> map_tipo = new HashMap<String, Object>();
		
		map_tipo = new HashMap<String, Object>();
		map_tipo.put("cod", "01");
		map_tipo.put("name", "Recurrente");
		lstTipoNecesidad.add(map_tipo);
		/*
		map_tipo = new HashMap<String, Object>();
		map_tipo.put("cod", "02");
		map_tipo.put("name", "Nueva");
		lstTipoNecesidad.add(map_tipo);
				
		map_tipo = new HashMap<String, Object>();
		map_tipo.put("cod", "03");
		map_tipo.put("name", "Incremental");
		lstTipoNecesidad.add(map_tipo);
		*/
		return lstTipoNecesidad;
	}
	
	
	public Collection listarMeses() {
		List<Map<String, Object>> lstMeses = new ArrayList<Map<String,Object>>();
		HashMap<String, Object> map_mes = new HashMap<String, Object>();
		
		map_mes = new HashMap<String, Object>();
		map_mes.put("cod", "01");
		map_mes.put("name", "ENERO");
		lstMeses.add(map_mes);
		
		map_mes = new HashMap<String, Object>();
		map_mes.put("cod", "02");
		map_mes.put("name", "FEBRERO");
		lstMeses.add(map_mes);
		
		map_mes = new HashMap<String, Object>();
		map_mes.put("cod", "03");
		map_mes.put("name", "MARZO");
		lstMeses.add(map_mes);
		
		map_mes = new HashMap<String, Object>();
		map_mes.put("cod", "04");
		map_mes.put("name", "ABRIL");
		lstMeses.add(map_mes);
		
		map_mes = new HashMap<String, Object>();
		map_mes.put("cod", "05");
		map_mes.put("name", "MAYO");
		lstMeses.add(map_mes);
		
		map_mes = new HashMap<String, Object>();
		map_mes.put("cod", "06");
		map_mes.put("name", "JUNIO");
		lstMeses.add(map_mes);
		
		map_mes = new HashMap<String, Object>();
		map_mes.put("cod", "07");
		map_mes.put("name", "JULIO");
		lstMeses.add(map_mes);
		
		map_mes = new HashMap<String, Object>();
		map_mes.put("cod", "08");
		map_mes.put("name", "AGOSTO");
		lstMeses.add(map_mes);
		
		map_mes = new HashMap<String, Object>();
		map_mes.put("cod", "09");
		map_mes.put("name", "SEPTIEMBRE");
		lstMeses.add(map_mes);
		
		map_mes = new HashMap<String, Object>();
		map_mes.put("cod", "10");
		map_mes.put("name", "OCTUBRE");
		lstMeses.add(map_mes);
		
		map_mes = new HashMap<String, Object>();
		map_mes.put("cod", "11");
		map_mes.put("name", "NOVIEMBRE");
		lstMeses.add(map_mes);
		
		map_mes = new HashMap<String, Object>();
		map_mes.put("cod", "12");
		map_mes.put("name", "DICIEMBRE");
		lstMeses.add(map_mes);
		
		map_mes = new HashMap<String, Object>();
		map_mes.put("cod", "-1");
		map_mes.put("name", "TODOS");
		lstMeses.add(map_mes);
		
		return lstMeses;
	}
	
//INICIO: DPORRASC
	/**
	 * Reqista los item del Requerimiento No Programado sin considerar metas.
	 * @param Map<String, Object> parmt 
	 * @return String - Código del Requerimiento No Programado
	 */
	@Override
	public String registrarDetalleRqnpAUC(Map<String, Object> params) {
		if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.registrarCabActualizaRqnp");
		RequerimientoNoProgramadoBean rqnp= new RequerimientoNoProgramadoBean();
		Map parm = new HashMap();
		String cod_doc			="";
		String codigo_rqnp		="";
		String cod_exp			="";
		//(DPORRASC)List<Map<String, Object>> listaMetas= new ArrayList<Map<String,Object>>();
		BigDecimal montoTotal = new BigDecimal(0);
		BigDecimal suma = new BigDecimal(0);
		
		String anio_pro			=(String) params.get("anio_pro");
		//String cod_sede			=(String) params.get("cod_sede");
		String cod_responsable	=(String) params.get("cod_responsable");
		String cod_plaza		=(String) params.get("cod_plaza");

		String cod_rqnp[] =(String[]) params.get("cod_rqnp");
		String cod_bien[] =(String[]) params.get("cod_bien");
		String cod_unid[] =(String[]) params.get("cod_unid");
		String cantidad[] =(String[]) params.get("cantidad");
		String pre_unit[] =(String[]) params.get("pre_unit");
		String cod_clas[] =(String[]) params.get("cod_clas");
		String auct1[] =(String[]) params.get("auct1");
		//String auct2[] =(String[]) params.get("auct2");
		//String auct3[] =(String[]) params.get("auct3");
		
		String user =(String) params.get("user");
		
		String anio_ejec =(String) params.get ("anio_ejec");

		try {
			codigo_rqnp =(String) params.get("codigo_rqnp");
		
			if (log.isDebugEnabled()) log.debug("codigo_rqnp" + codigo_rqnp);
			
			rqnp.setCodigoRqnp(codigo_rqnp);
			rqnp.setMotivoSolicitud((String) params.get("motivoSolicitud"));
			rqnp.setCod_contacto((String) params.get("cod_contacto"));
			rqnp.setAnexo_contacto((String) params.get("anexo_contacto"));
			rqnp.setCod_necesidad((String) params.get("cod_necesidad"));
			rqnp.setCod_tip_nececidad((String) params.get("cod_tip_nececidad"));
			rqnp.setNom_tip_necesidad((String) params.get("nom_tip_necesidad"));
			
			rqnp.setCod_finalidad((String) params.get("cod_finalidad"));
			rqnp.setCod_proveedor((String) params.get("cod_proveedor"));
			rqnp.setObs_dir_entrega((String) params.get("obs_dir_entrega"));
			rqnp.setObs_justificacion((String) params.get("obs_justificacion"));
			rqnp.setObs_lugar_entrega((String) params.get("obs_lugar_entrega"));
			rqnp.setObs_plazo_entrega((String) params.get("obs_plazo_entrega"));

			DataSource dataSource = obtenerDataSource("sig", false);
			//accesoDao.setUsuarioAcceso(dataSource,"USER", user);
			//requerimientoNoProgramadoDao.update(dataSource, rqnp);
			//accesoDao.setUsuarioAccesoNull(dataSource,"USER");
			
			if (cod_rqnp !=null){
				for (int i =0 ; i < cod_rqnp.length; i++){
					RequerimientoNoProgBienesBean bienes = new RequerimientoNoProgBienesBean();
					bienes.setCodigoBien(cod_bien[i]);
					bienes.setItem_origen(cod_bien[i]);
					bienes.setCodigoRqnp(codigo_rqnp);
					bienes.setCod_plaza(cod_plaza);
					bienes.setCodigoUnidad(cod_unid[i]);
					bienes.setCodClasifGasto(cod_clas[i]);
					
					bienes.setCantBien( new BigDecimal(cantidad[i]));
					bienes.setPrecioUnid(new BigDecimal(pre_unit[i] ));
					bienes.setPrecio_item(new BigDecimal(pre_unit[i]));
					bienes.setPrecio_origen(new BigDecimal(pre_unit[i]));
					bienes.setPrecioUnidDol(new BigDecimal(0));
					
					bienes.setAuct1(auct1[i]);
					//bienes.setAuct2(auct2[i]);
					//bienes.setAuct3(auct3[i]);
					
					bienes.setInd_estado("01");//anteriormente estuvo "00"
					//if (((auct1[i]==null) ||(auct1[i].equals(""))) && ((auct2[i]==null) ||(auct2[i].equals(""))) && ((auct3[i]==null) ||(auct3[i].equals("")))){
					if ((auct1[i]==null)){
						bienes.setInd_especializado("N");
					}else{
						bienes.setInd_especializado("S");
					}
					
					//montoTotal=cantidad*pre_unit
					montoTotal = (new BigDecimal(cantidad[i])).multiply(new BigDecimal(pre_unit[i]));
					
					//suma: Es la sumatoria de montos totales
					suma= suma.add(montoTotal.setScale(2, RoundingMode.HALF_UP));
					
					//Lo encuentra si ya esta guardado en la BD
					//caso contrario, pasa a else
					if (cod_rqnp[i].equals(codigo_rqnp)){
						requerimientoNoProgBienesDao.update(bienes);
					}else{
						cod_doc = codigo_rqnp+ bienes.getCodigoBien();
						parm.put("anio_pro", anio_pro);
						//parm.put("cod_sede", cod_sede);
						parm.put("cod_responsable", cod_responsable);
						parm.put("cod_proceso", "249");
						parm.put("cod_doc", cod_doc);
						
						accesoDao.setUsuarioAcceso(dataSource,"USER", user);
						if (log.isDebugEnabled())log.debug("crear EXPEDIENTE>>>>>>>>>>>>>><<");
						cod_exp=requerimientoNoProgBienesDao.crearExpediente(parm).trim();
						bienes.setNum_exp(cod_exp);
						
						requerimientoNoProgBienesDao.insertar(bienes);
						
						parm.put("cod_accion", "001");
						parm.put("cod_exp", cod_exp);
						parm.put("exp_estado", "001");
						parm.put("exp_obs", "");
						if (log.isDebugEnabled())log.debug("crear ACCION>>>>>>>>>>>>>><<");
						requerimientoNoProgBienesDao.crearAccion(parm);
						
						accesoDao.setUsuarioAccesoNull(dataSource,"USER");
				}
				rqnp.setCodigoRqnp(codigo_rqnp);
				rqnp.setMontoRqnp(suma);
				
				requerimientoNoProgramadoDao.updateMonto(rqnp);
			}
			}
		}catch (Exception ex) {
			   log.error("Error en RequerimientoNoProgramadoServiceImpl.registrarCabActualizaRqnp: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
			} 
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.registrarCabActualizaRqnp");
		}
		return codigo_rqnp;
	}


//AGREGADO: DPORRASC
@Override
public Collection listarRqnpMetasBien(Map<String, Object> params) {
	if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.listarRqnpMetasBien");
	Map<String, Object> map;
	List<Map<String, Object>> lsMetas = new ArrayList<Map<String,Object>>();
	try {
		String precio_unid =(String)params.get("precio_unid");
		
		//List<RequerimientoNoProgMetasBean> lista = (List<RequerimientoNoProgMetasBean>) requerimientoNoProgMetasDao.listarRqnpMetas(params);
		List<RequerimientoNoProgMetasBean> lista = (List<RequerimientoNoProgMetasBean>) requerimientoNoProgMetasDao.listarRqnpMetasBien(params);
		
		BigDecimal precio_unitario = new BigDecimal(precio_unid);
		char ini=' ' ;
		char ini2= "'".charAt(0) ;
		char salto =(char)10;
		char retorno =(char)13;
		char espacio =(char)32;
		CharSequence comSimple="'";
		CharSequence comSimpleSalva="&#39";
		
		CharSequence comaSimple=",";
		CharSequence comaSimpleSalva=";";
		CharSequence comi ="&quot"; //DOBLE COMILLA
		CharSequence comis ="\"";
		
		for(RequerimientoNoProgMetasBean data: lista){
			map = new HashMap<String, Object>();
			map.put("codigoRqnp", data.getCodigoRqnp());
			map.put("codigoBien", data.getCodigoBien());
			map.put("anioEjec", data.getAnioEjec());
			map.put("secuenciaMeta", data.getSecuenciaMeta());
			map.put("cantidadTotal", data.getCantidadTotal().setScale(0, RoundingMode.HALF_UP).toString() );
			map.put("montoSoles", data.getMontoSoles().setScale(2, RoundingMode.HALF_UP).toString());
			map.put("montoDolar", data.getMontoDolar().setScale(2, RoundingMode.HALF_UP).toString());
			map.put("precioUnid",precio_unitario.setScale(2, RoundingMode.HALF_UP).toString());
			map.put("ubg", data.getUbg().replace(comis, comi).replace(comaSimple, comaSimpleSalva).replace(comSimple, comSimpleSalva). replace(salto, espacio).replace(retorno, espacio).trim());
			map.put("producto", data.getProducto().replace(comis, comi).replace(comaSimple, comaSimpleSalva).replace(comSimple, comSimpleSalva). replace(salto, espacio).replace(retorno, espacio).trim());
			map.put("meta",data.getMeta().replace(comis, comi).replace(comaSimple, comaSimpleSalva).replace(comSimple, comSimpleSalva). replace(salto, espacio).replace(retorno, espacio).trim());
			map.put("uuoo",data.getUuoo().replace(comis, comi).replace(comaSimple, comaSimpleSalva).replace(comSimple, comSimpleSalva). replace(salto, espacio).replace(retorno, espacio).trim());
			map.put("metaSiaf",data.getMetaSiaf().replace(comis, comi).replace(comaSimple, comaSimpleSalva).replace(comSimple, comSimpleSalva). replace(salto, espacio).replace(retorno, espacio).trim());
			lsMetas.add(map);
		}
		return lsMetas;
	} 
	catch (Exception ex) {
		   log.error("Error en RequerimientoNoProgramadoServiceImpl.listarRqnpMetasBien: " + ex.getMessage(), ex);
		   throw new ServiceException(this, ex);
	}
	finally {
		if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.listarRqnpMetasBien");
	}
}

@Override
public String validaMetasBienesAUC(String cod_req) {
	if (log.isDebugEnabled()) log.debug("inicio - RequerimientoNoProgramadoServiceImpl.validaMetasBienesAUC");
	String sw="0";

	try{	
		Collection<RequerimientoNoProgBienesBean> listaBienes = (List<RequerimientoNoProgBienesBean>) requerimientoNoProgBienesDao.listarRqnpBienes(cod_req);
		if (listaBienes!=null){
			if (listaBienes.size()!=0){
			for (RequerimientoNoProgBienesBean bien:listaBienes){
				
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("cod_bien",  bien.getCodigoBien());
				params.put("cod_req",cod_req);
	
				List<RequerimientoNoProgMetasBean> lista = (List<RequerimientoNoProgMetasBean>) requerimientoNoProgMetasDao.listarRqnpMetasBien(params);
				
				if (lista!= null){
					if (lista.size()>0 ){
						for ( RequerimientoNoProgMetasBean obj : lista){
							log.debug("bien " + obj.getCodigoBien() + " - " + obj.getMontoSoles());
							if (obj.getMontoSoles().doubleValue()==0){
								sw="2";
							}
						}
						if (sw.equals("2")){
							sw="1";
						}
					}else{
						sw="1";
					}
				}else{
					sw="1";
				}
				if(sw.equals("1")){
					break;
				}
			}
			}else{
				sw="3";
			}
		}else{
			sw="3";
		}
	}catch (Exception ex) {
	   log.error("Error en RequerimientoNoProgramadoServiceImpl.validaMetasBienesAUC: " + ex.getMessage(), ex);
	   throw new ServiceException(this, ex);
	} 
	finally {
		if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.validaMetasBienesAUC");
	}
	return sw;
}


/**
 * Reqistra  la Cabecera del Requerimientos No Programados AUC
 * Incluye las UUOO de conformidad UUOO1, UUOO2, UUOO3
 * @param Map<String, Object> params 
 * @return String - Código de Requerimiento no Programado
 */  
@Override
public String registrarCabRqnpAUC(Map<String, Object> params) {
	if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.registrarCabRqnp");
	RequerimientoNoProgramadoBean rqnp= new RequerimientoNoProgramadoBean();
	String 	codigo_rq="";
	String 	ls_anio="";
	String 	ls_mes="";
	String 	ls_sec_rqnp="";
	String 	codigo_rqnp="";
	String 	cod_uuoo="";
	String 	user="";
	try {
		cod_uuoo =(String) params.get("uuoo");
		user =(String) params.get("user");
		Calendar fecha =  Calendar.getInstance();
		ls_anio= String.valueOf( fecha.get(Calendar.YEAR)) ;
		
		Formatter fmt = new Formatter();
	
		ls_mes = fmt.format("%02d", fecha.get(Calendar.MONTH )+1) .toString();
		
		if (log.isDebugEnabled()) log.debug("MESSSS: "+ls_mes );
		
		ls_sec_rqnp = requerimientoNoProgramadoDao.secuencialRequerimientoRqnp(ls_anio);
		codigo_rq	= ls_anio+ls_sec_rqnp;
		ls_sec_rqnp=ls_sec_rqnp.substring(3,8);
		
		codigo_rqnp = requerimientoNoProgramadoDao.secuencialRequerimientoRqnpUuoo(ls_anio, cod_uuoo);
		codigo_rqnp= ls_anio+cod_uuoo+codigo_rqnp;
		
		rqnp.setCodigoRqnp(codigo_rq);
		rqnp.setAnioProceso(ls_anio);
	
		rqnp.setMesProeso(ls_mes);
		rqnp.setNumeroRqnp(ls_sec_rqnp);
		//rqnp.setCodigoSede((String) params.get("codigoSede"));
		rqnp.setFechaRqnp(new  Date());
		rqnp.setMotivoSolicitud((String) params.get("motivoSolicitud"));
		rqnp.setCod_contacto((String) params.get("cod_contacto"));
		rqnp.setAnexo_contacto((String) params.get("anexo_contacto"));
		rqnp.setCod_necesidad((String) params.get("cod_necesidad"));
		rqnp.setCod_tip_nececidad((String) params.get("cod_tip_nececidad"));
		rqnp.setNom_tip_necesidad((String) params.get("nom_tip_necesidad"));
		rqnp.setCod_finalidad((String) params.get("cod_finalidad"));
		rqnp.setCod_proveedor((String) params.get("cod_proveedor"));
		rqnp.setObs_dir_entrega((String) params.get("obs_dir_entrega"));
		rqnp.setObs_justificacion((String) params.get("obs_justificacion"));
		rqnp.setObs_lugar_entrega((String) params.get("obs_lugar_entrega"));
		rqnp.setObs_plazo_entrega((String) params.get("obs_plazo_entrega"));
		
		rqnp.setAnio_atencion((String) params.get("anio_atencion"));
		rqnp.setMes_atencion((String) params.get("mes_atencion"));
		rqnp.setTipo_vinculo((String) params.get("tipo_vinculo"));
		rqnp.setVinculo((String) params.get("vinculo"));
		
		rqnp.setInd_vinculo((String) params.get("ind_vinculo"));
		rqnp.setInd_prestamo((String) params.get("ind_prestamo"));
		//AGREGADO COMITE
		
		rqnp.setInd_comite((String) params.get("ind_comite"));
		rqnp.setInd_tec_tit((String) params.get("ind_tec_tit"));
		rqnp.setInd_tec_sup((String) params.get("ind_tec_sup"));
		rqnp.setCod_au_tit((String) params.get("cod_au_tit"));
		rqnp.setCod_au_sup((String) params.get("cod_au_sup"));
		rqnp.setCod_tec_tit((String) params.get("cod_tec_tit"));
		rqnp.setCod_tec_sup((String) params.get("cod_tec_sup"));
		rqnp.setNom_tec_tit((String) params.get("nom_tec_tit"));
		rqnp.setNom_tec_sup((String) params.get("nom_tec_sup"));
		
		rqnp.setCod_uoc1((String) params.get("cod_uoc1"));
		rqnp.setCod_uoc2((String) params.get("cod_uoc2"));
		rqnp.setCod_uoc3((String) params.get("cod_uoc3"));
		
		rqnp.setEstadoRqnp("01");
		rqnp.setMontoRqnp(new BigDecimal(0));
		rqnp.setCodigoDependencia((String) params.get("codigoDependencia"));
		rqnp.setTipoMoneda("S");
		rqnp.setCodigoResposanble((String) params.get("codigoEmpleado"));
		rqnp.setCodigoReqNoProgramado(codigo_rqnp);
		rqnp.setTipoBien("B");
		rqnp.setTipoPpto("B");
		
		//AGREGADO
		rqnp.setInd_registropor("AUC");
		
		DataSource dataSource = obtenerDataSource("sig", false);
		accesoDao.setUsuarioAcceso(dataSource,"USER", user);
		requerimientoNoProgramadoDao.insertarAUC(dataSource, rqnp);
		accesoDao.setUsuarioAccesoNull(dataSource,"USER");
		
	}catch (Exception ex) {
		   log.error("Error en RequerimientoNoProgramadoServiceImpl.registrarCabRqnp: " + ex.getMessage(), ex);
		   throw new ServiceException(this, ex);
		} 
	finally {
		if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.registrarCabRqnp");
	}
	
	return codigo_rq;
}


/**
 * Reqistra  la Cabecera del Requerimientos No Programados AUC
 * Incluye las UUOO de conformidad UUOO1, UUOO2, UUOO3
 * @param Map<String, Object> params 
 * @return String - Código de Requerimiento no Programado
 */  
@Override
public String registrarCabRqnpAUCModi(Map<String, Object> params) {
	if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.registrarCabRqnpAUCModi");
	RequerimientoNoProgramadoBean rqnp= new RequerimientoNoProgramadoBean();
	String 	codigo_rq=(String) params.get("cod_req");
	String 	ls_anio="";
	String 	ls_mes="";
	String 	ls_sec_rqnp="";
	String 	codigo_rqnp="";
	String 	cod_uuoo="";
	String 	user="";
	try {
		cod_uuoo =(String) params.get("uuoo");
		user =(String) params.get("user");
		
		//rqnp.setCodigoRqnp(codigo_rq);
		rqnp.setCodigoRqnp((String) params.get("cod_req"));
		rqnp.setCod_contacto((String) params.get("cod_contacto"));
		rqnp.setAnexo_contacto((String) params.get("anexo_contacto"));
		rqnp.setCod_proveedor((String) params.get("cod_proveedor"));
		rqnp.setObs_justificacion((String) params.get("obs_justificacion"));
		rqnp.setObs_dir_entrega((String) params.get("obs_dir_entrega"));
		rqnp.setObs_lugar_entrega((String) params.get("obs_lugar_entrega"));
		rqnp.setObs_plazo_entrega((String) params.get("obs_plazo_entrega"));
		
		rqnp.setCod_necesidad((String) params.get("cod_necesidad"));
		rqnp.setCod_tip_nececidad((String) params.get("cod_tip_nececidad"));
		rqnp.setNom_tip_necesidad((String) params.get("nom_tip_necesidad"));
		rqnp.setCod_finalidad((String) params.get("cod_finalidad"));
		
		rqnp.setAnio_atencion((String) params.get("anio_atencion"));
		rqnp.setMes_atencion((String) params.get("mes_atencion"));
		rqnp.setTipo_vinculo((String) params.get("tipo_vinculo"));
		rqnp.setVinculo((String) params.get("vinculo"));
		
		rqnp.setInd_vinculo((String) params.get("ind_vinculo"));
		rqnp.setInd_prestamo((String) params.get("ind_prestamo"));
		
		rqnp.setMotivoSolicitud((String) params.get("motivoSolicitud"));
		//AGREGADO
		rqnp.setCod_uoc1((String) params.get("cod_uoc1"));
		rqnp.setCod_uoc2((String) params.get("cod_uoc2"));
		rqnp.setCod_uoc3((String) params.get("cod_uoc3"));
		
		//AGREGADO COMITE
		rqnp.setInd_comite((String) params.get("ind_comite"));
		rqnp.setInd_tec_tit((String) params.get("ind_tec_tit"));
		rqnp.setInd_tec_sup((String) params.get("ind_tec_sup"));
		rqnp.setCod_au_tit((String) params.get("cod_au_tit"));
		rqnp.setCod_au_sup((String) params.get("cod_au_sup"));
		rqnp.setCod_tec_tit((String) params.get("cod_tec_tit"));
		rqnp.setCod_tec_sup((String) params.get("cod_tec_sup"));
		rqnp.setNom_tec_tit((String) params.get("nom_tec_tit"));
		rqnp.setNom_tec_sup((String) params.get("nom_tec_sup"));
		
		rqnp.setEstadoRqnp("01");
		rqnp.setMontoRqnp(new BigDecimal(0));
		rqnp.setCodigoDependencia((String) params.get("codigoDependencia"));
		rqnp.setTipoMoneda("S");
		rqnp.setCodigoResposanble((String) params.get("codigoEmpleado"));
		rqnp.setCodigoReqNoProgramado(codigo_rqnp);
		rqnp.setTipoBien("B");
		rqnp.setTipoPpto("B");
		
		//AGREGADO
		rqnp.setInd_registropor("AUC");
		
		DataSource dataSource = obtenerDataSource("sig", false);
		accesoDao.setUsuarioAcceso(dataSource,"USER", user);
		requerimientoNoProgramadoDao.updateAUC(dataSource, rqnp);
		
		accesoDao.setUsuarioAccesoNull(dataSource,"USER");
		
	}catch (Exception ex) {
		   log.error("Error en RequerimientoNoProgramadoServiceImpl.registrarCabRqnpAUCModi: " + ex.getMessage(), ex);
		   throw new ServiceException(this, ex);
		} 
	finally {
		if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.registrarCabRqnpAUCModi");
	}
	
	return codigo_rq;
}
@Override
public String obtenerTipoAUC(String cod_dep) {
	String tipo_auc=requerimientoNoProgramadoDao.obtenerTipoAUC(cod_dep);
	if(tipo_auc==null){
		tipo_auc="N";
	}
	return tipo_auc;
}

/**
 * Elimina ítems del Requerimiento No Programado.
 * @param Map<String, Object> parmt 
 * @return void
 */
@Override
public void deleteMetaRnp(Map<String, Object> params) {
	
	if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.deleteMetaRnp");
	String user="";
	RequerimientoNoProgBienesBean bien= new RequerimientoNoProgBienesBean();
	RequerimientoNoProgMetasBean meta = new RequerimientoNoProgMetasBean();
	RequerimientoNoProgramadoBean rqnp = new RequerimientoNoProgramadoBean();
	try {
		DataSource dataSource = obtenerDataSource("sig", false);
		
		meta.setAnioEjec((String) params.get("anioEjec"));
		meta.setCodigoBien((String) params.get("codigo_bien"));
		meta.setCodigoRqnp((String) params.get("codigo_rqnp"));
		meta.setSecuenciaMeta((String) params.get("secu_func"));
		
		requerimientoNoProgMetasDao.delete(meta);
		
		//Obtener Cantidad total de items despues de la eliminacion
		String cantBien=requerimientoNoProgMetasDao.obtenerCantBien(params);
		log.debug("cantBien: "+cantBien);
		//ACTUALIZAR CANTIDAD DE REQUERIMIENTO_NO_PROG_BIENES
		//bien.setCantBien(new BigDecimal(0));
		bien.setCantBien(new BigDecimal(cantBien));
		bien.setCodigoBien((String) params.get("codigo_bien"));
		bien.setCodigoRqnp((String) params.get("codigo_rqnp"));
		
		
		//Obtener Monto total del Rqnp despues de la eliminacion
		String montoRqnp=requerimientoNoProgMetasDao.obtenerMontoRqnp(params);
		log.debug("montoRqnp: "+montoRqnp);
		accesoDao.setUsuarioAcceso(dataSource,"USER", user);
		requerimientoNoProgBienesDao.updateCantidades(dataSource, bien);
		accesoDao.setUsuarioAccesoNull(dataSource,"USER");
		
//		UPDATE  REQUERIMIENTO_NO_PROG_BIENES  
//				SET CANT_TOTA_DSR = #cantBien#
//		WHERE 	CODI_RQNP_SRM 	= #codigoRqnp# AND
//				CODI_BSER_CAT 	= #codigoBien#
		
		//ACTUALIZAR CANTIDAD DE REQUERIMIENTO_NO_PROG
		rqnp.setCodigoRqnp((String) params.get("codigo_rqnp"));
		rqnp.setMontoRqnp(new BigDecimal(montoRqnp));
		
//		UPDATE  REQUERIMIENTO_NO_PROG  
//				SET MTO_VALO_SRM 	= #montoRqnp#
//		WHERE CODI_RQNP_SRM = #codigoRqnp# 
		
		requerimientoNoProgramadoDao.updateMonto(rqnp);
		
	}catch (Exception ex) {
		   log.error("Error en RequerimientoNoProgramadoServiceImpl.deleteMetaRnp: " + ex.getMessage(), ex);
		   throw new ServiceException(this, ex);
		} 
	finally {
		if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.deleteMetaRnp");
	}
}

@Override
public Collection listarProyProdAccion(Map<String, Object> params) {
	if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.listarProyProdAccion");
	Map<String, Object> map;
	List<Map<String, Object>> lsMetasProy = new ArrayList<Map<String,Object>>();
	try {
		String precio_unid =(String)params.get("precio_unid");
		
		//(ORIGINAL) List<RequerimientoNoProgMetasBean> lista = (List<RequerimientoNoProgMetasBean>) requerimientoNoProgMetasDao.listarRqnpMetas(params);
		List<MetasProyectosBean> lista = (List<MetasProyectosBean>) metasProyectosDao.listarProyProdAccion(params);
		
		BigDecimal precio_unitario = new BigDecimal(precio_unid);
		char ini=' ';
		char ini2= "'".charAt(0);
		char salto =(char)10;
		char retorno =(char)13;
		char espacio =(char)32;
		CharSequence comSimple="'";
		CharSequence comSimpleSalva="&#39";
		
		CharSequence comaSimple=",";
		CharSequence comaSimpleSalva=";";
		CharSequence comi ="&quot";
		CharSequence comis ="\"";
		
		for(MetasProyectosBean data: lista){
			map = new HashMap<String, Object>();
			
			map.put("secuFunc", data.getSecuFunc());
			map.put("nomActividad", data.getNomActividad().replace(comis, comi).replace(comaSimple, comaSimpleSalva).replace(comSimple, comSimpleSalva). replace(salto, espacio).replace(retorno, espacio).trim());
			map.put("nomProducto", data.getNomProducto().replace(comis, comi).replace(comaSimple, comaSimpleSalva).replace(comSimple, comSimpleSalva). replace(salto, espacio).replace(retorno, espacio).trim());
			map.put("nomMeta", data.getNomMeta().replace(comis, comi).replace(comaSimple, comaSimpleSalva).replace(comSimple, comSimpleSalva). replace(salto, espacio).replace(retorno, espacio).trim());
			map.put("metaSiaf",data.getMetaSiaf().replace(comis, comi).replace(comaSimple, comaSimpleSalva).replace(comSimple, comSimpleSalva). replace(salto, espacio).replace(retorno, espacio).trim());
			lsMetasProy.add(map);
		}
		return lsMetasProy;
	} 
	catch (Exception ex) {
		   log.error("Error en RequerimientoNoProgramadoServiceImpl.listarProyProdAccion: " + ex.getMessage(), ex);
		   throw new ServiceException(this, ex);
	}
	finally {
		if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.listarProyProdAccion");
	}
}

@Override
public String registrarMetasRqnpPopup(Map<String, Object> params) {
	if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.registrarMetasRqnp");
	
	String cod_req =(String) params.get ("codigo_rqnp");
	String anio_ejec =(String) params.get ("anio_ejec");
	String cod_bien =(String) params.get ("cod_bien");
	String user = (String) params.get("user");
	
	//MISMO CODIGO REUTILIZADO
	String txtBien =(String) params.get("txtBien");
	String txtCantidad =(String) params.get("txtCantidad");
	String txtxCantidad =(String) params.get("txtxCantidad");
	String txtMonto =(String) params.get("txtMonto");
	String txtSecuencia =(String) params.get("txtSecuencia");
	
	try {
		DataSource dataSource = obtenerDataSource("sig", false);
		BigDecimal cantidad = new BigDecimal(0);
		
		if (txtBien !=null){

			RequerimientoNoProgMetasBean metas = new RequerimientoNoProgMetasBean();
			metas.setCodigoRqnp(cod_req);
			metas.setCodigoBien(cod_bien);
			metas.setAnioEjec(anio_ejec);
			metas.setSecuenciaMeta(txtSecuencia);
			metas.setMontoSoles(new BigDecimal(txtMonto).setScale(2, RoundingMode.HALF_UP));
					
			metas.setCantidadTotal(new BigDecimal(txtxCantidad));
			cantidad=cantidad.add(new BigDecimal(txtxCantidad));
					
			requerimientoNoProgMetasDao.insertar(metas);
			}

	}catch (Exception ex) {
	
		   log.error("Error en RequerimientoNoProgramadoServiceImpl.registrarMetasRqnp: " + ex.getMessage(), ex);
		   throw new ServiceException(this, ex);
		} 
	finally {
		if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.registrarMetasRqnp");
	}
	
	return cod_req;
}

@Override
public Map<String, Object> spReplicarMetas(Map<String, Object> params)
		throws ServiceException {

	String user = (String)params.get("user");
	DataSource dataSource;
	
	try {
		dataSource = obtenerDataSource("sig", false);
		log.info("spReplicarMetas");
		accesoDao.setUsuarioAcceso(dataSource,"USER", user);
		requerimientoNoProgMetasDao.spReplicarMetas(dataSource, params);
		accesoDao.setUsuarioAccesoNull(dataSource,"USER");
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return  params;
}

@Override
public String registrarCabEntregaRqnpAUC(Map<String, Object> params) {
	if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.registrarCabEntregaRqnp");
	RequerimientoNoProgramadoBean rqnp= new RequerimientoNoProgramadoBean();
	String 	codigoRqnp="";

	String 	user="";
	try {
		
		user =(String) params.get("user");
		
		rqnp.setCodigoRqnp((String) params.get("codigoRqnp"));	
		rqnp.setMotivoSolicitud((String) params.get("motivoSolicitud"));
		rqnp.setCod_contacto((String) params.get("cod_contacto"));
		rqnp.setAnexo_contacto((String) params.get("anexo_contacto"));
		rqnp.setCod_necesidad((String) params.get("cod_necesidad"));
		rqnp.setCod_tip_nececidad((String) params.get("cod_tip_nececidad"));
		rqnp.setNom_tip_necesidad((String) params.get("nom_tip_necesidad"));
		rqnp.setCod_finalidad((String) params.get("cod_finalidad"));
		rqnp.setCod_proveedor((String) params.get("cod_proveedor"));
		rqnp.setObs_justificacion((String) params.get("obs_justificacion"));
		rqnp.setObs_dir_entrega((String) params.get("obs_dir_entrega"));
		rqnp.setObs_lugar_entrega((String) params.get("obs_lugar_entrega"));
		rqnp.setObs_plazo_entrega((String) params.get("obs_plazo_entrega"));
		//AGREGADO(DPORRASC)
		rqnp.setCod_uoc1((String) params.get("auct1"));
		rqnp.setCod_uoc2((String) params.get("auct2"));
		rqnp.setCod_uoc3((String) params.get("auct3"));
			
		DataSource dataSource = obtenerDataSource("sig", false);
		accesoDao.setUsuarioAcceso(dataSource,"USER", user);
		requerimientoNoProgramadoDao.updateAUC(dataSource, rqnp);
		accesoDao.setUsuarioAccesoNull(dataSource,"USER");
		
	}catch (Exception ex) {
		   log.error("Error en RequerimientoNoProgramadoServiceImpl.registrarCabEntregaRqnp: " + ex.getMessage(), ex);
		   throw new ServiceException(this, ex);
		} 
	finally {
		if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.registrarCabEntregaRqnp");
	}
	
	return codigoRqnp;
}

/**
 * Calcula el limite en compras directas de cada año .
 * ejemplo: (en el 2016 es 8 uits)
 */
@Override
public BigDecimal calculaLimiteCompraDirecta(String anio_ejec) {
	if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.calculaLimiteCompraDirecta");
	RequerimientoNoProgramadoBean rqnp= new RequerimientoNoProgramadoBean();
	 BigDecimal limite_cd = new BigDecimal(0);
	try {
		String uit=sysParametrosDao.obtenerValUitAnio(anio_ejec);
		log.debug("uit: "+uit);
		
		String parametro_uit=sysParametrosDao.obtenerNumUitAnio(anio_ejec);
		log.debug("parametro_uit: "+parametro_uit);
		
		BigDecimal val_numUits	= new BigDecimal(uit.replaceAll(",", "").replaceAll(",", ""));
		log.debug("val_numUits: "+val_numUits);
		
		BigDecimal val_uit		= new BigDecimal(parametro_uit.replaceAll(",", ""));
		log.debug("val_uit: "+val_uit);
		
		limite_cd	= val_numUits.multiply(val_uit);
		log.debug("limite_cd: "+limite_cd);
		
	}catch (Exception ex) { 
		   log.error("Error en RequerimientoNoProgramadoServiceImpl.calculaLimiteCompraDirecta: " + ex.getMessage(), ex);
		   throw new ServiceException(this, ex);
		} 
	finally {
		if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.calculaLimiteCompraDirecta");
	}
	
return limite_cd;
	
}


/**
 * Se obtiene el parametro de uits activo para cada año en compras directas .
 * ejemplo: (en el 2016 es 8 uits)
 */
@Override
public String obtenerUIT(String anioEjec) {
	if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.obtenerUIT");
	String parametro_uit="";
	Map<String, Object> map=new HashMap<String, Object>();
	try {
		parametro_uit= sysParametrosDao.obtenerNumUitAnio(anioEjec);
	}catch (Exception ex) { 
		   log.error("Error en RequerimientoNoProgramadoServiceImpl.obtenerUIT: " + ex.getMessage(), ex);
		   throw new ServiceException(this, ex);
		} 
	finally {
		if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.obtenerUIT");
	}
	
return parametro_uit;
	
}


/**
 * Verifica el Tipo de Dependencia AUC o APROBADOR (NO USA FUNCION ORACLE)
 * @param String - Código de Dependencia
 * @param String - Código de Empleado
 * @return boolean 
 */ 

@Override
public boolean esPerfilDependenciaAprobador(String codDependencia) {
	log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.esPerfilDependenciaAprobador");
	boolean sw=false;
	try {
		DataSource dataSource = obtenerDataSource("sig", false);
		return dependenciaDao.esPerfilDependenciaAprobador(codDependencia);
	} catch (Exception ex) {
		log.error("Error en RequerimientoNoProgramadoServiceImpl.esPerfilDependenciaAprobador: " + ex.getMessage(), ex);
		throw new ServiceException(this, ex);
	}
	finally {
		log.debug("Fin - RequerimientoNoProgramadoServiceImpl.esPerfilDependenciaAprobador");
	}
}


/**
 * Verifica el Tipo de Dependencia AUC o (NO USA FUNCION ORACLE)
 * @param String - Código de Dependencia
 * @param String - Código de Empleado
 * @return boolean 
 */ 

@Override
public boolean esPerfilDependenciaAUC(String codDependencia) {
	log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.esPerfilDependenciaAUC");
	boolean sw=false;
	try {
		DataSource dataSource = obtenerDataSource("sig", false);
		return dependenciaDao.esPerfilDependenciaAUC(codDependencia);
	}catch (Exception ex) {
		log.error("Error en RequerimientoNoProgramadoServiceImpl.esPerfilDependenciaAUC: " + ex.getMessage(), ex);
		throw new ServiceException(this, ex);
	}
	finally {
		log.debug("Fin - RequerimientoNoProgramadoServiceImpl.esPerfilDependenciaAUC");
	}
}

@Override
public DependenciaBean obtenerUuooRQNP(Map<String, Object> params) throws ServiceException {
    log.debug((Object)"Inicio - RequerimientoNoProgramadoServiceImpl.obtenerUuooRQNP");
    String codRqnp = (String)params.get("codRqnp");
    String codAprueba = (String)params.get("codAprueba");
    String cod_dep = (String)params.get("codDepeRqnp");
    try {
        DependenciaBean dependenciaBean = this.dependenciaDao.obtenerDependenciaRqnp(codRqnp);
        return dependenciaBean;
    }
    catch (Exception ex) {
        log.error((Object)("Error en RequerimientoNoProgramadoServiceImpl.obtenerUuooRQNP: " + ex.getMessage()), (Throwable)ex);
        throw new ServiceException((Object)this, ex);
    }
    finally {
        log.debug((Object)"Fin - RequerimientoNoProgramadoServiceImpl.obtenerUuooRQNP");
    }
}


@Override
public DependenciaBean obtenerUuooOEC(Map<String, Object> params)	throws ServiceException {
	log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.obtenerUuooOEC");
	String codRqnp = (String)params.get("codRqnp");
	String codAprueba = (String)params.get("codAprueba");
	//String cod_dep = (String)params.get("codDepeRqnp");
	String cod_dep = (String)params.get("codOecDefecto");
	try {
		DataSource dataSource = obtenerDataSource("sig", false);
		return dependenciaDao.obtenerDependenciaXcod(cod_dep);
		
	}catch (Exception ex) {
		log.error("Error en RequerimientoNoProgramadoServiceImpl.obtenerUuooOEC: " + ex.getMessage(), ex);
		throw new ServiceException(this, ex);
	}
	finally {
		log.debug("Fin - RequerimientoNoProgramadoServiceImpl.obtenerUuooOEC");
	}
}

@Override
public DependenciaBean obtenerUuooAprobadoraAuc(Map<String, Object> params)	throws ServiceException {
	log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.obtenerUuooAprobadoraAuc");
	String codRqnp = (String)params.get("codRqnp");
	String codAprueba = (String)params.get("codAprueba");
	//String cod_dep = (String)params.get("codDepeRqnp");
	String cod_dep = (String)params.get("codUuooAprueba");
	try {
		DataSource dataSource = obtenerDataSource("sig", false);
		return dependenciaDao.obtenerDependenciaXcod(cod_dep);
		
	}catch (Exception ex) {
		log.error("Error en RequerimientoNoProgramadoServiceImpl.obtenerUuooAprobadoraAuc: " + ex.getMessage(), ex);
		throw new ServiceException(this, ex);
	}
	finally {
		log.debug("Fin - RequerimientoNoProgramadoServiceImpl.obtenerUuooAprobadoraAuc");
	}
}

@Override
public DependenciaBean obtenerUuooAUC(Map<String, Object> params) throws ServiceException {
    log.debug((Object)"Inicio - RequerimientoNoProgramadoServiceImpl.obtenerUuooAUC");
    String codRqnp = (String)params.get("codRqnp");
    String codAprueba = (String)params.get("codAprueba");
    try {
        DependenciaBean dependenciaBean = this.dependenciaDao.obtenerDependenciaAUCdeRqnp(codRqnp);
        return dependenciaBean;
    }
    catch (Exception ex) {
        log.error((Object)("Error en RequerimientoNoProgramadoServiceImpl.obtenerUuooAUC: " + ex.getMessage()), (Throwable)ex);
        throw new ServiceException((Object)this, ex);
    }
    finally {
        log.debug((Object)"Fin - RequerimientoNoProgramadoServiceImpl.obtenerUuooAUC");
    }
}

@Override
public DependenciaBean obtenerUuoo(Map<String, Object> params) throws ServiceException {
    log.debug((Object)"Inicio - RequerimientoNoProgramadoServiceImpl.obtenerUuoo");
    String codDependencia = (String)params.get("codDependencia");
    try {
        DependenciaBean dependenciaBean = this.dependenciaDao.obtenerDependencia(codDependencia);
        return dependenciaBean;
    }
    catch (Exception ex) {
        log.error((Object)("Error en RequerimientoNoProgramadoServiceImpl.obtenerUuoo: " + ex.getMessage()), (Throwable)ex);
        throw new ServiceException((Object)this, ex);
    }
    finally {
        log.debug((Object)"Fin - RequerimientoNoProgramadoServiceImpl.obtenerUuoo");
    }
}

@Override
public String validaExisteMetaJson(String codDependencia, String anioMeta) {
    String sw;
    if (log.isDebugEnabled()) {
        log.debug((Object)"inicio - RequerimientoNoProgramadoServiceImpl.validaExisteMetaJson");
    }
    sw = "0";
    try {
        try {
            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("codDependencia", codDependencia);
            params.put("anioMeta", anioMeta);
            List lista = (List<Map<String, Object>>) this.requerimientoNoProgMetasDao.listarMetasUuoo(params);
            if (lista != null && lista.size() > 0) {
                sw = "1";
            }
        }
        catch (Exception ex) {
            log.error((Object)("Error en RequerimientoNoProgramadoServiceImpl.validaExisteMetaJson: " + ex.getMessage()), (Throwable)ex);
            throw new ServiceException((Object)this, ex);
        }
    }
    finally {
        if (log.isDebugEnabled()) {
            log.debug((Object)"Fin - RequerimientoNoProgramadoServiceImpl.validaExisteMetaJson");
        }
    }
    return sw;
}

@Override
public boolean validaMetaVacia(Map<String, Object> params) {
    log.debug((Object)"Inicio - RequerimientoNoProgramadoServiceImpl.validaMetaVacia");
    boolean sw = false;
    try {
        List<RequerimientoNoProgMetasBean> lista = (List<RequerimientoNoProgMetasBean>)this.requerimientoNoProgMetasDao.validaMetaVacia(params);
        if (lista != null) {
            if (lista.size() > 0) {
                for (RequerimientoNoProgMetasBean obj : lista) {
                    log.debug((Object)("bien " + obj.getCodigoBien() + " - " + obj.getCantidadTotal() + " - " + obj.getMontoSoles()));
                    if (obj.getMontoSoles() != null) {
                        if (obj.getMontoSoles().doubleValue() != 0.0) continue;
                        boolean bl = sw = true;
                        return bl;
                    }
                    boolean bl = sw = true;
                    return bl;
                }
            } else {
                boolean bl = sw = true;
                return bl;
            }
        }
        boolean bl = sw;
        return bl;
    }
    catch (Exception ex) {
        log.error((Object)("Error en RequerimientoNoProgramadoServiceImpl.validaMetaVacia: " + ex.getMessage()), (Throwable)ex);
        throw new ServiceException((Object)this, ex);
    }
    finally {
        log.debug((Object)"Fin - RequerimientoNoProgramadoServiceImpl.validaMetaVacia");
    }
}


@Override
public DependenciaBean obtenerDpg(String codParametro, String descParametro) throws ServiceException {
    log.debug((Object)"Inicio - RequerimientoNoProgramadoServiceImpl.obtenerDpg");
    String codDependencia =rqnpBandejaDao.obtenerDpg(codParametro, descParametro);
    try {
    	
        DependenciaBean dependenciaBean = this.dependenciaDao.obtenerDependencia(codDependencia);
        return dependenciaBean;
    }
    catch (Exception ex) {
        log.error((Object)("Error en RequerimientoNoProgramadoServiceImpl.obtenerDpg: " + ex.getMessage()), (Throwable)ex);
        throw new ServiceException((Object)this, ex);
    }
    finally {
        log.debug((Object)"Fin - RequerimientoNoProgramadoServiceImpl.obtenerDpg");
    }
}



@Override
public Map<String, Object> obtenerSecuFuncUuoo(Map<String, Object> params) {
	if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.obtenerSecuFuncUuoo");
	Map<String, Object> map=new HashMap<String, Object>();
	try {
		map=requerimientoNoProgMetasDao.obtenerSecuFuncUuoo(params);
	}catch (Exception ex) {
		   log.error("Error en RequerimientoNoProgramadoServiceImpl.obtenerSecuFuncUuoo: " + ex.getMessage(), ex);
		   throw new ServiceException(this, ex);
		} 
	finally {
		if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.obtenerSecuFuncUuoo");
	}
	return map;
}



public Collection listarTipoVinculo() {
	Map<String, Object> param = new HashMap<String, Object>();
	Map<String, Object> map;
	if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.listarTipoVinculo");
	 
	List<Map<String, Object>> lsTipoVinculo= new ArrayList<Map<String,Object>>();
	List<T01ParametroBean>lista = new ArrayList<T01ParametroBean>();
try{	 
		param.put("cod_par", "4031");
		param.put("cod_tipo","D");
		 
		lista =( List<T01ParametroBean> ) t01ParametroDao.recuperarParametroLista(param);
		for(T01ParametroBean data: lista){
			map = new HashMap<String, Object>();
			map.put("cod", data.getCod_argumento().trim());
			map.put("name", data.getNom_largo().trim());
			lsTipoVinculo.add(map);
		 }
		
		return lsTipoVinculo;

}catch (Exception ex) {
   log.error("Error en RequerimientoNoProgramadoServiceImpl.listarTipoVinculo: " + ex.getMessage(), ex);
   throw new ServiceException(this, ex);
} 
finally {
	if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.listarTipoVinculo");
}
	

}

/**
 * @author dporrasc
 * Recupera la vinculo 
 * @param String cod_parm - Codigo de Parametro
 * @return RequerimientoNoProgramadoBean - 
 */  
@Override
public Collection listarVinculo(String cod_parm) {
		 Map<String, Object> param = new HashMap<String, Object>();
		 Map<String, Object> map;
		 if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.listarVinculo");
		 
		 List<Map<String, Object>> lsVinculo= new ArrayList<Map<String,Object>>();
		 List<T01ParametroBean>lista = new ArrayList<T01ParametroBean>();
	try{	 
		 if (cod_parm != null){
			 
			 if(cod_parm.equals("01")){
				 param.put("cod_par", "4032");
			 }else if(cod_parm.equals("02")){
				 param.put("cod_par", "4033");
			 }else if(cod_parm.equals("03")){
				 param.put("cod_par", "4034");
			 }else if(cod_parm.equals("04")){
				 param.put("cod_par", "4040");
				 param.put("cod_arg", "01");
			 }
			 param.put("cod_tipo","D");
			 //param.put("cod_arg", "04");
			 
			 lista =( List<T01ParametroBean> ) t01ParametroDao.recuperarParametroLista(param);
			 for(T01ParametroBean data: lista){
					map = new HashMap<String, Object>();
					map.put("cod", data.getCod_argumento().trim());
					map.put("name", data.getNom_largo().trim());
					lsVinculo.add(map);
			 }
			
		 }else{
			 return lsVinculo ;
		 }
			 
	return lsVinculo;
	
}catch (Exception ex) {
	   log.error("Error en RequerimientoNoProgramadoServiceImpl.listarVinculo: " + ex.getMessage(), ex);
	   throw new ServiceException(this, ex);
}finally {
	if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.listarVinculo");
}

}

/**
 * @author dporrasc
 * Obtiene el saldo de un item seleccionado
 */
@Override
public BigDecimal getSaldoItem(Map<String, Object> params) throws ServiceException {
    log.debug((Object)"Inicio - RequerimientoNoProgramadoServiceImpl.getSaldoItem");
    BigDecimal saldoItem = new BigDecimal(0);
    try {
    	
    	saldoItem = this.rqnpBandejaDao.saldoCD(params);
        
    }
    catch (Exception ex) {
        log.error((Object)("Error en RequerimientoNoProgramadoServiceImpl.getSaldoItem: " + ex.getMessage()), (Throwable)ex);
        throw new ServiceException((Object)this, ex);
    }
    finally {
        log.debug((Object)"Fin - RequerimientoNoProgramadoServiceImpl.getSaldoItem");
    }
    return saldoItem;
}


//getter and setter

public RequerimientoNoProgramadoDao getRequerimientoNoProgramadoDao() {
	return requerimientoNoProgramadoDao;
}
public void setRequerimientoNoProgramadoDao(
		RequerimientoNoProgramadoDao requerimientoNoProgramadoDao) {
	this.requerimientoNoProgramadoDao = requerimientoNoProgramadoDao;
}
public CatalogoBienesDao getCatalogoBienesDao() {
	return catalogoBienesDao;
}
public void setCatalogoBienesDao(CatalogoBienesDao catalogoBienesDao) {
	this.catalogoBienesDao = catalogoBienesDao;
}
public RequerimientoNoProgBienesDao getRequerimientoNoProgBienesDao() {
	return requerimientoNoProgBienesDao;
}
public void setRequerimientoNoProgBienesDao(
		RequerimientoNoProgBienesDao requerimientoNoProgBienesDao) {
	this.requerimientoNoProgBienesDao = requerimientoNoProgBienesDao;
}
public RequerimientoNoProgMetasDao getRequerimientoNoProgMetasDao() {
	return requerimientoNoProgMetasDao;
}
public void setRequerimientoNoProgMetasDao(
		RequerimientoNoProgMetasDao requerimientoNoProgMetasDao) {
	this.requerimientoNoProgMetasDao = requerimientoNoProgMetasDao;
}
public AccesoDAO getAccesoDao() {
	return accesoDao;
}
public void setAccesoDao(AccesoDAO accesoDao) {
	this.accesoDao = accesoDao;
}
public MaestroPersonalDAO getMaestroPersonalDao() {
	return maestroPersonalDao;
}
public void setMaestroPersonalDao(MaestroPersonalDAO maestroPersonalDao) {
	this.maestroPersonalDao = maestroPersonalDao;
}
public DependenciaDAO getDependenciaDao() {
	return dependenciaDao;
}
public void setDependenciaDao(DependenciaDAO dependenciaDao) {
	this.dependenciaDao = dependenciaDao;
}
public RqnpBandejaDao getRqnpBandejaDao() {
	return rqnpBandejaDao;
}
public void setRqnpBandejaDao(RqnpBandejaDao rqnpBandejaDao) {
	this.rqnpBandejaDao = rqnpBandejaDao;
}
public SysEstadosDAO getSysEstadosDao() {
	return sysEstadosDao;
}
public void setSysEstadosDao(SysEstadosDAO sysEstadosDao) {
	this.sysEstadosDao = sysEstadosDao;
}
public ExpedienteInternoAccionDAO getExpedienteInternoAccionDao() {
	return expedienteInternoAccionDao;
}
public void setExpedienteInternoAccionDao(
		ExpedienteInternoAccionDAO expedienteInternoAccionDao) {
	this.expedienteInternoAccionDao = expedienteInternoAccionDao;
}
public SysParametrosDAO getSysParametrosDao() {
	return sysParametrosDao;
}
public void setSysParametrosDao(SysParametrosDAO sysParametrosDao) {
	this.sysParametrosDao = sysParametrosDao;
}
public ObjetivosDAO getObjetivosDao() {
	return objetivosDao;
}
public void setObjetivosDao(ObjetivosDAO objetivosDao) {
	this.objetivosDao = objetivosDao;
}
public MaestroContratistasDAO getMaestroContratistasDao() {
	return maestroContratistasDao;
}
public void setMaestroContratistasDao(
		MaestroContratistasDAO maestroContratistasDao) {
	this.maestroContratistasDao = maestroContratistasDao;
}
public RegistroPersonalService getRegistroPersonalService() {
	return registroPersonalService;
}
public void setRegistroPersonalService(
		RegistroPersonalService registroPersonalService) {
	this.registroPersonalService = registroPersonalService;
}
public MetasProyectosDao getMetasProyectosDao() {
	return metasProyectosDao;
}
public void setMetasProyectosDao(MetasProyectosDao metasProyectosDao) {
	this.metasProyectosDao = metasProyectosDao;
}
public T01ParametroDAO getT01ParametroDao() {
	return t01ParametroDao;
}
public void setT01ParametroDao(T01ParametroDAO t01ParametroDao) {
	this.t01ParametroDao = t01ParametroDao;
}

}
