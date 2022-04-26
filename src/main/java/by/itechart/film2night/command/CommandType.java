package by.itechart.film2night.command;

import by.itechart.film2night.command.impl.LoadFilmByIdCommand;
import by.itechart.film2night.command.impl.LoadFilmsTop250Command;

public enum CommandType {

    ID(new LoadFilmByIdCommand()),
    TOP_250_BEST_FILMS(new LoadFilmsTop250Command());

    CommandType(Command command) {
        this.command = command;
    }

    Command command;

    public Command getCurrentCommand() {
        return command;
    }
}
