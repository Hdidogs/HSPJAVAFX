package appli.patients;

import appli.StartApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Patients;
import repository.PatientsRepository;

import java.io.IOException;
import java.sql.SQLException;

public class AjouterPatientController {
    @FXML
    private TextField nomField, prenomField, numSecuField, mailField, telField, rueField, villeField, cpField;

    @FXML
    private Label erreurText;

    @FXML
    protected void onAjouterPatientClick() throws SQLException, IOException {
        if (nomField.getText().isEmpty() || prenomField.getText().isEmpty() || numSecuField.getText().isEmpty() ||
                mailField.getText().isEmpty() || telField.getText().isEmpty() || rueField.getText().isEmpty() ||
                villeField.getText().isEmpty() || cpField.getText().isEmpty()) {
            erreurText.setText("Tous les champs doivent être remplis !");
        } else {
            Patients patient = new Patients(nomField.getText(), prenomField.getText(), numSecuField.getText(),
                    mailField.getText(), telField.getText(), rueField.getText(),
                    villeField.getText(), Integer.parseInt(cpField.getText()));
            if (PatientsRepository.addPatient(patient)) {
                StartApplication.changeScene("patients/patientsview.fxml");
            } else {
                erreurText.setText("Erreur lors de l'ajout du patient.");
            }
        }
    }

    @FXML
    protected void onRetourClick() throws IOException {
        StartApplication.changeScene("patients/patientsview.fxml");
    }
}
