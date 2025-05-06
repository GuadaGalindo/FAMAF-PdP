package namedEntities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatsHandler {
    private HashMap<Category, HashMap<String, Integer>> categoryStats = new HashMap<>();
    private HashMap<Topics, HashMap<String, Integer>> topicStats = new HashMap<>();

    public void updateCategoryStats(Category category, String entity) {
        updateCategoryStatsInternal(category, entity, categoryStats);
    }

    public void updateTopicStats(List<Topics> topics, String entity) {
        for (Topics topic : topics) {
            updateTopicStatsInternal(topic, entity, topicStats);
        }
    }

    private void updateCategoryStatsInternal(Category category, String entity,
            HashMap<Category, HashMap<String, Integer>> stats) {
        stats.putIfAbsent(category, new HashMap<>());
        HashMap<String, Integer> specificStats = stats.get(category);
        specificStats.put(entity, specificStats.getOrDefault(entity, 0) + 1);
    }

    private void updateTopicStatsInternal(Topics topic, String entity,
            HashMap<Topics, HashMap<String, Integer>> stats) {
        stats.putIfAbsent(topic, new HashMap<>());
        HashMap<String, Integer> specificStats = stats.get(topic);
        specificStats.put(entity, specificStats.getOrDefault(entity, 0) + 1);
    }

    public void printCategoryStats() {
        printCategoryStatsInternal("Category", categoryStats);
    }

    public void printTopicStats() {
        printTopicStatsInternal("Topic", topicStats);
    }

    private void printCategoryStatsInternal(String type, HashMap<Category, HashMap<String, Integer>> stats) {
        for (Map.Entry<Category, HashMap<String, Integer>> entry : stats.entrySet()) {
            Category key = entry.getKey();
            System.out.println(type + ": " + key);
            HashMap<String, Integer> specificStats = entry.getValue();
            for (Map.Entry<String, Integer> specificEntry : specificStats.entrySet()) {
                System.out.println("\t" + specificEntry.getKey() + " (" + specificEntry.getValue() + ")");
            }
        }
    }

    private void printTopicStatsInternal(String type, HashMap<Topics, HashMap<String, Integer>> stats) {
        for (Map.Entry<Topics, HashMap<String, Integer>> entry : stats.entrySet()) {
            Topics key = entry.getKey();
            System.out.println(type + ": " + key);
            HashMap<String, Integer> specificStats = entry.getValue();
            for (Map.Entry<String, Integer> specificEntry : specificStats.entrySet()) {
                System.out.println("\t" + specificEntry.getKey() + " (" + specificEntry.getValue() + ")");
            }
        }
    }
}