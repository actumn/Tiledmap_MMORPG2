package com.game.server.userservice;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.json.simple.JSONObject;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Lee on 2016-06-01.
 */
public class UserService {
    private final int port;
    public UserService(int port) {
        this.port = port;
    }
    private HashMap<Integer, MapProxy> maps = new HashMap<>();
    private LinkedList<UserObject> users = new LinkedList<>();

    public void start() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();


        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
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

            ChannelFuture future = b.bind(port).sync();
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public void loginUser(final UserObject user) {
        final int mapId = user.getMapId();

        MapProxy map = getMapProxy(mapId, true);
        user.initMap(map);

        this.users.add(user);
    }

    public void moveObject(final UserObject user, final JSONObject packet) {
        MapProxy src_map = getMapProxy(user.getMapId(), false);
        if(src_map == null) return;
        src_map.moveObject(user, packet);

        int dest_map_id = (int)(long) packet.get("dest_map_id");
        if(user.getMapId() == dest_map_id) return;

        MapProxy dest_map = getMapProxy(dest_map_id, true);
        if(dest_map == null) return;
        dest_map.moveObject(user, packet);
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



    public boolean containsUser(UserObject user) {
        return this.users.contains(user);
    }
}
