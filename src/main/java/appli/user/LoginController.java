package appli.user;

import appli.StartApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.User;
import repository.UserRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

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
    private TextField registerEmailField;

    @FXML
    private PasswordField registerPasswordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private ComboBox<String> roleComboBox;

    @FXML
    public void initialize() {
        // Initialisation des rôles dans le ComboBox
        loadRoles();
    }

    /**
     * Charge les rôles dans le ComboBox.
     */
    private void loadRoles() {
        try {
            List<String> roles = UserRepository.getAllRoles();
            ObservableList<String> roleOptions = FXCollections.observableArrayList(roles);
            roleComboBox.setItems(roleOptions);
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors du chargement des rôles : " + e.getMessage(), Alert.AlertType.ERROR);
        }
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
                showAlert("Succès", "Connexion réussie.", Alert.AlertType.INFORMATION);
                // Redirection vers le tableau de bord
                StartApplication.changeScene("dashboard/dashboardview.fxml");
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
        String email = registerEmailField.getText().trim();
        String password = registerPasswordField.getText().trim();
        String confirmPassword = confirmPasswordField.getText().trim();
        String role = roleComboBox.getValue();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || role == null) {
            showAlert("Erreur", "Veuillez remplir tous les champs.", Alert.AlertType.WARNING);
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert("Erreur", "Les mots de passe ne correspondent pas.", Alert.AlertType.WARNING);
            return;
        }

        try {
            User newUser = new User(name, email, password, role);
            if (UserRepository.register(newUser)) {
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
        registerEmailField.clear();
        registerPasswordField.clear();
        confirmPasswordField.clear();
        roleComboBox.setValue(null);
    }

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
