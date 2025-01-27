package appli.user;

import appli.StartApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.User;
import repository.UserRepository;

import java.io.IOException;
import java.sql.SQLException;

public class ModifierUserController {
    @FXML
    private TextField nomField, prenomField, mailField, mdpField, roleField;

    @FXML
    private Label erreurText;

    private User user;

    public ModifierUserController(User user) {
        this.user = user;
    }

    @FXML
    public void initialize() {
        nomField.setText(user.getNom());
        prenomField.setText(user.getPrenom());
        mailField.setText(user.getMail());
        mdpField.setText(user.getMdp());
        roleField.setText(String.valueOf(user.getRoleId()));
    }

    @FXML
    protected void onModifierUserClick() throws SQLException {
        if (nomField.getText().isEmpty() || prenomField.getText().isEmpty() ||
                mailField.getText().isEmpty() || mdpField.getText().isEmpty() || roleField.getText().isEmpty()) {
            erreurText.setText("Tous les champs doivent être remplis !");
        } else {
            user.setNom(nomField.getText());
            user.setPrenom(prenomField.getText());
            user.setMail(mailField.getText());
            user.setMdp(mdpField.getText());
            user.setRoleId(Integer.parseInt(roleField.getText()));

            if (UserRepository.updateUser(user)) {
                StartApplication.changeScene("users/usersview.fxml");
            } else {
                erreurText.setText("Erreur lors de la modification de l'utilisateur.");
            }
        }
    }

    @FXML
    protected void onRetourClick() throws IOException {
        StartApplication.changeScene("users/usersview.fxml");
    }
}

