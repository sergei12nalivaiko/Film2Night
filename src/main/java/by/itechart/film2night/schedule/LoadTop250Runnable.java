package by.itechart.film2night.schedule;

import by.itechart.film2night.command.impl.LoadFilmsTop250Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;

public class LoadTop250Runnable implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger();
    private ServletContext servletContext;

    private LoadFilmsTop250Command loadFilmsTop250Command = new LoadFilmsTop250Command();

    public LoadTop250Runnable(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Override
    public void run() {
        LOGGER.warn("in run method LoadTop250");
        loadFilmsTop250Command.execute();
    }
}
