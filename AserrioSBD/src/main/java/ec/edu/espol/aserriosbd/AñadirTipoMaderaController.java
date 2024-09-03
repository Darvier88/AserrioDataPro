/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.aserriosbd;

import ec.edu.espol.aserriosbd.modelo.DatabaseConnection;
import ec.edu.espol.aserriosbd.modelo.TipoMadera;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AñadirTipoMaderaController implements Initializable {

    @FXML
    private VBox mainContainer;
    @FXML
    private GridPane formGrid;
    private AñadirBase<TipoMadera> añadirBase;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializar AñadirBase para la clase TipoMadera
        añadirBase = new AñadirBase<>(TipoMadera.class);
        
        // Agregar el formulario generado al VBox principal
        mainContainer.getChildren().add(añadirBase.getFormGrid());
    }

    private boolean insertarTipoMaderaEnBD(TipoMadera tipoMadera) {
        String sql = "CALL InsertTipoDeMadera (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement cstmt = conn.prepareCall(sql)) {

            cstmt.setString(1, tipoMadera.getId());
            cstmt.setString(2, tipoMadera.getNombre());
            cstmt.setFloat(3, tipoMadera.getPrecioUnitario());
            cstmt.setString(4, tipoMadera.getCondicionAmbiental());

            // Ejecutar el procedimiento almacenado
            boolean hasResultSet = cstmt.execute();
            return !hasResultSet; // El método execute() devuelve true si hay un ResultSet, false si es solo una actualización

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    private void añadir(ActionEvent event) {
        try {
            // Obtener el objeto TipoMadera con los valores ingresados
            TipoMadera nuevoTipoMadera = añadirBase.getObject();

            // Insertar el tipo de madera en la base de datos
            if (insertarTipoMaderaEnBD(nuevoTipoMadera)) {
                // Limpiar los campos después de añadir
                añadirBase.clearFields();
            } else {
                mostrarError("No se pudo añadir el tipo de madera a la base de datos.");
            }

            App.setRoot("tipoMadera");
        } catch (IllegalArgumentException e) {
            mostrarError(e.getMessage());
        } catch (Exception e) {
            mostrarError("Ocurrió un error al intentar añadir el tipo de madera.");
            e.printStackTrace();
            
        }
    }

    @FXML
    private void cancelar(ActionEvent event) throws IOException {
        App.setRoot("tipoMadera");
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}

