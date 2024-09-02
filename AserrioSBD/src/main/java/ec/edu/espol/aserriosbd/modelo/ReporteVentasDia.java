/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.aserriosbd.modelo;

/**
 *
 * @author ASUS VIVOBOOK PRO
 */
public class ReporteVentasDia {
    private String nombre;
    private int unidades;
    private float totalProdu;

    public ReporteVentasDia(String nombre, int unidades, float totalProdu) {
        this.nombre = nombre;
        this.unidades = unidades;
        this.totalProdu = totalProdu;
    }

    public String getNombre() {
        return nombre;
    }

    public int getUnidades() {
        return unidades;
    }

    public float getTotalProdu() {
        return totalProdu;
    }
}
