package ar.gob.onti.ventanilla.dao.impl;

import ar.gob.onti.ventanilla.dao.MensajeDAO;
import ar.gob.onti.ventanilla.model.Mensaje;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MensajeDAOImpl implements MensajeDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public Mensaje getMensajeByPK(Integer idMensaje) {
        Mensaje mensaje = null;
        List<Mensaje> mensajeList = new ArrayList<Mensaje>();

        mensajeList = sessionFactory.getCurrentSession()
                .createQuery("select m from Mensaje m where m.idMensaje = :idMensaje")
                .setParameter("idMensaje", idMensaje)

                .list();

        if (mensajeList != null && mensajeList.size() > 0) {
            mensaje = mensajeList.get(0);
        }
        return mensaje;
    }

    @Transactional
    public List<Mensaje> getMensajeAll() {

        List<Mensaje> mensajeList = new ArrayList<Mensaje>();

        mensajeList = sessionFactory.getCurrentSession()
                .createQuery("select m from Mensaje m").list();


        return mensajeList;
    }
}
