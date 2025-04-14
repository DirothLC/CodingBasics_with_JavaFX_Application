package kz.kstu.kutsinas.course_project.coding_basics.codingbasicscourseproject.controllers.lab5;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import kz.kstu.kutsinas.course_project.coding_basics.codingbasicscourseproject.algorithms.lab5.JPEGLib;

import javafx.scene.image.Image;

import java.io.File;
import java.io.IOException;

public class Lab5Controller {
    @FXML
    private TextField filePath;
    @FXML
    private TextField folderPath;
    @FXML
    private TextField fileName;

    @FXML
    private Button chooseFileButton;
    @FXML
    private Button chooseSaveFolderButton;

    @FXML
    private RadioButton compressButton;
    @FXML
    private RadioButton decompressButton;

    @FXML
    private Slider compressionForce;

    @FXML
    private ImageView inputImage;
    @FXML
    private ImageView outputImage;

    @FXML
    private TextArea outputArea;




    @FXML
    public void initialize() {
        String defaultPath = new File("src/main/resources/static/working_with_files").getAbsolutePath();
        folderPath.setText(defaultPath);
    }

    @FXML
    private void onRunButtonClick() {
        if (fileName.getText().isEmpty() || filePath.getText().isEmpty() || folderPath.getText().isEmpty()) {
            outputArea.setText("Заполните все поля!");
            return;
        }
        if (compressButton.isSelected()) {
            try {
                JPEGLib.convertWithQuality(filePath.getText(), folderPath.getText() + "/" + fileName.getText(), (float) compressionForce.getValue());
                File outputFile = new File(folderPath.getText(), fileName.getText() + ".jpg");
                outputImage.setImage(
                        new Image(outputFile.toURI().toString())
                );
                outputArea.setText("Сжатие выполнено успешно! \n Размер исходного файла: " + new File(filePath.getText()).length() +
                        "Размер сжатого файла: " + outputFile.length() +
                        "Качество: " + compressionForce.getValue() + "%");
            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
            }
        } else if (decompressButton.isSelected()) {
            try {
                JPEGLib.restoreToPNG(filePath.getText(), folderPath.getText() + "/" + fileName.getText());
                File outputFile = new File(folderPath.getText(), fileName.getText() + ".png");
                outputImage.setImage(
                        new Image(outputFile.toURI().toString())
                );
                outputArea.setText("Восстановление выполнено успешно! \n Размер исходного файла: " + new File(filePath.getText()).length() +
                        "Размер восстановленного файла: " + outputFile.length());
            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    @FXML
    private void onCompressButtonClick() {
        compressionForce.setVisible(true);
    }

    @FXML
    private void onDecompressButtonClick() {
        compressionForce.setVisible(false);
    }

    @FXML
    private void onFileChooseButtonClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите файл");
        fileChooser.setInitialDirectory(new File("src/main/resources/static/working_with_files").getAbsoluteFile());
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files (*.png)", "*.png"),
                new FileChooser.ExtensionFilter("Image Files (*.jpg)", "*.jpg")
        );

        Stage stage = (Stage) chooseFileButton.getScene().getWindow();

        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            filePath.setText(selectedFile.getAbsolutePath());
            inputImage.setImage(new javafx.scene.image.Image(selectedFile.toURI().toString()));
        } else {
            System.out.println("File selection cancelled.");
        }
    }

    @FXML
    private void onSaveFolderButtonClick() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Выберите папку для сохранения");

        Stage stage = (Stage) chooseSaveFolderButton.getScene().getWindow();
        File selectedDirectory = directoryChooser.showDialog(stage);

        if (selectedDirectory != null) {
            folderPath.setText(selectedDirectory.getAbsolutePath());
        } else {
            System.out.println("Directory selection cancelled.");
        }

    }

    @FXML
    private void onClearButtonClick(){
        outputArea.clear();
        filePath.clear();
        fileName.clear();

    }
}
