package edu.bsu.cs;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.net.URLEncoder;



public class WikipediaApiClient
{
    private static final String BASE_URL="https://www.wikipedia.org/";

    public static String fetchWikipediaData(String articleTitle) throws IOException{
        /*String encodedTitle=URL.Encoder.encode(articleTitle, StandardCharsets.UTF_8);
        String url=BASE_URL+encodedTitle;*/
        URL oracle=new URL(BASE_URL+articleTitle);



    }

}
