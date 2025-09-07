package entertainment.fi.tictactoegame.controller;

import entertainment.fi.tictactoegame.model.GameRecord;
import entertainment.fi.tictactoegame.service.GameHistoryService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.prefs.Preferences;

public class GameBoardController {

    @FXML
    private Label statusLabel;

    @FXML
    private GridPane gameGrid;

    private String[][] board = new String[3][3];
    private boolean xTurn = true;
    private String mode = "human";
    private String difficulty = "easy";
    private Preferences prefs = Preferences.userNodeForPackage(SettingsController.class);


    public void initSettings(String mode, String difficulty) {
        this.mode = mode;
        this.difficulty = difficulty;
        startGame();
    }

    private void startGame() {
        xTurn = true;
        statusLabel.setText(mode.equals("human")
                ? "Человек против человека"
                : "Человек против компьютера (" + switch (difficulty) {
            case "medium" -> "Средний";
            case "hard" -> "Сложный";
            default -> "Легкий";
        } + ")");
        initBoard();
        drawBoard();
    }

    private void initBoard() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                board[i][j] = "";
    }

    private void drawBoard() {
        gameGrid.getChildren().clear();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Button cell = new Button(board[row][col]);
                cell.getStyleClass().add("button-cell");
                cell.setMinSize(80, 80);
                cell.setPrefSize(100, 100);
                int r = row, c = col;
                cell.setOnAction(e -> handleMove(r, c, cell));
                gameGrid.add(cell, col, row);
            }
        }
    }

    private void handleMove(int row, int col, Button cell) {
        if (!board[row][col].isEmpty()) return;

        board[row][col] = xTurn ? "X" : "O";
        cell.setText(board[row][col]);

        if (checkWin()) {
            statusLabel.setText("Победил " + (xTurn ? "X" : "O"));
            saveGame((xTurn ? "X" : "O"));
            return;
        }

        xTurn = !xTurn;

        if (mode.equals("ai") && !xTurn) {
            aiMove();
        }
    }

    private void saveGame(String winnerName){
        GameHistoryService historyManager = new GameHistoryService();
        GameRecord record = new GameRecord(
                prefs.get("mode","human"),
                prefs.get("difficulty", "easy"),
                winnerName,
                LocalDateTime.now()
        );
        historyManager.addRecord(record);
    }
    private void aiMove() {
        Random random = new Random();
        int row, col;
        do {
            row = random.nextInt(3);
            col = random.nextInt(3);
        } while (!board[row][col].isEmpty());

        board[row][col] = "O";
        drawBoard();
        xTurn = true;
    }

    private boolean checkWin() {
        for (int i = 0; i < 3; i++) {
            if (!board[i][0].isEmpty() && board[i][0].equals(board[i][1]) && board[i][1].equals(board[i][2]))
                return true;
            if (!board[0][i].isEmpty() && board[0][i].equals(board[1][i]) && board[1][i].equals(board[2][i]))
                return true;
        }
        if (!board[0][0].isEmpty() && board[0][0].equals(board[1][1]) && board[1][1].equals(board[2][2]))
            return true;
        if (!board[0][2].isEmpty() && board[0][2].equals(board[1][1]) && board[1][1].equals(board[2][0]))
            return true;

        return false;
    }

    @FXML
    private void closeWindow() {
        Stage stage = (Stage) gameGrid.getScene().getWindow();
        stage.close();
    }
}
