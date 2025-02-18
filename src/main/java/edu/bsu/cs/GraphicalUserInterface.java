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
    private Label redirectLabel;
    private VBox mainLayout;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Wikipedia Revision Viewer");
        // Initialize components
        initializeComponents();

        // Set up the main layout
        setupMainLayout();

        // Add event handlers
        setupEventHandlers();

        // Create and set the scene
        Scene scene = new Scene(mainLayout, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
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

        redirectLabel = new Label();
        redirectLabel.setWrapText(true);
        redirectLabel.setStyle("-fx-text-fill: blue;");

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
                redirectLabel,
                revisionListView,
                statusLabel
        );
    }

    private void setupEventHandlers() {
        searchButton.setOnAction(e -> performSearch());

        // Add event handler for the Enter key
        articleInput.setOnAction(e -> performSearch());
    }

    private void performSearch() {
        String articleTitle = articleInput.getText().trim();

        if (articleTitle.isEmpty()) {
            showErrorDialog("Error", "Please enter an article title.");
            return;
        }

        // Disable UI during search
        setUIEnabled(false);
        statusLabel.setText("Searching...");
        redirectLabel.setText("");

        // Create a new thread for the Wikipedia API call
        Thread searchThread = new Thread(() -> {
            try {
                String jsonResponse = WikipediaApi.fetchWikipediaData(articleTitle);
                List<WikipediaRevision> revisions = WikipediaRevisionParser.parseWikipediaResponse(jsonResponse);

                Platform.runLater(() -> {
                    revisionListView.getItems().clear();
                    revisionListView.getItems().addAll(revisions);
                    statusLabel.setText("Found " + revisions.size() + " revisions");

                    // Display redirect information if available
                    WikipediaRevisionParser.getRedirectInfo().ifPresent(redirectInfo -> {
                        redirectLabel.setText(redirectInfo);
                    });

                    setUIEnabled(true);
                });
            } catch (Exception e) {
                Platform.runLater(() -> {
                    showErrorDialog("Error", "Failed to retrieve Wikipedia data: " + e.getMessage());
                    statusLabel.setText("Error occurred during search");
                    setUIEnabled(true);
                });
            }
        });

        searchThread.start();
    }

    private void setUIEnabled(boolean enabled) {
        articleInput.setDisable(!enabled);
        searchButton.setDisable(!enabled);
    }

    private void showErrorDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}