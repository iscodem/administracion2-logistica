package pe.gob.sunat.administracion2.siga.rqnp.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.util.WebUtils;

import pe.gob.sunat.administracion2.siga.registro.model.domain.RegistroArchivosFisicoBean;
import pe.gob.sunat.administracion2.siga.registro.model.domain.TdBeneficiarioBean;
import pe.gob.sunat.administracion2.siga.registro.service.RegistroArchivosService;
import pe.gob.sunat.administracion2.siga.rqnp.model.domain.RequerimientoNoProgramadoBean;
import pe.gob.sunat.administracion2.siga.rqnp.service.RequerimientoNoProgramadoService;
import pe.gob.sunat.administracion2.siga.rqnp.service.SolicitudBienService;
import pe.gob.sunat.framework.spring.util.exception.ServiceException;
import pe.gob.sunat.framework.spring.web.view.JsonView;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.MaestroPersonalBean;
import pe.gob.sunat.recurso2.administracion.siga.web.util.BaseController;
import pe.gob.sunat.tecnologia.menu.bean.UsuarioBean;

public class RqnpArchivoController extends BaseController{
	
	private ReloadableResourceBundleMessageSource 		messageSource;
	//private JsonView 							jsonView;
	//private View 								htmlView;
	private String 								uploadDir;
	private RegistroArchivosService				registroArchivosService;
	private RequerimientoNoProgramadoService 	requerimientoNoProgramadoService;
	private SolicitudBienService				solicitudBienService;
	
	private static final Log log = LogFactory.getLog(RqnpArchivoController.class);
	
