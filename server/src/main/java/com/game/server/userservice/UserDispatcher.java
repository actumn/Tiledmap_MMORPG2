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

            case "move" :
                move(packet);
                break;

            case "attack" :
                attack(packet);
                break;

            case "chat" :
                chat(packet);
                break;
        }
    }

    /* public for junit test */

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
                channel.writeAndFlush(packetFactory.notify("회원가입 성공").toJSONString()+"\r\n");
            }
            ps.close();
            con.close();
        } catch (SQLException e) {
            // DB Error
            //e.printStackTrace();
            channel.writeAndFlush(packetFactory.notify("회원가입 실패. 중복된 아이디 또는 닉네임").toJSONString()+"\r\n");
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
                int dbid = rs.getInt("ID");
                int level = rs.getInt("LEVEL");
                int map_id = rs.getInt("MAP_ID");
                int job_id = rs.getInt("JOB_ID");
                int x = rs.getInt("X"); int y = rs.getInt("Y");

                this.user = new UserObject()
                        .channel(channel)
                        .dbid(dbid)
                        .name(user_name)
                        .level(level)
                        .jobId(job_id)
                        .mapId(map_id)
                        .XY(x, y);
                response = "로그인 성공";

                rs.close();
                ps.close();
                con.close();
            } else {
                // there is no corresponding data
                response = "일치하는 데이터가 없습니다";
            }
        } catch (SQLException e) {
            // there is DB error
            response = "DB error";
            e.printStackTrace();
        }
        finally {
            channel.writeAndFlush(packetFactory.notify(response).toJSONString()+"\r\n");

            /* if login failed */
            if (user != null ){
                channel.writeAndFlush(packetFactory.character
                        (user.getUuid(), user.getName(), user.getLevel(), user.getJobId()).toJSONString() +"\r\n");
                channel.writeAndFlush(packetFactory.move
                        (user.getUuid(), user.getMapId(), user.getX(), user.getY()).toJSONString() +"\r\n");

                this.service.loginUser(this.user);
            }
        }
    }

    public void move(JSONObject packet) {
        packet.put("id", user.getUuid());
        this.service.moveObject(this.user, packet);
    }

    public void chat(JSONObject packet) {
        packet.put("id", user.getUuid());
        this.service.chat(this.user, packet);
    }

    public void attack(JSONObject packet) {
        packet.put("id", user.getUuid());
        this.service.attack(this.user, packet);
    }

    public void logout(Connection con) {
        if (this.user != null) {
            this.service.exitUser(user);

            try {
                String sql = "UPDATE USERS SET LEVEL=?, MAP_ID=?, X=?, Y=? WHERE ID=?";
                PreparedStatement ps = con.prepareStatement(sql);

                ps.setString(1, String.valueOf(user.getLevel()));
                ps.setString(2, String.valueOf(user.getMapId()));
                ps.setString(3, String.valueOf(user.getX()));
                ps.setString(4, String.valueOf(user.getY()));
                ps.setString(5, String.valueOf(user.getDbid()));

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



    public void setUser(UserObject user) {
        this.user = user;
    }
    public UserObject getUser() {
        return user;
    }

}
