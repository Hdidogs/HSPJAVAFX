package appli.user;

import appli.StartApplication;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.User;
import repository.UserRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Date;

public class ModifierUserController {
    @FXML
    private TextField nomField, prenomField, mailField, motDePasseField, refRoleField;

    @FXML
    private DatePicker dateCreationPicker;

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
        motDePasseField.setText(user.getMotDePasse());
        refRoleField.setText(String.valueOf(user.getRefRole()));
        dateCreationPicker.setValue(user.getDateCreation().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }

    @FXML
    protected void onModifierUserClick() throws SQLException, IOException {
        if (nomField.getText().isEmpty() || prenomField.getText().isEmpty() ||
                mailField.getText().isEmpty() || motDePasseField.getText().isEmpty() ||
                refRoleField.getText().isEmpty() || dateCreationPicker.getValue() == null) {
            erreurText.setText("Tous les champs doivent être remplis !");
        } else {
            user.setNom(nomField.getText());
            user.setPrenom(prenomField.getText());
            user.setMail(mailField.getText());
            user.setMotDePasse(motDePasseField.getText());
            user.setRefRole(Integer.parseInt(refRoleField.getText()));
            user.setDateCreation(Date.from(dateCreationPicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));

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