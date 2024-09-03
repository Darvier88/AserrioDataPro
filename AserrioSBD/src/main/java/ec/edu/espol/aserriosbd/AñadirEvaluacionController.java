/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.aserriosbd;

import ec.edu.espol.aserriosbd.modelo.DatabaseConnection;
import ec.edu.espol.aserriosbd.modelo.Especificación;
import ec.edu.espol.aserriosbd.modelo.Evaluacion;
import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Zambrano
 */
public class AñadirEvaluacionController implements Initializable {

    @FXML
    private VBox mainContainer;
    @FXML
    private GridPane formGrid;
    private AñadirBase<Evaluacion> añadirBase;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializar AñadirBase
        añadirBase = new AñadirBase<>(Evaluacion.class);
    // Agregar el formulario generado al VBox principal
        mainContainer.getChildren().add(añadirBase.getFormGrid());
    }    
     private boolean insertarEvaluacionEnBD(Evaluacion evaluacion) {
        String sql = "CALL InsertEvaluacion (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement cstmt = conn.prepareCall(sql)) {

            cstmt.setString(1, evaluacion.getIdProveedor());
            cstmt.setString(2, evaluacion.getCalidad());
            cstmt.setString(3, evaluacion.getPuntualidad());
            cstmt.setString(4, evaluacion.getDetalle()); 

            boolean hasResultSet = cstmt.execute();
            return !hasResultSet; // Verifica si se insertó al menos una fila

        } catch (SQLException e) {
            e.printStackTrace();
            
            return false;
        }
    }
    @FXML
    private void añadir(ActionEvent event) {
        try {
            // Obtener el objeto  con los valores ingresados
            Evaluacion evaluacion = añadirBase.getObject();
            // Insertar  en la base de datos
            if (insertarEvaluacionEnBD(evaluacion)) {
                // Limpiar los campos después de añadir
                añadirBase.clearFields();
            } else {
                mostrarError("No se pudo añadir la evaluacion a la base de datos.");
            }

            App.setRoot("evaluacion");
        } catch (IllegalArgumentException e) {
            mostrarError(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            mostrarError("Ocurrió un error al intentar añadir la evaluacion");
        }
    }

    @FXML
    private void cancelar(ActionEvent event) throws IOException {
        App.setRoot("evaluacion");
    }
    
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
}
