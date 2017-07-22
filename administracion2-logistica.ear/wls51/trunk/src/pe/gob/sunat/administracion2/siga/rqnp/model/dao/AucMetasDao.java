package pe.gob.sunat.administracion2.siga.rqnp.model.dao;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
public interface AucMetasDao {
	public Collection listarMetasAuc(Map<String, Object> params )	throws DataAccessException;
}
