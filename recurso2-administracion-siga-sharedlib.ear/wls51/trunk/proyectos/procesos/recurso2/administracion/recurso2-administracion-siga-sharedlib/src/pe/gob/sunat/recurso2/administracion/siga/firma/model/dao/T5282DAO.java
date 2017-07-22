package pe.gob.sunat.recurso2.administracion.siga.firma.model.dao;

import java.util.Map;

import pe.gob.sunat.recurso2.administracion.siga.firma.model.bean.T5282Archbin;

public interface T5282DAO {
	public void updateNombre(T5282Archbin paramUpdate);
	public T5282Archbin findByPk(Long id);
}
