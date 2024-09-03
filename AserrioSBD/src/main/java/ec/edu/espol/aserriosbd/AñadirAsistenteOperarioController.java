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
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Zambrano
 */
public class AñadirAsistenteOperarioController implements Initializable {

    @FXML
    private VBox mainContainer;
    @FXML
    private GridPane formGrid;
 private AñadirBase<Empleado> añadirBase;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializar AñadirBase para la clase Mantenimiento
        añadirBase = new AñadirBase<>(Empleado.class);

        // Agregar el formulario generado al VBox principal
        mainContainer.getChildren().add(añadirBase.getFormGrid());
    }    
    private void mostrarError(String mensaje) {
        // Mostrar un mensaje de error (por ejemplo, usando un Alert de JavaFX)
        System.err.println(mensaje);
    }
    private boolean insertarAsistenteEnBD(Empleado e) {
        String sql = "{call InsertAsistenteOperario(?, ?, ?, ?, ?, ?)}"; // Llamada al procedimiento almacenado

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
    @FXML
    private void añadir(ActionEvent event) {
        try {
        // Obtener el objeto Cliente con los valores ingresados
        Empleado nuevoE = añadirBase.getObject();
        // Insertar el cliente en la base de datos
        if (insertarAsistenteEnBD(nuevoE )) {
            // Limpiar los campos después de añadir
            añadirBase.clearFields();
        } else {
            mostrarError("No se pudo añadir al asistente de operario a la base de datos.");
        }
        App.setRoot("asistenteOperario");
    } catch (IllegalArgumentException e) {
        mostrarError(e.getMessage()); // Mostrar el mensaje de error al usuario
    } catch (Exception e) {
        e.printStackTrace();
        mostrarError("Ocurrió un error al intentar añadir al asistente de operario.");
    }
    }

    @FXML
    private void cancelar(ActionEvent event) throws IOException {
        App.setRoot("asistenteOperario");
    }
    
}
