package src.logic.connection;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import src.utils.requestModule.Request;
import src.utils.responseModule.Response;

public class Connection {
    private final static int BUFFER_SIZE = 2048;

    private String host;
    private int port;
    private byte[] buf = new byte[BUFFER_SIZE];
    private DatagramSocket socket;

    public Connection (String host, int port) {
        this.host = host;
        this.port = port;
        try {
            socket = new DatagramSocket();
            socket.setReuseAddress(true); // needed for IP multicasting
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public Response catchResponse() {
        Response incomeResponse;
        try {
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
            ByteArrayInputStream byteOS = new ByteArrayInputStream(packet.getData());
            ObjectInputStream objIS = new ObjectInputStream(byteOS);
            incomeResponse = (Response) objIS.readObject();
        } catch (IOException | ClassNotFoundException e) {
            incomeResponse = new Response("", null);
            System.out.println("Connection error" + e.getMessage());
        }
        return incomeResponse;
    }

    public void sendRequest(Request request) {
        try {
            byte[] dataToSend;
            ByteArrayOutputStream byteOS = new ByteArrayOutputStream();
            ObjectOutputStream objOS = new ObjectOutputStream(byteOS);
            objOS.writeObject(request);
            dataToSend = byteOS.toByteArray();
            InetAddress hostAddress = InetAddress.getByName(host);
            DatagramPacket packet = new DatagramPacket(dataToSend, dataToSend.length, hostAddress, port);
            socket.send(packet);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