	/**
     * Carga la Página inicial para cargar el archivo de informe
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView iniciarCargaArchivo(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("Inicio -  RqnpArchivoController.iniciarCargaArchivo");}
		Map<String, Object> mapRqnp = new HashMap<String, Object>();
		Map<String, Object> params 	= new HashMap<String, Object>();
		String num_archivo="";
		
		List<Map<String, Object>> listaArchivos = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> listaTiposDocumentos = new ArrayList<Map<String,Object>>();
		
		String cod_req	="";
		String cod_bien	="";
		String visor	="";
		String bien		="";
		String path_url="";
		try {
			cod_req 	= StringUtils.trim(request.getParameter("txtCodigoRqnp"));
			num_archivo	= StringUtils.trim(request.getParameter("txtNum_reg"));
			cod_bien	= StringUtils.trim(request.getParameter("txtCodigoBien"));
			visor		= StringUtils.trim(request.getParameter("txtvisor"));
			path_url	= StringUtils.trim(request.getParameter("path_url"));
			log.debug("path_url " +path_url);
			RequerimientoNoProgramadoBean rqnp= requerimientoNoProgramadoService.recuperarRqnp(cod_req);
		
			//Seteando Cabecera
			 mapRqnp.put("codigoRqnp", rqnp.getCodigoRqnp());
			 mapRqnp.put("codigoReqNoProgramado", rqnp.getCodigoReqNoProgramado());
			 mapRqnp.put("codigoResposanble", rqnp.getNumeroRegistro());
			 mapRqnp.put("resposanble", rqnp.getNombreSolicitante());
			 mapRqnp.put("codigoUuoo", rqnp.getUuoo());
			 mapRqnp.put("codigoDependencia", rqnp.getCodigoDependencia());
			 mapRqnp.put("anioProceso", rqnp.getAnioProceso());
			 mapRqnp.put("dependencia", rqnp.getNombreUOSolicitante());
			 mapRqnp.put("fechaRqnp", new SimpleDateFormat("dd/MM/yyyy HH:mm").format(rqnp.getFechaRqnp()));
			 mapRqnp.put("motivoRqnp",rqnp.getMotivoSolicitud());
			 mapRqnp.put("montoRqnp",  rqnp.getMontoRqnp().setScale(2, RoundingMode.HALF_UP) );
			 String fechaenvio="";
			 if (rqnp.getFechaEnvioSolicitud()!=null){
				 fechaenvio = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(rqnp.getFechaEnvioSolicitud());
			 }
			 log.debug("FECHA ------>>>>>>" +fechaenvio);
			 mapRqnp.put("fechaenvio", fechaenvio);
			 mapRqnp.put("numeroArchivo", num_archivo);
			 mapRqnp.put("codigoBien", cod_bien);
			 
			 for(Map<String, Object> obj: (List<Map<String, Object>>) rqnp.getListaBienes()){
				 if(cod_bien.equals((String)obj.get("codigoBien"))){
					 bien= (String)obj.get("desBien");
				 }
			 }
			 
			 mapRqnp.put("bien", cod_bien + "    "+bien);
			 //Recuperando Archivos Adjuntos////////////////////////////////////////////////////
			 if(num_archivo!= null){
				 if (!num_archivo.equals("")){
					 params.put("num_reg", num_archivo);
					 listaArchivos= (List<Map<String, Object>>) registroArchivosService.listarArchivos(params);
				 }
			 }
			 ///recuperando tipos de Archivos
			 params.put("cod_app", "LOGISTICA");
			 params.put("cod_mod", "RQNP");
			 listaTiposDocumentos= (List<Map<String, Object>>) registroArchivosService.listarTipoArchivos(params);
			 
			return new ModelAndView("formCargarArchivo").addObject("mapRqnp", mapRqnp)
			.addObject("visor", visor).addObject("listaArchivos", listaArchivos).addObject("listaTiposDocumentos",listaTiposDocumentos)
			.addObject("path_url", path_url);
		} 
		catch(ServiceException ex){
			log.error("Error en  RqnpArchivoController.iniciarCargaArchivo: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en  RqnpArchivoController.iniciarCargaArchivo: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin -  RqnpArchivoController.iniciarCargaArchivo");}
		}
	}
	
	
	
	/**
     * Carga la Página inicial para cargar el archivo de informe
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView iniciarCargaArchivoSolicitud(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("Inicio -  RqnpArchivoController.iniciarCargaArchivoSolicitud");}
		Map<String, Object> mapRqnp = new HashMap<String, Object>();
		Map<String, Object> params 	= new HashMap<String, Object>();
		String num_archivo="";
		
		List<Map<String, Object>> listaArchivos = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> listaTiposDocumentos = new ArrayList<Map<String,Object>>();
		
		String cod_sol	="";
		String cod_req	="";
		String visor	="";
		String bien		="";
		String path_url="";
		String path_url_back="";
		
		try {
			cod_req 	= StringUtils.trim(request.getParameter("txtCodigoRqnp"));
			cod_sol	= StringUtils.trim(request.getParameter("txtCodSol"));
			num_archivo	= StringUtils.trim(request.getParameter("txtNum_reg"));
			log.debug("cod_sol " +cod_sol);
			visor		= StringUtils.trim(request.getParameter("txtvisor"));
			path_url	= StringUtils.trim(request.getParameter("path_url"));
			path_url_back =StringUtils.trim(request.getParameter("path_url_back"));
			log.debug("path_url " +path_url);
			log.debug("num_archivo " +num_archivo);
			Map<String, Object> map_sol = (Map<String, Object>) solicitudBienService.recuperarSolictudBien(cod_sol);
			
			 //Recuperando Archivos Adjuntos////////////////////////////////////////////////////
			 if(num_archivo!= null){
				 if (!num_archivo.equals("")){
					 params.put("num_reg", num_archivo);
					 listaArchivos= (List<Map<String, Object>>) registroArchivosService.listarArchivos(params);
				 }
			 }
			 ///recuperando tipos de Archivos
			 params.put("cod_app", "LOGISTICA");
			 params.put("cod_mod", "RQNP");
			 listaTiposDocumentos= (List<Map<String, Object>>) registroArchivosService.listarTipoArchivos(params);
			 
			return new ModelAndView("formCargarArchivoSolicitud").addObject("mapRqnp", map_sol)
			.addObject("visor", visor).addObject("listaArchivos", listaArchivos).addObject("listaTiposDocumentos",listaTiposDocumentos)
			.addObject("path_url", path_url).addObject("txtCodigoRqnp", cod_req).addObject("path_url_back", path_url_back);
			
		} 
		catch(ServiceException ex){
			log.error("Error en  RqnpArchivoController.iniciarCargaArchivoSolicitud: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en  RqnpArchivoController.iniciarCargaArchivoSolicitud: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin -  RqnpArchivoController.iniciarCargaArchivoSolicitud");}
		}
	}
	

	/**
     * Descargar Archivos Adjuntos de los item del Requerimiento 
     * Requerimiento No Programado.
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView descargarArchivo(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()) {log.debug("debug Inicio - RqnpArchivoController.descargarArchivo");}
		Map<String, Object> params = new HashMap<String, Object>();
	
		try {			
			String sec_reg 	= StringUtils.trim(request.getParameter("txtSecArchivo"));
			log.debug("sec_reg "+sec_reg);
			params.put("sec_reg", sec_reg);
			RegistroArchivosFisicoBean archivo = registroArchivosService.recuperarArchivo(params);
			
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition","attachment;filename="+archivo.getFile_name());	        
			
			OutputStream os = response.getOutputStream();
		
			os.write(archivo.getData());

			os.flush();
			os.close();				
			return null;
		} 
		catch(ServiceException ex){
			log.error("Error en RqnpArchivoController.descargarArchivo: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en RqnpArchivoController.descargarArchivo: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin - RqnpArchivoController.descargarArchivo");}
		}
		
	}
	
	/**
     * Carga el Archivo al Servidor y registra en el la BD(upload)
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView cargarArchivo(HttpServletRequest request, HttpServletResponse response){
		if (log.isDebugEnabled()){ log.debug("Inicio -  RqnpArchivoController.cargarArchivo");}
		String visor	= StringUtils.trim(request.getParameter("txtvisor"));
		Map<String, Object> params 			= new HashMap<String, Object>();
		Map<String, Object> mapRqnp 		= new HashMap<String, Object>();
		String num_archivo		="";
		List<Map<String, Object>> listaArchivos = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>>  listaTiposDocumentos = new ArrayList<Map<String,Object>>();
		String cod_req			="";
		String cod_reg_archivo	="";
		String user				="";
		String cod_bien			="";
		String tipoDocumento	="";
		String fileDescripcion	="";
		String path_url			="";
		String path_url2		="";
		String path_url_back	="";
		try{
			//Prepara directorio temporal para la carga de archivos
			File farchivo = new File("/data0");
			File fupload = new File("/data0/tempo");
			if (!farchivo.exists() ){ farchivo.mkdir();	}
			if (!fupload.exists() ){ fupload.mkdir(); }
			
			visor		= StringUtils.trim(request.getParameter("txtvisor"));
			num_archivo	= StringUtils.trim(request.getParameter("txtNumArchivo")); //NUM_ARCHIVO de Tabla REQUERIMIENTO_NO_PROG_BIENES
			cod_bien	= StringUtils.trim(request.getParameter("txtCodigoBien"));
			path_url	= StringUtils.trim(request.getParameter("path_url")); 
			path_url_back	= StringUtils.trim(request.getParameter("path_url_back2"));
			tipoDocumento	= StringUtils.trim(request.getParameter("txtTipoDocumento"));
			fileDescripcion	= StringUtils.trim(request.getParameter("txtFileDescripcion"));
			log.debug("path_url: " +path_url);
			log.debug("path_url2: " + path_url2);
			
			UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
			user = usuarioBean.getLogin();
			
			cod_req = StringUtils.trim(request.getParameter("txtCodigoRqnp"));
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile multipartFile = multipartRequest.getFile("file");
		
			String nombre_archivo = multipartFile.getOriginalFilename();
			File file =new File(uploadDir, nombre_archivo.trim());
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			fileOutputStream.close();
			multipartFile.transferTo(file);
			String bien="";
			
			params.put("file", file);
			params.put("cod_req", cod_req);
			params.put("user", user);
			params.put("num_archivo", num_archivo);
			params.put("tipoDocumento", tipoDocumento);
			params.put("fileDescripcion", fileDescripcion);
		
			cod_reg_archivo=registroArchivosService.registrarArchivo(params);
				
			if (num_archivo.equals("")){
				requerimientoNoProgramadoService.updateFile(cod_req,cod_bien, cod_reg_archivo, user);
			}
			
			RequerimientoNoProgramadoBean rqnp= requerimientoNoProgramadoService.recuperarRqnp(cod_req);
			//Seteando Cabecera
			mapRqnp.put("codigoRqnp", rqnp.getCodigoRqnp());
			mapRqnp.put("codigoReqNoProgramado", rqnp.getCodigoReqNoProgramado());
			mapRqnp.put("codigoResposanble", rqnp.getNumeroRegistro());
			mapRqnp.put("resposanble", rqnp.getNombreSolicitante());
			mapRqnp.put("codigoUuoo", rqnp.getUuoo());
			mapRqnp.put("codigoDependencia", rqnp.getCodigoDependencia());
			mapRqnp.put("anioProceso", rqnp.getAnioProceso());
			mapRqnp.put("dependencia", rqnp.getNombreUOSolicitante());
			mapRqnp.put("fechaRqnp",new SimpleDateFormat("dd/MM/yyyy HH:mm").format(rqnp.getFechaRqnp()));
			mapRqnp.put("motivoRqnp",rqnp.getMotivoSolicitud());
			mapRqnp.put("montoRqnp",  rqnp.getMontoRqnp().setScale(2, RoundingMode.HALF_UP) );
	
			String fechaenvio="";
	 		if (rqnp.getFechaEnvioSolicitud()!=null){
	 			fechaenvio = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(rqnp.getFechaEnvioSolicitud());
	 		}
	 		log.debug("FECHA ------>>>>>>" +fechaenvio);
	 		mapRqnp.put("fechaenvio", fechaenvio);
			mapRqnp.put("numeroArchivo", cod_reg_archivo);
			mapRqnp.put("codigoBien", cod_bien);
			
			for(Map<String, Object> obj: (List<Map<String, Object>>) rqnp.getListaBienes()){
				if(cod_bien.equals((String)obj.get("codigoBien"))){
					bien= (String)obj.get("desBien");
				}
			}
			
			mapRqnp.put("bien", cod_bien + "    "+bien);
			
			if ((cod_reg_archivo != null) && 
		        (!cod_reg_archivo.equals(""))) {
		        params.put("num_reg", cod_reg_archivo);
		        listaArchivos = (List)this.registroArchivosService.listarArchivos(params);
		      }
			//recuperando tipos de Archivos
			params.put("cod_app", "LOGISTICA");
			params.put("cod_mod", "RQNP");
			listaTiposDocumentos= (List<Map<String, Object>>) registroArchivosService.listarTipoArchivos(params);
			
		} catch (Throwable e) {
	      log.error("", e);
	    } finally {
	      if (log.isDebugEnabled()) log.debug("Fin -  RqnpArchivoController.cargarArchivo");
	    }
	    return new ModelAndView("formCargarArchivo").addObject("mapRqnp", mapRqnp)
	      .addObject("listaArchivos", listaArchivos).addObject("visor", visor).addObject("listaTiposDocumentos", listaTiposDocumentos)
	      .addObject("path_url", path_url).addObject("path_url_back", path_url_back);
  }
	
	
	/**
     * Carga el Archivo al Servidor y registra en el la BD(upload)
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView cargarArchivoBeneficiario(HttpServletRequest request, HttpServletResponse response){
		if (log.isDebugEnabled()){ log.debug("Inicio -  RqnpArchivoController.cargarArchivoBeneficiario");}
		Map<String, Object> respuesta 	= new HashMap<String, Object>();
		ModelAndView viewPage = null;
		boolean flagOK=true;
		boolean flagSalir=false;
		ArrayList<String> datos = new ArrayList();
		String mensaje="";
		List<Map<String, Object>> listaArchivos = new ArrayList<Map<String,Object>>();
		int fila = 0;
		List<TdBeneficiarioBean> listTdBeneficiarioBean = new ArrayList();
		
		try{
			//Prepara directorio temporal para la carga de archivos
			File farchivo = new File("/data0");
			File fupload = new File("/data0/tempo");
			if (!farchivo.exists() ){ farchivo.mkdir();	}
			if (!fupload.exists() ){ fupload.mkdir(); }
			
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile multipartFile = multipartRequest.getFile("file");
			
			String nombre_archivo = multipartFile.getOriginalFilename();
			File file =new File(uploadDir, nombre_archivo.trim());
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			fileOutputStream.close();
			multipartFile.transferTo(file);
			FileInputStream inputStream = new FileInputStream(file);
			
			/*
			boolean existe= file.exists();
			boolean directorioVacio=file.isDirectory();
			
			if(existe && !directorioVacio)
				file.delete(); //delete if file already exists.
			*/
			
