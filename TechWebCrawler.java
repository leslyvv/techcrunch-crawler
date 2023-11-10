package WebCrawler;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.text.ParseException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TechWebCrawler implements Runnable{
    private static final int MAX_DEPTH = 2;
    private static final int MAX_THREADS = 4;
    private final ExecutorService threadPool = Executors.newFixedThreadPool(MAX_THREADS);
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
                // gets all anchor elements
                Elements links = doc.select(".a[href]");
                for(Element link: links){
                    String next_link = link.absUrl("href");
                    if(!visitedLink.contains(next_link)){
                    // new crawling tasks submitted to the thread pool
                      threadPool.execute(() -> crawl(level + 1, next_link));
                    }
                    }
            }
        }
    }
    //requesting of a URL
    private Document request(String url){

        try {
            Connection connect = Jsoup.connect(url);
            Document doc = connect
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .cookie("auth", "token")
                    .timeout(3000)
                    .get();
            //checks if connection is successful
            if (connect.response().statusCode() == 200) {
                // retrieves all content following under the class post_block
                Elements articleContent = doc.select(".post-block");

                // retrieves title, urls, author, content, date
                for (Element articles : articleContent) {

                    // retrieving all the content in the format of a string
                    String title = articles.select(".post-block__title__link").text();
                    String urls = articles.select(".post-block__title__link").attr("href");
                    String author = articles.select(".river-byline__authors").text();
                    String content = articles.select(".post-block__content").text();
                    String date = articles.select("time.river-byline__time").attr("datetime");

                    String formattedDate = formatDate(date);
                    //creates a new object every time for each link
                    TechArticles techArticle = new TechArticles(_threadID, title, urls, formattedDate, author, content);
                    articlesList.add(techArticle);

                }
                //displays the articles information
                for (TechArticles art : articlesList) {
                    System.out.println("Thread ID: " + art.get_thread());
                    System.out.println("Title: " + art.get_title());
                    System.out.println("URL: " + art.get_url());
                    System.out.println("Date and Time: " + art.get_date());
                    System.out.println("Author: " + art.get_author());
                    System.out.println("Content: " + art.get_content());
                }
                // adds the visited url to ensure it's stored
                visitedLink.add(url);
                return doc;
            } else {
                System.out.println("Error in loading page status code is: " + connect.response().statusCode());
            }
        } catch(IOException | ParseException e){
            e.printStackTrace();
        }

        return null;
    }
    private String formatDate(String date) throws ParseException{
        // original date format versus new date
        SimpleDateFormat originalDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        SimpleDateFormat newDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateObj = originalDate.parse(date);
        return newDate.format(dateObj);

        }
    public Thread getThread(){
        return thread;
    }
}
