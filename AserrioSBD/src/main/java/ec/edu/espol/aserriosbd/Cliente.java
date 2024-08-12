/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.aserriosbd;

/**
 *
 * @author ASUS VIVOBOOK PRO
 */
public class Cliente {
    private String cedula;
    private String nombre;
    private String direccion;
    private Integer numContacto;
    private String correoContacto;

    // Constructor
    public Cliente(){
        
    }
    public Cliente(String cedula, String nombre, String direccion, Integer numContacto, String correoContacto) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.direccion = direccion;
        this.numContacto = numContacto;
        this.correoContacto = correoContacto;
    }

    // Getters and Setters
    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getNumContacto() {
        return numContacto;
    }

    public void setNumContacto(Integer numContacto) {
        this.numContacto = numContacto;
    }

    public String getCorreoContacto() {
        return correoContacto;
    }

    public void setCorreoContacto(String correoContacto) {
        this.correoContacto = correoContacto;
    }
}
