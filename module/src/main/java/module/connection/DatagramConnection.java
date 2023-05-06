package module.connection;

import module.connection.packaging.Packet;
import module.connection.packaging.PacketManager;

import java.io.IOException;
import java.io.Serializable;
import java.net.*;

public class DatagramConnection implements IConnection {

    public static final int STANDARD_PORT = 8787;

    public static final int PACKAGE_SIZE = Packet.PACKAGE_SIZE;

//    private String host;
    private InetAddress host;

    private int port;

    private final DatagramSocket socket;

    public DatagramConnection() throws UnknownHostException {
        this(STANDARD_PORT);
    }

    public DatagramConnection(int port) throws UnknownHostException {
        this("localhost", port);
    }

    public DatagramConnection(String host, int port) throws UnknownHostException {
        this.host = InetAddress.getByName(host);
        this.port = port;

        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void send(Serializable object) {
        try {
            Packet[] packets = PacketManager.split(object);

            for(int i = 0; i < packets.length; i++) {
                DatagramPacket datagramPacket = new DatagramPacket(
                        PacketManager.serialize(packets[i]),
                        PACKAGE_SIZE,
                        host,
                        port);

                socket.send(datagramPacket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Serializable receive() {
        Serializable object;
        try {
            Packet[] packets = new Packet[0];
            byte[] bytes = new byte[PACKAGE_SIZE];

            int counter = 0;
            int packagesAmount = 1;
            do {
                DatagramPacket datagramPacket = new DatagramPacket(bytes, PACKAGE_SIZE);
                socket.receive(datagramPacket);
                Packet packet = (Packet) PacketManager.deserialize(bytes);

                if(counter == 0) {
                    packagesAmount = packet.getPackagesAmount();
                    packets = new Packet[packagesAmount];
                }

                packets[counter] = packet;

            } while (++counter != packagesAmount);

            object = PacketManager.assemble(packets);

        } catch (IOException io) {
            throw new RuntimeException(io);
        }

        return object;
    }
}
