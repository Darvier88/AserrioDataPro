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
public class ReporteMensualMantenimiento {
    private String empleadoNombre;
    private String detalles;
    private LocalDate fecha;
    private String maquinaria;

    public ReporteMensualMantenimiento(String empleadoNombre, String detalles, LocalDate fecha, String maquinaria) {
        this.empleadoNombre = empleadoNombre;
        this.detalles = detalles;
        this.fecha = fecha;
        this.maquinaria = maquinaria;
    }

    public String getEmpleadoNombre() {
        return empleadoNombre;
    }

    public String getDetalles() {
        return detalles;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getMaquinaria() {
        return maquinaria;
    }
}

