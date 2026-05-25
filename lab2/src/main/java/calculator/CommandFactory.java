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
                throw new RuntimeException("commands.properties not found");
            }
            COMMAND_MAP.load(is);
        } catch (Exception e) {
            LOGGER.severe("failed to load commands.properties: " + e.getMessage());
            throw new RuntimeException("failed to initialize CommandFactory", e);
        }
    }

    public Command createCommand(String commandName) throws CalcException {
        String className = COMMAND_MAP.getProperty(commandName);
        if (className == null) {
            throw new CalcException("unknown command: " + commandName);
        }
        try {
            Class<?> clazz = Class.forName(className);
            Object instance = clazz.getDeclaredConstructor().newInstance();
            if (!(instance instanceof Command)) {
                throw new CalcException("class " + className + " does not implement command");
            }
            return (Command) instance;
        } catch (Exception e) {
            throw new CalcException("failed to instantiate command for " + commandName, e);
        }
    }
}