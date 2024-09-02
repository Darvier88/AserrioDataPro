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
 * @author nicol
 */
public class SeccionComprasController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void irLoteMadera(MouseEvent event) {
        try {
            App.setRoot("tipoMadera");
        } catch (IOException ex) { 
        }
    }

    @FXML
    private void irTipoMadera(MouseEvent event) {
        try {
            App.setRoot("loteMadera");
        } catch (IOException ex) { 
        }
    }

    @FXML
    private void irEspecificación(MouseEvent event) {
    }

    @FXML
    private void irProveedor(MouseEvent event) {
        try {
            App.setRoot("proveedor");
        } catch (IOException ex) { 
        }
    }

    @FXML
    private void irEvaluacion(MouseEvent event) {
    }
    
}
