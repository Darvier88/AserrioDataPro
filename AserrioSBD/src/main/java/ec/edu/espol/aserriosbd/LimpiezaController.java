/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.aserriosbd;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author nicol
 */
public class LimpiezaController implements Initializable {

    @FXML
    private TableColumn<Limpieza, Integer> idAsistenteColumn;
    @FXML
    private TableColumn<Limpieza, String> LugarColumn;
    @FXML
    private TableColumn<Limpieza, Date> FechaColumn;
    @FXML
    private TableView<Limpieza> tableLimpieza;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idAsistenteColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        LugarColumn.setCellValueFactory(new PropertyValueFactory<>("lugar"));
        FechaColumn.setCellValueFactory(new PropertyValueFactory<>("fecha"));

        tableLimpieza.setItems(LimpiezaDAO.getLimpiezaList());
    }    

    @FXML
    private void regresar(MouseEvent event) {
    }

    @FXML
    private void irLimpieza(ContextMenuEvent event) {
    }
    
}
