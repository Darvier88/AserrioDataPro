/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.aserriosbd;

import ec.edu.espol.aserriosbd.modelo.DatabaseConnection;
import java.io.IOException;
import ec.edu.espol.aserriosbd.modelo.ObjetosDAO;
import ec.edu.espol.aserriosbd.modelo.Limpieza;
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
public class LimpiezaController implements Initializable {

    @FXML
    private Text text;
    @FXML
    private TableView<Limpieza> table;

    private InterfazBase interfazBase;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializar la instancia de InterfazBase
        interfazBase = new InterfazBase();
        interfazBase.configureTableFromClass(table, text, "Limpieza", Limpieza.class);

        // Configuración de la selección
        table.setItems(ObjetosDAO.getLimpiezaList());
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
        // Lógica para ir a la vista de limpieza
    }

    @FXML
    private void añadir(MouseEvent event) {
        try {
            App.setRoot("añadirLimpieza");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private boolean eliminarLimpiezaDeBD(Limpieza limpieza) {
        String sql = "{call EliminarLimpieza(?)}";  // Suposición de que la eliminación requiere el código de limpieza

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement cstmt = conn.prepareCall(sql)) {

            cstmt.setInt(1, limpieza.getId());

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
        Limpieza limpiezaSeleccionada = table.getSelectionModel().getSelectedItem();

        if (limpiezaSeleccionada != null) {
            // Crear una ventana de confirmación
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar Eliminación");
            alert.setHeaderText("¿Estás seguro de que deseas eliminar esta limpieza?");
            alert.setContentText("Al eliminar la limpieza, se eliminarán todos los detalles relacionados con ella.");

            ButtonType botonConfirmar = new ButtonType("Eliminar");
            ButtonType botonCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(botonConfirmar, botonCancelar);

            Optional<ButtonType> resultado = alert.showAndWait();
            if (resultado.isPresent() && resultado.get() == botonConfirmar) {
                // Lógica para eliminar la limpieza de la base de datos
                if (eliminarLimpiezaDeBD(limpiezaSeleccionada)) {
                    // Eliminar la limpieza del TableView
                    table.getItems().remove(limpiezaSeleccionada);
                } else {
                    mostrarError("No se pudo eliminar la limpieza de la base de datos.");
                }
            }
        } else {
            mostrarError("Por favor, selecciona una limpieza para eliminar.");
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
        Limpieza limpiezaSeleccionada = table.getSelectionModel().getSelectedItem();

        if (limpiezaSeleccionada != null) {
            try {
                // Llamar al método que carga la ventana de modificación en un `Stage` modal
                ModificarLimpiezaController.mostrarVentanaModificacion(limpiezaSeleccionada);
            } catch (IOException ex) {
                ex.printStackTrace();
                mostrarError("No se pudo cargar la ventana de modificación.");
            }
        } else {
            mostrarError("Por favor, selecciona un detalle para modificar.");
        }
    }

    
}
