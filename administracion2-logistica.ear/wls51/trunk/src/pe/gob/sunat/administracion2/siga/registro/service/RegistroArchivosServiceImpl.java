package pe.gob.sunat.administracion2.siga.registro.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jndi.JndiTemplate;

import pe.gob.sunat.administracion2.siga.registro.model.dao.SysRegistroArchivosDao;
import pe.gob.sunat.administracion2.siga.registro.model.dao.SysRegistroArchivosFisicoDao;
import pe.gob.sunat.administracion2.siga.registro.model.dao.TdBeneficiariosDao;
import pe.gob.sunat.administracion2.siga.registro.model.domain.RegistroArchivosBean;
import pe.gob.sunat.administracion2.siga.registro.model.domain.RegistroArchivosFisicoBean;
import pe.gob.sunat.administracion2.siga.registro.model.domain.TdBeneficiarioBean;
import pe.gob.sunat.framework.spring.util.exception.ServiceException;
import pe.gob.sunat.recurso2.administracion.siga.parametro.model.bean.T01ParametroBean;
import pe.gob.sunat.recurso2.administracion.siga.parametro.model.dao.T01ParametroDAO;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.dao.AccesoDAO;

public class RegistroArchivosServiceImpl implements RegistroArchivosService {

	private SysRegistroArchivosDao 			sysRegistroArchivosDao;
	private SysRegistroArchivosFisicoDao 	sysRegistroArchivosFisicoDao;
	private T01ParametroDAO					t01ParametroDao;
	private AccesoDAO						accesoDao;
	private TdBeneficiariosDao				tdBeneficiariosDao;
	
	private static final Log log = LogFactory.getLog(RegistroArchivosServiceImpl.class);
	
	public RegistroArchivosServiceImpl(){
		
	}
	
	/**
     * Registrar Unidades Beneficiarias
     * @param list<TdBeneficiarioBean>
     * @return Collection - lista de Archivos TdBeneficiarioBean
     */ 
	@Override
	public String registrarTdBeneficiarios(List<TdBeneficiarioBean> listTdBeneficiarioBean) {
	if (log.isDebugEnabled()) log.debug("Inicio - RegistroArchivosServiceImpl.registrarTdBeneficiarios");
	Map<String, Object> parm 	= new HashMap<String, Object>();
		try {
			//recuperando Lista de dependencias beneficiarias
			for ( TdBeneficiarioBean tdBeneficiarioBean :listTdBeneficiarioBean){
				String idBeneficiarios =tdBeneficiariosDao.obtenerSecuenciaBeneficiarios(parm);
				log.debug("Numero de secuencia: "+idBeneficiarios);
				tdBeneficiarioBean.setIdBeneficiarios(new BigDecimal(idBeneficiarios));
				tdBeneficiarioBean.setCodiRegiAdq(idBeneficiarios);
				tdBeneficiarioBean.setNumeItemCon(new BigDecimal(idBeneficiarios));
				tdBeneficiariosDao.insertarTdBeneficiarios(tdBeneficiarioBean);
			}
		}catch (Exception ex) {
				log.error("Error en RegistroArchivosServiceImpl.registrarTdBeneficiarios: " + ex.getMessage(), ex);
				throw new ServiceException(this, ex);
		}finally {
			if (log.isDebugEnabled()) log.debug("Fin - RegistroArchivosServiceImpl.registrarTdBeneficiarios");
		}
		
		return "1";
	}
	/**
     * Registrar Archivo
     * @param Map<String, Object> - params
     * @return String - Código de registro de Archivo
     */ 
	
