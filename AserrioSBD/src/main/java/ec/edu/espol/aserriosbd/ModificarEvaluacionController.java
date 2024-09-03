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
public class ModificarEvaluacionController implements Initializable {

    @FXML
    private GridPane formGrid;
    private Evaluacion evaluacion;
    private ModificarBase<Evaluacion> modificarBase;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         modificarBase = new ModificarBase<>(Evaluacion.class);
        formGrid.getChildren().addAll(modificarBase.getFormGrid().getChildren());
    }    
    
     public void setEvaluacion(Evaluacion evaluacion) {
         if (evaluacion != null) {
            this.evaluacion = evaluacion;
            modificarBase.loadObject(evaluacion);
        }
    
    }
     
      public static void mostrarVentanaModificacion(Evaluacion evaluacion) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("ModificarEvaluacion.fxml"));
        Parent root = loader.load();

        ModificarEvaluacionController controller = loader.getController();
        controller.setEvaluacion(evaluacion);

        Stage stage = new Stage();
        stage.setTitle("Modificar Evaluacion");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL); // Establecer la modalidad de la ventana
        stage.showAndWait(); // Mostrar la ventana y esperar hasta que sea cerrada
    }

    @FXML
    private void modificar(ActionEvent event) {
        try {
            // Obtener los valores modificados del formulario
            Evaluacion evaluacionModificado = modificarBase.getObject();

            // Actualizar el lote de madera en la base de datos
            if (actualizarEvaluacionEnBD(evaluacionModificado)) {
                // Cerrar la ventana emergente
                Stage stage = (Stage) formGrid.getScene().getWindow();
                stage.close();

                // Recargar la ventana principal
                App.setRoot("evaluacion");
            } else {
                mostrarError("No se pudo modificar la evaluacion en la base de datos.");
            }
        } catch (InstantiationException | IllegalAccessException | IOException e) {
            e.printStackTrace();
            mostrarError("Ocurrió un error al intentar modificar la evaluacion.");
        }
    }
    
    private boolean actualizarEvaluacionEnBD(Evaluacion evaluacionModificado) {
        String sql = "CALL actualizarEvaluacion(?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement cstmt = conn.prepareCall(sql)) {
            
            cstmt.setInt(1, evaluacionModificado.getId());
            cstmt.setString(2, evaluacionModificado.getIdProveedor());
            cstmt.setString(3, evaluacionModificado.getCalidad());
            cstmt.setString(4, evaluacionModificado.getPuntualidad());
            cstmt.setString(5, evaluacionModificado.getDetalle());
            
            cstmt.executeUpdate();
            return true; // Verificar si se actualizó al menos una fila

        } catch (SQLException e) {
            e.printStackTrace();
            mostrarError("Ocurrió un error al modificar el lote de madera en la base de datos.");
            return false;
        }
    }
    
    @FXML
    private void cancelar(ActionEvent event) {
        // Cerrar la ventana emergente
                Stage stage = (Stage) formGrid.getScene().getWindow();
                stage.close();
    }
    
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
