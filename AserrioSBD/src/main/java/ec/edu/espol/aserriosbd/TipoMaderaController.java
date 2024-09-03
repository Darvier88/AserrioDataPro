/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.aserriosbd;

import ec.edu.espol.aserriosbd.modelo.ObjetosDAO;
import ec.edu.espol.aserriosbd.modelo.DatabaseConnection;
import ec.edu.espol.aserriosbd.modelo.SessionManager;
import ec.edu.espol.aserriosbd.modelo.TipoMadera;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

public class TipoMaderaController implements Initializable {

    @FXML
    private Text text;
    @FXML
    private TableView<TipoMadera> table;
    
    private TipoMadera tipoMadera;
    
    private InterfazBase interfazBase;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializar la instancia de InterfazBase
        interfazBase = new InterfazBase();

        // Configurar la tabla con los datos de la clase TipoMadera
        interfazBase.configureTableFromClass(table, text, "Tipos de Madera", TipoMadera.class);
        table.setItems(ObjetosDAO.getTipoMaderaList());
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
        // Acción de ir a limpieza (puedes definir la lógica aquí si es necesario)
    }

    @FXML
    private void añadir(MouseEvent event) {
        try {
            App.setRoot("AñadirTipoMadera");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void eliminar(MouseEvent event) {
        TipoMadera tipoMaderaSeleccionado = table.getSelectionModel().getSelectedItem();

        if (tipoMaderaSeleccionado != null) {
            // Lógica para eliminar el tipo de madera de la base de datos
            // Crear una ventana de confirmación
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar Eliminación");
            alert.setHeaderText("¿Estás seguro de que deseas eliminar este tipo de madera?");
            alert.setContentText("Al eliminar el tipo de madera, todas las instancias relacionadas en las especificaciones se eliminarán.");

            ButtonType botonConfirmar = new ButtonType("Eliminar");
            ButtonType botonCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(botonConfirmar, botonCancelar);
            
            Optional<ButtonType> resultado = alert.showAndWait();
            if(resultado.isPresent() && resultado.get() == botonConfirmar){
                 if (eliminarTipoMaderaDeBD(tipoMaderaSeleccionado)) {
                // Eliminar el tipo de madera del TableView
                table.getItems().remove(tipoMaderaSeleccionado);
                } else {
                    mostrarError("No se pudo eliminar el tipo de madera de la base de datos.");
                }
            }   
        } else {
            mostrarError("Por favor, selecciona un tipo de madera para eliminar.");
        }
    }

    private boolean eliminarTipoMaderaDeBD(TipoMadera tipoMadera) {
    String sql = "{CALL eliminarMadera (?,?)}";

    SessionManager session = SessionManager.getInstance();
        String user = session.getUsuario();
        String password = session.getContraseña();
        
        try (Connection conn = DatabaseConnection.getConnection(user, password);
         CallableStatement cstmt = conn.prepareCall(sql)) {

        // Configurar el parámetro del CallableStatement
        cstmt.setString(1, tipoMadera.getId());
        cstmt.setBoolean(2, true);
        // Ejecutar el procedimiento almacenado
        cstmt.execute(); // Usa execute en lugar de executeUpdate para procedimientos almacenados

        // Comprobar si el cliente fue eliminado exitosamente
        return true; // El procedimiento se ejecutó sin errores

    } catch (SQLException e) {
        e.printStackTrace(); // Imprime el error para depuración
        return false; // Indica que ocurrió un error
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
        TipoMadera tipoMaderaSeleccionado = table.getSelectionModel().getSelectedItem();

        if (tipoMaderaSeleccionado != null) {
            try {
                // Llamar al método que carga la ventana de modificación en un `Stage` modal
                ModificarTipoMaderaController.mostrarVentanaModificacion(tipoMaderaSeleccionado);
            } catch (IOException ex) {
                ex.printStackTrace();
                mostrarError("No se pudo cargar la ventana de modificación.");
            }
        } else {
            mostrarError("Por favor, selecciona un tipo de madera para modificar.");
        }
    }
}

