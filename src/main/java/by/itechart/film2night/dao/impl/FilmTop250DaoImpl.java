package by.itechart.film2night.dao.impl;

import by.itechart.film2night.connection.ConnectionPool;
import by.itechart.film2night.dao.FilmTop250Dao;
import by.itechart.film2night.entity.FilmTop250;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FilmTop250DaoImpl implements FilmTop250Dao {

    private static final String INSERT_FILM = "INSERT INTO film_top_250 (id_film,name_film) values (?,?)";
    private static final String FIND_TOP_250 = "SELECT * FROM film_top_250";
    private static final String DELETE_ALL_FILMS = "DELETE FROM film_top_250";
    private static final String FIND_ALL_ID_FILMS = "SELECT id_film FROM kinopoiskdb.film_top_250";
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final Logger LOGGER = LogManager.getLogger();
    private static FilmTop250DaoImpl instance;

    private FilmTop250DaoImpl() {
    }

    public static FilmTop250DaoImpl getInstance() {
        if (instance == null) {
            synchronized (FilmTop250DaoImpl.class) {
                if (instance == null) {
                    instance = new FilmTop250DaoImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public List<FilmTop250> findFilmTop250() {
        LOGGER.info("find all 250 top films");
        List<FilmTop250> top250 = new ArrayList<>();
        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement findTop250 = connection.prepareStatement(FIND_TOP_250)) {
            ResultSet resultSet = findTop250.executeQuery();
            while (resultSet.next()) {
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
        LOGGER.info(filmTop250List);
        int rowCount = 0;
        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement insertFilmStatement = connection.prepareStatement(INSERT_FILM)) {
            for (FilmTop250 filmTop250 : filmTop250List) {
                insertFilmStatement.setInt(1, filmTop250.getId());
                insertFilmStatement.setString(2, filmTop250.getName());
                rowCount = insertFilmStatement.executeUpdate();
            }

            if (rowCount != 0) {
                LOGGER.info("add film_top_250");
            } else {
                LOGGER.error("film_top_250 do not add");
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to insert films");
        }
    }

    @Override
    public void deleteAllFilms() {
        LOGGER.info("try to delete film_top_250");

        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement insertFilmStatement = connection.prepareStatement(DELETE_ALL_FILMS)) {

            int rowCount = insertFilmStatement.executeUpdate();
            if (rowCount != 0) {
                LOGGER.info("delete film_top_250");
            } else {
                LOGGER.error("film_top_250 do not delete");
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to delete all films");
        }
    }

    @Override
    public List<Integer> findAllIdFilms() {
        LOGGER.info("find all id films");
        List<Integer> idList = new ArrayList<>();
        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement findAllId = connection.prepareStatement(FIND_ALL_ID_FILMS)) {
            ResultSet resultSet = findAllId.executeQuery();
            while (resultSet.next()) {
                idList.add(resultSet.getInt("id_film"));
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to find all films id");
        }
        return idList;
    }

    private FilmTop250 createFilmTop250(ResultSet resultSet) throws SQLException {
        LOGGER.info("try to create film_top_250");
        int filmId = resultSet.getInt("id");
        String filmName = resultSet.getString("name");
        FilmTop250 filmTop250 = new FilmTop250.filmTop250Builder()
                .setId(filmId)
                .setName(filmName)
                .build();
        return filmTop250;
    }
}
