package model;

// Clase que representa la tabla usuarios de la base de datos
public class UsuariosVO {

	private String nombre;
	private String correo;
	private String contrasenya;
	private String tipo;
	
	// constructor
	
	public UsuariosVO (String _nombre, String _correo, String _contrasenya, String _tipo) {
		this.nombre = _nombre;
		this.correo = _correo;
		this.contrasenya = _contrasenya;
		this.tipo = _tipo;
	}
	
	// getters
	
	public String getNombre() {
		return nombre;
	}
	
	public String getCorreo() {
		return correo;
	}
	
	public String getContrasenya() {
		return contrasenya;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	// setters
	
	public void setNombre(String _nombre) {
		this.nombre = _nombre;
	}
	
	public void setCorreo(String _correo) {
		this.correo = _correo;
	}
	
	public void setContrasenya(String _contrasenya) {
		this.contrasenya = _contrasenya;
	}
	
	public void setTipo(String _tipo) {
		this.tipo = _tipo;
	}
	
}
