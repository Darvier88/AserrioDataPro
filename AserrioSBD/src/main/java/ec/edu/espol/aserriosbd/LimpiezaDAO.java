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


public class LimpiezaDAO {
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
            ResultSet resultSet = statement.executeQuery("SELECT * FROM maquinarias");

            while (resultSet.next()) {
                Maquinaria maquinaria = new Maquinaria(
                        resultSet.getInt("codigo"),
                        resultSet.getString("nombre"),
                        resultSet.getString("marca"),
                        resultSet.getDate("fecha")
                );
                maquinariaList.add(maquinaria);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maquinariaList;
    }
}

