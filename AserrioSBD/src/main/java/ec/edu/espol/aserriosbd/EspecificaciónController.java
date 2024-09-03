/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.aserriosbd;

import ec.edu.espol.aserriosbd.modelo.ObjetosDAO;
import ec.edu.espol.aserriosbd.modelo.DatabaseConnection;
import ec.edu.espol.aserriosbd.modelo.Especificación;
import ec.edu.espol.aserriosbd.modelo.LoteMadera;
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
public class EspecificaciónController implements Initializable {

    @FXML
    private Text text;
    @FXML
    private TableView<Especificación> table;
    
    private Especificación especificacion;
    private InterfazBase interfazBase;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        interfazBase = new InterfazBase();
        interfazBase.configureTableFromClass(table, text, "Especificaciones", Especificación.class);

        // Configuración de la selección
        table.setItems(ObjetosDAO.getEspecificacionList());
        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }    

    @FXML
    private void irLimpieza(ContextMenuEvent event) {
    }

    @FXML
    private void añadir(MouseEvent event) {
        try {
            App.setRoot("añadirEspecificación");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void eliminar(MouseEvent event) {
        Especificación especificacionSeleccionada = table.getSelectionModel().getSelectedItem();
        
        if (especificacionSeleccionada != null) {
            // Lógica para eliminar de la base de datos
            // Crear una ventana de confirmación
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar Eliminación");
            alert.setHeaderText("¿Estás seguro de que deseas eliminar esta especificación?");
            alert.setContentText("Al eliminar la especificación, todas las instaciones relacionas en lote de madera se actualizarán con el nuevo valor deducido.");

            ButtonType botonConfirmar = new ButtonType("Eliminar");
            ButtonType botonCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(botonConfirmar, botonCancelar);
            
            Optional<ButtonType> resultado = alert.showAndWait();
             if(resultado.isPresent() && resultado.get() == botonConfirmar){   
                    // Lógica para eliminar de la base de datos
                    if (eliminarEspecificacionBD(especificacionSeleccionada)) {
                        // Eliminar el lote de madera del TableView
                        table.getItems().remove(especificacionSeleccionada);
                    } else {
                        mostrarError("No se pudo eliminar la especificación de la base de datos.");
                    }
                } else {
                    mostrarError("Por favor, selecciona una especificación para eliminar.");
                }
            }
    }
    
    private boolean eliminarEspecificacionBD(Especificación especificacion) {
        String sql = "CALL EliminarEspecificacion (?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
         CallableStatement cstmt = conn.prepareCall(sql)) {

                // Eliminar registros de la tabla especificacion
                // Configurar el parámetro del CallableStatement
            cstmt.setInt(1, especificacion.getIdLote());
            cstmt.setString(2, especificacion.getIdMadera());
            cstmt.execute(); // Usa execute en lugar de executeUpdate para procedimientos almacenados

        // Comprobar fue eliminado exitosamente
            return true;

            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
    }

    @FXML
    private void modificar(MouseEvent event) {
        Especificación especificacionSeleccionada = table.getSelectionModel().getSelectedItem();

        if (especificacionSeleccionada != null) {
            try {
                // Llamar al método que carga la ventana de modificación en un `Stage` modal
                ModificarEspecificacionesController.mostrarVentanaModificacion(especificacionSeleccionada);
            } catch (IOException ex) {
                ex.printStackTrace();
                mostrarError("No se pudo cargar la ventana de modificación.");
            }
        } else {
            mostrarError("Por favor, selecciona una especificación para modificar.");
        }
    }

    @FXML
    private void regresar(MouseEvent event) {
        try {
            App.setRoot("seccionCompras");
        } catch (IOException ex) { 
            ex.printStackTrace();
        }
    }
    
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    

    
}
