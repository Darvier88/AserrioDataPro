/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.aserriosbd;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author ASUS VIVOBOOK PRO
 */
public class AñadirClienteController implements Initializable {

    @FXML
    private VBox mainContainer;
    @FXML
    private GridPane formGrid;
    private AñadirBase<Cliente> añadirBase;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializar AñadirBase para la clase Cliente
        añadirBase = new AñadirBase<>(Cliente.class);
        
        // Agregar el formulario generado al VBox principal
        mainContainer.getChildren().add(añadirBase.getFormGrid());
    }    

    private boolean insertarClienteEnBD(Cliente cliente) {
    String sql = "INSERT INTO Cliente (cedula, nombre, direccion, num_contacto, correo_contacto) VALUES (?, ?, ?, ?, ?)";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        // Configurar los parámetros del PreparedStatement
        pstmt.setString(1, cliente.getCedula());
        pstmt.setString(2, cliente.getNombre());
        pstmt.setString(3, cliente.getDireccion());

        // Manejo de nulos para enteros
        if (cliente.getNumContacto() == null) {
            pstmt.setNull(4, java.sql.Types.INTEGER);
        } else {
            pstmt.setInt(4, cliente.getNumContacto());
        }

        pstmt.setString(5, cliente.getCorreoContacto());

        // Ejecutar la actualización
        int rowsAffected = pstmt.executeUpdate();
        return rowsAffected > 0;  // Verifica si se insertó al menos una fila

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
        Cliente nuevoCliente = añadirBase.getObject();

        // Insertar el cliente en la base de datos
        if (insertarClienteEnBD(nuevoCliente)) {
            // Limpiar los campos después de añadir
            añadirBase.clearFields();
        } else {
            mostrarError("No se pudo añadir el cliente a la base de datos.");
        }
    } catch (IllegalArgumentException e) {
        mostrarError(e.getMessage()); // Mostrar el mensaje de error al usuario
    } catch (Exception e) {
        e.printStackTrace();
        mostrarError("Ocurrió un error al intentar añadir el cliente.");
    }
    }

    @FXML
    private void cancelar(ActionEvent event) {
        
    }
    
}
