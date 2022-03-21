package by.itechart.film2night.dao.impl;

import by.itechart.film2night.connection.ConnectionPool;
import by.itechart.film2night.dao.FilmDao;
import by.itechart.film2night.entity.Film;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FilmDaoImpl implements FilmDao {

    private static final String FIND_BY_ID = "SELECT f.id,f.name,f.posterUrl,f.posterUrlPreview,f.webUrl,f.is_blocked," +
                                                "f.year,f.ratingKinopoisk FROM film f WHERE id = ?";

    private static final String INSERT_FILM = "INSERT INTO film (id,name,posterUrl,posterUrlPreview,webUrl,is_blocked," +
                                                "year,ratingKinopoisk) values (?,?,?,?,?,?,?,?)";

    private ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static final Logger LOGGER = LogManager.getLogger();


    @Override
    public void insertFilm(Film film) {
        LOGGER.info("try to insert film");

        try(Connection connection = connectionPool.takeConnection();
            PreparedStatement insertFilmStatement=connection.prepareStatement(INSERT_FILM)) {
            insertFilmStatement.setInt(1,film.getId());
            insertFilmStatement.setString(2,film.getName());
            insertFilmStatement.setString(3,film.getPosterUrl());
            insertFilmStatement.setString(4,film.getPosterUrlPreview());
            insertFilmStatement.setString(5,film.getWebUrl());
            insertFilmStatement.setBoolean(6,film.getIs_blocked());
            insertFilmStatement.setInt(7,film.getYear());
            insertFilmStatement.setFloat(8,film.getRatingKinopoisk());
            int rowCount = insertFilmStatement.executeUpdate();
            if (rowCount != 0) {
                LOGGER.info("add film");
            } else {
                LOGGER.error("film do not add");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Film findFilmById(int id) {

        LOGGER.info("try to find film by id");
        try(Connection connection = connectionPool.takeConnection();
        PreparedStatement findFilm = connection.prepareStatement(FIND_BY_ID)){
            findFilm.setInt(1,id);
            ResultSet resultSet = findFilm.executeQuery();
            while(resultSet.next()){
                return createFilm(resultSet);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    private Film createFilm(ResultSet resultSet) throws SQLException {
        LOGGER.info("try to create film");
        int film_id = resultSet.getInt("id");
        String film_name = resultSet.getString("name");
        String film_posterUrl = resultSet.getString("posterUrl");
        String film_posterUrlPreview = resultSet.getString("posterUrlPreview");
        String film_webUrl = resultSet.getString("webUrl");
        Boolean film_is_blocked = resultSet.getBoolean("is_blocked");
        int film_year = resultSet.getInt("year");
        float film_rating = resultSet.getFloat("ratingKinipoisk");
        return new Film(film_id,film_name,film_posterUrl,film_posterUrlPreview,film_webUrl,film_is_blocked,film_year,film_rating);
    }
}
