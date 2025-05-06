
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import namedEntities.EntityDictionaryManual;
import namedEntities.StatsHandler;
import utils.Config;
import utils.FeedsData;
import utils.JSONParser;
import utils.UserInterface;
import feed.Article;

public class App {

    public static void main(String[] args) throws IOException {

        long startTime = System.currentTimeMillis();

        String path = ("src/main/java/data/BigData.txt"); 

        List<FeedsData> feedsDataArray = new ArrayList<>();
        try {
            feedsDataArray = JSONParser.parseJsonFeedsData("src/main/java/data/feeds.json");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        UserInterface ui = new UserInterface();
        Config config = ui.handleInput(args);
        EntityDictionaryManual.initializeDictionary();
        run(config, feedsDataArray, path);
        if (config.getHelp()) {
            Config.printHelp(feedsDataArray);
        }
        long endTime = System.currentTimeMillis(); 
        long duration = endTime - startTime;
        System.out.println("Execution time: " + duration + "ms"); 
    }

    private static void run(Config config, List<FeedsData> feedsDataArray, String path) throws IOException {
        StatsHandler statsHandler = new StatsHandler();
        if (feedsDataArray == null || feedsDataArray.size() == 0) {
            System.out.println("No feeds data found");
            return;
        }

        Boolean error = false;

        List<Article> allArticles = Article.allArticlesMaker(config, feedsDataArray, error);
        if (error) {
            return;
        }

        Config.configHandler(config, allArticles, statsHandler, path);

    }
}
