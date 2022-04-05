package by.itechart.film2night.command;

public class CommandProvider {


    public static Command defineCommand(String command) {
        Command current = null;

        if (command == null || command.isEmpty()) {
            return null;
        }
        try {
            CommandType currentType = CommandType.valueOf(command.toUpperCase());
            current = currentType.getCurrentCommand();
        } catch (IllegalArgumentException e) {

            e.printStackTrace();
        }
        return current;
    }

}
