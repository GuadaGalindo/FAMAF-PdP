package namedEntities.heuristics;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NameandTitlesHeuristic extends Heuristics {

    public String name() {
        return "NameAndTitles";
    }

    public List<String> extractCandidates(String text) {
        List<String> candidates = new ArrayList<>();

        // Normalización del texto
        textNormalizer(text);

        // Lista de patrones de expresión regular
        List<Pattern> patterns = List.of(
                // Full names
                Pattern.compile(
                        "\\b(?<=\\b|^)(?!(?:El|La|Los|Las)\\b)[A-Z][a-z]+(?:[-\\s](?!(?:El|La|Los|Las)\\b)[A-Z][a-z]+)+\\b"),
                // Names and Titles
                Pattern.compile(
                        "\\b(?:Dr\\.|Sr\\.|Sra\\.|Presidente|Prof\\.)\\s(?<=\\b|^)(?!(?:El|La|Los|Las)\\b)[A-Z][a-z]+\\s(?<=\\b|^)(?!(?:El|La|Los|Las)\\b)[A-Z][a-z]+(?:\\sJr\\.)?\\b"));
        // Iterar sobre cada patrón y buscar coincidencias
        for (Pattern pattern : patterns) {
            Matcher matcher = pattern.matcher(text);
            while (matcher.find()) {
                candidates.add(matcher.group());
            }
        }
        return candidates;
    }
}