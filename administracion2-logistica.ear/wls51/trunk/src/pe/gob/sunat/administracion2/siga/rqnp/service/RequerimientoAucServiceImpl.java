package pe.gob.sunat.administracion2.siga.rqnp.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jndi.JndiTemplate;

import pe.gob.sunat.administracion2.siga.registro.model.domain.RegistroArchivosFisicoBean;
import pe.gob.sunat.administracion2.siga.rqnp.model.dao.AucBandejaDao;
import pe.gob.sunat.administracion2.siga.rqnp.model.dao.AucMetasDao;
import pe.gob.sunat.administracion2.siga.rqnp.model.dao.CatalogoBienesDao;
import pe.gob.sunat.administracion2.siga.rqnp.model.dao.RequerimientoNoProgBienesDao;
import pe.gob.sunat.administracion2.siga.rqnp.model.dao.RequerimientoNoProgMetasDao;
import pe.gob.sunat.administracion2.siga.rqnp.model.dao.RequerimientoNoProgramadoDao;
import pe.gob.sunat.administracion2.siga.rqnp.model.dao.RqnpBandejaDao;
import pe.gob.sunat.administracion2.siga.rqnp.model.domain.AucAnexoBean;
import pe.gob.sunat.administracion2.siga.rqnp.model.domain.AucBandejaBean;
import pe.gob.sunat.administracion2.siga.rqnp.model.domain.CatalogoBienesBean;
import pe.gob.sunat.administracion2.siga.rqnp.model.domain.RequerimientoNoProgBienesBean;
import pe.gob.sunat.administracion2.siga.rqnp.model.domain.RequerimientoNoProgMetasBean;
import pe.gob.sunat.administracion2.siga.rqnp.model.domain.RequerimientoNoProgramadoBean;
import pe.gob.sunat.administracion2.siga.registro.model.dao.*;
import pe.gob.sunat.framework.spring.util.exception.ServiceException;
import pe.gob.sunat.recurso2.administracion.siga.parametro.model.bean.PrgAnnoBean;
import pe.gob.sunat.recurso2.administracion.siga.parametro.model.bean.T01ParametroBean;
import pe.gob.sunat.recurso2.administracion.siga.parametro.model.dao.PrgAnnoDAO;
import pe.gob.sunat.recurso2.administracion.siga.parametro.model.dao.SysParametrosDAO;
import pe.gob.sunat.recurso2.administracion.siga.parametro.model.dao.T01ParametroDAO;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.DependenciaBean;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.MaestroContratistasBean;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.MaestroPersonalBean;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.SegRolesUsuariosBean;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.dao.AccesoDAO;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.dao.DependenciaDAO;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.dao.MaestroPersonalDAO;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.dao.SegRolesUsuariosDAO;
import pe.gob.sunat.recurso2.administracion.siga.registro.service.RegistroPersonalService;

public class RequerimientoAucServiceImpl implements RequerimientoAucService{

	private static final Log log = LogFactory.getLog(RequerimientoAucServiceImpl.class);
	
	AucBandejaDao aucBandejaDao ;
	RequerimientoNoProgramadoDao 	requerimientoNoProgramadoDao;
	RegistroPersonalService			registroPersonalService;
	DependenciaDAO 					dependenciaDao;
	AccesoDAO						accesoDao;
	RequerimientoNoProgBienesDao 	requerimientoNoProgBienesDao;	
	RequerimientoNoProgMetasDao		requerimientoNoProgMetasDao;
	
	CatalogoBienesDao				catalogoBienesDao;
	MaestroPersonalDAO				maestroPersonalDao;
	RqnpBandejaDao					rqnpBandejaDao;
	SysParametrosDAO				sysParametrosDao;
	SegRolesUsuariosDAO				segRolesUsuariosDao;
	PrgAnnoDAO						prgAnnoDao;
	T01ParametroDAO					t01ParametroDao;
	//INICIO: DPORRASC
	AucMetasDao 					aucMetasDao;
	/**
     * Lista los Requerimientos No Programados Para Atender po la AUC
     * @param Map params - Parametros(cod_per,anio_ant,cod_estado)
     * @return Collection - Lista de Requerimientos
     */  
	
	@Override
	public Collection listarRqnpAuc(Map<String, Object> params) {
		if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoAucServiceImpl.listarRqnpAuc");
		List<Map<String, Object>> lsRqnpAuc = new ArrayList<Map<String,Object>>();
		
		char salto =(char)10;
		char retorno =(char)13;
		char espacio =(char)32;
		
		CharSequence comi ="&quot";
		CharSequence comis ="\"";
		CharSequence comSimple="'";
		CharSequence comSimpleSalva="&#39";
		
		CharSequence comaSimple=",";
		CharSequence comaSimpleSalva=";";
		List<AucBandejaBean> lista = new ArrayList<AucBandejaBean>() ;
		Map<String, Object> aucBandejaBean;
		String cod_estado ="";
		
		try {
			cod_estado =(String) params.get("cod_estado");
			if (cod_estado.equals("02")){
				 lista = (List<AucBandejaBean>) aucBandejaDao.listarRqnpAuc(params);	
			}else{
				 lista = (List<AucBandejaBean>) aucBandejaDao.listarRqnpAucAll(params);
			}
			
			for(AucBandejaBean data: lista){
				aucBandejaBean = new HashMap<String, Object>();

				aucBandejaBean.put("num_rqnp", data.getNum_rqnp());
				aucBandejaBean.put("fec_rqnp",new SimpleDateFormat("dd/MM/yyyy HH:mm").format(data.getFec_rqnp()));
				//aucBandejaBean.put("fec_rqnp", data.getFec_rqnp().getFormatDate("dd/MM/yyyy"));
				aucBandejaBean.put("solicitante", data.getSolicitante().replace(comis, comi).replace(comaSimple, comaSimpleSalva).replace(comSimple, comSimpleSalva).replace(salto, espacio).replace(retorno, espacio));
				aucBandejaBean.put("uuoo_solicitante", data.getUuoo_solicitante());
				aucBandejaBean.put("motivo_sol", data.getMotivo_sol().replace(comis, comi).replace(comaSimple, comaSimpleSalva).replace(comSimple, comSimpleSalva).replace(salto, espacio).replace(retorno, espacio) );
				aucBandejaBean.put("cod_resp", data.getCod_resp());
				aucBandejaBean.put("cod_dep", data.getCod_dep());
				aucBandejaBean.put("cod_rqnp", data.getCod_rqnp());
				aucBandejaBean.put("monto_total", data.getMonto_total().setScale(2, RoundingMode.HALF_UP).toString());
				aucBandejaBean.put("num_auc", data.getNum_auc());
				aucBandejaBean.put("num_item", data.getNum_item());
				aucBandejaBean.put("atender", data.getCod_rqnp());
				aucBandejaBean.put("seguimiento", data.getCod_rqnp());
				aucBandejaBean.put("formato", data.getCod_rqnp());
				aucBandejaBean.put("anio_atencion", data.getAnio_atencion());
				aucBandejaBean.put("mes_atencion", data.getMes_atencion());
				
				//requerimientoNoProgramado.put("motivoSolicitud", data.getMotivoSolicitud().replace(comis, comi).replace(comaSimple, comaSimpleSalva).replace(comSimple, comSimpleSalva).replace(salto, espacio).replace(retorno, espacio) );
				
				lsRqnpAuc.add(aucBandejaBean);
			}
			return lsRqnpAuc;
		} 
		catch (Exception ex) {
			log.error("Error en RequerimientoAucServiceImpl.listarRqnpAuc: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);
		}
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoAucServiceImpl.listarRqnpAuc");
		}
	}
	
