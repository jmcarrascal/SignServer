package ar.gob.onti.ventanilla.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase modela los usuarios del sistema
 *
 * @author Juan Manuel Carrascal
 */

@Entity
@Table(name = "Usuario")
public class Usuario {

    private Long idUsuario;
    private String userName;
    private String password;
    private Timestamp ultimoAcceso;
    private Boolean activo;
    private List<UsuarioCertificado> usuarioCertificadoList = new ArrayList<UsuarioCertificado>();


    @Id
    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getUltimoAcceso() {
        return ultimoAcceso;
    }

    public void setUltimoAcceso(Timestamp ultimoAcceso) {
        this.ultimoAcceso = ultimoAcceso;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "usuario")
    public List<UsuarioCertificado> getUsuarioCertificadoList() {
        return usuarioCertificadoList;
    }

    public void setUsuarioCertificadoList(
            List<UsuarioCertificado> usuarioCertificadoList) {
        this.usuarioCertificadoList = usuarioCertificadoList;
    }


}
