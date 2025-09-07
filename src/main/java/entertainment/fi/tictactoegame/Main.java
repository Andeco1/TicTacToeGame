package entertainment.fi.tictactoegame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        URL fxmlUrl = getClass().getResource("/entertainment/fi/tictactoegame/main.fxml");
        FXMLLoader loader = new FXMLLoader(fxmlUrl);

        Scene scene = new Scene(loader.load(), 400, 300);
        stage.setTitle("Крестики-Нолики");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
