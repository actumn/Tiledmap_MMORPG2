package com.game.server.userservice;

import io.netty.channel.ChannelHandlerContext;
import org.json.simple.JSONObject;

/**
 * Created by Lee on 2016-06-02.
 */
public class UserDispatcher {
    private ResponseGenerator responseGenerator;
    private UserObject user;

    public void dispatch(ChannelHandlerContext ctx, JSONObject packet) {
        packet.get("type");
    }

    public UserObject loadCharacter() {
        user = new UserObject();
        return null;
    }

    public void logout() {
        if (this.user != null) {

        }
    }
}
