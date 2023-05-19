package model;

// Clase que representa la tabla comentarios_event de la base de datos
public class ComentariosEventVO {
	private int id;
    private String nombre;
    private String descripcion;
    private int evento;
    
    // constructor
    
    public ComentariosEventVO (int id_, String nombre_, String descripcion_, int evento_) {
        this.id = id_;
        this.nombre = nombre_;
        this.descripcion = descripcion_;
        this.evento = evento_;
    }
    
    // getters
    
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getEvento() {
        return evento;
    }

    // setters
    
    public void setId(int id_) {
        this.id = id_;
    }

    public void setNombre(String nombre_) {
        this.nombre = nombre_;
    }

    public void setDescripcion(String descripcion_) {
        this.descripcion = descripcion_;
    }
    
    public void setEvento(int evento_) {
        this.evento = evento_;
    }
}
