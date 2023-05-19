package model;

import java.sql.Date;

// Clase que representa la tabla eventos de la base de datos
public class EventosVO {	
	
	private int id;
	private String empresa;
	private Date fechaEvento;
	private String lugar;
	private String descripcion;
	private int numApuntados;
	private String grupo;
	private String nombre;
	
	// constructor
	
	public EventosVO (int _id, String _empresa, Date _fechaEvento, String _lugar, 
						String _descripcion, int _numApuntados, String _grupo, String _nombre) {
		this.id = _id;
		this.empresa = _empresa;
		this.fechaEvento = _fechaEvento;
		this.lugar = _lugar;
		this.descripcion = _descripcion;
		this.numApuntados = _numApuntados;
		this.grupo = _grupo;
		this.nombre = _nombre;
	}
	
	// getters
	
	public int getId() {
		return id;
	}
	
	public String getEmpresa() {
		return empresa;
	}
	
	public Date getFechaEvento() {
		return fechaEvento;
	}
	
	public String getLugar() {
		return lugar;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public int getNumApuntados() {
		return numApuntados;
	}
	
	public String getGrupo() {
		return grupo;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	// setters
	
	public void setId(int _id) {
		this.id = _id;
	}
	
	public void setEmpresa(String _empresa) {
		this.empresa = _empresa;
	}
	
	public void setFechaEvento(Date _fechaEvento) {
		this.fechaEvento = _fechaEvento;
	}
	
	public void setLugar(String _lugar) {
		this.lugar = _lugar;
	}
	
	public void setDescripcion(String _descripcion) {
		this.descripcion = _descripcion;
	}
	
	public void setNumApuntados(int _numApuntados) {
		this.numApuntados = _numApuntados;
	}
	
	public void setGrupo(String _grupo) {
		this.grupo = _grupo;
	}
	
	public void setNombre(String _nombre) {
		this.nombre = _nombre;
	}
	
}
