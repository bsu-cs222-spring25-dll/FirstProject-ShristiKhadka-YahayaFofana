package edu.bsu.cs;

import java.util.List;

public class WikipediaRevisionFormatter {

    public static String formatRevisions(List<WikipediaRevision> revisions) {
        if (revisions.isEmpty()) {
            return "No recent edits found.";
        }

        StringBuilder formattedOutput = new StringBuilder("\nRecent Wikipedia Edits:\n");
        for (WikipediaRevision revision : revisions) {
            formattedOutput.append(revision).append("\n");
        }
        return formattedOutput.toString();
    }
}
