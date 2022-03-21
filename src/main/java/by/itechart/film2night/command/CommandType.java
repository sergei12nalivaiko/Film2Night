package by.itechart.film2night.command;

import by.itechart.film2night.command.impl.LoadFilmByIdCommand;
import by.itechart.film2night.command.impl.LoadFilmsTop250Command;

public enum CommandType {

    ID {
        {
            this.command = new LoadFilmByIdCommand();
        }
    },
    TOP_250_BEST_FILMS{
        {
            this.command = new LoadFilmsTop250Command();
        }
    };

    Command command;

    public Command getCurrentCommand() {
        return command;
    }
}
