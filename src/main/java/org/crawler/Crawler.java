package org.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Hello world!
 *
 */
public class Crawler
{
    private static final int DEPTH = 2;
    private static int count = 0;
    public static void main( String[] args )
    {

        String url = "https://www.cricbuzz.com/";
        // Connect to the URL and get the HTML document
        LocalDateTime start = LocalDateTime.now();
        Set<String> visitedLinks = new HashSet<>();
        getLinks(url, 0, visitedLinks);
        LocalDateTime end = LocalDateTime.now();
        Duration timeTaken = Duration.between(start, end);
        System.out.println("time taken to crawl: " + timeTaken.getSeconds());
    }

    public static void getLinks(String url, int depth, Set<String> visitedLinks) {
        if(depth > DEPTH || visitedLinks.contains(url)){
            return;
        }
        System.out.println(++count + " " + url);
        visitedLinks.add(url);
        List<String> resLinks = new ArrayList<>();
        try {
            Document document = Jsoup.connect(url).get();
            Elements links = document.select("a");
            for (Element link : links) {
                String href = link.attr("href");
                try {
                    URI uri = new URI(href);
                    if (uri.getScheme() != null && uri.getHost() != null) {
                        if(url.equals(href+"/")){
                            continue;
                        }
                        resLinks.add(href);
                        System.out.println("    " + href);
                    }
                } catch (URISyntaxException ex) {
                    System.out.println("Uri parse Error: " + link);
                }
            }
        } catch (IOException ex) {
            System.out.println("Error in: " + url + ": cause: " + ex.getCause());
        }

        for(String link : resLinks){
            getLinks(link, depth+1, visitedLinks);
        }
    }
}
