package ru.gb.testing.toyshop.data;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * Работа с БД
 */
public class SQLiteConnection {
    private static String sqlConnectionString = "jdbc:sqlite:\\database\\sqlite:.db";
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        // Если connection создается первый раз или ранее был закрыт - то нужно открыть новый connection
        // Самое главное - открывать соединение только по необходимости, а не держать его постоянно открытым (иначе вы будете блокировать файл БД)
        if (connection == null || connection.isClosed()){
            try {
                connection = DriverManager.getConnection(sqlConnectionString);
            } catch (SQLException ex) {
                Logger.getAnonymousLogger().info(ex.getMessage());
            }
        }

        return connection;
    }
}
