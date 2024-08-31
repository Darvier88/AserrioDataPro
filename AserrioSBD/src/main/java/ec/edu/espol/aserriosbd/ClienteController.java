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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ASUS VIVOBOOK PRO
 */
public class ClienteController implements Initializable {

    @FXML
    private Text text;
    @FXML
    private TableView<Cliente> table;

    private InterfazBase interfazBase;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializar la instancia de InterfazBase
        interfazBase = new InterfazBase();

        // Configurar la tabla con los datos de la clase Cliente
        interfazBase.configureTableFromClass(table, text, "Clientes", Cliente.class);
        table.setItems(ObjetosDAO.getClienteList());
        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    }

    @FXML
    private void regresar(MouseEvent event) {
        try {
            App.setRoot("opcionesSecretaria");
        } catch (IOException ex) { 
            ex.printStackTrace();
        }
    }

    @FXML
    private void irLimpieza(ContextMenuEvent event) {
        // Acción de ir a limpieza
    }

    @FXML
    private void añadir(MouseEvent event) {
        try {
            App.setRoot("AñadirCliente");
    } catch (IOException ex) {
    }
    }

    @FXML
    private void eliminar(MouseEvent event) {
        Cliente clienteSeleccionado = table.getSelectionModel().getSelectedItem();
    
        if (clienteSeleccionado != null) {
            // Lógica para eliminar el cliente de la base de datos
            if (eliminarClienteDeBD(clienteSeleccionado)) {
                // Eliminar el cliente del TableView
                table.getItems().remove(clienteSeleccionado);
            } else {
                mostrarError("No se pudo eliminar el cliente de la base de datos.");
            }
        } else {
            mostrarError("Por favor, selecciona un cliente para eliminar.");
        }
    }
    private boolean eliminarClienteDeBD(Cliente cliente) {
    String sql = "DELETE FROM Cliente WHERE cedula = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cliente.getCedula());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    @FXML
    private void modificar(MouseEvent event) {
        Cliente clienteSeleccionado = table.getSelectionModel().getSelectedItem();

        if (clienteSeleccionado != null) {
            try {
                // Llamar al método que carga la ventana de modificación en un `Stage` modal
                ModificarClienteController.mostrarVentanaModificacion(clienteSeleccionado);
            } catch (IOException ex) {
                ex.printStackTrace();
                mostrarError("No se pudo cargar la ventana de modificación.");
            }
        } else {
            mostrarError("Por favor, selecciona un cliente para modificar.");
        }
    }
}

