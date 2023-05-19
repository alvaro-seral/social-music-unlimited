package model;

// Clase que representa la tabla comentarios_publi de la base de datos
public class ComentariosPubliVO {
	private int id;
    private String nombre;
    private String descripcion;
    private int publicacion;
    
    // constructor
    
    public ComentariosPubliVO (int id_, String nombre_, String descripcion_, int publicacion_) {
        this.id = id_;
        this.nombre = nombre_;
        this.descripcion = descripcion_;
        this.publicacion = publicacion_;
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

    public int getPublicacion() {
        return publicacion;
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
    
    public void setPublicacion(int publicacion_) {
        this.publicacion = publicacion_;
    }
}
