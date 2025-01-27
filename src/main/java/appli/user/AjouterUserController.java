package appli.user;

import appli.StartApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.User;
import repository.UserRepository;

import java.io.IOException;
import java.sql.SQLException;

public class AjouterUserController {
    @FXML
    private TextField nomField, prenomField, mailField, mdpField, roleField;

    @FXML
    private Label erreurText;

    @FXML
    protected void onAjouterUserClick() throws SQLException {
        if (nomField.getText().isEmpty() || prenomField.getText().isEmpty() ||
                mailField.getText().isEmpty() || mdpField.getText().isEmpty() || roleField.getText().isEmpty()) {
            erreurText.setText("Tous les champs doivent être remplis !");
        } else {
            User user = new User(nomField.getText(), prenomField.getText(), mailField.getText(),
                    mdpField.getText(), Integer.parseInt(roleField.getText()));
            if (UserRepository.addUser(user)) {*
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

