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
public class LoteMadera {
    private int id;
    private String idProveedor;
    private String proveedorNombre; // Nuevo campo para el nombre del proveedor
    private String idSecretaria;
    private String secretariaNombre; // Nuevo campo para el nombre de la secretaria
    private float precio;
    private LocalDate fechaLlegada;

    // Constructor actualizado para incluir los nombres
    public LoteMadera(int id, String idProveedor, String proveedorNombre, String idSecretaria, String secretariaNombre, float precio, LocalDate fechaLlegada) {
        this.id = id;
        this.idProveedor = idProveedor;
        this.proveedorNombre = proveedorNombre;
        this.idSecretaria = idSecretaria;
        this.secretariaNombre = secretariaNombre;
        this.precio = precio;
        this.fechaLlegada = fechaLlegada;
    }
    public LoteMadera(){
        
    }

    // Getters y setters para cada campo
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

    public String getProveedorNombre() {
        return proveedorNombre;
    }

    public void setProveedorNombre(String proveedorNombre) {
        this.proveedorNombre = proveedorNombre;
    }

    public String getIdSecretaria() {
        return idSecretaria;
    }

    public void setIdSecretaria(String idSecretaria) {
        this.idSecretaria = idSecretaria;
    }

    public String getSecretariaNombre() {
        return secretariaNombre;
    }

    public void setSecretariaNombre(String secretariaNombre) {
        this.secretariaNombre = secretariaNombre;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public LocalDate getFechaLlegada() {
        return fechaLlegada;
    }

    public void setFechaLlegada(LocalDate fechaLlegada) {
        this.fechaLlegada = fechaLlegada;
    }
}