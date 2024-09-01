package ec.edu.espol.aserriosbd;

import ec.edu.espol.aserriosbd.modelo.DatabaseConnection;
import ec.edu.espol.aserriosbd.modelo.Empleado;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
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

public class ModificarEmpleadoController implements Initializable {

    @FXML
    private GridPane formGrid;
    private Empleado empleado;
    private ModificarBase<Empleado> modificarBase;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializar ModificarBase con la clase Empleado
        modificarBase = new ModificarBase<>(Empleado.class);

        // Añadir el formulario generado dinámicamente al GridPane
        formGrid.getChildren().addAll(modificarBase.getFormGrid().getChildren());
    }

    public void setEmpleado(Empleado empleado) {
        if (empleado != null) {
            this.empleado = empleado;
            modificarBase.loadObject(empleado);
        }
    }

    public static void cargarVistaConEmpleado(Empleado empleado) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("modificarEmpleado.fxml"));
        Parent root = loader.load();

        ModificarEmpleadoController controller = loader.getController();
        controller.setEmpleado(empleado);

        App.setRoot("modificarEmpleado");
    }

    public static void mostrarVentanaModificacion(Empleado empleado) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("ModificarEmpleado.fxml"));
        Parent root = loader.load();

        ModificarEmpleadoController controller = loader.getController();
        controller.setEmpleado(empleado);

        Stage stage = new Stage();
        stage.setTitle("Modificar Empleado");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL); // Establecer la modalidad de la ventana
        stage.showAndWait(); // Mostrar la ventana y esperar hasta que sea cerrada
    }

    @FXML
    private void modificar(ActionEvent event) throws IOException {
        try {
            // Obtener los valores modificados del formulario
            Empleado empleadoModificado = modificarBase.getObject();

            // Actualizar el empleado en la base de datos
            if (actualizarEmpleadoEnBD(empleadoModificado)) {
                System.out.println("Empleado modificado exitosamente.");
                App.setRoot("empleado");
                // Cerrar la ventana emergente
                Stage stage = (Stage) formGrid.getScene().getWindow();
                stage.close();

                // Recargar la ventana principal
                App.setRoot("empleado");
            } else {
                mostrarError("No se pudo modificar el empleado en la base de datos.");
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            mostrarError("Ocurrió un error al intentar modificar el empleado.");
        }
    }

    private boolean actualizarEmpleadoEnBD(Empleado empleadoModificado) {
        String sql = "UPDATE Empleado SET nombre = ?, horaInicio = ?, horaFin = ?, fechaCapacitacion = ?, tipoCapacitacion = ? WHERE ID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, empleadoModificado.getNombre());
            pstmt.setTime(2, java.sql.Time.valueOf(empleadoModificado.getHoraInicio()));
            pstmt.setTime(3, java.sql.Time.valueOf(empleadoModificado.getHoraFin()));

            // Manejar el caso en que fechaCapacitacion sea null
            if (empleadoModificado.getFechaCapacitacion() != null) {
                pstmt.setDate(4, java.sql.Date.valueOf(empleadoModificado.getFechaCapacitacion()));
            } else {
                pstmt.setNull(4, java.sql.Types.DATE);
            }

            pstmt.setString(5, empleadoModificado.getTipoCapacitacion());
            pstmt.setString(6, empleadoModificado.getId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            mostrarError("Ocurrió un error al modificar el empleado en la base de datos.");
            return false;
        }
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Este método será llamado para cargar los datos del empleado seleccionado.
     */
    @FXML
    private void cancelar(ActionEvent event) throws IOException {
        // Cerrar la ventana emergente
        Stage stage = (Stage) formGrid.getScene().getWindow();
        stage.close();
    }

}

