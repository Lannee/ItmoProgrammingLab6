package src.logic.connection;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import src.utils.requestModule.Request;
import src.utils.responseModule.Response;

public class Connection {
    private String host;
    private int serversPort;
    private SocketAddress clientSocketAddress;
    private byte[] buf = new byte[524];
    private DatagramSocket socket;

    

    public Connection (String host, int port) {
        this.host = host;
        this.serversPort = port;
        try {
            socket = new DatagramSocket(port);
            // socket.setReuseAddress(true); // needed for IP multicasting
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public Request catchRequest() {
        Request incomeRequest;
        try {
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
            clientSocketAddress = packet.getSocketAddress();
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
            InetAddress hostAddress = InetAddress.getByName(host);
            DatagramPacket packet = new DatagramPacket(dataToSend, dataToSend.length, clientSocketAddress);
            socket.send(packet);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
