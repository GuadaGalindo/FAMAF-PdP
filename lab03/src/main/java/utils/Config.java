package utils;

import java.util.ArrayList;
import java.util.List;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.SparkSession;
import feed.Article;
import namedEntities.EntityClassifier;
import namedEntities.NamedEntity;
import namedEntities.StatsHandler;
import namedEntities.heuristics.Heuristics;
import scala.Tuple2;

public class Config {
    private boolean printFeed = false;
    private String computeNamedEntities = null;
    private boolean help = false;
    private String feedKey = null;
    private String statsFormat = null;

    public Config(boolean printFeed, String computeNamedEntities, boolean help, String feedKey, String statsFormat) {
        this.printFeed = printFeed;
        this.computeNamedEntities = computeNamedEntities;
        this.help = help;
        this.feedKey = feedKey;
        this.statsFormat = statsFormat;
    }

    public static void configHandler(Config config, List<Article> allArticles, StatsHandler statsHandler, String path) {
        SparkSession sc = SparkSession.builder()
                .appName("NamedEntityExtraction")
                .master("local[*]") // Usa todos los núcleos disponibles
                .getOrCreate();

        if (config.getComputeNamedEntities() != null) {
            System.out.println("HEURISTIC USED: " + config.getComputeNamedEntities());
            List<String> entityList = new ArrayList<>();
            for (Heuristics heuristic : Heuristics.getAvailableHeuristics()) {
                if (heuristic.name().equals(config.getComputeNamedEntities())) {
                    JavaRDD<String> lines = sc.read().textFile(path).javaRDD();
                    int numPartitions = Math.max(sc.sparkContext().defaultParallelism(), lines.getNumPartitions());
                    lines = lines.repartition(numPartitions);
                    JavaRDD<String> extract = lines.flatMap(s -> heuristic.extractCandidates(s).iterator());
                    List<String> reducedEntityList = extract
                            .mapToPair(entity -> new Tuple2<>(null, entity))
                            .groupByKey()
                            .flatMap(tuple -> tuple._2().iterator())
                            .collect();
                    entityList.addAll(reducedEntityList);
                    sc.stop();
                }
            }
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
            } else {
                System.out.println("Invalid format");
            }
        }
    }

    public static void printHelp(List<FeedsData> feedsDataArray) {

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

    public boolean getPrintFeed() {
        return printFeed;
    }

    public String getComputeNamedEntities() {
        return computeNamedEntities;
    }

    public boolean getHelp() {
        return help;
    }

    public String getFeedKey() {
        return feedKey;
    }

    public String getStatsFormat() {
        return statsFormat;
    }
}