package UserInterface;

import Database.MongoDbConnection;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.bson.Document;
import org.bson.types.ObjectId;

public class UI extends Application {
    private TextField idField;
    private TextField nameField;
    private TextField ageField;
    private TextField cityField;
    private MongoCollection<Document> collection;

    @Override
    public void start(Stage primaryStage) {

        // MONGODB CONNECTION
        MongoDbConnection mongoConnection = new MongoDbConnection();
        mongoConnection.init();
        MongoDatabase db = mongoConnection.getDatabase();
        collection = db.getCollection("users");

        // LABELS
        Label idLabel = new Label("ID:");
        Label nameLabel = new Label("Name:");
        Label ageLabel = new Label("Age:");
        Label cityLabel = new Label("City:");

        // TEXTFIELDS
        idField = new TextField();
        nameField = new TextField();
        ageField = new TextField();
        cityField = new TextField();

        // BUTTONS
        Button addButton = new Button("Add");
        Button readButton = new Button("Read");
        Button updateButton = new Button("Update");
        Button deleteButton = new Button("Delete");

        // CSS STYLING
        addButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px;");
        readButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px;");
        updateButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px;");
        deleteButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px;");
        addButton.setMinWidth(150);
        readButton.setMinWidth(150);
        updateButton.setMinWidth(150);
        deleteButton.setMinWidth(150);

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

        // ADD BUTTON EVENT
        addButton.setOnAction(e -> {
            if (checkUserInputs()) {
                String name = nameField.getText();
                int age = Integer.parseInt(ageField.getText());
                String city = cityField.getText();
                addToCollection(name, age, city);
            }
        });
        // READ BUTTON EVENT
        readButton.setOnAction(e -> {
            if (idField.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Empty ID field");
                alert.setContentText("Please fill the ID field");
                alert.showAndWait();
                return;
            }
            String id = idField.getText();
            try {
                retrieveCollection(id);
            } catch (Exception ex) {
                System.out.println("Error: " + ex);
            }
        });
        // UPDATE BUTTON EVENT
        updateButton.setOnAction(e -> {
            if (checkId() && checkUserInputs()) {
                String id = idField.getText();
                String name = nameField.getText();
                int age = Integer.parseInt(ageField.getText());
                String city = cityField.getText();
                try {
                    updateCollection(id, name, age, city);
                } catch (Exception ex) {
                    System.out.println("Error: " + ex);
                }
            }
        });
        // DELETE BUTTON EVENT
        deleteButton.setOnAction(e -> {
            if (checkId()) {
                String id = idField.getText();
                try {
                    deleteCollection(id);
                } catch (Exception ex) {
                    System.out.println("Error: " + ex);
                }
            }
        });
    }
    // CHECK USER INPUTS
    private boolean checkUserInputs() {
        if (nameField.getText().isEmpty() || ageField.getText().isEmpty() || cityField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Empty fields");
            alert.setContentText("Please fill all fields");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    private boolean checkId() {
        if (idField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Empty ID field");
            alert.setContentText("Please fill the ID field");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    // CREATE DOCUMENT
    private void addToCollection(String name, int age, String city) {
        Document document = new Document();
        document.append("name", name);
        document.append("age", age);
        document.append("city", city);
        try{
            collection.insertOne(document);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Document added successfully");
            alert.showAndWait();
        } catch (Exception e) {
            System.out.println("Error: " + e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error adding to collection");
            alert.setContentText("Error: " + e);
            alert.showAndWait();
        }
    }

    // READ DOCUMENT
    private void retrieveCollection(String id) {
        try {
            ObjectId idSearch = new ObjectId(id);
            Document query = new Document("_id", idSearch);
            Document result = collection.find(query).first();
            if (result != null) {
                String idResult = result.get("_id").toString();
                String documentName = result.getString("name");
                int age = result.getInteger("age");
                String city = result.getString("city");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Document found");
                alert.setHeaderText("ID: " + idResult + "\nName: " + documentName + "\nAge: " + age + "\nCity: " + city);
                alert.showAndWait();
            } else {
                System.out.println("Document not found");
            }
        } catch (Exception e) {
            System.out.println("Error retrieving document: " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error retrieving document");
            alert.setContentText("Error: " + e.getMessage());
            alert.showAndWait();
        }
    }


    // UPDATE DOCUMENT
    private void updateCollection(String id, String name, int age, String city) {
        try {
            ObjectId objectId = new ObjectId(id);
            Document query = new Document("_id", objectId);
            Document update = new Document();
            update.append("name", name);
            update.append("age", age);
            update.append("city", city);
            Document updateQuery = new Document("$set", update);
            collection.updateOne(query, updateQuery);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Document updated successfully");
            alert.showAndWait();
        } catch (Exception e) {
            System.out.println("Error: " + e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error updating document");
            alert.setContentText("Error: " + e);
            alert.showAndWait();
        }
    }
    // DELETE DOCUMENT
    private void deleteCollection(String id) {
        try {
            ObjectId objectId = new ObjectId(id);
            Document query = new Document("_id", objectId);
            collection.deleteOne(query);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Document deleted successfully");
            alert.showAndWait();
        } catch (Exception e) {
            System.out.println("Error: " + e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error deleting document");
            alert.setContentText("Error: " + e);
            alert.showAndWait();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}

