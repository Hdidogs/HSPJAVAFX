package com.hsp.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ProductRequestController implements BaseController {

    // Modèle de données pour les demandes de produits
    public static class ProductRequest {
        private final Integer id;
        private final LocalDate date;
        private final String doctor;
        private final String product;
        private final Integer quantity;
        private final String urgency;
        private String status;
        private final String justification;

        public ProductRequest(Integer id, LocalDate date, String doctor, String product,
                              Integer quantity, String urgency, String status, String justification) {
            this.id = id;
            this.date = date;
            this.doctor = doctor;
            this.product = product;
            this.quantity = quantity;
            this.urgency = urgency;
            this.status = status;
            this.justification = justification;
        }

        public Integer getId() { return id; }
        public LocalDate getDate() { return date; }
        public String getDoctor() { return doctor; }
        public String getProduct() { return product; }
        public Integer getQuantity() { return quantity; }
        public String getUrgency() { return urgency; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public String getJustification() { return justification; }
    }

    @FXML
    private Label userInfoLabel;

    @FXML
    private TableView<ProductRequest> requestsTableView;

    @FXML
    private TableColumn<ProductRequest, Integer> idColumn;

    @FXML
    private TableColumn<ProductRequest, String> dateColumn;

    @FXML
    private TableColumn<ProductRequest, String> doctorColumn;

    @FXML
    private TableColumn<ProductRequest, String> productColumn;

    @FXML
    private TableColumn<ProductRequest, Integer> quantityColumn;

    @FXML
    private TableColumn<ProductRequest, String> urgencyColumn;

    @FXML
    private TableColumn<ProductRequest, String> statusColumn;

    @FXML
    private ComboBox<String> statusFilterComboBox;

    @FXML
    private TextField searchField;

    @FXML
    private ComboBox<String> productComboBox;

    @FXML
    private Spinner<Integer> quantitySpinner;

    @FXML
    private ComboBox<String> urgencyComboBox;

    @FXML
    private TextArea justificationTextArea;

    @FXML
    private Button approveButton;

    @FXML
    private Button rejectButton;

    @FXML
    private Button newRequestButton;

    @FXML
    private Button saveRequestButton;

    private ObservableList<ProductRequest> allRequests = FXCollections.observableArrayList();
    private ObservableList<String> products = FXCollections.observableArrayList();

    private String currentUsername;
    private String currentRole;
    private ProductRequest selectedRequest;
    private boolean isEditMode = false;

    @FXML
    public void initialize() {
        // Initialisation des colonnes de la table
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        dateColumn.setCellValueFactory(cellData -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return new SimpleStringProperty(cellData.getValue().getDate().format(formatter));
        });

        doctorColumn.setCellValueFactory(new PropertyValueFactory<>("doctor"));
        productColumn.setCellValueFactory(new PropertyValueFactory<>("product"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        urgencyColumn.setCellValueFactory(new PropertyValueFactory<>("urgency"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Initialisation des données de test
        loadSampleData();

        // Initialisation des produits
        products.addAll("Paracétamol", "Ibuprofène", "Amoxicilline", "Oméprazole",
                "Seringues", "Gants stériles", "Compresses", "Désinfectant");
        productComboBox.setItems(products);

        // Configuration du spinner de quantité
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        quantitySpinner.setValueFactory(valueFactory);

        // Écouteur de sélection pour la table
        requestsTableView.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        selectedRequest = newSelection;
                        populateFormWithSelectedRequest();

                        // Activer/désactiver les boutons selon le rôle et le statut
                        boolean isProductManager = "Gestionnaire de produits".equals(currentRole);
                        boolean isPending = "En attente".equals(selectedRequest.getStatus());

                        approveButton.setDisable(!(isProductManager && isPending));
                        rejectButton.setDisable(!(isProductManager && isPending));
                    }
                }
        );

        // Initialiser le filtre de statut
        statusFilterComboBox.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> {
                    if (newVal != null) {
                        filterRequests();
                    }
                }
        );

        // Désactiver les boutons d'approbation/rejet par défaut
        approveButton.setDisable(true);
        rejectButton.setDisable(true);
    }

    @Override
    public void setUserInfo(String username, String role) {
        this.currentUsername = username;
        this.currentRole = role;
        userInfoLabel.setText(username + " (" + role + ")");

        // Ajuster l'interface selon le rôle
        boolean isDoctor = "Médecin".equals(role);
        boolean isProductManager = "Gestionnaire de produits".equals(role);

        newRequestButton.setVisible(isDoctor);
        saveRequestButton.setVisible(isDoctor);

        // Désactiver l'édition des champs pour les gestionnaires
        if (isProductManager) {
            productComboBox.setDisable(true);
            quantitySpinner.setDisable(true);
            urgencyComboBox.setDisable(true);
            justificationTextArea.setDisable(true);
        }
    }

    @FXML
    private void handleSearch(ActionEvent event) {
        filterRequests();
    }

    @FXML
    private void handleNewRequest(ActionEvent event) {
        // Réinitialiser le formulaire pour une nouvelle demande
        clearForm();
        isEditMode = false;
        saveRequestButton.setText("Enregistrer");
    }

    @FXML
    private void handleSaveRequest(ActionEvent event) {
        // Validation des champs
        if (productComboBox.getValue() == null ||
                urgencyComboBox.getValue() == null ||
                justificationTextArea.getText().trim().isEmpty()) {

            showAlert(Alert.AlertType.ERROR, "Erreur de saisie",
                    "Veuillez remplir tous les champs obligatoires.");
            return;
        }

        if (isEditMode && selectedRequest != null) {
            // Mise à jour d'une demande existante
            selectedRequest.setStatus("En attente"); // Réinitialiser le statut après modification

            // Mettre à jour la demande dans la liste
            int index = allRequests.indexOf(selectedRequest);
            allRequests.set(index, selectedRequest);
        } else {
            // Création d'une nouvelle demande
            int newId = allRequests.size() + 1;
            ProductRequest newRequest = new ProductRequest(
                    newId,
                    LocalDate.now(),
                    currentUsername,
                    productComboBox.getValue(),
                    quantitySpinner.getValue(),
                    urgencyComboBox.getValue(),
                    "En attente",
                    justificationTextArea.getText()
            );

            allRequests.add(newRequest);
        }

        // Rafraîchir la table et réinitialiser le formulaire
        filterRequests();
        clearForm();
        isEditMode = false;

        showAlert(Alert.AlertType.INFORMATION, "Succès",
                "La demande a été enregistrée avec succès.");
    }

    @FXML
    private void handleApprove(ActionEvent event) {
        if (selectedRequest != null) {
            selectedRequest.setStatus("Approuvé");
            requestsTableView.refresh();

            // Ici, vous pourriez implémenter la logique pour notifier le médecin
            // ou mettre à jour l'inventaire des produits

            showAlert(Alert.AlertType.INFORMATION, "Demande approuvée",
                    "La demande a été approuvée avec succès.");

            // Désactiver les boutons après approbation
            approveButton.setDisable(true);
            rejectButton.setDisable(true);
        }
    }

    @FXML
    private void handleReject(ActionEvent event) {
        if (selectedRequest != null) {
            // Optionnel: demander une raison de rejet
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Motif de rejet");
            dialog.setHeaderText("Veuillez indiquer le motif du rejet");
            dialog.setContentText("Motif:");

            dialog.showAndWait().ifPresent(reason -> {
                selectedRequest.setStatus("Refusé");
                requestsTableView.refresh();

                showAlert(Alert.AlertType.INFORMATION, "Demande refusée",
                        "La demande a été refusée avec succès.");

                // Désactiver les boutons après rejet
                approveButton.setDisable(true);
                rejectButton.setDisable(true);
            });
        }
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        try {
            // Redirection vers l'écran de connexion
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                    getClass().getResource("/com/hsp/views/personnel_login.fxml"));
            javafx.scene.Parent root = loader.load();

            javafx.stage.Stage stage = (javafx.stage.Stage) userInfoLabel.getScene().getWindow();
            javafx.scene.Scene scene = new javafx.scene.Scene(root);
            stage.setScene(scene);
            stage.setTitle("HSP - Connexion Personnel");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadSampleData() {
        // Données de test pour la démonstration
        allRequests.add(new ProductRequest(1, LocalDate.now().minusDays(2), "Dr. Martin",
                "Paracétamol", 50, "Moyenne", "Approuvé",
                "Stock faible en pharmacie"));

        allRequests.add(new ProductRequest(2, LocalDate.now().minusDays(1), "Dr. Dupont",
                "Seringues", 100, "Haute", "En attente",
                "Besoin urgent pour le service de réanimation"));

        allRequests.add(new ProductRequest(3, LocalDate.now(), "Dr. Bernard",
                "Gants stériles", 200, "Basse", "Refusé",
                "Renouvellement du stock standard"));

        requestsTableView.setItems(allRequests);
    }

    private void filterRequests() {
        ObservableList<ProductRequest> filteredList = FXCollections.observableArrayList();
        String searchText = searchField.getText().toLowerCase();
        String statusFilter = statusFilterComboBox.getValue();

        for (ProductRequest request : allRequests) {
            boolean matchesSearch = searchText.isEmpty() ||
                    request.getProduct().toLowerCase().contains(searchText) ||
                    request.getDoctor().toLowerCase().contains(searchText);

            boolean matchesStatus = statusFilter == null ||
                    statusFilter.equals("Tous") ||
                    request.getStatus().equals(statusFilter);

            if (matchesSearch && matchesStatus) {
                filteredList.add(request);
            }
        }

        requestsTableView.setItems(filteredList);
    }

    private void populateFormWithSelectedRequest() {
        if (selectedRequest != null) {
            productComboBox.setValue(selectedRequest.getProduct());
            quantitySpinner.getValueFactory().setValue(selectedRequest.getQuantity());
            urgencyComboBox.setValue(selectedRequest.getUrgency());
            justificationTextArea.setText(selectedRequest.getJustification());

            isEditMode = true;
            saveRequestButton.setText("Mettre à jour");
        }
    }

    private void clearForm() {
        productComboBox.getSelectionModel().clearSelection();
        quantitySpinner.getValueFactory().setValue(1);
        urgencyComboBox.getSelectionModel().clearSelection();
        justificationTextArea.clear();
        selectedRequest = null;
        saveRequestButton.setText("Enregistrer");
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}