/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.aserriosbd;

import ec.edu.espol.aserriosbd.modelo.DatabaseConnection;
import ec.edu.espol.aserriosbd.modelo.Empleado;
import ec.edu.espol.aserriosbd.modelo.ObjetosDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author ASUS VIVOBOOK PRO
 */
public class OperariosController implements Initializable {

    @FXML
    private Text text;
    @FXML
    private TableView<Empleado> table;
    private InterfazBase interfazBase;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializar la instancia de InterfazBase
        interfazBase = new InterfazBase();

        // Configurar la tabla con los datos de la clase Empleado
        interfazBase.configureTableFromClass(table, text, "Empleados", Empleado.class);
        table.setItems(ObjetosDAO.getOperarioList());
        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }    

    @FXML
    private void irLimpieza(ContextMenuEvent event) {
    }

    @FXML
    private void añadir(MouseEvent event) {
        try {
            App.setRoot("añadirOperario");
        } catch (IOException ex) {
        }
    }
    private boolean eliminarOperarioDeBD(Empleado emp) {
    String sql = "{CALL EliminarOperario(?)}"; // Llamada al procedimiento almacenado

    try (Connection conn = DatabaseConnection.getConnection();
         CallableStatement cstmt = conn.prepareCall(sql)) {

        // Configurar el parámetro del CallableStatement
        cstmt.setString(1, emp.getId()); // Asegúrate de que el tipo de datos coincida

        // Ejecutar el procedimiento almacenado
        cstmt.execute(); // Usa execute en lugar de executeUpdate para procedimientos almacenados

        // Comprobar si la factura fue eliminada exitosamente
        return true; // El procedimiento se ejecutó sin errores

    } catch (SQLException e) {
        e.printStackTrace(); // Imprime el error para depuración
        return false; // Indica que ocurrió un error
    }
}

    @FXML
    private void eliminar(MouseEvent event) {
        Empleado empSeleccionado = table.getSelectionModel().getSelectedItem();

    if (empSeleccionado != null) {
        // Crear una ventana de confirmación
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Eliminación");
        alert.setHeaderText("¿Estás seguro de que deseas eliminar este operario?");
        alert.setContentText("Al eliminar el operario, se eliminarán todos los detalles relacionados con este.");

        ButtonType botonConfirmar = new ButtonType("Eliminar");
        ButtonType botonCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(botonConfirmar, botonCancelar);

        Optional<ButtonType> resultado = alert.showAndWait();
        if (resultado.isPresent() && resultado.get() == botonConfirmar) {
            // Lógica para eliminar la factura de la base de datos
            if (eliminarOperarioDeBD(empSeleccionado)) {
                // Eliminar la factura del TableView
                table.getItems().remove(empSeleccionado);
            } else {
                mostrarError("No se pudo eliminar el operario de la base de datos.");
            }
        }
    } else {
        mostrarError("Por favor, selecciona un operario para eliminar.");
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
        Empleado empSeleccionado = table.getSelectionModel().getSelectedItem();

        if (empSeleccionado != null) {
            try {
                // Llamar al método que carga la ventana de modificación en un `Stage` modal
                ModificarOperarioController.mostrarVentanaModificacion(empSeleccionado);
            } catch (IOException ex) {
                ex.printStackTrace();
                mostrarError("No se pudo cargar la ventana de modificación.");
            }
        } else {
            mostrarError("Por favor, selecciona un empleado para modificar.");
        }
    }

    @FXML
    private void regresar(MouseEvent event) {
        try {
            App.setRoot("seccionEmpleados");
        } catch (IOException ex) { 
            ex.printStackTrace();
        }
    }
    
}
