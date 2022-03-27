package com.example.view;

import com.example.model.TabUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.example.model.Desx;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;

import static java.nio.file.Files.readString;

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
        tf.textProperty().addListener((ov, oldValue, newValue) -> {
            Pattern pattern = Pattern.compile("([0-9a-fA-F])+");
            if (tf.getText().length() > maxLength) {
                String s = tf.getText().substring(0, maxLength);
                tf.setText(s);
            }
            if (!pattern.matcher(newValue).matches()) {
                tf.setText(oldValue);
            }

        });
    }

    @FXML
    public void onGenerateButtonClick(ActionEvent actionEvent) {
        key1.setText("0123456789ABCDEF");
        key2.setText("1133557799BBDDFF");
        key3.setText("0022446688AACCEE");
    }

    private void encrypt() {
        String pt = plaintext.getText();
        Desx desx = new Desx(key1.getText(), key2.getText(), key3.getText());
        desx.encrypt(pt, false);

        ciphertext.setText(desx.getCipherText());
    }

    private void decrypt() {
        String ctHex = ciphertext.getText();
        String ctStr = TabUtils.hexToAscii(ctHex);
        Desx desx = new Desx(key1.getText(), key2.getText(), key3.getText());
        desx.encrypt(ctStr, true);

        plaintext.setText(desx.getCipherText());
    }

    @FXML
    public void onEncryptButtonClick(ActionEvent actionEvent) {
        if(key1.getText().length() == 16 && key2.getText().length() == 16 && key3.getText().length() == 16){
            encrypt();
        }
        else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText(null);
            a.setContentText("Któryś z kluczy nie ma 16 znaków");
            a.show();
        }


    }

    @FXML
    public void onDecryptButtonClick(ActionEvent actionEvent) {
        if(key1.getText().length() == 16 && key2.getText().length() == 16 && key3.getText().length() == 16){
            decrypt();
        }
        else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText(null);
            a.setContentText("Któryś z kluczy nie ma 16 znaków");
            a.show();
        }


    }

    @FXML
    public void onLoadPlainTextButtonClicked(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Otwórz plik z tekstem jawnym");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        File selectedFile = fileChooser.showOpenDialog(openPlaintextButton.getScene().getWindow());
        if(selectedFile != null) {
            try {
                String pt = Files.readString(Path.of(selectedFile.getAbsolutePath()));
                plaintext.setText(pt);
            } catch (IOException e) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Error");
                a.setHeaderText(null);
                a.setContentText("Błąd podczas wczytywania pliku z tekstem jawnym");
                a.show();
            }

        }
    }

    @FXML
    public void onLoadCipherTextButtonClicked(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Otwórz plik z szyfrogramem");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        File selectedFile = fileChooser.showOpenDialog(openPlaintextButton.getScene().getWindow());
        if(selectedFile != null) {
            try {
                String pt = Files.readString(Path.of(selectedFile.getAbsolutePath()));
                ciphertext.setText(pt);
            } catch (IOException e) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Błąd podczas wczytywania pliku z szyfrogramem");
                a.show();
            }

        }
    }


}
