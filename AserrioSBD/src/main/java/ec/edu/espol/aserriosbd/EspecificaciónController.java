/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.aserriosbd;

import ec.edu.espol.aserriosbd.modelo.ObjetosDAO;
import ec.edu.espol.aserriosbd.modelo.Especificación;
import java.io.IOException;
import javafx.scene.control.SelectionMode;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author nicol
 */
public class EspecificaciónController implements Initializable {

    @FXML
    private Text text;
    @FXML
    private TableView<Especificación> table;
    
    private InterfazBase interfazBase;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        interfazBase = new InterfazBase();
        interfazBase.configureTableFromClass(table, text, "Especificaciones", Especificación.class);

        // Configuración de la selección
        table.setItems(ObjetosDAO.getEspecificacionList());
        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }    

    @FXML
    private void irLimpieza(ContextMenuEvent event) {
    }

    @FXML
    private void añadir(MouseEvent event) {
    }

    @FXML
    private void eliminar(MouseEvent event) {
    }

    @FXML
    private void modificar(MouseEvent event) {
    }

    @FXML
    private void regresar(MouseEvent event) {
        try {
            App.setRoot("seccionCompras");
        } catch (IOException ex) { 
            ex.printStackTrace();
        }
    }
    
}
