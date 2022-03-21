package by.itechart.film2night.command.impl;

import by.itechart.film2night.command.Command;
import by.itechart.film2night.command.Router;
import by.itechart.film2night.dao.impl.FilmDaoImpl;
import by.itechart.film2night.dao.impl.FilmTop250DaoImp;
import by.itechart.film2night.entity.Film;
import by.itechart.film2night.service.FilmService;
import by.itechart.film2night.service.KinopoiskService;
import by.itechart.film2night.service.Top250Service;
import by.itechart.film2night.service.impl.FilmServiceImpl;
import by.itechart.film2night.service.impl.KinopoiskServiceImpl;
import by.itechart.film2night.service.impl.Top250SeviceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.HttpURLConnection;

public class LoadFilmByIdCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger();
    private KinopoiskService kinopoiskService = new KinopoiskServiceImpl();
    private FilmService filmService  = new FilmServiceImpl(new FilmDaoImpl());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse res) {
        Router router = new Router();
        router.setPagePath("https://kinopoiskapiunofficial.tech/api/v2.2/films/"+request.getAttribute("id"));
        HttpURLConnection httpURLConnection=kinopoiskService.connectToKinopoiskApi(router.getPagePath(),res);
        StringBuffer responseContent = kinopoiskService.readResponse(httpURLConnection);
        kinopoiskService.writeResponse(responseContent,res);
        Film film = kinopoiskService.parseJsonFilm(responseContent.toString());
        filmService.insertFilm(film);
    }
}
