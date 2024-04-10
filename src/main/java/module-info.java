module com.example.hash_table {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.hash_table to javafx.fxml;
    exports com.example.hash_table;
}