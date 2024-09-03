/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.aserriosbd;

import ec.edu.espol.aserriosbd.modelo.DatabaseConnection;
import ec.edu.espol.aserriosbd.modelo.Mantenimiento;
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
 * @author nicol
 */
public class MantenimientoController implements Initializable {

    @FXML
    private Text text;
    @FXML
    private TableView<Mantenimiento> table;

    private InterfazBase interfazBase;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        interfazBase = new InterfazBase();
        interfazBase.configureTableFromClass(table, text, "Mantenimiento", Mantenimiento.class);

        // Configuración de la selección
        table.setItems(ObjetosDAO.getMantenimientoList());
        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    private void regresar(MouseEvent event) {
        try {
            App.setRoot("seccionEmpleados");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void irLimpieza(ContextMenuEvent event) {
        // Aquí se puede añadir lógica específica para la acción de "ir a limpieza"
    }

    @FXML
    private void añadir(MouseEvent event) {
        try {
            App.setRoot("añadirMantenimiento");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private boolean eliminarMantenimientoDeBD(Mantenimiento mant) {
        String sql = "{call EliminarMantenimiento(?)}";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement cstmt = conn.prepareCall(sql)) {

            cstmt.setInt(1, mant.getId());

            // Ejecutar el procedimiento almacenado
            boolean hasResultSet = cstmt.execute();
            return !hasResultSet; // El método execute() devuelve true si hay un ResultSet, false si es solo una actualización
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    private void eliminar(MouseEvent event) {
        Mantenimiento mantSeleccionado = table.getSelectionModel().getSelectedItem();

        if (mantSeleccionado != null) {
            // Crear una ventana de confirmación
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar Eliminación");
            alert.setHeaderText("¿Estás seguro de que deseas eliminar este mantenimiento?");

            ButtonType botonConfirmar = new ButtonType("Eliminar");
            ButtonType botonCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(botonConfirmar, botonCancelar);

            Optional<ButtonType> resultado = alert.showAndWait();
            if (resultado.isPresent() && resultado.get() == botonConfirmar) {
                // Lógica para eliminar el mantenimiento de la base de datos
                if (eliminarMantenimientoDeBD(mantSeleccionado)) {
                    // Eliminar el mantenimiento del TableView
                    table.getItems().remove(mantSeleccionado);
                } else {
                    mostrarError("No se pudo eliminar el mantenimiento de la base de datos.");
                }
            }
        } else {
            mostrarError("Por favor, selecciona un mantenimiento para eliminar.");
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
        Mantenimiento mantSeleccionado = table.getSelectionModel().getSelectedItem();

        if (mantSeleccionado != null) {
            try {
                // Llamar al método que carga la ventana de modificación en un `Stage` modal
                ModificarMantenimientoController.mostrarVentanaModificacion(mantSeleccionado);
            } catch (IOException ex) {
                ex.printStackTrace();
                mostrarError("No se pudo cargar la ventana de modificación.");
            }
        } else {
            mostrarError("Por favor, selecciona un mantenimiento para modificar.");
        }
    }

    
}
