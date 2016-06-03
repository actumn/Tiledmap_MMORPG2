package com.game.server.userservice;

import com.game.server.db.DBManager;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.json.simple.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by Lee on 2016-06-02.
 */
public class UserDispatcher {
    private UserService userService;
    private ResponseGenerator responseGenerator;
    private UserObject user;


    public UserDispatcher(UserService userService) {
        this.userService = userService;
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

    public boolean join(Connection con, JSONObject packet) {

        return false;
    }

    public UserObject login(Connection con, JSONObject packet) {
        user = new UserObject();
        try {
            String sql = "SELECT * FROM 'users' WHERE ";
            PreparedStatement ps = con.prepareStatement(sql);
        } catch (SQLException e) {
            // there is DB error. no corresponding data
            e.printStackTrace();
        }
        return null;
    }


    public void logout(Connection con) {
        if (this.user != null) {
            this.userService.exitUser(user);
        }
    }




    public void setUser(UserObject user) {
        this.user = user;
    }
    public UserObject getUser() {
        return user;
    }

}
