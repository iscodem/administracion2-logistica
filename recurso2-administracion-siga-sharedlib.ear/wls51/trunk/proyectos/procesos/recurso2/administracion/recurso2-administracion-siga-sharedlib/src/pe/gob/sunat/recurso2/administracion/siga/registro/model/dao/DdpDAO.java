package pe.gob.sunat.recurso2.administracion.siga.registro.model.dao;

import java.util.List;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.Ddp;

public interface DdpDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table VWDDP
     *
     * @ibatorgenerated Wed Feb 08 15:42:39 COT 2017
     */
    List<Ddp> selectByExample(Ddp example);
}