import java.io.*;
import java.util.*;

public class TagProcessor {

    private Set<String> stopWords;
    private Map<String, Integer> wordCounts;

    public TagProcessor() {
        stopWords = new HashSet<>();
        wordCounts = new HashMap<>();
    }

    public void loadStopWords(File stopWordFile) throws IOException {
        stopWords.clear();
        try (Scanner scanner = new Scanner(stopWordFile)) {
            while (scanner.hasNextLine()) {
                String word = scanner.nextLine().trim().toLowerCase();
                if (!word.isEmpty()) {
                    stopWords.add(word);
                }
            }
        }
    }

    public void processFile(File textFile) throws IOException {
        wordCounts.clear();
        try (Scanner scanner = new Scanner(textFile)) {
            while (scanner.hasNext()) {
                String word = scanner.next().replaceAll("[^a-zA-Z]", "").toLowerCase();
                if (!word.isEmpty() && !stopWords.contains(word)) {
                    wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
                }
            }
        }
    }

    public Map<String, Integer> getWordCounts() {
        return wordCounts;
    }

    public String getFormattedResults() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Integer> entry : wordCounts.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }

    public void saveResultsToFile(File outputFile) throws IOException {
        try (PrintWriter writer = new PrintWriter(outputFile)) {
            for (Map.Entry<String, Integer> entry : wordCounts.entrySet()) {
                writer.println(entry.getKey() + ": " + entry.getValue());
            }
        }
    }
}
