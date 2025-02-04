package edu.bsu.cs;

import java.util.List;
import java.util.Scanner;

public class ConsoleView {
    private static final Scanner scanner = new Scanner(System.in);

    public static String getUserInput() {
        System.out.print("Enter Wikipedia article title: ");
        return scanner.nextLine().trim();
    }

    public static void displayRevisions(List<WikipediaRevision> revisions) {
        System.out.println("\nRecent Wikipedia Edits:");
        for (WikipediaRevision revision : revisions) {
            System.out.println(revision);
        }
    }

    public static void displayError(String message) {
        System.err.println("Error: " + message);
    }


}
