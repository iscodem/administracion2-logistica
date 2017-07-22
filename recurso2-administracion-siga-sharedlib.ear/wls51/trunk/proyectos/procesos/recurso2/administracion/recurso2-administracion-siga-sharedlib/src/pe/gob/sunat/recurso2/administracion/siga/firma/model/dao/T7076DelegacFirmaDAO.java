package pe.gob.sunat.recurso2.administracion.siga.firma.model.dao;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.List;
import org.springframework.dao.DataAccessException;

import pe.gob.sunat.recurso2.administracion.siga.firma.model.bean.T7076DelegacFirma;

public interface T7076DelegacFirmaDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table T7076DELEGACFIRMA
     *
     * @ibatorgenerated Wed Mar 30 14:05:42 COT 2016
     */
    int deleteByPrimaryKey(BigDecimal codDelegacion);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table T7076DELEGACFIRMA
     *
     * @ibatorgenerated Wed Mar 30 14:05:42 COT 2016
     */
    void insert(T7076DelegacFirma record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table T7076DELEGACFIRMA
     *
     * @ibatorgenerated Wed Mar 30 14:05:42 COT 2016
     */
    void insertSelective(T7076DelegacFirma record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table T7076DELEGACFIRMA
     *
     * @ibatorgenerated Wed Mar 30 14:05:42 COT 2016
     */
    T7076DelegacFirma selectByPrimaryKey(BigDecimal codDelegacion);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table T7076DELEGACFIRMA
     *
     * @ibatorgenerated Wed Mar 30 14:05:42 COT 2016
     */
    int updateByPrimaryKeySelective(T7076DelegacFirma record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table T7076DELEGACFIRMA
     *
     * @ibatorgenerated Wed Mar 30 14:05:42 COT 2016
     */
    int updateByPrimaryKey(T7076DelegacFirma record);

    List<T7076DelegacFirma> listByParameter(T7076DelegacFirma param);
}