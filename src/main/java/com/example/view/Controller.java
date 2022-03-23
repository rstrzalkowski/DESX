package com.example.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.example.model.DES;

public class Controller {
    @FXML
    private TextField key1;
    @FXML
    private TextField key2;
    @FXML
    private TextField key3;
    @FXML
    private Button generateButton;
    @FXML
    private Button loadKeysButton;
    @FXML
    private Button saveKeysButton;
    @FXML
    private Button openPlaintextButton;
    @FXML
    private Button openCiphertextButton;
    @FXML
    private Button encryptButton;
    @FXML
    private Button decryptButton;
    @FXML
    private RadioButton fileRadio;
    @FXML
    private RadioButton windowRadio;
    @FXML
    private Button savePlaintextButton;
    @FXML
    private Button saveCiphertextButton;
    @FXML
    private TextArea plaintext;
    @FXML
    private TextArea ciphertext;

    final ToggleGroup group = new ToggleGroup();


    @FXML
    public void initialize() {
        addTextLimiter(key1,16);
        addTextLimiter(key2,16);
        addTextLimiter(key3,16);

        fileRadio.setToggleGroup(group);
        windowRadio.setToggleGroup(group);
    }


    public static void addTextLimiter(final TextField tf, final int maxLength) {
        tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (tf.getText().length() > maxLength) {
                    String s = tf.getText().substring(0, maxLength);
                    tf.setText(s);
                }
            }
        });
    }

    @FXML
    public void onGenerateButtonClick(ActionEvent actionEvent) {
        key1.setText("0123456789ABCDEF");
        key2.setText("1133557799BBDDFF");
        key3.setText("0022446688AACCEE");
    }

}