package by.itechart.film2night.service;

import by.itechart.film2night.entity.Film;

import java.util.List;

public interface FilmService {

    Film findById(int id);

    void insertFilm(Film film);

    List<Integer> findIdForTwoLastDay();

    String findPosterUrl(int id);
}
