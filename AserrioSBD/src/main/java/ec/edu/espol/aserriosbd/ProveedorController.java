/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.aserriosbd;

import ec.edu.espol.aserriosbd.modelo.ObjetosDAO;
import ec.edu.espol.aserriosbd.modelo.DatabaseConnection;
import ec.edu.espol.aserriosbd.modelo.Proveedor;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.text.Text;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ProveedorController implements Initializable {

    @FXML
    private Text text;
    @FXML
    private TableView<Proveedor> table;
    private InterfazBase interfazBase;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializar la instancia de InterfazBase
        interfazBase = new InterfazBase();

        // Configurar la tabla con los datos de la clase Proveedor
        interfazBase.configureTableFromClass(table, text, "Proveedores", Proveedor.class);
        table.setItems(ObjetosDAO.getProveedorList());
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
    private void irLimpieza(ContextMenuEvent event) {
        // Acción de ir a limpieza
    }

    @FXML
    private void añadir(MouseEvent event) {
        try {
            App.setRoot("AñadirProveedor");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void eliminar(MouseEvent event) {
        Proveedor proveedorSeleccionado = table.getSelectionModel().getSelectedItem();

        if (proveedorSeleccionado != null) {
            // Lógica para eliminar el proveedor de la base de datos
            if (eliminarProveedorDeBD(proveedorSeleccionado)) {
                // Eliminar el proveedor del TableView
                table.getItems().remove(proveedorSeleccionado);
            } else {
                mostrarError("No se pudo eliminar el proveedor de la base de datos.");
            }
        } else {
            mostrarError("Por favor, selecciona un proveedor para eliminar.");
        }
    }

    private boolean eliminarProveedorDeBD(Proveedor proveedor) {
    String sqlEspecificacion = "DELETE FROM especificacion WHERE id_lote IN (SELECT id FROM lote_madera WHERE id_proveedor = ?)";
    String sqlLoteMadera = "DELETE FROM lote_madera WHERE id_proveedor = ?";
    String sqlEvaluacion = "DELETE FROM evaluacion WHERE id_proveedor = ?";
    String sqlProveedor = "DELETE FROM proveedor WHERE cedula = ?";

    try (Connection conn = DatabaseConnection.getConnection()) {
        conn.setAutoCommit(false); // Iniciar una transacción

        try (PreparedStatement pstmtEspecificacion = conn.prepareStatement(sqlEspecificacion);
             PreparedStatement pstmtLoteMadera = conn.prepareStatement(sqlLoteMadera);
             PreparedStatement pstmtEvaluacion = conn.prepareStatement(sqlEvaluacion);
             PreparedStatement pstmtProveedor = conn.prepareStatement(sqlProveedor)) {

            // Eliminar registros de la tabla especificacion
            pstmtEspecificacion.setString(1, proveedor.getCedula());
            pstmtEspecificacion.executeUpdate();

            // Eliminar registros de la tabla lote_madera
            pstmtLoteMadera.setString(1, proveedor.getCedula());
            pstmtLoteMadera.executeUpdate();

            // Eliminar registros de la tabla evaluacion
            pstmtEvaluacion.setString(1, proveedor.getCedula());
            pstmtEvaluacion.executeUpdate();

            // Finalmente, eliminar el proveedor
            pstmtProveedor.setString(1, proveedor.getCedula());
            int rowsAffected = pstmtProveedor.executeUpdate();

            conn.commit(); // Confirmar la transacción
            return rowsAffected > 0;

        } catch (SQLException e) {
            conn.rollback(); // Revertir la transacción en caso de error
            e.printStackTrace();
            return false;
        }
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
        Proveedor proveedorSeleccionado = table.getSelectionModel().getSelectedItem();

        if (proveedorSeleccionado != null) {
            try {
                // Llamar al método que carga la ventana de modificación en un `Stage` modal
                ModificarProveedorController.mostrarVentanaModificacion(proveedorSeleccionado);
            } catch (IOException ex) {
                ex.printStackTrace();
                mostrarError("No se pudo cargar la ventana de modificación.");
            }
        } else {
            mostrarError("Por favor, selecciona un proveedor para modificar.");
        }
    }


}
