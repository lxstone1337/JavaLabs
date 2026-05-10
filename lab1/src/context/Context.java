package context;

import model.WordData;
import java.util.*;

public class Context {
    private final Map<String, WordData> wordDataMap;

    public Context() {
        this.wordDataMap = new HashMap<>();
    }

    public void putWordData(String word, WordData data) {
        wordDataMap.put(word, data);
    }

    public WordData getWordData(String word) {
        return wordDataMap.get(word);
    }

    public Collection<WordData> getAll() {
        return wordDataMap.values();
    }

    public int size() {
        return wordDataMap.size();
    }

    public boolean isEmpty() {
        return wordDataMap.isEmpty();
    }
}