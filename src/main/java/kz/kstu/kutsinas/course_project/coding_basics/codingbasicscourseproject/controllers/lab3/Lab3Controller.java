package kz.kstu.kutsinas.course_project.coding_basics.codingbasicscourseproject.controllers.lab3;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import kz.kstu.kutsinas.course_project.coding_basics.codingbasicscourseproject.algorithms.lab3.Lab3Logic;


public class Lab3Controller {

    public Lab3Controller() {
    }
    @FXML
    private Button runButton;

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
    protected void onButtonRunClick(){
        double frequency = 20;
        double noise = 0.1;
        if(!frequencyInput.getText().isEmpty()) frequency=Double.parseDouble(frequencyInput.getText());
        if(!noiseInput.getText().isEmpty()) noise= Double.parseDouble(noiseInput.getText());

        var initialSignal = Lab3Logic.generateInitialSignal(frequency);
        var distortedSignal = Lab3Logic.generateDistortedSignal(initialSignal, noise);
        var restoredSignal = Lab3Logic.generateRestoredSignal(distortedSignal);

        initialGraph.getData().clear();
        initialGraph.getData().add(initialSignal);
        initialGraph.setCreateSymbols(false);
        initialGraph.setLegendVisible(false);

        distortedGraph.getData().clear();
        distortedGraph.getData().add(distortedSignal);
        distortedGraph.setCreateSymbols(false);
        distortedGraph.setLegendVisible(false);

        restoredGraph.getData().clear();
        restoredGraph.getData().add(restoredSignal);
        restoredGraph.setCreateSymbols(false);
        restoredGraph.setLegendVisible(false);
    }

    @FXML
    protected void onButtonClearClick(){
        frequencyInput.clear();
        noiseInput.clear();
        initialGraph.getData().clear();
        distortedGraph.getData().clear();
        restoredGraph.getData().clear();
    }
}
