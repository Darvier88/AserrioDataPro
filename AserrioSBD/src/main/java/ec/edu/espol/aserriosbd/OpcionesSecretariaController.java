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
public class OpcionesSecretariaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    

    @FXML
    private void irSeccionVentas(MouseEvent event) throws IOException {
        try {
            App.setRoot("seccionVentas");
        } catch (IOException ex) { 
        }
    }

    @FXML
    private void irSeccionCompras(MouseEvent event) throws IOException {
        App.setRoot("seccionCompras");
    }

    @FXML
    private void irSeccionEmpleados(MouseEvent event) throws IOException {
        App.setRoot("seccionEmpleados");
    }

    @FXML
    private void irReportes(MouseEvent event) throws IOException {
        App.setRoot("reportes");

    }

    @FXML
    private void regresar(MouseEvent event) throws IOException {
        App.setRoot("inicioPerfiles");
    }
    
}
