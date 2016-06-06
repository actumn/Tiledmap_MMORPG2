package com.game.server.userservice;

import com.game.server.db.DBManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.util.Date;

/**
 * Created by Lee on 2016-06-01.
 */
public class UserHandler extends SimpleChannelInboundHandler<String> {
    private static Logger logger = LogManager.getLogger("UserService");
    private UserDispatcher dispatcher;

    public UserHandler(UserService userService) {
        this.dispatcher = new UserDispatcher(userService);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("[" + new Date().toString() + "] Client connection - " + ctx.channel().remoteAddress());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("[" + new Date().toString() + "] Client disconnection - " + ctx.channel().remoteAddress());
        dispatcher.logout(DBManager.getConnection());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
        JSONObject data = stringToJson(s);
        dispatcher.dispatch(ctx.channel(), data);
    }


    public static JSONObject stringToJson(String s) {
        return (JSONObject) JSONValue.parse(s);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
