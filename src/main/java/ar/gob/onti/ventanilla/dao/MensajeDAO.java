package ar.gob.onti.ventanilla.dao;

import ar.gob.onti.ventanilla.model.Mensaje;

import java.util.List;


public interface MensajeDAO {

    public Mensaje getMensajeByPK(Integer idMensaje);

    public List<Mensaje> getMensajeAll();

}
