package kz.kstu.kutsinas.course_project.coding_basics.codingbasicscourseproject.controllers.lab2;

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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/kz/kstu/kutsinas/course_project/coding_basics/codingbasicscourseproject/views/lab2/lab2-task1-view.fxml"));
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

    @FXML
    protected void onTask2ButtonClick(){
        try{
            FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/kz/kstu/kutsinas/course_project/coding_basics/codingbasicscourseproject/views/lab2/lab2-task2-view.fxml"));
            Parent root =fxmlLoader.load();

            Stage stage = new Stage();
            stage.setTitle("Кодирование Шеннона-Фано");
            stage.setScene(new Scene(root));
            stage.show();

        }catch (IOException e) {
            e.printStackTrace();
            System.err.println("Ошибка в загрузке окна: " + e.getMessage());
        }
    }
}
