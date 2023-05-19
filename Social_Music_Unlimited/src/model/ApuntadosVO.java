package model;

// Clase que representa la tabla apuntados de la base de datos
public class ApuntadosVO {	
	
	private String usuario;
	private int evento;
	
	// constructor
	
	public ApuntadosVO (String _usuario, int _evento) {
		this.usuario = _usuario;
		this.evento = _evento;
	}
	
	// getters
	
	public String getUsuario() {
		return usuario;
	}
	
	public int getEvento() {
		return evento;
	}
	
	// setters
	
	public void setUsuario(String _usuario) {
		this.usuario = _usuario;
	}
	
	public void setEvento(int _evento) {
		this.evento = _evento;
	}
	
}
