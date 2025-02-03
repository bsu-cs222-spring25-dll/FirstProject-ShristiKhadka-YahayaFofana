package edu.bsu.cs;

import java.util.Scanner;

public class WikipediaRevisonClass {

    private String articletitle;

    public WikipediaRevisonClass(String title) {
        this.articletitle = title;
    }


    public String getArticletitle() {
        return articletitle;
    }

    public void setArticletitle(String title) {
        this.articletitle = title;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Title of Article: ");
        String title = scanner.nextLine();

        WikipediaRevisonClass article = new WikipediaRevisonClass(title);

        System.out.println("Article title is: " + article.getArticletitle());


    }
}
