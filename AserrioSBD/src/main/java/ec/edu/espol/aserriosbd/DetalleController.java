/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.aserriosbd;

import ec.edu.espol.aserriosbd.modelo.ObjetosDAO;
import ec.edu.espol.aserriosbd.modelo.DatabaseConnection;
import ec.edu.espol.aserriosbd.modelo.Detalle;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import java.sql.CallableStatement;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.input.ContextMenuEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.scene.control.SelectionMode;

/**
 * FXML Controller class
 *
 * @author nicol
 */
public class DetalleController implements Initializable {

    @FXML
    private Text text;
    @FXML
    private TableView<Detalle> table;
    private InterfazBase interfazBase;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        interfazBase = new InterfazBase();
        interfazBase.configureTableFromClass(table, text, "Detalles", Detalle.class);
        table.setItems(ObjetosDAO.getDetalleList());
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
            App.setRoot("AñadirDetalle");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void eliminar(MouseEvent event) {
        Detalle detalleSeleccionado = table.getSelectionModel().getSelectedItem();

        if (detalleSeleccionado != null) {
            if (eliminarDetalleDeBD(detalleSeleccionado)) {
                table.getItems().remove(detalleSeleccionado);
            } else {
                mostrarError("No se pudo eliminar el detalle de la base de datos.");
            }
        } else {
            mostrarError("Por favor, selecciona un detalle para eliminar.");
        }
    }

    private boolean eliminarDetalleDeBD(Detalle detalle) {
        String sql = "{call EliminarDetalle(?, ?)}";  // Suposición de que la eliminación requiere ambos IDs (idFactura, idProducto)

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement cstmt = conn.prepareCall(sql)) {

            cstmt.setInt(1, detalle.getIdFactura());
            cstmt.setInt(2, detalle.getIdProducto());

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
