/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.aserriosbd.modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author ASUS VIVOBOOK PRO
 */
public class ReportesDAO {
    public static void crearOActualizarVista1() {
        String sql = "CREATE OR REPLACE VIEW ReporteCompras AS " +
                "SELECT p.nombre, l.fecha_llegada, t.nombre AS name, e.importe, e.cantidad " +
                "FROM Proveedor p " +
                "JOIN Lote_madera l ON p.cedula = l.ID_proveedor " +
                "JOIN Especificacion e ON e.id_lote = l.id " +
                "JOIN Tipo_de_madera t ON t.id = e.ID_madera " +
                "WHERE MONTH(l.fecha_llegada) = MONTH(NOW()) AND YEAR(l.fecha_llegada) = YEAR(NOW()) " +
                "ORDER BY l.fecha_llegada;";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);
            System.out.println("Vista actualizada exitosamente.");

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al actualizar la vista.");
        }
    }
    public static ObservableList<ReporteCompra> getReporteComprasList() {
        ObservableList<ReporteCompra> reporteComprasList = FXCollections.observableArrayList();

        String query = "SELECT nombre, fecha_llegada, name, importe, cantidad " +
                       "FROM ReporteCompras";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                ReporteCompra reporteCompra = new ReporteCompra(
                        resultSet.getString("nombre"),
                        resultSet.getDate("fecha_llegada").toLocalDate(),
                        resultSet.getString("name"),
                        resultSet.getFloat("importe"),
                        resultSet.getInt("cantidad")
                );
                reporteComprasList.add(reporteCompra);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reporteComprasList;
    }
     // Método combinado que actualiza la vista y luego obtiene los datos
    public static ObservableList<ReporteCompra> obtenerDatosActualizados() {
        // Actualiza o crea la vista
        crearOActualizarVista1();

        // Obtiene los datos de la vista
        return getReporteComprasList();
    }
    public static void crearOActualizarVistaVentasDia() {
        String sql = "CREATE OR REPLACE VIEW ReporteVentasDia AS " +
                "SELECT p.nombre, d.unidades, SUM(d.totalProdu) AS totalProdu " +
                "FROM Producto p " +
                "JOIN Detalle d ON p.id = d.id_producto " +
                "JOIN Factura f ON f.id = d.id_factura " +
                "WHERE f.fecha = CURDATE() " +
                "GROUP BY p.nombre, d.unidades;";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);
            System.out.println("Vista ReporteVentasDia actualizada exitosamente.");

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al actualizar la vista ReporteVentasDia.");
        }
    }
    public static ObservableList<ReporteVentasDia> getReporteVentasDiaList() {
        ObservableList<ReporteVentasDia> reporteVentasDiaList = FXCollections.observableArrayList();

        String query = "SELECT nombre, unidades, totalProdu " +
                       "FROM ReporteVentasDia";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                ReporteVentasDia reporteVentasDia = new ReporteVentasDia(
                        resultSet.getString("nombre"),
                        resultSet.getInt("unidades"),
                        resultSet.getFloat("totalProdu")
                );
                reporteVentasDiaList.add(reporteVentasDia);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reporteVentasDiaList;
    }
    public static ObservableList<ReporteVentasDia> obtenerDatosVentasDiaActualizados() {
        // Actualiza o crea la vista
        crearOActualizarVistaVentasDia();

        // Obtiene los datos de la vista
        return getReporteVentasDiaList();
    }
    public static void crearOActualizarVistaMensualMantenimiento() {
        String sql = "CREATE OR REPLACE VIEW ReporteMensualDeMantenimiento AS " +
                "SELECT e.nombre AS empleado_nombre, m.detalles, m.fecha, ma.nombre AS maquinaria " +
                "FROM empleado e " +
                "JOIN operario o USING(id) " +
                "JOIN mantenimiento m ON m.id_operario = o.id " +
                "JOIN maquinaria ma ON ma.codigo = m.codigo_maquinaria " +
                "WHERE MONTH(m.fecha) = MONTH(NOW()) AND YEAR(m.fecha) = YEAR(NOW());";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);
            System.out.println("Vista ReporteMensualDeMantenimiento actualizada exitosamente.");

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al actualizar la vista ReporteMensualDeMantenimiento.");
        }
    }
    public static ObservableList<ReporteMensualMantenimiento> getReporteMensualMantenimientoList() {
        ObservableList<ReporteMensualMantenimiento> reporteMensualMantenimientoList = FXCollections.observableArrayList();

        String query = "SELECT empleado_nombre, detalles, fecha, maquinaria " +
                       "FROM ReporteMensualDeMantenimiento";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                ReporteMensualMantenimiento reporte = new ReporteMensualMantenimiento(
                        resultSet.getString("empleado_nombre"),
                        resultSet.getString("detalles"),
                        resultSet.getDate("fecha").toLocalDate(),
                        resultSet.getString("maquinaria")
                );
                reporteMensualMantenimientoList.add(reporte);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reporteMensualMantenimientoList;
    }
    public static ObservableList<ReporteMensualMantenimiento> obtenerDatosMensualesMantenimientoActualizados() {
        // Actualiza o crea la vista
        crearOActualizarVistaMensualMantenimiento();

        // Obtiene los datos de la vista
        return getReporteMensualMantenimientoList();
    }
    // Método para crear o actualizar la vista
    public static void crearOActualizarVistaAreaDeCorteLimpiezaDiaria() {
        String sql = "CREATE OR REPLACE VIEW AreaDeCorteLimpiezaDiaria AS " +
                "SELECT e.nombre " +
                "FROM empleado e " +
                "JOIN asistente_operario a USING(id) " +
                "JOIN registro r ON a.id = r.id_asistente " +
                "JOIN limpieza l ON r.id_limpieza = l.id " +
                "WHERE l.lugar = 'Área de Corte' AND r.fecha = CURRENT_DATE;";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);
            System.out.println("Vista AreaDeCorteLimpiezaDiaria actualizada exitosamente.");

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al actualizar la vista AreaDeCorteLimpiezaDiaria.");
        }
    }

    // Método para obtener los datos de la vista
    public static ObservableList<LDAC> getAreaDeCorteLimpiezaDiariaList() {
        ObservableList<LDAC> lista = FXCollections.observableArrayList();

        String query = "SELECT nombre FROM AreaDeCorteLimpiezaDiaria";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                LDAC reporte = new LDAC(
                        resultSet.getString("nombre")
                );
                lista.add(reporte);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // Método para actualizar o crear la vista y obtener los datos
    public static ObservableList<LDAC> obtenerDatosAreaDeCorteLimpiezaActualizados() {
        // Actualiza o crea la vista
        crearOActualizarVistaAreaDeCorteLimpiezaDiaria();

        // Obtiene los datos de la vista
        return getAreaDeCorteLimpiezaDiariaList();
    }

}
