/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.aserriosbd;

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

public class ModificarTipoMaderaController implements Initializable {

    @FXML
    private GridPane formGrid;
    private TipoMadera tipoMadera;
    private ModificarBase<TipoMadera> modificarBase;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializar ModificarBase con la clase TipoMadera
        modificarBase = new ModificarBase<>(TipoMadera.class);

        // Añadir el formulario generado dinámicamente al GridPane
        formGrid.getChildren().addAll(modificarBase.getFormGrid().getChildren());
    }

    // Método que se llamará para cargar los datos del tipo de madera seleccionado
    public void setTipoMadera(TipoMadera tipoMadera) {
        if (tipoMadera != null) {
            this.tipoMadera = tipoMadera; // Guardar el tipo de madera para referencia futura
            modificarBase.loadObject(tipoMadera); // Cargar los datos en los campos del formulario
        }
    }

    // Método estático para cargar la vista en una ventana emergente
    public static void mostrarVentanaModificacion(TipoMadera tipoMadera) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("ModificarTipoMadera.fxml"));
        Parent root = loader.load();

        ModificarTipoMaderaController controller = loader.getController();
        controller.setTipoMadera(tipoMadera);

        Stage stage = new Stage();
        stage.setTitle("Modificar Tipo de Madera");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL); // Establecer la modalidad de la ventana
        stage.showAndWait(); // Mostrar la ventana y esperar hasta que sea cerrada
    }

    @FXML
    private void modificar(ActionEvent event) throws IOException {
        try {
            TipoMadera tipoMaderaModificado = modificarBase.getObject();
            // Actualizar el tipo de madera en la base de datos
            if (actualizarTipoMaderaEnBD(tipoMaderaModificado)) {
                // Cerrar la ventana emergente
                Stage stage = (Stage) formGrid.getScene().getWindow();
                stage.close();

                // Recargar la ventana principal
                App.setRoot("tipoMadera");
            } else {
                mostrarError("No se pudo modificar el tipo de madera en la base de datos.");
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            mostrarError("Ocurrió un error al intentar modificar el tipo de madera.");
        }
    }

    private boolean actualizarTipoMaderaEnBD(TipoMadera tipoMaderaModificado) {
        String sql = "UPDATE tipo_de_madera SET nombre = ?, precio_unitario = ?, condic_ambiental = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, tipoMaderaModificado.getNombre());
            pstmt.setFloat(2, tipoMaderaModificado.getPrecioUnitario());
            pstmt.setString(3, tipoMaderaModificado.getCondicionAmbiental());
            pstmt.setString(4, tipoMaderaModificado.getId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Verificar si se actualizó al menos una fila

        } catch (SQLException e) {
            e.printStackTrace();
            mostrarError("Ocurrió un error al modificar el tipo de madera en la base de datos.");
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

    // Método estático para cargar la vista y pasar el tipo de madera seleccionado
    public static void cargarVistaConTipoMadera(TipoMadera tipoMadera) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("ModificarTipoMadera.fxml"));
        Parent root = loader.load();

        ModificarTipoMaderaController controller = loader.getController();
        controller.setTipoMadera(tipoMadera);

        App.setRoot("ModificarTipoMadera");
    }
}
