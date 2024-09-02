/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.aserriosbd.modelo;

/**
 *
 * @author nicol
 */
public class Evaluacion {
    private int id;               // ID de la evaluación
    private String idProveedor;   // ID del proveedor (FK)
    private int calidad;          // Calidad (1-10)
    private int puntualidad;      // Puntualidad (1-15)
    private String detalle;       // Detalle (hasta 15 caracteres)

    public Evaluacion(int id, String idProveedor, int calidad, int puntualidad, String detalle) {
        this.id = id;
        this.idProveedor = idProveedor;
        this.calidad = calidad;
        this.puntualidad = puntualidad;
        this.detalle = detalle;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(String idProveedor) {
        this.idProveedor = idProveedor;
    }

    public int getCalidad() {
        return calidad;
    }

    public void setCalidad(int calidad) {
        if (calidad >= 1 && calidad <= 10) {
            this.calidad = calidad;
        } else {
            throw new IllegalArgumentException("La calidad debe estar entre 1 y 10.");
        }
    }

    public int getPuntualidad() {
        return puntualidad;
    }

    public void setPuntualidad(int puntualidad) {
        if (puntualidad >= 1 && puntualidad <= 15) {
            this.puntualidad = puntualidad;
        } else {
            throw new IllegalArgumentException("La puntualidad debe estar entre 1 y 15.");
        }
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
        
    }
}
