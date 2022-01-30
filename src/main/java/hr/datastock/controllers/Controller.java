package hr.datastock.controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import org.springframework.stereotype.Component;

@Component
public class Controller {

    @FXML
    public LineChart<String, Double> main;
}
