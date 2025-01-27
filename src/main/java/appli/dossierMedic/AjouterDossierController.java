package appli.dossierMedic;

import appli.StartApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Dossiers;
import repository.DossiersRepository;

import java.io.IOException;
import java.sql.SQLException;

public class AjouterDossierController {
    @FXML
    private TextField patientField, userField, symptomesField, graviteField, etatField;

    @FXML
    private Label erreurText;

    @FXML
    protected void onAjouterDossierClick() throws SQLException {
        if (patientField.getText().isEmpty() || userField.getText().isEmpty() ||
                symptomesField.getText().isEmpty() || graviteField.getText().isEmpty() ||
                etatField.getText().isEmpty()) {
            erreurText.setText("Tous les champs doivent être remplis !");
        } else {
            Dossiers dossier = new Dossiers(Integer.parseInt(patientField.getText()),
                    Integer.parseInt(userField.getText()),
                    symptomesField.getText(),
                    Integer.parseInt(graviteField.getText()),
                    Integer.parseInt(etatField.getText()));
            if (DossiersRepository.addDossiers(dossier)) {
                StartApplication.changeScene("dossiers/dossiersview.fxml");
            } else {
                erreurText.setText("Erreur lors de l'ajout du dossier.");
            }
        }
    }

    @FXML
    protected void onRetourClick() throws IOException {
        StartApplication.changeScene("dossiers/dossiersview.fxml");
    }
}

