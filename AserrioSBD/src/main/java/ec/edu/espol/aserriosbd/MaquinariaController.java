/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.aserriosbd;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author nicol
 */
public class MaquinariaController implements Initializable {

    @FXML
    private TableColumn<Maquinaria, Integer> codigoColumn;
    @FXML
    private TableColumn<Maquinaria, String> nombreColumn;
    @FXML
    private TableColumn<Maquinaria, String> marcaColumn;
    @FXML
    private TableColumn<Maquinaria, Date> fechaColumn;
    @FXML
    private TableView<Maquinaria> tableMaquinarias;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        codigoColumn.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        marcaColumn.setCellValueFactory(new PropertyValueFactory<>("marca"));
        fechaColumn.setCellValueFactory(new PropertyValueFactory<>("fecha"));

        tableMaquinarias.setItems(LimpiezaDAO.getMaquinariaList());
    }    

    @FXML
    private void regresar(MouseEvent event) {
        try {
            App.setRoot("inicio");
        } catch (IOException ex) { 
        }
    
    }

    @FXML
    private void irLimpieza(ContextMenuEvent event) {
    }
    
}
