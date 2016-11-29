package ma.superyass.piserver;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;

public class FXMLController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private LineChart<String, Double> chart;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    private ObservableList<XYChart.Series<String, Double>> lineChartData;
    private Series<String, Double> series;

    private ServerSocketManager manager;

    private int counter;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        manager = new ServerSocketManager(this);
        manager.startServer();

        lineChartData = FXCollections.observableArrayList();
        series = new Series<>();
        series.setName("Data");

        lineChartData.add(series);

        chart.setData(lineChartData);

        counter = 0;
    }

    public void processMessage(final String message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                try {
                    double d = Double.parseDouble(message);
                    label.setText(d + "");
                    series.getData().add(new XYChart.Data(counter+"", d));
                    counter++;
                } catch (Exception e) {
                    label.setText(message + "n'est pas un nombre (double)");
                }
            }
        });
    }

}
