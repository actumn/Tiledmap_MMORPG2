package network;

import protocol.Packet.JsonPacketFactory;
import protocol.Packet.PacketFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.json.simple.JSONObject;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Lee on 2016-05-23.
 */
public class Network {
    private static Network instance = new Network("localhost", 6112);
    public static Network getInstance() { return instance; }

    private PacketFactory packetFactory = new JsonPacketFactory();
    private ConcurrentLinkedQueue<JSONObject> recvQueue = new ConcurrentLinkedQueue<JSONObject>();


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


    public void disconnect() {
        this.group.shutdownGracefully();
    }

    public Channel getChannel() {
        return channel;
    }

    public void send(JSONObject object) {
        channel.write(object.toJSONString());
    }
}
