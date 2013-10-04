package ar.gob.onti.ventanilla.model;

import javax.persistence.*;

/**
 * Esta clase modela los usuarios del sistema
 *
 * @author Juan Manuel Carrascal
 */

@Entity
@Table(name = "UsuarioCertificado")
public class UsuarioCertificado {

    @Id
    private Long idUsuarioCertificado;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Usuario.class)
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;

    @OneToOne(fetch = FetchType.EAGER, targetEntity = CertificadoConfiable.class)
    @JoinColumn(name = "idCertificadoConfiable")
    private CertificadoConfiable certificadoConfiable;


    public Long getIdUsuarioCertificado() {
        return idUsuarioCertificado;
    }

    public void setIdUsuarioCertificado(Long idUsuarioCertificado) {
        this.idUsuarioCertificado = idUsuarioCertificado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }


    public CertificadoConfiable getCertificadoConfiable() {
        return certificadoConfiable;
    }

    public void setCertificadoConfiable(CertificadoConfiable certificadoConfiable) {
        this.certificadoConfiable = certificadoConfiable;
    }

}
