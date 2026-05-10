package model;

public class WordData {
    private final String word;
    private final int frequency;
    private final double percentage;

    public WordData(String word, int frequency, double percentage) {
        this.word = word;
        this.frequency = frequency;
        this.percentage = percentage;
    }

    public String getWord() { return word; }
    public int getFrequency() { return frequency; }
    public double getPercentage() { return percentage; }
}