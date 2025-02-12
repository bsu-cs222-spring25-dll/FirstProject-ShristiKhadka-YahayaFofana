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

        if (revisions == null || revisions.isEmpty()) {
            System.out.println("No recent edits found.");
            return;
        }

        System.out.println(System.lineSeparator()+"Recent Wikipedia Edits:");
        int count = 1;
        for (WikipediaRevision revision : revisions) {
            System.out.println(count + ". " + revision);
            count++;
        }
    }

    public static void displayError(String message) {
        System.err.println("Error: " + message);
    }
}
