package by.itechart.film2night.controller;

import by.itechart.film2night.command.*;
import by.itechart.film2night.connection.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.*;
import java.sql.SQLException;
import java.text.ParseException;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class Top250Servlet extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger();

    public void init() {
        LOGGER.info("init");
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        LOGGER.info("in do get method");
        processRequest(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        LOGGER.info("in do post method");
        processRequest(req, res);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) {

        LOGGER.info("in processRequest method");
        String commandFromPage = request.getParameter(ParameterAndAttribute.TYPE);
        LOGGER.info(commandFromPage);
        Command command = CommandProvider.defineCommand(commandFromPage);
        try {
            command.execute(request, response);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
        try {
            ConnectionPool.getInstance().closePool();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}