package appli.user;

import appli.StartApplication;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.User;
import repository.UserRepository;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.ZoneId;

public class AjouterUserController {
    @FXML
    private TextField nomField, prenomField, mailField, motDePasseField, refRoleField;

    @FXML
    private DatePicker dateCreationPicker;

    @FXML
    private Label erreurText;

    @FXML
    protected void onAjouterUserClick() throws SQLException, IOException {
        if (nomField.getText().isEmpty() || prenomField.getText().isEmpty() ||
                mailField.getText().isEmpty() || motDePasseField.getText().isEmpty() ||
                refRoleField.getText().isEmpty() || dateCreationPicker.getValue() == null) {
            erreurText.setText("Tous les champs doivent être remplis !");
        } else {
            Date dateCreation = (Date) Date.from(dateCreationPicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());

            User user = new User(
                    nomField.getText(),
                    prenomField.getText(),
                    mailField.getText(),
                    motDePasseField.getText(),
                    Integer.parseInt(refRoleField.getText()),
                    dateCreation
            );

            if (UserRepository.addUser(user)) {
                StartApplication.changeScene("users/usersview.fxml");
            } else {
                erreurText.setText("Erreur lors de l'ajout de l'utilisateur.");
            }
        }
    }

    @FXML
    protected void onRetourClick() throws IOException {
        StartApplication.changeScene("users/usersview.fxml");
    }
}


