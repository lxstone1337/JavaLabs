package service;

import context.Context;
import model.WordData;
import java.util.*;

public class WFA {

    private final Context context;

    public WFA(Context context) {
        this.context = context;
    }

    public void countAndFillContext(List<String> words) {
        Map<String, Integer> tempFrequencyMap = new HashMap<>();

        for (String word : words) {
            int currentCount = tempFrequencyMap.getOrDefault(word, 0);
            tempFrequencyMap.put(word, currentCount + 1);
        }

        int totalWords = words.size();

        for (Map.Entry<String, Integer> entry : tempFrequencyMap.entrySet()) {
            String word = entry.getKey();
            int frequency = entry.getValue();
            double percentage = (frequency * 100.0) / totalWords;

            WordData data = new WordData(word, frequency, percentage);
            context.putWordData(word, data);
        }
    }
}