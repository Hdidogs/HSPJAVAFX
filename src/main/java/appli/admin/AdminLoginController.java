package com.hsp.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminLoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

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

        if (username.isEmpty() || password.isEmpty()) {
            showError("Veuillez remplir tous les champs");
            return;
        }

        // Ici, vous devriez implémenter la vérification des identifiants
        // avec votre service d'authentification
        boolean isAuthenticated = authenticateAdmin(username, password);

        if (isAuthenticated) {
            try {
                // Redirection vers le tableau de bord administrateur
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hsp/views/admin_dashboard.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) loginButton.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("HSP - Tableau de bord administrateur");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                showError("Erreur lors du chargement du tableau de bord");
            }
        } else {
            showError("Identifiants incorrects");
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        // Réinitialiser les champs
        usernameField.clear();
        passwordField.clear();
        errorMessage.setVisible(false);

        // Optionnel: fermer l'application ou revenir à l'écran précédent
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    private boolean authenticateAdmin(String username, String password) {
        // Implémentation fictive - à remplacer par votre logique d'authentification
        // Connectez-vous à votre base de données ou service d'authentification

        // Exemple simple pour démonstration
        return username.equals("admin") && password.equals("admin123");
    }

    private void showError(String message) {
        errorMessage.setText(message);
        errorMessage.setVisible(true);
    }
}