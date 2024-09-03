/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.aserriosbd;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import java.sql.SQLException;
import java.io.IOException;

/**
 * FXML Controller class
 *
 * @author ASUS VIVOBOOK PRO
 */
public class SeccionVentasController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void irCliente(MouseEvent event) {
        try {
            App.setRoot("cliente");
        } catch (IOException ex) { 
        }
    }

    @FXML
    private void irProducto(MouseEvent event) {
        try {
            App.setRoot("producto");
        } catch (IOException ex) { 
        }
    }

    @FXML
    private void irFactura(MouseEvent event) {
        try {
            App.setRoot("factura");
        } catch (IOException ex) { 
        }
    }

    @FXML
    private void irReclamacion(MouseEvent event) {
        try {
            App.setRoot("reclamacion");
        } catch (IOException ex) { 
            ex.getMessage();
        }
    }

    @FXML
    private void irDetalle(MouseEvent event) {
        try {
            App.setRoot("detalle");
        } catch (IOException ex) { 
            ex.getMessage();
        }
    }

    @FXML
    private void regresar(MouseEvent event) throws IOException {
        App.setRoot("opcionesSecretaria");
    }
    
}
