package com.hsp.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;
import java.time.LocalDate;

public class PrescriptionController {

    // --- Header ---
    @FXML
    private Label userInfoLabel;

    @FXML
    private Button logoutButton;

    @FXML
    private void handleLogout(ActionEvent event) {
        // Simuler la déconnexion
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Déconnexion");
        alert.setHeaderText(null);
        alert.setContentText("Vous avez été déconnecté.");
        alert.showAndWait();
    }

    // --- Liste des patients hospitalisés (panneau gauche) ---
    @FXML
    private TextField patientSearchField;

    @FXML
    private TableView<?> patientsTableView;

    @FXML
    private TableColumn<?, ?> patientIdColumn;

    @FXML
    private TableColumn<?, ?> patientNameColumn;

    @FXML
    private TableColumn<?, ?> roomNumberColumn;

    // --- Informations Patient (dans le TitledPane "Information Patient") ---
    @FXML
    private Label selectedPatientNameLabel;

    @FXML
    private Label patientDobLabel;

    @FXML
    private Label diagnosisLabel;

    @FXML
    private Label admissionDateLabel;

    // --- Section Ordonnance ---
    @FXML
    private DatePicker prescriptionDatePicker;

    @FXML
    private TableView<?> medicationsTableView;

    @FXML
    private TableColumn<?, ?> medNameColumn;

    @FXML
    private TableColumn<?, ?> medDosageColumn;

    @FXML
    private TableColumn<?, ?> medFrequencyColumn;

    @FXML
    private TableColumn<?, ?> medDurationColumn;

    // --- Détails du médicament (dans le TitledPane "Détails du médicament") ---
    @FXML
    private ComboBox<String> medicationComboBox;

    @FXML
    private TextField dosageTextField;

    @FXML
    private ComboBox<String> frequencyComboBox;

    @FXML
    private Spinner<Integer> durationSpinner;

    @FXML
    private ComboBox<String> durationUnitComboBox;

    @FXML
    private TextArea instructionsTextArea;

    // --- Instructions générales ---
    @FXML
    private TextArea generalInstructionsTextArea;

    // --- Boutons de la partie inférieure ---
    @FXML
    private Button previewButton;

    @FXML
    private Button printButton;

    @FXML
    private Button savePrescriptionButton;

    @FXML
    private Button dischargeButton;

    // --- Méthodes d'action pour la section "Ordonnance" ---

    @FXML
    private void handleAddMedication(ActionEvent event) {
        // Simuler l'ajout d'un médicament à la liste
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Ajouter Médicament");
        alert.setHeaderText(null);
        alert.setContentText("Ajout d'un nouveau médicament.");
        alert.showAndWait();
    }

    @FXML
    private void handleEditMedication(ActionEvent event) {
        // Simuler la modification d'un médicament sélectionné
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Modifier Médicament");
        alert.setHeaderText(null);
        alert.setContentText("Modification du médicament sélectionné.");
        alert.showAndWait();
    }

    @FXML
    private void handleDeleteMedication(ActionEvent event) {
        // Simuler la suppression d'un médicament de la liste
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Supprimer Médicament");
        alert.setHeaderText(null);
        alert.setContentText("Suppression du médicament sélectionné.");
        alert.showAndWait();
    }

    @FXML
    private void handleCancelMedication(ActionEvent event) {
        // Réinitialiser le formulaire de détails du médicament
        medicationComboBox.getSelectionModel().clearSelection();
        dosageTextField.clear();
        frequencyComboBox.getSelectionModel().clearSelection();
        durationSpinner.getValueFactory().setValue(7); // valeur par défaut
        durationUnitComboBox.getSelectionModel().clearSelection();
        instructionsTextArea.clear();
    }

    @FXML
    private void handleSaveMedication(ActionEvent event) {
        // Simuler la sauvegarde des détails du médicament
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Sauvegarder Médicament");
        alert.setHeaderText(null);
        alert.setContentText("Les détails du médicament ont été sauvegardés.");
        alert.showAndWait();
    }

    @FXML
    private void handlePreviewPrescription(ActionEvent event) {
        // Simuler l'aperçu de l'ordonnance
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Aperçu Ordonnance");
        alert.setHeaderText(null);
        alert.setContentText("Aperçu de l'ordonnance.");
        alert.showAndWait();
    }

    @FXML
    private void handlePrintPrescription(ActionEvent event) {
        // Simuler l'impression de l'ordonnance
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Imprimer Ordonnance");
        alert.setHeaderText(null);
        alert.setContentText("L'ordonnance a été envoyée à l'imprimante.");
        alert.showAndWait();
    }

    @FXML
    private void handleSavePrescription(ActionEvent event) {
        // Simuler la sauvegarde de l'ordonnance
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Sauvegarder Ordonnance");
        alert.setHeaderText(null);
        alert.setContentText("L'ordonnance a été sauvegardée avec succès.");
        alert.showAndWait();
    }

    @FXML
    private void handleDischargePatient(ActionEvent event) {
        // Simuler la procédure de sortie du patient
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Sortie Patient");
        alert.setHeaderText(null);
        alert.setContentText("Le patient a été libéré.");
        alert.showAndWait();
    }

    // --- Initialisation du contrôleur ---
    @FXML
    private void initialize() {
        // Initialiser le DatePicker avec la date actuelle
        prescriptionDatePicker.setValue(LocalDate.now());

        // Initialiser le Spinner pour la durée du médicament
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 365, 7);
        durationSpinner.setValueFactory(valueFactory);

        // Exemple d'initialisation pour les ComboBox
        medicationComboBox.setItems(FXCollections.observableArrayList("Médicament A", "Médicament B", "Médicament C"));
        frequencyComboBox.setItems(FXCollections.observableArrayList(
                "1 fois par jour",
                "2 fois par jour",
                "3 fois par jour",
                "4 fois par jour",
                "Toutes les 4 heures",
                "Toutes les 6 heures",
                "Toutes les 8 heures",
                "Au besoin"
        ));
        durationUnitComboBox.setItems(FXCollections.observableArrayList("Jours", "Semaines", "Mois"));
    }
}
