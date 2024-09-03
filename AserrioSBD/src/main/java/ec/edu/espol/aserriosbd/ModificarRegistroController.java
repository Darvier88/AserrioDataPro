/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.aserriosbd;

import ec.edu.espol.aserriosbd.modelo.DatabaseConnection;
import ec.edu.espol.aserriosbd.modelo.RegistroDeLimpieza;
import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ASUS VIVOBOOK PRO
 */
public class ModificarRegistroController implements Initializable {

    @FXML
    private GridPane formGrid;
    private RegistroDeLimpieza registro;
    private ModificarBase<RegistroDeLimpieza> modificarBase;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializar ModificarBase con la clase RegistroDeLimpieza
        modificarBase = new ModificarBase<>(RegistroDeLimpieza.class);

        // Añadir el formulario generado dinámicamente al GridPane
        formGrid.getChildren().addAll(modificarBase.getFormGrid().getChildren());
    }

    public void setRegistroDeLimpieza(RegistroDeLimpieza registro) {
        if (registro != null) {
            this.registro = registro;
            modificarBase.loadObject(registro);
        }
    }

    public static void cargarVistaConRegistroDeLimpieza(RegistroDeLimpieza registro) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("modificarRegistro.fxml"));
        Parent root = loader.load();

        ModificarRegistroController controller = loader.getController();
        controller.setRegistroDeLimpieza(registro);

        App.setRoot("modificarRegistro");
    }

    public static void mostrarVentanaModificacion(RegistroDeLimpieza registro) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("modificarRegistro.fxml"));
        Parent root = loader.load();

        ModificarRegistroController controller = loader.getController();
        controller.setRegistroDeLimpieza(registro);

        Stage stage = new Stage();
        stage.setTitle("Modificar Registro de Limpieza");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL); // Establecer la modalidad de la ventana
        stage.showAndWait(); // Mostrar la ventana y esperar hasta que sea cerrada
    }

    private boolean actualizarRegistroDeLimpiezaEnBD(RegistroDeLimpieza registro) {
        String sql = "{CALL actualizarRegistro(?, ?, ?, ?)}";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement cstmt = conn.prepareCall(sql)) {

            // Configurar los parámetros del CallableStatement
            cstmt.setInt(2, registro.getIdLimpieza());
            cstmt.setString(1, registro.getIdAsistente());
            cstmt.setString(3, registro.getIdSecretaria());
            LocalDate localDate = registro.getFecha();
            // Convertir LocalDate a java.sql.Date
            if (localDate != null) {
                cstmt.setDate(4, Date.valueOf(localDate)); // Convertir LocalDate a java.sql.Date
            } else {
                cstmt.setNull(4, java.sql.Types.DATE);
            }
            // Ejecutar el procedimiento almacenado
            boolean hasResultSet = cstmt.execute();
            return !hasResultSet; // El método execute() devuelve true si hay un ResultSet, false si es solo una actualización

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
            RegistroDeLimpieza registro = modificarBase.getObject();

            // Actualizar el registro de limpieza en la base de datos
            if (actualizarRegistroDeLimpiezaEnBD(registro)) {
                System.out.println("Registro de limpieza modificado exitosamente.");
                App.setRoot("registroDeLimpieza");
                // Cerrar la ventana emergente
                Stage stage = (Stage) formGrid.getScene().getWindow();
                stage.close();

                // Recargar la ventana principal
                App.setRoot("registroDeLimpieza");
            } else {
                mostrarError("No se pudo modificar el registro de limpieza en la base de datos.");
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            mostrarError("Ocurrió un error al intentar modificar el registro de limpieza.");
        }
    }

    @FXML
    private void cancelar(ActionEvent event) {
        // Cerrar la ventana emergente
        Stage stage = (Stage) formGrid.getScene().getWindow();
        stage.close();
    }
    
}
