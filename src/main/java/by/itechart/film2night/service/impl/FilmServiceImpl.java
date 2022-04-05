package by.itechart.film2night.service.impl;

import by.itechart.film2night.dao.FilmDao;
import by.itechart.film2night.dao.impl.FilmDaoImpl;
import by.itechart.film2night.entity.Film;
import by.itechart.film2night.service.FilmService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;


public class FilmServiceImpl implements FilmService {


    private List<Integer> ids = new ArrayList<>();
    private FilmDao filmDao = FilmDaoImpl.getInstance();
    private static final Logger LOGGER = LogManager.getLogger();

    private static FilmServiceImpl instance;

    private FilmServiceImpl() {
    }

    public static FilmServiceImpl getInstance() {
        if (instance == null) {
            synchronized (FilmServiceImpl.class) {
                if (instance == null) {
                    instance = new FilmServiceImpl();
                }
            }
        }
        return instance;
    }


    @Override
    public Film findById(int id) {
        LOGGER.info("find film by id");
        Film film = filmDao.findFilmById(id);
        return film;
    }

    @Override
    public void insertFilm(Film film) {
        filmDao.insertFilm(film);
    }

    @Override
    public List<Integer> findIdForTwoLastDay() {
        ids = filmDao.findIdForTwoLastDay();
        return ids;
    }

    @Override
    public String findPosterUrl(int id) {
        return filmDao.findPosterUrl(id);
    }
}
