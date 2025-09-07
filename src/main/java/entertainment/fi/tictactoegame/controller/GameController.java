package entertainment.fi.tictactoegame.controller;

import entertainment.fi.tictactoegame.model.GameRecord;
import entertainment.fi.tictactoegame.service.GameHistoryService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.prefs.Preferences;
import java.util.Random;

public class GameController {
    @FXML
    private Label statusLabel;
    private Preferences prefs = Preferences.userNodeForPackage(SettingsController.class);
    private String mode; // human/ai
    private String difficulty; // easy/medium/hard
    @FXML
    private void startNewGame() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/entertainment/fi/tictactoegame/gameboard.fxml"));
            Scene scene = new Scene(loader.load(), 550, 400);

            GameBoardController controller = loader.getController();
            controller.initSettings(
                    prefs.get("mode", "human"),
                    prefs.get("difficulty", "easy")
            );

            Stage gameStage = new Stage();
            gameStage.setTitle("Игра");
            gameStage.initModality(Modality.APPLICATION_MODAL);
            gameStage.setScene(scene);
            gameStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void openSettings() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/entertainment/fi/tictactoegame/settings.fxml"));
            Scene scene = new Scene(loader.load(), 600, 400);

            Stage settingsStage = new Stage();
            settingsStage.setTitle("Настройки игры");
            settingsStage.initModality(Modality.APPLICATION_MODAL);
            settingsStage.setScene(scene);
            settingsStage.showAndWait();

            mode = prefs.get("mode", "human");
            difficulty = prefs.get("difficulty", "easy");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void show() {
        GameHistoryService gameHistoryService = new GameHistoryService();
        Stage stage = new Stage();
        VBox root = new VBox();
        ListView<String> listView = new ListView<>();

        for (GameRecord record : gameHistoryService.getHistory()) {
            listView.getItems().add(record.toString());
        }

        root.getChildren().add(listView);
        stage.setScene(new Scene(root, 600, 300));
        stage.setTitle("История игр");
        stage.show();
    }
}
