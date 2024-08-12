/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.aserriosbd;

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

public class LoteMaderaController implements Initializable {

    @FXML
    private Text text;
    @FXML
    private TableView<LoteMadera> table;
    private InterfazBase interfazBase;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializar la instancia de InterfazBase
        interfazBase = new InterfazBase();

        // Configurar la tabla con los datos de la clase LoteMadera
        interfazBase.configureTableFromClass(table, text, "Lotes de Madera", LoteMadera.class);
        table.setItems(ObjetosDAO.getLoteMaderaList());
        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    private void regresar(MouseEvent event) {
        try {
            App.setRoot("inicio");
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
            App.setRoot("AñadirLoteMadera");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void eliminar(MouseEvent event) {
        LoteMadera loteMaderaSeleccionado = table.getSelectionModel().getSelectedItem();

        if (loteMaderaSeleccionado != null) {
            // Lógica para eliminar el lote de madera de la base de datos
            if (eliminarLoteMaderaDeBD(loteMaderaSeleccionado)) {
                // Eliminar el lote de madera del TableView
                table.getItems().remove(loteMaderaSeleccionado);
            } else {
                mostrarError("No se pudo eliminar el lote de madera de la base de datos.");
            }
        } else {
            mostrarError("Por favor, selecciona un lote de madera para eliminar.");
        }
    }

    private boolean eliminarLoteMaderaDeBD(LoteMadera loteMadera) {
        String sqlEspecificacion = "DELETE FROM especificacion WHERE id_lote = ?";
        String sqlLoteMadera = "DELETE FROM lote_madera WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false); // Iniciar una transacción

            try (PreparedStatement pstmtEspecificacion = conn.prepareStatement(sqlEspecificacion);
                 PreparedStatement pstmtLoteMadera = conn.prepareStatement(sqlLoteMadera)) {

                // Eliminar registros de la tabla especificacion
                pstmtEspecificacion.setInt(1, loteMadera.getId());
                pstmtEspecificacion.executeUpdate();

                // Finalmente, eliminar el lote de madera
                pstmtLoteMadera.setInt(1, loteMadera.getId());
                int rowsAffected = pstmtLoteMadera.executeUpdate();

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
        LoteMadera loteMaderaSeleccionado = table.getSelectionModel().getSelectedItem();

        if (loteMaderaSeleccionado != null) {
            try {
                // Llamar al método que carga la ventana de modificación en un `Stage` modal
                ModificarLoteMaderaController.mostrarVentanaModificacion(loteMaderaSeleccionado);
            } catch (IOException ex) {
                ex.printStackTrace();
                mostrarError("No se pudo cargar la ventana de modificación.");
            }
        } else {
            mostrarError("Por favor, selecciona un lote de madera para modificar.");
        }
    }
}

