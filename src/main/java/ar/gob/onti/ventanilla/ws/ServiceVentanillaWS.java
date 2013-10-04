package ar.gob.onti.ventanilla.ws;


import ar.gob.onti.ventanilla.services.SignDocumentService;
import ar.gob.onti.ventanilla.ws.model.VentanillaRequest;
import ar.gob.onti.ventanilla.ws.model.VentanillaResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jws.WebMethod;
import javax.jws.WebService;


@WebService
public class ServiceVentanillaWS {

    private static final Logger logger = Logger.getLogger(ServiceVentanillaWS.class);

    @Autowired(required = true)
    private SignDocumentService signDocumentService;

    @WebMethod(operationName = "signDocument")
    public VentanillaResponse signDocument(VentanillaRequest ventanillaRequest) {
        VentanillaResponse ventanillaResponse = signDocumentService.signDocument(ventanillaRequest);
        return ventanillaResponse;
    }

}