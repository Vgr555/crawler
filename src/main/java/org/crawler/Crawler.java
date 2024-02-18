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
import java.util.List;

/**
 * Hello world!
 *
 */
public class Crawler
{
    public static void main( String[] args )
    {
        String url = "https://itlumee.in/";
        // Connect to the URL and get the HTML document
        LocalDateTime start = LocalDateTime.now();
        List<String> links = getLinks(url);
        System.out.println(links);
        if (links.isEmpty()) return;
        for (int i = 0; i < links.size(); i++) {
            String s = links.get(i);
            System.out.println((i + 1) + " " + s);
            getLinks(s);
        }
        LocalDateTime end = LocalDateTime.now();
        Duration timeTaken = Duration.between(start, end);
        System.out.println("time taken to crawl: " + timeTaken.getSeconds());
    }

    public static List<String> getLinks(String url) {
        List<String> resLinks = new ArrayList<>();
        try {
            Document document = Jsoup.connect(url).get();
            Elements links = document.select("a");
            for (Element link : links) {
                String href = link.attr("href");
                try {
                    URI uri = new URI(href);
                    if (uri.getScheme() != null && uri.getHost() != null) {
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

        return resLinks;
    }
}
