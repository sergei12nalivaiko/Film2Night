package by.itechart.film2night.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandProvider {

    private static final Logger LOGGER = LogManager.getLogger();

    public static Command defineCommand(String command) {
        Command current = null;

        if (command.isEmpty()) {
            return null;
        }
        try {
            CommandType currentType = CommandType.valueOf(command.toUpperCase());
            current = currentType.getCurrentCommand();
        } catch (IllegalArgumentException e) {
            LOGGER.error("Command - {} do not exist!", command);
            e.printStackTrace();
        }
        return current;
    }

}
