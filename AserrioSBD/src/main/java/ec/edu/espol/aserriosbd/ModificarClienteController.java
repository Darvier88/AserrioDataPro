/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.aserriosbd;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author ASUS VIVOBOOK PRO
 */
public class ModificarClienteController implements Initializable {

    @FXML
    private GridPane formGrid;
    private Cliente cliente;
    private ModificarBase<Cliente> modificarBase;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         // Inicializar ModificarBase con la clase Cliente
        modificarBase = new ModificarBase<>(Cliente.class);

        // Añadir el formulario generado dinámicamente al GridPane
        formGrid.getChildren().addAll(modificarBase.getFormGrid().getChildren());
    }    

    @FXML
    private void modificar(ActionEvent event) {
        try {
            Cliente clienteModificado = modificarBase.getObject();
            // Actualizar el cliente en la base de datos
            actualizarClienteEnBD(clienteModificado);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            mostrarError("Ocurrió un error al intentar modificar el cliente.");
        }
    }
    private void actualizarClienteEnBD(Cliente clienteModificado) {
        String sql = "UPDATE Cliente SET nombre = ?, direccion = ?, num_contacto = ?, correo_contacto = ? WHERE cedula = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, clienteModificado.getNombre());
            pstmt.setString(2, clienteModificado.getDireccion());
            
            // Manejar el caso en que numContacto sea null
            if (clienteModificado.getNumContacto() != null) {
                pstmt.setInt(3, clienteModificado.getNumContacto());
            } else {
                pstmt.setNull(3, java.sql.Types.INTEGER);
            }
            
            pstmt.setString(4, clienteModificado.getCorreoContacto());
            pstmt.setString(5, clienteModificado.getCedula());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Cliente modificado exitosamente.");
            } else {
                mostrarError("No se pudo modificar el cliente en la base de datos.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            mostrarError("Ocurrió un error al modificar el cliente en la base de datos.");
        }
    }
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    // Este método será llamado para cargar los datos del cliente seleccionado
    public void setCliente(Cliente cliente) {
        if (cliente != null) {
            modificarBase.loadObject(cliente);
        }
    }
    @FXML
    private void cancelar(ActionEvent event) {
    }
    
}
