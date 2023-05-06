package module.connection;

import module.connection.packaging.Packet;
import module.connection.packaging.PacketManager;

import java.io.IOException;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Connection {

    public static final int STANDARD_PORT = 8787;

    public static final int PACKAGE_SIZE = Packet.PACKAGE_SIZE;

    private String host;
    private int port;

    private final DatagramSocket socket;

    public Connection() {
        this(STANDARD_PORT);
    }

    public Connection(int port) {
        this("", port);
    }

    public Connection(String host, int port) {
        this.host = host;
        this.port = port;

        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    public void send(Serializable object) {
        try {
            Packet[] packets = PacketManager.split(object);

            InetAddress hostAddress = InetAddress.getByName(host);

            for(int i = 0; i < packets.length; i++) {
                DatagramPacket datagramPacket = new DatagramPacket(
                        PacketManager.serialize(packets[i]),
                        PACKAGE_SIZE,
                        hostAddress,
                        port);

                socket.send(datagramPacket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
