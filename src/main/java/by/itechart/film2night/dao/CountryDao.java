package by.itechart.film2night.dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface CountryDao {

    int findIdByName(String name);

    int findIdByName(String name, Connection connection) throws SQLException;
}