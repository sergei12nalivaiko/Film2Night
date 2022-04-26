package by.itechart.film2night.dao.impl;

import by.itechart.film2night.connection.ConnectionPool;
import by.itechart.film2night.dao.CountryDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryDaoImpl implements CountryDao {

    private static final String FIND_ID_BY_NAME = "SELECT id_country FROM kinopoiskdb.country  WHERE name_country=?";
    private static final Integer DEFAULT_COUNTRY_ID = 1;
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final Logger LOGGER = LogManager.getLogger();

    private static CountryDaoImpl instance;

    private CountryDaoImpl() {
    }

    public static CountryDaoImpl getInstance() {
        if (instance == null) {
            synchronized (CountryDaoImpl.class) {
                if (instance == null) {
                    instance = new CountryDaoImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public int findIdByName(String name) {
        LOGGER.info("try to find ID by country_name");

        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement findId = connection.prepareStatement(FIND_ID_BY_NAME)) {
            findId.setString(1, name);
            ResultSet resultSet = findId.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id_country");
                LOGGER.warn(id);
                return id;
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to find country by id");
            e.printStackTrace();
        }
        return DEFAULT_COUNTRY_ID;
    }
}
