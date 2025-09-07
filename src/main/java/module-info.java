module entertainment.fi.tictactoegame {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.prefs;
    requires static lombok;

    opens entertainment.fi.tictactoegame.controller to javafx.fxml;
    opens entertainment.fi.tictactoegame to javafx.fxml;
    exports entertainment.fi.tictactoegame;
}