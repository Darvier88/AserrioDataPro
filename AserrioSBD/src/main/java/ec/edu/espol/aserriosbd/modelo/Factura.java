/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.aserriosbd.modelo;

/**
 *
 * @author ASUS VIVOBOOK PRO
 */

import java.time.LocalDate;
import java.time.LocalTime;

public class Factura {
    private int id;
    private String idSecretaria;
    private String idCliente;
    private LocalDate fecha;
    private LocalTime hora;
    private String direccionLocal;
    private String metodoPago;
    private float subtotalSinImpuestos;
    private float subtotal0Porcent;
    private float valorTotal;
    public Factura(){
        
    }

    // Constructor
    public Factura(int id, String idSecretaria, String idCliente, LocalDate fecha, LocalTime hora, String direccionLocal,
                   String metodoPago, float subtotalSinImpuestos, float subtotal0Porcent, float valorTotal) {
        this.id = id;
        this.idSecretaria = idSecretaria;
        this.idCliente = idCliente;
        this.fecha = fecha;
        this.hora = hora;
        this.direccionLocal = direccionLocal;
        this.metodoPago = metodoPago;
        this.subtotalSinImpuestos = subtotalSinImpuestos;
        this.subtotal0Porcent = subtotal0Porcent;
        this.valorTotal = valorTotal;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdSecretaria() {
        return idSecretaria;
    }

    public void setIdSecretaria(String idSecretaria) {
        this.idSecretaria = idSecretaria;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public String getDireccionLocal() {
        return direccionLocal;
    }

    public void setDireccionLocal(String direccionLocal) {
        this.direccionLocal = direccionLocal;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public float getSubtotalSinImpuestos() {
        return subtotalSinImpuestos;
    }

    public void setSubtotalSinImpuestos(float subtotalSinImpuestos) {
        this.subtotalSinImpuestos = subtotalSinImpuestos;
    }

    public float getSubtotal0Porcent() {
        return subtotal0Porcent;
    }

    public void setSubtotal0Porcent(float subtotal0Porcent) {
        this.subtotal0Porcent = subtotal0Porcent;
    }

    public float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(float valorTotal) {
        this.valorTotal = valorTotal;
    }

}

