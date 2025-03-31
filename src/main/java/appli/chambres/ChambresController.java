package appli.chambres;

import appli.StartApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Chambres;
import repository.ChambresRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ChambresController {

    @FXML
    private ComboBox<String> statusFilterComboBox;
    @FXML
    private TextField chambreNumberField;
    @FXML
    private ComboBox<String> roomTypeComboBox;
    @FXML
    private Spinner<Integer> capacitySpinner;
    @FXML
    private TextField searchField;
    @FXML
    private TableView<Chambres> chambreTableView;
    @FXML
    private TableColumn<Chambres, String> chambreNumberColumn;
    @FXML
    private TableColumn<Chambres, String> chambreTypeColumn;
    @FXML
    private TableColumn<Chambres, Integer> capaciteColumn;
    @FXML
    private TableColumn<Chambres, String> statusColumn;
    @FXML
    private TableColumn<Chambres, Integer> litoccuperColumn;

    private Chambres selectedChambre;

    @FXML
    private void backButton() throws IOException {
        StartApplication.changeScene("dashboard/dashboard.fxml");
    }

    @FXML
    public void initialize() {
        chambreNumberColumn.setCellValueFactory(new PropertyValueFactory<>("num"));
        chambreTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        capaciteColumn.setCellValueFactory(new PropertyValueFactory<>("capacite"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        litoccuperColumn.setCellValueFactory(new PropertyValueFactory<>("litOccupe"));

        loadTableData();

        roomTypeComboBox.setItems(FXCollections.observableArrayList("Simple", "Double", "Suite"));

        statusFilterComboBox.setItems(FXCollections.observableArrayList("Disponible", "Occupée", "Réservée"));
        statusFilterComboBox.getSelectionModel().selectFirst();

        capacitySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1));

        chambreTableView.setOnMouseClicked(this::handleTableClick);
    }

    private void loadTableData() {
        try {
            List<Chambres> chambresList = ChambresRepository.getAllChambres();
            ObservableList<Chambres> observableList = FXCollections.observableArrayList(chambresList);
            chambreTableView.setItems(observableList);
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors du chargement des données : " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleSearch() {
        String searchQuery = searchField.getText().trim();
        String type = roomTypeComboBox.getSelectionModel().getSelectedItem();
        String status = statusFilterComboBox.getSelectionModel().getSelectedItem();
        try {
            List<Chambres> filteredChambres = ChambresRepository.searchChambres(searchQuery, type, status);

            chambreTableView.setItems(FXCollections.observableArrayList(filteredChambres));
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors de la recherche : " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleAdd() {
        try {
            if (isFormValid()) {
                String roomType = roomTypeComboBox.getSelectionModel().getSelectedItem();
                int capacite = capacitySpinner.getValue();
                String status = "Disponible";

                Chambres newChambre = new Chambres(
                        chambreNumberField.getText(),
                        roomType,
                        capacite,
                        status,
                        0
                );

                if (ChambresRepository.addChambre(newChambre)) {
                    loadTableData();
                    resetAddForm();
                    showAlert("Succès", "Chambre ajoutée avec succès.", Alert.AlertType.INFORMATION);
                } else {
                    showAlert("Erreur", "Erreur lors de l'ajout de la chambre.", Alert.AlertType.ERROR);
                }
            }
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors de l'ajout : " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleUpdate() {
        if (selectedChambre != null) {
            try {
                selectedChambre.setNum(chambreNumberField.getText());
                selectedChambre.setType(roomTypeComboBox.getSelectionModel().getSelectedItem());
                selectedChambre.setCapacite(capacitySpinner.getValue());
                selectedChambre.setStatus("Réservée");

                if (ChambresRepository.updateChambre(selectedChambre)) {
                    loadTableData();
                    resetAddForm();
                    showAlert("Succès", "Chambre mise à jour avec succès.", Alert.AlertType.INFORMATION);
                } else {
                    showAlert("Erreur", "Erreur lors de la mise à jour de la chambre.", Alert.AlertType.ERROR);
                }
            } catch (SQLException e) {
                showAlert("Erreur", "Erreur lors de la mise à jour : " + e.getMessage(), Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Erreur", "Veuillez sélectionner une chambre à mettre à jour.", Alert.AlertType.WARNING);
        }
    }

    private void handleTableClick(MouseEvent event) {
        selectedChambre = chambreTableView.getSelectionModel().getSelectedItem();
        if (selectedChambre != null) {
            chambreNumberField.setText(selectedChambre.getNum());
            roomTypeComboBox.setValue(selectedChambre.getType());
            capacitySpinner.getValueFactory().setValue(selectedChambre.getCapacite());
        }
    }

    private void resetAddForm() {
        chambreNumberField.clear();
        roomTypeComboBox.getSelectionModel().clearSelection();
        capacitySpinner.getValueFactory().setValue(1);
        selectedChambre = null;
    }

    private boolean isFormValid() {
        if (chambreNumberField.getText().trim().isEmpty()) {
            showAlert("Erreur", "Le numéro de chambre est requis.", Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void resetSearch(ActionEvent actionEvent) {
        searchField.clear();
        loadTableData();
    }
}
