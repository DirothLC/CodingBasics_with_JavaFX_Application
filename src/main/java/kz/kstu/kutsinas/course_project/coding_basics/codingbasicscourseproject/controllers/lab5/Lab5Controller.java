package kz.kstu.kutsinas.course_project.coding_basics.codingbasicscourseproject.controllers.lab5;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

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
    private RadioButton codingMark;
    @FXML
    private RadioButton decodingMark;
    @FXML
    private RadioButton txtFormatMark;
    @FXML
    private RadioButton logFormatMark;

    @FXML
    private TextArea outputArea;


    @FXML
    public void initialize() {
        String defaultPath = new File("src/main/resources/static/working_with_files").getAbsolutePath();
        folderPath.setText(defaultPath);
    }

    @FXML
    private void onFileChooseButtonClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите файл");
        fileChooser.setInitialDirectory(new File("src/main/resources/static/working_with_files").getAbsoluteFile());
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files (*.txt)", "*.txt"),
                new FileChooser.ExtensionFilter("Log Files (*.log)", "*.log"),
                new FileChooser.ExtensionFilter("Binary Files (*.bin)", "*.bin")
        );

        Stage stage = (Stage) chooseFileButton.getScene().getWindow();

        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            filePath.setText(selectedFile.getAbsolutePath());
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
