package com.game.server.userservice;

import com.game.server.db.DBManager;
import io.netty.channel.Channel;
import org.json.simple.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Lee on 2016-06-02.
 */
public class UserDispatcher {
    private UserService service;
    private UserObject user;


    public UserDispatcher(UserService userService) {
        this.service = userService;
    }

    public void dispatch(Channel channel, JSONObject packet) throws SQLException {
        String type = (String) packet.get("type");

        switch (type) {
            case "join" :
                join(DBManager.getConnection(), packet);
                break;

            case "login" :
                break;

            case "logout" :
                break;

            case "move" :
                break;

            case "chat" :
                break;
        }
    }

    public String join(Connection con, JSONObject packet) {
        String response = "";

        return response;
    }

    /* using sql */
    public String login(Connection con, Channel channel, JSONObject packet) {
        String response = null;

        String user_id = (String) packet.get("user_id");
        String user_pw = (String) packet.get("user_pw");

        try {
            String sql = "SELECT * FROM USERS WHERE USER_ID=? AND USER_PW=?;";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user_id);
            ps.setString(2, user_pw);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String user_name = rs.getString("USER_NAME");
                int uuid = rs.getInt("ID");
                int level = rs.getInt("LEVEL");
                int map_id = rs.getInt("MAP_ID");
                int job_id = rs.getInt("JOB_ID");
                int x = rs.getInt("X"); int y = rs.getInt("Y");

                this.user = new UserObject()
                        .channel(channel)
                        .uuid(uuid)
                        .name(user_name)
                        .level(level)
                        .jobId(job_id)
                        .mapId(map_id)
                        .XY(x, y);

                this.service.loginUser(this.user);

                rs.close();
                ps.close();
                con.close();
                response = "login success";
            } else {
                // there is no corresponding data
                response = "no corresponding data";
            }
        } catch (SQLException e) {
            // there is DB error
            response = "DB error";
            e.printStackTrace();
        }

        return response;
    }


    public void logout(Connection con) {
        if (this.user != null) {
            this.service.exitUser(user);
        }
    }



    public void setUser(UserObject user) {
        this.user = user;
    }
    public UserObject getUser() {
        return user;
    }

}
