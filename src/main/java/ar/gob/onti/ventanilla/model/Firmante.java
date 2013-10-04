package ar.gob.onti.ventanilla.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * Esta clase modela los usuarios del sistema
 *
 * @author Juan Manuel Carrascal
 */

@Entity
@Table(name = "Firmante")
public class Firmante {

    @Id
    private Integer idFirmante;
    private Boolean usaTSA;
    private String userTSA;
    private String passwordTSA;
    private Timestamp ultimoAcceso;
    private String rutaKeyStore;
    private String passwordKeyStore;
    private String razon;
    private String locacion;
    private String urlTSA;


    public Integer getIdFirmante() {
        return idFirmante;
    }

    public void setIdFirmante(Integer idFirmante) {
        this.idFirmante = idFirmante;
    }

    public Boolean getUsaTSA() {
        return usaTSA;
    }

    public void setUsaTSA(Boolean usaTSA) {
        this.usaTSA = usaTSA;
    }

    public String getUserTSA() {
        return userTSA;
    }

    public void setUserTSA(String userTSA) {
        this.userTSA = userTSA;
    }

    public String getPasswordTSA() {
        return passwordTSA;
    }

    public void setPasswordTSA(String passwordTSA) {
        this.passwordTSA = passwordTSA;
    }

    public Timestamp getUltimoAcceso() {
        return ultimoAcceso;
    }

    public void setUltimoAcceso(Timestamp ultimoAcceso) {
        this.ultimoAcceso = ultimoAcceso;
    }

    public String getPasswordKeyStore() {
        return passwordKeyStore;
    }

    public void setPasswordKeyStore(String passwordKeyStore) {
        this.passwordKeyStore = passwordKeyStore;
    }

    public String getRazon() {
        return razon;
    }

    public void setRazon(String razon) {
        this.razon = razon;
    }

    public String getLocacion() {
        return locacion;
    }

    public void setLocacion(String locacion) {
        this.locacion = locacion;
    }

    public String getUrlTSA() {
        return urlTSA;
    }

    public void setUrlTSA(String urlTSA) {
        this.urlTSA = urlTSA;
    }

    public String getRutaKeyStore() {
        return rutaKeyStore;
    }

    public void setRutaKeyStore(String rutaKeyStore) {
        this.rutaKeyStore = rutaKeyStore;
    }


}
