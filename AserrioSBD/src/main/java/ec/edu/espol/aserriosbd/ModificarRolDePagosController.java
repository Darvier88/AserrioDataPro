/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.aserriosbd;

import ec.edu.espol.aserriosbd.modelo.DatabaseConnection;
import ec.edu.espol.aserriosbd.modelo.RolDePagos;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;

public class ModificarRolDePagosController implements Initializable {

    @FXML
    private GridPane formGrid;
    private RolDePagos rolDePagos;
    private ModificarBase<RolDePagos> modificarBase;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializar ModificarBase con la clase RolDePagos
        modificarBase = new ModificarBase<>(RolDePagos.class);

        // Añadir el formulario generado dinámicamente al GridPane
        formGrid.getChildren().addAll(modificarBase.getFormGrid().getChildren());
    }

    public void setRolDePagos(RolDePagos rolDePagos) {
        if (rolDePagos != null) {
            this.rolDePagos = rolDePagos;
            modificarBase.loadObject(rolDePagos);
        }
    }

    public static void cargarVistaConRolDePagos(RolDePagos rolDePagos) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("modificarRolDePagos.fxml"));
        Parent root = loader.load();

        ModificarRolDePagosController controller = loader.getController();
        controller.setRolDePagos(rolDePagos);

        App.setRoot("modificarRolDePagos");
    }

    public static void mostrarVentanaModificacion(RolDePagos rolDePagos) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("modificarRolDePagos.fxml"));
        Parent root = loader.load();

        ModificarRolDePagosController controller = loader.getController();
        controller.setRolDePagos(rolDePagos);

        Stage stage = new Stage();
        stage.setTitle("Modificar Rol de Pagos");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL); // Establecer la modalidad de la ventana
        stage.showAndWait(); // Mostrar la ventana y esperar hasta que sea cerrada
    }

    private boolean actualizarRolDePagosEnBD(RolDePagos rolDePagos) {
        String sql = "{CALL actualizarRolDePago(?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement cstmt = conn.prepareCall(sql)) {

            // Configurar los parámetros del CallableStatement
            cstmt.setInt(1, rolDePagos.getId());
            cstmt.setString(2, rolDePagos.getIdEmpleado());
            cstmt.setInt(3, rolDePagos.getDiasLaborados());
            cstmt.setFloat(4, rolDePagos.getSueldo());
            cstmt.setFloat(5, rolDePagos.getHorasExtras());
            cstmt.setFloat(6, rolDePagos.getTotalIngresos());
            cstmt.setFloat(7, rolDePagos.getEgresos());
            cstmt.setFloat(8, rolDePagos.getAnticipos());
            cstmt.setFloat(9, rolDePagos.getTotalEgresos());

            // Ejecutar el procedimiento almacenado
            boolean hasResultSet = cstmt.execute();
            return !hasResultSet;

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
    private void modificar(ActionEvent event) throws IOException {
        try {
            // Obtener los valores modificados del formulario
            RolDePagos rolDePagos = modificarBase.getObject();

            // Actualizar el RolDePagos en la base de datos
            if (actualizarRolDePagosEnBD(rolDePagos)) {
                System.out.println("Rol de pagos modificado exitosamente.");
                App.setRoot("rolDePagos");
                // Cerrar la ventana emergente
                Stage stage = (Stage) formGrid.getScene().getWindow();
                stage.close();

                // Recargar la ventana principal
                App.setRoot("rolDePagos");
            } else {
                mostrarError("No se pudo modificar el rol de pagos en la base de datos.");
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            mostrarError("Ocurrió un error al intentar modificar el rol de pagos.");
        }
    }

    @FXML
    private void cancelar(ActionEvent event) {
        // Cerrar la ventana emergente
        Stage stage = (Stage) formGrid.getScene().getWindow();
        stage.close();
    }
}
