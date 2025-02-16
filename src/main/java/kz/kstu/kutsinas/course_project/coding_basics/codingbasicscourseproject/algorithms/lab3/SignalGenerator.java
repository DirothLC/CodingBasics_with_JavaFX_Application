package kz.kstu.kutsinas.course_project.coding_basics.codingbasicscourseproject.algorithms.lab3;

import javafx.scene.chart.XYChart;

public class SignalGenerator {
    public static XYChart.Series<Number, Number> generateSignal(double frequency) {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        double samplingRate = 1000; // Частота дискретизации (Гц)
        double duration = 1.0; // Длительность сигнала (1 секунда)
        int points = (int) (samplingRate * duration);

        for (int i = 0; i < points; i++) {
            double t = i / samplingRate;
            double y = Math.sin(2 * Math.PI * frequency * t);
            series.getData().add(new XYChart.Data<>(t, y));
        }
        return series;
    }
}
