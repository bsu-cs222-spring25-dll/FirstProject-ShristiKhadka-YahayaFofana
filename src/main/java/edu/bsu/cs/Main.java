package edu.bsu.cs;

//Interacts with the User and Fetches wikipedia data

public class Main {



    public static void main(String[] args) {
        ConsoleView view = new ConsoleView();
        WikipediaController controller = new WikipediaController(view);
        controller.start();
    }
}

