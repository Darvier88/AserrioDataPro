/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.aserriosbd;

import java.io.IOException;
import ec.edu.espol.aserriosbd.modelo.ObjetosDAO;
import ec.edu.espol.aserriosbd.modelo.Limpieza;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author nicol
 */
public class LimpiezaController implements Initializable {

    @FXML
    private Text text;
    @FXML
    private TableView<Limpieza> table;

    private InterfazBase interfazBase;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        interfazBase = new InterfazBase();
        interfazBase.configureTableFromClass(table, text, "Limpiezas", Limpieza.class);

        // Configuración de la selección
        table.setItems(ObjetosDAO.getLimpiezaList());
        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    private void regresar(MouseEvent event) {
        try {
            App.setRoot("seccionEmpleados");
        } catch (IOException ex) { 
            ex.printStackTrace();
        }
    }

    

    @FXML
    private void añadir(MouseEvent event) {
        try {
            App.setRoot("AñadirCliente");
    } catch (IOException ex) {
    }
    }

    @FXML
    private void eliminar(MouseEvent event) {
        //////
    }
    private void eliminarClienteDeBD(Limpieza cliente) {
    //////
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    @FXML
    private void modificar(MouseEvent event) {
        /////
    }
    
}
