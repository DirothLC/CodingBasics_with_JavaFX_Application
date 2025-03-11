package kz.kstu.kutsinas.course_project.coding_basics.codingbasicscourseproject.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;

public class WindowManager {
    public static void stageLoad(String fxmlPath, String title) {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(WindowManager.class.getResource(fxmlPath));
            Parent root = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Ошибка в загрузке окна: " + e.getMessage());
        }
    }
    public static void sceneLoad(String fxmlFile, Node node) {
        try {
            Parent root = FXMLLoader.load(WindowManager.class.getResource(fxmlFile));
            Stage stage = (Stage) node.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Не удалось загрузить сцену");
        }
    }
}
