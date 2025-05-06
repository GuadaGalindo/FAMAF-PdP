package feed;

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

    public void pretty_print() {
        System.out.println("Title: " + title);
        System.out.println("Description: " + description);
        System.out.println("Publication Date: " + pubDate);
        System.out.println("Link: " + link);
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
