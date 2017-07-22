package pe.gob.sunat.administracion2.siga.rqnp.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jndi.JndiTemplate;

import pe.gob.sunat.administracion2.siga.rqnp.model.dao.SolicitudBienDao;
import pe.gob.sunat.administracion2.siga.rqnp.model.dao.UnidadMedidaDao;
import pe.gob.sunat.administracion2.siga.rqnp.model.domain.ConsultaCatalogoBean;
import pe.gob.sunat.administracion2.siga.rqnp.model.domain.RequerimientoNoProgramadoBean;
import pe.gob.sunat.administracion2.siga.rqnp.model.domain.SolicitudBienBean;
import pe.gob.sunat.administracion2.siga.rqnp.model.domain.UnidadMedidaBean;
import pe.gob.sunat.framework.spring.util.exception.ServiceException;
import pe.gob.sunat.recurso2.administracion.siga.parametro.model.bean.SysEstadosBean;
import pe.gob.sunat.recurso2.administracion.siga.parametro.model.bean.T01ParametroBean;
import pe.gob.sunat.recurso2.administracion.siga.parametro.model.dao.SysEstadosDAO;
import pe.gob.sunat.recurso2.administracion.siga.parametro.model.dao.SysParametrosDAO;
import pe.gob.sunat.recurso2.administracion.siga.parametro.model.dao.T01ParametroDAO;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.DependenciaBean;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.MaestroPersonalBean;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.dao.AccesoDAO;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.dao.DependenciaDAO;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.dao.MaestroPersonalDAO;

public class SolicitudBienServiceImpl  implements SolicitudBienService{
	private static final Log log = LogFactory.getLog(SolicitudBienServiceImpl.class);
	
	private SolicitudBienDao 	solicitudBienDao;
	private UnidadMedidaDao  	unidadMedidaDao;
	private AccesoDAO		 	accesoDao;
	T01ParametroDAO				t01ParametroDao;
	DependenciaDAO				dependenciaDao;
	MaestroPersonalDAO 			maestroPersonalDao;
	SysEstadosDAO				sysEstadosDao;
	SysParametrosDAO			sysParametrosDao;
	
	/**
     * Lista de Solitud por Usuario
     * @param Map<String, Object>
     * @return Collection - Lista de Solicitudes
     */  
	@Override
	public Collection listarSolicitudXPersona(Map<String, Object> params) {
		if (log.isDebugEnabled()) log.debug("Inicio - SolicitudBienServiceImpl.listarSolicitidXPersona");
		
		List<Map<String, Object>> lstSol = new ArrayList<Map<String,Object>>();
		Map<String, Object> map_soli;
		char ini=' ' ;
		char ini2= "'".charAt(0) ;
		char salto =(char)10;
		char retorno =(char)13;
		char espacio =(char)32;
		
		CharSequence comi ="&quot";
		CharSequence comis ="\"";
		
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		try {
			List<SolicitudBienBean> lista = (List<SolicitudBienBean>) solicitudBienDao.listarSolicitudes(params);
			
			for(SolicitudBienBean obj: lista){
				map_soli = new HashMap<String, Object>();

				map_soli.put("cod_solicitud", obj.getCod_solicitud());
				map_soli.put("cod_bien_rpta", obj.getCod_bien_rpta());
				map_soli.put("nombre_bien", obj.getNombre_bien().replace( comis,comi).replace(salto, espacio).replace(retorno, espacio));
				map_soli.put("cod_empl_sol", obj.getCod_empl_sol());
				map_soli.put("nombre_sol", obj.getNombre_sol().replace( comis,comi).replace(salto, espacio).replace(retorno, espacio));
				map_soli.put("estado", obj.getEstado());
				map_soli.put("nom_estado", obj.getNom_estado());
				
				String fecha_sol="";
				String fecha_rpta="";
				String fecha_envio="";
				String fecha_aprueba="";
				
				if(obj.getFecha_sol()!=null){
					fecha_sol = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(obj.getFecha_sol());
					log.debug("fecha_sol ------>>>>>>" +fecha_sol);
				}
				if(obj.getFecha_rpta()!=null){
					fecha_rpta = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(obj.getFecha_rpta());
				}
				
				if(obj.getFecha_envio()!=null){
					fecha_envio = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(obj.getFecha_envio());
				}
				
				if(obj.getFecha_aprueba()!=null){
					fecha_aprueba = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(obj.getFecha_aprueba());
				}
				map_soli.put("fecha_sol",  fecha_sol );
				map_soli.put("fecha_rpta",fecha_rpta);
				map_soli.put("fecha_envio",fecha_envio);
				map_soli.put("fecha_aprueba",fecha_aprueba);
				lstSol.add(map_soli);
			}
			return lstSol;
		} 
		catch (Exception ex) {
			   log.error("Error en SolicitudBienServiceImpl.listarSolicitidXPersona: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
		}
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - SolicitudBienServiceImpl.listarSolicitidXPersona");
		}
	}

