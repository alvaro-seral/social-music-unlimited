package model;

public class GruposUsuariosVO {	
	
	private String usuario;
	private String grupo;
	
	// constructor
	
	public GruposUsuariosVO (String _usuario, String _grupo) {
		this.usuario = _usuario;
		this.grupo = _grupo;
	}
	
	// getters
	
	public String getUsuario() {
		return usuario;
	}
	
	public String getGrupo() {
		return grupo;
	}
	
	// setters
	
	public void setUsuario(String _usuario) {
		this.usuario = _usuario;
	}
	
	public void setGrupo(String _grupo) {
		this.usuario = _grupo;
	}
	
}
