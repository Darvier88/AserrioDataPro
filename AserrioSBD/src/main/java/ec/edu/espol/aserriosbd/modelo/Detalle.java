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
    private int idProducto;
    private int cantidad;
    private float totalProducto;
    private boolean detalleAdic;

    // Constructor
    public Detalle(int idFactura, int idProducto, int cantidad, float totalProducto, boolean detalleAdic) {
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

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
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

    public boolean isDetalleAdic() {
        return detalleAdic;
    }

    public void setDetalleAdic(boolean detalleAdic) {
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
