package service;

import java.util.*;

public class Parser {

    public List<String> parseToWords(String text) {
        List<String> words = new ArrayList<>();
        StringBuilder currentWord = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);

            if (Character.isLetterOrDigit(ch)) {
                currentWord.append(Character.toLowerCase(ch));
            } else {
                if (!currentWord.isEmpty()) {
                    words.add(currentWord.toString());
                    currentWord.setLength(0);
                }
            }
        }
        //проверка ласт слова
        if (!currentWord.isEmpty()) {
            words.add(currentWord.toString());
        }

        return words;
    }
}