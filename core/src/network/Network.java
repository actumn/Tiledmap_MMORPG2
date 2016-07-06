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
    private ConcurrentLinkedQueue<JSONObject> recvQueue = new ConcurrentLinkedQueue<>();


    private Channel channel;
    private EventLoopGroup group;
    private boolean connected = false;

    private final String host;
    private final int port;
    private Network(String host, int port) {
        this.host = host;
        this.port = port;
    }


    public void connect() throws InterruptedException {
        this.group = new NioEventLoopGroup(1);

        Bootstrap bootstrap = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ClientInitializer());

        this.channel = bootstrap.connect(host, port).sync().channel();
        this.connected = true;
    }

    public void addPacket(JSONObject packet) {
        recvQueue.add(packet);
    }
    public JSONObject pollPacket() {
        return recvQueue.poll();
    }


    public void disconnect() {
        if (connected) this.group.shutdownGracefully();
        this.connected = false;
    }

    public void send(JSONObject object) {
        if (this.connected)
            this.channel.writeAndFlush(object.toJSONString()+"\r\n");
    }

    public boolean isConnected() {
        return connected;
    }

    public PacketFactory getPacketFactory() {
        return packetFactory;
    }

    public Channel TEST_getChannel() {
        return channel;
    }

    public void TEST_setChannel(Channel channel) {
        this.connected = true;
        this.channel = channel;
    }
}
