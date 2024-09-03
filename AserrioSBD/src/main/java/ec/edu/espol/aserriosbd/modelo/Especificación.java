/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.aserriosbd.modelo;

/**
 *
 * @author nicol
 */
public class Especificación {
    private int idLote;           // ID del lote (FK)
    private String idMadera;      // ID de la madera (FK)
    private int importe;          // Importe
    private int cantidad;         // Cantidad
    
    public Especificación() {
        // Inicialización de variables si es necesario
    }
    
    public Especificación(int idLote, String idMadera, int importe, int cantidad) {
        this.idLote = idLote;
        this.idMadera = idMadera;
        this.importe = importe;
        this.cantidad = cantidad;
    }
    

    // Getters y Setters
    public int getIdLote() {
        return idLote;
    }

    public void setIdLote(int idLote) {
        this.idLote = idLote;
    }

    public String getIdMadera() {
        return idMadera;
    }

    public void setIdMadera(String idMadera) {
        this.idMadera = idMadera;
    }

    public int getImporte() {
        return importe;
    }

    public void setImporte(int importe) {
        this.importe = importe;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
