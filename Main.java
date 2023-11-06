package WebCrawler;

import java.io.IOException;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        //Multiple WebCrawlers for different categories
        ArrayList<TechWebCrawler> threads = new ArrayList<>();
        threads.add(new TechWebCrawler("https://techcrunch.com/category/security/", 1));
        threads.add(new TechWebCrawler("https://techcrunch.com/category/startups/",2));
        threads.add(new TechWebCrawler("https://techcrunch.com/category/artificial-intelligence/",3));

        //joins the thread
        for(TechWebCrawler w : threads) {
            try {
                w.getThread().join();
            }
            catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
