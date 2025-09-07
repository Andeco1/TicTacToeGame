package entertainment.fi.tictactoegame.controller;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.util.prefs.Preferences;

public class SettingsController {

    @FXML private RadioButton humanMode;
    @FXML private RadioButton aiMode;

    @FXML private RadioButton easy;
    @FXML private RadioButton medium;
    @FXML private RadioButton hard;

    private ToggleGroup modeGroup;
    private ToggleGroup difficultyGroup;

    private Preferences prefs;

    @FXML
    private void initialize() {
        ToggleGroup modeGroup = new ToggleGroup();
        humanMode.setToggleGroup(modeGroup);
        aiMode.setToggleGroup(modeGroup);

        ToggleGroup difficultyGroup = new ToggleGroup();
        easy.setToggleGroup(difficultyGroup);
        medium.setToggleGroup(difficultyGroup);
        hard.setToggleGroup(difficultyGroup);

        prefs = Preferences.userNodeForPackage(SettingsController.class);

        // Восстановление сохраненных настроек
        String savedMode = prefs.get("mode", "human");
        if (savedMode.equals("ai")) {
            aiMode.setSelected(true);
        } else {
            humanMode.setSelected(true);
        }

        String savedDifficulty = prefs.get("difficulty", "easy");
        switch (savedDifficulty) {
            case "medium" -> medium.setSelected(true);
            case "hard" -> hard.setSelected(true);
            default -> easy.setSelected(true);
        }
    }

    @FXML
    private void saveSettings() {
        String mode = humanMode.isSelected() ? "human" : "ai";
        prefs.put("mode", mode);

        String difficulty = "easy";
        if (medium.isSelected()) difficulty = "medium";
        if (hard.isSelected()) difficulty = "hard";
        prefs.put("difficulty", difficulty);

        Stage stage = (Stage) humanMode.getScene().getWindow();
        stage.close();
    }
}
