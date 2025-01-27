package appli.produits;

import appli.StartApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Produits;
import repository.ProduitsRepository;

import java.io.IOException;
import java.sql.SQLException;

public class AjouterProduitController {
    @FXML
    private TextField libelleField, descriptionField, niveauDangerositeField, quantiteField, refFournisseursField;

    @FXML
    private Label erreurText;

    @FXML
    protected void onAjouterProduitClick() throws SQLException, IOException {
        if (libelleField.getText().isEmpty() || descriptionField.getText().isEmpty() ||
                niveauDangerositeField.getText().isEmpty() || quantiteField.getText().isEmpty() ||
                refFournisseursField.getText().isEmpty()) {
            erreurText.setText("Tous les champs doivent être remplis !");
        } else {
            Produits produit = new Produits(
                    libelleField.getText(),
                    descriptionField.getText(),
                    Integer.parseInt(niveauDangerositeField.getText()),
                    Integer.parseInt(quantiteField.getText()),
                    Integer.parseInt(refFournisseursField.getText())
            );

            if (ProduitsRepository.addProduit(produit)) {
                StartApplication.changeScene("produits/produitsview.fxml");
            } else {
                erreurText.setText("Erreur lors de l'ajout du produit.");
            }
        }
    }

    @FXML
    protected void onRetourClick() throws IOException {
        StartApplication.changeScene("produits/produitsview.fxml");
    }
}

