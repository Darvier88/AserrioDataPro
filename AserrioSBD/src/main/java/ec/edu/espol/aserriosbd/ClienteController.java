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
import javafx.scene.control.TableView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author ASUS VIVOBOOK PRO
 */
public class ClienteController implements Initializable {

    @FXML
    private Text text;
    @FXML
    private TableView<Cliente> table;

    private InterfazBase interfazBase;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializar la instancia de InterfazBase
        interfazBase = new InterfazBase();

        // Configurar la tabla con los datos de la clase Cliente
        interfazBase.configureTableFromClass(table, text, "Clientes", Cliente.class);
        table.setItems(ObjetosDAO.getClienteList());
    }

    @FXML
    private void regresar(MouseEvent event) {
        // Acción de regresar
    }

    @FXML
    private void irLimpieza(ContextMenuEvent event) {
        // Acción de ir a limpieza
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
    
    }

    @FXML
    private void modificar(MouseEvent event) {
    
    }
}
