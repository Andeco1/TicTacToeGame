package entertainment.fi.tictactoegame.service;

import entertainment.fi.tictactoegame.model.GameRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GameHistoryService {
    private static final String FILE_PATH = "game_history.dat";
    private List<GameRecord> history;

    public GameHistoryService() {
        history = loadHistory();
    }

    public void addRecord(GameRecord record) {
        history.add(record);
        saveHistory();
    }

    public List<GameRecord> getHistory() {
        return history;
    }

    private void saveHistory() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(history);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<GameRecord> loadHistory() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<GameRecord>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
