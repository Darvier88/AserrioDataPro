/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.aserriosbd;

import ec.edu.espol.aserriosbd.modelo.DatabaseConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Zambrano
 */
public class LoginDueñoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    private static final String USER = "dueño";
    @FXML
    private PasswordField contraseña;
    @FXML
    private Button inicioSesionBoton;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void mostrarDatos(MouseEvent event) {
        try(Connection connection = DatabaseConnection.getConnection("dueño", contraseña.getText())) {
             if (connection != null) {
            System.out.println("Conexión exitosa a la base de datos con usuario: dueño" );
            // Cambia la pantalla a "opcionesSecretaria" o cualquier otra dependiendo del rol
            App.setRoot("opcionesSecretaria");
            } else {
                mostrarAlertaError("Conexión fallida", "Fallo en la conexión, revisa las credenciales.");
                // Puedes mostrar un mensaje de error en la interfaz
            }
        } catch (SQLException ex) {
            System.err.println("Error al conectar a la base de datos: " + ex.getMessage());
            // Aquí podrías mostrar un mensaje de error al usuario
        } catch (IOException ex) {
            System.err.println("Error al cambiar de pantalla: " + ex.getMessage());
        }
    }
    
    private void mostrarAlertaError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    private void regresar(MouseEvent event) throws IOException {
        App.setRoot("inicioPerfiles");
    }
}
