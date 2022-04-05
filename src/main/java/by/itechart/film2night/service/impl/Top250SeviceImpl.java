package by.itechart.film2night.service.impl;

import by.itechart.film2night.dao.FilmTop250Dao;
import by.itechart.film2night.dao.impl.FilmTop250DaoImp;
import by.itechart.film2night.entity.FilmTop250;
import by.itechart.film2night.service.Top250Service;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class Top250SeviceImpl implements Top250Service {

    private final FilmTop250Dao filmTop250Dao = FilmTop250DaoImp.getInstance();
    private static Top250SeviceImpl instance;

    private Top250SeviceImpl() {
    }

    public static Top250SeviceImpl getInstance() {
        if (instance == null) {
            synchronized (Top250SeviceImpl.class) {
                if (instance == null) {
                    instance = new Top250SeviceImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public List<FilmTop250> findAllFilms(HttpServletRequest req) {
        return filmTop250Dao.findFilmTop250();
    }

    @Override
    public void deleteAllFilms() {
        filmTop250Dao.deleteAllFilms();
    }

    @Override
    public void insertFilms(List<FilmTop250> filmTop250List) {
        filmTop250Dao.insertFilm(filmTop250List);
    }

    @Override
    public List<Integer> findAllIdFilms() {
        return filmTop250Dao.findAllIdFilms();
    }
}
