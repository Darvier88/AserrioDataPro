/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.aserriosbd;

import ec.edu.espol.aserriosbd.modelo.DatabaseConnection;
import ec.edu.espol.aserriosbd.modelo.Reclamacion;
import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ASUS VIVOBOOK PRO
 */
public class ModificarReclamacionController implements Initializable {

    @FXML
    private GridPane formGrid;
    private Reclamacion reclamacion;
    private ModificarBase<Reclamacion> modificarBase;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializar ModificarBase con la clase Cliente
        modificarBase = new ModificarBase<>(Reclamacion.class);

        // Añadir el formulario generado dinámicamente al GridPane
        formGrid.getChildren().addAll(modificarBase.getFormGrid().getChildren());
    }    
    public void setReclamacion(Reclamacion reclamacion) {
        if (reclamacion != null) {
            this.reclamacion = reclamacion;
            modificarBase.loadObject(reclamacion);
        }
    }
    public static void cargarVistaConCliente(Reclamacion  reclamacion ) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("modificarCliente.fxml"));
        Parent root = loader.load();

        ModificarReclamacionController controller = loader.getController();
        controller.setReclamacion(reclamacion);

        App.setRoot("modificarReclamacion");
    }
        public static void mostrarVentanaModificacion(Reclamacion reclamacion) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("modificarReclamacion.fxml"));
        Parent root = loader.load();

        ModificarReclamacionController controller = loader.getController();
        controller.setReclamacion(reclamacion);

        Stage stage = new Stage();
        stage.setTitle("Modificar Reclamacion");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL); // Establecer la modalidad de la ventana
        stage.showAndWait(); // Mostrar la ventana y esperar hasta que sea cerrada
    }
    @FXML
    private void modificar(ActionEvent event) throws IOException {
        try {
            // Obtener los valores modificados del formulario
            Reclamacion reclamacionModificado = modificarBase.getObject();

            // Actualizar el cliente en la base de datos
            if (actualizarClienteEnBD(reclamacionModificado)) {
                System.out.println("Cliente modificado exitosamente.");
                App.setRoot("reclamacion");
                // Cerrar la ventana emergente
                Stage stage = (Stage) formGrid.getScene().getWindow();
                stage.close();

                // Recargar la ventana principal
                App.setRoot("reclamacion");
            } else {
                mostrarError("No se pudo modificar la reclamacion en la base de datos.");
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            mostrarError("Ocurrió un error al intentar modificar la reclamacion.");
        }
    }
    private boolean actualizarClienteEnBD(Reclamacion reclamacionModificado) {
        String sql = "{CALL ActualizarReclamacion(?, ?, ?, ?)}";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement cstmt = conn.prepareCall(sql)) {
            // Establecer los parámetros del procedimiento almacenado
            cstmt.setInt(1, reclamacionModificado.getId());
            cstmt.setString(2, reclamacionModificado.getIdSecretaria());
            cstmt.setString(3, reclamacionModificado.getIdCliente());
            cstmt.setString(4, reclamacionModificado.getDescripcion());
            // Ejecutar el procedimiento almacenado
            cstmt.execute();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            mostrarError("Ocurrió un error al modificar el cliente en la base de datos.");
            return false;
        }
    }
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    @FXML
    private void cancelar(ActionEvent event) {
        // Cerrar la ventana emergente
                Stage stage = (Stage) formGrid.getScene().getWindow();
                stage.close();
    }
    
}
