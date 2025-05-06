package feed;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.File;


import utils.Config;
import utils.FeedsData;

public class Article {
    private String title;
    private String description;
    private String pubDate;
    private String link;

    // Constrctor
    public Article(String title, String description, String pubDate, String link) {
        this.title = title;
        this.pubDate = pubDate;
        this.description = description;
        this.link = link;
    }
    
    @Override
    public String toString() {
        return "Title: " + title + "\n" + 
               "Description: " + description + "\n" + 
               "Publication Date: " + pubDate + "\n" + 
               "Link: " + link + "\n";
    }

    public void pretty_print() {
        System.out.println("Title: " + title);
        System.out.println("Description: " + description);
        System.out.println("Publication Date: " + pubDate);
        System.out.println("Link: " + link);
    }

    public static List<Article> allArticlesMaker (Config config, List<FeedsData> feedsDataArray, Boolean error) throws IOException {
        List<Article> allArticles = new ArrayList<>();
        for (FeedsData feedData : feedsDataArray) {
            if (config.getFeedKey() == null || config.getFeedKey().equals(feedData.getLabel())) {
                try {
                    String xmlData = FeedParser.fetchFeed(feedData.getUrl());
                    allArticles.addAll(FeedParser.parseXML(xmlData));
                    
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error fetching feed data");
                    error = true;
                    return allArticles;
                }
                
            }
            if (config.getPrintFeed()){
               File file = new File ("src/main/java/data/BigData.txt");
               FileWriter write = new FileWriter(file, false);
               try{
                    for (Article article: allArticles){
                    write.write(article.toString());
            }
                    write.close();
               }catch (IOException e) {
                    e.printStackTrace();
                    System.exit(1);
               }
            }

        } 
        return allArticles;

    }

    // Accesors
    public String get_title() {
        return title;
    }

    public String get_description() {
        return description;
    }

    public String get_pubDate() {
        return pubDate;
    }

    public String get_link() {
        return link;
    }
}
