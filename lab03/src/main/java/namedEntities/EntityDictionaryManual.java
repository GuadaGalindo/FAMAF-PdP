package namedEntities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

public class EntityDictionaryManual {

    private static final Map<String, NamedEntity> entityMap = new HashMap<>();

    public static void initializeDictionary() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/main/java/data/dictionary.json"));
            StringBuilder jsonBuilder = new StringBuilder();
            String line;  
            
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
            reader.close();

            JSONArray entities = new JSONArray(jsonBuilder.toString());
            for (int i = 0; i < entities.length(); i++) {
                JSONObject entity = entities.getJSONObject(i);
                String label = entity.getString("label");
                Category category = Category.valueOf(entity.getString("Category"));
                JSONArray topicsArray = entity.getJSONArray("Topics");
                List<Topics> topics = new ArrayList<>();
                for (int j = 0; j < topicsArray.length(); j++) {
                    topics.add(Topics.valueOf(topicsArray.getString(j)));
                }
                JSONArray keywordsArray = entity.getJSONArray("keywords");
                List<String> keywords = new ArrayList<>();
                for (int j = 0; j < keywordsArray.length(); j++) {
                    keywords.add(keywordsArray.getString(j));
                }

                NamedEntity namedEntity = new NamedEntity(label, category, topics);
                for (String keyword : keywords) {
                    entityMap.put(keyword.toLowerCase(), namedEntity);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static NamedEntity getEntityInfo(String entity) {
        return entityMap.getOrDefault(entity.toLowerCase(),
                new NamedEntity(entity, Category.OTHER, List.of(Topics.OTHER)));
    }
}