	/**
     * Lista de Unidad de Medida
     * @return Collection - Lista de Unidades de medida
     */  
	@Override
	public Collection listarUnidadMedidad() {
		if (log.isDebugEnabled()) log.debug("Inicio - SolicitudBienServiceImpl.listarUnidadMedidad");
		
		List<Map<String, Object>> lstUnidad = new ArrayList<Map<String,Object>>();
		Map<String, Object> map_Unidad;
		try {
			List<UnidadMedidaBean> lista = (List<UnidadMedidaBean>) unidadMedidaDao.listarUnidades();
			
			for(UnidadMedidaBean obj: lista){
				map_Unidad = new HashMap<String, Object>();

				map_Unidad.put("cod_unidad", obj.getCod_unidad());
				map_Unidad.put("nom_corto", obj.getNom_corto());
				map_Unidad.put("nom_largo", obj.getNom_largo());
				map_Unidad.put("equi_unid", obj.getEqui_unid());
				
				lstUnidad.add(map_Unidad);
			}
			map_Unidad = new HashMap<String, Object>();
			
			lstUnidad.add(map_Unidad);
			
			return lstUnidad;
		} 
		catch (Exception ex) {
			   log.error("Error en SolicitudBienServiceImpl.listarUnidadMedidad: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
		}
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - SolicitudBienServiceImpl.listarUnidadMedidad");
		}
	
	}
	
