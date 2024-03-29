package com.kanbanplus.gui;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class KanbanBoardLogin extends Application {

    @Override
    public void start(Stage primaryStage) {
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
            System.out.println("Username: " + userTextField.getText() + " Password: " + pwBox.getText());
        });

        // Create scene and show on the stage
        Scene scene = new Scene(grid, 300, 275);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Kanban Board Plus Login");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
