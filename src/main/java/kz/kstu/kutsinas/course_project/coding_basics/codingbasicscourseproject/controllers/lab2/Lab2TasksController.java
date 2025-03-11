package kz.kstu.kutsinas.course_project.coding_basics.codingbasicscourseproject.controllers.lab2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import kz.kstu.kutsinas.course_project.coding_basics.codingbasicscourseproject.utils.WindowManager;

import java.io.IOException;

public class Lab2TasksController {
    @FXML
    protected void onTask1ButtonClick(){
        WindowManager.stageLoad("/kz/kstu/kutsinas/course_project/coding_basics/codingbasicscourseproject/views/lab2/lab2-task1-view.fxml", "Кодирование Хаффмана");
    }

    @FXML
    protected void onTask2ButtonClick(){
        WindowManager.stageLoad("/kz/kstu/kutsinas/course_project/coding_basics/codingbasicscourseproject/views/lab2/lab2-task2-view.fxml", "Кодирование Шеннона-Фано");
    }
    @FXML
    protected void onTask3ButtonClick() {
        WindowManager.stageLoad("/kz/kstu/kutsinas/course_project/coding_basics/codingbasicscourseproject/views/lab2/lab2-task3-view.fxml", "Блоковое кодирование с использованием кодов Рида-Соломона");

    }

}
