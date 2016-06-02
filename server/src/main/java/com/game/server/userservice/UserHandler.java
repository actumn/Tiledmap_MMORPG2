package com.game.server.userservice;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;

import java.util.Date;

/**
 * Created by Lee on 2016-06-01.
 */
public class UserHandler extends SimpleChannelInboundHandler<String> {
    private static Logger logger = LogManager.getLogger("UserService");
    private UserDispatcher dispatcher = new UserDispatcher();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("[" + new Date().toString() + "] Client connection - " + ctx.channel().remoteAddress());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("[" + new Date().toString() + "] Client disconnection - " + ctx.channel().remoteAddress());
        dispatcher.logout();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
        JSONObject data = ResponseGenerator.stringToJson(s);
        dispatcher.dispatch(ctx, data);
    }

}
