/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.aserriosbd;

import ec.edu.espol.aserriosbd.modelo.Cliente;
import ec.edu.espol.aserriosbd.modelo.DatabaseConnection;
import ec.edu.espol.aserriosbd.modelo.Factura;
import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
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
public class ModificarFacturaController implements Initializable {

    @FXML
    private GridPane formGrid;
    private Factura factura;
    private ModificarBase<Factura> modificarBase;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializar ModificarBase con la clase Cliente
        modificarBase = new ModificarBase<>(Factura.class);

        // Añadir el formulario generado dinámicamente al GridPane
        formGrid.getChildren().addAll(modificarBase.getFormGrid().getChildren());
    }    

    public void setFactura(Factura factura) {
        if (factura != null) {
            this.factura = factura;
            modificarBase.loadObject(factura);
        }
    }
    public static void cargarVista(Factura factura) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("modificarFactura.fxml"));
        Parent root = loader.load();

        ModificarFacturaController controller = loader.getController();
        controller.setFactura(factura);

        App.setRoot("modificarFactura");
    }
    public static void mostrarVentanaModificacion(Factura factura) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("ModificarFactura.fxml"));
        Parent root = loader.load();

        ModificarFacturaController controller = loader.getController();
        controller.setFactura(factura);

        Stage stage = new Stage();
        stage.setTitle("Modificar Factura");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL); // Establecer la modalidad de la ventana
        stage.showAndWait(); // Mostrar la ventana y esperar hasta que sea cerrada
    }
    private boolean actualizarFacturaEnBD(Factura facturaModificado) {
     String sql = "{CALL ActualizarFactura(?, ?, ?, ?, ?, ?, ?, ?)}";

    try (Connection conn = DatabaseConnection.getConnection();
         CallableStatement cstmt = conn.prepareCall(sql)) {

        // Configurar los parámetros del CallableStatement
        cstmt.setString(1, factura.getIdSecretaria());
        cstmt.setString(2, factura.getIdCliente());
        // Convertir LocalDate a java.sql.Date
        Date localDate = factura.getFecha();
        if (localDate != null) {
            cstmt.setDate(3, Date.valueOf(localDate)); // Convertir LocalDate a java.sql.Date
        } else {
            cstmt.setNull(3, java.sql.Types.DATE);
        }
        // Convertir LocalTime a java.sql.Time
        LocalTime localTime = factura.getHora();
        if (localTime != null) {
            cstmt.setTime(4, Time.valueOf(localTime)); // Convertir LocalTime a java.sql.Time
        } else {
            cstmt.setNull(4, java.sql.Types.TIME);
        }
        cstmt.setString(5, factura.getDireccionLocal());

        // Manejo de ENUM
        if (factura.getMetodoPago() != null) {
            cstmt.setString(6, factura.getMetodoPago());
        } else {
            cstmt.setNull(6, java.sql.Types.VARCHAR);
        }

        cstmt.setFloat(7, factura.getSubtotalSinImpuestos());
        cstmt.setFloat(8, factura.getSubtotal0Porcent());
        cstmt.setFloat(9, factura.getValorTotal());

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
            Factura facturaModificada = modificarBase.getObject();

            // Actualizar la factura en la base de datos
            if (actualizarFacturaEnBD(facturaModificada)) {
                System.out.println("Factura modificada exitosamente.");
                App.setRoot("factura");
                // Cerrar la ventana emergente
                Stage stage = (Stage) formGrid.getScene().getWindow();
                stage.close();

                // Recargar la ventana principal
                App.setRoot("factura");
            } else {
                mostrarError("No se pudo modificar la factura en la base de datos.");
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            mostrarError("Ocurrió un error al intentar modificar la factura.");
        }
    }


    @FXML
    private void cancelar(ActionEvent event) {
        // Cerrar la ventana emergente
                Stage stage = (Stage) formGrid.getScene().getWindow();
                stage.close();
    }
    
}
