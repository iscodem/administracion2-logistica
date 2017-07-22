package pe.gob.sunat.administracion2.siga.rqnp.service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

import javax.xml.rpc.ServiceException;

import org.springframework.dao.DataAccessException;

import pe.gob.sunat.administracion2.siga.rqnp.model.domain.RequerimientoNoProgBienesBean;
import pe.gob.sunat.administracion2.siga.rqnp.model.domain.RequerimientoNoProgramadoBean;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.DependenciaBean;

public interface RequerimientoNoProgramadoService {
	
	public Collection listarRqnpUsuario(String ano_pro, String est_req , String cod_sed, String cod_res ,  String mes_pro,String ind_registrado);
	public Collection listarRqnpJefe(String ano_pro, String est_req , String cod_sed, String mes_pro, String cod_per);
	public Collection listarRqnpIntendente(String ano_pro, String est_req , String cod_sed,  String mes_pro, String cod_per);
	public Collection listarRqnpMetas(Map<String, Object> params);
	public Collection listarRqnpMetasVista(Map<String, Object> params);
	public String registrarCabRqnp(Map<String, Object> params);
	public String updateCabRqnp(Map<String, Object> params);
	public String registrarDetalleRqnp(Map<String, Object> params) throws ServiceException;//REGISTRA BIENES/METAS DE RQNP (DETALLE)
	public String registrarMetasRqnp(Map<String, Object> params);
	public String registrarMetasRqnpAUC(Map<String, Object> params);//SE MODIFICO PARA REGISTRAR LAS METAS DE AUC
	public String registrarDetalleEntregaBien(Map<String, Object> params);
	public BigDecimal registrarCabMonto(Map<String, Object> params);
	public void registrarCabObjeto(Map<String, Object> params) ;
	public void deleteBienRnp(Map<String, Object> params);
	public RequerimientoNoProgramadoBean recuperarRqnp(String cod_req);
	public RequerimientoNoProgramadoBean recuperarRqnpAUC(String cod_req);
	public RequerimientoNoProgramadoBean recuperarRqnpCab(String cod_req);
	public RequerimientoNoProgramadoBean recuperarRqnpCabAUC(String cod_req);
	public Collection listarCatalogo(Map<String, Object> params);
	public Collection listarCatalogoFamilia(Map<String, Object> params);
	public String registrarAnularRqnp(Map<String, Object> params) ;
	public String registrarEnviarRqnp(Map<String, Object> params) throws ServiceException;
	public String registrarAprobarRqnp(Map<String, Object> params) throws ServiceException;
	public String registrarAprobarUBG(Map<String, Object> params) ;
	public String registrarRechazarRqnp(Map<String, Object> params) ;
	public Collection recuperarBienesJefeInmediato(Map<String, Object> params);
	public RequerimientoNoProgBienesBean recuperarDatosBienModifica(String cod_req ,String cod_bien) ;
	public Collection recuperarBienesJefeUBG(Map<String, Object> params);
	public Collection recuperarBienesJefeOEC(Map<String, Object> params);
	public Collection recuperarBienesIntedente(Map<String, Object> params);
	public void updateFile(String cod_rqnp,String cod_bien, String num_reg, String user);
	public void envioMailUct(String cod_rqnp, String cod_bien);
	public void envioMailDerivar(String cod_rqnp, String cod_bien);
	public void envioMailRechazo(String cod_rqnp, String cod_bien, String cod_bandeja);
	public boolean enviadoUCT(String cod_req,String cod_bien) ;
	public String validaMetasBienes(String cod_req);
	public Collection listarSysEstados(Map<String, Object> params);
	public String registrarActualizaEstadoItem(Map<String, Object> params);
	public String EnviarMailRqnp(Map<String, Object> params) ;
	public RequerimientoNoProgBienesBean recuperarDatosEntregaBien(String cod_req ,String cod_bien) ;
	public Collection listaFinalidad();
	public Collection listarAccionesBien(String num_expediente);
	public Collection listarMeses();
	public Collection listaNecesidad();
	public Collection listaTipoNecesidad();
	
//INICIO: DPORRASC
	
	public String registrarCabRqnpAUC(Map<String, Object> params);
	public String registrarCabRqnpAUCModi(Map<String, Object> params);
	public Collection listarRqnpMetasBien(Map<String, Object> params);
	public String registrarDetalleRqnpAUC(Map<String, Object> params);
	public String registrarMetasRqnpPopup(Map<String, Object> params);
	public Collection listarProyProdAccion(Map<String, Object> params);
	public String validaMetasBienesAUC(String cod_req);
	public String obtenerTipoAUC(String cod_dep) ; //OBTIENE EL TIPO DE AUC (TÉCNICA O DESCONCENTRADA)
	public String obtenerUIT(String anio_ejec) ; 
	public BigDecimal calculaLimiteCompraDirecta(String anio_ejec) ; 
	public void deleteMetaRnp(Map<String, Object> params);
	public String registrarCabEntregaRqnpAUC(Map<String, Object> params);
	public Map<String, Object> spReplicarMetas( Map<String, Object> params)throws ServiceException;
	public String registrarEnviarRqnpAuAuc(Map<String, Object> params) ; // RUTA 04
	////////////
	public boolean esPerfilDependenciaAprobador(String codDependencia ) ;
	public boolean esPerfilDependenciaAUC(String codDependencia ) ;
	public abstract DependenciaBean obtenerUuooRQNP(Map<String, Object> params) throws ServiceException;
	public abstract DependenciaBean obtenerUuooOEC(Map<String, Object> params) throws ServiceException;
	public abstract DependenciaBean obtenerUuooAUC(Map<String, Object> params) throws ServiceException;
	public abstract DependenciaBean obtenerUuooAprobadoraAuc(Map<String, Object> params) throws ServiceException;
	public abstract DependenciaBean obtenerUuoo(Map<String, Object> params) throws ServiceException; 
	public abstract boolean validaMetaVacia(Map<String, Object> params) throws ServiceException;
	public abstract String validaExisteMetaJson(String paramString1, String paramString2);
	public abstract DependenciaBean obtenerDpg(String codParametro, String descParametro) throws ServiceException;
	
	public Map<String, Object> obtenerSecuFuncUuoo(Map<String, Object> params) ; // RUTA 04
	
	public abstract Collection listarTipoVinculo();
	public abstract Collection listarVinculo(String cod_parm) throws ServiceException;
	
	public BigDecimal getSaldoItem(Map<String, Object> params) throws ServiceException;
	
	public String registrarRqnpDetalle(RequerimientoNoProgramadoBean rqnpCabBean, RequerimientoNoProgBienesBean rqnpBienBean ) throws ServiceException;//REGISTRA BIENES/METAS DE RQNP (DETALLE)
}
