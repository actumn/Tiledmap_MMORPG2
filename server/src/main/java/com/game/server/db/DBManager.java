package com.game.server.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Lee on 2016-06-02.
 */
public class DBManager {
    private static DBManager instance = new DBManager();
    public static DBManager getInstance() { return instance; }

    public DBManager() { }

    public Connection getConnection() throws SQLException {
        // DriverManager.getConnection("jdbc:mariadb://ip:port/DBName?option=blah", id, password)
        return null;
    }
}