	@Override
	public String registrarArchivo(Map<String, Object> params) {
		if (log.isDebugEnabled()) log.debug("Inicio - RegistroArchivosServiceImpl.RegistrarArchivo");
		
		String num_reg="";
		String 	cod_req =	(String) params.get ("cod_req");
		String 	user 	=	(String) params.get ("user");
		File 	file 	= 	(File) params.get("file");
		String num_archivo = (String) params.get ("num_archivo");
		String tipoDocumento = (String) params.get ("tipoDocumento");
		String fileDescripcion = (String) params.get ("fileDescripcion");
		
		Long sec_reg=new Long(0);
		 //
		try {
				
			//recuperando extensión
			String cadena= file.getName();
			String extension="";
			StringTokenizer token = new StringTokenizer(cadena,".");
			do{
				extension=token.nextToken();
			}while(token.hasMoreTokens());
			 
			DataSource dataSource = obtenerDataSource("sig", false);
			log.debug(" num_archivo >>>>>>>>>>>>>>"+ num_archivo+"<<<<");
				if (num_archivo.equals("") ){
					num_reg=sysRegistroArchivosDao.obtenerContador();
					log.debug("num_reg (secuencia):" + num_reg);
					log.debug("cod_req: " + cod_req);
					
					RegistroArchivosBean regArchivo = new RegistroArchivosBean();
					
					regArchivo.setNum_reg(num_reg);
					regArchivo.setAplicacion("LOGISTICA");
					regArchivo.setModulo("RQNP");
					regArchivo.setRegistro(cod_req);
					regArchivo.setDescripcion("Adjuntos del Requerimiento No Programado");
					
					accesoDao.setUsuarioAcceso(dataSource,"USER", user);
					sysRegistroArchivosDao.insertar(dataSource, regArchivo);
					accesoDao.setUsuarioAccesoNull(dataSource,"USER");
				}else{
					num_reg= num_archivo;
				}
			
			params.put("num_reg", num_reg);
			
			sec_reg = sysRegistroArchivosFisicoDao.obtenerContador(params);
			//sec_reg ++;--Se cambiao por secuenciador
			if (log.isDebugEnabled()) log.debug("num_reg" + sec_reg);
			
			RegistroArchivosFisicoBean archivoFisico = new RegistroArchivosFisicoBean();
			archivoFisico.setData( getBytesFromFile(file));
			archivoFisico.setDescrip_reg(fileDescripcion);
			archivoFisico.setFile_ext(extension.toUpperCase().substring(0,3));
			archivoFisico.setFile_name(file.getName() );
			archivoFisico.setFlag_imagen("N");
			archivoFisico.setFlag_security("N");
			archivoFisico.setFuente_reg(tipoDocumento);
			archivoFisico.setNom_object(file.getName());
			archivoFisico.setNum_reg(num_reg);
	
			archivoFisico.setSize( new Long (file.length()) );
			archivoFisico.setSec_reg(sec_reg);
			
			log.debug("Nombre del Archivo: "+archivoFisico.getFile_name());
			log.debug("Tamanio del Archivo: "+archivoFisico.getSize());
			log.debug("Tipo de Archivo: "+archivoFisico.getFile_ext());
			
			accesoDao.setUsuarioAcceso(dataSource,"USER", user);
			sysRegistroArchivosFisicoDao.insertar(dataSource, archivoFisico);
			accesoDao.setUsuarioAccesoNull(dataSource,"USER");
			
		}catch (Exception ex) {
			   log.error("Error en RegistroArchivosServiceImpl.RegistrarArchivo: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
			} 
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RegistroArchivosServiceImpl.RegistrarArchivo");
		}
		
		return num_reg;
	}

	
	/**
     * Registrar Archivo
     * @param Map<String, Object> - params
     * @return String - Código de registro de Archivo
     */ 
	
