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
public class ProductoController implements Initializable {

    @FXML
    private TableView<Producto> tableMaquinarias;
    @FXML
    private TableColumn<Producto, String> codigoColumn;
    @FXML
    private TableColumn<Producto, String> nombreColumn;
    @FXML
    private TableColumn<Producto, Integer> precioUnitColumn;
    @FXML
    private TableColumn<Producto, String> calidadColumn;
    @FXML
    private TableColumn<Producto, String> condicAmbColumn;
    @FXML
    private TableColumn<Producto, Integer> tiempoAlmcntoColumn;
    @FXML
    private TableColumn<Producto, String> dimensionColumn;
    @FXML
    private TableColumn<Producto, String> descripcionColumn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        codigoColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        precioUnitColumn.setCellValueFactory(new PropertyValueFactory<>("precioUnitario"));
        calidadColumn.setCellValueFactory(new PropertyValueFactory<>("calidad"));
        condicAmbColumn.setCellValueFactory(new PropertyValueFactory<>("condicAmbiental"));
        tiempoAlmcntoColumn.setCellValueFactory(new PropertyValueFactory<>("tiempoAlmacenamiento"));
        dimensionColumn.setCellValueFactory(new PropertyValueFactory<>("dimension"));
        descripcionColumn.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        tableMaquinarias.setItems(ObjetosDAO.getProductoList());
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
