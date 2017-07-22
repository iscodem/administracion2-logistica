package pe.gob.sunat.administracion2.siga.rqnp.model.dao;

import java.util.Collection;

import javax.sql.DataSource;
import org.springframework.dao.DataAccessException;
import pe.gob.sunat.administracion2.siga.rqnp.model.domain.RequerimientoNoProgramadoBean;


public interface RequerimientoNoProgramadoDao {
	public Collection listarRqnpUsuario(String ano_pro, String est_req , String cod_sed,
			String 	cod_res , String mes_pro, String ind_registropor)
	throws DataAccessException;
	
	public Collection listarRqnpJefe(String ano_pro, String est_req , String cod_sed,
			 String mes_pro,String cod_per)
	throws DataAccessException;
	
	public Collection listarRqnpIntendente(String ano_pro, String est_req , String cod_sed,
			 String mes_pro,String cod_per)
	throws DataAccessException;
	
	
	public String secuencialRequerimientoRqnpUuoo(String anio_pro, String cod_uuoo  ) 	throws DataAccessException;
	public String secuencialRequerimientoRqnp(String anio_pro ) throws DataAccessException;
	public void insertar(RequerimientoNoProgramadoBean bean) throws DataAccessException;
	public void update(RequerimientoNoProgramadoBean bean) throws DataAccessException;
	public void updateAUC(DataSource datasource, RequerimientoNoProgramadoBean bean) throws DataAccessException;
	public void updateCodObjeto(DataSource datasource, RequerimientoNoProgramadoBean bean) throws DataAccessException;
	public void updateMonto(RequerimientoNoProgramadoBean bean) throws DataAccessException;
	public void updateEstado(DataSource datasource, RequerimientoNoProgramadoBean bean) throws DataAccessException;
	public void updateAnula(DataSource datasource, RequerimientoNoProgramadoBean bean) throws DataAccessException;
	public void updateEnvio(DataSource datasource, RequerimientoNoProgramadoBean bean) throws DataAccessException;
	public void updateFile(DataSource datasource, RequerimientoNoProgramadoBean bean) throws DataAccessException;
	public RequerimientoNoProgramadoBean recuperarRqnp(String cod_req ) throws DataAccessException;
	public void envioMailUct( String cod_rqnp, String cod_bien) throws DataAccessException;
	public void envioMailRechazo( String cod_rqnp, String cod_bien, String cod_bandeja) throws DataAccessException;
	public void envioMailDerivar( String cod_rqnp, String cod_bien) throws DataAccessException;
	public String getTipoObjetoRqnp(String cod_req) throws DataAccessException ;
	
	//INICIO: DPORRASC
	public void insertarAUC(DataSource datasource, RequerimientoNoProgramadoBean bean)
	throws DataAccessException;
	
	public String obtenerTipoAUC(String cod_dep) //OBTIENE EL TIPO DE AUC (TÉCNICA O DESCONCENTRADA)
	throws DataAccessException;
	
	public String validaUserAuAUC(String cod_per) throws DataAccessException;
	
	public String obtenerAucOfRqnp(String codRqnp) throws DataAccessException;

}
