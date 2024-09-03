/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.aserriosbd.modelo;

import java.util.Date;

/**
 *
 * @author nicol
 */
public class Maquinaria {
   private int codigo;           
    private String nombre;        
    private String marca;          
    private Date fechaAdquisicion; 

    public Maquinaria(int codigo, String nombre, String marca, Date fechaAdquisicion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.marca = marca;
        this.fechaAdquisicion = fechaAdquisicion;
    }

    // Getters y Setters
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Date getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public void setFechaAdquisicion(Date fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
    }
}
