/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.aserriosbd.modelo;

/**
 *
 * @author nicol
 */
public class Producto {
    private String id;
    private String nombre;
    private float precioUnitario;
    private String calidad;
    private String condicAmbiental;
    private int tiempoAlmacenamiento;
    private String dimension;
    private String descripcion;

    public Producto() {
        // Inicializar atributos si es necesario
    }
    public Producto(String id, String nombre, float precioUnitario, String calidad, String condicAmbiental, int tiempoAlmacenamiento, String dimension, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.precioUnitario = precioUnitario;
        this.calidad = calidad;
        this.condicAmbiental = condicAmbiental;
        this.tiempoAlmacenamiento = tiempoAlmacenamiento;
        this.dimension = dimension;
        this.descripcion = descripcion;
    }

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

    public String getCalidad() {
        return calidad;
    }

    public void setCalidad(String calidad) {
        this.calidad = calidad;
    }

    public String getCondicAmbiental() {
        return condicAmbiental;
    }

    public void setCondicAmbiental(String condicAmbiental) {
        this.condicAmbiental = condicAmbiental;
    }

    public int getTiempoAlmacenamiento() {
        return tiempoAlmacenamiento;
    }

    public void setTiempoAlmacenamiento(int tiempoAlmacenamiento) {
        this.tiempoAlmacenamiento = tiempoAlmacenamiento;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}

