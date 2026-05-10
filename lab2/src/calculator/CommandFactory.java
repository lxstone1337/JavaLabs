package calculator;

import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class CommandFactory {
    private static final Logger LOGGER = Logger.getLogger(CommandFactory.class.getName());
    private static final Properties COMMAND_MAP = new Properties();

    static {
        try (InputStream is = CommandFactory.class.getResourceAsStream("commands.properties")) {
            if (is == null) {
                throw new RuntimeException("commands.properties not found in classpath");
            }
            COMMAND_MAP.load(is);
        } catch (Exception e) {
            LOGGER.severe("Failed to load commands.properties: " + e.getMessage());
            throw new RuntimeException("Failed to initialize CommandFactory", e);
        }
    }

    public Command createCommand(String commandName) throws CalcException {
        String className = COMMAND_MAP.getProperty(commandName);
        if (className == null) {
            throw new CalcException("Unknown command: " + commandName);
        }
        try {
            Class<?> clazz = Class.forName(className);
            Object instance = clazz.getDeclaredConstructor().newInstance();
            if (!(instance instanceof Command)) {
                throw new CalcException("Class " + className + " does not implement Command");
            }
            return (Command) instance;
        } catch (Exception e) {
            throw new CalcException("Failed to instantiate command for " + commandName, e);
        }
    }
}