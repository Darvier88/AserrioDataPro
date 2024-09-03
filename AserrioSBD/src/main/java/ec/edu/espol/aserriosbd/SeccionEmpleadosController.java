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
public class SeccionEmpleadosController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void irEmpleados(MouseEvent event) throws IOException {
        App.setRoot("empleado");
    }

    @FXML
    private void irRolDePagos(MouseEvent event) throws IOException {
        App.setRoot("rolDePagos");
    }

    @FXML
    private void irRegistroLimpieza(MouseEvent event) throws IOException {
        App.setRoot("registroDeLimpieza");
    }

    @FXML
    private void irMaquinarias(MouseEvent event) throws IOException {
        App.setRoot("maquinaria");
    }

    @FXML
    private void irMantenimiento(MouseEvent event) throws IOException {
        App.setRoot("mantenimiento");
    }

    @FXML
    private void irLimpieza(MouseEvent event) throws IOException {
        App.setRoot("limpieza");
    }

    @FXML
    private void regresar(MouseEvent event) throws IOException {
        App.setRoot("opcionesSecretaria");
    }

    @FXML
    private void irOperarios(MouseEvent event) throws IOException {
        App.setRoot("operarios");
    }

    @FXML
    private void irAsistentes(MouseEvent event) {
    }

    @FXML
    private void irSecretarias(MouseEvent event) {
    }
    
}
