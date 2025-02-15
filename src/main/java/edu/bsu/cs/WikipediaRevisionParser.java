package edu.bsu.cs;

import org.json.JSONObject;
import org.json.JSONArray;
import java.util.ArrayList;
import java.util.List;

public class WikipediaRevisionParser {

    // Parses JSON and returns a list of WikipediaRevision objects
    public static List<WikipediaRevision> parseWikipediaResponse(String jsonResponse) throws Exception {
        JSONObject pages = validateResponse(jsonResponse);
        return extractRevisions(pages);
    }

    // Validates JSON response and extracts the "pages" object
    private static JSONObject validateResponse(String jsonResponse) throws Exception {
        JSONObject jsonObject = new JSONObject(jsonResponse);
        JSONObject query = jsonObject.optJSONObject("query");

        if (query == null) {
            throw new Exception("Invalid response: 'query' not found.");
        }

        handleRedirects(query);

        JSONObject pages = query.optJSONObject("pages");
        if (pages == null || pages.isEmpty()) {
            throw new Exception("Invalid response: 'pages' not found.");
        }

        checkForMissingArticles(pages);
        return pages;
    }

    // Handles Wikipedia redirects
    private static void handleRedirects(JSONObject query) {
        JSONArray redirects = query.optJSONArray("redirects");
        if (redirects != null) {
            for (int i = 0; i < redirects.length(); i++) {
                JSONObject redirect = redirects.getJSONObject(i);
                String from = redirect.optString("from", "Unknown");
                String to = redirect.optString("to", "Unknown");
                System.out.println("Redirect from " + from + " to " + to);
            }
        }
    }

    // Checks if the requested article is missing
    private static void checkForMissingArticles(JSONObject pages) throws Exception {
        for (String pageId : pages.keySet()) {
            JSONObject page = pages.getJSONObject(pageId);
            if (page.has("missing")) {
                throw new Exception("Error: Article not found.");
            }
        }
    }

    // Extracts revision data from JSON
    private static List<WikipediaRevision> extractRevisions(JSONObject pages) {
        List<WikipediaRevision> revisions = new ArrayList<>();

        for (String pageId : pages.keySet()) {
            JSONObject page = pages.getJSONObject(pageId);
            JSONArray revisionsArray = page.optJSONArray("revisions");

            if (revisionsArray != null) {
                revisions.addAll(parseRevisions(revisionsArray));
            }
        }
        return revisions;
    }

    // Parses individual revision objects
    private static List<WikipediaRevision> parseRevisions(JSONArray revisionsArray) {
        List<WikipediaRevision> revisions = new ArrayList<>();

        for (int i = 0; i < revisionsArray.length(); i++) {
            JSONObject revision = revisionsArray.getJSONObject(i);
            String user = revision.optString("user", "Unknown User");
            String timestamp = revision.optString("timestamp", "Unknown Timestamp");
            revisions.add(new WikipediaRevision(user, timestamp));
        }
        return revisions;
    }
}

