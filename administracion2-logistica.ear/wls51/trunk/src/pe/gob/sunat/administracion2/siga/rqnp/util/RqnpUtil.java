package pe.gob.sunat.administracion2.siga.rqnp.util;
import java.io.IOException;
import java.io.StringWriter;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pe.gob.sunat.administracion2.siga.rqnp.model.domain.RequerimientoNoProgBienesBean;
import pe.gob.sunat.administracion2.siga.rqnp.model.domain.RequerimientoNoProgramadoBean;
import pe.gob.sunat.administracion2.siga.rqnp.web.controller.RqnpController;
import pe.gob.sunat.framework.spring.util.exception.ServiceException;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.MaestroContratistasBean;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.MaestroPersonalBean;
import pe.gob.sunat.recurso2.administracion.siga.util.Anio;
import pe.gob.sunat.recurso2.administracion.siga.util.FechaUtil;
import pe.gob.sunat.recurso2.administracion.siga.util.Mes;
import pe.gob.sunat.recurso2.administracion.siga.util.NumeroUtil;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RqnpUtil {
	
	/**
	 * Método que conviente una lista en objeto JSON para enviar al jsp
	 * @author dporrasc
	 * @param lista
	 * @return 
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	
	public static StringWriter toJSON(List lista) throws JsonGenerationException, JsonMappingException, IOException {
		final StringWriter sw =new StringWriter();
	    final ObjectMapper mapper = new ObjectMapper();
	    mapper.writeValue(sw, lista);
	    
	    // asegurar que las listas no sean null
		return (StringWriter)(sw == null ? new ArrayList() : sw);
	}



	/**
	 * Metodo que permite armar la lista de objetos Anio a visualizar en las bandejas de registro de RQNP
	 * 
	 * @author dporrasc
	 * @param anioActual anio actual
	 * @return lista de objetos anios
	 * @see Anio
	 */
	public static ArrayList<Anio> obtenerAniosRqnp(int anioActual) {
		ArrayList<Anio> anioList = new ArrayList<Anio>();
		int anioInicial = anioActual - 4;
		for (int i = anioInicial; i < anioActual + 4; i++) {
			Anio anio = new Anio();
			anio.setCodigoAnio(Integer.toString(i));
			anio.setDescripcionAnio(Integer.toString(i));
			anioList.add(anio);
		}
		return anioList;
	}
	
	/**
	 * Metodo que permite armar la lista de objetos Mes a visualizar en las bandejas de registro de RQNP
	 * 
	 * @author dporrasc
	 * @param anioActual mes actual
	 * @return lista de objetos meses
	 * @see Anio
	 */
	public static ArrayList<Mes> obtenerMesesAnio() {
		ArrayList<Mes> mesList = new ArrayList<Mes>();
		String[] mesArray = {"enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"};
		Mes mes;
		for (int i = 0; i < mesArray.length; i++) {
			mes = new Mes();
			mes.setCodigoMes(NumeroUtil.formatNumeroToStringWithCerosLeft(2, (i + 1)));
			mes.setDescripcionMes(mesArray[i]);
			mesList.add(mes);
		}
		return mesList;
	}
	
	
	/**
	 * @author dporrasc
	 * Leer los datos cabecera del requerimiento
	 * @param  request
     * @param  response 
     * @return ModelAndView 
	 */
	
	public static Map<String,Object> getRqnpCabDatos(RequerimientoNoProgramadoBean rqnp){
		Map<String,Object> mapRqnp = new HashMap<String, Object>();
		Map<String,Object> parm = new HashMap<String, Object>();
		
			mapRqnp.put("codigoRqnp", rqnp.getCodigoRqnp());
			mapRqnp.put("numReqNoProgramado", rqnp.getCodigoReqNoProgramado());
			mapRqnp.put("fechaRqnp",FechaUtil.formatDateToDateDDMMYYYYHHMM(rqnp.getFechaRqnp()));
			mapRqnp.put("codEmpleadoResponsable",rqnp.getCodigoResposanble());
			mapRqnp.put("numRegResponsable", rqnp.getNumeroRegistro());
			mapRqnp.put("nomResponsable", rqnp.getNombreSolicitante());
			mapRqnp.put("codDepResponsable", rqnp.getCodigoDependencia());
			mapRqnp.put("uuooResponsable", rqnp.getUuoo());
			mapRqnp.put("nomUuooResponsable", rqnp.getNombreUOSolicitante());
			mapRqnp.put("anexoContacto", rqnp.getAnexo_contacto());
			mapRqnp.put("codFinalidad", rqnp.getCod_finalidad());
			mapRqnp.put("codNecesidad", rqnp.getCod_necesidad());
			mapRqnp.put("motivoSustento",rqnp.getMotivoSolicitud());
			
			mapRqnp.put("obsJustificacion", rqnp.getObs_justificacion());
			mapRqnp.put("obsPlazoEntrega", rqnp.getObs_plazo_entrega());
			mapRqnp.put("obsLugarEntrega", rqnp.getObs_lugar_entrega());
			mapRqnp.put("obsDirEntrega", rqnp.getObs_dir_entrega());
			mapRqnp.put("anioAtencion", rqnp.getAnio_atencion());
			mapRqnp.put("mesAtencion", rqnp.getMes_atencion());
			mapRqnp.put("tipoVinculo", rqnp.getTipo_vinculo());
			mapRqnp.put("vinculo", rqnp.getVinculo());
			mapRqnp.put("indVinculo", rqnp.getInd_vinculo());
			mapRqnp.put("indPrestamo", rqnp.getInd_prestamo());
			mapRqnp.put("montoRqnp",  rqnp.getMontoRqnp().setScale(2, RoundingMode.HALF_UP) );
			
		
		return mapRqnp;
	}

	
	/**
	 * @author dporrasc
	 * Leer los datos del contacto del requerimiento
	 * @param  request
     * @param  response 
     * @return ModelAndView 
	 */
	
	public static Map<String,Object> getRqnpCabDatosContacto(MaestroPersonalBean personalContacto){
		Map<String,Object> mapRqnp = new HashMap<String, Object>();
		Map<String,Object> parm = new HashMap<String, Object>();
			
		mapRqnp.put("codEmpleadoContacto", personalContacto.getCodigoEmpleado());
		mapRqnp.put("nomContacto", personalContacto.getNombre_completo());
		mapRqnp.put("numRegContacto", personalContacto.getNumero_registro());
		
		return mapRqnp;
	}
	
	
	
	/**
	 * @author dporrasc
	 * Leer los datos del proveedor del requerimiento
	 * @param  request
     * @param  response 
     * @return ModelAndView 
	 */
	
	public static Map<String,Object> getRqnpCabDatosProveedor(MaestroContratistasBean contratista){
		Map<String,Object> mapRqnp = new HashMap<String, Object>();
		Map<String,Object> parm = new HashMap<String, Object>();
			
		parm.put("cod_cont", contratista.getCod_cont());
		
		if (contratista!=null){
			mapRqnp.put("cod_cont", contratista.getCod_cont());
			mapRqnp.put("num_ruc", contratista.getNum_ruc());
			mapRqnp.put("raz_social", contratista.getRaz_social());
		}
			
		return mapRqnp;
	}
	
	
	/**
	 * @author dporrasc
	 * Metodo que busca un item en la lista 
	 * @param codigoBien
	 * @param listaBienes
	 * @return true si encuentra y false si no lo encuentra
	 */
	public static boolean existeItemEnListaBienes(String codigoBien, ArrayList<RequerimientoNoProgBienesBean> listaBienes){
		Boolean rpta=false;
		for(int i=0;i<listaBienes.size();i++){ //cuando ya existe items guardados en DB
			RequerimientoNoProgBienesBean bienDB = new RequerimientoNoProgBienesBean();
			bienDB = listaBienes.get(i);
			if(codigoBien.equals(bienDB.getCodigoBien())){
				rpta=true; 
			}
		}
		return rpta;
	}
}