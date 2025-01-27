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
    private TextField libelleField, descriptionField, dangerositeField, qtnField, fournisseurField;

    @FXML
    private Label erreurText;

    @FXML
    protected void onAjouterProduitClick() throws SQLException {
        if (libelleField.getText().isEmpty() || descriptionField.getText().isEmpty() ||
                dangerositeField.getText().isEmpty() || qtnField.getText().isEmpty() || fournisseurField.getText().isEmpty()) {
            erreurText.setText("Tous les champs doivent être remplis !");
        } else {
            Produits produit = new Produits(libelleField.getText(), descriptionField.getText(),
                    Integer.parseInt(dangerositeField.getText()),
                    Integer.parseInt(qtnField.getText()),
                    Integer.parseInt(fournisseurField.getText()));
            if (ProduitsRepository.addProduit(produit)) {
                StartApplication.changeScene("stocks/stocksview.fxml");
            } else {
                erreurText.setText("Erreur lors de l'ajout du produit.");
            }
        }
    }

    @FXML
    protected void onRetourClick() throws IOException {
        StartApplication.changeScene("stocks/stocksview.fxml");
    }
}
