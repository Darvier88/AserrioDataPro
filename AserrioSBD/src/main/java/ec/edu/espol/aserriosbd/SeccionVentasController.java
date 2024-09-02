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
    private void irCliente(MouseEvent event) throws IOException {
        App.setRoot("cliente");
    }

    @FXML
    private void irProducto(MouseEvent event) throws IOException {
        App.setRoot("producto");
    }

    @FXML
    private void irFactura(MouseEvent event) throws IOException {
        App.setRoot("factura");
    }

    @FXML
    private void irReclamacion(MouseEvent event) {
    }
    
}
