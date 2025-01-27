package appli.dashboard;

import appli.StartApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.io.IOException;

public class DashboardController {

    @FXML
    private Label patientCountLabel;

    @FXML
    private Label activeDossiersLabel;

    @FXML
    private Label stockCountLabel;

    @FXML
    private PieChart patientStatChart;

    @FXML
    private LineChart<String, Number> admissionsChart;

    @FXML
    public void initialize() {
        loadStatistics();
        loadPatientStatsChart();
        loadAdmissionsChart();
    }

    private void loadStatistics() {
        int totalPatients = 120;
        int activeDossiers = 45;
        int stockCount = 350;

        // Mise à jour des labels
        patientCountLabel.setText(String.valueOf(totalPatients));
        activeDossiersLabel.setText(String.valueOf(activeDossiers));
        stockCountLabel.setText(String.valueOf(stockCount));
    }

    private void loadPatientStatsChart() {
        // Exemple de données fictives
        patientStatChart.getData().addAll(
                new PieChart.Data("Hommes", 60),
                new PieChart.Data("Femmes", 50),
                new PieChart.Data("Enfants", 10)
        );
    }

    private void loadAdmissionsChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Admissions par mois");
        series.getData().add(new XYChart.Data<>("Janvier", 15));
        series.getData().add(new XYChart.Data<>("Février", 20));
        series.getData().add(new XYChart.Data<>("Mars", 30));
        series.getData().add(new XYChart.Data<>("Avril", 25));
        series.getData().add(new XYChart.Data<>("Mai", 35));
        series.getData().add(new XYChart.Data<>("Juin", 40));

        admissionsChart.getData().add(series);
    }

    @FXML
    private void showPatientManagement() throws IOException {
        StartApplication.changeScene("patients/patientmanagement.fxml");
    }

    @FXML
    private void showMedicalRecords() throws IOException {
        StartApplication.changeScene("dossiers/medicalrecords.fxml");
    }

    @FXML
    private void showStockManagement() throws IOException {
        StartApplication.changeScene("stocks/stockmanagement.fxml");
    }

    @FXML
    private void handleLogout() throws IOException {
        StartApplication.changeScene("auth/login.fxml");
    }
}
