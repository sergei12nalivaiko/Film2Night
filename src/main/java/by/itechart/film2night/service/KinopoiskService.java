package by.itechart.film2night.service;

import by.itechart.film2night.entity.Film;
import by.itechart.film2night.entity.FilmTop250;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.text.ParseException;
import java.util.List;

public interface KinopoiskService {

    HttpURLConnection connectToKinopoiskApi(String path, HttpServletResponse res);

    HttpURLConnection connectToKinopoiskApi(String path);

    StringBuffer readResponse(HttpURLConnection connection);

    void writeResponse(StringBuffer responseContent, HttpServletResponse res);

    List<FilmTop250> parseJsonTop250Films(String responseBody);

    Film parseJsonFilm(String responseBody) throws ParseException;

    void downloadPoster(String urlString) throws IOException;

    String parseUrl(String path);


}
