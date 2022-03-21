package by.itechart.film2night.service.impl;

import by.itechart.film2night.entity.Film;
import by.itechart.film2night.entity.FilmTop250;
import by.itechart.film2night.service.KinopoiskService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class KinopoiskServiceImpl implements KinopoiskService {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String apiKey = "2b6107f3-1a75-4568-9277-82f26d953833";
    private HttpURLConnection connection;

    @Override
    public HttpURLConnection connectToKinopoiskApi(String path,HttpServletResponse res) {
        try {
            URL url = new URL(path);
            connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty("x-api-key", apiKey);
            connection.setRequestMethod("GET");
            connection.addRequestProperty("Content-Type", "application/json");


            res.setContentType("application/json");
            res.setCharacterEncoding("UTF-8");

            int status = connection.getResponseCode();

            if(status<299){
               LOGGER.info("Connect successfull");
               return connection;
            }else{
                LOGGER.info("Connect is not successful");
                }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public StringBuffer readResponse(HttpURLConnection connection) {
        StringBuffer responseContent = new StringBuffer();
        BufferedReader reader;
        String line;
        try {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
                    while((line = reader.readLine()) != null){
                        responseContent.append(line);
                        LOGGER.info(responseContent);}
            reader.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseContent;
    }

    @Override
    public void writeResponse(StringBuffer responseContent, HttpServletResponse res) {
        PrintWriter printWriter;
        try {
            printWriter=res.getWriter();
            printWriter.print(responseContent);
        } catch (IOException e) {
            e.printStackTrace();
        }

        LOGGER.info(responseContent);
    }

    @Override
    public List<FilmTop250> parseJsonTop250Films(String responseBody) {

        List<FilmTop250> filmTop250List = new ArrayList<>();
        LOGGER.info("parseJsonTop250Films");
        LOGGER.info("in parse");
        JSONObject jsonObject1 =  new JSONObject(responseBody);

        JSONArray rows = jsonObject1.getJSONArray("films");
        //FilmTop250Dao filmTop250Dao =new FilmTop250DaoImp();
        //filmTop250Dao.deleteAllFilms();

        for (int i =0;i<rows.length();i++){
            JSONObject jsonObject = rows.getJSONObject(i);
            LOGGER.info(jsonObject);
            int id = jsonObject.getInt("filmId");
            LOGGER.info(id);
            String name = jsonObject.getString("nameRu");
            LOGGER.info(name);
            String posterUrl = jsonObject.getString("posterUrl");
            LOGGER.info(posterUrl);
            String posterUrlPreview = jsonObject.getString("posterUrlPreview");
            LOGGER.info(posterUrlPreview);
            int year = jsonObject.getInt("year");
            LOGGER.info(year);
            float rating = jsonObject.getFloat("rating");
            LOGGER.info(rating);
            FilmTop250 filmTop250 = new FilmTop250(id,name,year,rating,posterUrl,posterUrlPreview);
            filmTop250List.add(filmTop250);
            //filmTop250Dao.insertFilm(filmTop250);
        }
        return filmTop250List;
    }

    @Override
    public Film parseJsonFilm(String responseBody) {

        LOGGER.info("in parse");
        JSONObject jsonObject =  new JSONObject(responseBody);
        LOGGER.info(jsonObject);
        int id = jsonObject.getInt("kinopoiskId");
        LOGGER.info(id);
        String name = jsonObject.getString("nameRu");
        LOGGER.info(name);
        String posterUrl = jsonObject.getString("posterUrl");
        LOGGER.info(posterUrl);
        String posterUrlPreview = jsonObject.getString("posterUrlPreview");
        LOGGER.info(posterUrlPreview);
        String webUrl = jsonObject.getString("webUrl");
        LOGGER.info(webUrl);
        int year = jsonObject.getInt("year");
        LOGGER.info(year);
        float ratingKinopoisk = jsonObject.getFloat("ratingKinopoisk");
        LOGGER.info(ratingKinopoisk);
        Film film = new Film(id,name,posterUrl,posterUrlPreview,webUrl,false,year,ratingKinopoisk);
        return film;
    }
}
