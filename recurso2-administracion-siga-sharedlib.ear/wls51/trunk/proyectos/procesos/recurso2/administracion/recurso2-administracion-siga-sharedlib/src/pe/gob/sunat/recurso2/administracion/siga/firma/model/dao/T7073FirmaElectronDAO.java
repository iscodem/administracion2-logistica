package pe.gob.sunat.recurso2.administracion.siga.firma.model.dao;

import java.util.List;

import pe.gob.sunat.recurso2.administracion.siga.firma.model.bean.T7073FirmaElectron;

public interface T7073FirmaElectronDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table T7073FIRMAELECTRON
     *
     * @ibatorgenerated Wed Mar 30 14:05:42 COT 2016
     */
    int deleteByPrimaryKey(String codFirma);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table T7073FIRMAELECTRON
     *
     * @ibatorgenerated Wed Mar 30 14:05:42 COT 2016
     */
    void insert(T7073FirmaElectron record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table T7073FIRMAELECTRON
     *
     * @ibatorgenerated Wed Mar 30 14:05:42 COT 2016
     */
    void insertSelective(T7073FirmaElectron record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table T7073FIRMAELECTRON
     *
     * @ibatorgenerated Wed Mar 30 14:05:42 COT 2016
     */
    T7073FirmaElectron selectByPrimaryKey(String codFirma);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table T7073FIRMAELECTRON
     *
     * @ibatorgenerated Wed Mar 30 14:05:42 COT 2016
     */
    int updateByPrimaryKeySelective(T7073FirmaElectron record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table T7073FIRMAELECTRON
     *
     * @ibatorgenerated Wed Mar 30 14:05:42 COT 2016
     */
    int updateByPrimaryKey(T7073FirmaElectron record);
    
    public List<T7073FirmaElectron> listByParameter(T7073FirmaElectron param);
}