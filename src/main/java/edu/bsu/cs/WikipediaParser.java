package edu.bsu.cs;


import org.json.JSONObject;
import org.json.JSONArray;
import java.util.ArrayList;
import java.util.List;

public class WikipediaParser {
    public static List<WikipediaRevision> parseWikipediaResponse(String jsonResponse) throws Exception {
        List<WikipediaRevision> revisions = new ArrayList<>();

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

            // Handle redirects
            if (page.has("missing")) {
                throw new Exception("Error: Article not found.");
            }
            if (page.has("redirects")) {
                throw new Exception("Error: Article was redirected.");
            }

            JSONArray revisionsArray = page.optJSONArray("revisions");
            if (revisionsArray == null) {
                throw new Exception("No revision history found.");
            }

            for (int i = 0; i < revisionsArray.length(); i++) {
                JSONObject revision = revisionsArray.getJSONObject(i);
                String user = revision.optString("user", "Unknown User");
                String timestamp = revision.optString("timestamp", "Unknown Timestamp");

                revisions.add(new WikipediaRevision(user, timestamp));
            }
        }
        return revisions;
    }
}
