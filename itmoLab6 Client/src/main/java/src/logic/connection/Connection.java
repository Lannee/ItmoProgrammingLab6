package src.logic.connection;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Connection {
    private String host;
    private int port;

    public Connection (String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void sendMessage(String message) {
        try {
            byte[] dataToSend = message.getBytes();
            InetAddress address = InetAddress.getByName(host);
            DatagramPacket packet = new DatagramPacket(dataToSend, dataToSend.length, address, port);
            System.out.println(packet.getData());
            DatagramSocket socket = new DatagramSocket();
            socket.send(packet);
            socket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public String getMessageToSend() {
        String message = "Zhopa";
        return message.trim();
    }

}
