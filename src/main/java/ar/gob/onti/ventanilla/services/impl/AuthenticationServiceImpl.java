package ar.gob.onti.ventanilla.services.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.gob.onti.ventanilla.dao.UsuarioDAO;
import ar.gob.onti.ventanilla.model.Usuario;
import ar.gob.onti.ventanilla.services.AuthenticationService;
import ar.gob.onti.ventanilla.ws.ServiceVentanillaWS;
import ar.gob.onti.ventanilla.ws.model.VentanillaRequest;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{

	@Autowired
	private UsuarioDAO usuarioDAO;
	
	private static final Logger logger = Logger.getLogger(ServiceVentanillaWS.class);

	@Transactional
	public Usuario authenticate(VentanillaRequest ventanillaRequest) {			
		return usuarioDAO.getUserByCredencial(ventanillaRequest.getUsuario(), ventanillaRequest.getPassword());	
	}	
	

}
