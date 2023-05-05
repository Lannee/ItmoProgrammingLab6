package src.logic.connection;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import module.connection.requestModule.Request;
import module.connection.responseModule.Response;

public class Connection {
    private final static int PACKAGE_SIZE = 2048;
    private String host;
    private int port;
    private byte[] buf = new byte[PACKAGE_SIZE];
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
        Response incomeResponse = null;
        try {
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
            System.out.println("Catched");
            ByteArrayInputStream byteOS = new ByteArrayInputStream(packet.getData());
            ObjectInputStream objIS = new ObjectInputStream(byteOS);
            incomeResponse = (Response) objIS.readObject();
//            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Connection error");
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

    public Response sendRequestGetResponse(Request request) {
        sendRequest(request);
        return catchResponse();
    }
}
