package edu.bsu.cs;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.nio.file.Files;
import java.nio.file.Paths;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

class TestWikipediaRevisionParser {

    private String sampleJson;

    @BeforeEach
    void setUp() throws IOException {
        sampleJson = Files.readString(Paths.get("src/test/resources/sample.json"), StandardCharsets.UTF_8);
    }

    @Test
    void testParseWikipediaResponse_ValidResponse() throws Exception {
        List<WikipediaRevision> revisions = WikipediaRevisionParser.parseWikipediaResponse(sampleJson);
        assertFalse(revisions.isEmpty());
    }

    @Test
    void testParseWikipediaResponse_MissingQuery() {
        String jsonResponse = "{}";
        Exception exception = assertThrows(Exception.class, () ->
                WikipediaRevisionParser.parseWikipediaResponse(jsonResponse));
        assertEquals("Invalid response: 'query' not found.", exception.getMessage());
    }

    @Test
    void testParseWikipediaResponse_MissingPages() {
        String jsonResponse = "{\"query\": {}}";
        Exception exception = assertThrows(Exception.class, () ->
                WikipediaRevisionParser.parseWikipediaResponse(jsonResponse));
        assertEquals("Invalid response: 'pages' not found.", exception.getMessage());
    }

    @Test
    void testParseWikipediaResponse_MissingArticle() {
        String jsonResponse = """
            {
                "query": {
                    "pages": {
                        "12345": {
                            "missing": ""
                        }
                    }
                }
            }
        """;
        Exception exception = assertThrows(Exception.class, () ->
                WikipediaRevisionParser.parseWikipediaResponse(jsonResponse));
        assertEquals("Error: Article not found.", exception.getMessage());
    }


    @Test
    void testParseWikipediaResponse_NoRevisions() throws Exception {
        String jsonResponse = """
            {
                "query": {
                    "pages": {
                        "12345": {}
                    }
                }
            }
        """;
        List<WikipediaRevision> revisions = WikipediaRevisionParser.parseWikipediaResponse(jsonResponse);
        assertTrue(revisions.isEmpty());
    }
}
