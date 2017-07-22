package pe.gob.sunat.recurso2.administracion.siga.archivo.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

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

public class ReporteJasperUtil {

	static Log log = LogFactory.getLog(ReporteJasperUtil.class);
	
	public static ReporteArchivoBean GenerarArchivoPDF(ReporteJasperBean params){
		ReporteArchivoBean reportBean = new ReporteArchivoBean();
		
		//ReporteArchivoBean fbean = new ReporteArchivoBean();
		
		String path_jasper= ReporteJasperConstantes.RUTA_JASPER + params.getJasperName();
		String path_pdf= ReporteJasperConstantes.RUTA_PDF + params.getFileName();
		
		InputStream is = null;
		byte[] fileBytes ;
		try {
			log.debug(path_jasper);
			is = new FileInputStream(path_jasper);
			JasperReport reporte;
			log.debug("avialble: " + is.available() );
			reporte = (JasperReport) JRLoader.loadObject(is);
			log.debug("name JAsper: " + reporte.getName());
			 
	        JRDataSource dsLista = new JRBeanCollectionDataSource(params.getListaDetalle());
	        log.debug("Detalles de reporte dsLista: " + dsLista.toString());
	        
	        JasperPrint jasperPrint=new JasperPrint() ;
	        jasperPrint = JasperFillManager.fillReport( reporte,params.getParametros(), dsLista );
	       
	        
	        log.debug("pages: "+ jasperPrint.getPageWidth());
	      
	        fileBytes=JasperExportManager.exportReportToPdf(jasperPrint);
	        
	        
	        log.debug("Tamanio bye: "+fileBytes.length);
	        if(fileBytes.length > 0){
	        	FileOutputStream file = new FileOutputStream(path_pdf + ".pdf");
	        	file.write(fileBytes);
	        	reportBean.setData(fileBytes);
	        	reportBean.setFieldExtension("pdf");
	        	reportBean.setFieldName(path_pdf+".pdf");
	        	
	        }else{
	        	log.error("No se Genero El  Jasper y por lo tanto no el PDF");
	        }
	        //reportBean.setData(fileBytes);
	        //reportBean.setFieldName(fieldName);
	        
	        is.close();
		} catch (FileNotFoundException ex) {
			log.error("FileNotFoundException en ReporteJasperUtil.GenerarArchivoPDF: " + ex.getMessage(), ex);
			ex.printStackTrace();
		}catch (JRException ex) {
			log.error("JRException en ReporteJasperUtil.GenerarArchivoPDF: " + ex.getMessage(), ex);
			ex.printStackTrace();
		} catch (IOException ex) {
			log.error("IOException en ReporteJasperUtil.GenerarArchivoPDF: " + ex.getMessage(), ex);
			ex.printStackTrace();
		}
		return reportBean;
	}

	public static ReporteArchivoBean generarArchivoPDF(ReporteJasperBean params, String rutaJasper, String rutaPdf){
		ReporteArchivoBean reportBean = new ReporteArchivoBean();
		
		//ReporteArchivoBean fbean = new ReporteArchivoBean();
		
		String path_jasper= rutaJasper + params.getJasperName();
		String path_pdf= rutaPdf + params.getFileName();
		
		InputStream is = null;
		byte[] fileBytes ;
		try {
			log.debug(path_jasper);
			is = new FileInputStream(path_jasper);
			JasperReport reporte;
			log.debug("avialble: " + is.available() );
			reporte = (JasperReport) JRLoader.loadObject(is);
			log.debug("name JAsper: " + reporte.getName());
			 
	        JRDataSource dsLista = new JRBeanCollectionDataSource(params.getListaDetalle());
	        
	        JasperPrint jasperPrint=new JasperPrint() ;
	        jasperPrint = JasperFillManager.fillReport( reporte,params.getParametros(), dsLista );
	       
	        
	        log.debug("pages: "+ jasperPrint.getPageWidth());
	      
	        fileBytes=JasperExportManager.exportReportToPdf(jasperPrint);
	        
	        
	        log.debug("Tamanio bye: "+fileBytes.length);
	        if(fileBytes.length > 0){
	        	FileOutputStream file = new FileOutputStream(path_pdf + ".pdf");
	        	file.write(fileBytes);
	        	reportBean.setData(fileBytes);
	        	reportBean.setFieldExtension("pdf");
	        	reportBean.setFieldName(path_pdf+".pdf");
	        	
	        }else{
	        	log.error("No se Genero El  Jasper y por lo tanto no el PDF");
	        }
	        //reportBean.setData(fileBytes);
	        //reportBean.setFieldName(fieldName);
	        
	        is.close();
		} catch (FileNotFoundException ex) {
			log.error("FileNotFoundException en ReporteJasperUtil.GenerarArchivoPDF: " + ex.getMessage(), ex);
			ex.printStackTrace();
		}catch (JRException ex) {
			log.error("JRException en ReporteJasperUtil.GenerarArchivoPDF: " + ex.getMessage(), ex);
			ex.printStackTrace();
		} catch (IOException ex) {
			log.error("IOException en ReporteJasperUtil.GenerarArchivoPDF: " + ex.getMessage(), ex);
			ex.printStackTrace();
		}
		return reportBean;
	}
}
