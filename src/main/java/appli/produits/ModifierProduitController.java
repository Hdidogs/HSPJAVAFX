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
    private TextField libelleField, descriptionField, niveauDangerositeField, quantiteField, refFournisseursField;

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
        niveauDangerositeField.setText(String.valueOf(produit.getNiveauDangerosite()));
        quantiteField.setText(String.valueOf(produit.getQuantite()));
        refFournisseursField.setText(String.valueOf(produit.getRefFournisseurs()));
    }

    @FXML
    protected void onModifierProduitClick() throws SQLException, IOException {
        if (libelleField.getText().isEmpty() || descriptionField.getText().isEmpty() ||
                niveauDangerositeField.getText().isEmpty() || quantiteField.getText().isEmpty() ||
                refFournisseursField.getText().isEmpty()) {
            erreurText.setText("Tous les champs doivent être remplis !");
        } else {
            produit.setLibelle(libelleField.getText());
            produit.setDescription(descriptionField.getText());
            produit.setNiveauDangerosite(Integer.parseInt(niveauDangerositeField.getText()));
            produit.setQuantite(Integer.parseInt(quantiteField.getText()));
            produit.setRefFournisseurs(Integer.parseInt(refFournisseursField.getText()));

            if (ProduitsRepository.updateProduit(produit)) {
                StartApplication.changeScene("produits/produitsview.fxml");
            } else {
                erreurText.setText("Erreur lors de la modification du produit.");
            }
        }
    }

    @FXML
    protected void onRetourClick() throws IOException {
        StartApplication.changeScene("produits/produitsview.fxml");
    }
}
