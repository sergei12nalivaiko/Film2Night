package by.itechart.film2night.controller;

import by.itechart.film2night.command.*;
import by.itechart.film2night.connection.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.*;
import java.net.HttpURLConnection;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", urlPatterns = {"/controller/*"})
public class MainServlet extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger();
    private static HttpURLConnection connection;
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
        String parameter = null;

        String commandFromPage = request.getParameter(ParameterAndAttribute.COMMAND);
        LOGGER.info(commandFromPage);
        if(commandFromPage == null){
            parameter=parseUrl(request,response);
            request.setAttribute("id",parameter);
            Command command = CommanProvider.defineCommand("id");
            command.execute(request,response);
        }else{
            Command command = CommanProvider.defineCommand(commandFromPage);
            command.execute(request,response);
        }
    }

    public void destroy() {
        try {
            ConnectionPool.getInstance().closePool();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String parseUrl(HttpServletRequest request, HttpServletResponse response) {
        StringBuffer urlString= request.getRequestURL();
        LOGGER.info(urlString);
        String id = urlString.substring(33);
        LOGGER.info(id);
        return id;
    }
}