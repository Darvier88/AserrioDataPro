/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.aserriosbd;

import ec.edu.espol.aserriosbd.modelo.ObjetosDAO;

import ec.edu.espol.aserriosbd.modelo.DatabaseConnection;

import ec.edu.espol.aserriosbd.modelo.Evaluacion;
import ec.edu.espol.aserriosbd.modelo.SessionManager;
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
public class EvaluacionController implements Initializable {

    @FXML
    private Text text;
    @FXML
    private TableView<Evaluacion> table;
    private InterfazBase interfazBase;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        interfazBase = new InterfazBase();
        interfazBase.configureTableFromClass(table, text, "Evaluaciones", Evaluacion.class);

        // Configuración de la selección
        table.setItems(ObjetosDAO.getEvaluacionList());
        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    private void irLimpieza(ContextMenuEvent event) {
    }

    @FXML
    private void añadir(MouseEvent event) {
        try {
            App.setRoot("AñadirEvaluacion");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void eliminar(MouseEvent event) {
        Evaluacion evaluacionSeleccionada = table.getSelectionModel().getSelectedItem();
        
        if (evaluacionSeleccionada != null) {
            // Lógica para eliminar de la base de datos
            // Crear una ventana de confirmación
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar Eliminación");
            alert.setHeaderText("¿Estás seguro de que deseas eliminar esta evaluacion?");
            alert.setContentText("Seleccione una opción.");

            ButtonType botonConfirmar = new ButtonType("Eliminar");
            ButtonType botonCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(botonConfirmar, botonCancelar);
            
            Optional<ButtonType> resultado = alert.showAndWait();
             if(resultado.isPresent() && resultado.get() == botonConfirmar){   
                    // Lógica para eliminar de la base de datos
                    if (eliminarEvaluacionBD(evaluacionSeleccionada)) {
                        // Eliminar el lote de madera del TableView
                        table.getItems().remove(evaluacionSeleccionada);
                    } else {
                        mostrarError("No se pudo eliminar la evalaucion de la base de datos.");
                    }
                } else {
                    mostrarError("Por favor, selecciona una evaluacion para eliminar.");
                }
            }
    }
    
    private boolean eliminarEvaluacionBD(Evaluacion evaluacion) {
        String sql = "CALL EliminarEvaluacion (?)";
        SessionManager session = SessionManager.getInstance();
        String user = session.getUsuario();
        String password = session.getContraseña();
        
        try (Connection conn = DatabaseConnection.getConnection(user, password);
         CallableStatement cstmt = conn.prepareCall(sql)) {

                // Eliminar registros de la tabla especificacion
                // Configurar el parámetro del CallableStatement
            cstmt.setInt(1, evaluacion.getId());
            
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
        Evaluacion evaluacionSeleccionada = table.getSelectionModel().getSelectedItem();

        if (evaluacionSeleccionada != null) {
            try {
                // Llamar al método que carga la ventana de modificación en un `Stage` modal
                ModificarEvaluacionController.mostrarVentanaModificacion(evaluacionSeleccionada);
            } catch (IOException ex) {
                ex.printStackTrace();
                mostrarError("No se pudo cargar la ventana de modificación.");
            }
        } else {
            mostrarError("Por favor, selecciona una evaluacion para modificar.");
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
