/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.aserriosbd.modelo;

import java.sql.Date;
import java.time.LocalDate;

/**
 *
 * @author nicol
 */
public class Mantenimiento {
    private int id;                // ID del mantenimiento
    private String idOperario;     // ID del operario
    private int codigoMaquinaria;  // Código de la maquinaria
    private String idSecretaria;   // ID de la secretaria
    private String detalle;        // Detalle del mantenimiento
    private LocalDate fecha;            // Fecha del mantenimiento
    public Mantenimiento(){
        
    }
    public Mantenimiento(int id, String idOperario, int codigoMaquinaria, String idSecretaria, String detalle, LocalDate fecha) {
        this.id = id;
        this.idOperario = idOperario;
        this.codigoMaquinaria = codigoMaquinaria;
        this.idSecretaria = idSecretaria;
        this.detalle = detalle;
        this.fecha = fecha;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdOperario() {
        return idOperario;
    }

    public void setIdOperario(String idOperario) {
        this.idOperario = idOperario;
    }

    public int getCodigoMaquinaria() {
        return codigoMaquinaria;
    }

    public void setCodigoMaquinaria(int codigoMaquinaria) {
        this.codigoMaquinaria = codigoMaquinaria;
    }

    public String getIdSecretaria() {
        return idSecretaria;
    }

    public void setIdSecretaria(String idSecretaria) {
        this.idSecretaria = idSecretaria;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
