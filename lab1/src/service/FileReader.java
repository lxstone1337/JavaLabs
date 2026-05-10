package service;

import java.io.*;

public class FileReader {

    public String readFile(String fileName) {
        Reader reader = null;
        StringBuilder content = new StringBuilder();

        try {
            reader = new InputStreamReader(new FileInputStream(fileName));
            int currentCharCode;

            while ((currentCharCode = reader.read()) != -1) {
                content.append((char) currentCharCode);
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getLocalizedMessage());
            return "";
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace(System.err);
                }
            }
        }

        return content.toString();
    }
}