package com.example.view;

import com.example.model.TabUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.example.model.Desx;
import javafx.stage.FileChooser;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
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
            Pattern pattern = Pattern.compile("([0-9a-fA-F])*");
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
        byte[] allBytes = pt.getBytes(StandardCharsets.UTF_8);
        Desx desx = new Desx(key1.getText(), key2.getText(), key3.getText());
        desx.encrypt(allBytes);

        ciphertext.setText(desx.getCipherText());
    }

    private void decrypt() {
        String ctHex = ciphertext.getText();
        String ctStr = TabUtils.hexToAscii(ctHex);
        Desx desx = new Desx(key1.getText(), key2.getText(), key3.getText());
        desx.decrypt(TabUtils.stringToBytes(ctStr));

        plaintext.setText(desx.getPlainText());
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
            a.setContentText("Któryś z kluczy nie ma 16 znaków!");
            a.show();
        }

    }

    @FXML
    public void onLoadPlainTextButtonClicked(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Otwórz plik z tekstem jawnym");


        File selectedFile = fileChooser.showOpenDialog(openPlaintextButton.getScene().getWindow());
        if(selectedFile != null) {
            try {
                byte[] allBytes = Files.readAllBytes(Path.of(selectedFile.getAbsolutePath()));

                Desx desx = new Desx(key1.getText(), key2.getText(), key3.getText());
                desx.encrypt(allBytes);
                ciphertext.setText(desx.getCipherText());


                String text = TabUtils.bytesToString(allBytes);
                plaintext.setText(text);

            } catch (IOException e) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Error");
                a.setHeaderText(null);
                a.setContentText("Błąd podczas wczytywania tekstu jawnego z pliku!");
                a.show();
            }

        }
    }

    @FXML
    public void onLoadCipherTextButtonClicked(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Otwórz plik z szyfrogramem");


        File selectedFile = fileChooser.showOpenDialog(openCiphertextButton.getScene().getWindow());
        if (selectedFile != null) {
            try {
                byte[] allBytes = Files.readAllBytes(Path.of(selectedFile.getAbsolutePath()));
                String text = TabUtils.bytesToString(allBytes);
                ciphertext.setText(text);
            } catch (IOException e) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText(null);
                a.setTitle("Błąd podczas wczytywania szyfrogramu z pliku!");
                a.show();
            }
        }
    }

    @FXML
    public void onLoadKeysButtonClicked(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Otwórz plik z kluczami");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        File selectedFile = fileChooser.showOpenDialog(loadKeysButton.getScene().getWindow());
        BufferedReader reader;
        String line1 = "";
        String line2= "";
        String line3= "";
        if (selectedFile != null) {
            try {
                reader = new BufferedReader(new FileReader(selectedFile.getAbsolutePath()));
                line1 = reader.readLine();
                line2 = reader.readLine();
                line3 = reader.readLine();

            } catch (Exception e) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText(null);
                a.setTitle("Błąd podczas wczytywania kluczy z pliku!");
                a.show();
            }
            if(line1 != null && line1.length() > 0 && line1.matches("([0-9a-fA-F])+")) {
                key1.setText(line1);
            }
            if(line2 != null && line2.length() > 0 && line2.matches("([0-9a-fA-F])+")) {
                key2.setText(line2);
            }
            if(line3 != null && line3.length() > 0 && line3.matches("([0-9a-fA-F])+")) {
                key3.setText(line3);
            }
        }
    }

    @FXML
    public void onSaveKeysButtonClicked(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Zapisz klucze do pliku");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        File selectedFile = fileChooser.showSaveDialog(saveKeysButton.getScene().getWindow());
        if (selectedFile != null) {
            try {
                FileWriter writer = new FileWriter(selectedFile);
                writer.append(key1.getText()).append("\n");
                writer.append(key2.getText()).append("\n");
                writer.append(key3.getText()).append("\n");
                writer.close();
            } catch (IOException e) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText(null);
                a.setTitle("Błąd podczas zapisywania kluczy do pliku!");
                a.show();
            }
        }
    }

    @FXML
    public void onSavePlainTextButtonClicked(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Zapisz tekst jawny do pliku");

        File selectedFile = fileChooser.showSaveDialog(savePlaintextButton.getScene().getWindow());
        if (selectedFile != null) {
            try {
                Files.write(selectedFile.toPath(), TabUtils.stringToBytes(plaintext.getText()));
            } catch (IOException e) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText(null);
                a.setTitle("Błąd podczas zapisywania tekstu jawnego do pliku!");
                a.show();
            }
        }
    }

    @FXML
    public void onSaveCipherTextButtonClicked(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Zapisz szyfrogram do pliku");

        File selectedFile = fileChooser.showSaveDialog(saveCiphertextButton.getScene().getWindow());
        if (selectedFile != null) {
            try {
                Files.write(selectedFile.toPath(), TabUtils.stringToBytes(ciphertext.getText()));
            } catch (IOException e) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText(null);
                a.setTitle("Błąd podczas zapisywania szyfrogramu do pliku!");
                a.show();
            }
        }
    }




}
