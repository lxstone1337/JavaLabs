package printer;

import context.Context;
import model.WordData;
import java.io.*;
import java.util.*;

public class Printer {

    private final Context context;
    private final String outputFile;

    public Printer(Context context, String outputFile) {
        this.context = context;
        this.outputFile = outputFile;
    }

    public void scvprint() {
        if (context.isEmpty()) {
            System.err.println("нет слов для вывода!");
            return;
        }

        Collection<WordData> allData = context.getAll();
        List<WordData> sortedList = new ArrayList<>(allData);
        sortedList.sort((a, b) -> Integer.compare(b.getFrequency(), a.getFrequency()));

        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {
            writer.printf("%-20s%10s%15s%n", "Слово", "Количество", "Частота(%)");

            for (WordData data : sortedList) {
                writer.printf("%-20s%10d%14.2f%%%n",
                        data.getWord(),
                        data.getFrequency(),
                        data.getPercentage()
                );
            }
        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + e.getLocalizedMessage());
        }
    }
}