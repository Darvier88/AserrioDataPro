/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.aserriosbd;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import javafx.scene.control.TableCell;
import javafx.scene.layout.Region;
/**
 *
 * @author ASUS VIVOBOOK PRO
 */
public class InterfazBase {

    // Método para configurar la tabla y el texto del label
    public <T> void configureTableFromClass(TableView<T> tableView, Text textLabel, String name, Class<T> clazz) {
        setTextLabel(textLabel, name);

        // Obtener columnas y propiedades desde la clase
        List<String> columnas = new ArrayList<>();
        List<String> propiedades = new ArrayList<>();
        List<Class<?>> tipos = new ArrayList<>();
        extractColumnsAndProperties(clazz, columnas, propiedades, tipos);

        // Configurar el TableView con las columnas, propiedades y tipos obtenidos
        setCellValueFactories(tableView, columnas, propiedades, tipos);
    }

    // Método para configurar el texto del label
    private void setTextLabel(Text textLabel, String name) {
        textLabel.setText(name);
    }

    // Método para configurar el CellValueFactory de cada columna en base a una lista de propiedades y tipos
    private <T> void setCellValueFactories(TableView<T> tableView, List<String> columnas, List<String> propiedades, List<Class<?>> tipos) {
        validateColumnAndPropertySize(columnas, propiedades, tipos);

        tableView.getColumns().clear();

        for (int i = 0; i < columnas.size(); i++) {
            TableColumn<T, ?> newColumn = createColumn(columnas.get(i), propiedades.get(i), tipos.get(i));
            tableView.getColumns().add(newColumn);
        }
    }

    // Método auxiliar para validar que las listas de columnas, propiedades y tipos tengan el mismo tamaño
    private void validateColumnAndPropertySize(List<String> columnas, List<String> propiedades, List<Class<?>> tipos) {
        if (columnas.size() != propiedades.size() || propiedades.size() != tipos.size()) {
            throw new IllegalArgumentException("El número de columnas, propiedades y tipos debe ser el mismo.");
        }
    }

    // Método auxiliar para crear una columna con su respectivo CellValueFactory
    private <T, S> TableColumn<T, S> createColumn(String columnName, String propertyName, Class<S> type) {
        TableColumn<T, S> column = new TableColumn<>(columnName);
        column.setCellValueFactory(new PropertyValueFactory<>(propertyName));

        // Ajustar el tamaño de la columna para que se ajuste al contenido
        column.setMinWidth(100);  // Ancho mínimo para que el texto no se corte
        column.setPrefWidth(150); // Ancho preferido (ajusta este valor según sea necesario)
        column.setMaxWidth(Double.MAX_VALUE);  // Permitir que se expanda según sea necesario

        return column;
    }

    // Método extraído para obtener columnas, propiedades y tipos de la clase
    private <T> void extractColumnsAndProperties(Class<T> clazz, List<String> columnas, List<String> propiedades, List<Class<?>> tipos) {
        Method[] methods = clazz.getDeclaredMethods();

        for (Method method : methods) {
            if (isGetterMethod(method)) {
                String propertyName = getPropertyNameFromGetter(method);
                String columnName = generateColumnName(propertyName);

                columnas.add(columnName);
                propiedades.add(propertyName);
                tipos.add(method.getReturnType());  // Añadir el tipo de retorno del método getter
            }
        }
    }

    // Método extraído para verificar si un método es un getter
    private boolean isGetterMethod(Method method) {
        return method.getName().startsWith("get") && method.getParameterCount() == 0;
    }

    // Método extraído para obtener el nombre de la propiedad desde un getter
    private String getPropertyNameFromGetter(Method method) {
        return method.getName().substring(3, 4).toLowerCase() + method.getName().substring(4);
    }

    // Método auxiliar para generar nombres de columnas legibles
    private String generateColumnName(String propertyName) {
        StringBuilder columnName = new StringBuilder();

        for (char c : propertyName.toCharArray()) {
            if (Character.isUpperCase(c)) {
                columnName.append(' ');
            }
            columnName.append(c);
        }

        return columnName.toString().trim();
    }
}