/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.aserriosbd;

import ec.edu.espol.aserriosbd.modelo.DatabaseConnection;
import ec.edu.espol.aserriosbd.modelo.ObjetosDAO;
import ec.edu.espol.aserriosbd.modelo.RolDePagos;
import java.io.IOException;
import javafx.scene.control.SelectionMode;
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
import javafx.scene.control.TableView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author nicol
 */
public class RolDePagosController implements Initializable {

      @FXML
    private Text text;
    @FXML
    private TableView<RolDePagos> table;

    private InterfazBase interfazBase;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        interfazBase = new InterfazBase();
        interfazBase.configureTableFromClass(table, text, "Rol de Pagos", RolDePagos.class);

        // Configuración de la selección
        table.setItems(ObjetosDAO.getRolDePagosList());
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
    private void añadir(MouseEvent event) {
        try {
            App.setRoot("añadirRolDePagos");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private boolean eliminarRolDePagosDeBD(RolDePagos rolDePagos) {
        String sql = "{call EliminarRolDePago(?)}";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.setInt(1, rolDePagos.getId());

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
        RolDePagos rolDePagosSeleccionado = table.getSelectionModel().getSelectedItem();

        if (rolDePagosSeleccionado != null) {
            // Crear una ventana de confirmación
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar Eliminación");
            alert.setHeaderText("¿Estás seguro de que deseas eliminar este rol de pago?");

            ButtonType botonConfirmar = new ButtonType("Eliminar");
            ButtonType botonCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(botonConfirmar, botonCancelar);

            Optional<ButtonType> resultado = alert.showAndWait();
            if (resultado.isPresent() && resultado.get() == botonConfirmar) {
                // Lógica para eliminar el rol de pago de la base de datos
                if (eliminarRolDePagosDeBD(rolDePagosSeleccionado)) {
                    // Eliminar el rol de pago del TableView
                    table.getItems().remove(rolDePagosSeleccionado);
                } else {
                    mostrarError("No se pudo eliminar el rol de pago de la base de datos.");
                }
            }
        } else {
            mostrarError("Por favor, selecciona un rol de pago para eliminar.");
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
        RolDePagos rolDePagosSeleccionado = table.getSelectionModel().getSelectedItem();

        if (rolDePagosSeleccionado != null) {
            try {
                // Llamar al método que carga la ventana de modificación en un `Stage` modal
                ModificarRolDePagosController.mostrarVentanaModificacion(rolDePagosSeleccionado);
            } catch (IOException ex) {
                ex.printStackTrace();
                mostrarError("No se pudo cargar la ventana de modificación.");
            }
        } else {
            mostrarError("Por favor, selecciona un rol de pago para modificar.");
        }
    }

    @FXML
    private void irLimpieza(ContextMenuEvent event) {
    }
    
}
