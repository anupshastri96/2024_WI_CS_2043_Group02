package com.kanbanplus.gui;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kanbanplus.database.database;

public class KanbanBoardGUI extends Application {

    private Map<String, List<String>> lists; //store lists and their corresponding notes
	private Map<String, String> listStages; //to store the stage for each list
	Connection connector = database.openConnection();
    @Override
    public void start(Stage primaryStage) {
        // Initialize lists map
        lists = new HashMap<>();
		listStages = new HashMap<>();

        // Create the layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Create the components
        Label userName = new Label("User Name:");
        TextField userTextField = new TextField();
        Label pw = new Label("Password:");
        PasswordField pwBox = new PasswordField();
        Button btn = new Button("Sign in");

        // Add components to the layout
        grid.add(userName, 0, 1);
        grid.add(userTextField, 1, 1);
        grid.add(pw, 0, 2);
        grid.add(pwBox, 1, 2);
        grid.add(btn, 1, 4);

        // Set the action for the login button
        btn.setOnAction(e -> {
            // Handle login logic here
            String username = userTextField.getText();
            String password = pwBox.getText();
            try{
                if (database.checkPassword(connector, username, password)) {
                    // Successfully authenticated, show Kanban Board
                    primaryStage.setScene(createKanbanBoardScene(primaryStage));
                    primaryStage.setTitle("Kanban Board Plus");
                }
            }
            catch(Exception exception) {
                // Authentication failed, show error message
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Authentication Failed");
                alert.setHeaderText(null);
                alert.setContentText(exception.getMessage());
                alert.showAndWait();
            }
            
        });

        // Create scene and show on the stage
        Scene scene = new Scene(grid, 300, 275);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Kanban Board Plus Login");
        primaryStage.show();
    }


    private Scene createKanbanBoardScene(Stage primaryStage) {
        // scene for the kanban board after logging in
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10));

        ToolBar toolBar = new ToolBar();
        Button addListButton = new Button("Add List");
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

  private void refreshLists(VBox mainContent, Stage primaryStage) {
    mainContent.getChildren().clear();
    for (String listName : lists.keySet()) {
        VBox listPane = new VBox(10);
        listPane.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #000000;");

        // Create an HBox to hold the list label and buttons
        HBox listItem = new HBox(10);
        listItem.setAlignment(Pos.CENTER_LEFT);
	
		 // Create ComboBox with stages
        ComboBox<String> stageComboBox = new ComboBox<>();
        stageComboBox.getItems().addAll("To Do", "In Progress", "Done");
        stageComboBox.setValue(listStages.getOrDefault(listName, "To Do"));
        stageComboBox.setOnAction(event -> {
            listStages.put(listName, stageComboBox.getValue());
        });
        listItem.getChildren().add(stageComboBox);
		
        // Add the list label to the HBox
        Label listLabel = new Label(listName);
        listLabel.setStyle("-fx-font-weight: bold");
        listItem.getChildren().add(listLabel);
		
        // Add a Region to push buttons to the right
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        listItem.getChildren().add(spacer);

        // Add the access button
        Button accessButton = new Button("Access");
        accessButton.setOnAction(event -> {
            // Switch to a scene specific for the selected list
             primaryStage.setScene(switchToListScene(primaryStage, listName));
        });
        listItem.getChildren().add(accessButton);

        // Add the delete button and logic for deletion
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(event -> {
            lists.remove(listName);
            refreshLists(mainContent, primaryStage);
        });
        listItem.getChildren().add(deleteButton);

        listPane.getChildren().add(listItem);

        for (String note : lists.get(listName)) {
            Label noteLabel = new Label(note);
            listPane.getChildren().add(noteLabel);
        }

        mainContent.getChildren().add(listPane);
    }
}
	private Scene switchToListScene(Stage primaryStage, String listName){
	BorderPane borderPane = new BorderPane();
    borderPane.setPadding(new Insets(10));

    // Create a tool bar for the "go back" button
    ToolBar toolBar = new ToolBar();
    Button backButton = new Button("Go Back");
    backButton.setOnAction(event -> {
        primaryStage.setScene(createKanbanBoardScene(primaryStage));
    });
    toolBar.getItems().add(backButton);
    borderPane.setTop(toolBar);

        return new Scene(borderPane, 600, 600);
	}

    public static void main(String[] args) {
        launch(args);
    }
}