/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.aserriosbd.modelo;
import java.sql.Date;
import java.time.LocalDate;

/**
 *
 * @author nicol
 */
public class RegistroDeLimpieza {
    private String idAsistente;    // ID del asistente
    private int idLimpieza;        // ID de la limpieza
    private String idSecretaria;   // ID de la secretaria
    private LocalDate fecha;            // Fecha del registro
    public RegistroDeLimpieza(){
        
    }
    public RegistroDeLimpieza( String idAsistente, int idLimpieza, String idSecretaria, LocalDate  fecha)  {
        this.idAsistente = idAsistente;
        this.idLimpieza = idLimpieza;
        this.idSecretaria = idSecretaria;
        this.fecha = fecha;
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

    public LocalDate  getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    
}
