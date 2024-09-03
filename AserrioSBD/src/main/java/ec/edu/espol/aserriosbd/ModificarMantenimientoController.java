/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.aserriosbd;

import ec.edu.espol.aserriosbd.modelo.DatabaseConnection;
import ec.edu.espol.aserriosbd.modelo.Mantenimiento;
import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
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
public class ModificarMantenimientoController implements Initializable {

    @FXML
    private GridPane formGrid;
    private Mantenimiento mant;
    private ModificarBase<Mantenimiento> modificarBase;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializar ModificarBase con la clase Mantenimiento
        modificarBase = new ModificarBase<>(Mantenimiento.class);

        // Añadir el formulario generado dinámicamente al GridPane
        formGrid.getChildren().addAll(modificarBase.getFormGrid().getChildren());
    }

    public void setMantenimiento(Mantenimiento mant) {
        if (mant != null) {
            this.mant = mant;
            modificarBase.loadObject(mant);
        }
    }

    public static void cargarVistaConMantenimiento(Mantenimiento mant) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("modificarMantenimiento.fxml"));
        Parent root = loader.load();

        ModificarMantenimientoController controller = loader.getController();
        controller.setMantenimiento(mant);

        App.setRoot("modificarMantenimiento");
    }

    public static void mostrarVentanaModificacion(Mantenimiento mant) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("ModificarMantenimiento.fxml"));
        Parent root = loader.load();

        ModificarMantenimientoController controller = loader.getController();
        controller.setMantenimiento(mant);

        Stage stage = new Stage();
        stage.setTitle("Modificar Mantenimiento");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL); // Establecer la modalidad de la ventana
        stage.showAndWait(); // Mostrar la ventana y esperar hasta que sea cerrada
    }

    private boolean actualizarMantenimientoEnBD(Mantenimiento mantenimiento) {
        String sql = "{CALL actualizarMantenimiento(?, ?, ?, ?, ?, ?)}";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement cstmt = conn.prepareCall(sql)) {

            // Configurar los parámetros del CallableStatement
            System.out.println(mantenimiento.getId());
            cstmt.setInt(1, mantenimiento.getId());
            cstmt.setInt(3, mantenimiento.getCodigoMaquinaria());
            cstmt.setString(4, mantenimiento.getIdSecretaria());
            cstmt.setString(2, mantenimiento.getIdOperario());
            cstmt.setString(5, mantenimiento.getDetalle());
            LocalDate localDate = mantenimiento.getFecha();
            // Convertir LocalDate a java.sql.Date
            if (localDate != null) {
                cstmt.setDate(6, Date.valueOf(localDate)); // Convertir LocalDate a java.sql.Date
            } else {
                cstmt.setNull(6, java.sql.Types.DATE);
            }
            // Ejecutar el procedimiento almacenado
            boolean hasResultSet = cstmt.execute();
            return !hasResultSet; // El método execute() devuelve true si hay un ResultSet, false si es solo una actualización

        } catch (SQLException e) {
            e.printStackTrace();
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
    private void modificar(ActionEvent event) throws IOException {
        try {
            // Obtener los valores modificados del formulario
            Mantenimiento mantenimiento = modificarBase.getObject();

            // Actualizar el mantenimiento en la base de datos
            if (actualizarMantenimientoEnBD(mantenimiento)) {
                System.out.println("Mantenimiento modificado exitosamente.");
                App.setRoot("mantenimiento");
                // Cerrar la ventana emergente
                Stage stage = (Stage) formGrid.getScene().getWindow();
                stage.close();

                // Recargar la ventana principal
                App.setRoot("mantenimiento");
            } else {
                mostrarError("No se pudo modificar el mantenimiento en la base de datos.");
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            mostrarError("Ocurrió un error al intentar modificar el mantenimiento.");
        }
    }

    @FXML
    private void cancelar(ActionEvent event) {
        // Cerrar la ventana emergente
        Stage stage = (Stage) formGrid.getScene().getWindow();
        stage.close();
    }

    
}
