module com.example.tap2024 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.tap2024 to javafx.fxml;
    exports com.example.tap2024;
}