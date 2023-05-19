package model;

// Clase que representa la tabla publicaciones de la base de datos
public class PublicacionesVO {

	private int id;
    private String nombre;
    private String descripcion;
    private String grupo;
    
    // constructor
    
    public PublicacionesVO (int id_, String nombre_, String descripcion_, String grupo_) {
        this.id = id_;
        this.nombre = nombre_;
        this.descripcion = descripcion_;
        this.grupo = grupo_;
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

    public String getGrupo() {
        return grupo;
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
    
    public void setGrupo(String grupo_) {
        this.grupo = grupo_;
    }
	
}
