package model;

public class GruposVO {

    private String nombre;
    private int numParticipantes;
    
    // constructor
    
    public GruposVO (String nombre_, int numParticipantes_) {
        this.nombre = nombre_;
        this.numParticipantes = numParticipantes_;
    }
    
    // getters
    
    public String getNombre() {
        return nombre;
    }
    
    public int getNumParticipantes() {
        return numParticipantes;
    }

    // setters
    
    public void setNombre(String nombre_) {
        this.nombre = nombre_;
    }
    
    public void setNumParticipantes(int numParticipantes_) {
        this.numParticipantes = numParticipantes_;
    }
    
}