package com.game.server.db;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Lee on 2016-06-03.
 */
public final class DBManager {
    private static final BasicDataSource dataSource = new BasicDataSource();

    static {
        dataSource.setDriverClassName("org.mariadb.jdbc.Driver");
        dataSource.setUrl("jdbc:mariadb://localhost:3306/test");
        dataSource.setUsername("root");
        dataSource.setPassword("qwe098");
    }
    private DBManager() {}

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
