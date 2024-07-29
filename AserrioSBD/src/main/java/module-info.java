module ec.edu.espol.aserriosbd {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens ec.edu.espol.aserriosbd to javafx.fxml;
    exports ec.edu.espol.aserriosbd;
}
