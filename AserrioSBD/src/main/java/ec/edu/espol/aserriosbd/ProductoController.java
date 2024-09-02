/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.aserriosbd;

import ec.edu.espol.aserriosbd.modelo.ObjetosDAO;
import ec.edu.espol.aserriosbd.modelo.DatabaseConnection;
import ec.edu.espol.aserriosbd.modelo.Producto;
import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
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
            App.setRoot("opcionesSecretaria");
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
            // Mostrar una alerta de confirmación antes de eliminar
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar eliminación");
            alert.setHeaderText("¿Estás seguro de que deseas eliminar este producto?");
            alert.setContentText("Al eliminar este producto, se eliminarán todas las instancias asociadas en la tabla Detalle.");

            // Mostrar y esperar la respuesta del usuario
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Si el usuario confirma, proceder con la eliminación
                if (eliminarProductoDeBD(productoSeleccionado)) {
                    tableMaquinarias.getItems().remove(productoSeleccionado);
                    mostrarInfo("Producto eliminado", "El producto ha sido eliminado correctamente.");
                } else {
                    mostrarError("No se pudo eliminar el producto de la base de datos.");
                }
            } else {
                // Si el usuario cancela, no hacer nada
                mostrarInfo("Eliminación cancelada", "El producto no ha sido eliminado.");
            }
        } else {
            mostrarError("Por favor, selecciona un producto para eliminar.");
        }
    }
    private void mostrarInfo(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    private boolean eliminarProductoDeBD(Producto producto) {
    String sql = "{CALL EliminarProducto(?, ?)}";

    try (Connection conn = DatabaseConnection.getConnection();
         CallableStatement cstmt = conn.prepareCall(sql)) {

        // Establecer los parámetros del procedimiento almacenado
        cstmt.setString(1, producto.getId());
        System.out.println(producto.getId());
        cstmt.setBoolean(2, true);

        // Ejecutar el procedimiento almacenado
        cstmt.executeUpdate();
        return true;

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
