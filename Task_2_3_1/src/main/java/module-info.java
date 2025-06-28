module ru.nsu.lyskov {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens ru.nsu.lyskov to javafx.fxml;
    opens ru.nsu.lyskov.controllers to javafx.fxml;

    exports ru.nsu.lyskov;
    exports ru.nsu.lyskov.controllers;
    exports ru.nsu.lyskov.models;
    opens ru.nsu.lyskov.models to javafx.fxml;
}