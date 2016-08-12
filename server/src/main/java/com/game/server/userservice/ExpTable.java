package com.game.server.userservice;

import com.game.server.db.DBManager;

import java.sql.*;
import java.util.HashMap;

/**
 * Created by Lee on 2016-08-12.
 */
public class ExpTable {
    private static ExpTable instance;
    private HashMap<Integer, Integer> expTable;

    public ExpTable(Connection con) {
        instance = this;
        this.expTable = new HashMap<>();

        try {
            String sql = "SELECT * FROM EXPS";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()) {
                int level = rs.getInt("level");
                int exp = rs.getInt("exp");

                expTable.put(level, exp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private int getExp(int level) {
        return expTable.get(level) != null? expTable.get(level) : 9999999;
    }


    public static int get(int level) {
        return instance.getExp(level);
    }
}
