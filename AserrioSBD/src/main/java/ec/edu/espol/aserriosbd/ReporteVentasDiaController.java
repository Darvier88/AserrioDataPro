/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.aserriosbd;

import ec.edu.espol.aserriosbd.modelo.ReporteVentasDia;
import ec.edu.espol.aserriosbd.modelo.ReportesDAO;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author ASUS VIVOBOOK PRO
 */
public class ReporteVentasDiaController implements Initializable {

    @FXML
    private Text text;
    @FXML
    private TableView<ReporteVentasDia> table;
    private InterfazBase interfazBase;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializar la instancia de InterfazBase
        interfazBase = new InterfazBase();

        // Configurar la tabla con los datos de la clase Cliente
        interfazBase.configureTableFromClass(table, text, "Reporte diario de ventas", ReporteVentasDia.class);
        table.setItems(ReportesDAO.obtenerDatosVentasDiaActualizados());
        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }    

    @FXML
    private void regresar(MouseEvent event) {
        try {
            App.setRoot("opcionesSecretaria");
        } catch (IOException ex) { 
            ex.printStackTrace();
        }
    }
    
}
