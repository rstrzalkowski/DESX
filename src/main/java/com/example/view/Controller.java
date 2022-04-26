package com.example.view;

import com.example.model.TabUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.example.model.Desx;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;
import java.util.regex.Pattern;

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
    @FXML
    private Text fileName;
    @FXML
    private Text cipherFileName;

    private final ToggleGroup group = new ToggleGroup();

    private File filePT = null;
    private File fileCT = null;
    private byte[] allBytesPT;
    private byte[] allBytesCT;


    @FXML
    public void initialize() {
        addTextLimiter(key1,16);
        addTextLimiter(key2,16);
        addTextLimiter(key3,16);

        fileRadio.setToggleGroup(group);
        windowRadio.setToggleGroup(group);
        windowRadio.setSelected(true);
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
        String HEX_ARRAY = "0123456789ABCDEF";

        Random r = new Random();
        StringBuilder s1 = new StringBuilder();
        StringBuilder s2 = new StringBuilder();
        StringBuilder s3 = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            s1.append(HEX_ARRAY.charAt(Math.abs(r.nextInt() % 16)));
            s2.append(HEX_ARRAY.charAt(Math.abs(r.nextInt() % 16)));
            s3.append(HEX_ARRAY.charAt(Math.abs(r.nextInt() % 16)));
        }
        key1.setText(s1.toString());
        key2.setText(s2.toString());
        key3.setText(s3.toString());
    }

    private void encrypt(boolean fromWindow) {
        Desx desx = new Desx(key1.getText(), key2.getText(), key3.getText());
        if (fromWindow) {
            String pt = plaintext.getText();
            byte[] allBytes = pt.getBytes(StandardCharsets.UTF_8);
            desx.encrypt(allBytes);
            ciphertext.setText(desx.getCipherText());
            allBytesCT = desx.getBytes();

        } else {
            desx.encrypt(allBytesPT);
            allBytesCT = desx.getBytes();
            ciphertext.setText(desx.getCipherText());

        }

    }

    private void decrypt(boolean fromWindow) {
        Desx desx = new Desx(key1.getText(), key2.getText(), key3.getText());
        if (fromWindow) {
            String ctHex = ciphertext.getText();
            byte[] allBytes = TabUtils.hexStringToBytes(ctHex);

            desx.decrypt(allBytes);

            plaintext.setText(desx.getPlainText());
            allBytesPT = desx.getBytes();
        } else {

            desx.decrypt(allBytesCT);

            plaintext.setText(desx.getPlainText());
            allBytesPT = desx.getBytes();
        }
    }

    @FXML
    public void onFileToggleRadioClick(ActionEvent actionEvent) {
        plaintext.setDisable(true);

    }

    public void onWindowToggleRadioClick(ActionEvent actionEvent) {
        plaintext.setDisable(false);

    }

    @FXML
    public void onEncryptButtonClick(ActionEvent actionEvent) {
        if (key1.getText().length() == 16 && key2.getText().length() == 16 && key3.getText().length() == 16) {
            if (windowRadio.isSelected()) {
                encrypt(true);
            } else if (filePT != null) {
                encrypt(false);

            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Error");
                a.setHeaderText(null);
                a.setContentText("Nie wczytano żadnego pliku z tekstem jawnym!");
                a.show();
            }

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
            if (windowRadio.isSelected()) {
                decrypt(true);
            } else if (fileCT != null) {
                decrypt(false);

            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Error");
                a.setHeaderText(null);
                a.setContentText("Nie wczytano żadnego pliku z szyfrogramem!");
                a.show();
            }
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


        filePT = fileChooser.showOpenDialog(openPlaintextButton.getScene().getWindow());
        if(filePT != null) {

            try {
                allBytesPT = Files.readAllBytes(Path.of(filePT.getAbsolutePath()));

                String text = TabUtils.bytesToString(allBytesPT);
                plaintext.setText(text);

            } catch (IOException e) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Error");
                a.setHeaderText(null);
                a.setContentText("Błąd podczas wczytywania tekstu jawnego z pliku!");
                a.show();
            }

            fileName.setText(filePT.getName());

        }
    }

    @FXML
    public void onLoadCipherTextButtonClicked(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Otwórz plik z szyfrogramem");


        fileCT = fileChooser.showOpenDialog(openCiphertextButton.getScene().getWindow());
        if (fileCT != null) {
            try {
                allBytesCT = Files.readAllBytes(Path.of(fileCT.getAbsolutePath()));
                String text = TabUtils.bytesToHex(allBytesCT);
                ciphertext.setText(text);
            } catch (IOException e) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText(null);
                a.setTitle("Błąd podczas wczytywania szyfrogramu z pliku!");
                a.show();
            }
            cipherFileName.setText(fileCT.getName());
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
        byte[] allBytes;
        if (allBytesPT == null) {
            allBytes = TabUtils.stringToBytes(plaintext.getText());
        } else {
            allBytes = allBytesPT;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Zapisz tekst jawny do pliku");

        File selectedFile = fileChooser.showSaveDialog(savePlaintextButton.getScene().getWindow());
        if (selectedFile != null) {
            try {
                Files.write(selectedFile.toPath(), allBytes);
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
        byte[] allBytes = TabUtils.hexStringToBytes(ciphertext.getText());

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Zapisz szyfrogram do pliku");

        File selectedFile = fileChooser.showSaveDialog(saveCiphertextButton.getScene().getWindow());
        if (selectedFile != null) {
            try {
                Files.write(selectedFile.toPath(), allBytes);
            } catch (IOException e) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText(null);
                a.setTitle("Błąd podczas zapisywania szyfrogramu do pliku!");
                a.show();
            }
        }
    }
}
