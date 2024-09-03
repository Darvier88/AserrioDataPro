/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.aserriosbd;

import ec.edu.espol.aserriosbd.modelo.DatabaseConnection;
import ec.edu.espol.aserriosbd.modelo.Maquinaria;
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
public class ModificarMaquinariaController implements Initializable {

    @FXML
    private GridPane formGrid;
    private Maquinaria maq;
    private ModificarBase<Maquinaria> modificarBase;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializar ModificarBase con la clase Cliente
        modificarBase = new ModificarBase<>(Maquinaria.class);

        // Añadir el formulario generado dinámicamente al GridPane
        formGrid.getChildren().addAll(modificarBase.getFormGrid().getChildren());
    }    
    public void setMaquinaria(Maquinaria maq) {
        if (maq != null) {
            this.maq= maq;
            modificarBase.loadObject(maq);
        }
    }
    public static void cargarVistaConCliente(Maquinaria maq) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("modificarMaquinaria.fxml"));
        Parent root = loader.load();

        ModificarMaquinariaController controller = loader.getController();
        controller.setMaquinaria(maq);

        App.setRoot("modificarMaquinaria");
    }
    public static void mostrarVentanaModificacion(Maquinaria maq) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("ModificarMaquinaria.fxml"));
        Parent root = loader.load();

        ModificarMaquinariaController controller = loader.getController();
        controller.setMaquinaria(maq);

        Stage stage = new Stage();
        stage.setTitle("Modificar Maquinaria");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL); // Establecer la modalidad de la ventana
        stage.showAndWait(); // Mostrar la ventana y esperar hasta que sea cerrada
    }
    private boolean actualizarMaquinariaEnBD(Maquinaria maquinaria) {
        String sql = "{CALL actualizarMaquinaria(?, ?, ?, ?)}";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement cstmt = conn.prepareCall(sql)) {
            // Configurar los parámetros del CallableStatement
            cstmt.setInt(1, maquinaria.getCodigo());
            cstmt.setString(2, maquinaria.getNombre());
            cstmt.setString(3, maquinaria.getMarca());
            LocalDate localDate = maquinaria.getFechaAdquisicion();
            // Convertir LocalDate a java.sql.Date
            if (localDate != null) {
                cstmt.setDate(4, Date.valueOf(localDate)); // Convertir LocalDate a java.sql.Date
            } else {
                cstmt.setNull(4, java.sql.Types.DATE);
            }

            // Ejecutar el procedimiento almacenado
            cstmt.execute();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            mostrarError("Ocurrió un error al modificar el cliente en la base de datos.");
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
            Maquinaria maquinaria = modificarBase.getObject();

            // Actualizar el cliente en la base de datos
            if (actualizarMaquinariaEnBD(maquinaria)) {
                System.out.println("Maquinaria modificado exitosamente.");
                App.setRoot("maquinaria");
                // Cerrar la ventana emergente
                Stage stage = (Stage) formGrid.getScene().getWindow();
                stage.close();

                // Recargar la ventana principal
                App.setRoot("maquinaria");
            } else {
                mostrarError("No se pudo modificar la maquinaria en la base de datos.");
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            mostrarError("Ocurrió un error al intentar modificar la maquinaria .");
        }
    }

    @FXML
    private void cancelar(ActionEvent event) {
         // Cerrar la ventana emergente
                Stage stage = (Stage) formGrid.getScene().getWindow();
                stage.close();
    }
    
}
