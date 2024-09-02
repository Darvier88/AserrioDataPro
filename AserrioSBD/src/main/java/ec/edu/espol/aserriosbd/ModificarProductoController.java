/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.aserriosbd;

import ec.edu.espol.aserriosbd.modelo.DatabaseConnection;
import ec.edu.espol.aserriosbd.modelo.Producto;
import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class ModificarProductoController implements Initializable {

    @FXML
    private GridPane formGrid;
    private Producto producto;
    private ModificarBase<Producto> modificarBase;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializar ModificarBase con la clase Producto
        modificarBase = new ModificarBase<>(Producto.class);

        // Añadir el formulario generado dinámicamente al GridPane
        formGrid.getChildren().addAll(modificarBase.getFormGrid().getChildren());
    }

    // Método que se llamará para cargar los datos del producto seleccionado
    public void setProducto(Producto producto) {
        if (producto != null) {
            this.producto = producto; // Guardar el producto para referencia futura
            modificarBase.loadObject(producto); // Cargar los datos en los campos del formulario
        }
    }

    @FXML
    private void modificar(ActionEvent event) {
        try {
            Producto productoModificado = modificarBase.getObject();
            // Actualizar el producto en la base de datos
            if (actualizarProductoEnBD(productoModificado)) {
                // Cerrar la ventana emergente
                Stage stage = (Stage) formGrid.getScene().getWindow();
                stage.close();

                // Recargar la ventana principal
                App.setRoot("producto");
            } else {
                mostrarError("No se pudo modificar el producto en la base de datos.");
            }
        } catch (InstantiationException | IllegalAccessException | IOException e) {
            e.printStackTrace();
            mostrarError("Ocurrió un error al intentar modificar el producto.");
        }
    }

    private boolean actualizarProductoEnBD(Producto productoModificado) {
    String sql = "{CALL ActualizarProducto(?, ?, ?, ?, ?, ?, ?, ?)}";

    try (Connection conn = DatabaseConnection.getConnection();
         CallableStatement cstmt = conn.prepareCall(sql)) {

        // Establecer los parámetros del procedimiento almacenado
        cstmt.setString(1, productoModificado.getId());
        cstmt.setString(2, productoModificado.getNombre());
        System.out.println(productoModificado.getPrecioUnitario());
        cstmt.setFloat(3, productoModificado.getPrecioUnitario());
        cstmt.setString(4, productoModificado.getCalidad());
        cstmt.setString(5, productoModificado.getCondicAmbiental());
        cstmt.setInt(6, productoModificado.getTiempoAlmacenamiento());
        cstmt.setString(7, productoModificado.getDimension());
        cstmt.setString(8, productoModificado.getDescripcion());

        // Ejecutar el procedimiento almacenado
        cstmt.executeUpdate();
        return true;

    } catch (SQLException e) {
        e.printStackTrace();
        mostrarError("Ocurrió un error al modificar el producto en la base de datos.");
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

    @FXML
    private void cancelar(ActionEvent event) throws IOException {
        // Cerrar la ventana emergente
        Stage stage = (Stage) formGrid.getScene().getWindow();
        stage.close();
    }

    // Método estático para cargar la vista y pasar el producto seleccionado
    public static void cargarVistaConProducto(Producto producto) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("ModificarProducto.fxml"));
        Parent root = loader.load();

        ModificarProductoController controller = loader.getController();
        controller.setProducto(producto);

        App.setRoot("ModificarProducto");
    }

    // Método estático para mostrar la ventana de modificación en un `Stage` modal
    public static void mostrarVentanaModificacion(Producto producto) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("ModificarProducto.fxml"));
        Parent root = loader.load();

        ModificarProductoController controller = loader.getController();
        controller.setProducto(producto);

        Stage stage = new Stage();
        stage.setTitle("Modificar Producto");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL); // Establecer la modalidad de la ventana
        stage.showAndWait(); // Mostrar la ventana y esperar hasta que sea cerrada
    }
}
