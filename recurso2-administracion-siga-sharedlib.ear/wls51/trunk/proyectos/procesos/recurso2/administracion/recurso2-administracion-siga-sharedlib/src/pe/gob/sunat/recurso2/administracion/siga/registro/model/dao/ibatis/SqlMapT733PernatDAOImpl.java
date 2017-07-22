package pe.gob.sunat.recurso2.administracion.siga.registro.model.dao.ibatis;

import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.bean.T733Pernat;
import pe.gob.sunat.recurso2.administracion.siga.registro.model.dao.T733PernatDAO;

public class SqlMapT733PernatDAOImpl extends SqlMapClientDaoSupport implements T733PernatDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table VWT733PERNAT
     *
     * @ibatorgenerated Wed Feb 08 15:42:39 COT 2017
     */
    public SqlMapT733PernatDAOImpl() {
        super();
    }


    @SuppressWarnings("unchecked")
    public List<T733Pernat> selectByExample(T733Pernat example) {
        List<T733Pernat> list = getSqlMapClientTemplate().queryForList("VWT733PERNAT.selectByExample", example);
        return list;
    }
}