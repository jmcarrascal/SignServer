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
@Table(name = "Mensaje")
public class Mensaje {

    private Integer idMensaje;
    private String descrip;

    @Id
    public Integer getIdMensaje() {
        return idMensaje;
    }

    public void setIdMensaje(Integer idMensaje) {
        this.idMensaje = idMensaje;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }


}
