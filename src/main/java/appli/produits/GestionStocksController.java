package appli.produits;

import appli.StartApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Box;
import model.Produits;
import model.Fournisseurs;
import repository.ProduitsRepository;
import repository.FournisseursRepository;

import java.io.IOException;
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
    private ComboBox<String> niveauDangerositeComboBox;

    @FXML
    private TextField quantiteField;

    @FXML
    private ComboBox<Fournisseurs> fournisseurComboBox;

    @FXML
    private Button ajoutProduitButton;

    @FXML
    private Button modificationProduitButton;

    @FXML
    private VBox box;

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
            box.setOnMouseClicked(this::boxTableClick);

            modificationProduitButton.setDisable(true);
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
        ObservableList<String> dangerositeOptions = FXCollections.observableArrayList("1", "2", "3", "4", "5");
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

    @FXML
    private void backButton() throws IOException {
        StartApplication.changeScene("dashboard/dashboard.fxml");
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
                    Integer.parseInt(niveauDangerositeComboBox.getValue()),
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
            selectedProduit.setNiveauDangerosite(Integer.parseInt(niveauDangerositeComboBox.getValue()));
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
                niveauDangerositeComboBox.setValue(String.valueOf(selectedProduit.getNiveauDangerosite()));
                quantiteField.setText(String.valueOf(selectedProduit.getQuantite()));
                fournisseurComboBox.setValue(FournisseursRepository.getFournisseurById(selectedProduit.getRefFournisseurs()));

                ajoutProduitButton.setDisable(true);
                modificationProduitButton.setDisable(false);
            }
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors de la sélection : " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void boxTableClick(MouseEvent event) {
        clearForm();

        ajoutProduitButton.setDisable(false);
        modificationProduitButton.setDisable(true);
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
        niveauDangerositeComboBox.setPromptText("Niveau de dangerosité");
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
