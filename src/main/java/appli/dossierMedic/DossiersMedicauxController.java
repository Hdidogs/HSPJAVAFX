package appli.dossierMedic;

import appli.StartApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.*;
import repository.DossiersRepository;
import repository.EtatsRepository;
import repository.PatientsRepository;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class DossiersMedicauxController {

    @FXML
    private TextField searchField;

    @FXML
    private TableView<Dossiers> dossierTable;

    @FXML
    private TableColumn<Dossiers, String> patientColumn;

    @FXML
    private TableColumn<Dossiers, Date> dateArriveeColumn;

    @FXML
    private TableColumn<Dossiers, String> symptomesColumn;

    @FXML
    private TableColumn<Dossiers, Integer> niveauGraviteColumn;

    @FXML
    private TableColumn<Dossiers, String> etatColumn;

    @FXML
    private ComboBox<Patients> patientComboBox;

    @FXML
    private DatePicker dateArriveeField;

    @FXML
    private TextArea symptomesField;

    @FXML
    private ComboBox<Integer> niveauGraviteComboBox;

    @FXML
    private ComboBox<Etats> etatComboBox;

    private Dossiers selectedDossier;

    @FXML
    private void backButton() throws IOException {
        StartApplication.changeScene("dashboard/dashboard.fxml");
    }

    @FXML
    public void initialize() {
        // Initialisation des colonnes de la table
        patientColumn.setCellValueFactory(new PropertyValueFactory<>("refPatients"));
        dateArriveeColumn.setCellValueFactory(new PropertyValueFactory<>("dateArrivee"));
        symptomesColumn.setCellValueFactory(new PropertyValueFactory<>("symptomes"));
        niveauGraviteColumn.setCellValueFactory(new PropertyValueFactory<>("niveauGravite"));
        etatColumn.setCellValueFactory(new PropertyValueFactory<>("refEtat"));

        // Charger les données initiales
        loadTableData();
        loadPatientComboBox();
        loadEtatComboBox();
        loadNiveauGraviteOptions();

        // Gestion de la sélection dans la table
        dossierTable.setOnMouseClicked(this::handleTableClick);

        patientComboBox.setCellFactory(param -> new ListCell<Patients>() {
            @Override
            protected void updateItem(Patients item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNom() + " " + item.getPrenom());
                }
            }
        });

        patientComboBox.setButtonCell(new ListCell<Patients>() {
            @Override
            protected void updateItem(Patients item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNom() + " " + item.getPrenom());
                }
            }
        });

        etatComboBox.setCellFactory(param -> new ListCell<Etats>() {
            @Override
            protected void updateItem(Etats item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getLibelle());
                }
            }
        });

        etatComboBox.setButtonCell(new ListCell<Etats>() {
            @Override
            protected void updateItem(Etats item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getLibelle());
                }
            }
        });
    }

    private void loadTableData() {
        try {
            List<Dossiers> dossiersList = DossiersRepository.getAllDossiers();
            ObservableList<Dossiers> observableList = FXCollections.observableArrayList(dossiersList);
            dossierTable.setItems(observableList);
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors du chargement des données : " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void loadPatientComboBox() {
        try {
            List<Patients> patients = PatientsRepository.getAllPatients();
            ObservableList<Patients> patientOptions = FXCollections.observableArrayList(patients);
            patientComboBox.setItems(patientOptions);
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors du chargement des patients : " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void loadEtatComboBox() {
        try {
            List<Etats> etats = EtatsRepository.getAlletats();
            ObservableList<Etats> etatOptions = FXCollections.observableArrayList(etats);
            etatComboBox.setItems(etatOptions);
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors du chargement des états : " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void loadNiveauGraviteOptions() {
        ObservableList<Integer> graviteOptions = FXCollections.observableArrayList(1, 2, 3, 4, 5);
        niveauGraviteComboBox.setItems(graviteOptions);
    }

    @FXML
    private void handleSearch() {
        try {
            String searchQuery = searchField.getText().trim();
            List<Dossiers> filteredDossiers = DossiersRepository.searchDossiers(searchQuery);
            dossierTable.setItems(FXCollections.observableArrayList(filteredDossiers));
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors de la recherche : " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleAdd() {
        try {
            if (isFormValid()) {
                Dossiers newDossier = new Dossiers(
                        patientComboBox.getValue().getId(),
                        2,
                        Date.valueOf(dateArriveeField.getValue()),
                        symptomesField.getText(),
                        niveauGraviteComboBox.getValue(),
                        etatComboBox.getValue().getId(),
                        null
                );

                if (DossiersRepository.addDossiers(newDossier)) {
                    loadTableData();
                    clearForm();
                    showAlert("Succès", "Dossier ajouté avec succès.", Alert.AlertType.INFORMATION);
                } else {
                    showAlert("Erreur", "Erreur lors de l'ajout du dossier.", Alert.AlertType.ERROR);
                }
            }
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors de l'ajout : " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleUpdate() {
        try {
            if (selectedDossier != null && isFormValid()) {
                selectedDossier.setRefPatients(patientComboBox.getValue().getId());
                selectedDossier.setDateArrivee(Date.valueOf(dateArriveeField.getValue()));
                selectedDossier.setSymptomes(symptomesField.getText());
                selectedDossier.setNiveauGravite(niveauGraviteComboBox.getValue());
                selectedDossier.setRefEtat(etatComboBox.getValue().getId());

                if (DossiersRepository.updateDossiers(selectedDossier)) {
                    loadTableData();
                    clearForm();
                    showAlert("Succès", "Dossier modifié avec succès.", Alert.AlertType.INFORMATION);
                } else {
                    showAlert("Erreur", "Erreur lors de la modification du dossier.", Alert.AlertType.ERROR);
                }
            }
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors de la modification : " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void handleTableClick(MouseEvent event) {
        try {
            selectedDossier = dossierTable.getSelectionModel().getSelectedItem();
            if (selectedDossier != null) {
                patientComboBox.setValue(PatientsRepository.getPatientById(selectedDossier.getRefPatients()));
                dateArriveeField.setValue(selectedDossier.getDateArrivee().toLocalDate());
                symptomesField.setText(selectedDossier.getSymptomes());
                niveauGraviteComboBox.setValue(selectedDossier.getNiveauGravite());
                etatComboBox.setValue(DossiersRepository.getEtatById(selectedDossier.getRefEtat()));
            }
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors du chargement des détails : " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private boolean isFormValid() {
        if (patientComboBox.getValue() == null || dateArriveeField.getValue() == null ||
                symptomesField.getText().isEmpty() || niveauGraviteComboBox.getValue() == null ||
                etatComboBox.getValue() == null) {
            showAlert("Erreur", "Veuillez remplir tous les champs obligatoires.", Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }

    private void clearForm() {
        patientComboBox.setValue(null);
        dateArriveeField.setValue(null);
        symptomesField.clear();
        niveauGraviteComboBox.setValue(null);
        etatComboBox.setValue(null);
        selectedDossier = null;
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
