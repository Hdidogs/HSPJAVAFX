package appli.dossierMedic;

import appli.StartApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.Dossiers;

import java.io.IOException;

public class VisualiserDossierController {
    @FXML
    private Label refPatientsLabel, refUserLabel, symptomesLabel, niveauGraviteLabel, refEtatLabel, dateArriveeLabel, dateClotureLabel;

    private Dossiers dossier;

    public VisualiserDossierController(Dossiers dossier) {
        this.dossier = dossier;
    }

    @FXML
    public void initialize() {
        refPatientsLabel.setText(String.valueOf(dossier.getRefPatients()));
        refUserLabel.setText(String.valueOf(dossier.getRefUser()));
        symptomesLabel.setText(dossier.getSymptomes());
        niveauGraviteLabel.setText(String.valueOf(dossier.getNiveauGravite()));
        refEtatLabel.setText(String.valueOf(dossier.getRefEtat()));
        dateArriveeLabel.setText(dossier.getDateArrivee().toString());
        dateClotureLabel.setText(dossier.getDateCloture() != null ? dossier.getDateCloture().toString() : "N/A");
    }

    @FXML
    protected void onRetourClick() throws IOException {
        StartApplication.changeScene("dossiers/dossiersview.fxml");
    }
}


