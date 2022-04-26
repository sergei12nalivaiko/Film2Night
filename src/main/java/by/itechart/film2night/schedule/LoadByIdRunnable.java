package by.itechart.film2night.schedule;

import by.itechart.film2night.command.impl.LoadFilmByIdCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class LoadByIdRunnable implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger();
    private final LoadFilmByIdCommand loadFilmByIdCommand = new LoadFilmByIdCommand();

    public LoadByIdRunnable() {

    }

    @Override
    public void run() {
        LOGGER.warn("in run method LoadById");

        loadFilmByIdCommand.execute();
    }
}