	/**
     * Recuperar de Solitud de Usuario
     * @param String - C�digo de Solicitud
     * @return Map - Solicitud 
     */  
	@Override
	public Map recuperarSolictudBien(String cod_sol) {
		if (log.isDebugEnabled()) log.debug("Inicio - SolicitudBienServiceImpl.recuperarSolictudBien");
		
		Map<String, Object> map_sol = new HashMap<String, Object>();
		char ini=' ' ;
		String cod_auc ;
		char ini2= "'".charAt(0) ;
		char salto =(char)10;
		char retorno =(char)13;
		char espacio =(char)32;
		String nom_empl_rpta ="";
		String cod_empl_rpta="";
		String cod_contacto="";
		String nom_contacto="";
		String reg_contacto="";
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		try {
			SolicitudBienBean bien = solicitudBienDao.recuperarSolicitud(cod_sol);
			if (bien !=null){
				map_sol.put("cod_solicitud", bien.getCod_solicitud());
				map_sol.put("cod_bien_rpta",(bien.getCod_bien_rpta() ==null) ? "" : bien.getCod_bien_rpta() );
				map_sol.put("cod_empl_sol", bien.getCod_empl_sol());
				map_sol.put("nombre_bien",(bien.getNombre_bien() ==null) ? "" : bien.getNombre_bien().replace(salto, espacio).replace(retorno, espacio)  );
				map_sol.put("otras_caract",(bien.getOtras_caract() ==null) ? "" : bien.getOtras_caract().replace(salto, espacio).replace(retorno, espacio)  );
				
				String fecha_sol="";
				String fecha_rpta="";
				String fecha_envio="";
				String fecha_aprueba="";
				
				if(bien.getFecha_sol()!=null){
					fecha_sol = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(bien.getFecha_sol());
					log.debug("fecha_sol ------>>>>>>" +fecha_sol);
					
				}
				if(bien.getFecha_rpta()!=null){
					fecha_rpta = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(bien.getFecha_rpta());
					log.debug("fecha_rpta ------>>>>>>" +fecha_rpta);
				}
				
				if(bien.getFecha_envio()!=null){
					fecha_envio = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(bien.getFecha_envio());
					log.debug("fecha_envio ------>>>>>>" +fecha_envio);
				}
				
				if(bien.getFecha_aprueba()!=null){
					fecha_aprueba = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(bien.getFecha_aprueba());
					log.debug("fecha_aprueba ------>>>>>>" +fecha_aprueba);
				}
				map_sol.put("fecha_sol",  fecha_sol );
				map_sol.put("fecha_rpta",fecha_rpta);
				//////////////////////////////////////////////////////////////////////////////////////////////////
				map_sol.put("fecha_envio",fecha_envio);
				map_sol.put("fecha_aprueba",fecha_aprueba);
				
				//log.debug("fecha_aprueba" +  (bien.getFecha_aprueba())==null? " ": formateador.format(bien.getFecha_aprueba()));
				///////////////////////////////////////////
				map_sol.put("unidad_sol",(bien.getUnidad_sol() ==null) ? "-1" : bien.getUnidad_sol()  );
				map_sol.put("precio_sol",(bien.getPrecio_sol() ==null) ? "" : bien.getPrecio_sol().setScale(2, RoundingMode.HALF_UP).toString() );
				map_sol.put("comentario_rpta",(bien.getComentario_rpta() ==null) ? "" : bien.getComentario_rpta().replace(salto, espacio).replace(retorno, espacio));
				map_sol.put("estado", bien.getEstado()==null? "": bien.getEstado());
				
				cod_empl_rpta =bien.getCod_emp_rpta()==null? "": bien.getCod_emp_rpta();
				log.debug("cod_empl_rpta" + cod_empl_rpta);
				map_sol.put("cod_emp_rpta", cod_empl_rpta);
				map_sol.put("cod_sede_sol", bien.getCod_sede_sol()==null? "": bien.getCod_sede_sol());
				map_sol.put("flag", bien.getFlag());
				
				//map_sol.put("tipo_bien", bien.getTipo_bien().replace(salto, espacio).replace(retorno, espacio));
				map_sol.put("tipo_bien", (bien.getTipo_bien()==null) ? "" :bien.getTipo_bien().replace(salto, espacio).replace(retorno, espacio));
				log.debug ( "tipo_bien " + ((bien.getTipo_bien()==null) ? "" :bien.getTipo_bien().replace(salto, espacio).replace(retorno, espacio)));
				//(INICIO)MODIFICADO DESPUES DEL PASE
				map_sol.put("tipo_sol", bien.getTipo_sol()==null ? "":bien.getTipo_sol().replace(salto, espacio).replace(retorno, espacio));
				//(FIN)MODIFICADO DESPUES DEL PASE
				map_sol.put("presentacion", (bien.getPresentacion() ==null) ? "" : bien.getPresentacion().replace(salto, espacio).replace(retorno, espacio) );
				map_sol.put("caract_tecnica", (bien.getCaract_tecnica() ==null) ? "" : bien.getCaract_tecnica().replace(salto, espacio).replace(retorno, espacio));
				map_sol.put("caract_fisica", (bien.getCaract_fisica() ==null) ? "" : bien.getCaract_fisica().replace(salto, espacio).replace(retorno, espacio));
				map_sol.put("suger_proveedor",(bien.getSuger_proveedor() ==null) ? "" : bien.getSuger_proveedor().replace(salto, espacio).replace(retorno, espacio) );
				map_sol.put("tiempo_expira",(bien.getTiempo_expira() ==null) ? "" : bien.getTiempo_expira().replace(salto, espacio).replace(retorno, espacio) );
				map_sol.put("envase", (bien.getEnvase() ==null) ? "" : bien.getEnvase().replace(salto, espacio).replace(retorno, espacio));
				map_sol.put("nombre_sol", bien.getNombre_sol()==null? "": bien.getNombre_sol());
				map_sol.put("nombre_uuo", bien.getNombre_uuo()==null? "": bien.getNombre_uuo());
				map_sol.put("cod_unidad_rpta",(bien.getCod_unidad_rpta() ==null) ? "" : bien.getCod_unidad_rpta() );
				map_sol.put("precio_rpta",(bien.getPrecio_rpta() ==null) ? "" : bien.getPrecio_rpta().setScale(2, RoundingMode.HALF_UP).toString()  );
				map_sol.put("nom_bien_rpta",(bien.getNom_bien_rpta() ==null) ? "" : bien.getNom_bien_rpta() );
				map_sol.put("unidad_rpta",(bien.getUnidad_rpta() ==null) ? "" : bien.getUnidad_rpta() );
				map_sol.put("nom_estado", (bien.getNom_estado() ==null) ? "" : bien.getNom_estado());
				//log.debug("getCod_categoria" +bien.getCod_categoria().trim().substring(0, 2) );
				map_sol.put("cod_categoria", (bien.getCod_categoria() ==null) ? "" : bien.getCod_categoria().substring(0,2));
				map_sol.put("cod_subcategoria", (bien.getCod_categoria() ==null) ? "" : bien.getCod_categoria().trim());
				log.debug(" numero_archivo " + ((bien.getNumeroArchivo() ==null) ? "" : bien.getNumeroArchivo()));
				map_sol.put("numero_archivo", (bien.getNumeroArchivo() ==null) ? "" : bien.getNumeroArchivo());
				
				cod_auc = (bien.getCod_auc() ==null) ? "" : bien.getCod_auc();
				
				map_sol.put("cod_auc",cod_auc);
				
				//////////////////////////////////////////////////////////////////////////////
				cod_contacto =bien.getCod_contacto()==null? "": bien.getCod_contacto();
				map_sol.put("reg_contacto", (bien.getCod_contacto() ==null) ? "" : bien.getCod_contacto());
				map_sol.put("anexo_contacto", (bien.getNum_anexo_contacto() ==null) ? "" : bien.getNum_anexo_contacto());
				map_sol.put("obs_motivo", (bien.getObs_motivo() ==null) ? "" : bien.getObs_motivo().trim());
				
				/////////////////////////////////////////////////////////////////////////////
				//recuperando auc 
				
				DependenciaBean dependencia= dependenciaDao.obtenerDependencia(cod_auc);
				if (dependencia != null ){
					map_sol.put("cod_dep",dependencia.getCod_dep());
					map_sol.put("uuoo_dep",dependencia.getUuoo());
					map_sol.put("nom_dep",dependencia.getNom_corto());
					map_sol.put("auc",dependencia.getUuoo() + " " +dependencia.getNom_largo());
				}
				//recueprando nombre respuesta
				if (!cod_empl_rpta.equals("")  ){
					MaestroPersonalBean maestro  = maestroPersonalDao.obtenerPersonaxCodigo(cod_empl_rpta);
					if( maestro != null){
						nom_empl_rpta = maestro.getNombre_completo();
					}
				}
				
				//RECUPERANDO DATOS DEL CONTACTO
				if (!cod_contacto.equals("")  ){
					MaestroPersonalBean personal  = maestroPersonalDao.obtenerPersonaxCodigo(cod_contacto);
					if( personal != null){
						nom_contacto = personal.getNombre_completo();
						reg_contacto = personal.getNumero_registro();
					}
				}
				
				map_sol.put("nom_empl_rpta",nom_empl_rpta);
				
				map_sol.put("nom_contacto",nom_contacto);
				map_sol.put("reg_contacto",reg_contacto);
				
				log.debug("nom_empl_rpta" + nom_empl_rpta);
			}
			return map_sol;
		} 
		catch (Exception ex) {
			   log.error("Error en SolicitudBienServiceImpl.recuperarSolictudBien: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
		}
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - SolicitudBienServiceImpl.recuperarSolictudBien");
		}
	}
	
	
	
