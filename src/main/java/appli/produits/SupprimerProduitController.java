package appli.produits;

import appli.StartApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.Produits;
import repository.ProduitsRepository;

import java.io.IOException;
import java.sql.SQLException;

public class SupprimerProduitController {
    @FXML
    private Label confirmationText;

    private Produits produit;

    public SupprimerProduitController(Produits produit) {
        this.produit = produit;
    }

    @FXML
    protected void onSupprimerProduitClick() throws SQLException, IOException {
        if (ProduitsRepository.deleteProduit(produit.getId())) {
            StartApplication.changeScene("stocks/stocksview.fxml");
        } else {
            confirmationText.setText("Erreur lors de la suppression du produit.");
        }
    }

    @FXML
    protected void onRetourClick() throws IOException {
        StartApplication.changeScene("stocks/stocksview.fxml");
    }
}

