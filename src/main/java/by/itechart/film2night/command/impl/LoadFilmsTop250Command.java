package by.itechart.film2night.command.impl;

import by.itechart.film2night.command.Command;
import by.itechart.film2night.command.Router;
import by.itechart.film2night.entity.FilmTop250;
import by.itechart.film2night.service.KinopoiskService;
import by.itechart.film2night.service.Top250Service;
import by.itechart.film2night.service.impl.KinopoiskServiceImpl;
import by.itechart.film2night.service.impl.Top250SeviceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Properties;

public class LoadFilmsTop250Command implements Command {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String URL_TOP250 = "url.top250";
    private final Properties urlProperties = new Properties();
    private final LoadFilmByIdCommand loadFilmByIdCommand = new LoadFilmByIdCommand();
    private final KinopoiskService kinopoiskService = KinopoiskServiceImpl.getInstance();
    private final Top250Service top250Service = Top250SeviceImpl.getInstance();
    private List<FilmTop250> filmTop250List;
    private List<Integer> allIdFilms;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse res) {

        Router router = new Router();
        try {
            urlProperties.load(getClass().getClassLoader().getResourceAsStream("url.properties"));


        } catch (IOException e) {
            e.printStackTrace();
        }
        String url = urlProperties.getProperty(URL_TOP250);
        router.setPagePath(url);
        HttpURLConnection httpURLConnection = kinopoiskService.connectToKinopoiskApi(router.getPagePath(), res);
        StringBuffer responseContent = kinopoiskService.readResponse(httpURLConnection);
        kinopoiskService.writeResponse(responseContent, res);
        filmTop250List = kinopoiskService.parseJsonTop250Films(responseContent.toString());
        top250Service.deleteAllFilms();
        top250Service.insertFilms(filmTop250List);
        allIdFilms = top250Service.findAllIdFilms();
        LOGGER.error(allIdFilms.size());
        loadFilmByIdCommand.execute(allIdFilms);
    }

    public void execute() {
        Router router = new Router();
        try {
            urlProperties.load(getClass().getClassLoader().getResourceAsStream("url.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String url = urlProperties.getProperty(URL_TOP250);
        router.setPagePath(url);
        HttpURLConnection httpURLConnection = kinopoiskService.connectToKinopoiskApi(router.getPagePath());
        StringBuffer responseContent = kinopoiskService.readResponse(httpURLConnection);
        filmTop250List = kinopoiskService.parseJsonTop250Films(responseContent.toString());
        top250Service.deleteAllFilms();
        top250Service.insertFilms(filmTop250List);
        allIdFilms = top250Service.findAllIdFilms();
        loadFilmByIdCommand.execute(allIdFilms);
    }
}
