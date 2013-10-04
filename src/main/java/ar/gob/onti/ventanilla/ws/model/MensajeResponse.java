package ar.gob.onti.ventanilla.ws.model;

public class MensajeResponse {
	
	private Integer codError;
	private Integer codTransaccion;
	private String descripError;

	public Integer getCodError() {
		return codError;
	}
	public void setCodError(Integer codError) {
		this.codError = codError;
	}
	public Integer getCodTransaccion() {
		return codTransaccion;
	}
	public void setCodTransaccion(Integer codTransaccion) {
		this.codTransaccion = codTransaccion;
	}
	public String getDescripError() {
		return descripError;
	}
	public void setDescripError(String descripError) {
		this.descripError = descripError;
	}



}
