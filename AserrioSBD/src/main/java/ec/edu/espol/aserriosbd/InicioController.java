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
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author nicol
 */
public class InicioController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void irMaquinaria(MouseEvent event) {
        try {
            App.setRoot("maquinaria");
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
    private void irCliente(MouseEvent event) {
        try {
            App.setRoot("cliente");
        } catch (IOException ex) { 
        }
    }


    

  
    
}
