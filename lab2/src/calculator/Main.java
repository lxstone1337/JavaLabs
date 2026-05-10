package calculator;

import java.io.*;
import java.util.Arrays;
import java.util.logging.*;
import java.util.logging.SimpleFormatter;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    static {
        // Настройка логирования в файл при загрузке класса
        try {
            // Создаём папку logs, если её нет
            File logDir = new File("logs");
            if (!logDir.exists()) {
                logDir.mkdir();
            }

            // Обработчик для файла (максимальный размер 5MB, 3 файла ротации)
            FileHandler fileHandler = new FileHandler("logs/calculator.log", 5 * 1024 * 1024, 3, true);
            fileHandler.setLevel(Level.ALL);
            fileHandler.setFormatter(new SimpleFormatter() {
                @Override
                public String format(LogRecord record) {
                    return record.getLevel() + ": " + record.getMessage() + "\n";
                }
            });

            LOGGER.addHandler(fileHandler);

            // Опционально: убрать вывод в консоль
            LOGGER.setUseParentHandlers(false);

        } catch (IOException e) {
            System.err.println("Ошибка настройки логирования: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        CalcContext context = new CalcContext();
        CommandFactory factory = new CommandFactory();
        BufferedReader reader = null;

        try {
            if (args.length > 0) {
                reader = new BufferedReader(new FileReader(args[0]));
                LOGGER.info("Reading commands from file: " + args[0]);
            } else {
                reader = new BufferedReader(new InputStreamReader(System.in));
                LOGGER.info("Reading commands from standard input");
            }

            String line;
            int lineNum = 0;
            while ((line = reader.readLine()) != null) {
                lineNum++;
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                String[] parts = line.split("\\s+");
                String cmdName = parts[0];
                String[] cmdArgs = Arrays.copyOfRange(parts, 1, parts.length);

                try {
                    Command command = factory.createCommand(cmdName);
                    LOGGER.info(String.format("Line %d: %s %s", lineNum, cmdName, Arrays.toString(cmdArgs)));
                    command.execute(cmdArgs, context);
                } catch (CalcException e) {
                    LOGGER.severe(String.format("Line %d: %s", lineNum, e.getMessage()));
                } catch (Exception e) {
                    LOGGER.log(Level.SEVERE, String.format("Line %d: Unexpected error", lineNum), e);
                }
            }
        } catch (FileNotFoundException e) {
            LOGGER.severe("File not found: " + args[0]);
        } catch (IOException e) {
            LOGGER.severe("IO error: " + e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }
}