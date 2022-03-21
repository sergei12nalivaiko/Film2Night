package by.itechart.film2night.service.impl;

import by.itechart.film2night.dao.FilmTop250Dao;
import by.itechart.film2night.entity.FilmTop250;
import by.itechart.film2night.service.Top250Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class Top250SeviceImpl implements Top250Service {
    private static final Logger LOGGER= LogManager.getLogger();
    private final FilmTop250Dao filmTop250Dao;

    public Top250SeviceImpl(FilmTop250Dao filmTop250DaoImpl) {
        this.filmTop250Dao = filmTop250DaoImpl;
    }

    @Override
    public List<FilmTop250> findAllFilms(HttpServletRequest req) {
        List<FilmTop250> films= filmTop250Dao.findFilmTop250();
        return films;
    }

    @Override
    public void deleteAllFilms() {
        filmTop250Dao.deleteAllFilms();
    }

    @Override
    public void insertFilms(List<FilmTop250> filmTop250List) {
        filmTop250Dao.insertFilm(filmTop250List);
    }
}
