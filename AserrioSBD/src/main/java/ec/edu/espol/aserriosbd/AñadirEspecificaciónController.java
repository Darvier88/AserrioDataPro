/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.aserriosbd;

import ec.edu.espol.aserriosbd.modelo.DatabaseConnection;
import ec.edu.espol.aserriosbd.modelo.Especificación;
import ec.edu.espol.aserriosbd.modelo.LoteMadera;
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
public class AñadirEspecificaciónController implements Initializable {

    @FXML
    private VBox mainContainer;
    @FXML
    private GridPane formGrid;
    private AñadirBase<Especificación> añadirBase;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializar AñadirBase
        añadirBase = new AñadirBase<>(Especificación.class);
    // Agregar el formulario generado al VBox principal
        mainContainer.getChildren().add(añadirBase.getFormGrid());
    }    
    
     private boolean insertarEspecificacionEnBD(Especificación especificacion) {
        String sql = "CALL InsertEspecificacion (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement cstmt = conn.prepareCall(sql)) {

            cstmt.setInt(1, especificacion.getIdLote());
            cstmt.setString(2, especificacion.getIdMadera());
            cstmt.setInt(3, 0);
            cstmt.setInt(4, especificacion.getCantidad()); 

            boolean hasResultSet = cstmt.execute();
            System.out.println("Resultado del insert: " + !hasResultSet);
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
            Especificación especificacion = añadirBase.getObject();
            // Insertar  en la base de datos
            if (insertarEspecificacionEnBD(especificacion)) {
                // Limpiar los campos después de añadir
                añadirBase.clearFields();
            } else {
                mostrarError("No se pudo añadir la especificación a la base de datos.");
            }

            App.setRoot("especificación");
        } catch (IllegalArgumentException e) {
            mostrarError(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            mostrarError("Ocurrió un error al intentar añadir la especificación");
        }
    }
     @FXML
    private void cancelar(ActionEvent event) throws IOException {
        App.setRoot("especificación");
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
}
