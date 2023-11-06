package WebCrawler;

public class TechArticles {

    private String _title;
    private String _author;
    private String _date;
    private String _content;
    private String _url;
    private int _thread;

    public TechArticles(int thread, String title, String url, String date,String author, String content){
        set_thread(thread);
        set_title(title);
        set_url(url);
        set_author(author);
        set_date(date);
        set_content(content);
    }
    public TechArticles(String title, String url, String date,String author, String content){
        set_title(title);
        set_url(url);
        set_author(author);
        set_date(date);
        set_content(content);
    }
    void set_thread(int thread){
        _thread = thread;
    }
    void set_title(String title){
        _title = title;
    }
    void set_url(String url){
        _url = url;
    }
    void set_author(String author){
        _author = author;
    }
    void set_date(String date){
        _date = date;
    }
    void set_content(String content){
        _content = content;
    }

    public int get_thread(){
        return _thread;
    }
    public String get_title() {
        return _title;
    }

    public String get_url() {
        return _url;
    }
    public String get_date() {
        return _date;
    }

    public String get_author() {
        return _author;
    }
    public String get_content(){
        return _content;
    }



}
