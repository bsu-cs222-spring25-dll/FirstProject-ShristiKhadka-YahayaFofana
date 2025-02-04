package edu.bsu.cs;

public class Main {
    public static void main(String[] args) {
        ConsoleView view = new ConsoleView();
        WikipediaController controller = new WikipediaController(view);
        controller.start();
    }
}
