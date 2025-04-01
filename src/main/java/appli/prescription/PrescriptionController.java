package com.hsp.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class PrescriptionController implements BaseController {

    // Modèles de données
    public static class Patient {
        private final Integer id;
        private final String lastName;
        private final String firstName;
        private final LocalDate dateOfBirth;
        private final String gender;

        public Patient(Integer id, String lastName, String firstName, LocalDate dateOfBirth, String gender) {
            this.id = id;
            this.lastName = lastName;
            this.firstName = firstName;
            this.dateOfBirth = dateOfBirth;
            this.gender = gender;
        }

        public Integer getId() { return id; }
        public String getLastName() { return lastName; }
        public String getFirstName() { return firstName; }
        public LocalDate getDateOfBirth() { return dateOfBirth; }
        public String getGender() { return gender; }

        @Override
        public String toString() {
            return lastName + " " + firstName + " (ID: " + id + ")";
        }
    }

    public static class Medication {
        private final String name;
        private final String dosage;
        private final String frequency;
        private final String duration;
        private final String instructions;

        public Medication(String name, String dosage, String frequency, String duration, String instructions) {
            this.name = name;
            this.dosage = dosage;
            this.frequency = frequency;
            this.duration = duration;
            this.instructions = instructions;
        }

        public String getName() { return name; }
        public String getDosage() { return dosage; }
        public String getFrequency() { return frequency; }
        public String getDuration() { return duration; }
        public String getInstructions() { return instructions; }
    }

    // Composants FXML
    @FXML
    private Label userInfoLabel;

    @FXML
    private TextField patientSearchField;

    @FXML
    private TableView<Patient> patientsTableView;

    @FXML
    private TableColumn<Patient, Integer> patientIdColumn;

    @FXML
    private TableColumn<Patient, String> patientNameColumn;

    @FXML
    private TableColumn<Patient, String> roomNumberColumn;

    @FXML
    private Label selectedPatientNameLabel;

    @FXML
    private Label patientDobLabel;

    @FXML
    private Label diagnosisLabel;

    @FXML
    private Label admissionDateLabel;

    @FXML
    private DatePicker prescriptionDatePicker;

    @FXML
    private TableView<Medication> medicationsTableView;

    @FXML
    private TableColumn<Medication, String> medNameColumn;

    @FXML
    private TableColumn<Medication, String> medDosageColumn;

    @FXML
    private TableColumn<Medication, String> medFrequencyColumn;

    @FXML
    private TableColumn<Medication, String> medDurationColumn;

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

    @FXML
    private TextArea generalInstructionsTextArea;

    // Variables de classe
    private String currentUsername;
    private String currentRole;
    private ObservableList<Patient> hospitalizedPatients = FXCollections.observableArrayList();
    private ObservableList<Medication> prescribedMedications = FXCollections.observableArrayList();
    private ObservableList<String> availableMedications = FXCollections.observableArrayList();
    private Patient selectedPatient;
    private Medication selectedMedication;
    private boolean isEditingMedication = false;

    @FXML
    public void initialize() {
        // Initialisation des colonnes de la table des patients
        patientIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        patientNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getLastName() + " " +
                        cellData.getValue().getFirstName()));

        // La colonne roomNumber serait normalement liée à une propriété de chambre
        // Pour cette démo, nous utilisons des valeurs fictives
        roomNumberColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty("" + (100 + cellData.getValue().getId())));

        // Initialisation des colonnes de la table des médicaments
        medNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        medDosageColumn.setCellValueFactory(new PropertyValueFactory<>("dosage"));
        medFrequencyColumn.setCellValueFactory(new PropertyValueFactory<>("frequency"));
        medDurationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));

        // Initialisation du DatePicker
        prescriptionDatePicker.setValue(LocalDate.now());

        // Initialisation du Spinner de durée
        SpinnerValueFactory<Integer> durationValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 365, 7);
        durationSpinner.setValueFactory(durationValueFactory);

        // Initialisation des ComboBox
        availableMedications.addAll(
                "Paracétamol", "Ibuprofène", "Amoxicilline", "Oméprazole", "Lorazépam",
                "Metformine", "Amlodipine", "Simvastatine", "Lévothyroxine", "Salbutamol"
        );
        medicationComboBox.setItems(availableMedications);

        durationUnitComboBox.getItems().addAll("Jours", "Semaines", "Mois");
        durationUnitComboBox.setValue("Jours");

        // Écouteurs de sélection
        patientsTableView.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        selectedPatient = newSelection;
                        updatePatientInfo(selectedPatient);
                    }
                }
        );

        medicationsTableView.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        selectedMedication = newSelection;
                    }
                }
        );

        // Chargement des données de test
        loadSampleData();
    }

    @Override
    public void setUserInfo(String username, String role) {
        this.currentUsername = username;
        this.currentRole = role;
        userInfoLabel.setText(username + " (" + role + ")");
    }

    public void setPatientInfo(Patient patient) {
        // Cette méthode est appelée lorsqu'un patient est sélectionné depuis une autre vue
        if (patient != null) {
            selectedPatient = patient;
            updatePatientInfo(selectedPatient);

            // Sélectionner le patient dans la table
            for (Patient p : hospitalizedPatients) {
                if (p.getId().equals(patient.getId())) {
                    patientsTableView.getSelectionModel().select(p);
                    break;
                }
            }
        }
    }

    @FXML
    private void handleAddMedication(ActionEvent event) {
        // Réinitialiser le formulaire pour un nouveau médicament
        clearMedicationForm();
        isEditingMedication = false;
    }

    @FXML
    private void handleEditMedication(ActionEvent event) {
        if (selectedMedication != null) {
            // Remplir le formulaire avec les détails du médicament sélectionné
            medicationComboBox.setValue(selectedMedication.getName());
            dosageTextField.setText(selectedMedication.getDosage());
            frequencyComboBox.setValue(selectedMedication.getFrequency());

            // Extraire la durée et l'unité
            String duration = selectedMedication.getDuration();
            String[] parts = duration.split(" ");
            if (parts.length == 2) {
                try {
                    int value = Integer.parseInt(parts[0]);
                    durationSpinner.getValueFactory().setValue(value);
                    durationUnitComboBox.setValue(parts[1]);
                } catch (NumberFormatException e) {
                    // Valeur par défaut en cas d'erreur
                    durationSpinner.getValueFactory().setValue(7);
                    durationUnitComboBox.setValue("Jours");
                }
            }

            instructionsTextArea.setText(selectedMedication.getInstructions());
            isEditingMedication = true;
        } else {
            showAlert(Alert.AlertType.WARNING, "Aucune sélection",
                    "Veuillez sélectionner un médicament à modifier.");
        }
    }

    @FXML
    private void handleDeleteMedication(ActionEvent event) {
        if (selectedMedication != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText("Supprimer le médicament");
            alert.setContentText("Êtes-vous sûr de vouloir supprimer ce médicament de l'ordonnance?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                prescribedMedications.remove(selectedMedication);
                selectedMedication = null;
                medicationsTableView.getSelectionModel().clearSelection();
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Aucune sélection",
                    "Veuillez sélectionner un médicament à supprimer.");
        }
    }

    @FXML
    private void handleSaveMedication(ActionEvent event) {
        // Validation des champs
        if (medicationComboBox.getValue() == null ||
                dosageTextField.getText().trim().isEmpty() ||
                frequencyComboBox.getValue() == null) {

            showAlert(Alert.AlertType.ERROR, "Erreur de saisie",
                    "Veuillez remplir tous les champs obligatoires.");
            return;
        }

        String durationText = durationSpinner.getValue() + " " + durationUnitComboBox.getValue();

        if (isEditingMedication && selectedMedication != null) {
            // Créer un nouveau médicament avec les valeurs mises à jour
            // (puisque Medication est immuable)
            Medication updatedMedication = new Medication(
                    medicationComboBox.getValue(),
                    dosageTextField.getText(),
                    frequencyComboBox.getValue(),
                    durationText,
                    instructionsTextArea.getText()
            );

            // Remplacer l'ancien médicament par le nouveau
            int index = prescribedMedications.indexOf(selectedMedication);
            prescribedMedications.set(index, updatedMedication);
        } else {
            // Ajouter un nouveau médicament
            Medication newMedication = new Medication(
                    medicationComboBox.getValue(),
                    dosageTextField.getText(),
                    frequencyComboBox.getValue(),
                    durationText,
                    instructionsTextArea.getText()
            );

            prescribedMedications.add(newMedication);
        }

        // Réinitialiser le formulaire
        clearMedicationForm();
        isEditingMedication = false;
    }

    @FXML
    private void handleCancelMedication(ActionEvent event) {
        clearMedicationForm();
        isEditingMedication = false;
    }

    @FXML
    private void handlePreviewPrescription(ActionEvent event) {
        if (selectedPatient == null) {
            showAlert(Alert.AlertType.WARNING, "Aucun patient sélectionné",
                    "Veuillez sélectionner un patient avant de prévisualiser l'ordonnance.");
            return;
        }

        if (prescribedMedications.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Aucun médicament",
                    "Veuillez ajouter au moins un médicament à l'ordonnance.");
            return;
        }

        // Afficher une fenêtre de prévisualisation
        try {
            // Dans une application réelle, vous utiliseriez un modèle d'ordonnance
            // et passeriez les données pour générer un aperçu

            // Pour cette démo, nous affichons simplement un résumé
            StringBuilder preview = new StringBuilder();
            preview.append("ORDONNANCE\n\n");
            preview.append("Date: ").append(prescriptionDatePicker.getValue().format(
                    DateTimeFormatter.ofPattern("dd/MM/yyyy"))).append("\n\n");

            preview.append("Patient: ").append(selectedPatient.getLastName())
                    .append(" ").append(selectedPatient.getFirstName()).append("\n");
            preview.append("Date de naissance: ").append(selectedPatient.getDateOfBirth().format(
                    DateTimeFormatter.ofPattern("dd/MM/yyyy"))).append("\n\n");

            preview.append("MÉDICAMENTS:\n");
            for (Medication med : prescribedMedications) {
                preview.append("- ").append(med.getName()).append(" ").append(med.getDosage())
                        .append(", ").append(med.getFrequency()).append(", ")
                        .append(med.getDuration()).append("\n");

                if (!med.getInstructions().isEmpty()) {
                    preview.append("  Instructions: ").append(med.getInstructions()).append("\n");
                }
                preview.append("\n");
            }

            if (!generalInstructionsTextArea.getText().isEmpty()) {
                preview.append("INSTRUCTIONS GÉNÉRALES:\n");
                preview.append(generalInstructionsTextArea.getText()).append("\n\n");
            }

            preview.append("Médecin: ").append(currentUsername);

            // Afficher l'aperçu dans une boîte de dialogue
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Aperçu de l'ordonnance");
            alert.setHeaderText("Ordonnance pour " + selectedPatient.getLastName() + " " +
                    selectedPatient.getFirstName());

            TextArea textArea = new TextArea(preview.toString());
            textArea.setEditable(false);
            textArea.setWrapText(true);
            textArea.setPrefWidth(500);
            textArea.setPrefHeight(400);

            alert.getDialogPane().setContent(textArea);
            alert.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur",
                    "Une erreur est survenue lors de la prévisualisation de l'ordonnance.");
        }
    }

    @FXML
    private void handlePrintPrescription(ActionEvent event) {
        if (selectedPatient == null) {
            showAlert(Alert.AlertType.WARNING, "Aucun patient sélectionné",
                    "Veuillez sélectionner un patient avant d'imprimer l'ordonnance.");
            return;
        }

        if (prescribedMedications.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Aucun médicament",
                    "Veuillez ajouter au moins un médicament à l'ordonnance.");
            return;
        }

        // Dans une application réelle, vous généreriez un document PDF
        // et l'enverriez à l'imprimante

        // Pour cette démo, nous simulons l'impression
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            boolean proceed = job.showPrintDialog(null);
            if (proceed) {
                showAlert(Alert.AlertType.INFORMATION, "Impression",
                        "L'ordonnance a été envoyée à l'imprimante.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur d'impression",
                    "Impossible d'accéder à l'imprimante.");
        }
    }

    @FXML
    private void handleSavePrescription(ActionEvent event) {
        if (selectedPatient == null) {
            showAlert(Alert.AlertType.WARNING, "Aucun patient sélectionné",
                    "Veuillez sélectionner un patient avant d'enregistrer l'ordonnance.");
            return;
        }

        if (prescribedMedications.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Aucun médicament",
                    "Veuillez ajouter au moins un médicament à l'ordonnance.");
            return;
        }

        // Dans une application réelle, vous enregistreriez l'ordonnance dans la base de données

        showAlert(Alert.AlertType.INFORMATION, "Succès",
                "L'ordonnance a été enregistrée avec succès.");
    }

    @FXML
    private void handleDischargePatient(ActionEvent event) {
        if (selectedPatient == null) {
            showAlert(Alert.AlertType.WARNING, "Aucun patient sélectionné",
                    "Veuillez sélectionner un patient avant de procéder à sa sortie.");
            return;
        }

        if (prescribedMedications.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Aucun médicament prescrit");
            alert.setContentText("Aucun médicament n'a été prescrit. Voulez-vous quand même procéder à la sortie du patient?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() != ButtonType.OK) {
                return;
            }
        }

        // Confirmation de sortie
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de sortie");
        alert.setHeaderText("Sortie du patient");
        alert.setContentText("Confirmez-vous la sortie de " + selectedPatient.getLastName() +
                " " + selectedPatient.getFirstName() + " ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Dans une application réelle, vous mettriez à jour le statut du patient
            // et libéreriez la chambre dans la base de données

            // Pour cette démo, nous supprimons simplement le patient de la liste
            hospitalizedPatients.remove(selectedPatient);

            showAlert(Alert.AlertType.INFORMATION, "Succès",
                    "Le patient a été libéré avec succès.");

            // Réinitialiser le formulaire
            clearPatientInfo();
            prescribedMedications.clear();
            generalInstructionsTextArea.clear();
        }
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        try {
            // Redirection vers l'écran de connexion
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hsp/views/personnel_login.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) userInfoLabel.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("HSP - Connexion Personnel");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur",
                    "Impossible de charger la vue de connexion.");
        }
    }

    private void loadSampleData() {
        // Chargement des patients hospitalisés de test
        hospitalizedPatients.add(new Patient(1, "Dupont", "Jean", LocalDate.of(1975, 5, 15), "M"));
        hospitalizedPatients.add(new Patient(2, "Martin", "Sophie", LocalDate.of(1982, 9, 23), "F"));
        hospitalizedPatients.add(new Patient(3, "Bernard", "Michel", LocalDate.of(1968, 3, 10), "M"));

        patientsTableView.setItems(hospitalizedPatients);
        medicationsTableView.setItems(prescribedMedications);
    }

    private void updatePatientInfo(Patient patient) {
        if (patient != null) {
            selectedPatientNameLabel.setText(patient.getLastName() + " " + patient.getFirstName());
            patientDobLabel.setText(patient.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

            // Dans une application réelle, vous récupéreriez ces informations depuis la base de données
            diagnosisLabel.setText("Pneumonie"); // Exemple
            admissionDateLabel.setText(LocalDate.now().minusDays(5).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))); // Exemple
        }
    }

    private void clearPatientInfo() {
        selectedPatient = null;
        selectedPatientNameLabel.setText("Aucun patient sélectionné");
        patientDobLabel.setText("-");
        diagnosisLabel.setText("-");
        admissionDateLabel.setText("-");
        patientsTableView.getSelectionModel().clearSelection();
    }

    private void clearMedicationForm() {
        medicationComboBox.getSelectionModel().clearSelection();
        dosageTextField.clear();
        frequencyComboBox.getSelectionModel().clearSelection();
        durationSpinner.getValueFactory().setValue(7);
        durationUnitComboBox.setValue("Jours");
        instructionsTextArea.clear();
        selectedMedication = null;
        medicationsTableView.getSelectionModel().clearSelection();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}