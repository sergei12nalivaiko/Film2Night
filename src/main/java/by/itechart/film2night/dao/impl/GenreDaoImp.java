package by.itechart.film2night.dao.impl;

import by.itechart.film2night.connection.ConnectionPool;
import by.itechart.film2night.dao.GenreDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreDaoImp implements GenreDao {

    private static final String FIND_ID_BY_NAME = "SELECT id_genre FROM kinopoiskdb.genre  WHERE name_genre=?";
    private static final Integer DefaultGenreId = 1;
    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final Logger LOGGER = LogManager.getLogger();
    private static GenreDaoImp instance;

    private GenreDaoImp() {
    }

    public static GenreDaoImp getInstance() {
        if (instance == null) {
            synchronized (GenreDaoImp.class) {
                if (instance == null) {
                    instance = new GenreDaoImp();
                }
            }
        }
        return instance;
    }

    @Override
    public int findIdByName(String name) {
        LOGGER.info("try to find ID by genre_name");
        LOGGER.warn(name);

        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement findId = connection.prepareStatement(FIND_ID_BY_NAME)) {
            findId.setString(1, name);
            ResultSet resultSet = findId.executeQuery();
            LOGGER.warn(resultSet.getInt("id_genre"));
            while (resultSet.next()) {
                int id = resultSet.getInt("id_genre");
                return id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return DefaultGenreId;
    }

    @Override
    public int findIdByName(String name, Connection connection) throws SQLException {
        LOGGER.info("try to find ID by genre_name");
        PreparedStatement findId = connection.prepareStatement(FIND_ID_BY_NAME);
        findId.setString(1, name);
        ResultSet resultSet = findId.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt("id_genre");
            return id;
        }
        return DefaultGenreId;
    }
}
