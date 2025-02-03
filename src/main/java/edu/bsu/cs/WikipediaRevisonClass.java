package edu.bsu.cs;

import java.util.Scanner;

public class WikipediaRevisonClass {
    Scanner scanner = new Scanner(System.in);

    private String articletitle;

    public WikipediaRevisonClass(String articletitle) {
        this.articletitle = articletitle;
    }


    public String getArticletitle() {
        return articletitle;
    }

    public void setArticletitle(String articletitle) {
        this.articletitle = articletitle;
    }
}
