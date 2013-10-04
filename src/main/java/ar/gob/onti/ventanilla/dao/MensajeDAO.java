package ar.gob.onti.ventanilla.dao;

import java.util.List;

import ar.gob.onti.ventanilla.model.Mensaje;



public interface MensajeDAO {

	public Mensaje getMensajeByPK(Integer idMensaje);
	
	public List<Mensaje> getMensajeAll();
	
}