			Workbook workbook=null;
			
				//String excelFilePath = nombre_archivo;

				if (file.toString().endsWith(".xls")) {
				   workbook = new HSSFWorkbook(inputStream); //Para extensiones .xls
				} else {
				   workbook = new XSSFWorkbook(inputStream); //Para extensiones .xlsx
				}
				
				//workbook = WorkbookFactory.create(inputStream);
				Sheet firstSheet = workbook.getSheetAt(0);
				Iterator<Row> iterator = firstSheet.iterator();
				
				log.debug("Antes del bucle de Iterator: iterator.hasNext()");
				
				while (iterator.hasNext() && !flagSalir) {
					Row nextRow = iterator.next();
					Iterator<Cell> cellIterator = nextRow.cellIterator();
					TdBeneficiarioBean tdBeneficiarioBean = new TdBeneficiarioBean();
					if(fila>0 && !flagSalir){
						String uuoo=null;
						double cant=0;
						String descUuoo=null;
						
						while (cellIterator.hasNext() && !flagSalir) {
							Cell nextCell = cellIterator.next();
							int columnIndex = nextCell.getColumnIndex();
							switch (columnIndex) {
								case 0:
									uuoo=(String) getCellValue(nextCell);
									//tdBeneficiarioBean.setUnidadOrganizacional(uuoo);
									if(uuoo!=null){
										if(uuoo.equals("")){
											mensaje="Verifique que el archivo no tenga unidades beneficiarias vacias, revise el excel y vuelva a intentar.";
											listTdBeneficiarioBean.clear();
											flagSalir=true;
										}else{
											if(!this.isDuplicado(datos,uuoo)){
												tdBeneficiarioBean.setUnidadOrganizacional(uuoo);
												datos.add(uuoo);
											}else{
												mensaje="Existen unidades duplicadas en el archivo, revise el excel y vuelva a intentar.";
												listTdBeneficiarioBean.clear();
												flagSalir=true;
											}
										}
										
									}else{
										mensaje="Verifique que el archivo no tenga unidades beneficiarias vacias, revise el excel y vuelva a intentar." ;
										listTdBeneficiarioBean.clear();
										flagSalir=true;
									}
									break;
								case 1:
									descUuoo=(String) getCellValue(nextCell);
									//tdBeneficiarioBean.setDescDepeTde(descUuoo);
									if(descUuoo!=null){
										if(descUuoo.equals("")){
											mensaje="Verifique que el archivo no tenga unidades beneficiarias vacias, revise el excel y vuelva a intentar.";
											listTdBeneficiarioBean.clear();
											flagSalir=true;
											
										}else{
											if(!this.isDuplicado(datos,descUuoo)){ 
												tdBeneficiarioBean.setDescDepeTde(descUuoo);
												datos.add(descUuoo);
											}else{
												mensaje="Existen unidades organizacionales duplicadas en el archivo, revise el excel y vuelva a intentar.";
												listTdBeneficiarioBean.clear();
												flagSalir=true;
											}
											
										}
									}else{
										mensaje="Verifique que el archivo no tenga unidades beneficiarias vacias, revise el excel y vuelva a intentar.";
										listTdBeneficiarioBean.clear();
										flagSalir=true;
									}
									break;
								case 2:
									String valor="";
									valor = (String.valueOf(getCellValue(nextCell)));
									
									if(this.isNumeric(valor)){
										cant=Double.parseDouble(valor);
										if(cant>=0){
											tdBeneficiarioBean.setCantidad(cant);
										}
									}else{
										flagOK=false;
									}
									break;
							}
							
							if(flagSalir){
								break; //salir del bucle while
							}
						}
						
						if(uuoo!=null && cant>0){
							Map<String, Object> param = new HashMap<String, Object>();
							param.put("uuoo", uuoo);
							String secuFuncSfu= (String) requerimientoNoProgramadoService.obtenerSecuFuncUuoo(param).get("SECU_FUNC_SFU");
							String metaSiaf= (String) requerimientoNoProgramadoService.obtenerSecuFuncUuoo(param).get("META_SIAF");
							if(secuFuncSfu !=null){
								tdBeneficiarioBean.setSecuFuncSfu(secuFuncSfu);
								tdBeneficiarioBean.setMetaSiaf(metaSiaf);
								listTdBeneficiarioBean.add(tdBeneficiarioBean);
							}else{
								log.debug("secuFuncSfu de: "+ uuoo +" es NULL");
							}
						}
					}
					//log.debug("Numero fila: "+fila);
					fila+=1;
				}
				
