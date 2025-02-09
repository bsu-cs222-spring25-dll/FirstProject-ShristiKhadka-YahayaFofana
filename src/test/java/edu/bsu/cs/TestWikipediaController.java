package edu.bsu.cs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

class WikipediaControllerTest {

    private ConsoleView mockView;
    private WikipediaController controller;

    @BeforeEach
    void setUp() {
        mockView = Mockito.mock(ConsoleView.class);
        controller = new WikipediaController(mockView);
    }

    @Test
    void testStart_SuccessfulDataRetrieval() throws Exception {
        // Simulate user input
        String simulatedInput = "Java (programming language)\n";
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        // Mock the WikipediaApi and WikipediaRevisionParser
        String jsonResponse = "{\"query\":{\"revisions\":[{\"timestamp\":\"2023-10-01T12:00:00Z\",\"user\":\"Alice\"}]}}";
        List<WikipediaRevision> revisions = List.of(new WikipediaRevision("2023-10-01T12:00:00Z", "Alice"));

        when(WikipediaApi.fetchWikipediaData("Java (programming language)")).thenReturn(jsonResponse);
        when(WikipediaRevisionParser.parseWikipediaResponse(jsonResponse)).thenReturn(revisions);

        // Act
        controller.start();

        // Assert
        verify(mockView).displayRevisions(revisions);
    }

    @Test
    void testStart_ArticleNotFound() throws Exception {
        // Simulate user input
        String simulatedInput = "Nonexistent Article\n";
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        // Mock the WikipediaApi and WikipediaRevisionParser
        String jsonResponse = "{\"query\":{\"revisions\":[]}}";

        when(WikipediaApi.fetchWikipediaData("Nonexistent Article")).thenReturn(jsonResponse);
        when(WikipediaRevisionParser.parseWikipediaResponse(jsonResponse)).thenReturn(Collections.emptyList());

        // Act
        controller.start();

        // Assert
        verify(mockView).displayError("Wikipedia article not found.");
    }

    @Test
    void testStart_ApiThrowsException() throws Exception {
        // Simulate user input
        String simulatedInput = "Java (programming language)\n";
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        // Mock the WikipediaApi to throw an exception
        Exception exception = new Exception("API unavailable");
        when(WikipediaApi.fetchWikipediaData("Java (programming language)")).thenThrow(exception);

        // Act
        controller.start();

        // Assert
        verify(mockView).displayError("Failed to retrieve Wikipedia data: " + exception.getMessage());
    }
}