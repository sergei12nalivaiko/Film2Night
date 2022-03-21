package by.itechart.film2night.service;

import by.itechart.film2night.entity.FilmTop250;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface Top250Service {

    List<FilmTop250> findAllFilms(HttpServletRequest req);
    void deleteAllFilms();
    void insertFilms(List<FilmTop250> filmTop250List);

}
