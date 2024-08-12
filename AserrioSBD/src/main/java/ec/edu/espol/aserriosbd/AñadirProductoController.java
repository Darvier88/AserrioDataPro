/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.aserriosbd;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;

public class AñadirProductoController implements Initializable {

    @FXML
    private VBox mainContainer;
    @FXML
    private GridPane formGrid;
    private AñadirBase<Producto> añadirBase;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializar AñadirBase para la clase Producto
        añadirBase = new AñadirBase<>(Producto.class);
        
        // Agregar el formulario generado al VBox principal
        mainContainer.getChildren().add(añadirBase.getFormGrid());
    }

    private boolean insertarProductoEnBD(Producto producto) {
        String sql = "INSERT INTO producto (id, nombre, precioUnitario, calidad, condicAmbiental, tiempoAlmacenamiento, dimension, descripcion) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, producto.getId());
            pstmt.setString(2, producto.getNombre());
            pstmt.setInt(3, (int) producto.getPrecioUnitario());
            pstmt.setString(4, producto.getCalidad());
            pstmt.setString(5, producto.getCondicAmbiental());
            pstmt.setInt(6, producto.getTiempoAlmacenamiento());
            pstmt.setString(7, producto.getDimension());
            pstmt.setString(8, producto.getDescripcion());

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
            // Obtener el objeto Producto con los valores ingresados
            Producto nuevoProducto = añadirBase.getObject();

            // Insertar el producto en la base de datos
            if (insertarProductoEnBD(nuevoProducto)) {
                // Limpiar los campos después de añadir
                añadirBase.clearFields();
            } else {
                mostrarError("No se pudo añadir el producto a la base de datos.");
            }

            App.setRoot("producto");
        } catch (IllegalArgumentException e) {
            mostrarError(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            mostrarError("Ocurrió un error al intentar añadir el producto.");
        }
    }

    @FXML
    private void cancelar(ActionEvent event) throws IOException {
        App.setRoot("producto");
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}

