/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.aserriosbd;

import ec.edu.espol.aserriosbd.modelo.DatabaseConnection;
import ec.edu.espol.aserriosbd.modelo.Empleado;
import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
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
 * @author Zambrano
 */
public class ModificarSecretariaController implements Initializable {

    @FXML
    private GridPane formGrid;
    private Empleado empleado;
    private ModificarBase<Empleado> modificarBase;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializar ModificarBase con la clase Empleado
        modificarBase = new ModificarBase<>(Empleado.class);

        // Añadir el formulario generado dinámicamente al GridPane
        formGrid.getChildren().addAll(modificarBase.getFormGrid().getChildren());
    }

    public void setEmpleado(Empleado empleado) {
        if (empleado != null) {
            this.empleado = empleado;
            modificarBase.loadObject(empleado);
        }
    }

    public static void cargarVistaConEmpleado(Empleado empleado) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("modificarSecretaria.fxml"));
        Parent root = loader.load();

        ModificarSecretariaController controller = loader.getController();
        controller.setEmpleado(empleado);

        App.setRoot("modificarSecretaria");
    }

    public static void mostrarVentanaModificacion(Empleado empleado) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("ModificarSecretaria.fxml"));
        Parent root = loader.load();

        ModificarSecretariaController controller = loader.getController();
        controller.setEmpleado(empleado);

        Stage stage = new Stage();
        stage.setTitle("Modificar Empleado");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL); // Establecer la modalidad de la ventana
        stage.showAndWait(); // Mostrar la ventana y esperar hasta que sea cerrada
    }

    @FXML
    private void modificar(ActionEvent event) throws IOException {
        try {
            // Obtener los valores modificados del formulario
            Empleado empleadoModificado = modificarBase.getObject();

            // Actualizar el empleado en la base de datos
            if (actualizarEmpleadoEnBD(empleadoModificado)) {
                System.out.println("Empleado modificado exitosamente.");
                App.setRoot("secretaria");
                // Cerrar la ventana emergente
                Stage stage = (Stage) formGrid.getScene().getWindow();
                stage.close();

                // Recargar la ventana principal
                App.setRoot("secretaria");
            } else {
                mostrarError("No se pudo modificar el empleado en la base de datos.");
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            mostrarError("Ocurrió un error al intentar modificar el empleado.");
        }
    }

    private boolean actualizarEmpleadoEnBD(Empleado e) {
        String sql = "{CALL ActualizarOperario(?, ?, ?, ?, ?, ?)}";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement cstmt = conn.prepareCall(sql)) {

            // Configurar los parámetros del CallableStatement
            cstmt.setString(1, e.getId());
            cstmt.setString(2, e.getNombre());
            LocalTime horaI = e.getHoraInicio();
                if (horaI != null) {
                // Asegurarse de que solo HH:mm sea representado
                Time sqlTime = Time.valueOf(horaI.withSecond(0).withNano(0)); 
                cstmt.setTime(3, sqlTime);
            } else {
                cstmt.setNull(3, java.sql.Types.TIME);
            }
            LocalTime horaF = e.getHoraFin();
                if (horaF != null) {
                // Asegurarse de que solo HH:mm sea representado
                Time sqlTime = Time.valueOf(horaF.withSecond(0).withNano(0)); 
                cstmt.setTime(4, sqlTime);
            } else {
                cstmt.setNull(4, java.sql.Types.TIME);
            }
            LocalDate localDate = e.getFechaCapacitacion();
            // Convertir LocalDate a java.sql.Date
            if (localDate != null) {
                cstmt.setDate(5, Date.valueOf(localDate)); // Convertir LocalDate a java.sql.Date
            } else {
                cstmt.setNull(5, java.sql.Types.DATE);
            }
            cstmt.setString(6, e.getTipoCapacitacion());

        // Ejecutar el procedimiento almacenado
        boolean hasResultSet = cstmt.execute();
        return !hasResultSet;

        } catch (SQLException emp) {
            emp.printStackTrace();
            mostrarError("Ocurrió un error al modificar el empleado en la base de datos.");
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

    /**
     * Este método será llamado para cargar los datos del empleado seleccionado.
     */
    @FXML
    private void cancelar(ActionEvent event) throws IOException {
        // Cerrar la ventana emergente
        Stage stage = (Stage) formGrid.getScene().getWindow();
        stage.close();
    }
    
}
