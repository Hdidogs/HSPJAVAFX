package appli;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StartApplication extends Application {
    private static Stage main;

    @Override
    public void start(Stage stage) throws IOException {
        main = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("Login/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 760);
        main.setScene(scene);
        main.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void changeScene(String nomDuFichierFxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource(nomDuFichierFxml));
        Scene scene = new Scene(fxmlLoader.load(), main.getWidth(), main.getHeight());
        main.setScene(scene);
        main.show();
    }

    public static void changeScene(String nomDuFichierFxml, Object controller) {
        main.close();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource(nomDuFichierFxml + ".fxml"));
            fxmlLoader.setController(controller);
            Scene scene = new Scene(fxmlLoader.load(), main.getWidth(), main.getHeight());
            main.setScene(scene);
            main.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}