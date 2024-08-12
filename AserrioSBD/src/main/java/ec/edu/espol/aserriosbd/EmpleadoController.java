package ec.edu.espol.aserriosbd;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class for Empleado
 *
 * @author ASUS VIVOBOOK PRO
 */
public class EmpleadoController implements Initializable {

    @FXML
    private Text text;
    @FXML
    private TableView<Empleado> table;

    private InterfazBase interfazBase;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializar la instancia de InterfazBase
        interfazBase = new InterfazBase();

        // Configurar la tabla con los datos de la clase Empleado
        interfazBase.configureTableFromClass(table, text, "Empleados", Empleado.class);
        table.setItems(ObjetosDAO.getEmpleadoList());
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
            App.setRoot("AñadirEmpleado");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void eliminar(MouseEvent event) {
        Empleado empleadoSeleccionado = table.getSelectionModel().getSelectedItem();

        if (empleadoSeleccionado != null) {
            // Lógica para eliminar el empleado de la base de datos
            if (eliminarEmpleadoDeBD(empleadoSeleccionado)) {
                // Eliminar el empleado del TableView
                table.getItems().remove(empleadoSeleccionado);
            } else {
                mostrarError("No se pudo eliminar el empleado de la base de datos.");
            }
        } else {
            mostrarError("Por favor, selecciona un empleado para eliminar.");
        }
    }

    private boolean eliminarEmpleadoDeBD(Empleado empleado) {
        String sql = "DELETE FROM Empleado WHERE ID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, empleado.getId());

            int rowsAffected = pstmt.executeUpdate();
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
        Empleado empleadoSeleccionado = table.getSelectionModel().getSelectedItem();

        if (empleadoSeleccionado != null) {
            try {
                // Llamar al método que carga la ventana de modificación en un `Stage` modal
                ModificarEmpleadoController.mostrarVentanaModificacion(empleadoSeleccionado);
            } catch (IOException ex) {
                ex.printStackTrace();
                mostrarError("No se pudo cargar la ventana de modificación.");
            }
        } else {
            mostrarError("Por favor, selecciona un empleado para modificar.");
        }
    }
}
