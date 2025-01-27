package appli.dossierMedic;

import appli.StartApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.Dossiers;
import repository.DossiersRepository;

import java.io.IOException;
import java.sql.SQLException;

public class SupprimerDossierController {
    @FXML
    private Label confirmationText;

    private Dossiers dossier;

    public SupprimerDossierController(Dossiers dossier) {
        this.dossier = dossier;
    }

    @FXML
    protected void onSupprimerDossierClick() throws SQLException, IOException {
        if (DossiersRepository.deleteDossiers(dossier.getId())) {
            StartApplication.changeScene("dossiers/dossiersview.fxml");
        } else {
            confirmationText.setText("Erreur lors de la suppression du dossier.");
        }
    }

    @FXML
    protected void onRetourClick() throws IOException {
        StartApplication.changeScene("dossiers/dossiersview.fxml");
    }
}

