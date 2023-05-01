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
import java.net.UnknownHostException;

import src.utils.requestModule.Request;
import src.utils.responseModule.Response;

public class Connection {
    private String host;
    private int port;
    private byte[] buf = new byte[524];
    private DatagramSocket socket;

    public Connection (String host, int port) {
        this.host = host;
        this.port = port;
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {}
    }

    public Request catchRequest() {
        Request incomeRequest;
        try {
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
            ByteArrayInputStream byteOS = new ByteArrayInputStream(packet.getData());
            ObjectInputStream objIS = new ObjectInputStream(byteOS);
            incomeRequest = (Request) objIS.readObject();
        } catch (IOException | ClassNotFoundException e) {
            incomeRequest = new Request("null", "null", null);
            System.out.println("Connection error" + e.getMessage());         
        }
        return incomeRequest;
    }

    public void sendResponse(Response response) {
        try {
            byte[] dataToSend;
            ByteArrayOutputStream byteOS = new ByteArrayOutputStream();
            ObjectOutputStream objOS = new ObjectOutputStream(byteOS);
            objOS.writeObject(response);
            dataToSend = byteOS.toByteArray();
            DatagramPacket packet = new DatagramPacket(dataToSend, dataToSend.length, InetAddress.getByName(host), port);
            socket.send(packet);
            System.out.println("Sended");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
