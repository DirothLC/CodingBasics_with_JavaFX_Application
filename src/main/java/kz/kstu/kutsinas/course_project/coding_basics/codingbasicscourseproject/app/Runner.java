package kz.kstu.kutsinas.course_project.coding_basics.codingbasicscourseproject.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import kz.kstu.kutsinas.course_project.coding_basics.codingbasicscourseproject.algorithms.ReedSolomonCoding;

import java.io.IOException;

public class Runner extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Runner.class.getResource("/kz/kstu/kutsinas/course_project/coding_basics/codingbasicscourseproject/views/intro-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Coding Basics by Kutsinas Darius ISS 22-5");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();

    }
}