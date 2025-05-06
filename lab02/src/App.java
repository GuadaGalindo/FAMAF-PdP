import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import feed.Article;
import feed.FeedParser;
import namedEntities.NamedEntity;
import namedEntities.StatsHandler;
import namedEntities.EntityClassifier;
import namedEntities.EntityDictionaryManual;
import namedEntities.heuristics.Heuristics;
import utils.Config;
import utils.FeedsData;
import utils.JSONParser;
import utils.UserInterface;

public class App {

    public static void main(String[] args) {

        List<FeedsData> feedsDataArray = new ArrayList<>();
        try {
            feedsDataArray = JSONParser.parseJsonFeedsData("src/data/feeds.json");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        UserInterface ui = new UserInterface();
        Config config = ui.handleInput(args);
        EntityDictionaryManual.initializeDictionary();
        run(config, feedsDataArray);
        if (config.getHelp()) {
            printHelp(feedsDataArray);
        }

    }

    private static void run(Config config, List<FeedsData> feedsDataArray) {
        StatsHandler statsHandler = new StatsHandler();
        if (feedsDataArray == null || feedsDataArray.size() == 0) {
            System.out.println("No feeds data found");
            return;
        }

        List<Article> allArticles = new ArrayList<>();
        for (FeedsData feedData : feedsDataArray) {
            if (config.getFeedKey() == null || config.getFeedKey().equals(feedData.getLabel())) {
                try {
                    String xmlData = FeedParser.fetchFeed(feedData.getUrl());
                    allArticles.addAll(FeedParser.parseXML(xmlData));
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error fetching feed data");
                    return;
                }
            }
        }

        if (config.getPrintFeed()) {
            System.out.println("Printing feed(s)");
            for (Article article : allArticles) {
                article.pretty_print();
            }
        }

        if (config.getComputeNamedEntities() != null) {
            System.out.println("HEURISTIC USED: " + config.getComputeNamedEntities());

            List<String> entityList = new ArrayList<>();
            for (Heuristics heuristic : Heuristics.getAvailableHeuristics()) {
                if (heuristic.name().equals(config.getComputeNamedEntities())) {
                    for (Article article : allArticles) {
                        entityList.addAll(heuristic.extractCandidates(article.get_title()));
                        entityList.addAll(heuristic.extractCandidates(article.get_description()));
                    }
                }
            }

            // System.out.println("Lista de entidades: " + entityList); (imprime sin
            // clasificar)

            List<NamedEntity> classifiedEntities = EntityClassifier.classifyEntities(entityList, statsHandler);
            System.out.println("Lista de entidades clasificadas:");
            for (NamedEntity entity : classifiedEntities) {
                System.out.println(entity);
            }
        }

        if (config.getStatsFormat() != null) {
            if (config.getStatsFormat().isEmpty() || config.getStatsFormat().equals("cat")) {
                statsHandler.printCategoryStats();
            } else if (config.getStatsFormat().equals("topic")) {
                statsHandler.printTopicStats();
            } else
                System.out.println("Invalid format");
        }
    }

    private static void printHelp(List<FeedsData> feedsDataArray) {

        System.out.println("Usage: make run ARGS=\"[OPTION]\"");
        System.out.println("Options:");
        System.out.println("  -h, --help: Show this help message and exit");
        System.out.println("  -f, --feed <feedKey>:                Fetch and process the feed with ");
        System.out.println("                                       the specified key ");
        System.out.println("                                       Available feed keys are: ");
        for (FeedsData feedData : feedsDataArray) {
            System.out.println("                                       " + feedData.getLabel());
        }
        System.out.println("  -ne, --named-entity <heuristicName>: Use the specified heuristic to extract");
        System.out.println("                                       named entities");
        System.out.println("                                       Available heuristic names are: ");
        for (Heuristics heuristic : Heuristics.getAvailableHeuristics()) {
            System.out.println("                                       " + heuristic.name());
        }
        System.out.println("                                       <name>: <description> ");
        System.out.println("  -pf, --print-feed:                   Print the fetched feed");
        System.out.println("  -sf, --stats-format <format>:        Print the stats in the specified format ");
        System.out.println("                                       Available formats are: ");
        System.out.println("                                       cat: Category-wise stats ");
        System.out.println("                                       topic: Topic-wise stats ");
    }
}
