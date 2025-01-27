package appli.patients;

import appli.StartApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.Patients;
import repository.PatientsRepository;

import java.io.IOException;
import java.sql.SQLException;

public class SupprimerPatientController {
    @FXML
    private Label confirmationText;

    private Patients patient;

    public SupprimerPatientController(Patients patient) {
        this.patient = patient;
    }

    @FXML
    protected void onSupprimerPatientClick() throws SQLException {
        if (PatientsRepository.deletePatient(patient.getId())) {
            StartApplication.changeScene("patients/patientsview.fxml");
        } else {
            confirmationText.setText("Erreur lors de la suppression du patient.");
        }
    }

    @FXML
    protected void onRetourClick() throws IOException {
        StartApplication.changeScene("patients/patientsview.fxml");
    }
}

