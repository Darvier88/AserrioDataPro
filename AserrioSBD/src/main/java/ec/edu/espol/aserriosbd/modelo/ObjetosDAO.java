/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.aserriosbd.modelo;

import ec.edu.espol.aserriosbd.modelo.DatabaseConnection;
import ec.edu.espol.aserriosbd.modelo.TipoMadera;
import ec.edu.espol.aserriosbd.modelo.Proveedor;
import ec.edu.espol.aserriosbd.modelo.Producto;
import ec.edu.espol.aserriosbd.modelo.Maquinaria;
import ec.edu.espol.aserriosbd.modelo.LoteMadera;
import ec.edu.espol.aserriosbd.modelo.Limpieza;
import ec.edu.espol.aserriosbd.modelo.Empleado;
import ec.edu.espol.aserriosbd.modelo.Cliente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
/**
 *
 * @author nicol
 */


public class ObjetosDAO {
    public static ObservableList<Limpieza> getLimpiezaList() {
        ObservableList<Limpieza> limpiezaList = FXCollections.observableArrayList();
        Connection connection = DatabaseConnection.getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM limpieza");

            while (resultSet.next()) {
                Limpieza limpieza = new Limpieza(
                        resultSet.getInt("id"),
                        resultSet.getString("lugar"),
                        resultSet.getDate("fecha")
                );
                limpiezaList.add(limpieza);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return limpiezaList;
    }
    
    public static ObservableList<Maquinaria> getMaquinariaList() {
        ObservableList<Maquinaria> maquinariaList = FXCollections.observableArrayList();
        Connection connection = DatabaseConnection.getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM maquinaria");

            while (resultSet.next()) {
                Maquinaria maquinaria = new Maquinaria(
                        resultSet.getInt("codigo"),
                        resultSet.getString("nombre"),
                        resultSet.getString("marca"),
                        resultSet.getDate("fecha_adqui")
                );
                maquinariaList.add(maquinaria);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maquinariaList;
    }
    
    public static ObservableList<Producto> getProductoList() {
        ObservableList<Producto> productoList = FXCollections.observableArrayList();
        Connection connection = DatabaseConnection.getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM producto");

            while (resultSet.next()) {
                Producto producto = new Producto(
                        resultSet.getString("ID"),
                        resultSet.getString("nombre"),
                        resultSet.getFloat("precioUnitario"),
                        resultSet.getString("calidad"),
                        resultSet.getString("condic_ambiental"),
                        resultSet.getInt("tiempo_almacenamiento"),
                        resultSet.getString("dimension"),
                        resultSet.getString("descripcion")
                );
                productoList.add(producto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productoList;
    }
    public static ObservableList<Cliente> getClienteList() {
        ObservableList<Cliente> clienteList = FXCollections.observableArrayList();
        Connection connection = DatabaseConnection.getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Cliente");

            while (resultSet.next()) {
                Cliente cliente = new Cliente(
                        resultSet.getString("cedula"),
                        resultSet.getString("nombre"),
                        resultSet.getString("direccion"),
                        resultSet.getInt("num_contacto"),
                        resultSet.getString("correo_contacto")
                );
                clienteList.add(cliente);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clienteList;
    }
    public static ObservableList<Proveedor> getProveedorList() {
        ObservableList<Proveedor> proveedorList = FXCollections.observableArrayList();

        String query = "SELECT * FROM proveedor";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Proveedor proveedor = new Proveedor(
                        resultSet.getString("cedula"),
                        resultSet.getString("nombre"),
                        resultSet.getInt("telefono")
                );
                proveedorList.add(proveedor);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return proveedorList;
    }
    public static ObservableList<LoteMadera> getLoteMaderaList() {
    ObservableList<LoteMadera> loteMaderaList = FXCollections.observableArrayList();

    String query = "SELECT lm.id, lm.id_proveedor, lm.id_secretaria, lm.precio, lm.fecha_llegada, " +
                   "p.nombre AS proveedor_nombre, s.nombre AS secretaria_nombre " +
                   "FROM lote_madera lm " +
                   "JOIN proveedor p ON lm.id_proveedor = p.cedula " +
                   "JOIN secretaria s ON lm.id_secretaria = s.ID";

    try (Connection connection = DatabaseConnection.getConnection();
         Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(query)) {

        while (resultSet.next()) {
            LoteMadera loteMadera = new LoteMadera(
                    resultSet.getInt("id"),
                    resultSet.getString("id_proveedor"),
                    resultSet.getString("proveedor_nombre"),
                    resultSet.getString("id_secretaria"),
                    resultSet.getString("secretaria_nombre"),
                    resultSet.getFloat("precio"),
                    resultSet.getDate("fecha_llegada").toLocalDate()
            );
            loteMaderaList.add(loteMadera);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return loteMaderaList;
}

public static ObservableList<Empleado> getEmpleadoList() {
    ObservableList<Empleado> empleadoList = FXCollections.observableArrayList();
    Connection connection = DatabaseConnection.getConnection();

    try {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Empleado");

        while (resultSet.next()) {
            Empleado empleado = new Empleado(
                    resultSet.getString("ID"),
                    resultSet.getString("nombre"),
                    resultSet.getTime("horaInicio").toLocalTime(),
                    resultSet.getTime("horaFin").toLocalTime(),
                    resultSet.getDate("fechaCapacitacion") != null ? resultSet.getDate("fechaCapacitacion").toLocalDate() : null,
                    resultSet.getString("tipoCapacitacion")
            );
            empleadoList.add(empleado);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return empleadoList;
}

    

     public static ObservableList<TipoMadera> getTipoMaderaList() {
        ObservableList<TipoMadera> tipoMaderaList = FXCollections.observableArrayList();

        String query = "SELECT * FROM tipo_de_madera";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                TipoMadera tipoMadera = new TipoMadera(
                        resultSet.getString("id"),
                        resultSet.getString("nombre"),
                        resultSet.getFloat("precio_unitario"),
                        resultSet.getString("condic_ambiental")
                );
                tipoMaderaList.add(tipoMadera);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tipoMaderaList;
    }
     public static ObservableList<Factura> getFacturaList(){
         ObservableList<Factura> facturaList =FXCollections.observableArrayList();
         String query = "SELECT * FROM factura";
         try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Factura factura = new Factura(
                    resultSet.getInt("ID"),
                    resultSet.getString("ID_secretaria"),
                    resultSet.getString("ID_cliente"),
                        resultSet.getDate("fecha").toLocalDate(),
                    resultSet.getTime("hora").toLocalTime(),
                    resultSet.getString("direccion_local"),
                    resultSet.getString("metodo_pago"),
                    resultSet.getFloat("subtotal_sin_impuestos"),
                        resultSet.getFloat("subtotal_0Porcent"),
                        resultSet.getFloat("ValorTotal")
                );
                facturaList.add(factura);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return facturaList;
    }
     
    public static ObservableList<Reclamacion> getReclamacionList() {
        ObservableList<Reclamacion> multaList = FXCollections.observableArrayList();
        Connection connection = DatabaseConnection.getConnection();

        String query = "SELECT * FROM reclamacion"; // Asegúrate que este sea el nombre correcto de la tabla en tu base de datos

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Reclamacion multa = new Reclamacion(
                        resultSet.getInt("ID"),
                        resultSet.getInt("ID_secretaria"),
                        resultSet.getInt("ID_cliente"),
                        resultSet.getString("descripcion")
                );
                multaList.add(multa);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return multaList;
    }
    
    public static ObservableList<Detalle> getDetalleList() {
    ObservableList<Detalle> detalleList = FXCollections.observableArrayList();
    Connection connection = DatabaseConnection.getConnection();

    String query = "SELECT * FROM detalle";  // Asegúrate de que este es el nombre correcto de la tabla

    try (Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(query)) {

        while (resultSet.next()) {
            Detalle detalle = new Detalle(
                    resultSet.getInt("ID_Factura"),
                    resultSet.getString("ID_producto"),
                    resultSet.getInt("unidades"),
                    resultSet.getFloat("totalProdu"),
                    resultSet.getString("detalle_adic")
            );
            detalleList.add(detalle);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return detalleList;
}
}

