package kz.kstu.kutsinas.course_project.coding_basics.codingbasicscourseproject.algorithms.lab3;

import javafx.scene.chart.XYChart;

public class Lab3Logic {
    public static XYChart.Series<Number, Number> generateInitialSignal(double frequency) {
        return SignalGenerator.generateSignal(frequency);
    }

    public static XYChart.Series<Number, Number> generateDistortedSignal(XYChart.Series<Number, Number> original, double noiseAmplitude) {
        return NoiseAdder.addNoise(original, noiseAmplitude);
    }

    public static XYChart.Series<Number, Number> generateRestoredSignal(XYChart.Series<Number, Number> noisySignal) {
        return ChebyshevFilter.applyFilter(noisySignal);
    }


}
