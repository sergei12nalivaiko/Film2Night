package by.itechart.film2night.dao;

import by.itechart.film2night.entity.Film;

public interface FilmDao {

    Film findFilmById(int id);
    void insertFilm(Film  film);
}
