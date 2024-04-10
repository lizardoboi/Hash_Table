package com.example.hash_table;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private HashTable<String, Integer> hashTable;
    private TextArea outputTextArea;

    @Override
    public void start(Stage primaryStage) {
        hashTable = new HashTable<>();

        BorderPane root = new BorderPane();
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        Label label = new Label("Key:");
        TextField keyField = new TextField();
        Label valueLabel = new Label("Value:");
        TextField valueField = new TextField();
        Button addButton = new Button("Add");
        Button removeButton = new Button("Remove");
        Button printButton = new Button("Print Table");
        Button sizeButton = new Button("Table Size");
        Button countButton = new Button("Element Count");
        Button emptyButton = new Button("Is Empty");
        Button clearButton = new Button("Clear");
        Button searchButton = new Button("Search");
        Button chainingButton = new Button("Chaining");
        Button openAddressingButton = new Button("Open Addressing");

        outputTextArea = new TextArea();
        outputTextArea.setEditable(false);
        outputTextArea.setWrapText(true);
        outputTextArea.setPrefSize(500, 500);

        chainingButton.setOnAction(e -> {
            hashTable.setCollisionResolution(HashTable.CollisionResolution.CHAINING);
            updateOutputTextArea();
        });

        openAddressingButton.setOnAction(e -> {
            hashTable.setCollisionResolution(HashTable.CollisionResolution.OPEN_ADDRESSING);
            updateOutputTextArea();
        });

        addButton.setOnAction(e -> {
            String key = keyField.getText();
            Integer value = Integer.valueOf(valueField.getText());
            hashTable.put(key, value);
            keyField.clear();
            valueField.clear();
            updateOutputTextArea();
        });

        removeButton.setOnAction(e -> {
            String key = keyField.getText();
            hashTable.remove(key);
            keyField.clear();
            valueField.clear();
            updateOutputTextArea();
        });

        printButton.setOnAction(e -> {
            updateOutputTextArea();
        });

        sizeButton.setOnAction(e -> {
            outputTextArea.setText("Table Size: " + hashTable.getTableSize());
        });

        countButton.setOnAction(e -> {
            outputTextArea.setText("Element Count: " + hashTable.getElementCount());
        });

        emptyButton.setOnAction(e -> {
            outputTextArea.setText("Is Empty: " + hashTable.isEmpty());
        });

        clearButton.setOnAction(e -> {
            hashTable.clear();
            updateOutputTextArea();
        });

        searchButton.setOnAction(e -> {
            String key = keyField.getText();
            Integer value = hashTable.search(key);
            if (value != null) {
                outputTextArea.setText("Value for key " + key + ": " + value);
            } else {
                outputTextArea.setText("Key not found");
            }
        });

        vbox.getChildren().addAll(label, keyField, valueLabel, valueField, addButton, removeButton, printButton, sizeButton, countButton, emptyButton, clearButton, searchButton,chainingButton, openAddressingButton, outputTextArea);
        root.setCenter(vbox);

        Scene scene = new Scene(root, 1000, 1000);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Hash_Table");
        primaryStage.show();
    }

    private void updateOutputTextArea() {
        outputTextArea.setText("Hash table structure:\n" + hashTable.getTableAsString());
        outputTextArea.appendText("\nCollision Resolution: " + hashTable.getCollisionResolution());
    }

    public static void main(String[] args) {
        launch(args);
    }
}