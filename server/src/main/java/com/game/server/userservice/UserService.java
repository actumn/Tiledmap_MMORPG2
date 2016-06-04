package com.game.server.userservice;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Lee on 2016-06-01.
 */
public class UserService {
    private HashMap<Integer, MapProxy> maps = new HashMap<Integer, MapProxy>();
    private LinkedList<UserObject> users = new LinkedList<UserObject>();

    public void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();


        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();

                        p.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
                        p.addLast("decoder", new StringDecoder(Charset.defaultCharset()));
                        p.addLast("encoder", new StringEncoder(Charset.defaultCharset()));

                        p.addLast("handler", new UserHandler(UserService.this));
                    }
                })
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
    }

    public void loginUser(final UserObject user) {
        final int mapId = user.getMapId();

        MapProxy map = getMapProxy(mapId, true);
        user.initMap(map, user.getX(), user.getY());

        this.users.add(user);
    }

    public void exitUser(final UserObject user) {
        final int mapId = user.getMapId();

        MapProxy map = getMapProxy(mapId, false);
        if (map == null) return;
        map.exitUser(user);
        if (map.isEmpty()) this.maps.remove(mapId, map);

        this.users.remove(user);
    }

    public MapProxy getMapProxy(int mapId, boolean makable) {
        if(!maps.containsKey(mapId)) {
            if (!makable) return null;

            MapProxy newMap = new MapProxy(mapId);
            maps.put(mapId, newMap);
        }
        return maps.get(mapId);
    }


}
