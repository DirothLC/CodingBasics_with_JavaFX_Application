package kz.kstu.kutsinas.course_project.coding_basics.codingbasicscourseproject.controllers.lab1;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import kz.kstu.kutsinas.course_project.coding_basics.codingbasicscourseproject.algorithms.lab1.Lab1Logic;


public class Lab1Controller {
  @FXML
  private TextArea result;

  @FXML
  private void onRunButtonClick(){
      result.setText(Lab1Logic.programRun());
  }

  @FXML
  private void onClearButtonClick(){
      result.clear();
  }

}
