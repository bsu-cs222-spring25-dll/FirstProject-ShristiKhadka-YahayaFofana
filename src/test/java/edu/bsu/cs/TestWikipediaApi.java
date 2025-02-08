
package edu.bsu.cs;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class TestWikipediaApi {

    @Test
    void testUrlEncoding() throws IOException {
        String articleTitle = "Albert Einstein";
        String encodedTitle = URLEncoder.encode(articleTitle, StandardCharsets.UTF_8);

        assertEquals("Albert+Einstein", encodedTitle);
    }

    @Test
    void testFetchWikipediaData_ValidTitle() throws IOException {
        WikipediaApi api = new WikipediaApi();
        String response = api.fetchWikipediaData("Albert Einstein");

        assertNotNull(response, "Response should not be null");
        assertTrue(response.contains("query"), "Response should contain 'query'");
    }



}
