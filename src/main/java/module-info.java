module com.example.smt {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.smt to javafx.fxml;
    exports com.example.smt;
}