	@Override
	public Map recuperarDependencia(String cod_dep) {
		if (log.isDebugEnabled()) log.debug("Inicio - SolicitudBienServiceImpl.recuperarDependencia");
		
		Map<String, Object> map_dep = new HashMap<String, Object>();
	
		try{
		//recuperando auc 
	
		DependenciaBean dependencia= dependenciaDao.obtenerDependencia(cod_dep);
		if (dependencia != null ){
			map_dep.put("cod_dep",dependencia.getCod_dep());
			map_dep.put("uuoo_dep",dependencia.getUuoo());
			map_dep.put("nom_dep",dependencia.getNom_corto());
			map_dep.put("auc",dependencia.getUuoo() + " " +dependencia.getNom_largo()); //DPORRASC-CATALOGO
		}
		
		return map_dep;
		} 
		catch (Exception ex) {
			   log.error("Error en SolicitudBienServiceImpl.recuperarDependencia: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
		}
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - SolicitudBienServiceImpl.recuperarDependencia");
	}
}
	
	
	/**
     * Registrar de Solitud de Usuario
     * @param Map<String, Object> - params
     * @return String - C�digo de solicitud
     */  
	@Override
	public String registrarSolictudBien(Map<String, Object> params) {
		if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.registrarSolictudBien");
		String j_crud ="";
		
		String activar_flujo_auc="";
		
		SolicitudBienBean solicitud = new SolicitudBienBean();
		Long ll_count =new Long(0);
		String user =(String) params.get ("user");
		solicitud.setFecha_sol(new Date());
		j_crud= (String)params.get("j_crud");
		solicitud.setTipo_bien((String)params.get("txtTipBien"));
		solicitud.setTipo_sol((String)params.get("txtTipoSol"));
		solicitud.setNombre_bien((String)params.get("txtNomBien"));
		solicitud.setPresentacion((String)params.get("txtPresentacion"));
		solicitud.setCaract_tecnica((String)params.get("txtCaract_tecnica"));
		solicitud.setCaract_fisica((String)params.get("txtCaract_fisica"));
		solicitud.setUnidad_sol((String)params.get("txtUnidad_sol"));
		solicitud.setPrecio_sol( new BigDecimal( (String) params.get("txtPrecio_sol")) );
		solicitud.setTiempo_expira((String)params.get("txtTiempo_expira"));
		solicitud.setOtras_caract((String)params.get("txtOtras_caract"));
		
		solicitud.setSuger_proveedor((String)params.get("txtSuger_proveedor"));
		solicitud.setEnvase((String)params.get("txtEnvase"));
		solicitud.setEstado("0");
		solicitud.setCod_empl_sol((String)params.get("txtSolcitante"));
		solicitud.setCod_sede_sol((String)params.get("txtSede"));
		
		solicitud.setCod_categoria((String) params.get("txtSubCategoria"));
		solicitud.setCod_auc(( String) params.get("cod_auc"));
		
		////////////////////////////////////////////////////////////
		solicitud.setCod_contacto(( String) params.get("txtCod_contacto"));
		solicitud.setNum_anexo_contacto(( String) params.get("txtNum_anexo_contacto"));
		solicitud.setObs_motivo(( String) params.get("txtObs_motivo"));
		////////////////////////////////////////////////////////////
		
		try {
			DataSource dataSource = obtenerDataSource("sig", false);
			if (j_crud.equals("insert")){
				
				ll_count= solicitudBienDao.obtenerContador();
				solicitud.setCod_solicitud(ll_count.toString());
				
				accesoDao.setUsuarioAcceso(dataSource,"USER", user);
				solicitudBienDao.insertar(dataSource, solicitud);
				accesoDao.setUsuarioAccesoNull(dataSource,"USER");
				
			}else if(j_crud.equals("update")){
				
				ll_count=new Long( (String)params.get("txtCodSol"));
				solicitud.setCod_solicitud(ll_count.toString());
				
				accesoDao.setUsuarioAcceso(dataSource,"USER", user);
				solicitudBienDao.update(dataSource, solicitud);
				accesoDao.setUsuarioAccesoNull(dataSource,"USER");
				
			}else if (j_crud.equals("enviar")){
				////////////////////////////////////
				activar_flujo_auc= sysParametrosDao.activarFlujoAuc();
				if(activar_flujo_auc.equals("1")){
					solicitud.setEstado("1");
				}else{
					solicitud.setEstado("4");
				}
				/*
				 * QUITAR COMENTARIO PARA EL SIGUIENTE PASE
				solicitud.setEstado("1");
				*/
				////////////////////////////////////
				
				solicitud.setFecha_envio(new Date());
				ll_count=new Long( (String)params.get("txtCodSol"));
				solicitud.setCod_solicitud(ll_count.toString());
				
				accesoDao.setUsuarioAcceso(dataSource,"USER", user);
				solicitudBienDao.updateEstado(dataSource, solicitud);
				accesoDao.setUsuarioAccesoNull(dataSource,"USER");
			}
		
		}catch (Exception ex) {
			   log.error("Error en RequerimientoNoProgramadoServiceImpl.registrarSolictudBien: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
			} 
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.registrarSolictudBien");
		}
		
		return ll_count.toString();
	}
	
	
	/**
     * Recuperar Parametros de T01
     * @param Map<String, Object>  - params
     * @return T01ParametroBean - t01parametro
     */ 
	@Override
	public T01ParametroBean obtenerParametro(Map<String, Object> params ) {
		if (log.isDebugEnabled()) log.debug("Inicio - SolicitudBienServiceImpl.obtenerParametro");
		try {
			
			return t01ParametroDao.recuperarParametro(params);
		} catch (Exception ex) {
			   log.error("Error en SolicitudBienServiceImpl.obtenerParametro: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
		}
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - SolicitudBienServiceImpl.obtenerParametro");
		}
	}
	
	
	/**
     * Recuperar Parametros de T01
     * @param Map<String, Object>  - params
     * @return T01ParametroBean - t01parametro
     */ 
	@Override
	public Collection obtenerParametroLista(Map<String, Object> params ) {
		if (log.isDebugEnabled()) log.debug("Inicio - SolicitudBienServiceImpl.obtenerParametroLista");
		List<Map<String, Object>> lsParametros= new ArrayList<Map<String,Object>>();
		 List<T01ParametroBean>lista = new ArrayList<T01ParametroBean>();
		 Map<String, Object> map;
		 
		try {
			 lista =( List<T01ParametroBean> ) t01ParametroDao.recuperarParametroLista(params);
			 for(T01ParametroBean data: lista){
					map = new HashMap<String, Object>();
					map.put("cod", data.getCod_argumento().trim());
					map.put("name", data.getCod_argumento().trim()+' '+data.getNom_largo().trim()); //DPORRASC CATALOGO
					lsParametros.add(map);
			 }
			return lsParametros;
		} catch (Exception ex) {
			   log.error("Error en SolicitudBienServiceImpl.obtenerParametroLista: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
		}
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - SolicitudBienServiceImpl.obtenerParametroLista");
		}
	}
	
	
	
