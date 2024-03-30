import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.*;

public class KanbanBoardGUI extends Application {
    private Map<String, List<String>> lists; // Store lists and their corresponding notes
    private Map<String, String> listStages; // To store the stage for each list
    // Map to store the priority of each note. Note IDs as keys for simplicity in this example.
    private Map<String, String> notePriorities;
    // Class-level variable
    private Map<String, String> listPriorities; // Store the priority for each list


    @Override
    public void start(Stage primaryStage) {
        lists = new HashMap<>();
        listStages = new HashMap<>();
        notePriorities = new HashMap<>();
        listPriorities = new HashMap<>(); // Initialize list priorities map

        Scene scene = createLoginScene(primaryStage);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Kanban Board Plus Login");
        primaryStage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null); // No header text
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private Scene createLoginScene(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label userName = new Label("User Name:");
        TextField userTextField = new TextField();
        Label pw = new Label("Password:");
        PasswordField pwBox = new PasswordField();
        Button btn = new Button("Sign in");

        btn.setOnAction(e -> {
            String username = userTextField.getText();
            String password = pwBox.getText();
            if (authenticate(username, password)) {
                primaryStage.setScene(createKanbanBoardScene(primaryStage));
                primaryStage.setTitle("Kanban Board Plus");
            } else {
                showAlert("Authentication Failed", "Invalid username or password. Please try again.");
            }
        });        

        grid.add(userName, 0, 1);
        grid.add(userTextField, 1, 1);
        grid.add(pw, 0, 2);
        grid.add(pwBox, 1, 2);
        grid.add(btn, 1, 4);

        return new Scene(grid, 300, 275);
    }

    private boolean authenticate(String username, String password) {
        return !username.isEmpty() && !password.isEmpty();
    }

    private Scene createKanbanBoardScene(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #f0f4f8;"); // Light background for the whole scene
        
        // Styling the toolbar and add list button
        ToolBar toolBar = new ToolBar();
        toolBar.setStyle("-fx-background-color: #394867; -fx-padding: 10;"); // Darker background for toolbar
        Button addListButton = createStyledButton("Add List", "#5C6BC0", "#ffffff"); // Blue button
        toolBar.getItems().add(addListButton);
        borderPane.setTop(toolBar);

        VBox mainContent = new VBox(10);
        mainContent.setAlignment(Pos.TOP_CENTER);
        borderPane.setCenter(mainContent);

        addListButton.setOnAction(event -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Add List");
            dialog.setHeaderText(null);
            dialog.setContentText("Enter the name of the list:");
            dialog.showAndWait().ifPresent(name -> {
                if (!lists.containsKey(name)) {
                    lists.put(name, new ArrayList<>());
                    refreshLists(mainContent, primaryStage);
                }
            });
        });

        refreshLists(mainContent, primaryStage);

