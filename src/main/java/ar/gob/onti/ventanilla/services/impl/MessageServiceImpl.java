package ar.gob.onti.ventanilla.services.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.gob.onti.ventanilla.dao.MensajeDAO;
import ar.gob.onti.ventanilla.model.Mensaje;
import ar.gob.onti.ventanilla.services.MessageService;
import ar.gob.onti.ventanilla.ws.ServiceVentanillaWS;
import ar.gob.onti.ventanilla.ws.model.VentanillaResponse;

@Service
public class MessageServiceImpl implements MessageService{

	@Autowired
	private MensajeDAO mensajeDAO;
	
	private static final Logger logger = Logger.getLogger(ServiceVentanillaWS.class);
	
	public Mensaje buildMessage(Integer idAuthFailed) {
		return 	mensajeDAO.getMensajeByPK(idAuthFailed);
	}

	
	public VentanillaResponse buildErrorVentanillaResponse(Integer idAuthFailed) {
		VentanillaResponse ventanillaResponse = new VentanillaResponse();
		Mensaje mensajeResponse = buildMessage(idAuthFailed);
		ventanillaResponse.setMessage(mensajeResponse);
		return ventanillaResponse;
	}	
	
	public List<Mensaje> getMensajeAll(){
		return mensajeDAO.getMensajeAll();
	}

}
