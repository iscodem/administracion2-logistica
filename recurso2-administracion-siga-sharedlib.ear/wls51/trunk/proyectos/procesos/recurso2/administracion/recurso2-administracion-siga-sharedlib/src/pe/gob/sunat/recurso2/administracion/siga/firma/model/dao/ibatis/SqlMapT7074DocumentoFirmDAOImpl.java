package pe.gob.sunat.recurso2.administracion.siga.firma.model.dao.ibatis;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import pe.gob.sunat.recurso2.administracion.siga.firma.model.bean.T7074DocumentoFirm;
import pe.gob.sunat.recurso2.administracion.siga.firma.model.bean.T7076DelegacFirma;
import pe.gob.sunat.recurso2.administracion.siga.firma.model.dao.T7074DocumentoFirmDAO;

public class SqlMapT7074DocumentoFirmDAOImpl extends SqlMapClientDaoSupport implements T7074DocumentoFirmDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table T7074DOCUMENTOFIRM
     *
     * @ibatorgenerated Wed Mar 30 14:05:42 COT 2016
     */
    public SqlMapT7074DocumentoFirmDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table T7074DOCUMENTOFIRM
     *
     * @ibatorgenerated Wed Mar 30 14:05:42 COT 2016
     */
    public int deleteByPrimaryKey(BigDecimal codDocaut) {
        T7074DocumentoFirm key = new T7074DocumentoFirm();
        key.setCodDocaut(codDocaut);
        int rows = getSqlMapClientTemplate().delete("t7074documentofirm.deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table T7074DOCUMENTOFIRM
     *
     * @ibatorgenerated Wed Mar 30 14:05:42 COT 2016
     */
    public void insert(T7074DocumentoFirm record) {
        getSqlMapClientTemplate().insert("t7074documentofirm.insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table T7074DOCUMENTOFIRM
     *
     * @ibatorgenerated Wed Mar 30 14:05:42 COT 2016
     */
    public void insertSelective(T7074DocumentoFirm record) {
        getSqlMapClientTemplate().insert("t7074documentofirm.insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table T7074DOCUMENTOFIRM
     *
     * @ibatorgenerated Wed Mar 30 14:05:42 COT 2016
     */
    public T7074DocumentoFirm selectByPrimaryKey(BigDecimal codDocaut) {
        T7074DocumentoFirm key = new T7074DocumentoFirm();
        key.setCodDocaut(codDocaut);
        T7074DocumentoFirm record = (T7074DocumentoFirm) getSqlMapClientTemplate().queryForObject("t7074documentofirm.selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table T7074DOCUMENTOFIRM
     *
     * @ibatorgenerated Wed Mar 30 14:05:42 COT 2016
     */
    public int updateByPrimaryKeySelective(T7074DocumentoFirm record) {
        int rows = getSqlMapClientTemplate().update("t7074documentofirm.updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table T7074DOCUMENTOFIRM
     *
     * @ibatorgenerated Wed Mar 30 14:05:42 COT 2016
     */
    public int updateByPrimaryKey(T7074DocumentoFirm record) {
        int rows = getSqlMapClientTemplate().update("t7074documentofirm.updateByPrimaryKey", record);
        return rows;
    }
    
    public List<T7074DocumentoFirm> listByParameter(T7074DocumentoFirm param) {
        List<T7074DocumentoFirm> list = (List<T7074DocumentoFirm>) getSqlMapClientTemplate().queryForList("t7074documentofirm.listByParameter", param);
        return list;
    }
    
    public List<T7074DocumentoFirm> listByParameterConDatosFirmantes(T7074DocumentoFirm param) {
        List<T7074DocumentoFirm> list = (List<T7074DocumentoFirm>) getSqlMapClientTemplate().queryForList("t7074documentofirm.listByParameterConDatosFirmantes", param);
        return list;
    }
    
    public int updateEstadoDocumento(T7074DocumentoFirm record) {
        int rows = getSqlMapClientTemplate().update("t7074documentofirm.updateEstadoDocumento", record);
        return rows;
    }
    
    public Long obtenerSecuencial() throws Exception {
    	Map<String, Object> param = new HashMap<String, Object>();
    	
    	param.put("nomtabla", "T7074DOCUMENTOFIRM");
    	param.put("nomcampo", "COD_DOCAUT");
    	
        getSqlMapClientTemplate().update("t7074documentofirm.obtenerSecuencial", param);
        String seqString = (String)param.get("vSecuencia");
        
        Long seq = 0l;
        try {
        	seq = Long.parseLong(seqString);
        }
        catch (Exception e) {
        	throw e;
        }
        return seq;
    }
    
    public Long obtenerSecuencialFirmaSiga() throws Exception {
    	Map<String, Object> param = new HashMap<String, Object>();
    	
    	param.put("nomtabla", "T7074DOCUMENTOFIRM");
    	param.put("nomcampo", "COD_IDARCHIVO");
    	
        getSqlMapClientTemplate().update("t7074documentofirm.obtenerSecuencial", param);
        String seqString = (String)param.get("vSecuencia");
        
        Long seq = 0l;
        try {
        	seq = Long.parseLong(seqString);
        }
        catch (Exception e) {
        	throw e;
        }
        return seq;
    }
    
  	@Override
  	public String encriptaCadena(Map parm) {
  		return (String)getSqlMapClientTemplate().queryForObject("t7074documentofirm.encriptaCadena", parm);
  	}
  	
  	
  	@Override
  	public String desencriptaCadena(Map parm) {
  		return (String)getSqlMapClientTemplate().queryForObject("t7074documentofirm.desencriptaCadena", parm);
  	}
}