	@Override
	public RegistroArchivosFisicoBean recuperarArchivoAnexo(Map<String, Object> params) {
		if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoAucServiceImpl.recuperarArchivoAnexo");
		
		Map<String, Object> map_operacion = new HashMap<String, Object>() ;
		List<Map<String, Object>> lst_item = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> lst_metas = new ArrayList<Map<String,Object>>();
		String fileName="";
		char salto =(char)10;
		char retorno =(char)13;
		char espacio =(char)32;
		String ls_tipo_vinculo;
		String ls_vinculo;
		
		String ind_vinculo;
		String ind_prestamo;
		
		CharSequence comi ="&quot";
		CharSequence comis ="\"";
		CharSequence comSimple="'";
		CharSequence comSimpleSalva="&#39";
		
		CharSequence comaSimple=",";
		CharSequence comaSimpleSalva=";";
		List<AucAnexoBean> lista = new ArrayList<AucAnexoBean>() ;
		List<AucAnexoBean> listaMetas = new ArrayList<AucAnexoBean>() ;
		Map<String, Object> anexoBean;
		Map<String, Object> metaBean;
		String cod_objeto="";
		
		String ls_cod_proyecto="";
		String ls_cod_cambio="";
		String ls_cod_inversion="";
		String ls_des_proyecto="";
		String ls_des_cambio="";
		String ls_des_inversion="";
		
		String des_meta="";
		String cod_finaliadad="";
		
		//INICIO - AGREGADO PARA EL PASE
		MaestroPersonalBean miembroComiteAuTit=new MaestroPersonalBean();
		MaestroPersonalBean miembroComiteAuSup=new MaestroPersonalBean();
		MaestroPersonalBean miembroComiteTecTit=new MaestroPersonalBean();
		MaestroPersonalBean miembroComiteTecSup=new MaestroPersonalBean();
		//FIN - AGREGADO PARA EL PASE
		
		RegistroArchivosFisicoBean fBean = new RegistroArchivosFisicoBean();
		try {
			
			lista = (List<AucAnexoBean>) aucBandejaDao.listarRqnpAnexo(params);	
			BigDecimal suma = new BigDecimal(0);
			
			NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH) ;
			nf.setMinimumFractionDigits(2);
			for(AucAnexoBean data: lista){
				anexoBean = new HashMap<String, Object>();
				metaBean= new HashMap<String, Object>();
				des_meta = data.getDes_meta();
				cod_finaliadad =data.getCod_finalidad();
				if (data.getCod_objeto() !=null){
					cod_objeto=data.getCod_objeto().trim();
				}else{
					cod_objeto="";
				}
				
				if (cod_objeto.equals("1")){
					cod_objeto="Bienes";
				}else if(cod_objeto.equals("2")){
					cod_objeto="Servicio";
				}else if(cod_objeto.equals("3")){
					cod_objeto="Obras";
				}else{
					cod_objeto="SIN";
				}
				
				if (des_meta ==null){ des_meta="";}
				map_operacion.put("num_rqnp", data.getNum_rqnp());
				map_operacion.put("fec_rqnp", data.getFec_rqnp());
				map_operacion.put("nom_contacto", data.getNom_contacto().replace(comis, comi).replace(comaSimple, comaSimpleSalva).replace(comSimple, comSimpleSalva).replace(salto, espacio).replace(retorno, espacio));
				map_operacion.put("uuoo_des", data.getUuoo_des());
				map_operacion.put("anexo_contacto", data.getAnexo_contacto());
				map_operacion.put("des_proceso", data.getDes_proceso());
				map_operacion.put("des_ubg", data.getDes_ubg());
				map_operacion.put("cod_objeto", cod_objeto);
				map_operacion.put("des_meta",des_meta );
				map_operacion.put("cod_finalidad", data.getCod_finalidad());
				map_operacion.put("dur_necesidad", data.getDur_necesidad());
				map_operacion.put("des_motivo", data.getDes_motivo().replace(comis, comi).replace(comaSimple, comaSimpleSalva).replace(comSimple, comSimpleSalva).replace(salto, espacio).replace(retorno, espacio) );
				map_operacion.put("tip_necesidad", data.getTip_necesidad());
				map_operacion.put("des_auc", data.getDes_auc());
				map_operacion.put("des_c1", data.getDes_c1());
				map_operacion.put("des_c2", data.getDes_c2());
				map_operacion.put("des_c3", data.getDes_c3());
				map_operacion.put("anio_atencion", data.getAnio_atencion());
				map_operacion.put("mes_atencion", data.getMes_atencion());
				
				map_operacion.put("SUBREPORT_DIR", "/data0/generador/jasper/plantillas/");
				
				ls_tipo_vinculo = data.getTipo_vinculo();
				ind_vinculo=data.getInd_vinculo();
				ind_prestamo=data.getInd_prestamo();
				
				ls_vinculo=data.getVinculo();
				
				map_operacion.put("tipo_vinculo", ls_tipo_vinculo);
				map_operacion.put("vinculo", ls_vinculo);
				
				map_operacion.put("ind_vinculo", data.getInd_vinculo()==null?"":data.getInd_vinculo());
				map_operacion.put("ind_prestamo", data.getInd_prestamo()==null?"":data.getInd_prestamo());
				
				//AGREGADO PARA EL COMITE
				map_operacion.put("ind_comite", data.getInd_comite()==null?"":data.getInd_comite());
				map_operacion.put("ind_tec_tit", data.getInd_tec_tit()==null?"":data.getInd_tec_tit());
				map_operacion.put("ind_tec_sup", data.getInd_tec_sup()==null?"":data.getInd_tec_sup());
				map_operacion.put("cod_au_tit", data.getCod_au_tit()==null?"":data.getCod_au_tit());
				map_operacion.put("cod_au_sup", data.getCod_au_sup()==null?"":data.getCod_au_sup());
				map_operacion.put("cod_tec_tit", data.getCod_tec_tit()==null?"":data.getCod_tec_tit());
				map_operacion.put("cod_tec_sup", data.getCod_tec_sup()==null?"":data.getCod_tec_sup());
				map_operacion.put("nom_tec_tit", data.getNom_tec_tit()==null?"":data.getNom_tec_tit());
				map_operacion.put("nom_tec_sup", data.getNom_tec_sup()==null?"":data.getNom_tec_sup());
				
				//RECUPERANDO MIEMBROS DEL COMITE
				miembroComiteAuTit=registroPersonalService.obtenerPersonaxCodigo(data.getCod_au_tit()==null?"":data.getCod_au_tit());
				miembroComiteAuSup=registroPersonalService.obtenerPersonaxCodigo(data.getCod_au_sup()==null?"":data.getCod_au_sup());
				
				if(miembroComiteAuTit!=null){
					map_operacion.put("nombre_au_tit", miembroComiteAuTit.getNombre_completo()==null?"":miembroComiteAuTit.getNombre_completo());
					map_operacion.put("reg_au_tit", miembroComiteAuTit.getNumero_registro()==null?"":miembroComiteAuTit.getNumero_registro());
				}else{
					map_operacion.put("nombre_au_tit", "");
					map_operacion.put("reg_au_tit", "");
				}
				
				if(miembroComiteAuSup!=null){
					map_operacion.put("nombre_au_sup", miembroComiteAuSup.getNombre_completo()==null?"":miembroComiteAuSup.getNombre_completo());
					map_operacion.put("reg_au_sup", miembroComiteAuSup.getNumero_registro()==null?"":miembroComiteAuSup.getNumero_registro());
				}else{
					map_operacion.put("nombre_au_sup", "");
					map_operacion.put("reg_au_sup", "");
				}
				
				if(data.getCod_tec_tit()!=null ){
					miembroComiteTecTit=registroPersonalService.obtenerPersonaxCodigo(data.getCod_tec_tit().trim());
					map_operacion.put("nombre_tec_tit", miembroComiteTecTit.getNombre_completo());
					map_operacion.put("reg_tec_tit", miembroComiteTecTit.getNumero_registro());
				}else if (data.getNom_tec_tit()!=null){
					map_operacion.put("nombre_tec_tit", data.getNom_tec_tit());
					map_operacion.put("reg_tec_tit", "");
				}else{
					map_operacion.put("nombre_tec_tit", "");
					map_operacion.put("reg_tec_tit", ""); 
					}
				
				if(data.getCod_tec_sup()!=null){
					miembroComiteTecSup=registroPersonalService.obtenerPersonaxCodigo(data.getCod_tec_sup().trim());
					map_operacion.put("nombre_tec_sup", miembroComiteTecSup.getNombre_completo());
					map_operacion.put("reg_tec_sup", miembroComiteTecSup.getNumero_registro());
				}else if (data.getNom_tec_sup()!=null){
					map_operacion.put("nombre_tec_sup", data.getNom_tec_sup());
					map_operacion.put("reg_tec_sup", "");
				}else{
					map_operacion.put("nombre_tec_sup", "");
					map_operacion.put("reg_tec_sup", ""); 
					}

				//////////////////////////////////////////////////
				Map<String, Object> param = new HashMap<String, Object>();
				List<T01ParametroBean>listaParametro = new ArrayList<T01ParametroBean>();
				
				log.debug("tipo_vinculo" + ls_tipo_vinculo);
				log.debug("vinculo" + ls_vinculo);
				
				if (ls_tipo_vinculo != null){
					if( ls_vinculo !=null){
						map_operacion.put("cod_cambio","");
						map_operacion.put("des_cambio", "");
						map_operacion.put("cod_proyecto", "");
						map_operacion.put("des_proyecto", "");
						map_operacion.put("cod_inversion", "");
						map_operacion.put("des_inversion", "");
						
						if (ls_tipo_vinculo.equals("01")){
							param.put("cod_par", "4032");
							param.put("cod_tipo","D");
							param.put("COD_ARGUMENTO",ls_vinculo.trim());
							listaParametro =( List<T01ParametroBean> ) t01ParametroDao.recuperarParametroLista(param);
							//T01ParametroBean bean = listaParametro.get(0);
							//map_operacion.put("cod_cambio", bean.getCod_argumento());
							//map_operacion.put("des_cambio", bean.getNom_largo());
							//DPORRASC - AGREGADO PARA EL PASE
							for(int i=0; i< listaParametro.size(); i++) {
					            if(ls_vinculo.trim().equals(listaParametro.get(i).getCod_argumento().trim())){
					            	T01ParametroBean bean = listaParametro.get(i);
					            	map_operacion.put("cod_cambio", listaParametro.get(i).getCod_argumento());
									map_operacion.put("des_cambio", listaParametro.get(i).getNom_largo());
					            }
					        }
						}
						
						if (ls_tipo_vinculo.equals("02")){
							param.put("cod_par", "4033");
							param.put("cod_tipo","D");
							param.put("COD_ARGUMENTO",ls_vinculo.trim());
							listaParametro =( List<T01ParametroBean> ) t01ParametroDao.recuperarParametroLista(param);
							//T01ParametroBean bean = listaParametro.get(0);
							//DPORRASC - AGREGADO PARA EL PASE
							for(int i=0; i< listaParametro.size(); i++) {
					            if(ls_vinculo.trim().equals(listaParametro.get(i).getCod_argumento().trim())){
					            	T01ParametroBean bean = listaParametro.get(i);
					            	map_operacion.put("cod_proyecto", listaParametro.get(i).getCod_argumento());
									map_operacion.put("des_proyecto", listaParametro.get(i).getNom_largo());
					            }
					        }
							
						}
						
						if (ls_tipo_vinculo.equals("03")){
							param.put("cod_par", "4034");
							param.put("cod_tipo","D");
							param.put("COD_ARGUMENTO",ls_vinculo.trim());
							listaParametro =( List<T01ParametroBean> ) t01ParametroDao.recuperarParametroLista(param);
							//T01ParametroBean bean = listaParametro.get(0);
							//map_operacion.put("cod_inversion", bean.getCod_argumento());
							//map_operacion.put("des_inversion", bean.getNom_largo());
							//DPORRASC - AGREGADO PARA EL PASE
							for(int i=0; i< listaParametro.size(); i++) {
					            if(ls_vinculo.trim().equals(listaParametro.get(i).getCod_argumento().trim())){
					            	T01ParametroBean bean = listaParametro.get(i);
					            	map_operacion.put("cod_inversion", listaParametro.get(i).getCod_argumento());
									map_operacion.put("des_inversion", listaParametro.get(i).getNom_largo());
					            }
					        }
						}
						
						if (ls_tipo_vinculo.equals("04")){
							param.put("cod_par", "4040");
							param.put("cod_tipo","D");
							param.put("COD_ARGUMENTO",ls_vinculo.trim());
							//listaParametro =( List<T01ParametroBean> ) t01ParametroDao.recuperarParametroLista(param);
							//T01ParametroBean bean = listaParametro.get(0);
							map_operacion.put("cod_inversion", "");
							map_operacion.put("des_inversion", "");
							//DPORRASC - AGREGADO PARA EL PASE
						}
					}else{
						map_operacion.put("cod_cambio","");
						map_operacion.put("des_cambio", "");
						map_operacion.put("cod_proyecto", "");
						map_operacion.put("des_proyecto", "");
						map_operacion.put("cod_inversion", "");
						map_operacion.put("des_inversion", "");
					}
				}else{
					map_operacion.put("cod_cambio","");
					map_operacion.put("des_cambio", "");
					map_operacion.put("cod_proyecto", "");
					map_operacion.put("des_proyecto", "");
					map_operacion.put("cod_inversion", "");
					map_operacion.put("des_inversion", "");
				}
				//--DETALLE-------------------------------------------------------------
				anexoBean.put("cod_bien", data.getCod_bien());
				anexoBean.put("des_bien", data.getDes_bien());
				anexoBean.put("monto_bien",nf.format( data.getMonto_bien().setScale(2, RoundingMode.HALF_UP)));
				
				anexoBean.put("cantidad_bien",nf.format( data.getCantidad_bien().setScale(2, RoundingMode.HALF_UP) ));
				anexoBean.put("unidad_bien", data.getUnidad_bien());
				fileName= data.getNum_rqnp();
				suma=suma.add(data.getMonto_bien().setScale(2, RoundingMode.HALF_UP));
				lst_item.add(anexoBean);
				
			}
			listaMetas = (List<AucAnexoBean>) aucBandejaDao.listarRqnpAnexoMetas(params);
			for(AucAnexoBean data: listaMetas){
				metaBean = new HashMap<String, Object>();
				//metaBean.put("ubg_det", data.getUbg_det());
				//metaBean.put("monto_ubg", nf.format(data.getMonto_ubg() .setScale(2, RoundingMode.HALF_UP) ));
				if(listaMetas.size()>1){
					metaBean.put("ubg_det", data.getUbg_det()+" entre otras.");
				}else{
					metaBean.put("ubg_det", data.getUbg_det());
				}
				
				metaBean.put("monto_ubg", nf.format(suma.setScale(2, RoundingMode.HALF_UP) ));
				log.debug("ubg_det"+ data.getUbg_det());
				log.debug("monto_ubg "+ nf.format(suma.setScale(2, RoundingMode.HALF_UP) ));
				lst_metas.add(metaBean);
				break;
			}
		
			map_operacion.put("metas", lst_metas);
			
			map_operacion.put("valor_estimado", nf.format(suma.setScale(2, RoundingMode.HALF_UP)));
			
			log.debug("map_operacion: "+map_operacion.toString());
			log.debug("lst_item: "+lst_item.toString());
			log.debug("lst_metas: "+lst_metas.toString());
			log.debug("map " + map_operacion.size() + " - list_item " + lst_item.size() + " file_name " + fileName);
			fBean=invicarLocalServicePDF(map_operacion, lst_item, fileName);
			
			return fBean;
		} 
		catch (Exception ex) {
			   log.error("Error en RequerimientoAucServiceImpl.listarRqnpAnexo: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
		}
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoAucServiceImpl.listarRqnpAnexo");
		}
		
	}
	
	/**
     * Recupera la Cabecera de un  Requerimientos No Programado 
     * @param String cod_req - Código de Requerimeinto no Programado 
     * @return RequerimientoNoProgramadoBean - Requerimiento no Programado
     */  
	@Override
	public Map<String, Object> recuperarRqnpCab(Map<String, Object> params) {
		Map<String, Object> mapRqnp = new HashMap<String, Object>();
		String cod_req ="";
		String cod_dep_user="";
		String ls_cod_contacto="";
		String ls_reg_contacto="";
		String ls_nom_contacto="";
		String ls_cod_proveedor="";
		String ls_cod_uoc1 ="";
		String ls_num_uoc1 ="";
		String ls_cod_uoc2="";
		String ls_num_uoc2 ="";
		String ls_cod_uoc3="";
		String ls_num_uoc3 ="";
		String ls_nom_uoc1 ="";
		String ls_nom_uoc2="";
		String ls_nom_uoc3="";
		
		MaestroPersonalBean miembroComiteAuTit=new MaestroPersonalBean();
		MaestroPersonalBean miembroComiteAuSup=new MaestroPersonalBean();
		MaestroPersonalBean miembroComiteTecTit=new MaestroPersonalBean();
		MaestroPersonalBean miembroComiteTecSup=new MaestroPersonalBean();
		 
		cod_req = (String)params.get("cod_req");
		cod_dep_user = (String)params.get("cod_dep_user");
		if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoAucServiceImpl.recuperarRqnpCab");
		try{
			RequerimientoNoProgramadoBean rqnp= requerimientoNoProgramadoDao.recuperarRqnp(cod_req);
			String cod_objeto =requerimientoNoProgramadoDao.getTipoObjetoRqnp(cod_req);
			//Seteando Cabecera
			mapRqnp.put("codigoRqnp", rqnp.getCodigoRqnp());
			mapRqnp.put("codigoReqNoProgramado", rqnp.getCodigoReqNoProgramado());
			mapRqnp.put("codigoResposanble", rqnp.getNumeroRegistro());
			mapRqnp.put("resposanble", rqnp.getNombreSolicitante());
			mapRqnp.put("codigoUuoo", rqnp.getUuoo());
			mapRqnp.put("codigoDependencia", rqnp.getCodigoDependencia());
			mapRqnp.put("anioProceso", rqnp.getAnioProceso());
			ls_cod_contacto = rqnp.getCod_contacto();
			mapRqnp.put("cod_contacto", ls_cod_contacto);
			if (  ls_cod_contacto != null){
				MaestroPersonalBean contacto = registroPersonalService.obtenerPersonaxCodigo(rqnp.getCod_contacto());
				if (contacto !=null){
					ls_nom_contacto =contacto.getNombre_completo() ;	 
					ls_reg_contacto = contacto.getNumero_registro();
				}
			}
			mapRqnp.put("nom_contacto", ls_nom_contacto);
			mapRqnp.put("reg_contacto", ls_reg_contacto);
			 
			mapRqnp.put("anexo_contacto", rqnp.getAnexo_contacto());
			ls_cod_proveedor=rqnp.getCod_proveedor();
			if (ls_cod_proveedor !=null){
				Map<String,Object> mapa =new HashMap<String,Object>() ;
				mapa.put("cod_cont",ls_cod_proveedor );
				MaestroContratistasBean contratista= registroPersonalService.recuperarContratista(mapa);
				if(contratista!=null){
					mapRqnp.put("cod_cont", ls_cod_proveedor );
					mapRqnp.put("num_ruc", contratista.getNum_ruc() );
					mapRqnp.put("raz_social", contratista.getRaz_social() ); 
				}
			}
			mapRqnp.put("obs_justificacion", rqnp.getObs_justificacion() );			 
			mapRqnp.put("obs_dir_entrega", rqnp.getObs_dir_entrega() );
			mapRqnp.put("obs_lugar_entrega", rqnp.getObs_lugar_entrega() );
			mapRqnp.put("obs_plazo_entrega", rqnp.getObs_plazo_entrega() );
			 
			mapRqnp.put("cod_necesidad", rqnp.getCod_necesidad());
			mapRqnp.put("cod_finalidad", rqnp.getCod_finalidad());
			 
			mapRqnp.put("dependencia", rqnp.getNombreUOSolicitante());
			mapRqnp.put("fechaRqnp",new SimpleDateFormat("dd/MM/yyyy HH:mm").format(rqnp.getFechaRqnp()));
			mapRqnp.put("motivoRqnp",rqnp.getMotivoSolicitud());
			log.debug("MotivoSolicitud"+rqnp.getMotivoSolicitud());
			mapRqnp.put("montoRqnp",  rqnp.getMontoRqnp().setScale(2, RoundingMode.HALF_UP) );
			mapRqnp.put("motivoRechazo", rqnp.getMotivoRechazo());
			 
			mapRqnp.put("des_proceso", rqnp.getDes_proceso());
			mapRqnp.put("des_meta", rqnp.getDes_meta() ) ;
			mapRqnp.put("anio_atencion", rqnp.getAnio_atencion() ) ;
			mapRqnp.put("mes_atencion", rqnp.getMes_atencion() ) ;
			 
			//////////////////////////////////////////////////////////////
			mapRqnp.put("ind_prestamo", rqnp.getInd_prestamo()==null?"N":rqnp.getInd_prestamo());
			mapRqnp.put("ind_vinculo", rqnp.getInd_vinculo()==null?"N":rqnp.getInd_vinculo());
			//mapRqnp.put("tipo_vinculo", rqnp.getTipo_vinculo()==null?"04":rqnp.getTipo_vinculo());
			//mapRqnp.put("vinculo", rqnp.getVinculo()==null?"01":rqnp.getVinculo());
			//////////////////////////////////////////////////////////////
			
			if(rqnp.getInd_vinculo()==null){
				mapRqnp.put("ind_vinculo","N");
				mapRqnp.put("tipo_vinculo","04");
				mapRqnp.put("vinculo","01");
			}else{
				mapRqnp.put("tipo_vinculo",rqnp.getTipo_vinculo());
				mapRqnp.put("vinculo",rqnp.getVinculo());
			}
			
			if(rqnp.getTipo_vinculo()==null){
				mapRqnp.put("tipo_vinculo","04");
				mapRqnp.put("vinculo","01");
			}else{
				mapRqnp.put("tipo_vinculo",rqnp.getTipo_vinculo());
				mapRqnp.put("vinculo",rqnp.getVinculo());
			}
			
			
			log.debug("ind_vinculo: " + rqnp.getInd_vinculo());
			log.debug("ind_prestamo: " + rqnp.getInd_prestamo());
			log.debug("tipo_vinculo: " + rqnp.getTipo_vinculo());
			log.debug("vinculo: " + rqnp.getVinculo());
			
			
			if(cod_objeto.equals("B")){
				cod_objeto="1";
			}else if (cod_objeto.equals("S")){
				cod_objeto="2";
			}else if(cod_objeto.equals("O")){
				cod_objeto="3";
			}
				 
			mapRqnp.put("cod_objeto", cod_objeto ) ;
			 
			ls_cod_uoc1= rqnp.getCod_uoc1();
			ls_cod_uoc2= rqnp.getCod_uoc2();
			ls_cod_uoc3= rqnp.getCod_uoc3();
			 
			if (ls_cod_uoc1 != null ){
				log.debug("cod_uoc1" + ls_cod_uoc1);
				mapRqnp.put("cod_uoc1", ls_cod_uoc1);
				DependenciaBean dependencia= dependenciaDao.obtenerDependencia(ls_cod_uoc1);
				if (dependencia != null){
					ls_nom_uoc1 = dependencia.getNom_largo();
					ls_num_uoc1 = dependencia.getUuoo();
				 }
				 
				mapRqnp.put("nom_uuoo1", ls_nom_uoc1);
				mapRqnp.put("num_uuoo1", ls_num_uoc1);
			}else{
				log.debug("La dependencia no fue registrada");
				///se agrega la uuoo del usuario
				String aucOfRqnp = requerimientoNoProgramadoDao.obtenerAucOfRqnp(rqnp.getCodigoRqnp());
				if (aucOfRqnp !=null){
					DependenciaBean dependencia = dependenciaDao.obtenerDependencia(aucOfRqnp);
					if (dependencia != null){
						ls_nom_uoc1 = dependencia.getNom_largo();
						ls_num_uoc1 = dependencia.getUuoo();
					 }
					mapRqnp.put("cod_uoc1", cod_dep_user);
					mapRqnp.put("nom_uuoo1", ls_nom_uoc1);
					mapRqnp.put("num_uuoo1", ls_num_uoc1);
					log.debug(ls_nom_uoc1);
				}
			}
			if (ls_cod_uoc2 != null ){
				mapRqnp.put("cod_uoc2", ls_cod_uoc2);	
				DependenciaBean dependencia= dependenciaDao.obtenerDependencia(ls_cod_uoc2);
				if (dependencia != null){
					ls_nom_uoc2 = dependencia.getNom_largo();
					ls_num_uoc2 = dependencia.getUuoo();
				 }
				 
				mapRqnp.put("nom_uuoo2", ls_nom_uoc2);
				mapRqnp.put("num_uuoo2", ls_num_uoc2);
			}
			
			if (ls_cod_uoc3 != null ){
				mapRqnp.put("cod_uoc3", ls_cod_uoc3);
				DependenciaBean dependencia= dependenciaDao.obtenerDependencia(ls_cod_uoc3);
				if (dependencia != null){
					ls_nom_uoc3 = dependencia.getNom_largo();
					ls_num_uoc3 = dependencia.getUuoo();
				 }
				mapRqnp.put("nom_uuoo3", ls_nom_uoc3);
				mapRqnp.put("num_uuoo3", ls_num_uoc3);
			}
			 
			//AGREGADO PARA RECUPERAR COMITE
			//RECUPERANDO COMITE
			//INDICADOR COMITE
			 
			mapRqnp.put("ind_comite", rqnp.getInd_comite()==null?"":rqnp.getInd_comite());
			mapRqnp.put("ind_tec_tit", rqnp.getInd_tec_tit()==null?"":rqnp.getInd_tec_tit());
			mapRqnp.put("ind_tec_sup", rqnp.getInd_tec_sup()==null?"":rqnp.getInd_tec_sup());
			mapRqnp.put("cod_au_tit", rqnp.getCod_au_tit()==null?"":rqnp.getCod_au_tit());
			mapRqnp.put("cod_au_sup", rqnp.getCod_au_sup()==null?"":rqnp.getCod_au_sup());
			mapRqnp.put("cod_tec_tit", rqnp.getCod_tec_tit()==null?"":rqnp.getCod_tec_tit());
			mapRqnp.put("cod_tec_sup", rqnp.getCod_tec_sup()==null?"":rqnp.getCod_tec_sup());
			mapRqnp.put("nom_tec_tit", rqnp.getNom_tec_tit()==null?"":rqnp.getNom_tec_tit());
			mapRqnp.put("nom_tec_sup", rqnp.getNom_tec_sup()==null?"":rqnp.getNom_tec_sup());
			 
			//RECUPERANDO MIEMBROS DEL COMITE
			miembroComiteAuTit=registroPersonalService.obtenerPersonaxCodigo(rqnp.getCod_au_tit()==null?"":rqnp.getCod_au_tit());
			miembroComiteAuSup=registroPersonalService.obtenerPersonaxCodigo(rqnp.getCod_au_sup()==null?"":rqnp.getCod_au_sup());
			 
			//RECUPERANDO MIEMBROS DEL COMITE
				miembroComiteAuTit=registroPersonalService.obtenerPersonaxCodigo(rqnp.getCod_au_tit()==null?"":rqnp.getCod_au_tit());
				miembroComiteAuSup=registroPersonalService.obtenerPersonaxCodigo(rqnp.getCod_au_sup()==null?"":rqnp.getCod_au_sup());
				
				if(miembroComiteAuTit!=null){
					mapRqnp.put("nombre_au_tit", miembroComiteAuTit.getNombre_completo()==null?"":miembroComiteAuTit.getNombre_completo());
					mapRqnp.put("reg_au_tit", miembroComiteAuTit.getNumero_registro()==null?"":miembroComiteAuTit.getNumero_registro());
				}else{
					mapRqnp.put("nombre_au_tit", "");
					mapRqnp.put("reg_au_tit", "");
				}
				
				if(miembroComiteAuSup!=null){
					mapRqnp.put("nombre_au_sup", miembroComiteAuSup.getNombre_completo()==null?"":miembroComiteAuSup.getNombre_completo());
					mapRqnp.put("reg_au_sup", miembroComiteAuSup.getNumero_registro()==null?"":miembroComiteAuSup.getNumero_registro());
				}else{
					mapRqnp.put("nombre_au_sup", "");
					mapRqnp.put("reg_au_sup", "");
				}
				
				if(rqnp.getCod_tec_tit()!=null ){
					 miembroComiteTecTit=registroPersonalService.obtenerPersonaxCodigo(rqnp.getCod_tec_tit().trim());
					 mapRqnp.put("nombre_tec_tit", miembroComiteTecTit.getNombre_completo());
					 mapRqnp.put("reg_tec_tit", miembroComiteTecTit.getNumero_registro());
				}else if (rqnp.getNom_tec_tit()!=null){
					mapRqnp.put("nombre_tec_tit", rqnp.getNom_tec_tit());
					mapRqnp.put("reg_tec_tit", "");
				}else{
					mapRqnp.put("nombre_tec_tit", "");
					mapRqnp.put("reg_tec_tit", ""); 
					 }
				 
				if(rqnp.getCod_tec_sup()!=null){
					 miembroComiteTecSup=registroPersonalService.obtenerPersonaxCodigo(rqnp.getCod_tec_sup().trim());
					 mapRqnp.put("nombre_tec_sup", miembroComiteTecSup.getNombre_completo());
					 mapRqnp.put("reg_tec_sup", miembroComiteTecSup.getNumero_registro());
				 }else if (rqnp.getNom_tec_sup()!=null){
					 mapRqnp.put("nombre_tec_sup", rqnp.getNom_tec_sup());
					 mapRqnp.put("reg_tec_sup", "");
				 }else{
					 mapRqnp.put("nombre_tec_sup", "");
					 mapRqnp.put("reg_tec_sup", ""); 
					 }
			 //FIN AGREGADO PARA RECUPERAR COMITE
		return mapRqnp;
		
	}catch (Exception ex) {
		   log.error("Error en RequerimientoAucServiceImpl.recuperarRqnpCab: " + ex.getMessage(), ex);
		   throw new ServiceException(this, ex);
		} 
	finally {
		if (log.isDebugEnabled()) log.debug("Fin - RequerimientoAucServiceImpl.recuperarRqnpCab");
	}
	
	}

	/**
     * Recupera la dependencia 
     * @param String num_uuoo - Numero de la UUOO 
     * @return RequerimientoNoProgramadoBean - 
     */  
	@Override
	public Map<String, Object> recuperarDependenciaXuuoo(String num_uuoo) {
		 Map<String, Object> mapRqnp = new HashMap<String, Object>();
		String ls_nom_uoc="";
		String ls_cod_uoc="";
		try{	 
			 if (num_uuoo != null ){
				 
				 DependenciaBean dependencia= dependenciaDao.obtenerDependenciaXuuoo(num_uuoo);
				 if (dependencia != null){
					 ls_nom_uoc = dependencia.getNom_largo();
					 ls_cod_uoc = dependencia.getCod_dep();
					
					 mapRqnp.put("nom_dep", ls_nom_uoc);
					 mapRqnp.put("cod_dep", ls_cod_uoc);
					 mapRqnp.put("cod_uuoo", dependencia.getUuoo());
					 mapRqnp.put("cod_plaza", dependencia.getCod_plaza());
					 
				 }else{
					 mapRqnp=null;
				 }
				
			 }else{
				 mapRqnp=null;
			 }
		return mapRqnp;
		
	}catch (Exception ex) {
		   log.error("Error en RequerimientoAucServiceImpl.recuperarRqnpCab: " + ex.getMessage(), ex);
		   throw new ServiceException(this, ex);
		} 
	finally {
		if (log.isDebugEnabled()) log.debug("Fin - RequerimientoAucServiceImpl.recuperarRqnpCab");
	}
	
	}
	
	
	/**
     * Recupera la dependencia 
     * @param String param
     * @return RequerimientoNoProgramadoBean - 
     */  
	@Override
	public Map<String, Object> obtenerUuoo(Map<String, Object> parm) {
		 Map<String, Object> mapRqnp = new HashMap<String, Object>();
		String ls_nom_uoc="";
		String ls_cod_uoc="";
		try{	 
			 if (parm != null ){
				 DependenciaBean dependencia= dependenciaDao.obtenerUuoo(parm);
				 if (dependencia != null){
					 ls_nom_uoc = dependencia.getNom_largo();
					 ls_cod_uoc = dependencia.getCod_dep();
					
					 mapRqnp.put("nom_dep", ls_nom_uoc);
					 mapRqnp.put("cod_dep", ls_cod_uoc);
					 mapRqnp.put("cod_uuoo", dependencia.getUuoo());
					 mapRqnp.put("cod_plaza", dependencia.getCod_plaza());
					 mapRqnp.put("flagInfoInf", dependencia.getFlagInfoInf());
					 
				 }else{
					 mapRqnp=null;
				 }
				
			 }else{
				 mapRqnp=null;
			 }
		return mapRqnp;
		
		}catch (Exception ex) {
			   log.error("Error en RequerimientoAucServiceImpl.recuperarRqnpCab: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
			} 
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoAucServiceImpl.recuperarRqnpCab");
		}
	
	}
	
	
	/**
     * Reqistra  la Cabecera del Requerimientos No Programados 
     * @param Map<String, Object> params 
     * @return String - Código de Requerimiento no Programado
     */  
	@Override
	public String registrarCabRqnpAuc(Map<String, Object> params) {
		if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoAucServiceImpl.registrarCabRqnpAuc");
		RequerimientoNoProgramadoBean rqnp= new RequerimientoNoProgramadoBean();
		String 	codigo_rq="";
	
		String 	ls_mes="";
		String 	ls_sec_rqnp="";
		String 	codigo_rqnp="";
		String 	cod_uuoo="";
		String 	user="";
		try {
			cod_uuoo	=(String) params.get("uuoo");
			user 		=(String) params.get("user");
			codigo_rq 	=(String) params.get("codigoRqnp");
			rqnp.setCodigoRqnp(codigo_rq);
			
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
			
			rqnp.setDes_proceso((String) params.get("des_proceso"));
			rqnp.setDes_meta((String) params.get("des_meta"));
			rqnp.setCod_uoc1((String) params.get("cod_uoc1"));
			rqnp.setCod_uoc2((String) params.get("cod_uoc2"));
			rqnp.setCod_uoc3((String) params.get("cod_uoc3"));
			rqnp.setAnio_atencion((String) params.get("anio_atencion"));
			rqnp.setMes_atencion ((String) params.get("mes_atencion"));
			rqnp.setTipo_vinculo((String) params.get("tipo_vinculo"));
			rqnp.setMotivoSolicitud( (String) params.get("motivoSolicitud"));
			rqnp.setVinculo((String) params.get("vinculo"));
			rqnp.setCod_objeto((String) params.get("cod_objeto"));
			
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
			
			DataSource dataSource = obtenerDataSource("sig", false);
			accesoDao.setUsuarioAcceso(dataSource,"USER", user);
			requerimientoNoProgramadoDao.updateAUC(dataSource, rqnp);
			accesoDao.setUsuarioAccesoNull(dataSource,"USER");
			
		}catch (Exception ex) {
			   log.error("Error en RequerimientoAucServiceImpl.registrarCabRqnpAuc: " + ex.getMessage(), (Throwable)ex);
			   throw new ServiceException(this, ex);
			} 
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoAucServiceImpl.registrarCabRqnpAuc");
		}
		
		return codigo_rq;
	}
	

	/**
     * Reqistra  el Cambio de Bien del Requerimiento
     * @param Map<String, Object> params 
     * @return String - Código de Requerimiento no Programado
     */  
	@Override
	public void registrarCambioBien(RequerimientoNoProgBienesBean bien) {
		if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoAucServiceImpl.registrarCambioBien");
		RequerimientoNoProgramadoBean rqnp= new RequerimientoNoProgramadoBean();
		List<RequerimientoNoProgMetasBean> listaMetas = new ArrayList<RequerimientoNoProgMetasBean>();
		Map<String,Object> params = new HashMap<String, Object>();
		BigDecimal precio = new BigDecimal(0);
		BigDecimal cantidad = new BigDecimal(0);
		BigDecimal monto = new BigDecimal(0);
		BigDecimal suma = new BigDecimal(0);
		String 	user="";
		try {
			user= bien.getCod_jefe() ;//aqui va el valor del usuario login
			
			DataSource dataSource = obtenerDataSource("sig", false);
			accesoDao.setUsuarioAcceso(dataSource,"USER", user);
			
			// recuperar la lista de metas--------------------------------
			params.put("cod_req", bien.getCodigoRqnp());
			params.put("cod_bien", bien.getItem_origen());
			precio = bien.getPrecioUnid();
			log.debug("precioUnid :" + precio.byteValue());
			listaMetas= (List<RequerimientoNoProgMetasBean>) requerimientoNoProgMetasDao.listarRqnpMetasBien(params);
			
			//eliminando metas-------------------------------
			
			RequerimientoNoProgMetasBean metas = new RequerimientoNoProgMetasBean();
			metas.setCodigoRqnp(bien.getCodigoRqnp());
			metas.setCodigoBien( bien.getItem_origen());
			
			requerimientoNoProgMetasDao.delete(metas);
			
			//actualizando bien
			params.clear();
			
			params.put("cod_req", bien.getCodigoRqnp());
			//actualizar los item
			requerimientoNoProgBienesDao.updateModificaBien(dataSource, bien);
			
			///Insertando nuevas metas
			
			log.debug( "listametas " + listaMetas.size());
			for (RequerimientoNoProgMetasBean obj : listaMetas ){
				//actualizar los montos 
				cantidad =  obj.getCantidadTotal();
				monto = cantidad.multiply(precio);
				metas = new RequerimientoNoProgMetasBean();
				metas.setCodigoBien( bien.getCodigoBien());
				metas.setCodigoRqnp(bien.getCodigoRqnp());
				metas.setSecuenciaMeta( obj.getSecuenciaMeta());
				metas.setAnioEjec( obj.getAnioEjec());
				metas.setMontoSoles(monto);
				metas.setCantidadTotal(obj.getCantidadTotal());
				metas.setMontoDolar(new BigDecimal(0) );
								
				//metas.setCodigoBien_origen(bien.getItem_origen());
				
				log.debug("Monto " + monto.longValue());
				requerimientoNoProgMetasDao.insertar(metas);
				cantidad = new BigDecimal(0);
				monto = new BigDecimal(0);
			}
			
			listaMetas.clear();
			//recuperar los item
			listaMetas= (List<RequerimientoNoProgMetasBean>) requerimientoNoProgMetasDao.listarRqnpMetasBien(params);
			for (RequerimientoNoProgMetasBean  obj : listaMetas ){
				monto = obj.getMontoSoles();
				suma= suma.add(monto);
			}
			//actualizar saldo total
			rqnp.setCodigoRqnp(bien.getCodigoRqnp());
			rqnp.setMontoRqnp(suma);
			requerimientoNoProgramadoDao.updateMonto(rqnp);
			
			accesoDao.setUsuarioAccesoNull(dataSource,"USER");
		}catch (Exception ex) {
			   log.error("Error en RequerimientoAucServiceImpl.registrarCambioBien: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
			} 
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoAucServiceImpl.registrarCambioBien");
		}
	}
	
	
	/**
     * Registra el Rechazo de la atención de un item del RQNP
     *  por parte del Jefe Inmediato o Intendente.
     * @param Map<String, Object> params 
     * @return String - Intendente(S-N)
     */
	@Override
	public String registrarRechazarRqnp(Map<String, Object> params) {
		if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoAucServiceImpl.registrarRechazarRqnp");
		Map<String,Object> parm = new HashMap<String, Object>();
		
		String cod_req 	=(String) params.get ("cod_rqnp");
		String cod_aprueba 	=(String) params.get ("cod_aprueba");
		String cod_Bien		=(String) params.get ("cod_Bien");
		String obs_rechazo		=(String) params.get ("obs_rechazo");
		String user 	=(String) params.get ("user");
		
		try {
			DataSource dataSource = obtenerDataSource("sig", false);
			
			RequerimientoNoProgBienesBean bien = new RequerimientoNoProgBienesBean();
			bien=requerimientoNoProgBienesDao.recuperarRqnpBienes(cod_req, cod_Bien);
			//bien.setCodigoRqnp(cod_req);
			//bien.setCodigoBien(cod_Bien);
			if (bien.getInd_estado().equals("02") ){
				params.put("cod_accion", "004");
				//params.put("exp_obs", "REQUERIMIENTO RECHAZADO POR EL AUC");
				params.put("exp_obs", "REQUERIMIENTO RECHAZADO POR EL \u00C1REA FORMULADORA");
			}
			
			bien.setObs_rechazo(obs_rechazo);
			bien.setFec_rechazo(new Date());
			bien.setCod_jefe(cod_aprueba);
			bien.setInd_estado("08");//RECHAZADO
			
			accesoDao.setUsuarioAcceso(dataSource,"USER", user);
			requerimientoNoProgBienesDao.updateRechazoAuc(dataSource, bien);
			
			parm.put("anio_pro", (String) params.get ("anio_pro"));
			parm.put("cod_sede", (String) params.get ("cod_sede"));
			parm.put("cod_responsable", cod_aprueba);
			parm.put("cod_proceso", "249");
			parm.put("cod_accion", (String) params.get ("cod_accion"));
			parm.put("cod_exp", bien.getNum_exp());
			parm.put("exp_estado", "003");
			parm.put("exp_obs", (String) params.get ("exp_obs"));
			requerimientoNoProgBienesDao.crearAccion(parm);
			accesoDao.setUsuarioAccesoNull(dataSource,"USER");
		}catch (Exception ex) {
			   log.error("Error en RequerimientoAucServiceImpl.registrarRechazarRqnp: " + ex.getMessage(), (Throwable)ex);
			   throw new ServiceException(this, ex);
			} 
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoAucServiceImpl.registrarRechazarRqnp");
		}
		
		return cod_req;
	}
	
	
	
	/**
     * Recupera un  Requerimientos No Programado 
     * @param String cod_req - Código de Requerimeinto no Programado 
     * @return RequerimientoNoProgramadoBean - Requerimiento no Programado
     */  
	@Override
	public RequerimientoNoProgramadoBean recuperarRqnp(String cod_req) {
		if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoAucServiceImpl.RecuperarRqnp");
		
		List<Map<String, Object>> lsbienes = new ArrayList<Map<String,Object>>();
		Map<String, Object> requerimientoNoProgBienesBean ;
		char ini=' ' ;
		char ini2= "'".charAt(0) ;
		char salto =(char)10; 
		char retorno =(char)13; 
		char espacio =(char)32; 
		CharSequence comi ="&quot";
		CharSequence comis ="\"";
		try{
			Map map_rqnp = new HashMap();
			map_rqnp.put("cod_req", cod_req);
	
			map_rqnp.put("ind_estado", "02");
		RequerimientoNoProgramadoBean rqnp= requerimientoNoProgramadoDao.recuperarRqnp(cod_req);
		Collection<RequerimientoNoProgBienesBean> listaBienes = (List<RequerimientoNoProgBienesBean>) requerimientoNoProgBienesDao.listarRqnpBienes(map_rqnp);
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
				if(obj.getAuct1() !=null){
					DependenciaBean dependencia=dependenciaDao.obtenerDependencia(obj.getAuct1());
					if (dependencia != null){
						requerimientoNoProgBienesBean.put("auct_name", dependencia.getCod_dep()+" - "+ dependencia.getNom_corto());
					}else{
						requerimientoNoProgBienesBean.put("auct_name", " ");
					}
				}else{
					requerimientoNoProgBienesBean.put("auct_name", " ");
				}
				log.debug("Adicionando " + obj.getCodigoBien());
				lsbienes.add(requerimientoNoProgBienesBean);
			}
		}
		rqnp.setListaBienes(lsbienes);
		return rqnp;
		
	}catch (Exception ex) {
		   log.error("Error en RequerimientoAucServiceImpl.RecuperarRqnp: " + ex.getMessage(), ex);
		   throw new ServiceException(this, ex);
		} 
	finally {
		if (log.isDebugEnabled()) log.debug("Fin - RequerimientoAucServiceImpl.RecuperarRqnp");
	}
	
	}
	
	/**
     * Registra la Aprobación de la atención de un ítem del RQNP
     *  por parte del Jefe Inmediato o Intendente.
     * @param Map<String, Object> params 
     * @return String - Intendente(S-N)
     */
	@Override
	public String registrarContratacionRqnp(Map<String, Object> params)throws  ServiceException{
		if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoAucServiceImpl.registrarContratacionRqnp");
		//String intendente;
		String ls_super_intendente;
		String ls_jefe;
		
		String ls_anio 		= "";
		String cod_dep		= "";
		String oec_ini		= "";
		
		String oec_sig		= "";
		String ind_fin		= "";
		String ls_solicitante="";
		String ls_intendente ="";
		String ls_jefe_auc ="";
		
		Map<String, Object> parm = new HashMap<String, Object>();
		CatalogoBienesBean bienCatalogo= new CatalogoBienesBean(); 
		String cod_req 		=(String) params.get ("cod_rqnp");
		String cod_aprueba 		=(String) params.get ("cod_aprueba"); //Usuario Logueado
		String cod_bien			=(String) params.get ("cod_bien");
		String ind_especializado	=(String) params.get ("ind_especializado");
		String 	user 		=(String) params.get ("user");
		ls_anio 			=(String) params.get ("anio_pro");
		String tipo_ruta ="";  
		String cod_auc ="";
		//Recuperando Bien///////////////////////////////////////
		try {
			if (log.isDebugEnabled()) log.debug("cod_aprueba:"+cod_aprueba);
			DataSource dataSource = obtenerDataSource("sig", false);
			RequerimientoNoProgBienesBean bien = new RequerimientoNoProgBienesBean();
			RequerimientoNoProgramadoBean rqnp = new RequerimientoNoProgramadoBean();
			
			rqnp = requerimientoNoProgramadoDao.recuperarRqnp(cod_req); 
			rqnp.setEstadoRqnp("03");//estado registrado
			bien=requerimientoNoProgBienesDao.recuperarRqnpBienes(cod_req, cod_bien);
			tipo_ruta 	= bien.getTipo_ruta();
			cod_auc 	= bien.getAuct1();
			log.debug("tipo_ruta " +  tipo_ruta);
			log.debug("cod_auc " +  cod_auc);
			
			if (bien.getOec_compra()!=null){
				oec_ini=bien.getOec_compra();
			}
			ls_solicitante=rqnp.getCodigoResposanble();
			bienCatalogo=catalogoBienesDao.recuperarCatalogoBien( params); 
			
			ls_super_intendente = maestroPersonalDao.obtenerSuperIntendente(ls_solicitante);
			ls_intendente		= maestroPersonalDao.obtenerAprobadorAUC(cod_auc);
			ls_jefe				= maestroPersonalDao.obtenerJefeInmediato(ls_solicitante);
			ls_jefe_auc			= maestroPersonalDao.obtenerJefeAuc(cod_auc);;
			
			if (log.isDebugEnabled()) log.debug("ls_jefe: "+ls_jefe);
			if (log.isDebugEnabled()) log.debug("ls_super_intendente: "+ls_super_intendente);
			if (log.isDebugEnabled()) log.debug("ls_jefe_uuoo: "+ls_intendente);
			if (log.isDebugEnabled()) log.debug("ls_jefe_auc: "+ls_jefe_auc);
			
			cod_dep = rqnp.getCodigoDependencia();
			
			if(ls_jefe_auc.equals(ls_intendente)){
				bien.setCod_jefe(cod_aprueba);
				///SE ENVIA A LA DPG-INA 
				parm=this.identificaBandeja(ls_anio, cod_bien, cod_dep, oec_ini, bien,0);
				if (parm !=null){
					//bien.setCod_jefe(cod_aprueba);
					bien.setInd_estado((String)parm.get ("ind_estado"));
					bien.setInd_estado_alterno((String)parm.get ("ind_estado_alterno"));
					bien.setCod_plaza((String) parm.get("cod_plaza"));
					bien.setOec_compra((String) parm.get("oec_sig"));
					parm.put("cod_accion", "004");
				}
				
			
			} else if (ls_intendente.equals(ls_jefe)) {
					bien.setCod_jefe(cod_aprueba);
					///SE ENVIA A LA OEC Ó DPG-INA 
					parm=this.identificaBandeja(ls_anio, cod_bien, cod_dep, oec_ini, bien,0);
					if (parm !=null){
						//bien.setCod_jefe(cod_aprueba);
						bien.setInd_estado((String)parm.get ("ind_estado"));
						bien.setInd_estado_alterno((String)parm.get ("ind_estado_alterno"));
						bien.setCod_plaza((String) parm.get("cod_plaza"));
						bien.setOec_compra((String) parm.get("oec_sig"));
						parm.put("cod_accion", "004");
					}	
					
				}else{
					bien.setCod_jefe(cod_aprueba);
					///SE ENVIA A LA AUC SUPERIOR 
					MaestroPersonalBean jefe= maestroPersonalDao.obtenerPersonaxCodigo(ls_intendente);
					parm.put("cod_accion", "004");
					bien.setInd_estado("04");//ENVIADO INTENDENTE
					parm.put("exp_obs", "REQUERIMIENTO ENVIADO AL JEFE UUOO SUPERIOR DE "+ jefe.getUuoo()+ " - " + jefe.getDependencia() +"  PARA SU APROBACIÓN");
				}
			
			if (parm !=null){
				bien.setInd_autorizaauct1("A");
				bien.setFec_autorizaauct1(new Date());
				bien.setCod_usuarioauct1(cod_aprueba);
				String fechEnviOec = (String)parm.get("fech_envi_oec");
	            if (fechEnviOec != null && fechEnviOec.equals("fech_envi_oec")) {
	               bien.setFechEnviOec(new Date());
	             }
				
				accesoDao.setUsuarioAcceso(dataSource,"USER", user);
				requerimientoNoProgBienesDao.updateEstadoAUC(dataSource, bien);	
				parm.put("anio_pro", (String) params.get ("anio_pro"));
				//parm.put("cod_sede", (String) params.get ("cod_sede"));
				parm.put("cod_responsable", (String) params.get ("cod_aprueba"));
				parm.put("cod_proceso", "249");		
				parm.put("cod_exp", bien.getNum_exp());
				parm.put("exp_estado", "002");	///HITO (Cambia el estado MODIFICADO por CONFORME)
				requerimientoNoProgBienesDao.crearAccion(parm);
				requerimientoNoProgramadoDao.updateEstado(dataSource, rqnp);
			
				accesoDao.setUsuarioAccesoNull(dataSource,"USER");
			}	
			
		}catch (ServiceException exsrv) {	
			log.error("Error ServiceException en RequerimientoAucServiceImpl.registrarContratacionRqnp: " + exsrv.getMessage(), (Throwable)exsrv);
			throw new ServiceException(this, exsrv);
		}catch (Exception ex) {
			   log.error("Error en RequerimientoAucServiceImpl.registrarContratacionRqnp: " + ex.getMessage(), (Throwable)ex);
			   throw new ServiceException(this, ex);
			} 
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoAucServiceImpl.registrarContratacionRqnp");
		}
		return "";
	}
	
	

	private Map<String, Object> identificaBandeja(String ls_anio,String cod_bien, String cod_dep, String oec_ini , RequerimientoNoProgBienesBean bien , int pasada )
	throws ServiceException{
		Map<String , Object> params = new  HashMap<String, Object>();
		if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoAucServiceImpl.identificaBandeja");
		BigDecimal saldo = new BigDecimal(0);
		BigDecimal monto = bien.getCantBien().multiply(bien.getPrecioUnid() );
		if (log.isDebugEnabled())log.debug("monto : "+ monto.doubleValue());
		String cod_plaza="";
		String oec_sig="";
		String ind_fin="";
		String ind_saldo="";
		String oec_central="";
		String ls_exceso="";
		String indicador_oec="";
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
				//oec central-----------------------------------------------
				//oec_central=rqnpBandejaDao.oecEsCentral(oec_sig);
				indicador_oec=rqnpBandejaDao.obtenerIndicadorOec(oec_sig);
				//if (oec_central.equals("S")){
				if (indicador_oec.equals("0")){	
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
						params.put("fech_envi_oec", "fech_envi_oec");//Para fecha de envio a la OEC
						params.put("exp_obs", "Requerimiento ha sido derivado a la OEC "+ dependencia.getUuoo() + " - "+ dependencia.getNom_corto().trim()+ " PARA SU ATENCIÓN.");
					}
					
					if (log.isDebugEnabled())log.debug("CON SALDO NO CENTRAL");
					if (log.isDebugEnabled())log.debug("07.01 Requerimiento ha sido derivado a la OEC para su atención.");
				}
			}else if (ind_fin.equals("S")){
				//Se permite exceso------------------------------------------
				ls_exceso=sysParametrosDao.permiteExcesoRqnp();
				if(ls_exceso.equals("S")){
					//oec central--------------------------------------------
					//oec_central=rqnpBandejaDao.oecEsCentral(oec_sig);
					indicador_oec=rqnpBandejaDao.obtenerIndicadorOec(oec_sig);
					//if (oec_central.equals("S")){
					if (indicador_oec.equals("0")){
						params.put("ind_estado", "05");
						params.put("ind_estado_alterno", "02");
						params.put("exp_obs", "Requerimiento ha sido derivado a la DPG PARA DETERMINAR LA NECESIDAD DE SU COMPRA DIRECTA.");
						if (log.isDebugEnabled())log.debug("SIN SALDO ... REG FINAL ...EXCESO  ...CENTRAL");
						if (log.isDebugEnabled())log.debug("05.02 Requerimiento ha sido derivado a la DPG para determinar la necesidad de su compra directa.");
					}else{
						params.put("ind_estado", "07");
						params.put("ind_estado_alterno", "02");
						
						dependencia =dependenciaDao.obtenerDependencia(oec_sig);
						if (dependencia !=null){
							params.put("fech_envi_oec", "fech_envi_oec");//Para fecha de envio a la OEC
							params.put("exp_obs", "Requerimiento ha sido derivado a la OEC "+ dependencia.getUuoo() + " - "+ dependencia.getNom_corto().trim()+ " PARA DETERMINAR LA NECESIDAD DE SU COMPRA DIRECTA.");
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
				if (3 >pasada  && 0<=pasada    ){
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
			if (log.isDebugEnabled()) {
        		log.debug((Object)"Fin - RequerimientoAucServiceImpl.identificaBandeja");
            }
		}finally{
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoAucServiceImpl.identificaBandeja");
		}
		return params;
	}
	
	
	private RegistroArchivosFisicoBean invicarLocalServicePDF(Map map_operacion, List lst_item,String fileName){
		log.debug("Inicio RequerimientoAucServiceImpl.invicarLocalServicePDF"  );
		RegistroArchivosFisicoBean fbean = new RegistroArchivosFisicoBean();
		String path_base="";
		//String path_pdf="\\data0\\generador\\jasper\\pdf\\";
		//String path_jasper="\\data0\\generador\\jasper\\plantillas\\PLANTILLA000262.jasper";
		
		String path_pdf="/data0/generador/jasper/pdf/";
		String path_jasper="/data0/generador/jasper/plantillas/PLANTILLA000262.jasper";
		
		InputStream is;
		byte[] fileBytes ;
		try {
			log.debug(path_jasper);
			is = new FileInputStream(path_jasper);
			JasperReport reporte;
			log.debug("avialble: " + is.available() );
			reporte = (JasperReport) JRLoader.loadObject(is);
			 log.debug("name JAsper: " + reporte.getName());
			 
	        JRDataSource dsLista = new JRBeanCollectionDataSource(lst_item);
	        
	        JasperPrint jasperPrint=new JasperPrint() ;
	        jasperPrint = JasperFillManager.fillReport( reporte, map_operacion, dsLista );
	       
	        
	        log.debug("pages: "+ jasperPrint.getPageWidth());
	      
	        fileBytes=JasperExportManager.exportReportToPdf(jasperPrint);
	        
	        
	        log.debug("Tamanio bye: "+fileBytes.length);
	        if(fileBytes.length > 0){
	        	FileOutputStream file = new FileOutputStream(path_pdf+fileName+".pdf");
	        	file.write(fileBytes);
	        	fbean.setData(fileBytes);
	        	fbean.setFile_ext("pdf");
	        	fbean.setFile_name(fileName+".pdf");
	        	
	        }else{
	        	log.error("No se Genero El  Jasper y por lo tanto no el PDF");
	        }
			
	        is.close();
		} catch (FileNotFoundException ex) {
			log.error("FileNotFoundException en RequerimientoAucServiceImpl.invicarLocalServicePDF: " + ex.getMessage(), ex);
			ex.printStackTrace();
		}catch (JRException ex) {
			log.error("JRException en RequerimientoAucServiceImpl.invicarLocalServicePDF: " + ex.getMessage(), ex);
			ex.printStackTrace();
		} catch (IOException ex) {
			log.error("IOException en RequerimientoAucServiceImpl.invicarLocalServicePDF: " + ex.getMessage(), ex);
			ex.printStackTrace();
		}

		log.debug("Fin RequerimientoAucServiceImpl.invicarLocalServicePDF"  );
		return fbean;
	}
	
	
	//DPORRASC2015 - INICIO
	/**
	 * Metodo que valida el rol de au para la RUTA 04
	 */
	public String validaUserAuAUC(String cod_per)throws  ServiceException{
		if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoAucServiceImpl.obtenerJefeInmediatoEncargado");
		String cod_empl="";
		try {
			DataSource dataSource = obtenerDataSource("sig", false);
			
			return requerimientoNoProgramadoDao.validaUserAuAUC(cod_per);
		} catch (Exception ex) {
			   log.error("Error en RequerimientoAucServiceImpl.obtenerJefeInmediatoEncargado: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
		}
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoAucServiceImpl.obtenerJefeInmediatoEncargado");
		}
		
	}
	
	//DPORRASC2015 - FIN
	
	public String validaUserAUC(Map<String, Object> params)throws  ServiceException{
		if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoAucServiceImpl.validaUserAUC");
		
		String is_user="N";
		List< SegRolesUsuariosBean> lista = new ArrayList<SegRolesUsuariosBean>() ;
		
		try {
			 lista = (List<SegRolesUsuariosBean>) segRolesUsuariosDao.listarRoles(params);
			 if (lista != null && lista.size() > 0) {
				 is_user="S";
			 }
			return is_user;
		} 
		catch (Exception ex) {
			   log.error("Error en RequerimientoAucServiceImpl.validaUserAUC: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
		}
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoAucServiceImpl.validaUserAUC");
		}
		
	}
	
	//valida grupo auc
	public String validaGrupoAUC(Map<String, Object> params)throws  ServiceException{
		if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoAucServiceImpl.validaGrupoAUC");
		
		String is_user="N";
		List< SegRolesUsuariosBean> lista = new ArrayList<SegRolesUsuariosBean>() ;
		
		try {
			//lista = (List<SegRolesUsuariosBean>) segRolesUsuariosDao.listarRoles(params);
			lista = (List<SegRolesUsuariosBean>) segRolesUsuariosDao.listarGrupoAUC(params);
			if (lista != null && lista.size() > 0) {
					 is_user="S";
			}
			return is_user;
		} 
		catch (Exception ex) {
			   log.error("Error en RequerimientoAucServiceImpl.validaGrupoAUC: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
		}
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoAucServiceImpl.validaGrupoAUC");
		}
			
	}
	
	
	/**
	 * Metodo que  lista los años
	 * @param
	 * 
	 */
	public Collection listarAnnos()throws  ServiceException{
		if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoAucServiceImpl.listarAnnos");
		Map<String, Object> map= new HashMap<String, Object>();
		Calendar 	fecha =  Calendar.getInstance();
		fecha.add(Calendar.YEAR, -3);
		String ls_anio= String.valueOf( fecha.get(Calendar.YEAR)) ;
		map.put("anio_act", ls_anio);
		
		List< PrgAnnoBean> lista = new ArrayList<PrgAnnoBean>() ;
		List<Map<String, Object>> lsAnnos= new ArrayList<Map<String,Object>>();
		try {
			 lista = (List<PrgAnnoBean>) prgAnnoDao.listarAnnos(map);
			 
			 for (PrgAnnoBean obj :lista){
				 map = new HashMap<String, Object>();
				 map.put("cod", obj.getAnno());
				 map.put("name", obj.getAnno());
				
				 
				 lsAnnos.add(map);
			}
			 
			 return lsAnnos;
		} 
		catch (Exception ex) {
			   log.error("Error en RequerimientoAucServiceImpl.listarAnnos: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
		}
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoAucServiceImpl.listarAnnos");
		}
			
		
	}
	public Collection listarAnnosAll()throws  ServiceException{
		if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoAucServiceImpl.listarAnnos");
		Map<String, Object> map= new HashMap<String, Object>();
		
		List< PrgAnnoBean> lista = new ArrayList<PrgAnnoBean>() ;
		List<Map<String, Object>> lsAnnos= new ArrayList<Map<String,Object>>();
		try {
			 lista = (List<PrgAnnoBean>) prgAnnoDao.listarAnnos(map);
			 
			 for (PrgAnnoBean obj :lista){
				 map = new HashMap<String, Object>();
				 map.put("cod", obj.getAnno());
				 map.put("name", obj.getAnno());

				 lsAnnos.add(map);
			}
			 
			 return lsAnnos;
		} 
		catch (Exception ex) {
			   log.error("Error en RequerimientoAucServiceImpl.listarAnnos: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
		}
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoAucServiceImpl.listarAnnos");
		}

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

		return lstMeses;
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
	
	
	//INICIO: DPORRASC
	//LISTA LAS METAS DE UN ITEM SELECCIONADO
	//DEBERA ESTAR GUARDADO EN LA BASE DE DATOS
	@Override
	public Collection listarAucMetas(Map<String, Object> params) {
		if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoAucServiceImpl.listarAucMetas");
		Map<String, Object> map;
		
		List<Map<String, Object>> lsMetasAcc = new ArrayList<Map<String,Object>>();
		
		try {
			String precio_unid =(String)params.get("precio_unid");
			
			List<RequerimientoNoProgMetasBean> lista = (List<RequerimientoNoProgMetasBean>) requerimientoNoProgMetasDao.listarRqnpMetas(params);
			
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
			
			for(RequerimientoNoProgMetasBean data: lista){
				map = new HashMap<String, Object>();
				
			//SE COMENTO PARA ESTANDARIZAR CON LA LISTA ANTERIOR
				
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

				lsMetasAcc.add(map);
			}
			return lsMetasAcc;
		} 
		catch (Exception ex) {
			   log.error("Error en RequerimientoAucServiceImpl.listarAucMetas: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
		}
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoAucServiceImpl.listarAucMetas");
		}
	}

	/**
     * Recupera la dependencia 
     * @param String num_uuoo - Codigo de Dependencia
     * @return RequerimientoNoProgramadoBean - 
     */  
	@Override
	public Map<String, Object> recuperarDependenciaXcod(String cod_dep) {
		 Map<String, Object> mapRqnp = new HashMap<String, Object>();
		String ls_nom_uoc="";
		String ls_cod_uoc="";
		String ls_plaza_uoc=""; //AGREGADO
		String ls_uuoo=""; //AGREGADO
		try{	 
			 if (cod_dep != null ){
				 
				 DependenciaBean dependencia= dependenciaDao.obtenerDependenciaXcod(cod_dep);
				 if (dependencia != null){
					 ls_nom_uoc = dependencia.getNom_largo();
					 ls_cod_uoc = dependencia.getCod_dep();
					 ls_plaza_uoc = dependencia.getCod_plaza();//AGREGADO
					 ls_uuoo=dependencia.getUuoo(); //AGREGADO
					 
					 mapRqnp.put("nom_dep", ls_nom_uoc);
					 mapRqnp.put("cod_dep", ls_cod_uoc);
					 mapRqnp.put("cod_plaza", ls_plaza_uoc); //AGREGADO
					 mapRqnp.put("uuoo", ls_uuoo); //AGREGADO
				 }else{
					 mapRqnp=null;
				 }
				
			 }else{
				 mapRqnp=null;
			 }
		return mapRqnp;
		
	}catch (Exception ex) {
		   log.error("Error en RequerimientoAucServiceImpl.recuperarRqnpCab: " + ex.getMessage(), ex);
		   throw new ServiceException(this, ex);
		} 
	finally {
		if (log.isDebugEnabled()) log.debug("Fin - RequerimientoAucServiceImpl.recuperarRqnpCab");
	}
	}

	public AucMetasDao getAucMetasDao() {
		return aucMetasDao;
	}
	public void setAucMetasDao(AucMetasDao aucMetasDao) {
		this.aucMetasDao = aucMetasDao;
	}
	
	
	/**
	 * Metodo que obtiene una cadena con las dependencias en la que un Jefe SUC se encnuentra encargado.
	 * @param String
	 */
	@Override
	public String obtenerCadenaEncargosAUC(String cod_empl) {
		if (log.isDebugEnabled()) log.debug("Inicio - RequerimientoAucServiceImpl.obtenerCadenaEncargosAUC");
		try {

			return aucBandejaDao.obtenerCadenaEncargosAUC(cod_empl);

		}catch (Exception ex) {
			   log.error("Error en RequerimientoAucServiceImpl.obtenerCadenaEncargosAUC: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
			} 
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RequerimientoAucServiceImpl.obtenerCadenaEncargosAUC");
		}
	}
	
	@Override
	public Collection obtenerBeneficiariasForExcel(Map parmSearch) {
		log.debug("Servicio RequerimientoAucServiceImpl.obtenerBeneficiariasForExcel - inicio");
		List<DependenciaBean> listaBeneficiarias = new ArrayList<DependenciaBean>();
		List<Map<String,Object>> lsMapBeneficiarias = new ArrayList<Map<String,Object>>();
		try {
			listaBeneficiarias =  (List<DependenciaBean>) aucBandejaDao.obtenerBeneficiariasForExcel(parmSearch);
		
		for(DependenciaBean  obj:listaBeneficiarias){
			
			Map<String,Object> beneficiariaBean = new HashMap<String, Object>();
			
			beneficiariaBean.put("uuoo", obj.getUuoo());
			beneficiariaBean.put("descUuoo", obj.getNom_largo());
			beneficiariaBean.put("plazaUuoo", obj.getCod_plaza());
			beneficiariaBean.put("cantidad", "");
			
			lsMapBeneficiarias.add(beneficiariaBean);
		}
		}catch( Exception e)
		{
			log.error("Error en RequerimientoAucServiceImpl.obtenerBeneficiariasForExcel: " + e.getMessage(), e);
			throw new ServiceException(this, e);
		}
		
		log.debug("Servicio RequerimientoAucServiceImpl.obtenerBeneficiariasForExcel - Fin");
		return lsMapBeneficiarias;
	}

	public AucBandejaDao getAucBandejaDao() {
		return aucBandejaDao;
	}

	public void setAucBandejaDao(AucBandejaDao aucBandejaDao) {
		this.aucBandejaDao = aucBandejaDao;
	}

	public RequerimientoNoProgramadoDao getRequerimientoNoProgramadoDao() {
		return requerimientoNoProgramadoDao;
	}

	public void setRequerimientoNoProgramadoDao(
			RequerimientoNoProgramadoDao requerimientoNoProgramadoDao) {
		this.requerimientoNoProgramadoDao = requerimientoNoProgramadoDao;
	}

	public RegistroPersonalService getRegistroPersonalService() {
		return registroPersonalService;
	}

	public void setRegistroPersonalService(
			RegistroPersonalService registroPersonalService) {
		this.registroPersonalService = registroPersonalService;
	}

	public DependenciaDAO getDependenciaDao() {
		return dependenciaDao;
	}

	public void setDependenciaDao(DependenciaDAO dependenciaDao) {
		this.dependenciaDao = dependenciaDao;
	}

	public AccesoDAO getAccesoDao() {
		return accesoDao;
	}

	public void setAccesoDao(AccesoDAO accesoDao) {
		this.accesoDao = accesoDao;
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

	public CatalogoBienesDao getCatalogoBienesDao() {
		return catalogoBienesDao;
	}

	public void setCatalogoBienesDao(CatalogoBienesDao catalogoBienesDao) {
		this.catalogoBienesDao = catalogoBienesDao;
	}

	public MaestroPersonalDAO getMaestroPersonalDao() {
		return maestroPersonalDao;
	}

	public void setMaestroPersonalDao(MaestroPersonalDAO maestroPersonalDao) {
		this.maestroPersonalDao = maestroPersonalDao;
	}

	public RqnpBandejaDao getRqnpBandejaDao() {
		return rqnpBandejaDao;
	}

	public void setRqnpBandejaDao(RqnpBandejaDao rqnpBandejaDao) {
		this.rqnpBandejaDao = rqnpBandejaDao;
	}

	public SysParametrosDAO getSysParametrosDao() {
		return sysParametrosDao;
	}

	public void setSysParametrosDao(SysParametrosDAO sysParametrosDao) {
		this.sysParametrosDao = sysParametrosDao;
	}

	public SegRolesUsuariosDAO getSegRolesUsuariosDao() {
		return segRolesUsuariosDao;
	}

	public void setSegRolesUsuariosDao(SegRolesUsuariosDAO segRolesUsuariosDao) {
		this.segRolesUsuariosDao = segRolesUsuariosDao;
	}

	public PrgAnnoDAO getPrgAnnoDao() {
		return prgAnnoDao;
	}

	public void setPrgAnnoDao(PrgAnnoDAO prgAnnoDao) {
		this.prgAnnoDao = prgAnnoDao;
	}

	public T01ParametroDAO getT01ParametroDao() {
		return t01ParametroDao;
	}

	public void setT01ParametroDao(T01ParametroDAO t01ParametroDao) {
		this.t01ParametroDao = t01ParametroDao;
	}
	
	
	//getter and setter
	
}