	@Override
	public String registrarArchivoSolicitud(Map<String, Object> params) {
		if (log.isDebugEnabled()) log.debug("Inicio - RegistroArchivosServiceImpl.registrarArchivoSolicitud");
		
		String num_reg="";
		String 	cod_sol =	(String) params.get ("cod_sol");
		String 	user 	=	(String) params.get ("user");
		File 	file 	= 	(File) params.get("file");
		String num_archivo = (String) params.get ("num_archivo");
		String tipoDocumento = (String) params.get ("tipoDocumento");
		String fileDescripcion = (String) params.get ("fileDescripcion");
		
		Long sec_reg=new Long(0);
		 //
		try {
				
			//recuperando extensión
			 String cadena= file.getName();
			 String extension="";
			 StringTokenizer token = new StringTokenizer(cadena,".");
			 do{
				extension=token.nextToken();
			 }while(token.hasMoreTokens());
			 
			DataSource dataSource = obtenerDataSource("sig", false);
			log.debug(" num_archivo >>>>>>>>>>>>>>"+ num_archivo+"<<<<");
				if (num_archivo.equals("") ){
					num_reg=sysRegistroArchivosDao.obtenerContador();
					log.debug("num_reg" + num_reg);
					log.debug("cod_sol" + cod_sol);
					
					RegistroArchivosBean regArchivo = new RegistroArchivosBean();
					
					regArchivo.setNum_reg(num_reg);
					regArchivo.setAplicacion("LOGISTICA");
					regArchivo.setModulo("RQNP");
					regArchivo.setRegistro(cod_sol);
					regArchivo.setDescripcion("Adjuntos del Requerimiento No Programado");
					
					accesoDao.setUsuarioAcceso(dataSource,"USER", user);
					sysRegistroArchivosDao.insertar(dataSource, regArchivo);
					accesoDao.setUsuarioAccesoNull(dataSource,"USER");
				}else{
					num_reg= num_archivo;
				}
			
			params.put("num_reg", num_reg);
			
			sec_reg = sysRegistroArchivosFisicoDao.obtenerContador(params);
			//sec_reg ++;--Se cambiao por secuenciador
			if (log.isDebugEnabled()) log.debug("num_reg" + sec_reg);
			RegistroArchivosFisicoBean archivoFisico = new RegistroArchivosFisicoBean();
			archivoFisico.setData( getBytesFromFile(file));
			archivoFisico.setDescrip_reg(fileDescripcion);
			archivoFisico.setFile_ext(extension.toUpperCase().substring(0,3));
			archivoFisico.setFile_name(file.getName() );
			archivoFisico.setFlag_imagen("N");
			archivoFisico.setFlag_security("N");
			archivoFisico.setFuente_reg(tipoDocumento);
			archivoFisico.setNom_object(file.getName());
			archivoFisico.setNum_reg(num_reg);
	
			archivoFisico.setSize( new Long (file.length()) );
			archivoFisico.setSec_reg(sec_reg);
						
			accesoDao.setUsuarioAcceso(dataSource,"USER", user);
			sysRegistroArchivosFisicoDao.insertar(dataSource, archivoFisico);
			accesoDao.setUsuarioAccesoNull(dataSource,"USER");
			
		}catch (Exception ex) {
			   log.error("Error en RegistroArchivosServiceImpl.registrarArchivoSolicitud: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
			} 
		finally {
			if (log.isDebugEnabled()) log.debug("Fin - RegistroArchivosServiceImpl.registrarArchivoSolicitud");
		}
		
		return num_reg;
	}	
	@Override
	public void eliminarArchivoFisico(Map<String, Object> params) {
		if (log.isDebugEnabled()){
			log.debug("Inicio - RegistroArchivosServiceImpl.eliminarArchivoFisico");
		}
		RegistroArchivosFisicoBean archivoFisico = new RegistroArchivosFisicoBean();
		String 	user 		=	(String) params.get ("user");
		String 	sec_archivo 		=	(String) params.get ("sec_arc");
		
		archivoFisico.setSec_reg(new Long(sec_archivo.trim()));
		try{
			DataSource dataSource = obtenerDataSource("sig", false);			
			accesoDao.setUsuarioAcceso(dataSource,"USER", user);
			sysRegistroArchivosFisicoDao.eliminaArchivo(dataSource, archivoFisico);
			accesoDao.setUsuarioAccesoNull(dataSource,"USER");
		}catch (Exception ex) {
			   log.error("Error en RegistroArchivosServiceImpl.eliminarArchivoFisico: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
		}finally {
			if (log.isDebugEnabled()) log.debug("Fin - RegistroArchivosServiceImpl.eliminarArchivoFisico");
		}
	}
	
	 public RegistroArchivosFisicoBean recuperarArchivo(Map<String, Object> params){
		 if (log.isDebugEnabled()) log.debug("Inicio - RegistroArchivosServiceImpl.recuperarArchivo");
			
			try{
				return sysRegistroArchivosFisicoDao.recuperarArchivo(params);
			}catch (Exception ex) {
				   log.error("Error en RegistroArchivosServiceImpl.recuperarArchivo: " + ex.getMessage(), ex);
				   throw new ServiceException(this, ex);
			}finally {
				if (log.isDebugEnabled()) log.debug("Fin - RegistroArchivosServiceImpl.recuperarArchivo");
			}
	 }
	

	/**
     * listar Archivos
     * @param Map<String, Object> - params
     * @return Collection - lista de Archivos RegistroArchivosFisicoBean
     */ 
	@Override
	public Collection listarArchivos(Map<String, Object> params) {
	if (log.isDebugEnabled()) log.debug("Inicio - RegistroArchivosServiceImpl.listarArchivos");
	List<Map<String, Object>> listaArchivos = new ArrayList<Map<String,Object>>();
	List<RegistroArchivosFisicoBean> lista = new ArrayList<RegistroArchivosFisicoBean>();
		log.debug("parms num_reg: " + params.get("num_reg"));
		 //
		try {
			//recuperando extensión
			lista=(List<RegistroArchivosFisicoBean> )sysRegistroArchivosFisicoDao.listarArchivos(params);
			
			for (  RegistroArchivosFisicoBean obj :lista){
				Map archivo = new HashMap();
				archivo.put("num_reg",obj.getNum_reg());
				archivo.put("sec_reg",obj.getSec_reg());
				archivo.put("file_name",obj.getFile_name());
				archivo.put("tipo_doc",obj.getFuente_reg());
				
				String newDate = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(obj.getFec_carga());
				log.debug("FECHA ------>>>>>>" +newDate);
				archivo.put("fec_carga",newDate );
				
				archivo.put("size",converBytes(obj.getSize()));
				archivo.put("file_ext",obj.getFile_ext());
				archivo.put("flag_imagen",obj.getFlag_imagen());
				listaArchivos.add(archivo);			
			}
		}catch (Exception ex) {
			   log.error("Error en RegistroArchivosServiceImpl.listarArchivos: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
		}finally {
			if (log.isDebugEnabled()) log.debug("Fin - RegistroArchivosServiceImpl.listarArchivos");
		}
		
		return listaArchivos;
	}
	
	
	/**
     * listar Tipos Archivos
     * @param Map<String, Object> - params
     * @return Collection - lista de Archivos RegistroArchivosFisicoBean
     */ 
	@Override
	public Collection listarTipoArchivos(Map<String, Object> params) {
	if (log.isDebugEnabled()) log.debug("Inicio - RegistroArchivosServiceImpl.listarTipoArchivos");
	List<Map<String, Object>> listaTipoArchivos = new ArrayList<Map<String,Object>>();
	List<T01ParametroBean> lista = new ArrayList<T01ParametroBean>();
		log.debug("parms num_reg: " + params.get("num_reg"));
		 //
		try {
			//recuperando extensión
			lista=(List<T01ParametroBean> )t01ParametroDao.recuperarTipoArchivo(params);
			
			for (  T01ParametroBean obj :lista){
				Map tipoArchivo = new HashMap();
				tipoArchivo.put("num_reg",obj.getCod_tipo());
				tipoArchivo.put("sec_reg",obj.getCod_argumento());
				tipoArchivo.put("file_name",obj.getCod_modulo() );
				tipoArchivo.put("fec_carga",obj.getCod_parametro() );
				tipoArchivo.put("size",obj.getCod_tipo() );
				tipoArchivo.put("file_ext",obj.getDesc_abrv());
				tipoArchivo.put("flag_imagen",obj.getNom_corto());
				tipoArchivo.put("flag_imagen",obj.getNom_largo());
				
				tipoArchivo.put("cod", obj.getDesc_abrv());
				tipoArchivo.put("name", obj.getDesc_abrv());
				
				listaTipoArchivos.add(tipoArchivo);		
			}
		}catch (Exception ex) {
			   log.error("Error en RegistroArchivosServiceImpl.listarTipoArchivos: " + ex.getMessage(), ex);
			   throw new ServiceException(this, ex);
		}finally {
			if (log.isDebugEnabled()) log.debug("Fin - RegistroArchivosServiceImpl.listarTipoArchivos");
		}
		
		return listaTipoArchivos;
	}

	@Override
	public String registrarArchivoFisico(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String registrarBajaArchivo(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public static byte[] getBytesFromFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);
    
        // Get the size of the file
        long length = file.length();
    
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }
    
        // Create the byte array to hold the data
        byte[] bytes = new byte[(int)length];
    
        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
               && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }
    
        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }
        
        // Close the input stream and return bytes
        is.close();
        return bytes;
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
	
	private String converBytes (long size){
		String ls_size="";
		BigDecimal kb= new BigDecimal(0);
		BigDecimal mb= new BigDecimal(0);
		if ( size <1024){
			ls_size=size +" bytes";
		}else if ( size >=1024){
			kb = new BigDecimal(size /1024).setScale(2, RoundingMode.HALF_UP) ;
			if (kb.doubleValue()<1024){
				ls_size=kb.toString() +" Kb";
			}else{
				mb = new BigDecimal(kb.doubleValue() /1024).setScale(2, RoundingMode.HALF_UP) ;
				ls_size=mb.toString() +" Mb";
			}
		}
		return ls_size;
	}
	
	public SysRegistroArchivosDao getSysRegistroArchivosDao() {
		return sysRegistroArchivosDao;
	}

	public T01ParametroDAO getT01ParametroDao() {
		return t01ParametroDao;
	}

	public void setT01ParametroDao(T01ParametroDAO t01ParametroDao) {
		this.t01ParametroDao = t01ParametroDao;
	}

	public AccesoDAO getAccesoDao() {
		return accesoDao;
	}

	public void setAccesoDao(AccesoDAO accesoDao) {
		this.accesoDao = accesoDao;
	}

	public void setSysRegistroArchivosDao(
			SysRegistroArchivosDao sysRegistroArchivosDao) {
		this.sysRegistroArchivosDao = sysRegistroArchivosDao;
	}

	public SysRegistroArchivosFisicoDao getSysRegistroArchivosFisicoDao() {
		return sysRegistroArchivosFisicoDao;
	}

	public void setSysRegistroArchivosFisicoDao(
			SysRegistroArchivosFisicoDao sysRegistroArchivosFisicoDao) {
		this.sysRegistroArchivosFisicoDao = sysRegistroArchivosFisicoDao;
	}

	public TdBeneficiariosDao getTdBeneficiariosDao() {
		return tdBeneficiariosDao;
	}

	public void setTdBeneficiariosDao(TdBeneficiariosDao tdBeneficiariosDao) {
		this.tdBeneficiariosDao = tdBeneficiariosDao;
	}

	
}
