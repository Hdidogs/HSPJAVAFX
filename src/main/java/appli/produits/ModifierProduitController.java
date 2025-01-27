package appli.produits;

import appli.StartApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Produits;
import repository.ProduitsRepository;

import java.io.IOException;
import java.sql.SQLException;

public class ModifierProduitController {
    @FXML
    private TextField libelleField, descriptionField, dangerositeField, qtnField, fournisseurField;

    @FXML
    private Label erreurText;

    private Produits produit;

    public ModifierProduitController(Produits produit) {
        this.produit = produit;
    }

    @FXML
    public void initialize() {
        libelleField.setText(produit.getLibelle());
        descriptionField.setText(produit.getDescription());
        dangerositeField.setText(String.valueOf(produit.getDangerosite()));
        qtnField.setText(String.valueOf(produit.getQuantite()));
        fournisseurField.setText(String.valueOf(produit.getFournisseurId()));
    }

    @FXML
    protected void onModifierProduitClick() throws SQLException {
        if (libelleField.getText().isEmpty() || descriptionField.getText().isEmpty() ||
                dangerositeField.getText().isEmpty() || qtnField.getText().isEmpty() ||
                fournisseurField.getText().isEmpty()) {
            erreurText.setText("Tous les champs doivent être remplis !");
        } else {
            produit.setLibelle(libelleField.getText());
            produit.setDescription(descriptionField.getText());
            produit.setDangerosite(Integer.parseInt(dangerositeField.getText()));
            produit.setQuantite(Integer.parseInt(qtnField.getText()));
            produit.setFournisseurId(Integer.parseInt(fournisseurField.getText()));

            if (ProduitsRepository.updateProduit(produit)) {
                StartApplication.changeScene("stocks/stocksview.fxml");
            } else {
                erreurText.setText("Erreur lors de la modification du produit.");
            }
        }
    }

    @FXML
    protected void onRetourClick() throws IOException {
        StartApplication.changeScene("stocks/stocksview.fxml");
    }
}

