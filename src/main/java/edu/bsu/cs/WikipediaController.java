package edu.bsu.cs;


public class WikipediaController
{


    public WikipediaController(ConsoleView view) {

    }

    public void start()
    {
        try{
            String articleTitle = ConsoleView.getUserInput();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
