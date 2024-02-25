package org.crawler;

import java.util.Set;

public class CrawlerTask implements Runnable{

    private String url;
    private int depth;
    private Set<String> visitedLinks;

    public CrawlerTask(String url, int depth, Set<String> visitedLinks){
        this.url = url;
        this.depth = depth;
        this.visitedLinks = visitedLinks;
    }

    @Override
    public void run() {
        Crawler.getLinks(url, depth, visitedLinks);
    }


}
