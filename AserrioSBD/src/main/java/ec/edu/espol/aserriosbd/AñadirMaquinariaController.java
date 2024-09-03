/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.aserriosbd;

import ec.edu.espol.aserriosbd.modelo.DatabaseConnection;
import ec.edu.espol.aserriosbd.modelo.Maquinaria;
import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author ASUS VIVOBOOK PRO
 */
public class AñadirMaquinariaController implements Initializable {

    @FXML
    private VBox mainContainer;
    @FXML
    private GridPane formGrid;
    private AñadirBase<Maquinaria> añadirBase;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializar AñadirBase para la clase Cliente
        añadirBase = new AñadirBase<>(Maquinaria.class);
        
        // Agregar el formulario generado al VBox principal
        mainContainer.getChildren().add(añadirBase.getFormGrid());
    }    
    private boolean insertarDetalleEnBD(Maquinaria maquinaria) {
        String sql = "{call InsertMaquinaria(?, ?, ?, ?)}"; // Llamada al procedimiento almacenado

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement cstmt = conn.prepareCall(sql)) {

            // Configurar los parámetros del CallableStatement
            cstmt.setInt(1, maquinaria.getCodigo());
            cstmt.setString(2, maquinaria.getNombre());
            cstmt.setString(3, maquinaria.getMarca());
            LocalDate localDate = maquinaria.getFechaAdquisicion();
            // Convertir LocalDate a java.sql.Date
            if (localDate != null) {
                cstmt.setDate(4, Date.valueOf(localDate)); // Convertir LocalDate a java.sql.Date
            } else {
                cstmt.setNull(4, java.sql.Types.DATE);
            }
            // Ejecutar el procedimiento almacenado
            boolean hasResultSet = cstmt.execute();
            return !hasResultSet; // El método execute() devuelve true si hay un ResultSet, false si es solo una actualización

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    private void mostrarError(String mensaje) {
        // Mostrar un mensaje de error (por ejemplo, usando un Alert de JavaFX)
        System.err.println(mensaje);
    }
    @FXML
    private void añadir(ActionEvent event) {
        try {
        // Obtener el objeto Cliente con los valores ingresados
        Maquinaria nuevaMaq = añadirBase.getObject();

        // Insertar el cliente en la base de datos
        if (insertarDetalleEnBD(nuevaMaq)) {
            // Limpiar los campos después de añadir
            añadirBase.clearFields();
        } else {
            mostrarError("No se pudo añadir la maquinaria a la base de datos.");
        }
        App.setRoot("maquinaria");
        } catch (IllegalArgumentException e) {
            mostrarError(e.getMessage()); // Mostrar el mensaje de error al usuario
        } catch (Exception e) {
            e.printStackTrace();
            mostrarError("Ocurrió un error al intentar añadir la maquinaria.");
        }
    }

    @FXML
    private void cancelar(ActionEvent event) throws IOException {
        App.setRoot("maquinaria");
    }
    
}
