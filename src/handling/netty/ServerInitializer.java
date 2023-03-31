/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handling.netty;

import handling.MapleServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

/**
 *
 * @author wubin
 */
public class ServerInitializer extends ChannelInitializer<SocketChannel> {

    private int world;
    private int channels;
    private int port;
    private boolean cs;

    public ServerInitializer() {
    }

    public ServerInitializer(int world, int channels, int port, boolean cs) {
        this.world = world;
        this.channels = channels;
        this.port = port;
        this.cs = cs;
    }

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipe = channel.pipeline();
        pipe.addLast(new IdleStateHandler(90, 90, 0));
        pipe.addLast("decoder", new MaplePacketDecoder()); // decodes the packet
        pipe.addLast("encoder", new MaplePacketEncoder()); //encodes the packet
        pipe.addLast("handler", new MapleServerHandler(channels, cs)); //encodes the packet
    }

}
