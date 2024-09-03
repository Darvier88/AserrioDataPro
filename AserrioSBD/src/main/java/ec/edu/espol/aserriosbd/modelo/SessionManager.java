/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.aserriosbd.modelo;

/**
 *
 * @author nicol
 */
public class SessionManager {
    private static SessionManager instance;
    private String usuario;
    private String contraseña;

    // Constructor privado para evitar instanciación directa
    private SessionManager() {
        this.usuario = null;
        this.contraseña = null;
    }

    // Método para obtener la instancia única de SessionManager
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    // Métodos getter y setter para usuario y contraseña
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    // Método para limpiar la sesión
    public void clearSession() {
        this.usuario = null;
        this.contraseña = null;
    }
    
}
