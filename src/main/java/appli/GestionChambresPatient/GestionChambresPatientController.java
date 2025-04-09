package appli.GestionChambresPatient;

import appli.StartApplication;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Chambres;
import model.Patients;
import repository.ChambresRepository;
import repository.PatientsRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class GestionChambresPatientController {

    // FXML - Patients
    @FXML private TextField patientSearchField;
    @FXML private TableView<Patients> unassignedPatientsTable;
    @FXML private TableColumn<Patients, Integer> unassignedIdColumn;
    @FXML private TableColumn<Patients, String> unassignedNameColumn;
    @FXML private TableColumn<Patients, Void> selectPatientColumn;

    // FXML - Chambres
    @FXML private ComboBox<String> roomTypeFilter;
    @FXML private TableView<Chambres> availableRoomsTable;
    @FXML private TableColumn<Chambres, String> roomNumberColumn;
    @FXML private TableColumn<Chambres, String> roomTypeColumn;
    @FXML private TableColumn<Chambres, Integer> availableBedsColumn;
    @FXML private TableColumn<Chambres, Void> selectRoomColumn;

    // FXML - Sélections
    @FXML private Label selectedPatientLabel;
    @FXML private Label selectedRoomLabel;

    // FXML - Patients affectés (non encore utilisé ici)
    @FXML private TableView<?> assignedPatientsTable;

    // Bouton de validation
    private BooleanProperty assignButtonDisabled = new SimpleBooleanProperty(true);
    @FXML public BooleanProperty assignButtonDisabledProperty() { return assignButtonDisabled; }

    // Objets sélectionnés
    private Patients selectedPatient;
    private Chambres selectedRoom;

    @FXML
    public void initialize() {
        System.out.println("GestionChambresPatientController initialisé.");

        // Colonnes patients
        unassignedIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        unassignedNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrenom() + " " + cellData.getValue().getNom()));

        // Colonnes chambres
        roomNumberColumn.setCellValueFactory(new PropertyValueFactory<>("num"));
        roomTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        availableBedsColumn.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getCapacite() - cellData.getValue().getLitOccupe()).asObject());

        // ComboBox types
        roomTypeFilter.setItems(FXCollections.observableArrayList("Simple", "Double", "Suite"));

        // Sélection patient
        unassignedPatientsTable.setOnMouseClicked(event -> {
            selectedPatient = unassignedPatientsTable.getSelectionModel().getSelectedItem();
            if (selectedPatient != null) {
                selectedPatientLabel.setText(selectedPatient.getPrenom() + " " + selectedPatient.getNom());
                checkAssignReady();
            }
        });

        // Sélection chambre
        availableRoomsTable.setOnMouseClicked(event -> {
            selectedRoom = availableRoomsTable.getSelectionModel().getSelectedItem();
            if (selectedRoom != null) {
                selectedRoomLabel.setText("Chambre " + selectedRoom.getNum());
                checkAssignReady();
            }
        });

        // Chargement initial
        loadPatients();
    }

    private void loadPatients() {
        try {
            unassignedPatientsTable.setItems(FXCollections.observableArrayList(PatientsRepository.getAllPatients()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadRoomsByType(String type) {
        try {
            List<Chambres> chambres = ChambresRepository.searchChambres("", type, "Disponible");
            availableRoomsTable.setItems(FXCollections.observableArrayList(chambres));
            System.out.println("Chambres trouvées : " + chambres.size());
            for (Chambres c : chambres) {
                System.out.println(" - " + c.getNum() + " | " + c.getType() + " | " + c.getStatus());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void checkAssignReady() {
        assignButtonDisabled.set(selectedPatient == null || selectedRoom == null);
    }

    @FXML
    private void filterAvailableRooms() {
        String selectedType = roomTypeFilter.getValue();
        if (selectedType != null) {
            loadRoomsByType(selectedType);
        }
    }

    @FXML
    private void assignPatientToRoom() {
        System.out.println("Affectation du patient à la chambre sélectionnée.");

        if (selectedPatient == null || selectedRoom == null) {
            System.out.println("Aucune sélection.");
            return;
        }

        try {
            // Exemple : suppose que tu as une méthode d'affectation dans un repository HospitalisationRepository
            boolean success = ChambresRepository.assignPatientToRoom(selectedPatient.getId(), selectedRoom.getId());

            if (success) {
                // Mise à jour des lits occupés + rechargement
                System.out.println("Affectation réussie !");
                loadPatients();
                filterAvailableRooms();
                selectedPatient = null;
                selectedRoom = null;
                selectedPatientLabel.setText("Aucun patient sélectionné");
                selectedRoomLabel.setText("Aucune chambre sélectionnée");
                assignButtonDisabled.set(true);
            } else {
                System.out.println("Échec de l'affectation.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Ajout de la méthode backButton pour gérer l'action du bouton "Retour"
    @FXML
    private void backButton() throws IOException {
        StartApplication.changeScene("dashboard/dashboard.fxml");
    }
}
