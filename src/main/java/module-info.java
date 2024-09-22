module com.example.flappybirdfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.flappybirdfx to javafx.fxml;
    exports com.example.flappybirdfx;
}