package edu.bsu.cs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestConsoleView {

    @Test
    void testUserInput() {
        // Simulate user input
        String simulatedInput = "Some Article Title\n";
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        // when getUserInput() is called, it will read from the simulated input
        ConsoleView consoleView = new ConsoleView();
        String result = consoleView.getUserInput();

        // Check if the result matches the simulated input
        assertEquals("Some Article Title", result);
    }


    @Test

    public void testDisplayRevisions(){

        List<WikipediaRevision> revisions = Arrays.asList(
                new WikipediaRevision("User1", "2025-01-06T12:08:11Z"),
                new WikipediaRevision("User2", "2025-01-06T12:00:53Z")
                //We create a list of WikipediaRevision objects to simulate recent edits.
                //We check if the expected strings (like "Recent Wikipedia Edits:",
                // and the revision details like "User1", "2025-01-06T12:00:53Z") appear in the output.
        );

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);
        //We use ByteArrayOutputStream to capture the console output.
        //System.setOut(printStream) is used to redirect System.out to our ByteArrayOutputStream.
        //After the method executes, we can check the content of outputStream.toString().

        ConsoleView.displayRevisions(revisions);

        // Get the output as a string
        String output = outputStream.toString();

        //Check to see if output has the same expected tests
        Assertions.assertTrue(output.contains("Recent Wikipedia Edits:"));
        Assertions.assertTrue(output.contains("User1"));
        Assertions.assertTrue(output.contains("2025-01-06T12:08:11Z"));
        Assertions.assertTrue(output.contains("User2"));
        Assertions.assertTrue(output.contains("2025-01-06T12:00:53Z"));
    }

    @Test

    public void testDisplayError(){
        String errorMessage = "Something went wrong";

        // Capture the console error output by redirecting System.err
        ByteArrayOutputStream errorOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(errorOutputStream);
        System.setErr(printStream);

        ConsoleView.displayError(errorMessage);

        // Get the error output as a string
        String errorOutput = errorOutputStream.toString();

        Assertions.assertTrue(errorOutput.contains("Error: " + errorMessage));
    }
}
