/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.aserriosbd.modelo;
import java.sql.Date;

/**
 *
 * @author nicol
 */
public class RegistroDeLimpieza {
    private int id;                // ID del registro
    private String idAsistente;    // ID del asistente
    private int idLimpieza;        // ID de la limpieza
    private String idSecretaria;   // ID de la secretaria
    private Date fecha;            // Fecha del registro

    public RegistroDeLimpieza(int id, String idAsistente, int idLimpieza, String idSecretaria, Date fecha)  {
        this.id = id;
        this.idAsistente = idAsistente;
        this.idLimpieza = idLimpieza;
        this.idSecretaria = idSecretaria;
        this.fecha = fecha;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdAsistente() {
        return idAsistente;
    }

    public void setIdAsistente(String idAsistente) {
        this.idAsistente = idAsistente;
    }

    public int getIdLimpieza() {
        return idLimpieza;
    }

    public void setIdLimpieza(int idLimpieza) {
        this.idLimpieza = idLimpieza;
    }

    public String getIdSecretaria() {
        return idSecretaria;
    }

    public void setIdSecretaria(String idSecretaria) {
        this.idSecretaria = idSecretaria;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
}
