package ar.gob.onti.ventanilla.services;

import ar.gob.onti.ventanilla.model.Usuario;
import ar.gob.onti.ventanilla.ws.model.VentanillaRequest;

public interface AuthenticationService {

	Usuario authenticate(VentanillaRequest ventanillaRequest);


}
