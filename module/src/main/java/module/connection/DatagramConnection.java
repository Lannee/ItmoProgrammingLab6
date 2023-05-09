package module.connection;

import module.connection.packaging.Packet;
import module.connection.packaging.PacketManager;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;


import java.io.IOException;
import java.io.Serializable;
import java.net.*;

public class DatagramConnection implements IConnection {

    public static final int STANDARD_PORT = 8787;

    public static final int PACKAGE_SIZE = Packet.PACKAGE_SIZE;

//    private String host;
    private InetAddress host;

    private int port;

    private boolean isListeningPort;

    private final DatagramSocket socket;
    private static final Logger logger = LoggerFactory.getLogger(DatagramConnection.class);

    public DatagramConnection() throws UnknownHostException {
        this(false);
    }

    public DatagramConnection(boolean isListeningPort) throws UnknownHostException {
        this(STANDARD_PORT, isListeningPort);
    }

    public DatagramConnection(int port) throws UnknownHostException {
        this(port, false);
    }

    public DatagramConnection(int port, boolean isListeningPort) throws UnknownHostException {
        this("localhost", port, isListeningPort);
    }

    public DatagramConnection(String host, int port) throws UnknownHostException {
        this(host, port, false);
    }
    public DatagramConnection(String host, int port, boolean isListeningPort) throws UnknownHostException {
        this.host = InetAddress.getByName(host);
        this.port = port;
        this.isListeningPort = isListeningPort;

        try {
            if(isListeningPort)
                socket = new DatagramSocket(port);
            else
                socket = new DatagramSocket();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void send(Serializable object) {
        try {
            Packet[] packets = PacketManager.split(object);

            for (Packet packet : packets) {
                DatagramPacket datagramPacket = new DatagramPacket(
                        PacketManager.serialize(packet),
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

                    if(isListeningPort) {
                        this.host = datagramPacket.getAddress();
                        this.port = datagramPacket.getPort();
                    }
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
