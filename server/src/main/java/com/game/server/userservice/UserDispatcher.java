package com.game.server.userservice;

import com.game.server.db.DBManager;
import io.netty.channel.Channel;
import org.json.simple.JSONObject;
import protocol.Packet.JsonPacketFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Lee on 2016-06-02.
 */
public class UserDispatcher {
    private JsonPacketFactory packetFactory;
    private UserService service;
    private UserObject user;


    public UserDispatcher(UserService userService) {
        this.service = userService;
        this.packetFactory = new JsonPacketFactory();
    }

    public void dispatch(Channel channel, JSONObject packet) throws SQLException {
        String type = (String) packet.get("type");

        switch (type) {
            case "join" :
                join(DBManager.getConnection(), channel, packet);
                break;

            case "login" :
                login(DBManager.getConnection(), channel, packet);
                break;

            case "logout" :
                break;

            case "move" :
                break;

            case "chat" :
                break;
        }
    }

    public void join(Connection con, Channel channel, JSONObject packet) {
        String user_id = (String) packet.get("user_id");
        String user_pw = (String) packet.get("user_pw");
        String user_name = (String) packet.get("user_name");
        int job_id = (int)(long) packet.get("job_id");

        try {
            String sql = "INSERT INTO USERS (USER_ID, USER_PW, USER_NAME, JOB_ID) VALUES(?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user_id);
            ps.setString(2, user_pw);
            ps.setString(3, user_name);
            ps.setInt(4, job_id);

            if(ps.executeUpdate() > 0) {
                channel.writeAndFlush(packetFactory.notify("join success").toJSONString());
            }
            ps.close();
            con.close();
        } catch (SQLException e) {
            // DB Error
            //e.printStackTrace();
            channel.writeAndFlush(packetFactory.notify("join fail").toJSONString());
        }
    }

    public void login(Connection con, Channel channel, JSONObject packet) {
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
                response = "login success";

                rs.close();
                ps.close();
                con.close();
            } else {
                // there is no corresponding data
                response = "no corresponding data";
            }
        } catch (SQLException e) {
            // there is DB error
            response = "DB error";
            e.printStackTrace();
        }
        finally {
            channel.writeAndFlush(packetFactory.notify(response).toJSONString());

            /* if login failed */
            if (user != null ){
                channel.writeAndFlush(packetFactory.character
                        (user.getUuid(), user.getName(), user.getLevel(), user.getJobId()).toJSONString());

                this.service.loginUser(this.user);

                channel.writeAndFlush(packetFactory.move
                        (user.getUuid(), user.getMapId(), user.getX(), user.getY()).toJSONString());
            }
        }
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
