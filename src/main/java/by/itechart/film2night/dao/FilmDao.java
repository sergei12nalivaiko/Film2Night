package by.itechart.film2night.dao;

import by.itechart.film2night.entity.Film;
import java.util.List;

public interface FilmDao {

    Film findFilmById(int id);

    void insertFilm(Film film);

    void deleteFilmById(int id);

    List<Integer> findIdForTwoLastDay();

    String findPosterUrl(int id);
}
