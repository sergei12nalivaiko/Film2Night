package by.itechart.film2night.service.impl;

import by.itechart.film2night.dao.FilmDao;
import by.itechart.film2night.entity.Film;
import by.itechart.film2night.service.FilmService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class FilmServiceImpl implements FilmService {

    private final  FilmDao filmDao;

    public FilmServiceImpl(FilmDao filmDao) {
        this.filmDao = filmDao;
    }

    private static final Logger LOGGER = LogManager.getLogger();


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
}
