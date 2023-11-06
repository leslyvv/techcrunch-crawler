package WebCrawler;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class TechWebCrawler implements Runnable{
    private static final int MAX_DEPTH = 1;
    private Thread thread;
    private String _link;
    private ArrayList<String> visitedLink = new ArrayList<String>();
    private ArrayList<TechArticles> articlesList = new ArrayList<>();
    private int _threadID;

    public TechWebCrawler(String link, int threadID){
        _link = link;
        _threadID = threadID;
        thread = new Thread(this);
        thread.start();
    }
    @Override
    public void run(){
        crawl(1,_link);
    }
    // crawler based on depth level and makes sure to not visit same link twice
    private void crawl(int level, String url){
        if(level <= MAX_DEPTH){
            Document doc = request(url);

            if(doc != null){
                for(Element link: doc.select(".post-block__title__link[href]")){
                    String next_link = link.absUrl("href");
                    if(visitedLink.contains(next_link)==false){
                        crawl(level+1, next_link);
                    }
                    }
            }
        }
    }
    //requesting of an URL
    private Document request(String url){

        try {
            Connection connect = Jsoup.connect(url);
            Document doc = connect
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .timeout(3000)
                    .get();
            //checks if connection is successful
            if (connect.response().statusCode() == 200) {
                // retrieves all content following under the class post_block
                Elements articleContent = doc.select(".post-block");


                    //get all links on doc
                    //title and link
                    for (Element articles : articleContent) {

                        // retrieving all the content in the format of a string
                        String title = articles.select(".post-block__title__link").text();
                        String urls = articles.select(".post-block__title__link").attr("href");
                        String date = articles.select("time.river-byline__time").attr("datetime");
                        String author = articles.select(".river-byline__authors").text();
                        String content = articles.select(".post-block__content").text();

                        //creates a new object everytime for each link
                        TechArticles techArticle = new TechArticles(_threadID,title, urls, date, author,content);
                        articlesList.add(techArticle);

                    }
                    //displays the articles information
                for(TechArticles art: articlesList){
                    System.out.println("Thread ID: " + art.get_thread());
                    System.out.println("Title: " + art.get_title());
                    System.out.println("URL: " + art.get_url());
                    System.out.println("Date: " + art.get_date());
                    System.out.println("Author: " + art.get_author());
                    System.out.println("Content: " + art.get_content());
                }
                // adds the visited url to ensure it's stored
                visitedLink.add(url);
                return doc;
                       }
            return null;
        } catch(IOException e){
            return null;
        }

    }
    public Thread getThread(){
        return thread;
    }
}
