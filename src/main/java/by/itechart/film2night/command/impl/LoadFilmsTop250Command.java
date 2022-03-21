package by.itechart.film2night.command.impl;

import by.itechart.film2night.command.Command;
import by.itechart.film2night.command.Router;
import by.itechart.film2night.dao.impl.FilmTop250DaoImp;
import by.itechart.film2night.entity.FilmTop250;
import by.itechart.film2night.service.KinopoiskService;
import by.itechart.film2night.service.Top250Service;
import by.itechart.film2night.service.impl.KinopoiskServiceImpl;
import by.itechart.film2night.service.impl.Top250SeviceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.HttpURLConnection;
import java.util.List;

public class LoadFilmsTop250Command implements Command {

    private static final Logger LOGGER = LogManager.getLogger();
    private KinopoiskService kinopoiskService = new KinopoiskServiceImpl();
    private Top250Service top250Service = new Top250SeviceImpl(new FilmTop250DaoImp());
    private List<FilmTop250> filmTop250List;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse res) {

        Router router = new Router();
        router.setPagePath("https://kinopoiskapiunofficial.tech/api/v2.2/films/top?type=TOP_250_BEST_FILMS");
        HttpURLConnection httpURLConnection=kinopoiskService.connectToKinopoiskApi(router.getPagePath(),res);
        StringBuffer responseContent = kinopoiskService.readResponse(httpURLConnection);
        kinopoiskService.writeResponse(responseContent,res);
        filmTop250List = kinopoiskService.parseJsonTop250Films(responseContent.toString());
        top250Service.deleteAllFilms();
        top250Service.insertFilms(filmTop250List);
    }
}
