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
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ModificarLoteMaderaController implements Initializable {

    @FXML
    private GridPane formGrid;
    private LoteMadera loteMadera;
    private ModificarBase<LoteMadera> modificarBase;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializar ModificarBase con la clase LoteMadera
        modificarBase = new ModificarBase<>(LoteMadera.class);

        // Lista de nombres de los campos que deseas incluir en el formulario
    List<String> fieldNames = Arrays.asList("id", "idProveedor", "idSecretaria", "precio", "fechaLlegada");

    // Generar el formulario, manteniendo el campo id pero excluyendo los nombres de proveedor y secretaria
    GridPane formGrid = modificarBase.getFormGridWithId(fieldNames, "proveedorNombre", "secretariaNombre");
    formGrid.getChildren().add(formGrid);
    }

    // Método que se llamará para cargar los datos del lote de madera seleccionado
    public void setLoteMadera(LoteMadera loteMadera) {
        if (loteMadera != null) {
            this.loteMadera = loteMadera; // Guardar el lote de madera para referencia futura
            modificarBase.loadObject(loteMadera); // Cargar los datos en los campos del formulario
        }
    }

    public static void mostrarVentanaModificacion(LoteMadera loteMadera) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("ModificarLoteMadera.fxml"));
        Parent root = loader.load();

        ModificarLoteMaderaController controller = loader.getController();
        controller.setLoteMadera(loteMadera);

        Stage stage = new Stage();
        stage.setTitle("Modificar Lote de Madera");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL); // Establecer la modalidad de la ventana
        stage.showAndWait(); // Mostrar la ventana y esperar hasta que sea cerrada
    }
    @FXML
    private void modificar(ActionEvent event) {
        try {
            // Obtener los valores modificados del formulario
            LoteMadera loteMaderaModificado = modificarBase.getObject();

            // Actualizar el lote de madera en la base de datos
            if (actualizarLoteMaderaEnBD(loteMaderaModificado)) {
                System.out.println("Lote de madera modificado con éxito.");
                // Cerrar la ventana emergente
                Stage stage = (Stage) formGrid.getScene().getWindow();
                stage.close();

                // Recargar la ventana principal
                App.setRoot("loteMadera");
            } else {
                mostrarError("No se pudo modificar el lote de madera en la base de datos.");
            }
        } catch (InstantiationException | IllegalAccessException | IOException e) {
            e.printStackTrace();
            mostrarError("Ocurrió un error al intentar modificar el lote de madera.");
        }
    }
    private boolean actualizarLoteMaderaEnBD(LoteMadera loteMaderaModificado) {
        String sql = "UPDATE lote_madera SET id_proveedor = ?, id_secretaria = ?, precio = ?, fecha_llegada = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, loteMaderaModificado.getIdProveedor());
            pstmt.setString(2, loteMaderaModificado.getIdSecretaria());
            pstmt.setFloat(3, loteMaderaModificado.getPrecio());
            pstmt.setDate(4, java.sql.Date.valueOf(loteMaderaModificado.getFechaLlegada())); // Convertir LocalDate a SQL Date
            pstmt.setInt(5, loteMaderaModificado.getId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Verificar si se actualizó al menos una fila

        } catch (SQLException e) {
            e.printStackTrace();
            mostrarError("Ocurrió un error al modificar el lote de madera en la base de datos.");
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

    // Método estático para cargar la vista y pasar el lote de madera seleccionado
    public static void cargarVistaConLoteMadera(LoteMadera loteMadera) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("ModificarLoteMadera.fxml"));
        Parent root = loader.load();

        ModificarLoteMaderaController controller = loader.getController();
        controller.setLoteMadera(loteMadera);

        App.setRoot("ModificarLoteMadera");
    }
}
