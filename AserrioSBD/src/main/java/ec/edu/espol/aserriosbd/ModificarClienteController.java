/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.aserriosbd;

import ec.edu.espol.aserriosbd.modelo.DatabaseConnection;
import ec.edu.espol.aserriosbd.modelo.Cliente;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;

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
    public void setCliente(Cliente cliente) {
        if (cliente != null) {
            this.cliente = cliente;
            modificarBase.loadObject(cliente);
        }
    }

    public static void cargarVistaConCliente(Cliente cliente) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("modificarCliente.fxml"));
        Parent root = loader.load();

        ModificarClienteController controller = loader.getController();
        controller.setCliente(cliente);

        App.setRoot("modificarCliente");
    }
    public static void mostrarVentanaModificacion(Cliente cliente) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("ModificarCliente.fxml"));
        Parent root = loader.load();

        ModificarClienteController controller = loader.getController();
        controller.setCliente(cliente);

        Stage stage = new Stage();
        stage.setTitle("Modificar Cliente");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL); // Establecer la modalidad de la ventana
        stage.showAndWait(); // Mostrar la ventana y esperar hasta que sea cerrada
    }
    @FXML
    private void modificar(ActionEvent event) throws IOException {
        try {
            // Obtener los valores modificados del formulario
            Cliente clienteModificado = modificarBase.getObject();

            // Actualizar el cliente en la base de datos
            if (actualizarClienteEnBD(clienteModificado)) {
                System.out.println("Cliente modificado exitosamente.");
                App.setRoot("cliente");
                // Cerrar la ventana emergente
                Stage stage = (Stage) formGrid.getScene().getWindow();
                stage.close();

                // Recargar la ventana principal
                App.setRoot("cliente");
            } else {
                mostrarError("No se pudo modificar el cliente en la base de datos.");
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            mostrarError("Ocurrió un error al intentar modificar el cliente.");
        }
    }

    private boolean actualizarClienteEnBD(Cliente clienteModificado) {
    String sql = "{CALL ActualizarCliente(?, ?, ?, ?, ?)}";

    try (Connection conn = DatabaseConnection.getConnection();
         CallableStatement cstmt = conn.prepareCall(sql)) {

        // Establecer los parámetros del procedimiento almacenado
        cstmt.setString(1, clienteModificado.getCedula());
        cstmt.setString(2, clienteModificado.getNombre());
        cstmt.setString(3, clienteModificado.getDireccion());
        
        // Manejar el caso en que numContacto sea null
        if (clienteModificado.getNumContacto() != null) {
            cstmt.setInt(4, clienteModificado.getNumContacto());
        } else {
            cstmt.setNull(4, java.sql.Types.INTEGER);
        }

        cstmt.setString(5, clienteModificado.getCorreoContacto());

        // Ejecutar el procedimiento almacenado
        cstmt.executeUpdate();
        return true;

    } catch (SQLException e) {
        e.printStackTrace();
        mostrarError("Ocurrió un error al modificar el cliente en la base de datos.");
        return false;
    }
}


    private void mostrarError(String mensaje) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Este método será llamado para cargar los datos del cliente seleccionado.
     */
    @FXML
    private void cancelar(ActionEvent event) throws IOException {
        // Cerrar la ventana emergente
                Stage stage = (Stage) formGrid.getScene().getWindow();
                stage.close();
    }


}

