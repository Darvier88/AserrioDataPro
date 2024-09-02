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
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * FXML Controller class
 *
 * @author ASUS VIVOBOOK PRO
 */
public class AñadirFacturaController implements Initializable {

    @FXML
    private VBox mainContainer;
    @FXML
    private GridPane formGrid;
    private AñadirBase<Factura> añadirBase;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    String sql = "{call InsertFactura(?, ?, ?, ?, ?, ?, ?, ?, ?)}"; // Llamada al procedimiento almacenado
        // Inicializar AñadirBase para la clase Cliente
        añadirBase = new AñadirBase<>(Factura.class);
        
        // Agregar el formulario generado al VBox principal
        mainContainer.getChildren().add(añadirBase.getFormGrid());
    }    
    public boolean insertarFactura(Factura factura) {
    String sql = "{call InsertFactura(?, ?, ?, ?, ?, ?, ?, ?, ?)}"; // Llamada al procedimiento almacenado

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
        // Mostrar un mensaje de error (por ejemplo, usando un Alert de JavaFX)
        System.err.println(mensaje);
    }
    @FXML
    private void añadir(ActionEvent event) {
        try {
        // Obtener el objeto Cliente con los valores ingresados
        Factura nuevaFactura = añadirBase.getObject();

        // Insertar el cliente en la base de datos
        if (insertarFactura(nuevaFactura)) {
            // Limpiar los campos después de añadir
            añadirBase.clearFields();
        } else {
            mostrarError("No se pudo añadir el cliente a la base de datos.");
        }
        App.setRoot("factura");
    } catch (IllegalArgumentException e) {
        mostrarError(e.getMessage()); // Mostrar el mensaje de error al usuario
    } catch (Exception e) {
        e.printStackTrace();
        mostrarError("Ocurrió un error al intentar añadir el cliente.");
    }
    }


    @FXML
    private void cancelar(ActionEvent event) throws IOException {
        App.setRoot("factura");
    }
    
}
