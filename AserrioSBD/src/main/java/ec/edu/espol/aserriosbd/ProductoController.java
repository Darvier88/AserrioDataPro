/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.aserriosbd;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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

    @FXML
    private void añadir(MouseEvent event) {
        try {
            App.setRoot("AñadirProducto");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void modificar(MouseEvent event) {
        Producto productoSeleccionado = tableMaquinarias.getSelectionModel().getSelectedItem();

        if (productoSeleccionado != null) {
            try {
                ModificarProductoController.mostrarVentanaModificacion(productoSeleccionado);
            } catch (IOException ex) {
                ex.printStackTrace();
                mostrarError("No se pudo cargar la ventana de modificación.");
            }
        } else {
            mostrarError("Por favor, selecciona un producto para modificar.");
        }
    }

    @FXML
    private void eliminar(MouseEvent event) {
        Producto productoSeleccionado = tableMaquinarias.getSelectionModel().getSelectedItem();

        if (productoSeleccionado != null) {
            if (eliminarProductoDeBD(productoSeleccionado)) {
                tableMaquinarias.getItems().remove(productoSeleccionado);
            } else {
                mostrarError("No se pudo eliminar el producto de la base de datos.");
            }
        } else {
            mostrarError("Por favor, selecciona un producto para eliminar.");
        }
    }

    private boolean eliminarProductoDeBD(Producto producto) {
        String sqlEspecificacion = "DELETE FROM detalle WHERE id_producto = ?";
        String sqlProducto = "DELETE FROM producto WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false); // Iniciar una transacción

            try (PreparedStatement pstmtEspecificacion = conn.prepareStatement(sqlEspecificacion);
                 PreparedStatement pstmtProducto = conn.prepareStatement(sqlProducto)) {

                // Eliminar registros de la tabla especificacion
                pstmtEspecificacion.setString(1, producto.getId());
                pstmtEspecificacion.executeUpdate();

                // Finalmente, eliminar el producto
                pstmtProducto.setString(1, producto.getId());
                int rowsAffected = pstmtProducto.executeUpdate();

                conn.commit(); // Confirmar la transacción
                return rowsAffected > 0;

            } catch (SQLException e) {
                conn.rollback(); // Revertir la transacción en caso de error
                e.printStackTrace();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
    }
}

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
}
