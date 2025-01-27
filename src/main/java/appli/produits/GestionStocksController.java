package appli.produits;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Produits;
import model.Fournisseurs;
import repository.ProduitsRepository;
import repository.FournisseursRepository;

import java.sql.SQLException;
import java.util.List;

public class GestionStocksController {

    @FXML
    private TextField searchField;

    @FXML
    private TableView<Produits> produitTable;

    @FXML
    private TableColumn<Produits, String> libelleColumn;

    @FXML
    private TableColumn<Produits, String> descriptionColumn;

    @FXML
    private TableColumn<Produits, Integer> niveauDangerositeColumn;

    @FXML
    private TableColumn<Produits, Integer> quantiteColumn;

    @FXML
    private TableColumn<Produits, String> fournisseurColumn;

    @FXML
    private TextField libelleField;

    @FXML
    private TextField descriptionField;

    @FXML
    private ComboBox<Integer> niveauDangerositeComboBox;

    @FXML
    private TextField quantiteField;

    @FXML
    private ComboBox<Fournisseurs> fournisseurComboBox;

    private Produits selectedProduit;

    @FXML
    public void initialize() {
        try {
            // Initialisation des colonnes de la table
            libelleColumn.setCellValueFactory(new PropertyValueFactory<>("libelle"));
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
            niveauDangerositeColumn.setCellValueFactory(new PropertyValueFactory<>("niveauDangerosite"));
            quantiteColumn.setCellValueFactory(new PropertyValueFactory<>("quantite"));
            fournisseurColumn.setCellValueFactory(new PropertyValueFactory<>("refFournisseurs"));

            // Charger les données initiales
            loadTableData();
            loadFournisseurComboBox();
            loadNiveauDangerositeOptions();

            // Gestion de la sélection dans la table
            produitTable.setOnMouseClicked(this::handleTableClick);
        } catch (Exception e) {
            showAlert("Erreur", "Erreur lors de l'initialisation : " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Charge les données des produits dans la table.
     */
    private void loadTableData() {
        try {
            List<Produits> produitsList = ProduitsRepository.getAllProduits();
            ObservableList<Produits> observableList = FXCollections.observableArrayList(produitsList);
            produitTable.setItems(observableList);
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors du chargement des produits : " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Charge les fournisseurs dans le ComboBox.
     */
    private void loadFournisseurComboBox() {
        try {
            List<Fournisseurs> fournisseurs = FournisseursRepository.getAllFournisseurs();
            ObservableList<Fournisseurs> fournisseurOptions = FXCollections.observableArrayList(fournisseurs);
            fournisseurComboBox.setItems(fournisseurOptions);
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors du chargement des fournisseurs : " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Charge les options de niveau de dangerosité.
     */
    private void loadNiveauDangerositeOptions() {
        ObservableList<Integer> dangerositeOptions = FXCollections.observableArrayList(1, 2, 3, 4, 5);
        niveauDangerositeComboBox.setItems(dangerositeOptions);
    }

    /**
     * Gère la recherche d'un produit.
     */
    @FXML
    private void handleSearch() {
        try {
            String searchQuery = searchField.getText().trim();
            List<Produits> filteredProduits = ProduitsRepository.searchProduits(searchQuery);
            produitTable.setItems(FXCollections.observableArrayList(filteredProduits));
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors de la recherche : " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Gère l'ajout d'un produit.
     */
    @FXML
    private void handleAdd() {
        if (isFormValid()) {
            Produits newProduit = new Produits(
                    libelleField.getText(),
                    descriptionField.getText(),
                    niveauDangerositeComboBox.getValue(),
                    Integer.parseInt(quantiteField.getText()),
                    fournisseurComboBox.getValue().getId()
            );

            try {
                if (ProduitsRepository.addProduit(newProduit)) {
                    loadTableData();
                    clearForm();
                    showAlert("Succès", "Produit ajouté avec succès.", Alert.AlertType.INFORMATION);
                } else {
                    showAlert("Erreur", "Erreur lors de l'ajout du produit.", Alert.AlertType.ERROR);
                }
            } catch (SQLException e) {
                showAlert("Erreur", "Erreur lors de l'ajout : " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    /**
     * Gère la modification d'un produit.
     */
    @FXML
    private void handleUpdate() {
        if (selectedProduit != null && isFormValid()) {
            selectedProduit.setLibelle(libelleField.getText());
            selectedProduit.setDescription(descriptionField.getText());
            selectedProduit.setNiveauDangerosite(niveauDangerositeComboBox.getValue());
            selectedProduit.setQuantite(Integer.parseInt(quantiteField.getText()));
            selectedProduit.setRefFournisseurs(fournisseurComboBox.getValue().getId());

            try {
                if (ProduitsRepository.updateProduit(selectedProduit)) {
                    loadTableData();
                    clearForm();
                    showAlert("Succès", "Produit modifié avec succès.", Alert.AlertType.INFORMATION);
                } else {
                    showAlert("Erreur", "Erreur lors de la modification du produit.", Alert.AlertType.ERROR);
                }
            } catch (SQLException e) {
                showAlert("Erreur", "Erreur lors de la modification : " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    /**
     * Gère la sélection dans la table.
     */
    private void handleTableClick(MouseEvent event) {
        try {
            selectedProduit = produitTable.getSelectionModel().getSelectedItem();
            if (selectedProduit != null) {
                libelleField.setText(selectedProduit.getLibelle());
                descriptionField.setText(selectedProduit.getDescription());
                niveauDangerositeComboBox.setValue(selectedProduit.getNiveauDangerosite());
                quantiteField.setText(String.valueOf(selectedProduit.getQuantite()));
                fournisseurComboBox.setValue(FournisseursRepository.getFournisseurById(selectedProduit.getRefFournisseurs()));
            }
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors de la sélection : " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Valide les champs du formulaire.
     */
    private boolean isFormValid() {
        if (libelleField.getText().isEmpty() || descriptionField.getText().isEmpty() ||
                niveauDangerositeComboBox.getValue() == null || quantiteField.getText().isEmpty() ||
                fournisseurComboBox.getValue() == null) {
            showAlert("Erreur", "Veuillez remplir tous les champs obligatoires.", Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }

    /**
     * Efface le formulaire.
     */
    private void clearForm() {
        libelleField.clear();
        descriptionField.clear();
        niveauDangerositeComboBox.setValue(null);
        quantiteField.clear();
        fournisseurComboBox.setValue(null);
        selectedProduit = null;
    }

    /**
     * Affiche une alerte.
     */
    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
