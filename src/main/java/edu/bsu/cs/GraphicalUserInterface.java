package edu.bsu.cs;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Modality;
import java.util.List;

public class GraphicalUserInterface extends Application {
    private TextField articleInput;
    private Button searchButton;
    private ListView<WikipediaRevision> revisionListView;
    private Label statusLabel;
    private VBox mainLayout;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Wikipedia Revision Viewer");


    }

    private void initializeComponents() {
        articleInput = new TextField();
        articleInput.setPromptText("Enter Wikipedia article title");

        searchButton = new Button("Search");

        revisionListView = new ListView<>();
        revisionListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(WikipediaRevision revision, boolean empty) {
                super.updateItem(revision, empty);
                if (empty || revision == null) {
                    setText(null);
                } else {
                    setText(String.format("Editor: %s\nTimestamp: %s\n",
                            revision.getUsername(),
                            revision.getTimestamp()));
                }
            }
        });

        statusLabel = new Label();
        statusLabel.setWrapText(true);
    }

    private void setupMainLayout() {
        mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(10));
        mainLayout.getChildren().addAll(
                new Label("Wikipedia Article Title:"),
                articleInput,
                searchButton,
                revisionListView,
                statusLabel
        );
    }



    public static void main(String[] args) {
        launch(args);
    }
}