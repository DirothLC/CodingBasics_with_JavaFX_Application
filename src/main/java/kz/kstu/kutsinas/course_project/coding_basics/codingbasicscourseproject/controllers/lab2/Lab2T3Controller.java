package kz.kstu.kutsinas.course_project.coding_basics.codingbasicscourseproject.controllers.lab2;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import kz.kstu.kutsinas.course_project.coding_basics.codingbasicscourseproject.algorithms.lab2.LogicLab2;

public class Lab2T3Controller {
    @FXML
    private TextField input;

    @FXML
    private TextArea result;

    @FXML
    protected void onButtonRunClick(){
        String answer= LogicLab2.Lab2T3Logic(input.getText());
        result.setText(answer);
    }

    @FXML
    protected void onClearButtonClick() {
        input.clear();
        result.clear();
    }
}
