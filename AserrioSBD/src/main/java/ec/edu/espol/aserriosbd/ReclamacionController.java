/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.aserriosbd;

import ec.edu.espol.aserriosbd.modelo.ObjetosDAO;
import ec.edu.espol.aserriosbd.modelo.DatabaseConnection;
import ec.edu.espol.aserriosbd.modelo.Reclamacion;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.scene.control.SelectionMode;

/**
 * FXML Controller class
 *
 * @author nicol
 */
public class ReclamacionController implements Initializable {

    @FXML
    private Text text;
    @FXML
    private TableView<Reclamacion> table;
    private InterfazBase interfazBase;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializar la instancia de InterfazBase
        interfazBase = new InterfazBase();

        // Configurar la tabla con los datos de la clase Multa
        interfazBase.configureTableFromClass(table, text, "Reclamaciones", Reclamacion.class);
        table.setItems(ObjetosDAO.getReclamacionList());
        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    private void regresar(MouseEvent event) {
        try {
            App.setRoot("opcionesSecretaria");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void añadir(MouseEvent event) {
        try {
            App.setRoot("AñadirMulta");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void eliminar(MouseEvent event) {
        Reclamacion multaSeleccionada = (Reclamacion) table.getSelectionModel().getSelectedItem();

        if (multaSeleccionada != null) {
            if (eliminarMultaDeBD(multaSeleccionada)) {
                table.getItems().remove(multaSeleccionada);
            } else {
                mostrarError("No se pudo eliminar la multa de la base de datos.");
            }
        } else {
            mostrarError("Por favor, selecciona una multa para eliminar.");
        }
    }

    private boolean eliminarMultaDeBD(Reclamacion multa) {
        String sql = "{call EliminarMulta(?)}"; // Llamada al procedimiento almacenado

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement cstmt = conn.prepareCall(sql)) {

            cstmt.setInt(1, multa.getId());

            int rowsAffected = cstmt.executeUpdate();
            return rowsAffected > 0;

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
        
    }
    
}
