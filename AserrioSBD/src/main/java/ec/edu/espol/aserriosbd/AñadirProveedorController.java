/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.aserriosbd;
import ec.edu.espol.aserriosbd.modelo.DatabaseConnection;
import ec.edu.espol.aserriosbd.modelo.Proveedor;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AñadirProveedorController implements Initializable {

    @FXML
    private VBox mainContainer;
    @FXML
    private GridPane formGrid;
    private AñadirBase<Proveedor> añadirBase;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializar AñadirBase para la clase Proveedor
        añadirBase = new AñadirBase<>(Proveedor.class);
        
        // Agregar el formulario generado al VBox principal
        mainContainer.getChildren().add(añadirBase.getFormGrid());
    }

    private boolean insertarProveedorEnBD(Proveedor proveedor) {
        String sql = "CALL InsertProveedor (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement cstmt = conn.prepareCall(sql)) {

            cstmt.setString(1, proveedor.getCedula());
            cstmt.setString(2, proveedor.getNombre());

            // Manejo de nulos para el campo telefono
            if (proveedor.getTelefono() == null) {
                cstmt.setNull(3, java.sql.Types.INTEGER);
            } else {
                cstmt.setInt(3, proveedor.getTelefono());
            }

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
            // Obtener el objeto Proveedor con los valores ingresados
            Proveedor nuevoProveedor = añadirBase.getObject();

            // Insertar el proveedor en la base de datos
            if (insertarProveedorEnBD(nuevoProveedor)) {
                // Limpiar los campos después de añadir
                añadirBase.clearFields();
            } else {
                mostrarError("No se pudo añadir el proveedor a la base de datos.");
            }

            App.setRoot("proveedor");
        } catch (IllegalArgumentException e) {
            mostrarError(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            mostrarError("Ocurrió un error al intentar añadir el proveedor.");
        }
    }

    @FXML
    private void cancelar(ActionEvent event) throws IOException {
        App.setRoot("proveedor");
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}

