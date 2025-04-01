package com.hsp.controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class HospitalizationController implements BaseController {

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

    public static class Room {
        private final String number;
        private final String type;
        private final Integer floor;
        private final Integer availableBeds;
        private final String features;

        public Room(String number, String type, Integer floor, Integer availableBeds, String features) {
            this.number = number;
            this.type = type;
            this.floor = floor;
            this.availableBeds = availableBeds;
            this.features = features;
        }

        public String getNumber() { return number; }
        public String getType() { return type; }
        public Integer getFloor() { return floor; }
        public Integer getAvailableBeds() { return availableBeds; }
        public String getFeatures() { return features; }

        @Override
        public String toString() {
            return "Chambre " + number + " (" + type + ", Étage " + floor + ")";
        }
    }

    public static class Hospitalization {
        private final Integer id;
        private final Patient patient;
        private final LocalDate startDate;
        private final Integer duration;
        private final Room room;
        private final String doctor;
        private final String status;
        private final String diseaseDescription;

        public Hospitalization(Integer id, Patient patient, LocalDate startDate, Integer duration,
                               Room room, String doctor, String status, String diseaseDescription) {
            this.id = id;
            this.patient = patient;
            this.startDate = startDate;
            this.duration = duration;
            this.room = room;
            this.doctor = doctor;
            this.status = status;
            this.diseaseDescription = diseaseDescription;
        }

        public Integer getId() { return id; }
        public Patient getPatient() { return patient; }
        public LocalDate getStartDate() { return startDate; }
        public Integer getDuration() { return duration; }
        public Room getRoom() { return room; }
        public String getDoctor() { return doctor; }
        public String getStatus() { return status; }
        public String getDiseaseDescription() { return diseaseDescription; }
    }

    // Composants FXML
    @FXML
    private Label userInfoLabel;

    @FXML
    private TextField patientSearchField;

    @FXML
    private TableView<Patient> patientSearchResultsTable;

    @FXML
    private TableColumn<Patient, Integer> patientIdColumn;

    @FXML
    private TableColumn<Patient, String> patientLastNameColumn;

    @FXML
    private TableColumn<Patient, String> patientFirstNameColumn;

    @FXML
    private TableColumn<Patient, String> patientDobColumn;

    @FXML
    private TableColumn<Patient, String> patientGenderColumn;

    @FXML
    private Label selectedPatientLabel;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private Spinner<Integer> durationSpinner;

    @FXML
    private ComboBox<String> departmentComboBox;

    @FXML
    private ComboBox<String> doctorComboBox;

    @FXML
    private TextArea diseaseDescriptionTextArea;

    @FXML
    private TextArea initialTreatmentTextArea;

    @FXML
    private ComboBox<String> roomTypeComboBox;

    @FXML
    private ComboBox<Integer> floorComboBox;

    @FXML
    private TableView<Room> availableRoomsTable;

    @FXML
    private TableColumn<Room, String> roomNumberColumn;

    @FXML
    private TableColumn<Room, String> roomTypeColumn;

    @FXML
    private TableColumn<Room, Integer> roomFloorColumn;

    @FXML
    private TableColumn<Room, Integer> roomBedsColumn;

    @FXML
    private TableColumn<Room, String> roomFeaturesColumn;

    @FXML
    private Label selectedRoomLabel;

    @FXML
    private ComboBox<String> hospitalizationStatusFilterComboBox;

    @FXML
    private TextField hospitalizationSearchField;

    @FXML
    private TableView<Hospitalization> hospitalizationsTableView;

    @FXML
    private TableColumn<Hospitalization, Integer> hospIdColumn;

    @FXML
    private TableColumn<Hospitalization, String> hospPatientColumn;

    @FXML
    private TableColumn<Hospitalization, String> hospStartDateColumn;

    @FXML
    private TableColumn<Hospitalization, Integer> hospDurationColumn;

    @FXML
    private TableColumn<Hospitalization, String> hospRoomColumn;

    @FXML
    private TableColumn<Hospitalization, String> hospDoctorColumn;

    @FXML
    private TableColumn<Hospitalization, String> hospStatusColumn;

    // Variables de classe
    private String currentUsername;
    private String currentRole;
    private ObservableList<Patient> allPatients = FXCollections.observableArrayList();
    private ObservableList<Room> allRooms = FXCollections.observableArrayList();
    private ObservableList<Hospitalization> allHospitalizations = FXCollections.observableArrayList();
    private Patient selectedPatient;
    private Room selectedRoom;
    private Hospitalization selectedHospitalization;

    @FXML
    public void initialize() {
        // Initialisation des colonnes de la table des patients
        patientIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        patientLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        patientFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        patientDobColumn.setCellValueFactory(cellData -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return new SimpleStringProperty(cellData.getValue().getDateOfBirth().format(formatter));
        });

        patientGenderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));

        // Initialisation des colonnes de la table des chambres
        roomNumberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        roomTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        roomFloorColumn.setCellValueFactory(new PropertyValueFactory<>("floor"));
        roomBedsColumn.setCellValueFactory(new PropertyValueFactory<>("availableBeds"));
        roomFeaturesColumn.setCellValueFactory(new PropertyValueFactory<>("features"));

        // Initialisation des colonnes de la table des hospitalisations
        hospIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        hospPatientColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPatient().getLastName() + " " +
                        cellData.getValue().getPatient().getFirstName()));

        hospStartDateColumn.setCellValueFactory(cellData -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return new SimpleStringProperty(cellData.getValue().getStartDate().format(formatter));
        });

        hospDurationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));

        hospRoomColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getRoom().getNumber()));

        hospDoctorColumn.setCellValueFactory(new PropertyValueFactory<>("doctor"));
        hospStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Initialisation du DatePicker
        startDatePicker.setValue(LocalDate.now());

        // Initialisation du Spinner de durée
        SpinnerValueFactory<Integer> durationValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 365, 1);
        durationSpinner.setValueFactory(durationValueFactory);

        // Initialisation des ComboBox
        departmentComboBox.getItems().addAll(
                "Cardiologie", "Neurologie", "Pédiatrie", "Chirurgie", "Oncologie",
                "Psychiatrie", "Urgences", "Soins intensifs", "Maternité"
        );

        doctorComboBox.getItems().addAll(
                "Dr. Martin", "Dr. Dupont", "Dr. Bernard", "Dr. Petit", "Dr. Dubois"
        );

        floorComboBox.getItems().addAll(1, 2, 3, 4, 5);

        // Écouteurs de sélection
        patientSearchResultsTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        selectedPatient = newSelection;
                        selectedPatientLabel.setText(selectedPatient.toString());
                    }
                }
        );

        availableRoomsTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        selectedRoom = newSelection;
                        selectedRoomLabel.setText(selectedRoom.toString());
                    }
                }
        );

        hospitalizationsTableView.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        selectedHospitalization = newSelection;
                    }
                }
        );

        // Chargement des données de test
        loadSampleData();

        // Initialisation des filtres
        hospitalizationStatusFilterComboBox.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> {
                    if (newVal != null) {
                        filterHospitalizations();
                    }
                }
        );
    }

    @Override
    public void setUserInfo(String username, String role) {
        this.currentUsername = username;
        this.currentRole = role;
        userInfoLabel.setText(username + " (" + role + ")");
    }

    @FXML
    private void handlePatientSearch(ActionEvent event) {
        String searchText = patientSearchField.getText().toLowerCase();
        ObservableList<Patient> filteredPatients = FXCollections.observableArrayList();

        for (Patient patient : allPatients) {
            if (patient.getLastName().toLowerCase().contains(searchText) ||
                    patient.getFirstName().toLowerCase().contains(searchText) ||
                    patient.getId().toString().contains(searchText)) {

                filteredPatients.add(patient);
            }
        }

        patientSearchResultsTable.setItems(filteredPatients);
    }

    @FXML
    private void handleRoomSearch(ActionEvent event) {
        String roomType = roomTypeComboBox.getValue();
        Integer floor = floorComboBox.getValue();

        ObservableList<Room> filteredRooms = FXCollections.observableArrayList();

        for (Room room : allRooms) {
            boolean matchesType = roomType == null || room.getType().equals(roomType);
            boolean matchesFloor = floor == null || room.getFloor().equals(floor);

            if (matchesType && matchesFloor && room.getAvailableBeds() > 0) {
                filteredRooms.add(room);
            }
        }

        availableRoomsTable.setItems(filteredRooms);
    }

    @FXML
    private void handleHospitalizationSearch(ActionEvent event) {
        filterHospitalizations();
    }

    @FXML
    private void handleSaveHospitalization(ActionEvent event) {
        // Validation des champs
        if (selectedPatient == null || selectedRoom == null ||
                startDatePicker.getValue() == null ||
                departmentComboBox.getValue() == null ||
                doctorComboBox.getValue() == null ||
                diseaseDescriptionTextArea.getText().trim().isEmpty()) {

            showAlert(Alert.AlertType.ERROR, "Erreur de saisie",
                    "Veuillez remplir tous les champs obligatoires et sélectionner un patient et une chambre.");
            return;
        }

        // Création d'une nouvelle hospitalisation
        int newId = allHospitalizations.size() + 1;
        Hospitalization newHospitalization = new Hospitalization(
                newId,
                selectedPatient,
                startDatePicker.getValue(),
                durationSpinner.getValue(),
                selectedRoom,
                doctorComboBox.getValue(),
                "Planifiée",
                diseaseDescriptionTextArea.getText()
        );

        allHospitalizations.add(newHospitalization);

        // Mise à jour de la disponibilité de la chambre
        for (Room room : allRooms) {
            if (room.getNumber().equals(selectedRoom.getNumber())) {
                // Dans une application réelle, vous mettriez à jour la base de données ici
                break;
            }
        }

        // Rafraîchir la table et réinitialiser le formulaire
        filterHospitalizations();
        clearForm();

        showAlert(Alert.AlertType.INFORMATION, "Succès",
                "L'hospitalisation a été enregistrée avec succès.");
    }

    @FXML
    private void handleClearForm(ActionEvent event) {
        clearForm();
    }

    @FXML
    private void handleViewDetails(ActionEvent event) {
        if (selectedHospitalization != null) {
            showHospitalizationDetails(selectedHospitalization);
        } else {
            showAlert(Alert.AlertType.WARNING, "Aucune sélection",
                    "Veuillez sélectionner une hospitalisation à consulter.");
        }
    }

    @FXML
    private void handleEditHospitalization(ActionEvent event) {
        if (selectedHospitalization != null) {
            // Dans une application réelle, vous chargeriez les détails dans le formulaire
            // et passeriez en mode édition
            showAlert(Alert.AlertType.INFORMATION, "Fonctionnalité non implémentée",
                    "La modification d'hospitalisation n'est pas implémentée dans cette démo.");
        } else {
            showAlert(Alert.AlertType.WARNING, "Aucune sélection",
                    "Veuillez sélectionner une hospitalisation à modifier.");
        }
    }

    @FXML
    private void handleDischargePatient(ActionEvent event) {
        if (selectedHospitalization != null) {
            if (!"En cours".equals(selectedHospitalization.getStatus())) {
                showAlert(Alert.AlertType.WARNING, "Action impossible",
                        "Seules les hospitalisations en cours peuvent être terminées.");
                return;
            }

            // Confirmation
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de sortie");
            alert.setHeaderText("Libérer le patient");
            alert.setContentText("Êtes-vous sûr de vouloir libérer ce patient et terminer l'hospitalisation?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Dans une application réelle, vous mettriez à jour le statut dans la base de données
                // et libéreriez la chambre

                // Redirection vers la vue d'ordonnance
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hsp/views/prescription.fxml"));
                    Parent root = loader.load();

                    PrescriptionController controller = loader.getController();
                    controller.setUserInfo(currentUsername, currentRole);
                    controller.setPatientInfo(selectedHospitalization.getPatient());

                    Stage stage = (Stage) userInfoLabel.getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setTitle("HSP - Rédaction d'ordonnance");
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                    showAlert(Alert.AlertType.ERROR, "Erreur",
                            "Impossible de charger la vue d'ordonnance.");
                }
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Aucune sélection",
                    "Veuillez sélectionner une hospitalisation.");
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
        // Chargement des patients de test
        allPatients.add(new Patient(1, "Dupont", "Jean", LocalDate.of(1975, 5, 15), "M"));
        allPatients.add(new Patient(2, "Martin", "Sophie", LocalDate.of(1982, 9, 23), "F"));
        allPatients.add(new Patient(3, "Bernard", "Michel", LocalDate.of(1968, 3, 10), "M"));
        allPatients.add(new Patient(4, "Petit", "Marie", LocalDate.of(1990, 11, 5), "F"));
        allPatients.add(new Patient(5, "Dubois", "Pierre", LocalDate.of(1955, 7, 30), "M"));

        // Chargement des chambres de test
        allRooms.add(new Room("101", "Standard", 1, 2, "TV, Salle de bain"));
        allRooms.add(new Room("102", "Standard", 1, 0, "TV, Salle de bain"));
        allRooms.add(new Room("201", "Semi-privée", 2, 1, "TV, Salle de bain, Climatisation"));
        allRooms.add(new Room("202", "Privée", 2, 1, "TV, Salle de bain, Climatisation, Mini-frigo"));
        allRooms.add(new Room("301", "Soins intensifs", 3, 1, "Équipement médical spécialisé"));

        // Chargement des hospitalisations de test
        allHospitalizations.add(new Hospitalization(
                1, allPatients.get(0), LocalDate.now().minusDays(5), 10,
                allRooms.get(0), "Dr. Martin", "En cours", "Pneumonie"
        ));

        allHospitalizations.add(new Hospitalization(
                2, allPatients.get(1), LocalDate.now().minusDays(2), 3,
                allRooms.get(2), "Dr. Dupont", "En cours", "Appendicite"
        ));

        allHospitalizations.add(new Hospitalization(
                3, allPatients.get(2), LocalDate.now().plusDays(2), 7,
                allRooms.get(3), "Dr. Bernard", "Planifiée", "Chirurgie du genou"
        ));

        // Initialisation des tables
        patientSearchResultsTable.setItems(allPatients);
        availableRoomsTable.setItems(allRooms);
        hospitalizationsTableView.setItems(allHospitalizations);
    }

    private void filterHospitalizations() {
        String searchText = hospitalizationSearchField.getText().toLowerCase();
        String statusFilter = hospitalizationStatusFilterComboBox.getValue();

        ObservableList<Hospitalization> filteredList = FXCollections.observableArrayList();

        for (Hospitalization hosp : allHospitalizations) {
            boolean matchesSearch = searchText.isEmpty() ||
                    hosp.getPatient().getLastName().toLowerCase().contains(searchText) ||
                    hosp.getPatient().getFirstName().toLowerCase().contains(searchText) ||
                    hosp.getDoctor().toLowerCase().contains(searchText);

            boolean matchesStatus = statusFilter == null ||
                    statusFilter.equals("Tous") ||
                    hosp.getStatus().equals(statusFilter);

            if (matchesSearch && matchesStatus) {
                filteredList.add(hosp);
            }
        }

        hospitalizationsTableView.setItems(filteredList);
    }

    private void clearForm() {
        selectedPatient = null;
        selectedPatientLabel.setText("Aucun patient sélectionné");
        patientSearchField.clear();
        patientSearchResultsTable.getSelectionModel().clearSelection();

        startDatePicker.setValue(LocalDate.now());
        durationSpinner.getValueFactory().setValue(1);
        departmentComboBox.getSelectionModel().clearSelection();
        doctorComboBox.getSelectionModel().clearSelection();
        diseaseDescriptionTextArea.clear();
        initialTreatmentTextArea.clear();

        selectedRoom = null;
        selectedRoomLabel.setText("Aucune chambre sélectionnée");
        roomTypeComboBox.getSelectionModel().clearSelection();
        floorComboBox.getSelectionModel().clearSelection();
        availableRoomsTable.getSelectionModel().clearSelection();
    }

    private void showHospitalizationDetails(Hospitalization hospitalization) {
        // Dans une application réelle, vous afficheriez une fenêtre modale avec tous les détails
        StringBuilder details = new StringBuilder();
        details.append("ID: ").append(hospitalization.getId()).append("\n");
        details.append("Patient: ").append(hospitalization.getPatient().getLastName())
                .append(" ").append(hospitalization.getPatient().getFirstName()).append("\n");
        details.append("Date de début: ").append(hospitalization.getStartDate().format(
                DateTimeFormatter.ofPattern("dd/MM/yyyy"))).append("\n");
        details.append("Durée prévue: ").append(hospitalization.getDuration()).append(" jours\n");
        details.append("Chambre: ").append(hospitalization.getRoom().getNumber()).append("\n");
        details.append("Médecin: ").append(hospitalization.getDoctor()).append("\n");
        details.append("Statut: ").append(hospitalization.getStatus()).append("\n");
        details.append("Description: ").append(hospitalization.getDiseaseDescription());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Détails de l'hospitalisation");
        alert.setHeaderText("Hospitalisation #" + hospitalization.getId());
        alert.setContentText(details.toString());
        alert.showAndWait();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}