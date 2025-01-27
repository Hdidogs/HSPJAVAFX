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

public class ModifierDossierController {
    @FXML
    private TextField refPatientsField, refUserField, symptomesField, niveauGraviteField, refEtatField;

    @FXML
    private DatePicker dateArriveePicker, dateCloturePicker;

    @FXML
    private Label erreurText;

    private Dossiers dossier;

    public ModifierDossierController(Dossiers dossier) {
        this.dossier = dossier;
    }

    @FXML
    public void initialize() {
        refPatientsField.setText(String.valueOf(dossier.getRefPatients()));
        refUserField.setText(String.valueOf(dossier.getRefUser()));
        symptomesField.setText(dossier.getSymptomes());
        niveauGraviteField.setText(String.valueOf(dossier.getNiveauGravite()));
        refEtatField.setText(String.valueOf(dossier.getRefEtat()));
        dateArriveePicker.setValue(dossier.getDateArrivee().toLocalDate());
        if (dossier.getDateCloture() != null) {
            dateCloturePicker.setValue(dossier.getDateCloture().toLocalDate());
        }
    }

    @FXML
    protected void onModifierDossierClick() throws SQLException, IOException {
        if (refPatientsField.getText().isEmpty() || refUserField.getText().isEmpty() ||
                symptomesField.getText().isEmpty() || niveauGraviteField.getText().isEmpty() ||
                refEtatField.getText().isEmpty() || dateArriveePicker.getValue() == null) {
            erreurText.setText("Tous les champs obligatoires doivent être remplis !");
        } else {
            dossier.setRefPatients(Integer.parseInt(refPatientsField.getText()));
            dossier.setRefUser(Integer.parseInt(refUserField.getText()));
            dossier.setSymptomes(symptomesField.getText());
            dossier.setNiveauGravite(Integer.parseInt(niveauGraviteField.getText()));
            dossier.setRefEtat(Integer.parseInt(refEtatField.getText()));
            dossier.setDateArrivee(Date.valueOf(dateArriveePicker.getValue()));
            if (dateCloturePicker.getValue() != null) {
                dossier.setDateCloture(Date.valueOf(dateCloturePicker.getValue()));
            }

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


