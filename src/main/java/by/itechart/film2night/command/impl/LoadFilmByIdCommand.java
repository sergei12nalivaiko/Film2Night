package by.itechart.film2night.command.impl;

import by.itechart.film2night.command.Command;
import by.itechart.film2night.command.Router;
import by.itechart.film2night.entity.Film;
import by.itechart.film2night.service.FilmService;
import by.itechart.film2night.service.KinopoiskService;
import by.itechart.film2night.service.impl.FilmServiceImpl;
import by.itechart.film2night.service.impl.KinopoiskServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.text.ParseException;
import java.util.List;
import java.util.Properties;

public class LoadFilmByIdCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String URL_FIND_BY_ID = "url.findById";
    private final Properties urlProperties = new Properties();
    private KinopoiskService kinopoiskService = KinopoiskServiceImpl.getInstance();
    private FilmService filmService = FilmServiceImpl.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse res) {
        Router router = new Router();
        try {
            urlProperties.load(getClass().getClassLoader().getResourceAsStream("url.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String url = urlProperties.getProperty(URL_FIND_BY_ID);
        router.setPagePath(url + request.getAttribute("id"));
        HttpURLConnection httpURLConnection = kinopoiskService.connectToKinopoiskApi(router.getPagePath(), res);
        StringBuffer responseContent = kinopoiskService.readResponse(httpURLConnection);
        kinopoiskService.writeResponse(responseContent, res);
        String idFilm = kinopoiskService.parseUrl(router.getPagePath());

        Film film = null;
        try {
            film = kinopoiskService.parseJsonFilm(responseContent.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        filmService.insertFilm(film);
        String posterUrl = filmService.findPosterUrl(Integer.valueOf(idFilm));
        LOGGER.error(posterUrl);
        try {
            kinopoiskService.downloadPoster(posterUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void execute(List<Integer> allIdFilms) {
        Router router = new Router();
        try {
            urlProperties.load(getClass().getClassLoader().getResourceAsStream("url.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String url = urlProperties.getProperty(URL_FIND_BY_ID);
        for (Integer id : allIdFilms) {
            router.setPagePath(url + id);
            HttpURLConnection httpURLConnection = kinopoiskService.connectToKinopoiskApi(router.getPagePath());
            StringBuffer responseContent = kinopoiskService.readResponse(httpURLConnection);
            Film film = null;
            try {
                film = kinopoiskService.parseJsonFilm(responseContent.toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            filmService.insertFilm(film);
        }
    }

    public void execute() {
        List<Integer> ids = filmService.findIdForTwoLastDay();
        Router router = new Router();
        try {
            urlProperties.load(getClass().getClassLoader().getResourceAsStream("url.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String url = urlProperties.getProperty(URL_FIND_BY_ID);
        for (Integer id : ids) {
            router.setPagePath(url + id);
            HttpURLConnection httpURLConnection = kinopoiskService.connectToKinopoiskApi(router.getPagePath());
            StringBuffer responseContent = kinopoiskService.readResponse(httpURLConnection);
            Film film = null;
            try {
                film = kinopoiskService.parseJsonFilm(responseContent.toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            filmService.insertFilm(film);
        }
    }


}
