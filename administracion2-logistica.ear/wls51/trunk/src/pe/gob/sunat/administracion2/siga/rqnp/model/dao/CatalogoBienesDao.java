package pe.gob.sunat.administracion2.siga.rqnp.model.dao;

import java.util.Collection;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import pe.gob.sunat.administracion2.siga.rqnp.model.domain.CatalogoBienesBean;

public interface CatalogoBienesDao {
	public Collection listarCatalogo(Map<String, Object> params)
	throws DataAccessException;
	
	public Collection listarCatalogoFamilia(Map<String, Object> params)
	throws DataAccessException;
	
	public CatalogoBienesBean recuperarCatalogoBien(Map<String, Object> params)
	throws DataAccessException;

}
