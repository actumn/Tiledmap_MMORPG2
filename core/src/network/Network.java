package network;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by Lee on 2016-05-23.
 */
public class Network {
    private static Network instance = new Network("localhost", 6112);
    public static Network getInstance() { return instance; }

    private Channel channel;
    private EventLoopGroup group;

    private final String host;
    private final int port;
    public Network(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void connect() throws InterruptedException {
        this.group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ClientInitializer());

        this.channel = bootstrap.connect(host, port).sync().channel();
    }

    public void send() {

    }

    public void disconnect() {
        this.group.shutdownGracefully();
    }
}
