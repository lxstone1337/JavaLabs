package main;

import context.Context;
import service.FileReader;
import service.Parser;
import service.WFA;
import printer.Printer;

public class Main {
    public static void main(String[] args) {

        if (args.length < 2) {
            System.err.println("надо указать входной и выходной файлы");
            System.exit(1);
        }

        String inputFile = args[0];
        String outputFile = args[1];

        Context context = new Context();

        FileReader fileReader = new FileReader();
        Parser parser = new Parser();
        WFA counter = new WFA(context);
        Printer printer = new Printer(context, outputFile);

        String fileContent = fileReader.readFile(inputFile);
        if (fileContent.isEmpty()) {
            System.err.println("файл пуст или нечитаем");
            return;
        }

        java.util.List<String> words = parser.parseToWords(fileContent);
        if (words.isEmpty()) {
            System.err.println("нет слов!");
            return;
        }

        //считаем частоту и заполняем контекст (counter уже знает context)
        counter.countAndFillContext(words);

        //выводим результат в файл (printer уже знает context и outputFile)
        printer.scvprint();

        System.out.println("\nрезультат сохранён в файл: " + outputFile);
        System.out.println("всего уникальных слов: " + context.size());
    }
}