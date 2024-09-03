/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.aserriosbd.modelo;

import java.util.Date;

/**
 *
 * @author nicol
 */
public class Limpieza {
    private int id;      
    private String lugar;   
    public Limpieza(){
        
    }
    public Limpieza(int id, String lugar) {
        this.id = id;
        this.lugar = lugar;
        
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    
}
