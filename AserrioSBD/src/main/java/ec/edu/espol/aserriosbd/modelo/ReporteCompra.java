/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.aserriosbd.modelo;

import java.time.LocalDate;

/**
 *
 * @author ASUS VIVOBOOK PRO
 */
public class ReporteCompra {
     private String nombre;
    private LocalDate fechaLlegada;
    private String tipoMaderaNombre;
    private float importe;
    private int cantidad;

    public ReporteCompra(String nombre, LocalDate fechaLlegada, String tipoMaderaNombre, float importe, int cantidad) {
        this.nombre = nombre;
        this.fechaLlegada = fechaLlegada;
        this.tipoMaderaNombre = tipoMaderaNombre;
        this.importe = importe;
        this.cantidad = cantidad;
    }

    // Getters and Setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaLlegada() {
        return fechaLlegada;
    }

    public void setFechaLlegada(LocalDate fechaLlegada) {
        this.fechaLlegada = fechaLlegada;
    }

    public String getTipoMaderaNombre() {
        return tipoMaderaNombre;
    }

    public void setTipoMaderaNombre(String tipoMaderaNombre) {
        this.tipoMaderaNombre = tipoMaderaNombre;
    }

    public float getImporte() {
        return importe;
    }

    public void setImporte(float importe) {
        this.importe = importe;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
