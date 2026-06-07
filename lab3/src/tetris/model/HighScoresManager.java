package tetris.model;

import java.io.*;
import java.util.*;

public class HighScoresManager {
    private static final String SCORES_FILE = "highscores.txt";
    private List<ScoreEntry> scores;

    public HighScoresManager() {
        scores = new ArrayList<>();
        loadScores();
    }

    public void addScore(String name, int score) {
        scores.add(new ScoreEntry(name, score));
        scores.sort((a, b) -> Integer.compare(b.score, a.score));
        if (scores.size() > 10) {
            scores = scores.subList(0, 10);
        }
        saveScores();
    }

    public List<ScoreEntry> getTopScores() {
        return new ArrayList<>(scores);
    }

    private void loadScores() {
        File file = new File(SCORES_FILE);
        if (!file.exists()) {
            System.out.println("No scores file found, creating new");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 2) {
                    try {
                        String name = parts[0];
                        int score = Integer.parseInt(parts[1]);
                        scores.add(new ScoreEntry(name, score));
                    } catch (NumberFormatException e) {
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading scores: " + e.getMessage());
        }
    }

    private void saveScores() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SCORES_FILE))) {
            for (ScoreEntry entry : scores) {
                writer.write(entry.name + "|" + entry.score);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving scores: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static class ScoreEntry {
        public String name;
        public int score;

        public ScoreEntry(String name, int score) {
            this.name = name;
            this.score = score;
        }

        @Override
        public String toString() {
            return name + ": " + score;
        }
    }
}