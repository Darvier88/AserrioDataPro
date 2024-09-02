/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.aserriosbd.modelo;

/**
 *
 * @author ASUS VIVOBOOK PRO
 */
public class Proveedor {
    private String cedula;
    private String nombre;
    private Integer telefono;

    public Proveedor(String cedula, String nombre, Integer telefono) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.telefono = telefono;
    }
    public Proveedor(){
        
    }

    // Getters y setters
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

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }
}

