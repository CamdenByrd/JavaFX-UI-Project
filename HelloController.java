package com.example.hw1;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HelloController {
    @FXML
    private CheckBox musicCheckbox;
    @FXML
    private ToggleGroup appToggleGroup;
    @FXML
    private CheckBox appCheckbox;
    @FXML
    private ComboBox musicComboBox;
    @FXML
    private RadioButton gameRadioButton;
    @FXML
    private RadioButton prodRadioButton;
    @FXML
    private RadioButton eduRadioButton;
    @FXML
    private HBox appRadios;
    @FXML
    private TextField nameField;
    @FXML
    private TextField streetField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField stateField;
    @FXML
    private TextField zipField;
    @FXML
    private Button submitButton;
    @FXML
    private TextField titleField;
    @FXML
    private TextField dateField;
    @FXML
    private TextField accountField;
    @FXML
    private Button finishButton;
    private final List<String> customerData = new ArrayList<>();
    public void musicBoxGreying() {
        musicComboBox.setDisable(appCheckbox.isSelected());
    }
    @FXML
    public void appSelectionGreying() {
        appRadios.setDisable(musicCheckbox.isSelected());
    }
    public void finishFunctionality() {
        Platform.exit();
    }
    public void submitValidation() {
        TextField[] fieldArray = {nameField, streetField, cityField, stateField, zipField, titleField, dateField, accountField};
        StringBuilder emptyFields = new StringBuilder();
        for (TextField field : fieldArray) {
            if (field.getText().isEmpty()) {
                field.setStyle("-fx-border-color: red;");
                field.requestFocus();
                String fieldName = field.getId().replace("Field", "");
                emptyFields.append(fieldName).append("\n");
            }
        }
        if (!emptyFields.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty fields detected");
            alert.setContentText("The following fields can't be empty:\n" + emptyFields);
            alert.showAndWait();
        } else {
            StringBuilder customerInfo = new StringBuilder();
            for (TextField field : fieldArray) {
                field.setStyle("");
                String fieldName = field.getId().replace("Field", "");
                customerInfo.append(fieldName).append(": ").append(field.getText()).append("\n");
                field.clear();
            }
            customerData.add(customerInfo.toString());

            StringBuilder dataToWrite = new StringBuilder(customerInfo);
            if (musicCheckbox.isSelected()) {
                String selectedMusic = musicComboBox.getSelectionModel().getSelectedItem().toString();
                dataToWrite.append("Selected Music: ").append(selectedMusic).append("\n");
                writeToTextFile("music.txt", dataToWrite.toString());
            } else if (appCheckbox.isSelected()) {
                RadioButton selectedRadioButton = (RadioButton) appToggleGroup.getSelectedToggle();
                String selectedApp = selectedRadioButton.getText();
                dataToWrite.append("Selected App: ").append(selectedApp).append("\n");
                writeToTextFile("app.txt", dataToWrite.toString());
            }
            nameField.requestFocus();
        }
    }

    private void writeToTextFile(String filename, String data) {
        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.write(data + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
