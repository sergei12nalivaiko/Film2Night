package by.itechart.film2night.schedule;

import by.itechart.film2night.command.impl.LoadFilmByIdCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;


public class LoadByIdRunnable implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger();
    private LoadFilmByIdCommand loadFilmByIdCommand = new LoadFilmByIdCommand();
    private ServletContext servletContext;

    public LoadByIdRunnable(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Override
    public void run() {
        LOGGER.warn("in run method LoadById");

        loadFilmByIdCommand.execute();

    }
}
