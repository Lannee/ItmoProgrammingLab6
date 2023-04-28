package src.logic.connection;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import src.utils.requestModule.Request;
import src.utils.requestModule.TypeOfRequest;

public class RequestCatcher {
    private DatagramSocket socket;
    private boolean running = true;
    private byte[] buf = new byte[524];

    public RequestCatcher() {
        try {
            socket = new DatagramSocket(8448);
            System.out.println("Server is ready!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Request run() {
        Request incomeRequest;
        try {
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
            ByteArrayInputStream byteOS = new ByteArrayInputStream(packet.getData());
            ObjectInputStream objIS = new ObjectInputStream(byteOS);
            incomeRequest = (Request) objIS.readObject();
        } catch (IOException | ClassNotFoundException e) {
            incomeRequest = new Request("null", "null", null);
            System.out.println("Connection error");
        }
        return incomeRequest;
    }
}
