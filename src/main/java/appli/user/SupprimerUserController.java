package appli.user;

import appli.StartApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.User;
import repository.UserRepository;

import java.io.IOException;
import java.sql.SQLException;

public class SupprimerUserController {
    @FXML
    private Label confirmationText;

    private User user;

    public SupprimerUserController(User user) {
        this.user = user;
    }

    @FXML
    protected void onSupprimerUserClick() throws SQLException, IOException {
        if (UserRepository.deleteUser(user.getId())) {
            StartApplication.changeScene("users/usersview.fxml");
        } else {
            confirmationText.setText("Erreur lors de la suppression de l'utilisateur.");
        }
    }

    @FXML
    protected void onRetourClick() throws IOException {
        StartApplication.changeScene("users/usersview.fxml");
    }
}
