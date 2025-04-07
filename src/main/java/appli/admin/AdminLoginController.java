package com.hsp.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

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

    /**
     * Méthode appelée lors du clic sur le bouton "Connexion".
     * Elle vérifie les informations saisies et affiche un message en cas d'erreur ou procède à la connexion.
     */
    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Exemple de vérification (à adapter avec une vraie authentification)
        if ("admin".equals(username) && "password".equals(password)) {
            errorMessage.setVisible(false);
            // TODO : ajouter la logique pour une connexion réussie (par exemple, changement de scène)
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Connexion réussie");
            alert.setHeaderText(null);
            alert.setContentText("Vous êtes connecté !");
            alert.showAndWait();
        } else {
            errorMessage.setText("Identifiant ou mot de passe incorrect");
            errorMessage.setVisible(true);
        }
    }

    /**
     * Méthode appelée lors du clic sur le bouton "Annuler".
     * Elle réinitialise les champs de saisie et masque le message d'erreur.
     */
    @FXML
    private void handleCancel(ActionEvent event) {
        usernameField.clear();
        passwordField.clear();
        errorMessage.setVisible(false);
    }
}
