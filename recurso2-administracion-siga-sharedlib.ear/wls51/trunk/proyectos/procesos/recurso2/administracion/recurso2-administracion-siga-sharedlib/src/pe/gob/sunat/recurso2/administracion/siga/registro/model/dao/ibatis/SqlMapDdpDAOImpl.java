package pe.gob.sunat.recurso2.administracion.siga.registro.model.dao.ibatis;

import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.Ddp;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.dao.DdpDAO;

public class SqlMapDdpDAOImpl extends SqlMapClientDaoSupport implements DdpDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table VWDDP
     *
     * @ibatorgenerated Wed Feb 08 15:42:39 COT 2017
     */
    public SqlMapDdpDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table VWDDP
     *
     * @ibatorgenerated Wed Feb 08 15:42:39 COT 2017
     */
    @SuppressWarnings("unchecked")
    public List<Ddp> selectByExample(Ddp example) {
        List<Ddp> list = getSqlMapClientTemplate().queryForList("VWDDP.selectByExample", example);
        return list;
    }
}