package ar.gob.onti.ventanilla.ws.model;

/**
 * @author juan.carrascal
 */
public class VentanillaRequest {

    private byte[] documento;
    private String usuario;
    private String password;


    public byte[] getDocumento() {
        return documento;
    }

    public void setDocumento(byte[] documento) {
        this.documento = documento;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
