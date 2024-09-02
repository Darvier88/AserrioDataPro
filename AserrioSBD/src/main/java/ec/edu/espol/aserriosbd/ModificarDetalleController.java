/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.aserriosbd;

import ec.edu.espol.aserriosbd.modelo.DatabaseConnection;
import ec.edu.espol.aserriosbd.modelo.Detalle;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ASUS VIVOBOOK PRO
 */
public class ModificarDetalleController implements Initializable {

    @FXML
    private GridPane formGrid;
    private Detalle detalle;
    private ModificarBase<Detalle> modificarBase;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializar ModificarBase con la clase Cliente
        modificarBase = new ModificarBase<>(Detalle.class);

        // Añadir el formulario generado dinámicamente al GridPane
        formGrid.getChildren().addAll(modificarBase.getFormGrid().getChildren());
    }    
    public void setDetalle(Detalle detalle) {
        if (detalle != null) {
            this.detalle = detalle;
            modificarBase.loadObject(detalle);
        }
    }
    public static void cargarVistaConCliente(Detalle detalle) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("modificarDetalle.fxml"));
        Parent root = loader.load();

        ModificarDetalleController controller = loader.getController();
        controller.setDetalle(detalle);

        App.setRoot("modificarDetalle");
    }
    public static void mostrarVentanaModificacion(Detalle detalle) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("ModificarDetalle.fxml"));
        Parent root = loader.load();

        ModificarDetalleController controller = loader.getController();
        controller.setDetalle(detalle);

        Stage stage = new Stage();
        stage.setTitle("Modificar Detalle");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL); // Establecer la modalidad de la ventana
        stage.showAndWait(); // Mostrar la ventana y esperar hasta que sea cerrada
    }
    private boolean actualizarClienteEnBD(Detalle detalleModificado) {
        String sql = "{CALL ActualizarDetalle(?, ?, ?, ?)}";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement cstmt = conn.prepareCall(sql)) {
            // Establecer los parámetros del procedimiento almacenado
            cstmt.setInt(1, detalleModificado.getIdFactura());
            cstmt.setString(2, detalleModificado.getIdProducto());
            cstmt.setInt(3, detalleModificado.getCantidad());
            cstmt.setString(4,detalleModificado.getDetalleAdic());

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
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    @FXML
    private void modificar(ActionEvent event) throws IOException {
        try {
            // Obtener los valores modificados del formulario
            Detalle detalleModificado = modificarBase.getObject();

            // Actualizar el cliente en la base de datos
            if (actualizarClienteEnBD(detalleModificado)) {
                System.out.println("Detalle modificado exitosamente.");
                App.setRoot("detalle");
                // Cerrar la ventana emergente
                Stage stage = (Stage) formGrid.getScene().getWindow();
                stage.close();

                // Recargar la ventana principal
                App.setRoot("detalle");
            } else {
                mostrarError("No se pudo modificar el detalle en la base de datos.");
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            mostrarError("Ocurrió un error al intentar modificar el detalle.");
        }
    }

    @FXML
    private void cancelar(ActionEvent event) {
        // Cerrar la ventana emergente
                Stage stage = (Stage) formGrid.getScene().getWindow();
                stage.close();
    }
    
}
