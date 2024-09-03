package ec.edu.espol.aserriosbd;

import ec.edu.espol.aserriosbd.modelo.ObjetosDAO;
import ec.edu.espol.aserriosbd.modelo.DatabaseConnection;
import ec.edu.espol.aserriosbd.modelo.Empleado;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

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
            App.setRoot("seccionEmpleados");
        } catch (IOException ex) { 
            ex.printStackTrace();
        }
    }


    private void añadir(MouseEvent event) {
        try {
            App.setRoot("AñadirEmpleado");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

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

    private void modificar(MouseEvent event) {
        Empleado empleadoSeleccionado = table.getSelectionModel().getSelectedItem();

        if (empleadoSeleccionado != null) {
            try {
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
