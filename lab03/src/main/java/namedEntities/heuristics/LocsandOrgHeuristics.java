package namedEntities.heuristics;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LocsandOrgHeuristics extends Heuristics {

    public String name() {
        return "LocsOrgs";
    }

    public List<String> extractCandidates(String text) {
        List<String> candidates = new ArrayList<>();

        // Normalización del texto
        text = textNormalizer(text);

        // Lista de patrones de expresión regular
        List<Pattern> patterns = List.of(
                // Full names
                Pattern.compile(
                        "\\b(?<=\\b|^)(?!(?:El|La|Los|Las)\\b)[A-Z][a-z]+(?:[-\\s](?!(?:El|La|Los|Las)\\b)[A-Z][a-z]+)+\\b"),
                // Organizations
                Pattern.compile(
                        "\\b(?<=\\b|^)(?!(?:El|La|Los|Las)\\b)[A-Z][a-z]+(?:\\s(?!(?:El|La|Los|Las)\\b)[A-Z][a-z]+)*\\s(?:Inc|Ltd|LCC|Corp|S\\.A\\.)\\b"),
                // Locations
                Pattern.compile(
                        "\\b(?<=\\b|^)(?!(?:El|La|Los|Las)\\b)[A-Z][a-z]+(?:\\s(?!(?:El|La|Los|Las)\\b)[A-Z][a-z]+)*\\s(?:de|en|por)\\s(?!(?:El|La|Los|Las)\\b)[A-Z][a-z]+\\b"),
                // Acronimos y acronimos mixtos
                Pattern.compile("\\b[A-Z]{2,}\\b"));

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