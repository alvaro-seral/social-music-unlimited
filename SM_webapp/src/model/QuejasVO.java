package model;

public class QuejasVO {	
	
	private int id;
    private String nombre;
    private String descripcion;
    
    // constructor
    
    public QuejasVO (int id_, String nombre_, String descripcion_) {
        this.id = id_;
        this.nombre = nombre_;
        this.descripcion = descripcion_;
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
	
}
