package namedEntities.heuristics;

import java.io.Serializable;

import java.text.Normalizer;
import java.util.List;

abstract public class Heuristics implements Serializable{

    abstract public String name();

    abstract public List<String> extractCandidates(String text);

    public static List<Heuristics> getAvailableHeuristics() {
        return List.of(
                new CapitalizedWordHeuristic(),
                new LocsandOrgHeuristics(),
                new NameandTitlesHeuristic());
    }
    public static String textNormalizer (String text) {
        text = text.replaceAll("[-+.^:,\"]", "");
        text = Normalizer.normalize(text, Normalizer.Form.NFD);
        text = text.replaceAll("\\p{M}", "");
        return text;
    }
}