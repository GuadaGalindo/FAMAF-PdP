package namedEntities;

import java.util.ArrayList;
import java.util.List;

public class EntityClassifier {
    public static List<NamedEntity> classifyEntities(List<String> entityList, StatsHandler statsHandler) {

        List<NamedEntity> entities = new ArrayList<>();

        for (String word : entityList) {
            NamedEntity entity = EntityDictionaryManual.getEntityInfo(word);
            if (entity != null) {
                entities.add(entity);
                statsHandler.updateCategoryStats(entity.get_category(), word);
                statsHandler.updateTopicStats(entity.get_topic(), word);
            }

        }
        return entities;
    }
}