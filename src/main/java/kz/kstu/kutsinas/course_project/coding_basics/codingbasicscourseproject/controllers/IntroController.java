package kz.kstu.kutsinas.course_project.coding_basics.codingbasicscourseproject.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class IntroController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onLab1ButtonClick() {
        welcomeText.setText("Данные по данной лабе отсутствуют");
    }
    @FXML
    protected void onLab2ButtonClick() {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/kz/kstu/kutsinas/course_project/coding_basics/codingbasicscourseproject/views/lab2/lab2-tasks-view.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setTitle("Список заданий");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Ошибка в загрузке окна: " + e.getMessage());
        }
    }

    @FXML
    protected void onLab3ButtonClick(){
        try{
            URL fxmlUrl = getClass().getResource("/kz/kstu/kutsinas/course_project/coding_basics/codingbasicscourseproject/views/lab3/lab3-view.fxml");
            if (fxmlUrl == null) {
                System.err.println("FXML-файл не найден! Проверь путь.");
            } else {
                FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
                Parent root = fxmlLoader.load();

                Stage stage=new Stage();
                stage.setTitle("Фильтр Чебышева");
                stage.setScene(new Scene(root));
                stage.show();
            }

        }catch (IOException e){
            e.printStackTrace();
            System.err.println("Ошибка в загрузке окна: " + e.getMessage());
        }
    }

}