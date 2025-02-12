package kz.kstu.kutsinas.course_project.coding_basics.codingbasicscourseproject.controllers.lab3;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.TextField;

public class Lab3Controller {
    @FXML
    private TextField frequencyInput;

    @FXML
    private TextField noiseInput;

    @FXML
    private LineChart<Number,Number> initialGraph;

    @FXML
    private LineChart<Number,Number> distortedGraph;

    @FXML
    private LineChart<Number,Number> restoredGraph;

    @FXML
    protected void onButtonRunClick(){}

    @FXML
    protected void onButtonClearClick(){
        frequencyInput.clear();
        noiseInput.clear();
        initialGraph.getData().clear();
        distortedGraph.getData().clear();
        restoredGraph.getData().clear();
    }
}
