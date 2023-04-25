package src.logic.connection;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import src.utils.requestModule.Request;

public class Connection {
    private String host;
    private int port;

    public Connection (String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void sendRequest(Request request) {
        try {
            byte[] dataToSend;
            ByteArrayOutputStream byteOS = new ByteArrayOutputStream();
            ObjectOutputStream objOS = new ObjectOutputStream(byteOS);
            objOS.writeObject(request);
            dataToSend = byteOS.toByteArray();
            InetAddress address = InetAddress.getByName(host);
            DatagramPacket packet = new DatagramPacket(dataToSend, dataToSend.length, address, port);
            DatagramSocket socket = new DatagramSocket();
            socket.send(packet);
            socket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
