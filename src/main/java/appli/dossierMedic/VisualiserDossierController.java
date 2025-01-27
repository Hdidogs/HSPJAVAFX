package appli.dossierMedic;

import appli.StartApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.Dossiers;

import java.io.IOException;

public class VisualiserDossierController {
    @FXML
    private Label patientLabel, userLabel, symptomesLabel, graviteLabel, etatLabel, dateArriveeLabel, dateClotureLabel;

    private Dossiers dossier;

    public VisualiserDossierController(Dossiers dossier) {
        this.dossier = dossier;
    }

    @FXML
    public void initialize() {
        patientLabel.setText(String.valueOf(dossier.getPatientId()));
        userLabel.setText(String.valueOf(dossier.getUserId()));
        symptomesLabel.setText(dossier.getSymptomes());
        graviteLabel.setText(String.valueOf(dossier.getGravite()));
        etatLabel.setText(String.valueOf(dossier.getEtatId()));
        dateArriveeLabel.setText(dossier.getDateArrivee().toString());
        dateClotureLabel.setText(dossier.getDateCloture() != null ? dossier.getDateCloture().toString() : "N/A");
    }

    @FXML
    protected void onRetourClick() throws IOException {
        StartApplication.changeScene("dossiers/dossiersview.fxml");
    }
}

