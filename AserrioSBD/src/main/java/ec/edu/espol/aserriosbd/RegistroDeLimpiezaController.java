/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.aserriosbd;

import ec.edu.espol.aserriosbd.modelo.DatabaseConnection;
import ec.edu.espol.aserriosbd.modelo.ObjetosDAO;
import ec.edu.espol.aserriosbd.modelo.RegistroDeLimpieza;
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
public class RegistroDeLimpiezaController implements Initializable {

      @FXML
    private Text text;
    @FXML
    private TableView<RegistroDeLimpieza> table;

    private InterfazBase interfazBase;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        interfazBase = new InterfazBase();
        interfazBase.configureTableFromClass(table, text, "RegistroDeLimpieza", RegistroDeLimpieza.class);

        // Configuración de la selección
        table.setItems(ObjetosDAO.getRegistroLimpiezaList());
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
            App.setRoot("añadirRegistro");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private boolean eliminarRegistroDeLimpiezaDeBD(RegistroDeLimpieza registro) {
        String sql = "{call EliminarRegistro(?, ?, ?)}";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.setString(1, registro.getIdAsistente());
            cstmt.setString(2, registro.getIdSecretaria());
            cstmt.setInt(3, registro.getIdLimpieza());

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
        RegistroDeLimpieza registroSeleccionado = table.getSelectionModel().getSelectedItem();

        if (registroSeleccionado != null) {
            // Crear una ventana de confirmación
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar Eliminación");
            alert.setHeaderText("¿Estás seguro de que deseas eliminar este registro de limpieza?");

            ButtonType botonConfirmar = new ButtonType("Eliminar");
            ButtonType botonCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(botonConfirmar, botonCancelar);

            Optional<ButtonType> resultado = alert.showAndWait();
            if (resultado.isPresent() && resultado.get() == botonConfirmar) {
                // Lógica para eliminar el registro de limpieza de la base de datos
                if (eliminarRegistroDeLimpiezaDeBD(registroSeleccionado)) {
                    // Eliminar el registro de limpieza del TableView
                    table.getItems().remove(registroSeleccionado);
                } else {
                    mostrarError("No se pudo eliminar el registro de limpieza de la base de datos.");
                }
            }
        } else {
            mostrarError("Por favor, selecciona un registro de limpieza para eliminar.");
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
        RegistroDeLimpieza registroSeleccionado = table.getSelectionModel().getSelectedItem();

        if (registroSeleccionado != null) {
            try {
                // Llamar al método que carga la ventana de modificación en un `Stage` modal
                ModificarRegistroController.mostrarVentanaModificacion(registroSeleccionado);
            } catch (IOException ex) {
                ex.printStackTrace();
                mostrarError("No se pudo cargar la ventana de modificación.");
            }
        } else {
            mostrarError("Por favor, selecciona un registro de limpieza para modificar.");
        }
    }
    
}
