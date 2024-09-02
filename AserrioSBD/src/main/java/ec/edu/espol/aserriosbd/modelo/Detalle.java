/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.aserriosbd.modelo;

/**
 *
 * @author nicol
 */
public class Detalle {
    private int idFactura;
    private String idProducto;
    private int cantidad;
    private float totalProducto;
    private String detalleAdic;
    
    public Detalle(){
        
    }
    // Constructor
    public Detalle(int idFactura, String idProducto, int cantidad, float totalProducto, String detalleAdic) {
        this.idFactura = idFactura;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.totalProducto = totalProducto;
        this.detalleAdic = detalleAdic;
    }

    // Getters y Setters
    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getTotalProducto() {
        return totalProducto;
    }

    public void setTotalProducto(float totalProducto) {
        this.totalProducto = totalProducto;
    }

    public String getDetalleAdic() {
        return detalleAdic;
    }

    public void setDetalleAdic(String detalleAdic) {
        this.detalleAdic = detalleAdic;
    }

    @Override
    public String toString() {
        return "Detalle{" +
                "idFactura=" + idFactura +
                ", idProducto=" + idProducto +
                ", cantidad=" + cantidad +
                ", totalProducto=" + totalProducto +
                ", detalleAdic=" + detalleAdic +
                '}';
    }
}
