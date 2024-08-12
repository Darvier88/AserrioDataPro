/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.aserriosbd;

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
}

