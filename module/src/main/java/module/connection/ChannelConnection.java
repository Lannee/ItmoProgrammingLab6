package module.connection;

import module.connection.packaging.Packet;
import module.connection.packaging.PacketManager;

import java.io.IOException;
import java.io.Serializable;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class ChannelConnection implements IConnection {

    public static final int STANDARD_PORT = 8787;

    public static final int PACKAGE_SIZE = Packet.PACKAGE_SIZE;

//    private String host;
    private InetAddress host;
    private int port;

    private final DatagramChannel datagramChannel;

    public ChannelConnection() throws UnknownHostException {
        this(STANDARD_PORT);
    }

    public ChannelConnection(int port) throws UnknownHostException {
        this("localhost", port);
    }

    public ChannelConnection(String host, int port) throws UnknownHostException {
        this.host = InetAddress.getByName(host);
        this.port = port;
        try {
            datagramChannel = DatagramChannel.open();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void send(Serializable obj) {
        try {
            Packet[] packets = PacketManager.split(obj);
            ByteBuffer byteBuffer = ByteBuffer.allocate(PACKAGE_SIZE);

            SocketAddress address = new InetSocketAddress(host, port);

            for(int i = 0; i < packets.length; i++) {
                byteBuffer.put(PacketManager.serialize(packets[i]));
                datagramChannel.send(byteBuffer, address);
            }
        } catch (IOException io) {
            io.printStackTrace();
        }

    }

    @Override
    public Serializable receive() {
        Serializable object;
        try {
            Packet[] packets = new Packet[0];
            ByteBuffer byteBuffer = ByteBuffer.allocate(PACKAGE_SIZE);

            int counter = 0;
            int packagesAmount = 1;
            do {
                datagramChannel.receive(byteBuffer);
                Packet packet = (Packet) PacketManager.deserialize(byteBuffer.array());

                if(counter == 0) {
                    packagesAmount = packet.getPackagesAmount();
                    packets = new Packet[packagesAmount];
                }

                packets[counter] = packet;

            } while (++counter != packagesAmount);

            object = PacketManager.assemble(packets);

        } catch (IOException io) {
            io.printStackTrace();
            return null;
        }

        return object;
    }
}