	/**
     * Recuperar Parametros de T01
     * @param Map<String, Object>  - params
     * @return T01ParametroBean - t01parametro
     */ 
	@Override
	public Collection obtenerEstadosLista(Map<String, Object> params ){
		if (log.isDebugEnabled()) log.debug("Inicio - SolicitudBienServiceImpl.obtenerEstadosLista");
		List<Map<String, Object>> lsParametros= new ArrayList<Map<String,Object>>();
		 List<SysEstadosBean>lista = new ArrayList<SysEstadosBean>();
		 Map<String, Object> map;
		 
		try {
			lista =( List<SysEstadosBean> )sysEstadosDao.listarEstados(params);
			for(SysEstadosBean data: lista){
					map = new HashMap<String, Object>();
					map.put("cod", data.getVal_estado() .trim());
					map.put("name", data.getDesc_estado().trim());
					lsParametros.add(map);
			}
			return lsParametros;
		} catch (Exception ex) {
			   log.error("Error en SolicitudBienServiceImpl.obtenerEstadosLista: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
		}
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - SolicitudBienServiceImpl.obtenerEstadosLista");
		}
	}
	
	
	
	/**
     * Registra el N�mero de Registro de Archivos Adjuntos.
     * @param String cod_rqnp - C�digo de Rqnp
     * @param String cod_bien - Codigo de �tem
     * @return void
     */
	@Override
	public void updateFile(String cod_sol, String num_reg, String user) {
		if (log.isDebugEnabled()) log.debug("Fin - SolicitudBienServiceImpl.updateFile");
		try{	
			DataSource dataSource = obtenerDataSource("sig", false);
			SolicitudBienBean bean = new SolicitudBienBean();
			bean.setCod_solicitud (cod_sol);
			bean.setNumeroArchivo(num_reg);
		
			accesoDao.setUsuarioAcceso(dataSource,"USER", user);
			solicitudBienDao.updateFile(dataSource, bean);
			accesoDao.setUsuarioAccesoNull(dataSource,"USER");
			
		}catch (Exception ex) {
		   log.error("Error en SolicitudBienServiceImpl.updateFile: " + ex.getMessage(), ex);
		   throw new ServiceException(this, ex);
		} 
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - SolicitudBienServiceImpl.updateFile");
		}
	}
	
	
	/**
     * Registra la Aprobacion por parte de auc.
     * @param SolicitudBienBean bean 
     * @param String user - usuario
     * @return void
     */
	@Override
	public void updateAprueba(SolicitudBienBean bean, String user) {
		if (log.isDebugEnabled()) log.debug("Fin - SolicitudBienServiceImpl.updateAprueba");
		try{	
			DataSource dataSource = obtenerDataSource("sig", false);
			
		
			accesoDao.setUsuarioAcceso(dataSource,"USER", user);
			solicitudBienDao.updateAprueba(dataSource, bean);
			accesoDao.setUsuarioAccesoNull(dataSource,"USER");
			
		}catch (Exception ex) {
		   log.error("Error en SolicitudBienServiceImpl.updateAprueba: " + ex.getMessage(), ex);
		   throw new ServiceException(this, ex);
		} 
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - SolicitudBienServiceImpl.updateAprueba");
		}
	}
	
	
	/**
     * Registra el Rechazo por parte de auc.
     * @param SolicitudBienBean bean 
     * @param String user - usuario
     * @return void
     */
	@Override
	public void updateRechaza(SolicitudBienBean bean, String user) {
		if (log.isDebugEnabled()) log.debug("Fin - SolicitudBienServiceImpl.updateRechaza");
		try{	
			DataSource dataSource = obtenerDataSource("sig", false);
			
		
			accesoDao.setUsuarioAcceso(dataSource,"USER", user);
			solicitudBienDao.updateRechaza(dataSource, bean);
			accesoDao.setUsuarioAccesoNull(dataSource,"USER");
			
		}catch (Exception ex) {
		   log.error("Error en SolicitudBienServiceImpl.updateRechaza: " + ex.getMessage(), ex);
		   throw new ServiceException(this, ex);
		} 
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - SolicitudBienServiceImpl.updateRechaza");
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

	
	
	/**
     * Registra el envio a las bandeja de la AUC para la incorporacion al catalogo.
     * @param Map<String, Object> params 
     * @return BigDecimal - C�digo de RQNP
     */
	@Override
	public String EnviarMailRqnp(Map<String, Object> params) {
		if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.EnviarMailRqnp");
		RequerimientoNoProgramadoBean rqnp= new RequerimientoNoProgramadoBean();
		
		SolicitudBienBean  solicitud = new SolicitudBienBean();
		
		List<Map<String, Object>> listaBienes = new ArrayList<Map<String,Object>>();
		
		String cod_req =(String) params.get("txtCodSol");
	
		StringBuilder ls_bien=new StringBuilder("");
		 
		try {
			solicitud= this.recuperarSolicitudBienBean(cod_req);	
			
			String tipo_msg=(String)params.get("tipo_msg");
			
			solicitudBienDao.envioMailDerivar(tipo_msg,cod_req);
			
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
     * Recupera un  Requerimientos No Programado 
     * @param String cod_req - C�digo de Requerimeinto no Programado 
     * @return RequerimientoNoProgramadoBean - Requerimiento no Programado
     */  
	@Override
	public SolicitudBienBean recuperarSolicitudBienBean(String cod_req) {
		if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoNoProgramadoServiceImpl.recuperarSolicitudBienBean");
		
		List<Map<String, Object>> lsbienes = new ArrayList<Map<String,Object>>();
		Map<String, Object> SolicitudBienBean ;
		//AGREGADO (DPORRASC)
		String tipo_auc;
		char ini=' ' ;
		char ini2= "'".charAt(0) ;
		char salto =(char)10;
		char retorno =(char)13;
		char espacio =(char)32;
		CharSequence comi ="&quot";
		CharSequence comis ="\"";
		
		String cod_auc ;
		String nom_empl_rpta ="";
		String cod_empl_rpta="";
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
		
		try{
			SolicitudBienBean bien = solicitudBienDao.recuperarSolicitudBienBean(cod_req);
			return bien;
		
	}catch (Exception ex) {
		   log.error("Error en RequerimientoNoProgramadoServiceImpl.RecuperarRqnp: " + ex.getMessage(), ex);
		   throw new ServiceException(this, ex);
		} 
	finally {
		if (log.isDebugEnabled()) log.debug("Fin - RequerimientoNoProgramadoServiceImpl.RecuperarRqnp");
	}
}


	/**
     * Lista de Solitud por Usuario
     * @param Map<String, Object>
     * @return Collection - Lista de Solicitudes
     */  
	@Override
	public Collection listarConsultaCatalogo(Map<String, Object> params) {
		if (log.isDebugEnabled()) log.debug("Inicio - SolicitudBienServiceImpl.listarConsultaCatalogo");
		
		List<Map<String, Object>> lstSol = new ArrayList<Map<String,Object>>();
		Map<String, Object> map_soli;
		char ini=' ' ;
		char ini2= "'".charAt(0) ;
		char salto =(char)10;
		char retorno =(char)13;
		char espacio =(char)32;
		
		CharSequence comi ="&quot";
		CharSequence comis ="\"";
		try {
			List<ConsultaCatalogoBean> lista = (List<ConsultaCatalogoBean>) solicitudBienDao.listarConsultaCatalogo(params);
			
			for(ConsultaCatalogoBean obj: lista){
				map_soli = new HashMap<String, Object>();

				map_soli.put("tipo", obj.getTipo());
				map_soli.put("codigo_item", obj.getCodigo_item());
				map_soli.put("descripcion_item", obj.getDescripcion_item());
				map_soli.put("unidad_medida", obj.getUnidad_medida());
				map_soli.put("precio_unitario", obj.getPrecio_unitario());
				map_soli.put("clasificador_gasto", obj.getClasificador_gasto());
				map_soli.put("descripcion_clasificador", obj.getDescripcion_clasificador());
				map_soli.put("auc", obj.getAuc());
				map_soli.put("ambito", obj.getAmbito());
				map_soli.put("ruta_programado", obj.getRuta_programado());
				map_soli.put("ruta_no_programado", obj.getRuta_no_programado());
				map_soli.put("rubro_general", obj.getRubro_general());
				map_soli.put("rubro_especifico", obj.getRubro_especifico());
				map_soli.put("estado", obj.getEstado());
				map_soli.put("alerta", obj.getAlerta());
				map_soli.put("grupo_catalogo", obj.getGrupo_catalogo());
				map_soli.put("grupo_descripcion", obj.getGrupo_descripcion());
				map_soli.put("clase_catalogo", obj.getClase_catalogo());
				map_soli.put("clase_descripcion", obj.getClase_descripcion());
				map_soli.put("familia_catalogo", obj.getFamilia_catalogo());
				map_soli.put("familia_descripcion", obj.getFamilia_descripcion());
				
				lstSol.add(map_soli);
			}
			return lstSol;
		} 
		catch (Exception ex) {
			   log.error("Error en SolicitudBienServiceImpl.listarConsultaCatalogo: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
		}
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - SolicitudBienServiceImpl.listarConsultaCatalogo");
		}
	}	
	
	
	////////////////////////////////////////////////
	/**
     * Recupera la dependencia 
     * @param String num_uuoo - Numero de la UUOO 
     * @return RequerimientoNoProgramadoBean - 
     */  
	@Override
	public String recuperarDependenciaPorUUOO(String num_uuoo) {
		String cod_dep="";
		try{	 
			 if (num_uuoo != null ){
				 
				 DependenciaBean dependencia= dependenciaDao.obtenerDependenciaXuuoo(num_uuoo);
				 if (dependencia != null){
					 cod_dep = dependencia.getCod_dep();
				 
				 }else{
					 cod_dep="";
				 }
				
			 }else{
				 cod_dep="";
			 }
		return cod_dep;
		
	}catch (Exception ex) {
		   log.error("Error en SolicitudBienServiceImpl.recuperarDependenciaPorUUOO: " + ex.getMessage(), ex);
		   throw new ServiceException(this, ex);
		} 
	finally {
		if (log.isDebugEnabled()) log.debug("Fin - SolicitudBienServiceImpl.recuperarDependenciaPorUUOO");
	}
	
	}
	
	
	/**
     * Recupera la dependencia 
     * @param String num_uuoo - Numero de la UUOO 
     * @return RequerimientoNoProgramadoBean - 
     */  
	@Override
	public String activarFlujoAuc(String parametro) {
		String rpta="";
		try{	 
			 if (parametro != null ){
				 
				 rpta= sysParametrosDao.activarFlujoAuc();
			 }else{
				 rpta="";
			 }
			 return rpta;
		
		}catch (Exception ex) {
			   log.error("Error en SolicitudBienServiceImpl.activarFlujoAuc: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
			} 
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - SolicitudBienServiceImpl.activarFlujoAuc");
		}
	
	}
	
	
	//getter and setter
	public SolicitudBienDao getSolicitudBienDao() {
		return solicitudBienDao;
	}

	public void setSolicitudBienDao(SolicitudBienDao solicitudBienDao) {
		this.solicitudBienDao = solicitudBienDao;
	}

	public UnidadMedidaDao getUnidadMedidaDao() {
		return unidadMedidaDao;
	}

	public void setUnidadMedidaDao(UnidadMedidaDao unidadMedidaDao) {
		this.unidadMedidaDao = unidadMedidaDao;
	}

	public AccesoDAO getAccesoDao() {
		return accesoDao;
	}

	public void setAccesoDao(AccesoDAO accesoDao) {
		this.accesoDao = accesoDao;
	}

	public T01ParametroDAO getT01ParametroDao() {
		return t01ParametroDao;
	}

	public void setT01ParametroDao(T01ParametroDAO t01ParametroDao) {
		this.t01ParametroDao = t01ParametroDao;
	}

	public DependenciaDAO getDependenciaDao() {
		return dependenciaDao;
	}

	public void setDependenciaDao(DependenciaDAO dependenciaDao) {
		this.dependenciaDao = dependenciaDao;
	}

	public MaestroPersonalDAO getMaestroPersonalDao() {
		return maestroPersonalDao;
	}

	public void setMaestroPersonalDao(MaestroPersonalDAO maestroPersonalDao) {
		this.maestroPersonalDao = maestroPersonalDao;
	}

	public SysEstadosDAO getSysEstadosDao() {
		return sysEstadosDao;
	}

	public void setSysEstadosDao(SysEstadosDAO sysEstadosDao) {
		this.sysEstadosDao = sysEstadosDao;
	}

	public SysParametrosDAO getSysParametrosDao() {
		return sysParametrosDao;
	}

	public void setSysParametrosDao(SysParametrosDAO sysParametrosDao) {
		this.sysParametrosDao = sysParametrosDao;
	}
	

}
