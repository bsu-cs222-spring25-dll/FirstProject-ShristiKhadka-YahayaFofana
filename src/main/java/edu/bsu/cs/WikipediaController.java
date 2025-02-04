package edu.bsu.cs;

import java.util.List;

public class WikipediaController {
    private final ConsoleView view;

    public WikipediaController(ConsoleView view) {
        this.view = view;
    }

    public void start() {
        try {
            // Step 1: Get the article title from the user
            String articleTitle = ConsoleView.getUserInput();

            // Step 2: Fetch JSON data from Wikipedia API
            String jsonResponse = WikipediaApiClient.fetchWikipediaData(articleTitle);

            // Step 3: Parse JSON to extract revision data
            List<WikipediaRevision> revisions = WikipediaParser.parseWikipediaResponse(jsonResponse);

            // Step 4: Handle case where article is missing
            if (revisions.isEmpty()) {
                view.displayError("Wikipedia article not found.");
            } else {
                // Step 5: Format parsed revisions for display
                String formattedRevisions = WikipediaRevisionFormatter.formatRevisions(revisions);

                // Step 6: Display the formatted revisions
                System.out.println(formattedRevisions);
            }
        } catch (Exception e) {
            view.displayError("Failed to retrieve Wikipedia data: " + e.getMessage());
        }
    }
}
