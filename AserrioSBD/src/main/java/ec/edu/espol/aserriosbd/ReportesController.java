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
public class ReportesController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void reporteMcompras(MouseEvent event) throws IOException {
        App.setRoot("ReporteMCompras");
    }

    @FXML
    private void reporteDventas(MouseEvent event) throws IOException {
        App.setRoot("reporteVentasDia");
    }

    @FXML
    private void reporteMmant(MouseEvent event) throws IOException {
        App.setRoot("reporteMensualMantenimiento");
    }

    @FXML
    private void LDAC(MouseEvent event) throws IOException {
        App.setRoot("LDAC");
    }
    
}