        return new Scene(borderPane, 400, 600);
    }

    private Button createStyledButton(String text, String bgColor, String textColor) {
        Button button = new Button(text);
        button.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: %s;", bgColor, textColor));
        // Adding hover effect
        button.setOnMouseEntered(e -> button.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: %s; -fx-opacity: 0.85;", bgColor, textColor)));
        button.setOnMouseExited(e -> button.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: %s;", bgColor, textColor)));
        return button;
    }

    private VBox createListPane(String listName, String listStage) {
        VBox listPane = new VBox(10);
        listPane.setPadding(new Insets(10));
        listPane.setStyle(getListStyleByStage(listStage));
    
        // Header container for the priority ComboBox, aligned to the top-right
        HBox header = new HBox(10);
        header.setAlignment(Pos.TOP_RIGHT);
        header.setPadding(new Insets(0, 0, 10, 0)); // Add some padding at the bottom of the header
    
        // Priority label
        Label priorityLabel = new Label("Priority:");
        priorityLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #333333; -fx-font-size: 14px;");
    
        // Priority ComboBox
        ComboBox<String> priorityComboBox = new ComboBox<>();
        priorityComboBox.getItems().addAll("High", "Medium", "Low");
        priorityComboBox.setValue(listPriorities.getOrDefault(listName, "Medium"));
        priorityComboBox.setOnAction(event -> {
            // Update the priority in the listPriorities map when changed
            listPriorities.put(listName, priorityComboBox.getValue());
        });
    
        // Add the priority label and ComboBox to the header
        header.getChildren().addAll(priorityLabel, priorityComboBox);
    
        // Styling the list name label and positioning it at the top of the list pane
        Label listNameLabel = new Label(listName);
        listNameLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #333333; -fx-font-size: 16px;");
        listNameLabel.setMaxWidth(Double.MAX_VALUE);
        listNameLabel.setAlignment(Pos.CENTER); // Center the list name label
    
        // Add the list name label and header to the list pane
        listPane.getChildren().addAll(listNameLabel, header);
    
        return listPane;
    }
    
    
    
    private String getListStyleByStage(String listStage) {
        String color;
        switch (listStage) {
            case "To Do":
                color = "#FFD54F"; // Yellow
                break;
            case "In Progress":
                color = "#29B6F6"; // Blue
                break;
            case "Done":
                color = "#81C784"; // Green
                break;
            default:
                color = "#eeeeee"; // Grey for undefined stages
                break;
        }
        return "-fx-background-color: " + color + "; " +
               "-fx-border-color: #cccccc; " +
               "-fx-background-radius: 5; " +
               "-fx-border-radius: 5;";
    }
    

    private void refreshLists(VBox mainContent, Stage primaryStage) {
        mainContent.getChildren().clear();
        lists.keySet().forEach(listName -> {
            // Retrieve the stage of the list to apply color styling
            String listStage = listStages.getOrDefault(listName, "To Do");
    
            // Create the list pane with the appropriate styling
            VBox listPane = createListPane(listName, listStage);
    
            HBox listItem = new HBox(10);
            listItem.setAlignment(Pos.CENTER_LEFT);
    
            ComboBox<String> stageComboBox = new ComboBox<>();
            stageComboBox.getItems().addAll("To Do", "In Progress", "Done");
            stageComboBox.setValue(listStage); // Use the current stage value
            stageComboBox.setOnAction(event -> {
                listStages.put(listName, stageComboBox.getValue());
                refreshLists(mainContent, primaryStage); // Refresh to update stage colors
            });
            listItem.getChildren().add(stageComboBox);
    
            Label listLabel = new Label(listName);
            listLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #333;");
            listItem.getChildren().add(listLabel);
    
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);
            listItem.getChildren().add(spacer);
    
            Button accessButton = new Button("Access");
            accessButton.setOnAction(event -> primaryStage.setScene(switchToListScene(primaryStage, listName)));
            listItem.getChildren().add(accessButton);
    
            Button deleteButton = new Button("Delete");
            deleteButton.setOnAction(event -> {
                lists.remove(listName);
                listStages.remove(listName); // Also remove the stage from the listStages map
                refreshLists(mainContent, primaryStage);
            });
            listItem.getChildren().add(deleteButton);
    
            listPane.getChildren().add(listItem);
    
            lists.get(listName).forEach(note -> {
                HBox noteItem = new HBox(10);
                noteItem.setAlignment(Pos.CENTER_LEFT);
                
                Label noteLabel = new Label(note); // Assuming 'note' is the note text or ID
                noteLabel.setStyle("-fx-padding: 5;");
                noteItem.getChildren().add(noteLabel);
                
                // Dropdown for note priority
                ComboBox<String> priorityComboBox = new ComboBox<>();
                priorityComboBox.getItems().addAll("High", "Medium", "Low");
                priorityComboBox.setValue(notePriorities.getOrDefault(note, "Medium"));
                priorityComboBox.setOnAction(e -> notePriorities.put(note, priorityComboBox.getValue()));
                noteItem.getChildren().add(priorityComboBox);
                
                listPane.getChildren().add(noteItem);
            });
    
            mainContent.getChildren().add(listPane);
        });
    }

    private Scene switchToListScene(Stage primaryStage, String listName) {
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10));
    
        ToolBar toolBar = new ToolBar();
        Button backButton = new Button("Go Back");
        backButton.setOnAction(event -> primaryStage.setScene(createKanbanBoardScene(primaryStage)));
        toolBar.getItems().add(backButton);
        borderPane.setTop(toolBar);
    
        // Main content area where the list and its tasks will be shown
        VBox mainContent = new VBox(10);
        mainContent.setPadding(new Insets(10));
    
        // Title/header for the list
        Label listHeader = new Label("List: " + listName);
        listHeader.setStyle("-fx-font-weight: bold; -fx-font-size: 24px;");
        mainContent.getChildren().add(listHeader);
    
        // Input field for adding a new task
        TextField newTaskField = new TextField();
        newTaskField.setPromptText("Enter a new task...");
        Button addTaskButton = new Button("Add Task");
        addTaskButton.setOnAction(event -> {
            String task = newTaskField.getText();
            if (!task.isEmpty()) {
                lists.get(listName).add(task); // Add new task to the list
                newTaskField.clear(); // Clear the input field
                refreshListDetails(mainContent, listName); // Refresh the list details view
            }
        });
    
        HBox addTaskBar = new HBox(5, newTaskField, addTaskButton);
        mainContent.getChildren().add(addTaskBar);
    
        // Method to refresh the list details view
        refreshListDetails(mainContent, listName);
    
        borderPane.setCenter(new ScrollPane(mainContent)); // Use a ScrollPane for the content area
    
        return new Scene(borderPane, 600, 400);
    }

    private void refreshListDetails(VBox mainContent, String listName) {
        // Clear previous content but keep the header and add task bar
        if (mainContent.getChildren().size() > 2) {
            mainContent.getChildren().remove(2, mainContent.getChildren().size());
        }
    
        // Display tasks for the list
        List<String> tasks = lists.getOrDefault(listName, new ArrayList<>());
        for (int i = 0; i < tasks.size(); i++) {
            String task = tasks.get(i); // Get the current task
            HBox taskItem = new HBox(10);
            taskItem.setPadding(new Insets(5));
            taskItem.setStyle("-fx-border-color: #cccccc; -fx-padding: 10; -fx-border-radius: 5;");
    
            TextField taskField = new TextField(task);
            taskField.setEditable(false); // Start as non-editable
            taskField.setMaxWidth(Double.MAX_VALUE);
            HBox.setHgrow(taskField, Priority.ALWAYS);
    
            Button editButton = new Button("Edit");
            int finalI = i; // Index for use within the lambda
            editButton.setOnAction(event -> {
                taskField.setEditable(true);
                taskField.requestFocus();
                editButton.setVisible(false); // Hide the edit button when in edit mode
            });
    
            Button saveButton = new Button("Save");
            saveButton.setOnAction(event -> {
                tasks.set(finalI, taskField.getText()); // Update the task name
                taskField.setEditable(false); // Make the field non-editable again
                editButton.setVisible(true); // Show the edit button again
                refreshListDetails(mainContent, listName); // Refresh the list details view
            });
    
            Button deleteButton = new Button("Delete");
            deleteButton.setOnAction(event -> {
                tasks.remove(finalI); // Remove task from the list
                refreshListDetails(mainContent, listName); // Refresh the list details view
            });
    
            // Only show the save button if the text field is editable
            taskField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
                if (!isNowFocused && taskField.isEditable()) {
                    saveButton.fire();
                }
            });
    
            taskItem.getChildren().addAll(taskField, editButton, saveButton, deleteButton);
            mainContent.getChildren().add(taskItem);
        }
    }
            
}            
