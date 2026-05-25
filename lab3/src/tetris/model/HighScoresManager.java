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
        System.out.println("Adding score: " + name + " - " + score);
        scores.add(new ScoreEntry(name, score));
        scores.sort((a, b) -> Integer.compare(b.score, a.score));
        if (scores.size() > 10) {
            scores = scores.subList(0, 10);
        }
        saveScores();
        System.out.println("Score saved. Total scores: " + scores.size());
    }

    public List<ScoreEntry> getTopScores() {
        return new ArrayList<>(scores);
    }

    public boolean isHighScore(int score) {
        if (scores.size() < 10) {
            System.out.println("Is high score: true (less than 10 scores)");
            return true;
        }
        boolean isHigh = score > scores.get(scores.size() - 1).score;
        System.out.println("Is high score: " + isHigh + " (score: " + score + ", lowest: " + scores.get(scores.size() - 1).score + ")");
        return isHigh;
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
                        System.out.println("Skipping invalid line: " + line);
                    }
                }
            }
            System.out.println("Loaded " + scores.size() + " scores from file");
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
            System.out.println("Saved " + scores.size() + " scores to file");
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