package ar.gob.onti.ventanilla.controller;

import ar.gob.onti.ventanilla.model.Mensaje;
import ar.gob.onti.ventanilla.services.impl.MessageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class RESTController {

    @Autowired
    private MessageServiceImpl messageServiceImpl;


    @RequestMapping(value = "/message/getAll", method = RequestMethod.GET, headers = "Accept=application/json")
    public
    @ResponseBody
    List<Mensaje> getAll() {
        return messageServiceImpl.getMensajeAll();
    }


}