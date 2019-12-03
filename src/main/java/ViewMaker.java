import javax.swing.*;

import math.Point2D;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.util.Arrays;

public class ViewMaker extends JFrame {

    private static final long serialVersionUID = 1L;
    private static ChartPanel panel;


    public ViewMaker() {
        super("Statistic");
    }

    public void showBarChart(double[] data, int barsCount, boolean isFirst){
        Arrays.sort(data);
        double min = 0;
        double max = 10;
        double step = (max - min)/barsCount;
        double[] result = new double[barsCount];
        String[] keys = new String[barsCount];
        double localMax = min + step;
        int j = 0;
        int i = 0;
        while ((i < data.length) && (j < barsCount)) {
            while ((i < data.length) && (data[i] <= localMax)) {
                result[j]++;
                i++;
            }
            j++;
            localMax += step;
        }
        for (int k = 0; k < barsCount; k++) {
            keys[k] = "" + step * (k + 1);
        }
        if (isFirst)
            showBarChart(result, keys);
        else
            renewChart(result, keys);
    }

    private double round(double d){
        d *= 10;
        int n = (int)(d + 0.5);
        return n/10.0;
    }

    public void showXYChart(Point2D[] data){
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series = new XYSeries("");
        for (Point2D d : data) {
            series.add(d.x, d.y);
        }
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createScatterPlot(
                "",
                "",
                "",
                dataset
        );
        chart.getXYPlot().setRangeGridlinesVisible(false);
        chart.getXYPlot().setDomainGridlinesVisible(false);
        pack();
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        panel = new ChartPanel(chart);
        setContentPane(panel);
    }

    public void showBarChart(double[] data, String[] keys) {
        JFreeChart chart = getChart(data, keys);
        pack();
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        panel = new ChartPanel(chart);
        setContentPane(panel);
    }

    private void renewChart(double[] data, String[] keys){
        panel.revalidate();
        panel.setChart(getChart(data, keys));
    }

    private JFreeChart getChart(double[] data, String[] keys){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int i = 0; i < data.length; i++) {
            dataset.addValue(data[i], "1", keys[i]);
        }
        JFreeChart chart = ChartFactory.createBarChart(
                "",
                "",
                "",
                dataset
        );
        chart.getCategoryPlot().getRangeAxis().setUpperBound(1000);
        return chart;
    }
}
