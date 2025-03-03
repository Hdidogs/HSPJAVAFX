package appli.patients;

import appli.StartApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Patients;
import repository.PatientsRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class GestionPatientsController {

    @FXML
    private TextField searchField;

    @FXML
    private TableView<Patients> patientTable;

    @FXML
    private TableColumn<Patients, String> nomColumn;

    @FXML
    private TableColumn<Patients, String> prenomColumn;

    @FXML
    private TableColumn<Patients, String> numSecuColumn;

    @FXML
    private TableColumn<Patients, String> mailColumn;

    @FXML
    private TableColumn<Patients, String> telColumn;

    @FXML
    private TableColumn<Patients, String> adresseColumn;

    @FXML
    private TextField nomField;

    @FXML
    private TextField prenomField;

    @FXML
    private TextField numSecuField;

    @FXML
    private TextField mailField;

    @FXML
    private TextField telField;

    @FXML
    private TextField adresseField;

    @FXML
    private TextField villeField;

    private Patients selectedPatient;

    @FXML
    public void initialize() {
        try {
            nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
            prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            numSecuColumn.setCellValueFactory(new PropertyValueFactory<>("numSecu"));
            mailColumn.setCellValueFactory(new PropertyValueFactory<>("mail"));
            telColumn.setCellValueFactory(new PropertyValueFactory<>("tel"));
            adresseColumn.setCellValueFactory(new PropertyValueFactory<>("adresseComplete"));

            loadTableData();

            patientTable.setOnMouseClicked(this::handleTableClick);
        } catch (Exception e) {
            showAlert("Erreur", "Erreur lors de l'initialisation : " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    private void loadTableData() {
        try {
            List<Patients> patientsList = PatientsRepository.getAllPatients();
            ObservableList<Patients> observableList = FXCollections.observableArrayList(patientsList);
            patientTable.setItems(observableList);
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors du chargement des patients : " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void backButton() throws IOException {
        StartApplication.changeScene("dashboard/dashboard.fxml");
    }

    @FXML
    private void handleSearch() {
        try {
            String searchQuery = searchField.getText().trim();
            List<Patients> filteredPatients = PatientsRepository.searchPatients(searchQuery);
            patientTable.setItems(FXCollections.observableArrayList(filteredPatients));
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors de la recherche : " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    @FXML
    private void handleAdd() {
        if (isFormValid()) {
            Patients newPatient = new Patients(
                    nomField.getText(),
                    prenomField.getText(),
                    numSecuField.getText(),
                    mailField.getText(),
                    telField.getText(),
                    adresseField.getText(),
                    villeField.getText(),
                    0
            );

            try {
                if (PatientsRepository.addPatient(newPatient)) {
                    loadTableData();
                    clearForm();
                    showAlert("Succès", "Patient ajouté avec succès.", Alert.AlertType.INFORMATION);
                } else {
                    showAlert("Erreur", "Erreur lors de l'ajout du patient.", Alert.AlertType.ERROR);
                }
            } catch (SQLException e) {
                showAlert("Erreur", "Erreur lors de l'ajout : " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void handleUpdate() {
        if (selectedPatient != null && isFormValid()) {
            selectedPatient.setNom(nomField.getText());
            selectedPatient.setPrenom(prenomField.getText());
            selectedPatient.setNumSecu(numSecuField.getText());
            selectedPatient.setMail(mailField.getText());
            selectedPatient.setTel(telField.getText());
            selectedPatient.setRue(adresseField.getText());

            try {
                if (PatientsRepository.updatePatient(selectedPatient)) {
                    loadTableData();
                    clearForm();
                    showAlert("Succès", "Patient modifié avec succès.", Alert.AlertType.INFORMATION);
                } else {
                    showAlert("Erreur", "Erreur lors de la modification du patient.", Alert.AlertType.ERROR);
                }
            } catch (SQLException e) {
                showAlert("Erreur", "Erreur lors de la modification : " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    private void handleTableClick(MouseEvent event) {
        selectedPatient = patientTable.getSelectionModel().getSelectedItem();
        if (selectedPatient != null) {
            nomField.setText(selectedPatient.getNom());
            prenomField.setText(selectedPatient.getPrenom());
            numSecuField.setText(selectedPatient.getNumSecu());
            mailField.setText(selectedPatient.getMail());
            telField.setText(selectedPatient.getTel());
            adresseField.setText(selectedPatient.getRue());
        }
    }

    private boolean isFormValid() {
        if (nomField.getText().isEmpty() || prenomField.getText().isEmpty() ||
                numSecuField.getText().isEmpty() || mailField.getText().isEmpty() ||
                telField.getText().isEmpty() || adresseField.getText().isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs obligatoires.", Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }

    private void clearForm() {
        nomField.clear();
        prenomField.clear();
        numSecuField.clear();
        mailField.clear();
        telField.clear();
        adresseField.clear();
        selectedPatient = null;
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
