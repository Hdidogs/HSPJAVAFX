package appli.dashboard;

import appli.StartApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import model.Etats;
import repository.DossiersRepository;
import repository.EtatsRepository;
import repository.PatientsRepository;
import repository.ProduitsRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class DashboardController {

    @FXML
    private Label patientCountLabel;

    @FXML
    private Label activeDossiersLabel;

    @FXML
    private Label closeDossiersLabel;

    @FXML
    private Label stockCountLabel;

    @FXML
    private PieChart patientStatChart;

    @FXML
    private LineChart<String, Number> admissionsChart;

    @FXML
    public void initialize() throws SQLException {
        loadStatistics();
        loadPatientStatsChart();
        loadAdmissionsChart();
    }

    private void loadStatistics() throws SQLException {
        int totalPatients = PatientsRepository.getNumberPatients();
        int activeDossiers = DossiersRepository.getDossiersActive();
        int closeDossiers = DossiersRepository.getDossiersClose();;
        int stockCount = ProduitsRepository.getNumberProduits();

        // Mise à jour des labels
        patientCountLabel.setText(String.valueOf(totalPatients));
        activeDossiersLabel.setText(String.valueOf(activeDossiers));
        closeDossiersLabel.setText(String.valueOf(closeDossiers));
        stockCountLabel.setText(String.valueOf(stockCount));
    }

    private void loadPatientStatsChart() throws SQLException {
        List<Etats> etats = EtatsRepository.getAlletats();
        for (Etats etat : etats) {
            patientStatChart.getData().add(new PieChart.Data(etat.getLibelle(), DossiersRepository.getDossiersByEtat(etat.getId())));
        }
        //patientStatChart.getData().addAll(
        //        new PieChart.Data("Hommes", 60),
        //        new PieChart.Data("Femmes", 50),
        //        new PieChart.Data("Enfants", 10)
        //);
    }

    private void loadAdmissionsChart() throws SQLException {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Admissions par mois");
        series.getData().add(new XYChart.Data<>("Janvier", DossiersRepository.getDossiersByMonth(1)));
        series.getData().add(new XYChart.Data<>("Février", DossiersRepository.getDossiersByMonth(2)));
        series.getData().add(new XYChart.Data<>("Mars", DossiersRepository.getDossiersByMonth(3)));
        series.getData().add(new XYChart.Data<>("Avril", DossiersRepository.getDossiersByMonth(4)));
        series.getData().add(new XYChart.Data<>("Mai", DossiersRepository.getDossiersByMonth(5)));
        series.getData().add(new XYChart.Data<>("Juin", DossiersRepository.getDossiersByMonth(6)));
        series.getData().add(new XYChart.Data<>("Juillet", DossiersRepository.getDossiersByMonth(7)));
        series.getData().add(new XYChart.Data<>("Août", DossiersRepository.getDossiersByMonth(8)));
        series.getData().add(new XYChart.Data<>("Septembre", DossiersRepository.getDossiersByMonth(9)));
        series.getData().add(new XYChart.Data<>("Octobre", DossiersRepository.getDossiersByMonth(10)));
        series.getData().add(new XYChart.Data<>("Novembre", DossiersRepository.getDossiersByMonth(11)));
        series.getData().add(new XYChart.Data<>("Décembre", DossiersRepository.getDossiersByMonth(12)));

        admissionsChart.getData().add(series);
    }

    @FXML
    private void showPatientManagement() throws IOException {
        StartApplication.changeScene("GestionPatients/GestionPatients.fxml");
    }

    @FXML
    private void showMedicalRecords() throws IOException {
        StartApplication.changeScene("GestionDossiersMedicaux/GestionDossiersMedicaux.fxml");
    }

    @FXML
    private void showStockManagement() throws IOException {
        StartApplication.changeScene("GestionStocks/GestionStocks.fxml");
    }

    @FXML
    private void handleLogout() throws IOException {
        StartApplication.changeScene("Login/Login.fxml");
    }
}
