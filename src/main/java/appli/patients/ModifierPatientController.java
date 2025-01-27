package appli.patients;

import appli.StartApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Patients;
import repository.PatientsRepository;

import java.io.IOException;
import java.sql.SQLException;

public class ModifierPatientController {
    @FXML
    private TextField nomField, prenomField, numSecuField, mailField, telField, rueField, villeField, cpField;

    @FXML
    private Label erreurText;

    private Patients patient;

    public ModifierPatientController(Patients patient) {
        this.patient = patient;
    }

    @FXML
    public void initialize() {
        nomField.setText(patient.getNom());
        prenomField.setText(patient.getPrenom());
        numSecuField.setText(patient.getNumSecu());
        mailField.setText(patient.getMail());
        telField.setText(patient.getTel());
        rueField.setText(patient.getRue());
        villeField.setText(patient.getVille());
        cpField.setText(String.valueOf(patient.getCp()));
    }

    @FXML
    protected void onModifierPatientClick() throws SQLException {
        if (nomField.getText().isEmpty() || prenomField.getText().isEmpty() || numSecuField.getText().isEmpty() ||
                mailField.getText().isEmpty() || telField.getText().isEmpty() || rueField.getText().isEmpty() ||
                villeField.getText().isEmpty() || cpField.getText().isEmpty()) {
            erreurText.setText("Tous les champs doivent être remplis !");
        } else {
            patient.setNom(nomField.getText());
            patient.setPrenom(prenomField.getText());
            patient.setNumSecu(numSecuField.getText());
            patient.setMail(mailField.getText());
            patient.setTel(telField.getText());
            patient.setRue(rueField.getText());
            patient.setVille(villeField.getText());
            patient.setCp(Integer.parseInt(cpField.getText()));

            if (PatientsRepository.updatePatient(patient)) {
                StartApplication.changeScene("patients/patientsview.fxml");
            } else {
                erreurText.setText("Erreur lors de la modification du patient.");
            }
        }
    }

    @FXML
    protected void onRetourClick() throws IOException {
        StartApplication.changeScene("patients/patientsview.fxml");
    }
}

