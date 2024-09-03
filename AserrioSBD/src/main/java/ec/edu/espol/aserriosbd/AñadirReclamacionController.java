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
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author ASUS VIVOBOOK PRO
 */
public class AñadirReclamacionController implements Initializable {

    @FXML
    private VBox mainContainer;
    @FXML
    private GridPane formGrid;
    private AñadirBase<Reclamacion> añadirBase;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         // Inicializar AñadirBase para la clase Proveedor
        añadirBase = new AñadirBase<>(Reclamacion.class);
        
        // Agregar el formulario generado al VBox principal
        mainContainer.getChildren().add(añadirBase.getFormGrid());
    }    
    private boolean insertarReclamacionEnBD(Reclamacion reclamacion) {
    String sql = "{CALL InsertReclamacion(?, ?, ?)}";

    try (Connection conn = DatabaseConnection.getConnection();
         CallableStatement cstmt = conn.prepareCall(sql)) {

        cstmt.setString(1, reclamacion.getIdSecretaria());
        cstmt.setString(2, reclamacion.getIdCliente());
        cstmt.setString(3, reclamacion.getDescripcion());

        // Ejecutar el procedimiento almacenado
        cstmt.execute();
        return true;

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
        // Obtener el objeto Cliente con los valores ingresados
        Reclamacion nuevaReclamacion = añadirBase.getObject();

        // Insertar el cliente en la base de datos
        if (insertarReclamacionEnBD(nuevaReclamacion )) {
            // Limpiar los campos después de añadir
            añadirBase.clearFields();
        } else {
            mostrarError("No se pudo añadir el cliente a la base de datos.");
        }
        App.setRoot("reclamacion");
    } catch (IllegalArgumentException e) {
        mostrarError(e.getMessage()); // Mostrar el mensaje de error al usuario
    } catch (Exception e) {
        e.printStackTrace();
        mostrarError("Ocurrió un error al intentar añadir el cliente.");
    }
    }

    @FXML
    private void cancelar(ActionEvent event) throws IOException {
        App.setRoot("reclamacion");
    }
    
}
