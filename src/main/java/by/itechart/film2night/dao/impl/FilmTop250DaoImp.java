package by.itechart.film2night.dao.impl;

import by.itechart.film2night.connection.ConnectionPool;
import by.itechart.film2night.dao.FilmTop250Dao;
import by.itechart.film2night.entity.Film;
import by.itechart.film2night.entity.FilmTop250;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FilmTop250DaoImp implements FilmTop250Dao {

    private static final String INSERT_FILM = "INSERT INTO film_top_250 (id,name,year,rating,posterUrl,posterUrlPreview)" +
                                                " values (?,?,?,?,?,?)";

    private static final String FIND_TOP_250 = "SELECT * FROM film_top_250";

    private static final String DELETE_ALL_FILMS = "DELETE FROM film_top_250";

    private ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public List<FilmTop250> findFilmTop250() {
       LOGGER.info("find all 250 top films");
        List<FilmTop250> top250 = new ArrayList<>();
        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement findTop250 = connection.prepareStatement(FIND_TOP_250)){
             ResultSet resultSet = findTop250.executeQuery();
             while (resultSet.next()){
                top250.add(createFilmTop250(resultSet));
             }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return top250;
    }

    @Override
    public void insertFilm(List<FilmTop250> filmTop250List) {
        LOGGER.info("try to insert film_top_250");
        int rowCount=0;
        try(Connection connection = connectionPool.takeConnection();
            PreparedStatement insertFilmStatement=connection.prepareStatement(INSERT_FILM)) {
            for (FilmTop250 filmTop250:filmTop250List){
                insertFilmStatement.setInt(1,filmTop250.getId());
                insertFilmStatement.setString(2,filmTop250.getName());
                insertFilmStatement.setInt(3,filmTop250.getYear());
                insertFilmStatement.setFloat(4,filmTop250.getRating());
                insertFilmStatement.setString(5,filmTop250.getPosterUrl());
                insertFilmStatement.setString(6,filmTop250.getPosterUrlPreview());
                rowCount = insertFilmStatement.executeUpdate();
            }


            if (rowCount != 0) {
                LOGGER.info("add film_top_250");
            } else {
                LOGGER.error("film_top_250 do not add");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAllFilms(){
        LOGGER.info("try to delete film_top_250");

        try(Connection connection = connectionPool.takeConnection();
            PreparedStatement insertFilmStatement=connection.prepareStatement(DELETE_ALL_FILMS)) {

            int rowCount = insertFilmStatement.executeUpdate();
            if (rowCount != 0) {
                LOGGER.info("delete film_top_250");
            } else {
                LOGGER.error("film_top_250 do not delete");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private FilmTop250 createFilmTop250(ResultSet resultSet) throws SQLException {
        LOGGER.info("try to create film_top_250");
            int film_id = resultSet.getInt("id");
            String film_name = resultSet.getString("name");
            int film_year = resultSet.getInt("year");
            float film_rating = resultSet.getFloat("rating");
            String film_posterUrl = resultSet.getString("posterUrl");
            String film_posterUrlPreview = resultSet.getString("posterUrlPreview");
        return  new FilmTop250(film_id,film_name,film_year,film_rating,film_posterUrl,film_posterUrlPreview);
    }
}
