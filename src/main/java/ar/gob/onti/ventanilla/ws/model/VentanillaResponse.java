package ar.gob.onti.ventanilla.ws.model;

import ar.gob.onti.ventanilla.model.Mensaje;

public class VentanillaResponse {
	
private byte[] documentoFirmado;
private Mensaje message;


public Mensaje getMessage() {
	return message;
}

public void setMessage(Mensaje message) {
	this.message = message;
}

public VentanillaResponse() {
}

public VentanillaResponse(Mensaje message) {
	this.message = message;
}

public byte[] getDocumentoFirmado() {
	return documentoFirmado;
}
public void setDocumentoFirmado(byte[] documentoFirmado) {
	this.documentoFirmado = documentoFirmado;
}

}
