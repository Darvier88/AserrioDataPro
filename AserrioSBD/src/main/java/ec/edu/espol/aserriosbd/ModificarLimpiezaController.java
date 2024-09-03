/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.aserriosbd;

import ec.edu.espol.aserriosbd.modelo.DatabaseConnection;
import ec.edu.espol.aserriosbd.modelo.Limpieza;
import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
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
public class ModificarLimpiezaController implements Initializable {

    @FXML
    private GridPane formGrid;
    private Limpieza limpieza;
    private ModificarBase<Limpieza> modificarBase;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializar ModificarBase con la clase Limpieza
        modificarBase = new ModificarBase<>(Limpieza.class);

        // Añadir el formulario generado dinámicamente al GridPane
        formGrid.getChildren().addAll(modificarBase.getFormGrid().getChildren());
    }    

    public void setLimpieza(Limpieza limpieza) {
        if (limpieza != null) {
            this.limpieza = limpieza;
            modificarBase.loadObject(limpieza);
        }
    }

    public static void cargarVistaConLimpieza(Limpieza limpieza) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("modificarLimpieza.fxml"));
        Parent root = loader.load();

        ModificarLimpiezaController controller = loader.getController();
        controller.setLimpieza(limpieza);

        App.setRoot("modificarLimpieza");
    }

    public static void mostrarVentanaModificacion(Limpieza limpieza) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("ModificarLimpieza.fxml"));
        Parent root = loader.load();

        ModificarLimpiezaController controller = loader.getController();
        controller.setLimpieza(limpieza);

        Stage stage = new Stage();
        stage.setTitle("Modificar Limpieza");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL); // Establecer la modalidad de la ventana
        stage.showAndWait(); // Mostrar la ventana y esperar hasta que sea cerrada
    }

    private boolean actualizarLimpiezaEnBD(Limpieza limpieza) {
        String sql = "{CALL actualizarLimpieza(?, ?)}";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement cstmt = conn.prepareCall(sql)) {
            // Configurar los parámetros del CallableStatement
            cstmt.setInt(1, limpieza.getId());
            cstmt.setString(2, limpieza.getLugar());

            // Ejecutar el procedimiento almacenado
            cstmt.execute();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            mostrarError("Ocurrió un error al modificar la limpieza en la base de datos.");
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
            Limpieza limpieza = modificarBase.getObject();

            // Actualizar la limpieza en la base de datos
            if (actualizarLimpiezaEnBD(limpieza)) {
                System.out.println("Limpieza modificada exitosamente.");
                App.setRoot("limpieza");
                // Cerrar la ventana emergente
                Stage stage = (Stage) formGrid.getScene().getWindow();
                stage.close();

                // Recargar la ventana principal
                App.setRoot("limpieza");
            } else {
                mostrarError("No se pudo modificar la limpieza en la base de datos.");
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            mostrarError("Ocurrió un error al intentar modificar la limpieza.");
        }
    }

    @FXML
    private void cancelar(ActionEvent event) {
        // Cerrar la ventana emergente
        Stage stage = (Stage) formGrid.getScene().getWindow();
        stage.close();
    }

    
}
