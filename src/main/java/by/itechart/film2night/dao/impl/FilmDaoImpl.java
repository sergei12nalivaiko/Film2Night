package by.itechart.film2night.dao.impl;

import by.itechart.film2night.connection.ConnectionPool;
import by.itechart.film2night.dao.CountryDao;
import by.itechart.film2night.dao.FilmDao;
import by.itechart.film2night.dao.GenreDao;
import by.itechart.film2night.entity.Film;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.*;
import java.text.ParseException;
import java.util.*;


public class FilmDaoImpl implements FilmDao {

    private static final String FIND_BY_ID = "SELECT * FROM film f WHERE f.kinopoiskId = ?";
    private static final String INSERT_FILM = "INSERT INTO film (kinopoiskId,nameOriginal,posterUrl,ratingKinopoisk," +
            "ratingKinopoiskVoteCount,webUrl,year,filmLength,country_id,genre_id,lastSync,isBlocked)" +
            "values (?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String DELETE_FILM_BY_ID = "DELETE FROM film WHERE kinopoiskId = ?";
    private static final String FIND_ALL_ID_BY_DATE_BEFORE = "SELECT distinct kinopoiskId FROM kinopoiskdb.film  WHERE lastSync < ?";
    private static final String FIND_POSTER_BY_ID = "SELECT distinct posterUrl FROM kinopoiskdb.film where kinopoiskId = ?";
    private static final Logger LOGGER = LogManager.getLogger();
    private GenreDao genreDaoImp = GenreDaoImpl.getInstance();
    private final CountryDao countryDaoImp = CountryDaoImpl.getInstance();
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static FilmDaoImpl instance;

    private FilmDaoImpl() {
    }

    public static FilmDaoImpl getInstance() {
        if (instance == null) {
            synchronized (FilmDaoImpl.class) {
                if (instance == null) {
                    instance = new FilmDaoImpl();
                }
            }
        }
        return instance;
    }


    @Override
    public void insertFilm(Film film) {

        LOGGER.info("try to insert film");
        LOGGER.info(film);
        List<String> countryList = new ArrayList<>(film.getCountries());
        List<String> genreList = new ArrayList<>(film.getGenres());
        Film filmDuplicate = findFilmById(film.getKinopoiskId());
        LOGGER.info(film.getKinopoiskId());
        LOGGER.info(filmDuplicate);
        int length = countryList.size() > genreList.size() ? countryList.size() : genreList.size();

        LOGGER.warn(countryList.get(0));

        if (filmDuplicate != null) {

            deleteFilmById(film.getKinopoiskId());
        }

        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement insertFilmStatement = connection.prepareStatement(INSERT_FILM)) {
            int countryId = 0;
            int genreId = 0;
            for (int i = 0; i < length; i++) {

                if (i < countryList.size()) {
                    countryId = countryDaoImp.findIdByName(countryList.get(i));
                }
                if (i < genreList.size()) {
                    genreId = genreDaoImp.findIdByName(genreList.get(i));
                }

                insertFilmStatement.setInt(1, film.getKinopoiskId());
                insertFilmStatement.setString(2, film.getNameOriginal());
                insertFilmStatement.setString(3, film.getPosterUrl());
                insertFilmStatement.setFloat(4, film.getRatingKinopoisk());
                insertFilmStatement.setInt(5, film.getRatingKinopoiskVoteCount());
                insertFilmStatement.setString(6, film.getWebUrl());
                insertFilmStatement.setInt(7, film.getYear());
                insertFilmStatement.setInt(8, film.getFilmLength());
                insertFilmStatement.setInt(9, countryId);
                insertFilmStatement.setInt(10, genreId);
                insertFilmStatement.setTimestamp(11, film.getLastSync());
                insertFilmStatement.setString(12, film.getIs_blocked());
                int rowCount = insertFilmStatement.executeUpdate();
                if (rowCount != 0) {
                    LOGGER.info("add film");
                } else {
                    LOGGER.error("film do not add");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to insert film");
            e.printStackTrace();
        }
    }

    @Override
    public Film findFilmById(int id) {

        LOGGER.info("try to find film by id");
        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement findFilm = connection.prepareStatement(FIND_BY_ID)) {
            findFilm.setInt(1, id);
            ResultSet resultSet = findFilm.executeQuery();
            while (resultSet.next()) {
                return createFilm(resultSet);
            }
        } catch (SQLException | ParseException e) {
            LOGGER.error("Failed to find film by id");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteFilmById(int id) {
        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement deleteFilmStatement = connection.prepareStatement(DELETE_FILM_BY_ID)) {
            deleteFilmStatement.setInt(1, id);
            int rowCount = deleteFilmStatement.executeUpdate();
            if (rowCount != 0) {
                LOGGER.info("delete film by id");
            } else {
                LOGGER.error(" do not delete film by id");
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to delete film by id");
            e.printStackTrace();
        }
    }

    @Override
    public List<Integer> findIdForTwoLastDay() {
        LOGGER.error("findIdForTwoLastDay");
        Timestamp currentDate = new Timestamp(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currentDate.getTime());
        calendar.add(Calendar.DAY_OF_MONTH, -2);
        Timestamp dateBefore = new Timestamp(calendar.getTime().getTime());
        LOGGER.warn(dateBefore);

        List<Integer> idList = new ArrayList<>();
        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement findAllId = connection.prepareStatement(FIND_ALL_ID_BY_DATE_BEFORE)) {
            findAllId.setTimestamp(1, dateBefore);
            ResultSet resultSet = findAllId.executeQuery();
            while (resultSet.next()) {
                idList.add(resultSet.getInt("kinopoiskId"));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return idList;
    }

    @Override
    public String findPosterUrl(int id) {
        LOGGER.info("try to find poster by id");
        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement findFilm = connection.prepareStatement(FIND_POSTER_BY_ID)) {
            findFilm.setInt(1, id);
            ResultSet resultSet = findFilm.executeQuery();
            while (resultSet.next()) {
                return resultSet.getString("posterUrl");
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to find poster url by film id");
            e.printStackTrace();
        }
        return null;
    }

    private Film createFilm(ResultSet resultSet) throws SQLException, ParseException {
        LOGGER.info("try to create film");
        int filmId = resultSet.getInt("kinopoiskId");
        String filmNameOriginal = resultSet.getString("nameOriginal");
        String filmPosterUrl = resultSet.getString("posterUrl");
        float filmRating = resultSet.getFloat("ratingKinopoisk");
        int filmRatingKinopoiskVoteCount = resultSet.getInt("ratingKinopoiskVoteCount");
        String filmWebUrl = resultSet.getString("webUrl");
        int filmYear = resultSet.getInt("year");
        int filmFilmLength = resultSet.getInt("filmLength");
        String filmLastSync = resultSet.getString("lastSync");
        String filmIsBlocked = resultSet.getString("isBlocked");
        Timestamp lastSyn = Timestamp.valueOf(filmLastSync);
        LOGGER.info(lastSyn);

        return new Film.filmBuilder()
                .setKinopoiskId(filmId)
                .setNameOriginal(filmNameOriginal)
                .setPosterUrl(filmPosterUrl)
                .setRatingKinopoisk(filmRating)
                .setRatingKinopoiskVoteCount(filmRatingKinopoiskVoteCount)
                .setWebUrl(filmWebUrl)
                .setYear(filmYear)
                .setFilmLength(filmFilmLength)
                .setLastSync(lastSyn)
                .setIs_blocked(filmIsBlocked)
                .build();
    }
}
