/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.aserriosbd;

import ec.edu.espol.aserriosbd.modelo.DatabaseConnection;
import ec.edu.espol.aserriosbd.modelo.Producto;
import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
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
    String sql = "{CALL InsertProducto(?, ?, ?, ?, ?, ?, ?, ?)}";

    try (Connection conn = DatabaseConnection.getConnection();
         CallableStatement cstmt = conn.prepareCall(sql)) {

        // Establecer los parámetros del procedimiento almacenado
        cstmt.setString(1, producto.getId());
        cstmt.setString(2, producto.getNombre());
        cstmt.setFloat(3, producto.getPrecioUnitario());
        cstmt.setString(4, producto.getCalidad());
        cstmt.setString(5, producto.getCondicAmbiental());
        cstmt.setInt(6, producto.getTiempoAlmacenamiento());
        cstmt.setString(7, producto.getDimension());
        cstmt.setString(8, producto.getDescripcion());

        // Ejecutar el procedimiento almacenado
        cstmt.execute();
        return true;

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

