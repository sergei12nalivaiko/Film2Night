package by.itechart.film2night.service;

import by.itechart.film2night.entity.Film;
import by.itechart.film2night.entity.FilmTop250;

import javax.servlet.http.HttpServletResponse;
import java.net.HttpURLConnection;
import java.util.List;

public interface KinopoiskService {

    HttpURLConnection connectToKinopoiskApi(String path,HttpServletResponse res);

    StringBuffer readResponse(HttpURLConnection connection);

    void writeResponse(StringBuffer responseContent, HttpServletResponse res);

    List<FilmTop250> parseJsonTop250Films(String responseBody);

    Film parseJsonFilm(String responseBody);

}
