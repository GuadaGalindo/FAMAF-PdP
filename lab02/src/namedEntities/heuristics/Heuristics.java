package namedEntities.heuristics;

import java.util.List;

abstract public class Heuristics {

    abstract public String name();

    abstract public List<String> extractCandidates(String text);

    public static List<Heuristics> getAvailableHeuristics() {
        return List.of(
                new CapitalizedWordHeuristic(),
                new LocsandOrgHeuristics(),
                new NameandTitlesHeuristic());
    }
}