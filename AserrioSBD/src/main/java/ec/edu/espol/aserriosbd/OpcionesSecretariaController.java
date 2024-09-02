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

    private void irMaquinaria(MouseEvent event) {
        try {
            App.setRoot("maquinaria");
        } catch (IOException ex) { 
            System.out.println(ex.getMessage());
        }
    }

    private void irProducto(MouseEvent event) {
        try {
            App.setRoot("producto");
        } catch (IOException ex) { 
        }
    }

    private void irCliente(MouseEvent event) {
        try {
            App.setRoot("cliente");
        } catch (IOException ex) { 
            ex.printStackTrace(); 
        }
    }

    private void irProveedor(MouseEvent event) {
        try {
            App.setRoot("proveedor");
        } catch (IOException ex) { 
        }
    }

    private void irLoteMadera(MouseEvent event) {
        try {
            App.setRoot("loteMadera");
        } catch (IOException ex) { 
        }
    }
    
    private void irEmpleado(MouseEvent event) {
        try {
            App.setRoot("empleado");
        } catch (IOException ex) { 
        }
    }


    private void irTipoMadera(MouseEvent event) {
        try {
            App.setRoot("tipoMadera");
        } catch (IOException ex) { 
        }
    }

    @FXML
    private void irSeccionVentas(MouseEvent event) throws IOException {
        App.setRoot("seccionVentas");
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
    
}
