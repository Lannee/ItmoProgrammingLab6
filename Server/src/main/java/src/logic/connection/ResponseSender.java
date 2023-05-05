package src.logic.connection;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import src.utils.responseModule.Response;

public class ResponseSender {
    private String host;
    private int port;
    private byte[] buf = new byte[524];
    private DatagramSocket socket;

    public ResponseSender(String host, int port) {
        this.host = host;
        this.port = port;
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {}
    }

    public void sendResponse(Response response) {
        try {
            byte[] dataToSend;
            ByteArrayOutputStream byteOS = new ByteArrayOutputStream();
            ObjectOutputStream objOS = new ObjectOutputStream(byteOS);
            objOS.writeObject(response);
            dataToSend = byteOS.toByteArray();
            InetAddress hostAddress = InetAddress.getByName(host);
            DatagramPacket packet = new DatagramPacket(dataToSend, dataToSend.length, hostAddress, port);
            socket.send(packet);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
