package com.alesandro.ejercicio15c;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import static javafx.scene.control.TableView.TableViewSelectionModel;

import javafx.scene.control.cell.PropertyValueFactory;
import com.alesandro.model.Persona;

import java.util.Arrays;

/**
 * Clase que controla los eventos de la ventana
 */
public class PersonasController {
    @FXML // fx:id="txtNombre"
    private TextField txtNombre; // Value injected by FXMLLoader

    @FXML // fx:id="txtApellidos"
    private TextField txtApellidos; // Value injected by FXMLLoader

    @FXML // fx:id="txtEdad"
    private TextField txtEdad; // Value injected by FXMLLoader

    @FXML // fx:id="tabla"
    private TableView<Persona> tabla; // Value injected by FXMLLoader

    @FXML // fx:id="colNombre"
    private TableColumn<Persona, String> colNombre; // Value injected by FXMLLoader

    @FXML // fx:id="colApellidos"
    private TableColumn<Persona, String> colApellidos; // Value injected by FXMLLoader

    @FXML // fx:id="colEdad"
    private TableColumn<Persona, Integer> colEdad; // Value injected by FXMLLoader

    /**
     * Función que se ejecuta cuando se inicia la ventana
     */
    public void initialize() {
        // Modificar las celdas de la tabla
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colApellidos.setCellValueFactory(new PropertyValueFactory("apellidos"));
        colEdad.setCellValueFactory(new PropertyValueFactory("edad"));
        tabla.getColumns().setAll(colNombre, colApellidos, colEdad);
        // Añadir listener para cuando se selecciona un item de la tabla
        tabla.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Persona>() {
            @Override
            public void changed(ObservableValue<? extends Persona> observableValue, Persona oldValue, Persona newValue) {
                if (newValue != null) {
                    txtNombre.setText(newValue.getNombre());
                    txtApellidos.setText(newValue.getApellidos());
                    txtEdad.setText(newValue.getEdad() + "");
                }
            }
        });
    }

    /**
     * Válida los datos del formulario
     *
     * @return si los datos son validos o no
     */
    private boolean validarDatos() {
        String error = "";
        if (txtNombre.getText().isEmpty()) {
            error += "El campo nombre es obligatorio";
        }
        if (txtApellidos.getText().isEmpty()) {
            if (!error.isEmpty()) {
                error += "\n";
            }
            error += "El campo apellidos es obligatorio";
        }
        if (txtEdad.getText().isEmpty()) {
            if (!error.isEmpty()) {
                error += "\n";
            }
            error += "El campo edad es obligatorio";
        } else {
            try {
                int edad = Integer.parseInt(txtEdad.getText());
            } catch (NumberFormatException e) {
                if (!error.isEmpty()) {
                    error += "\n";
                }
                error += "El campo edad tiene que ser numérico";
            }
        }
        if (!error.isEmpty()) {
            alerta(error);
            return false;
        }
        return true;
    }

    /**
     * Función que procesa los datos cuándo se pulsa el botón "Agregar Persona"
     *
     * @param event
     */
    @FXML
    void agregarPersona(ActionEvent event) {
        boolean resultado = validarDatos();
        if (resultado) {
            Persona p = new Persona(txtNombre.getText(), txtApellidos.getText(), Integer.parseInt(txtEdad.getText()));
            ObservableList<Persona> lst = tabla.getItems();
            if (lst.contains(p)) {
                alerta("Esa persona ya existe");
            } else {
                tabla.getItems().add(p);
                confirmacion("Persona añadida correctamente");
                vaciarFormulario();
            }
        }
    }

    /**
     * Función que muestra un mensaje de alerta al usuario
     *
     * @param texto contenido de la alerta
     */
    public void alerta(String texto) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setHeaderText(null);
        alerta.setTitle("ERROR");
        alerta.setContentText(texto);
        alerta.showAndWait();
    }

    /**
     * Función que muestra un mensaje de confirmación al usuario
     *
     * @param texto contenido del mensaje
     */
    public void confirmacion(String texto) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setHeaderText(null);
        alerta.setTitle("Info");
        alerta.setContentText(texto);
        alerta.showAndWait();
    }

    /**
     * Vacía el formulario y de-selecciona los elementos de la tabla
     */
    public void vaciarFormulario() {
        tabla.getSelectionModel().clearSelection();
        txtNombre.setText("");
        txtApellidos.setText("");
        txtEdad.setText("");
    }

}
