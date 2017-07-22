package pe.gob.sunat.recurso2.administracion.siga.firma.model.dao;

import java.util.List;

import pe.gob.sunat.recurso2.administracion.siga.firma.model.bean.T01Parametro;

public interface T01ParametroDao {

	public List<T01Parametro> recuperarParametro(T01Parametro param);
}
