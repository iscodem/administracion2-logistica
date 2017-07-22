package pe.gob.sunat.recurso2.administracion.siga.firma.model.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import pe.gob.sunat.recurso2.administracion.siga.firma.model.bean.Dependencia;

public interface DependenciaDao {

	public List<Dependencia> obtenerDatosDependencia(Dependencia param) throws DataAccessException;	
}
