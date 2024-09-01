/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.aserriosbd.modelo;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author Zambrano
 */
public class Empleado {
      private String id;
    private String nombre;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private LocalDate fechaCapacitacion;
    private String tipoCapacitacion;

    // Constructor
    public Empleado() {
        
    }

    public Empleado(String id, String nombre, LocalTime horaInicio, LocalTime horaFin, LocalDate fechaCapacitacion, String tipoCapacitacion) {
        this.id = id;
        this.nombre = nombre;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.fechaCapacitacion = fechaCapacitacion;
        this.tipoCapacitacion = tipoCapacitacion;
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

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public LocalDate getFechaCapacitacion() {
        return fechaCapacitacion;
    }

    public void setFechaCapacitacion(LocalDate fechaCapacitacion) {
        this.fechaCapacitacion = fechaCapacitacion;
    }

    public String getTipoCapacitacion() {
        return tipoCapacitacion;
    }

    public void setTipoCapacitacion(String tipoCapacitacion) {
        this.tipoCapacitacion = tipoCapacitacion;
    }
}
