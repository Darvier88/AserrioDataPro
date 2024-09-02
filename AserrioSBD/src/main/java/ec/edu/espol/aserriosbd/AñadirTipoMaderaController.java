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
        String sql = "INSERT INTO tipo_de_madera (id, nombre, precio_unitario, condic_ambiental) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, tipoMadera.getId());
            pstmt.setString(2, tipoMadera.getNombre());
            pstmt.setFloat(3, tipoMadera.getPrecioUnitario());
            pstmt.setString(4, tipoMadera.getCondicionAmbiental());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Verifica si se insertó al menos una fila

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
            e.printStackTrace();
            mostrarError("Ocurrió un error al intentar añadir el tipo de madera.");
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