				//workbook.close();
				inputStream.close();
			
			//Metodo que graba a una tabla REG_ADQUISICIONES_BIENES_TEMP temporales los datos cargados
			//String okRegistrarTdBeneficiarios=registroArchivosService.registrarTdBeneficiarios(listTdBeneficiarioBean);
				
		} catch (Throwable e) {
			e.printStackTrace();
	    } finally {
	    	if (log.isDebugEnabled()) log.debug("Fin -  RqnpArchivoController.cargarArchivoBeneficiario");
	    }
		
		
		if(flagSalir){
			respuesta.put("okRegistrarTdBeneficiarios", "VACIO");
			respuesta.put("msg", mensaje);
		}else{
			if(!listTdBeneficiarioBean.isEmpty()){
				if(flagOK){
					respuesta.put("okRegistrarTdBeneficiarios", "OK");
					respuesta.put("msg", "OK");
				}else{
					respuesta.put("okRegistrarTdBeneficiarios", "CONERROR");
					respuesta.put("msg", "S\u00F3lo se cargaron datos v\u00E1lidos.");
				}
				
			}else{
				respuesta.put("okRegistrarTdBeneficiarios", "VACIO");
				respuesta.put("msg", "El archivo excel importado se encuentra vac\u00EDo");
			}
		}
		
		
		
		
		respuesta.put("listTdBeneficiarioBean", listTdBeneficiarioBean);
		viewPage = new ModelAndView(getJsonView(), respuesta);
		return viewPage;
  }
	
	private static boolean isNumeric(String cadena){
		try {
			//Integer.parseInt(cadena);
			Double.parseDouble(cadena);
			return true;
		} catch (NumberFormatException nfe){
			return false;
		}
	}
	
	private static boolean isDuplicado(ArrayList<String> datos, String dato){
		boolean rpta=false;
		for(String a: datos){
			if(dato==a){
				rpta= true;
				break;
			}
		}
		return rpta;
	}
	
	private Object getCellValue(Cell cell) {
	    switch (cell.getCellType()) {
	    case Cell.CELL_TYPE_STRING:
	        return cell.getStringCellValue();
	 
	    case Cell.CELL_TYPE_BOOLEAN:
	        return cell.getBooleanCellValue();
	 
	    case Cell.CELL_TYPE_NUMERIC:
	        return cell.getNumericCellValue();
	    }
	 
	    return null;
	}
	
	/**
     * Metodo que valida el tamaño del archivo que sera adjuntado al requerimiento
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	
	  public ModelAndView validarTamanioArchivoJson(HttpServletRequest request, HttpServletResponse response)
	  {
	    if (log.isDebugEnabled()) log.debug("Inicio -  RqnpArchivoController.validarTamanioArchivoJson");
	    Map data = new HashMap();
	    try
	    {
	      File farchivo = new File("/data0");
	      File fupload = new File("/data0/tempo");
	      if (!farchivo.exists()) farchivo.mkdir();
	      if (!fupload.exists()) fupload.mkdir();

	      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
	      MultipartFile multipartFile = multipartRequest.getFile("file");

	      log.info("El archivo (size) info: " + multipartFile.getSize());
	      log.debug("El archivo (size) debug: " + multipartFile.getSize());
	      
	      if (multipartFile.getSize() > 1048576L) {
	        log.debug("Archivo INCORRECTO - SI supera el tamaño permitido:>>>");
	        data.put("mensaje", "El archivo supera el tamaño permitido (1MB). Adjuntar un archivo de menor tamaño. ");
	        data.put("flag", "1");
	      } else {
	        log.debug("Archivo CORRECTO - NO supera el tamaño permitido:>>>");
	        data.put("mensaje", "Archivo CORRECTO - NO supera el tamaño permitido:>>>");
	        data.put("flag", "0");
	      }
	      return new ModelAndView(this.jsonView, "data", data);
	    }
	    catch (ServiceException ex) {
	      log.error("Error en RqnpArchivoController.validarTamanioArchivoJson: " + ex.getMessage());
	      throw new ServiceException(this, ex);
	    } catch (Exception ex) {
	      log.error("Error en RqnpArchivoController.validarTamanioArchivoJson: " + ex.getMessage(), ex);
	      throw new ServiceException(this, ex);
	    } finally {
	      if (log.isDebugEnabled()) log.debug("Fin - RqnpArchivoController.validarTamanioArchivoJson");
	    }
	  }
	  
	  
	/**
     * Carga el Archivo al Servidor y registra en el la BD(upload)
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView cargarArchivoSolicitud(HttpServletRequest request, HttpServletResponse response){
		if (log.isDebugEnabled()){ log.debug("Inicio -  RqnpArchivoController.cargarArchivoSolicitud");}
		String visor	= StringUtils.trim(request.getParameter("txtvisor"));
		Map<String, Object> params 			= new HashMap<String, Object>();
		Map<String, Object> mapRqnp 		= new HashMap<String, Object>();
		
		String num_archivo		="";
		List<Map<String, Object>> listaArchivos = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> listaTiposDocumentos = new ArrayList<Map<String,Object>>();
		String cod_sol			="";
		String cod_req			="";
		String cod_reg_archivo	="";
		String user				="";

		String tipoDocumento	="";
		String fileDescripcion	="";
		String path_url			="";
		String path_url_back	="";
		
		try{
			File farchivo = new File("/data0");
			File fupload = new File("/data0/tempo");
			if (!farchivo.exists() ){ farchivo.mkdir(); }
			if (!fupload.exists() ){ fupload.mkdir(); }
			
			cod_sol	= StringUtils.trim(request.getParameter("txtCodSol"));
			visor		= StringUtils.trim(request.getParameter("txtvisor"));
			num_archivo	= StringUtils.trim(request.getParameter("txtNumArchivo2"));
			path_url	= StringUtils.trim(request.getParameter("path_url2"));
			path_url_back	= StringUtils.trim(request.getParameter("path_url_back2"));
			cod_req 	= StringUtils.trim(request.getParameter("txtCodigoRqnp2"));
			tipoDocumento	= StringUtils.trim(request.getParameter("txtTipoDocumento"));
			fileDescripcion	= StringUtils.trim(request.getParameter("txtFileDescripcion"));
			
			log.debug("cod_sol:"+ cod_sol);
			log.debug("path_url " +path_url);
			
			UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
			user = usuarioBean.getLogin();
			
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile multipartFile = multipartRequest.getFile("file");
			String nombre_archivo = multipartFile.getOriginalFilename();
			File file =new File(uploadDir, nombre_archivo.trim());
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			fileOutputStream.close();
			multipartFile.transferTo(file);
		
			params.put("file", file);
			params.put("cod_sol", cod_sol);
			params.put("user", user);
			params.put("num_archivo", num_archivo);
			params.put("tipoDocumento", tipoDocumento);
			params.put("fileDescripcion", fileDescripcion);
			
			cod_reg_archivo=registroArchivosService.registrarArchivoSolicitud(params);
			
			log.info("(RqnpArchivoController.cargarArchivoSolicitud) cod_reg_archivo: "+cod_reg_archivo);
			
			if (num_archivo.equals("")){
				
				solicitudBienService.updateFile(cod_sol, cod_reg_archivo, user);
			}
			mapRqnp = (Map<String, Object>) solicitudBienService.recuperarSolictudBien(cod_sol);
		
			//Recuperando Archivos Adjuntos//
			 if(cod_reg_archivo!= null){
				 if (!cod_reg_archivo.equals("")){
					 params.put("num_reg", cod_reg_archivo);
					 listaArchivos= (List<Map<String, Object>>) registroArchivosService.listarArchivos(params);
				 }
			 }
			///recuperando tipos de Archivos
			params.put("cod_app", "LOGISTICA");
			params.put("cod_mod", "RQNP");
			listaTiposDocumentos= (List<Map<String, Object>>) registroArchivosService.listarTipoArchivos(params);
		}catch(Throwable e){
			log.error("", e);
		} finally {
            if (log.isDebugEnabled()) {log.debug("Fin -  RqnpArchivoController.cargarArchivoSolicitud");}
		}
		 return new ModelAndView("formCargarArchivoSolicitud").addObject("mapRqnp", mapRqnp)
		.addObject("listaArchivos", listaArchivos).addObject("visor", visor).addObject("listaTiposDocumentos",listaTiposDocumentos)
		.addObject("path_url",path_url ).addObject("txtCodigoRqnp", cod_req).addObject("path_url_back", path_url_back);
	}
	
	
	/**
     * Elimina Archivos Fisico.
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView eliminarArchivoFisico(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("Inicio -  RqnpArchivoController.eliminarArchivoFisico");}
		Map<String, Object> mapRqnp = new HashMap<String, Object>();
		Map<String, Object> params 	= new HashMap<String, Object>();
		String num_archivo="";
		String user="";
		List<Map<String, Object>> listaArchivos = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>>  listaTiposDocumentos = new ArrayList<Map<String,Object>>();
		String cod_req	="";
		String cod_bien	="";
		String visor	="";
		String bien		="";
		String sec_archivo="";
		String path_url="";
		String path_url2="";
		try {
			UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
			user = usuarioBean.getLogin();
			
			cod_req 	= StringUtils.trim(request.getParameter("txtCodigoRqnp"));
			num_archivo	= StringUtils.trim(request.getParameter("txtNumArchivo"));
			cod_bien	= StringUtils.trim(request.getParameter("txtCodigoBien"));
			sec_archivo	= StringUtils.trim(request.getParameter("txtSecArchivo"));
			path_url	= StringUtils.trim(request.getParameter("path_url2"));
			//path_url2	= StringUtils.trim(request.getParameter("path_url2"));
			visor	= StringUtils.trim(request.getParameter("txtvisor"));
			
			RequerimientoNoProgramadoBean rqnp= requerimientoNoProgramadoService.recuperarRqnp(cod_req);
			//Eliminando Archivo Fisico
			 params.put("num_reg", num_archivo);
			 params.put("sec_arc", sec_archivo);
			 params.put("cod_req", cod_req);
			 params.put("user", user);
			registroArchivosService.eliminarArchivoFisico(params);
			//Seteando Cabecera
			 mapRqnp.put("codigoRqnp", rqnp.getCodigoRqnp());
			 mapRqnp.put("codigoReqNoProgramado", rqnp.getCodigoReqNoProgramado());
			 mapRqnp.put("codigoResposanble", rqnp.getNumeroRegistro());
			 mapRqnp.put("resposanble", rqnp.getNombreSolicitante());
			 mapRqnp.put("codigoUuoo", rqnp.getUuoo());
			 mapRqnp.put("codigoDependencia", rqnp.getCodigoDependencia());
			 mapRqnp.put("anioProceso", rqnp.getAnioProceso());
			 mapRqnp.put("dependencia", rqnp.getNombreUOSolicitante());
			 mapRqnp.put("fechaRqnp",new SimpleDateFormat("dd/MM/yyyy HH:mm").format(rqnp.getFechaRqnp()));
			 mapRqnp.put("motivoRqnp",rqnp.getMotivoSolicitud());
			 mapRqnp.put("montoRqnp",  rqnp.getMontoRqnp().setScale(2, RoundingMode.HALF_UP) );

			 String fechaenvio="";
 			 if (rqnp.getFechaEnvioSolicitud()!=null){
 				 fechaenvio = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(rqnp.getFechaEnvioSolicitud());
 			 }
 			 log.debug("FECHA ------>>>>>>" +fechaenvio);
 			 mapRqnp.put("fechaenvio", fechaenvio);
			 
			 mapRqnp.put("numeroArchivo", num_archivo);
			 mapRqnp.put("codigoBien", cod_bien);
			 
			 for(Map<String, Object> obj: (List<Map<String, Object>>) rqnp.getListaBienes()){
				 if(cod_bien.equals((String)obj.get("codigoBien"))){
					 bien= (String)obj.get("desBien");
				 }
			 }
			 
			 mapRqnp.put("bien", cod_bien + "    "+bien);
			 //Recuperando Archivos Adjuntos////////////////////////////////////////////////////
			 if(num_archivo!= null){
				 if (!num_archivo.equals("")){
					 params.put("num_reg", num_archivo);
					 listaArchivos= (List<Map<String, Object>>) registroArchivosService.listarArchivos(params);
				 }
			 }
			 
			///recuperando tipos de Archivos
			 params.put("cod_app", "LOGISTICA");
			 params.put("cod_mod", "RQNP");
			 listaTiposDocumentos= (List<Map<String, Object>>) registroArchivosService.listarTipoArchivos(params);
			 
			return new ModelAndView("formCargarArchivo").addObject("mapRqnp", mapRqnp)
			.addObject("visor", visor).addObject("listaArchivos", listaArchivos).addObject("listaTiposDocumentos",listaTiposDocumentos)
			.addObject("path_url", path_url);
			
		} 
		catch(ServiceException ex){
			log.error("Error en  RqnpArchivoController.eliminarArchivoFisico: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en  RqnpArchivoController.eliminarArchivoFisico: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin -  RqnpArchivoController.eliminarArchivoFisico");}
		}
	}

	
	
	/**
     * Elimina Archivos Fisico.
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView eliminarArchivoFisicoSolicitud(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("Inicio -  RqnpArchivoController.eliminarArchivoFisicoSolicitud");}
		Map<String, Object> mapRqnp = new HashMap<String, Object>();
		Map<String, Object> params 	= new HashMap<String, Object>();
		String num_archivo="";
		String user="";
		List<Map<String, Object>> listaArchivos = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>>  listaTiposDocumentos = new ArrayList<Map<String,Object>>();
		String cod_sol	="";
		String visor	="";
		String cod_req	="";
	
		String sec_archivo="";
		String path_url;
		String path_url_back;
		
		try {
			UsuarioBean usuarioBean = (UsuarioBean) WebUtils.getSessionAttribute(request, "usuarioBean");
			user = usuarioBean.getLogin();
			
			cod_sol	= StringUtils.trim(request.getParameter("txtCodSol2"));
			num_archivo	= StringUtils.trim(request.getParameter("txtNumArchivo"));
		
			sec_archivo	= StringUtils.trim(request.getParameter("txtSecArchivo"));
			path_url	= StringUtils.trim(request.getParameter("path_url"));
			cod_req 	= StringUtils.trim(request.getParameter("txtCodigoRqnp"));
			visor	= StringUtils.trim(request.getParameter("txtvisor"));
			path_url_back= StringUtils.trim(request.getParameter("path_url_back"));
			mapRqnp = (Map<String, Object>) solicitudBienService.recuperarSolictudBien(cod_sol);
			
			log.debug("num_archivo " +num_archivo +"  " + "sec_archivo " +sec_archivo);
			//Eliminando Archivo Fisico
			 params.put("num_reg", num_archivo);
			 params.put("sec_arc", sec_archivo);
			// params.put("cod_req", cod_req);
			 params.put("user", user);
			registroArchivosService.eliminarArchivoFisico(params);
			//Recuperando Archivos Adjuntos////////////////////////////////////////////////////
			 if(num_archivo!= null){
				 if (!num_archivo.equals("")){
					 params.put("num_reg", num_archivo);
					 listaArchivos= (List<Map<String, Object>>) registroArchivosService.listarArchivos(params);
				 }
			 }
			 
			///recuperando tipos de Archivos
			 params.put("cod_app", "LOGISTICA");
			 params.put("cod_mod", "RQNP");
			 listaTiposDocumentos= (List<Map<String, Object>>) registroArchivosService.listarTipoArchivos(params);
			 
			return new ModelAndView("formCargarArchivoSolicitud").addObject("mapRqnp", mapRqnp)
			.addObject("visor", visor).addObject("listaArchivos", listaArchivos).addObject("listaTiposDocumentos",listaTiposDocumentos)
			.addObject("path_url", path_url).addObject("txtCodigoRqnp", cod_req).addObject("path_url_back", path_url_back);
			
		} 
		catch(ServiceException ex){
			log.error("Error en  RqnpArchivoController.eliminarArchivoFisico: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en  RqnpArchivoController.eliminarArchivoFisicoSolicitud: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin -  RqnpArchivoController.eliminarArchivoFisicoSolicitud");}
		}
	}


	
	/**
     * Carga la P�gina inicial para descargar el archivo adjunto del item
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView iniciarDescargaArchivo(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("Inicio -  RqnpArchivoController.iniciarDescargaArchivo");}
		Map<String, Object> mapRqnp = new HashMap<String, Object>();
		Map<String, Object> params 	= new HashMap<String, Object>();
		String num_archivo="";
		
		List<Map<String, Object>> listaArchivos = new ArrayList<Map<String,Object>>();
		
		String cod_req	="";
		String cod_bien	="";
		String visor	="";
		String bien		="";
		String path_url		="";
		try {
			MaestroPersonalBean  maestroPersonal=  (MaestroPersonalBean) WebUtils.getSessionAttribute(request, "maestroPersonalBean");
			if (log.isDebugEnabled()){ log.debug(maestroPersonal.getNombre_completo());}
			
			cod_req 	= StringUtils.trim(request.getParameter("txtCodigoRqnp"));
			num_archivo	= StringUtils.trim(request.getParameter("txtNum_reg"));
			cod_bien	= StringUtils.trim(request.getParameter("txtCodigoBien"));
			
			visor	= StringUtils.trim(request.getParameter("txtvisor"));
			
			RequerimientoNoProgramadoBean rqnp= requerimientoNoProgramadoService.recuperarRqnp(cod_req);
		
			//Seteando Cabecera
			 mapRqnp.put("codigoRqnp", rqnp.getCodigoRqnp());
			 mapRqnp.put("codigoReqNoProgramado", rqnp.getCodigoReqNoProgramado());
			 mapRqnp.put("codigoResposanble", rqnp.getNumeroRegistro());
			 mapRqnp.put("resposanble", rqnp.getNombreSolicitante());
			 mapRqnp.put("codigoUuoo", rqnp.getUuoo());
			 mapRqnp.put("codigoDependencia", rqnp.getCodigoDependencia());
			 mapRqnp.put("anioProceso", rqnp.getAnioProceso());
			 
			 mapRqnp.put("dependencia", rqnp.getNombreUOSolicitante());
			 
			 String newDate = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(rqnp.getFechaRqnp());
				log.debug("FECHA ------>>>>>>" +newDate);
				 mapRqnp.put("fechaRqnp",newDate);
			 
			 mapRqnp.put("motivoRqnp",rqnp.getMotivoSolicitud());
			 mapRqnp.put("montoRqnp",  rqnp.getMontoRqnp().setScale(2, RoundingMode.HALF_UP) );
			 
			 String fechaenvio="";
			 	if (rqnp.getFechaEnvioSolicitud()!=null){
			 		fechaenvio = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(rqnp.getFechaEnvioSolicitud());
			 		}
			 	log.debug("FECHA ------>>>>>>" +fechaenvio);
			 mapRqnp.put("fechaenvio", fechaenvio);
			 
			 mapRqnp.put("numeroArchivo", num_archivo);
			 mapRqnp.put("codigoBien", cod_bien);
			 path_url	= StringUtils.trim(request.getParameter("path_url"));
			 for(Map<String, Object> obj: (List<Map<String, Object>>) rqnp.getListaBienes()){
				 if(cod_bien.equals((String)obj.get("codigoBien"))){
					 bien= (String)obj.get("desBien");
				 }
			 }
			 
			 mapRqnp.put("bien", cod_bien + "    "+bien);
			 //Recuperando Archivos Adjuntos////////////////////////////////////////////////////
			 if(num_archivo!= null){
				 if (!num_archivo.equals("")){
					 params.put("num_reg", num_archivo);
					 listaArchivos= (List<Map<String, Object>>) registroArchivosService.listarArchivos(params);
				 }
			 }
			 
			return new ModelAndView("formCargarArchivo").addObject("mapRqnp", mapRqnp)
			.addObject("visor", visor).addObject("listaArchivos", listaArchivos).addObject("path_url", path_url);
			
		} 
		catch(ServiceException ex){
			log.error("Error en  RqnpArchivoController.iniciarDescargaArchivo: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en  RqnpArchivoController.iniciarDescargaArchivo: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin -  RqnpArchivoController.iniciarDescargaArchivo");}
		}
	}


	
	/**
     * Carga la P�gina inicial para descargar el archivo adjunto del item
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView iniciarDescargaArchivoSolicitud(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("Inicio -  RqnpArchivoController.iniciarDescargaArchivoSolicitud");}
		Map<String, Object> mapRqnp = new HashMap<String, Object>();
		Map<String, Object> params 	= new HashMap<String, Object>();
		String num_archivo="";
		
		List<Map<String, Object>> listaArchivos = new ArrayList<Map<String,Object>>();
		
		String cod_sol	="";
		String cod_req	="";
		String visor	="";
		String path_url	="";
		String path_url_back	="";
		try {
			MaestroPersonalBean  maestroPersonal=  (MaestroPersonalBean) WebUtils.getSessionAttribute(request, "maestroPersonalBean");
			if (log.isDebugEnabled()){ log.debug(maestroPersonal.getNombre_completo());}
			
			cod_sol	= StringUtils.trim(request.getParameter("txtCodSol2"));
			num_archivo	= StringUtils.trim(request.getParameter("txtNum_reg"));
			cod_req 	= StringUtils.trim(request.getParameter("txtCodigoRqnp"));
			path_url	= StringUtils.trim(request.getParameter("path_url"));
			visor	= StringUtils.trim(request.getParameter("txtvisor"));
			path_url_back= StringUtils.trim(request.getParameter("path_url_back"));
		
			mapRqnp = (Map<String, Object>) solicitudBienService.recuperarSolictudBien(cod_sol);
			 //Recuperando Archivos Adjuntos////////////////////////////////////////////////////
			 if(num_archivo!= null){
				 if (!num_archivo.equals("")){
					 params.put("num_reg", num_archivo);
					 listaArchivos= (List<Map<String, Object>>) registroArchivosService.listarArchivos(params);
				 }
			 }
			 
			return new ModelAndView("formCargarArchivoSolicitud").addObject("mapRqnp", mapRqnp)
			.addObject("visor", visor).addObject("listaArchivos", listaArchivos).addObject("txtCodigoRqnp", cod_req).addObject("path_url", path_url).addObject("path_url_back", path_url_back);
			
		} 
		catch(ServiceException ex){
			log.error("Error en  RqnpArchivoController.iniciarDescargaArchivoSolicitud: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en  RqnpArchivoController.iniciarDescargaArchivoSolicitud: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin -  RqnpArchivoController.iniciarDescargaArchivoSolicitud");}
		}
	}


	/**
     * Carga la P�gina inicial para descargar el archivo adjunto del item
     * @param  request
     * @param  response 
     * @return ModelAndView 
     **/
	public ModelAndView iniciarDescargaArchivoJI(HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()){ log.debug("Inicio -  RqnpArchivoController.iniciarDescargaArchivoJI");}
		Map<String, Object> mapRqnp = new HashMap<String, Object>();
		Map<String, Object> params 	= new HashMap<String, Object>();
		String num_archivo="";
		
		List<Map<String, Object>> listaArchivos = new ArrayList<Map<String,Object>>();
		String cod_req	="";
		String cod_bien	="";
		String visor	="";
		String bien		="";
		try {
			
			cod_req 	= StringUtils.trim(request.getParameter("txtCodigoRqnp"));
			num_archivo	= StringUtils.trim(request.getParameter("txtNum_reg"));
			cod_bien	= StringUtils.trim(request.getParameter("txtCodigobien"));
			
			visor	= StringUtils.trim(request.getParameter("txtvisor"));
			
			RequerimientoNoProgramadoBean rqnp= requerimientoNoProgramadoService.recuperarRqnp(cod_req);
		
			//Seteando Cabecera
			 mapRqnp.put("codigoRqnp", rqnp.getCodigoRqnp());
			 mapRqnp.put("codigoReqNoProgramado", rqnp.getCodigoReqNoProgramado());
			 mapRqnp.put("codigoResposanble", rqnp.getNumeroRegistro());
			 mapRqnp.put("resposanble", rqnp.getNombreSolicitante());
			 mapRqnp.put("codigoUuoo", rqnp.getUuoo());
			 mapRqnp.put("codigoDependencia", rqnp.getCodigoDependencia());
			 mapRqnp.put("anioProceso", rqnp.getAnioProceso());
			 
			 mapRqnp.put("dependencia", rqnp.getNombreUOSolicitante());
			 
			 String newDate = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(rqnp.getFechaRqnp());
				log.debug("FECHA ------>>>>>>" +newDate);
				 mapRqnp.put("fechaRqnp",newDate);
			 mapRqnp.put("motivoRqnp",rqnp.getMotivoSolicitud());
			 mapRqnp.put("montoRqnp",  rqnp.getMontoRqnp().setScale(2, RoundingMode.HALF_UP) );
			 
			 String fechaenvio="";
			 	 if (rqnp.getFechaEnvioSolicitud()!=null){
			 		 fechaenvio = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(rqnp.getFechaEnvioSolicitud());
	 			 }
			 log.debug("FECHA ------>>>>>>" +fechaenvio);
			 mapRqnp.put("fechaenvio", fechaenvio);
			 			 
			 mapRqnp.put("numeroArchivo", num_archivo);
			 mapRqnp.put("codigoBien", cod_bien);
			 
			 for(Map<String, Object> obj: (List<Map<String, Object>>) rqnp.getListaBienes()){
				 if(cod_bien.equals((String)obj.get("codigoBien"))){
					 bien= (String)obj.get("desBien");
				 }
			 }
			 
			 mapRqnp.put("bien", cod_bien + "    "+bien);
			 //Recuperando Archivos Adjuntos////////////////////////////////////////////////////
			 if(num_archivo!= null){
				 if (!num_archivo.equals("")){
					 params.put("num_reg", num_archivo);
					 listaArchivos= (List<Map<String, Object>>) registroArchivosService.listarArchivos(params);
				 }
			 }
			 
			return new ModelAndView("formDescargarArchivo").addObject("mapRqnp", mapRqnp)
			.addObject("visor", visor).addObject("listaArchivos", listaArchivos);
			
		} 
		catch(ServiceException ex){
			log.error("Error en  RqnpArchivoController.iniciarDescargaArchivoJI: " + ex.getMessage());
			throw new ServiceException(this, ex);
		}
		catch(Exception ex){
			log.error("Error en  RqnpArchivoController.iniciarDescargaArchivoJI: " + ex.getMessage(), ex);
			throw new ServiceException(this, ex);		   
		}
		finally{
			if (log.isDebugEnabled()){ log.debug("Fin -  RqnpArchivoController.iniciarDescargaArchivoJI");}
		}
	}
	
	public ReloadableResourceBundleMessageSource getMessageSource() {
		return messageSource;
	}
	public void setMessageSource(ReloadableResourceBundleMessageSource messageSource) {
		this.messageSource = messageSource;
	}
	public String getUploadDir() {
		return uploadDir;
	}
	public void setUploadDir(String uploadDir) {
		this.uploadDir = uploadDir;
	}
	public RegistroArchivosService getRegistroArchivosService() {
		return registroArchivosService;
	}
	public void setRegistroArchivosService(
			RegistroArchivosService registroArchivosService) {
		this.registroArchivosService = registroArchivosService;
	}
	public RequerimientoNoProgramadoService getRequerimientoNoProgramadoService() {
		return requerimientoNoProgramadoService;
	}
	public void setRequerimientoNoProgramadoService(
			RequerimientoNoProgramadoService requerimientoNoProgramadoService) {
		this.requerimientoNoProgramadoService = requerimientoNoProgramadoService;
	}
	public SolicitudBienService getSolicitudBienService() {
		return solicitudBienService;
	}
	public void setSolicitudBienService(SolicitudBienService solicitudBienService) {
		this.solicitudBienService = solicitudBienService;
	}
}
