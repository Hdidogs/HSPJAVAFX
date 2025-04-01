package com.hsp.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

public class PersonnelLoginController {

    @FXML
    private ComboBox<String> roleComboBox;

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
     * Elle vérifie que l'utilisateur a sélectionné un rôle et que les informations de connexion sont correctes.
     */
    @FXML
    private void handleLogin(ActionEvent event) {
        String role = roleComboBox.getValue();
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Vérifier qu'un rôle a été sélectionné
        if (role == null || role.trim().isEmpty()) {
            errorMessage.setText("Veuillez sélectionner un rôle.");
            errorMessage.setVisible(true);
            return;
        }

        // Exemple de vérification (à adapter avec une authentification réelle)
        if ("user".equals(username) && "pass".equals(password)) {
            errorMessage.setVisible(false);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Connexion réussie");
            alert.setHeaderText(null);
            alert.setContentText("Bienvenue, " + role + " " + username + " !");
            alert.showAndWait();
            // TODO : ajouter la logique de navigation vers la prochaine vue
        } else {
            errorMessage.setText("Identifiant ou mot de passe incorrect.");
            errorMessage.setVisible(true);
        }
    }

    /**
     * Méthode appelée lors du clic sur le bouton "Annuler".
     * Elle réinitialise tous les champs de saisie et masque le message d'erreur.
     */
    @FXML
    private void handleCancel(ActionEvent event) {
        roleComboBox.getSelectionModel().clearSelection();
        usernameField.clear();
        passwordField.clear();
        errorMessage.setVisible(false);
    }
}
