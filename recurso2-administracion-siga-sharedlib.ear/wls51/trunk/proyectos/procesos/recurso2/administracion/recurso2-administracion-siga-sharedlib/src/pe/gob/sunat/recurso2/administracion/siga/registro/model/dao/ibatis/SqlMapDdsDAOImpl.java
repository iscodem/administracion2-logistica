package pe.gob.sunat.recurso2.administracion.siga.registro.model.dao.ibatis;

import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.Dds;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.dao.DdsDAO;

public class SqlMapDdsDAOImpl extends SqlMapClientDaoSupport implements DdsDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table VWDDS
     *
     * @ibatorgenerated Wed Feb 08 15:42:39 COT 2017
     */
    public SqlMapDdsDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table VWDDS
     *
     * @ibatorgenerated Wed Feb 08 15:42:39 COT 2017
     */
    @SuppressWarnings("unchecked")
    public List<Dds> selectByExample(Dds example) {
        List<Dds> list = getSqlMapClientTemplate().queryForList("VWDDS.selectByExample", example);
        return list;
    }
}