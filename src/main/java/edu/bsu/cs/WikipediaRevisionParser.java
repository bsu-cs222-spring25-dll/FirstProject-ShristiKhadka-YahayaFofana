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

        JSONObject pages = query.optJSONObject("pages");
        if (pages == null || pages.isEmpty()) {
            throw new Exception("Invalid response: 'pages' not found.");
        }

        for (String pageId : pages.keySet()) {
            JSONObject page = pages.getJSONObject(pageId);
            if (page.has("missing")) {
                throw new Exception("Error: Article not found.");
            }
            if (page.has("redirects")) {
                throw new Exception("Error: Article was redirected.");
            }
        }
        return pages;
    }

    // Extracts revision data from JSON
    private static List<WikipediaRevision> extractRevisions(JSONObject pages) {
        List<WikipediaRevision> revisions = new ArrayList<>();

        for (String pageId : pages.keySet()) {
            JSONObject page = pages.getJSONObject(pageId);
            JSONArray revisionsArray = page.optJSONArray("revisions");

            if (revisionsArray != null) {
                for (int i = 0; i < revisionsArray.length(); i++) {
                    JSONObject revision = revisionsArray.getJSONObject(i);
                    String user = revision.optString("user", "Unknown User");
                    String timestamp = revision.optString("timestamp", "Unknown Timestamp");
                    revisions.add(new WikipediaRevision(user, timestamp));
                }
            }
        }
        return revisions;
    }
}
