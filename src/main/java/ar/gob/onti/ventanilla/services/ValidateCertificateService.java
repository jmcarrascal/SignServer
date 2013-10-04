package ar.gob.onti.ventanilla.services;

import ar.gob.onti.ventanilla.model.Usuario;
import ar.gob.onti.ventanilla.ws.model.VentanillaResponse;


public interface ValidateCertificateService {

    VentanillaResponse verifyCertificates(byte[] documento,
                                          Usuario usuarioValido);


}
