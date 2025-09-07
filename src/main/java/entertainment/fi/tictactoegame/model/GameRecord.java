package entertainment.fi.tictactoegame.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
public class GameRecord implements Serializable {
    private String gameMode;
    private String difficulty;
    private String winner;
    private LocalDateTime date;


    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        String mode = "";
        String d = "";
        switch (gameMode){
            case "human":
                mode = "Человек vs Человек";
                break;
            case "ai":
                mode = "Человек vs Компьютер";
                break;
        }
        switch (difficulty){
            case "easy":
                d = "Лёгкая";
                break;
            case "medium":
                d = "Средняя";
                break;
            case "hard":
                d = "Сложная";
                break;
            default:
                d = "-";
                break;
        }
        if(gameMode.equals("human")) d = " - ";
        else{
                 if(winner.equals("X"))
                     winner = "Человек";
                 else winner = "Компьютер";
        }
        return String.format("%s | Режим: %s | Сложность: %s | Победитель: %s",
                date.format(formatter),
                mode,
                d,
                winner);
    }
}
