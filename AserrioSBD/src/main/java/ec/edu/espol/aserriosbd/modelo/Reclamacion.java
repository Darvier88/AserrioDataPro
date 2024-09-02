/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.aserriosbd.modelo;

/**
 *
 * @author nicol
 */
public class Reclamacion {
    private int id;
    private int idSecretaria;
    private int idCliente;
    private String descripcion;

    // Constructor
    public Reclamacion(int id, int idSecretaria, int idCliente, String descripcion) {
        this.id = id;
        this.idSecretaria = idSecretaria;
        this.idCliente = idCliente;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdSecretaria() {
        return idSecretaria;
    }

    public void setIdSecretaria(int idSecretaria) {
        this.idSecretaria = idSecretaria;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Multa{" +
                "id=" + id +
                ", idSecretaria=" + idSecretaria +
                ", idCliente=" + idCliente +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
