/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.aserriosbd;

import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ASUS VIVOBOOK PRO
 */
public class ModificarBase<T> {
    private Class<T> clazz;
    private GridPane formGrid;
    private Map<String, TextField> textFieldMap;

    public ModificarBase(Class<T> clazz) {
        this.clazz = clazz;
        this.textFieldMap = new HashMap<>();
        this.formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(10);
        formGrid.setAlignment(javafx.geometry.Pos.CENTER);

        generateForm();
    }

    private void generateForm() {
        Field[] fields = clazz.getDeclaredFields();
        int rowIndex = 0;

        for (Field field : fields) {
            String fieldName = field.getName();
            TextField textField = new TextField();
            textFieldMap.put(fieldName, textField);

            formGrid.add(new Label(fieldName + ":"), 0, rowIndex);
            formGrid.add(textField, 1, rowIndex);

            rowIndex++;
        }
    }

    public GridPane getFormGrid() {
        return formGrid;
    }

    public void loadObject(T instance) {
        for (Map.Entry<String, TextField> entry : textFieldMap.entrySet()) {
            String fieldName = entry.getKey();
            TextField textField = entry.getValue();

            Field field = null;
            try {
                field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);

                Object value = field.get(instance);

                if (value != null) {
                    textField.setText(value.toString());
                } else {
                    textField.clear();
                }

            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
                throw new IllegalArgumentException("Error al cargar el campo " + fieldName + ": " + e.getMessage());
            }
        }
    }

    public T getObject() throws InstantiationException, IllegalAccessException {
    T instance = clazz.newInstance();

    for (Map.Entry<String, TextField> entry : textFieldMap.entrySet()) {
        String fieldName = entry.getKey();
        TextField textField = entry.getValue();
        String textValue = textField.getText();

        Field field = null;
        try {
            field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);

            if (field.getType() == String.class) {
                field.set(instance, textValue.isEmpty() ? null : textValue);
            } else if (field.getType() == int.class) {
                field.set(instance, textValue.isEmpty() ? 0 : Integer.parseInt(textValue));
            } else if (field.getType() == Integer.class) {
                field.set(instance, textValue.isEmpty() ? null : Integer.valueOf(textValue));
            } else if (field.getType() == double.class) {
                field.set(instance, textValue.isEmpty() ? 0.0 : Double.parseDouble(textValue));
            } else if (field.getType() == Double.class) {
                field.set(instance, textValue.isEmpty() ? null : Double.valueOf(textValue));
            } else if (field.getType() == float.class) {
                field.set(instance, textValue.isEmpty() ? 0.0f : Float.parseFloat(textValue));
            } else if (field.getType() == Float.class) {
                field.set(instance, textValue.isEmpty() ? null : Float.valueOf(textValue));
            }
            // Agrega más tipos de datos según sea necesario
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Error al procesar el campo " + fieldName + ": " + e.getMessage());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Error al convertir el valor del campo " + fieldName + ": " + e.getMessage());
        }
    }

    return instance;
}

     // Método para crear un GridPane con cualquier campo que comience con `id` no editable
    // y con la capacidad de establecer valores específicos para esos campos
    public GridPane buildDynamicFormWithLockedId(List<String> fieldNames, Map<String, String> idFieldValues) {
        GridPane gridPane = new GridPane();

        int row = 0;

        for (String fieldName : fieldNames) {
            Label label = new Label(fieldName);
            TextField textField = new TextField();

            // Si el campo es un "id", configúralo como no editable y establece su valor
            if (fieldName.startsWith("id")) {
                textField.setEditable(false); // Hacer el campo no editable
                String value = idFieldValues.getOrDefault(fieldName, ""); // Obtener el valor desde el mapa
                textField.setText(value); // Establecer el valor del campo
            }

            gridPane.add(label, 0, row);
            gridPane.add(textField, 1, row);
            row++;
        }

        return gridPane;
    }
    public void clearFields() {
        for (TextField textField : textFieldMap.values()) {
            textField.clear();
        }
    }
}
