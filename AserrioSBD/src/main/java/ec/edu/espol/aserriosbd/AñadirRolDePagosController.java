/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.aserriosbd;

import ec.edu.espol.aserriosbd.modelo.DatabaseConnection;
import ec.edu.espol.aserriosbd.modelo.RolDePagos;
import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author ASUS VIVOBOOK PRO
 */
public class AñadirRolDePagosController implements Initializable {

    @FXML
    private VBox mainContainer;
    @FXML
    private GridPane formGrid;
    private AñadirBase<RolDePagos> añadirBase;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializar AñadirBase para la clase RolDePagos
        añadirBase = new AñadirBase<>(RolDePagos.class);

        // Agregar el formulario generado al VBox principal
        mainContainer.getChildren().add(añadirBase.getFormGrid());
    }    

    private boolean insertarRolDePagosEnBD(RolDePagos rolDePagos) {
        String sql = "{call InsertRolDePagos(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement cstmt = conn.prepareCall(sql)) {

            // Configurar los parámetros del CallableStatement
            cstmt.setString(1, rolDePagos.getIdEmpleado());
            cstmt.setString(2, rolDePagos.getRol());
            cstmt.setInt(3, rolDePagos.getDiasLaborados());
            cstmt.setFloat(4, rolDePagos.getSueldo());
            cstmt.setFloat(5, rolDePagos.getHorasExtras());
            cstmt.setFloat(6, rolDePagos.getTotalIngresos());
            cstmt.setFloat(7, rolDePagos.getEgresos());
            cstmt.setFloat(8, rolDePagos.getAnticipos());
            cstmt.setFloat(9, rolDePagos.getTotalEgresos());
            cstmt.setFloat(10, rolDePagos.getLiquidoARecibir());

            // Ejecutar el procedimiento almacenado
            boolean hasResultSet = cstmt.execute();
            return !hasResultSet;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void mostrarError(String mensaje) {
        // Mostrar un mensaje de error (por ejemplo, usando un Alert de JavaFX)
        System.err.println(mensaje);
    }

    @FXML
    private void añadir(ActionEvent event) {
        try {
            // Obtener el objeto RolDePagos con los valores ingresados
            RolDePagos rolDePagos = añadirBase.getObject();

            // Insertar el RolDePagos en la base de datos
            if (insertarRolDePagosEnBD(rolDePagos)) {
                // Limpiar los campos después de añadir
                añadirBase.clearFields();
            } else {
                mostrarError("No se pudo añadir el rol de pagos a la base de datos.");
            }
            App.setRoot("rolDePagos"); // Cambiar la vista a la vista de rolDePagos
        } catch (IllegalArgumentException e) {
            mostrarError(e.getMessage()); // Mostrar el mensaje de error al usuario
        } catch (Exception e) {
            e.printStackTrace();
            mostrarError("Ocurrió un error al intentar añadir el rol de pagos.");
        }
    }

    @FXML
    private void cancelar(ActionEvent event) throws IOException {
        App.setRoot("rolDePagos"); // Cambiar la vista a la vista de rolDePagos
    }
}
