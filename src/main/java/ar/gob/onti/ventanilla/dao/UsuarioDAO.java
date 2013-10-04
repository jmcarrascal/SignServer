package ar.gob.onti.ventanilla.dao;

import ar.gob.onti.ventanilla.model.Usuario;


public interface UsuarioDAO {


    public Usuario getUserByCredencial(String userName, String password);

}
