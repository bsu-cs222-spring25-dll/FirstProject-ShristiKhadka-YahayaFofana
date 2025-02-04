package edu.bsu.cs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class WikipediaApiClient {

    // Fetches JSON data from Wikipedia API based on the given article title
    public static String fetchWikipediaData(String articleTitle) throws IOException {
        // Encode the article title to be URL-safe
        String encodedTitle = URLEncoder.encode(articleTitle, StandardCharsets.UTF_8);

        // Wikipedia API URL with encoded title
        String apiUrl = "https://en.wikipedia.org/w/api.php?action=query&format=json&prop=revisions&titles="
                + encodedTitle + "&rvprop=timestamp|user&rvlimit=21&redirects=1";

        // Create URL object
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Set request properties (User-Agent is required by Wikipedia API)
        connection.setRequestProperty("User-Agent", "WikipediaClient/1.0 (your_email@bsu.edu)");
        connection.setRequestProperty("Accept", "application/json");

        // Read response data
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return response.toString(); // Return JSON response
        } finally {
            connection.disconnect(); // Close the connection
        }
    }
}
