package by.itechart.film2night.controller;

import by.itechart.film2night.command.Command;
import by.itechart.film2night.command.CommandProvider;
import by.itechart.film2night.connection.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class FindByIdServlet extends HttpServlet {


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
        String parameter = parseUrl(request);
        request.setAttribute("id", parameter);
        Command command = CommandProvider.defineCommand("id");
        try {
            command.execute(request, response);
        } catch (ParseException e) {
            LOGGER.error("Failed to execute command id");
        }
    }

    public void destroy() {
        try {
            ConnectionPool.getInstance().closePool();
        } catch (SQLException | InterruptedException e) {
            LOGGER.error("Failed to close poolConnection");
        }
    }

    private String parseUrl(HttpServletRequest request) {
        StringBuffer urlString = request.getRequestURL();
        StringBuilder id = new StringBuilder();
        LOGGER.info(urlString);

        for (int i = urlString.length() - 1; i > 0; i--) {

            if (urlString.charAt(i) != '/') {
                id.append(urlString.charAt(i));
            } else {
                break;
            }
        }
        return id.reverse().toString();
    }
}
