/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.aserriosbd;

import ec.edu.espol.aserriosbd.modelo.ObjetosDAO;
import ec.edu.espol.aserriosbd.modelo.DatabaseConnection;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TipoMaderaController implements Initializable {

    @FXML
    private Text text;
    @FXML
    private TableView<TipoMadera> table;
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
            if (eliminarTipoMaderaDeBD(tipoMaderaSeleccionado)) {
                // Eliminar el tipo de madera del TableView
                table.getItems().remove(tipoMaderaSeleccionado);
            } else {
                mostrarError("No se pudo eliminar el tipo de madera de la base de datos.");
            }
        } else {
            mostrarError("Por favor, selecciona un tipo de madera para eliminar.");
        }
    }

    private boolean eliminarTipoMaderaDeBD(TipoMadera tipoMadera) {
    String sqlEspecificacion = "DELETE FROM especificacion WHERE id_madera = ?";
    String sqlTipoMadera = "DELETE FROM tipo_de_madera WHERE id = ?";

    try (Connection conn = DatabaseConnection.getConnection()) {
        conn.setAutoCommit(false); // Iniciar una transacción

        try (PreparedStatement pstmtEspecificacion = conn.prepareStatement(sqlEspecificacion);
             PreparedStatement pstmtTipoMadera = conn.prepareStatement(sqlTipoMadera)) {

            // Eliminar registros de la tabla especificacion donde id_madera es igual al id del tipo de madera
            pstmtEspecificacion.setString(1, tipoMadera.getId());
            pstmtEspecificacion.executeUpdate();

            // Eliminar el tipo de madera
            pstmtTipoMadera.setString(1, tipoMadera.getId());
            int rowsAffected = pstmtTipoMadera.executeUpdate();

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

