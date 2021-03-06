package pe.gob.sunat.recurso2.administracion.siga.firma.model.dao;

import java.math.BigDecimal;
import java.util.List;

import pe.gob.sunat.recurso2.administracion.siga.firma.model.bean.T7074DocumentoFirm;
import pe.gob.sunat.recurso2.administracion.siga.firma.model.bean.T7075TerminoCond;

public interface T7075TerminoCondDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table T7075TERMINOCOND
     *
     * @ibatorgenerated Wed Mar 30 14:05:42 COT 2016
     */
    int deleteByPrimaryKey(BigDecimal codTerycon);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table T7075TERMINOCOND
     *
     * @ibatorgenerated Wed Mar 30 14:05:42 COT 2016
     */
    void insert(T7075TerminoCond record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table T7075TERMINOCOND
     *
     * @ibatorgenerated Wed Mar 30 14:05:42 COT 2016
     */
    void insertSelective(T7075TerminoCond record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table T7075TERMINOCOND
     *
     * @ibatorgenerated Wed Mar 30 14:05:42 COT 2016
     */
    T7075TerminoCond selectByPrimaryKey(BigDecimal codTerycon);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table T7075TERMINOCOND
     *
     * @ibatorgenerated Wed Mar 30 14:05:42 COT 2016
     */
    int updateByPrimaryKeySelective(T7075TerminoCond record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table T7075TERMINOCOND
     *
     * @ibatorgenerated Wed Mar 30 14:05:42 COT 2016
     */
    int updateByPrimaryKey(T7075TerminoCond record);
    
    List<T7075TerminoCond> listByParameter(T7075TerminoCond param);
}