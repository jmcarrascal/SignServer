package ar.gob.onti.ventanilla.services;

import ar.gob.onti.ventanilla.ws.model.VentanillaRequest;
import ar.gob.onti.ventanilla.ws.model.VentanillaResponse;

public interface SignDocumentService {

    VentanillaResponse signDocument(VentanillaRequest ventanillaRequest);

}
