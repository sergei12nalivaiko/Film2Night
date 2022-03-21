package by.itechart.film2night.dao;

import by.itechart.film2night.entity.Film;
import by.itechart.film2night.entity.FilmTop250;

import java.util.List;

public interface FilmTop250Dao {

    List<FilmTop250> findFilmTop250();
    void insertFilm(List<FilmTop250> filmTop250);
    void deleteAllFilms();
}
