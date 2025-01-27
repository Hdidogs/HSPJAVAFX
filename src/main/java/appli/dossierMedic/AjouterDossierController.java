package appli.dossierMedic;

import appli.StartApplication;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Dossiers;
import repository.DossiersRepository;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

public class AjouterDossierController {
    @FXML
    private TextField refPatientsField, refUserField, symptomesField, niveauGraviteField, refEtatField;

    @FXML
    private DatePicker dateArriveePicker, dateCloturePicker;

    @FXML
    private Label erreurText;

    @FXML
    protected void onAjouterDossierClick() throws SQLException, IOException {
        if (refPatientsField.getText().isEmpty() || refUserField.getText().isEmpty() ||
                symptomesField.getText().isEmpty() || niveauGraviteField.getText().isEmpty() ||
                refEtatField.getText().isEmpty() || dateArriveePicker.getValue() == null) {
            erreurText.setText("Tous les champs obligatoires doivent être remplis !");
        } else {
            Date dateArrivee = Date.valueOf(dateArriveePicker.getValue());
            Date dateCloture = dateCloturePicker.getValue() != null ? Date.valueOf(dateCloturePicker.getValue()) : null;

            Dossiers dossier = new Dossiers(
                    Integer.parseInt(refPatientsField.getText()),
                    Integer.parseInt(refUserField.getText()),
                    dateArrivee,
                    symptomesField.getText(),
                    Integer.parseInt(niveauGraviteField.getText()),
                    Integer.parseInt(refEtatField.getText()),
                    dateCloture
            );

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


