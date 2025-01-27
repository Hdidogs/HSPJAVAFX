package appli.dossierMedic;

import appli.StartApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Dossiers;
import repository.DossiersRepository;

import java.io.IOException;
import java.sql.SQLException;

public class ModifierDossierController {
    @FXML
    private TextField patientField, userField, symptomesField, graviteField, etatField;

    @FXML
    private Label erreurText;

    private Dossiers dossier;

    public ModifierDossierController(Dossiers dossier) {
        this.dossier = dossier;
    }

    @FXML
    public void initialize() {
        patientField.setText(String.valueOf(dossier.getPatientId()));
        userField.setText(String.valueOf(dossier.getUserId()));
        symptomesField.setText(dossier.getSymptomes());
        graviteField.setText(String.valueOf(dossier.getGravite()));
        etatField.setText(String.valueOf(dossier.getEtatId()));
    }

    @FXML
    protected void onModifierDossierClick() throws SQLException {
        if (patientField.getText().isEmpty() || userField.getText().isEmpty() ||
                symptomesField.getText().isEmpty() || graviteField.getText().isEmpty() || etatField.getText().isEmpty()) {
            erreurText.setText("Tous les champs doivent être remplis !");
        } else {
            dossier.setPatientId(Integer.parseInt(patientField.getText()));
            dossier.setUserId(Integer.parseInt(userField.getText()));
            dossier.setSymptomes(symptomesField.getText());
            dossier.setGravite(Integer.parseInt(graviteField.getText()));
            dossier.setEtatId(Integer.parseInt(etatField.getText()));

            if (DossiersRepository.updateDossiers(dossier)) {
                StartApplication.changeScene("dossiers/dossiersview.fxml");
            } else {
                erreurText.setText("Erreur lors de la modification du dossier.");
            }
        }
    }

    @FXML
    protected void onRetourClick() throws IOException {
        StartApplication.changeScene("dossiers/dossiersview.fxml");
    }
}

