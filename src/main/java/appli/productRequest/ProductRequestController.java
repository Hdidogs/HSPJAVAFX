package com.hsp.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert.AlertType;

public class ProductRequestController {

    // --- Header ---
    @FXML
    private Label userInfoLabel;

    @FXML
    private Button logoutButton;

    @FXML
    private void handleLogout(ActionEvent event) {
        // TODO : Implémenter la déconnexion
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Déconnexion");
        alert.setHeaderText(null);
        alert.setContentText("Vous avez été déconnecté.");
        alert.showAndWait();
    }

    // --- Filtrage et liste des demandes ---
    @FXML
    private ComboBox<String> statusFilterComboBox;

    @FXML
    private TextField searchField;

    @FXML
    private void handleSearch(ActionEvent event) {
        String statusFilter = statusFilterComboBox.getValue();
        String searchTerm = searchField.getText();
        // TODO : Implémenter la logique de recherche des demandes
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Recherche");
        alert.setHeaderText(null);
        alert.setContentText("Recherche effectuée avec le filtre : " + statusFilter + " et le terme : " + searchTerm);
        alert.showAndWait();
    }

    @FXML
    private TableView<?> requestsTableView;

    @FXML
    private TableColumn<?, ?> idColumn;

    @FXML
    private TableColumn<?, ?> dateColumn;

    @FXML
    private TableColumn<?, ?> doctorColumn;

    @FXML
    private TableColumn<?, ?> productColumn;

    @FXML
    private TableColumn<?, ?> quantityColumn;

    @FXML
    private TableColumn<?, ?> urgencyColumn;

    @FXML
    private TableColumn<?, ?> statusColumn;

    // --- Détails de la demande ---
    @FXML
    private ComboBox<String> productComboBox;

    @FXML
    private Spinner<Integer> quantitySpinner;

    @FXML
    private ComboBox<String> urgencyComboBox;

    @FXML
    private TextArea justificationTextArea;

    @FXML
    private Button newRequestButton;

    @FXML
    private Button saveRequestButton;

    @FXML
    private Button approveButton;

    @FXML
    private Button rejectButton;

    @FXML
    private void handleNewRequest(ActionEvent event) {
        // Réinitialiser le formulaire pour une nouvelle demande
        productComboBox.getSelectionModel().clearSelection();
        quantitySpinner.getValueFactory().setValue(1);
        urgencyComboBox.getSelectionModel().clearSelection();
        justificationTextArea.clear();

        // Désactiver les boutons d'approbation et de refus en mode création
        approveButton.setDisable(true);
        rejectButton.setDisable(true);
    }

    @FXML
    private void handleSaveRequest(ActionEvent event) {
        // Récupérer les informations de la demande
        String produit = productComboBox.getValue();
        int quantite = quantitySpinner.getValue();
        String urgence = urgencyComboBox.getValue();
        String justification = justificationTextArea.getText();

        // TODO : Ajouter la logique d'enregistrement de la demande
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Enregistrement");
        alert.setHeaderText(null);
        alert.setContentText("La demande pour le produit " + produit + " a été enregistrée.");
        alert.showAndWait();
    }

    @FXML
    private void handleApprove(ActionEvent event) {
        // TODO : Implémenter l'approbation de la demande sélectionnée
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Approbation");
        alert.setHeaderText(null);
        alert.setContentText("La demande a été approuvée.");
        alert.showAndWait();
    }

    @FXML
    private void handleReject(ActionEvent event) {
        // TODO : Implémenter le refus de la demande sélectionnée
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Refus");
        alert.setHeaderText(null);
        alert.setContentText("La demande a été refusée.");
        alert.showAndWait();
    }

    // --- Initialisation ---
    @FXML
    private void initialize() {
        // Initialiser la ComboBox des statuts
        statusFilterComboBox.setItems(FXCollections.observableArrayList("Tous", "En attente", "Approuvé", "Refusé"));
        statusFilterComboBox.setValue("Tous");

        // Initialiser le Spinner pour la quantité
        SpinnerValueFactory<Integer> quantityValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        quantitySpinner.setValueFactory(quantityValueFactory);

        // Initialiser la ComboBox pour les produits
        productComboBox.setItems(FXCollections.observableArrayList("Produit A", "Produit B", "Produit C"));

        // Initialiser la ComboBox pour le niveau d'urgence
        urgencyComboBox.setItems(FXCollections.observableArrayList("Basse", "Moyenne", "Haute", "Critique"));

        // Par défaut, désactiver les boutons d'approbation et de refus
        approveButton.setDisable(true);
        rejectButton.setDisable(true);
    }
}
