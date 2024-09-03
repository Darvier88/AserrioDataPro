/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.aserriosbd.modelo;

/**
 *
 * @author nicol
 */
public class RolDePagos {
    private int id;                  // ID del rol de pagos
    private String idEmpleado;       // ID del empleado
    private String rol;              // Rol del empleado (Asistente, Operario, Secretaria)
    private int diasLaborados;       // Días laborados
    private float sueldo;            // Sueldo
    private float horasExtras;       // Horas extras
    private float totalIngresos;     // Total ingresos
    private float egresos;           // Egresos
    private float anticipos;         // Anticipos
    private float totalEgresos;      // Total egresos
    private float liquidoARecibir;   // Líquido a recibir
    public RolDePagos(){
        
    }
    public RolDePagos(int id, String idEmpleado, String rol, int diasLaborados, float sueldo,
                      float horasExtras, float totalIngresos, float egresos, float anticipos,
                      float totalEgresos, float liquidoARecibir) {
        this.id = id;
        this.idEmpleado = idEmpleado;
        this.rol = rol;
        this.diasLaborados = diasLaborados;
        this.sueldo = sueldo;
        this.horasExtras = horasExtras;
        this.totalIngresos = totalIngresos;
        this.egresos = egresos;
        this.anticipos = anticipos;
        this.totalEgresos = totalEgresos;
        this.liquidoARecibir = liquidoARecibir;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public int getDiasLaborados() {
        return diasLaborados;
    }

    public void setDiasLaborados(int diasLaborados) {
        this.diasLaborados = diasLaborados;
    }

    public float getSueldo() {
        return sueldo;
    }

    public void setSueldo(float sueldo) {
        this.sueldo = sueldo;
    }

    public float getHorasExtras() {
        return horasExtras;
    }

    public void setHorasExtras(float horasExtras) {
        this.horasExtras = horasExtras;
    }

    public float getTotalIngresos() {
        return totalIngresos;
    }

    public void setTotalIngresos(float totalIngresos) {
        this.totalIngresos = totalIngresos;
    }

    public float getEgresos() {
        return egresos;
    }

    public void setEgresos(float egresos) {
        this.egresos = egresos;
    }

    public float getAnticipos() {
        return anticipos;
    }

    public void setAnticipos(float anticipos) {
        this.anticipos = anticipos;
    }

    public float getTotalEgresos() {
        return totalEgresos;
    }

    public void setTotalEgresos(float totalEgresos) {
        this.totalEgresos = totalEgresos;
    }

    public float getLiquidoARecibir() {
        return liquidoARecibir;
    }

    public void setLiquidoARecibir(float liquidoARecibir) {
        this.liquidoARecibir = liquidoARecibir;
    }
}
