package appli.user;

import appli.StartApplication;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.User;
import model.UserLog;
import repository.UserRepository;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {

    // Champs pour la connexion
    @FXML
    private TextField loginEmailField;

    @FXML
    private PasswordField loginPasswordField;

    // Champs pour l'inscription
    @FXML
    private TextField registerNameField;

    @FXML
    private TextField registerFirstNameField;

    @FXML
    private TextField registerEmailField;

    @FXML
    private PasswordField registerPasswordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    public void initialize() {
    }

    /**
     * Gère la connexion de l'utilisateur.
     */
    @FXML
    private void handleLogin() {
        String email = loginEmailField.getText().trim();
        String password = loginPasswordField.getText().trim();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs.", Alert.AlertType.WARNING);
            return;
        }

        try {
            User user = UserRepository.login(email, password);
            if (user != null) {
                if (UserLog.initInstance(user)) {
                    StartApplication.changeScene("dashboard/dashboard.fxml");
                }
            } else {
                showAlert("Erreur", "Email ou mot de passe incorrect.", Alert.AlertType.ERROR);
            }
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors de la connexion : " + e.getMessage(), Alert.AlertType.ERROR);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gère l'inscription de l'utilisateur.
     */
    @FXML
    private void handleRegister() {
        String name = registerNameField.getText().trim();
        String firstName = registerFirstNameField.getText().trim();
        String email = registerEmailField.getText().trim();
        String password = registerPasswordField.getText().trim();
        String confirmPassword = confirmPasswordField.getText().trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs.", Alert.AlertType.WARNING);
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert("Erreur", "Les mots de passe ne correspondent pas.", Alert.AlertType.WARNING);
            return;
        }

        try {
            User newUser = new User(name, firstName, email, password, 1);
            if (UserRepository.addUser(newUser)) {
                showAlert("Succès", "Inscription réussie.", Alert.AlertType.INFORMATION);
                clearRegisterForm();
            } else {
                showAlert("Erreur", "Erreur lors de l'inscription.", Alert.AlertType.ERROR);
            }
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors de l'inscription : " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Efface les champs du formulaire d'inscription.
     */
    private void clearRegisterForm() {
        registerNameField.clear();
        registerFirstNameField.clear();
        registerEmailField.clear();
        registerPasswordField.clear();
        confirmPasswordField.clear();}

    /**
     * Affiche une alerte.
     */
    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
