/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.aserriosbd;

import ec.edu.espol.aserriosbd.modelo.ObjetosDAO;
import ec.edu.espol.aserriosbd.modelo.DatabaseConnection;
import ec.edu.espol.aserriosbd.modelo.Proveedor;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.text.Text;
import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ProveedorController implements Initializable {

    @FXML
    private Text text;
    @FXML
    private TableView<Proveedor> table;
    private InterfazBase interfazBase;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializar la instancia de InterfazBase
        interfazBase = new InterfazBase();

        // Configurar la tabla con los datos de la clase Proveedor
        interfazBase.configureTableFromClass(table, text, "Proveedores", Proveedor.class);
        table.setItems(ObjetosDAO.getProveedorList());
        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    private void regresar(MouseEvent event) {
        try {
            App.setRoot("seccionCompras");
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
            App.setRoot("AñadirProveedor");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void eliminar(MouseEvent event) {
        Proveedor proveedorSeleccionado = table.getSelectionModel().getSelectedItem();
        if (proveedorSeleccionado != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar Eliminación");
            alert.setHeaderText("¿Estás seguro de que deseas eliminar este proveedor?");
            alert.setContentText("Al eliminar el proveedor, se eliminarán también todas las instancias relacionadas en las evaluaciones, los lotes de madera, y las especificaciones asociadas a estos lotes.");
            ButtonType botonConfirmar = new ButtonType("Eliminar");
            ButtonType botonCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(botonConfirmar, botonCancelar);
            
            Optional<ButtonType> resultado = alert.showAndWait();
            if(resultado.isPresent() && resultado.get() == botonConfirmar){
            // Lógica para eliminar el proveedor de la base de datos
                if (eliminarProveedorDeBD(proveedorSeleccionado)) {
                    // Eliminar el proveedor del TableView
                    table.getItems().remove(proveedorSeleccionado);
                } else {
                    mostrarError("No se pudo eliminar el proveedor de la base de datos.");
                }
            } else {
                mostrarError("Por favor, selecciona un proveedor para eliminar.");
            }
        }
    }

    private boolean eliminarProveedorDeBD(Proveedor proveedor) {
    String sql = "CALL EliminarProveedor (?, ?)";
    
    try (Connection conn = DatabaseConnection.getConnection();
         CallableStatement cstmt = conn.prepareCall(sql)) {

                // Eliminar registros de la tabla especificacion
                // Configurar el parámetro del CallableStatement
            cstmt.setString(1, proveedor.getCedula());
            cstmt.setBoolean(2, true);
            cstmt.execute(); // Usa execute en lugar de executeUpdate para procedimientos almacenados

        // Comprobar fue eliminado exitosamente
            return true;

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
        Proveedor proveedorSeleccionado = table.getSelectionModel().getSelectedItem();

        if (proveedorSeleccionado != null) {
            try {
                // Llamar al método que carga la ventana de modificación en un `Stage` modal
                ModificarProveedorController.mostrarVentanaModificacion(proveedorSeleccionado);
            } catch (IOException ex) {
                ex.printStackTrace();
                mostrarError("No se pudo cargar la ventana de modificación.");
            }
        } else {
            mostrarError("Por favor, selecciona un proveedor para modificar.");
        }
    }


}
