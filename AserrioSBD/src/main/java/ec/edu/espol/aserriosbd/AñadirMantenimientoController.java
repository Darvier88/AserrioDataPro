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
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author ASUS VIVOBOOK PRO
 */
public class AñadirMantenimientoController implements Initializable {

    @FXML
    private VBox mainContainer;
    @FXML
    private GridPane formGrid;
    private AñadirBase<Mantenimiento> añadirBase;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializar AñadirBase para la clase Mantenimiento
        añadirBase = new AñadirBase<>(Mantenimiento.class);

        // Agregar el formulario generado al VBox principal
        mainContainer.getChildren().add(añadirBase.getFormGrid());
    }

    private boolean insertarDetalleEnBD(Mantenimiento mantenimiento) {
        String sql = "{call InsertMantenimiento(?, ?, ?, ?, ?)}"; // Llamada al procedimiento almacenado

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement cstmt = conn.prepareCall(sql)) {

            // Configurar los parámetros del CallableStatement
            cstmt.setInt(2, mantenimiento.getCodigoMaquinaria());
            cstmt.setString(3, mantenimiento.getIdSecretaria());
            cstmt.setString(1, mantenimiento.getIdOperario());
            cstmt.setString(4, mantenimiento.getDetalle());
            LocalDate localDate = mantenimiento.getFecha();
            // Convertir LocalDate a java.sql.Date
            if (localDate != null) {
                cstmt.setDate(5, Date.valueOf(localDate)); // Convertir LocalDate a java.sql.Date
            } else {
                cstmt.setNull(5, java.sql.Types.DATE);
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
        // Mostrar un mensaje de error (por ejemplo, usando un Alert de JavaFX)
        System.err.println(mensaje);
    }

    @FXML
    private void añadir(ActionEvent event) {
        try {
            // Obtener el objeto Mantenimiento con los valores ingresados
            Mantenimiento nuevoMant = añadirBase.getObject();

            // Insertar el mantenimiento en la base de datos
            if (insertarDetalleEnBD(nuevoMant)) {
                // Limpiar los campos después de añadir
                añadirBase.clearFields();
            } else {
                mostrarError("No se pudo añadir el mantenimiento a la base de datos.");
            }
            App.setRoot("mantenimiento");
        } catch (IllegalArgumentException e) {
            mostrarError(e.getMessage()); // Mostrar el mensaje de error al usuario
        } catch (Exception e) {
            e.printStackTrace();
            mostrarError("Ocurrió un error al intentar añadir el mantenimiento.");
        }
    }

    @FXML
    private void cancelar(ActionEvent event) throws IOException {
        App.setRoot("mantenimiento");
    }

    
}
