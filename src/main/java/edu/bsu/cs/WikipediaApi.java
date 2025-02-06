package edu.bsu.cs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class WikipediaApi {
    // Fetches JSON data from Wikipedia API based on the given article title
    public static String fetchWikipediaData(String articleTitle) throws IOException {
        String apiUrl = buildWikipediaUrl(articleTitle);  // Build the URL separately
        return getApiResponse(apiUrl);  // Handle API request separately
    }

    // Constructs the Wikipedia API URL
    private static String buildWikipediaUrl(String articleTitle) {
        String encodedTitle = URLEncoder.encode(articleTitle, StandardCharsets.UTF_8);
        return "https://en.wikipedia.org/w/api.php?action=query&format=json&prop=revisions&titles="
                + encodedTitle + "&rvprop=timestamp|user&rvlimit=21&redirects=1";
    }

    //Handles the API request and returns JSON response as a string
    private static String getApiResponse(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("User-Agent", "WikipediaClient/1.0 (your_email@bsu.edu)");
        connection.setRequestProperty("Accept", "application/json");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        } finally {
            connection.disconnect();
        }
    }

}

