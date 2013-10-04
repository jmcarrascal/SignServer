package ar.gob.onti.ventanilla.dao.impl;

import ar.gob.onti.ventanilla.dao.UsuarioDAO;
import ar.gob.onti.ventanilla.model.Usuario;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UsuarioDAOImpl implements UsuarioDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public Usuario getUserByCredencial(String userName, String password) {
        Usuario usuario = null;
        List<Usuario> usuarioList = new ArrayList<Usuario>();
        if (userName != null && !userName.trim().equals("") && userName != null && !password.trim().equals("")) {
            usuarioList = sessionFactory.getCurrentSession()
                    .createQuery("select u from Usuario u where u.userName = :userName and password = :password and activo = 1")
                    .setParameter("userName", userName)
                    .setParameter("password", password)
                    .list();
        }
        if (usuarioList != null && usuarioList.size() > 0) {
            usuario = usuarioList.get(0);
        }
        return usuario;
    }

}
