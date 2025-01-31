package kz.kstu.kutsinas.course_project.coding_basics.codingbasicscourseproject.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Lab2TasksController {
    @FXML
    protected void onTask1ButtonClick(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/kz/kstu/kutsinas/course_project/coding_basics/codingbasicscourseproject/views/lab2-task1-view.fxml"));
            Parent root= fxmlLoader.load();

            Stage stage = new Stage();
            stage.setTitle("Кодирование Хаффмана");
            stage.setScene(new Scene(root));
            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Ошибка в загрузке окна: " + e.getMessage());
        }
    }
}
