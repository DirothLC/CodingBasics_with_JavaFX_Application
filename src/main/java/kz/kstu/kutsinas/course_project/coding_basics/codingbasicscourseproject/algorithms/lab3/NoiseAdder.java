package kz.kstu.kutsinas.course_project.coding_basics.codingbasicscourseproject.algorithms.lab3;

import javafx.scene.chart.XYChart;

import java.util.Random;

public class NoiseAdder {
    public static XYChart.Series<Number, Number> addNoise(XYChart.Series<Number, Number> original, double noiseAmplitude) {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        Random random = new Random();

        for (XYChart.Data<Number, Number> point : original.getData()) {
            double noise = (random.nextDouble() * 2 - 1) * noiseAmplitude; // Шум в диапазоне [-A, A]
            series.getData().add(new XYChart.Data<>(point.getXValue(), point.getYValue().doubleValue() + noise));
        }
        return series;
    }
}
