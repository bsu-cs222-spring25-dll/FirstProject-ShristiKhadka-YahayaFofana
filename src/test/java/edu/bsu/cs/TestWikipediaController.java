package edu.bsu.cs;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import java.util.Arrays;
import java.util.List;

public class TestWikipediaController {
    @Mock
    private ConsoleView mockView;

    @Mock
    private WikipediaApi mockApi;

    @InjectMocks
    private WikipediaController controller;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this); // Initialize mocks
    }

    @Test
    public void testStart_SuccessfulFetch() throws Exception {
        // Arrange: Set up mock behavior
        String articleTitle = "Java";
        String jsonResponse = "{ \"query\": { \"pages\": { \"123\": { \"revisions\": [{ \"timestamp\": \"2025-02-08\", \"user\": \"tester\" }] }}}}";

        List<WikipediaRevision> mockRevisions = Arrays.asList(
                new WikipediaRevision("tester", "2025-02-08")
        );

        // Simulate the user input and API call
        when(mockView.getUserInput()).thenReturn(articleTitle);
        when(mockApi.fetchWikipediaData(articleTitle)).thenReturn(jsonResponse);
        when(WikipediaRevisionParser.parseWikipediaResponse(jsonResponse)).thenReturn(mockRevisions);

        // Act: Call the start method
        controller.start();

        // Assert: Verify interactions and output
        verify(mockView).getUserInput();
        verify(mockView).displayRevisions(mockRevisions);
        verify(mockView, never()).displayError(anyString()); // Ensure no error displayed
    }

    @Test
    public void testStart_ArticleNotFound() throws Exception {
        // Arrange: Set up mock behavior
        String articleTitle = "NonExistentArticle";
        String jsonResponse = "{}"; // Empty response, meaning article not found

        // Simulate the user input and API call
        when(mockView.getUserInput()).thenReturn(articleTitle);
        when(mockApi.fetchWikipediaData(articleTitle)).thenReturn(jsonResponse);

        // Act: Call the start method
        controller.start();

        // Assert: Verify that the error message was displayed
        verify(mockView).getUserInput();
        verify(mockView).displayError("Wikipedia article not found.");
        verify(mockView, never()).displayRevisions(anyList()); // Ensure revisions are not displayed
    }

    @Test
    public void testStart_ApiFailure() throws Exception {
        // Arrange: Set up mock behavior
        String articleTitle = "Java";
        when(mockView.getUserInput()).thenReturn(articleTitle);
        when(mockApi.fetchWikipediaData(articleTitle)).thenThrow(new RuntimeException("API failure"));

        // Act: Call the start method
        controller.start();

        // Assert: Verify the error handling
        verify(mockView).getUserInput();
        verify(mockView).displayError("Failed to retrieve Wikipedia data: API failure");
        verify(mockView, never()).displayRevisions(anyList()); // Ensure revisions are not displayed
    }
}
