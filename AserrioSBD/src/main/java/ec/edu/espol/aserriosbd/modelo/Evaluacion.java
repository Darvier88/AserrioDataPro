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
    private String calidad;          // Calidad (1-10)
    private String puntualidad;      // Puntualidad (1-15)
    private String detalle;       // Detalle (hasta 15 caracteres)

    public Evaluacion (){
        
    }
    public Evaluacion(int id, String idProveedor, String calidad, String puntualidad, String detalle) {
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

    public String getCalidad() {
        return calidad;
    }

    public void setCalidad(String calidad) {
        this.calidad = calidad;
    }

    public String getPuntualidad() {
        return puntualidad;
    }

    public void setPuntualidad(String puntualidad) {
        this.puntualidad = puntualidad;
    }


    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
        
    }
}
