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
        
        // Lista de nombres de los campos que deseas incluir en el formulario
    List<String> fieldNames = Arrays.asList("id", "idProveedor", "idSecretaria", "precio", "fechaLlegada");

    // Generar el formulario, manteniendo el campo id pero excluyendo los nombres de proveedor y secretaria
    GridPane formGrid = añadirBase.getFormGridWithId(fieldNames, "proveedorNombre", "secretariaNombre");
    mainContainer.getChildren().add(formGrid);
    }

    private boolean insertarLoteMaderaEnBD(LoteMadera loteMadera) {
        String sql = "INSERT INTO lote_madera (id_proveedor, id_secretaria, precio, fecha_llegada) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, loteMadera.getIdProveedor());
            pstmt.setString(2, loteMadera.getIdSecretaria());
            pstmt.setFloat(3, loteMadera.getPrecio());
            pstmt.setDate(4, java.sql.Date.valueOf(loteMadera.getFechaLlegada())); // Convertir LocalDate a SQL Date

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



