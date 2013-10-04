package ar.gob.onti.ventanilla.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Esta clase modela los parametros de la aplicaci�n
 *
 * @author Juan Manuel Carrascal
 */

@Entity
@Table(name = "CertificadoConfiable")
public class CertificadoConfiable {

    @Id
    private Long idCertificadoConfiable;
    private String descrip;
    private byte[] certificado;

    public Long getIdCertificadoConfiable() {
        return idCertificadoConfiable;
    }

    public void setIdCertificadoConfiable(Long idCertificadoConfiable) {
        this.idCertificadoConfiable = idCertificadoConfiable;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public byte[] getCertificado() {
        return certificado;
    }

    public void setCertificado(byte[] certificado) {
        this.certificado = certificado;
    }


}
