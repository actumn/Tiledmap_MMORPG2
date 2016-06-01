package com.game.server.service.object;

import io.netty.channel.Channel;

/**
 * Created by Lee on 2016-06-01.
 */
public class UserObject {
    public static int uniqueId;

    private long uuid;
    private Channel channel;

    private String name;
    private int x,y;

}
