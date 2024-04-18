package UserInterface;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class UI extends Application {

    @Override
    public void start(Stage primaryStage) {
        // LABELS
        Label idLabel = new Label("ID:");
        Label nameLabel = new Label("Name:");
        Label ageLabel = new Label("Age:");
        Label cityLabel = new Label("City:");

        // TEXTFIELDS
        TextField idField = new TextField();
        TextField nameField = new TextField();
        TextField ageField = new TextField();
        TextField cityField = new TextField();

        // BUTTONS
        Button addButton = new Button("Add");
        Button readButton = new Button("Read");
        Button updateButton = new Button("Update");
        Button deleteButton = new Button("Delete");

        // GRIDPANE LAYOUT
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        // ADD COMPONENTS TO GRIDPANE
        gridPane.add(idLabel, 0, 0);
        gridPane.add(nameLabel, 0, 1);
        gridPane.add(ageLabel, 0, 2);
        gridPane.add(cityLabel, 0, 3);

        gridPane.add(idField, 1, 0);
        gridPane.add(nameField, 1, 1);
        gridPane.add(ageField, 1, 2);
        gridPane.add(cityField, 1, 3);

        gridPane.add(addButton, 0, 4);
        gridPane.add(readButton, 1, 4);
        gridPane.add(updateButton, 0, 5);
        gridPane.add(deleteButton, 1, 5);

        // SCENE
        Scene scene = new Scene(gridPane, 400, 300);

        // STAGE
        primaryStage.setTitle("MongoDB CRUD Operations");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

