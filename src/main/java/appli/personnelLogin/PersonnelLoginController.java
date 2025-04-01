package com.hsp.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class PersonnelLoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ComboBox<String> roleComboBox;

    @FXML
    private Button loginButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Label errorMessage;

    @FXML
    public void initialize() {
        // Initialisation des composants
        errorMessage.setVisible(false);
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String selectedRole = roleComboBox.getValue();

        if (username.isEmpty() || password.isEmpty() || selectedRole == null) {
            showError("Veuillez remplir tous les champs et sélectionner un rôle");
            return;
        }

        // Vérification des identifiants
        boolean isAuthenticated = authenticatePersonnel(username, password, selectedRole);

        if (isAuthenticated) {
            try {
                // Redirection vers la vue appropriée selon le rôle
                String viewPath = getViewPathForRole(selectedRole);
                FXMLLoader loader = new FXMLLoader(getClass().getResource(viewPath));
                Parent root = loader.load();

                // Passage des informations utilisateur au contrôleur suivant
                if (loader.getController() instanceof BaseController) {
                    BaseController controller = loader.getController();
                    controller.setUserInfo(username, selectedRole);
                }

                Stage stage = (Stage) loginButton.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("HSP - " + selectedRole);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                showError("Erreur lors du chargement de l'interface");
            }
        } else {
            showError("Identifiants incorrects ou accès non autorisé pour ce rôle");
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        // Réinitialiser les champs
        usernameField.clear();
        passwordField.clear();
        roleComboBox.getSelectionModel().clearSelection();
        errorMessage.setVisible(false);

        // Fermer l'application ou revenir à l'écran précédent
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    private boolean authenticatePersonnel(String username, String password, String role) {
        // Implémentation fictive - à remplacer par votre logique d'authentification
        // Connectez-vous à votre base de données ou service d'authentification

        // Exemple simple pour démonstration
        if (role.equals("Médecin")) {
            return username.equals("docteur") && password.equals("doc123");
        } else if (role.equals("Infirmier")) {
            return username.equals("infirmier") && password.equals("inf123");
        } else if (role.equals("Gestionnaire de produits")) {
            return username.equals("gestionnaire") && password.equals("gest123");
        } else if (role.equals("Réceptionniste")) {
            return username.equals("reception") && password.equals("rec123");
        }

        return false;
    }

    private String getViewPathForRole(String role) {
        // Retourne le chemin de la vue correspondant au rôle
        switch (role) {
            case "Médecin":
                return "/com/hsp/views/doctor_dashboard.fxml";
            case "Infirmier":
                return "/com/hsp/views/nurse_dashboard.fxml";
            case "Gestionnaire de produits":
                return "/com/hsp/views/product_requests.fxml";
            case "Réceptionniste":
                return "/com/hsp/views/receptionist_dashboard.fxml";
            default:
                return "/com/hsp/views/personnel_dashboard.fxml";
        }
    }

    private void showError(String message) {
        errorMessage.setText(message);
        errorMessage.setVisible(true);
    }
}

// Interface de base pour les contrôleurs qui ont besoin d'informations utilisateur
interface BaseController {
    void setUserInfo(String username, String role);
}