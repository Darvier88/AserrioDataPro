/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.aserriosbd;

/**
 *
 * @author ASUS VIVOBOOK PRO
 */
public class TipoMadera {
    private String id;
    private String nombre;
    private float precioUnitario;
    private String condicionAmbiental;

    public TipoMadera(String id, String nombre, float precioUnitario, String condicionAmbiental) {
        this.id = id;
        this.nombre = nombre;
        this.precioUnitario = precioUnitario;
        this.condicionAmbiental = condicionAmbiental;
    }
    public TipoMadera(){
        
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(float precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public String getCondicionAmbiental() {
        return condicionAmbiental;
    }

    public void setCondicionAmbiental(String condicionAmbiental) {
        this.condicionAmbiental = condicionAmbiental;
    }
}

