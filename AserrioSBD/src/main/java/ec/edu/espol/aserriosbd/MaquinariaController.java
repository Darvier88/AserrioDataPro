/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.aserriosbd;

import ec.edu.espol.aserriosbd.modelo.DatabaseConnection;
import ec.edu.espol.aserriosbd.modelo.ObjetosDAO;
import ec.edu.espol.aserriosbd.modelo.Maquinaria;
import javafx.scene.control.SelectionMode;
import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author nicol
 */
public class MaquinariaController implements Initializable {

    
    @FXML
    private Text text;
    @FXML
    private TableView<Maquinaria> table;
    
    private InterfazBase interfazBase;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        interfazBase = new InterfazBase();
        interfazBase.configureTableFromClass(table, text, "Maquinaria", Maquinaria.class);

        // Configuración de la selección
        table.setItems(ObjetosDAO.getMaquinariaList());
        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }   

    @FXML
    private void regresar(MouseEvent event) {
        try {
            App.setRoot("seccionEmpleados");
        } catch (IOException ex) { 
        }
    
    }

    @FXML
    private void irLimpieza(ContextMenuEvent event) {
    }

    @FXML
    private void añadir(MouseEvent event) {
        try {
            App.setRoot("añadirMaquinaria");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private boolean eliminarMaquinariaDeBD(Maquinaria maq) {
        String sql = "{call EliminarMaquinaria(?)}";  // Suposición de que la eliminación requiere ambos IDs (idFactura, idProducto)

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement cstmt = conn.prepareCall(sql)) {

            cstmt.setInt(1, maq.getCodigo());

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
        Maquinaria maqSeleccionada = table.getSelectionModel().getSelectedItem();

        if (maqSeleccionada != null) {
            // Crear una ventana de confirmación
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar Eliminación");
            alert.setHeaderText("¿Estás seguro de que deseas eliminar esta maquinaria?");
            alert.setContentText("Al eliminar la maquinaria, se eliminarán todos los detalles relacionados con ella.");

            ButtonType botonConfirmar = new ButtonType("Eliminar");
            ButtonType botonCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(botonConfirmar, botonCancelar);

            Optional<ButtonType> resultado = alert.showAndWait();
            if (resultado.isPresent() && resultado.get() == botonConfirmar) {
                // Lógica para eliminar la factura de la base de datos
                if (eliminarMaquinariaDeBD(maqSeleccionada)) {
                    // Eliminar la factura del TableView
                    table.getItems().remove(maqSeleccionada);
                } else {
                    mostrarError("No se pudo eliminar la maquinaria de la base de datos.");
                }
            }
        } else {
            mostrarError("Por favor, selecciona una factura para eliminar.");
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
        Maquinaria maqSeleccionada = table.getSelectionModel().getSelectedItem();

        if (maqSeleccionada != null) {
            try {
                // Llamar al método que carga la ventana de modificación en un `Stage` modal
                ModificarMaquinariaController.mostrarVentanaModificacion(maqSeleccionada);
            } catch (IOException ex) {
                ex.printStackTrace();
                mostrarError("No se pudo cargar la ventana de modificación.");
            }
        } else {
            mostrarError("Por favor, selecciona un detalle para modificar.");
        }
    }   
}
