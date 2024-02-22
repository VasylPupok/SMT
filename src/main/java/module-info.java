module com.example.smt {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    exports main;
    opens main to javafx.fxml;
    exports main.views;
    opens main.views to javafx.fxml;
}