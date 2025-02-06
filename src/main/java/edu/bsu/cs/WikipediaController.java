package edu.bsu.cs;

import java.util.List;

import static edu.bsu.cs.WikipediaApi.fetchWikipediaData;

public class WikipediaController {
    private final ConsoleView view;

    public WikipediaController(ConsoleView view) {
        this.view = view;
    }

    public void start() {
        try {
            //  Get the article title from the user
            String articleTitle = ConsoleView.getUserInput();

            // Fetch JSON data from Wikipedia API
            String jsonResponse = fetchWikipediaData(articleTitle);

            // Parse JSON to extract revision data
            List<WikipediaRevision> revisions = WikipediaRevisionParser.parseWikipediaResponse(jsonResponse);

            // Handle case where article is missing
            if (revisions.isEmpty()) {
                ConsoleView.displayError("Wikipedia article not found.");
            } else {
                //print output
                ConsoleView.displayRevisions(revisions);
            }
        } catch (Exception e) {
            ConsoleView.displayError("Failed to retrieve Wikipedia data: " + e.getMessage());
        }
    }
}
