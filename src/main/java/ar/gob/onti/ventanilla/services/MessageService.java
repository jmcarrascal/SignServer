package ar.gob.onti.ventanilla.services;

import ar.gob.onti.ventanilla.model.Mensaje;
import ar.gob.onti.ventanilla.ws.model.VentanillaResponse;


public interface MessageService {

    public Mensaje buildMessage(Integer idAuthFailed);

    public VentanillaResponse buildErrorVentanillaResponse(Integer idAuthFailed);

}
