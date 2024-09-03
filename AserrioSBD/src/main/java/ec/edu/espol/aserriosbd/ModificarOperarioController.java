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
 * @author ASUS VIVOBOOK PRO
 */
public class ModificarOperarioController implements Initializable {

    @FXML
    private GridPane formGrid;
    private Empleado emp;
    private ModificarBase<Empleado> modificarBase;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializar ModificarBase con la clase Mantenimiento
        modificarBase = new ModificarBase<>(Empleado.class);

        // Añadir el formulario generado dinámicamente al GridPane
        formGrid.getChildren().addAll(modificarBase.getFormGrid().getChildren());
    }   
    public void setEmpleado(Empleado emp) {
        if (emp != null) {
            this.emp = emp;
            modificarBase.loadObject(emp);
        }
    }
    public static void cargarVista(Empleado emp) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("modificarOperario.fxml"));
        Parent root = loader.load();

        ModificarOperarioController controller = loader.getController();
        controller.setEmpleado(emp);

        App.setRoot("modificarOperario");
    }
    public static void mostrarVentanaModificacion(Empleado emp) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("modificarOperario.fxml"));
        Parent root = loader.load();

        ModificarOperarioController controller = loader.getController();
        controller.setEmpleado(emp);

        Stage stage = new Stage();
        stage.setTitle("Modificar Operario");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL); // Establecer la modalidad de la ventana
        stage.showAndWait(); // Mostrar la ventana y esperar hasta que sea cerrada
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
        return !hasResultSet; // El método execute() devuelve true si hay un ResultSet, false si es solo una actualización

    } catch (SQLException ex) {
        ex.printStackTrace();
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
            Empleado emp = modificarBase.getObject();
            // Actualizar la factura en la base de datos
            if (actualizarEmpleadoEnBD(emp)) {
                System.out.println("Operario modificada exitosamente.");
                App.setRoot("operarios");
                // Cerrar la ventana emergente
                Stage stage = (Stage) formGrid.getScene().getWindow();
                stage.close();

                // Recargar la ventana principal
                App.setRoot("operarios");
            } else {
                mostrarError("No se pudo modificar el operario en la base de datos.");
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            mostrarError("Ocurrió un error al intentar modificar el operario.");
        }
    }

    @FXML
    private void cancelar(ActionEvent event) {
        // Cerrar la ventana emergente
                Stage stage = (Stage) formGrid.getScene().getWindow();
                stage.close();
    }
    
}
