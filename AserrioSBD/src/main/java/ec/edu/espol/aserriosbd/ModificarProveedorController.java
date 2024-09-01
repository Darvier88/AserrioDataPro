/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.aserriosbd;

import ec.edu.espol.aserriosbd.modelo.DatabaseConnection;
import ec.edu.espol.aserriosbd.modelo.Proveedor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ModificarProveedorController implements Initializable {

    @FXML
    private GridPane formGrid;
    private Proveedor proveedor;
    private ModificarBase<Proveedor> modificarBase;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializar ModificarBase con la clase Proveedor
        modificarBase = new ModificarBase<>(Proveedor.class);

        // Añadir el formulario generado dinámicamente al GridPane
        formGrid.getChildren().addAll(modificarBase.getFormGrid().getChildren());
    }

    // Método que se llamará para cargar los datos del proveedor seleccionado
    public void setProveedor(Proveedor proveedor) {
        if (proveedor != null) {
            this.proveedor = proveedor; // Guardar el proveedor para referencia futura
            modificarBase.loadObject(proveedor); // Cargar los datos en los campos del formulario
        }
    }
    // Método estático para cargar la vista en una ventana emergente
public static void mostrarVentanaModificacion(Proveedor proveedor) throws IOException {
    FXMLLoader loader = new FXMLLoader(App.class.getResource("ModificarProveedor.fxml"));
    Parent root = loader.load();

    ModificarProveedorController controller = loader.getController();
    controller.setProveedor(proveedor);

    Stage stage = new Stage();
    stage.setTitle("Modificar Proveedor");
    stage.setScene(new Scene(root));
    stage.initModality(Modality.APPLICATION_MODAL); // Establecer la modalidad de la ventana
    stage.showAndWait(); // Mostrar la ventana y esperar hasta que sea cerrada
}
    @FXML
    private void modificar(ActionEvent event) throws IOException {
        try {
            Proveedor proveedorModificado = modificarBase.getObject();
            // Actualizar el proveedor en la base de datos
            if (actualizarProveedorEnBD(proveedorModificado)) {
                // Cerrar la ventana emergente
                Stage stage = (Stage) formGrid.getScene().getWindow();
                stage.close();

                // Recargar la ventana principal
                App.setRoot("proveedor");
            } else {
                mostrarError("No se pudo modificar el proveedor en la base de datos.");
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            mostrarError("Ocurrió un error al intentar modificar el proveedor.");
        }
    }

    private boolean actualizarProveedorEnBD(Proveedor proveedorModificado) {
        String sql = "UPDATE proveedor SET nombre = ?, telefono = ? WHERE cedula = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, proveedorModificado.getNombre());

            // Manejar el caso en que telefono sea null
            if (proveedorModificado.getTelefono() != null) {
                pstmt.setInt(2, proveedorModificado.getTelefono());
            } else {
                pstmt.setNull(2, java.sql.Types.INTEGER);
            }

            pstmt.setString(3, proveedorModificado.getCedula());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Verificar si se actualizó al menos una fila

        } catch (SQLException e) {
            e.printStackTrace();
            mostrarError("Ocurrió un error al modificar el proveedor en la base de datos.");
            return false;
        }
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    private void cancelar(ActionEvent event) throws IOException {
        // Cerrar la ventana emergente
                Stage stage = (Stage) formGrid.getScene().getWindow();
                stage.close();
    }

    // Método estático para cargar la vista y pasar el proveedor seleccionado
    public static void cargarVistaConProveedor(Proveedor proveedor) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("ModificarProveedor.fxml"));
        Parent root = loader.load();

        ModificarProveedorController controller = loader.getController();
        controller.setProveedor(proveedor);

        App.setRoot("ModificarProveedor");
    }
}
