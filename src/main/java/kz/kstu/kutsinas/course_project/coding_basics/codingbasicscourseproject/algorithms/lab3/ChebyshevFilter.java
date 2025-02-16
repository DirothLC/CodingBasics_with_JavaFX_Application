package kz.kstu.kutsinas.course_project.coding_basics.codingbasicscourseproject.algorithms.lab3;

import javafx.scene.chart.XYChart;

import com.github.psambit9791.jdsp.filter.Chebyshev;

public class ChebyshevFilter {
    public static XYChart.Series<Number, Number> applyFilter(XYChart.Series<Number, Number> noisySignal) {
        double samplingRate = 1000.0; // Частота дискретизации, Гц
        double cutoffFreq = 50.0; // Частота среза, Гц

        // Параметры фильтра Чебышева
        double ripple = 0.5; // Пульсации в дБ
        int order = 4; // Порядок фильтра
        int filterType = 1; // Тип фильтра (1 - Чебышев I, 2 - Чебышев II)

        int length = noisySignal.getData().size();
        double[] signal = new double[length];
        double[] time = new double[length];

        for (int i = 0; i < length; i++) {
            signal[i] = noisySignal.getData().get(i).getYValue().doubleValue();
            time[i] = noisySignal.getData().get(i).getXValue().doubleValue();
        }

        Chebyshev filter = new Chebyshev(samplingRate, ripple, filterType);

        double[] filteredSignal = filter.lowPassFilter(signal, order, cutoffFreq);

        XYChart.Series<Number, Number> filteredSeries = new XYChart.Series<>();
        for (int i = 0; i < length; i++) {
            filteredSeries.getData().add(new XYChart.Data<>(time[i], filteredSignal[i]));
        }
        return filteredSeries;
    }
}
