/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.aserriosbd;

import ec.edu.espol.aserriosbd.modelo.DatabaseConnection;
import ec.edu.espol.aserriosbd.modelo.LoteMadera;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;

import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class AñadirLoteMaderaController implements Initializable {

    @FXML
    private VBox mainContainer;
    @FXML
    private GridPane formGrid;
    private AñadirBase<LoteMadera> añadirBase;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializar AñadirBase para la clase LoteMadera
        añadirBase = new AñadirBase<>(LoteMadera.class);
    // Agregar el formulario generado al VBox principal
        mainContainer.getChildren().add(añadirBase.getFormGrid());
        
    }

    private boolean insertarLoteMaderaEnBD(LoteMadera loteMadera) {
        String sql = "CALL insertLoteMadera (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement cstmt = conn.prepareCall(sql)) {

            cstmt.setString(1, loteMadera.getIdProveedor());
            cstmt.setString(2, loteMadera.getIdSecretaria());
            cstmt.setFloat(3, 0);
            cstmt.setDate(4, java.sql.Date.valueOf(loteMadera.getFechaLlegada())); // Convertir LocalDate a SQL Date

            boolean hasResultSet = cstmt.execute();
            System.out.println("Resultado del insert: " + !hasResultSet);
            return !hasResultSet; // Verifica si se insertó al menos una fila

        } catch (SQLException e) {
            e.printStackTrace();
            
            return false;
        }
    }

    @FXML
    private void añadir(ActionEvent event) {
        try {
            // Obtener el objeto LoteMadera con los valores ingresados
            LoteMadera nuevoLoteMadera = añadirBase.getObject();
            // Insertar el lote de madera en la base de datos
            if (insertarLoteMaderaEnBD(nuevoLoteMadera)) {
                // Limpiar los campos después de añadir
                añadirBase.clearFields();
            } else {
                mostrarError("No se pudo añadir el lote de madera a la base de datos.");
            }

            App.setRoot("loteMadera");
        } catch (IllegalArgumentException e) {
            mostrarError(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            mostrarError("Ocurrió un error al intentar añadir el lote de madera.");
        }
    }

    @FXML
    private void cancelar(ActionEvent event) throws IOException {
        App.setRoot("loteMadera");
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}



