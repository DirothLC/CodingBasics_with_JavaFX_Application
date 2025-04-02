package kz.kstu.kutsinas.course_project.coding_basics.codingbasicscourseproject.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import kz.kstu.kutsinas.course_project.coding_basics.codingbasicscourseproject.utils.WindowManager;

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
        WindowManager.stageLoad("/kz/kstu/kutsinas/course_project/coding_basics/codingbasicscourseproject/views/lab2/lab2-tasks-view.fxml", "Список заданий");
    }

    @FXML
    protected void onLab3ButtonClick(){
        WindowManager.stageLoad("/kz/kstu/kutsinas/course_project/coding_basics/codingbasicscourseproject/views/lab3/lab3-view.fxml", "Фильтр Чебышева");

    }

    @FXML
    protected void onLab4ButtonClick(){
        WindowManager.stageLoad("/kz/kstu/kutsinas/course_project/coding_basics/codingbasicscourseproject/views/lab4/lab4-view.fxml", "Эффективное кодирование без потерь");
    }

    @FXML
    protected void onLab5ButtonClick(){
        WindowManager.stageLoad("/kz/kstu/kutsinas/course_project/coding_basics/codingbasicscourseproject/views/lab5/lab5-view.fxml", "Алгоритм сжатия JPEG");
    }

}